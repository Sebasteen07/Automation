// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.apiehcore.utils;

import java.net.URL;
import com.medfusion.common.utils.ExcelSheetReader;
import com.medfusion.common.utils.IHGUtil;

public class EhcoreAPITestData {

	EhcoreAPI ehcoreapiObj = null;
	ExcelSheetReader excelReader = null;

	public EhcoreAPITestData(EhcoreAPI ehcoreAPI) throws Exception {

		// which enviroment data need to picked
		String temp = IHGUtil.getEnvironmentType().toString();
		// file name
		URL url = ClassLoader.getSystemResource("data-driven/IHG_CONFIG.xls");
		// reading the entire file
		excelReader = new ExcelSheetReader(url.getFile());
		// filtering the entire file
		ehcoreapiObj = (EhcoreAPI) excelReader.getSingleExcelRow(ehcoreAPI, temp);
	}

	public String getHost() {
		return ehcoreapiObj.host;
	}

	public String getPort() {
		return ehcoreapiObj.port;
	}

	public String getDBCCDHost() {
		return ehcoreapiObj.db_ccd_host;
	}

	public String getDBCCDSID() {
		return ehcoreapiObj.db_ccd_sid;
	}

	public String getMongoServerAddress() {
		return ehcoreapiObj.mongo_serverAddress;
	}

	public String getPracticeID() {
		return ehcoreapiObj.practiceId;
	}

	public String getProviderID() {
		return ehcoreapiObj.providerId;
	}

	public String getPatientID() {
		return ehcoreapiObj.patientId;
	}

	public String getCCD() {
		return ehcoreapiObj.ccd;
	}

	public String getUrl() {
		return ehcoreapiObj.url;
	}

	public String getProtocol() {
		return ehcoreapiObj.protocol;
	}

	public String getAllscriptsccdexporturl() {
		return ehcoreapiObj.allscriptsccdexporturl;
	}

	public String getAllscriptsccdimporturl() {
		return ehcoreapiObj.allscriptsccdimporturl;
	}

	public String getmongoproperty() {
		return ehcoreapiObj.mongoproperty;
	}

}
