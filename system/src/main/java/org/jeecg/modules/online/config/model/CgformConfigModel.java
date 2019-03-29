package org.jeecg.modules.online.config.model;

import java.util.List;

import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

public class CgformConfigModel {
	
	/**表格名称*/
	private String tableName;
	
	/**是否同步了数据库*/
	private String isDbSynch;
	/**datagrid是否显示复选框*/
	
	/**功能注释*/
	private String content;
	
	/**表单版本*/
	private String jformVersion;
	
	/**表单类型*/
	private Integer jformType;
	
	/**表单主键策略*/
	private String jformPkType;
	
	/**表单主键策略-序列(针对oracle等数据库)*/
	
	private String jformPkSequence;
	
	/**附表关联类型*/
	private Integer relationType;
	
	/**附表清单*/
	private String subTableStr;
	
	/**一对多Tab顺序*/
	private Integer tabOrder;
	/**
	 * 表格列属性
	 */
	private List<OnlCgformField> columns;
	/**
	 * 索引
	 */
	private List<OnlCgformIndex> indexes;
	
	/**树形列表 父id列名*/
	private String treeParentIdFieldName;
	/**树形列表 id列名*/
	private String treeIdFieldname;
	/**树形列表 菜单列名*/
	private String treeFieldname;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getIsDbSynch() {
		return isDbSynch;
	}
	public void setIsDbSynch(String isDbSynch) {
		this.isDbSynch = isDbSynch;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getJformVersion() {
		return jformVersion;
	}
	public void setJformVersion(String jformVersion) {
		this.jformVersion = jformVersion;
	}
	public Integer getJformType() {
		return jformType;
	}
	public void setJformType(Integer jformType) {
		this.jformType = jformType;
	}
	public String getJformPkType() {
		return jformPkType;
	}
	public void setJformPkType(String jformPkType) {
		this.jformPkType = jformPkType;
	}
	public String getJformPkSequence() {
		return jformPkSequence;
	}
	public void setJformPkSequence(String jformPkSequence) {
		this.jformPkSequence = jformPkSequence;
	}
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	public String getSubTableStr() {
		return subTableStr;
	}
	public void setSubTableStr(String subTableStr) {
		this.subTableStr = subTableStr;
	}
	public Integer getTabOrder() {
		return tabOrder;
	}
	public void setTabOrder(Integer tabOrder) {
		this.tabOrder = tabOrder;
	}
	public List<OnlCgformField> getColumns() {
		return columns;
	}
	public void setColumns(List<OnlCgformField> columns) {
		this.columns = columns;
	}
	public List<OnlCgformIndex> getIndexes() {
		return indexes;
	}
	public void setIndexes(List<OnlCgformIndex> indexes) {
		this.indexes = indexes;
	}
	public String getTreeParentIdFieldName() {
		return treeParentIdFieldName;
	}
	public void setTreeParentIdFieldName(String treeParentIdFieldName) {
		this.treeParentIdFieldName = treeParentIdFieldName;
	}
	public String getTreeIdFieldname() {
		return treeIdFieldname;
	}
	public void setTreeIdFieldname(String treeIdFieldname) {
		this.treeIdFieldname = treeIdFieldname;
	}
	public String getTreeFieldname() {
		return treeFieldname;
	}
	public void setTreeFieldname(String treeFieldname) {
		this.treeFieldname = treeFieldname;
	}
	
}
