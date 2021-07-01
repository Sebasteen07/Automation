// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.helpers;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import com.medfusion.payment_modulator.tests.BaseRest;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.Validations;

public class TransactionResourceDetails extends BaseRest {

	protected PropertyFileLoader testData;

	public List<String> makeAnAuthorize(String mmid, Boolean flag) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap(
				(testData.getProperty("transaction.amount")), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("authorize/" + mmid).then()
				.spec(responseSpec).and().extract().response();

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());
		List<String> transactiondetailslist = new ArrayList<String>();
		transactiondetailslist.add(jsonpath.get("externalTransactionId").toString());
		transactiondetailslist.add(jsonpath.get("orderId").toString());

		// Check to see if mmid is element. For element transactions check for Network
		// id in the response
		if (flag.equals(true)) {
			validate.verifyIfNetworkIdPresent(response.asString());
		}
		return transactiondetailslist;

	}

	public void makeACapture(String mmid, String transactionid, String orderid) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForCaptureMap(
				(testData.getProperty("transaction.amount")), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"), transactionid, orderid);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("capture/" + mmid).then()
				.spec(responseSpec).and().extract().response();
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
	}

	public List<String> makeASale(String mmid) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap(
				(testData.getProperty("transaction.amount")), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("sale/" + mmid).then()
				.spec(responseSpec).and().extract().response();

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());
		List<String> transactiondetailslist = new ArrayList<String>();
		transactiondetailslist.add(jsonpath.get("externalTransactionId").toString());
		transactiondetailslist.add(jsonpath.get("orderId").toString());
		return transactiondetailslist;

	}

	public void makeAVoid(String mmid, String transactionid) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForVoidCreditMap(
				(testData.getProperty("transaction.amount")), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"), transactionid);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("void/" + mmid).then()
				.spec(responseSpec).and().extract().response();
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

	}

	public void makeARefund(String mmid, String transactionid, String refundamount) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForVoidCreditMap(refundamount,
				testData.getProperty("account.number"), testData.getProperty("consumer.name"),
				testData.getProperty("payment.source"), testData.getProperty("cvv"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("bin"), testData.getProperty("zipcode"), testData.getProperty("last.name"),
				testData.getProperty("address.line1"), testData.getProperty("city"), testData.getProperty("state"),
				testData.getProperty("first.name"), transactionid);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("credit/" + mmid).then()
				.spec(responseSpec).and().extract().response();
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

	}

	public void makeAChargeback(String mmid, String transactionid, String orderid, String chargebackurl)
			throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForChargeback(mmid, transactionid, orderid,
				testData.getProperty("chargeback.amount"));

		Response response = given().spec(requestSpec).body(transactiondetails).when()
				.post(chargebackurl + "merchant/" + mmid + "/chargeback").then().spec(responseSpec).and().extract()
				.response();
		Validations validate = new Validations();
		validate.verifyChargebackTransactionDetails(response.asString());
	}

}