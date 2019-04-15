package org.jeecg.modules.online.graphreport.entity;

import java.io.Serializable;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Description: 图表报告项
 * @author:jeecg-boot
 * @date: 2019-04-11
 * @version:V1.0
 */
@Data
@TableName("onl_graphreport_item")
public class OnlGraphreportItem implements Serializable {
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/** 主表ID */
	private java.lang.String graphreportHeadId;
	/** 字段名 */
	@Excel(name = "字段名", width = 15)
	private java.lang.String fieldName;
	/** 字段文本 */
	@Excel(name = "字段文本", width = 15)
	private java.lang.String fieldTxt;
	/** 是否列表显示 */
	@Excel(name = "是否列表显示", width = 15)
	private java.lang.String isShow;
	/** 是否查询 */
	@Excel(name = "是否查询", width = 15)
	private java.lang.String searchFlag;
	/** 查询模式 */
	@Excel(name = "查询模式", width = 15)
	private java.lang.String searchMode;
	/** 字典Code */
	@Excel(name = "字典Code", width = 15)
	private java.lang.String dictCode;
	/** 字段href */
	@Excel(name = "字段href", width = 15)
	private java.lang.String fieldHref;
	/** 字段类型 */
	@Excel(name = "字段类型", width = 15)
	private java.lang.String fieldType;
	/** 排序 */
	@Excel(name = "排序", width = 15)
	private java.lang.Integer orderNum;
	/** 取值表达式 */
	@Excel(name = "取值表达式", width = 15)
	private java.lang.String replaceVal;
	/** 创建人 */
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/** 创建时间 */
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/** 修改人 */
	@Excel(name = "修改人", width = 15)
	private java.lang.String updateBy;
	/** 修改时间 */
	@Excel(name = "修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
}
