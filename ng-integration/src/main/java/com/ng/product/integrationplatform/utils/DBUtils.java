package com.ng.product.integrationplatform.utils;

import java.sql.Connection;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.DatabaseConnection;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.medfusion.common.utils.IHGUtil;

public class DBUtils {	
	
	@SuppressWarnings("resource")
	public static String executeQueryOnDB(String strDBName, String sqlQuery) throws Throwable {
//		System.out.println("On "+strDBName+" Execute Query "+sqlQuery);
		String temp = IHGUtil.getEnvironmentType().toString();
    	String env = TestConfig.getUserDefinedProperty("test.environment");
		 Connection connection = null;
		 PropertyFileLoader PropertyLoaderObj = new PropertyFileLoader();
		 if(strDBName.equalsIgnoreCase("NGCoreDB")){
			 
//		    	System.out.print("\n dbHostName "+ PropertyLoaderObj.getCoreMSSQLdbHostName());
//		    	System.out.print("\n dbName "+PropertyLoaderObj.getCoreMSSQLdbName());
//		    	System.out.print("\n dbUserName "+PropertyLoaderObj.getCoreMSSQLdbUserName());
//		    	System.out.print("\n dbPassword "+PropertyLoaderObj.getCoreMSSQLdbPassword());
		    	
		    	String dbHostName = PropertyLoaderObj.getCoreMSSQLdbHostName();
		    	String dbName =PropertyLoaderObj.getCoreMSSQLdbName();
		    	String dbUserName = PropertyLoaderObj.getCoreMSSQLdbUserName();
		    	String dbPassword = PropertyLoaderObj.getCoreMSSQLdbPassword();
			 
			 connection= DatabaseConnection.makeDBConnection("SQLServer",dbHostName, dbName, dbUserName, dbPassword);
			 }
		 else if(strDBName.equalsIgnoreCase("MFAgentDB")){
			     	
		    	
		    	String dbHostName = PropertyLoaderObj.getMFMSSQLdbHostName();
		    	String dbName =PropertyLoaderObj.getMFMSSQLdbName();
		    	String dbUserName = PropertyLoaderObj.getMFMSSQLdbUserName();
		    	String dbPassword = PropertyLoaderObj.getMFMSSQLdbPassword();
		    	
//		    	System.out.print("\n dbHostName "+ dbHostName);
//		    	System.out.print("\n dbName "+ dbName);
//		    	System.out.print("\n dbUserName "+ dbUserName);
//		    	System.out.print("\n dbPassword "+ dbPassword);
			 
		     connection= DatabaseConnection.makeDBConnection("SQLServer",dbHostName, dbName, dbUserName, dbPassword);
		     }
		 else if (strDBName.equalsIgnoreCase("PostGredgeMFPortalDB")){
//		    	System.out.print("\n dbHostName "+ PropertyLoaderObj.getPostGREdbHostName());
//		    	System.out.print("\n dbName "+PropertyLoaderObj.getPostGREdbName());
//		    	System.out.print("\n dbUserName "+PropertyLoaderObj.getPostGREdbUserName());
//		    	System.out.print("\n dbPassword "+PropertyLoaderObj.getPostGREdbPassword());
		    	
		    	String dbHostName = PropertyLoaderObj.getPostGREdbHostName();
		    	String dbName =PropertyLoaderObj.getPostGREdbName();
		    	String dbUserName = PropertyLoaderObj.getPostGREdbUserName();
		    	String dbPassword = PropertyLoaderObj.getPostGREdbPassword();

		    	connection= DatabaseConnection.makeDBConnection("PostGRESQLServer",dbHostName, dbName, dbUserName, dbPassword);
		 }
		 else if (strDBName.equalsIgnoreCase("ConsumerMFPortalDB")){
		  	    	
		    	String dbHostName = PropertyLoaderObj.getMFOracleSQLdbHostName();
		    	String dbName =PropertyLoaderObj.getMFOracleSQLdbName();
		    	String dbUserName = PropertyLoaderObj.getMFOracleSQLdbUserName();
		    	String dbPassword = PropertyLoaderObj.getMFOracleSQLdbPassword();

//		    	System.out.print("\n dbHostName "+ dbHostName);
//		    	System.out.print("\n dbName "+ dbName);
//		    	System.out.print("\n dbUserName "+ dbUserName);
//		    	System.out.print("\n dbPassword "+ dbPassword);
		    	
		    	connection= DatabaseConnection.makeDBConnection("OracleSQLServer",dbHostName, dbName, dbUserName, dbPassword);
		 }
		 
	    ResultSet rs= DatabaseConnection.statementExecution(connection, sqlQuery);
	    String strResultValue ="";
			while (rs.next())
			{
				ResultSetMetaData metadata = rs.getMetaData();
				int columnCount = metadata.getColumnCount();
				for (int i = 1; i <= columnCount; i++)
//					System.out.println(metadata.getColumnLabel(i)+"-------"+ rs.getObject(i));
				strResultValue = rs.getObject(1).toString();
			}
                System.out.println("ColumnValue is "+strResultValue);
                
            
        if (connection != null)
            DatabaseConnection.closeDBConnection(connection);
        return strResultValue;
    }
}