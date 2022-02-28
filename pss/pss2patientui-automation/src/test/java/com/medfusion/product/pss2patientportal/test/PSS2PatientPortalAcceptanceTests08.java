// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;
import java.io.IOException;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestPMNG;
import com.medfusion.product.pss2patientapi.payload.PayloadAM02;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadPM02;
import com.medfusion.product.pss2patientapi.payload.PayloadPssPMNG;
import com.medfusion.product.pss2patientmodulatorapi.test.PSS2PatientModulatorrAcceptanceNGTests;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;

public class PSS2PatientPortalAcceptanceTests08 extends BaseTestNGWebDriver {

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

	APIVerification apv = new APIVerification();

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
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlerts_DefaultMsgAT() throws Exception {

		logStep("Verify the default message for patient status - Alerts & Lockout");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;

		String patientMatch = payloadAM.patientMatchAt();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);

		String group = propertyData.getProperty("lockoutgroup.ng");
		String lockoutMessage = propertyData.getProperty("lockout.default.message");

		String activeL = payloadAM.activeLockoutAT(group);
		response = postAPIRequestAM.lockoutPost(practiceId, activeL, "/lockout");
		apv.responseCodeValidation(response, 200);

		String lockoutid = apv.responseKeyValidationJson(response, "id");

		log("Lockout Id- " + lockoutid);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.at");
		String ln = propertyData.getProperty("lockout.ln.at");
		String dob = propertyData.getProperty("lockout.dob.at");
		String gender = propertyData.getProperty("lockout.gender.at");

		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		String actualPopUpMessage = homePage.getTextLockoutPopUpMsg();

		assertEquals(actualPopUpMessage, lockoutMessage, "Alert message is wrong");

		log("Test Case passed.");

		response = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.deleteLockoutById(practiceId, lockoutid);
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlerts_DefaultMsgGE() throws Exception {

		logStep("Verify the default message for patient status - Alerts & Lockout");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;

		String patientMatch = payloadAM.patientMatchGE();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);

		String lockoutEngMessage = propertyData.getProperty("lockout.default.message");
		String activeL = payloadAM.patientStatusGE();
		response = postAPIRequestAM.lockoutPost(practiceId, activeL, "/lockout");
		apv.responseCodeValidation(response, 200);

		String lockoutid = apv.responseKeyValidationJson(response, "id");

		log("Lockout Id- " + lockoutid);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.ge");
		String ln = propertyData.getProperty("lockout.ln.ge");
		String dob = propertyData.getProperty("lockout.dob.ge");
		String gender = propertyData.getProperty("lockout.gender.ge");		
		String email = propertyData.getProperty("lockout.email.ge");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, "", "");
		String actualPopUpMessage = homePage.getTextLockoutPopUpMsg();

		assertEquals(actualPopUpMessage, lockoutEngMessage, "Lockout message is wrong");

		response = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.deleteLockoutById(practiceId, lockoutid);
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlerts_DefaultMsgGW() throws Exception {

		logStep("Verify the default message for patient status - Alerts & Lockout");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		
		String patientMatch=payloadAM.patientInfoWithOptionalLLNG();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);
		
		String idPatientStatus= propertyData.getProperty("patientstatus.id.gw");
		String group=propertyData.getProperty("alertgroup.ng");
		String lockoutMessage=propertyData.getProperty("patientstatus.msg.en.gw");
		String lockouType=propertyData.getProperty("patientstatus.type");
		String key=propertyData.getProperty("patientstatus.key.gw");
		
		String lockoutPayload=payloadAM.patientStatusGW(idPatientStatus, key, lockouType, group, lockoutMessage, lockoutMessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		apv.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.gw");
		String ln = propertyData.getProperty("lockout.ln.gw");
		String dob = propertyData.getProperty("lockout.dob.gw");
		String gender = propertyData.getProperty("lockout.gender.gw");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		String actualPopUpMessage = homePage.getTextAlertPopUpMsg();

		assertEquals(actualPopUpMessage, lockoutMessage, "Alert message is wrong");

		homePage.clickAlertPopUp();
		boolean bool = homePage.isbtnstartSchedulingPresent();

		assertEquals(bool, true, "Alert workflow is wrong");
		log("Start Schedule Button is visible. So Test Case passed.");	
	
	}
	
	

}
