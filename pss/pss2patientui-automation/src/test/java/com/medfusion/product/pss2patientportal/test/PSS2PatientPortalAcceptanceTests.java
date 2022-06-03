// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.CancelRescheduleDecisionPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.PatientIdentificationPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.SelectProfilePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.ExistingPatient;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.ExistingPatientIDP;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.OnlineAppointmentScheduling;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PrivacyPolicy;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.Appointment.Speciality.Speciality;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.object.maps.pss2.page.settings.AdminAppointment;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.object.maps.pss2.page.settings.PatientFlow;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import com.medfusion.product.pss2patientapi.payload.PayloadAM02;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.validation.ValidationAdapterModulator;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSNewPatient;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;
import com.medfusion.product.pss2patientui.utils.YopMailUtility;
import com.medfusion.product.pss2patientui.utils.YopMailUtility;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2PatientPortalAcceptanceTests extends BaseTestNGWebDriver {
	
	public static PayloadAdapterModulator payloadAM;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	HeaderConfig headerConfig;
	public static String openToken;
	public static String practiceId;
	public static PayloadAM02 payloadAM02;

	ValidationAdapterModulator validateAdapter = new ValidationAdapterModulator();
	Timestamp timestamp = new Timestamp();


	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification aPIVerification = new APIVerification();
	
	public void setUp(String practiceId1,String userID) throws IOException {
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();

		practiceId = practiceId1;

		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId,
				payloadAM.openTokenPayload(practiceId,userID));

		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}
	
	

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForExistingPatientGW() throws Exception {

		log("E2E test to verify loginless appointment for a Existing patient for GW");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Set the Test Data for GW ADMIN & APPOINTMENT-------");
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGW());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		testData.setFutureApt(true);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}


	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForExistingPatientGE() throws Exception {

		log("E2E test to verify loginless appointment for a Existing patient for GE");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Set the Test Data for GE ADMIN & APPOINTMENT-------");
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGE());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		testData.setFutureApt(true);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(3000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForExistingPatientNG() throws Exception {

		log("E2E test to verify loginless appointment for a Existing patient for NG");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Set the Test Data for NG ADMIN & APPOINTMENT-------");
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		testData.setFutureApt(true);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());
		Thread.sleep(3000);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(3000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForExistingPatientAT() throws Exception {

		log("E2E test to verify loginless appointment for a Existing patient for NG");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Set the Test Data for AT ADMIN & APPOINTMENT-------");
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientMatchAt());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		testData.setFutureApt(true);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());
		Thread.sleep(3000);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(3000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGW() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		logStep("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		testData.setFutureApt(true);
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGW());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGE() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		logStep("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		testData.setFutureApt(true);
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGE());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientNG() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		logStep("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		testData.setFutureApt(true);
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientAT() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		logStep("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		testData.setFutureApt(true);
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientMatchAt());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowGW() throws Exception {
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(15000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowAllPartner(String partnerPractice) throws Exception {
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Login to PSS 2.0 Admin portal");
		adminUtils.getAdminRule(driver, adminuser);

		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(15000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);
		log("Successfully upto Home page");
		homepage.skipInsurance(driver);
		Provider provider = homepage.selectStartPoint(PSSConstants.START_PROVIDER);
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), false);
		AppointmentDateTime appointmentdatetime = location.selectDatTime(testData.getLocation());
		appointmentdatetime.selectDate(testData.getIsNextDayBooking());
		psspatientutils.bookAppointment(false, appointmentdatetime, testData, driver);
	}

	@Parameters({ "partnerType" })
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowforOnePartner(String partnerPractice) throws Exception {
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Login to PSS 2.0 Admin portal");
		adminUtils.getAdminRule(driver, adminuser);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(15000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);
		homepage.skipInsurance(driver);
		Provider provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), false);
		AppointmentDateTime appointmentdatetime = location.selectDatTime(testData.getLocation());
		appointmentdatetime.selectDate(testData.getIsNextDayBooking());
		psspatientutils.bookAppointment(false, appointmentdatetime, testData, driver);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOforAllPartnerselectFlow(String partnerPractice) throws Exception {
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Login to PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(15000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowGE() throws Exception {
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		
		testData.setInsuranceAtEnd(true);
		
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(15000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);

		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowNewPatientWithoutEMRIDGW() throws Exception {
		log("Test To Verify if a newly created Patient without EMRID unable to loginto SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setAppointmentResponseGW(testData);
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		patient.setUrl(testData.getPatientPortalURL());
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		Thread.sleep(12000);
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(),
				patient.getPassword(), testDataPFL);
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		assertEquals(homePage.appointmentNotScheduled(), "Online Scheduling Unavailable");
		homePage.closeModalPopUp();
		log("Logging out..");
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowNewPatientWithoutEMRIDGE() throws Exception {
		log("Test To Verify if a newly created Patient without EMRID unable to loginto SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setAppointmentResponseGE(testData);
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		patient.setUrl(testData.getPatientPortalURL());
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		Thread.sleep(12000);
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(),
				patient.getPassword(), testDataPFL);
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		assertEquals(homePage.appointmentNotScheduled(), "Online Scheduling Unavailable");
		homePage.closeModalPopUp();
		log("Logging out..");
		homePage.clickOnLogout();
	}

	

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionRequiredNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		adminUser.setLastQuestionMandatory(true);

		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		pssPatientUtils.TLBLastQuestion(homepage, testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionRequiredGE() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		adminUser.setLastQuestionMandatory(true);

		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		pssPatientUtils.TLBLastQuestion(homepage, testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionRequiredAT() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		adminUser.setLastQuestionMandatory(true);

		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		pssPatientUtils.TLBLastQuestion(homepage, testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastQuestionRequiredGW() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		pssPatientUtils.TLBLastQuestion(homepage, testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApptWithoutLastQuestionNG() throws Exception {

		logStep("This TC verifies the Book Appointment without last question on confirmation Screen ");
		logStep("This TC verifies the Book Appointment with insurance at End- Loginless flow");
		logStep("This TC verifies the loginless flow for existing patient");

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		
		Response response;

		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		testData.setFutureApt(false);
		testData.setInsuranceAtEnd(true);

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
		}

		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.resourceConfigRulePutPayload());
		aPIVerification.responseCodeValidation(responseRulePost, 200);

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEnd(true));
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApptWithoutLastQuestionAT() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		
		testData.setFutureApt(true);

		logStep("Make the settings in admin- Insurance at end");
		testData.setInsuranceAtEnd(true);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEndAT(false));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
		}

		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.resourceConfigRulePutPayload());
		aPIVerification.responseCodeValidation(responseRulePost, 200);

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEndAT(true));
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApptWithoutLastQuestionGE() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);		

		logStep("Make the settings in admin- Insurance at end");
		testData.setInsuranceAtEnd(true);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEndAT(false));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
		}

		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.resourceConfigRulePutPayload());
		aPIVerification.responseCodeValidation(responseRulePost, 200);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEnd(true));
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApptWithoutLastQuestionGW() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Make the settings in admin- Insurance at end");
		testData.setInsuranceAtEnd(true);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEnd(false));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
		}

		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.resourceConfigRulePutPayload());
		aPIVerification.responseCodeValidation(responseRulePost, 200);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEnd(true));
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApptWithLastQuestionNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		testData.setLastQuestionOptional(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApptWithLastQuestionAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		testData.setLastQuestionOptional(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApptWithLastQuestionGW() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		testData.setLastQuestionOptional(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApptWithLastQuestionGE() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		testData.setLastQuestionOptional(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Start booking an appointment in PSS---> PATIENT UI STEPS ");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	
	@DataProvider(name = "partnerType")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] { { "GW" }, { "GE" }, { "NG" }, { "AT" } };
		return obj;
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessForNewPatient(String partnerPractice) throws Exception {
		log("Test To Verify if New Patient is able to access PSS2.0 Patient UI via Loginless flow for "
				+ partnerPractice);
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.AT)) {
			propertyData.setAppointmentResponseAT(testData);
		}
		log("URL >>>>>> " + testData.getUrlLoginLess());
		log("Step 1: Create New patient details");
		PSSNewPatient pssnewpatient = new PSSNewPatient();
		pssnewpatient.createPatientDetails(testData);
		log("Step 2: Login to PSS Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlLoginLess());
		log("Step 3: Select New Patient");
		LoginlessPatientInformation loginlesspatientinformation = onlineappointmentschedulingPage
				.selectNewPatientLoginLess();
		log("Step 4: Fill Patient criteria Form");
		Thread.sleep(9000);
		Boolean insuranceSelected = false;
		HomePage homepage;
		if (insuranceSelected) {
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlesspatientinformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			homepage = loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		log(testData.getFirstName());
		log(testData.getLastName());
		log(testData.getEmail());
		log(testData.getPrimaryNumber());
		log(testData.getGender());
		log(testData.getDob());
		Thread.sleep(9000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		log("Step 5: Verify if PSS2.0 Patient UI HomePage is loaded");

	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessForExistingPatient(String partnerPractice) throws Exception {
		log("Test If Existing patient is able to access PSS2.0 Patient UI in Loginless flow.");
		log("Step 1: set test data for existing patient ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		log("Step 2: Enter PSS2.0 Patient UI");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlLoginLess());
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 3: Set the Patient criteria");
		Boolean isPrivacyPageEnabled = false;
		HomePage homepage = null;
		if (isPrivacyPageEnabled) {
			log("Privacy Enabled");
			PrivacyPolicy privacypolicy = existingpatient.loginPatient(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode());
			homepage = privacypolicy.submitPrivacyPage();

		} else {
			log("Privacy Disabled");
			homepage = existingpatient.login(testData.getFirstName(), testData.getLastName(), testData.getDob(),
					testData.getEmail(), testData.getGender(), testData.getZipCode());
		}
		Thread.sleep(7000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		log("Step 4: Verify if homepage is loaded");

	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateLLAccountWithExistingPatientDetails(String partnerPractice) throws Exception {
		log("Test Case to Verify apporpriate error message is displayed when Existing patient details are used while creating new patient.");
		log("Step 1: Load patient details for " + partnerPractice);
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		log("Step 2: Load PSS 2.0 patient UI");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlLoginLess());
		log("Step 3: Select New Patient");
		LoginlessPatientInformation loginlesspatientinformation = onlineappointmentschedulingPage
				.selectNewPatientLoginLess();
		log("Step 4: Fill Patient Details");
		loginlesspatientinformation.isPageLoaded();
		loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
				testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
				testData.getPrimaryNumber());
		Thread.sleep(15000);
		log("Step 5: Close the PopUp");
		loginlesspatientinformation.closePopUp();
		Thread.sleep(5000);
		log("Step 6: Verify if online appointment scheduling Page is loaded.");
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginWithWrongPatientDetails(String partnerPractice) throws Exception {
		log("Test Case to Verify apporpriate error message is displayed when Existing patient details are used while creating new patient for "
				+ partnerPractice);
		log("Step 1: Load patient details");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		log("Step 2: Move to PSS patient Portal 2.0 to book an Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlLoginLess());
		log("Step 3: Select Existing patient from the list");
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 4: Set the Patient criteria");
		existingpatient.login(PSSConstants.NON_EXISTENCE_FIRSTNAME, testData.getLastName(), testData.getDob(),
				PSSConstants.NON_EXISTENCE_PATIENT_EMAIL, testData.getGender(), testData.getZipCode());
		Thread.sleep(3500);
		existingpatient.dismissPopUp();
		log("Step 5: Verify if online appointment scheduling Page is loaded.");
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)

	public void testSchedulePatientAppointmentAndCancel(String partnerPractice) throws Exception {
		log("Test To Verify an appoint is booked and displayed in upcoming schedule list " + partnerPractice);
		log("Step 1: set test data for existing patient from external property file");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAdminGW(adminuser);
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAdminGE(adminuser);
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");
		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(true);
		log("Step 7: Select the flow as per the rule.");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		rule = "S,T,B,L";
		testData.setIsCancelApt(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPSSSlotsConfigurations(String partnerPractice) throws Exception {
		log("Test to verify different slots configuration which are set through pss admin ui are reflected in PSS2.0 patient UI.");
		log("Step 1: set test data for existing patient from external property file");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAdminGW(adminuser);
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAdminGE(adminuser);
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		if (testData.getIsAdminActive()) {
			PSSAdminUtils adminUtils = new PSSAdminUtils();
			PSS2PracticeConfiguration practiceconfiguration = adminUtils.loginToAdminPortal(driver, adminuser);
			adminUtils.navigateTo(practiceconfiguration);
		}
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		Thread.sleep(9000);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.check_Provider_Apt_Loc_List(driver, homepage, testData);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)

	public void testPSSSAgeRuleCriteriaForUnderAgePatient(String partnerPractice) throws Exception {
		log("Test PSS 2.0 Age Rule Criteria");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAdminGW(adminuser);
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAdminGE(adminuser);
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		if (testData.getIsAdminActive()) {
			PSSAdminUtils adminUtils = new PSSAdminUtils();
			PSS2PracticeConfiguration practiceconfiguration = adminUtils.loginToAdminPortal(driver, adminuser);
			adminUtils.navigateTo(practiceconfiguration);
		}
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getUnderAgePatientUserName() + " and password="
				+ testData.getUnderAgePatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getUnderAgePatientUserName(),
				testData.getUnderAgePatientPassword());
		log("Step 4: Verify if home page is loaded.");
		Thread.sleep(9000);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.ageRule(driver, homepage, testData, true, rule);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)

	public void testPSSSAgeRuleForPatientWithinCriteria(String partnerPractice) throws Exception {
		log("Test PSS 2.0 Age Rule Criteria");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAdminGW(adminuser);
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAdminGE(adminuser);
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		if (testData.getIsAdminActive()) {
			PSSAdminUtils adminUtils = new PSSAdminUtils();
			PSS2PracticeConfiguration practiceconfiguration = adminUtils.loginToAdminPortal(driver, adminuser);
			adminUtils.navigateTo(practiceconfiguration);
		}
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		Thread.sleep(9000);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.ageRule(driver, homepage, testData, false, rule);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)

	public void testPSSSLBTAssociation(String partnerPractice) throws Exception {
		log("Test to verify associations of various providers and their appoinement types and locations are displayed appropriately.");
		log("Step 1: set test data for existing patient from external property file");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAdminGW(adminuser);
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAdminGE(adminuser);
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAppointmentResponseNG(testData);
		}
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		if (testData.getIsAdminActive()) {
			PSSAdminUtils adminUtils = new PSSAdminUtils();
			PSS2PracticeConfiguration practiceconfiguration = adminUtils.loginToAdminPortal(driver, adminuser);
			adminUtils.navigateTo(practiceconfiguration);
		}
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		Thread.sleep(9000);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.verifyProviderAssociation(driver, homepage, testData, rule);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)

	public void testSchedulingForSpecialityFlow(String partnerPractice) throws Exception {
		log("Test to verify associations of various providers and their appoinement types and locations are displayed appropriately.");
		log("Step 1: set test data for existing patient from external property file");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAdminGW(adminuser);
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAdminGE(adminuser);
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAdminNG(adminuser);
			propertyData.setAppointmentResponseNG(testData);
		}
		testData.setIsAdminActive(true);
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		if (testData.getIsAdminActive()) {
			PSSAdminUtils adminUtils = new PSSAdminUtils();
			PSS2PracticeConfiguration practiceconfiguration = adminUtils.loginToAdminPortal(driver, adminuser);
			PatientFlow patientflow = practiceconfiguration.gotoPatientFlowTab();
			patientflow.removeAllRules();
			log("-----------------------------------------------------------------------------------------");
			patientflow.addNewRulesButton();
			patientflow.selectRuleName("Speciality Apt");
			patientflow.addNewRules(PSSConstants.RULE_SPECIALITY_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
			patientflow.saveRule();
			Thread.sleep(8000);
			log("--------------------------------WAIT FOR RULE TO BE ADDED--------------------------------");
			patientflow.addNewRulesButton();
			patientflow.selectRuleName("Speciality Loc");
			patientflow.addNewRules(PSSConstants.RULE_SPECIALITY_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
			patientflow.saveRule();
			Thread.sleep(8000);
			log("--------------------------------WAIT FOR RULE TO BE ADDED--------------------------------");
			patientflow.addNewRulesButton();
			patientflow.selectRuleName("Speciality Provider");
			patientflow.addNewRules(PSSConstants.RULE_SPECIALITY_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_PROVIDER_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_LOCATION_VALUE);
			patientflow.addNewRules(PSSConstants.RULE_APPOINTMENT_VALUE);
			patientflow.saveRule();
			Thread.sleep(8000);
			log("--------------------------------WAIT FOR RULE TO BE ADDED--------------------------------");
			log("Logging out of PSS 2.0 admin UI");
			patientflow.logout();
			log("Step : PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
			OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
					testData.getUrlIPD());
			log("Select Existing patient button");
			ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
			log("Step : Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
					+ testData.getPatientPassword());
			HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
					testData.getPatientPassword());
			Thread.sleep(9000);
			PSSPatientUtils psspatientutils = new PSSPatientUtils();
			psspatientutils.closeHomePagePopUp(homepage);
			log("Logout from PSS 2.0 patient UI");
			homepage.logout();
			Thread.sleep(9000);
			adminUtils.setRuleWithoutSpeciality(driver, adminuser);
		}
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancel(String partnerPractice) throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData(partnerPractice, testData, adminuser);

		pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();

		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");

		if (can1 == true & can2 == false) {

			homepage.cancelAppointment("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");
			homepage.cancelAppointmentPMReason();

		} else if (can1 == false & can2 == false) {

			String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelE2ETestingGW() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		propertyData.setAppointmentResponseGW(testData);
		propertyData.setAdminGW(adminuser);

		testData.setFutureApt(true);

		pssadminutils.getInsuranceStateandRule(driver, adminuser, testData);
		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homepage.btnStartSchedClick();
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();
		String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
		String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

		homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);

		yo.deleteEmail(driver, userName);
	}

	@Test(enabled = true, groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGW")
	public void testCancelFromEmailGW() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		propertyData.setAppointmentResponseGW(testData);
		propertyData.setAdminGW(adminuser);

		pssadminutils.getInsuranceStateandRule(driver, adminuser, testData);
		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);
		log("Step 6: Book an Appointment to test the Cancel Appointment from Email");
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);
		Thread.sleep(2000);
		String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
		String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmailSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		log("Step 9: Click on Cancel/Reschedule link from email");
		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,
				CancelReschedulelink);

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());

		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage
				.fillPatientForm(testData.getFirstName(), testData.getLastName());
		homepage = cancelRescheduleDecisionPage.clickCancel();

		homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);

		yo.deleteEmail(driver, userName);
	}

	@Test(enabled = true, groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGE")
	public void testCancelFromEmailGE() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("GE", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData,
				adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(2000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmailSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,
				CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());

		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage
				.fillPatientForm(testData.getFirstName(), testData.getLastName());
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}

		yo.deleteEmail(driver, userName);
	}

	@Test(enabled = true, groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientAT")
	public void testCancelFromEmailAT() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData,
				adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(2000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmailSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,
				CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());

		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage
				.fillPatientForm(testData.getFirstName(), testData.getLastName());
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}

		yo.deleteEmail(driver, userName);
	}

	@Test(enabled = true, groups = {"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelFromEmailNG() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData,
				adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		HomePage homepage;

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		String CancelReschedulelink =yo.getCancelRescheduleLink(driver, userName);		
		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,
				CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());

		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage
				.fillPatientForm(testData.getFirstName(), testData.getLastName());
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}

		yo.deleteEmail(driver, userName);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelE2ETestingGE() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		SoftAssert softAssertion = new SoftAssert();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("GE", testData, adminuser);

		log("Step 2: Check the cancel reschedule settings in Admin UI");
		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData,
				adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		log("Step 3: Get the rule details from admin");
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();

		log("Step 6: Verify if home page is loaded.");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		softAssertion.assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");

		if (can1 == true & can2 == false) {

			homepage.cancelAppointment("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReason(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

		yo.deleteEmail(driver, userName);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelE2ETestingNG() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		SoftAssert softAssertion = new SoftAssert();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

		log("Step 2: Check the admin settings for cancel an appointment");
		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData,
				adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		log("Step 3: Get the rule details");
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		log("Step 6: Login to Patient Portal and book an future appointment****");
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();

		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		softAssertion.assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");

		log("Step 8: Now cancel an appointent as per the settings in admin UI");
		if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReason(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

		yo.deleteEmail(driver, userName);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelE2ETestingAT() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		SoftAssert softAssertion = new SoftAssert();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		log("Step 2: Check the admin settings for cancel");
		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData,
				adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		log("Step 3: Get the rule details");
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homepage.btnStartSchedClick();
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		softAssertion.assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");

		if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReason(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg = "We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

		yo.deleteEmail(driver, userName);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppointmentGE() throws Exception {
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);

		testData.setFutureApt(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		log("Login to PSS 2.0 Admin portal");
		pssadminutils.getRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());

		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();

		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(2000);

		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);

		log("Step 4: Login to PSS Appointment");
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(1500);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(1200);

		homepage.btnStartSchedClick();

		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);
		homepage = scheduledAppointment.backtoHomePage();

		homepage.clickRescheduleLink();
		psspatientutils.rescheduleAPT(testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppointmentNG() throws Exception {
		log("Test To Verify if a Patient is reschedule an appointment via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0 NextGen");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);

		testData.setFutureApt(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		log("Login to PSS 2.0 Admin portal");
		pssadminutils.getRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());

		log("Detecting if Home Page is opened");
		homePage.clickFeaturedAppointmentsReq();

		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(2000);

		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);

		log("Step 4: Login to PSS Appointment");
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(2500);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(200);

		homepage.btnStartSchedClick();

		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);
		homepage = scheduledAppointment.backtoHomePage();

		homepage.clickRescheduleLink();
		psspatientutils.rescheduleAPT(testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppointmentGW() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		propertyData.setAppointmentResponseGW(testData);
		propertyData.setAdminGW(adminuser);

		testData.setFutureApt(true);

		pssadminutils.getInsuranceStateandRule(driver, adminuser, testData);
		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());

		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();

		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(200);

		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);

		log("Step 4: Login to PSS Appointment");
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(1500);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(500);

		homepage.btnStartSchedClick();

		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);
		homepage = scheduledAppointment.backtoHomePage();

		homepage.clickRescheduleLink();
		psspatientutils.rescheduleAPT(testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppointmentAT() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());

		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();

		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(200);

		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);

		log("Step 4: Login to PSS Appointment");
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(500);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(200);

		homepage.btnStartSchedClick();

		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow_New(driver, rule, homepage, testData);
		homepage = scheduledAppointment.backtoHomePage();

		homepage.clickRescheduleLink();
		psspatientutils.rescheduleAPT(testData, driver);

		yo.deleteEmail(driver, userName);
	}

	@Test(enabled = true, groups = {"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGE")
	public void testRescheduleviaEmailNotifiicationGE() throws Exception {

		log("Test to verify if Reschedule an Appointment via Email Notification");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("GE", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData,
				adminAppointment);

		log("adminCancelReasonList- " + adminCancelReasonList);
		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		log("Step 8: Fetch the Cancel/Reschedule link from email");		
		YopMailUtility yo = new YopMailUtility(driver);
		String CancelReschedulelink = yo.getCancelRescheduleLink(driver, testData.getGmailUserName());
		
		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,
				CancelReschedulelink);

		String userName=testData.getEmail();

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());

		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage
				.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT(testData, driver);

		yo.deleteEmail(driver, userName);

	}

	@Test(enabled = true, groups = {"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testBookApptWithLastQuestionAT")
	public void testRescheduleviaEmailNotifiicationAT() throws Exception {

		log("Test to verify if Reschedule an Appointment via Email Notification");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData,
				adminAppointment);

		log("adminCancelReasonList- " + adminCancelReasonList);
		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		
		log("Step 8: Fetch the Cancel/Reschedule link from email");		
		YopMailUtility yo = new YopMailUtility(driver);
		String userName=testData.getEmail();
		String CancelReschedulelink = yo.getCancelRescheduleLink(driver, userName);
		
		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,
				CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());

		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage
				.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT(testData, driver);

		yo.deleteEmail(driver, userName);

	}

	@Test(enabled = true, groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testBookApptWithLastQuestionGW")
	public void testRescheduleviaEmailNotifiicationGW() throws Exception {

		log("Test to verify if Reschedule an Appointment via Email Notification");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		psspatientutils.setTestData("GW", testData, adminuser);
		testData.setFutureApt(true);

		String userName=testData.getEmail();

		pssadminutils.getInsuranceStateandRule(driver, adminuser, testData);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		log("Step 8: Fetch the Cancel/Reschedule link from email");		
		YopMailUtility yo = new YopMailUtility(driver);
		String CancelReschedulelink = yo.getCancelRescheduleLink(driver, testData.getGmailUserName());
		
		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,
				CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());

		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage
				.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT(testData, driver);

		yo.deleteEmail(driver, userName);

	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBlockPatients(String partnerPractice) throws Exception {
		log("Test to verify if Block Patient are not allowed to book slots.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		adminappointment.blockPatientsAsPerMonth(PSSConstants.MAX_BLOCK_MONTHS);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getOldPatientUserName() + " and password="
				+ testData.getOldPatientPassword());
		existingpatient.patientSignIn(testData.getPatientUserName(), testData.getPatientPassword());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDisplaySlotCount(String partnerPractice) throws Exception {
		log("Test to verify if slots are displayed as per Display slot count mentioned in PSS2.0 Admin");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		adminappointment.slotcountToBeDisplayed(PSSConstants.DISPLAY_SLOTS_COUNT);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");
		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
		log("Display slots count set in Admin =" + PSSConstants.DISPLAY_SLOTS_COUNT
				+ " = display slot count in pss2.0 patient UI=" + testData.getDisplaySlotCountLength());
		assertEquals(Integer.parseInt(PSSConstants.DISPLAY_SLOTS_COUNT), testData.getDisplaySlotCountLength());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMaxAppointments(String partnerPractice) throws Exception {
		log("Test to verify if Max Appointment is available as per mentioned in PSS2.0 Admin");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		adminappointment.maxAppointments(PSSConstants.MAX_SLOTS_MONTHS);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");
		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMaxCalendarMonthsForSlots(String partnerPractice) throws Exception {
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		adminappointment.maxSlotMonthsToBeDisplayed(PSSConstants.MAX_SLOTS_MONTHS);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");
		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		testData.setIsNextDayBooking(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMajorAge(String partnerPractice) throws Exception {
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		adminappointment.majorAgeToSet(PSSConstants.MAJOR_AGE);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");
		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		testData.setIsNextDayBooking(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
		log("Are Slots Displated for Patient outside Major age rule ?" + testData.getIsCalanderDateDisplayed());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testShowProviderImages(String partnerPractice) throws Exception {
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		adminappointment.majorAgeToSet(PSSConstants.MAJOR_AGE);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = new ExistingPatientIDP(driver, testData.getUrlIPD());
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		SelectProfilePage selectProfilePage = existingpatient.patientSignIn(testData.getPatientUserName(),
				testData.getPatientPassword());
		HomePage homepage = selectProfilePage.selectProfile();
		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");
		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		testData.setIsNextDayBooking(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
		String responseCode = psspatientutils.getURLResponse(testData.getProviderImageAPI());
		assertEquals("200", responseCode);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowPCPBooking(String partnerPractice) throws Exception {
		log("Verify that PCP is followed for patient on PSS2.0 patient UI");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		adminappointment.toggleAllowPCP();
		log("Step 2A:Success Toggle .");
		Thread.sleep(1000);
		adminappointment.gotoPatientFlowTab();
		String rule = "S, T, L, B";
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = new ExistingPatientIDP(driver, testData.getUrlIPD());
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		SelectProfilePage selectProfilePage = existingpatient.patientSignIn(testData.getPatientUserName(),
				testData.getPatientPassword());
		HomePage homepage = selectProfilePage.selectProfile();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowPCPBookingGE() throws Exception {
		log(" To Verify that PCP is followed for patient on PSS2.0 patient portal UI");
		log("Step 1: Load test Data from External Property file.");
		log("Step 1.1: set test data for existing patient. ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		adminuser.setIsExisting(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);

		log("Step 3: Clicking to Appointment tab.");
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();

		log("Step 4: Checking the Enable care Team and Force Care Team is ON/OFF and set configuration accordingly.");
		adminappointment.toggleAllowPCP();
		adminappointment.pCPAvailabilityDuration(PSSConstants.PCP_AVAILABILITY_DURATION_GE);
		adminappointment.forceCareteamDuration(PSSConstants.FCT_AVAILABILITY_DURATION_GE);
		adminappointment.chooseRCPorPCP(PSSConstants.selectRCPorPCP_GE);

		log("Step 5: Fetching the rule and insurance.");
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		testData.setInsuranceVisible(patientflow.insuracetogglestatus());
		log("Insurance Status = " + patientflow.insuracetogglestatus());
		testData.setStartPointPresent(patientflow.isstartpagepresent());
		log("Startpoint  Status = " + patientflow.isstartpagepresent());
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 6: PSS patient Portal 2.0 to book an Appointment - " + testData.getPatientPortalURL());
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());

		log("Step 7: Opening the Patient Portal URL and Logging in : ");
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());

		log("Step 8: Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		Thread.sleep(1000);

		log("Step 9: Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);

		log("Switching tabs::::::::");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(1000);

		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(6000);

		log("Step 10: Verify PSS2 patient portal elements");

		log("Step 11: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");

		log("Step 12: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");

		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 13: Select the flow as per the rule.");
		testData.setIsCancelApt(false);

		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowPCPBookingGW() throws Exception {
		log(" To Verify that PCP is followed for patient on PSS2.0 patient portal UI");
		log("Step 1: Load test Data from External Property file.");
		log("Step 1.1: set test data for existing patient. ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		adminuser.setIsExisting(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);

		log("Step 3: Clicking to Appointment tab.");
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();

		log("Step 4: Checking the Enable care Team and Force Care Team is ON/OFF and set configuration accordingly.");
		adminappointment.toggleAllowPCP();
		adminappointment.pCPAvailabilityDuration(PSSConstants.PCP_AVAILABILITY_DURATION_GW);
		adminappointment.forceCareteamDuration(PSSConstants.FCT_AVAILABILITY_DURATION_GW);
		adminappointment.chooseRCPorPCP(PSSConstants.selectRCPorPCP_GW);

		log("Step 5: Fetching the rule and insurance.");
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		testData.setInsuranceVisible(patientflow.insuracetogglestatus());
		log("Insurance Status = " + patientflow.insuracetogglestatus());
		testData.setStartPointPresent(patientflow.isstartpagepresent());
		log("Startpoint  Status = " + patientflow.isstartpagepresent());
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 6: PSS patient Portal 2.0 to book an Appointment - " + testData.getPatientPortalURL());
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());

		log("Step 7: Opening the Patient Portal URL and Logging in : ");
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());

		log("Step 8: Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		Thread.sleep(1000);

		log("Step 9: Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);

		log("Switching tabs::::::::");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(1000);

		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(6000);

		log("Step 10: Verify PSS2 patient portal elements");

		log("Step 11: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");

		log("Step 12: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");

		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 13: Select the flow as per the rule.");
		testData.setIsCancelApt(false);

		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testShowSearchLocation(String partnerPractice) throws Exception {
		log("Verify that search location  zip code and area raduis selection is displayed on PSS2.0 patient UI");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		adminappointment.toggleSearchLocation();
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");
		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		testData.setIsNextDayBooking(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

		log("Verify Search Location Zip code and select Radius isEnabled.");
		assertTrue(testData.getIsSearchLocationDisplayed(), "Search Location is not displayed.");
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentWithoutSettingAnyConfiguration(String partnerPractice) throws Exception {
		log("Verify slots booking from pss2.0 patient ui when no configuration is set.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal.");
		PSS2PracticeConfiguration pss2practiceconfig = pssadminutils.loginToAdminPortal(driver, adminuser);
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();
		Thread.sleep(6000);
		adminappointment.clearAll();
		Thread.sleep(6000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		ExistingPatientIDP existingpatient = new ExistingPatientIDP(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		SelectProfilePage selectProfilePage = existingpatient.patientSignIn(testData.getPatientUserName(),
				testData.getPatientPassword());
		Thread.sleep(3000);
		HomePage homepage = selectProfilePage.selectProfile();
		log("Step 5 : rule from admin=" + rule);
		log("Step 6 : dont set Is Next Day Booking ");
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		testData.setIsNextDayBooking(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierFlow(String partnerPractice) throws Exception {
		log("Verify when Insurance carrier is set at begining of homepage.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 2: Login to Admin portal and set insurance.");
		pssadminutils.setInsuranceState(driver, adminuser);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		testData.setIsInsuranceEnabled(false);
		log("Step 3: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver,
				testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 4: Fill Existing Patient username=" + testData.getPatientUserName() + " and password="
				+ testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(),
				testData.getPatientPassword());
		log("Step 6 : dont set Is Next Day Booking ");
		testData.setIsNextDayBooking(false);
		logStep("Step 7: Select the flow as per the rule." + rule);
		testData.setIsCancelApt(false);
		testData.setIsNextDayBooking(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowNG() throws Exception {

		logStep("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		logStep("This test verfies the book appointment with Insurance at the end of workflow");
		
		logStep("Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		
		logStep("Make the settings in admin- Insurance at end");		
		testData.setInsuranceAtEnd(true);	
		
		Response response;
		
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEnd(false));
		aPIVerification.responseCodeValidation(response, 200);
		
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		logStep("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		logStep("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		logStep("Detecting if Home Page is opened");
		homePage.clickFeaturedAppointmentsReq();
		logStep("Wait for PSS 2.0 Patient UI to be loaded.");
		logStep("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		logStep("Successfully upto Home page");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.insuranceAtStartorEnd(true));
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowAT() throws Exception {
		Thread.sleep(12000);
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(15000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeGWReserveForSameDay() throws Exception {
		log("Test To Verify Lead Time Functionality For GE Partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.reserveforDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(15000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);

		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(15000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(15000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());

		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		Log4jUtil.log("date- " + date);

		log("Next date is    " + psspatientutils.numDate(testData));
		if (psspatientutils.timeDifferenceendTime(testData) < 0) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
		log("Done Confirmation");
		Thread.sleep(6000);
		ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
		confirmationpage.dateConfirm();
		log("Current Date is " + psspatientutils.currentDateandLeadDay(testData));
		log("Confirmation Date " + confirmationpage.dateConfirm());
		log("Is Confirmation time is Valid   " + psspatientutils.isValidTime(confirmationpage.timeConfirm()));
		log("Current time and lead time is  " + psspatientutils.leadTime(testData));

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMaxperDayGE() throws Exception {

		log("Test To Verify Lead Time Functionality");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.maxPerDayGE(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		int n = Integer.parseInt(testData.getMaxperDay());
		for (int i = 0; i < n; i++) {
			Log4jUtil.log("Book an appointment as per the max per day");
			startappointmentInOrder = homepage.skipInsurance(driver);
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
			Log4jUtil.log("clicked on provider ");
			Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
			AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
			Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = "
					+ testData.getAppointmenttype());
			Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
			Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
			AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
			Thread.sleep(6000);
			psspatientutils.clickOnSubmitAppt1(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Max per day appointment booked and now verifying the current date is disabled or Not");
		homepage.btnStartSchedClick();
		startappointmentInOrder = homepage.skipInsurance(driver);
		provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		Log4jUtil.log("clicked on provider ");
		Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDateforMaxPDay(testData.getIsNextDayBooking());
		Thread.sleep(6000);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMaxperDayAT() throws Exception {

		log("Test To Verify Lead Time Functionality");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.maxPerDayAT(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		Thread.sleep(15000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);

		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		int n = Integer.parseInt(testData.getMaxperDay());
		for (int i = 0; i < n; i++) {
			Log4jUtil.log("Book an appointment as per the max per day");
			startappointmentInOrder = homepage.skipInsurance(driver);
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
			Log4jUtil.log("clicked on provider ");
			Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
			AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
			Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = "
					+ testData.getAppointmenttype());
			Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
			Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
			AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
			Thread.sleep(6000);
			psspatientutils.clickOnSubmitAppt1(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Max per day appointment booked and now verifying the current date is disabled or Not");
		homepage.btnStartSchedClick();
		startappointmentInOrder = homepage.skipInsurance(driver);
		provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		Log4jUtil.log("clicked on provider ");
		Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDateforMaxPDay(testData.getIsNextDayBooking());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMaxperDayNG() throws Exception {
		log("Test To Verify Lead Time Functionality");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.maxPerDayAT(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(),
				testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);
		log("Switching tabs");
		String currentUrl = psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver, currentUrl);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}

		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		int n = Integer.parseInt(testData.getMaxperDay());
		for (int i = 0; i < n; i++) {
			Log4jUtil.log("Book an appointment as per max per day");
			startappointmentInOrder = homepage.skipInsurance(driver);
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
			Log4jUtil.log("clicked on provider ");
			Log4jUtil.log("Step 9: Verify Provider Page and provider =" + testData.getProvider());
			AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
			Log4jUtil.log("Step 10: Verify Appointment Page and appointment to be selected = "
					+ testData.getAppointmenttype());
			Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
			Log4jUtil.log("Step 11: Verify Location Page and location = " + testData.getAppointmenttype());
			AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
			Thread.sleep(6000);
			psspatientutils.clickOnSubmitAppt1(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Max per day appointment booked and now verifying the current date is disabled or Not");
		homepage.btnStartSchedClick();
		startappointmentInOrder = homepage.skipInsurance(driver);
		provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		Log4jUtil.log("clicked on provider ");
		Log4jUtil.log("Step 9: Verify Provider Page and provider =" + testData.getProvider());
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verify Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDateforMaxPDay(testData.getIsNextDayBooking());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.acceptforsameDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.acceptforsameDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.acceptforsameDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.acceptforsameDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider
				.getProviderandClick(propertyData.getProperty("provider.acceptsameday.ng"));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventSchedFutureNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.preventSchedAptSettings(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminUser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("preventsched.future.fn");
		String ln = propertyData.getProperty("preventsched.future.ln");
		String dob = propertyData.getProperty("preventsched.future.dob");
		String email = propertyData.getProperty("preventsched.future.email");
		String zip = propertyData.getProperty("preventsched.future.zip");
		String gender = propertyData.getProperty("preventsched.future.gender");
		String phone = propertyData.getProperty("preventsched.future.phone");

		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " " + email + " " + phone + " " + zip);
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zip, phone);

		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);

		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Verify Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		String expPrevSchMsg = "The practice does not allow this appointment to be scheduled within "
				+ testData.getPreSchedDays()
				+ " days from your last visit for the same appointment type. Please look for a later date or call the practice if the schedule does not allow selecting a later date.";
		String actPreventSchMsg = appointment.preventAppointmentTypeMsg(testData.getAppointmenttype());

		assertEquals(actPreventSchMsg, expPrevSchMsg,
				"Prevent Scheduling Error message is not matches with expected message");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventSchedPastNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.preventSchedAptSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		

		String fn = propertyData.getProperty("preventsched.past.fn");
		String ln = propertyData.getProperty("preventsched.past.ln");
		String dob = propertyData.getProperty("preventsched.past.dob");
		String email = propertyData.getProperty("preventsched.past.email");
		String zip = propertyData.getProperty("preventsched.past.zip");
		String gender = propertyData.getProperty("preventsched.past.gender");
		String phone = propertyData.getProperty("preventsched.past.phone");

		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " " + email + " " + phone + " " + zip);
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zip, phone);

		logStep("Fetch the Past Appointment Date from home page");
		String pastAptDate = homePage.fetchPastAptDate();

		SimpleDateFormat formatter2 = new SimpleDateFormat("MMM dd yyyy");
		Date date1 = formatter2.parse(pastAptDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);

		// manipulate date
		cal.add(Calendar.DATE, testData.getPreSchedDays());
		Date dateWithPrevSchedDaysAdded = cal.getTime();
		logStep("Slot should be visible from this date- " + dateWithPrevSchedDaysAdded);

		DateFormat dateFormat = new SimpleDateFormat("dd");
		String strDateExp = dateFormat.format(dateWithPrevSchedDaysAdded);

		logStep("Calculated Date in DD format- " + strDateExp);

		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		
		String expPrevSchMsg ="The practice does not allow this appointment to be scheduled within "
				+ testData.getPreSchedDays()
				+ " days from your last visit for the same appointment type. Please look for a later date or call the practice if the schedule does not allow selecting a later date.";
		String actPreventSchMsg = appointment.preventAppointmentTypeMsg(testData.getAppointmenttype());

		assertEquals(actPreventSchMsg, expPrevSchMsg, "Prevent Scheduling for Past Appointment Type is not working properly");
		appointment.pressOkBtn();
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncement() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.announcementSettings(driver, adminUser, testData, PSSConstants.LOGINLESS);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAptProviderOFFNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();

		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.providerOffSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Book an appointment flow started");
		homePage.btnStartSchedClick();
		pssPatientUtils.selectAFlow(driver, rule, homePage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAptProviderOFFAT() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();

		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.providerOffSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);

		logStep("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Book an appointment flow started");
		homePage.btnStartSchedClick();
		pssPatientUtils.selectAFlow(driver, rule, homePage, testData);
	}


	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenerationGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to Admin Portal and generate link for location and provider");
		adminUtils.adminSettingLinkGeneration(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		logStep("Click on Start Scheduling Button");
		homepage.btnStartSchedClick();
		homepage.getLocationText();
		homepage.getProviderText();
		logStep("Click on Start Scheduling Button");
		assertEquals(homepage.getLocationText(), testData.getLocation());
		assertEquals(homepage.getProviderText(), testData.getLinkProvider());
		Log4jUtil.log("Test Case Passed");
	}


	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenerationGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to Admin Portal and generate link for location and provider");
		adminUtils.adminSettingLinkGeneration(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		logStep("Click on Start Scheduling Button");
		homepage.btnStartSchedClick();
		homepage.getLocationText();
		homepage.getProviderText();
		logStep("Click on Start Scheduling Button");
		assertEquals(homepage.getLocationText(), testData.getLocation());
		assertEquals(homepage.getProviderText(), testData.getProvider());
		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenerationAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to Admin Portal and generate link for location and provider");
		adminUtils.adminSettingLinkGeneration(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		logStep("Click on Start Scheduling Button");
		homepage.btnStartSchedClick();
		homepage.getLocationText();
		homepage.getProviderText();
		logStep("Click on Start Scheduling Button");
		assertEquals(homepage.getLocationText(), testData.getLocation());
		assertEquals(homepage.getProviderText(), testData.getLinkProvider());
		Log4jUtil.log("Test Case Passed");
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenerationNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to Admin Portal and generate link for location and provider");
		adminUtils.adminSettingLinkGeneration(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
				testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
				testData.getPrimaryNumber());
		logStep("Click on Start Scheduling Button");
		homepage.btnStartSchedClick();
		homepage.getLocationText();
		homepage.getProviderText();
		assertEquals(homepage.getLocationText(), testData.getLinkLocation());
		assertEquals(homepage.getProviderText(), testData.getLinkProvider());
		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenandDeleteLinkGW() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingLinkGenandDeleteLink(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		log("The Messege Getting in Patient UI is  " + dismissPage.popUpMessage());
		log("expected messege is " + testData.getPopUpMessege());
		assertEquals(testData.getPopUpMessege(), dismissPage.popUpMessage());
		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenandDeleteLinkGE() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingLinkGenandDeleteLink(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		log("The Messege Getting in Patient UI is  " + dismissPage.popUpMessage());
		log("expected messege is " + testData.getPopUpMessege());
		assertEquals(testData.getPopUpMessege(), dismissPage.popUpMessage());
		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenandDeleteLinkAT() throws Exception {
		log("To Verify that when provider link is deleted then popup message is display on demographic screen for AT partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingLinkGenandDeleteLink(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		log("The Messege Getting in Patient UI is  " + dismissPage.popUpMessage());
		log("expected messege is " + testData.getPopUpMessege());
		assertEquals(testData.getPopUpMessege(), dismissPage.popUpMessage());
		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenandDeleteLinkNG() throws Exception {
		log("To Verify that when provider link is deleted then popup message is display on demographic screen for Nextgen partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingLinkGenandDeleteLink(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		log("The Messege Getting in Patient UI is  " + dismissPage.popUpMessage());
		log("expected messege is " + testData.getPopUpMessege());
		assertEquals(testData.getPopUpMessege(), dismissPage.popUpMessage());
		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleGW() throws Exception {
		log("Test To Verify Age Rule Functionality for GW partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.ageRule(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(15000);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
					Boolean.valueOf(testData.getIsAppointmentPopup()));
			Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
			AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		} else {
			Log4jUtil.log("No Provider avaliable Beacuase age Rule");
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleGE() throws Exception {
		log("Test To Verify Age Rule Functionality for GE partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.ageRule(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(15000);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
					Boolean.valueOf(testData.getIsAppointmentPopup()));
			Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
			Thread.sleep(15000);
			AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		} else {
			Log4jUtil.log("No Provider avaliable Beacuase age Rule");
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleNG() throws Exception {
		log("Test To Verify Age Rule Functionality for GE partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.ageRule(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(15000);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
					Boolean.valueOf(testData.getIsAppointmentPopup()));
			Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
			Thread.sleep(15000);
			AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		} else {
			Log4jUtil.log("No Provider avaliable Beacuase age Rule");
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleAT() throws Exception {
		log("Test To Verify Age Rule Functionality for AT partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.ageRule(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(15000);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
					Boolean.valueOf(testData.getIsAppointmentPopup()));
			Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
			Thread.sleep(15000);
			AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		} else {
			Log4jUtil.log("No Provider avaliable Beacuase age Rule");
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPCPShareONGE() throws Exception {
		log(" To Verify that PCP When Share Patient is OFF");
		log("Step 1: Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		adminuser.setIsExisting(true);

		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		pssadminutils.allowpcp(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstNameCarePatient());
		log("Last Name- " + testData.getLastNameCarePatient());
		log("Gender- " + testData.getGenderCarePatient());
		log("Email- " + testData.getEmailCarePatient());
		log("Phone Number- " + testData.getPhoneCarePatient());
		log("Date Of Birth- " + testData.getDobCarePatient());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstNameCarePatient(),
				testData.getLastNameCarePatient(), testData.getDobCarePatient(), testData.getEmailCarePatient(),
				testData.getGenderCarePatient(), testData.getZipCarePatient(), testData.getPhoneCarePatient());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(2000);
		provider.getProvider(testData.getCareProvider());

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPCPShareONGW() throws Exception {
		log(" To Verify that PCP When Share Patient is OFF");
		log("Step 1: Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		adminuser.setIsExisting(true);

		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		pssadminutils.allowpcp(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstNameCarePatient());
		log("Last Name- " + testData.getLastNameCarePatient());
		log("Gender- " + testData.getGenderCarePatient());
		log("Email- " + testData.getEmailCarePatient());
		log("Phone Number- " + testData.getPhoneCarePatient());
		log("Date Of Birth- " + testData.getDobCarePatient());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstNameCarePatient(),
				testData.getLastNameCarePatient(), testData.getDobCarePatient(), testData.getEmailCarePatient(),
				testData.getGenderCarePatient(), testData.getZipCarePatient(), testData.getPhoneCarePatient());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(2000);
		provider.getProvider(testData.getCareProvider());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPCPShareONNG() throws Exception {
		log(" To Verify that PCP When Share Patient is OFF");
		log("Step 1: Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		adminuser.setIsExisting(true);

		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		pssadminutils.allowpcp(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstNameCarePatient());
		log("Last Name- " + testData.getLastNameCarePatient());
		log("Gender- " + testData.getGenderCarePatient());
		log("Email- " + testData.getEmailCarePatient());
		log("Phone Number- " + testData.getPhoneCarePatient());
		log("Date Of Birth- " + testData.getDobCarePatient());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstNameCarePatient(),
				testData.getLastNameCarePatient(), testData.getDobCarePatient(), testData.getEmailCarePatient(),
				testData.getGenderCarePatient(), testData.getZipCarePatient(), testData.getPhoneCarePatient());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log(
				"Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(2000);
		provider.getProvider(testData.getCareProvider());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderRuleGW() throws Exception {
		log(" VeriFy Gender Rule with the Specility for GW PArtner");
		log("Step 1: Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		adminuser.setIsExisting(true);
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		pssadminutils.genderRule(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		speciality.selectSpeciality1(testData.getSpeciality());
		Log4jUtil.log("Specility Name is....." + speciality.selectSpeciality1(testData.getSpeciality()));
		assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		Log4jUtil.log("Test Case Passed.....");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderRuleGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		adminuser.setIsExisting(true);
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		pssadminutils.genderRule(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
				HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		speciality.selectSpeciality1(testData.getSpeciality());
		logStep("Specility Name is....." + speciality.selectSpeciality1(testData.getSpeciality()));
		assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
	}


	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderRuleNG() throws Exception {
		log(" VeriFy Gender Rule with the Specility for GW PArtner");
		log("Step 1: Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		adminuser.setIsExisting(true);
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		pssadminutils.genderRule(driver, adminuser, testData);
		log(testData.getUrlLoginLess());
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		speciality.selectSpeciality1(testData.getSpeciality());
		Log4jUtil.log("Specility Name is....." + speciality.selectSpeciality1(testData.getSpeciality()));
		assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		Log4jUtil.log("Test Case Passed.....");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testageRulewithSpecialityGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		logStep("Login to PSS admin portal and add the Age Rule in Speciality tab");
		pssadminutils.ageRuleWithSpeciality(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		logStep("Successfully upto Home page On The Patient Portal");
		homepage.btnStartSchedClick();
		logStep("Clicked on the start scheduling button");
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			speciality.selectSpeciality1(testData.getSpeciality());
			assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		} else {
			assertNotEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		}
	}


	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testageRulewithSpecialityAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		logStep("Login to PSS admin portal and add the Age Rule in Speciality tab");
		pssadminutils.ageRuleWithSpeciality(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		logStep("Successfully upto Home page On The Patient Portal");
		homepage.btnStartSchedClick();
		logStep("Clicked on the start scheduling button");
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			speciality.selectSpeciality1(testData.getSpeciality());
			assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		} else {
			assertNotEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		}
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testageRulewithSpecialityGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		logStep("Login to PSS admin portal and add the Age Rule in Speciality tab");
		pssadminutils.ageRuleWithSpeciality(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		logStep("Successfully upto Home page On The Patient Portal");
		homepage.btnStartSchedClick();
		logStep("Clicked on the start scheduling button");
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			speciality.selectSpeciality1(testData.getSpeciality());
			assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		} else {
			assertNotEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		}
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testageRulewithSpecialityNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		logStep("Login to PSS admin portal and add the Age Rule in Speciality tab");
		pssadminutils.ageRuleWithSpeciality(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		logStep("Successfully upto Home page On The Patient Portal");
		homepage.btnStartSchedClick();
		logStep("Clicked on the start scheduling button");
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			speciality.selectSpeciality1(testData.getSpeciality());
			assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		} else {
			assertNotEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		}
	}


	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeMarkGW() throws Exception {
		log(" VeriFy TimeMark Feature For The All Partner");
		logStep("Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		logStep("Move to PSS admin portal and Click on the timemark Functionality on Book Appointment Type");
		pssadminutils.timeMark(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		psspatientutils.timeMarkLTBRule(homePage, testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeMarkGE() throws Exception {
		log(" VeriFy TimeMark Feature For The All Partner");
		logStep("Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		logStep("Move to PSS admin portal and Click on the timemark Functionality on Book Appointment Type");
		pssadminutils.timeMark(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		psspatientutils.timeMarkLTBRule(homePage, testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeMarkNG() throws Exception {
		log(" VeriFy TimeMark Feature For The All Partner");
		logStep("Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		logStep("Move to PSS admin portal and Click on the timemark Functionality on Book Appointment Type");
		pssadminutils.timeMark(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		psspatientutils.timeMarkLTBRule(homePage, testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeMarkAT() throws Exception {
		log(" VeriFy TimeMark Feature For The All Partner");
		logStep("Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone = aPIVerification.responseKeyValidationJson(response, "practiceTimezone");
		testData.setCurrentTimeZone(timezone);

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
	
		payloadAM02= new PayloadAM02();		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM02.turnOFFShowProvider(true));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM02.turnOFFShowProvider(true));
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS admin portal and Click on the timemark Functionality on Book Appointment Type");
		pssadminutils.timeMark(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		psspatientutils.timeMarkLTBRule(homePage, testData, driver);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessWithInsuranceAT() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();

		logStep("Set the Test Data for AT ADMIN & APPOINTMENT-------");
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		testData.setFutureApt(true);
		testData.setInsuranceDetails(true);

		logStep("Test Data for book an appointment");
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());

		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsLoginless(driver, adminUser, testData, PSSConstants.LOGINLESS);
		String rule = adminUser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);

		logStep("Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Enter the Demographic Details");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Book an appointment flow started");
		homePage.btnStartSchedClick();
		pssPatientUtils.selectAFlow(driver, rule, homePage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessWithInsuranceNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();

		logStep("Set the Test Data for AT ADMIN & APPOINTMENT-------");
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		testData.setFutureApt(true);
		testData.setInsuranceDetails(true);
		testData.setInsuranceAtEnd(true);

		logStep("Test Data for book an appointment");
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());

		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsLoginless(driver, adminUser, testData, PSSConstants.LOGINLESS);
		String rule = adminUser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);

		logStep("Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Enter the Demographic Details");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Book an appointment flow started");
		homePage.btnStartSchedClick();
		pssPatientUtils.selectAFlow(driver, rule, homePage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessWithInsuranceGE() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();

		logStep("Set the Test Data for AT ADMIN & APPOINTMENT-------");
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		testData.setFutureApt(true);
		testData.setInsuranceDetails(true);

		logStep("Test Data for book an appointment");
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());

		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsLoginless(driver, adminUser, testData, PSSConstants.LOGINLESS);
		String rule = adminUser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);

		logStep("Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Enter the Demographic Details");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Book an appointment flow started");
		homePage.btnStartSchedClick();
		pssPatientUtils.selectAFlow(driver, rule, homePage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessWithInsuranceGW() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();

		logStep("Set the Test Data for AT ADMIN & APPOINTMENT-------");
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		testData.setFutureApt(true);
		testData.setInsuranceDetails(true);

		logStep("Test Data for book an appointment");
		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());

		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsLoginless(driver, adminUser, testData, PSSConstants.LOGINLESS);
		String rule = adminUser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);

		logStep("Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Enter the Demographic Details");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		logStep("Book an appointment flow started");
		homePage.btnStartSchedClick();
		pssPatientUtils.selectAFlow(driver, rule, homePage, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenWithProviGW() throws Exception {
		log("provider link is created with the newly/existing associated provider without associating any location For GW");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.linkGenerationWithProvider(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkProviderURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getLinkProviderURL());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		Boolean insuranceSelected = adminUser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homePage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newPatientInsuranceInfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homePage = newPatientInsuranceInfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		log("Verfiy Location Page and location =" + testData.getLocation());
		String popUp = startAppointmentInOrder.locationPopUp();
		log("Location popup message is  " + popUp);
		log("Expected message is    " + testData.getPopUpMessege());
		assertEquals(testData.getPopUpMessege(), popUp);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenWithProviGE() throws Exception {
		log("provider link is created with the newly/existing associated provider without associating any location For GE");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.linkGenerationWithProvider(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkProviderURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getLinkProviderURL());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		Boolean insuranceSelected = adminUser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homePage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newPatientInsuranceInfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homePage = newPatientInsuranceInfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		log("Verfiy Location Page and location =" + testData.getLocation());
		String popUp = startAppointmentInOrder.locationPopUp();
		log("Location popup message is  " + popUp);
		log("Expected message is    " + testData.getPopUpMessege());
		assertEquals(testData.getPopUpMessege(), popUp);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenWithProviNG() throws Exception {
		log("provider link is created with the newly/existing associated provider without associating any location For NG");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.linkGenerationWithProvider(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkProviderURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getLinkProviderURL());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		Boolean insuranceSelected = adminUser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homePage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newPatientInsuranceInfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homePage = newPatientInsuranceInfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		log("Verfiy Location Page and location =" + testData.getLinkLocation());
		String popUp = startAppointmentInOrder.locationPopUp();
		log("Location popup message is  " + popUp);
		log("Expected message is    " + testData.getPopUpMessege());
		assertEquals(testData.getPopUpMessege(), popUp);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenWithProviAT() throws Exception {
		log("provider link is created with the newly/existing associated provider without associating any location For AT");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.linkGenerationWithProvider(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkProviderURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getLinkProviderURL());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(3000);
		Boolean insuranceSelected = adminUser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homePage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newPatientInsuranceInfo = loginlessPatientInformation.fillPatientForm(
					testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homePage = newPatientInsuranceInfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER,
					PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(),
					testData.getPrimaryNumber());
		}
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		log("Verfiy Location Page and location =" + testData.getLocation());
		String popUp = startAppointmentInOrder.locationPopUp();
		log("Location popup message is  " + popUp);
		log("Expected message is    " + testData.getPopUpMessege());
		assertEquals(testData.getPopUpMessege(), popUp);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailableDisplayedWithLocationNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.linkGenerationWithLocation(driver, adminUser, testData, PSSConstants.LOGINLESS);
		log("Location link is " + testData.getLinkLocationURL());
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkLocationURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getLinkLocationURL());
		Thread.sleep(1000);
		logStep("Open the location link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");
		AppointmentPage appointment;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		logStep("Verfiy Appointment Page and appointment =" + propertyData.getProperty("link.appointment.ng"));
		Provider provider = appointment.selectTypeOfProvider(propertyData.getProperty("link.appointment.ng"),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getLinkProvider());
		log("Next availiable text is  " + provider.getNextavaliableText());
		assertEquals(testData.getNextAvailiableText(), provider.getNextavaliableText());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailableProviderOffNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.providerOffSettings(driver, adminUser, testData, PSSConstants.LOGINLESS);
		log("Location link is " + testData.getLinkLocationURL());
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkLocationURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Open the location link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");
		AppointmentPage appointment;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		logStep("Verfiy Appointment Page and appointment =" + propertyData.getProperty("link.appointment.ng"));
		Location location = appointment.selectTypeOfLocation(propertyData.getProperty("link.appointment.ng"),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(),testData.getNextAvailiableText());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailableDisplayedWithLocationAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.linkGenerationWithLocation(driver, adminUser, testData, PSSConstants.LOGINLESS);
		log("Location link is " + testData.getLinkLocationURL());
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkLocationURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getLinkLocationURL());
		Thread.sleep(1000);
		logStep("Open the location link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");
		AppointmentPage appointment;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		logStep("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		log("Next availiable text is  " + provider.getNextavaliableText());
		assertEquals(testData.getNextAvailiableText(), provider.getNextavaliableText());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailableDisplayedWithLocationGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.linkGenerationWithLocation(driver, adminUser, testData, PSSConstants.LOGINLESS);
		log("Location link is " + testData.getLinkLocationURL());
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkLocationURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getLinkLocationURL());
		Thread.sleep(1000);
		logStep("Open the location link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");
		AppointmentPage appointment;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		logStep("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		provider.selectLocation(testData.getProvider());
		log("Next availiable text is  " + provider.getNextavaliableText());
		assertEquals(testData.getNextAvailiableText(), provider.getNextavaliableText());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailableDisplayedWithLocationGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.linkGenerationWithLocation(driver, adminUser, testData, PSSConstants.LOGINLESS);
		log("Location link is " + testData.getLinkLocationURL());
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		log("link is   " + testData.getLinkLocationURL());
		DismissPage dismissPage = new DismissPage(driver, testData.getLinkLocationURL());
		Thread.sleep(1000);
		logStep("Open the location link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");
		AppointmentPage appointment;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		logStep("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		provider.selectLocation(testData.getProvider());
		log("Next availiable text is  " + provider.getNextavaliableText());
		assertEquals(testData.getNextAvailiableText(), provider.getNextavaliableText());

	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExcludeSlotsNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS Admin portal ");
		adminUtils.exclueSlots(driver, adminuser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		String time = aptDateTime.getfirsttime().replace(":", "");
		String time1 = time.substring(0, 4);
		log("First time is   " + time1);
		assertEquals(time1, testData.getExcludeSlotSecondValue());

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExcludeSlotsAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS Admin portal ");
		adminUtils.exclueSlots(driver, adminuser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		String time = aptDateTime.getfirsttime().replace(":", "");
		String time1 = time.substring(0, 4);
		log("First time is   " + time1);
		assertEquals(time1, testData.getExcludeSlotSecondValue());

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExcludeSlotsGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS Admin portal ");
		adminUtils.exclueSlots(driver, adminuser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		String time = aptDateTime.getfirsttime().replace(":", "");
		String time1 = time.substring(0, 4);
		log("First time is   " + time1);
		assertEquals(time1, testData.getExcludeSlotSecondValue());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testExcludeSlotsGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS Admin portal ");
		adminUtils.exclueSlots(driver, adminuser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		String time = aptDateTime.getfirsttime().replace(":", "");
		String time1 = time.substring(0, 4);
		log("First time is   " + time1);
		assertEquals(time1, testData.getExcludeSlotSecondValue());

	}

	@Test(enabled = true, groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientNG")
	public void testRescheduleviaEmailNotifiicationNG() throws Exception {

		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		PSSAdminUtils pssAdminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		logStep("Load all the test data related to admin UI and patient UI");
		pssPatientUtils.setTestData("NG", testData, adminUser);

		testData.setFutureApt(true);

		logStep("Check the Admin Settings for Reschedule Appointment");
		ArrayList<String> adminCancelReasonList = pssAdminutils.getCancelRescheduleSettings(driver, adminUser, testData,
				adminAppointment);

		log("adminCancelReasonList- " + adminCancelReasonList);
		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		logStep("Fetch the Cancel/Reschedule link from email");

		String subject = testData.getEmailSubject();
		String messageLink = "Reschedule or cancel";
		String userName = testData.getGmailUserName();

		log("Subject- " + subject);
		log("messageLink- " + messageLink);
		log("userName- " + userName);

		YopMailUtility yo = new YopMailUtility(driver);

		String CancelReschedulelink = yo.getCancelRescheduleLink(driver, userName);

		logStep("Hit the Cancel/Reschedule link which we get from email in browser");
		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,
				CancelReschedulelink);

		logStep("Wait for the page to load completely and check for the Greetings pop up");
		Thread.sleep(3000);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		logStep("Fill Patient details on Patient Identification Page");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage
				.fillPatientForm(testData.getFirstName(), testData.getLastName());

		logStep("Verify the appointment details and click on Cancel button");
		cancelRescheduleDecisionPage.clickReschedule();
		pssPatientUtils.rescheduleAPT(testData, driver);

		yo.deleteEmail(driver, userName);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EMergeSlotGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS Admin portal and do setting related to merge Slot");
		adminUtils.mergeSlot(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		testData.setFirstHour(aptDateTime.getFirstTimeWithHour());
		testData.setFirstMinute(aptDateTime.getFirstTimeWithMinute());
		String time = aptDateTime.getfirsttime();
		log("First Time is  " + time);
		String nexttime = aptDateTime.getNextTimeWithMinute();
		log("Next Time is " + nexttime);
		log("Time Add After  " + psspatientutils.addTimeinFixedTime(testData));
		assertEquals(aptDateTime.getNextTimeWithMinute(), psspatientutils.addTimeinFixedTime(testData));
	}


	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EMergeSlotGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS Admin portal and do setting related to merge Slot");
		adminUtils.mergeSlot(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		testData.setFirstHour(aptDateTime.getFirstTimeWithHour());
		testData.setFirstMinute(aptDateTime.getFirstTimeWithMinute());
		String time = aptDateTime.getfirsttime();
		log("First Time is  " + time);
		String nexttime = aptDateTime.getNextTimeWithMinute();
		log("Next Time is " + nexttime);
		log("Time Add After  " + psspatientutils.addTimeinFixedTime(testData));
		assertEquals(aptDateTime.getNextTimeWithMinute(), psspatientutils.addTimeinFixedTime(testData));

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EMergeSlotNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		pssNewPatient.createPatientDetails(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS Admin portal and do setting related to merge Slot");
		adminUtils.mergeSlot(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		testData.setFirstHour(aptDateTime.getFirstTimeWithHour());
		testData.setFirstMinute(aptDateTime.getFirstTimeWithMinute());
		String time = aptDateTime.getfirsttime();
		log("First Time is  " + time);
		String nexttime = aptDateTime.getNextTimeWithMinute();
		log("Next Time is " + nexttime);
		log("Time Add After  " + psspatientutils.addTimeinFixedTime(testData));
		assertEquals(aptDateTime.getNextTimeWithMinute(), psspatientutils.addTimeinFixedTime(testData));

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeMarkWithShowProOffAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		adminuser.setIsExisting(true);
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		
		
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone = aPIVerification.responseKeyValidationJson(response, "practiceTimezone");
		testData.setCurrentTimeZone(timezone);

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
		
		payloadAM02= new PayloadAM02();
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL", "T,L"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LT", "L,T"));
		aPIVerification.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM02.turnOFFShowProvider(false));
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS admin portal and Click on the timemark Functionality on Book Appointment Type");
		pssadminutils.timeMarkWithShowOff(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);

		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);

		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		log("Appointment minute " + aptDateTime.getFirstTimeWithMinute());
		log("Value  is " + testData.getTimeMarkValue());
		if (testData.getTimeMarkValue().equalsIgnoreCase("0")) {
			log("Default Slot is Selected ");
		} else if (testData.getTimeMarkValue().equalsIgnoreCase("60")) {
			String timeForHour = testData.getTimeMarkValue().replace('6', '0');
			assertEquals(aptDateTime.getFirstTimeWithMinute(), timeForHour);
		} else {
			assertEquals(aptDateTime.getFirstTimeWithMinute(), testData.getTimeMarkValue());
		}
		
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeMarkWithShowProOffNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		adminuser.setIsExisting(true);
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		logStep("Move to PSS admin portal and Click on the timemark Functionality on Book Appointment Type");
		pssadminutils.timeMarkWithShowOff(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);

		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);

		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		log("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		log("Appointment minute " + aptDateTime.getFirstTimeWithMinute());
		log("Value  is " + testData.getTimeMarkValue());
		if (testData.getTimeMarkValue().equalsIgnoreCase("0")) {
			log("Default Slot is Selected ");
		} else if (testData.getTimeMarkValue().equalsIgnoreCase("60")) {
			String timeForHour = testData.getTimeMarkValue().replace('6', '0');
			assertEquals(aptDateTime.getFirstTimeWithMinute(), timeForHour);
		} else {
			assertEquals(aptDateTime.getFirstTimeWithMinute(), testData.getTimeMarkValue());
		}
		
	}
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayWithShowProviderOffAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();	
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
        response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone=aPIVerification.responseKeyValidationJson(response, "practiceTimezone");	
		testData.setCurrentTimeZone(timezone);	
		
		log("Current Time Zone Is"+testData.getCurrentTimeZone());
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        payloadAM.resourceConfigRulePutPayloadLT());
        aPIVerification.responseCodeValidation(responseRulePost, 200);
        
        Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
                payloadAM.resourceConfigRulePostPayloadTL());
                aPIVerification.responseCodeValidation(responseRulePostTL, 200);


		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.acceptForSameDayWithShowProviderOFF(driver, adminuser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.nextDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayWithShowProviderOffNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();	
        setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
        Response response;
        response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone=aPIVerification.responseKeyValidationJson(response, "practiceTimezone");	
		testData.setCurrentTimeZone(timezone);	
		
		log("Current Time Zone Is"+testData.getCurrentTimeZone());
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        payloadAM.resourceConfigRulePutPayloadLT());
        aPIVerification.responseCodeValidation(responseRulePost, 200);
        
        Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
                payloadAM.resourceConfigRulePostPayloadTL());
                aPIVerification.responseCodeValidation(responseRulePostTL, 200);		
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.acceptForSameDayWithShowProviderOFF(driver, adminuser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.nextDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayWithReserveShowProviderOffNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();	
        setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
        Response response;
        response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone=aPIVerification.responseKeyValidationJson(response, "practiceTimezone");	
		testData.setCurrentTimeZone(timezone);	
		
		log("Current Time Zone Is"+testData.getCurrentTimeZone());
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        payloadAM.resourceConfigRulePutPayloadLT());
        aPIVerification.responseCodeValidation(responseRulePost, 200);
        
        Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
                payloadAM.resourceConfigRulePostPayloadTL());
                aPIVerification.responseCodeValidation(responseRulePostTL, 200);

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.acceptForSameDayWithReserveShowProviderOFF(driver, adminuser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		int size=aptDateTime.getAppointmentDateList();
		logStep("Date selected is for App" + date);
		String dateSize=String.valueOf(size);  
		assertEquals(dateSize, "1");
		log("current date is"+psspatientutils.currentESTDate(testData));
		assertEquals(date, psspatientutils.currentESTDate(testData));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayWithReserveShowProviderOffAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();	

		
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
        response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone=aPIVerification.responseKeyValidationJson(response, "practiceTimezone");	
		testData.setCurrentTimeZone(timezone);	
		
		log("Current Time Zone Is"+testData.getCurrentTimeZone());
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        payloadAM.resourceConfigRulePutPayloadLT());
        aPIVerification.responseCodeValidation(responseRulePost, 200);
        
        Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
                payloadAM.resourceConfigRulePostPayloadTL());
                aPIVerification.responseCodeValidation(responseRulePostTL, 200);
		
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.acceptForSameDayWithReserveShowProviderOFF(driver, adminuser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		int size=aptDateTime.getAppointmentDateList();
		logStep("Date selected is for App" + date);
		String dateSize=String.valueOf(size);  
		assertEquals(dateSize, "1");
		log("current date is"+psspatientutils.currentESTDate(testData));
		assertEquals(date, psspatientutils.currentESTDate(testData));
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDisableLoginless() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);

		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessDisable());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.loginlessGet(practiceId, "/loginless");

		JsonPath js = new JsonPath(response.asString());
		String loginlessLink = js.getString("link");
		DismissPage dismissPage = new DismissPage(driver, loginlessLink);
		Thread.sleep(1000);
		String errorMessage = dismissPage.verifyErrorPage();
		assertEquals(errorMessage, "Link is currently unavailable for the practice.", "Error message in wrong");

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessEnable());

	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOffNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();	

		
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
        response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone=aPIVerification.responseKeyValidationJson(response, "practiceTimezone");	
		testData.setCurrentTimeZone(timezone);	
		
		log("Current Time Zone Is"+testData.getCurrentTimeZone());
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        payloadAM.resourceConfigRulePutPayloadLT());
        aPIVerification.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.resourceConfigRulePostPayloadTL());
		aPIVerification.responseCodeValidation(responseRulePostTL, 200);

		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.providerOff());
		aPIVerification.responseCodeValidation(responseShowOff, 200);

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimeWithReserveShowProviderOFF(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
			assertEquals(date, psspatientutils.currentDateandLeadDay(testData));
	}
	
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOffAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();	

        Response response;
        setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));

        response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone=aPIVerification.responseKeyValidationJson(response, "practiceTimezone");	
		testData.setCurrentTimeZone(timezone);	
		
		log("Current Time Zone Is"+testData.getCurrentTimeZone());
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
        JSONArray arr = new JSONArray(response.body().asString());
        int l=arr.length();
        log("Length is- "+l);
        for (int i=0; i<l; i++) {
        int ruleId=arr.getJSONObject(i).getInt("id");
        log("Object No."+i+"- "+ruleId);
        Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
        aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
        }
        Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
        payloadAM.resourceConfigRulePutPayloadLT());
        aPIVerification.responseCodeValidation(responseRulePost, 200);
        
        Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
                payloadAM.resourceConfigRulePostPayloadTL());
                aPIVerification.responseCodeValidation(responseRulePostTL, 200);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimeWithReserveShowProviderOFF(driver, adminuser, testData,propertyData.getProperty("leadtime.at"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
    	StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
			assertEquals(date, psspatientutils.currentDateandLeadDay(testData));
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDisableAnonymousNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);

		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.anonymousConfg(false));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.anonymousGet(practiceId, "/anonymous");

		JsonPath js = new JsonPath(response.asString());
		String loginlessLink = js.getString("link");
		DismissPage dismissPage = new DismissPage(driver, loginlessLink);
		Thread.sleep(1000);
		String errorMessage = dismissPage.verifyErrorPage();
		assertEquals(errorMessage, "Link is currently unavailable for the practice.", "Error message in wrong");

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.anonymousConfg(true));
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDisableLoginlessNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);

		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessDisable());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.loginlessGet(practiceId, "/loginless");

		JsonPath js = new JsonPath(response.asString());
		String loginlessLink = js.getString("link");
		DismissPage dismissPage = new DismissPage(driver, loginlessLink);
		Thread.sleep(1000);
		String errorMessage = dismissPage.verifyErrorPage();
		assertEquals(errorMessage, "Link is currently unavailable for the practice.", "Error message in wrong");

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessEnable());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDisableAnonymousAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.anonymousConfg(false));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.anonymousGet(practiceId, "/anonymous");

		JsonPath js = new JsonPath(response.asString());
		String loginlessLink = js.getString("link");
		DismissPage dismissPage = new DismissPage(driver, loginlessLink);
		Thread.sleep(1000);
		String errorMessage = dismissPage.verifyErrorPage();
		assertEquals(errorMessage, "Link is currently unavailable for the practice.", "Error message in wrong");

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.anonymousConfg(true));
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDisableLoginlessAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessDisable());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.loginlessGet(practiceId, "/loginless");

		JsonPath js = new JsonPath(response.asString());
		String loginlessLink = js.getString("link");
		DismissPage dismissPage = new DismissPage(driver, loginlessLink);
		Thread.sleep(1000);
		String errorMessage = dismissPage.verifyErrorPage();
		assertEquals(errorMessage, "Link is currently unavailable for the practice.", "Error message in wrong");

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessEnable());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDisableLoginlessGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessDisable());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.loginlessGet(practiceId, "/loginless");

		JsonPath js = new JsonPath(response.asString());
		String loginlessLink = js.getString("link");
		DismissPage dismissPage = new DismissPage(driver, loginlessLink);
		Thread.sleep(1000);
		String errorMessage = dismissPage.verifyErrorPage();
		assertEquals(errorMessage, "Link is currently unavailable for the practice.", "Error message in wrong");

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessEnable());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDisableLoginlessGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessDisable());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.loginlessGet(practiceId, "/loginless");

		JsonPath js = new JsonPath(response.asString());
		String loginlessLink = js.getString("link");
		DismissPage dismissPage = new DismissPage(driver, loginlessLink);
		Thread.sleep(1000);
		String errorMessage = dismissPage.verifyErrorPage();
		assertEquals(errorMessage, "Link is currently unavailable for the practice.", "Error message in wrong");

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.loginlessEnable());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingPastApptDisableNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		adminUser.setLastQuestionMandatory(true);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.upcimingPastAptOnOff(false));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		boolean bool = homePage.isUpcomingAptPresent();

		assertEquals(bool, false, "Upcoming and past appointment list is present on the screen");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingPastApptDisableGE() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.upcimingPastAptOnOff(false));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		boolean bool = homePage.isUpcomingAptPresent();

		assertEquals(bool, false, "Upcoming and past appointment list is present on the screen");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingPastApptDisableGW() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.upcimingPastAptOnOffGW(false));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		boolean bool = homePage.isUpcomingAptPresent();

		assertEquals(bool, false, "Upcoming and past appointment list is present on the screen");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingPastApptDisableAT() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		Response response;

		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.upcimingPastAptOnOff(false));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		logStep("Patient details are as mentioned below-");
		log("Demographic Details- " + testData.getFirstName() + " " + testData.getLastName() + " " + testData.getDob()
				+ " " + testData.getGender() + " " + testData.getEmail() + " " + testData.getPrimaryNumber() + " "
				+ testData.getZipCode());

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		boolean bool = homePage.isUpcomingAptPresent();

		assertEquals(bool, false, "Upcoming and past appointment list is present on the screen");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_LBT_NG() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRulePutPayload());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRuleLBTPayload());
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);

		log("Verfiy location Page and location =" + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("Verfiy Provider Page and provider to be selected = " + testData.getProvider());
		AppointmentPage appointment = provider.selectAppointment(testData.getProvider());

		String apptLabel = appointment.getNextAvailableOffText();
		log("Next availiable text is  " + apptLabel);
		assertFalse(apptLabel.contains("Next Available"));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_LBT_GE() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRulePutPayload());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRuleLBTPayload());
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);

		log("Verfiy location Page and location =" + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("Verfiy Provider Page and provider to be selected = " + testData.getProvider());
		AppointmentPage appointment = provider.selectAppointment(testData.getProvider());

		String apptLabel = appointment.getNextAvailableOffText();
		log("Next availiable text is  " + apptLabel);
		assertFalse(apptLabel.contains("Next Available"));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_LBT_AT() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRulePutPayload());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRuleLBTPayload());
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);

		log("Verfiy location Page and location =" + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("Verfiy Provider Page and provider to be selected = " + testData.getProvider());
		AppointmentPage appointment = provider.selectAppointment(testData.getProvider());

		String apptLabel = appointment.getNextAvailableOffText();
		log("Next availiable text is  " + apptLabel);
		assertFalse(apptLabel.contains("Next Available"));
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_TBL_NG() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());

		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(), testData.getNextAvailiableText());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_STBL_NG() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("STBL", "S,T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("SBTL", "S,B,T,L"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		logStep("Clicked on the Skip Insurance Button ");

		Speciality speciality = homePage.skipInsuranceForSpeciality(driver);
		startAppointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());

		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());

		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(), testData.getNextAvailiableText());
	}
	

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testWelcomeMessageNG() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		String welcomeMsg=propertyData.getProperty("welcome.message");

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.getAnnouncementByCode(practiceId, "AG");
		aPIVerification.responseCodeValidation(response, 200);
		
		JsonPath js= new JsonPath(response.asString());
		int id=js.getInt("id");
		
		response=postAPIRequestAM.deleteAnnouncement(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);
		
		response=postAPIRequestAM.saveAnnouncement(practiceId, payloadAM.annSavePayload("Greetings", "AG", welcomeMsg));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		String actualGreetingText=dismissPage.getDismissText();
		log("Text present- "+actualGreetingText);
		assertEquals(actualGreetingText, welcomeMsg, "Greeting message in not matching with admin UI");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testWelcomeMessageGW() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		String welcomeMsg=propertyData.getProperty("welcome.message");

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.getAnnouncementByCode(practiceId, "AG");
		aPIVerification.responseCodeValidation(response, 200);
		
		JsonPath js= new JsonPath(response.asString());
		int id=js.getInt("id");
		
		response=postAPIRequestAM.deleteAnnouncement(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);	
		
		response=postAPIRequestAM.saveAnnouncement(practiceId, payloadAM.annSavePayload("Greetings", "AG",welcomeMsg));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		String actualGreetingText=dismissPage.getDismissText();
		log("Text present- "+actualGreetingText);
		assertEquals(actualGreetingText, welcomeMsg, "Greeting message in not matching with admin UI");

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testWelcomeMessageAT() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		String welcomeMsg=propertyData.getProperty("welcome.message");

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.getAnnouncementByCode(practiceId, "AG");
		aPIVerification.responseCodeValidation(response, 200);
		
		JsonPath js= new JsonPath(response.asString());
		int id=js.getInt("id");
		
		response=postAPIRequestAM.deleteAnnouncement(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);	
		
		response=postAPIRequestAM.saveAnnouncement(practiceId, payloadAM.annSavePayload("Greetings", "AG", welcomeMsg));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		String actualGreetingText=dismissPage.getDismissText();
		log("Text present- "+actualGreetingText);
		assertEquals(actualGreetingText, welcomeMsg, "Greeting message in not matching with admin UI");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testWelcomeMessageGE() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		String welcomeMsg=propertyData.getProperty("welcome.message");

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.getAnnouncementByCode(practiceId, "AG");
		aPIVerification.responseCodeValidation(response, 200);
		
		JsonPath js= new JsonPath(response.asString());
		int id=js.getInt("id");
		
		response=postAPIRequestAM.deleteAnnouncement(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);
		
		response=postAPIRequestAM.saveAnnouncement(practiceId, payloadAM.annSavePayload("Greetings", "AG", welcomeMsg));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		String actualGreetingText=dismissPage.getDismissText();
		log("Text present- "+actualGreetingText);
		assertEquals(actualGreetingText, welcomeMsg, "Greeting message in not matching with admin UI");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_LT_NG() throws Exception {

		logStep("To verify that Next available is displayed on location (TL) when show provider is off");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		PSSNewPatient pssNewPatient= new PSSNewPatient();	

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
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
		
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.providerOff());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL","T,L"));
		aPIVerification.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LT","L,T"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		pssNewPatient.createPatientDetails(testData);

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);

		log("Verfiy Location Page and location =" + testData.getLocation());
		location.selectAppointment(testData.getLocation());
		log("Verfiy Appointment Page and appointment to be selected = " + propertyData.getProperty("appointmenttype.ng"));
		
		String apptLabel = location.getNextavaliableText();
		log("Next availiable text is  " + apptLabel);
		assertTrue(apptLabel.contains("Next Available"));
	}

	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_LT_Athena() throws Exception {

		logStep("To verify that Next available is displayed on location (TL) when show provider is off");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		PSSNewPatient pssNewPatient= new PSSNewPatient();	

		propertyData.setAdminAT(adminUser);
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
		
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.providerOff());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL","T,L"));
		aPIVerification.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LT","L,T"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		pssNewPatient.createPatientDetails(testData);

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);

		log("Verfiy Location Page and location =" + testData.getLocation());
		location.selectAppointment(testData.getLocation());
		log("Verfiy Appointment Page and appointment to be selected = " + propertyData.getProperty("appointmenttype.ng"));
		
		String apptLabel = location.getNextavaliableText();
		log("Next availiable text is  " + apptLabel);
		assertTrue(apptLabel.contains("Next Available"));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_TL_NG() throws Exception {

		logStep("To verify that Next available is displayed on location (TL) when show provider is off");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		PSSNewPatient pssNewPatient= new PSSNewPatient();	

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
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
		
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.providerOff());
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TL","T,L"));
		aPIVerification.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LT","L,T"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		pssNewPatient.createPatientDetails(testData);

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + propertyData.getProperty("appointmenttype.ng"));
		Location location = appointment.selectTypeOfLocation(propertyData.getProperty("appointmenttype.ng"),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		
		String apptLabel = location.getNextavaliableText();
		log("Next availiable text is  " + apptLabel);
		assertTrue(apptLabel.contains("Next Available"));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_STLB_GW() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("STBL", "S,T,L,B"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("SBTL", "S,B,T,L"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		logStep("Clicked on the Skip Insurance Button ");

		Speciality speciality = homePage.skipInsuranceForSpeciality(driver);
		startAppointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());

		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		
		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("address = " + location.getAddressValue());
		log("Verfiy Provider Page and Provider = " + testData.getProvider());

		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(), testData.getNextAvailiableText());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_TBL_GE() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());

		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(), testData.getNextAvailiableText());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_TBL_AT() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());

		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(), testData.getNextAvailiableText());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_TLB_AT() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());

		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(), testData.getNextAvailiableText());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_TLB_NG() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());

		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(), testData.getNextAvailiableText());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailable_TLB_GE() throws Exception {

		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Login To admin portal and Generate link for Provider");
		adminUtils.upcomingPastApptSetting(driver, adminUser, testData, PSSConstants.LOGINLESS);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());

		log("Next availiable text is  " + location.getNextavaliableText());
		assertEquals(location.getNextavaliableText(), testData.getNextAvailiableText());
	}


	

	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleAppointmentTypeNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		adminUtils.ageRuleAppointmentType(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = null;
		appointment = location.selectAppointment(testData.getLocation());
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			String name = appointment.selectTypeOfApp(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			assertEquals(testData.getAppointmenttype(), name);
		} else {
			String name = appointment.selectTypeOfApp(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			assertNotEquals(testData.getAppointmenttype(), name);
		}
		adminUtils.resetAgeRuleAppointmentType(driver, adminUser, testData, PSSConstants.LOGINLESS);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleAppointmentTypeAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		adminUtils.ageRuleAppointmentType(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep(" Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = null;
		appointment = location.selectAppointment(testData.getLocation());
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			String name = appointment.selectTypeOfApp(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			assertEquals(testData.getAppointmenttype(), name);
		} else {
			String name = appointment.selectTypeOfApp(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			assertNotEquals(testData.getAppointmenttype(), name);
		}
		adminUtils.resetAgeRuleAppointmentType(driver, adminUser, testData, PSSConstants.LOGINLESS);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleAppointmentTypeGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		adminUtils.ageRuleAppointmentType(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = null;
		appointment = location.selectAppointment(testData.getLocation());
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			String name = appointment.selectTypeOfApp(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			assertEquals(testData.getAppointmenttype(), name);
		} else {
			String name = appointment.selectTypeOfApp(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			assertNotEquals(testData.getAppointmenttype(), name);
		}
		adminUtils.resetAgeRuleAppointmentType(driver, adminUser, testData, PSSConstants.LOGINLESS);
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAgeRuleAppointmentTypeGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		adminUtils.ageRuleAppointmentType(driver, adminUser, testData, PSSConstants.LOGINLESS);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = null;
		appointment = location.selectAppointment(testData.getLocation());
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			String name = appointment.selectTypeOfApp(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			assertEquals(testData.getAppointmenttype(), name);
		} else {
			String name = appointment.selectTypeOfApp(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			assertNotEquals(testData.getAppointmenttype(), name);
		}
		adminUtils.resetAgeRuleAppointmentType(driver, adminUser, testData, PSSConstants.LOGINLESS);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMaxPerDayShowProviderOFFNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRulePutPayloadLT());
		aPIVerification.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRulePostPayloadTL());
		aPIVerification.responseCodeValidation(responseRulePostTL, 200);

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.maxPerDayWithShowProviderOFF(driver, adminuser, testData);
		Assert.assertFalse(testData.isMaxPerDayStatus());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMaxPerDayShowProviderOFFAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRulePutPayloadLT());
		aPIVerification.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.resourceConfigRulePostPayloadTL());
		aPIVerification.responseCodeValidation(responseRulePostTL, 200);

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.maxPerDayWithShowProviderOFF(driver, adminuser, testData);
		Assert.assertFalse(testData.isMaxPerDayStatus());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testReserveForSameDayGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone = aPIVerification.responseKeyValidationJson(response, "practiceTimezone");
		testData.setCurrentTimeZone(timezone);

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		adminUtils.reserveForSameDay(driver, adminUser, testData, PSSConstants.LOGINLESS);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		int size = aptDateTime.getAppointmentDateList();
		logStep("Date selected is for App" + date);
		String dateSize = String.valueOf(size);
		assertEquals(dateSize, "1");
		log("current date is" + psspatientutils.currentESTDate(testData));
		assertEquals(date, psspatientutils.currentESTDate(testData));
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testReserveForSameDayGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone = aPIVerification.responseKeyValidationJson(response, "practiceTimezone");
		testData.setCurrentTimeZone(timezone);

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		adminUtils.reserveForSameDay(driver, adminUser, testData, PSSConstants.LOGINLESS);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		int size = aptDateTime.getAppointmentDateList();
		logStep("Date selected is for App" + date);
		String dateSize = String.valueOf(size);
		assertEquals(dateSize, "1");
		log("current date is" + psspatientutils.currentESTDate(testData));
		assertEquals(date, psspatientutils.currentESTDate(testData));
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testReserveForSameDayNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone = aPIVerification.responseKeyValidationJson(response, "practiceTimezone");
		testData.setCurrentTimeZone(timezone);

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		adminUtils.reserveForSameDay(driver, adminUser, testData, PSSConstants.LOGINLESS);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		int size = aptDateTime.getAppointmentDateList();
		logStep("Date selected is for App" + date);
		String dateSize = String.valueOf(size);
		assertEquals(dateSize, "1");
		log("current date is" + psspatientutils.currentESTDate(testData));
		assertEquals(date, psspatientutils.currentESTDate(testData));
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testReserveForSameDayAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		String timezone = aPIVerification.responseKeyValidationJson(response, "practiceTimezone");
		testData.setCurrentTimeZone(timezone);

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		adminUtils.reserveForSameDay(driver, adminUser, testData, PSSConstants.LOGINLESS);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		int size = aptDateTime.getAppointmentDateList();
		logStep("Date selected is for App" + date);
		String dateSize = String.valueOf(size);
		assertEquals(dateSize, "1");
		log("current date is" + psspatientutils.currentESTDate(testData));
		assertEquals(date, psspatientutils.currentESTDate(testData));
	}	
}