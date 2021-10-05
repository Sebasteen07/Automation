// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.ParseJSONFile;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestPMNG;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestPatientMod;
import com.medfusion.product.pss2patientapi.payload.PayloadPssPMNG;
import com.medfusion.product.pss2patientapi.payload.PayloadPssPatientModulator;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PSS2PatientModulatorrAcceptanceNGTests extends BaseTestNG {

	public static HeaderConfig headerConfig;
	public static PSSPropertyFileLoader propertyData;

	public static Appointment testData;
	public static PostAPIRequestPMNG postAPIRequest;
	public static PayloadPssPMNG payloadPatientMod;
	public static String accessToken;
	public static String practiceid;
	public static String baseurl;
	public PSSPatientUtils pssPatientUtils;

	APIVerification apv;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {

		log("Set the Authorization for Patient Modulator");

		headerConfig = new HeaderConfig();
		propertyData = new PSSPropertyFileLoader();
		testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		postAPIRequest = new PostAPIRequestPMNG();
		payloadPatientMod = new PayloadPssPMNG();

		baseurl = propertyData.getProperty("base.url.pm.ng");
		practiceid = propertyData.getProperty("practice.id.pm.ng");
		accessToken = postAPIRequest.createToken(baseurl, practiceid);

		// testData.setAccessToken(accessToken);

		pssPatientUtils = new PSSPatientUtils();
		apv = new APIVerification();

		log("Base URL for Patient Modulator - " + baseurl);

	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01AnnouncementByLanguageGET() throws IOException {

		Response response = postAPIRequest.announcementByLanguage(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test02AnnouncementByNameGET() throws IOException {

		String validanncode = "AG";
		Response response = postAPIRequest.announcementByName(baseurl, headerConfig.defaultHeader(), practiceid,
				validanncode);
		String message = apv.responseKeyValidationJson(response, "message");
		log("Announcement message is -" + message);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test03AnnouncementTypeGET() throws IOException {

		Response response = postAPIRequest.announcementType(baseurl, headerConfig.defaultHeader());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "code");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test04AllowOnlineCancellationPost() throws IOException {
		
		String extapptid=propertyData.getProperty("extappt.id.pm.ng");
		String patientid=propertyData.getProperty("patient.id.pm.ng");

		String b = payloadPatientMod.allowOnlineCancellationPayload(extapptid);
		log("Practice Id is-" + practiceid);

		Response response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		assertEquals(apv.responseKeyValidationJson(response, "checkCancelAppointmentStatus"), "true");
		assertEquals(apv.responseKeyValidationJson(response, "preventScheduling"), null);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test05getAppointment() throws IOException {

		String extapptid = propertyData.getProperty("extappt.id.pm.ng");

		Response response = postAPIRequest.appointment(baseurl, headerConfig.defaultHeader(), practiceid,
				extapptid);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "patientId");
		apv.responseKeyValidationJson(response, "confirmationNo");
		apv.responseKeyValidationJson(response, "bookName");
		apv.responseKeyValidationJson(response, "locationName");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test06getAppointmentForIcs() throws IOException {
		
		String extapptid = propertyData.getProperty("extappt.id.pm.ng");

		Response response = postAPIRequest.appointmentForIcs(baseurl, headerConfig.defaultHeader(),
				practiceid, extapptid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test07AvailableSlotsPost_ExistingPatient() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		Response response = postAPIRequest.availableSlots(baseurl, b,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test08AvailableSlotsPost_NewPatient() throws IOException {

		String patientid = null;

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String currentdate = dateFormat.format(date);
		log("Current Date- " + currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		Response response = postAPIRequest.availableSlots(baseurl, b,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test09Sched_Resc_Cancel_AppointmentPost() throws IOException {

		String patientid=propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		Response response = postAPIRequest.availableSlots(baseurl, b,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(2).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPatientMod.scheduleAppointmentPayload(slotid, date, time,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid,
				testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String confirmationno = apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "slotAlreadyTaken");

		String startdate = date + " " + time;

		String d = payloadPatientMod.rescheduleAppointmentPayload(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid,
				testData.getPatientIdAvailableSlots(), testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");

		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extapptid_new),
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);
		apv.responseCodeValidation(cancelResponse, 200);
		apv.responseTimeValidation(cancelResponse);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test10PastAppointmentsByPageGET() throws IOException {
		
		String patientid=propertyData.getProperty("pastappt.patient.id.pm.ng");

		Response response = postAPIRequest.pastAppointmentsByPage(baseurl,
				headerConfig.HeaderwithToken(accessToken), practiceid,patientid);
			
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());

		String appmntid = js.getString("content[0].appointmentTypes.name");
		log("Appointment Id- " + appmntid);

		String location = js.getString("content[0].location.name");
		log("Location Name- " + location);

		String book = js.getString("content[0].book.resourceName");
		log("Resource Name - " + book);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test11TimeZoneCodePost() throws IOException {

		String patientid=propertyData.getProperty("pastappt.patient.id.pm.ng");
		
		Response response = postAPIRequest.timeZoneCode(baseurl,
				payloadPatientMod.timeZnCodeForDatePayload(), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		String localtimezonecode = js.getString("locTimeZoneCode");
		assertEquals(localtimezonecode, propertyData.getProperty("location.timezone.code"), "TimeZoneCode is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test12UpcomingAppointmentsByPageGET() throws IOException {

		String patientid = propertyData.getProperty("pastappt.patient.id.pm.ng");

		Response response = postAPIRequest.upcomingAppointmentsByPage(baseurl,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test13AppointmentTypesByRulePost_NewPatient() throws IOException {

		String patientid = null;

		Response response = postAPIRequest.appointmentTypesByRule(baseurl,
				payloadPatientMod.appointmentTypesByrulePayload(), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test14AppointmentTypesByRulePost_ExPatient() throws IOException {

		Response response = postAPIRequest.appointmentTypesByRule(baseurl,
				payloadPatientMod.appointmentTypesByrulePayload_New(), headerConfig.HeaderwithToken(accessToken),
				practiceid, testData.getPatientIdAppointmentTypesRule());

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test15AppTypeByNextAvailablePost_NewPatient() throws IOException {

		String patientid = null;

		String apptid = propertyData.getProperty("nextavailable.apptid.pm.ng");
		String locationid = propertyData.getProperty("nextavailable.locationid.pm.ng");

		String b = payloadPatientMod.appTypeByNextAvailablePayload(locationid, apptid);

		Response response = postAPIRequest.apptTypeNextAvailable(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray jo = new JSONArray(response.asString());
		int apptid_actual = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");

		log("Next Available slot for Appointment Type- " + apptid_actual + " is- " + nextavailableslot);
		assertEquals(apptid_actual, Integer.parseInt(apptid), "location practice id is wrong");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test16AppTypeByNextAvailablePost_ExistingPatient() throws IOException {
		
		String patientid=propertyData.getProperty("patient.id.pm.ng");
		String apptid = propertyData.getProperty("nextavailable.apptid.pm.ng");
		String locationid = propertyData.getProperty("nextavailable.locationid.pm.ng");

		String b = payloadPatientMod.appTypeByNextAvailablePayload_Exe(locationid, apptid);

		Response response = postAPIRequest.apptTypeNextAvailable(baseurl, b,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);			

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray jo = new JSONArray(response.asString());
		int apptid_actual = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");

		log("Next Available slot for Appointment Type- " + apptid_actual + " is- " + nextavailableslot);
		assertEquals(apptid_actual, Integer.parseInt(apptid), "location practice id is wrong");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test17CancelStatus_New() throws IOException {

		String patientid = null;
		String extappt = propertyData.getProperty("cancelstatus.ext.apptype.pm.ng");
		String appcat = propertyData.getProperty("cancelstatus.cat.apptype.pm.ng");

		String b = payloadPatientMod.cancelStatusPayload(extappt, appcat);

		Response response = postAPIRequest.cancelStatus(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancel18Status_Ex() throws IOException {
		
		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String extappt = propertyData.getProperty("cancelstatus.ext.apptype.pm.ng");
		String appcat = propertyData.getProperty("cancelstatus.cat.apptype.pm.ng");
		
		String b = payloadPatientMod.cancelStatusPayload(extappt, appcat);

		Response response = postAPIRequest.cancelStatus(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

}
