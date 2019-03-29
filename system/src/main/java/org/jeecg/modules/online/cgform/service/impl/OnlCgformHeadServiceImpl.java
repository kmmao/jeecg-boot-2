package org.jeecg.modules.online.cgform.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.jeecg.modules.online.cgform.util.CgformUtil;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.model.CgformConfigModel;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.jeecg.modules.online.config.util.DbTableProcess;
import org.jeecg.modules.online.config.util.DbTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import freemarker.template.TemplateException;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
@Service
public class OnlCgformHeadServiceImpl extends ServiceImpl<OnlCgformHeadMapper, OnlCgformHead> implements IOnlCgformHeadService {

	@Autowired
	private IOnlCgformFieldService fieldService;
	@Autowired
	private IOnlCgformIndexService indexService;
	
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
			field.setOrderNum(i);
		}

		for (OnlCgformIndex index : indexs) {
			index.setId(null);
			index.setCgformHeadId(uuid);
		}

		super.save(head);
		fieldService.saveBatch(fields);
		indexService.saveBatch(indexs);

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
			onlCgformField.setOrderNum(++maxOrderNum);
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

}
