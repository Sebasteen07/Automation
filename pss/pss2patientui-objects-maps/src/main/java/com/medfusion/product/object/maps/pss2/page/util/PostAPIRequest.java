// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequest extends BaseTestNGWebDriver {

	RequestSpecification req = new RequestSpecBuilder().setContentType("application/json")
			.setBaseUri("https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1").build();

	ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType("application/json")
			.build();

	public Response sampleMethod01(String baseurl, String b, Map<String, String> Header) {

		Response response = given().log().all().spec(req).headers(Header).body(b).post("/24248/availableslots/26407")
				.then().log().all().spec(res).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		log(response.asString());
		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "slotTime");
		ParseJSONFile.getKey(jsonobject, "SlotId");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response sampleMethod(String baseurl, String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).when().post().then().log().all()
				.extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "name");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseTimeValidation(response);
		return response;
	}

}
