package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.AptPrecheckPayload;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPrecheck;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AptPrecheckTests extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptPrecheckPayload payload;
	public static MfAppointmentSchedulerPayload schedulerPayload;
	public static PostAPIRequestAptPrecheck postAPIRequest;
	public static PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();
	AptPrecheckMfAppointmentSchedulerTests apptScheduler;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptPrecheck.getPostAPIRequestAptPrecheck();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptPrecheckPayload.getAptPrecheckPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		apptScheduler = new AptPrecheckMfAppointmentSchedulerTests();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.precheck"));
		log("BASE URL-" + propertyData.getProperty("baseurl.apt.precheck"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointments() throws IOException {
		Response response = postAPIRequest.getAppointments(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id.new"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptData(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.patient.id.new"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsAppId() throws IOException {
		Response response = postAPIRequest.getAppointmentsAppId(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id.new"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPerticularIdApptData(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.patient.id.new"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsStartDay() throws IOException {
		Response response = postAPIRequest.getAppointmentsStartDay(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.appts.start.day"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptDataForStartDay(response, propertyData.getProperty("apt.precheck.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTReturnsPageOfAppointments() throws IOException {
		Response response = postAPIRequest.aptPostPracticeId(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getPracticeIdPayload(propertyData.getProperty("apt.precheck.practice.appt.date.range.start"),
						propertyData.getProperty("apt.precheck.practice.appt.date.range.end")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsBasedOnPaging(response, propertyData.getProperty("apt.precheck.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTReturnsPageOfAppointmentsWithInvalidDateRange() throws IOException {
		Response response = postAPIRequest.aptPostPracticeId(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getPracticeIdWithInvalidDateRange(
						propertyData.getProperty("apt.precheck.practice.invalid.date.range"),
						propertyData.getProperty("apt.precheck.practice.appt.date.range.end")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsWithInvalidDateRange(response,
				propertyData.getProperty("apt.precheck.practice.invalid.date.range"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTReturnsCurbSidePageOfAppointments() throws IOException {
		Response response = postAPIRequest.aptPostArrivals(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getArrivalsPayload(propertyData.getProperty("apt.precheck.arrivals.appt.date.range.start"),
						propertyData.getProperty("apt.precheck.arrivals.appt.date.range.end")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsBasedOnPaging(response, propertyData.getProperty("apt.precheck.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTReturnsCurbSidePageOfApptsWithInvalidDateRange() throws IOException {
		Response response = postAPIRequest.aptPostArrivals(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getArrivalsPayload(propertyData.getProperty("apt.precheck.practice.invalid.date.range"),
						propertyData.getProperty("apt.precheck.arrivals.appt.date.range.end")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsWithInvalidDateRange(response,
				propertyData.getProperty("apt.precheck.practice.invalid.date.range"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTHistoryMessage() throws IOException {
		Response response = postAPIRequest.aptPostHistoryMessage(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getHistoryMessage(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMessageHistory(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTMessageHistoryBroadcastForEmail() throws IOException {
		Response response = postAPIRequest.aptMessageHistory(propertyData.getProperty("post.daily.start.date"),
				propertyData.getProperty("message.history.email.midium"),
				propertyData.getProperty("message.history.broadcast.type"),
				propertyData.getProperty("apt.precheck.practice.id"), payload.getMessageHistoryPayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMessageHistoryForAppt(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTMessageHistoryBroadcastForText() throws IOException {
		Response response = postAPIRequest.aptMessageHistory(propertyData.getProperty("post.daily.start.date"),
				propertyData.getProperty("message.history.text.midium"),
				propertyData.getProperty("message.history.broadcast.type"),
				propertyData.getProperty("apt.precheck.practice.id"), payload.getMessageHistoryPayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMessageHistoryForAppt(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTMessageHistoryCheckInForEmail() throws IOException {
		Response response = postAPIRequest.aptMessageHistory(propertyData.getProperty("post.daily.start.date"),
				propertyData.getProperty("message.history.email.midium"),
				propertyData.getProperty("message.history.checkin.type"),
				propertyData.getProperty("apt.precheck.practice.id"), payload.getMessageHistoryPayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMessageHistoryForAppt(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTMessageHistoryCheckInForText() throws IOException {
		Response response = postAPIRequest.aptMessageHistory(propertyData.getProperty("post.daily.start.date"),
				propertyData.getProperty("message.history.text.midium"),
				propertyData.getProperty("message.history.checkin.type"),
				propertyData.getProperty("apt.precheck.practice.id"), payload.getMessageHistoryPayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMessageHistoryForAppt(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETFormInformation() throws IOException {
		Response response = postAPIRequest.getFormInformation(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("form.info.patient.id"),
				propertyData.getProperty("form.info.appt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyFormInformation(response, propertyData.getProperty("form.info.patirnt.first.name"),
				propertyData.getProperty("form.info.patirnt.last.name"),
				propertyData.getProperty("form.info.patirnt.birth.date"),
				propertyData.getProperty("form.info.email.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETFormInformWithInvalidPatientToken() throws IOException {
		Response response = postAPIRequest.getFormInfoWithInvalidPatientToken(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("form.info.patient.id"), propertyData.getProperty("form.info.appt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyFormInfoWithInvalidPatientToken(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBroadcastMessage() throws IOException, InterruptedException {
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);

		log("Schedule a new Appointment");
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("appt.scheduler.patient.id"),
				propertyData.getProperty("appt.scheduler.appt.id"));

		Response response = postAPIRequest.aptBroadcastMessage(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getBroadcastMessagePayload(propertyData.getProperty("apt.precheck.broadcast.date.range.start"),
						propertyData.getProperty("apt.precheck.broadcast.date.range.end"),
						propertyData.getProperty("appt.scheduler.patient.id"),
						propertyData.getProperty("appt.scheduler.appt.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "success");
		apiVerification.responseKeyValidationJson(response, "fail");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCheckinActions() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		String apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),schedulerPayload.putAppointmentPayload(plus20Minutes,
				propertyData.getProperty("mf.apt.scheduler.phone"),propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);

		Response actionResponse = postAPIRequest.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response CheckInResponse = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(CheckInResponse.getStatusCode(), 200);

		Response arrivalResponse = postAPIRequest.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		Response response = postAPIRequest.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),payload.getCheckinActionsPayload(apptId, patientId,
				propertyData.getProperty("apt.precheck.practice.id")),headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCheckInAppointments(response, propertyData.getProperty("apt.precheck.practice.id"),
				patientId, apptId, "CHECKIN");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCheckinActionsWithInvalidPatientId() throws IOException {
		Response response = postAPIRequest.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.getCheckinActionsPayload(propertyData.getProperty("apt.precheck.copay.skip.appointment.id"),
						propertyData.getProperty("checkin.actions.with.invalid.patient.id"),
						propertyData.getProperty("apt.precheck.balance.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCheckinActionsWithInvalidId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCheckinActionsWithInvalidApptId() throws IOException {
		Response response = postAPIRequest.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.getCheckinActionsPayload(propertyData.getProperty("checkin.actions.with.invalid.appt.id"),
						propertyData.getProperty("checkin.actions.with.invalid.patient.id"),
						propertyData.getProperty("apt.precheck.balance.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCheckinActionsWithInvalidId(response);
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
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.getDELETEAppointmentsFromDbPayload(propertyData.getProperty("delete.appt.db.start.date"),
						propertyData.getProperty("delete.appt.db.end.date")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEApptsFromDbWithSelectAllFalse() throws IOException {
		log("Schedule a new Appointments");
		for (int i = 1; i <= 5; i++) {
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
			log("Getting patients since timestamp: " + plus20Minutes);
			log("Schedule new Appointment");
			postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("mf.apt.scheduler.practice.id"),
					schedulerPayload.putAppointmentPayload(plus20Minutes,
							propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(getaccessToken),
					propertyData.getProperty("apt.precheck.select.all.false.patient.id") + i,
					propertyData.getProperty("apt.precheck.select.all.false.appt.id") + i);
		}
		Response response = postAPIRequest.getDELETEAppointmentsFromDb(
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.getDELETEApptsFromDbWithSelectAllFalsePayload(), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDeleteApptWithSelectAllFalse(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEApptsFromDbWithSelectAllTrue() throws IOException {
		log("Schedule new Appointments");
		for (int i = 1; i <= 15; i++) {
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
			log("Getting patients since timestamp: " + plus20Minutes);
			log("Schedule a new Appointment");
			postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("mf.apt.scheduler.practice.id"),
					schedulerPayload.putAppointmentPayload(plus20Minutes,
							propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(getaccessToken),
					propertyData.getProperty("apt.precheck.select.all.false.patient.id") + i,
					propertyData.getProperty("apt.precheck.select.all.false.appt.id") + i);
		}

		Response response = postAPIRequest.getDELETEAppointmentsFromDb(
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.getDELETEApptsFromDbWithSelectAllTruePayload(), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETPatientsIdentification() throws IOException {
		Response response = postAPIRequest.getPatientsIdentification(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.patient.id.new"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPatientsIdentification(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.integration.id"),
				propertyData.getProperty("apt.precheck.patient.id.new"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPrecheckAppt() throws IOException {
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptpostAppointments(propertyData.getProperty("apt.precheck.practice.id"),
				payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"), plus20Minutes),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.integration.id"),
				propertyData.getProperty("apt.precheck.create.patient.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointments(response, propertyData.getProperty("apt.precheck.practice.id"),
				propertyData.getProperty("apt.precheck.integration.id"),
				propertyData.getProperty("apt.precheck.create.patient.id"),
				propertyData.getProperty("precheck.appt.first.name"),
				propertyData.getProperty("precheck.appt.last.name"),
				propertyData.getProperty("precheck.appt.birth.date"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPrecheckApptWithoutTime() throws IOException {
		Response response = postAPIRequest.aptpostAppointments(propertyData.getProperty("apt.precheck.practice.id"),
				payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"), 0),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.integration.id"),
				propertyData.getProperty("apt.precheck.create.patient.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyprecheckApptWithoutTime(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPrecheckApptWithoutName() throws IOException {
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptpostAppointments(propertyData.getProperty("apt.precheck.practice.id"),
				payload.precheckApptPayloadWithoutName(plus20Minutes), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.integration.id"),
				propertyData.getProperty("apt.precheck.create.patient.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPrecheckApptWithoutPhone() throws IOException {
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptpostAppointments(propertyData.getProperty("apt.precheck.practice.id"),
				payload.precheckApptPayloadWithoutPhone(plus20Minutes), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.integration.id"),
				propertyData.getProperty("apt.precheck.create.patient.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyprecheckApptWithoutPhone(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopaySkip() throws IOException, InterruptedException {
		log("Schedule a new Appointment");
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));
		Response response = postAPIRequest.aptpostCopaySkip(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		log("Verifying the response");
		log("Copay skipped");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				propertyData.getProperty("appt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopaySkipIncorrectPatientId() throws IOException {
		Response response = postAPIRequest.aptpostCopaySkipIncorrectPatientId(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalanceSkip() throws IOException {
		log("Schedule a new Appointment");
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		Response response = postAPIRequest.aptPostBalanceSkip(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("appt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalanceSkipPay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				propertyData.getProperty("appt.precheck.copay.skip.patient.id"),
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"),
				propertyData.getProperty("apt.precheck.balance.skip.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalanceSkipIncorrectPatientId() throws IOException {
		Response response = postAPIRequest.aptpostBalanceSkipIncorrectPatientId(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"),
				propertyData.getProperty("apt.precheck.copay.skip.appointment.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTForms() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		String apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),schedulerPayload.putAppointmentPayload(plus20Minutes,
				propertyData.getProperty("mf.apt.scheduler.phone"),propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);

		Response response = postAPIRequest.aptPutForms(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getFormsPayload(), headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);

		log("Verify response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutForms(response, propertyData.getProperty("apt.precheck.practice.id"), patientId,
				apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETRequiredForms() throws IOException {
		Response response = postAPIRequest.getRequiredForms(propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id.new"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "title");
		apiVerification.responseKeyValidation(response, "url");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTInsurance() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		String apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),schedulerPayload.putAppointmentPayload(plus20Minutes,
			    propertyData.getProperty("mf.apt.scheduler.phone"),propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);

		Response response = postAPIRequest.aptPutInsurance(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getInsurancePayload(), headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutInsurance(response, propertyData.getProperty("apt.precheck.practice.id"), patientId,
				apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutDemographics() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		String apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),schedulerPayload.putAppointmentPayload(plus20Minutes,
		        propertyData.getProperty("mf.apt.scheduler.phone"),propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);

		Response response = postAPIRequest.aptPutDemographics(propertyData.getProperty("apt.precheck.practice.id"),
				payload.getDemographicsPayload(), headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);

		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyDemographics(response, propertyData.getProperty("apt.precheck.practice.id"), patientId,
				apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalancePayFromApi() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.balance.practice.id"),schedulerPayload.putAppointmentPayload(plus20Minutes,
				propertyData.getProperty("mf.apt.scheduler.phone"),propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"));

		Response response = postAPIRequest.aptBalancePay(propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getBalancePayPayload(patientId), headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPostBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, propertyData.getProperty("appt.precheck.balance.pay.appt.id"),
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalancePayForPrecheck() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheResponse = postAPIRequest
				.aptpostAppointments(propertyData.getProperty("apt.precheck.balance.practice.id"),
						payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"),
								plus20Minutes),
						headerConfig.HeaderwithToken(getaccessToken),
						propertyData.getProperty("apt.precheck.integration.id"), patientId);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		Response response = postAPIRequest.aptBalancePay(propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getBalancePayPayloadForPrecheck(patientId), headerConfig.HeaderwithToken(getaccessToken),
				patientId, apptPrecheckApptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPostBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, apptPrecheckApptId, propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopayPayFromApi() throws IOException, InterruptedException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"));

		Response response = postAPIRequest.aptCopayPay(propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getCopayPayPayload(patientId), headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCopayfromApiPay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, propertyData.getProperty("appt.precheck.balance.pay.appt.id"),
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopayPayForPrecheck() throws IOException, InterruptedException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheResponse = postAPIRequest
				.aptpostAppointments(propertyData.getProperty("apt.precheck.balance.practice.id"),
						payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"),
								plus20Minutes),
						headerConfig.HeaderwithToken(getaccessToken),
						propertyData.getProperty("apt.precheck.integration.id"), patientId);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		Response response = postAPIRequest.aptCopayPay(propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getCopayPayPayload(patientId), headerConfig.HeaderwithToken(getaccessToken), patientId,
				apptPrecheckApptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCopayfromApiPay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, apptPrecheckApptId, propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsConfirm() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		String apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),schedulerPayload.putAppointmentPayload(plus20Minutes,
				propertyData.getProperty("mf.apt.scheduler.phone"),propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);

		Response response = postAPIRequest.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		log("Verifying the response");

		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyAppointmentActions(response, propertyData.getProperty("confirm.appt.action"),
				propertyData.getProperty("apt.precheck.practice.id"), patientId, apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsCancel() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		String apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),schedulerPayload.putAppointmentPayload(plus20Minutes,
				propertyData.getProperty("mf.apt.scheduler.phone"),propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);

		Response response = postAPIRequest.aptAppointmentActionsCancel(
				propertyData.getProperty("apt.precheck.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				patientId, apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyAppointmentActions(response, propertyData.getProperty("cancel.appt.action"),
				propertyData.getProperty("apt.precheck.practice.id"), patientId, apptId);
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
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
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
				propertyData.getProperty("baseurl.apt.precheck"),
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
	public void testPOSTArrivalActionsCurbsideCheckIn() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		String apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),schedulerPayload.putAppointmentPayload(plus20Minutes,
				propertyData.getProperty("mf.apt.scheduler.phone"),propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);

		Response actionResponse = postAPIRequest.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response response = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptId);
		assertEquals(response.getStatusCode(), 200);
		
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyAppointmentActions(response,
					propertyData.getProperty("curbs.side.checkin.appt.action"),
					propertyData.getProperty("apt.precheck.practice.id"), patientId, apptId);
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
		apiVerification.verifyAppointmentsGuest(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));
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
		apiVerification.verifyGuestAuthorization(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));
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
		apiVerification.verifyApptIdGuest(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETRequiredFormsGuest() throws IOException {
		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.patient.id.new"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.getRequiredFormsGuest(propertyData.getProperty("apt.precheck.practice.id"),
				responseGuestToken, propertyData.getProperty("apt.precheck.patient.id.new"),
				propertyData.getProperty("apt.precheck.pm.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "title");
		apiVerification.responseKeyValidation(response, "url");
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
				responseGuestToken, propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"), headerConfig.defaultHeader());

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutInsurance(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"),
				propertyData.getProperty("insurance.guest.name"), propertyData.getProperty("insurance.guest.member.id"),
				propertyData.getProperty("insurance.guest.group.name"));
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
				responseGuestToken, propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"), headerConfig.defaultHeader());

		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyGuestDemographics(response, propertyData.getProperty("apt.precheck.guest.practice.id"),
				propertyData.getProperty("apt.precheck.guest.patient.id"),
				propertyData.getProperty("apt.precheck.guest.appointment.id"),
				propertyData.getProperty("guest.demographic.first.name"),
				propertyData.getProperty("guest.demographic.last.name"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCopaySkipGuest() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randomNo = random.nextInt(100000);
		String patientId = String.valueOf(randomNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostCopaySkipGuest(
				propertyData.getProperty("apt.precheck.balance.practice.id"), responseGuestToken, patientId,
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		log("Verifying the response");
		apiVerification.responseTimeValidation(response);
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, propertyData.getProperty("appt.precheck.copay.skip.appt.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalanceSkipGuest() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randomNo = random.nextInt(100000);
		String patientId = String.valueOf(randomNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostBalanceSkipGuest(
				propertyData.getProperty("apt.precheck.balance.practice.id"), responseGuestToken, patientId,
				propertyData.getProperty("appt.precheck.copay.skip.appt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalanceSkipPay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, propertyData.getProperty("appt.precheck.copay.skip.appt.id"),
				propertyData.getProperty("apt.precheck.balance.skip.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalancePayGuestFromApi() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"));

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostBalancePayGuest(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getBalancePayloadGuest(patientId),
				responseGuestToken, patientId, propertyData.getProperty("appt.precheck.balance.pay.appt.id"),
				headerConfig.defaultHeader());

		log("Verifying the response");
		log("Post an appointments action");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPostBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, propertyData.getProperty("appt.precheck.balance.pay.appt.id"),
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalancePayGuestForPrecheck() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheResponse = postAPIRequest
				.aptpostAppointments(propertyData.getProperty("apt.precheck.balance.practice.id"),
						payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"),
								plus20Minutes),
						headerConfig.HeaderwithToken(getaccessToken),
						propertyData.getProperty("apt.precheck.integration.id"), patientId);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptPrecheckApptId);

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostBalancePayGuest(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getBalancePayloadGuest(patientId),
				responseGuestToken, patientId, apptPrecheckApptId, headerConfig.defaultHeader());

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPostBalancePay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, apptPrecheckApptId, propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCopayPayGuestFromApi() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		postAPIRequestApptSche.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				schedulerPayload.putAppointmentCopayPayload(plus20Minutes),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"));

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getGuestSessionPayloadForCopay(),
				headerConfig.HeaderwithToken(getaccessToken), patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"));

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostCopayPayGuest(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getCopayPayPayloadGuest(patientId), responseGuestToken, patientId,
				propertyData.getProperty("appt.precheck.balance.pay.appt.id"), headerConfig.defaultHeader());

		apiVerification.verifyCopayfromApiPay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, propertyData.getProperty("appt.precheck.balance.pay.appt.id"),
				propertyData.getProperty("apt.precheck.balance.complete.status"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCopayPayGuestForPrecheck() throws IOException {
		log("Schedule a new Appointment");
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		String patientId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheResponse = postAPIRequest
				.aptpostAppointments(propertyData.getProperty("apt.precheck.balance.practice.id"),
						payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"),
								plus20Minutes),
						headerConfig.HeaderwithToken(getaccessToken),
						propertyData.getProperty("apt.precheck.integration.id"), patientId);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), patientId, apptPrecheckApptId);

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostCopayPayGuest(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getCopayPayloadForPrecheck(patientId), responseGuestToken, patientId, apptPrecheckApptId,
				headerConfig.defaultHeader());

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCopayfromApiPay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				patientId, apptPrecheckApptId, propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
	}
}