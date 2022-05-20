// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.payload.MfPracticeSettingsManagerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfPracticeSettingsManager;
import io.restassured.response.Response;

public class ApptPrecheckMfPracticeSettingsManagerTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfPracticeSettingsManagerPayload payload;
	public static PostAPIRequestMfPracticeSettingsManager postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();
	public static PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche;
	public static MfAppointmentSchedulerPayload schedulerPayload;
	CommonMethods commonMtd;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfPracticeSettingsManager.getPostAPIRequestMfPracticeSettingsManager();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfPracticeSettingsManagerPayload.getMfPracticeSettingsManagerPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.practice.settings.manager"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.practice.settings.manager"));
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		commonMtd = new CommonMethods();

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultConfirmationSettingGet() throws IOException {
		Response response = postAPIRequest.defaultConfirmationSetting(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDefaultConfirmationSetting(response, propertyData.getProperty("settings.delivery.method"),
				propertyData.getProperty("settings.appt.method"), propertyData.getProperty("settings.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultConfSettingGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest
				.defaultConfSettingWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultConfSettingGetWithoutDeliveryMtd() throws IOException {
		Response response = postAPIRequest.defaultConfSettingWithoutDeliveryMethod(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultReminderSettingGet() throws IOException {
		Response response = postAPIRequest.defaultReminderSetting(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDefaultReminderSetting(response, propertyData.getProperty("settings.delivery.method"),
				propertyData.getProperty("settings.appt.method"), propertyData.getProperty("settings.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultReminderSettingGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest
				.defaultReminderSettingWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultReminderSettingGetWithoutDeliveryMtd() throws IOException {
		Response response = postAPIRequest.defaultReminderSettingWithoutDeliveryMethod(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReminderSettingGet() throws IOException {
		Response getReminderResponse = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(getReminderResponse.getStatusCode(), 200);
		JSONArray arr = new JSONArray(getReminderResponse.getBody().asString());
		String reminderId = arr.getJSONObject(0).getJSONObject("notifySettings").getJSONArray("reminderNotification")
				.getJSONObject(0).getString("id");

		Response response = postAPIRequest.reminderSetting(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("settings.manager.practice.id"), reminderId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReminderSetting(response, propertyData.getProperty("settings.delivery.method"),
				propertyData.getProperty("settings.appt.method"), propertyData.getProperty("settings.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReminderSettingGetWithoutPracticeId() throws IOException {
		Response getReminderResponse = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(getReminderResponse.getStatusCode(), 200);
		JSONArray arr = new JSONArray(getReminderResponse.getBody().asString());
		String reminderId = arr.getJSONObject(0).getJSONObject("notifySettings").getJSONArray("reminderNotification")
				.getJSONObject(0).getString("id");

		Response response = postAPIRequest
				.reminderSettingWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken), reminderId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReminderSettingGetWithoutReminderId() throws IOException {
		Response response = postAPIRequest.reminderSettingWithoutReminderId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrivesSettingsGetForAllPractices() throws IOException {
		Response response = postAPIRequest.retrieveSettingForAllPractices(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrivesSettingsGetForSpecificPractice() throws IOException {
		Response response = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySettingsForSpecificedPractice(response,
				propertyData.getProperty("settings.manager.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrivesSettingsGetForAPractice() throws IOException {
		Response response = postAPIRequest.retrivesSettingsGetForAPractice(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySettingsForAPractice(response, propertyData.getProperty("settings.manager.practice.id"),
				propertyData.getProperty("retrives.system.id"), propertyData.getProperty("practice.display.name"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrivesSettingsGetWithoutAPracticeId() throws IOException {
		Response response = postAPIRequest
				.retrivesSettingsGetWithoutAPracticeId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrivesCadenceGetForAllPractices() throws IOException {
		Response response = postAPIRequest.retrivesCadenceForAllPractices(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "practiceId");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRetrivesCadenceGetForSpecifiedPractice() throws IOException {
		Response response = postAPIRequest.retrivesCadenceForSpecifiedPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCadenceForSpecifiedPractices(response,
				propertyData.getProperty("settings.manager.practice.id"));
		apiVerification.verifyResponseKeys(response, "timing");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateReminderSettingsPut() throws IOException {
		Response getReminderResponse = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(getReminderResponse.getStatusCode(), 200);
		JSONArray arr = new JSONArray(getReminderResponse.getBody().asString());
		String reminderId = arr.getJSONObject(0).getJSONObject("notifySettings").getJSONArray("reminderNotification")
				.getJSONObject(0).getString("id");
		assertEquals(getReminderResponse.getStatusCode(), 200);

		Response response = postAPIRequest.updateReminderSettings(
				payload.getUpdateReminderSettingsPayload(propertyData.getProperty("reminder.timing.days"), 1,
						reminderId, propertyData.getProperty("settings.delivery.method"),
						propertyData.getProperty("settings.appt.method"), propertyData.getProperty("settings.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReminderSettings(response, propertyData.getProperty("settings.manager.practice.id"),
				propertyData.getProperty("retrives.system.id"), reminderId,
				propertyData.getProperty("settings.delivery.method"), propertyData.getProperty("settings.appt.method"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateReminderSettingsPutWithInvalidDeliveryMtd() throws IOException {
		Response getReminderResponse = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(getReminderResponse.getStatusCode(), 200);
		JSONArray arr = new JSONArray(getReminderResponse.getBody().asString());
		String reminderId = arr.getJSONObject(0).getJSONObject("notifySettings").getJSONArray("reminderNotification")
				.getJSONObject(0).getString("id");
		assertEquals(getReminderResponse.getStatusCode(), 200);

		Response response = postAPIRequest.updateReminderSettings(
				payload.getUpdateReminderSettingsPayload(propertyData.getProperty("reminder.timing.days"), 1,
						reminderId, propertyData.getProperty("invalid.delivery.method"),
						propertyData.getProperty("settings.appt.method"), propertyData.getProperty("settings.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReminderSettingsWithInvalidDeliveryMtd(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateReminderSettingsPutWithInvalidApptMtd() throws IOException {
		Response getReminderResponse = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(getReminderResponse.getStatusCode(), 200);
		JSONArray arr = new JSONArray(getReminderResponse.getBody().asString());
		String reminderId = arr.getJSONObject(0).getJSONObject("notifySettings").getJSONArray("reminderNotification")
				.getJSONObject(0).getString("id");
		assertEquals(getReminderResponse.getStatusCode(), 200);

		Response response = postAPIRequest.updateReminderSettings(
				payload.getUpdateReminderSettingsPayload(propertyData.getProperty("reminder.timing.days"), 1,
						reminderId, propertyData.getProperty("settings.delivery.method"),
						propertyData.getProperty("invalid.appt.method"), propertyData.getProperty("settings.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReminderSettingsWithInvalidApptMtd(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateReminderSettingsPutWithInvalidStatus() throws IOException {
		Response getReminderResponse = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		JSONArray arr = new JSONArray(getReminderResponse.getBody().asString());
		String reminderId = arr.getJSONObject(0).getJSONObject("notifySettings").getJSONArray("reminderNotification")
				.getJSONObject(0).getString("id");
		assertEquals(getReminderResponse.getStatusCode(), 200);

		Response response = postAPIRequest.updateReminderSettings(
				payload.getUpdateReminderSettingsPayload(propertyData.getProperty("reminder.timing.days"), 1,
						reminderId, propertyData.getProperty("settings.delivery.method"),
						propertyData.getProperty("settings.appt.method"), propertyData.getProperty("invalid.status")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReminderSettingsWithInvalidStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateSettingsExistingPracticePut() throws IOException {
		Response response;
		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
			response = postAPIRequest.updateSettings(payload.getUpdateSettingsPayload(
					propertyData.getProperty("update.setting.practice"), propertyData.getProperty("update.system.id"),
					propertyData.getProperty("update.location.id"),
					propertyData.getProperty("settings.delivery.method"), propertyData.getProperty("update.language")),
					headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		} else {
			response = postAPIRequest.updateSettings(payload.getUpdateSettingsPayloadDemo(
					propertyData.getProperty("update.setting.practice"), propertyData.getProperty("update.system.id"),
					propertyData.getProperty("update.location.id"),
					propertyData.getProperty("settings.delivery.method"), propertyData.getProperty("update.language")),
					headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		}
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateSettings(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("retrives.system.id"), propertyData.getProperty("expected.location.id"),
				propertyData.getProperty("settings.delivery.method"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateSettingsPutWithInvalidLocationId() throws IOException {
		Response response = postAPIRequest.updateSettings(
				payload.getUpdateSettingsPayload(propertyData.getProperty("update.setting.practice"),
						propertyData.getProperty("update.system.id"), propertyData.getProperty("invalid.location.id"),
						propertyData.getProperty("settings.delivery.method"),
						propertyData.getProperty("update.language")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateSettingsWithInvalidLocationId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateSettingsPutInvalidDeliveryMtd() throws IOException {
		Response response = postAPIRequest.updateSettings(
				payload.getUpdateSettingsPayload(propertyData.getProperty("update.setting.practice"),
						propertyData.getProperty("update.system.id"), propertyData.getProperty("update.location.id"),
						propertyData.getProperty("invalid.update.delivery.method"),
						propertyData.getProperty("update.language")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateSettingsWithInvalidDeliveryMtd(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateSettingsPutInvalidLanguage() throws IOException {
		Response response = postAPIRequest.updateSettings(
				payload.getUpdateSettingsPayload(propertyData.getProperty("update.setting.practice"),
						propertyData.getProperty("update.system.id"), propertyData.getProperty("update.location.id"),
						propertyData.getProperty("settings.delivery.method"),
						propertyData.getProperty("invalid.notification.language")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateSettingsWithInvalidLanguage(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateActiveSettingsTruePut() throws IOException {
		Response response;
		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
			response = postAPIRequest.updateActiveSettings(payload.getUpdateSettingsPayload(
					propertyData.getProperty("update.setting.practice"), propertyData.getProperty("update.system.id"),
					propertyData.getProperty("update.location.id"),
					propertyData.getProperty("settings.delivery.method"), propertyData.getProperty("update.language")),
					headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"),
					propertyData.getProperty("practice.active.setting.true"));
		} else {
			response = postAPIRequest.updateActiveSettings(payload.getUpdateSettingsPayloadDemo(
					propertyData.getProperty("update.setting.practice"), propertyData.getProperty("update.system.id"),
					propertyData.getProperty("update.location.id"),
					propertyData.getProperty("settings.delivery.method"), propertyData.getProperty("update.language")),
					headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"),
					propertyData.getProperty("practice.active.setting.true"));
		}
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyActiveSettings(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("practice.active.setting.true"), propertyData.getProperty("update.system.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateActiveSettingsFalsePut() throws IOException {
		Response response;
		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
			response = postAPIRequest.updateActiveSettings(payload.getUpdateSettingsPayload(
					propertyData.getProperty("update.setting.practice"), propertyData.getProperty("update.system.id"),
					propertyData.getProperty("update.location.id"),
					propertyData.getProperty("settings.delivery.method"), propertyData.getProperty("update.language")),
					headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"),
					propertyData.getProperty("practice.active.setting.false"));
		} else {
			response = postAPIRequest.updateActiveSettings(payload.getUpdateSettingsPayloadDemo(
					propertyData.getProperty("update.setting.practice"), propertyData.getProperty("update.system.id"),
					propertyData.getProperty("update.location.id"),
					propertyData.getProperty("settings.delivery.method"), propertyData.getProperty("update.language")),
					headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"),
					propertyData.getProperty("practice.active.setting.false"));
		}
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyActiveSettings(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("practice.active.setting.false"),
				propertyData.getProperty("update.system.id"));
		testUpdateActiveSettingsTruePut();
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateActiveSettingsPutWithoutActiveValue() throws IOException {
		Response response = postAPIRequest.updateActiveSettingsWithoutActiveValue(
				payload.getUpdateSettingsPayload(propertyData.getProperty("update.setting.practice"),
						propertyData.getProperty("update.system.id"), propertyData.getProperty("update.location.id"),
						propertyData.getProperty("settings.delivery.method"),
						propertyData.getProperty("update.language")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateLocationSettingsPut() throws IOException {
		Response response = postAPIRequest.updateLocationSettings(
				payload.getUpdateLocationSettingPayload(propertyData.getProperty("update.setting.practice"),
						propertyData.getProperty("location.display.name"),
						propertyData.getProperty("location.street.name"), propertyData.getProperty("location.city"),
						propertyData.getProperty("location.state")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyLocationSettings(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("update.system.id"), propertyData.getProperty("location.id"),
				propertyData.getProperty("location.display.name"), propertyData.getProperty("location.street.name"),
				propertyData.getProperty("location.city"), propertyData.getProperty("location.state"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateLocationSettingsPutWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.updateLocationSettings(
				payload.getUpdateLocationSettingPayloadWithoutPracticeId(
						propertyData.getProperty("location.display.name"),
						propertyData.getProperty("location.street.name"), propertyData.getProperty("location.city"),
						propertyData.getProperty("location.state")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateLocationSettingsWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateMerchantSettingsPut() throws IOException {
		Response getReminderResponse = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		JSONArray arr = new JSONArray(getReminderResponse.getBody().asString());
		String merchantId = arr.getJSONObject(0).getJSONObject("merchantSettings").getString("id");
		assertEquals(getReminderResponse.getStatusCode(), 200);

		Response response = postAPIRequest.updateMerchantSettings(
				payload.getUpdateMerchantSettingPayload(merchantId, propertyData.getProperty("merchant.name")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyMerchantSettings(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("update.system.id"), merchantId, propertyData.getProperty("merchant.name"),
				propertyData.getProperty("credit.cards"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateMerchantSettingsPutWithInvalidCreditCard() throws IOException {
		Response getReminderResponse = postAPIRequest.retrieveSettingForSpecificPractice(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("settings.manager.practice.id"));
		log("Verifying the response");
		JSONArray arr = new JSONArray(getReminderResponse.getBody().asString());
		String merchantId = arr.getJSONObject(0).getJSONObject("merchantSettings").getString("id");
		assertEquals(getReminderResponse.getStatusCode(), 200);

		Response response = postAPIRequest.updateMerchantSettings(
				payload.getUpdateMerchantSettingPayloadWithInvalidCard(merchantId,
						propertyData.getProperty("merchant.name")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyMerchantSettingsWithInvalidCreditCard(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateMerchantSettingsPutWithoutIdAndName() throws IOException {
		Response response = postAPIRequest.updateMerchantSettings(
				payload.getUpdateMerchantSettingPayloadWithoutIdName(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateNotifySettingsPut() throws IOException {
		Response response = postAPIRequest.updateNotifySettings(payload.getUpdateNotifySettingPayload(
				propertyData.getProperty("notify.enabled"), propertyData.getProperty("notify.enabled.by.practice"),
				propertyData.getProperty("notify.one.day.out"), propertyData.getProperty("notify.three.days.out"),
				propertyData.getProperty("notify.five.days.out"), propertyData.getProperty("reminder.id"),
				propertyData.getProperty("notify.delivery.method"), propertyData.getProperty("notify.appt.method"),
				propertyData.getProperty("notify.status")), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotifySettings(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("update.system.id"), propertyData.getProperty("notify.enabled"),
				propertyData.getProperty("notify.enabled.by.practice"), propertyData.getProperty("notify.one.day.out"),
				propertyData.getProperty("notify.three.days.out"), propertyData.getProperty("notify.five.days.out"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateNotifySettingsPutWithInvalidDeliveryMtd() throws IOException {
		Response response = postAPIRequest.updateNotifySettings(payload.getUpdateNotifySettingPayload(
				propertyData.getProperty("notify.enabled"), propertyData.getProperty("notify.enabled.by.practice"),
				propertyData.getProperty("notify.one.day.out"), propertyData.getProperty("notify.three.days.out"),
				propertyData.getProperty("notify.five.days.out"), propertyData.getProperty("reminder.id"),
				propertyData.getProperty("invalid.delivery.method"), propertyData.getProperty("notify.appt.method"),
				propertyData.getProperty("notify.status")), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotifySettingsWithInvalidDeliveryMtd(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdatePmIntegrationSettingsPut() throws IOException {
		Response response = postAPIRequest.updatePmIntegrationSettings(
				payload.getUpdatePmIntegrationSettingPayload(propertyData.getProperty("update.system.id"),
						propertyData.getProperty("data.sync.enabled")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPmIntegrationSettings(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("update.system.id"), propertyData.getProperty("data.sync.enabled"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdatePmIntegrationSettingsPutWithoutPracticeId() throws IOException {
		Response response = postAPIRequest
				.updatePmIntegrationSettingsWithoutPracticeID(
						payload.getUpdatePmIntegrationSettingPayload(propertyData.getProperty("update.system.id"),
								propertyData.getProperty("data.sync.enabled")),
						headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotifySettingsWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdatePmIntegrationSettingsPutWithoutDataSyncEnabled() throws IOException {
		Response response = postAPIRequest.updatePmIntegrationSettings(
				payload.getUpdatePmIntegrationSettingPayloadWithoutDataSyncEnabled(
						propertyData.getProperty("update.system.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyNotifySettingsWithoutDataSyncEnabled(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdatePrecheckSettingsPut() throws IOException {
		Response response = postAPIRequest.updatePrecheckSettings(payload.getUpdatePrecheckSettingPayload(
				propertyData.getProperty("precheck.enabled"), propertyData.getProperty("precheck.disable.demographics"),
				propertyData.getProperty("precheck.insurance.settings"),
				propertyData.getProperty("precheck.text.entry.enabled"),
				propertyData.getProperty("precheck.ocr.enabled"), propertyData.getProperty("precheck.disable.copay"),
				propertyData.getProperty("precheck.disable.balance"),
				propertyData.getProperty("precheck.enable.patient.mode"),
				propertyData.getProperty("precheck.enable.forms")), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPrecheckSettings(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("precheck.enabled"), propertyData.getProperty("precheck.disable.demographics"),
				propertyData.getProperty("precheck.insurance.settings"),
				propertyData.getProperty("precheck.text.entry.enabled"),
				propertyData.getProperty("precheck.ocr.enabled"), propertyData.getProperty("precheck.disable.copay"),
				propertyData.getProperty("precheck.disable.balance"),
				propertyData.getProperty("precheck.enable.patient.mode"),
				propertyData.getProperty("precheck.enable.forms"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdatePrecheckSettingsPutWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.updatePrecheckSettingsWithoutPracticeId(
				payload.getUpdatePrecheckSettingPayload(propertyData.getProperty("precheck.enabled"),
						propertyData.getProperty("precheck.disable.demographics"),
						propertyData.getProperty("precheck.insurance.settings"),
						propertyData.getProperty("precheck.text.entry.enabled"),
						propertyData.getProperty("precheck.ocr.enabled"),
						propertyData.getProperty("precheck.disable.copay"),
						propertyData.getProperty("precheck.disable.balance"),
						propertyData.getProperty("precheck.enable.patient.mode"),
						propertyData.getProperty("precheck.enable.forms")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdatePrecheckSettingWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdatePssSettingsPut() throws IOException {
		Response response = postAPIRequest.updatePssSettings(
				payload.getUpdatePssSettingPayload(testData.isPssSetting()),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("update.setting.practice"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdatePssSetting(response, propertyData.getProperty("update.setting.practice"),
				propertyData.getProperty("update.system.id"), testData.isPssSetting());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdatePssSettingsPutWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.updatePssSettingsWithoutPracticeId(
				payload.getUpdatePssSettingPayload(testData.isPssSetting()),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateSettingsPost() throws IOException {
		Response response = postAPIRequest.createSettings(
				payload.getCreateSettingPayload(propertyData.getProperty("create.setting.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Create Practice setting");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyCreateSetting(response, propertyData.getProperty("create.setting.practice.id"));
		} else {
			log("Settings for the practice already exists");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifySettingIfAlreadyExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSettingsForAPracticeGet() throws IOException {
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

		Response response = postAPIRequest.getSettingsForAPractice(
				propertyData.getProperty("baseurl.apt-precheck.resource"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.pre.check.practice.id"), Appointment.patientId, Appointment.apptId,
				payload.getGuestTokenPayload(propertyData.getProperty("patient.dob"),
						propertyData.getProperty("zipcode")));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);

		apiVerification.verifyGetSettingsForAPractice(response, propertyData.getProperty("apt.pre.check.practice.id"),
				propertyData.getProperty("update.system.id"));
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}
