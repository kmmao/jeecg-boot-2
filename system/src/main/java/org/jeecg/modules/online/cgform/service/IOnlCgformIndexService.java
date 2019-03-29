package org.jeecg.modules.online.cgform.service;

import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date:   2019-03-12
 * @version: V1.0
 */
public interface IOnlCgformIndexService extends IService<OnlCgformIndex> {
	
	/**
	  * 创建表索引
	 */
	public void createIndex(String code, String databaseType, String tbname);

}
