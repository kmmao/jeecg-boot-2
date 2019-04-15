package org.jeecg.modules.online.graphreport.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jeecg.modules.online.graphreport.entity.OnlGraphreportHead;
import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportHeadMapper;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportItemMapper;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 图表报告
 * @author:jeecg-boot
 * @date: 2019-04-10
 * @version:V1.0
 */
@Service
public class OnlGraphreportHeadServiceImpl extends ServiceImpl<OnlGraphreportHeadMapper, OnlGraphreportHead> implements IOnlGraphreportHeadService {

	@Autowired
	private OnlGraphreportHeadMapper onlGraphreportHeadMapper;
	@Autowired
	private OnlGraphreportItemMapper onlGraphreportItemMapper;

	/**
	 * 执行查询SQL语句
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<?, ?>> executeSelete(String sql) {
		return onlGraphreportHeadMapper.executeSelete(sql);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(OnlGraphreportHead onlGraphreportHead, List<OnlGraphreportItem> onlGraphreportItemList) {
		onlGraphreportHeadMapper.insert(onlGraphreportHead);
		for (OnlGraphreportItem entity : onlGraphreportItemList) {
			// 外键设置
			entity.setGraphreportHeadId(onlGraphreportHead.getId());
			onlGraphreportItemMapper.insert(entity);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(OnlGraphreportHead onlGraphreportHead, List<OnlGraphreportItem> onlGraphreportItemList) {
		onlGraphreportHeadMapper.updateById(onlGraphreportHead);

		// 1.先删除子表数据
		onlGraphreportItemMapper.deleteByMainId(onlGraphreportHead.getId());

		// 2.子表数据重新插入
		for (OnlGraphreportItem entity : onlGraphreportItemList) {
			// 外键设置
			entity.setGraphreportHeadId(onlGraphreportHead.getId());
			onlGraphreportItemMapper.insert(entity);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		onlGraphreportHeadMapper.deleteById(id);
		onlGraphreportItemMapper.deleteByMainId(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for (Serializable id : idList) {
			onlGraphreportHeadMapper.deleteById(id);
			onlGraphreportItemMapper.deleteByMainId(id.toString());
		}
	}

}
