package com.intuit.ihg.product.support.utils;

import java.net.URL;
import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.support.utils.Support;
public class SupportTestcasesData {

	Support supportObj = null;
	ExcelSheetReader excelReader = null;
	

	public SupportTestcasesData(Support support) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); 
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		supportObj = (Support) excelReader.getSingleExcelRow(support, temp);
	}

	
	/**
	 * Returns Patient url from excel sheet
	 */
	public String getPatientUrl() {
		return supportObj.patientUrl;
	}

	/**
	 * Returns Url from excel sheet
	 */
	public String getUrl() {
		return supportObj.url;
	}
	
	/**
	 *  Returns AllScriptAdaptorUrl from excel sheet
	 */
	public String getAllScriptAdaptorUrl() {
		return supportObj.allScriptUrl;
	}
	
	/**
	 * Returns username from excel sheet
	 */
	public String getUsername() {
		return supportObj.username;
	}
	
	/**
	 * Returns username from excel sheet
	 */
	public String getPassword() {
		return supportObj.password;
	}
	
	/**
	 * Returns Physician Name from excel sheet
	 */
	public String getPhysicianName() {
		return supportObj.physicianName;
	}

	/**
	 * Returns Medfusion StaffId from excel sheet
	 */
	public String getMedStaffID() {
		return supportObj.medfusionStaffId;
	}

	/**
	 * Returns external Id from phr excel sheet
	 */
	public String getExternalID() {
		return supportObj.externalId;
	}
	
	/**
	 * Returns external system Id from phr excel sheet
	 */
	public String getExternalSysID() {
		return supportObj.externalSystemID;
	}
	
	/**
	 * Returns external System Name from phr excel sheet
	 */
	public String getExternalSysName() {
		return supportObj.externalSystemName;
	}
	
	/**
	 * Returns WebsSrvice UserName from phr excel sheet
	 */
	public String getWebServiceUserName() {
		return supportObj.webserviceUserName;
	}
	
	/**
	 * Returns WebsSrvice Password from phr excel sheet
	 */
	public String getsecondaryCity() {
		return supportObj.webservicePassword;
	}
	
	/**
	 * Returns PracticeID from phr excel sheet
	 */
	public String getPracticeID() {
		return supportObj.practiceId;
	}
}
