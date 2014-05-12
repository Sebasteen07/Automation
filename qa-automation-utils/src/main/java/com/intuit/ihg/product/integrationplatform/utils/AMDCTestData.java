package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class AMDCTestData {
	
	private AMDC AmdcObj = null;
	private ExcelSheetReader excelReader = null;

	public AMDCTestData(AMDC sheetName) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		AmdcObj = (AMDC) excelReader.getSingleExcelRow(sheetName, temp);
	}
	
	public String getUrl(){
		return AmdcObj.Url;
	}
	
	public String getUserName(){
		return AmdcObj.UserName;
	}
	
	public String getPassword(){
		return AmdcObj.Password;
	}
	
	public String getRestUrl(){
		return AmdcObj.RestUrl;
	}
	
	public String getResponsePath(){
		return AmdcObj.ResponsePath;
	}
	
	public String getFrom(){
		return AmdcObj.From;
	}
	
	public String getSecureMessagePath(){
		return AmdcObj.SecureMessagePath;
	}
	
	
		
	
	public String getOAuthProperty(){
		return AmdcObj.OAuthProperty;
	}
	
	public String getOAuthKeyStore(){
		return AmdcObj.OAuthKeyStore;
	}
	
	public String getOAuthAppToken(){
		return AmdcObj.OAuthAppToken;
	}
	
	public String getOAuthUsername(){
		return AmdcObj.OAuthUsername;
	}
	
	public String getOAuthPassword(){
		return AmdcObj.OAuthPassword;
	}	
	
	
}
