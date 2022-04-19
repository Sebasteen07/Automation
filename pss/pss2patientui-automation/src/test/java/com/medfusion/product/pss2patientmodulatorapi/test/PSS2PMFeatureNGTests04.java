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

public class PSS2PMFeatureNGTests04 extends BaseTestNG {

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
		practiceId = propertyData.getProperty("mf.practice.id.ng");
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
	public void testPatientStatusInactiveAlert_Spanish() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		String patientId = propertyData.getProperty("patient.id.inactive.pm.ng");
		String patientStatus = propertyData.getProperty("patientstatus.id.ng");

		adminPayload = payloadAM02.inactivePatientPyaload(Integer.parseInt(patientStatus));

		response = postAPIRequestAM.lockoutPost(practiceId, adminPayload, "/lockout");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		response = postAPIRequest.patientDemographics(baseUrl, headerConfig.HeaderwithTokenES(accessToken), practiceId,
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
		adminPayload = payloadAM02.activeLockoutPyaload();

		response = postAPIRequestAM.lockoutPost(practiceId, adminPayload, "/lockout");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String activeStatusID =apv.responseKeyValidationJson(response, "id");
		int idActiveStatus=Integer.parseInt(activeStatusID);

		response = postAPIRequest.patientDemographics(baseUrl, headerConfig.HeaderwithToken(accessToken), practiceId,
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
		
		adminPayload=payloadAM02.overbookingPyaload(true);
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

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId01);
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

		Response schedResponse = postAPIRequest.scheduleAppointment(baseUrl, c,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId01, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId02);
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

		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId01);
		apv.responseCodeValidation(cancelResponse, 200);
		
		adminPayload=payloadAM02.overbookingPyaload(true);
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
		
		String m=propertyData.getProperty("preventbacktoback.slotsize.pm.ng");		
		int slotsize=Integer.parseInt(m);
		int apptDuration= 2*slotsize;
		
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloadAM02.preventBackToBackPyaload(true);
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
				
		adminPayload=payloadAM02.preventBackToBackPyaload(false);
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
		
		String m=propertyData.getProperty("maxperday.count.pm.ng");		
		int maxPerDay=Integer.parseInt(m);
	
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloadAM02.maxPerDayPyaload(maxPerDay);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
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
		
		String schedPayload= payloadPatientMod.scheduleAppointmentPayload(slotid, date, time, bookid, locationid, apptid);
		
		response=postAPIRequest.scheduleAppointment(baseUrl, schedPayload, headerConfig.HeaderwithToken(accessToken), practiceId, patientId, "PT_EXISTING");
		apv.responseCodeValidation(response, 200);		
		String extApptId = apv.responseKeyValidationJson(response, "extApptId");

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
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

		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(cancelResponse, 200);		
				
