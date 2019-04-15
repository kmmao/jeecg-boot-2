package org.jeecg.modules.online.graphreport.vo;

import java.util.List;

import org.jeecg.modules.online.graphreport.entity.OnlGraphreportItem;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Description: 图表报告
 * @author:jeecg-boot
 * @date: 2019-04-10
 * @version:V1.0
 */
@Data
public class OnlGraphreportHeadPage {

	/** id */
	private java.lang.String id;
	/** 图表名称 */
	@Excel(name = "图表名称", width = 15)
	private java.lang.String name;
	/** 图表编码 */
	@Excel(name = "图表编码", width = 15)
	private java.lang.String code;
	/** 查询数据SQL */
	@Excel(name = "查询数据SQL", width = 15)
	private java.lang.String cgrSql;
	/** x轴数据字段 */
	@Excel(name = "x轴数据字段", width = 15)
	private java.lang.String xaxisField;
	/** y轴文字描述 */
	@Excel(name = "y轴文字描述", width = 15)
	private java.lang.String yaxisText;
	/** 描述 */
	@Excel(name = "描述", width = 15)
	private java.lang.String content;
	/** 扩展JS */
	@Excel(name = "扩展JS", width = 15)
	private java.lang.String extendJs;
	/** 图表类型 */
	@Excel(name = "图表类型", width = 15)
	private java.lang.String graphType;
	/** 是否组合 */
	@Excel(name = "是否组合", width = 15)
	private java.lang.String isCombination;
	/** 展示模板 */
	@Excel(name = "展示模板", width = 15)
	private java.lang.String displayTemplate;
	/** 数据类型 */
	@Excel(name = "数据类型", width = 15)
	private java.lang.String dataType;
	/** createTime */
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/** createBy */
	@Excel(name = "createBy", width = 15)
	private java.lang.String createBy;
	/** updateTime */
	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/** updateBy */
	@Excel(name = "updateBy", width = 15)
	private java.lang.String updateBy;

	@ExcelCollection(name = "图表报告项")
	private List<OnlGraphreportItem> onlGraphreportItemList;
}
