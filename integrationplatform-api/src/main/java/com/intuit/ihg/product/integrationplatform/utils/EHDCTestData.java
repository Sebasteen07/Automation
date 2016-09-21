package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class EHDCTestData {

	private EHDC EhdcObj = null;

	public EHDCTestData(EHDC sheetName) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		ExcelSheetReader excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		EhdcObj = (EHDC) excelReader.getSingleExcelRow(sheetName, temp);
	}


	public String getPHR_URL() {
		return EhdcObj.PHR_URL;
	}

	public String getURL() {
		return EhdcObj.URL;
	}

	public String getUserName() {
		return EhdcObj.UserName;
	}

	public String getPassword() {
		return EhdcObj.Password;
	}


	public String getRestUrl() {
		return EhdcObj.RestUrl;
	}

	public String getCCDPath() {
		return EhdcObj.CCDPath;
	}

	public String getResponsePath() {
		return EhdcObj.ResponsePath;
	}


	public String getOAuthProperty() {
		return EhdcObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return EhdcObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return EhdcObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return EhdcObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return EhdcObj.OAuthPassword;
	}

	public String getGmailUserName() {
		return EhdcObj.GmailUserName;
	}

	public String getGmailPassword() {
		return EhdcObj.GmailPassword;
	}

	public String getPracticeName() {
		return EhdcObj.PracticeName;
	}

}
