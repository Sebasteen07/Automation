// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.JsonObject;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;

public class GatewayProxyBaseTest extends GatewayProxyUtils {

	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;

	public static void setupRequestSpecBuilder() throws Exception {
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("proxybaseurl");
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter()).build();
	}

}
