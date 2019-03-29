package org.jeecg.modules.online.cgform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.util.CgformUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date: 2019-03-12
 * @version: V1.0
 */
@Service
@Slf4j
public class OnlCgformFieldServiceImpl extends ServiceImpl<OnlCgformFieldMapper, OnlCgformField> implements IOnlCgformFieldService {

	@Resource
	private OnlCgformFieldMapper onlCgformFieldMapper;
	
	
	@Override
	public Map<String, Object> queryAutolistPage(String tbname, String headId,Map<String,Object> params) {
		Map<String, Object> result = new HashMap<>();
		
		//1.查询字段集合
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, headId);
		query.eq(OnlCgformField::getIsShowList,1);
		List<OnlCgformField> fieldList = this.list(query);
		
		//2.获取查询条件sql
		String conditionSql = CgformUtil.getAutoListConditionSql(fieldList, params);
		//3.查询数据库总数
		String countSql = "select count(*) from "+tbname+CgformUtil.SQL_WHERE_TRUE+conditionSql;
		Integer count = this.onlCgformFieldMapper.queryCountBySql(countSql);
		if(count==null || count==0) {
			result.put("total", 0);
		}else {
			result.put("total",count);
			//4.若数据库有数据则分页查询
			Integer pageSize = params.get("pageSize")==null?10:Integer.parseInt(params.get("pageSize").toString());
			Integer pageNo = params.get("pageNo")==null?1:Integer.parseInt(params.get("pageNo").toString());
			int pageStart = (pageNo-1)*pageSize;
			StringBuffer sb = new StringBuffer();
			//基础SQL
			CgformUtil.getAutoListBaseSql(tbname, fieldList, sb);
			//拼接条件
			sb.append(CgformUtil.SQL_WHERE_TRUE+conditionSql);
			//排序 //TODO 这边默认每张表中都有create_time字段,如果没有这里应该会报错
			String order = params.get("order").toString();
			String column = params.get("column").toString();
			sb.append(CgformUtil.SQL_ORDER+oConvertUtils.camelToUnderline(column));
			if(CgformUtil.SQL_ASC.equals(order)) {
				sb.append(" "+CgformUtil.SQL_ASC);
			}else {
				sb.append(" "+CgformUtil.SQL_DESC);
			}
			//分页
			sb.append(CgformUtil.SQL_LIMIT+pageStart+","+pageSize);
			log.info("--查询SQL-->",sb.toString());
			List<Map<String,Object>> list = this.onlCgformFieldMapper.queryListBySql(sb.toString());
			result.put("records", list);
		}
		return result;
	}

	@Override
	public void saveFormData(String code,String tbname, JSONObject json) {
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.eq(OnlCgformField::getIsShowForm,1);
		List<OnlCgformField> fieldList = this.list(query);
		String sql = CgformUtil.getFormDataSaveSql(tbname, fieldList, json);
		onlCgformFieldMapper.saveFormData(sql);
	}

	@Override
	public void editFormData(String code,String tbname, JSONObject json) {
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.eq(OnlCgformField::getIsShowForm,1);
		List<OnlCgformField> fieldList =  this.list(query);
		String sql = CgformUtil.getFormDataEditSql(tbname, fieldList, json);
		onlCgformFieldMapper.editFormData(sql);
	}

	@Override
	public Map<String, Object> queryFormData(String code, String tbname, String id) {
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.eq(OnlCgformField::getIsShowForm,1);
		List<OnlCgformField> fieldList =  this.list(query);
		String sql = CgformUtil.getSelectFormSql(tbname, fieldList, id);
		return onlCgformFieldMapper.queryFormData(sql);
	}

	@Override
	public void deleteAutoList(String tbname, String id) {
		String[] arr = id.split(",");
		StringBuffer sb = new StringBuffer();
		for (String str : arr) {
			if(str==null||"".equals(str)) {
				continue;
			}
			sb.append("'"+str+"',");
		}
		String temp = sb.toString();
		String sql = "DELETE FROM "+tbname + " where id in("+temp.substring(0,temp.length()-1)+")";
		log.info("--删除sql-->"+sql);
		this.onlCgformFieldMapper.deleteAutoList(sql);
	}

	@Override
	public List<Map<String, String>> getAutoListQueryInfo(String code) {
		LambdaQueryWrapper<OnlCgformField> query = new LambdaQueryWrapper<OnlCgformField>();
		query.eq(OnlCgformField::getCgformHeadId, code);
		query.eq(OnlCgformField::getIsQuery,1);
		List<OnlCgformField> fieldList =  this.list(query);
		List<Map<String, String>> list = new ArrayList<>();
		int i=0;
		for (OnlCgformField item : fieldList) {
			Map<String,String> map = new HashMap<>();
			map.put("label",item.getDbFieldTxt());
			map.put("view",item.getFieldShowType());
			map.put("mode",item.getQueryMode());
			map.put("field",item.getDbFieldName());
			if((++i)>2) {
				map.put("hidden","1");
			}
			list.add(map);
		}
		return list;
	}

	
}
