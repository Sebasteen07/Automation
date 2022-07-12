// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.json.JSONArray;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.Appointment.Speciality.Speciality;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
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
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;

public class PSS2PatientPortalAcceptanceTests05 extends BaseTestNGWebDriver {

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
	public PSS2PatientPortalAcceptanceTests05 portal05;

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
		portal05 =new PSS2PatientPortalAcceptanceTests05();
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId,
				payloadAM02.openTokenPayload(practiceId, userID));
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}
	
	public void addRule(String r1, String r2) throws Exception {

		Response response;
		JSONArray arr;
		String rule1 = r1.replaceAll("[^a-zA-Z0-9]", "");
		log("Rule 1 - " + r1);
		String rule2 = r2.replaceAll("[^a-zA-Z0-9]", "");
		log("Rule 2 - " + r2);

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
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload(rule1, r1));
		apv.responseCodeValidation(response, 200);
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload(rule2, r2));
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_AddRuleNG() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		
		portal05.addRule("S,L,B,T", "S,T,L,B");
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_LastSeenProvider_NG() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String fn = propertyData.getProperty("lastseen.fn.pm.ng");
		String ln = propertyData.getProperty("lastseen.ln.pm.ng");
		String dob = propertyData.getProperty("lastseen.dob.pm.ng");
		String gender = propertyData.getProperty("lastseen.gender.pm.ng");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

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
		Provider provider = location.searchProviderAndClickNew(testData.getLocation());
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		
		int noProvider=provider.getNumberOfBook();
		log("Number of provider are-"+noProvider);
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_ForceLastSeen_NG() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String fn = propertyData.getProperty("lastseen.fn.pm.ng");
		String ln = propertyData.getProperty("lastseen.ln.pm.ng");
		String dob = propertyData.getProperty("lastseen.dob.pm.ng");
		String gender = propertyData.getProperty("lastseen.gender.pm.ng");

		String bookAppt=propertyData.getProperty("fct.bookappt.id.pm08");
		String book=propertyData.getProperty("fct.book.id.pm08");
		String appt=propertyData.getProperty("fct.appt.id.pm08");
		String loc=propertyData.getProperty("fct.location.id.pm08");
		String fct=propertyData.getProperty("fct.days.pm08");
		
		int bookApptId=Integer.parseInt(bookAppt);
		int bookId=Integer.parseInt(book);
		int apptId=Integer.parseInt(appt);
		int locationId=Integer.parseInt(loc);
		int fctDays=Integer.parseInt(fct);
		
		payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, fctDays);
		apv.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

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
		
		int noProvider=provider.getNumberOfBook();
		log("Number of provider are-"+noProvider);
		
		assertEquals(noProvider, 1);
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
		
		payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, 0);
		apv.responseCodeValidation(response, 200);
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_ForceLastSeen_Specialty_NG() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		logStep("Set up the desired rule in Admin UI using API");
		portal05.addRule("S,T,L,B", "S,L,T,B");
		
		String patientMatch=payloadAM.patientInfoWithOptionalLLNG();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String fn = propertyData.getProperty("lastseen.fn.pm.ng");
		String ln = propertyData.getProperty("lastseen.ln.pm.ng");
		String dob = propertyData.getProperty("lastseen.dob.pm.ng");
		String gender = propertyData.getProperty("lastseen.gender.pm.ng");

		String bookAppt=propertyData.getProperty("fct.bookappt.id.pm08");
		String book=propertyData.getProperty("fct.book.id.pm08");
		String appt=propertyData.getProperty("fct.appt.id.pm08");
		String loc=propertyData.getProperty("fct.location.id.pm08");
		String fct=propertyData.getProperty("fct.days.pm08");
		
		int bookApptId=Integer.parseInt(bookAppt);
		int bookId=Integer.parseInt(book);
		int apptId=Integer.parseInt(appt);
		int locationId=Integer.parseInt(loc);
		int fctDays=Integer.parseInt(fct);
		
		adminPayload= payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, fctDays);
		
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");
		
		AppointmentPage appointment;
		StartAppointmentInOrder startappointmentInOrder;
		Speciality speciality;

		speciality = homePage.skipInsuranceForSpeciality(driver);
		startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
		log("clicked on specility");

		log("StartPage is Present after clicked ok skip insurance");
		appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		
		int noProvider=provider.getNumberOfBook();
		log("Number of provider are-"+noProvider);
		
		assertEquals(noProvider, 1);
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
		
		payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, 0);
		apv.responseCodeValidation(response, 200);
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_ForceLastSeen_Specialty_GE() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));

		Response response;
		String adminPayload;
		logStep("Set up the desired rule in Admin UI using API");
		portal05.addRule("S,T,L,B", "S,L,T,B");
	
		String patientMatch=payloadAM.patientInfoWithOptionalGE();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		

		String bookAppt=propertyData.getProperty("fct.bookappt.id.ge.pm08");
		String book=propertyData.getProperty("fct.book.id.ge.pm08");
		String appt=propertyData.getProperty("fct.appt.id.ge.pm08");
		String loc=propertyData.getProperty("fct.location.id.ge.pm08");
		String fct=propertyData.getProperty("fct.days.pm08");
		
		int bookApptId=Integer.parseInt(bookAppt);
		int bookId=Integer.parseInt(book);
		int apptId=Integer.parseInt(appt);
		int locationId=Integer.parseInt(loc);
		int fctDays=Integer.parseInt(fct);
		
		adminPayload=payloadAM02.flsBookappt_GE(bookApptId, bookId, apptId, locationId, fctDays);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		String firstNamePreReq = propertyData.getProperty("firstname.prereqpast.ge");
		String lastNamePreReq = propertyData.getProperty("lastname.prereqpast.ge");
		String genderPreReq = propertyData.getProperty("gender.prereqpast.ge");
		String dobPreReq = propertyData.getProperty("dob.prereqpast.ge");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		
		logStep("Click on the Start Button ");
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNamePreReq, lastNamePreReq, dobPreReq, "", genderPreReq, "", "");
		homePage.btnStartSchedClick();		
		
		AppointmentPage appointment;
		StartAppointmentInOrder startappointmentInOrder;
		Speciality speciality;

		speciality = homePage.skipInsuranceForSpeciality(driver);
		startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
		log("clicked on specility");

		log("StartPage is Present after clicked ok skip insurance");
		appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		
		int noProvider=provider.getNumberOfBook();
		log("Number of provider are-"+noProvider);
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
		
		adminPayload=payloadAM02.flsBookappt_GE(bookApptId, bookId, apptId, locationId, 0);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_ForceLastSeen_Specialty_GW() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));

		Response response;
		String adminPayload;
		logStep("Set up the desired rule in Admin UI using API");
		portal05.addRule("S,T,L,B", "S,L,T,B");
	
		String patientMatch=payloadAM.patientInfoWithOptionalGW();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		

		String bookAppt=propertyData.getProperty("fct.bookappt.id.gw.pm08");
		String book=propertyData.getProperty("fct.book.id.gw.pm08");
		String appt=propertyData.getProperty("fct.appt.id.gw.pm08");
		String loc=propertyData.getProperty("fct.location.id.gw.pm08");
		String fct=propertyData.getProperty("fct.days.pm08");
		
		int bookApptId=Integer.parseInt(bookAppt);
		int bookId=Integer.parseInt(book);
		int apptId=Integer.parseInt(appt);
		int locationId=Integer.parseInt(loc);
		int fctDays=Integer.parseInt(fct);
		
		adminPayload=payloadAM02.flsBookappt_GW(bookApptId, bookId, apptId, locationId, fctDays);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		String fn = propertyData.getProperty("lastseen.fn.pm.gw");
		String ln = propertyData.getProperty("lastseen.ln.pm.gw");
		String dob = propertyData.getProperty("lastseen.dob.pm.gw");
		String gender = propertyData.getProperty("lastseen.gender.pm.gw");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		
		logStep("Click on the Start Button ");
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		homePage.btnStartSchedClick();		
		
		AppointmentPage appointment;
		StartAppointmentInOrder startappointmentInOrder;
		Speciality speciality;

		speciality = homePage.skipInsuranceForSpeciality(driver);
		startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
		log("clicked on specility");

		log("StartPage is Present after clicked ok skip insurance");
		appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		
		int noProvider=provider.getNumberOfBook();
		log("Number of provider are-"+noProvider);
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
		
		adminPayload=payloadAM02.flsBookappt_GW(bookApptId, bookId, apptId, locationId, 0);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_ForceLastSeen_Specialty_AT() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));

		Response response;
		String adminPayload;
		logStep("Set up the desired rule in Admin UI using API");
		portal05.addRule("S,T,L,B", "S,L,T,B");
	
		String patientMatch=payloadAM.patientMatchAt();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		

		String bookAppt=propertyData.getProperty("fct.bookappt.id.AT.pm08");
		String book=propertyData.getProperty("fct.book.id.at.pm08");
		String appt=propertyData.getProperty("fct.appt.id.at.pm08");
		String loc=propertyData.getProperty("fct.location.id.at.pm08");
		String fct=propertyData.getProperty("fct.days.pm08");
		
		int bookApptId=Integer.parseInt(bookAppt);
		int bookId=Integer.parseInt(book);
		int apptId=Integer.parseInt(appt);
		int locationId=Integer.parseInt(loc);
		int fctDays=Integer.parseInt(fct);
		
		adminPayload=payloadAM02.flsBookappt_AT(bookApptId, bookId, apptId, locationId, fctDays);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		String fn = propertyData.getProperty("lastseen.fn.pm.at");
		String ln = propertyData.getProperty("lastseen.ln.pm.at");
		String dob = propertyData.getProperty("lastseen.dob.pm.at");
		String gender = propertyData.getProperty("lastseen.gender.pm.at");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		
		logStep("Click on the Start Button ");
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		homePage.btnStartSchedClick();		
		
		AppointmentPage appointment;
		StartAppointmentInOrder startappointmentInOrder;
		Speciality speciality;

		speciality = homePage.skipInsuranceForSpeciality(driver);
		startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
		log("clicked on specility");

		log("StartPage is Present after clicked ok skip insurance");
		appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + testData.getLocation());
		Provider provider = location.searchProvider(testData.getLocation());
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		
		int noProvider=provider.getNumberOfBook();
		log("Number of provider are-"+noProvider);
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
		
		adminPayload=payloadAM02.flsBookappt_AT(bookApptId, bookId, apptId, locationId, 0);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_ForceLastSeen_BookFirst_NG() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("BLT", "B,L,T"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String fn = propertyData.getProperty("lastseen.fn.pm.ng");
		String ln = propertyData.getProperty("lastseen.ln.pm.ng");
		String dob = propertyData.getProperty("lastseen.dob.pm.ng");
		String gender = propertyData.getProperty("lastseen.gender.pm.ng");

		String bookAppt=propertyData.getProperty("fct.bookappt.id.pm08");
		String book=propertyData.getProperty("fct.book.id.pm08");
		String appt=propertyData.getProperty("fct.appt.id.pm08");
		String loc=propertyData.getProperty("fct.location.id.pm08");
		String fct=propertyData.getProperty("fct.days.pm08");
		
		int bookApptId=Integer.parseInt(bookAppt);
		int bookId=Integer.parseInt(book);
		int apptId=Integer.parseInt(appt);
		int locationId=Integer.parseInt(loc);
		int fctDays=Integer.parseInt(fct);
		
		payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, fctDays);
		apv.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		logStep("Clicked on the Skip Insurance Button ");
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Select First Provider- " + PSSConstants.START_PROVIDER);
		Provider provider = startAppointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);

		log("Verfiy Provider Page and Provider = " + testData.getProvider());		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
		
		payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, 0);
		apv.responseCodeValidation(response, 200);
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_ForceLastSeen_BookFirst_GE() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));

		Response response;
		String adminPayload;
		
		logStep("Set up the desired rule in Admin UI using API");
		addRule("B,L,T", "L,T,B");

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		String bookAppt=propertyData.getProperty("fct.bookappt.id.pm08");
		String book=propertyData.getProperty("fct.book.id.pm08");
		String appt=propertyData.getProperty("fct.appt.id.pm08");
		String loc=propertyData.getProperty("fct.location.id.pm08");
		String fct=propertyData.getProperty("fct.days.pm08");
		
		int bookApptId=Integer.parseInt(bookAppt);
		int bookId=Integer.parseInt(book);
		int apptId=Integer.parseInt(appt);
		int locationId=Integer.parseInt(loc);
		int fctDays=Integer.parseInt(fct);
		
		payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, fctDays);
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGE());
		apv.responseCodeValidation(response, 200);
		
		String firstNamePreReq = propertyData.getProperty("firstname.prereqpast.ge");
		String lastNamePreReq = propertyData.getProperty("lastname.prereqpast.ge");
		String genderPreReq = propertyData.getProperty("gender.prereqpast.ge");
		String dobPreReq = propertyData.getProperty("dob.prereqpast.ge");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		
		logStep("Click on the Start Button ");
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNamePreReq, lastNamePreReq, dobPreReq, "", genderPreReq, "", "");
		homePage.btnStartSchedClick();	
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		logStep("Clicked on the Skip Insurance Button ");
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Select First Provider- " + PSSConstants.START_PROVIDER);
		Provider provider = startAppointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);

		log("Verfiy Provider Page and Provider = " + testData.getProvider());		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
		
		payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, 0);
		apv.responseCodeValidation(response, 200);
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_ForceLastSeen_BookFirst_GW() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));

		Response response;
		String adminPayload;
		
		logStep("Set up the desired rule in Admin UI using API");
		addRule("B,L,T", "L,T,B");
		
		String patientMatch=payloadAM.patientInfoWithOptionalGW();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		

		String bookAppt=propertyData.getProperty("fct.bookappt.id.gw.pm08");
		String book=propertyData.getProperty("fct.book.id.gw.pm08");
		String appt=propertyData.getProperty("fct.appt.id.gw.pm08");
		String loc=propertyData.getProperty("fct.location.id.gw.pm08");
		String fct=propertyData.getProperty("fct.days.pm08");
		
		int bookApptId=Integer.parseInt(bookAppt);
		int bookId=Integer.parseInt(book);
		int apptId=Integer.parseInt(appt);
		int locationId=Integer.parseInt(loc);
		int fctDays=Integer.parseInt(fct);
		
		adminPayload=payloadAM02.flsBookappt_GW(bookApptId, bookId, apptId, locationId, fctDays);
		response=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		String fn = propertyData.getProperty("lastseen.fn.pm.gw");
		String ln = propertyData.getProperty("lastseen.ln.pm.gw");
		String dob = propertyData.getProperty("lastseen.dob.pm.gw");
		String gender = propertyData.getProperty("lastseen.gender.pm.gw");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		
		logStep("Click on the Start Button ");
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		homePage.btnStartSchedClick();	
		logStep("Clicked on the Start Button ");

		StartAppointmentInOrder startAppointmentInOrder = null;
		logStep("Clicked on the Skip Insurance Button ");
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Select First Provider- " + PSSConstants.START_PROVIDER);
		Provider provider = startAppointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);

		log("Verfiy Provider Page and Provider = " + testData.getProvider());		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
		
		payloadAM02.fctBookappointmenttype(bookApptId, bookId, apptId, locationId, 0);
		apv.responseCodeValidation(response, 200);
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_LastSeenProvider_GW() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGW());
		apv.responseCodeValidation(response, 200);
		
		String fn = propertyData.getProperty("lastseen.fn.pm.gw");
		String ln = propertyData.getProperty("lastseen.ln.pm.gw");
		String dob = propertyData.getProperty("lastseen.dob.pm.gw");
		String gender = propertyData.getProperty("lastseen.gender.pm.gw");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

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
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		int noProvider=provider.getNumberOfBook();
		log("Number of provider are-"+noProvider);
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_LastSeenProvider_GE() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaload(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGE());
		apv.responseCodeValidation(response, 200);
		
		String firstNamePreReq = propertyData.getProperty("firstname.prereqpast.ge");
		String lastNamePreReq = propertyData.getProperty("lastname.prereqpast.ge");
		String genderPreReq = propertyData.getProperty("gender.prereqpast.ge");
		String dobPreReq = propertyData.getProperty("dob.prereqpast.ge");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		
		logStep("Click on the Start Button ");
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNamePreReq, lastNamePreReq, dobPreReq, "", genderPreReq, "", "");
		homePage.btnStartSchedClick();		

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
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
				
		adminPayload=payloadAM02.lastSeenProviderPyaload(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01UI_LastSeenProvider_AT() throws Exception {
		
		logStep("Verify the Next Available should display for LBT Rule-");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));

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

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TLB", "T,L,B"));
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(response, 200);

		adminPayload=payloadAM02.lastSeenProviderPyaloadAT(12);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);		
		
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientMatchAt());
		apv.responseCodeValidation(response, 200);
		
		String fn = propertyData.getProperty("lastseen.fn.at");
		String ln = propertyData.getProperty("lastseen.ln.at");
		String dob = propertyData.getProperty("lastseen.dob.at");
		String gender = propertyData.getProperty("lastseen.gender.at");
		String email = propertyData.getProperty("lastseen.email.at");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,email,gender,"","");

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
		
		String actual_BookName=provider.getProviderText(testData.getProvider());
		log("Provider Name is-  " +actual_BookName );
		
		assertTrue(actual_BookName.contains("LAST SEEN"));	
				
		adminPayload=payloadAM02.lastSeenProviderPyaloadAT(1);
		response=postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);	

	}	

}
