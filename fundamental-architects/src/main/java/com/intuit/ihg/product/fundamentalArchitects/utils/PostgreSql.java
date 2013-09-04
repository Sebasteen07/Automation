package com.intuit.ihg.product.fundamentalArchitects.utils;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class PostgreSql {

	public static boolean main(String sUrl, String sUser, String sPassword, String sQuery) throws Exception {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		String url = sUrl;
		String user = sUser;
		String password = sPassword;

		connection = DriverManager.getConnection(url, user, password);
		statement = connection.prepareStatement(sQuery);
		result = statement.executeQuery();

		if (result.next()) {
			System.out.println(result.getString(1));
		}
		 
		return result.first(); 
	}
}
