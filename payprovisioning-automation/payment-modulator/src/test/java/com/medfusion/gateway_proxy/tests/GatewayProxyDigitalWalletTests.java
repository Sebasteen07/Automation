// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyDigitalWalletResource;
import com.medfusion.gateway_proxy.utils.GatewayProxyDigitalWalletUtils;
import com.medfusion.gateway_proxy.utils.GatewayProxyTestData;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GatewayProxyDigitalWalletTests extends GatewayProxyBaseTest {

	protected static PropertyFileLoader testData;

	@BeforeTest
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
	}

	@Test(enabled = true, priority = 1)
	public void testAddNewCardAndCreateWalletByValidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"), true);
		Assert.assertTrue(response.getStatusCode() == 200);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		Assert.assertEquals("VI-1111-1226", jsonPath.get("walletCards[0].cardAlias"));
		GatewayProxyDigitalWalletUtils.saveWalletDetails(jsonPath.get("externalWalletId").toString(),
				jsonPath.get("walletCards[0].externalCardId").toString());

	}

	@Test(enabled = true, priority = 1)
	public void testAddNewCardAndCreateWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer() + "jadgcl";

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"), true);
		Assert.assertTrue(response.getStatusCode() == 401);
	}

	@Test(enabled = true, priority = 3)
	public void testAddOneOrMoreCardToExistingWalletByValidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		Response responseCreate = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"), true);
		Assert.assertTrue(responseCreate.getStatusCode() == 200);
		JsonPath jsonPathCreate = new JsonPath(responseCreate.asString());

		String externalWalletId = jsonPathCreate.get("externalWalletId").toString();
		Response responseOfAddMoreCard = digitalWallet.addNewCardToExistingWallet(
				testData.getProperty("test.pay.customer.uuid"), externalWalletId, token,
				testData.getProperty("consumer.name1"), testData.getProperty("type1"),
				testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
				testData.getProperty("card.alias1"), testData.getProperty("zip.code1"), true);

		Assert.assertTrue(responseOfAddMoreCard.getStatusCode() == 200);

		JsonPath jsonPath = new JsonPath(responseOfAddMoreCard.asString());
		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].cardExpiryDate").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].cardType").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].primaryCard").toString().equals(true),
				"There is already one existing primary card associated with the wallet");

	}

	@Test(enabled = true, priority = 2)
	public void testGetListOfCardsInWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		String token1 = token + "jadgcl";
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.getListOfCardsInWallet(token1, testData.getProperty("default.wallet.id"));
		Assert.assertTrue(response.getStatusCode() == 401);

	}

	@Test(priority = 5, dataProvider = "card_details", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testAddNewCardAndCreateWalletWithNullValues(String customerUuid, String consumerName, String cardType, String cardnumber,
			String expiryDate, String cardAlias, String zipcode, boolean primaryCardFlag) throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, consumerName, cardType, cardnumber, expiryDate,
				cardAlias, zipcode, primaryCardFlag);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals("Bad Request", jsonPath.get("error"));
		Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());

	}

	@Test(enabled = true, priority = 6)

	public void testGetListOfCardsInWallet() throws Exception {

		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"), true);
		Assert.assertTrue(response.getStatusCode() == 200);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		Assert.assertEquals("VI-1111-1226", jsonPath.get("walletCards[0].cardAlias"));

		JsonPath jsonPathGet = new JsonPath(response.asString());

		Assert.assertTrue(!jsonPathGet.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPathGet.get("walletCards[0].cardExpiryDate").toString().isEmpty());
		String cardnumber = testData.getProperty("card.number");
		String aliasToVerify = testData.getProperty("type") + "-" + cardnumber.substring(cardnumber.length() - 4) + "-"
				+ testData.getProperty("expiration.number");
		System.out.println(aliasToVerify);
		System.out.println(jsonPath.get("walletCards[0].cardAlias"));
		Assert.assertEquals(aliasToVerify, jsonPath.get("walletCards[0].cardAlias"));
	}

	@Test(enabled = true, priority = 7)
	public void testUpdateCardDetailsWithValidAuth() throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();
		String zipcode = IHGUtil.createRandomZip();

		Response response = digitalWallet.updateZipcode(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("wallet"), testData.getProperty("card"), testData.getProperty("card.alias1"),
				zipcode, true);
		Assert.assertTrue(response.getStatusCode() == 200);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertEquals(zipcode, jsonPath.get("zipCode"));
		Assert.assertEquals(jsonPath.get("primaryCard"), true);
		Assert.assertEquals(jsonPath.get("cardAlias"), "Test-Alias");
	}

	@Test(enabled = true, priority = 8)
	public void testUpdateCardDetailsWithInvalidAuth() throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();
		String zipcode = IHGUtil.createRandomZip();

		Response response = digitalWallet.updateZipcode(token + "gcnj", testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("wallet"), testData.getProperty("card"), testData.getProperty("card.alias1"),
				zipcode, true);
		Assert.assertTrue(response.getStatusCode() == 401);
	}

	@Test(priority = 9, dataProvider = "update_card", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testCardDetailsWithInvalidData(String alias, String zipcode, boolean isPrimary, int statusCodeVerify,
			String verifyValidationMessage) throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();

		Response response = digitalWallet.updateZipcode(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("wallet"), testData.getProperty("card"), alias, zipcode, isPrimary);
		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("message") != null) {

			Assert.assertTrue(jsonPath.get("message").toString().contains(verifyValidationMessage));

		}

	}

	@Test(enabled = true, priority = 10)
	public void testDeleteCardInWallet() throws Exception {

		String token = GatewayProxyUtils.getTokenForCustomer();

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

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

		Response deleteResponse = digitalWallet.deleteCardInWallet(token,
				testData.getProperty("test.pay.customer.uuid"), externalWalletId, externalCardId);
		JsonPath jsonPathDelete = new JsonPath(deleteResponse.asString());

		Assert.assertEquals(externalWalletId, jsonPathDelete.get("externalWalletId"));
		Assert.assertEquals(externalCardId, jsonPathDelete.get("walletCards[0].externalCardId"));
		Assert.assertEquals("DELETED", jsonPathDelete.get("walletCards[0].status").toString());
		Assert.assertTrue(deleteResponse.getStatusCode() == 200);

	}

	@Test(enabled = true, priority = 11)
	public void testDeleteCardInWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		String token1 = token + "jadgcl";
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response deleteResponse = digitalWallet.deleteCardInWallet(token1,
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("default.wallet.id"),
				testData.getProperty("default.card.id"));
		Assert.assertTrue(deleteResponse.getStatusCode() == 401);

	}

	@Test(enabled = true, priority = 12)
	public void testAddOneOrMoreCardToExistingWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer() + "Jdjfh1";
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String externalWalletId = testData.getProperty("external.wallet.id");
		Response responseOfAddMoreCard = digitalWallet.addNewCardToExistingWallet(
				testData.getProperty("test.pay.customer.uuid"), externalWalletId, token,
				testData.getProperty("consumer.name1"), testData.getProperty("type1"),
				testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
				testData.getProperty("card.alias1"), testData.getProperty("zip.code1"), true);

		Assert.assertTrue(responseOfAddMoreCard.getStatusCode() == 401);

	}

	@Test(enabled = true, priority = 13)
	public void testDigitalWalletSaleWithValidAuth() throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();

		Response responseCreate = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.to.delete"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"), true);

		JsonPath jsonPathCreate = new JsonPath(responseCreate.asString());

		String externalWalletId = jsonPathCreate.get("externalWalletId").toString();
		String externalCardId = jsonPathCreate.get("walletCards[0].externalCardId").toString();

		int transanctionAmount = Integer.parseInt(IHGUtil.createRandomNumericString(4));

		Response response = digitalWallet.saleAPI(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"), externalWalletId, externalCardId, transanctionAmount);
		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertTrue(!jsonPath.get("externalTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("orderId").toString().isEmpty());
		Assert.assertEquals("Approved", jsonPath.get("message"));
		Assert.assertEquals("000", jsonPath.get("responseCode"));

	}

	@Test(enabled = true, priority = 14)
	public void testDigitalWalletSaleWithInvalidAuth() throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer() + "hgf";
		int transanctionAmount = Integer.parseInt(IHGUtil.createRandomNumericString(4));

		Response response = digitalWallet.saleAPI(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"), testData.getProperty("external.wallet.id"),
				testData.getProperty("external.card.id"), transanctionAmount);

		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 401);
		Assert.assertEquals("Authentication Failed", jsonpath.get("message"));
	}

	@Test(priority = 15, dataProvider = "txn_data_for_sale", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testDigitalWalletSaleWithInvalidData(String customeruuid, String mmid, String wallet, String card,
			int transanctionAmount) throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();

		Response response = digitalWallet.saleAPI(token, customeruuid, mmid, wallet, card, transanctionAmount);

		JsonPath jsonpath = new JsonPath(response.asString());

		if (response.getStatusCode() == 400) {
			Assert.assertTrue(!jsonpath.get("error").toString().isEmpty());
		} else if (response.getStatusCode() == 403) {
			Assert.assertTrue(!jsonpath.get("message").toString().isEmpty());
			Assert.assertTrue(jsonpath.get("error").toString().contains("Forbidden"));
		} else {
			Assert.assertTrue(response.getStatusCode() == 404);
			Assert.assertTrue(!jsonpath.get("message").toString().isEmpty());
			Assert.assertTrue(jsonpath.get("error").toString().contains("Not Found"));
		}
	}

	@Test(priority = 16, dataProvider = "get_list_of_cards_invalid_data", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testGetListOfCardsInWalletByInvalidData(String token, String externalWalletId, int statusCodeVerify,
			String verifyErrorMessage) throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		Response responseGet = digitalWallet.getListOfCardsInWallet(token, externalWalletId);

		JsonPath jsonPathGet = new JsonPath(responseGet.asString());

		Assert.assertNotNull(jsonPathGet, "Response was null");
		Assert.assertEquals(responseGet.getStatusCode(), statusCodeVerify);

		if (jsonPathGet.get("message") != null) {

			Assert.assertTrue(jsonPathGet.get("message").toString().contains(verifyErrorMessage));

		}
	}

	@Test(priority = 17, dataProvider = "add_one_or_more_cards_invalid_data", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testAddOneOrMoreCardsInWalletByInvalidData(String customerId, String token, String externalWalletId,
			String consumerName, String cardType, String cardNumber, String expiryDate, String zipcode,
			boolean isPrimary, int statusCodeVerify, String verifyErrorMessage) throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response responseAddCard = digitalWallet.addNewCardToExistingWallet(customerId, externalWalletId, token,
				consumerName, cardType, cardNumber, expiryDate, testData.getProperty("card.alias"), zipcode, isPrimary);

		JsonPath jsonPathAddCard = new JsonPath(responseAddCard.asString());

		Assert.assertNotNull(jsonPathAddCard, "Response was null");
		Assert.assertEquals(responseAddCard.getStatusCode(), statusCodeVerify);

		System.out.println(jsonPathAddCard.get("message"));
		System.out.println(verifyErrorMessage);

		if (verifyErrorMessage != null) {

			Assert.assertTrue(jsonPathAddCard.get("message").toString().contains(verifyErrorMessage));

		}
	}

	@Test(priority = 1, dataProvider = "delete_invalid_data", dataProviderClass = GatewayProxyTestData.class, enabled = true)

	public void testDeleteCardInWalletByInvalidData(String token, String customerId, String externalWalletId,
			String externalCardId, int statusCodeVerify, String verifyErrorMessage) throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		Response deleteResponse = digitalWallet.deleteCardInWallet(token, customerId, externalWalletId, externalCardId);
		JsonPath jsonPathDelete = new JsonPath(deleteResponse.asString());

		Assert.assertNotNull(jsonPathDelete, "Response was null");
		Assert.assertEquals(deleteResponse.getStatusCode(), statusCodeVerify);

		if (jsonPathDelete.get("message") != null) {

			Assert.assertTrue(jsonPathDelete.get("message").toString().contains(verifyErrorMessage));

		}

	}

	@Test(enabled = true)
	public void testAddMultipleCardsAndCreateWallet() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		Map<String, Object> cardsMap = PayloadDetails.getPayloadForAddingCardsToDigitalWallet(testData, 3, 1);

		Response response = digitalWallet.createNewWallet(token, cardsMap);
		Assert.assertTrue(response.getStatusCode() == 200);

		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[1].externalCardId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[2].externalCardId").toString().isEmpty());

		Assert.assertEquals(jsonPath.get("walletCards[0].primaryCard"), true, "First Card is a Primary Card");
		Assert.assertEquals(jsonPath.get("walletCards[1].primaryCard"), false, "Second Card is NOT a Primary Card");
		Assert.assertEquals(jsonPath.get("walletCards[2].primaryCard"), false, "Third Card is NOT a Primary Card");
	}

	@Test(dataProvider = "cards_details", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testAddMultipleCardsAndCreateWalletWithInvalidData(String name1, String type1, String cardNumber1,
			String expiry1, String alias1, String zipCode1, boolean primaryFlag1, String name2, String type2,
			String cardNumber2, String expiry2, String alias2, String zipCode2, boolean primaryFlag2, String name3,
			String type3, String cardNumber3, String expiry3, String alias3, String zipCode3, boolean primaryFlag3,
			int statusCodeVerify, String verifyErrorMessage) throws Exception {

		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		Map<String, Object> cardsMap = PayloadDetails.getMultipleCardsPayload(name1, type1, cardNumber1, expiry1,
				alias1, zipCode1, primaryFlag1, name2, type2, cardNumber2, expiry2, alias2, zipCode2, primaryFlag2,
				name3, type3, cardNumber3, expiry3, alias3, zipCode3, primaryFlag3);

		Response response = digitalWallet.createNewWallet(token, cardsMap);

		JsonPath jsonPath1 = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath1, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath1.get("message") != null) {

			Assert.assertTrue(jsonPath1.get("message").toString().contains(verifyErrorMessage));
		}
	}

	@Test(dataProvider = "empty_Wallet_Add_Cards", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testEmptyWalletAddMultipleCards(int noOfCards, int noofCrdsToBeTrue, int noOfCrdsToBeFalse,
			int noOfCrdsToBeNull, int noOfCrdsToBeOmitted, int noOfcardsTobeRandom, int statusCodeVerify,
			String verifyValidationMessage) throws Exception {
		String emptyWalletId;

		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		emptyWalletId = digitalWallet.getEmptyWallet(token, testData, digitalWallet);

		Map<String, Object> cardsMap = PayloadDetails.getPayloadForAddingMultipeCardsToDigitalWallet(testData,
				noOfCards, noofCrdsToBeTrue, noOfCrdsToBeFalse, noOfCrdsToBeNull, noOfCrdsToBeOmitted,
				noOfcardsTobeRandom);

		if (cardsMap != null) {
			Response responseAddCrdsToExisting = digitalWallet.addCardsToWallet(token, cardsMap, emptyWalletId);
			JsonPath jPAddCrdsToExisting = new JsonPath(responseAddCrdsToExisting.asString());

			Assert.assertNotNull(jPAddCrdsToExisting, "Response was null");
			Assert.assertEquals(responseAddCrdsToExisting.getStatusCode(), statusCodeVerify);

			if (statusCodeVerify == 200) {
				verifySuccessfulResponce(responseAddCrdsToExisting);
			}

			if (jPAddCrdsToExisting.get("message") != null) {

				Assert.assertTrue(jPAddCrdsToExisting.get("message").toString().contains(verifyValidationMessage));
			}

		}

	}

	@Test(dataProvider = "delete_card_with_sepcific_flag", dataProviderClass = GatewayProxyTestData.class, enabled = true)

	public void testWalletAddDeleteCardWithSpecificFlag(int noOfCards, int noofCrdsToBeTrue, int noOfCrdsToBeFalse,
			int noOfCrdsToBeNull, int noOfCrdsToBeOmitted, int noOfcardsTobeRandom, String cardFlagToBeDeleted,
			int statusCodeVerify, String verifyValidationMessage, int noOfCardsAfterDeletion) throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		Map<String, Object> cardsMapToBeCreated = PayloadDetails.getPayloadForAddingMultipeCardsToDigitalWallet(
				testData, noOfCards, noofCrdsToBeTrue, noOfCrdsToBeFalse, noOfCrdsToBeNull, noOfCrdsToBeOmitted,
				noOfcardsTobeRandom);

		Response response = digitalWallet.createNewWallet(token, cardsMapToBeCreated);
		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertTrue(response.getStatusCode() == 200);
		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());

		String walletId = jsonPath.get("externalWalletId").toString();

		Response deleteResponce = digitalWallet.makeWalletEmpty(token, testData, digitalWallet, walletId,
				cardFlagToBeDeleted);
		JsonPath jsonPathDelete = new JsonPath(deleteResponce.asString());

		if (statusCodeVerify == 200) {

			Response getCardsResponce = digitalWallet.getListOfCardsInWallet(token, walletId);

			JsonPath jsonPathgetCards = new JsonPath(getCardsResponce.asString());

			if (noOfCardsAfterDeletion == 0) {
				Assert.assertEquals("[]", jsonPathgetCards.get("walletCards[]").toString());
			} else if (statusCodeVerify == getCardsResponce.getStatusCode()) {
				Assert.assertEquals(jsonPathgetCards.get("walletCards[0].primaryCard"), true,
						"First Card is a Primary Card");
			}
		}

		else if (jsonPathDelete.get("message") != null) {

			Assert.assertTrue(jsonPathDelete.get("message").toString().contains(verifyValidationMessage));
		}

	}

	@Test(dataProvider = "Add_Multiple_Cards_with_specific_flag", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testWalletAddMultipleCardsWithPrimaryFlag(int noOfCardsToBeCreated, int noofCrdsCreatedToBeTrue,
			int noOfCrdsCreatedToBefalse, int noOfCardsToBeAdded, int noofCrdsToBeAddedTrue, int noOfCrdsToBeAddedFalse,
			int noOfCrdsToBeAddedNull, int noOfCrdsToBeAddedAsOmitted, int noOfcardsTobeAddedAsRandom,
			int statusCodeVerify, String verifyValidationMessage, String CardFlagToCheck) throws Exception {

		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		int flagForCardCount = 0;
		int maxNoOfCards = Integer.parseInt(testData.getProperty("max.dw.cards"));

		Map<String, Object> cardsMapToBeCreated = PayloadDetails.getPayloadForAddingMultipeCardsToDigitalWallet(
				testData, noOfCardsToBeCreated, noofCrdsCreatedToBeTrue, noOfCrdsCreatedToBefalse, 0, 0, 0);
		Response response = digitalWallet.createNewWallet(token, cardsMapToBeCreated);
		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertTrue(response.getStatusCode() == 200);
		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		String WalletId = jsonPath.get("externalWalletId").toString();

		if (response.getStatusCode() == 200) {

			HashMap<String, String> cardDetails = digitalWallet.verifyFlagInResponce("true", noOfCardsToBeCreated,
					jsonPath);

			Assert.assertTrue(cardDetails.containsKey("true"), "Verified the flag for newely added card");

		}

		int totalCards = noOfCardsToBeCreated + noOfCardsToBeAdded;
		if (totalCards <= maxNoOfCards) {

			flagForCardCount = noOfCardsToBeCreated + 1;

			Map<String, Object> cardsMapToBeAdded = PayloadDetails
					.getPayloadForAddingMultipeCardsToExistingDigitalWallet(testData, noOfCardsToBeAdded,
							noofCrdsToBeAddedTrue, noOfCrdsToBeAddedFalse, noOfCrdsToBeAddedNull,
							noOfCrdsToBeAddedAsOmitted, noOfcardsTobeAddedAsRandom, flagForCardCount);

			Response responseAddCrdsToExisting = digitalWallet.addCardsToWallet(token, cardsMapToBeAdded, WalletId);
			JsonPath jPAddCrdsToExisting = new JsonPath(responseAddCrdsToExisting.asString());

			Assert.assertNotNull(jPAddCrdsToExisting, "Response was null");

			if (statusCodeVerify == 200) {

				HashMap<String, String> cardDetails = digitalWallet.verifyFlagInResponce(CardFlagToCheck,
						noOfCardsToBeAdded, jPAddCrdsToExisting);

				Assert.assertTrue(cardDetails.containsKey("true"), "Verified the flag for newely added card");

			} else {
				Assert.assertEquals(statusCodeVerify, responseAddCrdsToExisting.getStatusCode());
				Assert.assertTrue(jPAddCrdsToExisting.get("message").toString().contains(verifyValidationMessage));

			}
		}

		else {
			System.out.println("Total cards are not matching ");

		}

	}

}