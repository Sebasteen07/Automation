// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payment_modulator.helpers.TransactionResourceDetails;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.ModulatorTestData;
import com.medfusion.payment_modulator.utils.Validations;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ElementTransactionsTests extends BaseRest {

	protected static PropertyFileLoader testData;
	Boolean flag = true;

	@BeforeTest
	public void setUp() throws IOException {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		setupResponsetSpecBuilder();
	}

	@Test(priority = 1, enabled = true)
	public void testMakeElementAuthorize() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		Response response = transaction.makeAnAuthorize(testData.getProperty("element.mmid"),
				testData.getProperty("transaction.amount"), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));
		JsonPath jsonPath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(!jsonPath.get("authCode").toString().isEmpty());

		// Check to see if mmid is element. For element transactions check for Network
		// id in the response
		validate.verifyIfNetworkIdPresent(response.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();
		CommonUtils.saveTransactionDetails(externalTransactionId, orderId);

	}

	@Test(priority = 2, dataProvider = "mod_ele_authorize_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)

	public void testMakeElementAuthorizeInvalidValidations(String mmid, String transactionAmount, String accountNumber,
			String paymentSource, String cardNumber, String expiratioNumber, int statusCodeVerify,
			String verifyErrorText, String messageText) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response response = transaction.makeAnAuthorize(mmid, transactionAmount, accountNumber,
				testData.getProperty("consumer.name"), paymentSource, testData.getProperty("cvv"),
				testData.getProperty("type"), cardNumber, expiratioNumber, testData.getProperty("bin"),
				testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null && jsonPath.get("message") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorText));
			Assert.assertTrue(jsonPath.get("message").toString().contains(messageText));
		}
	}

	@Test(priority = 3, enabled = true)
	public void testMakeACapture() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);

		Response response = transaction.makeAnAuthorize(testData.getProperty("element.mmid"), transanctionAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));
		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		Response responseCapture = transaction.makeACapture(testData.getProperty("element.mmid"), externalTransactionId,
				"testorderId", transanctionAmount, testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		JsonPath jsonPathCapture = new JsonPath(responseCapture.asString());
		Assert.assertEquals(responseCapture.getStatusCode(), 404);
		Assert.assertTrue(jsonPathCapture.get("error").toString().contains("Not Found"));
		Assert.assertTrue(jsonPathCapture.get("message").toString().contains("Could not find transactionAmount"));

		responseCapture = transaction.makeACapture(testData.getProperty("element.mmid"), externalTransactionId,
				orderId, transanctionAmount, testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		validate.verifyTransactionDetails(responseCapture.asString());

		jsonPathCapture = new JsonPath(responseCapture.asString());

		Assert.assertEquals(responseCapture.getStatusCode(), 200);
		Assert.assertTrue(!jsonPathCapture.get("externalTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPathCapture.get("orderId").toString().isEmpty());

	}

	@Test(priority = 4, dataProvider = "mod_ele_capture_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)

	public void testMakeElementCaptureInvalidValidations(String mmid, String externalTransactionId,
			String transactionAmount, int statusCodeVerify, String verifyErrorMessage) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);

		Response response = transaction.makeAnAuthorize(testData.getProperty("element.mmid"), transanctionAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));
		JsonPath jsonPath = new JsonPath(response.asString());

		String orderId = jsonPath.get("orderId").toString();

		Response responseCapture = transaction.makeACapture(mmid, externalTransactionId, orderId, transactionAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"),
				testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath1 = new JsonPath(responseCapture.asString());
		Assert.assertNotNull(jsonPath1, "Response was null");
		Assert.assertEquals(responseCapture.getStatusCode(), statusCodeVerify);

		if (jsonPath1.get("error") != null) {
			Assert.assertTrue(jsonPath1.get("error").toString().contains(verifyErrorMessage));
		}
	}

	@Test(priority = 5, enabled = true)
	public void testMakeAnElementSale() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		String transanctionAmount = IHGUtil.createRandomNumericString(5);

		Response response = transaction.makeASale(testData.getProperty("element.mmid"), transanctionAmount.toString(),
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

	@Test(priority = 6, dataProvider = "mod_ele_sale_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)

	public void testMakeElementSaleInvalidValidations(String mmid, String transactionAmount, String accountNumber,
			String paymentSource, String cardNumber, String expiratioNumber, int statusCodeVerify,
			String verifyErrorText, String messageText) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response response = transaction.makeASale(mmid, transactionAmount, accountNumber,
				testData.getProperty("consumer.name"), paymentSource, testData.getProperty("cvv"),
				testData.getProperty("type"), cardNumber, expiratioNumber, testData.getProperty("bin"),
				testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null && jsonPath.get("message") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorText));
			Assert.assertTrue(jsonPath.get("message").toString().contains(messageText));
		}
	}

	@Test(priority = 7, enabled = true)
	public void testVoidAnElementSale() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		String intialAmount = IHGUtil.createRandomNumericString(5);
		String diffrence = IHGUtil.createRandomNumericString(3);
		Integer transanctionAmount = Integer.parseInt(intialAmount) - Integer.parseInt(diffrence);

		Response response = transaction.makeASale(testData.getProperty("element.mmid"), transanctionAmount.toString(),
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		String transactionId = jsonPath.get("externalTransactionId").toString();

		Response responseVoidSale = transaction.makeAVoid(testData.getProperty("element.mmid"),
				transactionId, transanctionAmount.toString(),
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		validate.verifyTransactionDetails(responseVoidSale.asString());
		JsonPath jsonPathVoid = new JsonPath(response.asString());
		Assert.assertTrue(!jsonPathVoid.get("externalTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPathVoid.get("orderId").toString().isEmpty());
	}

	@Test(priority = 8, dataProvider = "mod_ele_void_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)

	public void testMakeElementVoidInvalidValidations(String mmid, String transactionId, String transactionAmount,
			String accountNumber, String paymentSource, String cardNumber, String expiratioNumber, int statusCodeVerify,
			String verifyErrorText, String messageText) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response response = transaction.makeAVoid(mmid, transactionId, transactionAmount, accountNumber,
				testData.getProperty("consumer.name"), paymentSource, testData.getProperty("cvv"),
				testData.getProperty("type"), cardNumber, expiratioNumber, testData.getProperty("bin"),
				testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null && jsonPath.get("message") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorText));
			Assert.assertTrue(jsonPath.get("message").toString().contains(messageText));
		}
	}

	@Test(priority = 9, enabled = true)
	public void testRefundAnElementSale() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();
		String refundAmount = IHGUtil.createRandomNumericString(5);

		Response response = transaction.makeASale(testData.getProperty("element.mmid"), refundAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		Response responseRefund = transaction.makeARefund(testData.getProperty("element.mmid"),
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

	@Test(priority = 10, dataProvider = "mod_ele_refund_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)

	public void testMakeElementRefundInvalidValidations(String mmid, String transactionId, String refundAmount,
			String accountNumber, String paymentSource, String cardNumber, String expiratioNumber, int statusCodeVerify,
			String verifyErrorText, String verifyMessageText) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response response = transaction.makeARefund(mmid, transactionId, refundAmount, accountNumber,
				testData.getProperty("consumer.name"), paymentSource, testData.getProperty("cvv"),
				testData.getProperty("type"), cardNumber, expiratioNumber, testData.getProperty("bin"),
				testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorText));
			Assert.assertTrue(jsonPath.get("message").toString().contains(verifyMessageText));
		}
	}

	@Test(priority = 11, enabled = true)
	public void testPartialRefundAnElementSale() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		String transanctionAmount = IHGUtil.createRandomNumericString(5);
		String diffrence = IHGUtil.createRandomNumericString(2);

		Integer partialRefundAmount = Integer.parseInt(transanctionAmount) - Integer.parseInt(diffrence);
		System.out.println("partialRefundAmount" + partialRefundAmount);

		Response response = transaction.makeASale(testData.getProperty("element.mmid"), transanctionAmount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		Response responseRefund = transaction.makeARefund(testData.getProperty("element.mmid"),
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

	@Test(priority = 12, enabled = true)
	public void testCreateChargeBack() throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		String intialAmount = IHGUtil.createRandomNumericString(4);
		String difference = IHGUtil.createRandomNumericString(2);
		Integer chargeBackAmount = Integer.parseInt(intialAmount) - Integer.parseInt(difference);

		Response response = transaction.makeASale(testData.getProperty("element.mmid"), chargeBackAmount.toString(),
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();
		CommonUtils.saveTransactionDetails(externalTransactionId, orderId);

		Response responseChargeBack = transaction.makeAChargeback(testData.getProperty("element.mmid"),
				jsonPath.get("externalTransactionId").toString(), jsonPath.get("orderId").toString(),
				testData.getProperty("chargeback.from.moduator"), chargeBackAmount.toString());

		JsonPath jsonPathResponseChargeback = new JsonPath(responseChargeBack.asString());

		Assert.assertTrue(!jsonPathResponseChargeback.get("parentExternalTransactionId").toString().isEmpty());

		Assert.assertTrue(!jsonPathResponseChargeback.get("parentOrderId").toString().isEmpty());

		Assert.assertTrue(!jsonPathResponseChargeback.get("orderId").toString().isEmpty());
		Assert.assertEquals(jsonPath.get("externalTransactionId"),
				jsonPathResponseChargeback.get("parentExternalTransactionId"));

		String expectedChargebackAmount = jsonPathResponseChargeback.get("chargebackAmount").toString();
		if (jsonPathResponseChargeback.get("chargebackAmount").toString().charAt(0) == '0') {
			expectedChargebackAmount.toString().replaceFirst("0", "");
		}
		Assert.assertEquals(chargeBackAmount.toString().trim(), expectedChargebackAmount.trim());

	}

	@Test(priority = 13, dataProvider = "mod_ele_chargeback_invalid_data", dataProviderClass = ModulatorTestData.class, enabled = true)

	public void makeElementChargeBackInvalidValidations(String mmid, String transactionId, String orderId,
			String chargeBackAmount, int statusCodeVerify, String verifyErrorText, String verifyMessageText) throws Exception {

		TransactionResourceDetails transaction = new TransactionResourceDetails();

		Response response = transaction.makeAChargeback(mmid, transactionId, orderId,
				testData.getProperty("chargeback.from.moduator"), chargeBackAmount);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("error") != null) {

			Assert.assertTrue(jsonPath.get("error").toString().contains(verifyErrorText));
			Assert.assertTrue(jsonPath.get("message").toString().contains(verifyMessageText));
		}
	}

}
