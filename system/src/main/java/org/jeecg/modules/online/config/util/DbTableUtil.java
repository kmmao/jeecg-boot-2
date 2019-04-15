package org.jeecg.modules.online.config.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.service.DbTableHandleI;
import org.jeecg.modules.online.config.service.impl.DbTableMysqlHandleImpl;
import org.jeecg.modules.online.config.service.impl.DbTableOracleHandleImpl;
import org.jeecg.modules.online.config.service.impl.DbTablePostgresHandleImpl;
import org.jeecg.modules.online.config.service.impl.DbTableSQLServerHandleImpl;

import lombok.extern.slf4j.Slf4j;


/**
 * 数据库工具类
 * @author jueyue
 * 2013年7月6日
 */
@Slf4j
public class DbTableUtil {
	
	public static final String DB_TYPE_MYSQL="MYSQL";
	public static final String DB_TYPE_ORACLE="ORACLE";
	public static final String DB_TYPE_POSTGRESQL="POSTGRESQL";
	public static final String DB_TYPE_SQLSERVER="SQLSERVER";
	
	/**
	  * 根据数据库类型构造不同handler
	 * @return
	 * @throws SQLException
	 * @throws DBException
	 */
	public static DbTableHandleI getTableHandle() throws SQLException,DBException {
		DbTableHandleI dbTableHandle = null;
		String dbType = getDatabaseType(); 
		switch (dbType) {
		case DB_TYPE_MYSQL:
			dbTableHandle = new DbTableMysqlHandleImpl();
			break;
		case DB_TYPE_ORACLE:
			dbTableHandle = new DbTableOracleHandleImpl();
			break;
		case DB_TYPE_SQLSERVER:
			dbTableHandle = new DbTableSQLServerHandleImpl();
			break;
		case DB_TYPE_POSTGRESQL:
			dbTableHandle = new DbTablePostgresHandleImpl();
			break;
		default:
			break;
		}
		return dbTableHandle;
	}
	
	/**
	  * 获取数据库连接对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		DataSource dataSource = SpringContextUtils.getApplicationContext().getBean(DataSource.class);
		return dataSource.getConnection();
	}
	
	/**
 	  * 获取数据库类型
	 * @return
	 * @throws SQLException
	 * @throws DBException
	 */
	public static String getDatabaseType() throws SQLException, DBException {
		DatabaseMetaData md = getConnection().getMetaData();
		String dbType = md.getDatabaseProductName().toLowerCase();
		if(dbType.indexOf("mysql")>=0) {
			return DB_TYPE_MYSQL;
		}else if(dbType.indexOf("oracle")>=0) {
			return DB_TYPE_ORACLE;
		}else if(dbType.indexOf("sqlserver")>=0) {
			return DB_TYPE_SQLSERVER;
		}else if(dbType.indexOf("postgresql")>=0) {
			return DB_TYPE_POSTGRESQL;
		}else {
			throw new DBException("数据库类型:["+dbType+"]不识别!");
		}
	}
	
	public static String getDatabaseType(DataSource dataSource) throws SQLException, DBException {
		DatabaseMetaData md = dataSource.getConnection().getMetaData();
		String dbType = md.getDatabaseProductName().toLowerCase();
		if(dbType.indexOf("mysql")>=0) {
			return DB_TYPE_MYSQL;
		}else if(dbType.indexOf("oracle")>=0) {
			return DB_TYPE_ORACLE;
		}else if(dbType.indexOf("sqlserver")>=0) {
			return DB_TYPE_SQLSERVER;
		}else if(dbType.indexOf("postgresql")>=0) {
			return DB_TYPE_POSTGRESQL;
		}else {
			throw new DBException("数据库类型:["+dbType+"]不识别!");
		}
	}
	
	public static String getDataType(Session session) throws SQLException, DBException{
		return getDatabaseType();
	}
	
	public static String getTableName(String tableName,String dataBaseType) {
		switch (tableName) {
		case DB_TYPE_ORACLE:
			return tableName.toUpperCase();
		case DB_TYPE_POSTGRESQL:
			return tableName.toLowerCase();
		default:
			return tableName;
		}
	}
	
	/**
	  * 判断数据库表是否存在
	 * @param tableName
	 * @return
	 */
	public static Boolean judgeTableIsExit(String tableName) {
		Connection conn = null;
		ResultSet rs = null;
		try {
			String[] types = { "TABLE" };
			conn = DbTableUtil.getConnection();
			DatabaseMetaData dbMetaData = conn.getMetaData();
			String dataBaseType = dbMetaData.getDatabaseProductName().toUpperCase();
			String tableNamePattern = getTableName(tableName, dataBaseType);
			rs = dbMetaData.getTables(null,null,tableNamePattern, types);
			if (rs.next()) {
				log.info("数据库表：【"+tableName+"】已存在");
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}finally{//关闭连接
			try {
				if(rs!=null){rs.close();}
				if(conn!=null){conn.close();}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 获取列的Map
	 * key 是 cloumn_name value 是 List<Map<String, Object>>
	 * @param queryForList
	 * @return 
	 */
	public static Map<String, Object> getColumnMap(
			List<Map<String, Object>> queryForList) {
		Map<String, Object> columnMap = new HashMap<String, Object>();
		for(int i =0 ;i<queryForList.size();i++){
			columnMap.put(queryForList.get(i).get("column_name").toString(), queryForList.get(i));
		}
		return columnMap;
	}
	

	/**
	  * 获取方言
	 * @return
	 * @throws SQLException
	 * @throws DBException
	 */
	//TODO 方言获取,此方法不能通用那就直接在yml中配置死
	public static String getDialect() throws SQLException, DBException {
		String databaseType = getDatabaseType();
		String dialect = "org.hibernate.dialect.MySQLDialect";
		switch (databaseType) {
		case DB_TYPE_SQLSERVER:
			dialect = "org.hibernate.dialect.SQLServerDialect";
			break;
		case DB_TYPE_POSTGRESQL:
			dialect = "org.hibernate.dialect.PostgreSQLDialect";
			break;
		case DB_TYPE_ORACLE:
			dialect = "org.hibernate.dialect.OracleDialect";
			break;
		default:
			break;
		}
		return dialect;
	}
	
	/**
	 * 把配置中的字段翻译成数据库中的字段  如:A ---> _a
	 * @param fileName
	 * @return
	 */
	public static String translatorToDbField(String fileName){
		
		//去掉转换
		return fileName;
//		String name = "";
//		char[] chars = fileName.toCharArray();
//		for(int i =0 ;i<chars.length;i++){
//			name+= chars[i]>'A'&&chars[i]<'Z'?("_"+Character.toLowerCase(chars[i])):chars[i];
//		}
//		return name;
	}
	
	
}
