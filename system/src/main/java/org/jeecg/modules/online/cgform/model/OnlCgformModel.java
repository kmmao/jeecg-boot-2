package org.jeecg.modules.online.cgform.model;

import java.util.List;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

/**
 * @Description: Online表单开发 model
 * @author: sunjianlei
 * @date: 2019-03-14
 * @version: V1.0
 */
public class OnlCgformModel {

	private OnlCgformHead head;

	private List<OnlCgformField> fields;
	private List<String> deleteFieldIds;

	private List<OnlCgformIndex> indexs;
	private List<String> deleteIndexIds;

	public OnlCgformHead getHead() {
		return head;
	}

	public void setHead(OnlCgformHead head) {
		this.head = head;
	}

	public List<OnlCgformField> getFields() {
		return fields;
	}

	public void setFields(List<OnlCgformField> fields) {
		this.fields = fields;
	}

	public List<OnlCgformIndex> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<OnlCgformIndex> indexs) {
		this.indexs = indexs;
	}

	public List<String> getDeleteFieldIds() {
		return deleteFieldIds;
	}

	public void setDeleteFieldIds(List<String> deleteFieldIds) {
		this.deleteFieldIds = deleteFieldIds;
	}

	public List<String> getDeleteIndexIds() {
		return deleteIndexIds;
	}

	public void setDeleteIndexIds(List<String> deleteIndexIds) {
		this.deleteIndexIds = deleteIndexIds;
	}

	@Override
	public String toString() {
		return "OnlCgformModel [head=" + head + ", fields=" + fields + ", deleteFieldIds=" + deleteFieldIds + ", indexs=" + indexs + ", deleteIndexIds=" + deleteIndexIds + "]";
	}

}
