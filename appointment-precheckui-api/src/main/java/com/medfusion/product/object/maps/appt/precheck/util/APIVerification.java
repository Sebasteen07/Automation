// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
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

	public void verifyMfRemServiceResponse(Response response, String cadence, String practiceId, String patientId,
			String apptId) {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Email content");
		assertEquals(arr.getJSONObject(0).getString("type"), "EMAIL");
		assertEquals(arr.getJSONObject(0).getString("notificationPurpose"), cadence);
		assertEquals(arr.getJSONObject(0).getString("notificationType"), "Appointment Check-In");
		assertEquals(arr.getJSONObject(0).getString("subjectId"), practiceId + "." + patientId + "." + apptId);
		log("Validate Text content");
		assertEquals(arr.getJSONObject(1).getString("type"), "TEXT");
		assertEquals(arr.getJSONObject(1).getString("notificationPurpose"), cadence);
		assertEquals(arr.getJSONObject(1).getString("notificationType"), "Appointment Check-In");
		assertEquals(arr.getJSONObject(1).getString("subjectId"), practiceId + "." + patientId + "." + apptId);
	}

	public void verifyResponseForTextMessageContent(Response response, String cadence, String practiceId,
			String patientId, String apptId) {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Text content");
		assertEquals(arr.getJSONObject(0).getString("type"), "TEXT");
		assertEquals(arr.getJSONObject(0).getString("notificationPurpose"), cadence);
		assertEquals(arr.getJSONObject(0).getString("notificationType"), "Appointment Check-In");
		assertEquals(arr.getJSONObject(0).getString("subjectId"), practiceId + "." + patientId + "." + apptId);
	}

	public void verifyResponseForEmailContent(Response response, String cadence, String practiceId, String patientId,
			String apptId) {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Email content");
		assertEquals(arr.getJSONObject(0).getString("type"), "EMAIL");
		assertEquals(arr.getJSONObject(0).getString("notificationPurpose"), cadence);
		assertEquals(arr.getJSONObject(0).getString("notificationType"), "Appointment Check-In");
		assertEquals(arr.getJSONObject(0).getString("subjectId"), practiceId + "." + patientId + "." + apptId);
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

	public void responseTimeValidationDailyAggregation(Response response) {
		long time = response.time();
		log("Response time " + time);
		ValidatableResponse valRes = response.then();
		valRes.time(Matchers.lessThan(30000L));
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

	public void verifyUpdateApptAction(Response response, String practiceId, String patientId, String apptId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Practioner Action");
		assertEquals(js.getString("action"), "ARRIVAL", "Action was incorrect");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), apptId, "Appointment id was incorrect");
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

	public void verifyRetrievesApptAction(Response response, String action, String practiceId, String pmPatientId,
			String pmAppointmentId) throws IOException {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Response");
		assertEquals(arr.getJSONObject(0).getString("action"), action, "Action was incorrect");
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
			String balanceAmount) throws IOException {
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
			String copayAmount) throws IOException {
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

	public void verifyProcessReminderData(Response response, String practiceId, String patientId, String ApptId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), ApptId, "Appointment Id was incorrect");
		assertEquals(js.getString("status"), "PUBLISH_TEMPLATE_METADATA_FINISHED", "Status was incorrect");
		assertEquals(js.getString("message"), "Finished publishing template metadata to queue",
				"Message was incorrect");

	}

	public void verifyProcessReminderDataWithInvalidData(Response response, String practiceId, String patientId,
			String ApptId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), ApptId, "Appointment Id was incorrect");
		assertEquals(js.getString("status"), "PUBLISH_TEMPLATE_METADATA_FINISHED", "Status was incorrect");
		assertEquals(js.getString("message"), "Finished publishing template metadata to queue",
				"Message was incorrect");
	}

	public void verifyProcessReminderDataCurbsSide(Response response, String practiceId, String patientId,
			String ApptId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), ApptId, "Appointment Id was incorrect");
		assertEquals(js.getString("status"), "PUBLISH_TEMPLATE_METADATA_FINISHED", "Status was incorrect");
		assertEquals(js.getString("message"), "Finished publishing template metadata to queue",
				"Message was incorrect");
	}

	public void verifySendsPatientProvidedDataWithoutCadence(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Cadence cannot be null or blank");
	}

	public void verifySendsPatientProvidedDataWithInvalidPracticeId(Response response, String practiceId,
			String patientId, String ApptId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), ApptId, "Appointment Id was incorrect");
		assertEquals(js.getString("status"), "PRACTICE_SETTINGS_DO_NOT_EXIST", "Status was incorrect");
		assertEquals(js.getString("message"), "Settings do not exist", "Message was incorrect");
	}

	public void verifySendsPatientProvidedDataWithInvalidPatientId(Response response, String practiceId,
			String patientId, String ApptId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), ApptId, "Appointment Id was incorrect");
		assertEquals(js.getString("status"), "FHIR_APPOINTMENT_NOT_FOUND", "Status was incorrect");
		assertEquals(js.getString("message"), "Did not find a FHIR appointment", "Message was incorrect");
	}

	public void verifySendsPatientProvidedDataWithInvalidApptId(Response response, String practiceId, String patientId,
			String ApptId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), ApptId, "Appointment Id was incorrect");
		assertEquals(js.getString("status"), "FHIR_APPOINTMENT_NOT_FOUND", "Status was incorrect");
		assertEquals(js.getString("message"), "Did not find a FHIR appointment", "Message was incorrect");
	}

	public void verifyEventResponse(Response response, String eventId, String eventSource, String eventTime,
			String eventType, String practiceId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Apppointments events");
		assertEquals(js.getString("eventId"), eventId, "EventId was incorrect");
		assertEquals(js.getString("eventSource"), eventSource, "EventSource was incorrect");
		assertEquals(js.getString("eventTime"), eventTime, "eventTimes incorrect");
		assertEquals(js.getString("eventType"), eventType, "eventType was incorrect");
		assertEquals(js.getString("practiceId"), practiceId, "practiceId was incorrect");
	}

	public void verifyEventIncorrectTime(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Event time must be in ISO8601 date format");
	}

	public void verifyMissingEventsource(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "eventSource cannot be blank or null");
	}

	public void verifyDailyAggregationIncorrectTime(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Event time must be in ISO8601 date format");
	}

	public void verifyDailyAggregationTimeRange(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Start date and time cannot be after End date and time");
	}

	public void verifyLongtermAggregationDateFormat(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Unable to parse date=2021-006-1833 - date is expected to be in yyyy-MM-dd format");
	}

	public void verifySendNotificationWithInvalidNotifType(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Processed 1 notifications; 1 of those failed: [Invalid notification type]");
	}

	public void verifySendNotificationhWithoutNotifType(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Processed 1 notifications; 1 of those failed: [The notification properties type cannot be null or empty.]");
	}

	public void verifyCreateProviderForPractice(Response response, String practiceId) throws IOException {
		log("Validate Response");
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
	}

	public void verifyCreateProviderForPracticeIfAlreadyExist(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Provider already exists.");
	}

	public void verifyCreateProviderForPracticeInvalidPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "PracticeId in request does not match practiceId on path.");
	}

	public void verifyProviderDetails(Response response, String practiceId, String providerId, String firstName,
			String lastName, String fileName, String contentType) throws IOException {
		log("Validate Response");
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.getString("practiceId"), practiceId, "Practice Id  was incorrect");
		assertEquals(js.getString("providerId"), providerId, "Provider Id Id was incorrect");
		assertEquals(js.getString("firstName"), firstName, "First Name was incorrect");
		assertEquals(js.getString("lastName"), lastName, "last Name was incorrect");
		assertEquals(js.getString("providerImage.fileName"), fileName, "FileName was incorrect");
		assertEquals(js.getString("providerImage.contentType"), contentType, "Content Type Id was incorrect");
	}

	public void verifyProviderDetailsWithoutPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Request method 'GET' not supported");
	}

	public void verifyUpdateAnExistingProvider(Response response, String practiceId, String providerId,
			String firstName, String lastName, String fileName, String contentType) throws IOException {
		log("Validate Response");
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.getString("practiceId"), practiceId, "Practice Id  was incorrect");
		assertEquals(js.getString("providerId"), providerId, "Provider Id Id was incorrect");
		assertEquals(js.getString("firstName"), firstName, "First Name was incorrect");
		assertEquals(js.getString("lastName"), lastName, "last Name was incorrect");
		assertEquals(js.getString("providerImage.fileName"), fileName, "FileName was incorrect");
		assertEquals(js.getString("providerImage.contentType"), contentType, "Content Type Id was incorrect");
	}

	public void verifyUpdateAnExistingProviderWithInvalidPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "PracticeId in request does not match practiceId on path.");
	}

	public void verifyDeleteExistingProviderWithoutProviderId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Request method 'DELETE' not supported");
	}

	public void verifyGetsTheImageDataWithoutProviderId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "No provider found for criteria.");
	}

	public void verifySendsConfirmationData(Response response, String practiceId, String apptId, String emrId)
			throws IOException {
		String stringResponse = response.asString();
		XmlPath xmlPath = new XmlPath(stringResponse);
		assertEquals(xmlPath.get("ns2:AppointmentConfirmation.PracticeId"), practiceId, "Practice Id was incorrect");
		assertEquals(xmlPath.get("ns2:AppointmentConfirmation.AppointmentId"), apptId, "Appointment Id was incorrect");
		assertEquals(xmlPath.get("ns2:AppointmentConfirmation.EmrId"), emrId, "EmrId was incorrect");
	}

	public void verifySendsConfirmationWithIncompleteData(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "must not be blank");
	}

	public void verifySendsPatientProvidedDataWithoutApptId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "pmAppointmentId cannot be null, empty, or blank");
	}

	public void verifySendsPatientProvidedDataWithoutPatientId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "pmPatientId cannot be null, empty, or blank");
	}

	public void verifySendsPatientProvidedDataWithoutPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "practiceId cannot be null, empty, or blank");
	}

	public void verifyAppointmentActions(Response response, String apptAction, String practiceId, String pmPatientId,
			String pmAppointmentId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Appointment actions");
		assertEquals(js.getString("action"), apptAction, "Action not allowed");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyAppointmentActionPast(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Action not allowed");
	}

	public void verifyAppointments(Response response, String practiceId, String IntegrationId, String pmPatientId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Apppointments");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("integrationId"), IntegrationId, "integrationId was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
	}

	public void verifyBalancePay(Response response, String practiceId, String pmPatientId, String pmAppointmentId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Balance Pay");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyBalancePaid(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "BALANCE_ALREADY_COMPLETE");
	}

	public void verifyCopayPaid(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "COPAY_ALREADY_COMPLETE");
	}

	public void verifyMessageHistory(Response response, String practiceId, String pmPatientId, String pmAppointmentId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Message history");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyDemographics(Response response, String practiceId, String pmPatientId, String pmAppointmentId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate demographis data");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyPutForms(Response response, String practiceId, String pmPatientId, String pmAppointmentId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Form created");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyPutInsurance(Response response, String practiceId, String pmPatientId, String pmAppointmentId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Insurance");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyReturnLogs(Response response, String subjectUrn, String subjectId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("result.subjectUrn"), subjectUrn, "Subject Urn  was incorrect");
		assertEquals(js.getString("result.subjectId"), subjectId, "System id was incorrect");
	}

	public void verifyReturnLogsWithoutSubjUrn(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Required request parameter 'subj_urn' for method parameter type String is not present");
	}

	public void verifyReturnLogsWithoutSubjId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Required request parameter 'subj_id' for method parameter type String is not present");
	}

	public void verifyReturnLogsPost(Response response, String subjectUrn, String subjectId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("result.subjectUrn"), "[platform:appointment]", "Subject Urn  was incorrect");
		assertEquals(js.getString("result.subjectId"), subjectId, "System id was incorrect");
	}

	public void verifyReturnLogsPostWithInvalidSubjId(Response response, String subjectId) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: platform:appointment subjectId: " + subjectId + "}");
	}

	public void verifyReturnLogsPostWithInvalidSubjUrn(Response response, String subjectId) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: plrfgatform:appointment subjectId: " + subjectId + "}");
	}

	public void verifyReturnLogsPostWithoutSubjId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: platform:appointment subjectId: null}");
	}

	public void verifyDeleteLogs(Response response, String subjectId, String practiceId, String patientId,
			String apptId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		if (js.getString("result.subjectUrn").equals("[platform:appointment]")) {
			assertEquals(js.getString("result.subjectUrn"), "[platform:appointment]", "Subject Urn  was incorrect");
			assertEquals(js.getString("result.subjectId"), subjectId, "System id was incorrect");
		} else
			assertEquals(js.get("message"), "Could not find log for: {subjectUrn: platform:appointment subjectId: "
					+ practiceId + "." + patientId + "." + apptId + "}");
	}

	public void verifyDeleteLogsWithInvalidSubjId(Response response, String subjectId) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: platform:appointment subjectId: " + subjectId + "}");
	}

	public void verifyDeleteLogsWithInvalidSubjUrn(Response response, String subjectId) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: plrfgatform:appointment subjectId: " + subjectId + "}");
	}

	public void verifyCreateStatus(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Status created");
	}

	public void verifyCreateStatusWithInvalidTime(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Invalid input value: Invalid status data value: Text '2021-06-1012:01:46' could not be parsed at index 10");
	}

	public void verifyCreateStatusWithoutNotifId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"/mf-notifications-log/v1/logs/v1/logs/d949174e-c056-456e-9c07-a37f022e488b/statuses");
	}

	public void verifyCreateNotification(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Status created");
	}

	public void verifyUpdateApptMetadata(Response response, String practiceId, String patientId, String apptId,
			String type) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), apptId, "Appointment Id was incorrect");
		assertEquals(js.getString("type"), type, "Message was incorrect");
		assertEquals(js.getString("status"), "booked", "Status was incorrect");

	}

	public void verifyUpdateApptMetadataInvalidWithPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Metadata for appointment does not exist; cannot update");
	}

	public void verifyUpdateApptMetadataWithoutPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Practice ID cannot be null, blank, or empty");
	}

	public void verifyMetadataForAppts(Response response, String practiceId, String patientId, String apptId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), apptId, "Appointment Id was incorrect");
	}

	public void verifyMetadataForApptsWithoutApptId(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Request method 'PUT' not supported");
	}

	public void verifyDeleteApptMetadata(Response response, String practiceId, String patientId, String apptId,
			String type) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), patientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), apptId, "Appointment Id was incorrect");
		assertEquals(js.getString("type"), type, "type was incorrect");
	}

	public void verifyDeleteApptMetadataIfNotExist(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Cannot delete non existent appointment metadata");
	}

	public void verifyScheduleReminders(Response response, String practiceId, String pmPatientId,
			String pmAppointmentId, String type) throws IOException {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Response");
		assertEquals(arr.getJSONObject(0).getString("practiceId"), practiceId, "id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("pmPatientId"), pmPatientId, "id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("pmAppointmentId"), pmAppointmentId, "id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("type"), type, "Type was incorrect");
	}

	public void verifyScheduleRemindersIfNotFind(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Did not find appointment metadata");
	}

	public void verifyScheduleRemindersWithInvalidData(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Did not find appointment metadata");
	}

	public void verifyReminderForApptMetadata(Response response, String practiceId, String pmPatientId,
			String pmAppointmentId, String type) throws IOException {
		log("Validate Response");
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment Id was incorrect");
		assertEquals(js.getString("type"), type, "Appointment Id was incorrect");
	}

	public void verifySaveApptMetadata(Response response, String practiceId, String pmPatientId, String pmAppointmentId,
			String type) throws IOException {
		log("Validate Response");
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.getString("practiceId"), practiceId, "practice Id  was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient Id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment Id was incorrect");
		assertEquals(js.getString("type"), type, "Appointment Id was incorrect");
	}

	public void verifySaveApptMetadataIfAlreadyExist(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Metadata for appointment already exists; cannot insert");
	}

	public void verifySaveApptMetadataWithInvalidStatus(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Metadata for appointment already exists; cannot insert");
	}

	public void verifySaveApptMetadataWithoutFilter(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Appointment Filtered cannot be null");
	}

	public void verifySaveApptMetadataWithoutTime(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Appointment Time cannot be null");
	}

	public void verifyPutFormsWithoutPatientId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "pmPatientId cannot be null, empty, or blank");
	}

	public void verifyinsuranceWithStatusBlank(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "'status' value must be one of: INCOMPLETE, SKIPPED, or COMPLETE.");
	}

	public void verifyinsuranceWithEditstatusIncorrrect(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"'editStatus' value must be one of: ADDED, EDITED, REMOVED, or PENDING, or CONFIRMED.");
	}

	public void verifyinsuranceWithIncorrectTier(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "'tier' value must be one of: PRIMARY, SECONDARY, TERTIARY or OTHER.");
	}

	public void verifyPostApptIncorrectPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "User is not authorized for this practice.");
	}

	public void verifyPatientIdentificationIncorrectdata(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Could not decode token.");
	}

	public void verifyRetrieveAllSubscriptionData(Response response, String email, String resource, String identifier,
			String mechanism) throws IOException {
		JSONArray arr = new JSONArray(response.body().asString());
		log("Validate Response");
		assertEquals(arr.getJSONObject(0).getString("email"), email, "Id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("resource"), resource, "Resource  was incorrect ");
		assertEquals(arr.getJSONObject(0).getString("identifier"), identifier, "Identifier was incorrect");
		assertEquals(arr.getJSONObject(0).getString("mechanism"), mechanism, "Mechanism  was incorrect");
	}

	public void verifyRetrieveSubsDataGetUsingEmailIdAndResourceId(Response response, String email, String resource,
			String identifier, String mechanism, String type) throws IOException {
		JsonPath js = new JsonPath(response.body().asString());
		log("Validate Response");
		assertEquals(js.getString("email"), email, "id was incorrect");
		assertEquals(js.getString("resource"), resource, "Days  was incorrect ");
		assertEquals(js.getString("identifier"), identifier, "Days Period Value was incorrect");
		assertEquals(js.getString("mechanism"), mechanism, "Hours Period Value was incorrect");
		assertEquals(js.getString("type"), type, "type  was incorrect ");
	}

	public void verifyRetrieveSubsDataIfNotUnsubscribe(Response response, String email, String practiceId,
			String apptType) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Did not find unsubscribed data for email/phone=" + email + " identifier="
				+ practiceId + " and type=" + apptType);
	}

	public void verifyRetrieveAllSubsnDataUsingTypeId(Response response, String email, String resource,
			String identifier, String mechanism, String apptType) throws IOException {
		JSONArray arr = new JSONArray(response.body().asString());
		log("Validate Response");
		assertEquals(arr.getJSONObject(0).getString("email"), email, "Id was incorrect");
		assertEquals(arr.getJSONObject(0).getString("resource"), resource, "Resource  was incorrect ");
		assertEquals(arr.getJSONObject(0).getString("identifier"), identifier, "Identifier was incorrect");
		assertEquals(arr.getJSONObject(0).getString("mechanism"), mechanism, "Mechanism  was incorrect");
		assertEquals(arr.getJSONObject(0).getString("type"), apptType, "Appt Default Type  was incorrect ");
	}

	public void verifySaveSubsDataWithInvalidEmailId(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Email must be a valid email address");
	}

	public void verifySaveSubsDataWithInvalidResource(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"),
				"Resource must be one of 'authuser', 'authuserrole', 'device', 'document', 'esystem', 'identity', 'location', 'org', 'org_staff', 'patient', 'person', 'pharmacy', 'practice', 'practitioner', 'profile', 'staff'");
	}

	public void verifySaveSubsDataWithInvalidApptType(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"),
				"Type must be one of 'Appointment Check-In', 'Appointment Default', 'Appointment Scheduled Confirmation', 'Broadcast Appointment', 'Single Use Otp Code'");
	}

	public void verifySaveSubsDataWithInvalidMechanism(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "Mechanism must be one of 'Email', 'Text', 'Push'");
	}

	public void verifySaveSubsDataWithoutSystemId(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "System cannot be null, empty, or blank");
	}

	public void verifySaveAllSubsDataWithInvalidEmailId(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"), "saveAll.unsubscribedDataList[0].email: Email must be a valid email address");
	}

	public void verifySaveAllSubsDataWithoutSystemId(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"),
				"saveAll.unsubscribedDataList[0].system: System cannot be null, empty, or blank");
	}

	public void verifySaveAllSubsDataWithoutResource(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"),
				"saveAll.unsubscribedDataList[0].resource: Resources cannot be null, empty, or blank");
	}

	public void verifySaveAllSubsDataWithoutApptType(Response response) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.get("message"),
				"saveAll.unsubscribedDataList[0].type: Type value cannot be null, empty, or blank");
	}

	public void verifySendEmail(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("status"), "Email request complete.");
	}

	public void verifyDeleteApmt(Response response, String practiceId, String pmPatientId, String pmAppointmentId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate appointment to be deleted");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyPastAppmnt(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Appointment time is in past cannot cancel");
	}

	public void verifyPutAppt(Response response, String practiceId, String pmPatientId, String pmAppointmentId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate put appointment");
		assertEquals(js.getString("practiceId"), practiceId, "Practice id was incorrect");
		assertEquals(js.getString("pmPatientId"), pmPatientId, "Patient id was incorrect");
		assertEquals(js.getString("pmAppointmentId"), pmAppointmentId, "Appointment id was incorrect");
	}

	public void verifyPutAppointmentPastTime(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Incoming Appointment time is in past cannot schedule, reschedule, cancel, or update");
	}

	public void verifyGetLogoForPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "No logo was found with id =practice");
	}

	public void verifyLogoInfo(Response response, String practiceId) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("practiceId"), practiceId, "Practice Id  was incorrect");
	}

	public void verifyLogoInfoWithoutPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "No logo was found with practiceId=info");
	}

	public void verifyUpdateLogo(Response response, String Id, String practiceId) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("id"), Id, "Id  was incorrect");
		assertEquals(jsonPath.get("practiceId"), practiceId, "Practice Id  was incorrect");
	}

	public void verifyDefaultConfirmationSetting(Response response, String deliveryMtd, String apptMethod,
			String status) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Default Confirmation Setting");
		assertEquals(js.getString("deliveryMethod"), deliveryMtd, "Delivery method id was incorrect");
		assertEquals(js.getString("version"), "Default", "Version was incorrect");
		assertEquals(js.getString("apptMethod"), apptMethod, "Appointment Method was incorrect");
		assertEquals(js.getString("status"), status, "Status was incorrect");
		assertEquals(js.getString("timing"), "Upon Scheduling", "timing was incorrect");
	}

	public void verifyWithoutDeliveryMethod(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "No message available");
	}

	public void verifyDefaultReminderSetting(Response response, String deliveryMtd, String apptMethod, String status)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Default reminder Setting");
		assertEquals(js.getString("deliveryMethod"), deliveryMtd, "Delivery method id was incorrect");
		assertEquals(js.getString("apptMethod"), apptMethod, "Appointment Method was incorrect");
		assertEquals(js.getString("status"), status, "Status  was incorrect");
	}

	public void verifyReminderSetting(Response response, String deliveryMtd, String apptMethod, String status)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Reminder Setting");
		assertEquals(js.getString("deliveryMethod"), deliveryMtd, "Delivery method id was incorrect");
		assertEquals(js.getString("apptMethod"), apptMethod, "Appointment Method was incorrect");
		assertEquals(js.getString("status"), status, "Status  was incorrect");
	}

	public void verifyWithoutReminderId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Request method 'GET' not supported");
	}

	public void verifySettingsForSpecificedPractice(Response response, String practiceId) throws IOException {
		JSONArray arr = new JSONArray(response.getBody().asString());
		log("Validate Response");
		assertEquals(arr.getJSONObject(0).getString("id"), practiceId, "id was incorrect");
	}

	public void verifySettingsForAPractice(Response response, String practiceId, String systemId, String displayName)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Setting for Practice");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "Integration id was incorrect");
		assertEquals(js.getString("locationSettings.primaryLocation.displayName"), displayName,
				"Display Name  was incorrect");
	}

	public void verifyCadenceForSpecifiedPractices(Response response, String practiceId) throws IOException {
		JSONArray arr = new JSONArray(response.body().asString());
		log("Validate Response");
		assertEquals(arr.getJSONObject(0).getString("practiceId"), practiceId, "id was incorrect");
	}

	public void verifyResponseKeys(Response response, String key) throws IOException {
		JSONArray arr = new JSONArray(response.body().asString());
		for (int i = 0; i < arr.length(); i++) {
			JSONArray obj = arr.getJSONObject(i).getJSONArray("cadence");
			for (int j = 0; j < obj.length(); j++) {
				JSONObject obj1 = obj.getJSONObject(j);
				log("Validated key-> " + key + " value is-  " + obj1.getString(key));
			}
		}
	}

	public void verifyReminderSettings(Response response, String practiceId, String systemId, String id,
			String deliveryMtd, String apptMethod) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Reminder Settings");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
		assertEquals(js.getString("notifySettings.reminderNotification[0].id"), id,
				"Reminder notification id was incorrect");
		assertEquals(js.getString("notifySettings.reminderNotification[0].deliveryMethod"), deliveryMtd,
				"delivery Method was incorrect");
		assertEquals(js.getString("notifySettings.reminderNotification[0].apptMethod"), apptMethod,
				"Appointment Method was incorrect");
		assertEquals(js.getString("notifySettings.reminderNotification[0].status"), "Published",
				"Status was incorrect");
	}

	public void verifyReminderSettingsWithInvalidDeliveryMtd(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Practice cadence notification type must be one of SMS, Email");
	}

	public void verifyReminderSettingsWithInvalidApptMtd(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Practice appointment method must be one of In Office, Virtual ");
	}

	public void verifyReminderSettingsWithInvalidStatus(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Practice appointment method must be Published ");
	}

	public void verifyUpdateSettings(Response response, String practiceId, String systemId, String locationId,
			String deliveryMtd) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Update Setting");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
		assertEquals(js.getString("locationSettings.locations.abc456"), locationId, "Location id was incorrect");
		assertEquals(js.getString("notifySettings.reminderNotification[0].deliveryMethod"), deliveryMtd,
				"Delivery method was incorrect");
	}

	public void verifyUpdateSettingsWithInvalidLocationId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Location key values and IDs must match");
	}

	public void verifyUpdateSettingsWithInvalidDeliveryMtd(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Practice cadence notification type must be one of SMS, Email");
	}

	public void verifyUpdateSettingsWithInvalidLanguage(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Practice notification language must be one of en, es, en-es");
	}

	public void verifyActiveSettings(Response response, String practiceId, String activeSetting, String systemId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Active Setting");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("active"), activeSetting, "System id was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
	}

	public void verifyUpdateSettingsWithoutActiveValue(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "No message available");
	}

	public void verifyLocationSettings(Response response, String practiceId, String systemId, String locationId,
			String displayName, String streetName, String city, String state) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Location Setting");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
		assertEquals(js.getString("locationSettings.primaryLocation.id"), locationId, "location Id was incorrect");
		assertEquals(js.getString("locationSettings.primaryLocation.displayName"), displayName,
				"Display Name was incorrect");
		assertEquals(js.getString("locationSettings.primaryLocation.streetName"), streetName,
				"Street Name was incorrect");
		assertEquals(js.getString("locationSettings.primaryLocation.city"), city, "City was incorrect");
		assertEquals(js.getString("locationSettings.primaryLocation.state"), state, "State was incorrect");
	}

	public void verifyUpdateLocationSettingsWithoutPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "The location ID must be provided");
	}

	public void verifyMerchantSettings(Response response, String practiceId, String systemId, String merchantId,
			String merchantName, String creditCards) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Merchant Setting");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
		assertEquals(js.getString("merchantSettings.id"), merchantId, "Merchant Id was incorrect");
		assertEquals(js.getString("merchantSettings.name"), merchantName, "Merchant Name was incorrect");
		assertEquals(js.getString("merchantSettings.acceptedCreditCards"), creditCards, "Street Name was incorrect");

	}

	public void verifyMerchantSettingsWithInvalidCreditCard(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Invalid credit card; valid values are: American Express, Care Credit, Discover, Mastercard, Visa");
	}

	public void verifyMerchantSettingsWithoutIdAndName(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"When defining a merchant, merchant ID must be provided, When defining a merchant, merchant name must be provided");
	}

	public void verifyNotifySettings(Response response, String practiceId, String systemId, String enabled,
			String enabledByPractice, String notifyOneDayOut, String notifyThreeDaysOut, String notifyFiveDaysOut)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Notify Setting");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
		assertEquals(js.getString("notifySettings.enabled"), enabled, "enabled was incorrect");
		assertEquals(js.getString("notifySettings.enabledByPractice"), enabledByPractice,
				"Enabled By Practice  was incorrect");
		assertEquals(js.getString("notifySettings.notifyOneDayOut"), notifyOneDayOut,
				" Notify One Day Out was incorrect");
		assertEquals(js.getString("notifySettings.notifyThreeDaysOut"), notifyThreeDaysOut,
				"Notify Three Days Out was incorrect");
		assertEquals(js.getString("notifySettings.notifyFiveDaysOut"), notifyFiveDaysOut,
				"Notify Five Days Out Id was incorrect");
	}

	public void verifyNotifySettingsWithInvalidDeliveryMtd(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Practice cadence notification type must be one of SMS, Email");
	}

	public void verifyPmIntegrationSettings(Response response, String practiceId, String systemId,
			String dataSyncEnabled) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.dataSyncEnabled"), dataSyncEnabled,
				"DataSyncEnabled was incorrect");

	}

	public void verifyNotifySettingsWithoutPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "PM integrations settings must be defined");
	}

	public void verifyNotifySettingsWithoutDataSyncEnabled(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Data sync enabled must be defined");
	}

	public void verifyPrecheckSettings(Response response, String practiceId, String precheckSettings,
			String disableDemographics, String insuranceSettings, String textEntryEnabled, String ocrEnabled,
			String disableCopay, String disableBalance, String enablePatientMode, String enableForms)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate Precheck Setting");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("precheckSettings.enabled"), precheckSettings, "Precheck Settings was incorrect");
		assertEquals(js.getString("precheckSettings.disableDemographics"), disableDemographics,
				"Disable Demographics was incorrect");
		assertEquals(js.getString("precheckSettings.insuranceSettings.enabled"), insuranceSettings,
				"Insurance Settings was incorrect");
		assertEquals(js.getString("precheckSettings.insuranceSettings.textEntryEnabled"), textEntryEnabled,
				"Text Entry Enabled was incorrect");
		assertEquals(js.getString("precheckSettings.insuranceSettings.ocrEnabled"), ocrEnabled,
				"Ocr Enabled  was incorrect");
		assertEquals(js.getString("precheckSettings.disableCopay"), disableCopay, "Disable Copay was incorrect");
		assertEquals(js.getString("precheckSettings.disableBalance"), disableBalance, "Disable Balance was incorrect");
		assertEquals(js.getString("precheckSettings.enablePatientMode"), enablePatientMode,
				"Enable Patient Mode was incorrect");
		assertEquals(js.getString("precheckSettings.enableForms"), enableForms, "Enable Forms was incorrect");

	}

	public void verifyUpdatePrecheckSettingWithoutPracticeId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		if (jsonPath.get("message").equals("PM integrations settings must be defined, Practice ID must be provided")) {
			assertEquals(jsonPath.get("message"),
					"PM integrations settings must be defined, Practice ID must be provided");
		} else {
			assertEquals(jsonPath.get("message"),
					"Practice ID must be provided, PM integrations settings must be defined");
		}
	}

	public void verifyUpdatePssSetting(Response response, String practiceId, String systemId, boolean pssSetting)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
		assertEquals(js.get("pssSettings.enabled"), pssSetting, "PSS Setting was incorrect");
	}

	public void verifyCreateSetting(Response response, String practiceId) throws IOException {
		JsonPath js = new JsonPath(response.asString());
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), "84", "System id was incorrect");
	}

	public void verifySettingIfAlreadyExist(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Settings for the practice already exists.  Cannot create.");
	}

	public void verifyGetSettingsForAPractice(Response response, String practiceId, String systemId)
			throws IOException {
		JsonPath js = new JsonPath(response.asString());
		log("Validate PM Integration Setting");
		assertEquals(js.getString("id"), practiceId, "practiceId  was incorrect");
		assertEquals(js.getString("pmIntegrationSettings.id"), systemId, "System id was incorrect");
		assertEquals(js.getString("active"), "true", "Practice inactive");
	}

	public void verifyIncorrectUuid(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		String uuid = jsonPath.getString("message");
		log("Verify incorrect Uuid" + uuid);
		Assert.assertTrue(true, "Invalid input value:" + uuid.contains("Invalid input value:"));
	}

	public void verifyAlreadyexistsAppt(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Appointment type with practiceId/integrationId/appointmentId/categoryId already exists");
	}

	public void verifyMsgHistoryIncorrectMedium(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"getMessagesHistory.medium: 'medium' must be one of: 'EMAIL', 'TEXT', 'EMAIL-TEXT'.");
	}

	public void verifyMsgHistoryIncorrectType(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"getMessagesHistory.type: 'type' must be one of: 'CHECK-IN', 'BROADCAST'.");
	}

}
