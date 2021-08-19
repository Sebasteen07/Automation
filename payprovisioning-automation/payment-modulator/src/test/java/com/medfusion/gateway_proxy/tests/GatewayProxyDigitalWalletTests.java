// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyDigitalWalletResource;
import com.medfusion.gateway_proxy.helpers.GatewayProxyTransactionResource;
import com.medfusion.gateway_proxy.utils.GatewayProxyDigitalWalletUtils;
import com.medfusion.gateway_proxy.utils.GatewayProxyTestData;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
				testData.getProperty("consumer.name1"), testData.getProperty("card.type1"),
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
	public void testAddNewCardAndCreateWalletWithNullValues(String consumerName, String cardType, String cardnumber,
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

		String externalWalletId = jsonPath.get("externalWalletId").toString();
		Response responseGet = digitalWallet.getListOfCardsInWallet(token, externalWalletId);
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
	public void testCardDetailsWithInvalidData(String alias, String zipcode, boolean isPrimary) throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();

		Response response = digitalWallet.updateZipcode(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("wallet"), testData.getProperty("card"), alias, zipcode, isPrimary);
		JsonPath jsonPath = new JsonPath(response.asString());

		if (zipcode.isEmpty()) {
			Assert.assertEquals("Bad Request", jsonPath.get("error"));
			Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());
			Assert.assertEquals("Zip code should be 5 digits or 9 digits number, Zip code is mandatory",
					jsonPath.get("message"));
		} else if (zipcode.length() < 5 || zipcode.length() > 9) {
			Assert.assertEquals("Bad Request", jsonPath.get("error"));
			Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());
			Assert.assertEquals("Zip code should be 5 digits or 9 digits number", jsonPath.get("message"));
		} else if (alias.isEmpty()) {
			Assert.assertTrue(response.getStatusCode() == 400);
			Assert.assertEquals("Bad Request", jsonPath.get("error"));
			Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());
			Assert.assertEquals("Card Alias is required", jsonPath.get("message"));
		} else {
			Assert.assertTrue(jsonPath.get("primaryCard").equals(true),
					"Default card will always be have primary flag as true");
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
				testData.getProperty("consumer.name1"), testData.getProperty("card.type1"),
				testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
				testData.getProperty("card.alias1"), testData.getProperty("zip.code1"), true);

		Assert.assertTrue(responseOfAddMoreCard.getStatusCode() == 401);

	}

	@Test(enabled = true, priority = 13)
	public void testDigitalWalletSaleWithValidAuth() throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();

		int transanctionAmount = Integer.parseInt(IHGUtil.createRandomNumericString(4));

		Response response = digitalWallet.saleAPI(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"), testData.getProperty("external.wallet.id"),
				testData.getProperty("external.card.id"), transanctionAmount);
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
		Assert.assertEquals("Unauthorized", jsonpath.get("message"));
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

	@Test(priority = 18, dataProvider = "delete_invalid_data", dataProviderClass = GatewayProxyTestData.class, enabled = true)

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
}