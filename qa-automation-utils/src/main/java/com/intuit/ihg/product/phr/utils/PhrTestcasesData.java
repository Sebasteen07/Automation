package com.intuit.ihg.product.phr.utils;

import java.net.URL;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class PhrTestcasesData {

	Phr phrobj = null;
	ExcelSheetReader excelReader = null;


	public PhrTestcasesData(Phr phr) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		phrobj = (Phr) excelReader.getSingleExcelRow(phr, temp);
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
	 * Returns ccdUserName from phr excel sheet
	 */
	public String getccdUserName() {
		return phrobj.ccdUserName;
	}


	/**
	 * Returns ccdUserPassword from phr excel sheet
	 */
	public String getccdUserPassword() {
		return phrobj.ccdUserPassword;
	}

	/**
	 * Returns ElektaRestURL from phr excel sheet
	 */
	public String getElektaRestURL() {
		return phrobj.elektaRestURL;
	}

	/**
	 * Returns ElektaPracticeUrl from phr excel sheet
	 */
	public String getElektaPracticeURL() {
		return phrobj.elektaPracticeURL;
	}

	/**
	 * Returns ElektaUser from phr excel sheet
	 */
	public String getElektaUser() {
		return phrobj.elektaUser;
	}

	/**
	 * Returns ElektaPassword from phr excel sheet
	 */
	public String getElektaPassword() {
		return phrobj.elektaPassword;
	}

}
