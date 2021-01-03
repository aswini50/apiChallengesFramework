package com.api.automation;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.google.gson.Gson;

public class ApiChallengerUtil {

	public static Properties props;
	String propertiesFile = "config.properties";
	static Connection conn = null;

	public  void loadProperties() {
		try {
			props = new Properties();
			InputStreamReader input = new InputStreamReader(
					getClass().getClassLoader().getResourceAsStream(propertiesFile));
			if (input != null)
				props.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void openConnection() {
		try {
			String url = "jdbc:sqlite:" + System.getProperty("user.dir") + props.getProperty("database.path");
			conn = DriverManager.getConnection(url);
			System.out.println("\t Connection to SQLite has been established.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static int getRecordSetCount(String tableName) {
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		String query = ApiChallengerUtil.props.getProperty("resultset.count.query");
		query = query.replace("{tablename}", tableName);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			count = rs.getInt("rowcount");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public static String resultSetToJson(String query) {
		List<Map<String, Object>> listOfMaps = null;
		try {
			QueryRunner queryRunner = new QueryRunner();
			listOfMaps = queryRunner.query(conn, query, new MapListHandler());
		} catch (SQLException se) {
			throw new RuntimeException("\t Couldn't query the database.", se);
		}
		return new Gson().toJson(listOfMaps);
	}

	public static void closeConnection() throws SQLException {
		if (conn != null) {
			DbUtils.closeQuietly(conn);
		}
	}
}
