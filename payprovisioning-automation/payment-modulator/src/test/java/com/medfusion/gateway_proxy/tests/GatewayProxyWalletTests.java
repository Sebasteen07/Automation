package com.medfusion.gateway_proxy.tests;

import com.medfusion.gateway_proxy.helpers.GatewayProxyDigitalWalletResource;
import com.medfusion.gateway_proxy.utils.GatewayProxyDigitalWalletUtils;
import com.medfusion.gateway_proxy.utils.GatewayProxyTestData;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.gateway_proxy.utils.PropertyFileLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GatewayProxyWalletTests extends GatewayProxyBaseTest{

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
    public void testAddNewCardAndCreateWalletWithNullValues(String customeruuid, String consumerName, String cardType, String cardnumber,
                                                            String expiryDate, String cardAlias, String zipcode, boolean primaryCardFlag) throws Exception {
        GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
        Response response = digitalWallet.createNewWalletWithDifferentEnv(token, env, testData.getProperty("x-api-key"), customeruuid,
                consumerName, cardType, cardnumber, expiryDate,
                cardAlias, zipcode, primaryCardFlag);

        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertTrue(jsonPath.get("error").toString().equalsIgnoreCase("Bad Request"));
        Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());
    }
}
