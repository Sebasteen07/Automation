package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class Oauth10TestData {

	private Oauth10 OauthObj = null;
	private ExcelSheetReader excelReader = null;

	public Oauth10TestData(Oauth10 oauth10Data) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		Log4jUtil.log("URL: " + url);
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		OauthObj = (Oauth10) excelReader.getSingleExcelRow(oauth10Data, temp);
	}

	public String getUserName() {
		return OauthObj.UserName;
	}

	public String getFrom() {
		return OauthObj.From;
	}

	public String getCommonPath() {
		return OauthObj.CommonPath;
	}

	public String getOAuthProperty() {
		return OauthObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return OauthObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return OauthObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return OauthObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return OauthObj.OAuthPassword;
	}

	public String getAMDCRestURL() {
		return OauthObj.AMDCRestURL;
	}

	public String getReadCommunicationURL() {
		return OauthObj.ReadCommunicationURL;
	}

	public String getPatientRestURL() {
		return OauthObj.PatientRestURL;
	}

	public String getAppointmentRestURL() {
		return OauthObj.AppointmentRestURL;
	}

	public String getEHDCRestURL() {
		return OauthObj.EHDCRestURL;
	}

	public String getPrescriptionRestURL() {
		return OauthObj.PrescriptionRestURL;
	}

	public String getccdExchangeBatch() {
		return OauthObj.ccdExchangeBatch;
	}

	public String getPullEventsURL() {
		return OauthObj.pullEventsURL;
	}

	public String getPaymentURL() {
		return OauthObj.paymentURL;
	}

}
