package org.jeecg.modules.online.cgform.controller;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
