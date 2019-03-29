package org.jeecg.modules.demo.test.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: jeecg 测试demo 
 * @author： jeecg-boot 
 * @date： 	2018-12-29 
 * @version：V1.0
 */
@Data
@TableName("demo")
public class JeecgDemo implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.UUID)
	private String id;
	/** 姓名 */
	@Excel(name="姓名",width=15)
	private String name;
	/** 关键词 */
	@Excel(name="关键词",width=15)
	private String keyWord;
	/** 打卡时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name="生日",format="yyyy-MM-dd")
	private Date punchTime;
	/** 工资 */
	private java.math.BigDecimal salaryMoney;
	/** 奖金 */
	private Double bonusMoney;
	/** 性别 {男:1,女:2} */
	private String sex;
	/** 年龄 */
	private Integer age;
	/** 生日 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	/** 邮箱 */
	private String email;
	/** 个人简介 */
	private String content;
	/** createTime */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/** 创建人 */
	private String createBy;
	/** 更新人 */
	private String updateBy;
	/** 更新时间 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
}
