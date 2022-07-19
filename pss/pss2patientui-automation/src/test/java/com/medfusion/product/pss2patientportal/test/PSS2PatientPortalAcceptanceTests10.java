// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.decisionTree.ManageDecisionTree;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.ManageAppointmentType;
import com.medfusion.product.object.maps.pss2.page.Location.ManageLocation;
import com.medfusion.product.object.maps.pss2.page.Resource.ManageResource;
import com.medfusion.product.object.maps.pss2.page.settings.AdminAppointment;
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

public class PSS2PatientPortalAcceptanceTests10 extends BaseTestNGWebDriver {
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyValidationMessageIsDisplayedWhenExportedAndEditedNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSNewPatient newPatient = new PSSNewPatient();
		String decisionTreeWithNullValues = propertyData.getProperty("decision.tree.displayNamesWithNull.ng");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		patientflow.turnOnProvider();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.importDecisionTreeWithNullValues(decisionTreeWithNullValues);
		
		String expValidationAlertMsg = propertyData.getProperty("decision.tree.alert.message.ng");
		String actValidationAlertMsg = manageDecisionTree.captureValidationAlertMessage();
		assertEquals(actValidationAlertMsg, expValidationAlertMsg, "Validation alert message is not matching with expected message");
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		newPatient.createPatientDetails(testData);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = homepage.skipInsurance(driver);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + decisionTreeWithNullValues);
		appointment.selectApptTypeDecisionTree(decisionTreeWithNullValues);
		if(appointment.verifyDecisionTreeApptTypePresent(decisionTreeWithNullValues) == false) {
			Assert.assertTrue(true);
			log("There is no decision tree found with "+ decisionTreeWithNullValues +" name");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyValidationMessageIsDisplayedWhenExportedAndEditedGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSNewPatient newPatient = new PSSNewPatient();
		String decisionTreeWithNullValues = propertyData.getProperty("decision.tree.displayNamesWithNull.ge");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.importDecisionTreeWithNullValues(decisionTreeWithNullValues);
		
		String expValidationAlertMsg = propertyData.getProperty("decision.tree.alert.message.ge");
		String actValidationAlertMsg = manageDecisionTree.captureValidationAlertMessage();
		assertEquals(actValidationAlertMsg, expValidationAlertMsg, "Validation alert message is not matching with expected message");
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		newPatient.createPatientDetails(testData);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = homepage.skipInsurance(driver);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + decisionTreeWithNullValues);
		appointment.selectApptTypeDecisionTree(decisionTreeWithNullValues);
		if(appointment.verifyDecisionTreeApptTypePresent(decisionTreeWithNullValues) == false) {
			Assert.assertTrue(true);
			log("There is no decision tree found with "+ decisionTreeWithNullValues +" name");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyValidationMessageIsDisplayedWhenExportedAndEditedGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSNewPatient newPatient = new PSSNewPatient();
		String decisionTreeWithNullValues = propertyData.getProperty("decision.tree.displayNamesWithNull.gw");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		Thread.sleep(2000);
		patientflow.enableDecisionTree();
		ManageDecisionTree manageDecisionTree = pssPracticeConfig.gotoDecisionTree();
		manageDecisionTree.importDecisionTreeWithNullValues(decisionTreeWithNullValues);
		
		String expValidationAlertMsg = propertyData.getProperty("decision.tree.alert.message.gw");
		String actValidationAlertMsg = manageDecisionTree.captureValidationAlertMessage();
		assertEquals(actValidationAlertMsg, expValidationAlertMsg, "Validation alert message is not matching with expected message");
		adminUtils.pageRefresh(driver);
		manageDecisionTree.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		newPatient.createPatientDetails(testData);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = homepage.skipInsurance(driver);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + decisionTreeWithNullValues);
		appointment.selectApptTypeDecisionTree(decisionTreeWithNullValues);
		if(appointment.verifyDecisionTreeApptTypePresent(decisionTreeWithNullValues) == false) {
			Assert.assertTrue(true);
			log("There is no decision tree found with "+ decisionTreeWithNullValues +" name");
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyApptSchedAndReschedForPreventRescheduleOnCancelSettingsNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		PSSNewPatient newPatient = new PSSNewPatient();
		String i = propertyData.getProperty("prevent.reschedule.cancel.days.ng");
		adminUtils.preventReschedOnCancelSettings(driver, adminUser, testData, testData.getAppointmenttype(), i);
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		newPatient.createPatientDetails(testData);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		patientUtils.bookAppointmentWithLTBFlow(homepage, testData, driver, testData.getProvider(), 
				testData.getAppointmenttype(), testData.getLocation());
		log("Cancelling an appointment");
		homepage.cancelAppointmentWithoutReasonEnabled();
		homepage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = homepage.skipInsurance(driver);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		String expPreventReschedOnCancelMsg ="The selected provider does not allow rescheduling or cancellation";
		String actPreventReschedOnCancelMsg = appointment.preventReschedOnCancelMsg(testData.getAppointmenttype());
	
		assertEquals(actPreventReschedOnCancelMsg, expPreventReschedOnCancelMsg, "Prevent Reschedule on cancel Error message is not matches with expected message");
		appointment.pressOkBtn();
		
		adminUtils.resetPreventReschedOnCancelSettings(driver, adminUser, testData, testData.getAppointmenttype());
	
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyApptSchedAndReschedForPreventRescheduleOnCancelSettingsGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		PSSNewPatient newPatient = new PSSNewPatient();
		String i = propertyData.getProperty("prevent.reschedule.cancel.days.ge");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		ManageAppointmentType manageAppointmentType = pssPracticeConfig.gotoAppointment();
		adminUtils.pageRefresh(driver);
		manageAppointmentType.selectAppointment(testData.getAppointmenttype());
		manageAppointmentType.prevReschedOnCancelSettings(testData.getAppointmenttype(), i);
		adminUtils.pageRefresh(driver);
		manageAppointmentType.logout();
		logStep("Move to PSS patient Portal 2.0 to login and then book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		newPatient.createPatientDetails(testData);
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), 
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(), 
				testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		patientUtils.bookAppointmentWithLTBFlow(homepage, testData, driver, testData.getProvider(), 
				testData.getAppointmenttype(), testData.getLocation());
		log("Cancelling an appointment");
		homepage.cancelAppointmentWithoutReasonEnabled();
		homepage.btnStartSchedClick();
		StartAppointmentInOrder startAppointmentInOrder = homepage.skipInsurance(driver);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		String expPreventReschedOnCancelMsg ="The selected provider does not allow rescheduling or cancellation";
		String actPreventReschedOnCancelMsg = appointment.preventReschedOnCancelMsg(testData.getAppointmenttype());
	
		assertEquals(actPreventReschedOnCancelMsg, expPreventReschedOnCancelMsg, "Prevent Reschedule on cancel Error message is not matches with expected message");
		appointment.pressOkBtn();
		
		adminUtils.resetPreventReschedOnCancelSettings(driver, adminUser, testData, testData.getAppointmenttype());
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifySlotSizeFunctionalityWithProviderOffNG() throws Exception {
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
		adminUtils.mergeSlotWithShowProviderOff(driver, adminUser, testData);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptType(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		testData.setFirstHour(aptDateTime.getFirstTimeWithHour());
		testData.setFirstMinute(aptDateTime.getFirstTimeWithMinute());
		String time = aptDateTime.getfirsttime();
		log("First Time is  " + time);
		String nextTime = aptDateTime.getNextTimeWithMinute();
		log("Next Time is " + nextTime);
		log("Time Add After  " + psspatientutils.addTimeinFixedTime(testData));
		assertEquals(aptDateTime.getNextTimeWithMinute(), psspatientutils.addTimeinFixedTime(testData));
	}
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyZipcodeSearchFunctionalityOnPatientUI_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		String zipCode = propertyData.getProperty("location.zip.code.ng");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		logStep("Login to PSS Admin portal");
		adminUtils.adminSettingsLocationSearchByZipcode(driver, adminUser, testData, testData.getLocation(), zipCode);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		location.searchLocationByZipCode(zipCode);
		if(location.chooseLocationText().contains(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location searched by Zip Code is matching");
		}else {
			Assert.assertTrue(false);
		}
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyZipcodeSearchFunctionalityOnPatientUI_GE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		String zipCode = propertyData.getProperty("location.zip.code.ge");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		AdminAppointment adminAppointment = pssPracticeConfig.gotoAdminAppointmentTab();
		adminAppointment.toggleSearchLocationClick();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageLocation manageLocation = pssPracticeConfig.gotoLocation();
		adminUtils.pageRefresh(driver);
		manageLocation.selectlocation(testData.getLocation());
		manageLocation.changeAddressZipCode(zipCode);
		manageLocation.logout();
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		location.searchLocationByZipCode(zipCode);
		if(location.chooseLocationText().contains(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location searched by Zip Code is matching");
		}else {
			Assert.assertTrue(false);
		}
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void verifyZipcodeSearchFunctionalityOnPatientUI_GW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		String zipCode = propertyData.getProperty("location.zip.code.gw");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		AdminAppointment adminAppointment = pssPracticeConfig.gotoAdminAppointmentTab();
		adminAppointment.toggleSearchLocationClick();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageLocation manageLocation = pssPracticeConfig.gotoLocation();
		adminUtils.pageRefresh(driver);
		manageLocation.selectlocation(testData.getLocation());
		manageLocation.changeAddressZipCode(zipCode);
		manageLocation.logout();
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		location.searchLocationByZipCode(zipCode);
		if(location.chooseLocationText().contains(testData.getLocation())) {
			Assert.assertTrue(true);
			log("Location searched by Zip Code is matching");
		}else {
			Assert.assertTrue(false);
		}
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamWhenSharePatientIsOnWithoutSpecialty_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("ct.book.name.ng");
		String providerName1 = propertyData.getProperty("ct.book.name1.ng");
		String appointmentTypeName = propertyData.getProperty("ct.appointment.type.ng");
		logStep("Login to PSS Admin portal");
		adminUtils.enableCareTeamWithSharePatientONSettings(driver, adminUser, testData, providerName, providerName1);
		
		String fn = propertyData.getProperty("ct.fn.pm10.ng");
		String ln = propertyData.getProperty("ct.ln.pm10.ng");
		String dob = propertyData.getProperty("ct.dob.pm10.ng");
		String email = propertyData.getProperty("ct.email.pm10.ng");
		String gender = propertyData.getProperty("ct.gender.pm10.ng");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.ng");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.ng");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointment.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider1 = " + providerName1);
		ArrayList<String> l2= new ArrayList<String>();
		l2=provider.getBookList();	
		log("List of book from Patient UI- " + l2);
		assertFalse(l2.contains(providerName1));
		assertTrue(l2.contains(providerName));
		logStep("Verify Provider Page and PCP = " + providerName);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamWhenSharePatientIsOnWithoutSpecialty_GE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("ct.book.name.ge");
		String providerName1 = propertyData.getProperty("ct.book.name1.ge");
		String appointmentTypeName = propertyData.getProperty("ct.appointment.type.ge");
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		AdminAppointment adminAppointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + adminAppointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(adminAppointment.toggleAllowPCPONOF());
		Log4jUtil.log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			Log4jUtil.log("Status of PCP  OFF");
			adminAppointment.pcptoggleclick();
			Log4jUtil.log("Status of PCP  OFF and Clicked on ON");
		} else {
			Log4jUtil.log("Status of PCP is Already ON");
		}
		adminAppointment.selectPrimaryCareProvider();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		manageResource.selectResource(providerName);
		manageResource.disableSharePatient();
		manageResource.clearSearchResource();
		manageResource.selectResource(providerName1);
		manageResource.disableSharePatient();
		adminUtils.pageRefresh(driver);
		manageResource.logout();
		
		String fn = propertyData.getProperty("ct.fn.pm10.ge");
		String ln = propertyData.getProperty("ct.ln.pm10.ge");
		String dob = propertyData.getProperty("ct.dob.pm10.ge");
		String email = propertyData.getProperty("ct.email.pm10.ge");
		String gender = propertyData.getProperty("ct.gender.pm10.ge");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.ge");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.ge");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointment.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider1 = " + providerName1);
		ArrayList<String> l2= new ArrayList<String>();
		l2=provider.getBookList();	
		log("List of book from Patient UI- " + l2);
		assertFalse(l2.contains(providerName1));
		assertTrue(l2.contains(providerName));
		logStep("Verify Provider Page and PCP = " + providerName);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamWhenSharePatientIsOnWithoutSpecialty_GW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("ct.book.name.gw");
		String providerName1 = propertyData.getProperty("ct.book.name1.gw");
		String appointmentTypeName = propertyData.getProperty("ct.appointment.type.gw");
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		AdminAppointment adminAppointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + adminAppointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(adminAppointment.toggleAllowPCPONOF());
		Log4jUtil.log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			Log4jUtil.log("Status of PCP  OFF");
			adminAppointment.pcptoggleclick();
			Log4jUtil.log("Status of PCP  OFF and Clicked on ON");
		} else {
			Log4jUtil.log("Status of PCP is Already ON");
		}
		adminAppointment.selectPrimaryCareProvider();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		manageResource.selectResource(providerName);
		manageResource.disableSharePatient();
		manageResource.clearSearchResource();
		manageResource.selectResource(providerName1);
		manageResource.disableSharePatient();
		adminUtils.pageRefresh(driver);
		manageResource.logout();
		
		String fn = propertyData.getProperty("ct.fn.pm10.gw");
		String ln = propertyData.getProperty("ct.ln.pm10.gw");
		String dob = propertyData.getProperty("ct.dob.pm10.gw");
		String email = propertyData.getProperty("ct.email.pm10.gw");
		String gender = propertyData.getProperty("ct.gender.pm10.gw");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.gw");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.gw");
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointment.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verify Provider Page and Provider1 = " + providerName1);
		ArrayList<String> l2= new ArrayList<String>();
		l2=provider.getBookList();	
		log("List of book from Patient UI- " + l2);
		assertFalse(l2.contains(providerName1));
		assertTrue(l2.contains(providerName));
		logStep("Verify Provider Page and PCP = " + providerName);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	}
}
