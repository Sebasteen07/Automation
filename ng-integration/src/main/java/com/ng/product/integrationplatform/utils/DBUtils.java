// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.utils;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.intuit.ifs.csscat.core.utils.DatabaseConnection;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class DBUtils {

	@SuppressWarnings("resource")
	public static String executeQueryOnDB(String strDBName, String sqlQuery) throws Throwable {
		Connection connection = null;
		PropertyFileLoader PropertyLoaderObj = new PropertyFileLoader();
		if (strDBName.equalsIgnoreCase("NGCoreDB")) {

			String dbHostName = PropertyLoaderObj.getCoreMSSQLdbHostName();
			String dbName = PropertyLoaderObj.getCoreMSSQLdbName();
			String dbUserName = PropertyLoaderObj.getCoreMSSQLdbUserName();
			String dbPassword = PropertyLoaderObj.getCoreMSSQLdbPassword();

			connection = DatabaseConnection.makeDBConnection("SQLServer", dbHostName, dbName, dbUserName, dbPassword);
		} else if (strDBName.equalsIgnoreCase("MFAgentDB")) {

			String dbHostName = PropertyLoaderObj.getMFMSSQLdbHostName();
			String dbName = PropertyLoaderObj.getMFMSSQLdbName();
			String dbUserName = PropertyLoaderObj.getMFMSSQLdbUserName();
			String dbPassword = PropertyLoaderObj.getMFMSSQLdbPassword();

			connection = DatabaseConnection.makeDBConnection("SQLServer", dbHostName, dbName, dbUserName, dbPassword);
		} else if (strDBName.equalsIgnoreCase("PostGredgeMFPortalDB")) {

			String dbHostName = PropertyLoaderObj.getPostGREdbHostName();
			String dbName = PropertyLoaderObj.getPostGREdbName();
			String dbUserName = PropertyLoaderObj.getPostGREdbUserName();
			String dbPassword = PropertyLoaderObj.getPostGREdbPassword();

			connection = DatabaseConnection.makeDBConnection("PostGRESQLServer", dbHostName, dbName, dbUserName,
					dbPassword);
		} else if (strDBName.equalsIgnoreCase("ConsumerMFPortalDB")) {

			String dbHostName = PropertyLoaderObj.getMFOracleSQLdbHostName();
			String dbName = PropertyLoaderObj.getMFOracleSQLdbName();
			String dbUserName = PropertyLoaderObj.getMFOracleSQLdbUserName();
			String dbPassword = PropertyLoaderObj.getMFOracleSQLdbPassword();

			connection = DatabaseConnection.makeDBConnection("OracleSQLServer", dbHostName, dbName, dbUserName,
					dbPassword);
		}

		ResultSet rs = null;
		try {
			rs = DatabaseConnection.statementExecution(connection, sqlQuery);
		} catch (NullPointerException e) {
			Log4jUtil.log(e.getMessage());
			Log4jUtil.log("Warning !! Result set is null");
		}
		String strResultValue = "";
		if (rs != null) {
			while (rs.next()) {
				ResultSetMetaData metadata = rs.getMetaData();
				int columnCount = metadata.getColumnCount();
				for (int i = 1; i <= columnCount; i++)
//					System.out.println(metadata.getColumnLabel(i)+"-------"+ rs.getObject(i));
					strResultValue = rs.getObject(1).toString();
			}
			Log4jUtil.log("Query value is " + strResultValue);
		}

		if (connection != null)
			DatabaseConnection.closeDBConnection(connection);
		return strResultValue;
	}
}