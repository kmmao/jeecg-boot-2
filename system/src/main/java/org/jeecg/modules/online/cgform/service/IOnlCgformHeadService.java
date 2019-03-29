package org.jeecg.modules.online.cgform.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.config.exception.DBException;

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
	public void doDbSynch(String code, String synMethod) throws HibernateException, IOException, TemplateException, SQLException, DBException;
	
	
	public void deleteRecordAndTable(String id) throws DBException,SQLException ;
	
	public List<Map<String,Object>> queryListData(String sql);

}
