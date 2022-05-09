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
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;

import io.restassured.response.Response;

public class AptPrecheckMfAppointmentSchedulerTests extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfAppointmentSchedulerPayload payload;
	public static PostAPIRequestMfAppointmentScheduler postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();
	CommonMethods commonMtd;
	
	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.appointment.scheduler"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.appointment.scheduler"));
		commonMtd = new CommonMethods();

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsForPss() throws IOException {		
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheduleResponse = postAPIRequest.aptPutAppointmentPss(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
		
		log("Get Appoinment");
		Response response = postAPIRequest.getAppointmentAptId(propertyData.getProperty("mf.apt.scheduler.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetAppt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsForPM() throws IOException {	
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheduleResponse = postAPIRequest.aptPutAppointmentPss(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
		
		log("Get Appoinment");
		Response response = postAPIRequest.getAppointmentAptId(propertyData.getProperty("mf.apt.scheduler.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetAppt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"), Appointment.patientId,
				Appointment.apptId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsForLegacy() throws IOException {
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheduleResponse = postAPIRequest.aptPutAppointmentPss(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
		
		log("Get Appoinment");
		Response response = postAPIRequest.getAppointmentAptId(propertyData.getProperty("mf.apt.scheduler.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId,  Appointment.apptId);
		
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetAppt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"),  Appointment.patientId,
				 Appointment.apptId);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEAppointmentForPss() throws IOException {
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheduleResponse = postAPIRequest.aptPutAppointmentPss(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
		
		log("Delete Appoinment");
		Response response = postAPIRequest.getDELETETAppointment(
				propertyData.getProperty("mf.apt.scheduler.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				 Appointment.patientId,  Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyDeleteApmt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"),  Appointment.patientId,
				 Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEAppointmentForPM() throws IOException {	
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheduleResponse = postAPIRequest.aptPutAppointmentPss(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
		
		log("Delete Appoinment");
		Response response = postAPIRequest.getDELETETAppointment(
				propertyData.getProperty("mf.apt.scheduler.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				 Appointment.patientId,  Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyDeleteApmt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"),  Appointment.patientId,
				 Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEAppointmentForLegacy() throws IOException {
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response scheduleResponse = postAPIRequest.aptPutAppointmentPss(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
		
		log("Delete Appoinment");
		Response response = postAPIRequest.getDELETETAppointment(
				propertyData.getProperty("mf.apt.scheduler.practice.id"), headerConfig.HeaderwithToken(getaccessToken),
				 Appointment.patientId,  Appointment.apptId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyDeleteApmt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"),  Appointment.patientId,
				 Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutAppointmentForPSS() throws IOException {
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptPutAppointmentPss(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		
		log("Verify response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutAppt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"), Appointment.patientId,
				Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutAppointmentForPM() throws IOException {
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptPutAppointmentPM(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		
		log("Verify response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutAppt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"), Appointment.patientId,
				Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutAppointmentForLegacy() throws IOException {
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptPutAppointmentLegacy(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		
		log("Verify response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutAppt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"), Appointment.patientId,
				Appointment.apptId);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutAppointmentForInvalidSource() throws IOException {
		log("Schedule new Appointments");
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = postAPIRequest.aptPutAppointmentInvalidSource(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		
		log("Verify response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutAppointmentPastTime() throws IOException {
		Appointment.patientId = commonMtd.generateRandomNum();
		Appointment.apptId = commonMtd.generateRandomNum();
		Response response = postAPIRequest.aptPutAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"), payload.putAppointmentPastTimePayload(),
				headerConfig.HeaderwithToken(getaccessToken),
				Appointment.patientId,
				Appointment.apptId );
		
		log("Verify response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.verifyPutAppointmentPastTime(response);
		apiVerification.responseTimeValidation(response);
	}
	
	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
		
	}
}
