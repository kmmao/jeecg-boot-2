package org.jeecg.modules.online.config.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.jeecg.modules.online.config.util.ColumnMeta;
import org.springframework.stereotype.Service;



/**
 * oracle的表工具类
 * oracle语句结尾不能使用 ;
 */
@Service
public class DbTableOracleHandleImpl implements DbTableHandleI {

	
	@Override
	public String getAddColumnSql(ColumnMeta columnMeta) {
		return " ADD  "+getAddFieldDesc(columnMeta)+"";
	}

	
	@Override
	public String getReNameFieldName(ColumnMeta columnMeta) {
		return "RENAME COLUMN  "+columnMeta.getOldColumnName() +" TO "+columnMeta.getColumnName()+"";
	}

	
	@Override
	public String getUpdateColumnSql(ColumnMeta cgformcolumnMeta,ColumnMeta datacolumnMeta) {
		return " MODIFY   "+getUpdateFieldDesc(cgformcolumnMeta,datacolumnMeta)+"";
	}

	
	@Override
	public String getMatchClassTypeByDataType(String dataType,int digits) {
		//update-begin--Author:scott  Date:20180227 for：oracle针对blob\text\nvarchar2逻辑处理--------------------
		String result ="";
		if (dataType.equalsIgnoreCase("varchar2")) {
			result="string";
		}if (dataType.equalsIgnoreCase("nvarchar2")) {
			result="string";
		} else if(dataType.equalsIgnoreCase("double")){
			result="double";
		}else if (dataType.equalsIgnoreCase("number") && digits==0) {
			result="int";
		}else if (dataType.equalsIgnoreCase("number") && digits!=0) {
			result="double";
		}else if (dataType.equalsIgnoreCase("int")) {
			result="int";
		}else if (dataType.equalsIgnoreCase("Date")) {
			result="date";
		}else if (dataType.equalsIgnoreCase("Datetime")) {
			result="date";
		}else if (dataType.equalsIgnoreCase("blob")) {
			result="blob";
		}else if (dataType.equalsIgnoreCase("clob")) {
			result="text";
		}
		//update-begin--Author:scott  Date:20180227 for：oracle针对blob\text\nvarchar2逻辑处理--------------------
		return result;
	}

	
	@Override
	public String dropTableSQL(String tableName) {
		return " DROP TABLE  "+tableName.toLowerCase()+" ";
	}

	
	@Override
	public String getDropColumnSql(String fieldName) {
		return " DROP COLUMN "+fieldName.toUpperCase()+"";
	}
	
	private String getAddFieldDesc(ColumnMeta columnMeta) {
		String result ="";
		if(columnMeta.getColunmType().equalsIgnoreCase("string")){
			result = columnMeta.getColumnName()+" varchar2("+columnMeta.getColumnSize()+")";
		}else if(columnMeta.getColunmType().equalsIgnoreCase("date")){
			result = columnMeta.getColumnName()+" datetime";
		}else if(columnMeta.getColunmType().equalsIgnoreCase("int")){
			result = columnMeta.getColumnName()+" NUMBER("+columnMeta.getColumnSize()+")";
		}else if(columnMeta.getColunmType().equalsIgnoreCase("double")){
			result = columnMeta.getColumnName()+" NUMBER("+columnMeta.getColumnSize()+","+columnMeta.getDecimalDigits()+")";
		}else if(columnMeta.getColunmType().equalsIgnoreCase("bigdecimal")){
			result = columnMeta.getColumnName()+" NUMBER("+columnMeta.getColumnSize()+","+columnMeta.getDecimalDigits()+")";
		}else if(columnMeta.getColunmType().equalsIgnoreCase("text")){ 
			result = columnMeta.getColumnName()+" CLOB ";
		}else if(columnMeta.getColunmType().equalsIgnoreCase("blob")){
			result = columnMeta.getColumnName()+" BLOB ";
		}
		result += (StringUtils.isNotEmpty(columnMeta.getFieldDefault())?" DEFAULT "+columnMeta.getFieldDefault():" ");
		result += ("Y".equals(columnMeta.getIsNullable())?" NULL":" NOT NULL");
		return result;
	}
	private String getUpdateFieldDesc(ColumnMeta cgformcolumnMeta,ColumnMeta datacolumnMeta) {
		String result ="";
		String isnull="";
		//oracle对于是否为空必须跟原来的比对
		if (!datacolumnMeta.getIsNullable().equals(cgformcolumnMeta.getIsNullable())) {
			isnull=(cgformcolumnMeta.getIsNullable().equals("Y")?"NULL":"NOT NULL");
		}
		//update-begin--Author:scott  Date:20180227 for：oracle针对blob\text\nvarchar2逻辑处理--------------------
		if(cgformcolumnMeta.getColunmType().equalsIgnoreCase("string")){
				result = cgformcolumnMeta.getColumnName()+" varchar2("+cgformcolumnMeta.getColumnSize()+")"+isnull;
			
		}else if(cgformcolumnMeta.getColunmType().equalsIgnoreCase("date")){
				result = cgformcolumnMeta.getColumnName()+" date "+isnull;
			
		}else if(cgformcolumnMeta.getColunmType().equalsIgnoreCase("int")){
				result = cgformcolumnMeta.getColumnName()+" NUMBER("+cgformcolumnMeta.getColumnSize()+") "+isnull;
			
		}else if(cgformcolumnMeta.getColunmType().equalsIgnoreCase("double")){
				result = cgformcolumnMeta.getColumnName()+" NUMBER("+cgformcolumnMeta.getColumnSize()+","+cgformcolumnMeta.getDecimalDigits()+") "+isnull;
		}else if(cgformcolumnMeta.getColunmType().equalsIgnoreCase("bigdecimal")){
			result = cgformcolumnMeta.getColumnName()+" NUMBER("+cgformcolumnMeta.getColumnSize()+","+cgformcolumnMeta.getDecimalDigits()+") "+isnull;
		}else if(cgformcolumnMeta.getColunmType().equalsIgnoreCase("blob")){
			result = cgformcolumnMeta.getColumnName()+" BLOB "+isnull;
		}else if(cgformcolumnMeta.getColunmType().equalsIgnoreCase("text")){ 
			result = cgformcolumnMeta.getColumnName()+" CLOB "+isnull;
		}
		//update-end--Author:scott  Date:20180227 for：oracle针对blob\text\nvarchar2逻辑处理--------------------
		result += (StringUtils.isNotEmpty(cgformcolumnMeta.getFieldDefault())?" DEFAULT "+cgformcolumnMeta.getFieldDefault():" ");
		result += isnull;
		return result;
	}

	
	@Override
	public String getCommentSql(ColumnMeta columnMeta) {
		return "COMMENT ON COLUMN "+columnMeta.getTableName()+"."+columnMeta.getColumnName()+" IS '" +columnMeta.getComment()+"'";
	}

	
	@Override
	public String getSpecialHandle(ColumnMeta cgformcolumnMeta,
			ColumnMeta datacolumnMeta) {
		return null;
	}


	@Override
	public String dropIndexs(String indexName, String tableName) {
		return "DROP INDEX " + indexName;
	}

}
