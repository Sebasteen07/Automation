// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;


import static org.testng.Assert.assertEquals;

import java.io.IOException;

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
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAMFeature01;
import com.medfusion.product.pss2patientapi.payload.PayloadPMNGFeature01;
import com.medfusion.product.pss2patientapi.payload.PayloadPssPMNG;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;

public class PSS2PMFeatureNGTests01 extends BaseTestNG {

	public static HeaderConfig headerConfig;
	public static PSSPropertyFileLoader propertyData;

	public static Appointment testData;
	public static PostAPIRequestPMNG postAPIRequest;
	public static PayloadPMNGFeature01 payloadPssPMNG1;
	public static PayloadPssPMNG payloadPatientMod;

	public static String accessToken;
	public static String practiceid;
	public static String baseurl;
	public PSSPatientUtils pssPatientUtils;

	APIVerification apv;
	public static PayloadAdapterModulator payloadAM;
	public static PayloadAMFeature01 payloadAdapterModulatorFeature;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	public static String openToken;
	public static String practiceIdAm;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {

		log("Set the Authorization for Patient Modulator");

		headerConfig = new HeaderConfig();
		propertyData = new PSSPropertyFileLoader();
		testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		postAPIRequest = new PostAPIRequestPMNG();
		payloadPssPMNG1 = new PayloadPMNGFeature01();
		payloadPatientMod=new PayloadPssPMNG();
		baseurl = propertyData.getProperty("base.url.pm.ng");
		practiceid = propertyData.getProperty("practice.id.feature.pm.ng");
		accessToken = postAPIRequest.createToken(baseurl, practiceid);
		pssPatientUtils = new PSSPatientUtils();
		apv = new APIVerification();

		log("Base URL for Patient Modulator - " + baseurl);

	}
	public void setUpAM(String practiceId1, String userID) throws IOException {
		payloadAdapterModulatorFeature = new PayloadAMFeature01();
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();
		practiceIdAm = practiceId1;
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceIdAm, payloadAdapterModulatorFeature.openTokenPayload(practiceIdAm, userID));
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"), headerConfig.HeaderwithToken(openToken));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testssoWithDecTreeShowProviderONPost() throws Exception {
		log("SSO flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider ON.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

        String b=payloadPssPMNG1.ssoSchedulewithDectree();
        String patientID=propertyData.getProperty("patient.id.feature.pm.ng");
		response = postAPIRequest.scheduleAppointmentExisting(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid, patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testssoWithoutDecTreeShowProviderONPost() throws IOException {
        String b=payloadPssPMNG1.ssoPostwithoutDecTree();
        String patientID=propertyData.getProperty("patient.id.feature.pm.ng");
		Response response = postAPIRequest.scheduleAppointmentExisting(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid, patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testssoWithoutDecTreeShowProviderOffPost() throws IOException {
        String b=payloadPssPMNG1.ssoPostwithoutDecTreeShowProviderOFF();
        String patientID=propertyData.getProperty("patient.id.feature.pm.ng");
		Response response = postAPIRequest.ssorescheduleAppointment(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid, patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testssoWithDecTreeShowProviderOffPost() throws IOException {
        String b=payloadPssPMNG1.ssoPostwithDecTreeShowProviderOFF();
        String patientID=propertyData.getProperty("patient.id.feature.pm.ng");
		Response response = postAPIRequest.ssorescheduleAppointment(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid, patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void timeMark() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		
		Response response;
		
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}
		
		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);
		
		
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		//int timeMarkValue=30;
		String adapPayload=payloadAdapterModulatorFeature.timeMark();
		
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, "206528");
		apv.responseCodeValidation(response, 200);
		String startDate=pssPatientUtils.sampleDateTime("MM/dd/yyyy");
        String b=payloadPssPMNG1.avaliableSlot(startDate);
        String patientID=propertyData.getProperty("patient.id.feature.pm.ng");
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid, patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReserveForSameDay() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String resValue="s";
		boolean accValue=true;
		int timeMarkValue=0;
		String adapPayload = payloadAdapterModulatorFeature.reserveForSameDay(resValue,accValue,timeMarkValue);

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, "206528");
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.avaliableSlot(startDate);

		String patientID = propertyData.getProperty("patient.id.feature.pm.ng");
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JSONArray arr1 = new JSONArray(response.getBody().asString());
		for (int i = 0; i < arr1.length(); i++) {
			JSONObject obj = arr1.getJSONObject(i);
			log("Validated key-> " + "date" + " value is-  " + obj.getString("date"));
			assertEquals(startDate, obj.getString("date"));
		}
		String adapPayloadforReset = payloadAdapterModulatorFeature.reserveForSameDay("n", accValue, 0);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayloadforReset, "206528");
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousSchedReschWithoutCatShowProviderON() throws Exception {
		log("Annonymous flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is On");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);
		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ano.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPssPMNG1.availableslotsPayloadLTB(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());

		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPssPMNG1.anoScheduleAppointmentPayloadLTBWithoutDecTree(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));
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

		String d = payloadPssPMNG1.anoRescheduleWithoutCatLTB(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid,
				testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);


	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousSchedReschWithCatShowProviderON() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider ON.");
		log("Annonymous flow : Verify the POST call for Reschedule appointment With Decision Tree and Show Provider is On");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);
		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ano.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPssPMNG1.availableslotsPayloadLTB(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());

		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPssPMNG1.anoScheduleAppointmentPayloadLTBWithDecTree(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

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

		String d = payloadPssPMNG1.anoRescheduleWithCatLTB(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);
		
		
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);
		

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousSchedReschWithCatShowProviderOFF() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider OFF.");
		log("Annonymous flow : Verify the POST call for Reschedule appointment With Decision Tree and Show Provider is OFF");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);
		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		 response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());

		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPssPMNG1.anoscheduleAppointmentPayloadWithDecTreeLT(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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

		String d = payloadPssPMNG1.anoRescheduleWithCatLT(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);
		
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousSchedReschWithoutCatShowProviderOFF() throws Exception {
		log("Annonymous flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is OFF");
		log("Annonymous flow : Verify the POST call for Schedule Appointment without Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);
		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		 response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());

		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPssPMNG1.anoscheduleAppointmentPayloadWithoutDecTreeLT(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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

		String d = payloadPssPMNG1.anoRescheduleWithoutCatLT(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHideSlot() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		int leadTime=3;
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.locationById(practiceIdAm, propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- "+locationTimeZone);
		
		String currentdate1=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - "+currentdate1);

		String futuredate=pssPatientUtils.addDaysToDate(currentdate1, Integer.toString(leadTime), "MM/dd/yyyy");
        log("Future Date -"+futuredate);
        
		String hideSlotPayload = payloadAdapterModulatorFeature.hideSlots(leadTime);
        String appTypeId=propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, hideSlotPayload, appTypeId);
		apv.responseCodeValidation(response, 200);
	
		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		 response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		JSONArray arr1 = new JSONArray(response.body().asString());
		String date = arr1.getJSONObject(0).getString("date");
		
		assertEquals(date, futuredate, "Lead Time is not working properly");

		String resetHideSlotPayload = payloadAdapterModulatorFeature.hideSlots(0);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, resetHideSlotPayload, appTypeId);
		apv.responseCodeValidation(response, 200);


	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleWithDeciShowProviderOFF() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String b = payloadPssPMNG1.ssoSchedulewithDectree();

		response = postAPIRequest.scheduleAppointment_PTNEW(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test19659UpcomingAppByIndex() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);
		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPssPMNG1.availableslotsPayloadLTB(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());

		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPssPMNG1.llScheduleAppointmentPayloadLTBWithoutDecTree(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));
		log("Payload-" + c);
		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);

		
		response = postAPIRequest.upComingAppointmentsByPage(baseurl,headerConfig.HeaderwithToken(accessToken),
				practiceid,patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test19660PastAppByIndex() throws Exception {
		logStep("Set up the API authentication");
		String patientId = propertyData.getProperty("patient.id.feature.pm.ng");
		Response response;
		response = postAPIRequest.pastAppointmentsByPage(baseurl,headerConfig.HeaderwithToken(accessToken),
				practiceid,patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void llAvailSchedRescheduleAppointment() throws Exception {
		log("Loginless flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is On");
		
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLTB(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

	   response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());
		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");
		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		String c = payloadPssPMNG1.llScheduleAppointmentPayloadLTBWithoutDecTree(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

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
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"),
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid,
				testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

	}
	
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void llAvailSchedRescheduleAppointment01() throws Exception {
		log("Loginless flow : Verify the POST call for Reschedule appointment With Decision Tree and Show Provider is On");
		
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLTB(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

	   response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());
		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");
		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		String c = payloadPssPMNG1.llScheduleAppointmentPayloadLTBWithDecTree(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

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

		String d = payloadPssPMNG1.rescheduleWithCatLTB(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void llAvailSchedRescheduleAppointment02() throws Exception {
		log("Loginless flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is OFF");
		
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

	   response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());
		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");
		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		String c = payloadPssPMNG1.llScheduleAppointmentPayloadLTWithoutDecTree(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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

		String d = payloadPssPMNG1.rescheduleWithoutCatLT(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid,patientid,
				testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);
		
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void llAvailSchedRescheduleAppointment03() throws Exception {
		log("Loginless flow : Verify the POST call for Reschedule appointment With Decision Tree and Show Provider is OFF");
		
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

	   response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());
		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");
		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		String c = payloadPssPMNG1.llScheduleAppointmentPayloadLTWithDecTree(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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

		String d = payloadPssPMNG1.rescheduleWithCatLT(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid,
				testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);
		
//		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
//				payloadAdapterModulatorFeature.turnONOFFDecisionTree(false));
//		apv.responseCodeValidation(response, 200);

	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayOFF() throws Exception {	logStep("Set up the API authentication");
	setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
	Response response;
	logStep("Set up the desired rule in Admin UI using API");
	response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
	JSONArray arr = new JSONArray(response.body().asString());
	int l = arr.length();
	log("Length is- " + l);
	for (int i = 0; i < l; i++) {
		int ruleId = arr.getJSONObject(i).getInt("id");
		log("Object No." + i + "- " + ruleId);
		response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
		apv.responseCodeValidation(response, 200);
	}

	response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
	apv.responseCodeValidation(response, 200);

	response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
	apv.responseCodeValidation(response, 200);

	response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
			payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
	apv.responseCodeValidation(response, 200);
	String reserveSameDayValue="n";
	boolean acceptSameDayValue=false;
	int timeMarkValue=0;
	String adapPayload = payloadAdapterModulatorFeature.reserveForSameDay(reserveSameDayValue, acceptSameDayValue,timeMarkValue);
    String appTypeId=propertyData.getProperty("availableslot.apptid.pm.feature.ng");
	response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload,appTypeId);
	apv.responseCodeValidation(response, 200);
	
	response = postAPIRequestAM.locationById(practiceIdAm, propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
	apv.responseCodeValidation(response, 200);
	String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
	log("TimeZone- "+locationTimeZone);
	
	String currentdate1=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
	log("currentdate - "+currentdate1);
    int futureAdd=1;
	String futuredate=pssPatientUtils.addDaysToDate(currentdate1, Integer.toString(futureAdd), "MM/dd/yyyy");
    log("Future Date -"+futuredate);
	
	String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
	String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
	String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
			propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
			propertyData.getProperty("availableslot.apptid.pm.feature.ng"));
	response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
			patientid);
	apv.responseCodeValidation(response, 200);
	apv.responseTimeValidation(response);
	
	JSONArray arr1 = new JSONArray(response.body().asString());
	String date = arr1.getJSONObject(0).getString("date");
	assertEquals(date, futuredate, "Accept Same day is not working properly");

	String adapPayload1 = payloadAdapterModulatorFeature.reserveForSameDay("n", true,0);
    propertyData.getProperty("availableslot.apptid.pm.feature.ng");
	response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload1,appTypeId);
	apv.responseCodeValidation(response, 200);
}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayON() throws Exception {	logStep("Set up the API authentication");
	setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
	Response response;
	logStep("Set up the desired rule in Admin UI using API");
	response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
	JSONArray arr = new JSONArray(response.body().asString());
	int l = arr.length();
	log("Length is- " + l);
	for (int i = 0; i < l; i++) {
		int ruleId = arr.getJSONObject(i).getInt("id");
		log("Object No." + i + "- " + ruleId);
		response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
		apv.responseCodeValidation(response, 200);
	}

	response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
	apv.responseCodeValidation(response, 200);

	response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
	apv.responseCodeValidation(response, 200);

	response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
			payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
	apv.responseCodeValidation(response, 200);
	String reserveSameDayValue="n";
	boolean acceptSameDayValue=true;
	int timeMarkValue=0;
	String adapPayload = payloadAdapterModulatorFeature.reserveForSameDay(reserveSameDayValue, acceptSameDayValue, timeMarkValue);
    String appTypeId=propertyData.getProperty("availableslot.apptid.pm.feature.ng");
	response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload,appTypeId);
	apv.responseCodeValidation(response, 200);
	
	response = postAPIRequestAM.locationById(practiceIdAm, propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
	apv.responseCodeValidation(response, 200);
	String locationTimeZone=apv.responseKeyValidationJson(response, "timezone");
	log("TimeZone- "+locationTimeZone);
	
	String currentdate1=pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
	log("currentdate - "+currentdate1);

	String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
	String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
	String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
			propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
			propertyData.getProperty("availableslot.apptid.pm.feature.ng"));
	response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
			patientid);
	apv.responseCodeValidation(response, 200);
	apv.responseTimeValidation(response);
	
	JSONArray arr1 = new JSONArray(response.body().asString());
	String date = arr1.getJSONObject(0).getString("date");
	assertEquals(date, currentdate1, "Accept Same day is not working properly");

}

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeMark() throws Exception {	logStep("Set up the API authentication");
	setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
	Response response;
	logStep("Set up the desired rule in Admin UI using API");
	response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
	JSONArray arr = new JSONArray(response.body().asString());
	int l = arr.length();
	log("Length is- " + l);
	for (int i = 0; i < l; i++) {
		int ruleId = arr.getJSONObject(i).getInt("id");
		log("Object No." + i + "- " + ruleId);
		response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
		apv.responseCodeValidation(response, 200);
	}

	response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LT", "L,T"));
	apv.responseCodeValidation(response, 200);

	response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TL", "T,L"));
	apv.responseCodeValidation(response, 200);

	response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
			payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
	apv.responseCodeValidation(response, 200);
	String reserveSameDayValue="n";
	boolean acceptSameDayValue=true;
	int timeMarkValue=15;
	String adapPayload = payloadAdapterModulatorFeature.reserveForSameDay(reserveSameDayValue, acceptSameDayValue, timeMarkValue);
    String appTypeId=propertyData.getProperty("availableslot.apptid.pm.feature.ng");
	response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload,appTypeId);
	apv.responseCodeValidation(response, 200);
	
	String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
	String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
	String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
			propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
			propertyData.getProperty("availableslot.apptid.pm.feature.ng"));
	response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
			patientid);
	apv.responseCodeValidation(response, 200);
	apv.responseTimeValidation(response);
	
	JSONArray arr1 = new JSONArray(response.body().asString());
	String date = arr1.getJSONObject(0).getString("date");
	String time = arr1.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
	String actualTime=time.substring(3, 5);
	String expectedTime=String.valueOf(timeMarkValue);
	log("Actual Time is "+actualTime);
	assertEquals(actualTime, expectedTime, "TimeMark is not working properly");

	String adapPayload1 = payloadAdapterModulatorFeature.reserveForSameDay(reserveSameDayValue, acceptSameDayValue, 0);
	response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload1,appTypeId);
	apv.responseCodeValidation(response, 200);


}
	

//	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
//	public void testAnores01() throws Exception {
//		log("Anonymous flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is On");
//		logStep("Set up the API authentication");
//		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),propertyData.getProperty("mf.authuserid.am.feature.ng"));
//		Response response;
//		logStep("Set up the desired rule in Admin UI using API");
//		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
//		JSONArray arr = new JSONArray(response.body().asString());
//		int l = arr.length();
//		log("Length is- " + l);
//		for (int i = 0; i < l; i++) {
//			int ruleId = arr.getJSONObject(i).getInt("id");
//			log("Object No." + i + "- " + ruleId);
//			response = postAPIRequestAM.deleteRuleById(practiceIdAm, Integer.toString(ruleId));
//			apv.responseCodeValidation(response, 200);
//		}
//
//		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LTB", "L,T,B"));
//		apv.responseCodeValidation(response, 200);
//
//		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TLB", "T,L,B"));
//		apv.responseCodeValidation(response, 200);
//
//		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
//				payloadAdapterModulatorFeature.turnONOFFShowProvider(true));
//		apv.responseCodeValidation(response, 200);
//
//		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
//				payloadAdapterModulatorFeature.turnONOFFDecisionTree(true));
//		apv.responseCodeValidation(response, 200);
//
//		
//		String patientid = propertyData.getProperty("patient.id.feature.pm.ng");
//
//		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
//
//		String b = payloadPssPMNG1.availableslotsPayloadLTB(currentdate,
//				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
//				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
//				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));
//
//		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
//				patientid);
//		apv.responseCodeValidation(response, 200);
//		apv.responseTimeValidation(response);
//
//		JSONArray arr1 = new JSONArray(response.body().asString());
//
//		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
//		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
//		String date = arr1.getJSONObject(2).getString("date");
//
//		log("slotTime-" + time);
//		log("slotId- " + slotid);
//		log("Date-" + date);
//
//		String c = payloadPssPMNG1.anoScheduleAppointmentPayloadLTBWithDecTree(slotid, date, time,
//				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
//				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
//				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));
//
//		log("Payload-" + c);
//
//		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
//				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());
//		apv.responseCodeValidation(schedResponse, 200);
//
//		
//
//	}

}
