// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.validation.constraints.AssertTrue;

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

public class PSS2PMFeatureNGTests05 extends BaseTestNG {

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
	public void testCancelRescLeadTime() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		JSONArray arr;
		String adminPayload;

		adminPayload = payloadAM02.onlineCancelLeadTimePyaload("24:00");
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		String patientId01 = propertyData.getProperty("patient.id.cancel.leadtime.pm05");
		log("Patient Id -"+patientId01);
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng03"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId01);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(1).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(1).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(1).getString("date");

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

		response = postAPIRequest.upcomingAppointmentsByPage(baseUrl, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId01);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.body().asString());

		boolean cancelStatus   = jo.getJSONArray("content").getJSONObject(0).getBoolean("cancel");
		boolean showReschedule = jo.getJSONArray("content").getJSONObject(0).getBoolean("showReschedule");

		assertEquals(cancelStatus, false, "Appointment Cancel Status is True, so test case failed");
		assertEquals(showReschedule, false, "Appointment Reschedule Status is True, so test case failed");

		adminPayload = payloadAM02.onlineCancelLeadTimePyaload("00:00");
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId01);
		apv.responseCodeValidation(cancelResponse, 200);
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam03() throws Exception {

		log("PSS-19765: Verify if PCP+ Care Team Members are displayed when Force booking with the");
		log("provider before showing the care team (days) is set to 0");

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcpwith0Confg.pm05");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.ng");
		String careTeamId = propertyData.getProperty("patient.id.careteam.id.pm05");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		HashSet<String> l2 = new HashSet<String>();
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamId);
		apv.responseCodeValidation(response, 200);

		JSONArray arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);

		for (int i = 0; i < len; i++) {
			String bookName = arr.getJSONObject(i).getString("displayName");
			l2.add(bookName);
			log("Book Added in list l2-" + bookName);
		}
		
		log("List of book from admin- " + l2);

		String patientId = propertyData.getProperty("patient.id.careteam.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");

		String b = payloadPM02.bookRuleCareTeamPyaload(locationid, apptid, null);
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());

		HashSet<String> l1 = new HashSet<String>();

		int l = jo.getJSONArray("books").length();
		for (int i = 0; i < l; i++) {
			String bookFromBookRule = jo.getJSONArray("books").getJSONObject(i).getString("displayName");
			l1.add(bookFromBookRule);
		}
		log("List of book from Patient UI- " + l1);
		assertTrue(l2.containsAll(l1));

	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam04() throws Exception {

		log("PSS-19766: Verify if all the providers whose Share Patient config is ON are displayed");
		log("when PCP is not a part of Care team");

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcpwith0Confg.pm05");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.ng");
		String careTeamId = propertyData.getProperty("patient.id.careteam.id.pm05");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String patientId = propertyData.getProperty("patient.id.careteam.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		String bookNotInCareTeam=propertyData.getProperty("sharepatients.book.id.pm05");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		response=postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamId);
		apv.responseCodeValidation(response, 200);
		
		logStep("Remove the PCP from CareTeam-");
		log("This is the PCP- "+bookid+" for the patient "+patientId);
		
		response=postAPIRequestAM.deleteCareTeamBook(practiceId, careTeamId, bookid);
		apv.responseCodeValidation(response, 200);
		
		response=postAPIRequestAM.getBookById(practiceId, bookNotInCareTeam);
		apv.responseCodeValidation(response, 200);
		String expected_BookID=apv.responseKeyValidationJson(response, "name");		
		assertEquals(apv.responseKeyValidationJson(response, "sharePatients"),"true");	
		
		String b = payloadPM02.bookRuleCareTeamPyaload(locationid, apptid, null);
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);

		JSONObject jo = new JSONObject(response.asString());

		HashSet<String> l1 = new HashSet<String>();

		int l = jo.getJSONArray("books").length();
		for (int i = 0; i < l; i++) {
			String bookFromBookRule = jo.getJSONArray("books").getJSONObject(i).getString("displayName");
			l1.add(bookFromBookRule);
		}

		assertTrue(l1.contains(expected_BookID));
		
		int book=Integer.parseInt(bookid);
		adminPayload=payloadAM02.addBookInCareTeamPyaload(book);
		response=postAPIRequestAM.saveCareTeamBook(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam05() throws Exception {

		log("Test Case ID- PSS-19774");
		log("Verify if No Providers are displayed when PCP's share patient config is OFF");

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		adminPayload= payloadAM02.careTeamSettingPyaload(1, 4);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload = payloadAM02.pcpSharePatientPyaload(false);
		response = postAPIRequestAM.bookSave(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String careTeamId = propertyData.getProperty("patient.id.careteam.id.pm05");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String patientId = propertyData.getProperty("patient.id.careteam.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		String specialty=propertyData.getProperty("specialty.careteam.id.pm05");

		response=postAPIRequestAM.deleteCareTeamBook(practiceId, careTeamId, bookid);
		apv.responseCodeValidation(response, 200);
		
		String b=payloadPM02.bookRuleCareTeamPyaload(locationid, apptid, specialty);	
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String pcpUnavailability=apv.responseKeyValidationJson(response, "pcpUnavailability");
		String restrictCareTeam =apv.responseKeyValidationJson(response, "restrictCareTeam");
		
		assertEquals(pcpUnavailability, "false");
		assertEquals(restrictCareTeam, "true");

		int book=Integer.parseInt(bookid);
		
		adminPayload=payloadAM02.addBookInCareTeamPyaload(book);
		response=postAPIRequestAM.saveCareTeamBook(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam06() throws Exception {

		log("Test Case ID- PSS-19774");
		log("Verify if No Providers are displayed when PCP's share patient config is OFF");

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		adminPayload= payloadAM02.careTeamSettingPyaload(1, 4);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		adminPayload = payloadAM02.pcpSharePatientPyaload(true);
		response = postAPIRequestAM.bookSave(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String careTeamId = propertyData.getProperty("patient.id.careteam.id.pm05");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng03");
		String patientId = propertyData.getProperty("patient.id.careteam.pm.ng");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.ng");
		String specialty=propertyData.getProperty("specialty.careteam.id.pm05");

		response=postAPIRequestAM.deleteCareTeamBook(practiceId, careTeamId, bookid);
		apv.responseCodeValidation(response, 200);
		
		String b=payloadPM02.bookRuleCareTeamPyaload(locationid, apptid, specialty);	
		response = postAPIRequest.booksByRule(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String pcpUnavailability=apv.responseKeyValidationJson(response, "pcpUnavailability");
		String restrictCareTeam =apv.responseKeyValidationJson(response, "restrictCareTeam");
		
		assertEquals(pcpUnavailability, "false");
		assertEquals(restrictCareTeam, "true");

		int book=Integer.parseInt(bookid);
		
		adminPayload=payloadAM02.addBookInCareTeamPyaload(book);
		response=postAPIRequestAM.saveCareTeamBook(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierOff() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		JSONArray arr;
		String adminPayload;
		
		adminPayload=payloadAM02.insurancePyaload(false);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String patientId01 = propertyData.getProperty("patient.id.careteam.pm.ng");
		log("Patient Id -"+patientId01);
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng03"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId01);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(1).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(1).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(1).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPM02.scheduleAppointmentPayload(slotid, date, time,
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

		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId01);
		apv.responseCodeValidation(cancelResponse, 200);
		
		adminPayload=payloadAM02.insurancePyaload(true);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierOn() throws Exception {

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		JSONArray arr;
		String adminPayload;
		
		adminPayload=payloadAM02.insurancePyaload(true);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		String patientId01 = propertyData.getProperty("patient.id.careteam.pm.ng");
		log("Patient Id -"+patientId01);
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm.ng03"),
				propertyData.getProperty("availableslot.locationid.pm.ng"),
				propertyData.getProperty("availableslot.apptid.pm.ng"));

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId01);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(1).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(1).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(1).getString("date");

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

		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId01);
		apv.responseCodeValidation(cancelResponse, 200);

	}

}
