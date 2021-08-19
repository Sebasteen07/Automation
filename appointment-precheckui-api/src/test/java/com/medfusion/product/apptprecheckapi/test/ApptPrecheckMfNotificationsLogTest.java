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
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.MfNotificationsLogPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfNotificationsLog;

import io.restassured.response.Response;

public class ApptPrecheckMfNotificationsLogTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfNotificationsLogPayload payload;
	public static PostAPIRequestMfNotificationsLog postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfNotificationsLog.getPostAPIRequestMfNotificationsLog();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfNotificationsLogPayload.getMfNotificationsLogPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.notifications.log"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.notifications.log"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsLogGet() throws IOException {
		Response response = postAPIRequest.returnsLog(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("notifiction.subj.urn"), propertyData.getProperty("notifiction.subj.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReturnLogs(response, propertyData.getProperty("notifiction.subj.urn"),
				propertyData.getProperty("notifiction.subj.id"));
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].notificationId");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].mechanism");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].notificationType");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].purpose");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].statuses[0].mfStatus");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].statuses[0].vendorStatus");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsLogGetWithoutSubUrn() throws IOException {
		Response response = postAPIRequest.returnsLogWithoutSubjUrn(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("notifiction.subj.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReturnLogsWithoutSubjUrn(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsLogGetWithoutSubjId() throws IOException {
		Response response = postAPIRequest.returnsLogWithoutSubjId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("notifiction.subj.urn"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReturnLogsWithoutSubjId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsLogPost() throws IOException {
		Response response = postAPIRequest.returnsLogPost(
				payload.returnLogsPayload(propertyData.getProperty("notifiction.subj.id"),
						propertyData.getProperty("notifiction.subj.urn")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
		apiVerification.verifyReturnLogsPost(response, propertyData.getProperty("notifiction.subj.urn"),
				propertyData.getProperty("notifiction.subj.id"));
		} else {
			apiVerification.verifyReturnLogsPost(response, propertyData.getProperty("notifiction.subj.urn"),
					propertyData.getProperty("notifiction.subject.id"));
		}
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].notificationId");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].mechanism");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].notificationType");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].purpose");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].statuses[0].mfStatus");
		apiVerification.responseKeyValidationJson(response, "result.notifications[0].statuses[0].vendorStatus");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsLogPostWithInvalidSubjId() throws IOException {
		Response response = postAPIRequest.returnsLogPost(
				payload.returnLogsPayload(propertyData.getProperty("invalid.notif.subj.id"),
						propertyData.getProperty("notifiction.subj.urn")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReturnLogsPostWithInvalidSubjId(response,
				propertyData.getProperty("invalid.notif.subj.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsLogPostWithInvalidSubjUrn() throws IOException {
		Response response = postAPIRequest.returnsLogPost(
				payload.returnLogsPayload(propertyData.getProperty("notifiction.subj.id"),
						propertyData.getProperty("invalid.notif.subj.urn")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReturnLogsPostWithInvalidSubjUrn(response,
				propertyData.getProperty("notifiction.subj.id"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReturnsLogPostWithoutSubjId() throws IOException {
		Response response = postAPIRequest.returnsLogPost(
				payload.returnLogsPayloadWithoutSubjId(propertyData.getProperty("notifiction.subj.urn")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReturnLogsPostWithoutSubjId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteLog() throws IOException {
		Response response = postAPIRequest.deleteLog(
				payload.deleteLogsPayload(propertyData.getProperty("delete.notif.subj.id"),
						propertyData.getProperty("notifiction.subj.urn")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDeleteLogs(response, propertyData.getProperty("notifiction.subj.id"),
				propertyData.getProperty("mf.notif.log.practice.id"),
				propertyData.getProperty("mf.notif.log.patient.id"), propertyData.getProperty("mf.notif.log.appt.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteLogWithInvalidSubjId() throws IOException {
		Response response = postAPIRequest.deleteLog(
				payload.deleteLogsPayload(propertyData.getProperty("delete.invalid.notif.subj.id"),
						propertyData.getProperty("notifiction.subj.urn")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDeleteLogsWithInvalidSubjId(response,
				propertyData.getProperty("delete.invalid.notif.subj.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteLogInvalidSubjUrn() throws IOException {
		Response response = postAPIRequest.deleteLog(
				payload.deleteLogsPayload(propertyData.getProperty("delete.notif.subj.id"),
						propertyData.getProperty("invalid.notif.subj.urn")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDeleteLogsWithInvalidSubjUrn(response, propertyData.getProperty("delete.notif.subj.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatesStatusPost() throws IOException {
		Response response = postAPIRequest.createsStatus(
				payload.createStatusPayload(propertyData.getProperty("create.status.notif.time")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("create.status.log.id"),
				propertyData.getProperty("create.status.notif.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 201);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatesStatusPostWithInvalidTime() throws IOException {
		Response response = postAPIRequest.createsStatus(
				payload.createStatusPayload(propertyData.getProperty("invalid.status.notif.time")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("create.status.log.id"),
				propertyData.getProperty("create.status.notif.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateStatusWithInvalidTime(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatesStatusPostWithoutNotifiId() throws IOException {
		Response response = postAPIRequest.createsStatusWithoutNotifId(
				payload.createStatusPayload(propertyData.getProperty("create.status.notif.time")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("create.status.notif.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatesNotificationPutForCadence30Minutes() throws IOException {
		Response response = postAPIRequest.createsNotification(
				payload.createsNotificationPayload(propertyData.getProperty("created.notification.time"),
						propertyData.getProperty("created.notification.mechanism"),
						propertyData.getProperty("created.notification.id"),
						propertyData.getProperty("created.notification.type"),
						propertyData.getProperty("notif.purpose.cadence.30minutes"),
						propertyData.getProperty("create.status.time"), propertyData.getProperty("vendor.status"),
						propertyData.getProperty("vendor")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("create.status.log.id"),
				propertyData.getProperty("update.notifi.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 201);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateNotification(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatesNotificationPutForCadence1() throws IOException {
		Response response = postAPIRequest.createsNotification(
				payload.createsNotificationPayload(propertyData.getProperty("created.notification.time"),
						propertyData.getProperty("created.notification.mechanism"),
						propertyData.getProperty("created.notification.id"),
						propertyData.getProperty("created.notification.type"),
						propertyData.getProperty("notif.purpose.cadence1"),
						propertyData.getProperty("create.status.time"), propertyData.getProperty("vendor.status"),
						propertyData.getProperty("vendor")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("create.status.log.id"),
				propertyData.getProperty("update.notifi.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 201);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateNotification(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatesNotificationPutForPssConfirmation() throws IOException {
		Response response = postAPIRequest.createsNotification(
				payload.createsNotificationPayload(propertyData.getProperty("created.notification.time"),
						propertyData.getProperty("created.notification.mechanism"),
						propertyData.getProperty("created.notification.id"),
						propertyData.getProperty("created.notification.type"),
						propertyData.getProperty("notif.purpose.pss.confirmation"),
						propertyData.getProperty("create.status.time"), propertyData.getProperty("vendor.status"),
						propertyData.getProperty("vendor")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("create.status.log.id"),
				propertyData.getProperty("update.notifi.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 201);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateNotification(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatesNotificationPutForCadenceCurbs() throws IOException {
		Response response = postAPIRequest.createsNotification(
				payload.createsNotificationPayload(propertyData.getProperty("created.notification.time"),
						propertyData.getProperty("created.notification.mechanism"),
						propertyData.getProperty("created.notification.id"),
						propertyData.getProperty("created.notification.type"),
						propertyData.getProperty("notif.purpose.cadence.curbs"),
						propertyData.getProperty("create.status.time"), propertyData.getProperty("vendor.status"),
						propertyData.getProperty("vendor")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("create.status.log.id"),
				propertyData.getProperty("update.notifi.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 201);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateNotification(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}
