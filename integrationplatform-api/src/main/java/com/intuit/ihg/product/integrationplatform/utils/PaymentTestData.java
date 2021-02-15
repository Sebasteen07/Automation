package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class PaymentTestData {

	private Payment paymentObj = null;
	private ExcelSheetReader excelReader = null;

	public PaymentTestData(Payment sheetName) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		paymentObj = (Payment) excelReader.getSingleExcelRow(sheetName, temp);
	}

	public String getUrl() {
		return paymentObj.Url;
	}

	public String getUserName() {
		return paymentObj.UserName;
	}

	public String getPassword() {
		return paymentObj.Password;
	}

	public String getRestUrl() {
		return paymentObj.RestUrl;
	}

	public String getResponsePath() {
		return paymentObj.ResponsePath;
	}

	public String getOAuthProperty() {
		return paymentObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return paymentObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return paymentObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return paymentObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return paymentObj.OAuthPassword;
	}

	public String getFrom() {
		return paymentObj.From;
	}

	public String getcommunicationXML() {
		return paymentObj.communicationXML;
	}

	public String getPaymentPath() {
		return paymentObj.PaymentPath;
	}

	public String getCommRestUrl() {
		return paymentObj.CommRestUrl;
	}

	public String getPracticeURL() {
		return paymentObj.PracticeURL;
	}

	public String getPracticeUserName() {
		return paymentObj.PracticeUserName;
	}

	public String getPracticePassword() {
		return paymentObj.PracticePassword;
	}

	public String getGmailUserName() {
		return paymentObj.GmailUserName;
	}

	public String getGmailPassword() {
		return paymentObj.GmailPassword;
	}

	public String getPracticeName() {
		return paymentObj.PracticeName;
	}
	
	public String getRestV3Url() {
		return paymentObj.RestV3Url;
	}
	
	public String getPaymentPathV3() {
		return paymentObj.PaymentPathV3;
	}
}
