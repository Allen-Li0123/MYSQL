package com.mysql.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.apache.log4j.Logger;

public class GetData extends DBHelper {
	
	private static  Logger logger = Logger.getLogger (GetData.class);
	private static Connection conn;
	private static PreparedStatement statement;
	private static ResultSet resultSet;
	
	public static void insertT_Vehicles_Brand() throws Exception{
		conn = getConn();
		statement = conn.prepareStatement("select AutoName,ChCode,Icon from Brand");
		resultSet  = statement.executeQuery();
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("INSERT INTO T_Vehicles_Brands " +
				"(name,status,pinYin,logo," +
				"description,createId,createTime," +
				"lastUpdateTime,source,isDelete) " +
				" VALUES" +
				" ( ? , 0 , ? , ? ," +
				"?,1, ?,?,'admin add',1)");
		while(resultSet.next()){
			String AutoName = resultSet.getString(1);
			String Icon = resultSet.getString(3);
			statement.setString(1, AutoName);
			statement.setString(2, getPingYin(AutoName));
			statement.setString(3, Icon);
			statement.setString(4, AutoName + " ~ " + getPingYin(AutoName));
			statement.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			statement.setString(6,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			statement.addBatch();
		}
		statement.executeBatch();
		conn.setAutoCommit(true);
		closeConn(resultSet, statement, conn);
	}
	
	//T_Vehicles_Displacement 2016-09-10 15:37:41
	public static void insertT_Vehicles_Series() throws Exception{
		conn = getConn();
		statement = conn.prepareStatement("select c.id,c.`name`,a.factoryText from BrandFactory a inner join Brand b on a.brandid = b.id left join T_Vehicles_Brands c on b.AutoName = c.`name`");
		resultSet  = statement.executeQuery();
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("INSERT INTO T_Vehicles_Series " +
				"(brandId,name,pinYin,logo," +
				"description,createId,createTime," +
				"lastUpdateTime,source,isDelete) " +
				" VALUES" +
				" ( ? , ? , ? , '' ," +
				" ?,1, '2016-09-10 15:37:41','2016-09-10 15:37:41','admin add',1)");
		while(resultSet.next()){
			String brandId = resultSet.getString(1);
			String name = resultSet.getString(2);
			String factoryText = resultSet.getString(3);
			statement.setString(1, brandId);
			statement.setString(2, factoryText);
			statement.setString(3, getPingYin(factoryText));
			statement.setString(4, name + " ~ " + factoryText);
			statement.addBatch();
		}
		statement.executeBatch();
		conn.setAutoCommit(true);
		closeConn(resultSet, statement, conn);
	}
	public static void insertT_Vehicles_Models() throws Exception{
		conn = getConn();
		statement = conn.prepareStatement("select b.id as seriesId,a.carmodelText,a.factoryText from xinghao a INNER join T_Vehicles_Series b on a.factoryText = b.`name` order by seriesId");
		resultSet  = statement.executeQuery();
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("INSERT INTO T_Vehicles_Models " +
				"(serieId,name,pinYin," +
				"description,createId,createTime," +
				"lastUpdateTime,source,isDelete) " +
				" VALUES" +
				" ( ? , ? , ? ," +
				" ?,1, '2016-09-10 15:37:41','2016-09-10 15:37:41','admin add',1)");
		while(resultSet.next()){
			String seriesId = resultSet.getString(1);
			String carmodelText = resultSet.getString(2);
			String factoryText = resultSet.getString(3);
			statement.setString(1, seriesId);
			statement.setString(2, carmodelText);
			statement.setString(3, getPingYin(carmodelText));
			statement.setString(4, factoryText + " ~ " + carmodelText);
			statement.addBatch();
		}
		statement.executeBatch();
		conn.setAutoCommit(true);
		closeConn(resultSet, statement, conn);
	}
	public static void insertT_Vehicles_Models_Versionname() throws Exception{
		conn = getConn();
		statement = conn.prepareStatement("select b.id as modelId,b.`name` ,a.AutoYears from AutoYears a inner join T_Vehicles_Models b on a.carmodelText = b.`name` inner join T_Vehicles_Series c on a.factoryText = c.`name` and b.serieId = c.id order by b.id");
		resultSet  = statement.executeQuery();
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("INSERT INTO T_Vehicles_Models_Versionname " +
				"(modelId,name,pinYin," +
				"description,createId,createTime," +
				"lastUpdateTime,source,isDelete) " +
				" VALUES" +
				" ( ? , ? , ? ," +
				" ?,1, '2016-09-10 15:37:41','2016-09-10 15:37:41','admin add',1)");
		while(resultSet.next()){
			String modelId = resultSet.getString(1);
			String name = resultSet.getString(2);
			String AutoYears = resultSet.getString(3);
			statement.setString(1, modelId);
			statement.setString(2, AutoYears);
			statement.setString(3, getPingYin(AutoYears));
			statement.setString(4, name + " ~ " + AutoYears);
			statement.addBatch();
		}
		statement.executeBatch();
		conn.setAutoCommit(true);
		closeConn(resultSet, statement, conn);
	}
	public static void insertT_Vehicles_Models_Versionname_Displacement() throws Exception{
		conn = getConn();
		statement = conn.prepareStatement("select b.id as versionNameId,c.id as displacementId  from AutoYearsDisplacement a INNER join T_Vehicles_Models_Versionname b on a.AutoYears = b.`name` inner join T_Vehicles_Displacement c on a.pailiang = c.displacement order by b.id");
		resultSet  = statement.executeQuery();
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("INSERT INTO T_Vehicles_Models_Versionname_Displacement " +
				"(versionNameId,displacementId,createId,createTime," +
				"lastUpdateTime,source,isDelete) " +
				" VALUES" +
				" ( ? , ? ," +
				" 1, '2016-09-10 15:37:41','2016-09-10 15:37:41','admin add',1)");
		while(resultSet.next()){
			String versionNameId = resultSet.getString(1);
			String displacementId = resultSet.getString(2);
			statement.setString(1, versionNameId);
			statement.setString(2, displacementId);
			statement.addBatch();
		}
		statement.executeBatch();
		conn.setAutoCommit(true);
		closeConn(resultSet, statement, conn);
	}
	public static void insertT_Vehicles_Versions() throws Exception{
		conn = getConn();
		statement = conn.prepareStatement("select b.id as versionNameId,a.YearData,a.YearName from  AutoYearsYearData a inner join T_Vehicles_Models_Versionname b on a.AutoYears = b.`name` ORDER BY b.id");
		resultSet  = statement.executeQuery();
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("INSERT INTO T_Vehicles_Versions " +
				"(versionNameId,year,description,createId,createTime," +
				"lastUpdateTime,source,isDelete) " +
				" VALUES" +
				" ( ? , ? ,?," +
				" 1, '2016-09-10 15:37:41','2016-09-10 15:37:41','admin add',1)");
		while(resultSet.next()){
			String versionNameId = resultSet.getString(1);
			String YearData = resultSet.getString(2);
			String YearName = resultSet.getString(3);
			statement.setString(1, versionNameId);
			statement.setString(2, YearData);
			statement.setString(3, YearName);
			statement.addBatch();
		}
		statement.executeBatch();
		conn.setAutoCommit(true);
		closeConn(resultSet, statement, conn);
	}
	
	
	public static void insertT_Vehicles_Displacement() throws Exception{
		conn = getConn();
		statement = conn.prepareStatement("select pailiang from AutoYearsDisplacement group by pailiang");
		resultSet  = statement.executeQuery();
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("INSERT INTO T_Vehicles_Displacement " +
				"(displacement,description,createId,createTime," +
				"lastUpdateTime,source,isDelete) " +
				" VALUES" +
				" ( ?  ," +
				"?,1, ?,?,'admin add',1)");
		while(resultSet.next()){
			String pailiang = resultSet.getString(1);
			if(!pailiang.equals("")){
				statement.setString(1, pailiang);
				statement.setString(2, "~ "+pailiang);
				statement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
				statement.setString(4,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
				statement.addBatch();
			}
			
		}
		statement.executeBatch();
		conn.setAutoCommit(true);
		closeConn(resultSet, statement, conn);
	}
	
	
	
	
	
	
	
	public static String getPingYin(String inputString) throws Exception {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		char[] input = inputString.trim().toCharArray();
		String output = "";
		for (int i = 0; i < input.length; i++) {
			if (java.lang.Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
				
				String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i],format);
				output += temp[0];
			} else
				output += java.lang.Character.toString(input[i]);
		}
		return output.toUpperCase();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			insertT_Vehicles_Versions();
//			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
			System.out.println(getPingYin("北汽新能源"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

}
