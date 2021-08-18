// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.MfNotificationModulatorPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfNotificationModulator;

import io.restassured.response.Response;

public class ApptPrecheckMfNotificationModulatorTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfNotificationModulatorPayload payload;
	public static PostAPIRequestMfNotificationModulator postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfNotificationModulator.getPostAPIRequestMfNotificationModulator();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfNotificationModulatorPayload.getMfNotificationModulatorPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.notification.modulator"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.notification.modulator"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForCadence1() throws IOException {
		Response response = postAPIRequest.sendNotifications(
				payload.sendNotificationPayload(propertyData.getProperty("notif.purpose.appt.check.in"),
						propertyData.getProperty("notif.type.cadence1"), propertyData.getProperty("notif.subject.id"),
						propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
						propertyData.getProperty("notif.recipient.address"),
						propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForCadenceCurbs() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.appt.check.in"),
				propertyData.getProperty("notif.type.cadence.curbs"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForPssConfirmation() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.appt.check.in"),
				propertyData.getProperty("notif.type.pss.confirmation"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForCadence30Minutes() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.appt.check.in"),
				propertyData.getProperty("notif.type.cadence.30minutes"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForCadence2Hours() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.appt.check.in"),
				propertyData.getProperty("notif.type.cadence.2hours"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForPerposeApptCheckIn() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.appt.check.in"),
				propertyData.getProperty("notif.type.cadence.2hours"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForPerposeApptDefault() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.appt.default"),
				propertyData.getProperty("notif.type.cadence.2hours"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForPerposeApptScheduleConf() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.appt.sche.conf"),
				propertyData.getProperty("notif.type.cadence.2hours"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostForPerposeBroadcastAppt() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.broadcast.appt"),
				propertyData.getProperty("notif.type.cadence.2hours"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostWithInvalidNotifType() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayload(
				propertyData.getProperty("notif.purpose.broadcast.appt"),
				propertyData.getProperty("notif.type.cadence.2hours"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("invalid.notification.type"),
				propertyData.getProperty("notif.recipient.address"), propertyData.getProperty("notification.subject")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendNotificationWithInvalidNotifType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendNotificationsPostWithoutNotifType() throws IOException {
		Response response = postAPIRequest.sendNotifications(payload.sendNotificationPayloadWithoutNotifType(
				propertyData.getProperty("notif.purpose.broadcast.appt"),
				propertyData.getProperty("notif.type.cadence.2hours"), propertyData.getProperty("notif.subject.id"),
				propertyData.getProperty("notif.subject.urn"), propertyData.getProperty("notif.recipient.address"),
				propertyData.getProperty("notification.subject")), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySendNotificationhWithoutNotifType(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}
