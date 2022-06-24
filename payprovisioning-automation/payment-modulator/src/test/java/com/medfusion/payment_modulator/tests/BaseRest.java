// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;
import com.medfusion.common.utils.PropertyFileLoader;

public class BaseRest {
	
	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;


	public static void setupRequestSpecBuilder() throws IOException
	{
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("base.url");
		requestSpec	 = new RequestSpecBuilder()
	   .setContentType(ContentType.JSON).and()
	   .addFilter(new ResponseLoggingFilter())
	   .addFilter(new RequestLoggingFilter())
	   .build();
	}

	public void setupResponsetSpecBuilder()
	{
		responseSpec = new ResponseSpecBuilder()
	   .expectContentType(ContentType.JSON)
	   .build();

	}

	public static void setupRequestSpecBuilderV5() throws IOException
	{
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("base.url.v5");
		requestSpec	 = new RequestSpecBuilder()
				.setContentType(ContentType.JSON).and()
				.addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter())
				.build();
	}

}
