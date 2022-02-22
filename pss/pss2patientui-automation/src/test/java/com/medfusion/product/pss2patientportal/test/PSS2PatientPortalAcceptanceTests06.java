// Copyright 2022 NXGN Management, LLC. All Rights Reserved.

package com.medfusion.product.pss2patientportal.test;

import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.validation.ValidationAdapterModulator;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2PatientPortalAcceptanceTests06 extends BaseTestNGWebDriver {
	public static PayloadAdapterModulator payloadAM;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	HeaderConfig headerConfig;
	public static String openToken;
	public static String practiceId;
	ValidationAdapterModulator validateAdapter = new ValidationAdapterModulator();
	Timestamp timestamp = new Timestamp();
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;
	APIVerification aPIVerification = new APIVerification();

	public void setUp(String practiceId1, String userID) throws IOException {
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();
		practiceId = practiceId1;
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId, payloadAM.openTokenPayload(practiceId, userID));
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"), headerConfig.HeaderwithToken(openToken));
	}

	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testAgeRuleInApptTypeNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		String actualAppointmentType = propertyData.getProperty("appointment.type.ng04");
		propertyData.setAppointmentResponseNG(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng04"), propertyData.getProperty("mf.authuserid.am.ng04"));
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
			aPIVerification.responseCodeValidation(response, 200);
		}
		
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("BTL", "B,T,L"));
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Set up age rule in appointment type");
		response = postAPIRequestAM.saveAppointmenttype(practiceId, payloadAM.setAgeRuleOnInApptType());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Enable descision Tree for practice");
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.setDecisionTree(true));
		aPIVerification.responseCodeValidation(response, 200);
			
		logStep("Login with new patient which has correct age as applied in appointment type");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage =loginlessPatientInformation.fillNewPatientForm(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8), propertyData.getProperty("new.dob.ng04"), RandomStringUtils.randomAlphabetic(5)+"@test.com",
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		
		homepage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder=null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		
		AppointmentPage appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		log("Verfiy Appointment Page and Category");
		AppointmentDateTime appTypeName = appointment.selectAppointmentandClick(actualAppointmentType, false);
		
		appTypeName.logout();	
		logStep("Login with new patient who doesn't fit in age criteria of appointment type");
				
		loginlessPatientInformation.fillNewPatientForm(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8), testData.getDob(), RandomStringUtils.randomAlphabetic(5)+"@test.com",
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		homepage.skipInsurance(driver);
		
		startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		boolean result = appointment.verifyApptTypePresent(actualAppointmentType);
		if(result==false) {
			logStep("Test case pass");
		}
		logStep("Reset appointment type values in admin");
		response = postAPIRequestAM.saveAppointmenttype(practiceId, payloadAM.setAgeRuleOffInApptType());
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.setDecisionTree(false));
		aPIVerification.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testAgeRuleInApptTypeGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		String actualAppointmentType = propertyData.getProperty("appointment.type.gw04");
		propertyData.setAppointmentResponseGW(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
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
			aPIVerification.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("BTL", "B,T,L"));
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Set age rule in Appointment type");
		
		response = postAPIRequestAM.saveAppointmenttype(practiceId, payloadAM.setAgeRuleOnInApptTypeGW());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Enable descision Tree for practice");
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.setDecisionTree(true));
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Login with new patient which has correct age as applied in appointment type");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage =loginlessPatientInformation.fillNewPatientForm(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8), propertyData.getProperty("new.dob.ng04"), RandomStringUtils.randomAlphabetic(5)+"@test.com",
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		
		
		homepage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder=null;
		startappointmentInOrder = homepage.skipInsurance(driver);

		AppointmentPage appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		AppointmentDateTime appTypeName = appointment.selectAppointmentandClick(actualAppointmentType, false);
				
		appTypeName.logout();	
		logStep("Login with new patient who doesn't fit in age criteria of appointment type");
			
		loginlessPatientInformation.fillNewPatientForm(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8), testData.getDob(), RandomStringUtils.randomAlphabetic(5)+"@test.com",
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		homepage.skipInsurance(driver);
		

		startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		boolean result = appointment.verifyApptTypePresent(actualAppointmentType);
		if(result==false) {
			logStep("Test case pass");
		}
		logStep("Reset appointment type values in admin");
		response = postAPIRequestAM.saveAppointmenttype(practiceId, payloadAM.setAgeRuleOffInApptTypeGW());
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.setDecisionTree(false));
		aPIVerification.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testAgeRuleInApptTypeAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminAT(adminUser);
		String actualAppointmentType = propertyData.getProperty("appointment.type.at04");
		propertyData.setAppointmentResponseAT(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
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
			aPIVerification.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("BTL", "B,T,L"));
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Set age rule in Appointment type");
		
		response = postAPIRequestAM.saveAppointmenttype(practiceId, payloadAM.setAgeRuleOnInApptTypeAT());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Enable descision Tree for practice");
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.setDecisionTree(true));
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Login with new patient which has correct age as applied in appointment type");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage =loginlessPatientInformation.fillNewPatientForm(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8), propertyData.getProperty("new.dob.ng04"), RandomStringUtils.randomAlphabetic(5)+"@test.com",
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		
		StartAppointmentInOrder startappointmentInOrder=null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		

		AppointmentPage appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		AppointmentDateTime appTypeName = appointment.selectTypeOfAppointment(actualAppointmentType, false);
		log("Appointment found");
		
		appTypeName.logout();	
		logStep("Login with new patient who doesn't fit in age criteria of appointment type");
		
		loginlessPatientInformation.fillNewPatientForm(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8), testData.getDob(), RandomStringUtils.randomAlphabetic(5)+"@test.com",
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		homepage.skipInsurance(driver);
		
		startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		boolean result = appointment.verifyApptTypePresent(actualAppointmentType);
		if(result==false) {
			logStep("Test case pass");
		}
		logStep("Reset appointment type values in admin");
		logStep("Enable descision Tree for practice");
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.setDecisionTree(false));
		aPIVerification.responseCodeValidation(response, 200);
		response = postAPIRequestAM.saveAppointmenttype(practiceId, payloadAM.setAgeRuleOffInApptTypeAT());	
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testAgeRuleInApptTypeGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		String actualAppointmentType = propertyData.getProperty("appointment.type.ge04");
		propertyData.setAppointmentResponseGE(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
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
			aPIVerification.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("BTL", "B,T,L"));
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Set age rule in Appointment type");
		
		response = postAPIRequestAM.saveAppointmenttype(practiceId, payloadAM.setAgeRuleOnInApptTypeGE());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Enable descision Tree for practice");
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.setDecisionTree(true));
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Login with new patient which has correct age as applied in appointment type");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage =loginlessPatientInformation.fillNewPatientForm(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8), propertyData.getProperty("new.dob.ng04"), RandomStringUtils.randomAlphabetic(5)+"@test.com",
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		
		homepage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder=null;
		startappointmentInOrder = homepage.skipInsurance(driver);

		AppointmentPage appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		AppointmentDateTime appTypeName = appointment.selectTypeOfAppointment(actualAppointmentType, false);
		log("Appointment found");
		
		appTypeName.logout();	
		logStep("Login with new patient who doesn't fit in age criteria of appointment type");
		
		loginlessPatientInformation.fillNewPatientForm(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(8), testData.getDob(), RandomStringUtils.randomAlphabetic(5)+"@test.com",
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		homepage.skipInsurance(driver);
		
		startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		boolean result = appointment.verifyApptTypePresent(actualAppointmentType);
		if(result==false) {
			logStep("Test case pass");
		}
		logStep("Reset appointment type values in admin");
		response = postAPIRequestAM.saveAppointmenttype(practiceId, payloadAM.setAgeRuleOffInApptTypeGE());
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.setDecisionTree(false));
		aPIVerification.responseCodeValidation(response, 200);
	}
}