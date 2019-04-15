package org.jeecg.modules.online.cgform.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.util.CgformUtil;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.util.DataBaseConst;
import org.jeecgframework.codegenerate.database.DbReadTableUtil;
import org.jeecgframework.codegenerate.generate.pojo.ColumnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: Controller
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
@RestController
@RequestMapping("/online/cgform/head")
@Slf4j
public class OnlCgformHeadController {

	@Autowired
	private IOnlCgformHeadService onlCgformHeadService;
	
	@Autowired
	private IOnlCgformFieldService onlCgformFieldService;

	/**
	 * 分页列表查询
	 * 
	 * @param onlCgformHead
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<OnlCgformHead>> queryPageList(OnlCgformHead onlCgformHead, //
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, //
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, //
			HttpServletRequest req) {
		Result<IPage<OnlCgformHead>> result = new Result<IPage<OnlCgformHead>>();
		QueryWrapper<OnlCgformHead> queryWrapper = QueryGenerator.initQueryWrapper(onlCgformHead, req.getParameterMap());
		Page<OnlCgformHead> page = new Page<OnlCgformHead>(pageNo, pageSize);
		IPage<OnlCgformHead> pageList = onlCgformHeadService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * 添加
	 * 
	 * @param onlCgformHead
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<OnlCgformHead> add(@RequestBody OnlCgformHead onlCgformHead) {
		Result<OnlCgformHead> result = new Result<OnlCgformHead>();
		try {
			onlCgformHeadService.save(onlCgformHead);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 * 编辑
	 * 
	 * @param onlCgformHead
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<OnlCgformHead> edit(@RequestBody OnlCgformHead onlCgformHead) {
		Result<OnlCgformHead> result = new Result<OnlCgformHead>();
		OnlCgformHead onlCgformHeadEntity = onlCgformHeadService.getById(onlCgformHead.getId());
		if (onlCgformHeadEntity == null) {
			result.error500("未找到对应实体");
		} else {
			boolean ok = onlCgformHeadService.updateById(onlCgformHead);
			// TODO 返回false说明什么？
			if (ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}

	/**
	 * 通过id删除
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
		try {
			onlCgformHeadService.deleteRecordAndTable(id);
		} catch (DBException e) {
			return Result.error("删除失败"+e.getMessage());
		} catch (SQLException e) {
			return Result.error("删除失败"+e.getMessage());
		}
		return Result.ok("删除成功!");
	}
	
	@DeleteMapping(value = "/removeRecord")
	public Result<OnlCgformHead> removeRecord(@RequestParam(name = "id", required = true) String id) {
		Result<OnlCgformHead> result = new Result<OnlCgformHead>();
		OnlCgformHead onlCgformHead = onlCgformHeadService.getById(id);
		if (onlCgformHead == null) {
			result.error500("未找到对应实体");
		} else {
			boolean ok = onlCgformHeadService.removeById(id);
			if (ok) {
				result.success("删除成功!");
			}
		}
		return result;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<OnlCgformHead> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		Result<OnlCgformHead> result = new Result<OnlCgformHead>();
		if (ids == null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		} else {
			this.onlCgformHeadService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<OnlCgformHead> queryById(@RequestParam(name = "id", required = true) String id) {
		Result<OnlCgformHead> result = new Result<OnlCgformHead>();
		OnlCgformHead onlCgformHead = onlCgformHeadService.getById(id);
		if (onlCgformHead == null) {
			result.error500("未找到对应实体");
		} else {
			result.setResult(onlCgformHead);
			result.setSuccess(true);
		}
		return result;
	}
	
	
	/*-----========================----------------JS/SQL/JAVA增强---------------------=========================-----*/
	/*-----------------------------------------------JS增强--------------------------------------------------------------*/
	@PostMapping(value = "/enhanceJs/{code}")
	public Result<?> saveEnhanceJs(@PathVariable("code") String code,@RequestBody OnlCgformEnhanceJs onlCgformEnhanceJs) {
		try {
			onlCgformEnhanceJs.setCgformHeadId(code);
			onlCgformHeadService.saveEnhance(onlCgformEnhanceJs);
			return Result.ok("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("保存失败!");
		}
	}
	@GetMapping(value = "/enhanceJs/{code}")
	public Result<?> getenhanceJs(@PathVariable("code") String code,HttpServletRequest req) {
		try {
			String type = req.getParameter("type");
			OnlCgformEnhanceJs obj = this.onlCgformHeadService.queryEnhance(code,type);
			if(obj==null) {
				return Result.error("查询为空");
			}else {
				return Result.ok(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("查询失败!");
		}
	}
	@PutMapping(value = "/enhanceJs/{code}")
	public Result<?> editEnhanceJs(@PathVariable("code") String code,@RequestBody OnlCgformEnhanceJs onlCgformEnhanceJs) {
		try {
			onlCgformEnhanceJs.setCgformHeadId(code);
			onlCgformHeadService.editEnhance(onlCgformEnhanceJs);
			return Result.ok("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("保存失败!");
		}
	}
	/*-----------------------------------------------JS增强--------------------------------------------------------------*/
	
	
	/**
 	  * 查询自定义button
	 * @param formId
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/enhanceButton/{formId}")
	public Result<?> enhanceButton(@PathVariable("formId") String formId,HttpServletRequest req) {
		try {
			List<OnlCgformButton> list = this.onlCgformHeadService.queryButtonList(formId);
			if(list==null || list.size()==0) {
				return Result.error("查询为空");
			}else {
				return Result.ok(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("查询失败!");
		}
	}
	
	
	/*-----------------------------------------------SQL增强--------------------------------------------------------------*/
	@PostMapping(value = "/enhanceSql/{formId}")
	public Result<?> enhanceSql(@PathVariable("formId") String formId,@RequestBody OnlCgformEnhanceSql onlCgformEnhanceSql) {
		try {
			onlCgformEnhanceSql.setCgformHeadId(formId);
			onlCgformHeadService.saveEnhance(onlCgformEnhanceSql);
			return Result.ok("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("保存失败!");
		}
	}
	@GetMapping(value = "/enhanceSql/{formId}")
	public Result<?> getEnhanceSql(@PathVariable("formId") String formId,HttpServletRequest req) {
		try {
			String buttonCode = req.getParameter("buttonCode");
			OnlCgformEnhanceSql obj = this.onlCgformHeadService.queryEnhanceSql(formId,buttonCode);
			if(obj==null) {
				return Result.error("查询为空");
			}else {
				return Result.ok(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("查询失败!");
		}
	}
	@PutMapping(value = "/enhanceSql/{formId}")
	public Result<?> editEnhanceSql(@PathVariable("formId") String formId,@RequestBody OnlCgformEnhanceSql onlCgformEnhanceSql) {
		try {
			onlCgformEnhanceSql.setCgformHeadId(formId);
			onlCgformHeadService.editEnhance(onlCgformEnhanceSql);
			return Result.ok("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("保存失败!");
		}
	}
	/*-----------------------------------------------SQL增强--------------------------------------------------------------*/

	/*-----------------------------------------------JAVA增强--------------------------------------------------------------*/
	@PostMapping(value = "/enhanceJava/{formId}")
	public Result<?> enhanceJava(@PathVariable("formId") String formId,@RequestBody OnlCgformEnhanceJava onlCgformEnhanceJava) {
		try {
			if(!CgformUtil.checkClassOrSpringBeanIsExist(onlCgformEnhanceJava)){
				return Result.error("类实例化失败，请检查!");
			}
			onlCgformEnhanceJava.setCgformHeadId(formId);
			if(onlCgformHeadService.checkOnlyEnhance(onlCgformEnhanceJava)) {
				onlCgformHeadService.saveEnhance(onlCgformEnhanceJava);
				return Result.ok("保存成功!");
			}else{
				return Result.error("保存失败,数据不唯一!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("保存失败!");
		}
	}
	@GetMapping(value = "/enhanceJava/{formId}")
	public Result<?> getEnhanceJava(@PathVariable("formId") String formId,OnlCgformEnhanceJava onlCgformEnhanceJava) {
		try {
			onlCgformEnhanceJava.setCgformHeadId(formId);
			OnlCgformEnhanceJava obj = this.onlCgformHeadService.queryEnhanceJava(onlCgformEnhanceJava);
			if(obj==null) {
				return Result.error("查询为空");
			}else {
				return Result.ok(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("查询失败!");
		}
	}
	@PutMapping(value = "/enhanceJava/{formId}")
	public Result<?> editEnhanceJava(@PathVariable("formId") String formId,@RequestBody OnlCgformEnhanceJava onlCgformEnhanceJava) {
		try {
			if(!CgformUtil.checkClassOrSpringBeanIsExist(onlCgformEnhanceJava)){
				return Result.error("类实例化失败，请检查!");
			}
			onlCgformEnhanceJava.setCgformHeadId(formId);
			if(onlCgformHeadService.checkOnlyEnhance(onlCgformEnhanceJava)) {
				onlCgformHeadService.editEnhance(onlCgformEnhanceJava);
				return Result.ok("保存成功!");
			}else{
				return Result.error("保存失败,数据不唯一!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return Result.error("保存失败!");
		}
	}
	/*-----------------------------------------------JAVA增强--------------------------------------------------------------*/
	
	
	/*----------------------------------------------导入表单-------------------------------------------------*/
	/**
	  * 获取表名集合 
	 * @param formId
	 * @param onlCgformEnhanceJava
	 * @return
	 */
	@GetMapping(value = "/queryTables")
	public Result<?> queryTables() {
		List<String> list = new ArrayList<String>();
		try {
			list = DbReadTableUtil.a();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return Result.error("同步失败，未获取数据库表信息");
		}
		CgformUtil.sortStringList(list);
		List<String> onlineTables = this.onlCgformHeadService.queryOnlinetables();
		list.removeAll(onlineTables);
		List<Map<String,String>> result = new ArrayList<>();
		for (String str : list) {
			Map<String,String> map = new HashMap<>();
			map.put("id", str);
			result.add(map);
		}
		return Result.ok(result);
	}
	
	private static String GENERATE_FORM_IDS;//这个东西也许有用吧 
	
	@PostMapping(value = "/transTables/{tbnames}")
	public Result<?> transTables(@PathVariable("tbnames") String tbnames) {
		if(oConvertUtils.isEmpty(tbnames)) {
			return Result.error("未识别的表名信息");
		}
		if(GENERATE_FORM_IDS!=null && GENERATE_FORM_IDS.equals(tbnames)){
			return Result.error("不允许重复生成!");
		}else{
			GENERATE_FORM_IDS = tbnames;
		}
		
		String[] arr = tbnames.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (oConvertUtils.isNotEmpty(arr[i])) {
				int count = this.onlCgformHeadService.count(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, arr[i]));
				if(count>0) {
					continue;
				}
				log.info("[IP"+"] [online数据库导入表] "+"  --表名："+arr[i]);
				this.onlCgformHeadService.saveDbTable2Online(arr[i]);
			}
		}
		GENERATE_FORM_IDS=null;
		return Result.ok("同步完成!");
	}
	
	
}
