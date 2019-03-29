package org.jeecg.modules.online.config.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.model.CgformConfigModel;
import org.jeecg.modules.online.config.service.DbTableHandleI;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过hibernate和脚本来处理来同步数据库
 * 对于修改数据库的字段，考虑各种数据库的情况，字段名称全部、类型修改成大写
 */
@Slf4j
public class DbTableProcess {
	
	private static final String tpl_url = "org/jeecg/modules/online/config/engine/tableTemplate.ftl";
	 
	
	private static  DbTableHandleI dbTableHandle;
	public DbTableProcess() throws SQLException, DBException {
		dbTableHandle = DbTableUtil.getTableHandle();
	}
	
	/**
	  * 创建表
	 * @param model
	 * @throws IOException
	 * @throws TemplateException
	 * @throws HibernateException
	 * @throws SQLException
	 * @throws DBException
	 */
	public static void createTable(CgformConfigModel model) throws IOException, TemplateException, HibernateException, SQLException, DBException  {
		//1.转换模板
		String xml = FreemarkerHelper.parseTemplate(tpl_url, getRootMap(model,DbTableUtil.getDatabaseType()));
		log.info(xml);
		
		//2.执行 SchemaExport
		org.hibernate.cfg.Configuration newconf = new org.hibernate.cfg.Configuration(); 
		String dialect = DbTableUtil.getDialect();
		newconf.addXML(xml).setProperty("hibernate.dialect",dialect);
		
		SchemaExport dbExport = null;
		Connection conn =null;
		try {
			conn = DbTableUtil.getConnection();
			dbExport = new SchemaExport(newconf,conn);
			dbExport.execute(true, true, false, true);
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		}finally {
			if(conn!=null) {
				conn.close();
			}
		}	
		
		//抛出执行异常，抛出第一个即可  
		@SuppressWarnings("unchecked")
		List<Exception> exceptionList = dbExport.getExceptions();
		for (Exception exception : exceptionList) {
			throw new DBException(exception.getMessage());
		}
	}

