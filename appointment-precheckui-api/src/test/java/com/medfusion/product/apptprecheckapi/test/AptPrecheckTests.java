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
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.AptPrecheckPayload;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
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
	CommonMethods commonMtd;

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
		commonMtd = new CommonMethods();
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointments() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		Response response = postAPIRequest.getAppointments(propertyData.getProperty("apt.precheck.test.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptData(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsAppId() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		Response response = postAPIRequest.getAppointmentsAppId(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPerticularIdApptData(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsStartDay() throws IOException {
		Response response = postAPIRequest.getAppointmentsStartDay(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.appts.start.day"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptDataForStartDay(response, propertyData.getProperty("apt.precheck.test.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTReturnsPageOfAppointments() throws IOException {
		Response response = postAPIRequest.aptPostPracticeId(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getPracticeIdPayload(propertyData.getProperty("apt.precheck.practice.appt.date.range.start"),
						propertyData.getProperty("apt.precheck.practice.appt.date.range.end")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsBasedOnPaging(response,
				propertyData.getProperty("apt.precheck.test.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTReturnsPageOfAppointmentsWithInvalidDateRange() throws IOException {
		Response response = postAPIRequest.aptPostPracticeId(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getPracticeIdWithInvalidDateRange(
						propertyData.getProperty("apt.precheck.practice.invalid.date.range"),
						propertyData.getProperty("apt.precheck.practice.appt.date.range.end")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsWithInvalidDateRange(response,
				propertyData.getProperty("apt.precheck.practice.appt.date.range.end"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTReturnsCurbSidePageOfAppointments() throws IOException {
		Response response = postAPIRequest.aptPostArrivals(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getArrivalsPayload(propertyData.getProperty("apt.precheck.arrivals.appt.date.range.start"),
						propertyData.getProperty("apt.precheck.arrivals.appt.date.range.end")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsBasedOnPaging(response,
				propertyData.getProperty("apt.precheck.test.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTReturnsCurbSidePageOfApptsWithInvalidDateRange() throws IOException {
		Response response = postAPIRequest.aptPostArrivals(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getArrivalsPayload(propertyData.getProperty("apt.precheck.practice.invalid.date.range"),
						propertyData.getProperty("apt.precheck.arrivals.appt.date.range.end")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsWithInvalidDateRange(response,
				propertyData.getProperty("apt.precheck.arrivals.appt.date.range.end"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTHistoryMessage() throws IOException {
		Response response = postAPIRequest.aptPostHistoryMessage(propertyData.getProperty("history.msg.practice.id"),
				payload.getHistoryMessage(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.messagehistory.patient.id"),
				propertyData.getProperty("apt.precheck.messagehistory.appmnt.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyMessageHistory(response, propertyData.getProperty("history.msg.practice.id"),
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
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.getFormInformation(propertyData.getProperty("apt.precheck.test.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);

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
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.getFormInfoWithInvalidPatientToken(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyFormInfoWithInvalidPatientToken(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBroadcastMessage() throws IOException, InterruptedException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptBroadcastMessage(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getBroadcastMessagePayload(propertyData.getProperty("apt.precheck.broadcast.date.range.start"),
						propertyData.getProperty("apt.precheck.broadcast.date.range.end"), Appointment.patientId,
						Appointment.apptId),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "success");
		apiVerification.responseKeyValidationJson(response, "fail");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCheckinActions() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response CheckInResponse = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(CheckInResponse.getStatusCode(), 200);

		Response arrivalResponse = postAPIRequest.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		Response response = postAPIRequest.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getCheckinActionsPayload(Appointment.apptId, Appointment.patientId,
						propertyData.getProperty("apt.precheck.test.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCheckInAppointments(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId, "CHECKIN");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCheckinActionsWithInvalidPatientId() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getCheckinActionsPayload(Appointment.patientId,
						propertyData.getProperty("checkin.actions.with.invalid.patient.id"),
						propertyData.getProperty("apt.precheck.test.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCheckinActionsWithInvalidId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCheckinActionsWithInvalidApptId() throws IOException {
		Response response = postAPIRequest.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getCheckinActionsPayload(propertyData.getProperty("checkin.actions.with.invalid.appt.id"),
						propertyData.getProperty("checkin.actions.with.invalid.patient.id"),
						propertyData.getProperty("apt.precheck.test.practice.id")),
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
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		Response response = postAPIRequest.getPatientsIdentification(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPatientsIdentification(response,
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("apt.precheck.integration.id"), Appointment.patientId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPrecheckAppt() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest
				.aptpostAppointments(propertyData.getProperty("apt.precheck.test.practice.id"),
						payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"),
								plus20Minutes),
						headerConfig.HeaderwithToken(getaccessToken),
						propertyData.getProperty("apt.precheck.integration.id"), Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointments(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("apt.precheck.integration.id"), Appointment.patientId,
				propertyData.getProperty("precheck.appt.first.name"),
				propertyData.getProperty("precheck.appt.last.name"),
				propertyData.getProperty("precheck.appt.birth.date"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPrecheckApptWithoutTime() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		Response response = postAPIRequest.aptpostAppointments(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"), 0),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("apt.precheck.integration.id"),
				Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyprecheckApptWithoutTime(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPrecheckApptWithoutName() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptpostAppointments(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.precheckApptPayloadWithoutName(plus20Minutes), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.integration.id"), Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTPrecheckApptWithoutPhone() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptpostAppointments(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.precheckApptPayloadWithoutPhone(plus20Minutes), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.integration.id"), Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyprecheckApptWithoutPhone(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopaySkip() throws IOException, InterruptedException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		Response response = postAPIRequest.aptpostCopaySkip(propertyData.getProperty("apt.precheck.test.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);

		log("Verifying the response");
		log("Copay skipped");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopaySkipIncorrectPatientId() throws IOException {
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		Response response = postAPIRequest.aptpostCopaySkipIncorrectPatientId(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"), Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalanceSkip() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptPostBalanceSkip(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalanceSkipPay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("apt.precheck.balance.skip.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalanceSkipIncorrectPatientId() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		Response response = postAPIRequest.aptpostBalanceSkipIncorrectPatientId(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.precheck.copay.skip.Incorrectpatient.id"), Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTForms() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptPutForms(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getFormsPayload(), headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId,
				Appointment.apptId);

		log("Verify response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutForms(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETRequiredForms() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		Response response = postAPIRequest.getRequiredForms(propertyData.getProperty("apt.precheck.test.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "title");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTInsurance() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptPutInsurance(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getInsurancePayload(), headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId,
				Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutInsurance(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutDemographics() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptPutDemographics(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getDemographicsPayload(), headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId,
				Appointment.apptId);

		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyDemographics(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalancePayFromApi() throws IOException {
		log("Schedule a new Appointment");
		Response scheResponse = commonMtd.scheduleNewAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		Response response = postAPIRequest.aptBalancePay(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getBalancePayPayload(Appointment.patientId), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, apptPrecheckApptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPostBalancePay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, apptPrecheckApptId,
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTBalancePayForPrecheck() throws IOException {
		log("Schedule a new Appointment");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheResponse = postAPIRequest
				.aptpostAppointments(propertyData.getProperty("apt.precheck.test.practice.id"),
						payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"),
								plus20Minutes),
						headerConfig.HeaderwithToken(getaccessToken),
						propertyData.getProperty("apt.precheck.integration.id"), Appointment.patientId);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		Response response = postAPIRequest.aptBalancePay(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getBalancePayPayloadForPrecheck(Appointment.patientId),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, apptPrecheckApptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPostBalancePay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, apptPrecheckApptId,
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopayPayFromApi() throws IOException, InterruptedException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptCopayPay(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getCopayPayPayload(Appointment.patientId), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCopayfromApiPay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTCopayPayForPrecheck() throws IOException, InterruptedException {
		log("Schedule a new Appointment");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheResponse = postAPIRequest
				.aptpostAppointments(propertyData.getProperty("apt.precheck.test.practice.id"),
						payload.postAppointmentsPayload(propertyData.getProperty("apt.precheck.integration.id"),
								plus20Minutes),
						headerConfig.HeaderwithToken(getaccessToken),
						propertyData.getProperty("apt.precheck.integration.id"), Appointment.patientId);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		Response response = postAPIRequest.aptCopayPay(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getCopayPayPayload(Appointment.patientId), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, apptPrecheckApptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCopayfromApiPay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, apptPrecheckApptId,
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsConfirm() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");

		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyAppointmentActions(response, propertyData.getProperty("confirm.appt.action"),
				propertyData.getProperty("apt.precheck.test.practice.id"), Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsCancel() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptAppointmentActionsCancel(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyAppointmentActions(response, propertyData.getProperty("cancel.appt.action"),
				propertyData.getProperty("apt.precheck.test.practice.id"), Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsArrival() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptAppointmentActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("appt.action.confirm"));
		assertEquals(actionResponse.getStatusCode(), 200);

		Response CheckInResponse = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(CheckInResponse.getStatusCode(), 200);

		Response arrivalResponse = postAPIRequest.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		assertEquals(arrivalResponse.getStatusCode(), 200);
		apiVerification.responseTimeValidation(arrivalResponse);
		apiVerification.verifyAppointmentActions(arrivalResponse, propertyData.getProperty("arrival.appt.action"),
				propertyData.getProperty("apt.precheck.test.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsArrivalForSamePatient() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptAppointmentActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("appt.action.confirm"));
		assertEquals(actionResponse.getStatusCode(), 200);

		Response CheckInResponse = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(CheckInResponse.getStatusCode(), 200);

		Response repeatArrivalAction = postAPIRequest.aptAppointmentActionsArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);

		assertEquals(repeatArrivalAction.getStatusCode(), 400);
		apiVerification.verifyAppointmentActionPast(repeatArrivalAction);
		apiVerification.responseTimeValidation(repeatArrivalAction);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsCurbscheckin() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptAppointmentActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("appt.action.confirm"));
		assertEquals(actionResponse.getStatusCode(), 200);

		Response CheckInResponse = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(CheckInResponse.getStatusCode(), 200);

		apiVerification.verifyAppointmentActions(CheckInResponse, propertyData.getProperty("curbscheckin.appt.action"),
				propertyData.getProperty("apt.precheck.test.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTAppointmentActionsCurbscheckinForSameAction() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptAppointmentActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("appt.action.confirm"));
		assertEquals(actionResponse.getStatusCode(), 200);

		Response checkInResponse = postAPIRequest.aptAppointmentActionsCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);

		assertEquals(checkInResponse.getStatusCode(), 400);
		apiVerification.verifyAppointmentActionPast(checkInResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideConfirm() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptAppointmentActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("appt.action.confirm"));
		assertEquals(actionResponse.getStatusCode(), 200);

		assertEquals(actionResponse.getStatusCode(), 200);
		apiVerification.verifyAppointmentActions(actionResponse, propertyData.getProperty("confirm.appt.action"),
				propertyData.getProperty("apt.precheck.balance.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideConfirmForSamePatient() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptArrivalActionsCurbsideConfirm(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 404);
		apiVerification.responseTimeValidation(actionResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideCancel() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptAppointmentActionsCancel(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");

		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyAppointmentActions(response, propertyData.getProperty("cancel.appt.action"),
				propertyData.getProperty("apt.precheck.test.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideCancelForSamePatient() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response response = postAPIRequest.aptArrivalActionsCurbsideCancel(
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideArrival() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = postAPIRequest.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);

		log("Verifying the response");
		assertEquals(arrivalResponse.getStatusCode(), 200);
		apiVerification.responseTimeValidation(arrivalResponse);
		apiVerification.verifyAppointmentActions(arrivalResponse, propertyData.getProperty("arrival.appt.action"),
				propertyData.getProperty("apt.precheck.test.practice.id"), Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTArrivalActionsCurbsideCheckIn() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		Response actionResponse = postAPIRequest.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response response = postAPIRequest.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.test.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		assertEquals(response.getStatusCode(), 200);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyAppointmentActions(response, propertyData.getProperty("curbs.side.checkin.appt.action"),
				propertyData.getProperty("apt.precheck.test.practice.id"), Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsGuest() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);

		log("Response guest token- " + responseGuestToken);
		Response response = postAPIRequest.getAppointmentsGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"), responseGuestToken, Appointment.patientId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyAppointmentsGuest(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGuestSessionsAuthorization() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);

		log("Response guest token- " + responseGuestToken);
		Response response = postAPIRequest.GuestSessionsAuthorization(responseGuestToken);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGuestAuthorization(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsAppIdGuest() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);

		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.getAppointmentsAppIdGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"), responseGuestToken, Appointment.patientId,
				Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptIdGuest(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETRequiredFormsGuest() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.getRequiredFormsGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"), responseGuestToken, Appointment.patientId,
				Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "title");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTFormsGuest() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutFormsGuest(propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getFormsPayloadGuest(), responseGuestToken, Appointment.patientId, Appointment.apptId,
				headerConfig.defaultHeader());

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutForms(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTInsuranceGuest() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutInsuranceGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getInsurancePayloadGuest(),
				responseGuestToken, Appointment.patientId, Appointment.apptId, headerConfig.defaultHeader());

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutInsurance(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("insurance.guest.name"),
				propertyData.getProperty("insurance.guest.member.id"),
				propertyData.getProperty("insurance.guest.group.name"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTDemographicsGuest() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutDemographicsGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getDemographicsPayloadGuest(),
				responseGuestToken, Appointment.patientId, Appointment.apptId, headerConfig.defaultHeader());

		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyGuestDemographics(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("guest.demographic.first.name"),
				propertyData.getProperty("guest.demographic.last.name"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCopaySkipGuest() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostCopaySkipGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"), responseGuestToken, Appointment.patientId,
				Appointment.apptId);

		log("Verifying the response");
		apiVerification.responseTimeValidation(response);
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyBalancePay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalanceSkipGuest() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostBalanceSkipGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"), responseGuestToken, Appointment.patientId,
				Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyBalanceSkipPay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId,
				propertyData.getProperty("apt.precheck.balance.skip.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalancePayGuestFromApi() throws IOException, InterruptedException {
		log("Schedule a new Appointment");
		Response scheResponse = commonMtd.scheduleNewAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, apptPrecheckApptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostBalancePayGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getBalancePayloadGuest(Appointment.patientId,
						propertyData.getProperty("balance.Pay.guest.dob")),
				responseGuestToken, Appointment.patientId, apptPrecheckApptId, headerConfig.defaultHeader());

		log("Verifying the response");
		log("Post an appointments action");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPostBalancePay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, apptPrecheckApptId,
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalancePayGuestForPrecheck() throws IOException {
		log("Schedule a new Appointment");
		Response scheResponse = commonMtd.scheduleNewAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, apptPrecheckApptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostBalancePayGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getBalancePayloadGuest(Appointment.patientId,
						propertyData.getProperty("balance.Pay.guest.dob")),
				responseGuestToken, Appointment.patientId, apptPrecheckApptId, headerConfig.defaultHeader());

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyPostBalancePay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, apptPrecheckApptId,
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCopayPayGuestFromApi() throws IOException, InterruptedException {

		Response scheResponse = commonMtd.scheduleNewAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.balance.practice.id"), payload.getGuestSessionPayloadForCopay(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, apptPrecheckApptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostCopayPayGuest(
				propertyData.getProperty("apt.precheck.balance.practice.id"),
				payload.getCopayPayPayloadGuest(Appointment.patientId), responseGuestToken, Appointment.patientId,
				apptPrecheckApptId, headerConfig.defaultHeader());

		apiVerification.verifyCopayfromApiPay(response, propertyData.getProperty("apt.precheck.balance.practice.id"),
				Appointment.patientId, apptPrecheckApptId,
				propertyData.getProperty("apt.precheck.balance.complete.status"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCopayPayGuestForPrecheck() throws IOException {
		log("Schedule a new Appointment");
		Response scheResponse = commonMtd.scheduleNewAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		JsonPath js = new JsonPath(scheResponse.asString());
		String apptPrecheckApptId = js.getString("pmAppointmentId");

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, apptPrecheckApptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPostCopayPayGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getCopayPayloadForPrecheck(Appointment.patientId), responseGuestToken, Appointment.patientId,
				apptPrecheckApptId, headerConfig.defaultHeader());

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCopayfromApiPay(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, apptPrecheckApptId,
				propertyData.getProperty("apt.precheck.balance.complete.status"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTDemographicsGuestPhoneType() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutDemographicsGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getDemographicsPayloadGuest(propertyData.getProperty("phone.type.work")), responseGuestToken,
				Appointment.patientId, Appointment.apptId, headerConfig.defaultHeader());

		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyGuestDemographics(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("guest.demographic.first.name"),
				propertyData.getProperty("guest.demographic.last.name"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTDemographicsGuestBlankPhoneType() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutDemographicsGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getDemographicsPayloadGuest(""),
				responseGuestToken, Appointment.patientId, Appointment.apptId, headerConfig.defaultHeader());

		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyGuestDemographics(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("guest.demographic.first.name"),
				propertyData.getProperty("guest.demographic.last.name"));
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPUTDemographicsGuestInvalidPhoneType() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.test.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);

		String responseGuestToken = postAPIRequest.aptGuestSessionsSession(
				propertyData.getProperty("apt.precheck.test.practice.id"), payload.getGuestSessionPayload(),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		log("Response guest token- " + responseGuestToken);

		Response response = postAPIRequest.aptPutDemographicsGuest(
				propertyData.getProperty("apt.precheck.test.practice.id"),
				payload.getDemographicsPayloadGuest(propertyData.getProperty("invalid.phone.type")), responseGuestToken,
				Appointment.patientId, Appointment.apptId, headerConfig.defaultHeader());

		log("Verify Put Demographics");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyGuestDemographics(response, propertyData.getProperty("apt.precheck.test.practice.id"),
				Appointment.patientId, Appointment.apptId, propertyData.getProperty("guest.demographic.first.name"),
				propertyData.getProperty("guest.demographic.last.name"));
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPOSTConfirmCancelAppt() throws IOException {
		log("Schedule a new Appointment");
		commonMtd.scheduleNewAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.phone"), propertyData.getProperty("mf.apt.scheduler.email"),
				getaccessToken);
		
		log("Delete Appoinment");
		Response response = postAPIRequestApptSche.getDELETETAppointment(
				propertyData.getProperty("mf.apt.scheduler.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				 Appointment.patientId,  Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		Response ConfirmResponse = postAPIRequest.getDeleteAppointmentActions(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId, Appointment.apptId);
		log("Verifying the response");
		assertEquals(ConfirmResponse.getStatusCode(), 404);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
	}
}