// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyTransactionResource;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.GatewayProxyTestData;
import com.medfusion.payment_modulator.utils.Validations;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GatewayProxyTransactionTests extends GatewayProxyBaseTest {

	protected static PropertyFileLoader testData;
	protected static String token;

	@BeforeTest
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		token = GatewayProxyUtils.getTokenForCustomer();
	}

	@Test(enabled = true)
	public void makeGatewayProxySale() throws Exception {
		String transanctionAmount = IHGUtil.createRandomNumericString(5);
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		Response response = transaction.makeASale(token, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), transanctionAmount);

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());
	}

	@Test(enabled = true)
	public void makeGatewayProxyCredit() throws Exception {

		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);

		Response responseSale = transaction.makeASale(token, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), transanctionAmount);

		JsonPath jsonPath = new JsonPath(responseSale.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(responseSale.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		if (responseSale.getStatusCode() == 200) {

			Response response = transaction.makeARefund(token, testData.getProperty("proxy.mmid"),
					testData.getProperty("test.pay.customer.uuid"), testData.getProperty("comment"),
					testData.getProperty("customer.id"), externalTransactionId, orderId, transanctionAmount);

			validate.verifyTransactionDetails(response.asString());

		}
	}

	@Test(enabled = true)

	public void makeGatewayProxyPartialRefund() throws Exception {

		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String transanctionAmount = IHGUtil.createRandomNumericString(3);
		String diffrence = IHGUtil.createRandomNumericString(2);

		Integer partialRefundAmount = Integer.parseInt(transanctionAmount) - Integer.parseInt(diffrence);
		System.out.println("partialRefundAmount" + partialRefundAmount);

		Response responseSale = transaction.makeASale(token, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), transanctionAmount);

		JsonPath jsonpath = new JsonPath(responseSale.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(responseSale.asString());

		String externalTransactionId = jsonpath.get("externalTransactionId").toString();
		String orderId = jsonpath.get("orderId").toString();

		Response response = transaction.makeARefund(token, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), "Test  partial refund",
				testData.getProperty("customer.id"), externalTransactionId, orderId,
				String.valueOf(partialRefundAmount));

		validate.verifyTransactionDetails(response.asString());

	}

	@Test(dataProvider = "refund_data", dataProviderClass = GatewayProxyTestData.class, enabled = true)

	public void makeGatewayProxyCreditInvalidData(String token, String mmid, String testPayCustomerUuid, String comment,
			String customerId, String externalTransId, String orderId, String transactionAmount, int statusCodeVerify,
			String verifyErrorMessage) throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.makeARefund(token, mmid, testPayCustomerUuid, comment, customerId,
				externalTransId, orderId, transactionAmount);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("message") != null) {

			Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));

		}

	}

}
