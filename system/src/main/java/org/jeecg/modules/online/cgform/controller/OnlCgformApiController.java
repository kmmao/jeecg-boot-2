package org.jeecg.modules.online.cgform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.util.CgFormExcelHandler;
import org.jeecg.modules.online.cgform.util.CgformUtil;
import org.jeecg.modules.online.cgreport.util.BrowserUtils;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.util.DbTableUtil;
import org.jeecg.modules.system.service.ISysDictService;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title: Controller
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/online/cgform/api")
public class OnlCgformApiController {

	@Autowired
	private IOnlCgformHeadService onlCgformHeadService;
	
	@Autowired
	private IOnlCgformFieldService onlCgformFieldService;
	
	@Autowired
	private ISysDictService sysDictService;
	

	@PostMapping(value = "/addAll")
	public Result<?> addAll(@RequestBody OnlCgformModel model) {
		try {
			String tbname = model.getHead().getTableName();
			if(DbTableUtil.judgeTableIsExit(tbname)) {
				return Result.error("数据库表["+tbname+"]已存在,请从数据库导入表单");
			}
			return onlCgformHeadService.addAll(model);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.addAll()发生异常：" + e.getMessage());
			return Result.error("操作失败");
		}
	}

	@PutMapping(value = "/editAll")
	public Result<?> editAll(@RequestBody OnlCgformModel model) {
		try {
			return onlCgformHeadService.editAll(model);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.editAll()发生异常：" + e.getMessage());
			return Result.error("操作失败");
		}
	}
	
	
	/**
	 * 查询columns 和 数据字典
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/getColumns/{code}")
	public Result<Map<String,Object>> getColumns(@PathVariable("code") String code) {
		Result<Map<String,Object>> result = new Result<>();
		OnlCgformHead head = onlCgformHeadService.getById(code);
		if (head == null) {
			result.error500("实体不存在");
			return result;
		}
		//1.查询字段集合
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.eq(OnlCgformField::getIsShowList,1);
		query.orderByAsc(OnlCgformField::getOrderNum);
		List<OnlCgformField> fieldList = onlCgformFieldService.list(query);
		
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		Map<String, Object> dictOptions = new HashMap<>();
		for (OnlCgformField item : fieldList) {
			//2.拼接column json 排除id
			String dbFieldName = item.getDbFieldName();
			if("id".equals(dbFieldName)) {
				continue;
			}
			Map<String, Object> column = new HashMap<String, Object>(3);
			column.put("title",item.getDbFieldTxt());
			column.put("dataIndex",dbFieldName);
			column.put("align", "center");
			
			//3.字典项加载(也可以只把字典的信息存下来 返回到前端 在前端在做新的请求获取字典项)
			//TODO  字典表的加载
			String dictCode = item.getDictField();
			if(oConvertUtils.isNotEmpty(dictCode)) {
				List<Map<String,Object>> ls = sysDictService.queryDictItemsByCode(dictCode);
				dictOptions.put(dbFieldName,ls);
				column.put("customRender", dbFieldName);
			}
			
			//4.判断文件 
			String view = item.getFieldShowType();
			if(view.indexOf("file")>=0) {
				JSONObject scopedSlots = new JSONObject();
				scopedSlots.put("customRender", "fileSlot");
				column.put("scopedSlots", scopedSlots);
			}else if(view.indexOf("image")>=0) {
				JSONObject scopedSlots = new JSONObject();
				scopedSlots.put("customRender", "imgSlot");
				column.put("scopedSlots", scopedSlots);
			}else if(view.indexOf("editor")>=0) {
				JSONObject scopedSlots = new JSONObject();
				scopedSlots.put("customRender", "htmlSlot");
				column.put("scopedSlots", scopedSlots);
			}else if("date".equals(view)) {
				JSONObject scopedSlots = new JSONObject();
				scopedSlots.put("customRender", "dateSlot");
				column.put("scopedSlots", scopedSlots);
			}
			//5.判断字段类型 时间/数值 支持排序
			String dbtype = item.getDbType();
			if("int".equals(dbtype)||"double".equals(dbtype)||"BigDecimal".equals(dbtype)||"Date".equals(dbtype)) {
				column.put("sorter",true);
			}
			columns.add(column);
		}
		Map<String,Object> info = new HashMap<>();
		info.put("columns", columns);
		info.put("dictOptions", dictOptions);
		info.put("formTemplate", head.getFormTemplate());
		info.put("description", head.getTableTxt());
		result.setResult(info);
		return result;
	}
	
	/**
	 * 查询列表分页数据
	 * @param code
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getData/{code}")
	@PermissionData
	public Result<Map<String,Object>> getData(@PathVariable("code") String code,HttpServletRequest request) {
		Result<Map<String,Object>> result = new Result<>();
		OnlCgformHead head = onlCgformHeadService.getById(code);
		if (head == null) {
			result.error500("实体不存在");
			return result;
		}
		try {
			String tbname = head.getTableName();
			Map<String,Object> params = CgformUtil.getParameterMap(request);
			Map<String,Object> page = onlCgformFieldService.queryAutolistPage(tbname, code, params);
			result.setResult(page);
		} catch (Exception e) {
			result.error500("数据库查询失败"+e.getMessage());
		}
		return result;
	}

	/**
	 * 查询表单结构数据
	 * @param code
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getFormItem/{code}")
	public Result<JSONObject> getFormItem(@PathVariable("code") String code,HttpServletRequest request) {
		Result<JSONObject> result = new Result<>();
		JSONObject schema = onlCgformHeadService.queryFormItem(code);
		System.out.println(schema.toJSONString());
		result.setResult(schema);
		return result;
	}
	
	/**
	 * 查询表单结构数据
	 * @param code
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getFormItemMany/{code}")
	public Result<JSONObject> getFormItemMany(@PathVariable("code") String code,HttpServletRequest request) {
		Result<JSONObject> result = new Result<>();
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.eq(OnlCgformField::getIsShowForm,1);
		List<OnlCgformField> fieldList = onlCgformFieldService.list(query);
		JSONObject schema = CgformUtil.getJsonSchemaByCgformFieldList(fieldList);
		result.setResult(schema);
		return result;
	}
	
	/**
	 * 查询表单数据
	 * @param code
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/form/{code}/{id}")
	public Result<?> getFormData(@PathVariable("code") String code,@PathVariable("id") String id) {
		try {
			Map<String, Object> map = this.onlCgformHeadService.queryManyFormData(code, id);
			return Result.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.getFormData()发生异常：" + e.getMessage());
			return Result.error("查询失败");
		}
	}
	
	/**
	 * 表单新增
	 * @param code
	 * @param jsonObject
	 * @return
	 */
	@PostMapping(value = "/form/{code}")
	public Result<?> formAdd(@PathVariable("code") String code,@RequestBody JSONObject jsonObject) {
		try {
			this.onlCgformHeadService.saveManyFormData(code,jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.formAdd()发生异常：" + e.getMessage());
			return Result.error("保存失败");
		}
		return Result.ok("保存成功!");
	}
	
	/**
	 * 表单修改
	 * @param code
	 * @param jsonObject
	 * @return
	 */
	@PutMapping(value="/form/{code}")
	public Result<?> formEdit(@PathVariable("code") String code,@RequestBody JSONObject jsonObject) {
		try {
			this.onlCgformHeadService.editManyFormData(code,jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.formEdit()发生异常：" + e.getMessage());
			return Result.error("修改失败");
		}
		return Result.ok("修改成功!");
	}
	
	/**
	 * 列表删除
	 * @param code
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/form/{code}/{id}")
	public Result<?> formEdit(@PathVariable("code") String code,@PathVariable("id") String id) {
		OnlCgformHead head = onlCgformHeadService.getById(code);
		if (head == null) {
			return Result.error("实体不存在");
		}
		String tbname = head.getTableName();
		try {
			onlCgformFieldService.deleteAutoList(tbname, id);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.formEdit()发生异常：" + e.getMessage());
			return Result.error("删除失败");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	 * 获取查询条件
	 */
	@GetMapping(value = "/getQueryInfo/{code}")
	public Result<?> getQueryInfo(@PathVariable("code") String code) {
	    try {
	    	List<Map<String,String>> list = onlCgformFieldService.getAutoListQueryInfo(code);
			return Result.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.getQueryInfo()发生异常：" + e.getMessage());
			return Result.error("查询失败");
		}
	}
	
	/**
	 * 同步数据库
	 */
	@PostMapping(value = "/doDbSynch/{code}/{synMethod}")
	public Result<?> doDbSynch(@PathVariable("code") String code,@PathVariable("synMethod") String synMethod) {
		try {
			onlCgformHeadService.doDbSynch(code, synMethod);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.doDbSynch()发生异常：" + e.getMessage());
			return Result.error("同步数据库失败");
		}
		return Result.ok("同步数据库成功!");
	}
	
	
	/**
	  * 导出
	 * @param code
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/exportXls/{code}")
	public void exportXls(@PathVariable("code") String code,HttpServletRequest request, HttpServletResponse response) {
		//1.查表信息
		OnlCgformHead head = onlCgformHeadService.getById(code);
		if (head == null) {
			return;
		}
		String sheetName = head.getTableTxt();
		//2.获取查询条件
		String paramsStr = request.getParameter("paramsStr");
		Map<String,Object> params = new HashMap<>();
		String deString = null;
		if (oConvertUtils.isNotEmpty(paramsStr)) {
			try {
				deString = URLDecoder.decode(paramsStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(deString!=null) {
				params = JSONObject.parseObject(deString,Map.class);  
			}
		}
		//Map<String,Object> params = CgformUtil.getParameterMap(request);
		//3.获取查询列
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.orderByAsc(OnlCgformField::getOrderNum);
		List<OnlCgformField> fieldList = this.onlCgformFieldService.list(query);
		//4.拼接sql查询数据
		String sql = CgformUtil.getQueryListDataCondition(head.getTableName(),fieldList, params);
		log.info("-----------动态列表查询sql》》"+sql);
		List<Map<String,Object>> data = this.onlCgformHeadService.queryListData(sql);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>(); 
		//5.根据selections 判断过滤数据
		String selections = params.get("selections")==null?null:params.get("selections").toString();
		if(oConvertUtils.isNotEmpty(selections)) {
			result = data.stream().filter(item -> selections.indexOf(item.get("id").toString())>=0).collect(Collectors.toList());
		}else {
			result.addAll(data);
		}
		//6.封装导出列对应title实体
		List<ExcelExportEntity> entityList = convertToExportEntity(fieldList,"id");
		//7.导出workbook
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, sheetName), entityList, result);	
		//8.response写出
		OutputStream outputStream=null;	
		try {
			response.setContentType("application/x-msdownload;charset=utf-8");
			
			String browse = BrowserUtils.checkBrowse(request);
			String codedFileName = head.getTableTxt()+"-v"+head.getTableVersion();
			if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) {
				response.setHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(codedFileName, "UTF-8") + ".xls");
			} else {
				String newtitle = new String(codedFileName.getBytes("UTF-8"), "ISO8859-1");
				response.setHeader("content-disposition", "attachment;filename=" + newtitle + ".xls");
			}
			
			OutputStream out=response.getOutputStream();
			workbook.write(out);
	        response.flushBuffer();
		} catch (Exception e) {
			log.info("--通过流的方式获取文件异常--"+e.getMessage());
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@PostMapping(value = "/importXls/{code}")
	public Result<?> importXls(@PathVariable("code") String code,HttpServletRequest request, HttpServletResponse response) {
		long start = System.currentTimeMillis();
		Result<?> result = new Result<>();
		String message = "";
		try {
			//1.查表信息
			OnlCgformHead head = onlCgformHeadService.getById(code);
			if (head == null) {
				return Result.error("数据库不存在该表记录");
			}
			//2.获取查询列
			LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
			query.eq(OnlCgformField::getCgformHeadId, code);
			List<OnlCgformField> fieldList = this.onlCgformFieldService.list(query);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			
			DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
			String databaseType = DbTableUtil.getDatabaseType(dataSource);
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile file = entity.getValue();// 获取上传文件对象
				ImportParams params = new ImportParams();
				params.setDataHanlder(new CgFormExcelHandler(fieldList));
				List<Map<String, Object>> listDate = ExcelImportUtil.importExcel(file.getInputStream(),  Map.class, params);
				if (listDate == null) {
					message = "识别模版数据错误";
					log.error(message);
				} else {
					Object mainId = "";
					for (Map<String, Object> map : listDate) {
						//标志是否为主表数据
						boolean isMainData = false;
						Set<String> keySet = map.keySet();
						Map<String,Object> mainData = new HashMap<String,Object>();
						for (String key : keySet) {
							if (key.indexOf("$subTable$")==-1) {
								if (key.indexOf("$mainTable$")!=-1 && oConvertUtils.isNotEmpty(map.get(key).toString())) {
									isMainData = true;
									//TMD 注意：不要在循环里获取connection或是dataSource相关的信息 因为那样直接就莫名其妙的不报错然后跑不了
									mainId = getPkValue(head,dataSource,databaseType);
								}
								mainData.put(key.replace("$mainTable$", ""), map.get(key));
							}
						}
						if (isMainData) {
							//处理字典项 TODO 这一块太浪费效率
						    dealDicForImport(mainData, fieldList);
							mainData.put("id", mainId);//主表数据
							String jsonStr = JSON.toJSONString(mainData);
							this.onlCgformFieldService.saveFormData(fieldList, head.getTableName(),JSONObject.parseObject(jsonStr));
							mainId =  mainData.get("id");
						}
						map.put("$mainTable$id", mainId);//为子表准备
					}
					//TODO 子表数据的导入
					message = "文件导入成功！";
				}
			}
			result.setSuccess(true);
			result.setMessage("导入成功!");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		log.info("=====online导入数据完成,耗时:"+(System.currentTimeMillis()-start)+"毫秒=====");
		return result;
	}
	
	/**
	 * 获取主键值
	 * @param cghead
	 * @param dataSource
	 * @param dbType
	 * @return
	 * @throws SQLException
	 * @throws DBException
	 */
	public Object getPkValue(OnlCgformHead  cghead,DataSource dataSource,String dbType) throws SQLException, DBException{
		Object pkValue = null;
		String pkType = cghead.getIdType();
		String pkSequence = cghead.getIdSequence();
		if(oConvertUtils.isNotEmpty(pkType)&&"UUID".equalsIgnoreCase(pkType)){
			pkValue = UUIDGenerator.generate();
		}else if(oConvertUtils.isNotEmpty(pkType)&&"NATIVE".equalsIgnoreCase(pkType)){
			if(oConvertUtils.isNotEmpty(dbType)&&"oracle".equalsIgnoreCase(dbType)){
				OracleSequenceMaxValueIncrementer incr = new OracleSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE");
				try{
					pkValue = incr.nextLongValue();
				}catch (Exception e) {
					log.info(e.getMessage());
				}
			}else if(oConvertUtils.isNotEmpty(dbType)&&"postgres".equalsIgnoreCase(dbType)){
				PostgreSQLSequenceMaxValueIncrementer incr = new PostgreSQLSequenceMaxValueIncrementer(dataSource, "HIBERNATE_SEQUENCE");
				try{
					pkValue = incr.nextLongValue();
				}catch (Exception e) {
					log.info(e.getMessage());
				}
			}else{
				pkValue = null;
			}
		}else if(oConvertUtils.isNotEmpty(pkType)&&"SEQUENCE".equalsIgnoreCase(pkType)){
			if(oConvertUtils.isNotEmpty(dbType)&&"oracle".equalsIgnoreCase(dbType)){
				OracleSequenceMaxValueIncrementer incr = new OracleSequenceMaxValueIncrementer(dataSource, pkSequence);
				try{
					pkValue = incr.nextLongValue();
				}catch (Exception e) {
					log.info(e.getMessage());
				}
			}else if(oConvertUtils.isNotEmpty(dbType)&&"postgres".equalsIgnoreCase(dbType)){
				PostgreSQLSequenceMaxValueIncrementer incr = new PostgreSQLSequenceMaxValueIncrementer(dataSource, pkSequence);
				try{
					pkValue = incr.nextLongValue();
				}catch (Exception e) {
					log.info(e.getMessage());
				}
			}else{
				pkValue = null;
			}
		}else{
			pkValue = UUIDGenerator.generate();
		}
		return pkValue;
	}
	
	/**
	 * 导入字典值转换
	 * @param result
	 * @param beans
	 */
	private void dealDicForImport(Map result,List<OnlCgformField> beans) {
		for (OnlCgformField bean : beans) {
			String dicTable = bean.getDictTable();//字典Table
			String dicCode = bean.getDictField();//字典Code
			String dicText = bean.getDictText();//字典text
			if (oConvertUtils.isEmpty(dicTable) && oConvertUtils.isEmpty(dicCode)) {
				//不需要处理字典
				continue;
			} else {
				if (!"popup".equals(bean.getFieldShowType())) {
					List<Map<String,Object>> dicDataList;
					String value = String.valueOf(result.get(bean.getDbFieldName()));
					if(oConvertUtils.isEmpty(dicTable)) {
						dicDataList = this.sysDictService.queryDictItemsByCode(dicCode);
					}else {
						dicDataList = this.sysDictService.queryTableDictItemsByCode(dicTable, dicText, dicCode);
					}
					for (Map<String,Object> item : dicDataList) {
						if(value.equals(item.get("text").toString())) {
							result.put(bean.getDbFieldName(), item.get("value"));
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取导出实体配置 支持字典转换
	 * @param lists
	 * @param pkField
	 * @return
	 */
	private List<ExcelExportEntity> convertToExportEntity(List<OnlCgformField> lists,String pkField) {
		List<ExcelExportEntity> entityList = new ArrayList<ExcelExportEntity>();
		for (int i = 0; i < lists.size(); i++) {
			if(null!=pkField && pkField.equals(lists.get(i).getDbFieldName())) {
				continue;
			}
			if (lists.get(i).getIsShowList()==1) {
				ExcelExportEntity entity = new ExcelExportEntity(lists.get(i).getDbFieldTxt(), lists.get(i).getDbFieldName());
				int columnWidth = lists.get(i).getDbLength() == 0 ? 12 : lists.get(i).getDbLength() > 30 ? 30 : lists.get(i).getDbLength();
				if (lists.get(i).getFieldShowType().equals("date")) {
					entity.setFormat("yyyy-MM-dd");
				} else if (lists.get(i).getFieldShowType().equals("datetime")) {
					entity.setFormat("yyyy-MM-dd HH:mm:ss");
				}
				entity.setWidth(columnWidth);
				//字典值设置
				String dictCode = lists.get(i).getDictField();
				if (oConvertUtils.isNotEmpty(dictCode)) {
					List<String> dictReplaces = new ArrayList<String>();
					List<Map<String, Object>> dictList = sysDictService.queryDictItemsByCode(dictCode);
					for (Map<String, Object> d : dictList) {
						dictReplaces.add(d.get("text") + "_" + d.get("value"));
					}
					//男_1, 女_2
					entity.setReplace(dictReplaces.toArray(new String[dictReplaces.size()]));
				}
				entityList.add(entity);
			}
		}
		return entityList;
	}
	
}
