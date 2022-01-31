package com.medfusion.product.pss2patientmodulatorapi.test;

import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.BeforeTest;

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
	public void testShowProviderImages() throws Exception {
		log("Verify call for provider images when ON");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		String adminPayload;
		adminPayload = payloadAM01.providerImages(true);
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

		String bookId = propertyData.getProperty("book.id.showimages.pm01");
		response = postAPIRequest.getImages(baseUrl, headerConfig.HeaderwithToken(accessToken), practiceId, bookId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		log("Verify provider images when OFF");
		adminPayload = payloadAM01.providerImages(false);
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);

		response = postAPIRequest.getImages(baseUrl, headerConfig.HeaderwithToken(accessToken), practiceId, bookId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleBookAppTypeBeforeProvider() throws Exception {
		log("Verify the Age Rule at Book-Appointment type level when Appointment Type is before Provider in the RULE");
		String patientDob = propertyData.getProperty("patient.dob.pmo1");
		int totalMonth = pssPatientUtils.ageCurrentmonths(patientDob);
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("BTL", "B,T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LBT", "L,B,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFDecisionTree(true));
		apv.responseCodeValidation(response, 200);

		String catName = "NewCat";
		adminPayload = payloadAM01.createDecisionTree();
		response = postAPIRequestAM.saveCategory(practiceIdAm, adminPayload);
		apv.responseCodeValidation(response, 200);
		int bookId = Integer.parseInt(propertyData.getProperty("book.id.pm01"));
		int apptId = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));
		String firstVal = propertyData.getProperty("agerule.firstvalue.pm01");
		String secondVal = propertyData.getProperty("agerule.secondvalue.pm01");
        int bookConfigId=Integer.parseInt(propertyData.getProperty("book.appt.config.id.pm01"));
		String b = payloadAM01.ageRuleOnBookAppointmentType(bookId, apptId, firstVal, secondVal,bookConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.bookRulePost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.appointmentTypesByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedCategory").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("exposedCategory").getJSONObject(i).getString("displayName");
			arrayList.add(kk);
		}
		int i = Integer.parseInt(firstVal);
		int j = Integer.parseInt(secondVal);
		boolean sExist = false;
		if (totalMonth > i && totalMonth < j) {
			String expectedValue = catName;
			sExist = arrayList.contains(expectedValue);
			assertEquals(true, sExist);
		} else {
			String expectedValue = catName;
			sExist = arrayList.contains(expectedValue);
			log("In Else Block");
			assertNotEquals(true, sExist);

		}
		log("Resetting the age rule");
		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFDecisionTree(false));

		apv.responseCodeValidation(response, 200);
		String resetAgePayload = payloadAM01.resetAgeRuleOnBookAppointmentType(bookId, apptId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, resetAgePayload);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExistingUserPatientTypePT_NEW_ApptRule() throws Exception {
		log("Verify Existing User with Patient type : NEW for https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1/24293/appointmenttypes/rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("BTL", "B,T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LBT", "L,B,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		int bookId = Integer.parseInt(propertyData.getProperty("book.id.pm01"));
		int apptId = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));
		String apptName = propertyData.getProperty("appt.name.pm01");
		String patientType = "PT_NEW";
		int bookApptConfigId = Integer.parseInt(propertyData.getProperty("book.appt.config.id.pm01"));
		String b = payloadAM01.bookAppointmentTypePatientType(bookId, apptId, patientType, bookApptConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.appointmentRulePost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.appointmentTypesByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			arrayList.add(kk);
		}
		boolean apptExist = arrayList.contains(apptName);
		assertNotEquals(true, apptExist);

		String resetAppType = payloadAM01.bookAppointmentTypePatientType(bookId, apptId, "PT_ALL", bookApptConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, resetAppType);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExistingUserPatientTypePT_EXISTING_ApptRule() throws Exception {
		log("Verify Existing User with Patient type : NEW for https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1/24293/appointmenttypes/rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("BTL", "B,T,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LBT", "L,B,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		int bookId = Integer.parseInt(propertyData.getProperty("book.id.pm01"));
		int apptId = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));
		String apptName = propertyData.getProperty("appt.name.pm01");
		String patientType = "PT_EXISTING";
		int bookApptConfigId = Integer.parseInt(propertyData.getProperty("book.appt.config.id.pm01"));
		String b = payloadAM01.bookAppointmentTypePatientType(bookId, apptId, patientType, bookApptConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.appointmentRulePost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.appointmentTypesByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			arrayList.add(kk);
		}
		boolean apptExist = arrayList.contains(apptName);
		assertEquals(true, apptExist);

		String resetAppType = payloadAM01.bookAppointmentTypePatientType(bookId, apptId, "PT_ALL", bookApptConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, resetAppType);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExistingUserPatientTypePT_NEW_BookRule() throws Exception {
		log("Verify EXISTING User with Patient type : New for https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1/24293/book/rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LBT", "L,B,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		int bookId = Integer.parseInt(propertyData.getProperty("book.id.pm01"));
		int apptId = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));
		String bookName = propertyData.getProperty("book.name.pm01");
		String patientType = "PT_NEW";
		int bookApptConfigId = Integer.parseInt(propertyData.getProperty("book.appt.config.id.pm01"));
		String b = payloadAM01.bookAppointmentTypePatientType(bookId, apptId, patientType, bookApptConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.bookRuleBeforeApptPost(apptId);
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.booksByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken), practiceId,
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
		boolean apptExist = arrayList.contains(bookName);
		assertNotEquals(true, apptExist);

		String resetAppType = payloadAM01.bookAppointmentTypePatientType(bookId, apptId, "PT_ALL", bookApptConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, resetAppType);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExistingUserPatientTypePT_EXISTINGBookRule() throws Exception {
		log("Verify EXISTING User with Patient type : EXISTING for https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1/24293/book/rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LBT", "L,B,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);
		int apptId = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));
		int bookId = Integer.parseInt(propertyData.getProperty("book.id.pm01"));
		String bookName = propertyData.getProperty("book.name.pm01");

		String patientType = "PT_EXISTING";
		int bookApptConfigId = Integer.parseInt(propertyData.getProperty("book.appt.config.id.pm01"));
		String b = payloadAM01.bookAppointmentTypePatientType(bookId, apptId, patientType, bookApptConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.bookRuleBeforeApptPost(apptId);
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.booksByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken), practiceId,
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
		boolean apptExist = arrayList.contains(bookName);
		assertEquals(true, apptExist);

		String resetAppType = payloadAM01.bookAppointmentTypePatientType(bookId, apptId, "PT_ALL", bookApptConfigId);
		response = postAPIRequestAM.bookAppointmentTypeUpdate(practiceIdAm, resetAppType);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNewUserPatientTypePT_NEW_ApptRuleShowProviderOFF() throws Exception {
		log("Verify New User with Patient type : New (Show provider OFF ) for https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1/24293/appointmenttypes/rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm, propertyData.getProperty("location.id.pm01"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentDate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentDate - " + currentDate);
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

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String apptName = propertyData.getProperty("appt.name.pm01");
		String patientType = "PT_NEW";
		int apptConfigId = Integer.parseInt(propertyData.getProperty("appt.config.id.pm01"));
		String adapPayload = payloadAM01.patientTypeShowProviderOFF(patientType, apptConfigId);
		String apptId = propertyData.getProperty("appt.id.pm01");

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, apptId);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.appTypeRulePTNew();
		response = postAPIRequest.appointmentTypesByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken),
				practiceId, "");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			arrayList.add(kk);
		}
		boolean apptExist = arrayList.contains(apptName);
		assertEquals(true, apptExist);

		String resetPayload = payloadAM01.patientTypeShowProviderOFF("PT_ALL", apptConfigId);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, resetPayload, apptId);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNewUserPatiTypePT_Existing_ApptRuleShowProviderOFF() throws Exception {
		log("Verify New User with Patient type : EXISTING (Show provider OFF ) for https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1/24293/appointmenttypes/rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm, propertyData.getProperty("location.id.pm01"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentDate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentDate - " + currentDate);
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

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String apptName = propertyData.getProperty("appt.name.pm01");
		String patientType = "PT_EXISTING";
		int apptConfigId = Integer.parseInt(propertyData.getProperty("appt.config.id.pm01"));
		String adapPayload = payloadAM01.patientTypeShowProviderOFF(patientType, apptConfigId);
		String apptId = propertyData.getProperty("appt.id.pm01");

		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, apptId);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.appTypeRulePTNew();
		response = postAPIRequest.appointmentTypesByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken),
				practiceId, "");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			arrayList.add(kk);
		}
		boolean apptExist = arrayList.contains(apptName);
		assertNotEquals(true, apptExist);

		String resetPayload = payloadAM01.patientTypeShowProviderOFF("PT_ALL", apptConfigId);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, resetPayload, apptId);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExiUserPatiPT_ExistingApptRuleShowProviderOFF() throws Exception {
		log("Verify Existing User with Patient type : EXISTING (Show provider OFF ) for https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1/24293/appointmenttypes/rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm, propertyData.getProperty("location.id.pm01"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentDate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentDate - " + currentDate);
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

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String apptName = propertyData.getProperty("appt.name.pm01");
		String patientType = "PT_EXISTING";
		int apptConfigId = Integer.parseInt(propertyData.getProperty("appt.config.id.pm01"));
		String apptId = propertyData.getProperty("appt.id.pm01");
		String adapPayload = payloadAM01.patientTypeShowProviderOFF(patientType, apptConfigId);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, apptId);
		apv.responseCodeValidation(response, 200);
		String patientId = propertyData.getProperty("patient.id.pm01");
		int apptId1 = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));

		String b1 = payloadPssPMNG1.bookRuleBeforeApptPost(apptId1);
		response = postAPIRequest.appointmentTypesByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			arrayList.add(kk);
		}
		log("Appointment List is ........." + arrayList);
		boolean apptExist = arrayList.contains(apptName);
		assertEquals(true, apptExist);

		String resetPayload = payloadAM01.patientTypeShowProviderOFF("PT_ALL", apptConfigId);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, resetPayload, apptId);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExiUserPT_ALL_ApptRuleShowProviderOFF() throws Exception {
		log("Verify Existing User with Patient type : ALL (Show provider OFF ) for https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1/24293/appointmenttypes/rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		response = postAPIRequestAM.locationById(practiceIdAm, propertyData.getProperty("location.id.pm01"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);

		String currentDate = pssPatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentDate - " + currentDate);
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

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(response, 200);

		String apptName = propertyData.getProperty("appt.name.pm01");
		String patientType = "PT_ALL";
		int apptConfigId = Integer.parseInt(propertyData.getProperty("appt.config.id.pm01"));
		String apptId = propertyData.getProperty("appt.id.pm01");
		int apptId1 = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));
		String adapPayload = payloadAM01.patientTypeShowProviderOFF(patientType, apptConfigId);
		response = postAPIRequestAM.appointmenttypeConfgWithBookOff(practiceIdAm, adapPayload, apptId);
		apv.responseCodeValidation(response, 200);
		String patientId = propertyData.getProperty("patient.id.pm01");

		String b1 = payloadPssPMNG1.bookRuleBeforeApptPost(apptId1);
		response = postAPIRequest.appointmentTypesByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			arrayList.add(kk);
		}
		boolean apptExist = arrayList.contains(apptName);
		assertEquals(true, apptExist);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleBookApptBeforeAppointment() throws Exception {
		log("Verify the Age Rule at Appointment Type Level when Provider is before Appointment type in Rule");
		String patientDob = propertyData.getProperty("patient.dob.pmo1");
		int totalMonth = pssPatientUtils.ageCurrentmonths(patientDob);
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LBT", "L,B,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		String bookName = propertyData.getProperty("book.name.pm01");
		String apptId1 = propertyData.getProperty("appt.id.pm01");
		int apptId = Integer.parseInt(apptId1);
		String firstVal = propertyData.getProperty("agerule.firstvalue.pm01");
		String secondVal = propertyData.getProperty("agerule.secondvalue.pm01");

		String b = payloadAM01.ageRuleAppType(apptId, firstVal, secondVal);
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.bookRulePost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.booksByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken), practiceId,
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
		int i = Integer.parseInt(firstVal);
		int j = Integer.parseInt(secondVal);
		log("firstvalue value is   " + i);
		log("second value is " + j);
		log("total Month is " + totalMonth);
		log("Array List is " + arrayList);
		if (totalMonth > i && totalMonth < j) {
			String expectedValue = bookName;
			boolean sExist = arrayList.contains(expectedValue);
			assertEquals(true, sExist);
		} else {
			String expectedValue = bookName;
			boolean value = arrayList.contains(expectedValue);
			assertNotEquals(true, value);
		}

		String resetPayload = payloadAM01.resetAgeRuleAppType();
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, resetPayload);
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetSessionTimeOut() throws Exception {
		log("Verify session time out - Admin UI");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		response = postAPIRequestAM.getSessionTimeout(practiceIdAm);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "tokenExpirationTime");
		apv.responseKeyValidationJson(response, "expirationWarningTime");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleAppointmentType() throws Exception {
		log("Verify the Age Rule at Appointment Type Level when Appointment type is before Provider in Rule");
		logStep("Set up the API authentication");
		String patientDob = "01-Jan-2000";
		int totalMonth = pssPatientUtils.ageCurrentmonths(patientDob);
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceIdAm, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigSavePost(practiceIdAm, payloadAM01.turnONOFFDecisionTree(false));
		apv.responseCodeValidation(response, 200);

		String firstVal = propertyData.getProperty("agerule.firstvalue.pm01");
		String secondVal = propertyData.getProperty("agerule.secondvalue.pm01");
		int apptId = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));
		String apptName = propertyData.getProperty("appt.name.pm01");
		String apptCatId = propertyData.getProperty("appt.cat.id.pm01");

		String b = payloadAM01.ageRuleAppTypeLevel(firstVal, secondVal, apptId, apptName, apptCatId);
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, b);
		apv.responseCodeValidation(response, 200);

		String b1 = payloadPssPMNG1.bookRulePost();
		String patientId = propertyData.getProperty("patient.id.pm01");
		response = postAPIRequest.appointmentTypesByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken),
				practiceId, patientId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("exposedAppointmentType").length();
		String kk = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < len; i++) {
			kk = jo.getJSONArray("exposedAppointmentType").getJSONObject(i).getString("displayName");
			arrayList.add(kk);
		}
		int i = Integer.parseInt(firstVal);
		int j = Integer.parseInt(secondVal);
		boolean sExist = false;
		if (totalMonth > i && totalMonth < j) {
			String expectedValue = apptName;
			sExist = arrayList.contains(expectedValue);
			assertEquals(true, sExist);
		} else {
			String expectedValue = apptName;
			sExist = arrayList.contains(expectedValue);
			assertNotEquals(true, sExist);

		}
		String resetAppType = payloadAM01.resetAgeRuleAppType();
		response = postAPIRequestAM.saveAppointmenttype(practiceIdAm, resetAppType);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPCP() throws Exception {
		log("Verify the Age Rule at Appointment Type Level when Appointment type is before Provider in Rule");
		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("practice.id.pm01"), propertyData.getProperty("mf.authuserid.am.ng01"));
		Response response;
		String fctValue = propertyData.getProperty("careteam.fct.pm01");
		String pcpValue = propertyData.getProperty("careteam.pcp.pm01");
		int f = Integer.parseInt(fctValue);
		int p = Integer.parseInt(pcpValue);
		String adminPayload;
		adminPayload = payloadAM01.careTeamSettingPyaload(f, p);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		int apptId = Integer.parseInt(propertyData.getProperty("appt.id.pm01"));
		int locationId = Integer.parseInt(propertyData.getProperty("location.id.pm01"));

		String b1 = payloadPssPMNG1.lastBookRulePayload(locationId, apptId);
		String patientId = propertyData.getProperty("patient.id.careteam.pm01");
		response = postAPIRequest.booksByRule(baseUrl, b1, headerConfig.HeaderwithToken(accessToken), practiceId,
				patientId);
		apv.responseCodeValidation(response, 200);
		String expectedBookName = propertyData.getProperty("care.book.name");
		JSONObject jo = new JSONObject(response.asString());
		int len = jo.getJSONArray("books").length();
		log("Length is- " + len);
		assertEquals(len, 1);
		ArrayList<String> arrayList = new ArrayList<String>();
		String bookName = jo.getJSONArray("books").getJSONObject(0).getString("displayName");
		arrayList.add(bookName);
		log("Book Added in list l2-" + bookName);
		assertEquals(expectedBookName, bookName);

	}
}