	/**
	 * 修改列
	 * @param model
	 * @return
	 * @throws DBException
	 * @throws SQLException
	 */
	public List<String> updateTable(CgformConfigModel model) throws DBException, SQLException{
		String dataType = DbTableUtil.getDatabaseType();
		String tableName = DbTableUtil.getTableName(model.getTableName(), dataType);
		String alterSql = "alter table  "+tableName+" ";
		List<String> alterList = new ArrayList<String>();
	       //对表的修改列和删除列的处理，解决hibernate没有该机制
	       try {
			 Map<String, ColumnMeta> dbMetaCol = getDbMetaColumns(null ,tableName);
			 Map<String, ColumnMeta> configCol = getConfigColumns(model);
			 Map<String,String> newAndOldFieldMap = getNewAndOldFieldName(model.getColumns());
			 for (String columnName : configCol.keySet()) {
				 //遍历配置表 判断原表列是否包含当前列
				 if(!dbMetaCol.containsKey(columnName)){
					 //表如果不存在该列，则要对表做修改、增加、删除该列动作 此处无法处理删除的列，因为在这个循环中无法获得该列
					//如果旧列中包含这个列名，说明是修改名称的
					 ColumnMeta cgFormColumnMeta = configCol.get(columnName);
					String oldFieldName = newAndOldFieldMap.get(columnName);
					if(newAndOldFieldMap.containsKey(columnName)&&(dbMetaCol.containsKey(oldFieldName))){
						ColumnMeta dataColumnMeta = dbMetaCol.get(oldFieldName);
						String changeSql = dbTableHandle.getReNameFieldName(cgFormColumnMeta);
						if (DbTableUtil.DB_TYPE_SQLSERVER.equals(dataType)) {
							//sqlserver 修改类名称需要调用存储过程
							alterList.add(changeSql);
						}else {
							alterList.add(alterSql+changeSql);
						} 
						//执行完成之后修改成一致 fildname和oldfieldname
						String oldFieldSql = getUpdateOldFieldSql(columnName, cgFormColumnMeta.getColumnId());
						alterList.add(oldFieldSql);
						
						//updateFieldName(columnName, cgFormColumnMeta.getColumnId(),session);
						//修改表名之后继续判断值有没有变化,有变化继续修改值
						if (!dataColumnMeta.equals(cgFormColumnMeta)) {
							alterList.add(alterSql+getUpdateColumnSql(cgFormColumnMeta,dataColumnMeta));
								if (DbTableUtil.DB_TYPE_POSTGRESQL.equals(dataType)) {
									alterList.add(alterSql + getUpdateSpecialSql(cgFormColumnMeta, dataColumnMeta));
								}
						}
						//判断注释是不是相同,修改注释
						if(!DbTableUtil.DB_TYPE_SQLSERVER.equals(dataType) && !dataColumnMeta.equalsComment(cgFormColumnMeta)){
							alterList.add(getCommentSql(cgFormColumnMeta));
						}
					}else{//不包含就是要增加
						alterList.add(alterSql+getAddColumnSql(cgFormColumnMeta));
						if(!DbTableUtil.DB_TYPE_SQLSERVER.equals(dataType) && StringUtils.isNotEmpty(cgFormColumnMeta.getComment())){
							alterList.add(getCommentSql(cgFormColumnMeta));
						}
					}
				}else {//已经存在的判断是否修改了类型长度。。
					//判断是否类型、长度、是否为空、精度被修改，如果有修改则处理修改
					ColumnMeta dataColumnMeta = dbMetaCol.get(columnName);
					ColumnMeta cgFormColumnMeta = configCol.get(columnName);
					//如果不相同，则表示有变化，则需要修改
					if (!dataColumnMeta.equalsByDataType(cgFormColumnMeta,dataType)) {
						alterList.add(alterSql+getUpdateColumnSql(cgFormColumnMeta,dataColumnMeta));
					}
					if(!DbTableUtil.DB_TYPE_SQLSERVER.equals(dataType) && !dataColumnMeta.equalsComment(cgFormColumnMeta)){
						alterList.add(getCommentSql(cgFormColumnMeta));
					}
				}
				
			}
			 //删除数据库的列
			 //要判断这个列不是修改的
			 for (String columnName : dbMetaCol.keySet()) {
				if ((!configCol.containsKey(columnName.toLowerCase()))&& (!newAndOldFieldMap.containsValue(columnName.toLowerCase()))) {
					alterList.add(alterSql+getDropColumnSql(columnName));
				}
			}
		} catch (SQLException e1) {
			throw new RuntimeException();
		}
		log.info(alterList.toString());
		return alterList;
	}
	
	
	/**
	  * 获取模板所需参数
	 * @param model
	 * @param dataType
	 * @return
	 */
	private static Map<String,Object> getRootMap(CgformConfigModel model,String dataType) {
		Map<String,Object> map = new HashMap<>();
		for(OnlCgformField field :model.getColumns()){
			field.setDbDefaultVal(judgeIsNumber(field.getDbDefaultVal()));
		}
		map.put("entity", model);
		map.put("dataType", dataType);
		return map;
	}
	
