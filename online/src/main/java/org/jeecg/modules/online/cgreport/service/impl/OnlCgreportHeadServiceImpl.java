package org.jeecg.modules.online.cgreport.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportHeadMapper;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 在线报表配置
 * @author: jeecg-boot
 * @date: 2019-03-08
 * @version: V1.0
 */
@Service
public class OnlCgreportHeadServiceImpl extends ServiceImpl<OnlCgreportHeadMapper, OnlCgreportHead> implements IOnlCgreportHeadService {

	@Autowired
	private IOnlCgreportParamService onlCgreportParamService;
	@Autowired
	private IOnlCgreportItemService onlCgreportItemService;

	@Autowired
	OnlCgreportHeadMapper mapper;

	/**
	 * 执行 select sql语句
	 * 
	 * @param sql
	 */
	@Override
	public List<Map<?, ?>> executeSelete(String sql) {
		return mapper.executeSelete(sql);
	}

	/** 修改全部项，包括新增、修改、删除 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> editAll(OnlCgreportModel values) {
		OnlCgreportHead head = values.getHead();

		OnlCgreportHead onlCgreportHeadEntity = super.getById(head.getId());
		if (onlCgreportHeadEntity == null) {
			return Result.error("未找到对应实体");
		}

		super.updateById(head);

		List<OnlCgreportParam> params = values.getParams();
		List<OnlCgreportItem> items = values.getItems();

		// 将param分类成 添加和修改 两个列表
		List<OnlCgreportParam> addParam = new ArrayList<OnlCgreportParam>();
		List<OnlCgreportParam> editParam = new ArrayList<OnlCgreportParam>();
		for (OnlCgreportParam param : params) {
			String id = String.valueOf(param.getId());

			if (id.length() == 32) {
				// 修改项
				editParam.add(param);

			} else {
				// 新增项
				param.setId(null);
				param.setCgrheadId(head.getId());
				addParam.add(param);
			}
		}
		onlCgreportParamService.saveBatch(addParam);

		// 使用 updateBatchById 会报错，原因未知，具体错误：
		// updateBatch Parameter 'param1' not found. Available parameters are
		// [et]

		for (OnlCgreportParam edit : editParam) {
			onlCgreportParamService.updateById(edit);
		}

		// 将item分类
		List<OnlCgreportItem> addItem = new ArrayList<OnlCgreportItem>();
		List<OnlCgreportItem> editItem = new ArrayList<OnlCgreportItem>();
		for (OnlCgreportItem item : items) {
			String id = String.valueOf(item.getId());

			if (id.length() == 32) {
				// 修改项
				editItem.add(item);

			} else {
				// 新增项
				item.setId(null);
				item.setCgrheadId(head.getId());
				addItem.add(item);
			}
		}
		onlCgreportItemService.saveBatch(addItem);

		for (OnlCgreportItem edit : editItem) {
			onlCgreportItemService.updateById(edit);
		}

		// 删除项
		onlCgreportParamService.removeByIds(values.getDeleteParamIdList());
		onlCgreportItemService.removeByIds(values.getDeleteItemIdList());

		return Result.ok("全部修改成功");

	}

}
