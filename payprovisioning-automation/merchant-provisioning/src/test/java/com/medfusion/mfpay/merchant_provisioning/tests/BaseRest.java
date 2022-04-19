// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.utils.User;

public class BaseRest extends BaseTestNG {
	
	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;


	public static void setupFinanceRequestSpecBuilder() throws IOException
	{
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("base.url");
		requestSpec	 = new RequestSpecBuilder()
	   .setContentType(ContentType.JSON)
	   .addHeader("Authorization", User.getCredentialsEncodedInBase("FINANCE"))
	   .addFilter(new ResponseLoggingFilter())
	   .addFilter(new RequestLoggingFilter())
	   .build();
	}
	
	public static void setupImplementationRequestSpecBuilder() throws IOException
	{
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("base.url");
		requestSpec	 = new RequestSpecBuilder()
	   .setContentType(ContentType.JSON)
	   .addHeader("Authorization", User.getCredentialsEncodedInBase("IMPLEMENTATION"))
	   .addFilter(new ResponseLoggingFilter())
	   .addFilter(new RequestLoggingFilter())
	   .build();
	}
	
	public static void setupAdminRequestSpecBuilder() throws IOException
	{
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("base.url");
		requestSpec	 = new RequestSpecBuilder()
	   .setContentType(ContentType.JSON)
	   .addHeader("Authorization", User.getCredentialsEncodedInBase("ADMIN"))
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
	

}
