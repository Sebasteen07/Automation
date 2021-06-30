// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import static io.restassured.RestAssured.given;

import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.Map;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.Validations;

public class GatewayProxyTransactionResource extends GatewayProxyBaseTest {

	protected PropertyFileLoader testData;

	public void makeASale(String mmid) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap(
				(testData.getProperty("transactionamount")), testData.getProperty("accountnumber"),
				testData.getProperty("consumername"), testData.getProperty("paymentsource"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("cardnumber"),
				testData.getProperty("expirationnumber"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("lastname"), testData.getProperty("addressline1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("firstname"));

		Response response = given().spec(requestSpec).auth().oauth2(GatewayProxyUtils.getTokenForCustomer()).body(transactiondetails).when()
				.post(testData.getProperty("testpaycustomeruuid") + "/merchant/" + testData.getProperty("proxymmid")
						+ "/sale")
				.then().and().extract().response();

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());

	}

	public void makeAuthorizeTransaction(String customerId, String mmid) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap(
				(testData.getProperty("transactionamount")), testData.getProperty("accountnumber"),
				testData.getProperty("consumername"), testData.getProperty("paymentsource"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("cardnumber"),
				testData.getProperty("expirationnumber"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("lastname"), testData.getProperty("addressline1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("firstname"));

		Response response = given().spec(requestSpec).auth().oauth2(GatewayProxyUtils.getTokenForCustomer()).body(transactiondetails).when()
				.post(customerId + "/merchant/" + mmid+ "/authorize")
				.then().and().extract().response();

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());

	}

}
