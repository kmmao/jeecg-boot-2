package org.jeecg.modules.online.cgreport.service;

import java.util.List;
import java.util.Map;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 在线报表配置
 * @author: jeecg-boot
 * @date: 2019-03-08
 * @version: V1.0
 */
public interface IOnlCgreportHeadService extends IService<OnlCgreportHead> {

	/**
	 * 修改全部项，包括新增、修改、删除
	 * 
	 * @param values
	 * @return
	 */
	Result<?> editAll(OnlCgreportModel values);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	List<Map<?, ?>> executeSelete(String sql);

}
