package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestAptReminderScheduler extends BaseTestNGWebDriver {
	private static PostAPIRequestAptReminderScheduler postAPIRequest = new PostAPIRequestAptReminderScheduler();

	private PostAPIRequestAptReminderScheduler() {
	}

	public static PostAPIRequestAptReminderScheduler getPostAPIRequestAptReminderScheduler() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response updateApptMetadata(String payload, Map<String, String> Header) {
		log("Execute Put request for update Appointment Metadata");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when().put("metadata")
				.then().log().all().extract().response();
		return response;
	}

	public Response metadataForPracticeAppts(Map<String, String> Header, String practiceId) {
		log("Execute get for get Metadata For Practice Appointments.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("metadata/practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response metadataForPracticeApptsWithoutPracticeId(Map<String, String> Header) {
		log("Execute get for get Metadata For Practice Appointments.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("metadata/practice/")
				.then().log().all().extract().response();
		return response;
	}

	public Response metadataForAppts(Map<String, String> Header, String practiceId, String patientId, String apptId) {
		log("Execute get for get Metadata For Appointments.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("metadata/practice/" + practiceId + "/patient/" + patientId + "/appointment/" + apptId).then()
				.log().all().extract().response();
		return response;
	}

	public Response metadataForApptsWithoutPracticeId(Map<String, String> Header, String patientId, String apptId) {
		log("Execute get for get Metadata For Appointments.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("metadata/practice/patient/" + patientId + "/appointment/" + apptId).then()
				.log().all().extract().response();
		return response;
	}

	public Response metadataForApptsWithoutApptId(Map<String, String> Header, String practiceId, String patientId) {
		log("Execute get for get Metadata For Appointments.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("metadata/practice/" + practiceId + "/patient/" + patientId + "/appointment/").then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteApptMetadata(Map<String, String> Header, String practiceId, String patientId, String apptId) {
		log("Execute delete for Delete Appointment Metadata.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("metadata/practice/" + practiceId + "/patient/" + patientId + "/appointment/" + apptId)
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteApptMetadataWithoutPracticeId(Map<String, String> Header, String patientId, String apptId) {
		log("Execute delete for Delete Appointment Metadata.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("metadata/practice/patient/" + patientId + "/appointment/" + apptId).then().log().all()
				.extract().response();
		return response;
	}

	public Response deleteApptMetadataWithoutApptId(Map<String, String> Header, String practiceId, String patientId) {
		log("Execute delete for Delete Appointment Metadata.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("metadata/practice/" + practiceId + "/patient/" + patientId + "/appointment/")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteApptReminder(String payload, Map<String, String> Header, String practiceId) {
		log("Execute delete for Delete Appointments Reminders.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.delete("metadata/reminder/practice/" + practiceId).then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteApptReminderWithoutPracticeId(String payload, Map<String, String> Header) {
		log("Execute delete for Delete Appointments Reminders.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.delete("metadata/reminder/practice/").then().log().all().extract().response();
		return response;
	}

	public Response scheduleReminder(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put for Schedule Reminders For Appointment Metadata.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("metadata/reminder/practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response scheduleReminderForApptMetadata(Map<String, String> Header, String practiceId, String patientId,
			String apptId) {
		log("Execute Put for Schedule Reminder For Appointment Metadata.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.put("metadata/reminder/practice/" + practiceId + "/patient/" + patientId + "/appointment/" + apptId)
				.then().log().all().extract().response();
		return response;
	}

	public Response scheduleReminderForApptMetadataWithoutApptId(Map<String, String> Header, String practiceId,
			String patientId) {
		log("Execute Put for Schedule Reminder For Appointment Metadata.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.put("metadata/reminder/practice/" + practiceId + "/patient/" + patientId + "/appointment/")
				.then().log().all().extract().response();
		return response;
	}

	public Response scheduleReminderForApptMetadataWithoutPatientId(Map<String, String> Header, String practiceId,
			String apptId) {
		log("Execute Put for Schedule Reminder For Appointment Metadata.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.put("metadata/reminder/practice/" + practiceId + "/patient/appointment/" + apptId)
				.then().log().all().extract().response();
		return response;
	}

	public Response saveApptMetadata(String payload, Map<String, String> Header) {
		log("Execute Put for Save Appointment Metadata.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when().post("metadata")
				.then().log().all().extract().response();
		return response;
	}
}
