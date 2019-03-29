package org.jeecg.modules.online.cgform.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
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
@RequestMapping("/online/cgform/field")
@Slf4j
public class OnlCgformFieldController {

	@Autowired
	private IOnlCgformFieldService onlCgformFieldService;

	/**
	 * 根据 headId 查询出 所有的 item
	 */
	@GetMapping(value = "/listByHeadId")
	public Result<?> listByHeadId(String headId) {
		QueryWrapper<OnlCgformField> queryWrapper = new QueryWrapper<OnlCgformField>();
		queryWrapper.eq("cgform_head_id", headId);
		queryWrapper.orderByAsc("order_num");
		List<OnlCgformField> list = onlCgformFieldService.list(queryWrapper);
		return Result.ok(list);
	}

	/**
	 * 分页列表查询
	 * 
	 * @param onlCgformField
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<OnlCgformField>> queryPageList(OnlCgformField onlCgformField, //
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, //
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, //
			HttpServletRequest req) {
		Result<IPage<OnlCgformField>> result = new Result<IPage<OnlCgformField>>();
		QueryWrapper<OnlCgformField> queryWrapper = QueryGenerator.initQueryWrapper(onlCgformField, req.getParameterMap());
		Page<OnlCgformField> page = new Page<OnlCgformField>(pageNo, pageSize);
		IPage<OnlCgformField> pageList = onlCgformFieldService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * 添加
	 * 
	 * @param onlCgformField
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<OnlCgformField> add(@RequestBody OnlCgformField onlCgformField) {
		Result<OnlCgformField> result = new Result<OnlCgformField>();
		try {
			onlCgformFieldService.save(onlCgformField);
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
	 * @param onlCgformField
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<OnlCgformField> edit(@RequestBody OnlCgformField onlCgformField) {
		Result<OnlCgformField> result = new Result<OnlCgformField>();
		OnlCgformField onlCgformFieldEntity = onlCgformFieldService.getById(onlCgformField.getId());
		if (onlCgformFieldEntity == null) {
			result.error500("未找到对应实体");
		} else {
			boolean ok = onlCgformFieldService.updateById(onlCgformField);
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
	public Result<OnlCgformField> delete(@RequestParam(name = "id", required = true) String id) {
		Result<OnlCgformField> result = new Result<OnlCgformField>();
		OnlCgformField onlCgformField = onlCgformFieldService.getById(id);
		if (onlCgformField == null) {
			result.error500("未找到对应实体");
		} else {
			boolean ok = onlCgformFieldService.removeById(id);
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
	public Result<OnlCgformField> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		Result<OnlCgformField> result = new Result<OnlCgformField>();
		if (ids == null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		} else {
			this.onlCgformFieldService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<OnlCgformField> queryById(@RequestParam(name = "id", required = true) String id) {
		Result<OnlCgformField> result = new Result<OnlCgformField>();
		OnlCgformField onlCgformField = onlCgformFieldService.getById(id);
		if (onlCgformField == null) {
			result.error500("未找到对应实体");
		} else {
			result.setResult(onlCgformField);
			result.setSuccess(true);
		}
		return result;
	}

}
