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

public class PSS2PMFeatureNGTests07 extends BaseTestNG {

	public static HeaderConfig headerConfig;
	public static PSSPropertyFileLoader propertyData;

	public static Appointment testData;
	public static PostAPIRequestPMNG postAPIRequest;
	public static PayloadPssPMNG payloadPatientMod;
	public static PayloadPM02 payloadPM02;
	public static String accessToken;
	public static String baseUrl;
	public PSSPatientUtils pssPatientUtils;
	public PSS2PatientModulatorrAcceptanceNGTests pmng;
	
	public static PayloadAdapterModulator payloadAM;
	public static PayloadAM02 payloadAM02;
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

		baseUrl = propertyData.getProperty("base.url.pm.ng");
		practiceId = propertyData.getProperty("mf.practice.id.ng02");
		accessToken = postAPIRequest.createToken(baseUrl, practiceId);
		pssPatientUtils = new PSSPatientUtils();
		apv = new APIVerification();
		pmng = new PSS2PatientModulatorrAcceptanceNGTests();
		log("Base URL for Patient Modulator - " + baseUrl);
	}
	
	public void setUpAM(String practiceId1, String userID) throws IOException {
		payloadAM02 = new PayloadAM02();
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();
		practiceId = practiceId1;
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId,
				payloadAM02.openTokenPayload(practiceId, userID));
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01AnnouncementByLanguageGET() throws IOException {
		
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;

		response = postAPIRequest.announcementByLanguage(baseUrl, headerConfig.HeaderwithToken(accessToken),
				practiceId);
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
				payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String adapPayload = payloadAM02.reserveForSameDay("s");

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceId, adapPayload, "206528");
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String availableSLotsNG = payloadPM02.avaliableSlot(startDate);
		String patientID = propertyData.getProperty("patient.id.feature.pm.ng");
		response = postAPIRequest.availableSlots(baseUrl, availableSLotsNG, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JSONArray arr1 = new JSONArray(response.getBody().asString());
		for (int i = 0; i < arr1.length(); i++) {
			JSONObject obj = arr1.getJSONObject(i);
			log("Validated key-> " + "date" + " value is- " + obj.getString("date"));
			assertEquals(startDate, obj.getString("date"));
		}
		String adapPayloadforReset = payloadAM02.reserveForSameDay("n");
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
				payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		String patientid = null;

		response = postAPIRequest.appointmentTypesByRule(baseUrl,
				payloadPM02.appointmentTypesByrulePayload(), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientid);

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

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
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

		Response schedResponse = postAPIRequest.scheduleAppointment(baseUrl, c,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		

		String d = payloadPM02.allowOnlineCancelPayload(extApptId);
		response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelApptWhenCancelReasonOff() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String cancelReasonPayload=payloadAM02.cancelReasonOffPyaload(false);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, cancelReasonPayload);
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
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
				propertyData.getProperty("availableslot.bookid.pm.ng03"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(baseUrl, c,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		

		String d = payloadPM02.allowOnlineCancelPayload(extApptId);
		response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentWithoutReasonPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
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
				payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloadAM02.rescheduleOnCancelPyaload("10");
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.onlinecancel.pm.ng");

		String b = payloadPM02.cancelStatusPyaload();

		response = postAPIRequest.cancelStatus(baseUrl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		
		apv.responseCodeValidation(response, 200);
		assertEquals("false",apv.responseKeyValidationJson(response, "checkCancelAppointmentStatus"));
		
		adminPayload=payloadAM02.rescheduleOnCancelPyaload("0");
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
				payloadAM02.turnOFFShowProvider(true));
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
		
		adminPayload=payloadAM02.rescheduleOnCancelPyaload("10");
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.onlinecancel.pm.ng");

		String b = payloadPM02.cancelStatusPyaload();

		response = postAPIRequest.cancelStatus(baseUrl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		
		apv.responseCodeValidation(response, 200);
		assertEquals("false",apv.responseKeyValidationJson(response, "checkCancelAppointmentStatus"));
		
		adminPayload=payloadAM02.rescheduleOnCancelPyaload("0");
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelApptWithCancelReasonOther_Spanish() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String cancelReasonPayload=payloadAM02.cancelReasonOffPyaload(true);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, cancelReasonPayload);
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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

		Response schedResponse = postAPIRequest.scheduleAppointment(baseUrl, c,
				headerConfig.HeaderwithTokenES(accessToken), practiceId, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		

		String d = payloadPM02.allowOnlineCancelPayload(extApptId);
		response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithTokenES(accessToken), practiceId, patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentWithReasonOtherPayload(extApptId), headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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
		
		adminPayload=payloadAM02.bookappointmenttypePyaload(leadTime);
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

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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
		
		adminPayload=payloadAM02.bookappointmenttypePyaload(0);
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
		
		String m=propertyData.getProperty("apppointment.duration.pm.ng");		
		int apptDuration=Integer.parseInt(m);
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloadAM02.apptDurationPyaload(apptDuration);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String currentdate=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);		
		log("currentdate - "+currentdate);
		
		String b = payloadPatientMod.availableslotsPayload(currentdate,bookid,locationid,apptid);
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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
		
		response=postAPIRequest.scheduleAppointment(baseUrl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
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
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(cancelResponse, 200);		
				
		adminPayload=payloadAM02.apptDurationPyaload(0);
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
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr= new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		
		
		adminPayload=payloadAM02.mergeSlotPyaload(slotCount);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		int mergeslottime=slotCount*5;
		String nextSlotInApptDuration=pssPatientUtils.addToTime(time, mergeslottime);		

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
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

				
		adminPayload=payloadAM02.mergeSlotPyaload(1);
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
				payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		

		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.locationsByNextAvailablePayload(null, locationid, apptid);

		response = postAPIRequest.locationsByNextAvailable(baseUrl, b,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientid);

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
		
		String practiceP=payloadAM02.practicePyaload();
		response=postAPIRequestAM.practicePost(practiceId, practiceP);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
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

		Response schedResponse = postAPIRequest.scheduleAppointment(baseUrl, c,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		

		String d = payloadPM02.allowOnlineCancelPayload(extApptId);
		response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), d,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
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
				payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		adminPayload = payloadAM02.apptTypeConfgPyaload(true);
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

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		adminPayload = payloadAM02.apptTypeConfgPyaload(false);
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

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
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
		adminPayload=payloadAM02.confgSaveAdmin("showCategory", true);
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
				payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.onlinecancel.pm.ng");
		String currentdate=pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		log("currentdate - "+currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, null,
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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
		
		response=postAPIRequest.scheduleAppointment(baseUrl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);
		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
		
		logStep("Turn off the decision tree configuration");
		adminPayload=payloadAM02.confgSaveAdmin("showCategory", false);
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
				payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		String patientId = propertyData.getProperty("patient.id.onlinecancel.pm.ng");
		String currentdate=pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		log("currentdate - "+currentdate);
		
		String location=propertyData.getProperty("availableslot.locationid.pm.ng");
		String appttype=propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPatientMod.availableslotsPayload(currentdate, null,
				location,
				appttype);

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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
		
		response=postAPIRequest.scheduleAppointment(baseUrl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);
		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
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

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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

		Response schedResponse = postAPIRequest.scheduleAppointment(baseUrl, c,
				headerConfig.HeaderwithTokenES(accessToken), practiceId, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentWithReasonOtherPayload(extApptId), headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventSchedulingFutureShowProviderOFF() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String m = propertyData.getProperty("preventsched.days.pm.ng");
		int preventSchedDays = Integer.parseInt(m);

		response = postAPIRequestAM.locationById(practiceId,propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		adminPayload = payloadAM02.preventSchedPyaload1(preventSchedDays);
		response = postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPM02.availableslotsPayloadLT(currentdate, locationid, apptid);
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 412);
		apv.responseTimeValidation(response);

		String errorMessage = apv.responseKeyValidationJson(response, "message");

		assertTrue(errorMessage.contains("Prevent Scheduling"));

		response = postAPIRequest.announcementByName(baseUrl, headerConfig.HeaderwithToken(accessToken), practiceId,
				"ARP");
		apv.responseCodeValidation(response, 200);

		adminPayload = payloadAM02.preventSchedPyaload1(0);
		response = postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
	}
}
