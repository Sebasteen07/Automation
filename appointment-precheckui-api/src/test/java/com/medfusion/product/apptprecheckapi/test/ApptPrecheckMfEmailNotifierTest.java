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
import com.medfusion.product.appt.precheck.payload.MfEmailNotifierPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfEmailNotifier;

import io.restassured.response.Response;

public class ApptPrecheckMfEmailNotifierTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfEmailNotifierPayload payload;
	public static PostAPIRequestMfEmailNotifier postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();
	CommonMethods commonMtd;
	public static PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche;
	public static MfAppointmentSchedulerPayload schedulerPayload;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfEmailNotifier.getPostAPIRequestMfEmailNotifier();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfEmailNotifierPayload.getMfEmailNotifierPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.email.notifier"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.email.notifier"));
		commonMtd = new CommonMethods();
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForCadence1() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.appt.check.in"),
				propertyData.getProperty("email.notif.type.cadence1"), propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId,
				propertyData.getProperty("email.subject.urn"), propertyData.getProperty("send.email.notif.type"),
				propertyData.getProperty("email.recipient.address"), propertyData.getProperty("send.email.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForCadenceCurbs() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.appt.check.in"),
				propertyData.getProperty("email.notif.type.cadence.curbs"),
				propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId,
				propertyData.getProperty("email.subject.urn"), propertyData.getProperty("send.email.notif.type"),
				propertyData.getProperty("email.recipient.address"), propertyData.getProperty("send.email.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForPssConfirmation() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.appt.check.in"),
				propertyData.getProperty("email.notif.type.pss.conf"), propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId,
				propertyData.getProperty("email.subject.urn"), propertyData.getProperty("send.email.notif.type"),
				propertyData.getProperty("email.recipient.address"), propertyData.getProperty("send.email.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForCadence30Minutes() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.appt.check.in"),
				propertyData.getProperty("email.notif.type.cadence.30minutes"),
				propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId, propertyData.getProperty("email.subject.urn"),
				propertyData.getProperty("send.email.notif.type"), propertyData.getProperty("email.recipient.address"),
				propertyData.getProperty("send.email.subject")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForCadence2hours() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.appt.check.in"),
				propertyData.getProperty("email.notif.type.cadence.2hours"),
				propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId, propertyData.getProperty("email.subject.urn"),
				propertyData.getProperty("send.email.notif.type"), propertyData.getProperty("email.recipient.address"),
				propertyData.getProperty("send.email.subject")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForPurposeCheckIn() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.appt.check.in"),
				propertyData.getProperty("email.notif.type.cadence.2hours"),
				propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId, propertyData.getProperty("email.subject.urn"),
				propertyData.getProperty("send.email.notif.type"), propertyData.getProperty("email.recipient.address"),
				propertyData.getProperty("send.email.subject")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForApptDefault() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.appt.default"),
				propertyData.getProperty("email.notif.type.cadence.2hours"),
				propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId, propertyData.getProperty("email.subject.urn"),
				propertyData.getProperty("send.email.notif.type"), propertyData.getProperty("email.recipient.address"),
				propertyData.getProperty("send.email.subject")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForApptScheConf() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.appt.sche.conf"),
				propertyData.getProperty("email.notif.type.cadence.2hours"),
				propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId, propertyData.getProperty("email.subject.urn"),
				propertyData.getProperty("send.email.notif.type"), propertyData.getProperty("email.recipient.address"),
				propertyData.getProperty("send.email.subject")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostForBroadcastAppt() throws IOException {
		Response response = postAPIRequest.sendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.broadcast.appt"),
				propertyData.getProperty("email.notif.type.cadence.2hours"),
				propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId, propertyData.getProperty("email.subject.urn"),
				propertyData.getProperty("send.email.notif.type"), propertyData.getProperty("email.recipient.address"),
				propertyData.getProperty("send.email.subject")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendEmail(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendEmailNotifierPostWithoutSendEmail() throws IOException {
		Response response = postAPIRequest.sendEmailWithoutSendEmail(payload.sendEmailPayload(
				propertyData.getProperty("email.notif.purpose.broadcast.appt"),
				propertyData.getProperty("email.notif.type.cadence.2hours"),
				propertyData.getProperty("update.appt.metadata.practice.id")+"."+Appointment.patientId+"."+Appointment.apptId, propertyData.getProperty("email.subject.urn"),
				propertyData.getProperty("send.email.notif.type"), propertyData.getProperty("email.recipient.address"),
				propertyData.getProperty("send.email.subject")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHandleCallbackPost() throws IOException {
		Response response = postAPIRequest.handleCallback(propertyData.getProperty("handle.callback.url"),
				propertyData.getProperty("handle.callback.username"),
				propertyData.getProperty("handle.callback.password"),
				payload.handleCallbackPayload(propertyData.getProperty("handle.callback.email")),
				headerConfig.defaultHeader());
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		Appointment.plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + Appointment.plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("update.appt.metadata.practice.id"),
				schedulerPayload.putAppointmentPayload(Appointment.plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
	}

}
