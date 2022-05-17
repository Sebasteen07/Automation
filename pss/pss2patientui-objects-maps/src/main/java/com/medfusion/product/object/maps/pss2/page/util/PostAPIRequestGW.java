// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequestGW extends BaseTestNGWebDriver {
	APIVerification apiVerification = new APIVerification();

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public void setupRequestSpecBuilder(String baseurl) {

		RestAssured.baseURI = baseurl;

		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	public Response demographics(String patientIdtestdata, String practiceid) throws Exception {
		Response response = given().queryParam("patientId", patientIdtestdata).log().all().spec(requestSpec).when()
				.get(practiceid + "/demographics").then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response demographicsWithoutPid(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when()
				.get(practiceid + "/demographics").then().log().all().extract().response();
		return response;
	}


	public Response searchPatient(Map<String, Object> map, String practiceid) throws IOException {

		return given().spec(requestSpec).log().all().body(map).when().post(practiceid + "/searchpatient").then().log()
				.all().extract().response();
	}

	public Response appointmentStatus(String practiceid, String appointmentId, String startTime) throws Exception {
		Response response = given().queryParam("appointmentId", appointmentId).queryParam("startDateTime", startTime)
				.log().all().spec(requestSpec).when().get(practiceid + "/appointmentstatus").then().spec(responseSpec)
				.log().all().extract().response();
		return response;
	}

	public Response appointmentStatusWithoutAppId(String practiceid, String startTime) throws Exception {
		Response response = given().queryParam("appointmentId", "").queryParam("startDateTime", startTime).log().all()
				.spec(requestSpec).when().get(practiceid + "/appointmentstatus").then().log().all().extract()
				.response();
		return response;
	}

	public Response getcancelappointment(String appointmentid, String practiceId, String patientId) throws Exception {
		Response response = given().queryParam("appointmentId", appointmentid).log().all().spec(requestSpec).when()
				.get(practiceId + "/cancelappointment/" + patientId).then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}

	public Response avaliableSlot(Map<String, Object> map, String practiceid) throws IOException {

		return given().spec(requestSpec).log().all().body(map).when().post(practiceid + "/availableslots").then().log()
				.all().extract().response();
	}

	public Response nextavaliableSlot(Map<String, Object> map, String practiceid) throws IOException {

		return given().spec(requestSpec).log().all().body(map).when().post(practiceid + "/nextavailableslots").then()
				.log().all().extract().response();
	}

	public Response pastAppt(String practiceid, String body) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(body).post(practiceid + "/pastappointments").then()
				.log().all().assertThat().statusCode(200).extract().response();
		return response;
	}
	
	public Response pastApptWithOutPid(String practiceid, Map<String, Object> hm) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(hm).post(practiceid + "/pastappointments").then()
				.log().all().extract().response();
		return response;
	}

	public Response upcommingAppt(String practiceid, Map<String, Object> hm) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(hm).post(practiceid + "/upcomingappointments")
				.then().log().all().assertThat().statusCode(200).extract().response();
		return response;
	}
	
	public Response upcommingApptWithoutPatientId(String practiceid, Map<String, Object> hm) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(hm).post(practiceid + "/upcomingappointments")
				.then().log().all().extract().response();
		return response;
	}


	public Response preventSchedulingDate(String patientId, String practiceid, String extAppId) throws Exception {
		Response response = given().log().all().spec(requestSpec).when()
				.get(practiceid + "/preventschedulingdate/" + patientId + "/" + extAppId).then().spec(responseSpec)
				.log().all().extract().response();
		return response;
	}
	
	public Response preventSchedulingDateWithoutExtId(String patientId, String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when()
				.get(practiceid + "/preventschedulingdate/" + patientId).then().log().all().extract().response();
		return response;
	}
	
	public Response preventSchedulingDateWithoutPid(String practiceid, String extAppId) throws Exception {
		Response response = given().log().all().spec(requestSpec).when()
				.get(practiceid + "/preventschedulingdate/" + extAppId).then().log().all().extract().response();
		return response;
	}

	public Response insurancecarrier(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/insurancecarrier").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response insurancecarrierWithoutPracticeId() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/insurancecarrier").then().log().all()
				.extract().response();
		return response;
	}

	public Response appointmenttypes(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/appointmenttypes").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response appointmenttypesWithoutPracticeID() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("appointmenttypes").then()
				.log().all().extract().response();
		return response;
	}

	public Response book(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/books").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response bookWithoutPracticeId() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/books").then()
				.log().all().extract().response();
		return response;
	}

	public Response flags(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/flags").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}
	public Response flagsWithout() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/flags").then().log().all().extract().response();
		return response;
	}

	public Response patientFlags(String practiceid, String patientId) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/patientflag/" + patientId)
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response patientFlagsWithoutPracticeId(String patientId) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/patientflag/" + patientId)
				.then().log().all().extract().response();
		return response;
	}
	
	public Response patientFlagsWithoutPId(String practiceId) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceId+"/patientflag/")
				.then().log().all().extract().response();
		return response;
	}

	public Response healthcheck(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/healthcheck").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response healthcheckWithoutPracticeId() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/healthcheck").then().log().all()
				.extract().response();
		return response;
	}

	public Response locations(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/locations").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response locationsWithoutPracticeId() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/locations").then().log()
				.all().extract().response();
		return response;
	}

	public Response lockout(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/lockout").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response lockoutWithoutPracticeId() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/lockout").then().log().all().extract()
				.response();
		return response;
	}
	public Response ping(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/ping").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response pingWithoutPracticeId() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/ping").then()
				.log().all().extract().response();
		return response;
	}

	public Response version(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/version").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response versionWithoutPracticeId() throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get("/version").then()
				.log().all().extract().response();
		return response;
	}
	
	public Response appointmentTypes(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when().get(practiceid + "/appointmenttypes").then()
				.spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response patientLastVisit(String patientId, String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when()
				.get(practiceid + "/patientlastvisit/" + patientId).then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}
	
	public Response patientLastVisitWithoutPracticeId(String patientId) throws Exception {
		Response response = given().log().all().spec(requestSpec).when()
				.get("/patientlastvisit/" + patientId).then().log().all().extract()
				.response();
		return response;
	}

	public Response patientLastVisitWithoutPatientId(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).when()
				.get(practiceid + "/patientlastvisit/").then().log().all().extract()
				.response();
		return response;
	}
	public Response addPatient(String body, String practiceid) throws IOException {

		return given().spec(requestSpec).log().all().body(body).when().post(practiceid + "/addpatient").then().log()
				.all().extract().response();
	}

	public Response matchPatient(String body, String practiceid) throws IOException {

		return given().spec(requestSpec).log().all().body(body).when().post(practiceid + "/matchpatient").then().log()
				.all().extract().response();
	}

	public Response cancelstatus(String body, String practiceid) throws IOException {
		return given().spec(requestSpec).log().all().body(body).when().post(practiceid + "/cancelstatus").then().log()
				.all().extract().response();
	}
	
	public Response cancelstatuswithoutPracticeId(String body) throws IOException {
		return given().spec(requestSpec).log().all().body(body).when().post("cancelstatus").then().log()
				.all().extract().response();
	}

	public Response cancelappointment(String body, String practiceid, String patientId) throws IOException {
		return given().spec(requestSpec).log().all().body(body).when()
				.post(practiceid + "/cancelappointment/" + patientId).then().log().all().extract().response();
	}

	public Response scheduleappointment(String body, String practiceid) throws IOException {

		return given().spec(requestSpec).log().all().body(body).when().post(practiceid + "/scheduleappointment").then()
				.log().all().extract().response();
	}

	public Response reScheduleappointment(String body, String practiceid) throws IOException {

		return given().spec(requestSpec).log().all().body(body).when().post(practiceid + "/rescheduleappointment").then()
				.log().all().extract().response();
	}

}
