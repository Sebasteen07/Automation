// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
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
	public void testGatewayAuthorizeByValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String token = GatewayProxyUtils.getTokenForCustomer();
		Response response = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"), testData.getProperty("type"),testData.getProperty("card.number"),
				testData.getProperty("expiration.number"),testData.getProperty("test.pay.customer.uuid"),testData.getProperty("proxy.mmid"));

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());
	}

	@Test
	public void testGatewayAuthorizeByInvalidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String token = GatewayProxyUtils.getTokenForCustomer()+"fgh";
		Response response = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"), testData.getProperty("type"),testData.getProperty("card.number"),
				testData.getProperty("expiration.number"),testData.getProperty("test.pay.customer.uuid"),testData.getProperty("proxy.mmid"));

		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 401);
		Assert.assertEquals("Unauthorized", jsonpath.get("message"));
	}

	@DataProvider(name = "txn_data_for_http_400_statuscodes")
	public Object[][] dpMethod() {
		return new Object[][]{
				{"VCS","VI","4111111111111111","1223",testData.getProperty("test.pay.customer.uuid")+"erd", testData.getProperty("proxy.mmid")},
				{"","VI","4111111111111111","1223",testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid")},
				{"VCS","","4111111111111111","1223",testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid")},
//				{"VCS","VI","","1223",testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid")},
//				{"VCS","VI","4111111111111111","",testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid")},
//				{"VCS","VI","4111111111111111","1223","", testData.getProperty("proxy.mmid")},
				{"VCS","VI","4111111111111111","1223",testData.getProperty("test.pay.customer.uuid"), ""}
		};
		}

	@Test(dataProvider = "txn_data_for_http_400_statuscodes")
	public void testGatewayAuthorizeForInvalidData(String paymentSource, String type, String cardNumber, String expiry, String customeruuid, String mmid) throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String token = GatewayProxyUtils.getTokenForCustomer();
		Response response = transaction.makeAuthorizeTransaction(token, paymentSource, type, cardNumber, expiry, customeruuid, mmid);

		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertEquals(response.getStatusCode(), 400);
//		Assert.assertTrue(jsonpath.toString().contains("Bad Request"));
	}


	@Test
	public void testGatewayCaptureByValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String token = GatewayProxyUtils.getTokenForCustomer();
		Response authResponse = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"), testData.getProperty("type"),testData.getProperty("card.number"),
				testData.getProperty("expiration.number"),testData.getProperty("test.pay.customer.uuid"),testData.getProperty("proxy.mmid"));
		JsonPath jsonpath = new JsonPath(authResponse.asString());

		Response captureResponse = transaction.makeCaptureTransaction(token, testData.getProperty("test.pay.customer.uuid"),testData.getProperty("proxy.mmid"), jsonpath.get("externalTransactionId").toString(), jsonpath.get("orderId").toString());
		JsonPath jsonpathCapture = new JsonPath(captureResponse.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(captureResponse.asString());
		CommonUtils.saveTransactionDetails(jsonpathCapture.get("externalTransactionId").toString(),
				jsonpathCapture.get("orderId").toString());
	}

	@Test
	public void testGatewayCaptureByInvalidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String token = GatewayProxyUtils.getTokenForCustomer();
		Response authResponse = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"), testData.getProperty("type"),testData.getProperty("card.number"),
				testData.getProperty("expiration.number"),testData.getProperty("test.pay.customer.uuid"),testData.getProperty("proxy.mmid"));
		JsonPath jsonpath = new JsonPath(authResponse.asString());

		String tokenForCapture = GatewayProxyUtils.getTokenForCustomer()+"hggf";
		Response captureResponse = transaction.makeCaptureTransaction(tokenForCapture, testData.getProperty("test.pay.customer.uuid"),testData.getProperty("proxy.mmid"), jsonpath.get("externalTransactionId").toString(), jsonpath.get("orderId").toString());
		JsonPath jsonpathCapture = new JsonPath(captureResponse.asString());
		Assert.assertTrue(captureResponse.getStatusCode() == 401);
		Assert.assertEquals("Unauthorized", jsonpathCapture.get("message"));
	}
}
