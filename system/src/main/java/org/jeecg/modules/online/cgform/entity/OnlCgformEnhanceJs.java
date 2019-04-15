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
@TableName("onl_cgform_enhance_js")
public class OnlCgformEnhanceJs implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	
	/**formId*/
	private java.lang.String cgformHeadId;
	
	/**js增强类型（form/list）*/
	private java.lang.String cgJsType;

	/**增强js Str*/
	private String cgJs;
	
	/**描述*/
	private java.lang.String content;	
}

