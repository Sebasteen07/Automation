// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.digital_wallet.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.digital_wallet.utils.DigitalWalletAPIUtils;
import com.medfusion.gateway_proxy.utils.GatewayProxyDigitalWalletUtils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

//This class is specifically for Digital Wallet APIs which are NOT hit through gateway-proxy
public class DigitalWalletBaseTest {
    protected static PropertyFileLoader testData;
    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;


    public static void setupRequestSpecBuilder() throws Exception
    {
        testData = new PropertyFileLoader();
        RestAssured.baseURI = testData.getProperty("digitalwallet.baseurl");
        requestSpec	 = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","Bearer "+ DigitalWalletAPIUtils.getTokenForCustomer())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    public static void setupResponsetSpecBuilder()
    {
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

    }
}
