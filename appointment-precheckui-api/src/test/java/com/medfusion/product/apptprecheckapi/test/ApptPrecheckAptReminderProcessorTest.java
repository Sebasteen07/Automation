// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.AptReminderProcessorPayload;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptReminderProcessor;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;

import io.restassured.response.Response;

public class ApptPrecheckAptReminderProcessorTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptReminderProcessorPayload payload;
	public static PostAPIRequestAptReminderProcessor postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	public static PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche;
	public static MfAppointmentSchedulerPayload schedulerPayload;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptReminderProcessor.getPostAPIRequestAptReminderProcessor();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptReminderProcessorPayload.getAptReminderProcessorPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.reminder.processor"));
		log("BASE URL-" + propertyData.getProperty("baseurl.apt.reminder.processor"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteClearSettingsCache() throws IOException {
		Response response = postAPIRequest.clearSettingsCache(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteClearSettingsCacheforPractice() throws IOException {
		Response response = postAPIRequest.clearSettingsCacheForPractice(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("clear.practice.setting.cache.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteClearSettingsCacheWithoutPracticeId() throws IOException {
		Response response = postAPIRequest
				.clearSettingsCacheForPracticeWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPost() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(propertyData.getProperty("process.reminder.data.cadence1"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProcessReminderData(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostForCadence1() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(propertyData.getProperty("process.reminder.data.cadence1"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProcessReminderData(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostForCadence3() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(propertyData.getProperty("process.reminder.data.cadence3"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProcessReminderData(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostForCadence5() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(propertyData.getProperty("process.reminder.data.cadence5"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProcessReminderData(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostForInvalidCadence2() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(propertyData.getProperty("process.reminder.data.invalid.cadence"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProcessReminderDataWithInvalidData(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostForCurbsSideCadence() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(
						propertyData.getProperty("process.reminder.data.curbs.side.cadence"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProcessReminderDataCurbsSide(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostWithInvalidCurbsSideCadence() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(
						propertyData.getProperty("process.reminder.data.invalid.curbs.side.cadence"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProcessReminderDataCurbsSide(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostWithoutCadence() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayloadWithoutCadence(
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithoutCadence(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostWithInvalidPracticeId() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(propertyData.getProperty("process.reminder.data.cadence3"),
						propertyData.getProperty("process.reminder.data.invalid.practice.id"), Appointment.patientId,
						Appointment.apptId, propertyData.getProperty("process.reminder.data.type"),
						Appointment.plus20Minutes, propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithInvalidPracticeId(response,
				propertyData.getProperty("process.reminder.data.invalid.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostWithInvalidPatientId() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(propertyData.getProperty("process.reminder.data.cadence3"),
						propertyData.getProperty("process.reminder.data.practice.id"),
						propertyData.getProperty("process.reminder.data.invalid.patient.id"), Appointment.apptId,
						propertyData.getProperty("process.reminder.data.type"), Appointment.plus20Minutes,
						propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithInvalidPatientId(response,
				propertyData.getProperty("process.reminder.data.practice.id"),
				propertyData.getProperty("process.reminder.data.invalid.patient.id"), Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostWithInvalidApptId() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayload(propertyData.getProperty("process.reminder.data.cadence3"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						propertyData.getProperty("process.reminder.data.invalid.appt.id"),
						propertyData.getProperty("process.reminder.data.type"), Appointment.plus20Minutes,
						propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithInvalidApptId(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				propertyData.getProperty("process.reminder.data.invalid.appt.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostWithoutStatus() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayloadWithoutStatus(
						propertyData.getProperty("process.reminder.data.cadence3"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						propertyData.getProperty("process.reminder.data.invalid.appt.id"),
						propertyData.getProperty("process.reminder.data.type"), Appointment.plus20Minutes),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProcessReminderDataPostWithoutTime() throws IOException {
		Response response = postAPIRequest.processReminderData(
				payload.getProcessReminderDataPayloadWithoutTime(
						propertyData.getProperty("process.reminder.data.cadence3"),
						propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
						propertyData.getProperty("process.reminder.data.invalid.appt.id"),
						propertyData.getProperty("process.reminder.data.type"),
						propertyData.getProperty("process.reminder.data.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithInvalidApptId(response,
				propertyData.getProperty("process.reminder.data.practice.id"), Appointment.patientId,
				propertyData.getProperty("process.reminder.data.invalid.appt.id"));
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		Appointment.patientId = String.valueOf(randamNo);
		Appointment.apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		Appointment.plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + Appointment.plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				schedulerPayload.putAppointmentPayload(Appointment.plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
	}
}
