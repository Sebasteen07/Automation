package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class PrescriptionTestData {

	private Prescription PrescriptionObj = null;
	private ExcelSheetReader excelReader = null;

	public PrescriptionTestData(Prescription aptData) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file

		PrescriptionObj = (Prescription) excelReader.getSingleExcelRow(aptData, temp);
	}

	public String getUrl() {
		return PrescriptionObj.Url;
	}

	public String getUserName() {
		return PrescriptionObj.UserName;
	}

	public String getPassword() {
		return PrescriptionObj.Password;
	}

	public String getRestUrl() {
		return PrescriptionObj.RestUrl;
	}

	public String getResponsePath() {
		return PrescriptionObj.ResponsePath;
	}

	public String getFrom() {
		return PrescriptionObj.From;
	}

	public String getPrescriptionPath() {
		return PrescriptionObj.PrescriptionPath;
	}

	public String getOAuthProperty() {
		return PrescriptionObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return PrescriptionObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return PrescriptionObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return PrescriptionObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return PrescriptionObj.OAuthPassword;
	}

	public String getPreferredDoctor() {
		return PrescriptionObj.PreferredDoctor;
	}

	public String getPhoneNumber() {
		return PrescriptionObj.PhoneNumber;
	}

	public String getPracticeURL() {
		return PrescriptionObj.PracticeURL;
	}

	public String getPracticeUserName() {
		return PrescriptionObj.PracticeUserName;
	}

	public String getPracticePassword() {
		return PrescriptionObj.PracticePassword;
	}

	public String getGmailUserName() {
		return PrescriptionObj.GmailUserName;
	}

	public String getGmailPassword() {
		return PrescriptionObj.GmailPassword;
	}

	public String getPracticeName() {
		return PrescriptionObj.PracticeName;
	}
	public String getRestV3Url() {
		return PrescriptionObj.RestV3Url;
	}
	
	public String getPrescriptionPathV3() {
		return PrescriptionObj.PrescriptionPathV3;
	}
	
	public String getPharmacyName() {
		return PrescriptionObj.PharmacyName;
	}

}
