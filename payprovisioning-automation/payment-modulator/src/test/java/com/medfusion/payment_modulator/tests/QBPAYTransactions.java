// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

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

public class QBPAYTransactions extends BaseRest {

	protected PropertyFileLoader testData;
	Boolean flag = false;

	@BeforeTest
	public void setBaseUri() throws Exception {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		setupResponsetSpecBuilder();

	}

	@Test
	public void makeAQBPAYSale() throws Exception {

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

	@Test
	public void voidAQBPAYSale() throws Exception {

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

	@Test
	public void refundAQBPAYSale() throws Exception {

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

	@Test
	public void partialRefundAQBPAYSale() throws Exception {

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
}
