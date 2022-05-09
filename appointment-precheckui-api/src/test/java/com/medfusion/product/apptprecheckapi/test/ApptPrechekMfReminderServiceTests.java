// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
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
import com.medfusion.product.appt.precheck.payload.MfReminderServicePayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfReminderService;

import io.restassured.response.Response;

public class ApptPrechekMfReminderServiceTests extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfReminderServicePayload payload;
	public static PostAPIRequestMfReminderService postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfReminderService.getPostAPIRequestMfReminderService();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfReminderServicePayload.getMfReminderServicePayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.reminder.service"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.reminder.service"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderPostServiceCadence1() throws IOException {
		Response response = postAPIRequest.apptReminderService(
				payload.getMfReminderServicePayload(propertyData.getProperty("mf.rem.service.appt.id"),
						propertyData.getProperty("mf.rem.service.patient.id"),
						propertyData.getProperty("mf.rem.service.practice.id"),
						propertyData.getProperty("practice.name"), propertyData.getProperty("system.id.ge"),
						propertyData.getProperty("timezone.id"), propertyData.getProperty("timezone.long.name"),
						propertyData.getProperty("cadence.1"), propertyData.getProperty("email.notification.type"),
						testData.isGenerateEmailTrue(), testData.isGenerateTextTrue(),
						propertyData.getProperty("mf.rem.service.email"), propertyData.getProperty("language.en")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("cadence.1"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostInvalidLanguage() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("invalid.language")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.verifyInvalidLanguage(response);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostWithoutNotifType() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayloadWithoutNotifType(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.verifyMfRemServiceWithoutNotifType(response);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostCadenceCurbs() throws IOException {
		Response response = postAPIRequest.apptReminderService(
				payload.getMfReminderServicePayload(propertyData.getProperty("mf.rem.service.appt.id"),
						propertyData.getProperty("mf.rem.service.patient.id"),
						propertyData.getProperty("mf.rem.service.practice.id"),
						propertyData.getProperty("practice.name"), propertyData.getProperty("system.id.ge"),
						propertyData.getProperty("timezone.id"), propertyData.getProperty("timezone.long.name"),
						propertyData.getProperty("cadence.curbs"), propertyData.getProperty("email.notification.type"),
						testData.isGenerateEmailTrue(), testData.isGenerateTextTrue(),
						propertyData.getProperty("mf.rem.service.email"), propertyData.getProperty("language.en")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("cadence.curbs"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostCadence30min() throws IOException {
		Response response = postAPIRequest.apptReminderService(
				payload.getMfReminderServicePayload(propertyData.getProperty("mf.rem.service.appt.id"),
						propertyData.getProperty("mf.rem.service.patient.id"),
						propertyData.getProperty("mf.rem.service.practice.id"),
						propertyData.getProperty("practice.name"), propertyData.getProperty("system.id.ge"),
						propertyData.getProperty("timezone.id"), propertyData.getProperty("timezone.long.name"),
						propertyData.getProperty("cadence.30.min"), propertyData.getProperty("email.notification.type"),
						testData.isGenerateEmailTrue(), testData.isGenerateTextTrue(),
						propertyData.getProperty("mf.rem.service.email"), propertyData.getProperty("language.en")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("cadence.30.min"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostCadence2Hours() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("cadence.2.hours"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("cadence.2.hours"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderservicePostPssConfirmation() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostEmailGenerateFalse() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailFalse(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyResponseForTextMessageContent(response, propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderservicePostTextGenerateFalse() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextFalse(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyResponseForEmailContent(response, propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostEmailTextGenerateFalse() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailFalse(),
				testData.isGenerateTextFalse(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostInvalidEmail() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("invalid.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.verifyInvalidEmail(response);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostInvalidApptIds() throws IOException {
		Response response = postAPIRequest.apptReminderService(
				payload.getInvalidApptIdsPayload(testData.getPmAppointmentId(), testData.getPmPatientId(),
						testData.getPmPracticeId(), propertyData.getProperty("practice.name"),
						propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
						propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
						propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
						testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
						propertyData.getProperty("language.en")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostLanguageEN() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostLanguageES() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.es")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostLanguageEnEs() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("timezone.id"),
				propertyData.getProperty("timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en.es")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptReminderServicePostChangeTimezone() throws IOException {
		Response response = postAPIRequest.apptReminderService(payload.getMfReminderServicePayload(
				propertyData.getProperty("mf.rem.service.appt.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.practice.id"), propertyData.getProperty("practice.name"),
				propertyData.getProperty("system.id.ge"), propertyData.getProperty("other.timezone.id"),
				propertyData.getProperty("other.timezone.long.name"), propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("email.notification.type"), testData.isGenerateEmailTrue(),
				testData.isGenerateTextTrue(), propertyData.getProperty("mf.rem.service.email"),
				propertyData.getProperty("language.en")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMfRemServiceResponse(response, propertyData.getProperty("pss.confirmation"),
				propertyData.getProperty("mf.rem.service.practice.id"),
				propertyData.getProperty("mf.rem.service.patient.id"),
				propertyData.getProperty("mf.rem.service.appt.id"));
		apiVerification.responseTimeValidation(response);

	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}

}
