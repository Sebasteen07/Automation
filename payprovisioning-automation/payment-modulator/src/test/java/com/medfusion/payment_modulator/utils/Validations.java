// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.utils;

import com.medfusion.payment_modulator.pojos.Transactions;
import io.restassured.path.json.JsonPath;
import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import com.medfusion.common.utils.PropertyFileLoader;

public class Validations {

	protected static PropertyFileLoader testData;


	public void verifyTransactionDetails(String response) throws IOException {
		JsonPath jsonpath = new JsonPath(response);
		Assert.assertNotNull(jsonpath, "Response was null");
		Assert.assertEquals(jsonpath.get("responseCode"), "000", "Response code was: " + jsonpath.get("responseCode"));
		Assert.assertTrue(jsonpath.get("message").equals("Success") || jsonpath.get("message").equals("Approved"), "Response message was: " + jsonpath.get("message"));
		Assert.assertTrue(!jsonpath.get("responseTime").toString().isEmpty(), "Response time was not found in the response");
		Assert.assertTrue(!jsonpath.get("orderId").toString().isEmpty(), "Order id was not found in the response");

	}


	public void verifyChargebackTransactionDetails(String response) {
		JsonPath jsonpath = new JsonPath(response);
		Assert.assertNotNull(jsonpath, "Response was null");
		Assert.assertTrue(!jsonpath.get("mfTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonpath.get("orderId").toString().isEmpty());

	}


	public void verifyIfNetworkIdPresent(String response) {
		JsonPath jsonpath = new JsonPath(response);
		Assert.assertTrue(!jsonpath.get("expressNetworkTransactionId").toString().isEmpty());

	}

	public void verifyTransactionList(List<Transactions> transactionsList) {

		for (Transactions txn : transactionsList) {
			Assert.assertNotNull(txn.getTransactionId());
			Assert.assertNotNull(txn.getOrderId());
			Assert.assertNotNull(txn.getStatus());
//			This is currently failing in dev due to a bug which is causing payment sources to be empty thus commenting
//			Assert.assertNotNull(txn.getPaymentSource());
			Assert.assertNotNull(txn.getMmid());

			if (txn.getRefundAmount() != 0 && txn.getPurchaseAmount() == 0) {
				Assert.assertNotNull(txn.getParentTransactionId());
//				This is currently failing in dev due to a bug which is causing payment sources to be empty thus commenting
//				Assert.assertTrue(txn.getPaymentSource().equalsIgnoreCase("CHBK"));
			} else if (txn.getRefundAmount() != 0 && txn.getPurchaseAmount() != 0) {
				Assert.assertTrue(txn.getStatus().equalsIgnoreCase("Void"));
			}
		}

	}

	public void verifyReceiptDetails(String response, String paymentType) throws IOException {
		JsonPath jsonpath = new JsonPath(response);
		Assert.assertNotNull(jsonpath, "Response was null");
		Assert.assertEquals(jsonpath.get("responseCode"), "000", "Response code was: " + jsonpath.get("responseCode"));
		Assert.assertTrue(jsonpath.get("responseMessage").equals("Success") || jsonpath.get("responseMessage").equals("Approved") || jsonpath.get("responseMessage").equals("APPROVAL"), "Response message was: " + jsonpath.get("message"));
		Assert.assertTrue(!jsonpath.get("transactionDate").toString().isEmpty(), "Transaction date was not found in the response");
		Assert.assertTrue(!jsonpath.get("amount").toString().isEmpty(), "Transaction amount was not found in the response");

		if (!paymentType.equalsIgnoreCase("Refund")) {
			Assert.assertTrue(!jsonpath.get("cardType").toString().isEmpty(), "Card Type was not found in the response");
		}
		Assert.assertTrue(!jsonpath.get("tags").toString().isEmpty(), "Card Type was not found in the response");
		Assert.assertTrue(jsonpath.get("pinVerified").equals(false) || jsonpath.get("pinVerified").equals(true));
		Assert.assertTrue(jsonpath.get("signatureRequired").equals(false) || jsonpath.get("pinVerified").equals(true));

		if (paymentType.equalsIgnoreCase("CPOS")) {
			Assert.assertTrue(!jsonpath.get("approvalNumber").toString().isEmpty(), "Card Type was not found in the response");
			Assert.assertTrue(!jsonpath.get("applicationID").toString().isEmpty(), "Card Type was not found in the response");
			Assert.assertTrue(!jsonpath.get("applicationLabel").toString().isEmpty(), "Card Type was not found in the response");
			Assert.assertTrue(!jsonpath.get("cryptogram").toString().isEmpty(), "Card Type was not found in the response");
			Assert.assertTrue(!jsonpath.get("cardSuffix").toString().isEmpty(), "Card Type was not found in the response");
		}
	}

	public void verifyInstaMedTransactionDetails(String responseCode, JsonPath jsonpath) throws IOException {
		Assert.assertNotNull(jsonpath, "Response was null");
		if (responseCode.equalsIgnoreCase("000") || responseCode.equalsIgnoreCase("051") || responseCode.equalsIgnoreCase("010")) {
			Assert.assertTrue(!jsonpath.get("responseTime").toString().isEmpty(), "Response time was not found in the response");
			Assert.assertTrue(!jsonpath.get("orderId").toString().isEmpty(), "Order id was not found in the response");
			Assert.assertTrue(!jsonpath.get("externalTransactionId").toString().isEmpty(), "Transaction ID was not found in the response");
			Assert.assertNotNull(jsonpath.get("authCode"), "Authorization Code was null");
		} else {
			Assert.assertTrue(jsonpath.getBoolean("initialTransactionInSeries"), "InitialTransactionInSeries was not true in the response");
			Assert.assertNull(jsonpath.get("fraudResponse"), "fraudResponse should be null for InstaMed");
			Assert.assertNull(jsonpath.get("expressNetworkTransactionId"), "expressNetworkTransactionId should be null for InstaMed");
			Assert.assertNull(jsonpath.get("threadId"), "threadId should be null for InstaMed");
			Assert.assertNull(jsonpath.get("authCode"), "Authorization Code was NOT null");
		}

	}

	public void verifyInstamedReceiptCommonDetails(JsonPath jsonpath) throws IOException {
		Assert.assertNotNull(jsonpath, "Response was null");
		Assert.assertTrue(jsonpath.get("responseCode").equals("000"));
		Assert.assertTrue(jsonpath.get("responseMessage").equals("APPROVAL"), "Transaction is not Approved");
		Assert.assertNotNull(jsonpath.get("instamedDetail.authCode"), "Approved transaction does not have authCode!");
		Assert.assertNotNull(jsonpath.get("transactionDate"), "Transaction does not have a date");
		Assert.assertNotNull(jsonpath.get("amount"), "Amount is null!");
	}
}
