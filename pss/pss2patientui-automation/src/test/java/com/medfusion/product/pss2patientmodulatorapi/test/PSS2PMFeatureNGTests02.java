// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestPMNG;
import com.medfusion.product.pss2patientapi.payload.PayloadAM02;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadPM02;
import com.medfusion.product.pss2patientapi.payload.PayloadPssPMNG;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;

public class PSS2PMFeatureNGTests02 extends BaseTestNG {

	public static HeaderConfig headerConfig;
	public static PSSPropertyFileLoader propertyData;

	public static Appointment testData;
	public static PostAPIRequestPMNG postAPIRequest;
	public static PayloadPssPMNG payloadPatientMod;
	public static PayloadPM02 payloadPM02;
	public static String accessToken;
	public static String practiceid;
	public static String baseurl;
	public PSSPatientUtils pssPatientUtils;
	public PSS2PatientModulatorrAcceptanceNGTests pmng;
	
	public static PayloadAdapterModulator payloadAM;
	public static PayloadAM02 payloaAM02;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	public static String openToken;
	public static String practiceId;

	APIVerification apv;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUpPM() throws IOException {

		log("Set the Authorization for Patient Modulator");

		headerConfig = new HeaderConfig();
		propertyData = new PSSPropertyFileLoader();
		testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		postAPIRequest = new PostAPIRequestPMNG();
		payloadPatientMod = new PayloadPssPMNG();

		payloadPM02 = new PayloadPM02();

		baseurl = propertyData.getProperty("base.url.pm.ng");
		practiceid = propertyData.getProperty("mf.practice.id.ng");
		accessToken = postAPIRequest.createToken(baseurl, practiceid);
		pssPatientUtils = new PSSPatientUtils();
		apv = new APIVerification();
		pmng = new PSS2PatientModulatorrAcceptanceNGTests();
		log("Base URL for Patient Modulator - " + baseurl);
	}
	
