package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.AptPrecheckDataPayload;
import com.medfusion.product.appt.precheck.payload.AptPrecheckPayload;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.FileReader;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPrecheck;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPrecheckDada;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;
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
	public static PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche;
	public static MfAppointmentSchedulerPayload schedulerPayload;
	AptPrecheckMfAppointmentSchedulerTests apptScheduler;
	AptPrecheckTests aptPrecheckTest;
	PostAPIRequestAptPrecheck aptPrecheckPost;
	public static AptPrecheckPayload aptPrecheckPayload;
	CommonMethods commonMtd;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptPrecheckDada.getPostAPIRequestAptPrecheckDada();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptPrecheckDataPayload.getAptPrecheckDataPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		apptScheduler = new AptPrecheckMfAppointmentSchedulerTests();
		aptPrecheckTest = new AptPrecheckTests();
		aptPrecheckPost = PostAPIRequestAptPrecheck.getPostAPIRequestAptPrecheck();
		aptPrecheckPayload = AptPrecheckPayload.getAptPrecheckPayload();
		commonMtd = new CommonMethods();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.precheck.data"));
		log("BASE URL-" + propertyData.getProperty("baseurl.apt.precheck.data"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptActionPost() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		Response response = postAPIRequest.updateApptAction(
				payload.getUpdateApptActionPayload(Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("appt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateApptAction(response, propertyData.getProperty("appt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptActionPostWithInvalidApptId() throws IOException {
		Response response = postAPIRequest.updateApptAction(
				payload.getUpdateApptActionPayload(propertyData.getProperty("invalid.appt.id"),
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
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response response = postAPIRequest.retrievesApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				payload.getRetrievesApptActionPayload(Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("retrieves.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyRetrievesApptAction(response, propertyData.getProperty("retrieves.appt.action"),
				propertyData.getProperty("retrieves.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptActionPutWithInvalidApptId() throws IOException {
		Response response = postAPIRequest.retrievesApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				payload.getRetrievesApptActionPayload(propertyData.getProperty("invalid.appt.id"),
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
		log("Saves Appointment Action");
		Response response = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("confirm.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyApptAction(response, propertyData.getProperty("confirm.appt.action"),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesApptActionPutForArrival() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		log("Saves Appointment Action");
		Response response = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("arrival.appt.action"));
		log("Verifying the response");
		apiVerification.responseTimeValidation(response);
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyApptAction(response, propertyData.getProperty("arrival.appt.action"),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesApptActionPutForCancel() throws IOException {
		log("Saves Appointment Action");
		Response response = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyApptAction(response, propertyData.getProperty("cancel.appt.action"),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesApptActionPutWithoutApptId() throws IOException {
		Response response = postAPIRequest.savesApptActionWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId,
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionForConfirm() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		log("deletes Appointment Action");
		Response response = postAPIRequest.deleteApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("confirm.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptAction(response, propertyData.getProperty("confirm.appt.action"),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAlreadyDeleteApptActionForConfirm() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		log("deletes Appointment Action");
		Response deletesResponse = postAPIRequest.deleteApptAction(
				propertyData.getProperty("baseurl.apt.precheck.data"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("confirm.appt.action"));
		assertEquals(deletesResponse.getStatusCode(), 200);

		Response alreadyDeletedResponse = postAPIRequest.deleteApptAction(
				propertyData.getProperty("baseurl.apt.precheck.data"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("confirm.appt.action"));

		assertEquals(alreadyDeletedResponse.getStatusCode(), 404);
		apiVerification.responseTimeValidation(alreadyDeletedResponse);
		apiVerification.verifyApptActionIfDoesNotExist(alreadyDeletedResponse);
		log("Action does not exist for appointment");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionForArrival() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		log("deletes Appointment Action");
		Response response = postAPIRequest.deleteApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("arrival.appt.action"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptAction(response, propertyData.getProperty("arrival.appt.action"),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAlreadyDeletedApptActionForArrival() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		log("deletes Appointment Action");
		Response deleteResponse = postAPIRequest.deleteApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("arrival.appt.action"));
		assertEquals(deleteResponse.getStatusCode(), 200);

		Response alreadyDeleteResponse = postAPIRequest.deleteApptAction(
				propertyData.getProperty("baseurl.apt.precheck.data"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("arrival.appt.action"));
		log("Verifying the response");
		assertEquals(alreadyDeleteResponse.getStatusCode(), 404);
		apiVerification.verifyApptActionIfDoesNotExist(alreadyDeleteResponse);
		log("Action does not exist for appointment");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionForCancel() throws IOException {
		log("Saves Appointment Action");
		Response saveResponse = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));
		assertEquals(saveResponse.getStatusCode(), 200);

		log("deletes Appointment Action");
		Response response = postAPIRequest.deleteApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptAction(response, propertyData.getProperty("cancel.appt.action"),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionForAlreadyCancel() throws IOException {
		log("Saves Appointment Action");
		Response saveResponse = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));
		assertEquals(saveResponse.getStatusCode(), 200);

		log("deletes Appointment Action");
		Response deleteResponse = postAPIRequest.deleteApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("cancel.appt.action"));
		assertEquals(deleteResponse.getStatusCode(), 200);

		log("Verifying the response");
		Response alreadyDeleteResponse = postAPIRequest.deleteApptAction(
				propertyData.getProperty("baseurl.apt.precheck.data"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));

		assertEquals(alreadyDeleteResponse.getStatusCode(), 404);
		apiVerification.responseTimeValidation(alreadyDeleteResponse);
		apiVerification.verifyApptActionIfDoesNotExist(alreadyDeleteResponse);
		log("Action does not exist for appointment");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesApptActionWithoutApptId() throws IOException {
		Response response = postAPIRequest.deleteApptActionWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId,
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionGetForConfirm() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		log("Retrieves Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("confirm.appt.action"));

		log("Retrives an appointments action");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptAction(response, propertyData.getProperty("confirm.appt.action"),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesDeletedApptActionGetForConfirm() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		log("deletes Appointment Action");
		Response deleteResponse = postAPIRequest.deleteApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("confirm.appt.action"));
		log("Verifying the response");
		assertEquals(deleteResponse.getStatusCode(), 200);

		log("Retrieves Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("confirm.appt.action"));

		log("Verifying the response");
		log("An appointment action Did not find");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptActionNotFound(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionGetForArrival() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		log("Retrieves an Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("arrival.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptAction(response, propertyData.getProperty("arrival.appt.action"),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesDeletedApptActionGetForArrival() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
		log("deletes Appointment Action");
		Response deletesResponse = postAPIRequest.deleteApptAction(
				propertyData.getProperty("baseurl.apt.precheck.data"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("arrival.appt.action"));
		log("Verifying the response");
		assertEquals(deletesResponse.getStatusCode(), 200);

		log("Retrieves an Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("arrival.appt.action"));

		log("Verifying the response");
		log("An appointment action Did not find");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptActionNotFound(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionGetForCancel() throws IOException {
		log("Saves Appointment Action");
		Response saveResponse = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));
		assertEquals(saveResponse.getStatusCode(), 200);

		log("Retrieves Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptAction(response, propertyData.getProperty("cancel.appt.action"),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesDeletedApptActionGetForCancel() throws IOException {
		log("Saves Appointment Action");
		Response saveResponse = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));
		assertEquals(saveResponse.getStatusCode(), 200);

		log("deletes Appointment Action");
		Response deletedResponse = postAPIRequest.deleteApptAction(
				propertyData.getProperty("baseurl.apt.precheck.data"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(deletedResponse.getStatusCode(), 200);

		log("Retrieves Appointment Action");
		Response response = postAPIRequest.retrievesApptAction(propertyData.getProperty("baseurl.apt.precheck.data"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptActionNotFound(response);
		log("An appointment action Did not find");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrievesApptActionGetWithoutApptId() throws IOException {
		Response response = postAPIRequest.retrivesApptActionWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId,
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptActionsConfirm() throws IOException {

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response response = postAPIRequest.deleteApptActions(
				payload.getDeleteApptActionPayload(Appointment.apptId, Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptActionsCurbsideCheckIn() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response response = postAPIRequest.deleteApptActions(
				payload.getDeleteApptActionPayload(Appointment.apptId, Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptActionsArrival() throws IOException {

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		Response response = postAPIRequest.deleteApptActions(
				payload.getDeleteApptActionPayload(Appointment.apptId, Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptActionsCheckIn() throws IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		Response checkInResponse = aptPrecheckPost.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				aptPrecheckPayload.getCheckinActionsPayload(Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("appt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		assertEquals(checkInResponse.getStatusCode(), 200);

		Response response = postAPIRequest.deleteApptActions(
				payload.getDeleteApptActionPayload(Appointment.apptId, Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptActionsWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.deleteApptActionsWithoutPracticeId(
				payload.getDeleteApptActionPayload(Appointment.apptId, Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAllApptActions() throws IOException {
		log("Create Appointment Actions");
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
		log("Cancal Appointment Action");
		Response CancelResponse = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(CancelResponse.getStatusCode(), 200);

		Response response = postAPIRequest.deleteAllApptActions(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDeleteAllApptAction(response, propertyData.getProperty("appt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAlreadyDeletedAllApptActions() throws IOException {
		log("Create Appointment Actions");
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.data.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("appt.precheck.data.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
		log("Cancal Appointment Action");
		Response CancelResponse = postAPIRequest.savesApptAction(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("cancel.appt.action"));
		log("Verifying the response");
		assertEquals(CancelResponse.getStatusCode(), 200);

		Response deletedResponse = postAPIRequest.deleteAllApptActions(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
		assertEquals(deletedResponse.getStatusCode(), 200);
		Response response = postAPIRequest.deleteAllApptActions(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDeleteAllApptActionIfNotExist(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAllApptActionsWithoutApptId() throws IOException {
		Response response = postAPIRequest.deleteAllApptActionsWithoutApptId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletesAllApptActionsWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.deleteAllApptActionsWithoutApptId(
				headerConfig.HeaderwithToken(getaccessToken), Appointment.apptId, Appointment.patientId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostForCheckIn() throws IOException {
		Response saveResponse = postAPIRequest.saveAllNotifications(
				payload.saveAllNotificationsPayload(propertyData.getProperty("save.all.notifications.id"),
						propertyData.getProperty("integration.id"), propertyData.getProperty("notification.check.in"),
						Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(saveResponse.getStatusCode(), 200);

		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayload(Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id"),
						propertyData.getProperty("notification.check.in")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetNotifications(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("notification.id"),
				propertyData.getProperty("notification.check.in"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostForBroadcast() throws IOException {
		Response saveResponse = postAPIRequest.saveAllNotifications(
				payload.saveAllNotificationsPayload(propertyData.getProperty("save.all.notifications.id"),
						propertyData.getProperty("integration.id"), propertyData.getProperty("notification.broadcast"),
						Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(saveResponse.getStatusCode(), 200);

		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayload(Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id"),
						propertyData.getProperty("notification.broadcast")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetNotifications(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("notification.id"),
				propertyData.getProperty("notification.broadcast"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostWithInvalidNotifType() throws IOException {
		Response saveResponse = postAPIRequest.saveAllNotifications(
				payload.saveAllNotificationsPayload(propertyData.getProperty("save.all.notifications.id"),
						propertyData.getProperty("integration.id"), propertyData.getProperty("notification.broadcast"),
						Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(saveResponse.getStatusCode(), 200);
		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayload(Appointment.apptId, Appointment.patientId,
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
		Response saveResponse = postAPIRequest.saveAllNotifications(
				payload.saveAllNotificationsPayload(propertyData.getProperty("save.all.notifications.id"),
						propertyData.getProperty("integration.id"), propertyData.getProperty("notification.broadcast"),
						Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(saveResponse.getStatusCode(), 200);
		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayloadWithoutType(Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotificationsWithoutNotifType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationsPostWithoutPatientId() throws IOException {
		Response saveResponse = postAPIRequest.saveAllNotifications(
				payload.saveAllNotificationsPayload(propertyData.getProperty("save.all.notifications.id"),
						propertyData.getProperty("integration.id"), propertyData.getProperty("notification.broadcast"),
						Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(saveResponse.getStatusCode(), 200);
		Response response = postAPIRequest.getNotifications(
				payload.getNotificationsPayloadWithoutPatientId(Appointment.apptId,
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
						Appointment.apptId, Appointment.patientId,
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
						Appointment.apptId, Appointment.patientId,
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
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("integration.id"));
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
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("integration.id"));
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
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotificationsWithInvalidNotifType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavesNotificationsPostWithoutNotifType() throws IOException {
		Response response = postAPIRequest.savesNotifications(payload.savesNotificationsPayloadWithoutType(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySavesNotificationsWithoutNotifType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteNotificationsForCurbsideAndBroadcast() throws IOException {
		Response broadcastResponse = postAPIRequest.savesNotifications(
				payload.savesNotificationsPayload(propertyData.getProperty("notification.broadcast")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(broadcastResponse.getStatusCode(), 200);

		Response checkInResponse = postAPIRequest.saveAllNotifications(
				payload.saveAllNotificationsPayload(propertyData.getProperty("save.all.notifications.id"),
						propertyData.getProperty("integration.id"), propertyData.getProperty("notification.check.in"),
						Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(checkInResponse.getStatusCode(), 200);

		Response response = postAPIRequest.deleteNotifications(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
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
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteNotificationsForCurbsideAndBroadcastWithoutPatientId() throws IOException {
		Response response = postAPIRequest.deleteNotificationsWithoutPatientId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("delete.notif.appt.id"), propertyData.getProperty("integration.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsListOfApptsPost() throws IOException {
		// First do manual precheck for patient
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
		// First do manual precheck for patient
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
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsListOfApptsGETWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.returnsListOfApptsWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
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
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsASpecificApptGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.returnsASpecificApptWithoutPatientId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				propertyData.getProperty("notification.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateBalanceInfoPut() throws IOException {
		Response response = postAPIRequest.updateBalanceInfo(
				payload.updateBalanceInfoPayload(propertyData.getProperty("update.balance.amount"), timestamp,
						propertyData.getProperty("update.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalanceInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.balance.amount"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateBalanceInfoPutWithInvalidStatus() throws IOException {
		Response response = postAPIRequest.updateBalanceInfo(
				payload.updateBalanceInfoPayload(propertyData.getProperty("update.balance.amount"), timestamp,
						propertyData.getProperty("invalid.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId);
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
				Appointment.patientId, Appointment.apptId);
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
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateCopayInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.copay.amount"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateCopayInfoPutWithInvalidStatus() throws IOException {
		Response response = postAPIRequest.updateCopayInfo(
				payload.updateCopayInfoPayload(propertyData.getProperty("update.copay.amount"), timestamp,
						propertyData.getProperty("invalid.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId);
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
				Appointment.patientId, Appointment.apptId);
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
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDemographicInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("patient.first.name"),
				propertyData.getProperty("patient.last.name"), propertyData.getProperty("patient.city"),
				propertyData.getProperty("patient.birth.date"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateDemographicsPutWithInvalidBirthDate() throws IOException {
		Response response = postAPIRequest.updateDemographics(
				payload.updateDemographicsPayload(propertyData.getProperty("invalid.birth.date"),
						propertyData.getProperty("patient.city"), propertyData.getProperty("patient.first.name"),
						propertyData.getProperty("patient.last.name")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId);
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
				Appointment.patientId, Appointment.patientId);
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
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyFormInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("form.title"),
				propertyData.getProperty("form.url"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateFormsPutWithoutApptId() throws IOException {
		Response response = postAPIRequest.updateFormWithoutApptId(
				payload.updateFormInfoPayload(propertyData.getProperty("form.title"),
						propertyData.getProperty("form.url")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateFormsInfoPutWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.updateFormWithoutPracticeId(
				payload.updateFormInfoPayload(propertyData.getProperty("form.title"),
						propertyData.getProperty("form.url")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notification.patient.id"),
				Appointment.apptId);
		log("Verifying the response");
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateInsurancePut() throws IOException {
		Response response = postAPIRequest.insuranceFormInfo(
				payload.updateInsurancePayload(propertyData.getProperty("insurance.group.number"),
						propertyData.getProperty("insurance.name"), propertyData.getProperty("insurance.member.id"),
						propertyData.getProperty("insurance.member.name"),
						propertyData.getProperty("insurance.edit.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyInsuranceInfo(response, propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("insurance.name"),
				propertyData.getProperty("insurance.member.id"), propertyData.getProperty("insurance.group.number"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateInsurancePutWithInvalidEditStatus() throws IOException {
		Response response = postAPIRequest.insuranceFormInfo(
				payload.updateInsurancePayload(propertyData.getProperty("insurance.group.number"),
						propertyData.getProperty("insurance.name"), propertyData.getProperty("insurance.member.id"),
						propertyData.getProperty("insurance.member.name"),
						propertyData.getProperty("invalid.insurance.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.data.practice.id"),
				Appointment.patientId, Appointment.apptId);
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
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyInsuranceWithoutStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateAppointmentIdPut() throws IOException {
		Response response = postAPIRequest.updateAppointmentId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("existing.appointment.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptIdPutWithoutPatientId() throws IOException {
		Response response = postAPIRequest.updateApptIdWithoutPatientId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.apptId,
				propertyData.getProperty("existing.appointment.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptIdPutWithoutApptId() throws IOException {
		Response response = postAPIRequest.updateApptIdWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteInsurance() throws IOException {
		Response response = postAPIRequest.deleteInsurance(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteInsuranceWithoutPatientId() throws IOException {
		Response response = postAPIRequest.deleteInsuranceWithoutPatientId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteInsuranceWithoutApptId() throws IOException {
		Response response = postAPIRequest.deleteInsuranceWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.data.practice.id"), Appointment.patientId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsAnApptGet() throws IOException {
		Response postApptResponse = postAPIRequest.returnsListOfAppointments(
				payload.returnsListOfAppointmentsPayload(propertyData.getProperty("notification.appt.id"),
						propertyData.getProperty("notification.patient.id"),
						propertyData.getProperty("apt.precheck.data.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(postApptResponse.getStatusCode(), 200);

		JSONObject jsonObject = new JSONObject(postApptResponse.asString());
		String id = jsonObject.getJSONArray("appointments").getJSONObject(0).getString("id");
		log("get the internal ID of the appointment: " + id);
		Response response = postAPIRequest.returnsAnAppt(headerConfig.HeaderwithToken(getaccessToken), id);
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
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		Appointment.plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + Appointment.plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.data.practice.id"),
				schedulerPayload.putAppointmentPayload(Appointment.plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
	}
	//
	//
	// testDeleteApptActionsCheckIn
}
