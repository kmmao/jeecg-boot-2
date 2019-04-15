package org.jeecg.modules.online.graphreport.service.impl;

import java.util.List;

import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import org.jeecg.modules.online.graphreport.mapper.OnlGraphreportItemMapper;
import org.jeecg.modules.online.graphreport.service.IOnlGraphreportItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 图表报告项
 * @author:jeecg-boot
 * @date: 2019-04-10
 * @version:V1.0
 */
@Service
public class OnlGraphreportItemServiceImpl extends ServiceImpl<OnlGraphreportItemMapper, OnlGraphreportItem> implements IOnlGraphreportItemService {

	@Autowired
	private OnlGraphreportItemMapper onlGraphreportItemMapper;

	@Override
	public List<OnlGraphreportItem> selectByMainId(String mainId) {
		return onlGraphreportItemMapper.selectByMainId(mainId);
	}
}
