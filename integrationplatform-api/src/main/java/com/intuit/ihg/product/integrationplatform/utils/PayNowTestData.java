package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class PayNowTestData {

	private PayNow payNowObj = null;
	private ExcelSheetReader excelReader = null;

	public PayNowTestData(PayNow sheetName) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		payNowObj = (PayNow) excelReader.getSingleExcelRow(sheetName, temp);
	}

	public String getUrl() {
		return payNowObj.Url;
	}

	public String getRestUrl() {
		return payNowObj.RestUrl;
	}

	public String getResponsePath() {
		return payNowObj.ResponsePath;
	}

	public String getOAuthProperty() {
		return payNowObj.OAuthProperty;
	}

	public String getOAuthKeyStore() {
		return payNowObj.OAuthKeyStore;
	}

	public String getOAuthAppToken() {
		return payNowObj.OAuthAppToken;
	}

	public String getOAuthUsername() {
		return payNowObj.OAuthUsername;
	}

	public String getOAuthPassword() {
		return payNowObj.OAuthPassword;
	}

	public String getPaymentPath() {
		return payNowObj.PaymentPath;
	}

	public String getPracticeURL() {
		return payNowObj.PracticeURL;
	}

	public String getPracticeUserName() {
		return payNowObj.PracticeUserName;
	}

	public String getPracticePassword() {
		return payNowObj.PracticePassword;
	}

	public String getFirstName() {
		return payNowObj.FirstName;
	}

	public String getLastName() {
		return payNowObj.LastName;
	}

	public String getZip() {
		return payNowObj.Zip;
	}

	public String getEmail() {
		return payNowObj.Email;
	}

	public String getRestV3Url() {
		return payNowObj.RestV3Url;
	}
	
	public String getPaymentPathV3() {
		return payNowObj.PaymentPathV3;
	}
}
