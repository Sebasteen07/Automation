// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.Map;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequestGE extends BaseTestNGWebDriver {

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public void setupRequestSpecBuilder(String baseurl) {

		RestAssured.baseURI = baseurl;

		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	APIVerification apiVerification = new APIVerification();

	public Response healthCheck(String practiceId) {
		Response response = given().log().all().spec(requestSpec).when().get(practiceId + "/healthcheck").then().log()
				.all().extract().response();
		return response;
	}

	public Response ping(String practiceId) {
		Response response = given().log().all().when().get(practiceId + "/ping").then().log().all().extract()
				.response();
		return response;
	}

	public Response version(String practiceId) {
		Response response = given().log().all().when().get(practiceId + "/version").then().log().all().extract()
				.response();
		return response;
	}

	public Response lockOut(String practiceId) {
		Response response = given().log().all().when().get(practiceId + "/lockout").then().log().all().extract()
				.response();
		return response;
	}

	public Response appointmentType(String practiceId) {
		Response response = given().log().all().when().get(practiceId + "/appointmenttypes").then().log().all()
				.extract().response();
		return response;
	}

	public Response books(String practiceId) {
		Response response = given().log().all().when().get(practiceId + "/books").then().log().all().extract()
				.response();
		return response;
	}

	public Response insuranceCarrier(String practiceId) {
		Response response = given().log().all().when().get(practiceId + "/insurancecarrier").then().log().all()
				.extract().response();
		return response;
	}

	public Response locations(String practiceId) {
		Response response = RestAssured.given().log().all().when().get(practiceId + "/locations").then().log().all()
				.extract().response();
		return response;
	}

	public Response actuator(String endPointPath) {
		Response response = given().log().all().when().get(endPointPath).then().log().all().extract().response();
		return response;
	}

	public Response lastseenProvider(String b, String practiceId) {
		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceId + "/getlastseenprovider").then().log().all().extract().response();
		return response;
	}

	public Response appointmentStatus(String practiceId, String appQuery, String apptId, String patientIdQuery,
			String patientId, String startDateQuery, String startDateTime, String apptTypeId) {
		Response response = given().log().all().queryParam(appQuery, apptId).queryParam(patientIdQuery, patientId)
				.queryParam(startDateQuery, startDateTime).when().get(practiceId + "/appointmentstatus").then().log()
				.all().extract().response();

		return response;
	}

	public Response scheduleApptPatient(String body, String practiceid) throws IOException {

		Response response = given().spec(requestSpec).log().all().body(body).when()
				.post(practiceid + "/scheduleappointment").then().log().all().extract().response();
		return response;
	}

	public Response cancelAppointmentStatus(String b, String practiceId) {

		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceId + "/cancelstatus")
				.then().log().all().extract().response();
		return response;
	}

	public Response cancelAppointment(String apptId, String practiceId, String patientId) {
		Response response = RestAssured.given().log().all().queryParam("appointmentId", apptId).when()
				.get(practiceId + "/cancelappointment/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response cancelApptWithCancelReason(String b, String practiceId, String patientId) {

		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceId + "/cancelappointment/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response pastAppointments(String b, String practiceId) {

		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceId + "/pastappointments")
				.then().log().all().extract().response();
		return response;
	}

	public Response upcomingAppt(String b, String practiceId) {

		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceId + "/upcomingappointments").then().log().all().extract().response();
		return response;
	}

	public Response preventScheduling(String practiceId, String patientId, String apptId) {
		Response response = given().log().all().when()
				.get(practiceId + "/preventschedulingdate/" + patientId + "/" + apptId).then().log().all().extract()
				.response();
		return response;
	}

	public Response rescheduleAppt(String b, String practiceId) {

		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceId + "/rescheduleappointment").then().log().all().extract().response();
		return response;
	}

	public Response availableSlots(String b, String practiceId) {

		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceId + "/availableslots")
				.then().log().all().extract().response();
		return response;
	}

	public Response addPatientPost(String b, String practiceId) {

		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceId + "/addpatient").then()
				.log().all().extract().response();
		return response;
	}

	public Response careProviderPost(String b, String practiceId) {
		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceId + "/careprovideravailability").then().log().all().extract().response();
		return response;
	}

	public Response demographicsGET(String practiceId, String patientId) {

		Response response = given().queryParam("patientId", patientId).log().all().when()
				.get(practiceId + "/demographics").then().log().all().extract().response();
		return response;

	}

	public Response healthOperationGET(String endPointPath) {
		Response response = given().log().all().when().get(endPointPath).then().log().all().extract().response();
		return response;

	}

	public Response matchPatientPost(String b, String practiceId) {
		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceId + "/matchpatient")
				.then().log().all().extract().response();
		return response;

	}

	public Response patientLastVisit(String practiceId, String patientId) {

		Response response = given().log().all().when().get(practiceId + "/patientlastvisit/" + patientId).then().log()
				.all().extract().response();
		return response;
	}

	public Response preReqAppointmentTypes(String practiceId) {

		Response response = given().log().all().when().get(practiceId + "/prerequisteappointmenttypes").then().log()
				.all().extract().response();
		return response;
	}

	public Response searchPatientPost(String b, String practiceId) {
		Response response = given().spec(requestSpec).log().all().body(b).when().post(practiceId + "/searchpatient")
				.then().log().all().extract().response();
		return response;

	}
}
