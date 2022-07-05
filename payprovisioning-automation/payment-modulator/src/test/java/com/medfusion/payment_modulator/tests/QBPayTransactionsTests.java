// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import com.medfusion.payment_modulator.utils.ModulatorTestData;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payment_modulator.helpers.TransactionResourceDetails;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.Validations;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class QBPayTransactionsTests extends BaseRest {

	protected PropertyFileLoader testData;

	@BeforeTest
	public void setBaseUri() throws Exception {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		setupResponsetSpecBuilder();

	}

	@Test(priority = 1, enabled = true)
	public void testQBPAYSale() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		String transanctionAmount = IHGUtil.createRandomNumericString(2);

		Response response = transaction.makeASale(testData.getProperty("qbpay.mmid"), transanctionAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		CommonUtils.saveTransactionDetails(jsonPath.get("externalTransactionId").toString(),
				jsonPath.get("orderId").toString());

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(!jsonPath.get("authCode").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("externalTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("orderId").toString().isEmpty());

	}

	@Test(priority = 2, dataProvider = "mod_qbpay_sale_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)
	public void testQBPAYSaleWithInvalidData(String mmid, String transactionAmount, String accountNumber,
											 String paymentSource, String cardNumber, String expiratioNumber, int statusCodeVerify,
											 String verifyErrorMessage) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response response = transaction.makeASale(mmid, transactionAmount,
				accountNumber, testData.getProperty("consumer.name"),
				paymentSource, testData.getProperty("cvv"), testData.getProperty("type"),
				cardNumber, expiratioNumber,
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorMessage));
		}
	}

	@Test(priority = 3, enabled = true)
	public void testVoidQBPAYSale() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		String transanctionAmount = IHGUtil.createRandomNumericString(3);

		Response response = transaction.makeASale(testData.getProperty("qbpay.mmid"), transanctionAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		Response responseVoidSale = transaction.makeAVoid(testData.getProperty("qbpay.mmid"),
				jsonPath.get("externalTransactionId").toString(), transanctionAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		validate.verifyTransactionDetails(responseVoidSale.asString());

	}

	@Test(priority = 4, dataProvider = "mod_qbpay_void_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)
	public void testVoidQBPAYSaleWithInvalidData(String mmid, String transactionId, String transactionAmount,
												 String accountNumber, String paymentSource, String cardNumber, String expirationNumber,
												 int statusCodeVerify, String verifyErrorMessage) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response responseVoidSale = transaction.makeAVoid(mmid,
				transactionId, transactionAmount,
				accountNumber, testData.getProperty("consumer.name"),
				paymentSource, testData.getProperty("cvv"), testData.getProperty("type"),
				cardNumber, expirationNumber,
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(responseVoidSale.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(responseVoidSale.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorMessage));
		}
	}

	@Test(priority = 5, enabled = true)
	public void testRefundQBPAYSale() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		String refundAmount = IHGUtil.createRandomNumericString(5);

		Response response = transaction.makeASale(testData.getProperty("qbpay.mmid"), refundAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		Response responseRefund = transaction.makeARefund(testData.getProperty("qbpay.mmid"),
				jsonPath.get("externalTransactionId").toString(), refundAmount, testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		JsonPath jsonPathResponseRefund = new JsonPath(responseRefund.asString());

		validate.verifyTransactionDetails(responseRefund.asString());
		Assert.assertTrue(!jsonPathResponseRefund.get("externalTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPathResponseRefund.get("orderId").toString().isEmpty());
	}

	@Test(priority = 6, dataProvider = "mod_qbpay_refund_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)
	public void testRefundQBPAYSaleWithInvalidData(String mmid, String transactionId, String refundAmount,
												   String accountNumber, String paymentSource, String cardNumber,
												   String expirationNumber, int statusCodeVerify,
												   String verifyErrorMessage) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response response = transaction.makeARefund(mmid,
				transactionId, refundAmount, accountNumber,
				testData.getProperty("consumer.name"), paymentSource,
				testData.getProperty("cvv"), testData.getProperty("type"), cardNumber,
				expirationNumber, testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorMessage));
		}
	}

	@Test(priority = 7, enabled = true)
	public void testPartialRefundQBPAYSale() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		String transanctionAmount = IHGUtil.createRandomNumericString(5);
		String diffrence = IHGUtil.createRandomNumericString(2);

		Integer partialRefundAmount = Integer.parseInt(transanctionAmount) - Integer.parseInt(diffrence);
		System.out.println("partialRefundAmount" + partialRefundAmount);

		Response response = transaction.makeASale(testData.getProperty("qbpay.mmid"), transanctionAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		Response responseRefund = transaction.makeARefund(testData.getProperty("qbpay.mmid"),
				jsonPath.get("externalTransactionId").toString(), partialRefundAmount.toString(),
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPathResponseRefund = new JsonPath(responseRefund.asString());

		validate.verifyTransactionDetails(responseRefund.asString());
		Assert.assertTrue(!jsonPathResponseRefund.get("externalTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPathResponseRefund.get("orderId").toString().isEmpty());
	}

	@Test(priority = 8, dataProvider = "mod_qbpay_refund_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)
	public void testPartialRefundQBPAYWithInvalidData(String mmid, String transactionId, String refundAmount,
													  String accountNumber, String paymentSource, String cardNumber,
													  String expirationNumber, int statusCodeVerify,
													  String verifyErrorMessage) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		String transanctionAmount = IHGUtil.createRandomNumericString(5);
		String diffrence = IHGUtil.createRandomNumericString(2);

		Integer partialRefundAmount = Integer.parseInt(transanctionAmount) - Integer.parseInt(diffrence);
		System.out.println("partialRefundAmount" + partialRefundAmount);

		Response response = transaction.makeARefund(mmid,
				transactionId, refundAmount, accountNumber,
				testData.getProperty("consumer.name"), paymentSource,
				testData.getProperty("cvv"), testData.getProperty("type"), cardNumber,
				expirationNumber, testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorMessage));
		}
	}

	@Test(priority = 9, enabled = true)
	public void testQBPAYAuthorize() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		Response response = transaction.makeAnAuthorize(testData.getProperty("qbpay.mmid"),
				testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		Assert.assertTrue(!jsonPath.get("authCode").toString().isEmpty());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		CommonUtils.saveTransactionDetails(externalTransactionId, orderId);
	}

	@Test(priority = 10, dataProvider = "mod_qbpay_authorize_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)
	public void testQBPAYAuthorizeWithInvalidData(String mmid, String transactionAmount, String accountNumber,
												   String paymentSource, String cardNumber, String expiratioNumber, int statusCodeVerify,
												   String verifyErrorMessage) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response response = transaction.makeAnAuthorize(mmid,
				transactionAmount, accountNumber,
				testData.getProperty("consumer.name"), paymentSource,
				testData.getProperty("cvv"), testData.getProperty("type"), cardNumber,
				expiratioNumber, testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorMessage));
		}
	}

	@Test(priority = 11, enabled = false)
	public void testQBPAYCapture() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);

		Response response = transaction.makeAnAuthorize(testData.getProperty("qbpay.mmid"),
				transanctionAmount, testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));
		JsonPath jsonPath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		Assert.assertTrue(!jsonPath.get("authCode").toString().isEmpty());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		Response responseCapture =	transaction.makeACapture(testData.getProperty("qbpay.mmid"),externalTransactionId,orderId,
				transanctionAmount, testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		validate.verifyTransactionDetails(responseCapture.asString());

		JsonPath jsonPathCapture = new JsonPath(responseCapture.asString());
		Assert.assertEquals(responseCapture.getStatusCode(),200);
		Assert.assertTrue(!jsonPathCapture.get("externalTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPathCapture.get("orderId").toString().isEmpty());

	}

	@Test(priority = 4, dataProvider = "mod_qbpay_capture_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = false)
	public void testQBPAYCaptureWithInvalidData(String mmid, String externalTransactionId, String orderId,
												 String transactionAmount, String accountNumber, String paymentSource, String cardNumber,
												 String expiratioNumber, int statusCodeVerify, String verifyErrorMessage) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response responseCapture = transaction.makeACapture(mmid, externalTransactionId, orderId,
				transactionAmount, accountNumber,
				testData.getProperty("consumer.name"), paymentSource,
				testData.getProperty("cvv"), testData.getProperty("type"), cardNumber,
				expiratioNumber, testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		JsonPath jsonPath1 = new JsonPath(responseCapture.asString());
		Assert.assertNotNull(jsonPath1, "Response was null");
		Assert.assertEquals(responseCapture.getStatusCode(), statusCodeVerify);

		if (jsonPath1.get("message") != null) {

			Assert.assertTrue(jsonPath1.get("message").toString().contains(verifyErrorMessage));
		}
	}
}
