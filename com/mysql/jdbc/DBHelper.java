package com.mysql.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class DBHelper {
		private static final String DB_URL = "jdbc:mysql://localhost:3306/CarLife?characterEncoding=utf8&useSSL=true";
		private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
		private static final String DB_USER = "root";
		private static final String DB_PWD = "root";
		private static  Logger logger = Logger.getLogger(DBHelper.class.getName());
		
		static {
			try {
				Class.forName(DRIVER_CLASS);
			} catch (ClassNotFoundException e) {
				logger.error(e.toString());
			}
		}
		
		public static Connection getConn(){
			 try {
				return DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.toString());
			}
			return null;
		}
		
		
		
		
		public static void closeConn(ResultSet set,PreparedStatement statement,Connection connection){
			try {
				if(set != null && !set.isClosed()){
					 set.close();
				}
				if(statement != null && !statement.isClosed()){
					statement.close();
				}
				if(connection != null && !connection.isClosed()){
					connection.close();
				}
			} catch (SQLException e) {
				logger.error(e.toString());
			}
		}
		
		public static List<Map<String, String>> returnMap(ResultSet set) throws Exception{
			List<Map<String, String>> resultMap = null;
			if(set != null){
				resultMap = new ArrayList<Map<String,String>>();
				ResultSetMetaData data = set.getMetaData();
				int clum = data.getColumnCount();
				while(set.next()){
					Map< String, String> map = new HashMap<String, String>();
					for(int i = 1; i <= clum ; i++){
						map.put(data.getColumnName(i), set.getString(i));
					}
					resultMap.add(map);
				}
			}
			
			return resultMap;
		}
}
