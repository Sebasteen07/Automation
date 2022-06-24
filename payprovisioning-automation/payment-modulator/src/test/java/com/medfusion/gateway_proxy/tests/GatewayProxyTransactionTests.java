// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.payment_modulator.pojos.Transactions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyTransactionResource;
import com.medfusion.gateway_proxy.utils.GatewayProxyTestData;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.Validations;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

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
	public void testGatewayProxySaleWithValidAuth() throws Exception {
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
	public void testGatewayProxySaleWithInvalidAuth() throws Exception {
		String transanctionAmount = IHGUtil.createRandomNumericString(5);
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		Response response = transaction.makeASale(token + "urf", testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), transanctionAmount);

		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 401);
		Assert.assertEquals("Authentication Failed", jsonpath.get("message"));
	}

	@Test(dataProvider = "txn_data_for_proxy_sale", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testGatewayProxySaleWithInvalidData(String mmid, String customeruuid, String txnAmount)
			throws Exception {

		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		Response response = transaction.makeASale(token, mmid, customeruuid, txnAmount);

		JsonPath jsonpath = new JsonPath(response.asString());

		if (response.getStatusCode() == 400) {
			if (response.getHeaders().toString().contains("text/html")) {
				Assert.assertTrue(response.asString().contains("Bad Request"));
			} else {
				Assert.assertTrue(!jsonpath.get("error").toString().isEmpty());
			}
		} else if (response.getStatusCode() == 403) {
			Assert.assertTrue(jsonpath.get("error").toString().contains("Forbidden"));
		} else {
			Assert.assertTrue(response.getStatusCode() == 404);
			Assert.assertTrue(!jsonpath.get("message").toString().isEmpty());
			Assert.assertTrue(jsonpath.get("error").toString().contains("Not Found"));
		}
	}

	@Test(enabled = true)
	public void testGatewayProxyCredit() throws Exception {

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

	public void testGatewayProxyPartialRefund() throws Exception {

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

	public void testGatewayProxyCreditInvalidData(String token, String mmid, String testPayCustomerUuid, String comment,
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

	@Test(enabled = true)
	public void testGatewayAuthorizeByValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"));

		JsonPath jsonpath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());
	}

	@Test(enabled = true)
	public void testGatewayAuthorizeByInvalidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		String token = GatewayProxyUtils.getTokenForCustomer() + "fgh";

		Response response = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"));

		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 401);
		Assert.assertEquals("Authentication Failed", jsonpath.get("message"));
	}

	@Test(dataProvider = "txn_data_for_http_400_statuscodes", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testGatewayAuthorizeForInvalidData(String paymentSource, String type, String cardNumber, String expiry,
			String customeruuid, String mmid) throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.makeAuthorizeTransaction(token, paymentSource, type, cardNumber, expiry,
				customeruuid, mmid);

		Assert.assertEquals(response.getStatusCode(), 400);
	}

	@Test(enabled = true)
	public void testGatewayCaptureByValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response authResponse = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"));

		JsonPath jsonpath = new JsonPath(authResponse.asString());

		Response captureResponse = transaction.makeCaptureTransaction(token, testData.getProperty("payment.source"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"), jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());

		JsonPath jsonpathCapture = new JsonPath(captureResponse.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(captureResponse.asString());

		CommonUtils.saveTransactionDetails(jsonpathCapture.get("externalTransactionId").toString(),
				jsonpathCapture.get("orderId").toString());
	}

	@Test(enabled = true)
	public void testGatewayCaptureByInvalidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response authResponse = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"));

		JsonPath jsonpath = new JsonPath(authResponse.asString());

		String tokenForCapture = GatewayProxyUtils.getTokenForCustomer() + "hggf";

		Response captureResponse = transaction.makeCaptureTransaction(tokenForCapture,
				testData.getProperty("payment.source"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
				jsonpath.get("externalTransactionId").toString(), jsonpath.get("orderId").toString());

		JsonPath jsonpathCapture = new JsonPath(captureResponse.asString());
		Assert.assertTrue(captureResponse.getStatusCode() == 401);
		Assert.assertEquals("Authentication Failed", jsonpathCapture.get("message"));
	}

	@Test(dataProvider = "txn_data_for_http_400_statuscodes", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testGatewayCaptureForInvalidData(String paymentSource, String type, String cardNumber, String expiry,
			String customeruuid, String mmid) throws Exception {

		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response authResponse = transaction.makeAuthorizeTransaction(token, testData.getProperty("payment.source"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"));

		JsonPath jsonpath = new JsonPath(authResponse.asString());

		Response captureResponse = transaction.makeCaptureTransaction(token, paymentSource, type, cardNumber, expiry,
				customeruuid, mmid, jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());

		Assert.assertEquals(captureResponse.getStatusCode(), 400);
	}

	@Test(enabled = true)
	public void testGatewayProxyVoidByValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);

		Response responseSale = transaction.makeASale(token, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), transanctionAmount);

		JsonPath jsonPath = new JsonPath(responseSale.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(responseSale.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		Response response = transaction.makeAVoid(token, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("comment"),
				testData.getProperty("customer.id"), externalTransactionId, orderId);

		validate.verifyTransactionDetails(response.asString());

	}

	@Test(enabled = true)
	public void testGatewayProxyVoidByInvalidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String tokenForVoid = GatewayProxyUtils.getTokenForCustomer() + "fgh";

		Response voidResponse = transaction.makeAVoid(tokenForVoid, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("comment"),
				testData.getProperty("customer.id"), testData.getProperty("external.transaction.id"),
				testData.getProperty("order.id"));

		JsonPath jsonpathVoid = new JsonPath(voidResponse.asString());
		Assert.assertTrue(voidResponse.getStatusCode() == 401);
		Assert.assertEquals("Authentication Failed", jsonpathVoid.get("message"));

	}

	@Test(dataProvider = "void_data", dataProviderClass = GatewayProxyTestData.class, enabled = true)

	public void testGatewayProxyVoidForInvalidData(String token, String mmid, String testPayCustomerUuid,
			String comment, String customerId, String externalTransId, String orderId, int statusCodeVerify,
			String verifyErrorMessage) throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.makeAVoid(token, mmid, testPayCustomerUuid, comment, customerId,
				externalTransId, orderId);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("message") != null) {

			Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));

		}

	}

	@Test(enabled = true)
	public void testGatewayProxyToGetTransactionDataByValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		Response response = transaction.getATransactionData(token, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("external.transaction.id"),
				testData.getProperty("order.id"));
		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertEquals(response.getStatusCode(), 200);
		String cardnumber = testData.getProperty("card.number");
		String expectedCard = cardnumber.substring(cardnumber.length() - 4);
		String actualCard = jsonpath.get("cardNumberLastFour");
		Assert.assertEquals(expectedCard, actualCard);
		String expectedMMID = testData.getProperty("proxy.mmid");
		String actualMMID = jsonpath.get("mmid").toString();
		Assert.assertEquals(expectedMMID, actualMMID);
		Assert.assertTrue(
				jsonpath.get("cardType").equals("VISA") || jsonpath.get("cardType").equals("American Express")
						|| jsonpath.get("cardType").equals("Discover") || jsonpath.get("cardType").equals("MasterCard"),
				"Response message was: " + jsonpath.get("message"));
		Assert.assertTrue(
				jsonpath.get("paymentSource").equals("VCS") || jsonpath.get("paymentSource").equals("CPOS")
						|| jsonpath.get("paymentSource").equals("OLBP") || jsonpath.get("paymentSource").equals("PAYN")
						|| jsonpath.get("paymentSource").equals("BDGB") || jsonpath.get("paymentSource").equals("CHBK")
						|| jsonpath.get("paymentSource").equals("PRCC") || jsonpath.get("paymentSource").equals("PRCB"),
				"Response message was: " + jsonpath.get("message"));

		Assert.assertTrue(!jsonpath.get("orderId").toString().isEmpty(), "orderId was not found in the response");
		Assert.assertTrue(!jsonpath.get("transactionId").toString().isEmpty(),
				"transactionId was not found in the response");
		Assert.assertTrue(!jsonpath.get("submittedDate").toString().isEmpty());

	}

	@Test(enabled = true)
	public void testGatewayProxyToGetTransactionDataByInalidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String token = GatewayProxyUtils.getTokenForCustomer() + "7fgh";
		Response response = transaction.getATransactionData(token, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("external.transaction.id"),
				testData.getProperty("order.id"));
		JsonPath jsonForGet = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 401);
		Assert.assertEquals("Authentication Failed", jsonForGet.get("message"));

	}

	@Test(enabled = true)
	public void testGPGetTransactionDataWithValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		long startDate = getEpochDate(-1);
		long endDate = getEpochDate(1);

		Response response = transaction.getTransactions(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"), startDate, endDate, "");

		JsonPath jsonPath = new JsonPath(response.asString());
		if(response.getStatusCode() == 204){
			Assert.assertNull(jsonPath);
		}
		else {
			Assert.assertNotNull(jsonPath, "Response was null");
			jsonPath = response.jsonPath();

			List<String> allTxns = jsonPath.getList("transactionId");
			for(String txn : allTxns)
			{
				Assert.assertTrue(!jsonPath.get("cardType").toString().isEmpty(), "cardType was not found in the response");
				Assert.assertTrue(!jsonPath.get("cardNumberLastFour").toString().isEmpty(), "cardNumberLastFour was not found in the response");
				Assert.assertTrue(!jsonPath.get("transactionId").toString().isEmpty(), "Order id was not found in the response");
				Assert.assertTrue(!jsonPath.get("orderId").toString().isEmpty(), "Order id was not found in the response");
				Assert.assertTrue(!jsonPath.get("status").toString().isEmpty(), "Order id was not found in the response");
				Assert.assertTrue(!jsonPath.get("purchaseAmount").toString().isEmpty(), "Order id was not found in the response");
			}
		}
	}

	@Test(dataProvider = "get_txns_for_different_sources", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testGPGetTransactionsForDifferentPaymentSources(String customeruuid, String mmid,
																String startDate, String endDate,
																String paymentSource) throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.getTransactions(token, customeruuid,
				mmid, Long.parseLong(startDate), Long.parseLong(endDate), paymentSource);

		JsonPath jsonPath = new JsonPath(response.asString());
		if(response.getStatusCode() == 204){
			Assert.assertNotNull(jsonPath, "Value like io.restassured.path.json.JsonPath@4afd21c6 is found");
		}
		else {
			Assert.assertNotNull(jsonPath, "Response was null");
			if(paymentSource.equalsIgnoreCase("CPOS")){
				Assert.assertEquals(jsonPath.get("paymentSource"), "CPOS");

			}
		}
	}

	@Test(enabled = true)
	public void testGPGetTransactionDataWithInvalidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		long startDate = getEpochDate(-1);
		long endDate = getEpochDate(0);

		Response response = transaction.getTransactions(token+"erf", testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"), startDate, endDate, testData.getProperty("payment.source"));

		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 401);
		Assert.assertEquals("Authentication Failed", jsonpath.get("message"));
	}

	@Test(dataProvider = "get_txn", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testGPGetTransactionDataWithInvalidData(String customeruuid, String mmid,
														long startDate, long endDate,
														String paymentSource) throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.getTransactions(token, customeruuid,
				mmid, startDate, endDate, paymentSource);

		JsonPath jsonpath = new JsonPath(response.asString());

		if(response.getStatusCode() == 400) {
			if (response.getHeaders().toString().contains("text/html")) {
				Assert.assertTrue(response.asString().contains("Bad Request"));
			}
			else {
				Assert.assertTrue(!jsonpath.get("error").toString().isEmpty());
				Assert.assertTrue(!jsonpath.get("message").toString().isEmpty());
			}
		}
		else if(response.getStatusCode() == 403){
			Assert.assertTrue(jsonpath.get("error").toString().contains("Forbidden"));
		}
		else {
			Assert.assertTrue(response.getStatusCode() == 404);
			Assert.assertTrue(!jsonpath.get("message").toString().isEmpty());
			Assert.assertTrue(jsonpath.get("error").toString().contains("Not Found"));
		}
	}

	@Test(enabled = true)
	public void testGPGetTransactionDataAsList() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		long startDate = getEpochDate(-2);
		long endDate = getEpochDate(0);

		List<Transactions> transactions = transaction.getTransactionsAsList(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"), startDate, endDate, "");
		Validations validate = new Validations();
		validate.verifyTransactionList(transactions);
	}


	@Test(enabled = true)
	public void testGetReceiptDataWithValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.getReceiptData(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("element.mmid"), testData.getProperty("proxy.cpos.transactionId"),
				testData.getProperty("proxy.cpos.orderId"));

		Validations validate = new Validations();
		validate.verifyReceiptDetails(response.asString(), "CPOS");
	}

	@Test(enabled = true)
	public void testGetReceiptDataWithInvalidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.getReceiptData(token+"erf", testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("element.mmid"), testData.getProperty("proxy.cpos.transactionId"),
				testData.getProperty("proxy.cpos.orderId"));

		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 401);
		Assert.assertEquals("Authentication Failed", jsonpath.get("message"));
	}

	@Test(dataProvider = "get_txns_for_receipt_data", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testGetReceiptDataWithDifferentPaymentTypes(String customeruuid, String mmid,
															  String transactionId, String orderId,
															  String paymentSource, int statusCode) throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.getReceiptData(token, customeruuid,
				mmid, transactionId, orderId);
		Validations validate = new Validations();
		Assert.assertTrue(response.getStatusCode() == statusCode);
		if(response.getStatusCode() == 200) {
			validate.verifyReceiptDetails(response.asString(), paymentSource);
		}
	}

}
