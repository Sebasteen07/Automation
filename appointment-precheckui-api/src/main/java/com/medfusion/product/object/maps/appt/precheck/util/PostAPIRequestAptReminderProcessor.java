// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestAptReminderProcessor extends BaseTestNGWebDriver {
	private static PostAPIRequestAptReminderProcessor postAPIRequest = new PostAPIRequestAptReminderProcessor();

	private PostAPIRequestAptReminderProcessor() {
	}

	public static PostAPIRequestAptReminderProcessor getPostAPIRequestAptReminderProcessor() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response clearSettingsCache(Map<String, String> Header) {
		log("Execute Delete request for Clear Settings Cache");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().delete("cache/settings").then()
				.log().all().extract().response();
		return response;
	}

	public Response clearSettingsCacheForPractice(Map<String, String> Header, String practiceId) {
		log("Execute Delete request for Clear Settings Cache for Practice");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().delete("cache/settings/practice/"+practiceId).then()
				.log().all().extract().response();
		return response;
	}

	public Response clearSettingsCacheForPracticeWithoutPracticeId(Map<String, String> Header) {
		log("Execute Delete request for Clear Settings Cache for Practice");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("cache/settings/practice/").then().log().all().extract().response();
		return response;
	}

	public Response processReminderData(String payload, Map<String, String> Header) {
		log("Execute Post request for Process Reminder Data");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("processor/reminder_data").then().log().all().extract().response();
		return response;
	}
}