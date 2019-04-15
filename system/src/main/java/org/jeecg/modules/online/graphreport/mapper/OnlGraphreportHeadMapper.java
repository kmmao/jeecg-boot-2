package org.jeecg.modules.online.graphreport.mapper;

import java.util.List;
import java.util.Map;

import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 图表报告
 * @author:jeecg-boot
 * @date: 2019-04-10
 * @version:V1.0
 */
public interface OnlGraphreportHeadMapper extends BaseMapper<OnlGraphreportHead> {
	/**
	 * 执行查询SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	List<Map<?, ?>> executeSelete(String sql);
}
