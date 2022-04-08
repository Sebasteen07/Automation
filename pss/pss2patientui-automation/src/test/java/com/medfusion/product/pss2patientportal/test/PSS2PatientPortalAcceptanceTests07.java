// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.decisionTree.ManageDecisionTree;
import com.medfusion.product.object.maps.pss2.decisionTree.ManageGeneralInformation;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.Appointment.Speciality.Speciality;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentQuestionsPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Login.PSS2AdminLogin;
import com.medfusion.product.object.maps.pss2.page.settings.AdminPatientMatching;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.object.maps.pss2.page.settings.PatientFlow;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSNewPatient;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;


public class PSS2PatientPortalAcceptanceTests07 extends BaseTestNGWebDriver {

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void excudeSlotsWithShowProviderOFF_NG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		String appointmentType = propertyData.getProperty("appointment.type.ng");
		String firstValue = propertyData.getProperty("firstvalue.excludeslot.pm01");
		String secondValue = propertyData.getProperty("secondvalue.excludeslot.pm01");
		adminUtils.exclueSlotsWithShowProviderOff(driver, adminUser, testData, appointmentType, firstValue, secondValue);
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
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App " + date);	
		String time = aptDateTime.getfirsttime();
		log("First time is   " + time);
		assertEquals(time, firstValue);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void excudeSlotsWithShowProviderOFF_AT() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		String appointmentType = propertyData.getProperty("appointmenttype.at");
		String firstValue = propertyData.getProperty("firstvalue.excludeslot.pm01");
		String secondValue = propertyData.getProperty("secondvalue.excludeslot.pm01");
		adminUtils.exclueSlotsWithShowProviderOff(driver, adminUser, testData, appointmentType, firstValue, secondValue);
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
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App " + date);	
		String time = aptDateTime.getfirsttime();
		log("First time is   " + time);
		assertEquals(time, firstValue);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientMatching_NG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		PSSNewPatient newPatient = new PSSNewPatient();
		String appointmentType = propertyData.getProperty("appointment.type.ng07");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientFlow = pssPracticeConfig.gotoPatientFlowTab();
		AdminPatientMatching adminPatientMatching = patientFlow.gotoPatientMatchingTab();
		adminPatientMatching.patientMatchingSelection();
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		newPatient.createPatientDetails(testData);
		String alternateNumber = propertyData.getProperty("alternate.number.ng07");
		HomePage homePage = loginlessPatientInformation.fillPatientFormWithAlternateNumber(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber(), alternateNumber);
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		homePage.logout();
		HomePage homePage1 = loginlessPatientInformation.fillPatientFormWithAlternateNumber(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber(), ""); 
		if(homePage.getFutureAppointmentListSize()>0) {
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(false);
		}

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientMatching_GE() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSNewPatient newPatient = new PSSNewPatient();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String appointmentType = propertyData.getProperty("appointment.type.ge07");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientFlow = pssPracticeConfig.gotoPatientFlowTab();
		AdminPatientMatching adminPatientMatching = patientFlow.gotoPatientMatchingTab();
		adminPatientMatching.patientMatchingSelection();
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		newPatient.createPatientDetails(testData);
		String alternateNumber = propertyData.getProperty("alternate.number.ge07");
		HomePage homePage = loginlessPatientInformation.fillPatientFormWithAlternateNumber(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber(), alternateNumber);
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		homePage.logout();
		HomePage homePage1 = loginlessPatientInformation.fillPatientFormWithAlternateNumber(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), 
				testData.getPrimaryNumber(), "");
		if(homePage.getFutureAppointmentListSize()>0) {
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void impactOnPastAndUpcominApptWithProviderOn_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		adminUtils.addRuleWithoutSpecialty(driver, adminUser);
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		patientUtils.bookAppointmentWithLTBFlow(homePage, testData, driver, testData.getProvider(), 
				testData.getAppointmenttype(), testData.getLocation());
		if(homePage.getFutureAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getPastAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getFutureAppointmentPracticeText().equals(testData.getProvider())) {
			Assert.assertTrue(true);
			log("Provider name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getPastAppointmentPracticeText().equals(testData.getProvider())) {
			Assert.assertTrue(true);
			log("Provider name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void impactOnPastAndUpcominApptWithoutShowProvider_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		adminUtils.addRuleWithoutBook(driver, adminUser);
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation1 = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation1.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		patientUtils.bookAppointmentWithLTFlow(homepage, testData, driver);
		if(homepage.getFutureAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homepage.getPastAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homepage.getFutureAppointmentPracticeText().equals(testData.getPracticeDisplayName())) {
			Assert.assertTrue(true);
			log("Practice name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homepage.getPastAppointmentPracticeText().equals(testData.getPracticeDisplayName())) {
			Assert.assertTrue(true);
			log("Practice name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void impactOnPastAndUpcominApptWithProviderOn_AT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		adminUtils.addRuleWithoutSpecialty(driver, adminUser);
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		patientUtils.bookAppointmentWithLTBFlow(homePage, testData, driver, testData.getProvider(), 
				testData.getAppointmenttype(), testData.getLocation());
		if(homePage.getFutureAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getPastAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getFutureAppointmentPracticeText().equals(testData.getProvider())) {
			Assert.assertTrue(true);
			log("Provider name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getPastAppointmentPracticeText().equals(testData.getProvider())) {
			Assert.assertTrue(true);
			log("Provider name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void impactOnPastAndUpcominApptWithoutShowProvider_AT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		adminUtils.addRuleWithoutBook(driver, adminUser);
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		patientUtils.bookAppointmentWithLTFlow(homepage, testData, driver);
		if(homepage.getFutureAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homepage.getPastAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homepage.getFutureAppointmentPracticeText().equals(testData.getPracticeDisplayName())) {
			Assert.assertTrue(true);
			log("Practice name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homepage.getPastAppointmentPracticeText().equals(testData.getPracticeDisplayName())) {
			Assert.assertTrue(true);
			log("Practice name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void impactOnPastAndUpcominApptWithProviderOn_GW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		PSS2PracticeConfiguration practiceconfiguration = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = practiceconfiguration.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		patientflow.logout();
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		patientUtils.bookAppointmentWithLTBFlow(homePage, testData, driver, testData.getProvider(), 
				testData.getAppointmenttype(), testData.getLocation());
		if(homePage.getFutureAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getPastAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getFutureAppointmentPracticeText().equals(testData.getProvider())) {
			Assert.assertTrue(true);
			log("Provider name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getPastAppointmentPracticeText().equals(testData.getProvider())) {
			Assert.assertTrue(true);
			log("Provider name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void impactOnPastAndUpcominApptWithProviderOn_GE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		PSS2PracticeConfiguration practiceconfiguration = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = practiceconfiguration.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		Log4jUtil.log("Logging out of PSS 2.0 admin UI");
		patientflow.logout();
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		patientUtils.bookAppointmentWithLTBFlow(homePage, testData, driver, testData.getProvider(), 
				testData.getAppointmenttype(), testData.getLocation());
		if(homePage.getFutureAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getPastAppointmentLocationText().equals(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getFutureAppointmentPracticeText().equals(testData.getProvider())) {
			Assert.assertTrue(true);
			log("Provider name is shown in Upcoming appointment details");
		}else {
			Assert.assertTrue(false);
		}
		
		if(homePage.getPastAppointmentPracticeText().equals(testData.getProvider())) {
			Assert.assertTrue(true);
			log("Provider name is shown in Past appointment details");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void publishedCategoryDisplayedOnPatientUI_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.ng");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.ng");
		adminUtils.decisionTreeSettingsWithProviderOFF(driver, adminUser, testData, testData.getDecisionTreeName(), 
				testData.getAppointmenttype(), reasonForAppointment);
		adminUtils.pageRefresh(driver);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		patientUtils.bookAppointmentWithDecisiontree(null, testData, driver, testData.getDecisionTreeName(), 
				testData.getAppointmenttype(), reasonForAppointment, decisionTreeAnswer);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletion(driver, adminUser, testData, testData.getDecisionTreeName());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void publishedCategoryDisplayedOnPatientUI_GE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.ge");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.ge");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.importDecisionTree(testData.getDecisionTreeName());
		manageDecisionTree.selectDecisionTree(testData.getDecisionTreeName());
		ManageGeneralInformation manageGeneralInformation = manageDecisionTree.goToGeneralInformation();
		manageGeneralInformation.setApptTypeDecisionTree(testData.getAppointmenttype());
		manageGeneralInformation.addReasonGeneralInfo(reasonForAppointment);
		manageGeneralInformation.publishGeneralInfo();
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		Provider provider = apptQuestion.selectAnswerFromQue(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletion(driver, adminUser, testData, testData.getDecisionTreeName());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void publishedCategoryDisplayedOnPatientUI_GW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.gw");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.gw");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.importDecisionTree(testData.getDecisionTreeName());
		manageDecisionTree.selectDecisionTree(testData.getDecisionTreeName());
		ManageGeneralInformation manageGeneralInformation = manageDecisionTree.goToGeneralInformation();
		manageGeneralInformation.setApptTypeDecisionTree(testData.getAppointmenttype());
		manageGeneralInformation.addReasonGeneralInfo(reasonForAppointment);
		manageGeneralInformation.publishGeneralInfo();
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		Provider provider = apptQuestion.selectAnswerFromQue(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletion(driver, adminUser, testData, testData.getDecisionTreeName());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void addMultipleFieldsInCategoryDisplayedOnPatientUING() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.ng");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.ng");
		String questionForAppointment = propertyData.getProperty("decision.tree.appointment.question.ng");
		adminUtils.addMultipleFieldsInDecisionTree(driver, adminUser, testData, testData.getDecisionTreeName(), testData.getAppointmenttype(), 
				reasonForAppointment,questionForAppointment, decisionTreeAnswer);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		Provider provider = apptQuestion.selectAnswerFromQue(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(testData.getProvider());
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletion(driver, adminUser, testData, testData.getDecisionTreeName());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void addMultipleFieldsInCategoryDisplayedOnPatientUIGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.ge");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.ge");
		String questionForAppointment = propertyData.getProperty("decision.tree.appointment.question.ge");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.addDecisionTree(testData.getDecisionTreeName());
		manageDecisionTree.selectDecisionTree(testData.getDecisionTreeName());
		ManageGeneralInformation manageGeneralInformation = manageDecisionTree.goToGeneralInformation();
		manageGeneralInformation.addQuestionInDecisionTree(questionForAppointment);
		manageGeneralInformation.addAnswerOneInDecisionTree(decisionTreeAnswer);
		manageGeneralInformation.setApptTypeDecisionTreeSet2(testData.getAppointmenttype());
		manageGeneralInformation.addReasonGeneralInfo(reasonForAppointment);
		manageGeneralInformation.publishGeneralInfo();
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		Provider provider = apptQuestion.selectAnswerFromQue(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletion(driver, adminUser, testData, testData.getDecisionTreeName());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void addMultipleFieldsInCategoryDisplayedOnPatientUIGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.gw");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.gw");
		String questionForAppointment = propertyData.getProperty("decision.tree.appointment.question.gw");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.addDecisionTree(testData.getDecisionTreeName());
		manageDecisionTree.selectDecisionTree(testData.getDecisionTreeName());
		ManageGeneralInformation manageGeneralInformation = manageDecisionTree.goToGeneralInformation();
		manageGeneralInformation.addQuestionInDecisionTree(questionForAppointment);
		manageGeneralInformation.addAnswerOneInDecisionTree(decisionTreeAnswer);
		manageGeneralInformation.setApptTypeDecisionTreeSet2(testData.getAppointmenttype());
		manageGeneralInformation.addReasonGeneralInfo(reasonForAppointment);
		manageGeneralInformation.publishGeneralInfo();
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		Provider provider = apptQuestion.selectAnswerFromQue(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletion(driver, adminUser, testData, testData.getDecisionTreeName());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void publishedCategoryWithProviderON_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.ng");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.ng");
		adminUtils.decisionTreeSettingsWithProviderON(driver, adminUser, testData, testData.getDecisionTreeName(), 
				testData.getAppointmenttype(), reasonForAppointment);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		Provider provider = apptQuestion.selectAnswerFromQue(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(testData.getProvider());
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletion(driver, adminUser, testData, testData.getDecisionTreeName());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void publishedCategoryWithSpecialty_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.ng");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.ng");
		adminUtils.decisionTreeSettingsWithSpecialty(driver, adminUser, testData, testData.getDecisionTreeName(), 
				testData.getAppointmenttype(), reasonForAppointment, testData.getSpeciality());
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Speciality speciality = homePage.skipInsuranceForSpeciality(driver);
		StartAppointmentInOrder startAppointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
		Provider provider = startAppointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation1(testData.getProvider());
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		AppointmentDateTime aptDateTime = apptQuestion.selectAnswerFromOptions(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletionWithSpeciality(driver, adminUser, testData, testData.getDecisionTreeName(), testData.getSpeciality());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void publishedCategoryWithSpecialty_GE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.ge");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.ge");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		adminUtils.setRulesNoSpecialitySet3(patientflow);
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.importDecisionTree(testData.getDecisionTreeName());
		manageDecisionTree.selectSpecility(testData.getSpeciality());
		manageDecisionTree.selectDecisionTree(testData.getDecisionTreeName());
		ManageGeneralInformation manageGeneralInformation = manageDecisionTree.goToGeneralInformation();
		manageGeneralInformation.setApptTypeDecisionTree(testData.getAppointmenttype());
		manageGeneralInformation.addReasonGeneralInfo(reasonForAppointment);
		manageGeneralInformation.publishGeneralInfo();
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Speciality speciality = homePage.skipInsuranceForSpeciality(driver);
		StartAppointmentInOrder startAppointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
		Provider provider = startAppointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation1(testData.getProvider());
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		AppointmentDateTime aptDateTime = apptQuestion.selectAnswerFromOptions(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletionWithSpeciality(driver, adminUser, testData, testData.getDecisionTreeName(), testData.getSpeciality());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void publishedCategoryWithSpecialty_GW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String reasonForAppointment = propertyData.getProperty("decision.tree.appointment.reason.ge");
		String decisionTreeAnswer = propertyData.getProperty("decision.tree.answer.ge");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		adminUtils.setRulesNoSpecialitySet3(patientflow);
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.importDecisionTree(testData.getDecisionTreeName());
		manageDecisionTree.selectSpecility(testData.getSpeciality());
		manageDecisionTree.selectDecisionTree(testData.getDecisionTreeName());
		ManageGeneralInformation manageGeneralInformation = manageDecisionTree.goToGeneralInformation();
		manageGeneralInformation.setApptTypeDecisionTree(testData.getAppointmenttype());
		manageGeneralInformation.addReasonGeneralInfo(reasonForAppointment);
		manageGeneralInformation.publishGeneralInfo();
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Speciality speciality = homePage.skipInsuranceForSpeciality(driver);
		StartAppointmentInOrder startAppointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
		Provider provider = startAppointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation1(testData.getProvider());
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getDecisionTreeName());
		AppointmentQuestionsPage apptQuestion = appointment.selectApptTypeDecisionTree(testData.getDecisionTreeName());
		AppointmentDateTime aptDateTime = apptQuestion.selectAnswerFromOptions(decisionTreeAnswer,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		if(aptDateTime.chooseAppttypeText(reasonForAppointment).equals(reasonForAppointment)) {
			Assert.assertTrue(true);
			log("Reason for appointment is matching");
		}else {
			Assert.assertTrue(false);
		}
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		logStep("Move to PSS admin Portal to login and then delete decision tree");
		adminUtils.decisionTreeDeletionWithSpeciality(driver, adminUser, testData, testData.getDecisionTreeName(), testData.getSpeciality());
	}
}