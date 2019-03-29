package org.jeecg.modules.online.cgform.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
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
@Slf4j
@RestController
@RequestMapping("/online/cgform/index")
public class OnlCgformIndexController {

	@Autowired
	private IOnlCgformIndexService onlCgformIndexService;

	/**
	 * 根据 headId 查询出 所有的 item
	 */
	@GetMapping(value = "/listByHeadId")
	public Result<?> listByHeadId(String headId) {
		QueryWrapper<OnlCgformIndex> queryWrapper = new QueryWrapper<OnlCgformIndex>();
		queryWrapper.eq("cgform_head_id", headId);
		queryWrapper.orderByDesc("create_time");
		List<OnlCgformIndex> list = onlCgformIndexService.list(queryWrapper);
		return Result.ok(list);
	}

	/**
	 * 分页列表查询
	 * 
	 * @param onlCgformIndex
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<OnlCgformIndex>> queryPageList(OnlCgformIndex onlCgformIndex, //
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, //
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, //
			HttpServletRequest req) {
		Result<IPage<OnlCgformIndex>> result = new Result<IPage<OnlCgformIndex>>();
		QueryWrapper<OnlCgformIndex> queryWrapper = QueryGenerator.initQueryWrapper(onlCgformIndex, req.getParameterMap());
		Page<OnlCgformIndex> page = new Page<OnlCgformIndex>(pageNo, pageSize);
		IPage<OnlCgformIndex> pageList = onlCgformIndexService.page(page, queryWrapper);

		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * 添加
	 * 
	 * @param onlCgformIndex
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<OnlCgformIndex> add(@RequestBody OnlCgformIndex onlCgformIndex) {
		Result<OnlCgformIndex> result = new Result<OnlCgformIndex>();
		try {
			onlCgformIndexService.save(onlCgformIndex);
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
	 * @param onlCgformIndex
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<OnlCgformIndex> edit(@RequestBody OnlCgformIndex onlCgformIndex) {
		Result<OnlCgformIndex> result = new Result<OnlCgformIndex>();
		OnlCgformIndex onlCgformIndexEntity = onlCgformIndexService.getById(onlCgformIndex.getId());
		if (onlCgformIndexEntity == null) {
			result.error500("未找到对应实体");
		} else {
			boolean ok = onlCgformIndexService.updateById(onlCgformIndex);
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
	public Result<OnlCgformIndex> delete(@RequestParam(name = "id", required = true) String id) {
		Result<OnlCgformIndex> result = new Result<OnlCgformIndex>();
		OnlCgformIndex onlCgformIndex = onlCgformIndexService.getById(id);
		if (onlCgformIndex == null) {
			result.error500("未找到对应实体");
		} else {
			boolean ok = onlCgformIndexService.removeById(id);
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
	public Result<OnlCgformIndex> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		Result<OnlCgformIndex> result = new Result<OnlCgformIndex>();
		if (ids == null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		} else {
			this.onlCgformIndexService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<OnlCgformIndex> queryById(@RequestParam(name = "id", required = true) String id) {
		Result<OnlCgformIndex> result = new Result<OnlCgformIndex>();
		OnlCgformIndex onlCgformIndex = onlCgformIndexService.getById(id);
		if (onlCgformIndex == null) {
			result.error500("未找到对应实体");
		} else {
			result.setResult(onlCgformIndex);
			result.setSuccess(true);
		}
		return result;
	}

}
