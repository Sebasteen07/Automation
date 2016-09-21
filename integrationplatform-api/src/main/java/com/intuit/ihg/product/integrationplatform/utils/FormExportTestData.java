package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class FormExportTestData {


	private FormExport formExportObj = null;
	private ExcelSheetReader excelReader = null;

	public FormExportTestData(FormExport formData) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file

		formExportObj = (FormExport) excelReader.getSingleExcelRow(formData, temp);
	}

	public String getUrl() {
		return formExportObj.Url;
	}


	public String getRestUrl() {
		return formExportObj.RestUrl;
	}

	public String getResponsePath() {
		return formExportObj.ResponsePath;
	}


	public String getOAuthProperty() {
		return formExportObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return formExportObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return formExportObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return formExportObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return formExportObj.OAuthPassword;
	}



	public String getFirstName() {
		return formExportObj.patientFirstName;
	}


	public String getLastName() {
		return formExportObj.patientLastName;
	}


	public String getEmail() {
		return formExportObj.patientEmail;
	}


	public String getPhoneNumber() {
		return formExportObj.patientPhoneNumber;
	}

	public String getPhoneType() {
		return formExportObj.patientPhoneType;
	}


	public String getDob_Month() {
		return formExportObj.patientDob_Month;
	}


	public String getDob_Day() {
		return formExportObj.patientDob_Day;
	}

	public String getDob_Year() {
		return formExportObj.patientDob_Year;
	}

	public String getZip() {
		return formExportObj.patientZip;
	}


	public String getSSN() {
		return formExportObj.patientSSN;
	}


	public String getSecretQuestion() {
		return formExportObj.patientSecretQuestion;
	}

	public String getAnswer() {
		return formExportObj.patientAnswer;
	}


	public String getPreferredDoctor() {
		Log4jUtil.log("Patient Preferred Provider: " + formExportObj.patientPreferredDoctor);
		return formExportObj.patientPreferredDoctor;
	}

	public String getAddress() {
		return formExportObj.address;
	}

	public String getAddressCity() {
		return formExportObj.city;
	}

	public String getAddressState() {
		return formExportObj.state;
	}

	public String getPassword() {
		return formExportObj.password;
	}

	public String getPracticeURL() {
		return formExportObj.practiceURL;
	}

	public String getPracticeUsername() {
		return formExportObj.practiceUserName;
	}

	public String getPracticePassword() {
		return formExportObj.practicePassword;
	}


}
