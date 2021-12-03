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
import com.medfusion.product.appt.precheck.payload.NotificationDisplayServicePayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestNotificationDisplayService;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApptPrecheckNotificationDisplayServiceTest extends BaseTestNG{
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static NotificationDisplayServicePayload payload;
	public static PostAPIRequestNotificationDisplayService postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	public static PostAPIRequestMfAppointmentScheduler postAPIRequestApptSche;
	public static MfAppointmentSchedulerPayload schedulerPayload;
	APIVerification apiVerification = new APIVerification();
	String id;
	
	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestNotificationDisplayService.getPostAPIRequestNotificationDisplayService();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = NotificationDisplayServicePayload.getNotificationDisplayServicePayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequestApptSche = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		schedulerPayload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.notif.display.service"));
		log("BASE URL-" + propertyData.getProperty("baseurl.notif.display.service"));
	}
	
	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		Appointment.patientId = String.valueOf(randamNo);
		Appointment.apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		log("Schedule a new Appointment");
		Response scheduleResponse = postAPIRequestApptSche.aptPutAppointment(
				propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				schedulerPayload.putAppointmentPayload(plus20Minutes,
						propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(getaccessToken), Appointment.patientId, Appointment.apptId);
		assertEquals(scheduleResponse.getStatusCode(), 200);
		JsonPath js = new JsonPath(scheduleResponse.asString());
		id=js.getString("id");
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePrecheckNotificationPost() throws IOException {
		Response response = postAPIRequest.createPrecheckNotification(
				payload.getCreatePrecheckNotifPayload(id,propertyData.getProperty("notif.service.practice.id"),
						propertyData.getProperty("notif.service.practice.name")),headerConfig.HeaderwithToken(getaccessToken));
		
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreatePrecheckNotif(response,propertyData.getProperty("notif.service.practice.id"),
				propertyData.getProperty("notif.service.practice.name"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateScheduleNotificationPost() throws IOException {
		Response response = postAPIRequest.createScheduleNotification(
				payload.getCreateScheduleNotifPayload(id,propertyData.getProperty("notif.service.practice.id"),
						propertyData.getProperty("notif.service.practice.name")),
				headerConfig.HeaderwithToken(getaccessToken));
		
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateScheduleNotif(response,propertyData.getProperty("notif.service.practice.id"),
				propertyData.getProperty("notif.service.practice.name"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationByIdForPrecheck() throws IOException {
		Response PostNotifResponse = postAPIRequest.createPrecheckNotification(
				payload.getCreatePrecheckNotifPayload(id,propertyData.getProperty("notif.service.practice.id"),
						propertyData.getProperty("notif.service.practice.name")),
				headerConfig.HeaderwithToken(getaccessToken));
		assertEquals(PostNotifResponse.getStatusCode(), 200);
		
		JsonPath js = new JsonPath(PostNotifResponse.asString());
		String id=js.getString("id");
		
		Response response = postAPIRequest.GetNotificationById(headerConfig.HeaderwithToken(getaccessToken),id);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateScheduleNotif(response,propertyData.getProperty("notif.service.practice.id"),
				propertyData.getProperty("notif.service.practice.name"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotifnWithoutIdForPrecheck() throws IOException {
		Response response = postAPIRequest.GetNotifWithoutIdForPrecheck(headerConfig.HeaderwithToken(getaccessToken));
		
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotificationByIdForSchedule() throws IOException {
		Response PostNotifResponse = postAPIRequest.createScheduleNotification(
				payload.getCreateScheduleNotifPayload(id,propertyData.getProperty("notif.service.practice.id"),
				propertyData.getProperty("notif.service.practice.name")),headerConfig.HeaderwithToken(getaccessToken));
		
		assertEquals(PostNotifResponse.getStatusCode(), 200);
		JsonPath js = new JsonPath(PostNotifResponse.asString());
		String id=js.getString("id");
		
		Response response = postAPIRequest.GetNotificationById(headerConfig.HeaderwithToken(getaccessToken),id);
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateScheduleNotif(response,propertyData.getProperty("notif.service.practice.id"),
				propertyData.getProperty("notif.service.practice.name"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetNotifWithoutIdForSchedule() throws IOException {
		Response response = postAPIRequest.GetNotifWithoutIdForSchedule(
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}
}
