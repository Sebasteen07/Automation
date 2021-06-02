// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.payment_modulator.pojos.BillToAddress;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GatewayProxyDigitalWalletResource extends GatewayProxyBaseTest {

	protected PropertyFileLoader testData;

	public Response deleteCardInWallet(String token, String walletId, String externalcardId) throws IOException {

		testData = new PropertyFileLoader();

		Response response = given().spec(requestSpec).auth().oauth2(token).when().delete(
				testData.getProperty("testpaycustomeruuid") + "/wallets/" + walletId + "/cards/" + externalcardId)
				.then().extract().response();

		return response;
	}

	public Response createNewWallet(String token, String consumerName, String cardType, String cardNumber,
			String expiryDate, String cardAlias, String zipcode) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToDigitalWallet(consumerName,
				cardType, cardNumber, expiryDate, cardAlias, zipcode);

		Response response = given().spec(requestSpec).auth().oauth2(token).body(digitalWallet)
				.when().post(testData.getProperty("testpaycustomeruuid") + "/wallets").then().extract().response();

		return response;
	}



	
	  public Response getListOfCardsInWallet(String token) throws IOException {
	  
	  testData = new PropertyFileLoader();
	 
	  Response response = given().that().spec(requestSpec).auth().oauth2(token).when().get( testData.getProperty("testpaycustomeruuid") +
	  "/wallets/" + testData.getProperty("externalWalletId"))
	  .then().and().extract().response();
	  
	  return response;
	  
	  }
	public Response updateZipcode(String token, String customeruuid, String walletId, String card, String zipcode)
			throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> zipcodePayload = BillToAddress.getBillingAdressMap(zipcode);

		Response response = given().spec(requestSpec).auth().oauth2(token).body(zipcodePayload)
				.when().patch(customeruuid + "/wallets/" + walletId + "/cards/" + card).then().extract().response();
		return response;
	}

}
