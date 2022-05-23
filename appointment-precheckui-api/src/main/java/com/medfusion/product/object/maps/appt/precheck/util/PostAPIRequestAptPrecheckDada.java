// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestAptPrecheckDada extends BaseTestNGWebDriver {
	private static PostAPIRequestAptPrecheckDada postAPIRequest = new PostAPIRequestAptPrecheckDada();

	private PostAPIRequestAptPrecheckDada() {
	}

	public static PostAPIRequestAptPrecheckDada getPostAPIRequestAptPrecheckDada() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response updateApptAction(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Post request for check in");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("appointment_actions/practice/" + practiceId + "/action/checkin").then().log().all().extract()
				.response();
		return response;
	}

	public Response updateApptActionWithoutPatientId(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Post request for check in");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("appointment_actions/practice/" + practiceId + "/action/checkin").then().log().all().extract()
				.response();
		return response;
	}

	public Response retrievesApptAction(String baseurl,String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Retrieves Appt Action");
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).body(payload).when()
				.put("appointment_actions/practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response retrievesApptActionWithInvalidPatientId(String payload, Map<String, String> Header,
			String practiceId) {
		log("Execute Put request for Retrieves Appt Action");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("appointment_actions/practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response savesApptAction(Map<String, String> Header, String practiceId, String patientId, String apptId,
			String apptAction) {
		log("Execute Put request Saves Appt Action");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.put("appointment_actions/practice/" + practiceId + "/patient/" + patientId + "/appointment/" + apptId
						+ "/action/" + apptAction)
				.then().log().all().extract().response();
		return response;
	}

	public Response savesApptActionWithoutApptId(Map<String, String> Header, String practiceId, String patientId,
			String apptAction) {
		log("Execute Put request Saves Appt Action");
		Response response = given().log().all().headers(Header).when().put("appointment_actions/practice/" + practiceId
				+ "/patient/" + patientId + "/appointment/action/" + apptAction).then().log().all().extract()
				.response();
		return response;
	}

	public Response deleteApptAction(String baseurl,Map<String, String> Header, String practiceId, String patientId, String apptId,
			String apptAction) {
		log("Execute delete request for Appt Action");
		RestAssured.baseURI = baseurl;
		Response response = given()
				.log().all().headers(Header).when().delete("appointment_actions/practice/" + practiceId + "/patient/"
						+ patientId + "/appointment/" + apptId + "/action/" + apptAction)
				.then().log().all().extract().response();
		return response;
	}

	public Response retrivesApptActionWithoutApptId(Map<String, String> Header, String practiceId, String patientId,
			String apptAction) {
		log("Execute delete request for Appt Action");
		Response response = given().log().all().headers(Header).when().delete("appointment_actions/practice/"
				+ practiceId + "/patient/" + patientId + "/appointment/action/" + apptAction).then().log().all()
				.extract().response();
		return response;
	}

	public Response deleteApptActionWithoutApptId(Map<String, String> Header, String practiceId, String patientId,
			String apptAction) {
		log("Execute delete request for Appt Action");
		Response response = given().log().all().headers(Header).when().delete("appointment_actions/practice/"
				+ practiceId + "/patient/" + patientId + "/appointment/action/" + apptAction).then().log().all()
				.extract().response();
		return response;
	}

	public Response retrievesApptAction(String baseurl,Map<String, String> Header, String practiceId, String patientId, String apptId,
			String apptAction) {
		log("Execute Get request for retrives an Appt Action");
		RestAssured.baseURI = baseurl;
		Response response = given()
				.log().all().headers(Header).when().get("appointment_actions/practice/" + practiceId + "/patient/"
						+ patientId + "/appointment/" + apptId + "/action/" + apptAction)
				.then().log().all().extract().response();
		return response;
	}

	public Response retrievesApptActionWithoutApptId(Map<String, String> Header, String practiceId, String patientId,
			String apptAction) {
		log("Execute Get request for retrives an Appt Action");
		Response response = given().log().all().headers(Header).when().get("appointment_actions/practice/" + practiceId
				+ "/patient/" + patientId + "/appointment/action/" + apptAction).then().log().all().extract()
				.response();
		return response;
	}

	public Response deleteApptActions(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Delete request for delete Appt Action");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.delete("appointment_actions/practice/" + practiceId + "/action/delete").then().log().all().extract()
				.response();
		return response;
	}

	public Response deleteApptActionsWithoutPracticeId(String payload, Map<String, String> Header) {
		log("Execute Delete request for delete Appt Action");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.delete("appointment_actions/practice/action/delete").then().log().all().extract().response();
		return response;
	}

	public Response deleteAllApptActions(Map<String, String> Header, String practiceId, String patientId,
			String apptId) {
		log("Execute Delete request for delete All Appt Action");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).when().delete("appointment_actions/practice/"
						+ practiceId + "/patient/" + patientId + "/appointment/" + apptId + "/action/all")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteAllApptActionsWithoutApptId(Map<String, String> Header, String practiceId, String patientId) {
		log("Execute Delete request for delete All Appt Action");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).when().delete("appointment_actions/practice/"
						+ practiceId + "/patient/" + patientId + "/appointment/action/all")
				.then().log().all().extract().response();
		return response;
	}

	public Response getNotifications(String payload, Map<String, String> Header) {
		log("Execute Post request for Get notifications by appointment identifiers");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("/appointments/messagehistory").then().log().all().extract().response();
		return response;
	}

	public Response saveAllNotifications(String payload, Map<String, String> Header) {
		log("Execute Post request for save all notifications");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("/appointments/notifications").then().log().all().extract().response();
		return response;
	}

	public Response savesNotifications(String payload, Map<String, String> Header, String practiceId, String patientId,
			String apptId, String integrationId) {
		log("Execute Post request for Saves notifications");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("/appointments/practice/" + practiceId + "/patient/" + patientId + "/appointment/" + apptId
						+ "/integrations/" + integrationId + "/notifications")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteNotifications(Map<String, String> Header, String practiceId, String patientId, String apptId,
			String integrationId) {
		log("Execute Delete request for Delete notifications");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("/appointments/practice/" + practiceId + "/patient/" + patientId + "/appointment/" + apptId
						+ "/integrations/" + integrationId + "/notifications")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteNotificationsWithoutApptId(Map<String, String> Header, String practiceId, String patientId,
			String integrationId) {
		log("Execute Delete request for Delete notifications");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).when().delete("/appointments/practice/" + practiceId
						+ "/patient/" + patientId + "/appointment/integrations/" + integrationId + "/notifications")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteNotificationsWithoutPatientId(Map<String, String> Header, String practiceId, String apptId,
			String integrationId) {
		log("Execute Delete request for Delete notifications");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).when().delete("/appointments/practice/" + practiceId
						+ "/patient/appointment/" + apptId + "/integrations/" + integrationId + "/notifications")
				.then().log().all().extract().response();
		return response;
	}

	public Response returnsListOfAppointments(String payload, Map<String, String> Header) {
		log("Execute Post request for Return list of Appointments");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("appointments/batchGet").then().log().all().extract().response();
		return response;
	}

	public Response returnsListOfAppts(Map<String, String> Header, String praciceId, String patientId) {
		log("Execute Get request for Return list of Appointments");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("appointments/practice/" + praciceId + "/patient/" + patientId).then().log().all().extract()
				.response();
		return response;
	}

	public Response returnsListOfApptsWithoutPatientId(Map<String, String> Header, String praciceId) {
		log("Execute Get request for Return list of Appointments");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("appointments/practice/" + praciceId + "/patient/").then().log().all().extract().response();
		return response;
	}

	public Response returnsListOfApptsWithoutPracticeId(Map<String, String> Header, String patientId) {
		log("Execute Get request for Return list of Appointments");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("appointments/practice/patient/" + patientId).then().log().all().extract().response();
		return response;
	}

	public Response returnsASpecificAppointment(Map<String, String> Header, String practiceId, String patientId,
			String apptId) {
		log("Execute Get request for Returns A Specific Appointments");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("appointments/practice/" + practiceId + "/patient/" + patientId + "/appointment/" + apptId).then()
				.log().all().extract().response();
		return response;
	}

	public Response updateBalanceInfo(Map<String, String> Header, String practiceId, String patientId) {
		log("Execute Get request for Returns A Specific Appointments");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("appointments/practice/" + practiceId + "/patient/" + patientId + "/appointment/").then().log()
				.all().extract().response();
		return response;
	}

	public Response returnsASpecificApptWithoutPatientId(Map<String, String> Header, String practiceId, String apptId) {
		log("Execute Get request for Returns A Specific Appointments");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("appointments/practice/" + practiceId + "/patient/appointment/" + apptId).then().log().all()
				.extract().response();
		return response;
	}

	public Response updateBalanceInfo(String payload, Map<String, String> Header, String practiceId, String patientId,
			String apptId) {
		log("Execute Put request for Update balance information for a specific appointment");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).body(payload).when().put("appointments/practice/"
						+ practiceId + "/patient/" + patientId + "/appointment/" + apptId + "/balance")
				.then().log().all().extract().response();
		return response;
	}

	public Response updateCopayInfo(String payload, Map<String, String> Header, String practiceId, String patientId,
			String apptId) {
		log("Execute Put request for Update copay information for a specific appointment");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).body(payload).when().put("appointments/practice/"
						+ practiceId + "/patient/" + patientId + "/appointment/" + apptId + "/copay")
				.then().log().all().extract().response();
		return response;
	}

	public Response updateDemographics(String payload, Map<String, String> Header, String practiceId, String patientId,
			String apptId) {
		log("Execute Put request for Update demographics for a specific appointment");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).body(payload).when().put("appointments/practice/"
						+ practiceId + "/patient/" + patientId + "/appointment/" + apptId + "/demographics")
				.then().log().all().extract().response();
		return response;
	}

	public Response updateFormInfo(String payload, Map<String, String> Header, String practiceId, String patientId,
			String apptId) {
		log("Execute Put request for Update forms information for a specific appointment");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).body(payload).when().put("appointments/practice/"
						+ practiceId + "/patient/" + patientId + "/appointment/" + apptId + "/forms")
				.then().log().all().extract().response();
		return response;
	}

	public Response updateFormWithoutApptId(String payload, Map<String, String> Header, String practiceId,
			String patientId) {
		log("Execute Put request for Update forms information for a specific appointment");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("appointments/practice/" + practiceId + "/patient/" + patientId + "/appointment/forms").then()
				.log().all().extract().response();
		return response;
	}

	public Response updateFormWithoutPracticeId(String payload, Map<String, String> Header, String patientId,
			String apptId) {
		log("Execute Put request for Update forms information for a specific appointment");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("appointments/practice/patient/" + patientId + "/appointment/" + apptId + "/forms").then().log()
				.all().extract().response();
		return response;
	}

	public Response insuranceFormInfo(String payload, Map<String, String> Header, String practiceId, String patientId,
			String apptId) {
		log("Execute Put request for Update insurance for a specific appointment");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).body(payload).when().put("appointments/practice/"
						+ practiceId + "/patient/" + patientId + "/appointment/" + apptId + "/insurance")
				.then().log().all().extract().response();
		return response;
	}

	public Response updateAppointmentId(Map<String, String> Header, String practiceId, String patientId, String apptId,
			String existingApptId) {
		log("Execute Put request for Update appointment ID");
		Response response = given().spec(requestSpec).log().all().queryParam("existingPmAppointmentId", existingApptId)
				.headers(Header).when().put("appointments/practice/" + practiceId + "/patient/" + patientId
						+ "/appointment/" + apptId + "/scheduling")
				.then().log().all().extract().response();
		return response;
	}

	public Response updateApptIdWithoutPatientId(Map<String, String> Header, String practiceId, String apptId,
			String existingApptId) {
		log("Execute Put request for Update appointment ID");
		Response response = given().spec(requestSpec).log().all().queryParam("existingPmAppointmentId", existingApptId)
				.headers(Header).when()
				.put("appointments/practice/" + practiceId + "/patient/appointment/" + apptId + "/scheduling").then()
				.log().all().extract().response();
		return response;
	}

	public Response updateApptIdWithoutApptId(Map<String, String> Header, String practiceId, String patientId,
			String existingApptId) {
		log("Execute Put request for Update appointment ID");
		Response response = given().spec(requestSpec).log().all().queryParam("existingPmAppointmentId", existingApptId)
				.headers(Header).when()
				.put("appointments/practice/" + practiceId + "/patient/" + patientId + "/appointment/scheduling").then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteInsurance(Map<String, String> Header, String practiceId, String patientId, String apptId) {
		log("Execute Delete request for Delete insurance for a specific appointment");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).when().delete("appointments/practice/" + practiceId
						+ "/patient/" + patientId + "/appointment/" + apptId + "/insurance")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteInsuranceWithoutPatientId(Map<String, String> Header, String practiceId, String apptId) {
		log("Execute delete request for Delete insurance for a specific appointment");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("appointments/practice/" + practiceId + "/patient/appointment/" + apptId + "/insurance").then()
				.log().all().extract().response();
		return response;
	}

	public Response deleteInsuranceWithoutApptId(Map<String, String> Header, String practiceId, String patientId) {
		log("Execute delete request for Delete insurance for a specific appointment");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("appointments/practice/" + practiceId + "/patient/" + patientId + "/appointment/insurance")
				.then().log().all().extract().response();
		return response;
	}

	public Response returnsAnAppt(Map<String, String> Header, String internalApptId) {
		log("Execute Get request for Returns an appointment with the specific internal ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("appointments/" + internalApptId).then().log().all().extract().response();
		return response;
	}

	public Response returnsAnApptWithoutApptId(Map<String, String> Header) {
		log("Execute Get request for Returns an appointment with the specific internal ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("appointments/").then()
				.log().all().extract().response();
		return response;
	}

	public Response returnsAnInsuranceImage(Map<String, String> Header, String fileName) {
		log("Execute Get request for Returns an insurance image with the specified file name");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("images/" + fileName)
				.then().log().all().extract().response();
		return response;
	}

	public Response returnsAnInsuranceImageWithoutFileName(Map<String, String> Header) {
		log("Execute Get request for Returns an insurance image with the specified file name");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("images/").then().log()
				.all().extract().response();
		return response;
	}

	public Response savesAnInsuranceImage(String payload, Map<String, String> Header, String fileName) {
		log("Execute Put request for Returns an insurance image with the specified file name");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("images/" + fileName).then().log().all().extract().response();
		return response;
	}

	public Response savesAnInsuranceImageWithoutFileName(String payload, Map<String, String> Header) {
		log("Execute Put request for Returns an insurance image with the specified file name");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when().put("images/")
				.then().log().all().extract().response();
		return response;
	}

	public Response InsuranceImage(String payload, Map<String, String> Header, String fileName) {
		log("Execute Put request for Returns an insurance image with the specified file name");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("images/" + fileName).then().log().all().extract().response();
		return response;
	}
}