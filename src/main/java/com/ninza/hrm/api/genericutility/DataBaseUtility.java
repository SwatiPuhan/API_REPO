package com.ninza.hrm.api.genericutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class DataBaseUtility {

	FileUtility fLib = new FileUtility();

	 Connection conn ;
	 ResultSet result;

	public void getDbConnection() throws Throwable {
		try {
			Driver dr = new Driver();
			DriverManager.registerDriver(dr);
			String dbUrl = fLib.getDataFromPropertiesFile("DBURL");
			String username = fLib.getDataFromPropertiesFile("DB_UN");
			String password = fLib.getDataFromPropertiesFile("DB_PWD");

			conn = DriverManager.getConnection(dbUrl, username, password);
		} catch (Exception e) {

		}

	}

	public ResultSet executeQuery(String query) {

		try {
			Statement stat = conn.createStatement();

			result = stat.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return result;
	}

	public void executeUpdateQuery() {

		try {

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public boolean executeQueryVerifyAndGetData(String query, int columnName, String expectedData)
			throws SQLException {
		boolean flag = false;
		result = conn.createStatement().executeQuery(query);

		while (result.next()) {
			if (result.getString(columnName).equals(expectedData)) {
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(expectedData + "is verified in database table");
			return true;
		} else {
			System.out.println(expectedData + "is not verified in database table");
			return false;
		}
	}

	public void closeDbConnection() throws SQLException {
		try {
			conn.close();
		} catch (Exception e) {

		}

	}
}