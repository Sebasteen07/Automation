package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class PIDCTestData {

	private PIDC PidcObj = null;
	private ExcelSheetReader excelReader = null;

	public PIDCTestData(PIDC sheetName) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		PidcObj = (PIDC) excelReader.getSingleExcelRow(sheetName, temp);
	}

	public String getUrl() {
		return PidcObj.Url;
	}

	public String getUserName() {
		return PidcObj.UserName;
	}

	public String getPassword() {
		return PidcObj.Password;
	}

	public String getRestUrl() {
		return PidcObj.RestUrl;
	}

	public String getPatientPath() {
		return PidcObj.PatientPath;
	}

	public String getResponsePath() {
		return PidcObj.ResponsePath;
	}

	public String getOAuthProperty() {
		return PidcObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return PidcObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return PidcObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return PidcObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return PidcObj.OAuthPassword;
	}

	public String getGmailUsername() {
		return PidcObj.GmailUsername;
	}

	public String getGmailPassword() {
		return PidcObj.GmailPassword;
	}

	public String getBirthDay() {
		return PidcObj.BirthDay;
	}

	public String getZipCode() {
		return PidcObj.ZipCode;
	}

	public String getSSN() {
		return PidcObj.SSN;
	}

	public String getEmail() {
		return PidcObj.Email;
	}

	public String getPatientPassword() {
		return PidcObj.PatientPassword;
	}

	public String getSecretQuestion() {
		return PidcObj.SecretQuestion;
	}

	public String getSecretAnswer() {
		return PidcObj.SecretAnswer;
	}

	public String getPracticeURL() {
		return PidcObj.PracticeURL;
	}

	public String getPracticeUserName() {
		return PidcObj.PracticeUserName;
	}

	public String getPracticePassword() {
		return PidcObj.PracticePassword;
	}

	public String getLastName() {
		return PidcObj.LastName;
	}

	public String getCity() {
		return PidcObj.City;
	}

	public String getState() {
		return PidcObj.State;
	}

	public String getAddress1() {
		return PidcObj.Address1;
	}

	public String getAddress2() {
		return PidcObj.Address2;
	}

	public String getHomePhoneNo() {
		return PidcObj.HomePhoneNo;
	}

	public String getInsurance_Type() {
		return PidcObj.Insurance_Type;
	}

	public String getInsurance_Name() {
		return PidcObj.Insurance_Name;
	}

	public String getRelation() {
		return PidcObj.Relation;
	}

	public String getPreferredLanguage() {
		return PidcObj.PreferredLanguage;
	}

	public String getRace() {
		return PidcObj.Race;
	}

	public String getEthnicity() {
		return PidcObj.Ethnicity;
	}

	public String getMaritalStatus() {
		return PidcObj.MaritalStatus;
	}

	public String getChooseCommunication() {
		return PidcObj.ChooseCommunication;
	}

	public String getBatch_PatientPath() {
		return PidcObj.Batch_PatientPath;
	}

	public String getPortalURL() {
		return PidcObj.PortalURL;
	}

	public String getPortalRestUrl() {
		return PidcObj.PortalRestUrl;
	}

	public String getHealthKeyPatientUserName() {
		return PidcObj.HealthKeyPatientUserName;
	}

	public String getHealthKeyPatientPath() {
		return PidcObj.HealthKeyPatientPath;
	}

	public String getInsuranceHealthKeyPatientUserName() {
		return PidcObj.InsuranceHealthKeyPatientUserName;
	}

	public String getInsurancePortalURL() {
		return PidcObj.InsurancePortalURL;
	}

	public String getInsurancePortalRestURL() {
		return PidcObj.InsurancePortalRestURL;
	}

	public String getInsurancePatientID() {
		return PidcObj.InsurancePatientID;
	}

	public String getInsuranceHealthKeyPatientUserName1() {
		return PidcObj.InsuranceHealthKeyPatientUserName1;
	}

	public String getInsurancePatientID1() {
		return PidcObj.InsurancePatientID1;
	}

	public String getSecondInsuranceName() {
		return PidcObj.SecondInsuranceName;
	}

	public String getTestPatientIDUserName() {
		return PidcObj.TestPatientIDUserName;
	}

	public String getFnameSC() {
		return PidcObj.FnameSC;
	}

	public String getMnameSC() {
		return PidcObj.MnameSC;
	}

	public String getLnameSC() {
		return PidcObj.LnameSC;
	}

	public String getAddress1SC() {
		return PidcObj.Address1SC;
	}

	public String getAddress2SC() {
		return PidcObj.Address1SC;
	}

	public String getCancelInviteRestUrl() {
		return PidcObj.CancelInviteRestUrl;
	}

	public String getGenderIdentityValues() {
		return PidcObj.GI;
	}
	
	public String getSexualOrientationValues() {
		return PidcObj.SO;
	}
	
	public String getNewPatientPassword() {
		return PidcObj.NewPassword;
	}

	public String getRestv1Url() {
		return PidcObj.Restv1Url;
	}

	public String getRestv2Url() {
		return PidcObj.Restv2Url;
	}

	public String getRestv3Url() {
		return PidcObj.Restv3Url;
	}
	
}
