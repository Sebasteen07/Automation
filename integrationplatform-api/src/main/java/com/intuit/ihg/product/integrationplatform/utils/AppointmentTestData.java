package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class AppointmentTestData {

	private Appointment AppointmentObj = null;
	private ExcelSheetReader excelReader = null;

	public AppointmentTestData(Appointment aptData) throws Exception {
		// which environment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		AppointmentObj = (Appointment) excelReader.getSingleExcelRow(aptData, temp);
	}

	public String getUrl() {
		return AppointmentObj.Url;
	}

	public String getUserName() {
		return AppointmentObj.UserName;
	}

	public String getPassword() {
		return AppointmentObj.Password;
	}

	public String getRestUrl() {
		return AppointmentObj.RestUrl;
	}

	public String getResponsePath() {
		return AppointmentObj.ResponsePath;
	}

	public String getFrom() {
		return AppointmentObj.From;
	}

	public String getAppointmentPath() {
		return AppointmentObj.AppointmentPath;
	}

	public String getOAuthProperty() {
		return AppointmentObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return AppointmentObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return AppointmentObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return AppointmentObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return AppointmentObj.OAuthPassword;
	}

	public String getPreferredDoctor() {
		return AppointmentObj.PreferredDoctor;
	}

	public String getPhoneNumber() {
		return AppointmentObj.PhoneNumber;
	}

	public String getPracticeURL() {
		return AppointmentObj.PracticeURL;
	}

	public String getPracticeUserName() {
		return AppointmentObj.PracticeUserName;
	}

	public String getPracticePassword() {
		return AppointmentObj.PracticePassword;
	}

	public String getGmailUserName() {
		return AppointmentObj.GmailUserName;
	}

	public String getGmailPassword() {
		return AppointmentObj.GmailPassword;
	}

	public String getPracticeName() {
		return AppointmentObj.PracticeName;
	}

	public String getRestV3Url() {
		return AppointmentObj.RestV3Url;
	}

	public String getAppointmentPathV3() {
		return AppointmentObj.AppointmentPathV3;
	}

	public String getRestUrlV3Headers() {
		return AppointmentObj.RestUrlV3Headers;
	}
}
