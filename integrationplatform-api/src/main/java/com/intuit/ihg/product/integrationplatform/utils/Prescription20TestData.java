package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class Prescription20TestData {

	private Prescription20 Prescription20Obj = null;
	private ExcelSheetReader excelReader = null;

	public Prescription20TestData(Prescription20 aptData) throws Exception {
		// which environment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file

		Prescription20Obj = (Prescription20) excelReader.getSingleExcelRow(aptData, temp);
	}

	public String getUrl() {
		return Prescription20Obj.Url;
	}

	public String getUserName() {
		return Prescription20Obj.UserName;
	}

	public String getPassword() {
		return Prescription20Obj.Password;
	}

	public String getRestUrl() {
		return Prescription20Obj.RestUrl;
	}

	public String getResponsePath() {
		return Prescription20Obj.ResponsePath;
	}

	public String getFrom() {
		return Prescription20Obj.From;
	}

	public String getPrescriptionPath() {
		return Prescription20Obj.PrescriptionPath;
	}

	public String getOAuthProperty() {
		return Prescription20Obj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return Prescription20Obj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return Prescription20Obj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return Prescription20Obj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return Prescription20Obj.OAuthPassword;
	}

	public String getPreferredDoctor() {
		return Prescription20Obj.PreferredDoctor;
	}

	public String getPhoneNumber() {
		return Prescription20Obj.PhoneNumber;
	}

	public String getPracticeURL() {
		return Prescription20Obj.PracticeURL;
	}

	public String getPracticeUserName() {
		return Prescription20Obj.PracticeUserName;
	}

	public String getPracticePassword() {
		return Prescription20Obj.PracticePassword;
	}

	public String getGmailUserName() {
		return Prescription20Obj.GmailUserName;
	}

	public String getGmailPassword() {
		return Prescription20Obj.GmailPassword;
	}

	public String getPracticeName() {
		return Prescription20Obj.PracticeName;
	}
	public String getRestV3Url() {
		return Prescription20Obj.RestV3Url;
	}
	
	public String getPrescriptionPathV3() {
		return Prescription20Obj.PrescriptionPathV3;
	}
	
	public String getPharmacyName() {
		return Prescription20Obj.PharmacyName;
	}

}
