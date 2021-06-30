// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyTransactionResource;

public class GatewayProxyTransactionTests extends GatewayProxyBaseTest {

	protected static PropertyFileLoader testData;

	@BeforeTest
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();

	}

	@Test
	public void makeGatewayProxySale() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		transaction.makeASale(testData.getProperty("proxy.mmid"));

	}

	@Test
	public void makeGatewayAuthorize() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		Response response = transaction.makeAuthorizeTransaction("75164da9-5747-449f-be45-c2e28f3ff77b","2560807797" );

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());
	}

	@Test
	public void makeGatewayCapture() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		Response authResponse = transaction.makeAuthorizeTransaction("75164da9-5747-449f-be45-c2e28f3ff77b","2560807797" );
		JsonPath jsonpath = new JsonPath(authResponse.asString());

		Response captureResponse = transaction.makeCaptureTransaction("75164da9-5747-449f-be45-c2e28f3ff77b","2560807797", jsonpath.get("externalTransactionId").toString(), jsonpath.get("orderId").toString());

		JsonPath jsonpathCapture = new JsonPath(captureResponse.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(captureResponse.asString());
		CommonUtils.saveTransactionDetails(jsonpathCapture.get("externalTransactionId").toString(),
				jsonpathCapture.get("orderId").toString());
	}
}
