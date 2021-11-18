// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;

public class GatewayProxyBaseTest extends GatewayProxyUtils {

	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public static void setupRequestSpecBuilder() throws Exception {
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("proxy.base.url");
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).addFilter(new ResponseLoggingFilter())
				.addFilter(new RequestLoggingFilter()).build();
	}

	public static void setupResponsetSpecBuilder() {
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	public static void verifySuccessfulResponce(Response responce) {
		JsonPath jsonPath = new JsonPath(responce.asString());
		Assert.assertTrue(!jsonPath.get("walletCards[0].externalCardId").toString().isEmpty());
		Assert.assertEquals(jsonPath.get("walletCards[0].primaryCard"), true);
	}

	public static String getUrl(String env, String customeruuid, String mmid, String endpoint) {

		String url = null;
		if (env.equalsIgnoreCase("DEV3") || env.equalsIgnoreCase("DEMO")) {

			url = customeruuid + "/merchant/" + mmid + endpoint;
		} else {
			url = customeruuid + "/pay/merchants/" + mmid + endpoint;

		}
		return url;
	}

}
