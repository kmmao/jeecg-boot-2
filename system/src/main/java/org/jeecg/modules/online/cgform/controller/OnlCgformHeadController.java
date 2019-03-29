package org.jeecg.modules.online.cgform.controller;

import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.config.exception.DBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
