package org.jeecg.modules.online.cgform.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.mapper.OnlCgformButtonMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceJavaMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceJsMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceSqlMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.jeecg.modules.online.cgform.util.CgformUtil;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.model.CgformConfigModel;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.jeecg.modules.online.config.util.DataBaseConst;
import org.jeecg.modules.online.config.util.DbTableProcess;
import org.jeecg.modules.online.config.util.DbTableUtil;
import org.jeecgframework.codegenerate.database.DbReadTableUtil;
import org.jeecgframework.codegenerate.generate.pojo.ColumnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
@Slf4j
@Service
public class OnlCgformHeadServiceImpl extends ServiceImpl<OnlCgformHeadMapper, OnlCgformHead> implements IOnlCgformHeadService {

	@Autowired
	private IOnlCgformFieldService fieldService;
	@Autowired
	private IOnlCgformIndexService indexService;
	
	@Resource
	private OnlCgformEnhanceJsMapper onlCgformEnhanceJsMapper;
	
	@Resource
	private OnlCgformButtonMapper onlCgformButtonMapper;
	
	@Resource
	private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;
	
	@Resource
	private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> addAll(OnlCgformModel model) {

		String uuid = UUID.randomUUID().toString().replace("-", "");

		OnlCgformHead head = model.getHead();
		List<OnlCgformField> fields = model.getFields();
		List<OnlCgformIndex> indexs = model.getIndexs();

		head.setId(uuid);

		for (int i = 0; i < fields.size(); i++) {
			OnlCgformField field = fields.get(i);
			field.setId(null);
			field.setCgformHeadId(uuid);
			if (field.getOrderNum() == null) {
				field.setOrderNum(i);
			}
		}

		for (OnlCgformIndex index : indexs) {
			index.setId(null);
			index.setCgformHeadId(uuid);
		}

		super.save(head);
		fieldService.saveBatch(fields);
		indexService.saveBatch(indexs);
		
		//添加一张表 需要处理是否有主表信息
		handleMainTableInfo(head, fields);

		return Result.ok("添加成功");
	}

	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> editAll(OnlCgformModel model) {
		OnlCgformHead head = model.getHead();

		OnlCgformHead headEntity = super.getById(head.getId());
		if (headEntity == null) {
			return Result.error("未找到对应实体");
		}
		String isDbSync = headEntity.getIsDbSynch();
		if(CgformUtil.databaseTableIsChange(headEntity, head)) {
			isDbSync = "N";
		}
		// table version 自增1
		Integer version = headEntity.getTableVersion();
		if (version == null) {
			version = 1;
		}
		head.setTableVersion(++version);

		List<OnlCgformField> fields = model.getFields();
		List<OnlCgformIndex> indexs = model.getIndexs();

		// 将fields分类成 添加和修改 两个列表
		List<OnlCgformField> addFields = new ArrayList<OnlCgformField>();
		List<OnlCgformField> editFields = new ArrayList<OnlCgformField>();
		for (OnlCgformField field : fields) {
			String id = String.valueOf(field.getId());

			if (id.length() == 32) {
				// 修改项
				editFields.add(field);

			} else {
				// id 不做修改
				String primaryKey = "_pk";
				if (!primaryKey.equals(id)) {
					// 新增项
					field.setId(null);
					field.setCgformHeadId(head.getId());
					addFields.add(field);
				}
			}
		}
		if(addFields.size()>0) {
			isDbSync = "N";
		}
		int maxOrderNum = 0;
		// 批量修改
		for (OnlCgformField field : editFields) {
			OnlCgformField dbField = this.fieldService.getById(field.getId());
			boolean ischange = CgformUtil.databaseFieldIsChange(dbField, field);
			if(ischange) {
				isDbSync = "N";
			}
			if((dbField.getOrderNum()==null?0:dbField.getOrderNum())>maxOrderNum) {
				maxOrderNum = dbField.getOrderNum();
			}
			fieldService.updateById(field);
		}
		//fieldService.saveBatch(addFields);
		for (OnlCgformField  onlCgformField: addFields) {
			if (onlCgformField.getOrderNum() == null) {
				onlCgformField.setOrderNum(++maxOrderNum);
			}
			fieldService.save(onlCgformField);
		}

		// 将item分类
		List<OnlCgformIndex> addIndex = new ArrayList<OnlCgformIndex>();
		List<OnlCgformIndex> editIndex = new ArrayList<OnlCgformIndex>();
		for (OnlCgformIndex index : indexs) {
			String id = String.valueOf(index.getId());

			if (id.length() == 32) {
				// 修改项
				editIndex.add(index);

			} else {
				// 新增项
				index.setId(null);
				index.setCgformHeadId(head.getId());
				addIndex.add(index);
			}
		}
		if(addIndex.size()>0) {
			isDbSync = "N";
		}
		indexService.saveBatch(addIndex);
		for (OnlCgformIndex index : editIndex) {
			OnlCgformIndex dbIndex = this.indexService.getById(index.getId());
			boolean ischange = CgformUtil.databaseIndexIsChange(dbIndex, index);
			if(ischange) {
				isDbSync = "N";
			}
			indexService.updateById(index);
		}

		// 删除项
		if (model.getDeleteFieldIds().size() > 0) {
			fieldService.removeByIds(model.getDeleteFieldIds());
		}
		if (model.getDeleteIndexIds().size() > 0) {
			indexService.removeByIds(model.getDeleteIndexIds());
		}

		head.setIsDbSynch(isDbSync);
		super.updateById(head);
		
		handleMainTableInfo(head, fields);
		
		return Result.ok("全部修改成功");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doDbSynch(String code,String synMethod) throws HibernateException, IOException, TemplateException, SQLException, DBException {
		//1.判断实体配置
		OnlCgformHead onlCgformHead = this.getById(code);
		if(onlCgformHead==null) {
			throw new DBException("实体配置不存在");
		}
		String tbname = onlCgformHead.getTableName();
		//2.查询字段集合
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.orderByAsc(OnlCgformField::getOrderNum);
		
		List<OnlCgformField> fieldList =  fieldService.list(query);
		//3.构造同步参数CgformConfigModel实例
		CgformConfigModel model = new CgformConfigModel();
		model.setTableName(tbname);
		model.setJformPkType(onlCgformHead.getIdType());
		model.setJformPkSequence(onlCgformHead.getIdSequence());
		model.setContent(onlCgformHead.getTableTxt());
		model.setColumns(fieldList);
		//4.判断是否普通同步
		if (CgformUtil.SYNC_NORMAL.equals(synMethod)) {
			boolean isExist = DbTableUtil.judgeTableIsExit(tbname);
			//判断数据库表是否存在 
			if(isExist) {
				DbTableProcess process = new DbTableProcess();
				List<String> list = process.updateTable(model);
				for (String sql : list) {
					if(oConvertUtils.isEmpty(sql)||oConvertUtils.isEmpty(sql.trim())) {
						continue;
					}
					this.baseMapper.executeDDL(sql);
				}
				//表已存在 那么就先删除索引
				List<OnlCgformIndex> configIndexList = indexService.list(new LambdaQueryWrapper<OnlCgformIndex>().eq(OnlCgformIndex::getCgformHeadId, code));
				
				for (OnlCgformIndex configIndex : configIndexList) {
					String delIndexSql = process.dropIndex(configIndex.getIndexName(), tbname);
					this.baseMapper.executeDDL(delIndexSql);
				}
			}else {
				DbTableProcess.createTable(model);
				
			}
		}else if (CgformUtil.SYNC_FORCE.equals(synMethod)) {
			//强制同步，先删除后添加
			DbTableHandleI handler = DbTableUtil.getTableHandle();
			String sql = handler.dropTableSQL(tbname);
			this.baseMapper.executeDDL(sql);
			DbTableProcess.createTable(model);
		}
		
		//处理完表 再处理索引
		indexService.createIndex(code, DbTableUtil.getDatabaseType(),tbname);
		
		//同步完成 修改同步标识
		onlCgformHead.setIsDbSynch("Y");
		this.updateById(onlCgformHead);
	}

	@Override
	public void deleteRecordAndTable(String id) throws DBException, SQLException {
		OnlCgformHead onlCgformHead = this.getById(id);
		if(onlCgformHead==null) {
			throw new DBException("实体配置不存在");
		}
		String sql = DbTableUtil.getTableHandle().dropTableSQL(onlCgformHead.getTableName());
		this.baseMapper.executeDDL(sql);
		this.baseMapper.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryListData(String sql){
		return this.baseMapper.queryList(sql);

	}

	@Override
	public void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
		this.onlCgformEnhanceJsMapper.insert(onlCgformEnhanceJs);
	}

	@Override
	public OnlCgformEnhanceJs queryEnhance(String code,String type) {
		return this.onlCgformEnhanceJsMapper.selectOne(new LambdaQueryWrapper<OnlCgformEnhanceJs>().eq(OnlCgformEnhanceJs::getCgJsType , type).eq(OnlCgformEnhanceJs::getCgformHeadId , code));
	}

	@Override
	public void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs) {
		this.onlCgformEnhanceJsMapper.updateById(onlCgformEnhanceJs);
		
	}

