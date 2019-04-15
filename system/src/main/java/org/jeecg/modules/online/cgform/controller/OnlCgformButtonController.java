package org.jeecg.modules.online.cgform.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

 /**
 * @Title: Controller
 * @Description: 自定义按钮
 * @author： jeecg-boot
 * @date：   2019-04-02
 * @version： V1.0
 */
@RestController
@RequestMapping("/online/cgform/button")
@Slf4j
public class OnlCgformButtonController {
	@Autowired
	private IOnlCgformButtonService onlCgformButtonService;
	
	/**
	  * 分页列表查询
	 * @param onlCgformButton
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list/{code}")
	public Result<IPage<OnlCgformButton>> queryPageList(OnlCgformButton onlCgformButton,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req,@PathVariable("code") String code) {
		Result<IPage<OnlCgformButton>> result = new Result<IPage<OnlCgformButton>>();
		onlCgformButton.setCgformHeadId(code);
		QueryWrapper<OnlCgformButton> queryWrapper = QueryGenerator.initQueryWrapper(onlCgformButton, req.getParameterMap());
		Page<OnlCgformButton> page = new Page<OnlCgformButton>(pageNo, pageSize);
		IPage<OnlCgformButton> pageList = onlCgformButtonService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param onlCgformButton
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<OnlCgformButton> add(@RequestBody OnlCgformButton onlCgformButton) {
		Result<OnlCgformButton> result = new Result<OnlCgformButton>();
		try {
			onlCgformButtonService.save(onlCgformButton);
			result.success("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param onlCgformButton
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<OnlCgformButton> edit(@RequestBody OnlCgformButton onlCgformButton) {
		Result<OnlCgformButton> result = new Result<OnlCgformButton>();
		OnlCgformButton onlCgformButtonEntity = onlCgformButtonService.getById(onlCgformButton.getId());
		if(onlCgformButtonEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = onlCgformButtonService.updateById(onlCgformButton);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<OnlCgformButton> delete(@RequestParam(name="id",required=true) String id) {
		Result<OnlCgformButton> result = new Result<OnlCgformButton>();
		OnlCgformButton onlCgformButton = onlCgformButtonService.getById(id);
		if(onlCgformButton==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = onlCgformButtonService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<OnlCgformButton> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<OnlCgformButton> result = new Result<OnlCgformButton>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.onlCgformButtonService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<OnlCgformButton> queryById(@RequestParam(name="id",required=true) String id) {
		Result<OnlCgformButton> result = new Result<OnlCgformButton>();
		OnlCgformButton onlCgformButton = onlCgformButtonService.getById(id);
		if(onlCgformButton==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(onlCgformButton);
			result.setSuccess(true);
		}
		return result;
	}

}
