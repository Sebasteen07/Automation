// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequestAT {

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public void setupRequestSpecBuilder(String baseurl) {

		RestAssured.baseURI = baseurl;

		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	public Response appointmentStatus(String patientIdtestdata, String practiceid, String apptid, String startDateTime)
			throws Exception {
		Response response = given().queryParam("patientId", patientIdtestdata).queryParam("appointmentId", apptid)
				.queryParam("startDateTime ", startDateTime).log()
				.all().spec(requestSpec).when().get(practiceid + "/appointmentstatus").then().spec(responseSpec).log()
				.all().extract().response();
		return response;
	}

}
