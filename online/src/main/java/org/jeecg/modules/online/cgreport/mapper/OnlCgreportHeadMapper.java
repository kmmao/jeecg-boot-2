package org.jeecg.modules.online.cgreport.mapper;

import java.util.List;
import java.util.Map;

import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 在线报表配置
 * @author: jeecg-boot
 * @date: 2019-03-08
 * @version: V1.0
 */
public interface OnlCgreportHeadMapper extends BaseMapper<OnlCgreportHead> {

	/**
	 * 执行查询SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	List<Map<?, ?>> executeSelete(String sql);

}
