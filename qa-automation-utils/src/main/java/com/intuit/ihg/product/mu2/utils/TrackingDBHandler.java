//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.mu2.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.jayway.awaitility.Awaitility;

@SuppressWarnings("unused")
public class TrackingDBHandler extends Awaitility {

	public static Connection dbConnPs = null;
	public static String getLatestSubjectID = "select * from (select * from msg where msg_type = ? and processing_status_type = ? and subject_id like ? order by CREATED_TS desc) where rownum <=3";

	public static Connection getConnectionOracle(String dbHost, String dbName, String dbUser, String dbPassword,
			String dbSID) {

		try {
			if (dbConnPs == null || dbConnPs.isClosed()) {
				// Load the JDBC driver
				String driverName = "oracle.jdbc.driver.OracleDriver";

				// Create a connection to the database
				String url = "jdbc:oracle:thin:@" + dbHost + ":" + MU2Constants.DBPORT + ":" + dbSID;
				Class.forName(driverName);
				dbConnPs = DriverManager.getConnection(url, dbUser, dbPassword);
				Log4jUtil.log("connectedto DB :" + dbConnPs.toString());
			}
		} catch (ClassNotFoundException cnfe) {
		} catch (SQLException se) {
		}
		return dbConnPs;
	}

	public static void establishConnection(String dbHost, String dbName, String dbUser, String dbPassword,
			String dbSID) {
		dbConnPs = getConnectionOracle(dbHost, dbName, dbUser, dbPassword, dbSID);
	}

	public static void closeConnection() throws SQLException {
		if (dbConnPs != null && !dbConnPs.isClosed())
			dbConnPs.close();
	}

	public static String getLatetSubjectIDFromDB(String msgType, String processingType, String pushEventPracticeid) {
		boolean status = false;
		String subject = null;

		try {
			PreparedStatement stmt = dbConnPs.prepareStatement(getLatestSubjectID);
			Log4jUtil.log("DB Query" + getLatestSubjectID);

			stmt.setString(1, msgType);
			stmt.setString(2, processingType);
			stmt.setString(3, pushEventPracticeid);
			String subjectId = null;

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("PROCESSING_STATUS_TYPE").equalsIgnoreCase("ERROR")) {
					Log4jUtil.log("SUBJECTID--> " + rs.getString("SUBJECT_ID") + "Time Stamp -->"
							+ rs.getString("PROCESSING_START_DT"));
					subjectId = rs.getString("SUBJECT_ID");
					subject = subjectId.split(":")[3];
					Log4jUtil.log("SUBJECT ID ---->" + subject);

				}

			}
			stmt.close();
		} catch (SQLException se) {
			Log4jUtil.log(se.getMessage());
		}
		Log4jUtil.log("SUBJECT ID returned" + subject);
		return subject;
	}

}
