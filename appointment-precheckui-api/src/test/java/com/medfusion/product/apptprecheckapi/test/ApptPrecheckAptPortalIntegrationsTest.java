// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.AptPortalIntegrationsPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPortalIntegrations;

import io.restassured.response.Response;

public class ApptPrecheckAptPortalIntegrationsTest extends BaseTestNGWebDriver {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptPortalIntegrationsPayload payload;
	public static PostAPIRequestAptPortalIntegrations postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptPortalIntegrations.getPostAPIRequestAptPortalIntegrations();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptPortalIntegrationsPayload.getAptPortalIntegrationsPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.portal.integrations"));
		log("BASE URL-" + propertyData.getProperty("baseurl.apt.portal.integrations"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsConfirmationPost() throws IOException {
		Response response = postAPIRequest.sendsConfirmation(
				payload.sendsConfirmationPayload(propertyData.getProperty("sends.conf.appointment.id"),
						propertyData.getProperty("sends.conf.patient.id"),
						propertyData.getProperty("sends.conf.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsConfirmationData(response, propertyData.getProperty("sends.conf.practice.id"),
				propertyData.getProperty("sends.conf.appointment.id"),
				propertyData.getProperty("sends.conf.patient.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsConfirmationPostWithoutApptId() throws IOException {
		Response response = postAPIRequest.sendsConfirmation(
				payload.sendsConfirmationPayloadWithoutApptId(
						propertyData.getProperty("sends.conf.patient.id"),
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
				payload.sendsConfirmationPayloadWithoutPracticeId(propertyData.getProperty("sends.conf.appointment.id"),
						propertyData.getProperty("sends.conf.patient.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsConfirmationWithIncompleteData(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsConfirmationPostWithoutPatientId() throws IOException {
		Response response = postAPIRequest.sendsConfirmation(
				payload.sendsConfirmationPayloadWithoutPatientId(propertyData.getProperty("sends.conf.appointment.id"),
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
				payload.sendsPatientProvidedDataPayload(propertyData.getProperty("sends.appt.id"),
						propertyData.getProperty("sends.patient.id"), propertyData.getProperty("sends.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsPatientProvidedDataPostWithoutApptId() throws IOException {
		Response response = postAPIRequest.sendsPatientProvidedData(
				payload.sendsPatientProvidedDataPayloadWithoutApptId(
						propertyData.getProperty("sends.patient.id"), propertyData.getProperty("sends.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithoutApptId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsPatientProvidedDataPostWithoutPatientId() throws IOException {
		Response response = postAPIRequest.sendsPatientProvidedData(
				payload.sendsPatientProvidedDataPayloadWithoutPatientId(
						propertyData.getProperty("sends.appt.id"), propertyData.getProperty("sends.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithoutPatientId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendsPatientProvidedDataPostWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.sendsPatientProvidedData(
				payload.sendsPatientProvidedDataPayloadWithoutPracticeId(
						propertyData.getProperty("sends.appt.id"), propertyData.getProperty("sends.patient.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendsPatientProvidedDataWithoutPracticeId(response);
	}

}
