package com.intuit.ihg.product.sitegen.utils;

import java.net.URL;
import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class SitegenTestData {

	Sitegen sitegenobj = null;
	ExcelSheetReader excelReader = null;

	/**
	 * 
	 * @param sitegen
	 * @throws Exception
	 */
	public SitegenTestData(Sitegen sitegen) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		sitegenobj = (Sitegen) excelReader.getSingleExcelRow(sitegen, temp);
	}

	/**
	 * Returns site gen url from excel sheet
	 */
	public String getSiteGenUrl() {
		return sitegenobj.sitegenUrl;
	}

	/**
	 * Returns site gen adminUser from excel sheet
	 */
	public String getadminUser() {

		return sitegenobj.adminUser;
	}

	/**
	 * Returns site gen adminPassword from excel sheet
	 */

	public String getadminPassword() {
		return sitegenobj.adminPassword;
	}

	/**
	 * Returns site gen PracticeName from excel sheet
	 */
	public String getAutomationPracticeName() {
		return sitegenobj.siteGenAutomationPractice;
	}

	/**
	 * Returns site gen ParticeURL from excel sheet
	 */
	public String getAutomationPracticeUrl() {
		return sitegenobj.siteGenAutomationPracticeUrl;
	}

	/**
	 * Returns site gen ParticeUserName from excel sheet
	 */
	public String getAutomationUser() {
		return sitegenobj.automationUser;
	}

	/**
	 * Returns site gen ParticeUserPassword from excel sheet
	 */

	public String getAutomationUserPassword() {
		return sitegenobj.automationUserPassword;
	}

	/**
	 * Returns site gen personnelType from excel sheet
	 */
	public String getpersonnelType() {
		return sitegenobj.personnelType;
	}

	/**
	 * Returns site gen personnelType UserName from excel sheet
	 */
	public String getpersonnelTypeUserName() {
		return sitegenobj.personnelTypeUserName;
	}

	/**
	 * Returns site gen personnelType Pswd from excel sheet
	 */
	public String getpersonnelTypePswd() {
		return sitegenobj.personnelTypePswd;
	}
	
	/**
	 * Returns site gen QBMS test username from excel sheet
	 */
	public String getQBMSUser() {
		return sitegenobj.qbmsUser;
	}

	/**
	 * Returns site gen QBMS test user password from excel sheet
	 */
	public String getQBMSPassword() {
		return sitegenobj.qbmsPassword;
	}

}
