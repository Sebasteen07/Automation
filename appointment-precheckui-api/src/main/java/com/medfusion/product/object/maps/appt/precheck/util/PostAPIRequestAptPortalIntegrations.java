// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestAptPortalIntegrations extends BaseTestNGWebDriver {
	private static PostAPIRequestAptPortalIntegrations postAPIRequest = new PostAPIRequestAptPortalIntegrations();

	private PostAPIRequestAptPortalIntegrations() {
	}

	public static PostAPIRequestAptPortalIntegrations getPostAPIRequestAptPortalIntegrations() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response sendsConfirmation(String payload, Map<String, String> Header) {
		log("Execute Post request for Sends confirmation data to practice's PM/EMR system");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("confirmation").then().log().all().extract().response();
		return response;
	}

	public Response sendsPatientProvidedData(String payload, Map<String, String> Header) {
		log("Execute Post request for Sends patient provided data to practice's PM/EMR system.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("data").then().log().all().extract().response();
		return response;
	}

}
