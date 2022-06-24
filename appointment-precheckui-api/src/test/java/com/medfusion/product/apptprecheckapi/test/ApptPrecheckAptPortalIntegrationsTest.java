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
import com.medfusion.product.appt.precheck.payload.AptPortalIntegrationsPayload;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPortalIntegrations;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;

import io.restassured.response.Response;

public class ApptPrecheckAptPortalIntegrationsTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptPortalIntegrationsPayload payload;
	public static PostAPIRequestAptPortalIntegrations postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	public static PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche;
	public static MfAppointmentSchedulerPayload schedulerPayload;
	APIVerification apiVerification = new APIVerification();
	String patientId;
	CommonMethods commonMtd;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptPortalIntegrations.getPostAPIRequestAptPortalIntegrations();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptPortalIntegrationsPayload.getAptPortalIntegrationsPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		commonMtd= new CommonMethods();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.portal.integrations"));
		log("BASE URL-" + propertyData.getProperty("baseurl.apt.portal.integrations"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsConfirmationPost() throws IOException {
		Response response = postAPIRequest.sendsConfirmation(
				payload.sendsConfirmationPayload( Appointment.apptId,Appointment.patientId, propertyData.getProperty("sends.conf.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsConfirmationData(response, propertyData.getProperty("sends.conf.practice.id"),
				Appointment.apptId, Appointment.patientId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsConfirmationPostWithoutApptId() throws IOException {
		Response response = postAPIRequest.sendsConfirmation(
				payload.sendsConfirmationPayloadWithoutApptId(Appointment.patientId,
						propertyData.getProperty("sends.conf.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsConfirmationWithIncompleteData(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsConfirmationPostWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.sendsConfirmation(
				payload.sendsConfirmationPayloadWithoutPracticeId(Appointment.apptId,
						Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsConfirmationWithIncompleteData(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsConfirmationPostWithoutPatientId() throws IOException {
		Response response = postAPIRequest.sendsConfirmation(
				payload.sendsConfirmationPayloadWithoutPatientId(Appointment.apptId,
						propertyData.getProperty("sends.conf.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsConfirmationWithIncompleteData(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsPatientProvidedDataPost() throws IOException {
		Response response = postAPIRequest.sendsPatientProvidedData(
				payload.sendsPatientProvidedDataPayload(Appointment.apptId,
						Appointment.patientId, propertyData.getProperty("sends.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsPatientProvidedDataPostWithoutApptId() throws IOException {
		Response response = postAPIRequest.sendsPatientProvidedData(
				payload.sendsPatientProvidedDataPayloadWithoutApptId(Appointment.patientId,
						propertyData.getProperty("sends.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsPatientProvidedDataPostWithoutPatientId() throws IOException {
		Response response = postAPIRequest.sendsPatientProvidedData(
				payload.sendsPatientProvidedDataPayloadWithoutPatientId(Appointment.apptId,
						propertyData.getProperty("sends.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithoutPatientId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsPatientProvidedDataPostWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.sendsPatientProvidedData(
				payload.sendsPatientProvidedDataPayloadWithoutPracticeId(Appointment.apptId,
						propertyData.getProperty("sends.patient.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithoutPracticeId(response);
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
}
