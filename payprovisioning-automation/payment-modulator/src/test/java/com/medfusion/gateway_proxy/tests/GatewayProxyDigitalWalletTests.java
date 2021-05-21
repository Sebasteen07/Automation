// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyDigitalWalletResource;
import com.medfusion.gateway_proxy.utils.GatewayProxyDigitalWalletUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GatewayProxyDigitalWalletTests extends GatewayProxyBaseTest{

    protected static PropertyFileLoader testData;

    @BeforeTest
    public void setUp() throws Exception{
        testData = new PropertyFileLoader();
        setupRequestSpecBuilder();
        setupResponsetSpecBuilder();
    }

	@DataProvider(name = "card_details")
	public Object[][] dpMethod(){
		return new Object[][] {
				{"", testData.getProperty("type"),testData.getProperty("cardnumber"),testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), "",testData.getProperty("cardnumber"),testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), testData.getProperty("type"),"",testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumber"),"1220",testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumber"),"testData.getProperty(\"expirationnumber\")",testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumber"),"testData.getProperty(\"expirationnumber\")",testData.getProperty("cardalias"),""},
				{testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumber"),"testData.getProperty(\"expirationnumber\")",testData.getProperty("cardalias"),"testData.getProperty(\"zipcode\")"}
		};
	}

	@Test(dataProvider = "card_details")
	  public void addNewCardAndCreateWallet(String consumerName, String cardType, String cardNumber, String expiryDate, String cardAlias, String zipcode) throws Exception {

	  GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
	  Response response = digitalWallet.createNewWallet(consumerName, cardType, cardNumber, expiryDate, cardAlias, zipcode);

	  JsonPath jsonPath = new JsonPath(response.asString());
	  if(jsonPath.get("status").equals("200")) {
		  Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		  Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		  Assert.assertEquals("VI-1111-1226", jsonPath.get("walletCards[0].cardAlias"));
		  GatewayProxyDigitalWalletUtils.saveWalletDetails(jsonPath.get("externalWalletId").toString(), jsonPath.get("walletCards[0].externalCardId").toString());
	  }
	  else{
	  	Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());
	  	}
	 }

    @Test(dependsOnMethods="addNewCardAndCreateWallet")
   
	public void getListOfCardsInWallet() throws Exception {

		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.getListOfCardsInWallet();
		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].cardExpiryDate").toString().isEmpty());

		String cardNumber = testData.getProperty("cardnumber");
		String aliasToVerify = testData.getProperty("type") + "-" + cardNumber.substring(cardNumber.length() - 4) + "-"
				+ testData.getProperty("expirationnumber");
		Assert.assertEquals(aliasToVerify, jsonPath.get("walletCards[0].cardAlias"));
	}
}
