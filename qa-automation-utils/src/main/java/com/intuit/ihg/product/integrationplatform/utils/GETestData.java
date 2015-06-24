package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class GETestData {

	private GE geObj = null;
	
	public GETestData(GE sheetName) throws Exception{
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		ExcelSheetReader excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		geObj = (GE) excelReader.getSingleExcelRow(sheetName, temp);
	}
	
	
	public String getPHR_URL(){
		return geObj.PHR_URL;
	}
	
	public String getURL(){
		return geObj.URL;
	}
	
	public String getUserName(){
		return geObj.UserName;
	}
	
	public String getPassword(){
		return geObj.Password;
	}
	
	
	public String getRestUrl(){
		return geObj.RestUrl;
	}
	
	public String getCCDPath(){
		return geObj.CCDPath;
	}
	
	public String getResponsePath(){
		return geObj.ResponsePath;
	}
	
	
	public String getOAuthProperty(){
		return geObj.OAuthProperty;
	}
	
	public String getOAuthKeyStore(){
		return geObj.OAuthKeyStore;
	}
	
	public String getOAuthAppToken(){
		return geObj.OAuthAppToken;
	}
	
	public String getOAuthUsername(){
		return geObj.OAuthUsername;
	}
	
	public String getOAuthPassword(){
		return geObj.OAuthPassword;
	}	
	
}
