package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

/**
 * @author bkrishnankutty
 * @Date 5/Aug/2013
 * @Description :-
 * @Note :
 */
public class IntegrationTestData {

	Integration integrationobj = null;
	ExcelSheetReader excelReader = null;

	/**
	 * 
	 * @param sitegen
	 * @throws Exception
	 */
	public IntegrationTestData(Integration sheetName) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		integrationobj = (Integration) excelReader.getSingleExcelRow(sheetName, temp);
	}

	/**
	 * Returns site gen url from excel sheet
	 */
/*	public String getSiteGenUrl() {
		return integrationobj.variable;
	}*/
	/**
	 * Returns patient portal login url from excel sheet
	 */
	public String getAMDCUrl() {
		return integrationobj.AMDCUrl;
	}
	
	public String getAMDCUserName() {
		return integrationobj.AMDCUserName;
	}
	
	public String getAMDCPassword() {
		return integrationobj.AMDCPassword;
	}
	
	

	

}
