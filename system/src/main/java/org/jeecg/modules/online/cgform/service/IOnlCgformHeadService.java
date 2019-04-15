package org.jeecg.modules.online.cgform.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
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
	
	/**
	 * 同步数据库
	 */
	public void doDbSynch(String code,String synMethod) throws HibernateException, IOException, TemplateException, SQLException, DBException;
	
	/**
	 * 删除表和删除配置信息
	 * @param id
	 * @throws DBException
	 * @throws SQLException
	 */
	public void deleteRecordAndTable(String id) throws DBException,SQLException ;
	
	/**
	 * 根据SQL查询集合
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryListData(String sql);
	
	
	/**
	 * 根据主表ID和JS增强类型查询实体
	 * @param code OnlCgformHead的id
	 * @param type JS增强类型
	 * @return
	 */
	public OnlCgformEnhanceJs queryEnhance(String code,String type);
	/**
	 * 保存JS增强
	 * @param onlCgformEnhanceJs
	 */
	public void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs);
	/**
	 * 编辑JS增强
	 * @param onlCgformEnhanceJs
	 */
	public void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs);
	
	/**
	 * 根据主表ID和按钮编码查询实体
	 * @param formId OnlCgformHead的id
	 * @param buttonCode 按钮编码
	 * @return
	 */
	public OnlCgformEnhanceSql queryEnhanceSql(String formId,String buttonCode);
	/**
	 * 保存SQL增强
	 * @param onlCgformEnhanceSql
	 */
	public void saveEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql);
	/**
	 * 编辑SQL增强
	 * @param onlCgformEnhanceSql
	 */
	public void editEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql);
	
	/**
	 * 查询java增强信息
	 */
	public OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava);
	/**
	 * 校验java增强配置是否唯一
	 */
	public boolean checkOnlyEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava);
	/**
	 * 保存java增强
	 */
	public void saveEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava);
	/**
	 * 编辑java增强
	 */
	public void editEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava);
	
	/**
	 * 查询自定义按钮集合
	 * @param code OnlCgformHead的id
	 * @return
	 */
	public List<OnlCgformButton> queryButtonList(String code);
	
	/**
	 * 查询online所有的配置表名集合
	 * @return
	 */
	public List<String> queryOnlinetables();
	
	/**
	 * 将数据库表信息保存到online表中
	 * @param cgformHead
	 * @param cgformFieldList
	 */
	public void saveDbTable2Online(String tbname);
	
	/**
	 * 根据主表ID组装表单所需的JsonSchema
	 * @param code
	 * @return
	 */
	public JSONObject queryFormItem(String code); 
	
	
	/**
	 * 保存online表单
	 */
	public void saveManyFormData(String code,JSONObject json) throws DBException;
	
	/**
	  * 查询online 表单
	 * @param code
	 * @param id
	 * @throws DBException
	 */
	public Map<String, Object> queryManyFormData(String code,String id)throws DBException;
	
	/**
	  * 编辑表单数据
	 * @param code
	 * @param json
	 * @throws DBException
	 */
	public void editManyFormData(String code,JSONObject json) throws DBException;
	

}
