package org.jeecg.modules.online.graphreport.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportItemService;
import org.jeecg.modules.online.graphreport.vo.OnlGraphreportHeadPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: Controller
 * @Description: 图表报告
 * @author:jeecg-boot
 * @date: 2019-04-10
 * @version:V1.0
 */
@RestController
@RequestMapping("/online/graphreport/head")
@Slf4j
public class OnlGraphreportHeadController {
	@Autowired
	private IOnlGraphreportHeadService onlGraphreportHeadService;
	@Autowired
	private IOnlGraphreportItemService onlGraphreportItemService;

	/**
	 * 分页列表查询
	 * 
	 * @param onlGraphreportHead
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<OnlGraphreportHead>> queryPageList(OnlGraphreportHead onlGraphreportHead, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
		Result<IPage<OnlGraphreportHead>> result = new Result<IPage<OnlGraphreportHead>>();
		QueryWrapper<OnlGraphreportHead> queryWrapper = QueryGenerator.initQueryWrapper(onlGraphreportHead, req.getParameterMap());
		Page<OnlGraphreportHead> page = new Page<OnlGraphreportHead>(pageNo, pageSize);
		IPage<OnlGraphreportHead> pageList = onlGraphreportHeadService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * 添加
	 * 
	 * @param onlGraphreportHeadPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<OnlGraphreportHead> add(@RequestBody OnlGraphreportHeadPage onlGraphreportHeadPage) {
		Result<OnlGraphreportHead> result = new Result<OnlGraphreportHead>();
		try {
			OnlGraphreportHead onlGraphreportHead = new OnlGraphreportHead();
			BeanUtils.copyProperties(onlGraphreportHeadPage, onlGraphreportHead);

			System.out.println("onlGraphreportHeadPage:" + onlGraphreportHeadPage);

			onlGraphreportHeadService.saveMain(onlGraphreportHead, onlGraphreportHeadPage.getOnlGraphreportItemList());
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
	 * @param onlGraphreportHeadPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<OnlGraphreportHead> edit(@RequestBody OnlGraphreportHeadPage onlGraphreportHeadPage) {
		Result<OnlGraphreportHead> result = new Result<OnlGraphreportHead>();
		OnlGraphreportHead onlGraphreportHead = new OnlGraphreportHead();
		BeanUtils.copyProperties(onlGraphreportHeadPage, onlGraphreportHead);
		OnlGraphreportHead onlGraphreportHeadEntity = onlGraphreportHeadService.getById(onlGraphreportHead.getId());
		if (onlGraphreportHeadEntity == null) {
			result.error500("未找到对应实体");
		} else {
			onlGraphreportHeadService.updateById(onlGraphreportHead);
			onlGraphreportHeadService.updateMain(onlGraphreportHead, onlGraphreportHeadPage.getOnlGraphreportItemList());
			result.success("修改成功!");
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
	public Result<OnlGraphreportHead> delete(@RequestParam(name = "id", required = true) String id) {
		Result<OnlGraphreportHead> result = new Result<OnlGraphreportHead>();
		OnlGraphreportHead onlGraphreportHead = onlGraphreportHeadService.getById(id);
		if (onlGraphreportHead == null) {
			result.error500("未找到对应实体");
		} else {
			onlGraphreportHeadService.delMain(id);
			result.success("删除成功!");
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
	public Result<OnlGraphreportHead> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		Result<OnlGraphreportHead> result = new Result<OnlGraphreportHead>();
		if (ids == null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		} else {
			this.onlGraphreportHeadService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public Result<OnlGraphreportHead> queryById(@RequestParam(name = "id", required = true) String id) {
		Result<OnlGraphreportHead> result = new Result<OnlGraphreportHead>();
		OnlGraphreportHead onlGraphreportHead = onlGraphreportHeadService.getById(id);
		if (onlGraphreportHead == null) {
			result.error500("未找到对应实体");
		} else {
			result.setResult(onlGraphreportHead);
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 通过id查询
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryOnlGraphreportItemByMainId")
	public Result<List<OnlGraphreportItem>> queryOnlGraphreportItemListByMainId(@RequestParam(name = "id", required = true) String id) {
		Result<List<OnlGraphreportItem>> result = new Result<List<OnlGraphreportItem>>();
		List<OnlGraphreportItem> onlGraphreportItemList = onlGraphreportItemService.selectByMainId(id);
		result.setResult(onlGraphreportItemList);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
		// Step.1 组装查询条件
		QueryWrapper<OnlGraphreportHead> queryWrapper = null;
		try {
			String paramsStr = request.getParameter("paramsStr");
			if (oConvertUtils.isNotEmpty(paramsStr)) {
				String deString = URLDecoder.decode(paramsStr, "UTF-8");
				OnlGraphreportHead onlGraphreportHead = JSON.parseObject(deString, OnlGraphreportHead.class);
				queryWrapper = QueryGenerator.initQueryWrapper(onlGraphreportHead, request.getParameterMap());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// Step.2 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		List<OnlGraphreportHeadPage> pageList = new ArrayList<OnlGraphreportHeadPage>();
		List<OnlGraphreportHead> onlGraphreportHeadList = onlGraphreportHeadService.list(queryWrapper);
		for (OnlGraphreportHead onlGraphreportHead : onlGraphreportHeadList) {
			OnlGraphreportHeadPage vo = new OnlGraphreportHeadPage();
			BeanUtils.copyProperties(onlGraphreportHead, vo);
			List<OnlGraphreportItem> onlGraphreportItemList = onlGraphreportItemService.selectByMainId(onlGraphreportHead.getId());
			vo.setOnlGraphreportItemList(onlGraphreportItemList);
			pageList.add(vo);
		}
		// 导出文件名称
		mv.addObject(NormalExcelConstants.FILE_NAME, "图表报告列表");
		mv.addObject(NormalExcelConstants.CLASS, OnlGraphreportHeadPage.class);
		mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("图表报告列表数据", "导出人:Jeecg", "导出信息"));
		mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
		return mv;
	}

	/**
	 * 通过excel导入数据
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<OnlGraphreportHeadPage> list = ExcelImportUtil.importExcel(file.getInputStream(), OnlGraphreportHeadPage.class, params);
				for (OnlGraphreportHeadPage page : list) {
					OnlGraphreportHead po = new OnlGraphreportHead();
					BeanUtils.copyProperties(page, po);
					onlGraphreportHeadService.saveMain(po, page.getOnlGraphreportItemList());
				}
				return Result.ok("文件导入成功！数据行数：" + list.size());
			} catch (Exception e) {
				log.error(e.getMessage());
				return Result.error("文件导入失败！");
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return Result.ok("文件导入失败！");
	}

}
