package org.jeecg.modules.online.cgform.service;

import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

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
	public Map<String,Object> queryAutolistPage(String tbname,String headId,Map<String,Object> params);
	
	/**
	 * 删除
	 */
	public void deleteAutoList(String tbname,String id);
	
	/**
	 * 删除表数据(主从表删除从表数据用到)
	 * @param tbname 表名
	 * @param linkField 关联字段名
	 * @param linkValue 关联字段值
	 */
	public void deleteAutoList(String tbname,String linkField,String linkValue);
	
	/**
	 * 保存
	 */
	public void saveFormData(String code,String tbname,JSONObject json);
	
	/**
	 * 保存
	 */
	public void saveFormData(List<OnlCgformField> fieldList,String tbname,JSONObject json);
	
	/**
	  * 查询表单/列表的显示字段
	 * @param code
	 * @param isform true表示是查询表单字段isShowForm为1
	 * @return
	 */
	public List<OnlCgformField> queryFormFields(String code,boolean isform);
	
	
	/**
	 * 编辑
	 */
	public void editFormData(String code,String tbname,JSONObject json);
	
	
	/**
	 * 查询表单数据
	 * @param code
	 * @param tbname
	 * @param id
	 * @return
	 */
	public Map<String,Object> queryFormData(String code,String tbname,String id);
	
	/**
	 * 查询表数据 
	 * @param fieldList 字段（需要查询 的字段）
	 * @param tbname 表名
	 * @param id 数据ID
	 * @return
	 */
	public Map<String,Object> queryFormData(List<OnlCgformField> fieldList,String tbname,String id);
	
	/**
	 * 查询表数据 （主从表查询从表数据）
	 * @param fieldList 字段（需要查询 的字段）
	 * @param tbname 表名
	 * @param linkField 关联字段名
	 * @param value 关联字段值
	 * @return
	 */
	public List<Map<String,Object>> querySubFormData(List<OnlCgformField> fieldList,String tbname,String linkField,String value);
	
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
