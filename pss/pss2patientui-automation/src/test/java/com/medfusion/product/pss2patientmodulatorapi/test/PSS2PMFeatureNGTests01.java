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
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;

public class PSS2PMFeatureNGTests01 extends BaseTestNG {

	public static HeaderConfig headerConfig;
	public static PSSPropertyFileLoader propertyData;

	public static Appointment testData;
	public static PostAPIRequestPMNG postAPIRequest;
	public static PayloadPMNGFeature01 payloadPssPMNG2;
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
		payloadPssPMNG2 = new PayloadPMNGFeature01();

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
				payloadAdapterModulatorFeature.turnONShowProvider());
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONDecisionTree(true));
		apv.responseCodeValidation(response, 200);

        String b=payloadPssPMNG2.ssoSchedulewithDectree();
        String patientID=propertyData.getProperty("patient.id.feature.pm.ng");
		response = postAPIRequest.scheduleAppointmentExisting(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid, patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testssoWithoutDecTreeShowProviderONPost() throws IOException {
        String b=payloadPssPMNG2.ssoPostwithoutDecTree();
        String patientID=propertyData.getProperty("patient.id.feature.pm.ng");
		Response response = postAPIRequest.scheduleAppointmentExisting(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid, patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testssoWithoutDecTreeShowProviderOffPost() throws IOException {
        String b=payloadPssPMNG2.ssoPostwithoutDecTreeShowProviderOFF();
        String patientID=propertyData.getProperty("patient.id.feature.pm.ng");
		Response response = postAPIRequest.ssorescheduleAppointment(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid, patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testssoWithDecTreeShowProviderOffPost() throws IOException {
        String b=payloadPssPMNG2.ssoPostwithDecTreeShowProviderOFF();
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
		
		
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAdapterModulatorFeature.turnOFFShowProvider());
		apv.responseCodeValidation(response, 200);
		
		//int timeMarkValue=30;
		String adapPayload=payloadAdapterModulatorFeature.timeMark();
		
		response = postAPIRequestAM.timeMark(practiceIdAm, adapPayload, "206528");
		apv.responseCodeValidation(response, 200);
		String startDate=pssPatientUtils.sampleDateTime("MM/dd/yyyy");
        String b=payloadPssPMNG2.avaliableSlot(startDate);
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
				payloadAdapterModulatorFeature.turnOFFShowProvider());
		apv.responseCodeValidation(response, 200);

		String adapPayload = payloadAdapterModulatorFeature.reserveForSameDay("s");

		response = postAPIRequestAM.timeMark(practiceIdAm, adapPayload, "206528");
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG2.avaliableSlot(startDate);

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
		String adapPayloadforReset = payloadAdapterModulatorFeature.reserveForSameDay("n");
		response = postAPIRequestAM.timeMark(practiceIdAm, adapPayloadforReset, "206528");
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousScheduleWithDeciShowProviderON() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider ON.");
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
				payloadAdapterModulatorFeature.turnONShowProvider());
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String b = payloadPssPMNG2.scheduleApp(startDate);

		String patientID = "58514";
		response = postAPIRequest.scheduleAppointmentExisting(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONDecisionTree(false));
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousScheduleWithDeciShowProviderOFF() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider OFF.");
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

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String b = payloadPssPMNG2.scheduleApp(startDate);

		String patientID = "58514";
		response = postAPIRequest.scheduleAppointmentExisting(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONDecisionTree(false));
		apv.responseCodeValidation(response, 200);

	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousScheduleWithoutDeciShowProviderOFF() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment without Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"), propertyData.getProperty("mf.authuserid.am.feature.ng"));
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
				payloadAdapterModulatorFeature.turnONDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String b = payloadPssPMNG2.scheduleApp(startDate);

		String patientID = "58514";
		response = postAPIRequest.scheduleAppointment(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientID,"PT_ALL");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousScheduleWithDeciShowProviderOFF1() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.feature.pm.ng"), propertyData.getProperty("mf.authuserid.am.feature.ng"));
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
				payloadAdapterModulatorFeature.turnONDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String b = payloadPssPMNG2.scheduleApp(startDate);

		String patientID = "58514";
		response = postAPIRequest.scheduleAppointment(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientID,"PT_ALL");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONDecisionTree(false));
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHideSlot() throws Exception {
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

		String adapPayload = payloadAdapterModulatorFeature.hideSlots();

		response = postAPIRequestAM.timeMark(practiceIdAm, adapPayload, "205754");
		apv.responseCodeValidation(response, 200);
		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String b = payloadPssPMNG2.avaliableSlot(startDate);
		String patientID = propertyData.getProperty("patient.id.feature.pm.ng");
		response = postAPIRequest.availableSlots(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
				patientID);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

//		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
//				payloadAdapterModulatorFeature.turnONDecisionTree(true));
//		apv.responseCodeValidation(response, 200);

//		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
//		String b = payloadPssPMNG2.scheduleApp(startDate);
//
//		String patientID = "58514";
//		response = postAPIRequest.scheduleAppointment(baseurl, b, headerConfig.HeaderwithToken(accessToken), practiceid,
//				patientID,"PT_ALL");
//		apv.responseCodeValidation(response, 200);
//		apv.responseTimeValidation(response);
//		
//		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
//				payloadAdapterModulatorFeature.turnONDecisionTree(false));
//		apv.responseCodeValidation(response, 200);

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
				payloadAdapterModulatorFeature.turnONDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String b = payloadPssPMNG2.scheduleAppForPTNEW(startDate);

		response = postAPIRequest.scheduleAppointment_PTNEW(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm,
				payloadAdapterModulatorFeature.turnONDecisionTree(false));
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppByIndex() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		Response response;
        String b=payloadPssPMNG2.schedulewithoutProvider();
		response = postAPIRequest.scheduleAppointmentExisting(baseurl, b, headerConfig.HeaderwithToken(accessToken),
				practiceid,"58514");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		response = postAPIRequest.upComingAppointmentsByPage(baseurl,headerConfig.HeaderwithToken(accessToken),
				practiceid, "58514");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppByIndex() throws Exception {
		logStep("Set up the API authentication");
		Response response;
		response = postAPIRequest.pastAppointmentsByPage(baseurl,headerConfig.HeaderwithToken(accessToken),
				practiceid, "58514");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void rescheduleApp() throws Exception {
		log("Annonymous flow : Verify the POST call for Schedule Appointment with Decision Tree and with Show Provider OFF.");
		logStep("Set up the API authentication");
		Response response;
        String schedulePayload=payloadPssPMNG2.schedulewithoutProvider();
		response = postAPIRequest.scheduleAppointmentExisting(baseurl,schedulePayload, headerConfig.HeaderwithToken(accessToken),
				practiceid,"58514");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		String reschedulePayload="";
		response = postAPIRequest.rescheduleAppointment(baseurl,reschedulePayload, headerConfig.HeaderwithToken(accessToken),
				practiceid, "58514", "PTALL");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
}
