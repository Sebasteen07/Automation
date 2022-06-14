// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequestNGE extends BaseTestNGWebDriver {

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification aPIVerification = new APIVerification();

	public void setupRequestSpecBuilder(String baseurl) {

		RestAssured.baseURI = baseurl;

		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	public Response appointmentStatus(String practiceid, String patientId) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec)
				.queryParam("appointmentId", "00b971f3-b83f-42c2-ac31-9c748fe7bef3").queryParam("patientId", patientId)
				.queryParam("startDateTime", "1612522800").when().get(practiceid + APIPath.apiPath.Appointment_Status)
				.then().log().all().extract().response();
		return response;

	}

	public Response appointmentType(String practiceid) {

		Response response = given().spec(requestSpec).log().all().when().get(practiceid + "/appointmenttypes")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response appointmentTypeSearch(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().body(b)
				.post(practiceid + "/appointmenttypesearch").then().log().all().extract().response();
		return response;
	}

	public Response rescheduleApptNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().body(b)
				.post(practiceid + APIPath.apiPath.rescheduleAppt).then().log().all().extract().response();
		return response;
	}

	public Response cancellationReasonT(String practiceid) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec).when()
				.get(practiceid + APIPath.apiPath.Cancel_Reason).then().log().all().extract().response();

		return response;

	}

	public Response pastApptNG(String practiceid, String b) {
		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceid + APIPath.apiPath.Past_APPT).then().log().all().extract().response();
		return response;

	}

	public Response nextAvailableNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.next_Available).then().log().all().extract().response();
		return response;

	}

	
		public Response scheduleApptNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).when().body(b).log().all().when()
				.post(practiceid + APIPath.apiPath.scheduleApptNG).then().log().all().extract().response();
		return response;
	}

	public Response upcommingApptNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).when().body(b).log().all()
				.post(practiceid + APIPath.apiPath.upcommingApptNG).then().log().all().extract().response();
		return response;

	}

	public Response availableSlots(String b, String practiceId) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec).body(b).when()
				.post(practiceId + "/availableslots").then().log().all().extract().response();
		return response;
	}


	public Response cancelAppointmentGET(String practiceid, String appId) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec)
				.queryParam("additionalFields", "Cancel").queryParam("appointmentId", appId).when()
				.get(practiceid + APIPath.apiPath.cancelAppointment).then().log().all().extract().response();

		return response;
	}

	public Response cancelAppointmentPOST(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec)
				.queryParam("additionalFields", "Cancel")
				.queryParam("appointmentId", "8ce85b6a-268a-4ef1-9baa-446afd56367d").when().body(b)
				.post(practiceid + APIPath.apiPath.cancelAppointment).then().log().all().extract().response();

		return response;
	}

	public Response prerequisteappointmenttypesPOST(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.prerequisteappointmenttypesNG).then().log().all().extract()
				.response();
		return response;
	}

	public Response cancellationReason(String practiceid) {

		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + APIPath.apiPath.cancellationReason).then().log().all().extract().response();

		return response;
	}

	public Response careproviderAvailability(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.careprovideravailabilityNG).then().log().all().extract().response();
		return response;
	}

	public Response insuranceCarrier(String practiceid) {

		Response response = given().when().spec(requestSpec).log().all()
				.get(practiceid + "/insurancecarrier").then().log().all().extract().response();
		return response;
	}

	public Response locations(String practiceid) {

		Response response = given().log().all().when().get(practiceid +"/locations").then().log().all()
				.extract().response();
		return response;

	}
	
	public Response lastSeenGET(String practiceid) {

		Response response = given().log().all().when().get(practiceid +"/patientlastvisit").then().log().all()
				.extract().response();
		return response;

	}

	public Response demographics(String practiceid,String patientId) {

		Response response = given().spec(requestSpec).log().all().queryParam("patientId", patientId).when()
				.get(practiceid + APIPath.apiPath.demographicNG).then().log().all().extract().response();
		return response;
	}
	
	public Response demographicsNGE(String practiceid,String patientId) {

		Response response = given().spec(requestSpec).log().all().queryParam("patientId", patientId).when()
				.get(practiceid + APIPath.apiPath.demographicNG).then().log().all().extract().response();
		return response;
	}

	public Response lockout(String practiceid) {

		Response response = given().log().all().when().get(practiceid + APIPath.apiPath.lockoutNG).then().log().all()
				.extract().response();

		return response;
	}

	public Response patientLastVisit(String practiceid,String patientId) {

		Response response = given().log().all().when()
				.get(practiceid +"/patientlastvisit/"+patientId).then().log().all().extract().response();
		return response;
	}

	public Response patietStatus(String practiceid) {

		Response response = given().log().all().when().get(practiceid + "/patientstatus").then().log()
				.all().extract().response();

		return response;
	}

	public Response matchPatientPOST(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.patientMatchNG).then().log().all().extract().response();
		return response;
	}

	public Response patientRecordbyApptTypePOST(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.patientrecordbyapptypesNG).then().log().all().extract().response();
		return response;
	}

	public Response searchpatient(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.searchpatientNG).then().log().all().extract().response();

		return response;
	}

	public Response patientrecordbyBooks(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.patientrecordbybooksNG).then().log().all().extract().response();
		return response;
	}

	public Response lastseenProvider(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.lastseenproviderNG).then().log().all().extract().response();

		return response;
	}

	public Response fetchNGBookList(String practiceid) {

		Response response = given().log().all().when().get(practiceid + "/books").then().log().all()
				.extract().response();

		return response;
	}

	public Response fetchNGSpeciltyList(String practiceid) {

		Response response = given().spec(requestSpec).log().all().when().get(practiceid + APIPath.apiPath.specialtyNG)
				.then().spec(responseSpec).log().all().extract().response();

		return response;
	}

	public Response addPatient(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b).post(practiceid + "/addpatient").then()
				.log().all().extract().response();

		return response;
	}
	
	public Response cancelStatus(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b).post(practiceid + "/cancelstatus").then()
				.log().all().extract().response();

		return response;
	}
	
}
