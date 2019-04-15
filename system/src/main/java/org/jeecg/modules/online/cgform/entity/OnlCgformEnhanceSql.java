package org.jeecg.modules.online.cgform.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("onl_cgform_enhance_sql")
public class OnlCgformEnhanceSql implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.UUID)
	private java.lang.String id;
	
	/**formId*/
	private java.lang.String cgformHeadId;
	
	/**按钮编码*/
	private java.lang.String buttonCode;
	
	/** 自定义sql */
	private java.lang.String cgbSql;
	
	private java.lang.String cgbSqlName;

	/**描述*/
	private java.lang.String content;
	
}