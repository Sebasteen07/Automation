package com.medfusion.product.pss2patientmodulatorapi.test;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.BeforeTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestPMNG;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAM01;
import com.medfusion.product.pss2patientapi.payload.PayloadPM01;
import com.medfusion.product.pss2patientapi.payload.PayloadPssPMNG;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;



public class PSS2PMFeatureNGTests06 extends BaseTestNG {
	
	public static HeaderConfig headerConfig;
	public static PSSPropertyFileLoader propertyData;

	public static Appointment testData;
	public static PostAPIRequestPMNG postAPIRequest;
	public static PayloadPM01 payloadPssPMNG1;
	public static PayloadPssPMNG payloadPatientMod;

	public static String accessToken;
	public static String practiceId;
	public static String baseUrl;
	public PSSPatientUtils pssPatientUtils;

	APIVerification apv;
	public static PayloadAdapterModulator payloadAM;
	public static PayloadAM01 payloadAM01;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	public static String openToken;
	public static String practiceIdAm;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUpPM() throws IOException {

		log("Set the Authorization for Patient Modulator");
		headerConfig = new HeaderConfig();
		propertyData = new PSSPropertyFileLoader();
		testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		postAPIRequest = new PostAPIRequestPMNG();
		payloadPssPMNG1 = new PayloadPM01();
		payloadPatientMod = new PayloadPssPMNG();
		baseUrl = propertyData.getProperty("base.url.pm.ng");
		practiceId = propertyData.getProperty("practice.id.pm01");
		accessToken = postAPIRequest.createToken(baseUrl, practiceId);
		pssPatientUtils = new PSSPatientUtils();
		apv = new APIVerification();
		log("Base URL for Patient Modulator - " + baseUrl);

	}

	public void setUpAM(String practiceId1, String userID) throws IOException {
		payloadAM01 = new PayloadAM01();
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();
		practiceIdAm = practiceId1;
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceIdAm,
				payloadAM01.openTokenPayload(practiceIdAm, userID));
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptDurationWithShowProviderOff() throws Exception {
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		int apptDuration = 30;
		response = postAPIRequestAM.locationById(practiceIdAm, propertyData.getProperty("location.id.pm01"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentDate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentDate - " + currentDate);

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

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		String patientId = propertyData.getProperty("patient.id.pm01");
		
		String locationId = propertyData.getProperty("location.id.pm01");
		String apptId = propertyData.getProperty("appt.id.duration01");
		adminPayload = payloadAM01.appTypeDuration();
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceId, adminPayload, apptId);
		apv.responseCodeValidation(response, 200);

		String currentdate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentDate, locationId, apptId);
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		arr = new JSONArray(response.body().asString());
		String slotId = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(0).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(0).getString("date");
		log("slotTime-" + time);
		log("slotId- " + slotId);
		log("Date-" + date);
		String nextSlotInApptDuration = pssPatientUtils.addToTime(time, apptDuration);
		String c = payloadPssPMNG1.llScheduleAppointmentPayloadLTWithoutDecTree(slotId, date, time, locationId, apptId);
		log("Payload-" + c);
		Response schedResponse = postAPIRequest.scheduleAppointment(baseUrl, c,
				headerConfig.HeaderwithToken(accessToken), practiceId, patientId, testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		String extapptId = apv.responseKeyValidationJson(schedResponse, "extApptId");
		log("ext App ID is " + extapptId);
		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
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
		Response cancelResponse = postAPIRequest.cancelAppointment(baseUrl,
				payloadPatientMod.cancelAppointmentPayload(extapptId), headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(cancelResponse, 200);
     	adminPayload = payloadAM01.resetAppTypeDuration();
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceId, adminPayload, apptId);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderRuleWithSpeciality() throws Exception {
		log("Verify Gender Rule in Specialty");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
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

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.gendermapPost(practiceIdAm, payloadAM01.genderMap("Male", "M", "M", 1));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.gendermapPost(practiceIdAm, payloadAM01.genderMap("Female", "F", "F", 2));
		apv.responseCodeValidation(response, 200);

		String specialityName = propertyData.getProperty("speciality.name.pm01");

		adminPayload = payloadAM01.genderRuleSpeciality();
		response = postAPIRequestAM.specialitySave(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.specialityPost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.specialtyByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken), practiceId,
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
			boolean specialiyStatus = arrayList.contains(specialityName);
			assertTrue(specialiyStatus);
		}

		adminPayload = payloadAM01.resetGenderRuleSpeciality();
		response = postAPIRequestAM.specialitySave(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventSchedulingFutureShowProviderOFF() throws Exception {
		log("Verify the Prevent Scheduling appointment type within (Days) with Show Provider OFF with POST call of /pss-patient-modulator/v1/{practiceId}/availableslot");
		logStep("Set up the API authentication");		
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		String adminPayload;
		JSONArray arr;
		int preventSchedDays = 30;
		String patientId = propertyData.getProperty("patient.id.pm01");
		String apptId1 = propertyData.getProperty("appt.id.pm01");
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
				payloadAM01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceIdAm,
				propertyData.getProperty("availableslot.locationid.pm.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		adminPayload = payloadAM01.preventSchedulePyaload(id, preventSchedDays, apptName, displayName, extId);
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

		String currentDate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentDate - " + currentDate);

		String b = payloadPssPMNG1.availableslotsPayloadLT(currentDate,
				propertyData.getProperty("location.id.pm01"),
				propertyData.getProperty("appt.id.pm01"));

		response = postAPIRequest.availableSlots(baseUrl, b, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 412);
		apv.responseTimeValidation(response);
		String errorMessage = apv.responseKeyValidationJson(response, "message");
		assertTrue(errorMessage.contains("Prevent Scheduling"));

		response = postAPIRequest.announcementByName(baseUrl, headerConfig.HeaderwithToken(accessToken), practiceId,
				"ARP");
		apv.responseCodeValidation(response, 200);

		adminPayload = payloadAM01.preventSchedulePyaload(id, 0, apptName, displayName, extId);
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void rough() throws Exception {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("atul");
		arrayList.add("atul1");
		arrayList.add("atul11");
		arrayList.add("atul111");

	   boolean aa= arrayList.contains("atul11");
	   log("value of aa is   "+aa);
		
		
       
		
	}
	

}

