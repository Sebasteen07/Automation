// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyDigitalWalletResource;
import com.medfusion.gateway_proxy.utils.GatewayProxyDigitalWalletUtils;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.payment_modulator.pojos.PayloadDetails;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.awt.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GatewayProxyDigitalWalletTests extends GatewayProxyBaseTest {

	protected static PropertyFileLoader testData;
	protected String token =null;
    
	@BeforeTest
	public void setUp() throws Exception {
		
		testData = new PropertyFileLoader();
		token =  GatewayProxyUtils.getTokenForCustomer();
		setupRequestSpecBuilder();
		
	}


    @Test
	public void addNewCardAndCreateWalletByInvalidAuth() throws Exception {
		String token = GatewayProxyUtils.getTokenForCustomer()+"jadgcl";
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumber"),testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode"));
		Assert.assertTrue(response.getStatusCode() == 401);
    }
    
	@Test(dataProvider = "card_details")
	  public void addNewCardAndCreateWalletWithNullValues(String consumerName, String cardType, String cardNumber, String expiryDate, String cardAlias, String zipcode) throws Exception {
  
	  GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
	  Response response = digitalWallet.createNewWallet(token, consumerName, cardType, cardNumber, expiryDate, cardAlias, zipcode);

	  JsonPath jsonPath = new JsonPath(response.asString());
	  Assert.assertEquals(response.getStatusCode(), 400);
	  Assert.assertEquals("Bad Request", jsonPath.get("error"));
	  Assert.assertTrue(!jsonPath.get("message").toString().isEmpty());

	 }
    
    
    @Test
   	public void addNewCardAndCreateWalletByValidAuth() throws Exception {
   		
   		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
   		Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumber"),testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode"));
   		Assert.assertTrue(response.getStatusCode() == 200);

   		JsonPath jsonPath = new JsonPath(response.asString());
   		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
   		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
   		Assert.assertEquals("VI-1111-1226", jsonPath.get("walletCards[0].cardAlias"));
   		GatewayProxyDigitalWalletUtils.saveWalletDetails(jsonPath.get("externalWalletId").toString(), jsonPath.get("walletCards[0].externalCardId").toString());

   	}

	@Test

	public void getListOfCardsInWallet() throws Exception {

		
		System.out.println(testData.getProperty("testpaycustomeruuid"));
		GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		Response response = digitalWallet.getListOfCardsInWallet(token);
		JsonPath jsonPath = new JsonPath(response.asString());
				
		Assert.assertTrue(!jsonPath.get("externalWalletId").toString().isEmpty());
		Assert.assertTrue(!jsonPath.get("walletCards[0].cardExpiryDate").toString().isEmpty());

	
	  String cardNumber = testData.getProperty("cardnumber"); String aliasToVerify
	  = testData.getProperty("type") + "-" +
	  cardNumber.substring(cardNumber.length() - 4) + "-" +
	  testData.getProperty("expirationnumber");
	  Assert.assertEquals(aliasToVerify, jsonPath.get("walletCards[0].cardAlias"));
	  Assert.assertTrue(response.getStatusCode() == 200);
	}
	
	 @Test
		public void getListOfCardsInWalletByInvalidAuth() throws Exception {
		 
			String token1 = token +"jadgcl";
			GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
			Response response = digitalWallet.getListOfCardsInWallet(token1);
			Assert.assertTrue(response.getStatusCode() == 401);
			
	    }

	@Test
	public void deleteCardInWallet() throws Exception {
		
		//String token = GatewayProxyUtils.getTokenForCustomer();
		
		 GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
		
	   	Response response = digitalWallet.createNewWallet(token, testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumberdeletion"),testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode"));
		  
		  JsonPath jsonPath = new JsonPath(response.asString());
		  
	      Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().
		  isEmpty()); String cardNumber = testData.getProperty("cardnumberdeletion");
		  String aliasToVerify = testData.getProperty("type") + "-" +
		  cardNumber.substring(cardNumber.length() - 4) + "-" +
		  testData.getProperty("expirationnumber"); Assert.assertEquals(aliasToVerify,
		  jsonPath.get("walletCards[0].cardAlias"));
		  
		  String externalWalletId = jsonPath.get("externalWalletId").toString(); String
		  externalCardId = jsonPath.get("walletCards[0].externalCardId").toString();
		  
		  // Delete card in a wallet Response deleteResponse =
		  Response deleteResponse  =digitalWallet.deleteCardInWallet(token,externalWalletId, externalCardId);
		  
		  JsonPath jsonPathDelete = new JsonPath(deleteResponse.asString());
		  
		  Assert.assertEquals(externalWalletId,
		  jsonPathDelete.get("externalWalletId")); Assert.assertEquals(externalCardId,
		  jsonPathDelete.get("walletCards[0].externalCardId"));
			Assert.assertEquals("DELETED",
		  jsonPathDelete.get("walletCards[0].status").toString());
		  Assert.assertTrue(deleteResponse.getStatusCode() == 200);
		 
	}
	
	 @Test
		public void deleteCardInWalletByInvalidAuth() throws Exception {
		 
			String token1 = token +"jadgcl";
			GatewayProxyDigitalWalletResource digitalWallet = new GatewayProxyDigitalWalletResource();
			Response deleteResponse  =digitalWallet.deleteCardInWallet(token1,testData.getProperty("externalWalletId"), testData.getProperty("externalCardId"));
			Assert.assertTrue(deleteResponse.getStatusCode() == 401);
			
	    }
	
	
	
	@DataProvider(name = "card_details")
	public Object[][] dpMethod(){
		return new Object[][] {
				{"", testData.getProperty("type"),testData.getProperty("cardnumber"),testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), "",testData.getProperty("cardnumber"),testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), testData.getProperty("type"),"",testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumber"),"1220",testData.getProperty("cardalias"),testData.getProperty("zipcode")},
				{testData.getProperty("consumername"), testData.getProperty("type"),testData.getProperty("cardnumber"),testData.getProperty("expirationnumber"),testData.getProperty("cardalias"),""}
		};
	}

}
