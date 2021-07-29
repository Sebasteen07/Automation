// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class APIVerification extends BaseTestNGWebDriver {

	public void responseCodeValidation(Response response, int statuscode) {
		assertEquals(statuscode, response.getStatusCode(), "Status Code doesnt match properly. Test Case failed");
		log("Status Code Validated as " + response.getStatusCode());
	}

	public void responseKeyValidation(Response response, String key) {
		JSONArray arr = new JSONArray(response.getBody().asString());
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			log("Validated key-> " + key + " value is-  " + obj.getString(key));
		}
	}

	public void verifyMfRemServiceResponse(Response response, String cadence) {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Email content");
		assertEquals(arr.getJSONObject(0).getString("type"), "EMAIL");
		assertEquals(arr.getJSONObject(0).getString("notificationPurpose"), cadence);
		assertEquals(arr.getJSONObject(0).getString("notificationType"), "Appointment Check-In");
		assertEquals(arr.getJSONObject(0).getString("subjectId"), "24333.27867.10083");
		log("Validate Text content");
		assertEquals(arr.getJSONObject(1).getString("type"), "TEXT");
		assertEquals(arr.getJSONObject(1).getString("notificationPurpose"), cadence);
		assertEquals(arr.getJSONObject(1).getString("notificationType"), "Appointment Check-In");
		assertEquals(arr.getJSONObject(1).getString("subjectId"), "24333.27867.10083");
	}

	public void verifyResponseForTextMessageContent(Response response, String cadence) {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Text content");
		assertEquals(arr.getJSONObject(0).getString("type"), "TEXT");
		assertEquals(arr.getJSONObject(0).getString("notificationPurpose"), cadence);
		assertEquals(arr.getJSONObject(0).getString("notificationType"), "Appointment Check-In");
		assertEquals(arr.getJSONObject(0).getString("subjectId"), "24333.27867.10083");
	}

	public void verifyResponseForEmailContent(Response response, String cadence) {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Email content");
		assertEquals(arr.getJSONObject(0).getString("type"), "EMAIL");
		assertEquals(arr.getJSONObject(0).getString("notificationPurpose"), cadence);
		assertEquals(arr.getJSONObject(0).getString("notificationType"), "Appointment Check-In");
		assertEquals(arr.getJSONObject(0).getString("subjectId"), "24333.27867.10083");
	}

	public void responseKeyValidationJson(Response response, String key) {
		JsonPath js = new JsonPath(response.asString());
		log("Validated key-> " + key + " value is-  " + js.getString(key));
	}

	public void responseTimeValidation(Response response) {
		long time = response.time();
		log("Response time " + time);
		ValidatableResponse valRes = response.then();
		valRes.time(Matchers.lessThan(5000L));
	}

	public void verifyInvalidLanguage(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not generate notification due to: , Language can be 'en', 'es', or 'en-es'");
	}

	public void verifyMfRemServiceWithoutNotifType(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not generate notification due to: , Email notification type cannot be empty");
	}

	public void verifyInvalidEmail(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not generate notification due to: , The email address format is invalid");
	}

	public void verifyUpdateApptAction(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Practioner Action");
		assertEquals(js.getString("action"), "ARRIVAL", "Action was incorrect");
		assertEquals(js.getString("practiceId"), "24333", "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), "27268", "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), "8881", "Appointment id was incorrect");
		assertEquals(js.getString("properties.practionerAction"), "CHECKIN", "Practioner Action was empty");
	}

	public void verifyWithoutApptId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "No message available");
	}

	public void verifyUpdateApptActionWithInvalidApptId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Arrival is not confirm by patient");
	}

	public void verifyRetrievesApptAction(Response response, String practiceId, String pmPatientId,
			String pmAppointmentId) throws IOException {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Response");
		assertEquals(arr.getJSONObject(0).getString("action"), "CONFIRM", "Action was incorrect");
		assertEquals(arr.getJSONObject(0).getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("pmAppointmentId"), pmAppointmentId,
				"Appointment id was incorrect");
	}

	public void verifyApptAction(Response response, String apptAction, String practiceId, String pmPatientId,
			String pmAppointmentId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Practioner Action");
		assertEquals(js.getString("action"), apptAction, "Action was incorrect");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyApptActionIfAlreadyExist(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "An appointment action already exists. Cannot save.");
	}

	public void verifyApptActionIfDoesNotExist(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Action does not exist for appointment");
	}

	public void verifyApptActionNotFound(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Did not find an appointment action for appointment");
	}

	public void verifyDeleteAllApptAction(Response response, String practiceId, String pmPatientId,
			String pmAppointmentId) throws IOException {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Response");
		assertEquals(arr.getJSONObject(0).getString("action"), "ARRIVAL", "Action was incorrect");
		assertEquals(arr.getJSONObject(0).getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("pmAppointmentId"), pmAppointmentId,
				"Appointment id was incorrect");
		assertEquals(arr.getJSONObject(1).getString("action"), "CANCEL", "Action was incorrect");
		assertEquals(arr.getJSONObject(1).getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(arr.getJSONObject(1).getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(arr.getJSONObject(1).getString("pmAppointmentId"), pmAppointmentId,
				"Appointment id was incorrect");
		assertEquals(arr.getJSONObject(2).getString("action"), "CONFIRM", "Action was incorrect");
		assertEquals(arr.getJSONObject(2).getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(arr.getJSONObject(2).getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(arr.getJSONObject(2).getString("pmAppointmentId"), pmAppointmentId,
				"Appointment id was incorrect");

	}

	public void verifyDeleteAllApptActionIfNotExist(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Actions do not exist for appointment");
	}

	public void verifyGetNotifications(Response response, String practiceId, String pmPatientId, String pmAppointmentId,
			String notificationId, String type) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Practioner Action");
		assertEquals(js.getString("notifications[0].practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("notifications[0].pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("notifications[0].pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
		assertEquals(js.getString("notifications[0].notifications[0].id"), notificationId,
				"Notifications id was incorrect");
		assertEquals(js.getString("notifications[0].notifications[0].type"), type, "Notifications type was incorrect");
		assertEquals(js.getString("notifications[0].notifications[0].medium.TEXT"), "SUCCESS", "TEXT Unsuccessful");
		assertEquals(js.getString("notifications[0].notifications[0].medium.EMAIL"), "SUCCESS", "Email Unsuccessful");
	}

	public void verifyNotificationsWithInvalidNotifType(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "'type' must be one of: 'CHECK-IN', 'BROADCAST'.");
	}

	public void verifyNotificationsWithoutNotifType(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "must not be null");
	}

	public void verifySavesNotifications(Response response, String apptAction, String contentEn, String contentEs)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Practioner Action");
		assertEquals(js.getString("type"), apptAction, "Action was incorrect");
		assertEquals(js.getString("content.en"), contentEn, "Content was incorrect");
		assertEquals(js.getString("content.es"), contentEs, "Content was incorrect");
		assertEquals(js.getString("medium.TEXT"), "SUCCESS", "TEXT Unsuccessful");
		assertEquals(js.getString("medium.EMAIL"), "SUCCESS", "Email Unsuccessful");
	}

	public void verifySavesNotificationsWithoutNotifType(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "must not be empty");
	}

	public void verifyListOfAppointments(Response response, String practiceId, String pmPatientId,
			String pmAppointmentId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment Ids");
		assertEquals(js.getString("appointments[0].practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("appointments[0].pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("appointments[0].pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifySpecificAppointment(Response response, String practiceId, String pmPatientId,
			String pmAppointmentId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment Ids");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyBalanceInfo(Response response, String practiceId, String pmPatientId, String pmAppointmentId,
			String balanceAmount)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment Ids");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
		assertEquals(js.getString("balance.status"), "COMPLETE", "Status incompleted");
		assertEquals(js.getString("balance.payments[0].amount"), balanceAmount, "Status incompleted");

	}

	public void verifyWithoutPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "No message available");
	}

	public void verifyBalanceInfoWithInvalidStatus(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "'status' value must be one of: INCOMPLETE, SKIPPED, or COMPLETE.");
	}

	public void verifyBalanceInfoWithoutStatus(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "'status' must not be null.");
	}

	public void verifyUpdateCopayInfo(Response response, String practiceId, String pmPatientId, String pmAppointmentId,
			String copayAmount)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment Ids");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
		assertEquals(js.getString("copay.status"), "COMPLETE", "Status incompleted");
		assertEquals(js.getString("copay.payments[0].amount"), copayAmount, "Status incompleted");
	}

	public void verifyCopayInfoWithInvalidStatus(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "'status' value must be one of: INCOMPLETE, SKIPPED, or COMPLETE.");
	}

	public void verifyCopayInfoWithoutStatus(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "'status' must not be null.");
	}

	public void verifyDemographicInfo(Response response, String practiceId, String pmPatientId, String pmAppointmentId,
			String firstName, String lastName, String city, String birthDate) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment Ids");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
		assertEquals(js.getString("patientDemographics.firstName"), firstName, "FirstName was incorrect");
		assertEquals(js.getString("patientDemographics.lastName"), lastName, "LastName was incorrect");
		assertEquals(js.getString("patientDemographics.city"), city, "City was incorrect");
		assertEquals(js.getString("patientDemographics.birthDate"), birthDate, "BirthDate was incorrect");
		assertEquals(js.getString("patientDemographics.status"), "COMPLETE", "Status incompleted");
	}

	public void verifyDemographicWithoutStatus(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "'status' must not be null.");
	}

	public void verifyWithoutPatientId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "No message available");
	}

	public void verifyUpdateApptActionWithoutPatientId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "must not be null");
	}

	public void verifyFormInfo(Response response, String practiceId, String pmPatientId, String pmAppointmentId,
			String title, String url) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment Ids");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
		assertEquals(js.getString("patientForms.forms[0].title"), title, "FirstName was incorrect");
		assertEquals(js.getString("patientForms.forms[0].url"), url, "LastName was incorrect");
		assertEquals(js.getString("patientForms.forms[0].status"), "COMPLETE", "City was incorrect");
	}

	public void verifyFormWithoutApptId(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Request method 'PUT' not supported");
	}

	public void verifyInsuranceInfo(Response response, String practiceId, String pmPatientId, String pmAppointmentId,
			String insuranceName, String memberId, String groupNumber) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment Ids");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
		assertEquals(js.getString("insurance.insuranceList[0].tier"), "PRIMARY", "Tire was incorrect");
		assertEquals(js.getString("insurance.insuranceList[0].details.insuranceName"), insuranceName,
				"Insurance Name was incorrect");
		assertEquals(js.getString("insurance.insuranceList[0].details.memberId"), memberId, "Member Id was incorrect");
		assertEquals(js.getString("insurance.insuranceList[0].details.groupNumber"), groupNumber,
				"Group Number was incorrect");
		assertEquals(js.getString("insurance.insuranceList[0].editStatus"), "CONFIRMED", "Status not Confirmed");
	}

	public void verifyReturnInsuranceImageWithoutFileName(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "No message available");
	}

	public void verifyInsuranceWithInvalidEditStatus(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"),
				"'tier' value must be one of: ADDED, EDITED, REMOVED, or CONFIRMED, or PENDING");
	}

	public void verifyInsuranceWithoutStatus(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "'status' must not be null.");
	}

	public void verifyApptIdWithoutApptId(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Request method 'PUT' not supported");
	}

	public void verifyDeleteInsuranceWithoutApptId(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Request method 'DELETE' not supported");
	}

	public void verifyReturnAppt(Response response, String practiceId, String pmPatientId, String pmAppointmentId,
			String firstName, String lastName, String birthDate) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment Ids");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
		assertEquals(js.getString("patientDemographics.firstName"), firstName, "FirstName  was incorrect");
		assertEquals(js.getString("patientDemographics.lastName"), lastName, "LastName  was incorrect");
		assertEquals(js.getString("patientDemographics.birthDate"), birthDate, "birthDate was incorrect");
		assertEquals(js.getString("patientDemographics.status"), "COMPLETE", "Tire was incorrect");
	}

	public void verifySaveInsuranceImageWithoutFileName(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "No message available");
	}

}
