package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class StatementPreferenceTestData {

	private StatementPreference StmtPrefObj = null;
	private ExcelSheetReader excelReader = null;

	public StatementPreferenceTestData(StatementPreference sheetName) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		StmtPrefObj = (StatementPreference) excelReader.getSingleExcelRow(sheetName, temp);
	}

	public String getUrl() {
		return StmtPrefObj.Url;
	}

	public String getUserName() {
		return StmtPrefObj.UserName;
	}

	public String getPassword() {
		return StmtPrefObj.Password;
	}

	public String getRestUrl() {
		return StmtPrefObj.RestUrl;
	}

	public String getStatementPath() {
		return StmtPrefObj.StatementPath;
	}

	public String getResponsePath() {
		return StmtPrefObj.ResponsePath;
	}

	public String getOAuthProperty() {
		return StmtPrefObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return StmtPrefObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return StmtPrefObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return StmtPrefObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return StmtPrefObj.OAuthPassword;
	}

	public String getPracticeURL() {
		return StmtPrefObj.PracticeURL;
	}

	public String getPracticeUserName() {
		return StmtPrefObj.PracticeUserName;
	}

	public String getPracticePassword() {
		return StmtPrefObj.PracticePassword;
	}

	public String getFirstName() {
		return StmtPrefObj.FirstName;
	}

	public String getLastName() {
		return StmtPrefObj.LastName;
	}
	
	public String getRestUrlV3() {
		return StmtPrefObj.RestUrlV3;
	}

	public String getStatementPathV3() {
		return StmtPrefObj.StatementPathV3;
	}
}
