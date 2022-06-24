// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.decisionTree.ManageDecisionTree;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.ManageAppointmentType;
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
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
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
}
