// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import static io.restassured.RestAssured.given;
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

	public void makeASale(String mmid) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap(
				(testData.getProperty("transaction.amount")), testData.getProperty("account.number"),
				testData.getProperty("consumer.name"), testData.getProperty("payment.source"),
				testData.getProperty("cvv"), testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("bin"), testData.getProperty("zipcode"),
				testData.getProperty("last.name"), testData.getProperty("address.line1"), testData.getProperty("city"),
				testData.getProperty("state"), testData.getProperty("first.name"));

		Response response = given().spec(requestSpec).log().all().body(transactiondetails).when()
				.post(testData.getProperty("test.pay.customer.uuid") + "/merchant/" + testData.getProperty("proxy.mmid")
						+ "/sale")
				.then().and().extract().response();

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());
		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());

	}

}
