// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GatewayProxyDigitalWalletResource extends GatewayProxyBaseTest {

	protected PropertyFileLoader testData;

	public Response createNewWallet() throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToDigitalWallet(
				testData.getProperty("consumername"), testData.getProperty("type"), testData.getProperty("cardnumber"),
				testData.getProperty("expirationnumber"), testData.getProperty("cardalias"),
				testData.getProperty("zipcode"));

		Response response = given().spec(requestSpec).body(digitalWallet).when()
				.post(testData.getProperty("testpaycustomeruuid") + "/wallets").then().spec(responseSpec).and()
				.extract().response();
		return response;
	}

	public Response getListOfCardsInWallet() throws IOException {

		testData = new PropertyFileLoader();

		Response response = given().that().spec(requestSpec).when()
				.get(testData.getProperty("testpaycustomeruuid") + "/wallets/"
						+ testData.getProperty("externalWalletId"))
				.then().spec(responseSpec).and().extract().response();

		return response;

	}

	public Response saleAPI(String customerUUID, String mmid, String externalWalletID, String externalCardID)
			throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForNewSaleAPI(
				testData.getProperty("payment.source"), Integer.parseInt(IHGUtil.createRandomNumericString(4)));

		Response response = given()
				.spec(requestSpec).body(digitalWallet).when().post(customerUUID + "/merchant/" + mmid + "/wallet/"
						+ externalWalletID + "/card/" + externalCardID + "/sale")
				.then().spec(responseSpec).and().extract().response();
		return response;
	}
}
