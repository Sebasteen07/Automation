package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class GWTestData {


	private GW GWObj = null;
	private ExcelSheetReader excelReader = null;

	public GWTestData(GW gwData) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file

		GWObj = (GW) excelReader.getSingleExcelRow(gwData, temp);
	}

	public String getUrl() {
		return GWObj.Url;
	}


	public String getRestUrl() {
		return GWObj.RestUrl;
	}

	public String getResponsePath() {
		return GWObj.ResponsePath;
	}


	public String getOAuthProperty() {
		return GWObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return GWObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return GWObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return GWObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return GWObj.OAuthPassword;
	}



	public String getFirstName() {
		return GWObj.patientFirstName;
	}


	public String getLastName() {
		return GWObj.patientLastName;
	}


	public String getEmail() {
		return GWObj.patientEmail;
	}


	public String getPhoneNumber() {
		return GWObj.patientPhoneNumber;
	}

	public String getPhoneType() {
		return GWObj.patientPhoneType;
	}


	public String getDob_Month() {
		return GWObj.patientDob_Month;
	}


	public String getDob_Day() {
		return GWObj.patientDob_Day;
	}

	public String getDob_Year() {
		return GWObj.patientDob_Year;
	}

	public String getZip() {
		return GWObj.patientZip;
	}


	public String getSSN() {
		return GWObj.patientSSN;
	}


	public String getSecretQuestion() {
		return GWObj.patientSecretQuestion;
	}

	public String getAnswer() {
		return GWObj.patientAnswer;
	}


	public String getPreferredDoctor() {
		Log4jUtil.log("Patient Preferred Provider: " + GWObj.patientPreferredDoctor);
		return GWObj.patientPreferredDoctor;
	}

	public String getAddress() {
		return GWObj.address;
	}

	public String getAddressCity() {
		return GWObj.city;
	}

	public String getAddressState() {
		return GWObj.state;
	}

	public String getPassword() {
		return GWObj.password;
	}

	public String getPracticeURL() {
		return GWObj.practiceURL;
	}

	public String getPracticeUsername() {
		return GWObj.practiceUserName;
	}

	public String getPracticePassword() {
		return GWObj.practicePassword;
	}

	public String getCCDPath() {
		return GWObj.CCDPath;
	}

	public String getExternalSystemID() {
		return GWObj.ExternalSystemID;
	}

}
