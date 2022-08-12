// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

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

public class PSS2PatientPortalAcceptanceTests12 extends BaseTestNGWebDriver {
	
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
	public void testCeamTeam_CareTeamMembersAvailableOnSlotConfiguration_NG() throws Exception {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));

		Response response;
		JSONArray arr;

		String pcpvalue = propertyData.getProperty("ct.pcpwith0Confg.pm.ng");
		String fctvalue = propertyData.getProperty("ct.fct.pm.ng");
		String careTeamIdValue = propertyData.getProperty("ct.id.ng");
		String pcpIdValue = propertyData.getProperty("ct.pcp.id.pm.ng");
		String bookIdValue=propertyData.getProperty("ct.book.id.pm.ng");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		String adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		int careTeamId=Integer.parseInt(careTeamIdValue);
		int pcpId=Integer.parseInt(pcpIdValue);
		int bookId=Integer.parseInt(bookIdValue);
		String addPcpInCareTeamPayload=payloadAM02.addBookInCareTeamPayload(careTeamId, pcpId);
		response = postAPIRequestAM.saveCareTeamBook(practiceId, addPcpInCareTeamPayload);
		apv.responseCodeValidation(response, 200);
		
		String addBookInCareTeamPayload=payloadAM02.addBookInCareTeamPayload(careTeamId, bookId);
		response = postAPIRequestAM.saveCareTeamBook(practiceId, addBookInCareTeamPayload);
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamIdValue);
		apv.responseCodeValidation(response, 200);
		
		response=postAPIRequestAM.getBookById(practiceId, pcpIdValue);
		apv.responseCodeValidation(response, 200);
		
		String expected_PcpID=apv.responseKeyValidationJson(response, "name");	
		log("expected_PcpID is "+expected_PcpID);
		
		response=postAPIRequestAM.getBookById(practiceId, bookIdValue);
		apv.responseCodeValidation(response, 200);
		
		String expected_BookID=apv.responseKeyValidationJson(response, "name");	
		log("expected_BookID is "+expected_BookID);
		
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
		
		String fn = propertyData.getProperty("ct.fn.pm10.ng");
		String ln = propertyData.getProperty("ct.ln.pm10.ng");
		String dob = propertyData.getProperty("ct.dob.pm10.ng");
		String email = propertyData.getProperty("ct.email.pm10.ng");
		String gender = propertyData.getProperty("ct.gender.pm10.ng");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.ng");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.ng");
		
		String careTeamMessage_Exp=propertyData.getProperty("careteam.ann.ng.pm08");
		String appointmentTypeName=propertyData.getProperty("ct.appointment.type.ng");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,email,gender,zipCode,phoneNumber);

		logStep("Click on the Start Button ");
		homePage.btnStartSchedClick();
		
		StartAppointmentInOrder startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointment.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and PCP = " + expected_PcpID);
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(expected_PcpID));
		assertTrue(bookList.contains(expected_BookID));
		
		String careTeamMessage_Actual=provider.getCareTeamAnn();
		assertEquals(careTeamMessage_Actual, careTeamMessage_Exp);
		
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamIdValue);
		arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);
		
		for (int i = 0; i < len; i++) {
			int providerId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + providerId);
			response = postAPIRequestAM.deleteCareTeamBook(practiceId, careTeamIdValue, Integer.toString(providerId));
			apv.responseCodeValidation(response, 200);
		}

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCeamTeam_CareTeamMembersAvailableOnSlotConfiguration_GE() throws Exception {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));

		Response response;
		JSONArray arr;

		String pcpvalue = propertyData.getProperty("ct.pcpwith0Confg.pm.ge");
		String fctvalue = propertyData.getProperty("ct.fct.pm.ge");
		String careTeamIdValue = propertyData.getProperty("ct.id.ge");
		String pcpIdValue = propertyData.getProperty("ct.pcp.id.pm.ge");
		String bookIdValue=propertyData.getProperty("ct.book.id.pm.ge");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		String adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		int careTeamId=Integer.parseInt(careTeamIdValue);
		int pcpId=Integer.parseInt(pcpIdValue);
		int bookId=Integer.parseInt(bookIdValue);
		String addPcpInCareTeamPayload=payloadAM02.addBookInCareTeamPayload(careTeamId, pcpId);
		response = postAPIRequestAM.saveCareTeamBook(practiceId, addPcpInCareTeamPayload);
		apv.responseCodeValidation(response, 200);
		
		String addBookInCareTeamPayload=payloadAM02.addBookInCareTeamPayload(careTeamId, bookId);
		response = postAPIRequestAM.saveCareTeamBook(practiceId, addBookInCareTeamPayload);
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamIdValue);
		apv.responseCodeValidation(response, 200);
		
		response=postAPIRequestAM.getBookById(practiceId, pcpIdValue);
		apv.responseCodeValidation(response, 200);
		
		String expected_PcpID=apv.responseKeyValidationJson(response, "name");	
		log("expected_PcpID is "+expected_PcpID);
		
		response=postAPIRequestAM.getBookById(practiceId, bookIdValue);
		apv.responseCodeValidation(response, 200);
		
		String expected_BookID=apv.responseKeyValidationJson(response, "name");	
		log("expected_BookID is "+expected_BookID);
		
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
		
		String fn = propertyData.getProperty("ct.fn.pm10.ge");
		String ln = propertyData.getProperty("ct.ln.pm10.ge");
		String dob = propertyData.getProperty("ct.dob.pm10.ge");
		String email = propertyData.getProperty("ct.email.pm10.ge");
		String gender = propertyData.getProperty("ct.gender.pm10.ge");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.ge");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.ge");
		
		String careTeamMessage_Exp=propertyData.getProperty("careteam.ann.ng.pm08");
		String appointmentTypeName=propertyData.getProperty("ct.appointment.type.ge");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,email,gender,zipCode,phoneNumber);

		logStep("Click on the Start Button ");
		homePage.btnStartSchedClick();
		
		StartAppointmentInOrder startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointment.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and PCP = " + expected_PcpID);
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(expected_PcpID));
		assertTrue(bookList.contains(expected_BookID));
		
		String careTeamMessage_Actual=provider.getCareTeamAnn();
		assertEquals(careTeamMessage_Actual, careTeamMessage_Exp);
		
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamIdValue);
		arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);
		
		for (int i = 0; i < len; i++) {
			int providerId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + providerId);
			response = postAPIRequestAM.deleteCareTeamBook(practiceId, careTeamIdValue, Integer.toString(providerId));
			apv.responseCodeValidation(response, 200);
		}
			
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCeamTeam_CareTeamMembersAvailableOnSlotConfiguration_GW() throws Exception {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUpAM(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));

		Response response;
		JSONArray arr;

		String pcpvalue = propertyData.getProperty("ct.pcpwith0Confg.pm.gw");
		String fctvalue = propertyData.getProperty("ct.fct.pm.gw");
		String careTeamIdValue = propertyData.getProperty("ct.id.gw");
		String pcpIdValue = propertyData.getProperty("ct.pcp.id.pm.gw");
		String bookIdValue=propertyData.getProperty("ct.book.id.pm.gw");

		int pcp = Integer.parseInt(pcpvalue);
		int fct = Integer.parseInt(fctvalue);

		String adminPayload = payloadAM02.careTeamSettingPyaload(pcp, fct);
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, adminPayload);
		apv.responseCodeValidation(response, 200);
		
		int careTeamId=Integer.parseInt(careTeamIdValue);
		int pcpId=Integer.parseInt(pcpIdValue);
		int bookId=Integer.parseInt(bookIdValue);
		String addPcpInCareTeamPayload=payloadAM02.addBookInCareTeamPayload(careTeamId, pcpId);
		response = postAPIRequestAM.saveCareTeamBook(practiceId, addPcpInCareTeamPayload);
		apv.responseCodeValidation(response, 200);
		
		String addBookInCareTeamPayload=payloadAM02.addBookInCareTeamPayload(careTeamId, bookId);
		response = postAPIRequestAM.saveCareTeamBook(practiceId, addBookInCareTeamPayload);
		apv.responseCodeValidation(response, 200);
		
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamIdValue);
		apv.responseCodeValidation(response, 200);
		
		response=postAPIRequestAM.getBookById(practiceId, pcpIdValue);
		apv.responseCodeValidation(response, 200);
		
		String expected_PcpID=apv.responseKeyValidationJson(response, "name");	
		log("expected_PcpID is "+expected_PcpID);
		
		response=postAPIRequestAM.getBookById(practiceId, bookIdValue);
		apv.responseCodeValidation(response, 200);
		
		String expected_BookID=apv.responseKeyValidationJson(response, "name");	
		log("expected_BookID is "+expected_BookID);
		
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
		
		String fn = propertyData.getProperty("ct.fn.pm10.gw");
		String ln = propertyData.getProperty("ct.ln.pm10.gw");
		String dob = propertyData.getProperty("ct.dob.pm10.gw");
		String email = propertyData.getProperty("ct.email.pm10.gw");
		String gender = propertyData.getProperty("ct.gender.pm10.gw");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.gw");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.gw");
		
		String careTeamMessage_Exp=propertyData.getProperty("careteam.ann.ng.pm08");
		String appointmentTypeName=propertyData.getProperty("ct.appointment.type.gw");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn,ln,dob,email,gender,zipCode,phoneNumber);

		logStep("Click on the Start Button ");
		homePage.btnStartSchedClick();
		
		StartAppointmentInOrder startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointment.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider1 = " + expected_PcpID);
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(expected_PcpID));
		assertTrue(bookList.contains(expected_BookID));
		
		String careTeamMessage_Actual=provider.getCareTeamAnn();
		assertEquals(careTeamMessage_Actual, careTeamMessage_Exp);
		
		response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careTeamIdValue);
		arr = new JSONArray(response.body().asString());
		int len = arr.length();
		log("Length is- " + len);
		
		for (int i = 0; i < len; i++) {
			int providerId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + providerId);
			response = postAPIRequestAM.deleteCareTeamBook(practiceId, careTeamIdValue, Integer.toString(providerId));
			apv.responseCodeValidation(response, 200);
		}
	
	}
}