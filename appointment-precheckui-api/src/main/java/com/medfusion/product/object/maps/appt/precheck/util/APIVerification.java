// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public void verifyEventResponse(Response response, String eventId, String eventSource, String eventTime, String eventType, String practiceId) throws IOException {
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
		assertEquals(jsonPath.get("message"), "Unable to parse date=2021-006-1833 - date is expected to be in yyyy-MM-dd format");
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
		assertEquals(js.getString("result.subjectId"), "[24333.27766.158]", "System id was incorrect");
	}

	public void verifyReturnLogsPostWithInvalidSubjId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: platform:appointment subjectId: 24333.12345.158}");
	}

	public void verifyReturnLogsPostWithInvalidSubjUrn(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: plrfgatform:appointment subjectId: 24333.27766.158}");
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

	public void verifyDeleteLogsWithInvalidSubjId(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: platform:appointment subjectId: 24333.297761.9826}");
	}

	public void verifyDeleteLogsWithInvalidSubjUrn(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"Could not find log for: {subjectUrn: plrfgatform:appointment subjectId: 24333.27761.9826}");
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
}
