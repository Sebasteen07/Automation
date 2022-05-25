// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.payment_modulator.pojos.PayloadDetails;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GatewayProxyDigitalWalletResource extends GatewayProxyBaseTest {

	protected PropertyFileLoader testData;

	public Response deleteCardInWallet(String token, String customerId, String walletId, String externalcardId)
			throws IOException {

		testData = new PropertyFileLoader();

		Response response = given().spec(requestSpec).auth().oauth2(token).when()
				.delete(customerId + "/wallets/" + walletId + "/cards/" + externalcardId).then().extract().response();

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

	public Response createNewWalletWithDifferentEnv(String token, String env, String xpi, String customeruuid,
			String consumerName, String cardType, String cardnumber, String expiryDate, String cardAlias,
			String zipcode, boolean primaryCardFlag) throws Exception {
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToDigitalWallet(consumerName,
				cardType, cardnumber, expiryDate, cardAlias, zipcode, primaryCardFlag);
		Response response;

		String url = null;
		if (env.equalsIgnoreCase("DEV3") || env.equalsIgnoreCase("DEMO")) {
			url = customeruuid + "/wallets";
			response = given().auth().oauth2(token).contentType(ContentType.JSON).body(digitalWallet).when().log().all()
					.post(url).then().extract().response();
		} else {
			url = customeruuid + "/pay/wallets";
			response = given().auth().oauth2(token).header("x-api-key", xpi).contentType(ContentType.JSON)
					.body(digitalWallet).when().log().all().post(url).then().log().all().extract().response();
		}
		return response;
	}

	public Response getListOfCardsInWallet(String token, String externalWalletId) throws IOException {

		testData = new PropertyFileLoader();

		Response response = given().that().spec(requestSpec).auth().oauth2(token).when()
				.get(testData.getProperty("test.pay.customer.uuid") + "/wallets/" + externalWalletId).then().and()
				.extract().response();
		return response;

	}

	public Response updateZipcode(String token, String customeruuid, String walletId, String card, String cardAlias,
			String zipcode, boolean primaryCardFlag) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> zipcodePayload = PayloadDetails.getPayloadForUpdatingCardDetails(cardAlias, zipcode,
				primaryCardFlag);

		Response response = given().spec(requestSpec).auth().oauth2(token).body(zipcodePayload).when()
				.patch(customeruuid + "/wallets/" + walletId + "/cards/" + card).then().extract().response();
		return response;
	}

	public Response addNewCardToExistingWallet(String customerId, String externalWalletId, String token,
			String consumerName, String cardType, String cardnumber, String expiryDate, String cardAlias,
			String zipcode, boolean primaryCardFlag) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToDigitalWallet(consumerName,
				cardType, cardnumber, expiryDate, cardAlias, zipcode, primaryCardFlag);

		Response response = given().spec(requestSpec).header("Authorization", "Bearer " + token).body(digitalWallet)
				.when().post(customerId + "/wallets/" + externalWalletId + "/cards").then().extract().response();
		return response;
	}

	public Response saleAPI(String accessToken, String customerUUID, String mmid, String externalWalletID,
			String externalCardID, int transanctionAmount) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails
				.getPayloadForNewSaleAPI(testData.getProperty("payment.source"), transanctionAmount);

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

	public Response addCardsToWallet(String token, Map<String, Object> digitalWallet, String externalWalletId)
			throws Exception {
		testData = new PropertyFileLoader();

		Response response = given().spec(requestSpec).auth().oauth2(token).body(digitalWallet).when()
				.post(testData.getProperty("test.pay.customer.uuid") + "/wallets/" + externalWalletId + "/cards").then()
				.extract().response();

		return response;
	}

	public String getEmptyWallet(String token, PropertyFileLoader testData,
			GatewayProxyDigitalWalletResource digitalWallet) throws Exception {

		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.to.delete"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"), true);

		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		String cardnumber = testData.getProperty("card.to.delete");
		String aliasToVerify = testData.getProperty("type") + "-" + cardnumber.substring(cardnumber.length() - 4) + "-"
				+ testData.getProperty("expiration.number");
		Assert.assertEquals(aliasToVerify, jsonPath.get("walletCards[0].cardAlias"));

		String externalWalletId = jsonPath.get("externalWalletId").toString();
		String externalCardId = jsonPath.get("walletCards[0].externalCardId").toString();

		digitalWallet.deleteCardInWallet(token, testData.getProperty("test.pay.customer.uuid"), externalWalletId,
				externalCardId);

		Response responseGet = digitalWallet.getListOfCardsInWallet(token, externalWalletId);
		JsonPath jsonPathGet = new JsonPath(responseGet.asString());
		System.out.println(jsonPathGet.get("walletCards[]").toString());
		Assert.assertEquals("[]", jsonPathGet.get("walletCards[]").toString());

		return externalWalletId;
	}

	public Response makeWalletEmpty(String token, PropertyFileLoader testData,
			GatewayProxyDigitalWalletResource digitalWallet, String externalWalletId, String cardFlagToBeDeleted)
			throws Exception {

		boolean walletEmpty = false;
		System.out.println("Going to make wallet empty ");
		Response responseGetBeforeDeletingCards = digitalWallet.getListOfCardsInWallet(token, externalWalletId);
		JsonPath jsonPathGetBeforeDeletingCards = new JsonPath(responseGetBeforeDeletingCards.asString());
		Response deleteResponse = null;

		int i = 0;
		while (jsonPathGetBeforeDeletingCards.get("walletCards[" + i + "].externalCardId") != null) {
			System.out.println(
					"Test card " + jsonPathGetBeforeDeletingCards.get("walletCards[" + i + "].externalCardId"));

			System.out.println(
					"Test" + jsonPathGetBeforeDeletingCards.get("walletCards[" + i + "].primaryCard").toString());
			if (jsonPathGetBeforeDeletingCards.get("walletCards[" + i + "].primaryCard").toString()
					.equals(cardFlagToBeDeleted)) {
				String externalCardId = jsonPathGetBeforeDeletingCards.get("walletCards[" + i + "].externalCardId")
						.toString().trim();
				System.out.println("Test inside"
						+ jsonPathGetBeforeDeletingCards.get("walletCards[" + i + "].primaryCard").toString());

				System.out.println("Going to delete the card ");
				deleteResponse = digitalWallet.deleteCardInWallet(token, testData.getProperty("test.pay.customer.uuid"),
						externalWalletId, externalCardId);
				walletEmpty = true;
			} else {
				i++;
			}

			if (walletEmpty == true) {
				break;
			}

		}
		return deleteResponse;

	}

	public HashMap verifyFlagInResponce(String flagToBeChecked, int noOfCardsToBeAdded, JsonPath jsonPath)
			throws IOException {
		String flagToCheck = "";
		HashMap<String, String> cardDetails = new HashMap<String, String>();

		for (int i = 0; i < noOfCardsToBeAdded; i++) {

			if (jsonPath.get("walletCards[" + i + "].primaryCard").toString().equals(flagToBeChecked)) {

				String exetrnalCardId = jsonPath.get("walletCards[" + i + "].externalCardId").toString();
				flagToCheck = "true";
				cardDetails.put(flagToCheck, exetrnalCardId);
				break;
			}

			else {
				i++;
			}
		}

		return cardDetails;

	}
}
