package org.jeecg.modules.online.graphreport.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 图表报告
 * @author:jeecg-boot
 * @date: 2019-04-10
 * @version:V1.0
 */
public interface IOnlGraphreportHeadService extends IService<OnlGraphreportHead> {

	/**
	 * 执行查询SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	List<Map<?, ?>> executeSelete(String sql);
	
	/**
	 * 添加一对多、
	 * 
	 * @param onlGraphreportHead
	 * @param onlGraphreportItemList
	 */
	public void saveMain(OnlGraphreportHead onlGraphreportHead, List<OnlGraphreportItem> onlGraphreportItemList);

	/**
	 * 修改一对多
	 * 
	 * @param onlGraphreportHead
	 * @param onlGraphreportItemList
	 */
	public void updateMain(OnlGraphreportHead onlGraphreportHead, List<OnlGraphreportItem> onlGraphreportItemList);

	/**
	 * 删除一对多
	 * 
	 * @param id
	 */
	public void delMain(String id);

	/**
	 * 批量删除一对多
	 * 
	 * @param idList
	 */
	public void delBatchMain(Collection<? extends Serializable> idList);

}
