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
@TableName("onl_cgform_index")
public class OnlCgformIndex implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId(type = IdType.UUID)
	private String id;
	/** 主表id */
	private String cgformHeadId;
	/** 索引名称 */
	private String indexName;
	/** 索引栏位 */
	private String indexField;
	/** 索引类型 */
	private String indexType;
	/** 创建人登录名称 */
	private String createBy;
	/** 创建日期 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/** 更新人登录名称 */
	private String updateBy;
	/** 更新日期 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
}
