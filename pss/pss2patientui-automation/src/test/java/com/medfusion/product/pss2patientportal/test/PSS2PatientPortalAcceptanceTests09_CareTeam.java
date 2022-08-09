// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONObject;
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

public class PSS2PatientPortalAcceptanceTests09_CareTeam extends BaseTestNGWebDriver {

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
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddRule() throws Exception {

		logStep("Verify the default message for patient status - Alerts & Lockout");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		addRule("S,L,B,T", "S,T,L,B");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_BasicCareTeam_NG() throws Exception {
		
		
		log("PSS-15457 : To verify if Care Team members are displayed when PCP gets filtered");
		log(" due to its slots unavailability");
		
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

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcpwith0Confg.pm05");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.ng");
		String careTeamId = propertyData.getProperty("patient.id.careteam.id.pm05");
		
		String locationName=propertyData.getProperty("ct.location.pm09.ng");
		String apptTypeName=propertyData.getProperty("ct.appttype.pm09.ng");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		HashSet<String> l2 = new HashSet<String>();
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamId);
		apv.responseCodeValidation(response, 200);

		arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);

		for (int i = 0; i < len; i++) {
			String bookName = arr.getJSONObject(i).getString("displayName");
			l2.add(bookName);
			log("Book Added in list l2-" + bookName);
		}
		
		log("List of book from admin- " + l2);
		
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
		
		String fn = propertyData.getProperty("ct.fn.pm08.ng");
		String ln = propertyData.getProperty("ct.ln.pm08.ng");
		String dob = propertyData.getProperty("ct.dob.pm08.ng");
		String gender = propertyData.getProperty("ct.gender.pm08.ng");
		String careTeamMessage_Exp=propertyData.getProperty("careteam.ann.ng.pm08");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
		homePage.btnStartSchedClick();
		
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + apptTypeName);
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(apptTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + locationName);
		Provider provider = location.searchProvider(locationName);
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		
		ArrayList<String>l1=provider.getBookList();
		
		String careTeamMessage_Actual=provider.getCareTeamAnn();
		
		assertEquals(careTeamMessage_Actual, careTeamMessage_Exp);
	
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.containsAll(l1));
	}	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_BasicCareTeam_GW() throws Exception {
		
		
		log("PSS-15457 : To verify if Care Team members are displayed when PCP gets filtered");
		log(" due to its slots unavailability");
		
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

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcp.pm.gw");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.gw");
		String careTeamId = propertyData.getProperty("patient.id.careteam.id.gw");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		adminPayload = payloadAM02.careTeamSettingPyaloadGW(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		HashSet<String> l2 = new HashSet<String>();
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamId);
		apv.responseCodeValidation(response, 200);

		arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);

		for (int i = 0; i < len; i++) {
			String bookName = arr.getJSONObject(i).getString("displayName");
			l2.add(bookName);
			log("Book Added in list l2-" + bookName);
		}
		
		log("List of book from admin- " + l2);
		
		logStep("Set up the desired rule in Admin UI using API");
		addRule("T,L,B","L,T,B");
		
		String fn = propertyData.getProperty("ct.fn.pm08.gw");
		String ln = propertyData.getProperty("ct.ln.pm08.gw");
		String dob = propertyData.getProperty("ct.dob.pm08.gw");
		String gender = propertyData.getProperty("ct.gender.pm08.gw");
		String careTeamMessage_Exp=propertyData.getProperty("careteam.ann.ng.pm08");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
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
		
		ArrayList<String>l1=provider.getBookList();
		
		String careTeamMessage_Actual=provider.getCareTeamAnn();
		
		assertEquals(careTeamMessage_Actual, careTeamMessage_Exp);
	
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.containsAll(l1));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_BasicCareTeam_GE() throws Exception {
		
		
		log("PSS-15457 : To verify if Care Team members are displayed when PCP gets filtered");
		log(" due to its slots unavailability");
		
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

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcp.pm.ge");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.ge");
		String careTeamId = propertyData.getProperty("patient.id.careteam.id.ge");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		HashSet<String> l2 = new HashSet<String>();
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamId);
		apv.responseCodeValidation(response, 200);

		arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);

		for (int i = 0; i < len; i++) {
			String bookName = arr.getJSONObject(i).getString("displayName");
			l2.add(bookName);
			log("Book Added in list l2-" + bookName);
		}
		
		log("List of book from admin- " + l2);
		
		logStep("Set up the desired rule in Admin UI using API");
		
		addRule("T,L,B","L,T,B");
		
		String patientMatch = payloadAM.patientMatchGE();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);
		
		String fn = propertyData.getProperty("ct.fn.pm08.ge");
		String ln = propertyData.getProperty("ct.ln.pm08.ge");
		String dob = propertyData.getProperty("ct.dob.pm08.ge");
		String gender = propertyData.getProperty("ct.gender.pm08.ge");
		String careTeamMessage_Exp=propertyData.getProperty("careteam.ann.ng.pm08");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
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
		
		ArrayList<String>l1=provider.getBookList();
		
		String careTeamMessage_Actual=provider.getCareTeamAnn();
		
		assertEquals(careTeamMessage_Actual, careTeamMessage_Exp);
	
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.containsAll(l1));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_PCPNotInCT() throws Exception {

		log("PSS-15462: Verify if all the providers whose Share Patient config is ON are displayed");
		log("when PCP is not a part of Care team");
		
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

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcpwith0Confg.pm05");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.ng");
		String careTeamId = propertyData.getProperty("patient.id.careteam.id.pm05");
		String bookid = propertyData.getProperty("availableslot.bookid.pm.ng");
		String patientId = propertyData.getProperty("patient.id.careteam.pm.ng");
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
		
		String fn = propertyData.getProperty("ct.fn.pm08.ng");
		String ln = propertyData.getProperty("ct.ln.pm08.ng");
		String dob = propertyData.getProperty("ct.dob.pm08.ng");
		String gender = propertyData.getProperty("ct.gender.pm08.ng");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
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
		ArrayList<String> bookList_actual =provider.getBookList();

		assertTrue(bookList_actual.contains(expected_BookID));
		
		int book=Integer.parseInt(bookid);
		adminPayload=payloadAM02.addBookInCareTeamPyaload(book);
		response=postAPIRequestAM.saveCareTeamBook(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_WithSpeciality_NG() throws Exception {
		
		
		log("PSS-11799: PCP/RP get displayed on the UI when Share Patient is ON with specialty");
				
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		String adminPayload;

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcpwith0Confg.pm05");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.ng");

		String shareBookId=propertyData.getProperty("ct.bookid.pm08.ng");
		String shareBookName=propertyData.getProperty("ct.sharepatient.pm08.ng");
		String specialtyName=propertyData.getProperty("ct.specialty.pm08.ng");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		ArrayList<String>l2= new ArrayList<String>();
		response = postAPIRequestAM.getBookById(practiceId, shareBookId);
		apv.responseCodeValidation(response, 200);
		
		JSONObject jo= new JSONObject(response.asString());
		
		boolean sharePatientStatus=(Boolean) jo.get("sharePatients");		
		assertTrue(sharePatientStatus);
		
		logStep("Set up the desired rule in Admin UI using API");
		addRule("S,B,T,L", "S,T,L,B");
		
		String fn = propertyData.getProperty("ct.fn.pm08.ng");
		String ln = propertyData.getProperty("ct.ln.pm08.ng");
		String dob = propertyData.getProperty("ct.dob.pm08.ng");
		String gender = propertyData.getProperty("ct.gender.pm08.ng");
			
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
		homePage.btnStartSchedClick();
		
		log("SBTL");
		Provider provider = null;
		Speciality speciality = null;
		StartAppointmentInOrder startappointmentInOrder = null;

		log("insurance is present on home Page going to skip insurance page");
		speciality = homePage.skipInsuranceForSpeciality(driver);
		startappointmentInOrder = speciality.selectSpeciality(specialtyName);
		log("clicked on specility");
		log("Starting point is present after insurance skipped ");
		provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		log("Successfully clicked on  " + PSSConstants.START_PROVIDER);

		log("Verfiy Provider Page and provider =" + testData.getProvider());		
		l2=provider.getBookList();
		log("Verfiy Provider Page and Provider = " + testData.getProvider());		
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.contains(shareBookName));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_WithSpeciality_GE() throws Exception {
		
		
		log("PSS-19765: Verify if PCP+ Care Team Members are displayed when Force booking with the");
		log("provider before showing the care team (days) is set to 0");
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));

		Response response;
		String adminPayload;

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcp.pm.ge");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.ge");
		String shareBookId=propertyData.getProperty("ct.bookid.pm08.ge");
		String shareBookName=propertyData.getProperty("ct.sharepatient.pm08.ge");
		String specialtyName=propertyData.getProperty("ct.specialty.pm08.ge");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		ArrayList<String>l2= new ArrayList<String>();
		response = postAPIRequestAM.getBookById(practiceId, shareBookId);
		apv.responseCodeValidation(response, 200);
		
		JSONObject jo= new JSONObject(response.asString());
		
		boolean sharePatientStatus=(Boolean) jo.get("sharePatients");
		
		assertTrue(sharePatientStatus);
		
		logStep("Set up the desired rule in Admin UI using API");
		addRule("S,B,T,L", "S,T,L,B");
		
		String patientMatch = payloadAM.patientMatchGE();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		apv.responseCodeValidation(response, 200);
		
		String fn = propertyData.getProperty("ct.fn.pm08.ge");
		String ln = propertyData.getProperty("ct.ln.pm08.ge");
		String dob = propertyData.getProperty("ct.dob.pm08.ge");
		String gender = propertyData.getProperty("ct.gender.pm08.ge");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
		homePage.btnStartSchedClick();		

		log("SBTL");
		Provider provider = null;
		Speciality speciality = null;
		StartAppointmentInOrder startappointmentInOrder = null;

		log("insurance is present on home Page going to skip insurance page");
		speciality = homePage.skipInsuranceForSpeciality(driver);
		startappointmentInOrder = speciality.selectSpeciality(specialtyName);
		log("clicked on specility");
		log("Starting point is present after insurance skipped ");
		provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		log("Successfully clicked on  " + PSSConstants.START_PROVIDER);

		log("Verfiy Provider Page and provider =" + testData.getProvider());		
		l2=provider.getBookList();
		log("Verfiy Provider Page and Provider = " + testData.getProvider());		
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.contains(shareBookName));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_WithSpeciality_GW() throws Exception {
		
		
		log("PSS-11799: PCP/RP get displayed on the UI when Share Patient is ON with specialty");
				
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));

		Response response;
		String adminPayload;

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcpwith0Confg.pm05");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.gw");

		String shareBookId=propertyData.getProperty("ct.bookid.pm08.gw");
		String shareBookName=propertyData.getProperty("ct.sharepatient.pm08.gw");
		String specialtyName=propertyData.getProperty("ct.specialty.pm08.gw");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		adminPayload = payloadAM02.careTeamSettingPyaloadGW(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		ArrayList<String>l2= new ArrayList<String>();
		response = postAPIRequestAM.getBookById(practiceId, shareBookId);
		apv.responseCodeValidation(response, 200);
		
		JSONObject jo= new JSONObject(response.asString());
		
		boolean sharePatientStatus=(Boolean) jo.get("sharePatients");
		
		assertTrue(sharePatientStatus);
		
		logStep("Set up the desired rule in Admin UI using API");
		addRule("S,B,T,L", "S,T,L,B");
		
		String fn = propertyData.getProperty("ct.fn.pm08.gw");
		String ln = propertyData.getProperty("ct.ln.pm08.gw");
		String dob = propertyData.getProperty("ct.dob.pm08.gw");
		String gender = propertyData.getProperty("ct.gender.pm08.ng");
			
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
		homePage.btnStartSchedClick();
		
		log("SBTL");
		Provider provider = null;
		Speciality speciality = null;
		StartAppointmentInOrder startappointmentInOrder = null;

		log("insurance is present on home Page going to skip insurance page");
		speciality = homePage.skipInsuranceForSpeciality(driver);
		startappointmentInOrder = speciality.selectSpeciality(specialtyName);
		log("clicked on specility");
		log("Starting point is present after insurance skipped ");
		provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		log("Successfully clicked on  " + PSSConstants.START_PROVIDER);

		log("Verfiy Provider Page and provider =" + testData.getProvider());		
		l2=provider.getBookList();
		log("Verfiy Provider Page and Provider = " + testData.getProvider());		
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.contains(shareBookName));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_SharePatient_NG() throws Exception {
		
		
		log("PSS-12105: PCP/RP get displayed on the UI when Share Patient is ON without specialty");
		
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

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcpwith0Confg.pm05");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.ng");
		String careTeamId = propertyData.getProperty("patient.id.careteam.id.pm05");
		
		String locationName=propertyData.getProperty("ct.location.pm09.ng");
		String apptTypeName=propertyData.getProperty("ct.appttype.pm09.ng");
		String bookid=propertyData.getProperty("ct.bookid.pm09.ng");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		response = postAPIRequestAM.getBookById(practiceId, bookid);
		apv.responseCodeValidation(response, 200);
		assertEquals(apv.responseKeyValidationJson(response, "sharePatients"),"true");

		adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		HashSet<String> l2 = new HashSet<String>();
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamId);
		apv.responseCodeValidation(response, 200);

		arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);

		for (int i = 0; i < len; i++) {
			String bookName = arr.getJSONObject(i).getString("displayName");
			l2.add(bookName);
			log("Book Added in list l2-" + bookName);
		}
		
		log("List of book from admin- " + l2);
		
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
		
		String fn = propertyData.getProperty("ct.fn.pm08.ng");
		String ln = propertyData.getProperty("ct.ln.pm08.ng");
		String dob = propertyData.getProperty("ct.dob.pm08.ng");
		String gender = propertyData.getProperty("ct.gender.pm08.ng");
		String careTeamMessage_Exp=propertyData.getProperty("careteam.ann.ng.pm08");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
		homePage.btnStartSchedClick();
		
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

		log("Verfiy Appointment Page and appointment =" + apptTypeName);
		log("does apt has a pop up? " + testData.getIsAppointmentPopup());

		Location location = appointment.selectTypeOfLocation(apptTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		log("Verfiy Location Page and location to be selected = " + locationName);
		Provider provider = location.searchProvider(locationName);
		log("address = " + location.getAddressValue());

		log("Verfiy Provider Page and Provider = " + testData.getProvider());
		
		ArrayList<String>l1=provider.getBookList();
		
		String careTeamMessage_Actual=provider.getCareTeamAnn();
		
		assertEquals(careTeamMessage_Actual, careTeamMessage_Exp);
	
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.containsAll(l1));
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_SharePatient_GW() throws Exception {
		
		
		log("PSS-12105: PCP/RP get displayed on the UI when Share Patient is ON without specialty");
		
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

		String pcpvalue = propertyData.getProperty("patient.id.careteam.pcp.pm.gw");
		String fctvalue = propertyData.getProperty("patient.id.careteam.fct.pm.gw");
		String careTeamId = propertyData.getProperty("patient.id.careteam.id.gw");
		String bookid=propertyData.getProperty("ct.bookid.pm09.gw");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);
		
		response = postAPIRequestAM.getBookById(practiceId, bookid);
		apv.responseCodeValidation(response, 200);
		assertEquals(apv.responseKeyValidationJson(response, "sharePatients"),"true");

		adminPayload = payloadAM02.careTeamSettingPyaloadGW(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);

		HashSet<String> l2 = new HashSet<String>();
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamId);
		apv.responseCodeValidation(response, 200);

		arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);

		for (int i = 0; i < len; i++) {
			String bookName = arr.getJSONObject(i).getString("displayName");
			l2.add(bookName);
			log("Book Added in list l2-" + bookName);
		}
		
		log("List of book from admin- " + l2);
		
		logStep("Set up the desired rule in Admin UI using API");
		addRule("T,L,B","L,T,B");
		
		String fn = propertyData.getProperty("ct.fn.pm08.gw");
		String ln = propertyData.getProperty("ct.ln.pm08.gw");
		String dob = propertyData.getProperty("ct.dob.pm08.gw");
		String gender = propertyData.getProperty("ct.gender.pm08.gw");
		String careTeamMessage_Exp=propertyData.getProperty("careteam.ann.ng.pm08");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,"",gender,"","");

		logStep("Click on the Start Button ");
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
		
		ArrayList<String>l1=provider.getBookList();
		
		String careTeamMessage_Actual=provider.getCareTeamAnn();
		
		assertEquals(careTeamMessage_Actual, careTeamMessage_Exp);
	
		log("List of book from Patient UI- " + l1);
		
	}

}