	/**
	  * 获取原表列数据
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private Map<String, ColumnMeta> getDbMetaColumns(String schemaName, String tableName) throws SQLException{
		Map<String, ColumnMeta> columnMap = new HashMap<String, ColumnMeta>();
		Connection conn = null;
		try {
			conn = DbTableUtil.getConnection();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		DatabaseMetaData dbMetaData = conn.getMetaData();
		ResultSet rs = dbMetaData.getColumns(null, schemaName, tableName, "%");
		ColumnMeta columnMeta;
		while (rs.next()){
			columnMeta = new ColumnMeta();
			columnMeta.setTableName(tableName);
			
			String columnName = rs.getString("COLUMN_NAME").toLowerCase();
			columnMeta.setColumnName(columnName);
			
			String typeName = rs.getString("TYPE_NAME");
			int decimalDigits = rs.getInt("DECIMAL_DIGITS");
			
			String colunmType = dbTableHandle.getMatchClassTypeByDataType(typeName,decimalDigits);
			columnMeta.setColunmType(colunmType);
			
			int columnSize = rs.getInt("COLUMN_SIZE");
			columnMeta.setColumnSize(columnSize);
			
			columnMeta.setDecimalDigits(decimalDigits);
			String isNullable = rs.getInt("NULLABLE")==1?"Y":"N";
			columnMeta.setIsNullable(isNullable);
			
			String comment = rs.getString("REMARKS");
			columnMeta.setComment(comment);
			
			String columnDef = rs.getString("COLUMN_DEF");
			String fieldDefault = judgeIsNumber(columnDef)==null?"":judgeIsNumber(columnDef);
			
			columnMeta.setFieldDefault(fieldDefault);
			log.info("getColumnMetadataFormDataBase --->COLUMN_NAME:"+columnName.toUpperCase()+" TYPE_NAME :"+typeName
					+" DECIMAL_DIGITS:"+decimalDigits+" COLUMN_SIZE:"+columnSize);
			columnMap.put(columnName, columnMeta);
		
		}
		
		return columnMap;
	}
	
	/**
	  * 获取cgform配置表数据列
	 * @return
	 */
	private Map<String, ColumnMeta> getConfigColumns(CgformConfigModel model){
		Map<String, ColumnMeta> columnMap = new HashMap<String, ColumnMeta>();
		List<OnlCgformField> fieldList = model.getColumns();
		ColumnMeta columnMeta;
		for (OnlCgformField field : fieldList) {
			columnMeta = new ColumnMeta();
			columnMeta.setTableName(model.getTableName().toLowerCase());
			columnMeta.setColumnId(field.getId());
			columnMeta.setColumnName(field.getDbFieldName().toLowerCase());
			columnMeta.setColumnSize(field.getDbLength());
			columnMeta.setColunmType(field.getDbType());
			columnMeta.setIsNullable(field.getDbIsNull()==1?"Y":"N");
			columnMeta.setComment(field.getDbFieldTxt());
			columnMeta.setDecimalDigits(field.getDbPointLength());
			columnMeta.setFieldDefault(judgeIsNumber(field.getDbDefaultVal()));
			columnMeta.setPkType(model.getJformPkType()==null?"UUID":model.getJformPkType());
			columnMeta.setOldColumnName(field.getDbFieldNameOld()!=null?field.getDbFieldNameOld().toLowerCase():null);
			log.info("getColumnMetadataFormCgForm ---->COLUMN_NAME:"+field.getDbFieldName().toLowerCase()+" TYPE_NAME:"+field.getDbType().toLowerCase()
					+" DECIMAL_DIGITS:"+field.getDbPointLength()+" COLUMN_SIZE:"+field.getDbLength());
			columnMap.put(field.getDbFieldName().toLowerCase(), columnMeta);
			
		}
		return columnMap;
	}
	
	
	/**
	 * 返回cgForm中列名的新和旧的对应关系
	 * @param table
	 * @return
	 */
	private Map<String, String> getNewAndOldFieldName(List<OnlCgformField> fieldList){
		Map<String, String> map = new HashMap<String, String>();
		for (OnlCgformField field : fieldList) {
			map.put(field.getDbFieldName(), field.getDbFieldNameOld());
		}
		return map;
	}
	
	
	/**
	 * 创建删除字段的sql
	 * @param fieldName
	 * @return
	 */
	private String getDropColumnSql(String fieldName) {
		//ALTER TABLE `test` DROP COLUMN `aaaa`;
		return dbTableHandle.getDropColumnSql(fieldName);
	}
	
	/**
	 * 创建更新字段的sql
	 * @param newColumn
	 * @param agoColumn 
	 * @return
	 */
	private String getUpdateColumnSql(ColumnMeta cgformcolumnMeta,ColumnMeta datacolumnMeta)throws DBException {
		//modify birthday varchar2(10) not null;
		//return " MODIFY COLUMN  "+getFieldDesc(columnMeta)+",";
		return dbTableHandle.getUpdateColumnSql(cgformcolumnMeta,datacolumnMeta);
	}
	
	/**
	 * 处理特殊sql
	 * @param cgformcolumnMeta
	 * @param datacolumnMeta
	 * @return
	 */
	private String getUpdateSpecialSql(ColumnMeta cgformcolumnMeta,ColumnMeta datacolumnMeta) {
		return dbTableHandle.getSpecialHandle(cgformcolumnMeta,datacolumnMeta);
	}
	
