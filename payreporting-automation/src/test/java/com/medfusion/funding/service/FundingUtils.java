//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package com.medfusion.funding.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.PropertyFileLoader;
import io.restassured.response.Response;

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

	public Response getReportsYearly(String url, String financialYearStart, String financialYearEnd) {
		return given().queryParam("financialYearStart", financialYearStart)
				.queryParam("financialYearEnd", financialYearEnd).when().get(url)
				.then().and().extract().response();
	}

	public Response getReportsQuarterly(String url, String quarter, String financialYearStart, String financialYearEnd) {
		return given().queryParam("quarter", quarter).queryParam("financialYearStart", financialYearStart)
				.queryParam("financialYearEnd", financialYearEnd).when().get(url)
				.then().extract().response();
	}

	public Response getReportsMonthly(String url, String month, String year) {
		return given().queryParam("month", month)
				.queryParam("year", year).when().get(url)
				.then().extract().response();
	}
}
