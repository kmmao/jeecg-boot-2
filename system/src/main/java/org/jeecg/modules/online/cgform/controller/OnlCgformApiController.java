package org.jeecg.modules.online.cgform.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.util.CgformUtil;
import org.jeecg.modules.system.service.ISysDictService;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.extern.slf4j.Slf4j;

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
			}else if(view.equals("date")) {
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
		OnlCgformHead head = onlCgformHeadService.getById(code);
		if (head == null) {
			return Result.error("实体不存在");
		}
		String tbname = head.getTableName();
		try {
			Map<String, Object> map = onlCgformFieldService.queryFormData(code, tbname, id);
			return Result.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("OnlCgformApiController.formAdd()发生异常：" + e.getMessage());
			return Result.error("保存失败");
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
		OnlCgformHead head = onlCgformHeadService.getById(code);
		if (head == null) {
			return Result.error("实体不存在");
		}
		String tbname = head.getTableName();
		try {
			onlCgformFieldService.saveFormData(code, tbname, jsonObject);
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
		OnlCgformHead head = onlCgformHeadService.getById(code);
		if (head == null) {
			return Result.error("实体不存在");
		}
		String tbname = head.getTableName();
		try {
			onlCgformFieldService.editFormData(code, tbname, jsonObject);
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
	@GetMapping(value = "/exportXls/{code}")
	public void exportXls(@PathVariable("code") String code,HttpServletRequest request, HttpServletResponse response) {
		//1.查表信息
		OnlCgformHead head = onlCgformHeadService.getById(code);
		if (head == null) {
			return;
		}
		String sheetName = head.getTableTxt();
		//2.获取查询条件
		Map<String,Object> params = CgformUtil.getParameterMap(request);
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
		String selections = request.getParameter("selections");
		if(oConvertUtils.isNotEmpty(selections)) {
			result = data.stream().filter(item -> selections.indexOf(item.get("id").toString())>=0).collect(Collectors.toList());
		}else {
			result.addAll(data);
		}
		//6.封装导出列对应title实体
		List<ExcelExportEntity> entityList = CgformUtil.convertToExportEntity(fieldList,"id");
		//7.导出workbook
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, sheetName), entityList, result);	
		//8.response写出
		OutputStream outputStream=null;	
		try {
			response.setContentType("application/x-msdownload;charset=utf-8");
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
	
	
}
