package org.jeecg.modules.online.cgform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
public interface OnlCgformHeadMapper extends BaseMapper<OnlCgformHead> {
	
	public void executeDDL(@Param("sqlStr") String sql);
	
	public List<Map<String,Object>> queryList(@Param("sqlStr") String sql);
	
}
