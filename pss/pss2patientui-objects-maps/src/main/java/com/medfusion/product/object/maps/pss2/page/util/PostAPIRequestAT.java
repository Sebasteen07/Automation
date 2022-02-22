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

	public Response cancelAppt(String practiceid, String appointmentId, String patientId) throws Exception {
		Response response = given().queryParam("appointmentId", appointmentId).log().all().spec(requestSpec).when()
				.get(practiceid + "/cancelappointment/" + patientId).then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}

	public Response cancelApptWithoutAppointmentId(String practiceid, String patientId) throws Exception {
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
	
	public Response upcommingApptInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/upcomingappointments")
				.then().log().all().extract().response();
		return response;
	}

	public Response lastSeenProvider(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/getlastseenprovider")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response lastSeenproviderInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/getlastseenprovider")
				.then().log().all().extract().response();
		return response;
	}

	public Response careProviderAvailability(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/careprovideravailability")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response careProviderAvailabilityInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/careprovideravailability")
				.then().log().all().extract().response();
		return response;
	}

	public Response addPatient(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/addpatient").then().log().all().spec(responseSpec).extract().response();
		return response;
	}
	
	public Response addPatientInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/addpatient").then().log()
				.all().extract().response();
		return response;
	}

	public Response matchPatient(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/matchpatient").then().spec(responseSpec)
				.log().all().extract().response();
		return response;
	}
	
	public Response matchPatientInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/matchpatient").then()
				.log().all().extract().response();
		return response;
	}

	public Response searchPatient(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/searchpatient").then().spec(responseSpec)
				.log().all().extract().response();
		return response;
	}
	
	public Response searchPatientInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/searchpatient").then()
				.log().all().extract().response();
		return response;
	}

	public Response prerequisteAppointmentTypes(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b)
				.post(practiceid + "/prerequisteappointmenttypes").then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response prerequisteAppointmentTypesInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b)
				.post(practiceid + "/prerequisteappointmenttypes").then().log().all().extract().response();
		return response;
	}

	public Response nextavailableSlots(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/nextavailableslots")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}

	public Response nextavailableSlotsWithInvalidApptId(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/nextavailableslots")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response availableSlots(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/availableslots").then()
				.log().all().extract().response();

		return response;
	}

	public Response scheduleAppt(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/scheduleappointment")
				.then().spec(responseSpec).log().all().extract().response();
		return response;
	}
	
	public Response scheduleApptInvalid(String practiceid, String b) throws Exception {
		Response response = given().log().all().spec(requestSpec).body(b).post(practiceid + "/scheduleappointment")
				.then().log().all().extract().response();
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

	public Response appointmentTypes(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/appointmenttypes").then().spec(responseSpec).log()
				.all().extract().response();
		return response;
	}
	
	public Response appointmentTypesInvalid() throws Exception {
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

	public Response cancellationReason(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/cancellationreason").then().spec(responseSpec).log()
				.all().extract().response();
		return response;
	}
	
	public Response cancellationReasonInvalid(String practiceid) throws Exception {
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

	public Response patientStatus(String practiceid) throws Exception {
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

	public Response insuranceCarrier(String practiceid) throws Exception {
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
	public Response prerequisteAppointmentTypes(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/prerequisteappointmenttypes").then().spec(responseSpec).log().all().extract()
				.response();
		return response;
	}
	
	public Response prerequisteAppointmentTypesInvalid(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/prerequisteappointmenttypess").then().log().all().extract()
				.response();
		return response;
	}
	
	public Response addPatient(String practiceid) throws Exception {
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
	public Response preventSchedulingDate(String practiceid,String endPoint) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + endPoint).then().log().all().extract()
				.response();
		return response;
	}
	
	public Response preventSchedulingdateInvalid(String practiceid) throws Exception {
		Response response = given().log().all().spec(requestSpec).get(practiceid + "/preventschedulingdate/10282").then().log().all().extract()
				.response();
		return response;
	}

}
