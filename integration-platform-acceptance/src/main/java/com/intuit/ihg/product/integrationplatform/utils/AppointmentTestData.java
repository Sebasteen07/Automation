package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class AppointmentTestData {
	
	private Appointment AppointmentObj = null;
	private ExcelSheetReader excelReader = null;

	public AppointmentTestData(Appointment aptData) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		AppointmentObj = (Appointment) excelReader.getSingleExcelRow(aptData, temp);
	}
	
	public String getUrl(){
		return AppointmentObj.Url;
	}
	
	public String getUserName(){
		return AppointmentObj.UserName;
	}
	
	public String getPassword(){
		return AppointmentObj.Password;
	}
	
	public String getRestUrl(){
		return AppointmentObj.RestUrl;
	}
	
	public String getResponsePath(){
		return AppointmentObj.ResponsePath;
	}
	
	public String getFrom(){
		return AppointmentObj.From;
	}
	
	public String getSecureMessagePath(){
		return AppointmentObj.SecureMessagePath;
	}
		
	public String getOAuthProperty(){
		return AppointmentObj.OAuthProperty;
	}
	
	public String getOAuthKeyStore(){
		return AppointmentObj.OAuthKeyStore;
	}
	
	public String getOAuthAppToken(){
		return AppointmentObj.OAuthAppToken;
	}
	
	public String getOAuthUsername(){
		return AppointmentObj.OAuthUsername;
	}
	
	public String getOAuthPassword(){
		return AppointmentObj.OAuthPassword;
	}

	public String getPreferredDoctor() {
		return AppointmentObj.PreferredDoctor;
	}

	public String getPhoneNumber() {
		return AppointmentObj.PhoneNumber;
	}	
	
	
}
