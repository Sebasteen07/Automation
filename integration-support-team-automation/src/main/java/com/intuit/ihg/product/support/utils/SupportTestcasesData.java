package com.intuit.ihg.product.support.utils;

import java.net.URL;
import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class SupportTestcasesData {

	Support phrobj = null;
	ExcelSheetReader excelReader = null;
	

	public SupportTestcasesData(Support phr) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls"); 
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		phrobj = (Support) excelReader.getSingleExcelRow(phr, temp);
	}

	/**
	 * Returns PHR url from excel sheet
	 */
	public String geturl() {
		return phrobj.url;
	}

	/**
	 * Returns UserName from excel sheet
	 */
	public String getUsername() {
		return phrobj.username;
	}

	/**
	 * Returns Password from excel sheet
	 */
	public String getPassword() {
		return phrobj.password;
	}

	/**
	 * Returns secondaryUser from phr excel sheet
	 */
	public String getsecondaryUser() {
		return phrobj.secondaryUser;
	}
	
	/**
	 * Returns secondaryUserPwd from phr excel sheet
	 */
	public String getsecondaryUserPwd() {
		return phrobj.secondaryUserPwd;
	}
	
	/**
	 * Returns secondaryUserPhone from phr excel sheet
	 */
	public String getsecondaryUserPhone() {
		return phrobj.secondaryUserPhone;
	}
	
	/**
	 * Returns secondaryUserZipCode from phr excel sheet
	 */
	public String getsecondaryUserZipCode() {
		return phrobj.secondaryUserZipCode;
	}
	
	/**
	 * Returns secondaryUserZipCode from phr excel sheet
	 */
	public String getsecondaryCity() {
		return phrobj.secondaryCity;
	}
	
	
	/**
	 * Returns allScriptAdapterURL from phr excel sheet
	 */
	public String getallScriptAdapterURL() {
		return phrobj.allScriptAdapterURL;
	}
	

	/**
	 * Returns secondaryUserZipCode from phr excel sheet
	 */
	public String getccdUserName() {
		return phrobj.ccdUserName;
	}
	
	
	/**
	 * Returns allScriptAdapterURL from phr excel sheet
	 */
	public String getccdUserPassword() {
		return phrobj.ccdUserPassword;
	}
}
