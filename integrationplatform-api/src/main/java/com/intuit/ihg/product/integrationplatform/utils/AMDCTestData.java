package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class AMDCTestData {

	private AMDC AmdcObj = null;
	private ExcelSheetReader excelReader = null;

	public AMDCTestData(AMDC sheetName) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		AmdcObj = (AMDC) excelReader.getSingleExcelRow(sheetName, temp);
	}

	public String getUrl() {
		return AmdcObj.Url;
	}

	public String getUserName() {
		return AmdcObj.UserName;
	}

	public String getPassword() {
		return AmdcObj.Password;
	}

	public String getRestUrl() {
		return AmdcObj.RestUrl;
	}

	public String getResponsePath() {
		return AmdcObj.ResponsePath;
	}

	public String getFrom() {
		return AmdcObj.From;
	}

	public String getSecureMessagePath() {
		return AmdcObj.SecureMessagePath;
	}



	public String getOAuthProperty() {
		return AmdcObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return AmdcObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return AmdcObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return AmdcObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return AmdcObj.OAuthPassword;
	}

	public String getReadCommunicationURL() {
		return AmdcObj.ReadCommuniationURL;
	}

	public String getSecureMessage_AskaStaffXML() {
		return AmdcObj.SecureMessage_AskaStaffXML;
	}

	public String getBatch_SecureMessage() {
		return AmdcObj.Batch_SecureMessage;
	}

	public String getUserName1() {
		return AmdcObj.UserName1;
	}

	public String getFrom1() {
		return AmdcObj.From1;
	}

	public String getUserName2() {
		return AmdcObj.UserName2;
	}

	public String getSender1() {
		return AmdcObj.Sender1;
	}

	public String getSender2() {
		return AmdcObj.Sender2;
	}

	public String getSender3() {
		return AmdcObj.Sender3;
	}

	public String getIntegrationPracticeID() {
		return AmdcObj.IntegrationPracticeID;
	}

	public String getPatientName1() {
		return AmdcObj.PatientName1;
	}

	public String getPatientName2() {
		return AmdcObj.PatientName2;
	}

	public String getPatientName3() {
		return AmdcObj.PatientName3;
	}

	public String getGmailUserName() {
		return AmdcObj.GmailUserName;
	}

	public String getGmailPassword() {
		return AmdcObj.GmailPassword;
	}

	public String getRestV3Url() {
		return AmdcObj.RestV3Url;
	}
}