	/**
	 * 修改列名
	 * @param columnMeta
	 * @return
	 */
	private String getReNameFieldName(ColumnMeta columnMeta){
		//CHANGE COLUMN `name1` `name2`  varchar(50)  NULL  COMMENT '姓名';
		//return "CHANGE COLUMN  "+columnMeta.getOldColumnName() +" "+getFieldDesc(columnMeta)+",";
		return dbTableHandle.getReNameFieldName(columnMeta);
	}
	
	/**
	 * 创建增加字段的sql
	 * @param column
	 * @param agoColumn 
	 * @return
	 */
	private String getAddColumnSql(ColumnMeta columnMeta) {
		//return " ADD COLUMN "+getFieldDesc(columnMeta)+",";
		return dbTableHandle.getAddColumnSql(columnMeta);
	}
	
	/**
	 * 添加注释的sql
	 *@Author JEECG
	 *@date   2013年12月1日
	 *@param cgFormColumnMeta
	 *@return
	 */
	private String getCommentSql(ColumnMeta columnMeta) {
		return dbTableHandle.getCommentSql(columnMeta);
	}
	
	private String getUpdateOldFieldSql(String columnName,String id) {
		return "update cgform_field set old_field_name= '"+columnName+"' where id='"+id+"'";
	}
	
	private int updateFieldName(String columnName,String id,Session session){
		return   session.createSQLQuery("update cgform_field set old_field_name= '"+columnName+"' where id='"+id+"'").executeUpdate();
	}
	
	/**
	 * 判断是不数字,不是数字的话加上''
	 *@Author JEECG
	 *@date   2013年11月27日
	 *@param text
	 *@return
	 */
	private static String judgeIsNumber(String text){
		if(StringUtils.isNotEmpty(text)){
			try{
				Double.valueOf(text);
			}catch(Exception e){
				//update-begin--Author:liushaoqian  Date:20180710 for：TASK #2924 【online样式 -少谦】字段默认值，循环加了很多单引号
				if(!( text.startsWith("\'") && text.endsWith("\'") )){
					text = "'"+text+"'";
				}
				//update-begin--Author:liushaoqian  Date:20180710 for：TASK #2924 【online样式 -少谦】字段默认值，循环加了很多单引号
			}
		}
		return text;
	}
	
	public String dropIndex(String indexName,String tableName) {
		return dbTableHandle.dropIndexs(indexName, tableName);
	}
	
	/**
	 * 获取索引信息
	 *  catalog : 类别名称，为 null 则表示该类别名称不应用于缩小搜索范围
	 *  schema : 模式名称，为 null 则表示该模式名称不应用于缩小搜索范围
	 *  table : 表名称，因为存储在此数据库中，所以它必须匹配表名称
	 *  unique : 该参数为 true 时，仅返回惟一值的索引；该参数为 false 时，返回所有索引，不管它们是否惟一
	 *  approximate : 该参数为 true 时，允许结果是接近的数据值或这些数据值以外的值；该参数为 false 时，要求结果是精确结果
	 * @return index_key_name 集合
	 * @throws SQLException 
	 */
	public static List<String> getIndexInfo(String tbname) throws SQLException {
		Connection conn = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<String>();
        try {
        	conn = DbTableUtil.getConnection();
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getIndexInfo(null, null, tbname, false, false);
            ResultSetMetaData md = rs.getMetaData();
            while (rs.next()) {
            	
            	String index_name = rs.getString("INDEX_NAME");
            	if(oConvertUtils.isEmpty(index_name)) {
            		index_name = rs.getString("index_name");
            	}
            	if(oConvertUtils.isNotEmpty(index_name)) {
            		list.add(index_name);
            	}
            	/*System.out.println("-----------------------------");
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    System.out.println(md.getColumnName(i) + "==" + rs.getObject(i));
                }
                System.out.println("-----------------------------");*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	if(conn!=null) {
        		 conn.close();
        	}
        }
        return list;
    }
	
}
