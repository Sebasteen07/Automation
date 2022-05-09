// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.payload.MfisAppointmentServicePayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfisAppointmentService;

import io.restassured.response.Response;

public class ApptPrecheckMfisAppointmentServiceTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfisAppointmentServicePayload payload;
	public static PostAPIRequestMfisAppointmentService postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	public static PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche;
	public static MfAppointmentSchedulerPayload schedulerPayload;
	APIVerification apiVerification = new APIVerification();
	CommonMethods commonMtd;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfisAppointmentService.getPostAPIRequestMfisAppointmentService();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfisAppointmentServicePayload.getMfisAppointmentServicePayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mfis.appt.service"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mfis.appt.service"));
		commonMtd = new CommonMethods();
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceGet() throws IOException {
		Response response = postAPIRequest.getApptService(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"),
				propertyData.getProperty("mfis.appt.service.integration.id"), Appointment.patientId,
				Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptserviceGet(response, propertyData.getProperty("mfis.appt.service.practice.id"),
				Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("mfis.appt.service.integration.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceGetWithoutIntegrationId() throws IOException {
		Response response = postAPIRequest.getApptServiceWithoutIntgrationId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mfis.appt.service.practice.id"),
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceGetWithoutApptId() throws IOException {
		Response response = postAPIRequest.getApptServiceWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"),
				propertyData.getProperty("mfis.appt.service.integration.id"), Appointment.patientId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServicePost() throws IOException {
		Response response = postAPIRequest.apptServicePost(payload.apptServicePayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "pageNumber");
		apiVerification.responseKeyValidationJson(response, "totalPages");
		apiVerification.responseKeyValidationJson(response, "totalAppointments");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServicePostWithoutDate() throws IOException {
		Response response = postAPIRequest.apptServicePost(payload.apptServiceWithoutDatePayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceForCountPost() throws IOException {
		Response response = postAPIRequest.apptServiceForCountPost(payload.apptServicePayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mfis.appt.service.practice.id"),
				propertyData.getProperty("mfis.appt.service.count"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "practiceId");
		apiVerification.responseKeyValidation(response, "pmPatientId");
		apiVerification.responseKeyValidation(response, "pmAppointmentId");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceForCountWithoutDatePost() throws IOException {
		Response response = postAPIRequest.apptServiceForCountPost(payload.apptServiceWithoutDatePayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mfis.appt.service.practice.id"),
				propertyData.getProperty("mfis.appt.service.count"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceWithoutCountPost() throws IOException {
		Response response = postAPIRequest.apptServiceWithoutCount(payload.apptServicePayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceForBroadcastAppointments() throws IOException {
		Response response = postAPIRequest.apptServiceForBroadcastAppts(
				payload.apptServiceForBroadcastApptsPayload(Appointment.patientId, Appointment.apptId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBroadcastAppointment(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceForCurbsidePage() throws IOException {
		Response response = postAPIRequest.apptServiceForCurbsidePage(payload.apptServicePayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "pageNumber");
		apiVerification.responseKeyValidationJson(response, "totalPages");
		apiVerification.responseKeyValidationJson(response, "totalAppointments");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceWithoutDateCurbsidePage() throws IOException {
		Response response = postAPIRequest.apptServiceForCurbsidePage(payload.apptServiceWithoutDatePayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceFlagTruePut() throws IOException {
		Response response = postAPIRequest.apptServicePut(payload.apptServicePayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mfis.appt.service.practice.id"),
				propertyData.getProperty("mfis.appt.service.true.flag"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceFlagFalsePut() throws IOException {
		Response response = postAPIRequest.apptServicePut(payload.apptServicePayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mfis.appt.service.practice.id"),
				propertyData.getProperty("mfis.appt.service.false.flag"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptServiceDelete() throws IOException {
		Response response = postAPIRequest.apptServiceDelete(
				payload.apptServiceDeletePayload(propertyData.getProperty("mfis.appt.service.practice.id"),
						Appointment.patientId, Appointment.apptId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mfis.appt.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}
}
