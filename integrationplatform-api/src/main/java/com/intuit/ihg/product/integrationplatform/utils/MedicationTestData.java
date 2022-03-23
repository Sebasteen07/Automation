package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class MedicationTestData {

	private Medication MedicationObj = null;
	private ExcelSheetReader excelReader = null;

	public MedicationTestData(Medication aptData) throws Exception {
		// which environment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		MedicationObj = (Medication) excelReader.getSingleExcelRow(aptData, temp);
	}

	public String getUrl() {
		return MedicationObj.Url;
	}

	public String getUserName() {
		return MedicationObj.UserName;
	}

	public String getPassword() {
		return MedicationObj.Password;
	}

	public String getRestUrl() {
		return MedicationObj.RestUrl;
	}

	public String getResponsePath() {
		return MedicationObj.ResponsePath;
	}

	public String getFrom() {
		return MedicationObj.From;
	}

	public String getMedicationPath() {
		return MedicationObj.MedicationPath;
	}

	public String getOAuthProperty() {
		return MedicationObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return MedicationObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return MedicationObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return MedicationObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return MedicationObj.OAuthPassword;
	}

	public String getPreferredDoctor() {
		return MedicationObj.PreferredDoctor;
	}

	public String getPhoneNumber() {
		return MedicationObj.PhoneNumber;
	}

	public String getPracticeURL() {
		return MedicationObj.PracticeURL;
	}

	public String getPracticeUserName() {
		return MedicationObj.PracticeUserName;
	}

	public String getPracticePassword() {
		return MedicationObj.PracticePassword;
	}

	public String getGmailUserName() {
		return MedicationObj.GmailUserName;
	}

	public String getGmailPassword() {
		return MedicationObj.GmailPassword;
	}

	public String getPracticeName() {
		return MedicationObj.PracticeName;
	}

	public String getFirstName() {
		return MedicationObj.FirstName;
	}

	public String getLastName() {
		return MedicationObj.LastName;
	}

	public String getMFPatientID() {
		return MedicationObj.MFPatientID;
	}

	public String getRemoveMedicationRestUrl() {
		return MedicationObj.RemoveMedicationRestUrl;
	}

}
