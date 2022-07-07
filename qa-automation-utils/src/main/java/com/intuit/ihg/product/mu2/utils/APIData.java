//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.mu2.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class APIData {

	APITestData apiobj = null;
	ExcelSheetReader excelReader = null;

	public APIData(APITestData getData) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		apiobj = (APITestData) excelReader.getSingleExcelRow(getData, temp);
	}


	/**
	 * Returns part of Pull API URL
	 */
	public String getPullAPIURL() {

		return apiobj.PULLAPI_URL;
	}

	/**
	 * Returns Oauth properties file path
	 */

	public String getOauthProperty() {
		return apiobj.OAUTH_PROPERTY;
	}

	/**
	 * Returns xml path where Pull API response written
	 */
	public String getResponsePath() {
		return apiobj.RESPONSEPATH;
	}

	/**
	 * Returns Oauth keystore path
	 */
	public String getOauthKeyStore() {
		return apiobj.OAUTH_KEYSTORE;
	}

	/**
	 * Returns Pull API host
	 */
	public String getHost() {
		return apiobj.HOST;
	}

	/**
	 * Returns Oauth App token
	 */

	public String getOauthAppToken() {
		return apiobj.OAUTH_APPTOKEN;
	}

	/**
	 * Returns Oauth user name
	 */
	public String getOauthUsername() {
		return apiobj.OAUTH_USERNAME;
	}

	/**
	 * Returns Oauth user password
	 */
	public String getOauthPassword() {
		return apiobj.OAUTH_PASSWORD;
	}

	/**
	 * Returns Push API URL
	 */
	public String getPushAPIURL() {
		return apiobj.PUSHAPI_URL;
	}

	/**
	 * Returns Patient1 first name
	 */
	public String getPatient1FN() {
		return apiobj.PATIENT1_FN;
	}

	/**
	 * Returns Patient1 last name
	 */
	public String getPatient1LN() {
		return apiobj.PATIENT1_LN;
	}

	/**
	 * Returns Patient2 first name
	 */
	public String getPatient2FN() {
		return apiobj.PATIENT2_FN;
	}

	/**
	 * Returns Patient2 last name
	 */
	public String getPatient2LN() {
		return apiobj.PATIENT2_LN;
	}

	/**
	 * Returns Push API response xml path
	 */
	public String getPushResponsePath() {
		return apiobj.PUSH_RESPONSEPATH;
	}

	/**
	 * Returns Push API host
	 */
	public String getPushHost() {
		return apiobj.PUSH_HOST;
	}

	/**
	 * Returns Push API Practice subject ID
	 */
	public String getPushSubjectID() {
		return apiobj.PUSH_SUBJECTID;
	}

	/**
	 * Returns Push API Practice ID
	 */
	public String getPushPracticeID() {
		return apiobj.PUSH_PRACTICEID;
	}

	/**
	 * Returns Push API expected XML path
	 */
	public String getPushExpectedXMLPath() {
		return apiobj.PUSH_EXPECTEDXML_PATH;
	}

	/**
	 * Returns DB_HOST
	 */
	public String getDBHost() {
		return apiobj.DB_HOST;
	}


	/**
	 * Returns DB_SID
	 */
	public String getDBSID() {
		return apiobj.DB_SID;
	}

	/**
	 * Returns DB_NAME
	 */
	public String getDBName() {
		return apiobj.DB_NAME;
	}

	/**
	 * Returns DB_USER
	 */
	public String getDBUser() {
		return apiobj.DB_USER;
	}

	/**
	 * Returns DB_PASSWORD
	 */
	public String getDBPassword() {
		return apiobj.DB_PASSWORD;
	}

	public String getCCDPath() {
		// TODO Auto-generated method stub
		return apiobj.CCD_PATH;
	}

	public String getPortalURL() {
		// TODO Auto-generated method stub
		return apiobj.PORTAL_URL;
	}

	public String getPortalUserName() {
		// TODO Auto-generated method stub
		return apiobj.PORTAL_USERNAME;
	}

	public String getPortalPassword() {
		// TODO Auto-generated method stub
		return apiobj.PORTAL_PASSWORD;
	}

	public String getIntuitPatientID() {
		// TODO Auto-generated method stub
		return apiobj.INTUIT_PATIENT_ID;
	}

	public String getRestUrl() {
		// TODO Auto-generated method stub
		return apiobj.REST_URL;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return apiobj.TRANSMIT_EMAIL;
	}

	public String getImagePath() {
		// TODO Auto-generated method stub
		return apiobj.IMAGE_PATH;
	}

	public String getPortalUserName2() {
		// TODO Auto-generated method stub
		return apiobj.PORTAL_USERNAME2;
	}

	public String getPatientID() {
		// TODO Auto-generated method stub
		return apiobj.PATIENT_ID;
	}

}
