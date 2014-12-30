package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

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
	
	public String getUrl(){
		return paymentObj.Url;
	}
	
	public String getUserName(){
		return paymentObj.UserName;
	}
	
	public String getPassword(){
		return paymentObj.Password;
	}
	
	public String getRestUrl(){
		return paymentObj.RestUrl;
	}
	
	public String getResponsePath(){
		return paymentObj.ResponsePath;
	}
	
	public String getOAuthProperty(){
		return paymentObj.OAuthProperty;
	}
	
	public String getOAuthKeyStore(){
		return paymentObj.OAuthKeyStore;
	}
	
	public String getOAuthAppToken(){
		return paymentObj.OAuthAppToken;
	}
	
	public String getOAuthUsername(){
		return paymentObj.OAuthUsername;
	}
	
	public String getOAuthPassword(){
		return paymentObj.OAuthPassword;
	}	
	

	
}
