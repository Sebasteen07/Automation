// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.digital_wallet.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

//This class is specifically for Digital Wallet APIs which are NOT hit through gateway-proxy
public class DigitalWalletBaseTest {
    protected static PropertyFileLoader testData;
    public static RequestSpecification requestSpec;

    public static void setupRequestSpecBuilder() throws Exception
    {
        testData = new PropertyFileLoader();
        RestAssured.baseURI = testData.getProperty("digital.wallet.baseurl");
        requestSpec	 = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    public static void setupRequestSpecBuilderv2Endpoint() throws Exception
    {
        testData = new PropertyFileLoader();
        RestAssured.baseURI = testData.getProperty("digital.wallet.baseurl.v2");
        requestSpec	 = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

}