	public void setUpAM(String practiceId1, String userID) throws IOException {
		payloaAM02 = new PayloadAM02();
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();
		practiceId = practiceId1;
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId,
				payloaAM02.openTokenPayload(practiceId, userID));
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01AnnouncementByLanguageGET() throws IOException {
		
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;

		response = postAPIRequest.announcementByLanguage(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReserveForSameDay() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng02"), propertyData.getProperty("mf.authuserid.am.ng02"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String adapPayload = payloaAM02.reserveForSameDay("s");

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceId, adapPayload, "206528");
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String availableSLotsNG = payloadPM02.avaliableSlot(startDate);
		String patientID = propertyData.getProperty("patient.id.feature.pm.ng");
		response = postAPIRequest.availableSlots(baseurl, availableSLotsNG, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JSONArray arr1 = new JSONArray(response.getBody().asString());
		for (int i = 0; i < arr1.length(); i++) {
			JSONObject obj = arr1.getJSONObject(i);
			log("Validated key-> " + "date" + " value is- " + obj.getString("date"));
			assertEquals(startDate, obj.getString("date"));
		}
		String adapPayloadforReset = payloaAM02.reserveForSameDay("n");
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceId, adapPayloadforReset, "206528");
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void tesApptTypeByRuleBOff() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng02"), propertyData.getProperty("mf.authuserid.am.ng02"));
		Response response;
		
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		String patientid = null;

		response = postAPIRequest.appointmentTypesByRule(baseurl,
				payloadPM02.appointmentTypesByrulePayload(), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowOnlineCancel() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPatientMod.scheduleAppointmentPayload(slotid, date, time,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		

		String d = payloadPM02.allowOnlineCancelPayload(extApptId);
		response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelApptWhenCancelReasonOff() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String cancelReasonPayload=payloaAM02.cancelReasonOffPyaload(false);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, cancelReasonPayload);
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPatientMod.scheduleAppointmentPayload(slotid, date, time,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		

		String d = payloadPM02.allowOnlineCancelPayload(extApptId);
		response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentWithoutReasonPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventRescheduleOnCancel() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr;
		int l;
		arr = new JSONArray(response.body().asString());
		l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloaAM02.rescheduleOnCancelPyaload("10");
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.onlinecancel.pm.ng");

		String b = payloadPM02.cancelStatusPyaload();

		response = postAPIRequest.cancelStatus(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		
		apv.responseCodeValidation(response, 200);
		assertEquals("false",apv.responseKeyValidationJson(response, "checkCancelAppointmentStatus"));
		
		adminPayload=payloaAM02.rescheduleOnCancelPyaload("0");
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		arr = new JSONArray(response.body().asString());
		l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloaAM02.turnOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);
		

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventRescheduleOnCancelBookOff() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		
		adminPayload=payloaAM02.rescheduleOnCancelPyaload("10");
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.onlinecancel.pm.ng");

		String b = payloadPM02.cancelStatusPyaload();

		response = postAPIRequest.cancelStatus(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		
		apv.responseCodeValidation(response, 200);
		assertEquals("false",apv.responseKeyValidationJson(response, "checkCancelAppointmentStatus"));
		
		adminPayload=payloaAM02.rescheduleOnCancelPyaload("0");
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelApptWithCancelReasonOther_Spanish() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String cancelReasonPayload=payloaAM02.cancelReasonOffPyaload(true);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, cancelReasonPayload);
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPatientMod.scheduleAppointmentPayload(slotid, date, time,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithTokenES(accessToken), practiceid, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		

		String d = payloadPM02.allowOnlineCancelPayload(extApptId);
		response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithTokenES(accessToken), practiceid, patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentWithReasonOtherPayload(extApptId), headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHideSlotDuration() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		int leadTime=3;		
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloaAM02.bookappointmenttypePyaload(leadTime);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String currentdate=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String futuredate=pssPatientUtils.addDaysToDate(currentdate, Integer.toString(leadTime), "MM/dd/yyyy");
		
		log("Future Date -"+futuredate);

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		assertEquals(date, futuredate, "Lead Time is not working properly");
		
		adminPayload=payloaAM02.bookappointmenttypePyaload(0);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	
	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptDuration() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		JSONArray arr;
		int apptDuration=30;		
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloaAM02.apptDurationPyaload(apptDuration);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String currentdate=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String b = payloadPatientMod.availableslotsPayload(currentdate,bookid,locationid,apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr= new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		String nextSlotInApptDuration=pssPatientUtils.addToTime(time, apptDuration);
		
		String schedPayload= payloadPatientMod.scheduleAppointmentPayload(slotid, date, time, bookid, locationid, apptid);
		
		response=postAPIRequest.scheduleAppointment(baseurl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		
		arr= new JSONArray(response.body().asString());
		String slotid2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date2 = arr.getJSONObject(0).getString("date");
		
		log("slotTime-" + time2);
		log("slotId- " + slotid2);
		log("Date-" + date2);
		
		logStep("Slot Booked on fits attenpt- "+time);
		logStep("Slot will book on second attempt- "+time2);
		logStep("Expected slot in Appt Duration is - "+nextSlotInApptDuration);

		assertEquals(time2, nextSlotInApptDuration);
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);		
				
		adminPayload=payloaAM02.apptDurationPyaload(0);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMergeSlot() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		JSONArray arr;
		int slotCount=3;		
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String currentdate=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String b = payloadPatientMod.availableslotsPayload(currentdate,bookid,locationid,apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr= new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		
		adminPayload=payloaAM02.mergeSlotPyaload(slotCount);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		int mergeslottime=slotCount*5;
		String nextSlotInApptDuration=pssPatientUtils.addToTime(time, mergeslottime);		

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		
		arr= new JSONArray(response.body().asString());
		String slotid2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(1).getString("slotId");
		String time2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(1).getString("slotTime");
		String date2 = arr.getJSONObject(0).getString("date");
		
		log("slotTime-" + time2);
		log("slotId- " + slotid2);
		log("Date-" + date2);
		
		logStep("Slot Booked on fits attenpt- "+time);
		logStep("Slot will book on second attempt- "+time2);
		logStep("Expected slot in Appt Duration is - "+nextSlotInApptDuration);

		assertEquals(time2, nextSlotInApptDuration);

				
		adminPayload=payloaAM02.mergeSlotPyaload(1);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsByNextAvailable_BookOff_NewPatient() throws Exception {

		String patientid = null;
		Response response;		
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr;
		int l;
		arr = new JSONArray(response.body().asString());
		l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		

		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.locationsByNextAvailablePayload(null, locationid, apptid);

		response = postAPIRequest.locationsByNextAvailable(baseurl, b,
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
	public void testAllowOnlineCancelWhenSpanishOFf() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		
		String practiceP=payloaAM02.practicePyaload();
		response=postAPIRequestAM.practicePost(practiceId, practiceP);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPatientMod.scheduleAppointmentPayload(slotid, date, time,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		

		String d = payloadPM02.allowOnlineCancelPayload(extApptId);
		response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void toekn() throws Exception {
		log("Generate Token");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayONBookOff() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng02"), propertyData.getProperty("mf.authuserid.am.ng02"));
		Response response;
		String adminPayload;
		JSONArray arr;
		
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		arr= new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		adminPayload = payloaAM02.apptTypeConfgPyaload(true);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceId, adminPayload, propertyData.getProperty("availableslot.apptid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String currentdate=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, null,
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		assertEquals(date, currentdate, "Accept Same Day is not working properly");
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayOffBookOff() throws Exception {
		
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng02"), propertyData.getProperty("mf.authuserid.am.ng02"));
		Response response;
		String adminPayload;
		JSONArray arr;

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		adminPayload = payloaAM02.apptTypeConfgPyaload(false);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceId, adminPayload,
				propertyData.getProperty("availableslot.apptid.pm.ng"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceId,
				propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		currentdate = pssPatientUtils.addDaysToDate(currentdate, "1", "MM/dd/yyyy");
		log("currentdate - " + currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, null,
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		assertEquals(date, currentdate, "Accept Same Day is not working properly");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSchedApptWithDecisionTreeBookOff() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		String adminPayload;
		JSONArray arr;
		
		logStep("Turn on the decision tree configuration");
		adminPayload=payloaAM02.confgSaveAdmin("showCategory", true);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		arr= new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.onlinecancel.pm.ng");
		String currentdate=pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		log("currentdate - "+currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, null,
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		String schedPayload= payloadPM02.SchedWithDecisionTreePyaload(date, time, null);
		
		response=postAPIRequest.scheduleAppointment(baseurl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);
		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
		
		logStep("Turn off the decision tree configuration");
		adminPayload=payloaAM02.confgSaveAdmin("showCategory", false);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSchedApptWithoutDecisionTreeBookOff() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		JSONArray arr;
		
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		arr= new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.onlinecancel.pm.ng");
		String currentdate=pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		log("currentdate - "+currentdate);
		
		String location=propertyData.getProperty("availableslot.locationid.pm.ng");
		String appttype=propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.availableslotsPayload(currentdate, null,
				location,
				appttype);

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		String schedPayload=payloadPatientMod.scheduleAppointmentPayload(slotid, currentdate, time, null, location, appttype);
		
		response=postAPIRequest.scheduleAppointment(baseurl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);
		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSchedApttWithDecisionTreeBookOn_Spanish() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPM02.SchedWithDecisionTreePyaload(date, time,
				propertyData.getProperty("availableslot.bookid.pm.ng"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithTokenES(accessToken), practiceid, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentWithReasonOtherPayload(extApptId), headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientStatusInactiveAlert_Spanish() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		String patientId = propertyData.getProperty("patient.id.inactive.pm.ng");
		String patientStatus = propertyData.getProperty("patientstatus.id.ng");

		adminPayload = payloaAM02.inactivePatientPyaload(Integer.parseInt(patientStatus));

		response = postAPIRequestAM.lockoutPost(practiceId, adminPayload, "/lockout");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		response = postAPIRequest.patientDemographics(baseurl, headerConfig.HeaderwithTokenES(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		String showAlerts = apv.responseKeyValidationJson(response, "showAlert");

		boolean showAlertsValue = Boolean.parseBoolean(showAlerts);
		assertEquals(showAlertsValue, true, "Alert is not getting displayed so test case failed");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientStatusActiveAlert() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		String patientId = propertyData.getProperty("patient.id.active.pm.ng");
		adminPayload = payloaAM02.activeLockoutPyaload();

		response = postAPIRequestAM.lockoutPost(practiceId, adminPayload, "/lockout");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String activeStatusID =apv.responseKeyValidationJson(response, "id");
		int idActiveStatus=Integer.parseInt(activeStatusID);

		response = postAPIRequest.patientDemographics(baseurl, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		String message = apv.responseKeyValidationJson(response, "message");
		
		String expectedMessage=propertyData.getProperty("activestatus.message.pm.ng");

		assertEquals(message, expectedMessage, "Active Alert message is not default message");
		
		response=postAPIRequestAM.deleteLockoutById(practiceId, idActiveStatus);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptStackingOverbooking() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		JSONArray arr;
		String adminPayload;
		
		adminPayload=payloaAM02.overbookingPyaload(true);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String patientId01 = propertyData.getProperty("patient.id.pm.ng");
		String patientId02 = propertyData.getProperty("patient.id.overbooking.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng03"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId01);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr= new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPatientMod.scheduleAppointmentPayload(slotid, date, time,
				propertyData.getProperty("availableslot.bookid.pm.ng03"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId01, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId02);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		String time2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date2 = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		assertEquals(date2, date);
		assertEquals(time2, time);	

		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId01);
		apv.responseCodeValidation(cancelResponse, 200);
		
		adminPayload=payloaAM02.overbookingPyaload(true);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventBackToBack() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		JSONArray arr;
		int slotsize=5;
		int apptDuration= 2*slotsize;
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloaAM02.preventBackToBackPyaload(true);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String currentdate=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String b = payloadPatientMod.availableslotsPayload(currentdate,bookid,locationid,apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr= new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		String nextSlotInApptDuration=pssPatientUtils.addToTime(time, apptDuration);
		
		String schedPayload= payloadPatientMod.scheduleAppointmentPayload(slotid, date, time, bookid, locationid, apptid);
		
		response=postAPIRequest.scheduleAppointment(baseurl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		
		arr= new JSONArray(response.body().asString());
		String slotid2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date2 = arr.getJSONObject(0).getString("date");
		
		log("slotTime-" + time2);
		log("slotId- " + slotid2);
		log("Date-" + date2);
		
		logStep("Slot Booked on fits attenpt- "+time);
		logStep("Slot will book on second attempt- "+time2);
		logStep("Expected slot in Appt Duration is - "+nextSlotInApptDuration);

		assertEquals(time2, nextSlotInApptDuration);
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);		
				
		adminPayload=payloaAM02.preventBackToBackPyaload(false);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMaxPerDay() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		JSONArray arr;
		int maxPerDay=1;
	
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloaAM02.maxPerDayPyaload(maxPerDay);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String currentdate=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String b = payloadPatientMod.availableslotsPayload(currentdate,bookid,locationid,apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		arr= new JSONArray(response.body().asString());
		
		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		String schedPayload= payloadPatientMod.scheduleAppointmentPayload(slotid, date, time, bookid, locationid, apptid);
		
		response=postAPIRequest.scheduleAppointment(baseurl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);		

		arr= new JSONArray(response.body().asString());
		int mainlength = arr.length();
		ArrayList<String> lis= new ArrayList<String>();
		
		for(int i=0; i<mainlength; i++) {
			
			String datee=arr.getJSONObject(i).getString("date");
			
			lis.add(datee);
		}
		
		boolean stringExist=lis.contains(date);
		log("Actual ......."+stringExist);
		assertEquals(false, stringExist);	

		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(cancelResponse, 200);		
				
		adminPayload=payloaAM02.maxPerDayPyaload(0);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventScheduling() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		int preventSchedDays=30;
	
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloaAM02.preventSchedPyaload(preventSchedDays);
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, bookid, locationid, apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 412);
		apv.responseTimeValidation(response);
		String errorMessage=apv.responseKeyValidationJson(response, "message");
		
		assertTrue(errorMessage.contains("Prevent Scheduling"));
		
		response=postAPIRequest.announcementByName(baseurl, headerConfig.HeaderwithToken(accessToken), practiceId, "ARP");
		apv.responseCodeValidation(response, 200);	
				
		adminPayload=payloaAM02.preventSchedPyaload(0);
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteDefaultNG() throws Exception {
		log("Verify complete history is searched if pre-requisite set to default");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		
		String name = propertyData.getProperty("prerequisite.appointmenttype.name.ng");
		String extAppID = propertyData.getProperty("prerequisite.appointmenttype.extapp.id.ng");
		String catId = propertyData.getProperty("prerequisite.appointmenttype.cat.id.ng");
		String catName = propertyData.getProperty("prerequisite.appointmenttype.cat.name.ng");
		String preReqAppId = propertyData.getProperty("appointment.id.prerequisite.ng");

		response = postAPIRequestAM.preRequisiteAppointmenttypes(practiceId, preReqAppId,
				payloadAM.preRequisiteAppointmentTypesDefualtNG(name, extAppID, catId, catName));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);

		String appName = propertyData.getProperty("appointmenttypefor.prereqname.ng");
		String patientId = propertyData.getProperty("prerequisite.appointmenttype.patientid.ng");

		String apptTypeRule = payloadPM02.preReqApptTypeRulePyaload();

		response = postAPIRequest.appointmentTypesByRule(baseurl, apptTypeRule,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		
		ArrayList<String> ls = new ArrayList<String>();
		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String appTypeNameActual;
		
		for (int i = 0; i < len; i++) {
			appTypeNameActual = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			ls.add(appTypeNameActual);
		}

		boolean stringExist = ls.contains(appName);
		assertEquals(true, stringExist);

		response = postAPIRequestAM.preRequisiteAppById(practiceId, preReqAppId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr1 = new JSONArray(response.body().asString());
		int l1 = arr1.length();
		int id = 0;
		log("Length is- " + l1);
		for (int i = 0; i < l1; i++) {
			id = arr1.getJSONObject(i).getInt("id");
		}
		String s = Integer.toString(id);
		log("preRequisiteApp Id is for Delete " + s);
		response = postAPIRequestAM.preRequisiteAppDeleteById(practiceId, s);
		apv.responseCodeValidation(response, 200);
	}
	

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteWithNoOfDaysNG() throws Exception {
		log("Verify Number of Days Configured in Admin UI Then re-requisites Appointment type are available within configured period");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Set up the API authentication");

		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone = apv.responseKeyValidationJson(response, "practiceTimezone");
		testData.setCurrentTimeZone(timezone);
		String pastDate = propertyData.getProperty("pastappointment.date.ng");
		String noofDays = propertyData.getProperty("no.ofdays.prereq.ng");
		String name = propertyData.getProperty("prerequisite.appointmenttype.name.ng");
		String extAppID = propertyData.getProperty("prerequisite.appointmenttype.extapp.id.ng");
		String catId = propertyData.getProperty("prerequisite.appointmenttype.cat.id.ng");
		String catName = propertyData.getProperty("prerequisite.appointmenttype.cat.name.ng");
		String preReqAppId = propertyData.getProperty("appointment.id.prerequisite.ng");
		String prereqId = propertyData.getProperty("prerequisite.id.ng");
		int preId = Integer.parseInt(prereqId);
		logStep("Setting For No. Of Days For PreRequisite Appointment type");
		response = postAPIRequestAM.preRequisiteAppointmenttypes(practiceId, preReqAppId,
				payloadAM.preRequisiteAppointmentTypesNoOfDaysNG(name, extAppID, catId, catName, noofDays, preId));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);
		String appName = propertyData.getProperty("appointmenttypefor.prereqname.ng");
		
		
		String patientId = propertyData.getProperty("prerequisite.appointmenttype.patientid.ng");

		String apptTypeRule = payloadPM02.preReqApptTypeRulePyaload();

		response = postAPIRequest.appointmentTypesByRule(baseurl, apptTypeRule,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		
		ArrayList<String> ls = new ArrayList<String>();
		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String appTypeNameActual;
		
		for (int i = 0; i < len; i++) {
			appTypeNameActual = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			ls.add(appTypeNameActual);
		}

		boolean stringExist = ls.contains(appName);
		logStep("Value of APPT Type "+appName+" present or not- "+stringExist);
	
		
		logStep("Verfiy Appointment Page and appointment =" + appName);
		int n = Integer.parseInt(noofDays);
		log("NO of Days is In Admin UI  " + n);
		log("Past Date is  " + pastDate);
		long dateDiffBetPastandCurrent = psspatientutils.dateDiffPastandCurrentDate(testData, pastDate);
		log("Number of Days within current and past date " + dateDiffBetPastandCurrent);
		
		if (n >= dateDiffBetPastandCurrent) {
			
			assertEquals(true, stringExist);
			log("First Condition Executed");
			log("Appointment Type is displayed in the list - Pre-Requistite Satisfied");
		} else {
			assertEquals(false, stringExist);

			log("Second Condition Executed");
			log("Appointment Type is not displayed in the list - Pre-Requistite not satisfied");
		}

		response = postAPIRequestAM.preRequisiteAppById(practiceId, preReqAppId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr1 = new JSONArray(response.body().asString());
		int l1 = arr1.length();
		int id = 0;
		log("Length is- " + l1);
		for (int i = 0; i < l1; i++) {
			id = arr1.getJSONObject(i).getInt("id");
			String s = Integer.toString(id);
			log("preRequisiteApp Id is for Delete " + s);
			response = postAPIRequestAM.preRequisiteAppDeleteById(practiceId, s);
			apv.responseCodeValidation(response, 200);
		}
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeMark() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloaAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		String timeMarkValue = "30";

		String adapPayload = payloaAM02.timeMarkPayload(timeMarkValue);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adapPayload);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceId,
				propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, bookid, locationid, apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken), practiceid,
				patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());
		String time = arr1.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String actualTime = time.substring(3, 5);
		String expectedTime = String.valueOf(timeMarkValue);
		log("Actual Time is " + actualTime);
		assertEquals(actualTime, expectedTime, "TimeMark is not working properly");

		adapPayload = payloaAM02.timeMarkPayload("0");
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adapPayload);
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExcludeSlot() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		JSONArray arr;
		int noOfSlots=3;		
		String firstExpectedSlotTime="01:00:00";
		String lastExpectedSlotTime= "01:10:00";
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloaAM02.apptTabSettingPyaload(30, 12);		
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloaAM02.excludeSlotPyaload();
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String currentdate=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String b = payloadPatientMod.availableslotsPayload(currentdate,bookid,locationid,apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr= new JSONArray(response.body().asString());
		
		int n=arr.length()-2;
		
		int actualSlotLength=arr.getJSONObject(n).getJSONArray("slotList").length();

		String slotid = arr.getJSONObject(n).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(n).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(n).getString("date");	

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		String time3 = arr.getJSONObject(n).getJSONArray("slotList").getJSONObject(actualSlotLength-1).getString("slotTime");

		log("slotTime-" + time3);		
		assertEquals(actualSlotLength, noOfSlots);
		
		assertEquals(time, firstExpectedSlotTime,"Slot Times are not matching");
		assertEquals(time3, lastExpectedSlotTime,"Slot Times are not matching");

	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastSeenProviderExPatient() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		JSONArray arr;

		adminPayload=payloaAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.lastseen.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String b = payloaAM02.bookRulePyaload(Integer.parseInt(locationid), Integer.parseInt(apptid));
		
		response = postAPIRequest.booksByRule(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		JSONObject jo= new JSONObject(response.asString());
		
		arr=jo.getJSONArray("books");
		int l=arr.length();
		String lastseen = null;
		
		log("Lenth of the Array is- "+l);
		
		for (int i = 0; i < l; i++) {

			int actualBookId = arr.getJSONObject(i).getInt("id");

			if (actualBookId == Integer.parseInt(bookid)) {

				lastseen = arr.getJSONObject(i).getString("lastSeen");
			}
		}
		
		assertNotNull(lastseen, "Last Seen Value is null so Test Case Failed for Last Seen Provider");		
				
		adminPayload=payloaAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastSeenProviderNewPatient() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		JSONArray arr;

		adminPayload = payloaAM02.lastSeenProviderPyaload(12);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloaAM02.patientTypeAdminSetting(5, 5);
		response= postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String patientId = propertyData.getProperty("patient.id.new.lastseen.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		
		String b = payloaAM02.lastSeenNewPatientPyaload();
		response = postAPIRequest.booksByRule(baseurl, b, headerConfig.HeaderwithTokenES(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());

		arr = jo.getJSONArray("books");
		int l = arr.length();
		String lastseen = null;
		log("Lenth of the Array is- " + l);
		
		for (int i = 0; i < l; i++) {
			int actualBookId = arr.getJSONObject(i).getInt("id");
			if (actualBookId == Integer.parseInt(bookid)) {
				lastseen = arr.getJSONObject(i).getString("lastSeen");
			}
		}

		assertNotNull(lastseen, "Last Seen Value is null so Test Case Failed for Last Seen Provider");

		adminPayload = payloaAM02.lastSeenProviderPyaload(1);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloaAM02.patientTypeAdminSetting(1095, 1095);
		response= postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testForceLastSeenProviderExPatient() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		JSONArray arr;
		
		adminPayload=payloaAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloaAM02.forceLastSeenPyaload(365);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.lastseen.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String b = payloaAM02.bookRulePyaload(Integer.parseInt(locationid), Integer.parseInt(apptid));
		
		response = postAPIRequest.booksByRule(baseurl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceid, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		JSONObject jo= new JSONObject(response.asString());
		
		arr=jo.getJSONArray("books");
		int l=arr.length();
		String lastseen = null;
		
		log("Lenth of the Array is- "+l);
		assertEquals(l, 1,"In Force Last Seen only 1 Provider should get display");
		
		for (int i = 0; i < l; i++) {

			int actualBookId = arr.getJSONObject(i).getInt("id");

			if (actualBookId == Integer.parseInt(bookid)) {

				lastseen = arr.getJSONObject(i).getString("lastSeen");
			}
		}
		
		assertNotNull(lastseen, "Last Seen Value is null so Test Case Failed for Last Seen Provider");		
				
		adminPayload=payloaAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	
		
		adminPayload=payloaAM02.forceLastSeenPyaload(0);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		

	}	
	
	
}
