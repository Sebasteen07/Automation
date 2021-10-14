//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.funding.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.PropertyFileLoader;

public class FundingUtils extends BaseTestNG {
	public static PropertyFileLoader testData;

	public String loadTransactionId() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("configurations.properties");
		prop.load(input);
		String transactionid = prop.getProperty("transactionid");
		return transactionid;
	}

	public String loadOrderId() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("configurations.properties");
		prop.load(input);
		String orderid = prop.getProperty("orderid");
		return orderid;
	}
	
	public String loadDataFromProperty(String keyToSearch) throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("configurations.properties");
		prop.load(input);
		String valueToReterive = prop.getProperty(keyToSearch);
		return valueToReterive;

	}
	public static String getBaseUrl() throws IOException {
		testData = new PropertyFileLoader();
		String fundurl = testData.getProperty("funding.base.url");
		String baseurl = fundurl + "/ibf-ibm/services/emaf/fund";
		return baseurl;

	}

}
