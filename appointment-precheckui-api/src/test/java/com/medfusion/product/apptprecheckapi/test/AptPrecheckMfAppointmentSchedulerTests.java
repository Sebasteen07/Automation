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
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
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
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsAppId() throws IOException {
		Response response = postAPIRequest.getAppointmentAptId(propertyData.getProperty("mf.apt.scheduler.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.patient.id"),
				propertyData.getProperty("mf.apt.scheduler.appt.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDELETEAppointment() throws IOException {

		Response response = postAPIRequest.getDELETETAppointment(propertyData.getProperty("mf.apt.scheduler.delete.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.delete.patient.id"),
				propertyData.getProperty("mf.apt.scheduler.delete.appt.id"));
		log("Verifying the response");

		if (response.getStatusCode() == 200) {
			log("Delete appointment action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyDeleteApmt(response,
					propertyData.getProperty("mf.apt.scheduler.delete.practice.id"),
					propertyData.getProperty("mf.apt.scheduler.delete.patient.id"),
					propertyData.getProperty("mf.apt.scheduler.delete.appt.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Cannot delete past appointment");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyPastAppmnt(response);
		}
		apiVerification.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutAppointment() throws IOException {
		Response response = postAPIRequest.aptPutAppointment(propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mf.apt.scheduler.put.patient.id"),
				propertyData.getProperty("mf.apt.scheduler.put.appt.id"));

		log("Payload- " + payload.putAppointmentPayload());
		log("Verify response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.verifyPutAppt(response, propertyData.getProperty("mf.apt.scheduler.practice.id"),
				propertyData.getProperty("mf.apt.scheduler.put.patient.id"),
				propertyData.getProperty("mf.apt.scheduler.put.appt.id"));
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutAppointmentPastTime() throws IOException {
		Response response = postAPIRequest.aptPutAppointment(propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPastTimePayload(), headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mf.apt.scheduler.put.patient.id"),
				propertyData.getProperty("mf.apt.scheduler.put.appt.id"));

		log("Payload- " + payload.putAppointmentPastTimePayload());
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
