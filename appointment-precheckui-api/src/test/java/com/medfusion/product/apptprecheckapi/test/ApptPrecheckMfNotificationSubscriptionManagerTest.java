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
import com.medfusion.product.appt.precheck.payload.MfNotificationSubscriptionManagerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfNotificationSubscriptionManager;

import io.restassured.response.Response;

public class ApptPrecheckMfNotificationSubscriptionManagerTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfNotificationSubscriptionManagerPayload payload;
	public static PostAPIRequestMfNotificationSubscriptionManager postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfNotificationSubscriptionManager
				.getPostAPIRequestMfNotificationSubscriptionManager();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfNotificationSubscriptionManagerPayload.getMfNotificationSubscriptionManagerPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.notif.subscription.manager"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.notif.subscription.manager"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteAllSubscriptionDataUsingEmailId() throws IOException {
		Response response = postAPIRequest.deleteAllSubscriptionDataUsingEmailId(propertyData.getProperty("baseurl.mf.notif.subscription.manager"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notif.subs.email.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteAllSubscriptionDataWithoutEmailId() throws IOException {
		Response response = postAPIRequest
				.deleteAllSubsDataWithoutEmailId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteAllSubscriptionDataUsingEmailIdAndTypeId() throws IOException {
		Response response = postAPIRequest.deleteAllSubsDataUsingEmailIdAndTypeId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notif.subs.email.id"),
				propertyData.getProperty("practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteAllSubsDataWithoutEmailId() throws IOException {
		Response response = postAPIRequest.deleteAllSubsDataWithoutEmailId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteAllSubsDataWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.deleteAllSubsDataWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notif.subs.email.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteSubsDataUsingEmailIdAndResourceIdForApptScheConfiType() throws IOException {
		Response response = postAPIRequest.deleteSubsDataUsingEmailIdAndResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notif.subs.email.id"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.scheduled.confirmation.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteSubsDataUsingEmailIdAndResourceIdForApptDefaultType() throws IOException {
		Response response = postAPIRequest.deleteSubsDataUsingEmailIdAndResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notif.subs.email.id"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.default.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteSubsDataUsingEmailIdAndResourceIdForBroadcastApptType() throws IOException {
		Response response = postAPIRequest.deleteSubsDataUsingEmailIdAndResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notif.subs.email.id"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("broadcast.appt.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteSubsDataUsingEmailIdAndResourceIdForApptCheckInType() throws IOException {
		Response response = postAPIRequest.deleteSubsDataUsingEmailIdAndResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("notif.subs.email.id"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.check.in.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteSubsDataWithoutApptType() throws IOException {
		Response response = postAPIRequest.deleteSubsDataWithoutApptType(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("notif.subs.email.id"), propertyData.getProperty("practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteSubsDataWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.deleteSubsDataWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("notif.subs.email.id"), propertyData.getProperty("appt.check.in.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubscriptionDataIdGet() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsDataId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyRetrieveAllSubscriptionData(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("notification.resource"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"));
		apiVerification.responseKeyValidation(response, "type");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetWithoutResourceId() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsDataWithoutResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("retrieve.subs.data.email"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetWithoutEmailId() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsDataWithoutEmailId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveSubsDataGetUsingEmailIdAndResourceIdForApptScheConf() throws IOException {
		Response response = postAPIRequest.retrieveSubsDataUsingEmailIdAndResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("retrieve.subs.data.email"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.scheduled.confirmation.type"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyRetrieveSubsDataGetUsingEmailIdAndResourceId(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("notification.resource"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
				propertyData.getProperty("appt.scheduled.confirmation.type"));
	} else {
		assertEquals(response.getStatusCode(), 404);
		log("Did not find unsubscribed data");
		apiVerification.verifyRetrieveSubsDataIfNotUnsubscribe(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("practice.id"),
				propertyData.getProperty("appt.scheduled.confirmation.type"));
	}
	apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveSubsDataGetUsingEmailIdAndResourceIdForApptDefault() throws IOException {
		Response response = postAPIRequest.retrieveSubsDataUsingEmailIdAndResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("retrieve.subs.data.email"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.default.type"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyRetrieveSubsDataGetUsingEmailIdAndResourceId(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("notification.resource"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
				propertyData.getProperty("appt.default.type"));
	} else {
		assertEquals(response.getStatusCode(), 404);
		log("Did not find unsubscribed data");
		apiVerification.verifyRetrieveSubsDataIfNotUnsubscribe(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("practice.id"),
				propertyData.getProperty("appt.default.type"));
	}
	apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveSubsDataGetUsingEmailIdAndResourceIdForBroadcastAppointment() throws IOException {
		Response response = postAPIRequest.retrieveSubsDataUsingEmailIdAndResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("retrieve.subs.data.email"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("broadcast.appt.type"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyRetrieveSubsDataGetUsingEmailIdAndResourceId(response,
					propertyData.getProperty("retrieve.subs.data.email"),
					propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
					propertyData.getProperty("notification.mechanism"),
					propertyData.getProperty("broadcast.appt.type"));
		} else {
			assertEquals(response.getStatusCode(), 404);
			log("Did not find unsubscribed data");
		apiVerification.verifyRetrieveSubsDataIfNotUnsubscribe(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("practice.id"),
				propertyData.getProperty("broadcast.appt.type"));
	}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveSubsDataGetUsingEmailIdAndResourceIdForApptCheckIn() throws IOException {
		Response response = postAPIRequest.retrieveSubsDataUsingEmailIdAndResourceId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("retrieve.subs.data.email"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.check.in.type"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyRetrieveSubsDataGetUsingEmailIdAndResourceId(response,
					propertyData.getProperty("retrieve.subs.data.email"),
					propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
					propertyData.getProperty("notification.mechanism"),
					propertyData.getProperty("appt.check.in.type"));
		} else {
			assertEquals(response.getStatusCode(), 404);
			log("Did not find unsubscribed data");
		apiVerification.verifyRetrieveSubsDataIfNotUnsubscribe(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("practice.id"),
				propertyData.getProperty("appt.check.in.type"));
	}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveSubsDataGetWithoutApptType() throws IOException {
		Response response = postAPIRequest.retrieveSubsDataWithoutApptType(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("notif.subs.email.id"), propertyData.getProperty("practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveSubsDataGetWithoutEmailId() throws IOException {
		Response response = postAPIRequest.retrieveSubsDataWithoutEmailId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.check.in.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetUsingTypeId() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsDataUsingTypeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("practice.id"));
		log("Verifying the response");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.responseTimeValidation(response);
			apiVerification.verifyRetrieveAllSubscriptionData(response,
					propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("notification.resource"),
					propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"));
			apiVerification.responseKeyValidation(response, "type");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest
				.retrieveAllSubsDataWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetUsingTypeIdForApptScheConfType() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsnDataUsingTypeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.scheduled.confirmation.type"));
		log("Verifying the response");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.responseTimeValidation(response);
			apiVerification.verifyRetrieveAllSubsnDataUsingTypeId(response,
					propertyData.getProperty("retrieve.subs.data.email"),
					propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
					propertyData.getProperty("notification.mechanism"),
					propertyData.getProperty("appt.scheduled.confirmation.type"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetUsingTypeIdForApptDefaultType() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsnDataUsingTypeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.default.type"));
		log("Verifying the response");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.responseTimeValidation(response);
			apiVerification.verifyRetrieveAllSubsnDataUsingTypeId(response,
					propertyData.getProperty("retrieve.subs.data.email"),
					propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
					propertyData.getProperty("notification.mechanism"), propertyData.getProperty("appt.default.type"));

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetUsingTypeIdForBroadcastAppointmentType() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsnDataUsingTypeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("practice.id"), propertyData.getProperty("broadcast.appt.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyRetrieveAllSubsnDataUsingTypeId(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("notification.resource"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
				propertyData.getProperty("broadcast.appt.type"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetUsingTypeIdForApptCheckInType() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsnDataUsingTypeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("practice.id"), propertyData.getProperty("appt.check.in.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyRetrieveAllSubsnDataUsingTypeId(response,
				propertyData.getProperty("retrieve.subs.data.email"), propertyData.getProperty("notification.resource"),
				propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
				propertyData.getProperty("appt.check.in.type"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetWithoutApptType() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsnDataWithoutApptType(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrieveAllSubsDataGetWithoutPracticeType() throws IOException {
		Response response = postAPIRequest.retrieveAllSubsnDataWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("broadcast.appt.type"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithApptScheduledConfirmationType() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
						propertyData.getProperty("appt.scheduled.confirmation.type"),
						propertyData.getProperty("notification.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithApptCheckInType() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
						propertyData.getProperty("appt.check.in.type"),
						propertyData.getProperty("notification.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithApptDefaultType() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
						propertyData.getProperty("appt.default.type"),
						propertyData.getProperty("notification.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithBroadcastAppointmentType() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
						propertyData.getProperty("broadcast.appt.type"),
						propertyData.getProperty("notification.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithInvalidEmailId() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubscriptionDataPayload(propertyData.getProperty("invalid.notif.subs.email.id"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
						propertyData.getProperty("broadcast.appt.type"),
						propertyData.getProperty("notification.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveSubsDataWithInvalidEmailId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithInvalidResource() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("notif.subs.system.id"), propertyData.getProperty("invalid.resource"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("broadcast.appt.type"),
						propertyData.getProperty("notification.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveSubsDataWithInvalidResource(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithInvalidApptType() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
						propertyData.getProperty("invalid.appt.type"),
						propertyData.getProperty("notification.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveSubsDataWithInvalidApptType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithInvalidMechanism() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
						propertyData.getProperty("appt.default.type"), propertyData.getProperty("invalid.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveSubsDataWithInvalidMechanism(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllSubsDataPostWithApptScheduledConfirmationType() throws IOException {
		Response response = postAPIRequest.saveAllSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesAllSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
						propertyData.getProperty("notification.resource"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("appt.scheduled.confirmation.type")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllSubsDataPostWithApptCheckInType() throws IOException {
		Response response = postAPIRequest.saveAllSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesAllSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
						propertyData.getProperty("notification.resource"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("appt.check.in.type")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllSubsDataPostWithApptDefaultType() throws IOException {
		Response response = postAPIRequest.saveAllSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesAllSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
						propertyData.getProperty("notification.resource"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("appt.default.type")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllSubsDataPostWithBroadcastAppointmentType() throws IOException {
		Response response = postAPIRequest.saveAllSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesAllSubscriptionDataPayload(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
						propertyData.getProperty("notification.resource"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("broadcast.appt.type")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllSubsDataPostWithInvalidEmailId() throws IOException {
		Response response = postAPIRequest.saveAllSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesAllSubscriptionDataPayload(propertyData.getProperty("invalid.notif.subs.email.id"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
						propertyData.getProperty("notification.resource"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("broadcast.appt.type")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveAllSubsDataWithInvalidEmailId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllSubsDataPostWithoutSystemId() throws IOException {
		Response response = postAPIRequest.saveAllSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesAllSubsDataPayloadWithoutSystemId(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
						propertyData.getProperty("notification.resource"),
						propertyData.getProperty("broadcast.appt.type")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveAllSubsDataWithoutSystemId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllSubsDataPostWithoutResource() throws IOException {
		Response response = postAPIRequest.saveAllSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesAllSubsDataPayloadWithoutResource(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
						propertyData.getProperty("notif.subs.system.id"),
						propertyData.getProperty("broadcast.appt.type")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveAllSubsDataWithoutResource(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveAllSubsDataPostWithoutApptType() throws IOException {
		Response response = postAPIRequest.saveAllSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesAllSubsDataPayloadWithoutApptType(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("practice.id"), propertyData.getProperty("notification.mechanism"),
						propertyData.getProperty("notification.resource"),
						propertyData.getProperty("notif.subs.system.id")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveAllSubsDataWithoutApptType(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveSubsDataPostWithoutSystemId() throws IOException {
		Response response = postAPIRequest.saveSubsDataWithApptType(headerConfig.HeaderwithToken(getaccessToken),
				payload.getSavesSubsDataPayloadWithInvalidSystemId(propertyData.getProperty("notif.subs.email.id"),
						propertyData.getProperty("notification.resource"), propertyData.getProperty("practice.id"),
						propertyData.getProperty("appt.default.type"),
						propertyData.getProperty("notification.mechanism")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveSubsDataWithoutSystemId(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}
