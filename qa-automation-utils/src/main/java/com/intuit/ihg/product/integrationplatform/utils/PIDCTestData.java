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
	
	public String getPatientPath(){
		return PidcObj.PatientPath;
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
	
	public String getGmailUsername(){
		return PidcObj.GmailUsername;
	}	
	
	public String getGmailPassword(){
		return PidcObj.GmailPassword;
	}
	
	public String getBirthDay(){
		return PidcObj.BirthDay;
	}
	
	public String getZipCode(){
		return PidcObj.ZipCode;
	}
	
	public String getSSN(){
		return PidcObj.SSN;
	}
	
	public String getEmail(){
		return PidcObj.Email;
	}
	
	public String getPatientPassword(){
		return PidcObj.PatientPassword;
	}	
	
	public String getSecretQuestion(){
		return PidcObj.SecretQuestion;
	}	
	
	public String getSecretAnswer(){
		return PidcObj.SecretAnswer;
	}
	public String getPracticeURL(){
		return PidcObj.PracticeURL;
	}	
	
	public String getPracticeUserName(){
		return PidcObj.PracticeUserName;
	}	
	
	public String getPracticePassword(){
		return PidcObj.PracticePassword;
	}
	
	public String getLastName(){
		return PidcObj.LastName;
	}
	
	public String getCity(){
		return PidcObj.City;
	}	
	
	public String getState(){
		return PidcObj.State;
	}	
	
	public String getAddress1(){
		return PidcObj.Address1;
	}
	
	public String getAddress2(){
		return PidcObj.Address2;
	}
	
	public String getHomePhoneNo(){
		return PidcObj.HomePhoneNo;
	}
	
	public String getInsurance_Type(){
		return PidcObj.Insurance_Type;
	}
	
	public String getInsurance_Name(){
		return PidcObj.Insurance_Name;
	}
	
	public String getRelation(){
		return PidcObj.Relation;
	}
	
	public String getPreferredLanguage(){
		return PidcObj.PreferredLanguage;
	}
	
	public String getRace(){
		return PidcObj.Race;
	}
	
	public String getEthnicity(){
		return PidcObj.Ethnicity;
	}
	
	public String getMaritalStatus(){
		return PidcObj.MaritalStatus;
	}
	
	public String getChooseCommunication(){
		return PidcObj.ChooseCommunication;
	}
	
	public String getBatch_PatientPath(){
		return PidcObj.Batch_PatientPath;
	}
}
