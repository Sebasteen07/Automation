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
import com.medfusion.product.appt.precheck.payload.AptPrecheckPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPrecheck;

import io.restassured.response.Response;

public class AptPrecheckTests extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptPrecheckPayload payload;
	public static PostAPIRequestAptPrecheck postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptPrecheck.getPostAPIRequestAptPrecheck();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptPrecheckPayload.getAptPrecheckPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.precheck"));
		log("BASE URL-" + propertyData.getProperty("base.url.apr.event.coll"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointments() throws IOException {
		Response response = postAPIRequest.getAppointments(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsAppId() throws IOException {
		Response response = postAPIRequest.getAppointmentsAppId(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsStartDay() throws IOException {
		Response response = postAPIRequest.getAppointmentsStartDay(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPracticeId() throws IOException {

		Response response = postAPIRequest.
		aptPostPracticeId(propertyData.getProperty("apt.precheck.practice.id"),
		payload.getPracticeIdPayload(propertyData.getProperty("apt.precheck.practice.appt.date.range.start"),
		propertyData.getProperty("apt.precheck.practice.appt.date.range.end")),
						headerConfig.HeaderwithToken(getaccessToken));

		log("Payload- " + payload.getPracticeIdPayload(propertyData.getProperty("apt.precheck.practice.appt.date.range.start"),
				propertyData.getProperty("apt.precheck.practice.appt.date.range.end")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivals() throws IOException {

		Response response = postAPIRequest.aptPostArrivals(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getArrivalsPayload(propertyData.getProperty("apt.precheck.arrivals.appt.date.range.start"),
						propertyData.getProperty("apt.precheck.arrivals.appt.date.range.end")), headerConfig.HeaderwithToken(getaccessToken));

		log("Payload- " + payload.getArrivalsPayload(propertyData.getProperty("apt.precheck.practice.appt.date.range.start"),
				propertyData.getProperty("apt.precheck.practice.appt.date.range.end")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTHistoryMessage() throws IOException {

		Response response = postAPIRequest.aptPostHistoryMessage(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getHistoryMessage(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		log("Payload- " + payload.getHistoryMessage());
		log("Verifying the response");
		log("Post history message");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMessageHistory(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTMessageHistory() throws IOException {

		Response response = postAPIRequest.aptMessageHistory(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getMessageHistoryPayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		log("Payload- " + payload.getMessageHistoryPayload());
		log("Verifying the response");
		log("Post message history");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMessageHistory(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETFormInformation() throws IOException {
		Response response = postAPIRequest.getFormInformation(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBroadcastMessage() throws IOException {

		Response response = postAPIRequest.aptBroadcastMessage(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getBroadcastMessagePayload(propertyData.getProperty("apt.precheck.broadcast.date.range.start"),
						propertyData.getProperty("apt.precheck.broadcast.date.range.end"),
						propertyData.getProperty("apt.precheck.broadcast.msg.patient.id"),
						propertyData.getProperty("apt.precheck.broadcast.msg.appt.id")), headerConfig.HeaderwithToken(getaccessToken));
		
		log("Payload- " + payload.getBroadcastMessagePayload(propertyData.getProperty("apt.precheck.broadcast.date.range.start"),
				propertyData.getProperty("apt.precheck.broadcast.date.range.end"),
				propertyData.getProperty("apt.precheck.broadcast.msg.patient.id"),
				propertyData.getProperty("apt.precheck.broadcast.msg.appt.id")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCheckinActions() throws IOException {

		Response response = postAPIRequest.getCheckinActions(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getCheckinActionsPayload(propertyData.getProperty("apt.precheck.copay.skip.appointment.id"),
						propertyData.getProperty("apt.precheck.copay.skip.patient.id"),propertyData.getProperty("apt.precheck.balance.practice.id")), headerConfig.HeaderwithToken(getaccessToken));

		log("Payload- " + payload.getCheckinActionsPayload(propertyData.getProperty("apt.precheck.copay.skip.appointment.id"),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),propertyData.getProperty("apt.precheck.balance.practice.id")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEAppointmentTypes() throws IOException {

		Response response = postAPIRequest.getDELETEAppointmentTypes(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEPractiseSettings() throws IOException {

		Response response = postAPIRequest.getDELETEPracticeSettings(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETETimezones() throws IOException {

		Response response = postAPIRequest.getDELETETimezones(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEAppointmentsFromDb() throws IOException {

		Response response = postAPIRequest.getDELETEAppointmentsFromDb(
				propertyData.getProperty("apt.precheck.practice.id"), payload.getDELETEAppointmentsFromDbPayload(propertyData.getProperty("apt.precheck.delete.appt.id"),
						propertyData.getProperty("apt.precheck.delete.patient.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Payload- " + payload.getDELETEAppointmentsFromDbPayload(propertyData.getProperty("apt.precheck.delete.appt.id"),
				propertyData.getProperty("apt.precheck.delete.patient.id")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETPatientsIdentification() throws IOException {
		Response response = postAPIRequest.getPatientsIdentification(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.patient.id"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointments() throws IOException {

		Response response = postAPIRequest.aptpostAppointments(propertyData.getProperty("apt.precheck.practice.id"),
				payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id")), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.integration.id"),
				propertyData.getProperty("apt.precheck.create.patient.id"));

		log("Payload- " + payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointments(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.integration.id"),
				propertyData.getProperty("apt.precheck.create.patient.id"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopaySkip() throws IOException {

		Response response = postAPIRequest.aptpostCopaySkip(propertyData.getProperty("apt.precheck.balance.practice.id"),

				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.balance.patient.id"),
				propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));

		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Copay skipped");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
					propertyData.getProperty("apt.precheck.balance.patient.id"),
					propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Copay already paid");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyCopayPaid(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopaySkipIncorrectPatientId() throws IOException {

		Response response = postAPIRequest.aptpostCopaySkipIncorrectPatientId(
				propertyData.getProperty("apt.precheck.practice.id"),

				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalanceSkip() throws IOException {

		Response response = postAPIRequest.aptpostBalanceSkip(propertyData.getProperty("apt.precheck.practice.id"),

				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalanceSkipIncorrectPatientId() throws IOException {

		Response response = postAPIRequest.aptpostBalanceSkipIncorrectPatientId(
				propertyData.getProperty("apt.precheck.practice.id"),

				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTForms() throws IOException {

		Response response = postAPIRequest.aptPutForms(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getFormsPayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getFormsPayload());
		log("Verify response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutForms(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETRequiredForms() throws IOException {
		Response response = postAPIRequest.getRequiredForms(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTInsurance() throws IOException {

		Response response = postAPIRequest.aptPutInsurance(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getInsurancePayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getInsurancePayload());
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutInsurance(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutDemographics() throws IOException {

		Response response = postAPIRequest.aptPutDemographics(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getDemographicsPayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Payload- " + payload.getDemographicsPayload());
		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyDemographics(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalancePay() throws IOException {

		Response response = postAPIRequest.aptBalancePay(propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getBalancePayPayload(propertyData.getProperty("apt.precheck.balance.patient.id")), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.balance.patient.id"),
				propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));

		log("Payload- " + payload.getBalancePayPayload(propertyData.getProperty("apt.precheck.balance.patient.id")));
		log("Verifying the response");

		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
					propertyData.getProperty("apt.precheck.balance.patient.id"),
					propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Balance already paid");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyBalancePaid(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopayPay() throws IOException {

		Response response = postAPIRequest.aptCopayPay(propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getCopayPayPayload(propertyData.getProperty("apt.precheck.balance.patient.id")), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.balance.patient.id"),
				propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));

		log("Payload- " + payload.getCopayPayPayload(propertyData.getProperty("apt.precheck.balance.patient.id")));
		log("Verifying the response");

		if (response.getStatusCode() == 200) {
			log("Post an Copay amount");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
					propertyData.getProperty("apt.precheck.balance.patient.id"),
					propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Copay already paid");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyCopayPaid(response);
		}

		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsConfirm() throws IOException {
		Response response = postAPIRequest.aptAppointmentActionsConfirm(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response, propertyData.getProperty("confirm.appt.action"),
					propertyData.getProperty("apt.precheck.practice.id"),
					propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
					propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAppointmentActionPast(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsCancel() throws IOException {
		Response response = postAPIRequest.aptAppointmentActionsCancel(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response, propertyData.getProperty("cancel.appt.action"),
					propertyData.getProperty("apt.precheck.practice.id"),
					propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
					propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAppointmentActionPast(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsArrival() throws IOException {
		Response response = postAPIRequest.aptAppointmentActionsArrival(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response, propertyData.getProperty("arrival.appt.action"),
					propertyData.getProperty("apt.precheck.practice.id"),
					propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
					propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAppointmentActionPast(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsCurbscheckin() throws IOException {
		Response response = postAPIRequest.aptAppointmentActionsCurbscheckin(
				propertyData.getProperty("apt.precheck.balance.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		log("Verifying the response");

		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response, propertyData.getProperty("curbscheckin.appt.action"),
					propertyData.getProperty("apt.precheck.balance.practice.id"),
					propertyData.getProperty("apt.precheck.copay.skip.patient.id"),
					propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAppointmentActionPast(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideConfirm() throws IOException {
		Response response = postAPIRequest.aptArrivalActionsCurbsideConfirm(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.balance.patient.id"),
				propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		log("Verifying the response");

		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response, propertyData.getProperty("confirm.appt.action"),
					propertyData.getProperty("apt.precheck.balance.practice.id"),
					propertyData.getProperty("apt.precheck.balance.patient.id"),
					propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAppointmentActionPast(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideCancel() throws IOException {
		Response response = postAPIRequest.aptArrivalActionsCurbsideCancel(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.balance.patient.id"),
				propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		log("Verifying the response");

		assertEquals(response.getStatusCode(), 200);

		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response, propertyData.getProperty("cancel.appt.action"),
					propertyData.getProperty("apt.precheck.balance.practice.id"),
					propertyData.getProperty("apt.precheck.balance.patient.id"),
					propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAppointmentActionPast(response);
		}
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideArrival() throws IOException {
		Response response = postAPIRequest.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.balance.patient.id"),
				propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		log("Verifying the response");

		if (response.getStatusCode() == 200) {
			log("Verify arrival actions");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response, propertyData.getProperty("arrival.appt.action"),
					propertyData.getProperty("apt.precheck.balance.practice.id"),
					propertyData.getProperty("apt.precheck.balance.patient.id"),
					propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAppointmentActionPast(response);
		}
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideCurbscheckin() throws IOException {
		Response response = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.balance.patient.id"),
				propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response, propertyData.getProperty("curbscheckin.appt.action"),
					propertyData.getProperty("apt.precheck.balance.practice.id"),
					propertyData.getProperty("apt.precheck.balance.patient.id"),
					propertyData.getProperty("apt.precheck.pm.balance.appmnt.id"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAppointmentActionPast(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.getAppointmentsGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), responseGuestToken,
				propertyData.getProperty("apt.precheck.guest.patient.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGuestSessionsAuthorization() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.GuestSessionsAuthorization(responseGuestToken);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsAppIdGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.getAppointmentsAppIdGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), responseGuestToken,
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETRequiredFormsGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.getRequiredFormsGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), responseGuestToken,
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTFormsGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutFormsGuest(propertyData.getProperty("apt.precheck.guest.practice.id"),
				payload.getFormsPayloadGuest(), responseGuestToken,

				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"), headerConfig.defaultHeader());

		log("Payload- " + payload.getFormsPayloadGuest());
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutForms(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTInsuranceGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutInsuranceGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getInsurancePayloadGuest(),
				responseGuestToken,

				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"), headerConfig.defaultHeader());

		log("Payload- " + payload.getInsurancePayloadGuest());
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutInsurance(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTDemographicsGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutDemographicsGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getDemographicsPayloadGuest(),
				responseGuestToken,

				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"), headerConfig.defaultHeader());

		log("Payload- " + payload.getDemographicsPayloadGuest());

		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyDemographics(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCopaySkipGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostCopaySkipGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), responseGuestToken,
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Skip  Copay amount");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
					propertyData.getProperty("apt.precheck.guest.patient.id"),
					propertyData.getProperty("apt.precheck.guest.appointment.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Copay already paid");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyCopayPaid(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalanceSkipGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostBalanceSkipGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), responseGuestToken,
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Skip  balance amount");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
					propertyData.getProperty("apt.precheck.guest.patient.id"),
					propertyData.getProperty("apt.precheck.guest.appointment.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Balance  already paid");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyBalancePaid(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalancePayGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostBalancePayGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getBalancePayloadGuest(propertyData.getProperty("apt.precheck.guest.patient.id")),
				responseGuestToken,

				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"), headerConfig.defaultHeader());

		log("Payload- " + payload.getBalancePayloadGuest(propertyData.getProperty("apt.precheck.guest.patient.id")));
		log("Verifying the response");

		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
					propertyData.getProperty("apt.precheck.guest.patient.id"),
					propertyData.getProperty("apt.precheck.guest.appointment.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Balance already paid");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyBalancePaid(response);
		}

		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCopayPayGuest() throws IOException {

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostCopayPayGuest(
				propertyData.getProperty("apt.precheck.guest.practice.id"), payload.getCopayPayPayloadGuest(propertyData.getProperty("apt.precheck.guest.patient.id")),
				responseGuestToken, propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"), headerConfig.defaultHeader());

		log("Payload- " + payload.getCopayPayPayloadGuest(propertyData.getProperty("apt.precheck.guest.patient.id")));
		log("Verifying the response");

		if (response.getStatusCode() == 200) {
			log("Post an Copay amount");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
					propertyData.getProperty("apt.precheck.guest.patient.id"),
					propertyData.getProperty("apt.precheck.guest.appointment.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Copay already paid");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyCopayPaid(response);
		}

		apiVerification.responseTimeValidation(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}