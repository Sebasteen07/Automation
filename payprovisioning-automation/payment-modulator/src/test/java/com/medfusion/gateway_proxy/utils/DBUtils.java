// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.utils;

import com.medfusion.common.utils.PropertyFileLoader;
import com.intuit.ifs.csscat.core.utils.*;
import org.apache.commons.lang.StringUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class DBUtils {

    public static String executeQueryOnDB(String strDBName, String sqlQuery) throws Throwable {
        Connection connection = null;
        PropertyFileLoader testData = new PropertyFileLoader();

        if (strDBName.equalsIgnoreCase("pay_walt")) {
            //For some reason it is adding a semicolon at the end of value we get from property file so chopping it
            String dbHostName = StringUtils.chop(testData.getProperty("postgres.sqlserver.dbhostname"));
            String dbName = StringUtils.chop(testData.getProperty("postgres.sqlserver.dbname"));
            String dbUserName = StringUtils.chop(testData.getProperty("postgres.sqlserver.dbusername"));
            String dbPassword = StringUtils.chop(testData.getProperty("postgres.sqlserver.dbpassword"));

            connection = DatabaseConnection.makeDBConnection("PostGRESQLServer", dbHostName, dbName, dbUserName, dbPassword);
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
}