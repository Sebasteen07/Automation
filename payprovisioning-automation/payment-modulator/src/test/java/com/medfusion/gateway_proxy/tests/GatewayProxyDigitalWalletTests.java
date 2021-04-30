// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyDigitalWalletResource;
import com.medfusion.gateway_proxy.utils.DigitalWalletUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GatewayProxyDigitalWalletTests extends GatewayProxyBaseTest{

    protected static PropertyFileLoader testData;

    @BeforeTest
    public void setUp() throws Exception{
        testData = new PropertyFileLoader();
        setupRequestSpecBuilder();
        setupResponsetSpecBuilder();
    }

	
	
	  @Test public void addNewCardAndCreateWallet() throws Exception {
	  GatewayProxyDigitalWalletResource digitalWallet = new
	  GatewayProxyDigitalWalletResource(); Response response
	  =digitalWallet.createNewWallet();
	  
	  JsonPath jsonpath = new JsonPath(response.asString());
	  
	  Assert.assertTrue(!jsonpath.get("externalWalletId").toString().isEmpty());
	  Assert.assertTrue(!jsonpath.get("walletCards[0].externalCardId").toString().
	  isEmpty());
	  Assert.assertEquals("VI-1111-1226",jsonpath.get("walletCards[0].cardAlias"));
	  
	  DigitalWalletUtils.saveWalletDetails(jsonpath.get("externalWalletId").
	  toString(),jsonpath.get("walletCards[0].externalCardId").toString());
	  
	  }	 
	  
    @Test(dependsOnMethods="addNewCardAndCreateWallet")
   
    public void getListOfCardsInWallet() throws Exception{
    	   	
    	GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
    	Response response = digitalWallet.getListOfCardsInWallet();
    	JsonPath jsonpath = new JsonPath(response.asString());
   		 	
    	Assert.assertTrue(!jsonpath.get("externalWalletId").toString().isEmpty());
    	Assert.assertTrue(!jsonpath.get("walletCards[0].cardExpiryDate").toString().isEmpty());
    	
    	String cardNumber = testData.getProperty("cardnumber");   
    	String aliasToVerify = testData.getProperty("type") + "-"+ cardNumber.substring(cardNumber.length() - 4) + "-"+testData.getProperty("expirationnumber");
    	Assert.assertEquals(aliasToVerify,jsonpath.get("walletCards[0].cardAlias"));    	
    }
}
