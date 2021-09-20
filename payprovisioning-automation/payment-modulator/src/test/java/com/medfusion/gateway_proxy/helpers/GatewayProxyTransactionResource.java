// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import static io.restassured.RestAssured.given;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.payment_modulator.pojos.Transactions;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.payment_modulator.pojos.PayloadDetails;

public class GatewayProxyTransactionResource extends GatewayProxyBaseTest {

	protected PropertyFileLoader testData;

	public Response makeASale(String token, String mmid, String testPayCustomerUuid, String transactionAmount)
			throws NullPointerException, Exception {
		testData = new PropertyFileLoader();

		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap((transactionAmount),
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		Response response = given().spec(requestSpec).auth().oauth2(token).log().all().body(transactiondetails).when()
				.post(testPayCustomerUuid + "/merchant/" + mmid + "/sale").then().and().extract().response();

		return response;
	}

	public Response makeARefund(String token, String mmid, String testPayCustomerUuid, String comment,
			String customerId, String externalTransId, String orderId, String transactionAmount)
			throws NullPointerException, Exception {
		testData = new PropertyFileLoader();

		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForRefundSaleMap(comment, customerId,
				externalTransId, orderId, transactionAmount);

		Response response = given().spec(requestSpec).auth().oauth2(token).log().all().body(transactiondetails).when()
				.post(testPayCustomerUuid + "/merchant/" + mmid + "/credit").then().and().extract().response();

		return response;
	}

	public Response makeAuthorizeTransaction(String token, String paymentSource, String cardType, String cardNo,
			String expiry, String customeruuid, String mmid) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap(
				(testData.getProperty("transaction.amount")), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), paymentSource, testData.getProperty("cvv"), cardType, cardNo,
				expiry, testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"));

		Response response = given().spec(requestSpec).auth().oauth2(token).log().all().body(transactiondetails).when()
				.post(customeruuid + "/merchant/" + mmid + "/authorize").then().and().extract().response();
		return response;
	}

	public Response makeCaptureTransaction(String token, String paymentSource, String cardType, String cardNo,
			String expiry, String customeruuid, String mmid, String txnid, String orderid) throws Exception {
		testData = new PropertyFileLoader();

		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForCaptureMap(
				IHGUtil.createRandomNumericString(3), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), paymentSource, testData.getProperty("cvv"), cardType, cardNo,
				expiry, testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"), txnid, orderid);

		Response response = given().spec(requestSpec).auth().oauth2(token).log().all().body(transactiondetails).when()
				.post(customeruuid + "/merchant/" + mmid + "/capture").then().and().extract().response();
		return response;
	}

	public Response makeAVoid(String token, String mmid, String testPayCustomerUuid, String comment, String customerId,
			String externalTransId, String orderId) throws NullPointerException, Exception {
		testData = new PropertyFileLoader();

		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForVoidSaleMap(comment, customerId,
				externalTransId, orderId);

		Response response = given().spec(requestSpec).auth().oauth2(token).log().all().body(transactiondetails).when()
				.post(testPayCustomerUuid + "/merchant/" + mmid + "/void").then().and().extract().response();

		return response;
	}

	public Response getATransactionData(String token, String mmid, String testPayCustomerUuid, String externalTransId,
			String orderId) throws NullPointerException, Exception {
		Response response = given().spec(requestSpec).auth().oauth2(token).when().get(
				testPayCustomerUuid + "/merchant/" + mmid + "/transaction/" + externalTransId + "/orderId/" + orderId)
				.then().extract().response();

		return response;

	}

	public Response getTransactions(String token, String customeruuid,
									String mmid, long epochStartDate,
									long epochEndDate, String paymentSource) throws IOException {
		testData = new PropertyFileLoader();

		Response response = given().that().spec(requestSpec).auth().oauth2(token).log().all()
				.param("startDate", epochStartDate)
				.param("endDate",epochEndDate)
				.param("paymentSource",paymentSource).when()
				.get(customeruuid + "/merchant/" + mmid +"/transactions").then().and()
				.extract().response();

		return response;
	}

	public List getTransactionsAsList(String token, String customeruuid,
									  String mmid, long epochStartDate,
									  long epochEndDate, String paymentSource) throws IOException {
		testData = new PropertyFileLoader();

		return given().that().spec(requestSpec).auth().oauth2(token).log().all()
				.param("startDate", epochStartDate)
				.param("endDate",epochEndDate)
				.param("paymentSource",paymentSource).when()
				.get(customeruuid + "/merchant/" + mmid +"/transactions").then().and()
				.extract().body().jsonPath().getList(".", Transactions.class);

	}

	public Response getReceiptData(String token, String customeruuid,
									String mmid, String transactionId, String orderId) throws IOException {
		testData = new PropertyFileLoader();

		Response response = given().that().spec(requestSpec).auth().oauth2(token).when()
				.get(customeruuid + "/merchant/" + mmid +"/transaction/" + transactionId + "/orderId/" + orderId + "/receipt").then().and()
				.extract().response();

		return response;
	}


}
