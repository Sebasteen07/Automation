package com.intuit.ihg.product.fundamentalArchitects.utils;

import java.net.URL;

import com.intuit.ihg.common.utils.ExcelSheetReader;
import com.intuit.ihg.common.utils.IHGUtil;

public class FundamentalArchitectsTestData {

	FundamentalArchitects fundamentalArchitectsobjects = null;
	ExcelSheetReader excelReader = null;

	/**
	 * 
	 * @param sitegen
	 * @throws Exception
	 */
	public FundamentalArchitectsTestData(FundamentalArchitects fundamentalArchitects) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		fundamentalArchitectsobjects = (FundamentalArchitects) excelReader.getSingleExcelRow(fundamentalArchitects, temp);
	}

	public String getRegistrationUrl() {

		return fundamentalArchitectsobjects.registrationUrl;
	}
	
	public String getGmailUsername() {
		return fundamentalArchitectsobjects.gmailUsername;
	}
	
	public String getGmailPassword() {
		return fundamentalArchitectsobjects.gmailPassword;
	}
	
	public String getGmailMessage() {
		return fundamentalArchitectsobjects.gmailMessage;
	}
}
