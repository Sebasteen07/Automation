// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

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

	public Response appointmentStatus(String patientid, String practiceid, String apptid, String startDateTime)
			throws Exception {
		Response response = given().spec(requestSpec).queryParam("patientId", patientid)
				.queryParam("appointmentId", apptid)
				.queryParam("startDateTime", startDateTime).log()
				.all().when().get(practiceid + "/appointmentstatus").then().spec(responseSpec).log()
				.all().extract().response();
		return response;
	}

	public Response demographics(String patientIdtestdata, String practiceid) throws Exception {
		Response response = given().queryParam("patientId", patientIdtestdata).log().all().spec(requestSpec).when()
				.get("24253/demographics").then().spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response cancelappt(String practiceid, String appointmentId, String patientId) throws Exception {
		Response response = given().queryParam("appointmentId", appointmentId)
				.log().all().spec(requestSpec).when()
				.get(practiceid + "/cancelappointment/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract()
				.response();
		return response;
	}

	public Response cancelApptPost(String practiceid, String apptid, String patientid)
			throws Exception {
		Response response = given().spec(requestSpec).body("{      \"appointmentId\": \"1783620\"\r\n" + "}").log()
				.all().when().post(practiceid + "/cancelappointment/" + patientid).then().log().all()
				.extract()
				.response();
		return response;
	}

	public Response pastAppt(String practiceid, String appointmentId, String patientId, Map<String, Object> hm)
			throws Exception {
		Response response = given().log().all().spec(requestSpec).body(hm)
				.get(practiceid + "/pastappointments/").then().log().all().assertThat().statusCode(200)
				.extract()
				.response();
		return response;
	}
}
