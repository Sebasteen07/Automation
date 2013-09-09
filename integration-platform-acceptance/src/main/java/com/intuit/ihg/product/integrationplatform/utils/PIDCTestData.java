package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

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
	
	public String getUrl(){
		return PidcObj.Url;
	}
	
	public String getUserName(){
		return PidcObj.UserName;
	}
	
	public String getPassword(){
		return PidcObj.Password;
	}
	
	public String getRestUrl(){
		return PidcObj.RestUrl;
	}
	
	public String getResponsePath(){
		return PidcObj.ResponsePath;
	}
	
	public String getOAuthProperty(){
		return PidcObj.OAuthProperty;
	}
	
	public String getOAuthKeyStore(){
		return PidcObj.OAuthKeyStore;
	}
	
	public String getOAuthAppToken(){
		return PidcObj.OAuthAppToken;
	}
	
	public String getOAuthUsername(){
		return PidcObj.OAuthUsername;
	}
	
	public String getOAuthPassword(){
		return PidcObj.OAuthPassword;
	}	
}
