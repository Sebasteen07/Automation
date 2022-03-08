// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.net.URL;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class IntegrationTestData {

	Integration integrationobj = null;
	ExcelSheetReader excelReader = null;

	public IntegrationTestData(Integration sheetName) throws Exception {
		String temp = IHGUtil.getEnvironmentType().toString();
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		excelReader = new ExcelSheetReader(url.getFile());
		integrationobj = (Integration) excelReader.getSingleExcelRow(sheetName, temp);
	}

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
