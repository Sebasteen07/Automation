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
import com.medfusion.product.appt.precheck.payload.AptReminderSchedulerPayload;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptReminderScheduler;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;

import io.restassured.response.Response;

public class ApptPrecheckAptReminderSchedulerTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptReminderSchedulerPayload payload;
	public static PostAPIRequestAptReminderScheduler postAPIRequest;
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
		postAPIRequest = PostAPIRequestAptReminderScheduler.getPostAPIRequestAptReminderScheduler();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptReminderSchedulerPayload.getAptReminderSchedulerPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		commonMtd = new CommonMethods();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.reminder.scheduler"));
		log("BASE URL-" + propertyData.getProperty("baseurl.apt.reminder.scheduler"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptMetadataPut() throws IOException, InterruptedException {
		Response response = postAPIRequest.updateApptMetadata(
				payload.getUpdateApptMetadataPayload(propertyData.getProperty("update.appt.metadata.practice.id"),
						Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"),
						propertyData.getProperty("update.appt.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateApptMetadata(response, propertyData.getProperty("update.appt.metadata.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptMetadataPutWithInvalidPracticeId() throws IOException {
		Response response = postAPIRequest
				.updateApptMetadata(
						payload.getUpdateApptMetadataPayload(propertyData.getProperty("update.invalid.practice.id"),
								Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"),
								propertyData.getProperty("update.appt.status")),
						headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateApptMetadataInvalidWithPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptMetadataPutWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.updateApptMetadata(
				payload.getUpdateApptMetadataPayloadWithoutPracticeId(
						propertyData.getProperty("update.invalid.practice.id"), Appointment.apptId,
						propertyData.getProperty("update.appt.type"), propertyData.getProperty("update.appt.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateApptMetadataWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMetadataForPracticeApptsGet() throws IOException {
		Response response = postAPIRequest.metadataForPracticeAppts(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "practiceId");
		apiVerification.responseKeyValidation(response, "pmPatientId");
		apiVerification.responseKeyValidation(response, "pmAppointmentId");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMetadataForPracticeApptsGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest
				.metadataForPracticeApptsWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMetadataForApptsGet() throws IOException {
		log("Save Appointment Metadata");
		postAPIRequest
				.saveApptMetadata(
						payload.getSaveApptMetadataPayload(propertyData.getProperty("update.appt.metadata.practice.id"),
								Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"),
								propertyData.getProperty("update.appt.status")),
						headerConfig.HeaderwithToken(getaccessToken));
		log("Get Appointment Metadata");
		Response response = postAPIRequest.metadataForAppts(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId,
				Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyMetadataForAppts(response, propertyData.getProperty("update.appt.metadata.practice.id"),
				Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMetadataForApptsGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.metadataForApptsWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMetadataForApptsGetWithoutApptId() throws IOException {
		Response response = postAPIRequest.metadataForApptsWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptMetadata() throws IOException {
		log("Save Appointment Metadata");
		postAPIRequest
				.saveApptMetadata(
						payload.getSaveApptMetadataPayload(propertyData.getProperty("update.appt.metadata.practice.id"),
								Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"),
								propertyData.getProperty("update.appt.status")),
						headerConfig.HeaderwithToken(getaccessToken));
		log("Delete Appointment Metadata");
		Response response = postAPIRequest.deleteApptMetadata(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId,
				Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyDeleteApptMetadata(response, propertyData.getProperty("update.appt.metadata.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptMetadataWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.deleteApptMetadataWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptMetadataWithoutApptId() throws IOException {
		Response response = postAPIRequest.deleteApptMetadataWithoutApptId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptsReminders() throws IOException {
		log("Save Appointment Metadata");
		postAPIRequest
				.saveApptMetadata(
						payload.getSaveApptMetadataPayload(propertyData.getProperty("update.appt.metadata.practice.id"),
								Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"),
								propertyData.getProperty("update.appt.status")),
						headerConfig.HeaderwithToken(getaccessToken));
		log("Delete Appointment Metadata");
		Response response = postAPIRequest.deleteApptReminder(
				payload.getDeleteApptsRemindersPayload(Appointment.apptId, Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteApptsRemindersWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.deleteApptReminderWithoutPracticeId(
				payload.getDeleteApptsRemindersPayload(Appointment.apptId, Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleRemindersPut() throws IOException {
		Response response = postAPIRequest.scheduleReminder(
				payload.getScheduleRemindersPayload(Appointment.apptId, Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyScheduleReminders(response, propertyData.getProperty("update.appt.metadata.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleRemindersPutWithInvalidApptId() throws IOException {
		Response response = postAPIRequest.scheduleReminder(
				payload.getScheduleRemindersPayload(propertyData.getProperty("schedule.reminders.invalid.appt.id"),
						Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyScheduleRemindersWithInvalidData(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleRemindersPutWithoutPatientId() throws IOException {
		Response response = postAPIRequest.scheduleReminder(
				payload.getScheduleRemindersPayloadWithoutPatientId(Appointment.apptId),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyScheduleRemindersWithInvalidData(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleReminderForApptMetadataPut() throws IOException {
		Response response = postAPIRequest.scheduleReminderForApptMetadata(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId,
				Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyReminderForApptMetadata(response,
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("update.appt.type"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleReminderForApptMetadataPutWithoutApptid() throws IOException {
		Response response = postAPIRequest.scheduleReminderForApptMetadataWithoutApptId(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleReminderForApptMetadataPutWithoutPatientId() throws IOException {
		Response response = postAPIRequest.scheduleReminderForApptMetadataWithoutPatientId(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptMetadataPost() throws IOException {
		log("Delete Appointment Metadata");
		postAPIRequest.deleteApptMetadata(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId,
				Appointment.apptId);
		log("Save Appointment Metadata");
		Response response = postAPIRequest
				.saveApptMetadata(
						payload.getSaveApptMetadataPayload(propertyData.getProperty("update.appt.metadata.practice.id"),
								Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"),
								propertyData.getProperty("update.appt.status")),
						headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifySaveApptMetadata(response, propertyData.getProperty("update.appt.metadata.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptMetadataPostInvalidStatus() throws IOException {
		Response response = postAPIRequest.saveApptMetadata(
				payload.getSaveApptMetadataPayload(propertyData.getProperty("update.appt.metadata.practice.id"),
						Appointment.patientId, Appointment.apptId, propertyData.getProperty("update.appt.type"),
						propertyData.getProperty("save.appt.invalid.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveApptMetadataWithInvalidStatus(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptMetadataPostWithoutFilter() throws IOException {
		Response response = postAPIRequest.saveApptMetadata(payload.getSaveApptMetadataPayloadWithoutFilter(
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("update.appt.type"), propertyData.getProperty("update.appt.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveApptMetadataWithoutFilter(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptMetadataPostWithoutTime() throws IOException {
		Response response = postAPIRequest.saveApptMetadata(payload.getSaveApptMetadataPayloadWithoutTime(
				propertyData.getProperty("update.appt.metadata.practice.id"), Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("update.appt.type"), propertyData.getProperty("update.appt.status")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifySaveApptMetadataWithoutTime(response);
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
