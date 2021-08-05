// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.json.JSONArray;

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
				.queryParam("appointmentId", apptid).queryParam("startDateTime", startDateTime).log().all().when()
				.get(practiceid + "/appointmentstatus").then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response appointmentStatusWithoutPatientId(String practiceid, String apptid, String startDateTime)
			throws Exception {
		Response response = given().spec(requestSpec)
				.queryParam("appointmentId", apptid).queryParam("startDateTime", startDateTime).log().all().when()
				.get(practiceid + "/appointmentstatus").then().log().all().extract().response();
		return response;
	}

	public Response demographics(String patientIdtestdata, String practiceid) throws Exception {
		Response response = given().queryParam("patientId", patientIdtestdata).log().all().spec(requestSpec).when()
				.get("24253/demographics").then().spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response cancelappt(String practiceid, String appointmentId, String patientId) throws Exception {
		Response response = given().queryParam("appointmentId", appointmentId).log().all().spec(requestSpec).when()
				.get(practiceid + "/cancelappointment/" + patientId).then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}

	public Response cancelapptWithoutAppointmentId(String practiceid, String patientId) throws Exception {
		Response response = given().log().all().spec(requestSpec).when()
				.get(practiceid + "/cancelappointment/" + patientId).then().log().all().extract()
				.response();
		return response;
	}
	
	public Response cancelApptPost(String practiceid, String apptid, String patientid) throws Exception {
		Response response = given().spec(requestSpec).body("{      \"appointmentId\": \"1783620\"\r\n" + "}").log()
				.all().when().post(practiceid + "/cancelappointment/" + patientid).then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}

	public Response pastAppt(String practiceid, String appointmentId, String patientId, Map<String, Object> hm)
			throws Exception {
		Response response = given().log().all().spec(requestSpec).body(hm).post(practiceid + "/pastappointments/")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response pastApptInvalid(String practiceid, String appointmentId, String patientId, Map<String, Object> hm)
			throws Exception {
		Response response = given().log().all().spec(requestSpec).body(hm).post(practiceid + "/pastappointments/")
				.then().log().all().extract().response();
		return response;
	}

	public Response upcommingAppt(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/upcomingappointments")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response lastseenprovider(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/getlastseenprovider")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response lastseenproviderInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/getlastseenprovider")
				.then().log().all().extract().response();
		return response;
	}

	public Response careprovideravailability(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/careprovideravailability")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response careprovideravailabilityInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/careprovideravailability")
				.then().log().all().extract().response();
		return response;
	}

	public Response addpatient(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/addpatient").then().spec(responseSpec).log()
				.all().extract().response();
		return response;
	}
	
	public Response addpatientInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/addpatient").then().log()
				.all().extract().response();
		return response;
	}

	public Response matchpatient(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/matchpatient").then().spec(responseSpec)
				.log().all().extract().response();
		return response;
	}
	
	public Response matchpatientInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/matchpatient").then()
				.log().all().extract().response();
		return response;
	}

	public Response searchpatient(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/searchpatient").then()
				.log().all().extract().response();
		return response;
	}

	public Response prerequisteappointmenttypes(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b)
				.post(practiceid + "/prerequisteappointmenttypes").then().spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response nextavailableslots(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/nextavailableslots")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response nextavailableslotsWithInvalidApptId(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/nextavailableslots")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response availableslots(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/availableslots").then()
				.log().all().extract().response();

		return response;
	}

	public Response scheduleAppt(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/scheduleappointment")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response rescheduleAppt(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/rescheduleappointment")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response healthcheck(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/healthcheck").then().spec(responseSpec).log().all()
				.extract().response();
		return response;
	}
	
	public Response healthcheckInvalid(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/healthcheckk").then().log().all()
				.extract().response();
		return response;
	}

	public Response appointmenttypes(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/appointmenttypes").then().spec(responseSpec).log()
				.all().extract().response();
		return response;
	}
	
	public Response appointmenttypesInvalid() throws Exception {
		Response response = given().log().all().spec(requestSpec).get("/appointmenttypes").then().log()
				.all().extract().response();
		return response;
	}

	public Response books(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/books").then().spec(responseSpec).log().all()
				.extract().response();
		return response;
	}
	
	public Response booksInvalid() throws Exception {
		Response response = given().log().all().spec(requestSpec).get("/24260/books").then().log().all()
				.extract().response();
		return response;
	}

	public Response cancellationreason(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/cancellationreason").then().spec(responseSpec).log()
				.all().extract().response();
		return response;
	}
	
	public Response cancellationreasonInvalid(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/cancellationreasonn").then().log()
				.all().extract().response();
		return response;
	}

	public Response locations(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/locations").then().spec(responseSpec).log().all()
				.extract().response();
		return response;
	}
	
	public Response locationsInvalid(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/locationss").then().log().all()
				.extract().response();
		return response;
	}

	public Response patientstatus(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/patientstatus").then().spec(responseSpec).log().all()
				.extract().response();
		return response;
	}

	public Response lockout(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/lockout").then().spec(responseSpec).log().all()
				.extract().response();
		return response;
	}
	
	public Response lockoutInvalid(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/lockoutt").then().log().all()
				.extract().response();
		return response;
	}

	public Response insurancecarrier(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/insurancecarrier").then().spec(responseSpec).log()
				.all().extract().response();
		return response;
	}

	public Response ping(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/ping").then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}
	public Response specialty(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/specialty").then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}
	public Response prerequisteappointmenttypes(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/prerequisteappointmenttypes").then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}
	
	public Response addpatient(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/addpatient").then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}
	public Response actuator(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get("/actuator").then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}
	
	public Response actuatorInvalid(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get("/actuatorr").then().log().all().extract()
				.response();
		return response;
	}
	public Response preventschedulingdate(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/preventschedulingdate/10282/82").then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}

}
