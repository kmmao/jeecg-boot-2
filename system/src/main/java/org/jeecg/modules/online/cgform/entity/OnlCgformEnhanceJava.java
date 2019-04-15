package org.jeecg.modules.online.cgform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
@Data
@TableName("onl_cgform_enhance_java")
public class OnlCgformEnhanceJava implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	
	private java.lang.String cgformHeadId;
	
	private java.lang.String buttonCode;
	
	private String cgJavaType;
	
	private String cgJavaValue;
	
	private String activeStatus;
	
	private String event;
}

