package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.AptPrecheckDataPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.FileReader;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPrecheckDada;

import io.restassured.response.Response;

public class ApptPrecheckDataTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptPrecheckDataPayload payload;
	public static PostAPIRequestAptPrecheckDada postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();
	long timestamp = System.currentTimeMillis();
	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptPrecheckDada.getPostAPIRequestAptPrecheckDada();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptPrecheckDataPayload.getAptPrecheckDataPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.precheck.data"));
		log("BASE URL-" + propertyData.getProperty("baseurl.apt.precheck.data"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptActionPost() throws IOException {
		Response response = postAPIRequest.updateApptAction(
				payload.getUpdateApptActionPayload(propertyData.getProperty("apt.precheck.data.appt.id"),
						propertyData.getProperty("apt.precheck.data.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateApptAction(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("apt.precheck.data.patient.id"),
				propertyData.getProperty("apt.precheck.data.appt.id"));
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptActionPostWithInvalidApptId() throws IOException {
		Response response = postAPIRequest
				.updateApptAction(payload.getUpdateApptActionPayload(propertyData.getProperty("invalid.appt.id"),
						propertyData.getProperty("apt.precheck.data.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateApptActionWithInvalidApptId(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptActionPostWithoutPatientId() throws IOException {
		Response response = postAPIRequest.updateApptActionWithoutPatientId(
				payload.getUpdateApptActionPayloadWithoutPatientId(
						propertyData.getProperty("apt.precheck.data.appt.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateApptActionWithoutPatientId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionPut() throws IOException {
		Response response = postAPIRequest.retrievesApptAction(
				payload.getRetrievesApptActionPayload(propertyData.getProperty("retrieves.appt.id"),
						propertyData.getProperty("retrieves.patient.id"),
						propertyData.getProperty("retrieves.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyRetrievesApptAction(response, propertyData.getProperty("retrieves.appt.action"),
				propertyData.getProperty("retrieves.practice.id"),
				propertyData.getProperty("retrieves.patient.id"), propertyData.getProperty("retrieves.appt.id"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptActionPutWithInvalidApptId() throws IOException {
		Response response = postAPIRequest
				.retrievesApptAction(payload.getRetrievesApptActionPayload(propertyData.getProperty("invalid.appt.id"),
						propertyData.getProperty("retrieves.patient.id"),
						propertyData.getProperty("retrieves.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionPutWithoutPatientId() throws IOException {
		Response response = postAPIRequest.retrievesApptActionWithInvalidPatientId(
				payload.getRetrievesApptActionPayloadWithoutPatientId(propertyData.getProperty("retrieves.appt.id"),
						propertyData.getProperty("retrieves.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesApptActionPutForConfirm() throws IOException {
		log("Delete Appointment Action");
		postAPIRequest.deleteApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("confirm.appt.action"));

		log("Saves Appointment Action");
		Response response = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("confirm.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Save an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("confirm.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("An appointment action already exists");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyApptActionIfAlreadyExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesApptActionPutForArrival() throws IOException {
		log("Delete Appointment Action");
		postAPIRequest.deleteApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("arrival.appt.action"));

		log("Saves Appointment Action");
		Response response = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("arrival.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Saves an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("arrival.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("An appointment action already exists");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyApptActionIfAlreadyExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesApptActionPutForCancel() throws IOException {
		log("Delete Appointment Action");
		postAPIRequest.deleteApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("cancel.appt.action"));

		log("Saves Appointment Action");
		Response response = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Saves an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("cancel.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("An appointment action already exists");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyApptActionIfAlreadyExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesApptActionPutWithoutApptId() throws IOException {
		Response response = postAPIRequest.savesApptActionWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		log("An appointment action already exists");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionForConfirm() throws IOException {
		log("Saves Appointment Action");
		postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("confirm.appt.action"));

		log("deletes Appointment Action");
		Response response = postAPIRequest.deleteApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("confirm.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Delete an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("confirm.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("Action does not exist for appointment");
			assertEquals(response.getStatusCode(), 404);
			apiVerification.verifyApptActionIfDoesNotExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionForArrival() throws IOException {
		log("Saves Appointment Action");
		postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("arrival.appt.action"));
		log("deletes Appointment Action");
		Response response = postAPIRequest.deleteApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("arrival.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Delete an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("arrival.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("Action does not exist for appointment");
			assertEquals(response.getStatusCode(), 404);
			apiVerification.verifyApptActionIfDoesNotExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionForCancel() throws IOException {
		log("Saves Appointment Action");
		postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("cancel.appt.action"));

		log("deletes Appointment Action");
		Response response = postAPIRequest.deleteApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Delete an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("cancel.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("Action does not exist for appointment");
			assertEquals(response.getStatusCode(), 404);
			apiVerification.verifyApptActionIfDoesNotExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionWithoutApptId() throws IOException {
		Response response = postAPIRequest.deleteApptActionWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionGetForConfirm() throws IOException {
		log("Saves Appointment Action");
		postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("confirm.appt.action"));

		log("Retrieves Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("confirm.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Retrives an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("confirm.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("An appointment action Did not find");
			assertEquals(response.getStatusCode(), 404);
			apiVerification.verifyApptActionNotFound(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionGetForArrival() throws IOException {
		log("Save an Appointment Action");
		postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("arrival.appt.action"));
		log("Retrieves an Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("arrival.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Retrives an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("arrival.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("An appointment action Did not find");
			assertEquals(response.getStatusCode(), 404);
			apiVerification.verifyApptActionNotFound(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionGetForCancel() throws IOException {
		log("Saves Appointment Action");
		postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("cancel.appt.action"));
		log("Retrieves Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("saves.appt.id"), propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Retrives an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyApptAction(response, propertyData.getProperty("cancel.appt.action"),
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("saves.patient.id"), propertyData.getProperty("saves.appt.id"));
		}
		else {
			log("An appointment action Did not find");
			assertEquals(response.getStatusCode(), 404);
			apiVerification.verifyApptActionNotFound(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionGetWithoutApptId() throws IOException {
		Response response = postAPIRequest.retrivesApptActionWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), propertyData.getProperty("saves.patient.id"),
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptActions() throws IOException {
		Response response = postAPIRequest.deleteApptActions(
				payload.getDeleteApptActionPayload(propertyData.getProperty("delete.appt.id"),
						propertyData.getProperty("delete.patient.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptActionsWithoutPracticeId() throws IOException {
		Response response = postAPIRequest
				.deleteApptActionsWithoutPracticeId(
						payload.getDeleteApptActionPayload(propertyData.getProperty("delete.appt.id"),
								propertyData.getProperty("delete.patient.id")),
						headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPracticeId(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAllApptActions() throws IOException {
		log("Create Appointment Actions");
		testSavesApptActionPutForArrival();
		testSavesApptActionPutForCancel();
		testSavesApptActionPutForConfirm();
		Response response = postAPIRequest.deleteAllApptActions(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.all.patient.id"), propertyData.getProperty("delete.all.appt.id"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyDeleteAllApptAction(response,
					propertyData.getProperty("apt.precheck.data.practice.id"),
					propertyData.getProperty("delete.all.patient.id"), propertyData.getProperty("delete.all.appt.id"));

		}
		else {
			log("Actions do not exist for appointment");
			assertEquals(response.getStatusCode(), 404);
			apiVerification.verifyDeleteAllApptActionIfNotExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAllApptActionsWithoutApptId() throws IOException {
		Response response = postAPIRequest.deleteAllApptActionsWithoutApptId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.all.patient.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutApptId(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAllApptActionsWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.deleteAllApptActionsWithoutApptId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.all.patient.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPracticeId(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostForCheckIn() throws IOException {
		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayload(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("notification.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id"),
						propertyData.getProperty("notification.check.in")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetNotifications(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("notification.id"), propertyData.getProperty("notification.check.in"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostForBroadcast() throws IOException {
		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayload(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("notification.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id"),
						propertyData.getProperty("notification.broadcast")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetNotifications(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("notification.id"), propertyData.getProperty("notification.broadcast"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostWithInvalidNotifType() throws IOException {
		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayload(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("notification.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id"),
						propertyData.getProperty("invalid.notification.type")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotificationsWithInvalidNotifType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostWithoutNotifType() throws IOException {
		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayloadWithoutType(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("notification.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotificationsWithoutNotifType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostWithoutPatientId() throws IOException {
		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayloadWithoutPatientId(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("apt.precheck.data.practice.id"),
						propertyData.getProperty("notification.broadcast")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllNotificationsPostForCheckIn() throws IOException {
		Response response = postAPIRequest.saveAllNotifications(
				payload.saveAllNotificationsPayload(propertyData.getProperty("save.all.notifications.id"),
						propertyData.getProperty("integration.id"), propertyData.getProperty("notification.check.in"),
						propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("notification.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllNotificationsPostForBroadcast() throws IOException {
		Response response = postAPIRequest.saveAllNotifications(
				payload.saveAllNotificationsPayload(propertyData.getProperty("save.all.notifications.id"),
						propertyData.getProperty("integration.id"), propertyData.getProperty("notification.broadcast"),
						propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("notification.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesNotificationsPostForCheckIn() throws IOException {
		Response response = postAPIRequest.savesNotifications(
				payload.savesNotificationsPayload(propertyData.getProperty("notification.check.in")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySavesNotifications(response, propertyData.getProperty("notification.check.in"),
				propertyData.getProperty("content.in.en"), propertyData.getProperty("content.in.es"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesNotificationsPostForBroadcast() throws IOException {
		Response response = postAPIRequest.savesNotifications(
				payload.savesNotificationsPayload(propertyData.getProperty("notification.broadcast")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySavesNotifications(response, propertyData.getProperty("notification.broadcast"),
				propertyData.getProperty("content.in.en"), propertyData.getProperty("content.in.es"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesNotificationsPostWithInvalidNotifType() throws IOException {
		Response response = postAPIRequest.savesNotifications(
				payload.savesNotificationsPayload(propertyData.getProperty("invalid.notification.type")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotificationsWithInvalidNotifType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesNotificationsPostWithoutNotifType() throws IOException {
		Response response = postAPIRequest.savesNotifications(payload.savesNotificationsPayloadWithoutType(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySavesNotificationsWithoutNotifType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteNotificationsForCurbsideAndBroadcast() throws IOException {
		Response response = postAPIRequest.deleteNotifications(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.notif.patient.id"), propertyData.getProperty("delete.notif.appt.id"),
				propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteNotificationsForCurbsideAndBroadcastWithoutApptId() throws IOException {
		Response response = postAPIRequest.deleteNotificationsWithoutApptId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.notif.patient.id"), propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteNotificationsForCurbsideAndBroadcastWithoutPatientId() throws IOException {
		Response response = postAPIRequest.deleteNotificationsWithoutPatientId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.notif.appt.id"), propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPatientId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsListOfApptsPost() throws IOException {
		Response response = postAPIRequest.returnsListOfAppointments(
				payload.returnsListOfAppointmentsPayload(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("notification.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyListOfAppointments(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsListOfAppsPostWithInvalidPatientId() throws IOException {
		Response response = postAPIRequest.returnsListOfAppointments(
				payload.returnsListOfAppointmentsPayload(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("invalid.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsListOfAppsPostWithoutPatientId() throws IOException {
		Response response = postAPIRequest.returnsListOfAppointments(
				payload.returnsListOfApptsPayloadWithoutPatientId(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsListOfApptsGet() throws IOException {
		Response response = postAPIRequest.returnsListOfAppts(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyListOfAppointments(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("get.appt.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsListOfApptsGetWithoutPatientId() throws IOException {
		Response response = postAPIRequest.returnsListOfApptsWithoutPatientId(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPatientId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsListOfApptsGETWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.returnsListOfApptsWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notification.patient.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsASpecificAppointmentGet() throws IOException {
		Response response = postAPIRequest.returnsASpecificAppointment(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySpecificAppointment(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsASpecificApptGetWithoutApptId() throws IOException {
		Response response = postAPIRequest.returnsASpecificApptWithoutPatientId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsASpecificApptGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.returnsASpecificApptWithoutPatientId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateBalanceInfoPut() throws IOException {
		Response response = postAPIRequest.updateBalanceInfo(
				payload.updateBalanceInfoPayload(propertyData.getProperty("update.balance.amount"), timestamp,
						propertyData.getProperty("update.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalanceInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("update.balance.amount"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateBalanceInfoPutWithInvalidStatus() throws IOException {
		Response response = postAPIRequest.updateBalanceInfo(
				payload.updateBalanceInfoPayload(propertyData.getProperty("update.balance.amount"), timestamp,
						propertyData.getProperty("invalid.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalanceInfoWithInvalidStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateBalanceInfoPutWithoutStatus() throws IOException {
		Response response = postAPIRequest.updateBalanceInfo(
				payload.updateBalanceInfoPayloadWithoutStatus(propertyData.getProperty("update.balance.amount"),
						timestamp),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalanceInfoWithoutStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateCopayInfoPut() throws IOException {
		Response response = postAPIRequest.updateCopayInfo(
				payload.updateCopayInfoPayload(propertyData.getProperty("update.copay.amount"), timestamp,
						propertyData.getProperty("update.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateCopayInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("update.copay.amount"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateCopayInfoPutWithInvalidStatus() throws IOException {
		Response response = postAPIRequest.updateCopayInfo(
				payload.updateCopayInfoPayload(propertyData.getProperty("update.copay.amount"), timestamp,
						propertyData.getProperty("invalid.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCopayInfoWithInvalidStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateCopayInfoPutWithoutStatus() throws IOException {
		Response response = postAPIRequest.updateCopayInfo(
				payload.updateCopayInfoPayloadWithoutStatus(propertyData.getProperty("update.copay.amount"), timestamp),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCopayInfoWithoutStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateDemographicsPut() throws IOException {
		Response response = postAPIRequest.updateDemographics(
				payload.updateDemographicsPayload(propertyData.getProperty("patient.birth.date"),
						propertyData.getProperty("patient.city"), propertyData.getProperty("patient.first.name"),
						propertyData.getProperty("patient.last.name")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDemographicInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("patient.first.name"), propertyData.getProperty("patient.last.name"),
				propertyData.getProperty("patient.city"), propertyData.getProperty("patient.birth.date"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateDemographicsPutWithInvalidBirthDate() throws IOException {
		Response response = postAPIRequest.updateDemographics(
				payload.updateDemographicsPayload(propertyData.getProperty("invalid.birth.date"),
						propertyData.getProperty("patient.city"), propertyData.getProperty("patient.first.name"),
						propertyData.getProperty("patient.last.name")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateDemographicsPutWithoutStatus() throws IOException {
		Response response = postAPIRequest.updateDemographics(
				payload.updateDemographicsPayloadWithoutStatus(propertyData.getProperty("patient.birth.date"),
						propertyData.getProperty("patient.city"), propertyData.getProperty("patient.first.name"),
						propertyData.getProperty("patient.last.name")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDemographicWithoutStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateFormsInfoPut() throws IOException {
		Response response = postAPIRequest.updateFormInfo(
				payload.updateFormInfoPayload(propertyData.getProperty("form.title"),
						propertyData.getProperty("form.url")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyFormInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("form.title"), propertyData.getProperty("form.url"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateFormsPutWithoutApptId() throws IOException {
		Response response = postAPIRequest.updateFormWithoutApptId(
				payload.updateFormInfoPayload(propertyData.getProperty("form.title"),
						propertyData.getProperty("form.url")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyFormWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateFormsInfoPutWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.updateFormWithoutPracticeId(
				payload.updateFormInfoPayload(propertyData.getProperty("form.title"),
						propertyData.getProperty("form.url")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notification.patient.id"),
				propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateInsurancePut() throws IOException {
		Response response = postAPIRequest.insuranceFormInfo(
				payload.updateInsurancePayload(propertyData.getProperty("insurance.group.number"),
						propertyData.getProperty("insurance.name"), propertyData.getProperty("insurance.member.id"),
						propertyData.getProperty("insurance.member.name"),
						propertyData.getProperty("insurance.edit.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyInsuranceInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("insurance.name"), propertyData.getProperty("insurance.member.id"),
				propertyData.getProperty("insurance.group.number"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateInsurancePutWithInvalidEditStatus() throws IOException {
		Response response = postAPIRequest.insuranceFormInfo(
				payload.updateInsurancePayload(propertyData.getProperty("insurance.group.number"),
						propertyData.getProperty("insurance.name"), propertyData.getProperty("insurance.member.id"),
						propertyData.getProperty("insurance.member.name"),
						propertyData.getProperty("invalid.insurance.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyInsuranceWithInvalidEditStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateInsurancePutWithoutStatus() throws IOException {
		Response response = postAPIRequest.insuranceFormInfo(
				payload.updateInsurancePayloadWithoutStatus(propertyData.getProperty("insurance.group.number"),
						propertyData.getProperty("insurance.name"), propertyData.getProperty("insurance.member.id"),
						propertyData.getProperty("insurance.member.name"),
						propertyData.getProperty("insurance.edit.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyInsuranceWithoutStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateAppointmentIdPut() throws IOException {
		Response response = postAPIRequest.updateAppointmentId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("existing.appointment.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptIdPutWithoutPatientId() throws IOException {
		Response response = postAPIRequest.updateApptIdWithoutPatientId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.appt.id"), propertyData.getProperty("existing.appointment.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPatientId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptIdPutWithoutApptId() throws IOException {
		Response response = postAPIRequest.updateApptIdWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"),
				propertyData.getProperty("existing.appointment.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptIdWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteInsurance() throws IOException {
		Response response = postAPIRequest.deleteInsurance(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.insurance.patient.id"),
				propertyData.getProperty("delete.insurance.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteInsuranceWithoutPatientId() throws IOException {
		Response response = postAPIRequest.deleteInsuranceWithoutPatientId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.insurance.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutPatientId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteInsuranceWithoutApptId() throws IOException {
		Response response = postAPIRequest.deleteInsuranceWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.insurance.patient.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDeleteInsuranceWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsAnApptGet() throws IOException {
		Response response = postAPIRequest.returnsAnAppt(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("internal.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReturnAppt(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.patient.id"), propertyData.getProperty("notification.appt.id"),
				propertyData.getProperty("patient.first.name"), propertyData.getProperty("patient.last.name"),
				propertyData.getProperty("patient.birth.date"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsAnApptGetWithoutApptId() throws IOException {
		Response response = postAPIRequest.returnsAnApptWithoutApptId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsAnInsuranceImageGet() throws IOException {
		Response response = postAPIRequest.returnsAnInsuranceImage(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("insurance.image.file.name"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "cardBack");
		apiVerification.responseKeyValidationJson(response, "cardFront");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsAnInsuranceImageGetWithoutFileName() throws IOException {
		Response response = postAPIRequest
				.returnsAnInsuranceImageWithoutFileName(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReturnInsuranceImageWithoutFileName(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesAnInsuranceImagePut() throws IOException {
		FileReader fileReader = new FileReader();
		String insuranceImagePayload = fileReader
				.readJsonFile(propertyData.getProperty("insurance.image.payload.file.path"));
		Response response = postAPIRequest.savesAnInsuranceImage(insuranceImagePayload,
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("saves.insurance.image.filename"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseKeyValidationJson(response, "cardBack");
		apiVerification.responseKeyValidationJson(response, "cardFront");
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesAnInsuranceImagePutWithoutFileName() throws IOException {
		FileReader fileReader = new FileReader();
		String insuranceImagePayload = fileReader
				.readJsonFile(propertyData.getProperty("insurance.image.payload.file.path"));
		Response response = postAPIRequest.savesAnInsuranceImageWithoutFileName(insuranceImagePayload,
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveInsuranceImageWithoutFileName(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}
