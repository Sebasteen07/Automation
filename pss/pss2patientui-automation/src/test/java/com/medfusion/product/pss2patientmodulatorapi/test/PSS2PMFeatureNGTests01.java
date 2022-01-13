// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import io.restassured.path.json.JsonPath;
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
	public static PayloadAMFeature01 payloadAMFeature01;
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
		payloadPatientMod = new PayloadPssPMNG();
		baseurl = propertyData.getProperty("base.url.pm.ng");
		practiceid = propertyData.getProperty("practice.id.feature.pm.ng");
		accessToken = postAPIRequest.createToken(baseurl, practiceid);
		pssPatientUtils = new PSSPatientUtils();
		apv = new APIVerification();

		log("Base URL for Patient Modulator - " + baseurl);

	}

	public void setUpAM(String practiceId1, String userID) throws IOException {
		payloadAMFeature01 = new PayloadAMFeature01();
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();
		practiceIdAm = practiceId1;
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceIdAm,
				payloadAMFeature01.openTokenPayload(practiceIdAm, userID));
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOWithCatShowProviONPost() throws Exception {
		log("SSO flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider ON.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		// String b = payloadPssPMNG1.ssoScheduleWithCatShowProviderON();
//		String patientID = propertyData.getProperty("patient.id.feature.pm.sso.ng");
//
//		response = postAPIRequest.scheduleAppointmentExisting(baseurl, b, headerConfig.HeaderwithToken(accessToken),
//				practiceid, patientID);
//		apv.responseCodeValidation(response, 200);
//		apv.responseTimeValidation(response);
		String patientId = propertyData.getProperty("patient.id.feature.pm.sso.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLTB(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());
		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");
		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		String c = payloadPssPMNG1.ssoScheduleWithCatShowProviderON(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		log("Payload-" + c);
		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId, testData.getPatientType());
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
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOWithoutCatShowProviONPost() throws Exception {

		log("SSO flow : Verify the POST call for Schedule Appointment without Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);
		String patientId = propertyData.getProperty("patient.id.feature.pm.sso.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLTB(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr1 = new JSONArray(response.body().asString());
		String slotid = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr1.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr1.getJSONObject(2).getString("date");
		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		String c = payloadPssPMNG1.ssoScheduleWithoutCatShowProviderON(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"),
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"));

		log("Payload-" + c);
		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);
		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String confirmationno = apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "slotAlreadyTaken");
		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		apv.responseKeyValidationJson(schedResponse, "slotAlreadyTaken");

		String startdate = date + " " + time;

		String d = payloadPatientMod.rescheduleAppointmentPayload(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.bookid.pm.feature.ng"),
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(baseurl, d,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOWithoutCatShowProviOffPost() throws Exception {
		log("SSO flow : Verify the POST call for Schedule Appointment without Decision Tree and with Show Provider ON.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);
//
//		String b = payloadPssPMNG1.ssoScheduleWithoutCatShowProviderOFF();
//		String patientID = propertyData.getProperty("patient.id.feature.pm.sso.ng");
//		response = postAPIRequest.ssorescheduleAppointment(baseurl, b, headerConfig.HeaderwithToken(accessToken),
//				practiceid, patientID);
//		apv.responseCodeValidation(response, 200);
//		apv.responseTimeValidation(response);

		String patientid = propertyData.getProperty("patient.id.feature.pm.sso.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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
		String c = payloadPssPMNG1.ssoScheduleWithoutCatShowProviderOFF(slotid, date, time,
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
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOWithCatShowProviOffPost() throws Exception {

		log("SSO flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);
//		String b = payloadPssPMNG1.ssoScheduleWithCatShowProviderOFF();
//		String patientID = propertyData.getProperty("patient.id.feature.pm.sso.ng");
//		response = postAPIRequest.ssorescheduleAppointment(baseurl, b, headerConfig.HeaderwithToken(accessToken),
//				practiceid, patientID);
//		apv.responseCodeValidation(response, 200);
//		apv.responseTimeValidation(response);

		String patientid = propertyData.getProperty("patient.id.feature.pm.sso.ng");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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
		String c = payloadPssPMNG1.ssoScheduleWithCatShowProviderOFF(slotid, date, time,
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
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReserveForSameDay() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String resValue = "s";
		boolean accValue = true;
		int timeMarkValue = 0;
		String adapPayload = payloadAMFeature01.reserveForSameDay(resValue, accValue, timeMarkValue);

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, "206528");
		apv.responseCodeValidation(response, 200);

		String currentDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentDate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		String patientID = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JSONArray arr1 = new JSONArray(response.getBody().asString());
		for (int i = 0; i < arr1.length(); i++) {
			JSONObject obj = arr1.getJSONObject(i);
			log("Validated key-> " + "date" + " value is-  " + obj.getString("date"));
			assertEquals(currentDate, obj.getString("date"));
		}
		String adapPayloadforReset = payloadAMFeature01.reserveForSameDay("n", accValue, 0);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayloadforReset, "206528");
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnoSchedReschWithoutCatShowProviON() throws Exception {
		log("Annonymous flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is On");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		logStep("Set up the desired Setting in Admin UI using API");
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);
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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		
		String patientid = propertyData.getProperty("patient.id.feature.pm.ano.ng");
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
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnoSchedReschWithCatShowProviON() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider ON.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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

		logStep("Show Provider On by using resourceConfigSavePost Call");
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		logStep("Category On by using resourceConfigSavePost Call");
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.feature.pm.ano.ng");
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

		log("Annonymous flow : Verify the POST call for Reschedule appointment With Decision Tree and Show Provider is On");
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
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnoSchedReschWithCatShowProviOFF() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
		
		logStep("Turn Off The Decision Tree By Using resourceConfigSavePost ");
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		logStep("Turn On The Show Provider By Using resourceConfigSavePost ");
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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
		logStep("Annonymous flow : Verify the POST call for Reschedule appointment With Decision Tree and Show Provider is OFF");
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
	public void testAnoSchedReschWithoutCatShowProviOFF() throws Exception {
		log("Annonymous flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is OFF");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
		
		logStep("Show Provider Off by using resourceConfigSavePost Call");
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		
		logStep("Category On by using resourceConfigSavePost Call");
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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
		logStep("Annonymous flow : Verify the POST call for Schedule Appointment without Decision Tree and with Show Provider OFF.");
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
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		int leadTime = 3;
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate1 = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate1);

		String futuredate = pssPatientUtils.addDaysToDate(currentdate1, Integer.toString(leadTime), "MM/dd/yyyy");
		log("Future Date -" + futuredate);

		String hideSlotPayload = payloadAMFeature01.hideSlots(leadTime);
		String appTypeId = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, hideSlotPayload, appTypeId);
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");
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

		assertEquals(date, futuredate, "Lead Time is not working properly");

		String resetHideSlotPayload = payloadAMFeature01.hideSlots(0);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, resetHideSlotPayload, appTypeId);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppByIndex() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);
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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");

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

		response = postAPIRequest.upComingAppointmentsByPage(baseurl, headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppByIndex() throws Exception {
		logStep("Set up the API authentication");
		String patientId = propertyData.getProperty("patient.id.pm01");
		Response response;
		response = postAPIRequest.pastAppointmentsByPage(baseurl, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLLAvailSchedRescheAppoWithoutCatShowProON() throws Exception {
		log("Loginless flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is On");

		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");
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
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailSchedRescheduleAppWithDecShowProON() throws Exception {
		log("Loginless flow : Verify the POST call for Reschedule appointment With Decision Tree and Show Provider is On");

		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");
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
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLLAvailSchedRescheAppWithoutCatShowProOFF() throws Exception {
		log("Loginless flow : Verify the POST call for Reschedule appointment Without Decision Tree and Show Provider is OFF");

		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLLAvailSchedRescheAppWithCatShowProvOFF() throws Exception {
		log("Loginless flow : Verify the POST call for Reschedule appointment With Decision Tree and Show Provider is OFF");

		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

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
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptid_new);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayOFF() throws Exception {
		log("Verify the Accept same day OFF with Show provider OFF with POST call of /pss-patient-modulator/v1/{practiceId}/availableslot");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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

		logStep("Set up Admin Setting provider Off");
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		String reserveSameDayValue = "n";
		boolean acceptSameDayValue = false;
		int timeMarkValue = 0;
		String adapPayload = payloadAMFeature01.reserveForSameDay(reserveSameDayValue, acceptSameDayValue,
				timeMarkValue);
		String appTypeId = propertyData.getProperty("availableslot.apptid.pm.feature.ng");

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, appTypeId);
		apv.responseCodeValidation(response, 200);
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);

		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		String currentdate1 = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate1);
		int futureAdd = 1;
		String futuredate = pssPatientUtils.addDaysToDate(currentdate1, Integer.toString(futureAdd), "MM/dd/yyyy");
		log("Future Date -" + futuredate);

		String patientid = propertyData.getProperty("patient.id.pm01");
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

		String adapPayload1 = payloadAMFeature01.reserveForSameDay("n", true, 0);
		propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload1, appTypeId);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayON() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		String reserveSameDayValue = "n";
		boolean acceptSameDayValue = true;
		int timeMarkValue = 0;
		String adapPayload = payloadAMFeature01.reserveForSameDay(reserveSameDayValue, acceptSameDayValue,
				timeMarkValue);
		String appTypeId = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, appTypeId);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate1 = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate1);

		String patientid = propertyData.getProperty("patient.id.pm01");
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
	public void testTimeMark() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);
		String reserveSameDayValue = "n";
		boolean acceptSameDayValue = true;
		int timeMarkValue = 15;
		String adapPayload = payloadAMFeature01.reserveForSameDay(reserveSameDayValue, acceptSameDayValue,
				timeMarkValue);
		String appTypeId = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, appTypeId);
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");
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
		String actualTime = time.substring(3, 5);
		String expectedTime = String.valueOf(timeMarkValue);
		log("Actual Time is " + actualTime);
		assertEquals(actualTime, expectedTime, "TimeMark is not working properly");

		String adapPayload1 = payloadAMFeature01.reserveForSameDay(reserveSameDayValue, acceptSameDayValue, 0);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload1, appTypeId);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleWithSpeciality() throws Exception {

		log("Verify Age Rule in Specialty");
		String patientDob = "01-Jan-2000";
		int totalMonth = pssPatientUtils.ageCurrentmonths(patientDob);
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("SLTB", "S,L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("STLB", "S,T,L,B"));
		apv.responseCodeValidation(response, 200);

		String id1 = propertyData.getProperty("speciality.id.pm2");
		String name = propertyData.getProperty("speciality.name.pm01");
		String displayName = propertyData.getProperty("speciality.displayname.pm02");
		int id = Integer.parseInt(id1);
		String fVal = "210";
		String sVal = "270";
		String b = payloadAMFeature01.specialityAgeRulePost(id, name, displayName, fVal, sVal);
		response = postAPIRequestAM.specialitySave(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.specialityPost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.specialtyByRule(baseurl, b1, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("specialtyList").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("specialtyList").getJSONObject(i).getString("displayName");
			arrayList.add(kk);

		}
//		log("AL IS "+arrayList);	
//		String expectedValue="specialty 1";
//		boolean sExist=arrayList.contains(expectedValue);
//		log("Actual ......."+sExist);
//		assertEquals(true, sExist);

		int i = Integer.parseInt(fVal);
		int j = Integer.parseInt(sVal);
		boolean sExist = false;
		if (totalMonth > i && totalMonth < j) {
			log("AL IS " + arrayList);
			String expectedValue = "Internal Medicine";
			sExist = arrayList.contains(expectedValue);
			log("Actual ......." + sExist);
			assertEquals(true, sExist);
		} else {
			log("AL IS " + arrayList);
			String expectedValue = "Internal Medicine";
			sExist = arrayList.contains(expectedValue);
			log("Actual ......." + sExist);
			assertNotEquals(true, sExist);
		}
		log("Resetting the age rule");
		response = postAPIRequestAM.specialitySave(practiceIdAm,
				payloadAMFeature01.specialityAgeRuleResetPost(id, name, displayName));
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleWithProvider() throws Exception {

		log("Verify Age Rule in Specialty");
		String patientDob = "01-Jan-2000";
		int totalMonth = pssPatientUtils.ageCurrentmonths(patientDob);
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		String id1 = propertyData.getProperty("availableslot.bookid.pm.feature.ng");
		String bookName = propertyData.getProperty("book.name.pm01");
		String displayName = propertyData.getProperty("book.disp.name.pm01");
		String extId = propertyData.getProperty("book.ext.id.pm01");
		int id = Integer.parseInt(id1);
		String fVal = "210";
		String sVal = "270";

		String b = payloadAMFeature01.bookAgeRulePost(id, bookName, displayName, extId, fVal, sVal);
		response = postAPIRequestAM.bookSave(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.bookRulePost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.booksByRule(baseurl, b1, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("books").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("books").getJSONObject(i).getString("displayName");
			arrayList.add(kk);

		}

		int i = Integer.parseInt(fVal);
		int j = Integer.parseInt(sVal);
		boolean sExist = false;
		if (totalMonth > i && totalMonth < j) {
			log("AL IS " + arrayList);
			String expectedValue = bookName;
			sExist = arrayList.contains(expectedValue);
			log("Actual ......." + sExist);
			assertEquals(true, sExist);
			log("Passssssssssss");
		} else {
			log("AL IS " + arrayList);
			String expectedValue = bookName;
			sExist = arrayList.contains(expectedValue);
			log("Actual ......." + sExist);
			assertNotEquals(true, sExist);
			log("failllllllllllllllll");

		}
		log("Resetting the age rule");
		String resetPayload = payloadAMFeature01.resetBookAgeRulePost(id, bookName, displayName, extId);
		response = postAPIRequestAM.bookSave(practiceIdAm, resetPayload);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMergeSlot() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		String slotSize = propertyData.getProperty("slotsize.pm.pm02");
		int size = Integer.parseInt(slotSize);
		int slotCount = 3;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String patientId = propertyData.getProperty("patient.id.pm01");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.feature.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.feature.ng");

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate, locationid, apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
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

		adminPayload = payloadAMFeature01.mergeSlotPyaload(slotCount, size);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

		int mergeslottime = slotCount * size;
		String nextSlotInApptDuration = pssPatientUtils.addToTime(time, mergeslottime);

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);

		arr = new JSONArray(response.body().asString());
		String slotid2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(1).getString("slotId");
		String time2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(1).getString("slotTime");
		String date2 = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time2);
		log("slotId- " + slotid2);
		log("Date-" + date2);

		logStep("Slot Booked on fits attenpt- " + time);
		logStep("Slot will book on second attempt- " + time2);
		logStep("Expected slot in Appt Duration is - " + nextSlotInApptDuration);

		assertEquals(time2, nextSlotInApptDuration);

		adminPayload = payloadAMFeature01.mergeSlotPyaload(1, 5);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMergeSlotInvalidData() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		String slotSizeInvalid = propertyData.getProperty("invalid.slotsize.pm.pm02");
		int size = Integer.parseInt(slotSizeInvalid);
		log("slot size is  " + size);
		int slotCount = 3;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String patientId = propertyData.getProperty("patient.id.pm01");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.feature.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.feature.ng");

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		adminPayload = payloadAMFeature01.mergeSlotPyaload(1, 5);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate, locationid, apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
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

		adminPayload = payloadAMFeature01.mergeSlotPyaload(slotCount, size);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 204);

		adminPayload = payloadAMFeature01.mergeSlotPyaload(1, 5);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExcludeSlot() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		int noOfSlots = 3;
		String firstExpectedSlotTime = "01:00:00";
		String lastExpectedSlotTime = "01:10:00";
		String patientId = propertyData.getProperty("patient.id.pm01");
		String locationid = propertyData.getProperty("availableslot.locationid.pm.feature.ng");
		String apptid = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		arr = new JSONArray(response.body().asString());
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		adminPayload = payloadAMFeature01.excludeSlotPyaload();
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate, locationid, apptid);
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());

		int n = arr.length() - 2;

		int actualSlotLength = arr.getJSONObject(n).getJSONArray("slotList").length();

		String slotid = arr.getJSONObject(n).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(n).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(n).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);

		String time3 = arr.getJSONObject(n).getJSONArray("slotList").getJSONObject(actualSlotLength - 1)
				.getString("slotTime");

		log("slotTime-" + time3);
		assertEquals(actualSlotLength, noOfSlots);

		assertEquals(time, firstExpectedSlotTime, "Slot Times are not matching");
		assertEquals(time3, lastExpectedSlotTime, "Slot Times are not matching");

		adminPayload = payloadAMFeature01.resetExcludeSlotPyaload();
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionFFShowProviOFF() throws Exception {
		logStep("Set up the API authentication");
		boolean lastQueEnableValue = false;
		boolean lastQueRequValue = false;
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String payloadLastQEnable = payloadAMFeature01.lastQEnableshowProviderOFF(lastQueEnableValue, lastQueRequValue);
		String apptid = propertyData.getProperty("availableslot.apptid.pm.feature.ng");

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, payloadLastQEnable, apptid);
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");

		String b = payloadPssPMNG1.lastQueReqShowProOff(
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"), currentdate);

		response = postAPIRequest.commentDetails(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		boolean acceptComment = js.getBoolean("acceptComment");
		boolean lastQuestRequired = js.getBoolean("lastQuestRequired");
		assertTrue(lastQueEnableValue == acceptComment);
		assertTrue(lastQueRequValue == lastQuestRequired);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionTFShowProviOFF() throws Exception {
		logStep("Set up the API authentication");
		boolean lastQueEnableValue = true;
		boolean lastQueRequValue = false;
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String payloadLastQEnable = payloadAMFeature01.lastQEnableshowProviderOFF(lastQueEnableValue, lastQueRequValue);
		String apptid = propertyData.getProperty("availableslot.apptid.pm.feature.ng");

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, payloadLastQEnable, apptid);
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");

		String b = payloadPssPMNG1.lastQueReqShowProOff(
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"), currentdate);

		response = postAPIRequest.commentDetails(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		boolean acceptComment = js.getBoolean("acceptComment");
		boolean lastQuestRequired = js.getBoolean("lastQuestRequired");
		assertTrue(lastQueEnableValue == acceptComment);
		assertTrue(lastQueRequValue == lastQuestRequired);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionTTShowProviOFF() throws Exception {
		logStep("Set up the API authentication");
		boolean lastQueEnableValue = true;
		boolean lastQueRequValue = true;
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String payloadLastQEnable = payloadAMFeature01.lastQEnableshowProviderOFF(lastQueEnableValue, lastQueRequValue);
		String apptid = propertyData.getProperty("availableslot.apptid.pm.feature.ng");

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, payloadLastQEnable, apptid);
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");

		String b = payloadPssPMNG1.lastQueReqShowProOff(
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"), currentdate);

		response = postAPIRequest.commentDetails(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		boolean acceptComment = js.getBoolean("acceptComment");
		boolean lastQuestRequired = js.getBoolean("lastQuestRequired");
		assertTrue(lastQueEnableValue == acceptComment);
		assertTrue(lastQueRequValue == lastQuestRequired);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionFFShowProviON() throws Exception {
		logStep("Set up the API authentication");
		boolean lastQueEnableValue = false;
		boolean lastQueRequValue = false;
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);
		String locationId = propertyData.getProperty("availableslot.locationid.pm.feature.ng");
		String apptId = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		String bookId = propertyData.getProperty("availableslot.bookid.pm.feature.ng");

		String bookName = "Ng3 Pss [PSS,NG3]";
		String extId = "C20178B6-BE4A-4176-B7FD-B6901C38A051";
		String payloadLastQueEnable = payloadAMFeature01.bookPayload(bookId, bookName, extId, lastQueEnableValue);

		String payloadLastQueReq = payloadAMFeature01.bookAppTypePayload(bookId, apptId, lastQueRequValue);

		response = postAPIRequestAM.saveBook(practiceIdAm, payloadLastQueEnable);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, payloadLastQueReq);
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");

		String b = payloadPssPMNG1.lastQueReqShowProON(locationId, bookId, apptId, currentdate);

		response = postAPIRequest.commentDetails(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		boolean acceptComment = js.getBoolean("acceptComment");
		boolean lastQuestRequired = js.getBoolean("lastQuestRequired");
		assertTrue(lastQueEnableValue == acceptComment);
		assertTrue(lastQueRequValue == lastQuestRequired);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionTTShowProviON() throws Exception {
		logStep("Set up the API authentication");
		boolean lastQueEnableValue = true;
		boolean lastQueRequValue = true;
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);
		String locationId = propertyData.getProperty("availableslot.locationid.pm.feature.ng");
		String apptId = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		String bookId = propertyData.getProperty("availableslot.bookid.pm.feature.ng");

		String bookName = "Ng3 Pss [PSS,NG3]";
		String extId = "C20178B6-BE4A-4176-B7FD-B6901C38A051";
		String payloadLastQueEnable = payloadAMFeature01.bookPayload(bookId, bookName, extId, lastQueEnableValue);

		String payloadLastQueReq = payloadAMFeature01.bookAppTypePayload(bookId, apptId, lastQueRequValue);
		response = postAPIRequestAM.saveBook(practiceIdAm, payloadLastQueEnable);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, payloadLastQueReq);
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");

		String b = payloadPssPMNG1.lastQueReqShowProON(locationId, bookId, apptId, currentdate);

		response = postAPIRequest.commentDetails(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		boolean acceptComment = js.getBoolean("acceptComment");
		boolean lastQuestRequired = js.getBoolean("lastQuestRequired");
		assertTrue(lastQueEnableValue == acceptComment);
		assertTrue(lastQueRequValue == lastQuestRequired);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionTFShowProviON() throws Exception {
		logStep("Set up the API authentication");
		boolean lastQueEnableValue = true;
		boolean lastQueRequValue = false;
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

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
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);
		String locationId = propertyData.getProperty("availableslot.locationid.pm.feature.ng");
		String apptId = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		String bookId = propertyData.getProperty("availableslot.bookid.pm.feature.ng");

		String bookName = "Ng3 Pss [PSS,NG3]";
		String extId = "C20178B6-BE4A-4176-B7FD-B6901C38A051";
		String payloadLastQueEnable = payloadAMFeature01.bookPayload(bookId, bookName, extId, lastQueEnableValue);

		String payloadLastQueReq = payloadAMFeature01.bookAppTypePayload(bookId, apptId, lastQueRequValue);

		response = postAPIRequestAM.saveBook(practiceIdAm, payloadLastQueEnable);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, payloadLastQueReq);
		apv.responseCodeValidation(response, 200);

		String patientid = propertyData.getProperty("patient.id.pm01");

		String b = payloadPssPMNG1.lastQueReqShowProON(locationId, bookId, apptId, currentdate);
		response = postAPIRequest.commentDetails(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		boolean acceptComment = js.getBoolean("acceptComment");
		boolean lastQuestRequired = js.getBoolean("lastQuestRequired");
		assertTrue(lastQueEnableValue == acceptComment);
		assertTrue(lastQueRequValue == lastQuestRequired);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderRuleWithSpeciality() throws Exception {

		log("Verify Gender Rule in Specialty");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("SLTB", "S,L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("STLB", "S,T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAMFeature01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.gendermapPost(practiceIdAm, payloadAMFeature01.genderMap("Male", "M", "M", 1));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.gendermapPost(practiceIdAm, payloadAMFeature01.genderMap("Female", "F", "F", 2));
		apv.responseCodeValidation(response, 200);

		// String name = propertyData.getProperty("speciality.name.pm01");

		adminPayload = payloadAMFeature01.genderRuleSpeciality();
		response = postAPIRequestAM.specialitySave(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.specialityPost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.specialtyByRule(baseurl, b1, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("specialtyList").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("specialtyList").getJSONObject(i).getString("displayName");
			arrayList.add(kk);

		}

		log("AL IS " + arrayList);

//		adminPayload = payloadAMFeature01.genderRuleSpeciality(id, name, displayName, true, true);
//		response = postAPIRequestAM.specialitySave(practiceIdAm, adminPayload);
//		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventBackToBack() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		int slotsize = 5;
		int apptDuration = 2 * slotsize;
		String apptid = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		arr = new JSONArray(response.body().asString());
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		adminPayload = payloadAMFeature01.preventBackToBackPyaload(true);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String patientid = propertyData.getProperty("patient.id.pm01");
		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());
		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");
		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);
		String nextSlotInApptDuration = pssPatientUtils.addToTime(time, apptDuration);

		String c = payloadPssPMNG1.llScheduleAppointmentPayloadLTWithoutDecTree(slotid, date, time,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		log("Payload-" + c);
		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientid, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);
		String extApptId = apv.responseKeyValidationJson(schedResponse, "extApptId");

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());
		String slotid2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date2 = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time2);
		log("slotId- " + slotid2);
		log("Date-" + date2);

		logStep("Slot Booked on fits attenpt- " + time);
		logStep("Slot will book on second attempt- " + time2);
		logStep("Expected slot in Appt Duration is - " + nextSlotInApptDuration);
		assertEquals(time2, nextSlotInApptDuration);

		String cancelPayload = payloadPatientMod.cancelAppointmentPayload(extApptId);
		log("CancelPayload is  " + cancelPayload);
		Response cancelResponse = postAPIRequest.cancelAppointment(baseurl,
				payloadPatientMod.cancelAppointmentPayload(extApptId), headerConfig.HeaderwithToken(accessToken),
				practiceid, patientid);
		apv.responseCodeValidation(cancelResponse, 200);

		adminPayload = payloadAMFeature01.preventBackToBackPyaload(false);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptid);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventScheduling() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		int preventSchedDays = 30;
		String patientid = propertyData.getProperty("patient.id.pm01");
		String apptId1 = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		String apptName = propertyData.getProperty("appt.name.pm01");
		String displayName = propertyData.getProperty("appt.disp.name.pm01");
		String extId = propertyData.getProperty("appt.ext.id.pm01");
		int id = Integer.parseInt(apptId1);

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		arr = new JSONArray(response.body().asString());
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		adminPayload = payloadAMFeature01.preventSchedulePyaload(id, preventSchedDays, apptName, displayName, extId);
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				"58553");
		apv.responseCodeValidation(response, 412);
		apv.responseTimeValidation(response);
		String errorMessage = apv.responseKeyValidationJson(response, "message");

		assertTrue(errorMessage.contains("Prevent Scheduling"));

		response = postAPIRequest.announcementByName(baseurl, headerConfig.HeaderwithToken(accessToken), practiceid,
				"ARP");
		apv.responseCodeValidation(response, 200);

		adminPayload = payloadAMFeature01.preventSchedulePyaload(id, 0, apptName, displayName, extId);
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventSchedulingPastApp() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		int preventSchedDays = 70;
		String patientid = "58419";
		String apptId1 = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		String apptName = propertyData.getProperty("appt.name.pm01");
		String displayName = propertyData.getProperty("appt.disp.name.pm01");
		String extId = propertyData.getProperty("appt.ext.id.pm01");
		int id = Integer.parseInt(apptId1);

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		arr = new JSONArray(response.body().asString());
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		adminPayload = payloadAMFeature01.preventSchedulePyaload(id, preventSchedDays, apptName, displayName, extId);
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String pastAptDate = propertyData.getProperty("pastappointment.date.ng");

		SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
		Date date1 = formatter2.parse(pastAptDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);

		cal.add(Calendar.DATE, preventSchedDays);
		Date dateWithPrevSchedDaysAdded = cal.getTime();
		logStep("Slot should be visible from this date- " + dateWithPrevSchedDaysAdded);

		DateFormat dateFormat = new SimpleDateFormat("dd");
		String strDateExp = dateFormat.format(dateWithPrevSchedDaysAdded);

		logStep("Calculated Date in DD format- " + strDateExp);

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate,
				propertyData.getProperty("availableslot.locationid.pm.feature.ng"),
				propertyData.getProperty("availableslot.apptid.pm.feature.ng"));

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		arr = new JSONArray(response.body().asString());
		String slotid2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time2 = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date2 = arr.getJSONObject(0).getString("date");
		String actualDate = date2.substring(3, 5);

		log("slotTime-" + time2);
		log("slotId- " + slotid2);
		log("Date-" + actualDate);

		log("Expected Date in DD format- " + strDateExp);
		log("Actual Date- " + actualDate);
		assertEquals(actualDate, strDateExp, "Prevent Scheduling not working properly");

		adminPayload = payloadAMFeature01.preventSchedulePyaload(id, 0, apptName, displayName, extId);
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlockoutBillingNotes() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JsonPath js;
		String expectedMsg = propertyData.getProperty("lockout.billing.expected.msg.pm01");
		adminPayload = payloadAMFeature01.lockoutBillingNotesPayload();
		response = postAPIRequestAM.lockoutPost(practiceIdAm, adminPayload, "/lockout");
		apv.responseCodeValidation(response, 200);
		String id = apv.responseKeyValidationJson(response, "id");
		response = postAPIRequest.patientDemographicsWithQueryParameter(baseurl,
				headerConfig.HeaderwithToken(accessToken), practiceid, "58283");
		apv.responseCodeValidation(response, 200);
		js = new JsonPath(response.body().asString());
		boolean lockOut = js.getBoolean("lockOut");
		String message = js.getString("message");
		log("lockoutValue -------" + lockOut);
		log("Message -------" + message);
		assertEquals(expectedMsg, message);
		assertEquals(true, lockOut);
		response = postAPIRequestAM.deleteLockoutById(practiceIdAm, id);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutPatientNotes() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JsonPath js;
		adminPayload = payloadAMFeature01.lockoutPatientNotesPayload();
		response = postAPIRequestAM.lockoutPost(practiceIdAm, adminPayload, "/lockout");
		apv.responseCodeValidation(response, 200);
		String id = apv.responseKeyValidationJson(response, "id");
		response = postAPIRequest.patientDemographicsWithQueryParameter(baseurl,
				headerConfig.HeaderwithToken(accessToken), practiceid, "58283");
		apv.responseCodeValidation(response, 200);
		js = new JsonPath(response.body().asString());
		boolean lockOut = js.getBoolean("lockOut");
		String message = js.getString("message");

		log("lockoutValue -------" + lockOut);
		log("Message -------" + message);
		response = postAPIRequestAM.deleteLockoutById(practiceIdAm, id);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testOverBookingStackingApp() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"),
				propertyData.getProperty("mf.authuserid.am01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		String apptId = propertyData.getProperty("availableslot.apptid.pm.feature.ng");
		String locationId = propertyData.getProperty("availableslot.locationid.pm.feature.ng");
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceIdAm);
		arr = new JSONArray(response.body().asString());
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
				payloadAMFeature01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceIdAm, locationId);
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		adminPayload = payloadAMFeature01.overBookingPayload(true);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		String patientId01 = propertyData.getProperty("patient.id.pm.ng");
		String patientId02 = propertyData.getProperty("patient.id.overbooking.pm.ng");

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentdate, locationId, apptId);

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId01);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		arr = new JSONArray(response.body().asString());
		String slotid = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("date2- " + date);

		String c = payloadPssPMNG1.llScheduleAppointmentPayloadLTWithoutDecTree(slotid, date, time, locationId, apptId);

		log("Payload-" + c);
		Response schedResponse = postAPIRequest.scheduleAppointment(baseurl, c,
				headerConfig.HeaderwithToken(accessToken), practiceid, patientId01, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);
		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");

		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientId02);
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

		adminPayload = payloadAMFeature01.overBookingPayload(false);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adminPayload, apptId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}
}
