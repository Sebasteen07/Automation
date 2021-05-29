// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GatewayProxyDigitalWalletResource extends GatewayProxyBaseTest {

    protected PropertyFileLoader testData;

    public Response createNewWallet(String token, String consumerName, String cardType, String cardNumber, String expiryDate, String cardAlias, String zipcode) throws Exception {
        testData = new PropertyFileLoader();
        Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToDigitalWallet(consumerName, cardType, cardNumber, expiryDate, cardAlias, zipcode );

        Response response = given().spec(requestSpec).header("Authorization","Bearer "+ token).
                body(digitalWallet).when().post(testData.getProperty("testpaycustomeruuid")+
                "/wallets")
                .then().extract().response();

        return response;
    }


    public Response getListOfCardsInWallet(String token) throws IOException{
    	
    	 testData = new PropertyFileLoader();

    	 Response response = given().that().spec(requestSpec).header("Authorization","Bearer "+ token).
                 when().get(testData.getProperty("testpaycustomeruuid")+
                 "/wallets/" + testData.getProperty("externalWalletId"))
                 .then().and().extract().response();
                   
    	 return response;
    	 
    }
}
