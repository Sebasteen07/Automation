// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.utils;

import static io.restassured.RestAssured.given;

import com.intuit.ifs.csscat.core.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import com.medfusion.common.utils.PropertyFileLoader;

public class GatewayProxyUtils {

	protected static PropertyFileLoader testData;
	protected static PropertyFileLoad testsData;

	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		RestAssured.useRelaxedHTTPSValidation();

	}

	public static String getTokenForCustomer() throws Exception {
		testData = new PropertyFileLoader();
		RestAssured.useRelaxedHTTPSValidation();

		Map<String, Object> tokenpayload = new HashMap<String, Object>();
		tokenpayload.put("clientId", testData.getProperty("client.id"));
		tokenpayload.put("clientSecret", testData.getProperty("client.secret"));
		tokenpayload.put("scope", "api:pay");

		Response response = given().contentType(ContentType.JSON).body(tokenpayload).and().log().body().when()
				.post((testData.getProperty("post.token.url"))
						+ (testData.getProperty("test.pay.customer.uuid") + "/tokens"))
				.then().statusCode(200).and().extract().response();
		JsonPath jsonpath = new JsonPath(response.asString());

		return jsonpath.get("token").toString();

	}

	public static String getTokenForCustomerForEnv(String env) throws Exception {
		Map<String, Object> tokenpayload = new HashMap<String, Object>();
		Response response;
		testsData = new PropertyFileLoad(env);
		tokenpayload.put("clientId", testsData.getProperty("client.id"));
		tokenpayload.put("clientSecret", testsData.getProperty("client.secret"));
		tokenpayload.put("scope", "api:pay");

		if(env.equalsIgnoreCase("DEMO") || env.equalsIgnoreCase("DEV3")) {
			response = given().contentType(ContentType.JSON).body(tokenpayload).and().log().body().when()
					.post((testsData.getProperty("post.token.url"))
							+ (testsData.getProperty("test.pay.customer.uuid") + "/tokens"))
					.then().statusCode(200).and().extract().response();
		}
		else {
			response = given().header("x-api-key", testsData.getProperty("x-api-key"))
					.contentType(ContentType.JSON).body(tokenpayload).and().log().body().when()
					.post(testsData.getProperty("post.token.url")
							+ (testsData.getProperty("test.pay.customer.uuid") + "/tokens"))
					.then().statusCode(200).and().extract().response();

		}
		JsonPath jsonpath = new JsonPath(response.asString());
		System.out.println(jsonpath.get("token").toString());

		return jsonpath.get("token").toString();
	}


	public static long getEpochDate(int amount){
		/*
		If we want epoch date of the past, amount date will be a negative value
		for current day, amount date will be 0,
		for future days, amount date will be a postive value
		 */

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, amount);
		return calendar.getTime().getTime();
	}

	public static Object getEnvironmentType() {
		return TestConfig.getUserDefinedProperty("test.environment");
	}
}