	@Override
	public OnlCgformEnhanceSql queryEnhanceSql(String formId,String buttonCode) {
		return this.onlCgformEnhanceSqlMapper.selectOne(new LambdaQueryWrapper<OnlCgformEnhanceSql>().eq(OnlCgformEnhanceSql::getCgformHeadId, formId).eq(OnlCgformEnhanceSql::getButtonCode, buttonCode));
	}

	@Override
	public void saveEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql) {
		onlCgformEnhanceSqlMapper.insert(onlCgformEnhanceSql);
		
	}

	@Override
	public void editEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql) {
		onlCgformEnhanceSqlMapper.updateById(onlCgformEnhanceSql);
		
	}

	@Override
	public OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
		LambdaQueryWrapper<OnlCgformEnhanceJava> query = new LambdaQueryWrapper<OnlCgformEnhanceJava>();
		query.eq(OnlCgformEnhanceJava::getButtonCode,onlCgformEnhanceJava.getButtonCode());
		query.eq(OnlCgformEnhanceJava::getCgformHeadId,onlCgformEnhanceJava.getCgformHeadId());
		query.eq(OnlCgformEnhanceJava::getCgJavaType,onlCgformEnhanceJava.getCgJavaType());
		query.eq(OnlCgformEnhanceJava::getEvent,onlCgformEnhanceJava.getEvent());
		return this.onlCgformEnhanceJavaMapper.selectOne(query);
	}

	@Override
	public void saveEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
		this.onlCgformEnhanceJavaMapper.insert(onlCgformEnhanceJava);
		
	}

	@Override
	public void editEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
		this.onlCgformEnhanceJavaMapper.updateById(onlCgformEnhanceJava);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OnlCgformButton> queryButtonList(String code) {
		LambdaQueryWrapper<OnlCgformButton> query = new LambdaQueryWrapper<OnlCgformButton>();
		query.eq(OnlCgformButton::getButtonStatus, "1");
		query.eq(OnlCgformButton::getCgformHeadId, code);
		query.orderByAsc(OnlCgformButton::getOrderNum);
		return this.onlCgformButtonMapper.selectList(query);
	}

	@Override
	public boolean checkOnlyEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
		LambdaQueryWrapper<OnlCgformEnhanceJava> query = new LambdaQueryWrapper<OnlCgformEnhanceJava>();
		query.eq(OnlCgformEnhanceJava::getButtonCode,onlCgformEnhanceJava.getButtonCode());
		query.eq(OnlCgformEnhanceJava::getCgformHeadId,onlCgformEnhanceJava.getCgformHeadId());
		query.eq(OnlCgformEnhanceJava::getCgJavaType,onlCgformEnhanceJava.getCgJavaType());
		query.eq(OnlCgformEnhanceJava::getEvent,onlCgformEnhanceJava.getEvent());
		Integer count = this.onlCgformEnhanceJavaMapper.selectCount(query);
		if(count!=null) {
			if(count==1 && oConvertUtils.isEmpty(onlCgformEnhanceJava.getId())) {
				return false;
			}else if(count==2) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<String> queryOnlinetables() {
		return this.baseMapper.queryOnlinetables();
	}

	@Override
	public void saveDbTable2Online(String tbname) {
		OnlCgformHead cgFormHead = new OnlCgformHead();
		cgFormHead.setTableType(1);
		cgFormHead.setIsCheckbox("Y");
		cgFormHead.setIsDbSynch("Y");
		cgFormHead.setIsTree("N");
		cgFormHead.setIsPage("Y");
		cgFormHead.setQueryMode("group");
		cgFormHead.setTableName(tbname.toLowerCase());
		cgFormHead.setTableTxt(tbname);//TODO 能不能获取表的备注信息
		cgFormHead.setTableVersion(1);
		cgFormHead.setFormTemplate("1");//默认一列展示
		String cgFormId = UUIDGenerator.generate();
		cgFormHead.setId(cgFormId);
		List<OnlCgformField> columnsList = new ArrayList<OnlCgformField>();
		try {
			List<ColumnVo> list = DbReadTableUtil.a(tbname);
			for (int k = 0; k < list.size(); k++) {
				ColumnVo columnt = list.get(k);
				log.info("  columnt : "+ columnt.toString());
				String fieldName = columnt.getFieldDbName();
				OnlCgformField cgFormField = new OnlCgformField();
				cgFormField.setCgformHeadId(cgFormId);
				cgFormField.setDbFieldNameOld(columnt.getFieldDbName().toLowerCase());
				cgFormField.setDbFieldName(columnt.getFieldDbName().toLowerCase());
						
				if (oConvertUtils.isNotEmpty(columnt.getFiledComment())) {
					cgFormField.setDbFieldTxt(columnt.getFiledComment());
				} else {
					cgFormField.setDbFieldTxt(columnt.getFieldName());
				}
				
				cgFormField.setDbIsKey(0);
				cgFormField.setIsShowForm(1);
				cgFormField.setIsQuery(0);
				cgFormField.setFieldMustInput("0");
				cgFormField.setIsShowList(1);
				cgFormField.setOrderNum(k + 2);
				cgFormField.setQueryMode("group");
				cgFormField.setDbLength(oConvertUtils.getInt(columnt.getPrecision()));
				cgFormField.setFieldLength(120);
				cgFormField.setDbPointLength(oConvertUtils.getInt(columnt.getScale()));
				cgFormField.setFieldShowType("text");
				cgFormField.setDbIsNull("Y".equals(columnt.getNullable())?1:0);
				
				if("id".equalsIgnoreCase(fieldName)){
					String[] pkTypeArr = {"java.lang.Integer","java.lang.Long"};
					String idFiledType = columnt.getFieldType();
					if(Arrays.asList(pkTypeArr).contains(idFiledType)){
						//如果主键是数字类型,则设置为自增长
						cgFormHead.setIdType("NATIVE");
					}else{
						//否则设置为UUID
						cgFormHead.setIdType("UUID");
					}
					cgFormField.setDbIsKey(1);
					cgFormField.setIsShowForm(0);
					cgFormField.setIsShowList(0);
				}
				if ("java.lang.Integer".equalsIgnoreCase(columnt.getFieldType())){
					cgFormField.setDbType(DataBaseConst.INT);
				}else if ("java.lang.Long".equalsIgnoreCase(columnt.getFieldType())) {
					cgFormField.setDbType(DataBaseConst.INT);
				} else if ("java.util.Date".equalsIgnoreCase(columnt.getFieldType())) {
					cgFormField.setDbType(DataBaseConst.DATE);
					cgFormField.setFieldShowType("date");
				} else if ("java.lang.Double".equalsIgnoreCase(columnt.getFieldType())
						||"java.lang.Float".equalsIgnoreCase(columnt.getFieldType())) {
					cgFormField.setDbType(DataBaseConst.DOUBLE);
				} else if ("java.math.BigDecimal".equalsIgnoreCase(columnt.getFieldType()) || "BigDecimal".equalsIgnoreCase(columnt.getFieldType())) {
					cgFormField.setDbType(DataBaseConst.BIGDECIMAL);
				} else if ("byte[]".equalsIgnoreCase(columnt.getFieldType()) || columnt.getFieldType().contains("blob")) {
					cgFormField.setDbType(DataBaseConst.BLOB);
					columnt.setCharmaxLength(null);
				} else {
					cgFormField.setDbType(DataBaseConst.STRING);
				}
				if (oConvertUtils.isEmpty(columnt.getPrecision()) && oConvertUtils.isNotEmpty(columnt.getCharmaxLength())) {
					if (Long.valueOf(columnt.getCharmaxLength()) >= 3000) {
						cgFormField.setDbType(DataBaseConst.TEXT);
						cgFormField.setFieldShowType(DataBaseConst.TEXTAREA);
						try{//有可能长度超出int的长度
							cgFormField.setDbLength(Integer.valueOf(columnt.getCharmaxLength()));
						}catch(Exception e){}
					} else {
						cgFormField.setDbLength(Integer.valueOf(columnt.getCharmaxLength()));
					}
				} else {
					if (oConvertUtils.isNotEmpty(columnt.getPrecision())) {
						cgFormField.setDbLength(Integer.valueOf(columnt.getPrecision()));
					}
					else{
						if(cgFormField.getDbType().equals(DataBaseConst.INT)){
							cgFormField.setDbLength(10);
						}
					}
					if (oConvertUtils.isNotEmpty(columnt.getScale())) {
						cgFormField.setDbPointLength(Integer.valueOf(columnt.getScale()));
					}

				}
				columnsList.add(cgFormField);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(oConvertUtils.isEmpty(cgFormHead.getFormCategory())){
			cgFormHead.setFormCategory("bdfl_include");
		}
		
		//保存表 字段 索引信息 
		//TODO 话说索引信息去哪里了
		this.save(cgFormHead);
		this.fieldService.saveBatch(columnsList);
		
	}
	
	/**
	 * 若当前为子表 需要同步主表的SubTableStr字段
	 * @param head
	 * @param fields
	 */
	private void handleMainTableInfo(OnlCgformHead head, List<OnlCgformField> fields) {
		//1.判断子表类型
		if(head.getTableType()==3) {
			head = this.baseMapper.selectById(head.getId());
			for (int i = 0; i < fields.size(); i++) {
				//2.遍历子表字段 拿到字段的外键配置
				OnlCgformField field = fields.get(i);
				String tbName = field.getMainTable();
				if(oConvertUtils.isEmpty(tbName)) {
					continue;
				}
				//3.拿到外键表名 获取主表信息
				OnlCgformHead mainTable = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tbName));
				if(mainTable==null) {
					continue;
				}
				
				//4.拿到主表SubTableStr 和当前这个做排序楼
				String allSubTable = mainTable.getSubTableStr();
				if(oConvertUtils.isEmpty(allSubTable)) {
					allSubTable = head.getTableName();
				}else {
					//TODO 这边应该对所有子表做一个排序
					if(allSubTable.indexOf(head.getTableName())>=0) {
						//如果当前这个子表字符串 包含当前子表 直接无视
						
					}else {
						//否则遍历、排序、加入
						@SuppressWarnings("unchecked")
						List<String> arr = new ArrayList(Arrays.asList(allSubTable.split(",")));
						for(int k=0;k<arr.size();k++) {
							String tempTbname = arr.get(k);
							OnlCgformHead tempTable = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tempTbname));
							if(tempTable==null) {
								continue;
							}
							if(head.getTabOrderNum()<tempTable.getTabOrderNum()) {
								arr.add(k, head.getTableName());
								break;
							}
						}
						if(arr.indexOf(head.getTableName())<0) {
							arr.add(head.getTableName());
						}
						allSubTable = String.join(",", arr);
					}
				}
				mainTable.setSubTableStr(allSubTable);
				this.baseMapper.updateById(mainTable);
				break;
			}
		}
	}


	@Override
	public JSONObject queryFormItem(String code) {
		OnlCgformHead head = this.baseMapper.selectById(code);
		if(head==null) {
			throw new JeecgBootException("表不存在");
		}
		if(head.getTableType()==3) {
			throw new JeecgBootException("该表类型不支持查看表单");
		}
		//单表
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.eq(OnlCgformField::getIsShowForm,1);
		List<OnlCgformField> fieldList = fieldService.list(query);
		JSONObject schema = CgformUtil.getJsonSchemaByCgformFieldList(fieldList);
		if(head.getTableType()==2) {
			//主表
			String subStr = head.getSubTableStr();
			if(oConvertUtils.isNotEmpty(subStr)) {
				for (String tbname : subStr.split(",")) {
					OnlCgformHead tempTable = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tbname));
					if(tempTable==null) {
						continue;
					}
					LambdaQueryWrapper<OnlCgformField> tempQuery = new LambdaQueryWrapper<OnlCgformField>();
					tempQuery.eq(OnlCgformField::getCgformHeadId, tempTable.getId());
					tempQuery.eq(OnlCgformField::getIsShowForm,1);
					List<OnlCgformField> subFieldList = fieldService.list(tempQuery);
					JSONObject subJson = CgformUtil.getSubJsonSchemaByCgformFieldList(tempTable.getTableTxt(), subFieldList);
					schema.getJSONObject("properties").put(tempTable.getTableName(), subJson);
				}
			}
		}
		log.info("----动态表单获取JSON-SCHEMA>>"+schema.toJSONString());
		return schema;
	}
	
	//TODO 这边有一个潜规则   即子表关联主表的那个字段(主表的字段)的要么是id 要么一定是表单上显示的字段 若违背这个原则 以下代码均有BUG.....
	// 小笔记：你不能设置一个不能被操作的字段当成关联子表的字段(id除外)
	@Override
	public void saveManyFormData(String code,JSONObject json) throws DBException {
		OnlCgformHead head = this.getById(code);
		if (head == null) {
			throw new DBException("数据库主表ID["+code+"]不存在");
		}
		json.put("id", UUIDGenerator.generate());
		String tbname = head.getTableName();
		if(head.getTableType()==2) {
			//如果他是主表 则还需要保存子表的数据
			String subTables = head.getSubTableStr();
			String[] arr = subTables.split(",");
			for (String tb : arr) {
				
				JSONArray jsonArray = json.getJSONArray(tb);
				if(jsonArray==null || jsonArray.size()==0) {
					continue;
				}
				
				OnlCgformHead temp = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tb));
				if(temp==null) {
					continue;
				}
				
				List<OnlCgformField> subFieldList = this.fieldService.queryFormFields(temp.getId(),false);
				//这里有一步非常重要 需要获取子表字段对应设置外键的那个字段然后赋值
				String sublinkField="",subLinkValue=null;
				for (OnlCgformField field : subFieldList) {
					if(oConvertUtils.isEmpty(field.getMainField())) {
						continue;
					}
					sublinkField = field.getDbFieldName();
					String mainField = field.getMainField();
					subLinkValue = json.getString(mainField);
				}
				
				for (Object obj : jsonArray) {
					JSONObject jsonObject = (JSONObject) obj;
					if(subLinkValue!=null) {
						jsonObject.put(sublinkField, subLinkValue);//设置外键值
					}
					this.fieldService.saveFormData(subFieldList, tb, jsonObject);
				}	
			}
		}
		this.fieldService.saveFormData(code, tbname, json);
	}

	@Override
	public Map<String, Object> queryManyFormData(String code, String id) throws DBException {
		OnlCgformHead head = this.getById(code);
		if (head == null) {
			throw new DBException("数据库主表ID["+code+"]不存在");
		}
		List<OnlCgformField> fieldList = this.fieldService.queryFormFields(code,true);
		Map<String, Object> map = this.fieldService.queryFormData(fieldList, head.getTableName(), id);
		if(head.getTableType()==2) {
			//如果他是主表 则还需要查询子表的数据 查询子表数据 需找到关联字段赋值
			String subTables = head.getSubTableStr();
			String[] arr = subTables.split(",");
			for (String tb : arr) {
				OnlCgformHead temp = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tb));
				if(temp==null) {
					continue;
				}
				List<OnlCgformField> subFieldList = this.fieldService.queryFormFields(temp.getId(),false);
				String sublinkField="",subLinkValue=null;
				for (OnlCgformField field : subFieldList) {
					if(oConvertUtils.isEmpty(field.getMainField())) {
						continue;
					}
					sublinkField = field.getDbFieldName();
					String mainField = field.getMainField();
					// 空指针 wcd?
					subLinkValue = map.get(mainField).toString();
				}
				
				List<Map<String, Object>> subList = this.fieldService.querySubFormData(subFieldList, tb, sublinkField, subLinkValue);
				if(subList==null || subList.size()==0) {
					map.put(tb, new String[] {});
				}else {
					map.put(tb, subList);
				}
			}
		}
		return map;
	}



	@Override
	public void editManyFormData(String code, JSONObject json) throws DBException {
		OnlCgformHead head = this.getById(code);
		if (head == null) {
			throw new DBException("数据库主表ID["+code+"]不存在");
		}
		String tbname = head.getTableName();
		fieldService.editFormData(code, tbname, json);
		if(head.getTableType()==2) {
			String subTables = head.getSubTableStr();
			String[] arr = subTables.split(",");
			for (String tb : arr) {
				OnlCgformHead temp = this.baseMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tb));
				if(temp==null) {
					continue;
				}
				List<OnlCgformField> subFieldList = this.fieldService.queryFormFields(temp.getId(),false);
				String sublinkField="",subLinkValue=null;
				for (OnlCgformField field : subFieldList) {
					if(oConvertUtils.isEmpty(field.getMainField())) {
						continue;
					}
					sublinkField = field.getDbFieldName();
					String mainField = field.getMainField();
					subLinkValue = json.getString(mainField);
				}
				// 删除从表数据
				if(oConvertUtils.isEmpty(subLinkValue)) {
					continue;
				}
				fieldService.deleteAutoList(tb, sublinkField, subLinkValue);
				//新增数据
				JSONArray jsonArray = json.getJSONArray(tb);
				if(jsonArray==null || jsonArray.size()==0) {
					continue;
				}
				for (Object obj : jsonArray) {
					JSONObject jsonObject = (JSONObject) obj;
					if(subLinkValue!=null) {
						jsonObject.put(sublinkField, subLinkValue);//设置外键值
					}
					this.fieldService.saveFormData(subFieldList, tb, jsonObject);
				}	
			}
		}
		
	}

}
