package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;

import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class AllScriptTestData {

	private AllScript allscriptObj = null;

	public AllScriptTestData(AllScript sheetName) throws Exception {
		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		System.out.println(url.getPath());
		// reading the entire file
		ExcelSheetReader excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		allscriptObj = (AllScript) excelReader.getSingleExcelRow(sheetName, temp);
	}


	public String getPHR_URL() {
		return allscriptObj.PHR_URL;
	}

	public String getURL() {
		return allscriptObj.URL;
	}

	public String getUserName() {
		return allscriptObj.UserName;
	}

	public String getPassword() {
		return allscriptObj.Password;
	}


	public String getRestUrl() {
		return allscriptObj.RestUrl;
	}

	public String getCCDPath() {
		return allscriptObj.CCDPath;
	}

	public String getResponsePath() {
		return allscriptObj.ResponsePath;
	}

	public String getEmail() {
		return allscriptObj.Email;
	}

}
