package com.medfusion.gateway_proxy.tests;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.gateway_proxy.helpers.GatewayProxyDigitalWalletResource;
import com.medfusion.gateway_proxy.helpers.GatewayProxyTransactionResource;
import com.medfusion.gateway_proxy.utils.GatewayProxyDigitalWalletUtils;
import com.medfusion.gateway_proxy.utils.GatewayProxyTestData;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.gateway_proxy.utils.PropertyFileLoad;
import com.medfusion.payment_modulator.utils.CommonUtils;
import com.medfusion.payment_modulator.utils.Validations;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GatewayProxyWalletTests extends GatewayProxyBaseTest {

	public PropertyFileLoad testData;
	public static String token;
	public static String env;

	@BeforeTest
	public void setUp() throws Exception {
		env = GatewayProxyUtils.getEnvironmentType().toString();
		testData = new PropertyFileLoad(env);
		RestAssured.baseURI = testData.getProperty("proxy.base.url");
		token = GatewayProxyUtils.getTokenForCustomerForEnv(env);
	}

	@Test(enabled = true, priority = 1)
	public void testAddNewCardAndCreateWalletByValidAuth() throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWalletWithDifferentEnv(token, env, testData.getProperty("x-api-key"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("consumer.name"),
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

	@Test(priority = 5, dataProvider = "card_details", dataProviderClass = GatewayProxyTestData.class, enabled = true)
	public void testAddNewCardAndCreateWalletWithNullValues(String customeruuid, String consumerName, String cardType,
			String cardnumber, String expiryDate, String cardAlias, String zipcode, boolean primaryCardFlag)
			throws Exception {
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWalletWithDifferentEnv(token, env, testData.getProperty("x-api-key"),
				customeruuid, consumerName, cardType, cardnumber, expiryDate, cardAlias, zipcode, primaryCardFlag);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertTrue(jsonPath.get("error").toString().equalsIgnoreCase("Bad Request"));
		Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());
	}

	@Test(enabled = true)
	public void testGatewayAuthorizeByValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		Response response = transaction.makeAuthorizeTransactionWithDiffEnv(token, env,
				testData.getProperty("payment.source"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"), testData);

		JsonPath jsonpath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());
	}

	@Test(enabled = true)
	public void testGatewaySaleByValidAuth() throws Exception {

		String transanctionAmount = IHGUtil.createRandomNumericString(5);
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();

		Response response = transaction.makeSaleTransactionWithDiffEnv(token, env,
				testData.getProperty("payment.source"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"), testData,
				transanctionAmount);

		JsonPath jsonpath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		CommonUtils.saveTransactionDetails(jsonpath.get("externalTransactionId").toString(),
				jsonpath.get("orderId").toString());
	}

	@Test(enabled = true)
	public void testGatewayCaptureByValidAuth() throws Exception {

		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		Response response = transaction.makeAuthorizeTransactionWithDiffEnv(token, env,
				testData.getProperty("payment.source"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"), testData);

		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		Response captureResponse = transaction.makeCaptureTransactionWithDiffEnv(token, env,
				testData.getProperty("payment.source"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"),
				jsonPath.get("externalTransactionId").toString(), jsonPath.get("orderId").toString(), testData);

		JsonPath jsonpathCapture = new JsonPath(captureResponse.asString());

		validate.verifyTransactionDetails(captureResponse.asString());

		CommonUtils.saveTransactionDetails(jsonpathCapture.get("externalTransactionId").toString(),
				jsonpathCapture.get("orderId").toString());
	}

	@Test(enabled = true)
	public void testGatewayProxyVoidByValidAuth() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);

		Response response = transaction.makeSaleTransactionWithDiffEnv(token, env,
				testData.getProperty("payment.source"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"), testData,
				transanctionAmount);

		JsonPath jsonPath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyTransactionDetails(response.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		Response responseVoid = transaction.makeAVoidWithDiffEnv(token, env, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("comment"),
				testData.getProperty("customer.id"), externalTransactionId, orderId, testData);

		validate.verifyTransactionDetails(responseVoid.asString());

	}

	@Test(enabled = true)
	public void testGatewayProxyCredit() throws Exception {

		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);
		Response responseSale = transaction.makeSaleTransactionWithDiffEnv(token, env,
				testData.getProperty("payment.source"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"), testData,
				transanctionAmount);

		JsonPath jsonPath = new JsonPath(responseSale.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(responseSale.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		Response response = transaction.makeARefundWithDiffEnv(token, env, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("comment"),
				testData.getProperty("customer.id"), externalTransactionId, orderId, transanctionAmount, testData);

		validate.verifyTransactionDetails(response.asString());

	}

	@Test(enabled = true)

	public void testGatewayProxyPartialRefund() throws Exception {

		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);
		String diffrence = IHGUtil.createRandomNumericString(2);

		Integer partialRefundAmount = Integer.parseInt(transanctionAmount) - Integer.parseInt(diffrence);

		Response responseSale = transaction.makeSaleTransactionWithDiffEnv(token, env,
				testData.getProperty("payment.source"), testData.getProperty("type"),
				testData.getProperty("card.number"), testData.getProperty("expiration.number"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("proxy.mmid"), testData,
				transanctionAmount);

		JsonPath jsonpath = new JsonPath(responseSale.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(responseSale.asString());

		String externalTransactionId = jsonpath.get("externalTransactionId").toString();
		String orderId = jsonpath.get("orderId").toString();

		Response response = transaction.makeARefundWithDiffEnv(token, env, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), testData.getProperty("comment"),
				testData.getProperty("customer.id"), externalTransactionId, orderId,
				Integer.toString(partialRefundAmount), testData);

		validate.verifyTransactionDetails(response.asString());
	}

}
