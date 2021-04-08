// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.utils;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import com.medfusion.common.utils.PropertyFileLoader;

public class GatewayProxyUtils {
	
	protected static PropertyFileLoader testData;
	
	public void setUp() throws Exception{
		testData = new PropertyFileLoader();
		RestAssured.useRelaxedHTTPSValidation();
		
	}
	
	
	public static String getTokenForCustomer() throws Exception{
		testData = new PropertyFileLoader();
		RestAssured.useRelaxedHTTPSValidation();
		Map<String, Object> tokenpayload = new HashMap<String, Object>(); 
		tokenpayload.put("clientId", testData.getProperty("clientid"));
		tokenpayload.put("clientSecret", testData.getProperty("clientsecret"));
		tokenpayload.put("scope", "api:pay");
		Response response = given().contentType(ContentType.JSON).body(tokenpayload).and().log().body().
		when().post((testData.getProperty("posttokenurl"))+(testData.getProperty("testpaycustomeruuid")+"/tokens"))
	   .then().statusCode(200).and().extract().response();
		JsonPath jsonpath = new JsonPath(response.asString());
		return jsonpath.get("token").toString();
		
	}

}
