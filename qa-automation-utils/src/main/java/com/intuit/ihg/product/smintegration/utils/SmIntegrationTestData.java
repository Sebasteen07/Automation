package com.intuit.ihg.product.smintegration.utils;

import java.net.URL;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

/**
 * @author bkrishnankutty
 * @Date 6/Aug/2013
 * @Description :-
 * @Note :
 */
public class SmIntegrationTestData {

	SmIntegration smobj = null;
	ExcelSheetReader excelReader = null;

	/**
	 * 
	 * @param sitegen
	 * @throws Exception
	 */
	public SmIntegrationTestData(SmIntegration smIntegration) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		Log4jUtil.log("ENV :"+IHGUtil.getEnvironmentType().toString());
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		smobj = (SmIntegration) excelReader.getSingleExcelRow(smIntegration, temp);
	}

//	/**
//	 * Returns  url from excel sheet
//	 */
//	public String getEnv() {
//		return smobj.env;
//	}

	/**
	 * Returns  url from excel sheet
	 */
	public String getUrl() {
		return smobj.url;
	}

	/**
	 * Returns  url from excel sheet
	 */
	public String getPractice() {
		return smobj.practice;
	}

	/**
	 * Returns  url from excel sheet
	 */
	public String getPracticeID() {
		return smobj.practiceID;
	}

	/**
	 * Returns  url from excel sheet
	 */
	public String getAccountID() {
		return smobj.accountID;
	}

	/**
	 * Returns  url from excel sheet
	 */
	public String getIntuitProviderID() {
		return smobj.intuitProviderID;
	}

	/**
	 * Returns  url from excel sheet
	 */
	public String getPracticeProviderID() {
		return smobj.practiceProviderID;
	}
	/**
	 * Returns  url from excel sheet
	 */
	public String getIntegrationID() {
		return smobj.integrationID;
	}

	/**
	 * Returns  url from excel sheet
	 */
	public String getDBUserName() {
		return smobj.dBUserName;
	}
	
	/**
	 * Returns  url from excel sheet
	 */
	public String getDBPassword() {
		return smobj.dBPassword;
	}

	/**
	 * Returns  url from excel sheet
	 */
	public String getPGDBName() {
		return smobj.pgDBName;
	}
	
	/**
	 * Returns  url from excel sheet
	 */
	public String getSMDBName() {
		return smobj.smDBName;
	}
	
	/**
	 * Returns  url from excel sheet
	 */
	public String getDBEnv() {
		return smobj.dBEnv;
	}
	
	/**
	 * Returns  url from excel sheet
	 */
	public String getUserName() {
		return smobj.username;
	}
	
	/**
	 * Returns  url from excel sheet
	 */
	public String getPassword() {
		return smobj.password;
	}
	
	/**
	 * 
	 */
	public String getEmail() {
		return smobj.patientEmail;
	}
	
	public String getFirstName() {
		return smobj.patientFirstName;
	}
	
	public String getLastName() {
		return smobj.patientLastName;
	}
	
	public String getPhoneNumber() {
		return smobj.patientPhoneNumber;
	}
	
	public String getPhoneType() {
		return smobj.patientPhoneType;
	}
	
	public String getDobMonth() {
		return smobj.patientDob_Month;
	}
	
	public String getDobDay() {
		return smobj.patientDob_Day;
	}
	
	public String getDobYear() {
		return smobj.patientDob_Year;
	}
	
	public String getZip() {
		return smobj.patientZip;
	}
	
	public String getSSN() {
		return smobj.patientSSN;
	}
	
	public String getSecretQuestion() {
		return smobj.patientSecretQuestion;
	}
	
	public String getAnswer() {
		return smobj.patientAnswer;
	}
	
	public String getAddress() {
		return smobj.address;
	}
	
	public String getAddressCity() {
		return smobj.city;
	}
	
	public String getAddressState() {
		return smobj.state;
	}
	
	/**
	 * Get PHR URL
	 * @return
	 */
	public String getPhrUrl() {
		return smobj.phrurl;
	}
	
	/**
	 * Get Patient ID
	 * @return
	 */
	public String getPatientID() {
		return smobj.patientID;
	}
	
	/**
	 * Get SM End point URL
	 * @return
	 */
	public String getEndPointUrl() {
		return smobj.endPointurl;
	}
	
	/**
	 * Get SM End point URL
	 * @return
	 */
	public String getARPatient() {
		return smobj.appointmentReq_patientID;
	}
	
	/**
	 * Get SM End point URL
	 * @return
	 */
	public String getARUserName() {
		return smobj.appointmentReq_user;
	}
	
	/**
	 * Get SM End point URL
	 * @return
	 */
	public String getARPassword() {
		return smobj.appointmentReq_password;
	}
	
	/**
	 * Get SM database Name
	 * @return
	 */
	public String getdBUserNameSM() {
		return smobj.dBUserNameSM;
	}
	
	/**
	 * Get SM database password
	 * @return
	 */
	public String getDBPasswordSM() {
		return smobj.dBPasswordSM;
	}
	
}
