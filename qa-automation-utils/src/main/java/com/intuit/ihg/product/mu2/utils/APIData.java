package com.intuit.ihg.product.mu2.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class APIData {
	
	APITestData apiobj = null;
	ExcelSheetReader excelReader = null;

	/**
	 * 
	 * @param sitegen
	 * @throws Exception
	 */
	public APIData(APITestData getData) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		apiobj = (APITestData)excelReader.getSingleExcelRow(getData, temp);
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
	 *  Returns Patient2 last name
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
	

	

}
