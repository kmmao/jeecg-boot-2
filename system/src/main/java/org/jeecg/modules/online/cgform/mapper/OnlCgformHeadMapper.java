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
	
	/**
	 * 执行DDL语句
	 * @param sql
	 */
	public void executeDDL(@Param("sqlStr") String sql);
	
	/**
	 * 执行SQL语句查询集合
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryList(@Param("sqlStr") String sql);
	
	/**
	 * 查询Online所有的配置表名集合
	 * @return
	 */
	public List<String> queryOnlinetables();

	
}
