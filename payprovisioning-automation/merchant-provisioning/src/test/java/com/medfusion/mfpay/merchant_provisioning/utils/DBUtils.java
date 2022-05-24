// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.utils;

import com.intuit.ifs.csscat.core.utils.DatabaseConnection;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DBUtils {

    public static String executeQueryOnDB(String strDBName, String sqlQuery) throws Throwable {
        Connection connection = null;
        PropertyFileLoader testData = new PropertyFileLoader();

        if(strDBName.equalsIgnoreCase("rcm")){

            connection = DatabaseConnection.makeDBConnection("PostGRESQLServer",
                    testData.getProperty("postgres.sqlserver.dbhostname"), testData.getProperty("postgres.sqlserver.dbname.rcm"),
                    testData.getProperty("postgres.sqlserver.dbusername.rcm"), testData.getProperty("postgres.sqlserver.dbpassword"));
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
                    strResultValue = rs.getObject(1).toString();
            }
            Log4jUtil.log("Query value is " + strResultValue);
        }

        if (connection != null)
            DatabaseConnection.closeDBConnection(connection);
        return strResultValue;
    }
    
	public static Object executeQueryOnDBGetResult(String strDBName, String sqlQuery, String columnName)
			throws Throwable {
		Connection connection = null;
		PropertyFileLoader testData = new PropertyFileLoader();

		if (strDBName.equalsIgnoreCase("rcm")) {

			connection = DatabaseConnection.makeDBConnection("PostGRESQLServer",
					testData.getProperty("postgres.sqlserver.dbhostname"),
					testData.getProperty("postgres.sqlserver.dbname.rcm"),
					testData.getProperty("postgres.sqlserver.dbusername.rcm"),
					testData.getProperty("postgres.sqlserver.dbpassword"));
		}
		ResultSet rs = null;
		try {
			rs = DatabaseConnection.statementExecution(connection, sqlQuery);
		} catch (NullPointerException e) {
			Log4jUtil.log(e.getMessage());
			Log4jUtil.log("Warning !! Result set is null");
		}

		Object strResultValue = "";
		if (rs != null) {
			while (rs.next()) {
				ResultSetMetaData metadata = rs.getMetaData();
				int columnCount = metadata.getColumnCount();

				for (int i = 1; i <= columnCount; i++)

					strResultValue = rs.getObject(columnName);

				System.out.println(strResultValue);
			}
			Log4jUtil.log("Query value is " + strResultValue);
		}

		if (connection != null)
			DatabaseConnection.closeDBConnection(connection);
		return strResultValue;
	}
}
