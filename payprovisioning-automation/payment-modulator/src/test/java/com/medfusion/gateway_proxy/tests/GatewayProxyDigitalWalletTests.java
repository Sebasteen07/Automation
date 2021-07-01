// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyDigitalWalletResource;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import com.medfusion.gateway_proxy.utils.GatewayProxyDigitalWalletUtils;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GatewayProxyDigitalWalletTests extends GatewayProxyBaseTest {

	protected static PropertyFileLoader testData;

	@BeforeTest
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
	}

	@Test
	public void testAddNewCardAndCreateWalletByValidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"));
		Assert.assertTrue(response.getStatusCode() == 200);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		Assert.assertEquals("VI-1111-1226", jsonPath.get("walletCards[0].cardAlias"));
		GatewayProxyDigitalWalletUtils.saveWalletDetails(jsonPath.get("externalWalletId").toString(),
				jsonPath.get("walletCards[0].externalCardId").toString());

	}

	@Test
	public void testAddNewCardAndCreateWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer() + "jadgcl";
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"));
		Assert.assertTrue(response.getStatusCode() == 401);
	}

	@Test
	public void testGetListOfCardsInWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		String token1 = token + "jadgcl";
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.getListOfCardsInWallet(token1, testData.getProperty("default.wallet.id"));
		Assert.assertTrue(response.getStatusCode() == 401);

	}

	@Test
	public void testDeleteCardInWallet() throws Exception {

		String token = GatewayProxyUtils.getTokenForCustomer();

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();

		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.to.delete"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"));

		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		String cardnumber = testData.getProperty("card.to.delete");
		String aliasToVerify = testData.getProperty("type") + "-" + cardnumber.substring(cardnumber.length() - 4) + "-"
				+ testData.getProperty("expiration.number");
		Assert.assertEquals(aliasToVerify, jsonPath.get("walletCards[0].cardAlias"));

		String externalWalletId = jsonPath.get("externalWalletId").toString();
		String externalCardId = jsonPath.get("walletCards[0].externalCardId").toString();

		Response deleteResponse = digitalWallet.deleteCardInWallet(token, externalWalletId, externalCardId);
		JsonPath jsonPathDelete = new JsonPath(deleteResponse.asString());

		Assert.assertEquals(externalWalletId, jsonPathDelete.get("externalWalletId"));
		Assert.assertEquals(externalCardId, jsonPathDelete.get("walletCards[0].externalCardId"));
		Assert.assertEquals("DELETED", jsonPathDelete.get("walletCards[0].status").toString());
		Assert.assertTrue(deleteResponse.getStatusCode() == 200);

	}

	@Test
	public void testDeleteCardInWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		String token1 = token + "jadgcl";
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response deleteResponse = digitalWallet.deleteCardInWallet(token1, testData.getProperty("default.wallet.id"),
				testData.getProperty("default.card.id"));
		Assert.assertTrue(deleteResponse.getStatusCode() == 401);

	}

	@DataProvider(name = "card_details")
	public Object[][] dpMethod() {
		return new Object[][] {
				{ "", testData.getProperty("type"), testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode") },
				{ testData.getProperty("consumer.name"), "", testData.getProperty("card.number"),
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode") },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"), "",
						testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
						testData.getProperty("zipcode") },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), "1220", testData.getProperty("card.alias"),
						testData.getProperty("zipcode") },
				{ testData.getProperty("consumer.name"), testData.getProperty("type"),
						testData.getProperty("card.number"), testData.getProperty("expiration.number"),
						testData.getProperty("card.alias"), "" } };
	}

	@Test(dataProvider = "card_details")
	public void testAddNewCardAndCreateWalletWithNullValues(String consumerName, String cardType, String cardnumber,
			String expiryDate, String cardAlias, String zipcode) throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, consumerName, cardType, cardnumber, expiryDate,
				cardAlias, zipcode);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals("Bad Request", jsonPath.get("error"));
		Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());

	}

	@Test

	public void testGetListOfCardsInWallet() throws Exception {

		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumer.name"),
				testData.getProperty("type"), testData.getProperty("card.number"),
				testData.getProperty("expiration.number"), testData.getProperty("card.alias"),
				testData.getProperty("zipcode"));
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

	@Test
	public void testUpdateZipcodeOfWalletWithValidAuth() throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();
		String zipcode = IHGUtil.createRandomZip();

		Response response = digitalWallet.updateZipcode(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("wallet"), testData.getProperty("card"), zipcode);
		Assert.assertTrue(response.getStatusCode() == 200);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertEquals(zipcode, jsonPath.get("zipCode"));
	}

	@Test
	public void testUpdateZipcodeOfWalletWithInvalidAuth() throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();
		String zipcode = IHGUtil.createRandomZip();

		Response response = digitalWallet.updateZipcode(token + "gcnj", testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("wallet"), testData.getProperty("card"), zipcode);
		Assert.assertTrue(response.getStatusCode() == 401);
	}

	@Test
	public void testUpdateZipcodeOfWalletWithInvalidZipcode() throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();

		Response response = digitalWallet.updateZipcode(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("wallet"), testData.getProperty("card"), "9800");
		Assert.assertTrue(response.getStatusCode() == 400);
		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertEquals("Bad Request", jsonPath.get("error"));
		Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());
		Assert.assertEquals("Zip code should be 5 digits or 9 digits number", jsonPath.get("message"));
	}

	@Test
	public void addOneOrMoreCardToExistingWalletByValidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String externalWalletId = testData.getProperty("external.wallet.id");
		Response responseOfAddMoreCard = digitalWallet.addNewCardToExistingWallet(externalWalletId, token,
				testData.getProperty("consumer.name1"), testData.getProperty("card.type1"),
				testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
				testData.getProperty("card.alias1"), testData.getProperty("zip.code1"));

		Assert.assertTrue(responseOfAddMoreCard.getStatusCode() == 200);

		JsonPath jsonPath = new JsonPath(responseOfAddMoreCard.asString());
		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].cardExpiryDate").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].cardType").toString().isEmpty());

	}

	@Test
	public void addOneOrMoreCardToExistingWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer() + "Jdjfh1";
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String externalWalletId = testData.getProperty("external.wallet.id");
		Response responseOfAddMoreCard = digitalWallet.addNewCardToExistingWallet(externalWalletId, token,
				testData.getProperty("consumer.name1"), testData.getProperty("card.type1"),
				testData.getProperty("card.number1"), testData.getProperty("expiration.number1"),
				testData.getProperty("card.alias1"), testData.getProperty("zip.code1"));

		Assert.assertTrue(responseOfAddMoreCard.getStatusCode() == 401);

	}

	@Test(dataProvider = "card_details")
	public void addOneOrMoreCardToExistingWalletWithNullValues(String consumerName, String cardType, String cardnumber,
			String expiryDate, String cardAlias, String zipcode) throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer();
		String externalWalletId = testData.getProperty("external.wallet.id");
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response responseOfAddMoreCard = digitalWallet.addNewCardToExistingWallet(externalWalletId, token, consumerName,
				cardType, cardnumber, expiryDate, cardAlias, zipcode);

		JsonPath jsonPath = new JsonPath(responseOfAddMoreCard.asString());
		Assert.assertEquals(responseOfAddMoreCard.getStatusCode(), 400);
		Assert.assertEquals("Bad Request", jsonPath.get("error"));
		Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());

	}

	@Test
	public void saleAPI() throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		String token = GatewayProxyUtils.getTokenForCustomer();

		Response response = digitalWallet.saleAPI(token, testData.getProperty("test.pay.customer.uuid"),
				testData.getProperty("proxy.mmid"), testData.getProperty("external.wallet.id"),
				testData.getProperty("external.card.id"));
		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertTrue(!jsonPath.get("externalTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("orderId").toString().isEmpty());
		Assert.assertEquals("Approved", jsonPath.get("message"));
		Assert.assertEquals("000", jsonPath.get("responseCode"));

	}
}