package org.jeecg.modules.online.cgform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformIndexMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.jeecg.modules.online.config.util.DbTableUtil;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: Online表单开发
 * @author: jeecg-boot
 * @date:   2019-03-12
 * @version: V1.0
 */
@Service
public class OnlCgformIndexServiceImpl extends ServiceImpl<OnlCgformIndexMapper, OnlCgformIndex> implements IOnlCgformIndexService {

	@Resource
	private OnlCgformHeadMapper onlCgformHeadMapper;
	
	
	@Override
	public void createIndex(String code,String databaseType,String tbname) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<OnlCgformIndex> query = new LambdaQueryWrapper<OnlCgformIndex>();
		query.eq(OnlCgformIndex::getCgformHeadId, code);
		List<OnlCgformIndex> indexes = this.list(query);
		if(indexes!=null && indexes.size()>0){
			for(OnlCgformIndex cgform : indexes){
				String sql = "";
				
				String indexName = cgform.getIndexName();
				String indexField = cgform.getIndexField();
				String indexType = "normal".equals(cgform.getIndexType())?" index ":cgform.getIndexType()+ " index ";
				//TODO 确认创建索引的语句是否是一样的,如果一样 没必要判断了
				switch (databaseType) {
				case DbTableUtil.DB_TYPE_MYSQL:
					sql = "create " + indexType  + indexName + " on " + tbname + "(" + indexField + ")";
					break;
				case DbTableUtil.DB_TYPE_ORACLE:
					sql = "create " + indexType  + indexName + " on " + tbname + "(" + indexField + ")";
					break;
				case DbTableUtil.DB_TYPE_SQLSERVER:
					sql = "create " + indexType + indexName + " on " + tbname + "(" + indexField + ")";
					break;
				case DbTableUtil.DB_TYPE_POSTGRESQL:
					sql = "create " + indexType + indexName + " on " + tbname + "(" + indexField + ")";
					break;
				default:
					sql = "create " + indexType + indexName + " on " + tbname + "(" + indexField + ")";
					break;
				}
				this.onlCgformHeadMapper.executeDDL(sql);
			}
		}
	}

}
