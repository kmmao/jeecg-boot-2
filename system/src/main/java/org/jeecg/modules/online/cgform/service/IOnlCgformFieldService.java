package org.jeecg.modules.online.cgform.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.config.exception.DBException;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

import freemarker.template.TemplateException;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
public interface IOnlCgformFieldService extends IService<OnlCgformField> {
	
	/**
	 * 分页查询
	 * @param tbname
	 * @param headId
	 * @param params
	 * @return total:总数 ,records:分页List数据
	 */
	public Map<String,Object> queryAutolistPage(String tbname, String headId, Map<String, Object> params);
	
	/**
	 * 删除
	 */
	public void deleteAutoList(String tbname, String id);
	
	/**
	 * 保存
	 */
	public void saveFormData(String code, String tbname, JSONObject json);
	
	
	/**
	 * 编辑
	 */
	public void editFormData(String code, String tbname, JSONObject json);
	
	
	
	/**
	 * 查询表单数据
	 * @param code
	 * @param tbname
	 * @param id
	 * @return
	 */
	public Map<String,Object> queryFormData(String code, String tbname, String id);
	
	/**
	 * 获取列表的查询条件
	 * @param code
	 * @return 
	 *<br> field 数据库字段名称
	 *<br> label 数据库字段备注
	 *<br> view 展示方式 list-text-date  checkbox->多选下拉框..
	 *<br> mode 查询模式 single/group
	 * 
	 */
	public List<Map<String,String>> getAutoListQueryInfo(String code);
	
}
