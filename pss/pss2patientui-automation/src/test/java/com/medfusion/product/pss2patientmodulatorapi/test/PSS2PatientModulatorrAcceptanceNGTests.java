// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.io.IOException;
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
import com.medfusion.product.pss2patientapi.payload.PayloadPssPMNG;
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

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptype = propertyData.getProperty("availableslot.apptid.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate, bookid, locationid, apptype);

		Response response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(2).getString("date");

		String c = payloadPatientMod.scheduleAppointmentPayload(slotid, date, time, bookid, locationid, apptype);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		String extapptid = apv.responseKeyValidationJson(schedResponse, "extApptId");

		String d = payloadPatientMod.allowOnlineCancellationPayload(extapptid);
		log("Practice Id is-" + practiceid);

		Response canresponse = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);
		apv.responseCodeValidation(canresponse, 200);
		apv.responseTimeValidation(canresponse);

		assertEquals(apv.responseKeyValidationJson(canresponse, "checkCancelAppointmentStatus"), "true");
		assertEquals(apv.responseKeyValidationJson(canresponse, "preventScheduling"), null);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test05getAppointment() throws IOException {

		String extapptid = propertyData.getProperty("extappt.id.pm.ng");

		Response response = postAPIRequest.appointment(baseurl, headerConfig.defaultHeader(), practiceid, extapptid);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "patientId");
		apv.responseKeyValidationJson(response, "confirmationNo");
		apv.responseKeyValidationJson(response, "bookName");
		apv.responseKeyValidationJson(response, "locationName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test06getAppointmentForIcs() throws IOException {

		String extapptid = propertyData.getProperty("extappt.id.pm.ng");

		Response response = postAPIRequest.appointmentForIcs(baseurl, headerConfig.defaultHeader(), practiceid,
				extapptid);
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

		Response response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
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

		Response response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test09Sched_Resc_Cancel_AppointmentPost() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		Response response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
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
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());
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
				headerConfig.HeaderwithToken(accessToken), practiceid, testData.getPatientIdAvailableSlots(),
				testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");

		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extapptid_new), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(cancelResponse, 200);
		apv.responseTimeValidation(cancelResponse);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test10PastAppointmentsByPageGET() throws IOException {

		String patientid = propertyData.getProperty("pastappt.patient.id.pm.ng");

		Response response = postAPIRequest.pastAppointmentsByPage(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

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

		String patientid = propertyData.getProperty("patient.id.pm.ng");

		Response response = postAPIRequest.timeZoneCode(baseurl, payloadPatientMod.timeZnCodeForDatePayload(),
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		String localtimezonecode = js.getString("locTimeZoneCode");
		assertEquals(localtimezonecode, propertyData.getProperty("location.timezone.code"), "TimeZoneCode is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test12UpcomingAppointmentsByPageGET() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");

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

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String fn = propertyData.getProperty("demographics.fn.pm.ng");
		String ln = propertyData.getProperty("demographics.ln.pm.ng");
		String email = propertyData.getProperty("demographics.email.pm.ng");
		String phone = propertyData.getProperty("demographics.phone.pm.ng");
		String zip = propertyData.getProperty("demographics.zip.pm.ng");

		Response response = postAPIRequest.appointmentTypesByRule(baseurl,
				payloadPatientMod.appointmentTypesByrulePayload_New(fn, ln, email, phone, zip),
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);

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

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String apptid = propertyData.getProperty("nextavailable.apptid.pm.ng");
		String locationid = propertyData.getProperty("nextavailable.locationid.pm.ng");

		String b = payloadPatientMod.appTypeByNextAvailablePayload_Exe(locationid, apptid);

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
	public void test17CancelStatus_New() throws IOException {

		String patientid = null;
		String extappt = propertyData.getProperty("cancelstatus.ext.apptype.pm.ng");
		String appcat = propertyData.getProperty("cancelstatus.cat.apptype.pm.ng");
		String apptid = propertyData.getProperty("nextavailable.apptid.pm.ng");
		String d1 = propertyData.getProperty("numberofdays.pm.ng");
		String d2 = propertyData.getProperty("preventscheduling.pm.ng");

		String b = payloadPatientMod.cancelStatusPayload(extappt, appcat, apptid, d1, d2);

		Response response = postAPIRequest.cancelStatus(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test18CancelStatus_Ex() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String extappt = propertyData.getProperty("cancelstatus.ext.apptype.pm.ng");
		String appcat = propertyData.getProperty("cancelstatus.cat.apptype.pm.ng");
		String apptid = propertyData.getProperty("nextavailable.apptid.pm.ng");
		String d1 = propertyData.getProperty("numberofdays.pm.ng");
		String d2 = propertyData.getProperty("preventscheduling.pm.ng");

		String b = payloadPatientMod.cancelStatusPayload(extappt, appcat, apptid, d1, d2);

		Response response = postAPIRequest.cancelStatus(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test19GetCommentDetails_New() throws IOException {

		String patientid = null;
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.commentDetailsPayload(apptid, bookid, locationid);

		Response response = postAPIRequest.commentDetails(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test20getCommentDetails_Ex() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.commentDetailsPayload(apptid, bookid, locationid);

		Response response = postAPIRequest.commentDetails(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test21BooksByNextAvailablePost_NewPatient() throws IOException {

		String patientid = null;
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.booksByNextAvailablePayload(bookid, locationid, apptid);

		Response response = postAPIRequest.booksBynextAvailable(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		JSONArray jo = new JSONArray(response.asString());
		int bookid_actual = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");
		log("Next Available slot for book " + bookid_actual + " is- " + nextavailableslot);
		assertEquals(bookid_actual, Integer.parseInt(bookid), "Book id is wrong");
		assertNotNull(nextavailableslot);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test22BooksByNextAvailablePost_ExistingPatient() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.booksByNextAvailablePayload(bookid, locationid, apptid);

		Response response = postAPIRequest.booksBynextAvailable(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		JSONArray jo = new JSONArray(response.asString());
		int bookid_actual = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");
		log("Next Available slot for book " + bookid_actual + " is- " + nextavailableslot);
		assertEquals(bookid_actual, Integer.parseInt(bookid), "Book id is wrong");
		assertNotNull(nextavailableslot);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test23BooksByRulePost_NewPatient() throws IOException {

		String patientid = null;
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.booksByRulePayload(apptid, locationid);

		Response response = postAPIRequest.booksByRule(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		JsonPath js = new JsonPath(response.asString());

		String displayname = js.getString("books[0].displayName");
		String bookid_actual = js.getString("books[0].id");

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		assertEquals(bookid_actual, bookid, "Book id is not matching with expected id");
		assertEquals(displayname, propertyData.getProperty("displayname.book.pm.ng"), "Display name is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test24BooksByRulePost_ExistingPatient() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.booksByRulePayload(apptid, locationid);

		Response response = postAPIRequest.booksByRule(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		JsonPath js = new JsonPath(response.asString());

		String displayname = js.getString("books[0].displayName");
		String bookid_actual = js.getString("books[0].id");

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		assertEquals(bookid_actual, bookid, "Book id is not matching with expected id");
		assertEquals(displayname, propertyData.getProperty("displayname.book.pm.ng"), "Display name is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test25CancellationReasonGET() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		Response response = postAPIRequest.cancellationReason(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "type");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test16CancellationReasonGETInvalid() throws IOException {

		Response response = postAPIRequest.cancellationReason(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid, "111111111");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "type");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test27RescheduleReasonGET() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");

		Response response = postAPIRequest.rescheduleReason(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "displayName");
		apv.responseKeyValidation(response, "type");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test28InsuranceCarrierGET() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");

		Response response = postAPIRequest.insuranceCarrier(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test28InsuranceCarrierGET_New() throws IOException {

		String patientid = null;

		Response response = postAPIRequest.insuranceCarrier(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test29ApptDetailFromGuidGET() throws IOException {

		String guidid = propertyData.getProperty("appt.detail.guid.id.pm.ng");

		Response response = postAPIRequest.apptDetailFromGuid(baseurl, headerConfig.HeaderwithToken(accessToken),
				guidid, practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "appointmentType.name");
		apv.responseKeyValidationJson(response, "book.displayName");
		apv.responseKeyValidationJson(response, "location.displayName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test30getDetails() throws IOException {

		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");

		String b = payloadPatientMod.getDetailsPayload(bookid);
		Response response = postAPIRequest.getDetails(baseurl, headerConfig.HeaderwithToken(accessToken), practiceid,
				b);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test31ValidateProviderLinkPost_ExistingPatient() throws IOException {

		String fn = propertyData.getProperty("demographics.fn.pm.ng");
		String ln = propertyData.getProperty("demographics.ln.pm.ng");
		String dob = propertyData.getProperty("demographics.dob.pm.ng");
		String gender = propertyData.getProperty("demographics.gender.pm.ng");
		String email = propertyData.getProperty("demographics.email.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String patientid = propertyData.getProperty("patient.id.pm.ng");

		String b = payloadPatientMod.validateProviderLinkPayload(fn, ln, dob, gender, email, bookid);

		Response response = postAPIRequest.validateProviderLink(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		JsonPath js = new JsonPath(response.asString());
		int id = js.getInt("id");
		log("Provider id-" + js.getString("id"));
		log("Provider Name -" + js.getString("displayName"));

		String linkId = Integer.toString(id);
		assertEquals(linkId, bookid, "Book id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test32ValidateProviderLinkPost_NewPatient() throws IOException {

		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");

		String b = payloadPatientMod.validateProviderLinkPayload_New(bookid, locationid);
		String patientid = null;
		Response response = postAPIRequest.validateProviderLink(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		JsonPath js = new JsonPath(response.asString());
		int id = js.getInt("id");
		log("Provider id-" + js.getString("id"));
		log("Provider Name -" + js.getString("displayName"));

		String linkId = Integer.toString(id);
		assertEquals(linkId, bookid, "Link id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test33ApptDetailFromGuidGET() throws IOException {

		String guidid = propertyData.getProperty("appt.detail.guid.id.pm.ng");

		Response response = postAPIRequest.apptDetailFromGuid(baseurl, headerConfig.HeaderwithToken(accessToken),
				guidid, practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "appointmentType.name");
		apv.responseKeyValidationJson(response, "book.displayName");
		apv.responseKeyValidationJson(response, "location.displayName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test34getDetails() throws IOException {

		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");

		String b = payloadPatientMod.getDetailsPayload(bookid);

		Response response = postAPIRequest.getDetails(baseurl, headerConfig.HeaderwithToken(accessToken), practiceid,
				b);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "book.id");
		apv.responseKeyValidationJson(response, "book.displayName");
		apv.responseKeyValidationJson(response, "book.acceptComment");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test35PracticeFromGuidAnonymousGET() throws IOException {

		String anonymousguid = propertyData.getProperty("anonymous.guid.pm.ng");
		Response response = postAPIRequest.practiceFromGuid(baseurl, headerConfig.defaultHeader(), anonymousguid);

		JsonPath js = new JsonPath(response.asString());
		String practiceid_actual = js.get("practiceId");
		String practicename = js.getString("name");

		log("Practice Id-" + practiceid_actual);
		log("Practice Name-" + practicename);

		assertEquals(practiceid_actual, practiceid, "Ext Practice Id is wrong");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test36LinksValueGuidGET() throws IOException {

		String guidid = propertyData.getProperty("appt.detail.guid.id.pm.ng");
		Response response = postAPIRequest.linksValueGuid(baseurl, headerConfig.defaultHeader(), guidid);

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));

		assertEquals(practiceId, practiceid, "practice Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test37LinksValueGuidAndPracticeGET() throws IOException {

		String guidid = propertyData.getProperty("appt.detail.guid.id.pm.ng");

		Response response = postAPIRequest.linksValueGuidAndPractice(baseurl, headerConfig.defaultHeader(), guidid,
				practiceid);

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));
		assertEquals(practiceId, practiceid, "Practice Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test38LinksValueGuidGET() throws IOException {

		String guidid = propertyData.getProperty("appt.detail.guid.id.pm.ng");

		Response response = postAPIRequest.linksValueGuid(baseurl, headerConfig.defaultHeader(), guidid);

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));

		assertEquals(practiceId, practiceid, "practice Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test39LinksValueGuidAndPracticeGET() throws IOException {

		String guidid = propertyData.getProperty("appt.detail.guid.id.pm.ng");

		Response response = postAPIRequest.linksValueGuidAndPractice(baseurl, headerConfig.defaultHeader(), guidid,
				practiceid);

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));
		assertEquals(practiceId, practiceid, "Practice Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test40LocationsByNextAvailablePost_ExistingPatient() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.locationsByNextAvailablePayload(bookid, locationid, apptid);

		Response response = postAPIRequest.locationsByNextAvailable(baseurl, b,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);

		JSONArray jo = new JSONArray(response.asString());
		int locationid_actual = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");

		log("Next Available slot for Location " + locationid + " is- " + nextavailableslot);
		assertEquals(locationid_actual, Integer.parseInt(locationid), "location practice id is wrong");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test41LocationsByNextAvailablePost_NewPatient() throws IOException {

		String patientid = null;

		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.locationsByNextAvailablePayload(bookid, locationid, apptid);

		Response response = postAPIRequest.locationsByNextAvailable(baseurl, b,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid);

		JSONArray jo = new JSONArray(response.asString());
		int locationid_actual = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");

		log("Next Available slot for Location " + locationid + " is- " + nextavailableslot);
		assertEquals(locationid_actual, Integer.parseInt(locationid), "location practice id is wrong");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test43LocationsByRulePost_New() throws IOException {

		String patientid = null;

		String fn = "FirstNameNG";
		String ln = "LastNameNG";
		String dob = propertyData.getProperty("demographics.dob.pm.ng");
		String gender = "F";
		String email = "Shweta.Sontakke@CrossAsyst.com";
		String phone = propertyData.getProperty("demographics.phone.pm.ng");
		String zipcode = propertyData.getProperty("demographics.zip.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.locationsByRulePayload(apptid, dob, fn, ln, gender, email, phone, zipcode);

		Response response = postAPIRequest.locationsByRule(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");
		ParseJSONFile.getKey(jsonobject, "locTimeZone");

		apv.responseKeyValidationJson(response, "locations.id");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test42LocationsByRulePost_Existing() throws IOException {

		String patientid = propertyData.getProperty("patient.id.pm.ng");
		String fn = propertyData.getProperty("demographics.fn.pm.ng");
		String ln = propertyData.getProperty("demographics.ln.pm.ng");
		String dob = propertyData.getProperty("demographics.dob.pm.ng");
		String gender = propertyData.getProperty("demographics.gender.pm.ng");
		String email = propertyData.getProperty("demographics.email.pm.ng");
		String phone = propertyData.getProperty("demographics.phone.pm.ng");
		String zipcode = propertyData.getProperty("demographics.zip.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.locationsByRulePayload(apptid, dob, fn, ln, gender, email, phone, zipcode);

		Response response = postAPIRequest.locationsByRule(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");
		ParseJSONFile.getKey(jsonobject, "locTimeZone");

		apv.responseKeyValidationJson(response, "locations.id");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test44PracticeFromGuidLoginlessGET() throws IOException {

		String loginlessguid = propertyData.getProperty("loginless.guid.pm.ng");
		Response response = postAPIRequest.practiceFromGuidLoginless(baseurl, headerConfig.defaultHeader(),
				loginlessguid);

		JsonPath js = new JsonPath(response.asString());

		String practiceid_actual = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		assertEquals(practiceid_actual, practiceid, "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGuidForLogoutPatientGET() throws IOException {

		String guidid = propertyData.getProperty("loginless.guid.pm.ng");

		Response response = postAPIRequest.guidForLogoutPatient(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid);
		JsonPath js = new JsonPath(response.asString());
		String guidid_actual = js.get("guid");
		assertEquals(guidid_actual, guidid, "guid Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidLoginlessGET() throws IOException {

		String guidid = propertyData.getProperty("loginless.guid.pm.ng");

		Response response = postAPIRequest.practiceFromGuidLoginless(baseurl, headerConfig.defaultHeader(), guidid);

		JsonPath js = new JsonPath(response.asString());

		String guid_actual = js.get("guid");
		log("Practice Name -" + js.getString("name"));
		log("Practice Id -" + js.getString("practiceId"));
		assertEquals(guid_actual, guidid, "Guid Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTokenForLoginlessGET() throws IOException {

		String apptguid = propertyData.getProperty("appt.detail.guid.id.pm.ng");

		Response response = postAPIRequest.tokenForLoginless(testData.getBasicURI(), headerConfig.defaultHeader(),
				apptguid);

		JsonPath js = new JsonPath(response.asString());

		String guid_actual = js.get("guid");
		log("GUID ID -" + js.getString("guid"));
		log("Practice Id -" + js.getString("practiceId"));
		assertEquals(guid_actual, apptguid, "Guid Id is wrong");
	}

}
