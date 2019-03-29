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
@TableName("onl_cgform_field")
public class OnlCgformField implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.UUID)
	private String id;
	/** 表ID */
	private String cgformHeadId;
	/** 字段名字 */
	private String dbFieldName;
	/** 字段备注 */
	private String dbFieldTxt;
	/** 原字段名 */
	private String dbFieldNameOld;
	/** 是否主键 0否 1是 */
	private Integer dbIsKey;
	/** 是否允许为空0否 1是 */
	private Integer dbIsNull;
	/** 数据库字段类型 */
	private String dbType;
	/** 数据库字段长度 */
	private Integer dbLength;
	/** 小数点 */
	private Integer dbPointLength;
	/** 表字段默认值 */
	private String dbDefaultVal;
	/** 字典code */
	private String dictField;
	/** 字典表 */
	private String dictTable;
	/** 字典Text */
	private String dictText;
	/** 表单控件类型 */
	private String fieldShowType;
	/** 跳转URL */
	private String fieldHref;
	/** 表单控件长度 */
	private Integer fieldLength;
	/** 表单字段校验规则 */
	private String fieldValidType;
	/** 字段是否必填 */
	private String fieldMustInput;
	/** 扩展参数JSON */
	private String fieldExtendJson;
	/** 填值规则code */
	private String fieldValueRuleCode;
	/** 是否查询条件0否 1是 */
	private Integer isQuery;
	/** 表单是否显示0否 1是 */
	private Integer isShowForm;
	/** 列表是否显示0否 1是 */
	private Integer isShowList;
	/** 查询模式 */
	private String queryMode;
	/** 外键主表名 */
	private String mainTable;
	/** 外键主键字段 */
	private String mainField;
	/** 排序 */
	private Integer orderNum;
	/** 修改人 */
	private String updateBy;
	/** 修改时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/** 创建时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/** 创建人 */
	private String createBy;
}
