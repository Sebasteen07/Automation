// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;

public class GatewayProxyBaseTest extends GatewayProxyUtils{
	
	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;


	public static void setupRequestSpecBuilder() throws Exception
	{
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("proxybaseurl");
		requestSpec	 = new RequestSpecBuilder()
	   .setContentType(ContentType.JSON)
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
