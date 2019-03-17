package org.jeecg.modules.online.cgform.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
public interface IOnlCgformHeadService extends IService<OnlCgformHead> {

	/**
	 * 添加所有
	 * 
	 * @param model
	 * @return
	 */
	Result<?> addAll(OnlCgformModel model);

	/**
	 * 修改所有，包括新增、修改、删除
	 * 
	 * @param model
	 * @return
	 */
	Result<?> editAll(OnlCgformModel model);

}
