package org.jeecg.modules.online.cgform.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
@Data
@TableName("onl_cgform_head")
public class OnlCgformHead implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.UUID)
	private String id;
	/** 表名 */
	private String tableName;
	/** 表类型: 0单表、1主表、2附表 */
	private Integer tableType;
	/** 表版本 */
	private Integer tableVersion;
	/** 表说明 */
	private String tableTxt;
	/** 是否带checkbox */
	private String isCheckbox;
	/** 同步数据库状态 */
	private String isDbSynch;
	/** 是否分页 */
	private String isPage;
	/** 是否是树 */
	private String isTree;
	/** 主键生成序列 */
	private String idSequence;
	/** 主键类型 */
	private String idType;
	/** 查询模式 */
	private String queryMode;
	/** 映射关系 1一对多 2一对一 */
	private Integer relationType;
	/** 子表 */
	private String subTableStr;
	/** 附表排序序号 */
	private Integer tabOrderNum;
	/** 树形表单父id */
	private String treeParentIdField;
	/** 树表主键字段 */
	private String treeIdField;
	/** 树开表单列字段 */
	private String treeFieldname;
	/** 表单分类 */
	private String formCategory;
	/** PC表单模板 */
	private String formTemplate;
	/** 表单模板样式(移动端) */
	private String formTemplateMobile;
	/** 修改人 */
	private String updateBy;
	/** 修改时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/** 创建人 */
	private String createBy;
	/** 创建时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
}
