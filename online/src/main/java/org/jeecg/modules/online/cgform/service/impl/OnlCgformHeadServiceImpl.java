package org.jeecg.modules.online.cgform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
@Service
public class OnlCgformHeadServiceImpl extends ServiceImpl<OnlCgformHeadMapper, OnlCgformHead> implements IOnlCgformHeadService {

	@Autowired
	private IOnlCgformFieldService fieldService;
	@Autowired
	private IOnlCgformIndexService indexService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> addAll(OnlCgformModel model) {

		String uuid = UUID.randomUUID().toString().replace("-", "");

		OnlCgformHead head = model.getHead();
		List<OnlCgformField> fields = model.getFields();
		List<OnlCgformIndex> indexs = model.getIndexs();

		head.setId(uuid);

		for (int i = 0; i < fields.size(); i++) {
			OnlCgformField field = fields.get(i);
			field.setId(null);
			field.setCgformHeadId(uuid);
			field.setOrderNum(i);
		}

		for (OnlCgformIndex index : indexs) {
			index.setId(null);
			index.setCgformHeadId(uuid);
		}

		super.save(head);
		fieldService.saveBatch(fields);
		indexService.saveBatch(indexs);

		return Result.ok("添加成功");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result<?> editAll(OnlCgformModel model) {
		OnlCgformHead head = model.getHead();

		OnlCgformHead headEntity = super.getById(head.getId());
		if (headEntity == null) {
			return Result.error("未找到对应实体");
		}
		// table version 自增1
		Integer version = headEntity.getTableVersion();
		if (version == null) {
			version = 1;
		}
		head.setTableVersion(++version);

		super.updateById(head);

		List<OnlCgformField> fields = model.getFields();
		List<OnlCgformIndex> indexs = model.getIndexs();

		// 将fields分类成 添加和修改 两个列表
		List<OnlCgformField> addFields = new ArrayList<OnlCgformField>();
		List<OnlCgformField> editFields = new ArrayList<OnlCgformField>();
		for (OnlCgformField field : fields) {
			String id = String.valueOf(field.getId());

			if (id.length() == 32) {
				// 修改项
				editFields.add(field);

			} else {
				// id 不做修改
				String primaryKey = "_pk";
				if (!primaryKey.equals(id)) {
					// 新增项
					field.setId(null);
					field.setCgformHeadId(head.getId());
					addFields.add(field);
				}
			}
		}
		fieldService.saveBatch(addFields);
		// 批量修改
		for (OnlCgformField field : editFields) {
			fieldService.updateById(field);
		}

		// 将item分类
		List<OnlCgformIndex> addIndex = new ArrayList<OnlCgformIndex>();
		List<OnlCgformIndex> editIndex = new ArrayList<OnlCgformIndex>();
		for (OnlCgformIndex index : indexs) {
			String id = String.valueOf(index.getId());

			if (id.length() == 32) {
				// 修改项
				editIndex.add(index);

			} else {
				// 新增项
				index.setId(null);
				index.setCgformHeadId(head.getId());
				addIndex.add(index);
			}
		}
		indexService.saveBatch(addIndex);
		for (OnlCgformIndex index : editIndex) {
			indexService.updateById(index);
		}

		// 删除项
		if (model.getDeleteFieldIds().size() > 0) {
			fieldService.removeByIds(model.getDeleteFieldIds());
		}
		if (model.getDeleteIndexIds().size() > 0) {
			indexService.removeByIds(model.getDeleteIndexIds());
		}

		return Result.ok("全部修改成功");
	}

}
