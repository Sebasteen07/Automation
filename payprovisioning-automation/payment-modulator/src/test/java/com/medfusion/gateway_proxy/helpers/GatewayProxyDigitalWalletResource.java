// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GatewayProxyDigitalWalletResource extends GatewayProxyBaseTest {

    protected PropertyFileLoader testData;

    public Response createNewWallet() throws IOException {
        testData = new PropertyFileLoader();
        Map<String, Object> digitalWallet = PayloadDetails.getPayloadForAddingCardToDigitalWallet(testData.getProperty("consumername"),
                testData.getProperty("type"), testData.getProperty("cardnumber"),
                testData.getProperty("expirationnumber"), testData.getProperty("cardalias"),
                testData.getProperty("zipcode"));

        Response response = given().spec(requestSpec).
                body(digitalWallet).when().post(testData.getProperty("testpaycustomeruuid")+
                "/wallets")
                .then().spec(responseSpec).and().extract().response();
        return response;
    }
}
