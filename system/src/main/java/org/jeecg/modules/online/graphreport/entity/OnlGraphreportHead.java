package org.jeecg.modules.online.graphreport.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Description: 图表报告
 * @author:jeecg-boot
 * @date: 2019-04-11
 * @version:V1.0
 */
@Data
@TableName("onl_graphreport_head")
public class OnlGraphreportHead implements Serializable {
	private static final long serialVersionUID = 1L;

	/** id */
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	/** 图表名称 */
	private java.lang.String name;
	/** 图表编码 */
	private java.lang.String code;
	/** 查询数据SQL */
	private java.lang.String cgrSql;
	/** x轴数据字段 */
	private java.lang.String xaxisField;
	/** y轴文字描述 */
	private java.lang.String yaxisText;
	/** 描述 */
	private java.lang.String content;
	/** 扩展JS */
	private java.lang.String extendJs;
	/** graphType */
	private java.lang.String graphType;
	/** isCombination */
	private java.lang.String isCombination;
	/** displayTemplate */
	private java.lang.String displayTemplate;
	/** dataType */
	private java.lang.String dataType;
	/** createTime */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/** createBy */
	private java.lang.String createBy;
	/** updateTime */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/** updateBy */
	private java.lang.String updateBy;
}
