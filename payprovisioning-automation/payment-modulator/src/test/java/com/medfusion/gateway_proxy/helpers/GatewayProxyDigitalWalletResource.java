// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
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
				testData.getProperty("test.pay.customer.uuid") + "/wallets/" + walletId + "/cards/" + externalcardId)
				.then().extract().response();

		return response;
	}

	public Response createNewWallet(String token, String consumerName, String cardType, String cardnumber,
			String expiryDate, String cardAlias, String zipcode, boolean primaryCardFlag) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToDigitalWallet(consumerName,
				cardType, cardnumber, expiryDate, cardAlias, zipcode, primaryCardFlag);

		Response response = given().spec(requestSpec).auth().oauth2(token).body(digitalWallet).when()
				.post(testData.getProperty("test.pay.customer.uuid") + "/wallets").then().extract().response();

		return response;
	}

	public Response getListOfCardsInWallet(String token, String externalWalletId) throws IOException {

		testData = new PropertyFileLoader();

		Response response = given().that().spec(requestSpec).auth().oauth2(token).when()
				.get(testData.getProperty("test.pay.customer.uuid") + "/wallets/" + externalWalletId).then().and()
				.extract().response();
				return response;

	}

	public Response updateZipcode(String token, String customeruuid, String walletId, String card, String cardAlias, String zipcode, boolean primaryCardFlag)
			throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> zipcodePayload = PayloadDetails.getPayloadForUpdatingCardDetails(cardAlias, zipcode, primaryCardFlag);

		Response response = given().spec(requestSpec).auth().oauth2(token).body(zipcodePayload).when()
				.patch(customeruuid + "/wallets/" + walletId + "/cards/" + card).then().extract().response();
		return response;
	}

	public Response addNewCardToExistingWallet(String externalWalletId, String token, String consumerName,
			String cardType, String cardnumber, String expiryDate, String cardAlias, String zipcode, boolean primaryCardFlag)
			throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToDigitalWallet(consumerName,
				cardType, cardnumber, expiryDate, cardAlias, zipcode, primaryCardFlag);

		Response response = given().spec(requestSpec).header("Authorization", "Bearer " + token).body(digitalWallet)
				.when().post(testData.getProperty("test.pay.customer.uuid") + "/wallets/" + externalWalletId + "/cards")
				.then().extract().response();
		return response;
	}

	public Response saleAPI(String accessToken, String customerUUID, String mmid, String externalWalletID,
			String externalCardID, int transanctionAmount) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForNewSaleAPI(
				testData.getProperty("payment.source"), transanctionAmount);

		Response response = given()
				.spec(requestSpec).auth().oauth2(accessToken).log().all().body(digitalWallet).when().post(customerUUID
						+ "/merchant/" + mmid + "/wallet/" + externalWalletID + "/card/" + externalCardID + "/sale")
				.then().and().extract().response();
		return response;
	}

	public Response createNewWallet(String token, Map<String, Object> digitalWallet) throws Exception {
		testData = new PropertyFileLoader();

		Response response = given().spec(requestSpec).auth().oauth2(token).body(digitalWallet).when()
				.post(testData.getProperty("test.pay.customer.uuid") + "/wallets").then().extract().response();

		return response;
	}
}
