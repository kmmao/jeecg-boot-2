package org.jeecg.modules.system.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecg.modules.system.entity.SysDict;
import org.jeecg.modules.system.mapper.SysDictMapper;
import org.jeecg.modules.system.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author zhangweijian
 * @since 2018-12-28
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

	@Autowired
	private SysDictMapper sysDictMapper;

	/**
	 * 通过查询指定code 获取字典
	 * @param code
	 * @return
	 */
	@Override
	public List<Map<String, String>> queryDictItemsByCode(String code) {
		return sysDictMapper.queryDictItemsByCode(code);
	}

	/**
	 * 通过查询指定table的 text code 获取字典
	 * @param table
	 * @param text
	 * @param code
	 * @return
	 */
	@Override
	public List<Map<String, String>> queryTableDictItemsByCode(String table, String text, String code) {
		return sysDictMapper.queryTableDictItemsByCode(table,text,code);
	}

	/**
	 * 通过查询指定code 获取字典值text
	 * @param code
	 * @param key
	 * @return
	 */

	@Override
	public String queryDictTextByKey(String code, String key) {
		return sysDictMapper.queryDictTextByKey(code, key);
	}

	/**
	 * 通过查询指定table的 text code 获取字典值text
	 * @param table
	 * @param text
	 * @param code
	 * @param key
	 * @return
	 */
	@Override
	public String queryTableDictTextByKey(String table,String text,String code, String key) {
		return sysDictMapper.queryTableDictTextByKey(table,text,code,key);
	}


}
