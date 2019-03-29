package org.jeecg.modules.online.cgform.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
public interface OnlCgformFieldMapper extends BaseMapper<OnlCgformField> {
	
	/**
	 * 根据传入sql查询集合
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryListBySql(@Param("sqlStr") String sql);
	
	/**
	 * 根据传入sql查询总数
	 * @param sql
	 * @return
	 */
	public Integer queryCountBySql(@Param("sqlStr") String sql);
	
	/**
	 * 删除
	 * @param sql
	 */
	public void deleteAutoList(@Param("sqlStr") String sql);
	
	/**
	 * 保存
	 */
	public void saveFormData(@Param("sqlStr") String sql);
	
	
	/**
	 * 编辑
	 */
	public void editFormData(@Param("sqlStr") String sql);
	
	
	/**
	 * 查询
	 * @param sql
	 * @return
	 */
	public Map<String, Object> queryFormData(@Param("sqlStr") String sql);

}