		adminPayload=payloadAM02.maxPerDayPyaload(0);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventScheduling() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		
		String m=propertyData.getProperty("preventsched.days.pm.ng");		
		int preventSchedDays=Integer.parseInt(m);
	
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		adminPayload=payloadAM02.preventSchedPyaload(preventSchedDays);
		response=postAPIRequestAM.saveAppointmenttype(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, bookid, locationid, apptid);
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 412);
		apv.responseTimeValidation(response);
		String errorMessage=apv.responseKeyValidationJson(response, "message");
		
		assertTrue(errorMessage.contains("Prevent Scheduling"));
		
		response=postAPIRequest.announcementByName(baseUrl, headerConfig.HeaderwithToken(accessToken), practiceId, "ARP");
		apv.responseCodeValidation(response, 200);	
				
		adminPayload=payloadAM02.preventSchedPyaload(0);
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

		response = postAPIRequest.appointmentTypesByRule(baseUrl, apptTypeRule,
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

		response = postAPIRequest.appointmentTypesByRule(baseUrl, apptTypeRule,
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

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM02.turnOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		String timeMarkValue = "30";

		String adapPayload = payloadAM02.timeMarkPayload(timeMarkValue);
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
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
				patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());
		String time = arr1.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String actualTime = time.substring(3, 5);
		String expectedTime = String.valueOf(timeMarkValue);
		log("Actual Time is " + actualTime);
		assertEquals(actualTime, expectedTime, "TimeMark is not working properly");

		adapPayload = payloadAM02.timeMarkPayload("0");
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
		
		adminPayload=payloadAM02.apptTabSettingPyaload(30, 12);		
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloadAM02.excludeSlotPyaload();
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

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.lastseen.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String b = payloadAM02.bookRulePyaload(Integer.parseInt(locationid), Integer.parseInt(apptid));
		
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
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

		adminPayload = payloadAM02.lastSeenProviderPyaload(12);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloadAM02.patientTypeAdminSetting(5, 5);
		response= postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String patientId = propertyData.getProperty("patient.id.new.lastseen.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		
		String b = payloadAM02.lastSeenNewPatientPyaload();
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
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

		adminPayload = payloadAM02.lastSeenProviderPyaload(1);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloadAM02.patientTypeAdminSetting(1095, 1095);
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
		
		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload=payloadAM02.forceLastSeenPyaload(365);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String patientId = propertyData.getProperty("patient.id.lastseen.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		
		String b = payloadAM02.bookRulePyaload(Integer.parseInt(locationid), Integer.parseInt(apptid));
		
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken),
				practiceId, patientId);
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
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	
		
		adminPayload=payloadAM02.forceLastSeenPyaload(0);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReserveForSameDayWithBook() throws Exception {

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM02.turnOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		adminPayload = payloadAM02.reserveSameDayWithBookPyaload("s");
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String currentDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String patientId = propertyData.getProperty("patient.id.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		response = postAPIRequestAM.locationById(practiceId, locationid);
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, bookid, locationid, apptid);
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithTokenES(accessToken), practiceId,
				patientId);

		String patientID = propertyData.getProperty("patient.id.feature.pm.ng");
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.getBody().asString());

		int noOfDaysInReserveForSameDay = arr.length();

		assertEquals(noOfDaysInReserveForSameDay, 1, "Accept Same Day is not working");

		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			log("Validated key-> " + "date" + " value is- " + obj.getString("date"));
			assertEquals(currentDate, obj.getString("date"));
		}

		adminPayload = payloadAM02.reserveSameDayWithBookPyaload("n");
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousAllowDuplicateFalse() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String cancelReasonPayload = payloadAM02.cancelReasonOffPyaload(false);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, cancelReasonPayload);
		apv.responseCodeValidation(response, 200);

		String allowDuplicate = payloadAM02.allowDuplicatePyaload(false);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, allowDuplicate);
		apv.responseCodeValidation(response, 200);

		String patientId = null;
		String accessToken_AN;

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		accessToken_AN = postAPIRequest.createToken(baseUrl, practiceId, "ANONYMOUS");

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken_AN), practiceId,
				patientId);
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
				headerConfig.HeaderwithToken(accessToken_AN), practiceId, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		patientId = apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");

		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentWithoutReasonPayload(extApptId),
				headerConfig.HeaderwithToken(accessToken_AN), practiceId, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousAllowDuplicateTrue() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String cancelReasonPayload = payloadAM02.cancelReasonOffPyaload(false);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, cancelReasonPayload);
		apv.responseCodeValidation(response, 200);

		String allowDuplicate = payloadAM02.allowDuplicatePyaload(true);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, allowDuplicate);
		apv.responseCodeValidation(response, 200);

		String patientId = null;
		String accessToken_AN;

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		accessToken_AN = postAPIRequest.createToken(baseUrl, practiceId, "ANONYMOUS");

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken_AN), practiceId,
				patientId);
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
				headerConfig.HeaderwithToken(accessToken_AN), practiceId, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		patientId = apv.responseKeyValidationJson(schedResponse, "patientId");
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");

		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentWithoutReasonPayload(extApptId),
				headerConfig.HeaderwithToken(accessToken_AN), practiceId, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam01() throws Exception {

		log("Verify if Care Team Members based on below confg");
		log("Force care teams for a duration (days) are displayed when PCP is not available within Force booking with the provider before showing the care team (days)");

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		
		String pcpvalue=propertyData.getProperty("patient.id.careteam.pcp.pm.ng");
		String fctvalue=propertyData.getProperty("patient.id.careteam.fct.pm.ng");
		
		int pcp=Integer.parseInt(pcpvalue);
		int fct=Integer.parseInt(fctvalue);
		
		adminPayload=payloadAM02.careTeamSettingPyaload(pcp, fct);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String patientId = propertyData.getProperty("patient.id.careteam.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		
		String b=payloadPM02.bookRuleCareTeamPyaload(locationid, apptid, "204921");	
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String pcpUnavailability=apv.responseKeyValidationJson(response, "pcpUnavailability");
		String restrictCareTeam =apv.responseKeyValidationJson(response, "restrictCareTeam");
		
		assertEquals(pcpUnavailability, "true");
		assertEquals(restrictCareTeam, "true");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam02() throws Exception {

		log("Verify if Care Team Members based on below confg");
		log("Verify if only PCP is displayed when PCP share patient config is OFF and FCT OFF");

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		adminPayload= payloadAM02.fctOnOffPyaload(false);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload = payloadAM02.pcpSharePatientPyaload(false);
		response = postAPIRequestAM.bookSave(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String patientId = propertyData.getProperty("patient.id.careteam.pm.ng");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		
		String b=payloadPM02.bookRuleCareTeamPyaload(locationid, apptid, "204921");	
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		JSONObject jo= new JSONObject(response.asString());
		int actual_pcpid=jo.getJSONArray("books").getJSONObject(0).getInt("id");
		
		String pcpUnavailability=apv.responseKeyValidationJson(response, "pcpUnavailability");
		String restrictCareTeam =apv.responseKeyValidationJson(response, "restrictCareTeam");
		
		assertEquals(pcpUnavailability, "false");
		assertEquals(restrictCareTeam, "true");
		assertEquals(actual_pcpid, Integer.parseInt(bookid), "The book present in response in not Primary Care Provider");
		
		adminPayload= payloadAM02.fctOnOffPyaload(true);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload = payloadAM02.pcpSharePatientPyaload(true);
		response = postAPIRequestAM.bookSave(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrivacyPolicyTrue() throws Exception {
		
		log("Verify the response when Privacy Policy is ON");

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		
		adminPayload=payloadAM02.llPrivactPolicyPyaload(true);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequest.practiceInfo(baseUrl, headerConfig.defaultHeader(),
				practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		String extPracticeId       = apv.responseKeyValidationJson(response, "id");
		String practiceName		   = apv.responseKeyValidationJson(response, "name");
		String showPrivacyPolicyLog= apv.responseKeyValidationJson(response, "showPrivacyPolicyLog");
		
		
		log("Practice name -" + practiceName);
		log("Ext PracticeId -" + extPracticeId);
		
		assertEquals(extPracticeId, practiceId, "Ext PracticeId is wrong");
		assertEquals(showPrivacyPolicyLog, "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrivacyPolicyFalse() throws Exception {
		
		log("Verify the response when Privacy Policy is off");

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;
		
		adminPayload=payloadAM02.llPrivactPolicyPyaload(false);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequest.practiceInfo(baseUrl, headerConfig.defaultHeader(),
				practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		String extPracticeId       = apv.responseKeyValidationJson(response, "id");
		String practiceName		   = apv.responseKeyValidationJson(response, "name");
		String showPrivacyPolicyLog= apv.responseKeyValidationJson(response, "showPrivacyPolicyLog");
		
		
		log("Practice name -" + practiceName);
		log("Ext PracticeId -" + extPracticeId);
		
		assertEquals(extPracticeId, practiceId, "Ext PracticeId is wrong");
		assertEquals(showPrivacyPolicyLog, "false");
	}

}
