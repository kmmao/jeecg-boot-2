package org.jeecg.modules.online.cgform.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("onl_cgform_button")
public class OnlCgformButton implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	
	/**formId*/
	private java.lang.String cgformHeadId;
	
	/**按钮编码*/
	private java.lang.String buttonCode;
	/**按钮名称*/
	private java.lang.String buttonName;
	/**按钮样式link/button*/
	private java.lang.String buttonStyle;
	/**动作类型:js/bus*/
	private java.lang.String optType;
	/**显示表达式:exp="status#eq#0"*/
	private java.lang.String exp;
	/**0:禁用/1:使用*/
	private java.lang.String buttonStatus;
	/**顺序*/
	private java.lang.Integer orderNum;
	/**按钮图标样式*/
	private java.lang.String buttonIcon;
	
}
