// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import java.util.ArrayList;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
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
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSNewPatient;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PSS2PatientPortalAcceptanceTests extends BaseTestNGWebDriver {

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForExistingPatientGW() throws Exception {

		log("E2E test to verify loginless appointment for a Existing patient for GW");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Step 1: Set the Test Data for GW ADMIN & APPOINTMENT-------");
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);

		testData.setFutureApt(true);

		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		log("--------Admin Setting for Loginless Flow Starts----------");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);

		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGW() throws Exception {
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

		testData.setFutureApt(true);

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGE() throws Exception {
		log("E2E test to verify loginless appointment for a New patient for GE");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		log("-----Set the Test Data for GE ADMIN-------");
		propertyData.setAdminGE(adminuser);
		log("----------Set the Test Data for GE APPOINTMENT----------");
		propertyData.setAppointmentResponseGE(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());

		testData.setFutureApt(true);

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("--------Admin Setting for Loginless Flow Starts----------");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);

		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientAT() throws Exception {
		log("-----------------------Athena----------------------");
		log("E2E test to verify loginless appointment for a New patient for ATHENA");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		log("-----Set the Test Data for ATHENA ADMIN-----------");
		propertyData.setAdminAT(adminuser);
		log("-----Set the Test Data for ATHENA APPOINTMENT------");
		propertyData.setAppointmentResponseAT(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());

		testData.setFutureApt(true);

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("-------Admin Settings for Loginless Flow Starts-------");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
		log("insuranceSelected--> OFF");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}

		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientNG() throws Exception {

		log("-----------------------NextGen----------------------");
		log("E2E test to verify loginless appointment for a New patient for NG");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		log("Set the Test Data for NG ADMIN");
		propertyData.setAdminNG(adminuser);
		log("Set the Test Data for GE APPOINTMENT");
		propertyData.setAppointmentResponseNG(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());

		testData.setFutureApt(true);

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.skipInsurance(driver);
		Provider provider = homepage.selectStartPoint(PSSConstants.START_PROVIDER);
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), false);
		AppointmentDateTime appointmentdatetime = location.selectDatTime(testData.getLocation());
		appointmentdatetime.selectDate(testData.getIsNextDayBooking());
		psspatientutils.bookAppointment(false, appointmentdatetime, testData, driver);
	}

	@Parameters({"partnerType"})
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		homepage.skipInsurance(driver);
		Provider provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), false);
		AppointmentDateTime appointmentdatetime = location.selectDatTime(testData.getLocation());
		appointmentdatetime.selectDate(testData.getIsNextDayBooking());
		psspatientutils.bookAppointment(false, appointmentdatetime, testData, driver);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowGE() throws Exception {
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(), patient.getPassword(), testDataPFL);
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		homePage.clickFeaturedAppointmentsReq();
		assertEquals(homePage.appointmentNotScheduled(), "Online Scheduling Unavailable");
		homePage.closeModalPopUp();
		log("Logging out..");
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(), patient.getPassword(), testDataPFL);
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		homePage.clickFeaturedAppointmentsReq();
		assertEquals(homePage.appointmentNotScheduled(), "Online Scheduling Unavailable");
		homePage.closeModalPopUp();
		log("Logging out..");
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForExistingPatientGE() throws Exception {

		log("E2E test to verify loginless appointment for a Existing patient for GE");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();

		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		log("Step 1: Set the Test Data for GE ADMIN & APPOINTMENT-------");
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);

		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		log("--------Admin Setting for Loginless Flow Starts----------");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);

		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForExistingPatientNG() throws Exception {

		log("E2E test to verify loginless appointment for a Existing patient for NG");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();

		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		log("Step 1: Set the Test Data for NG ADMIN & APPOINTMENT-------");
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);

		testData.setFutureApt(true);

		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		log("--------Admin Setting for Loginless Flow Starts----------");

		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);

		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForExistingPatientAT() throws Exception {

		log("E2E test to verify loginless appointment for a Existing patient for AT");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		log("Step 1: Set the Test Data for AT ADMIN & APPOINTMENT-------");
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);

		testData.setFutureApt(true);

		log(testData.getUrlLoginLess());
		log("Appointment Type- " + testData.getAppointmenttype());
		log("Location- " + testData.getLocation());
		log("Provider Name- " + testData.getProvider());

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		log("--------Admin Setting for Loginless Flow Starts----------");
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule- " + rule);

		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		log("Step 4: Login to PSS Appointment");
		log("Loginless url is " + testData.getUrlLoginLess());

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
		log("Zip Code- " + testData.getZipCode());
		Thread.sleep(3000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);

	}

	@DataProvider(name = "partnerType")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] {{"GW"}, {"GE"}, {"NG"}, {"AT"}};
		return obj;
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessForNewPatient(String partnerPractice) throws Exception {
		log("Test To Verify if New Patient is able to access PSS2.0 Patient UI via Loginless flow for " + partnerPractice);
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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());
		log("Step 3: Select New Patient");
		LoginlessPatientInformation loginlesspatientinformation = onlineappointmentschedulingPage.selectNewPatientLoginLess();
		log("Step 4: Fill Patient criteria Form");
		Thread.sleep(9000);
		Boolean insuranceSelected = false;
		HomePage homepage;
		if (insuranceSelected) {
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlesspatientinformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			homepage = loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
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
		assertTrue(homepage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 3: Set the Patient criteria");
		Boolean isPrivacyPageEnabled = false;
		HomePage homepage = null;
		if (isPrivacyPageEnabled) {
			log("Privacy Enabled");
			PrivacyPolicy privacypolicy = existingpatient.loginPatient(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode());
			homepage = privacypolicy.submitPrivacyPage();

		} else {
			log("Privacy Disabled");
			homepage = existingpatient.login(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
					testData.getZipCode());
		}
		Thread.sleep(7000);
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		log("Step 4: Verify if homepage is loaded");
		assertTrue(homepage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());
		log("Step 3: Select New Patient");
		LoginlessPatientInformation loginlesspatientinformation = onlineappointmentschedulingPage.selectNewPatientLoginLess();
		log("Step 4: Fill Patient Details");
		loginlesspatientinformation.isPageLoaded();
		loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		Thread.sleep(15000);
		log("Step 5: Close the PopUp");
		loginlesspatientinformation.closePopUp();
		Thread.sleep(5000);
		log("Step 6: Verify if online appointment scheduling Page is loaded.");
		assertTrue(onlineappointmentschedulingPage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginWithWrongPatientDetails(String partnerPractice) throws Exception {
		log("Test Case to Verify apporpriate error message is displayed when Existing patient details are used while creating new patient for " + partnerPractice);
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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());
		log("Step 3: Select Existing patient from the list");
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 4: Set the Patient criteria");
		existingpatient.login(PSSConstants.NON_EXISTENCE_FIRSTNAME, testData.getLastName(), testData.getDob(), PSSConstants.NON_EXISTENCE_PATIENT_EMAIL,
				testData.getGender(), testData.getZipCode());
		Thread.sleep(3500);
		existingpatient.dismissPopUp();
		log("Step 5: Verify if online appointment scheduling Page is loaded.");
		assertTrue(onlineappointmentschedulingPage.areBasicPageElementsPresent());
	}


	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)

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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
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

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		Thread.sleep(9000);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.check_Provider_Apt_Loc_List(driver, homepage, testData);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)

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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getUnderAgePatientUserName() + " and password=" + testData.getUnderAgePatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getUnderAgePatientUserName(), testData.getUnderAgePatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		Thread.sleep(9000);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.ageRule(driver, homepage, testData, true, rule);
	}


	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)

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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		Thread.sleep(9000);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.ageRule(driver, homepage, testData, false, rule);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)

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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		Thread.sleep(9000);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.verifyProviderAssociation(driver, homepage, testData, rule);
	}


	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)

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
			OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
			log("Select Existing patient button");
			ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
			log("Step : Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
			HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
			log("Step : Verify if home page is loaded.");
			homepage.areBasicPageElementsPresent();
			Thread.sleep(9000);
			PSSPatientUtils psspatientutils = new PSSPatientUtils();
			psspatientutils.closeHomePagePopUp(homepage);
			log("Logout from PSS 2.0 patient UI");
			homepage.logout();
			Thread.sleep(9000);
			adminUtils.setRuleWithoutSpeciality(driver, adminuser);
		}
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();

		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
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

			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.btnStartSchedClick();
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();
		String popupmsg =
				"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
		String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

		homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGW")
	public void testCancelFromEmailGW() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		propertyData.setAppointmentResponseGW(testData);
		propertyData.setAdminGW(adminuser);

		pssadminutils.getInsuranceStateandRule(driver, adminuser, testData);
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
		log("Step 6: Book an Appointment to test the Cancel Appointment from Email");
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);
		Thread.sleep(2000);
		String popupmsg =
				"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
		String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		log("Step 9: Click on Cancel/Reschedule link from email");
		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());

		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		homepage = cancelRescheduleDecisionPage.clickCancel();

		homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGE")
	public void testCancelFromEmailGE() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("GE", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(2000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientAT")
	public void testCancelFromEmailAT() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(2000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2ELoginlessForExistingPatientNG")
	public void testCancelFromEmailNG() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(2000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGE")
	public void testCancelFromMalinatorGE() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("GE", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		PatientIdentificationPage patientIdentificationPage = psspatientutils.newtabs(driver, "https://www.mailinator.com/", testData.getEmail());


		log(" ---This is cancel link");
		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReason(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGW")
	public void testCancelFromMalinatorGW() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("GW", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		PatientIdentificationPage patientIdentificationPage = psspatientutils.newtabs(driver, "https://www.mailinator.com/", testData.getEmail());

		log(" ---This is cancel link");
		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReason(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteEmail() throws Exception {

		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		psspatientutils.setTestData("GE", testData, adminuser);

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelE2ETestingGE() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		SoftAssert softAssertion= new SoftAssert();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("GE", testData, adminuser);	

		log("Step 2: Check the cancel reschedule settings in Admin UI");
		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		log("Step 3: Get the rule details from admin");
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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();

		log("Step 6: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");			
		softAssertion.assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");		
		
		if (can1 == true & can2 == false) {

			homepage.cancelAppointment("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReason(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelE2ETestingNG() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		SoftAssert softAssertion= new SoftAssert();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

		log("Step 2: Check the admin settings for cancel an appointment");
		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();		

		log("Step 3: Get the rule details");
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

		log("Step 6: Login to Patient Portal and book an future appointment****");		
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();

		log("Step 7: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");		
		softAssertion.assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");			

		log("Step 8: Now cancel an appointent as per the settings in admin UI");
		if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReason(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelE2ETestingAT() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		SoftAssert softAssertion= new SoftAssert();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		log("Step 2: Check the admin settings for cancel");
		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();

		log("Step 3: Get the rule details");
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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.btnStartSchedClick();
		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);

		homepage = scheduledAppointment.backtoHomePage();
		
		log("Step 6: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		softAssertion.assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");

		if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReason(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointment(popupmsg, confirmCancelmsg);
		}

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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

		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());

		homepage.btnStartSchedClick();

		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);
		homepage = scheduledAppointment.backtoHomePage();

		homepage.clickRescheduleLink();
		psspatientutils.rescheduleAPT(testData, driver);		

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		Thread.sleep(200);

		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());

		homepage.btnStartSchedClick();

		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);
		homepage = scheduledAppointment.backtoHomePage();

		homepage.clickRescheduleLink();
		psspatientutils.rescheduleAPT(testData, driver);		

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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

		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());

		homepage.btnStartSchedClick();

		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);
		homepage = scheduledAppointment.backtoHomePage();

		homepage.clickRescheduleLink();
		psspatientutils.rescheduleAPT(testData, driver);

		
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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

		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());

		homepage.btnStartSchedClick();

		ScheduledAppointment scheduledAppointment = psspatientutils.selectAFlow(driver, rule, homepage, testData);
		homepage = scheduledAppointment.backtoHomePage();

		homepage.clickRescheduleLink();
		psspatientutils.rescheduleAPT(testData, driver);

		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGE")
	public void testRescheduleviaEmailNotifiicationGE() throws Exception {

		log("Test to verify if Reschedule an Appointment via Email Notification");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("GE", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("adminCancelReasonList- " + adminCancelReasonList);
		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(2000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT(testData, driver);
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientAT")
	public void testRescheduleviaEmailNotifiicationAT() throws Exception {

		log("Test to verify if Reschedule an Appointment via Email Notification");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("adminCancelReasonList- " + adminCancelReasonList);
		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(2000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT(testData, driver);
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());

	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientNG")
	public void testRescheduleviaEmailNotifiicationNG() throws Exception {

		log("Test to verify if Reschedule an Appointment via Email Notification");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

		testData.setFutureApt(true);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("adminCancelReasonList- " + adminCancelReasonList);
		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(2000);

		log("Step 5: LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		Thread.sleep(1000);

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());

		homepage.patientLogout(driver);

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT(testData, driver);
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods = "testE2ELoginlessForExistingPatientGW")
	public void testRescheduleviaEmailNotifiicationGW() throws Exception {

		log("Test to verify if Reschedule an Appointment via Email Notification");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		psspatientutils.setTestData("GW", testData, adminuser);
		testData.setFutureApt(true);

		pssadminutils.getInsuranceStateandRule(driver, adminuser, testData);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(1500);
		if (patientIdentificationPage.isPopUP()) {
			patientIdentificationPage.popUPClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT(testData, driver);
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getEmail());

	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
		adminappointment.blockPatientsAsPerMonth(PSSConstants.MAX_BLOCK_MONTHS);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getOldPatientUserName() + " and password=" + testData.getOldPatientPassword());
		existingpatient.patientSignIn(testData.getPatientUserName(), testData.getPatientPassword());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
		adminappointment.slotcountToBeDisplayed(PSSConstants.DISPLAY_SLOTS_COUNT);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		log("Step 5: Verify Future appointment type for existence");
		assertTrue(homepage.getFutureAppointmentListSize() > 0, "No Future Appointment found.");
		log("Step 6: Verify Past appointment type for existence");
		assertTrue(homepage.getPastAppointmentListSize() > 0, "No Past Appointment found.");
		log("rule from admin=" + rule);
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
		log("Display slots count set in Admin =" + PSSConstants.DISPLAY_SLOTS_COUNT + " = display slot count in pss2.0 patient UI="
				+ testData.getDisplaySlotCountLength());
		assertEquals(Integer.parseInt(PSSConstants.DISPLAY_SLOTS_COUNT), testData.getDisplaySlotCountLength());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
		adminappointment.maxAppointments(PSSConstants.MAX_SLOTS_MONTHS);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
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

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
		adminappointment.maxSlotMonthsToBeDisplayed(PSSConstants.MAX_SLOTS_MONTHS);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
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

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
		adminappointment.majorAgeToSet(PSSConstants.MAJOR_AGE);
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
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

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
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
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		SelectProfilePage selectProfilePage = existingpatient.patientSignIn(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		HomePage homepage = selectProfilePage.selectProfile();
		homepage.areBasicPageElementsPresent();
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

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
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
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		SelectProfilePage selectProfilePage = existingpatient.patientSignIn(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		HomePage homepage = selectProfilePage.selectProfile();
		homepage.areBasicPageElementsPresent();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();

		log("Step 3: Clicking to Appointment tab.");
		adminappointment.areBasicPageElementsPresent();

		log("Step 4: Checking the Enable care Team and Force Care Team is ON/OFF and set configuration accordingly.");
		adminappointment.toggleAllowPCP();
		adminappointment.pCPAvailabilityDuration(PSSConstants.PCP_AVAILABILITY_DURATION_GE);
		adminappointment.forceCareteamDuration(PSSConstants.FCT_AVAILABILITY_DURATION_GE);
		adminappointment.chooseRCPorPCP(PSSConstants.selectRCPorPCP_GE);

		log("Step 5: Fetching the rule and insurance.");
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		testData.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		log("Insurance Status = " + patientflow.insuracetogglestatus());
		testData.setIsstartpointPresent(patientflow.isstartpagepresent());
		log("Startpoint  Status = " + patientflow.isstartpagepresent());
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 6: PSS patient Portal 2.0 to book an Appointment - " + testData.getPatientPortalURL());
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());

		log("Step 7: Opening the Patient Portal URL and Logging in : ");
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());

		log("Step 8: Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		assertTrue(homepage.areBasicPageElementsPresent());

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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		AdminAppointment adminappointment = pss2practiceconfig.gotoAdminAppointmentTab();

		log("Step 3: Clicking to Appointment tab.");
		adminappointment.areBasicPageElementsPresent();

		log("Step 4: Checking the Enable care Team and Force Care Team is ON/OFF and set configuration accordingly.");
		adminappointment.toggleAllowPCP();
		adminappointment.pCPAvailabilityDuration(PSSConstants.PCP_AVAILABILITY_DURATION_GW);
		adminappointment.forceCareteamDuration(PSSConstants.FCT_AVAILABILITY_DURATION_GW);
		adminappointment.chooseRCPorPCP(PSSConstants.selectRCPorPCP_GW);

		log("Step 5: Fetching the rule and insurance.");
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		testData.setIsinsuranceVisible(patientflow.insuracetogglestatus());
		log("Insurance Status = " + patientflow.insuracetogglestatus());
		testData.setIsstartpointPresent(patientflow.isstartpagepresent());
		log("Startpoint  Status = " + patientflow.isstartpagepresent());
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		log("Step 6: PSS patient Portal 2.0 to book an Appointment - " + testData.getPatientPortalURL());
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());

		log("Step 7: Opening the Patient Portal URL and Logging in : ");
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());

		log("Step 8: Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		assertTrue(homepage.areBasicPageElementsPresent());

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

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
		adminappointment.toggleSearchLocation();
		Thread.sleep(4000);
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
		String rule = patientflow.getRule();
		adminappointment.logout();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 2: PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
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

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		adminappointment.areBasicPageElementsPresent();
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
		log("Step 3: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		SelectProfilePage selectProfilePage = existingpatient.patientSignIn(testData.getPatientUserName(), testData.getPatientPassword());
		Thread.sleep(3000);
		HomePage homepage = selectProfilePage.selectProfile();
		log("Step 4: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		log("Step 5 : rule from admin=" + rule);
		log("Step 6 : dont set Is Next Day Booking ");
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule.");
		testData.setIsCancelApt(false);
		testData.setIsNextDayBooking(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Select Existing patient button");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 4: Fill Existing Patient username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		HomePage homepage = existingpatient.patientSignIn1(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 5: Verify if home page is loaded.");
		homepage.areBasicPageElementsPresent();
		log("Step 6 : dont set Is Next Day Booking ");
		testData.setIsNextDayBooking(false);
		log("Step 7: Select the flow as per the rule." + rule);
		testData.setIsCancelApt(false);
		testData.setIsNextDayBooking(false);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowNG() throws Exception {
		Thread.sleep(12000);
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeGW() throws Exception {

		log("Test To Verify Lead Time Functionality");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(15000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());

		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		Log4jUtil.log("date- " + date);
		log("Next date is    " + psspatientutils.numDate(testData));
		if (psspatientutils.timeDifferenceendTime(testData) < 0) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData), "Slots are Not Avaliable for current date");
		}
		log("Done Confirmation");
		log("Appointment first time is   " + aptDateTime.getfirsttime());
		Thread.sleep(6000);
		ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
		confirmationpage.dateConfirm();
		log("Current plus Lead Date is " + psspatientutils.currentDateandLeadDay(testData));
		log("Confirmation Date    " + confirmationpage.dateConfirm());
		assertEquals(psspatientutils.currentDateandLeadDay(testData), confirmationpage.dateConfirm());
		log("Current Timezone On AdminUi " + testData.getCurrentTimeZone());
		log("Confirmation Time is " + confirmationpage.timeConfirm());
		log("Is Confirmation time is Valid   " + psspatientutils.isValidTime(confirmationpage.timeConfirm()));
		log("Current time and lead time is  " + psspatientutils.leadTime(testData));
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeGE() throws Exception {

		log("Test To Verify Lead Time Functionality For GE Partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(2000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		Log4jUtil.log("date- " + date);
		log("Next date is    " + psspatientutils.numDate(testData));
		if (psspatientutils.timeDifferenceendTime(testData) < 0) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData), "Slots are Not Avaliable for current date");
		}
		log("Done Confirmation");
		Thread.sleep(6000);
		ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
		confirmationpage.dateConfirm();
		log("Current plus Lead Date is " + psspatientutils.currentDateandLeadDay(testData));
		log("Confirmation Date    " + confirmationpage.dateConfirm());
		assertEquals(psspatientutils.currentDateandLeadDay(testData), confirmationpage.dateConfirm());
		log("Current Timezone On AdminUi " + testData.getCurrentTimeZone());
		log("Confirmation Time is " + confirmationpage.timeConfirm());
		log("Is Confirmation time is Valid   " + psspatientutils.isValidTime(confirmationpage.timeConfirm()));
		log("Current time and lead time is  " + psspatientutils.leadTime(testData));
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeAT() throws Exception {

		log("Test To Verify Lead Time Functionality");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(15000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());

		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		Log4jUtil.log("date- " + date);
		log("Next date is    " + psspatientutils.numDate(testData));
		if (psspatientutils.timeDifferenceendTime(testData) < 0) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData), "Slots are Not Avaliable for current date");
		}
		log("Done Confirmation");
		log("Appointment first time is   " + aptDateTime.getfirsttime());
		Thread.sleep(6000);
		ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
		confirmationpage.dateConfirm();
		log("Current plus Lead Date is " + psspatientutils.currentDateandLeadDay(testData));
		log("Confirmation Date    " + confirmationpage.dateConfirm());
		assertEquals(psspatientutils.currentDateandLeadDay(testData), confirmationpage.dateConfirm());
		log("Current Timezone On AdminUi " + testData.getCurrentTimeZone());
		log("Confirmation Time is " + confirmationpage.timeConfirm());
		log("Is Confirmation time is Valid   " + psspatientutils.isValidTime(confirmationpage.timeConfirm()));
		log("Current time and lead time is  " + psspatientutils.leadTime(testData));
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeNG() throws Exception {

		log("Test To Verify Lead Time Functionality");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(15000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());

		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		Log4jUtil.log("date- " + date);
		log("Next date is    " + psspatientutils.numDate(testData));
		if (psspatientutils.timeDifferenceendTime(testData) < 0) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {

			assertEquals(date, psspatientutils.currentESTDate(testData), "Slots are Not Avaliable for current date");
		}
		log("Done Confirmation");
		log("Appointment first time is   " + aptDateTime.getfirsttime());
		Thread.sleep(6000);
		ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
		confirmationpage.dateConfirm();
		log("Current plus Lead Date is " + psspatientutils.currentDateandLeadDay(testData));
		log("Confirmation Date    " + confirmationpage.dateConfirm());
		assertEquals(psspatientutils.currentDateandLeadDay(testData), confirmationpage.dateConfirm());
		log("Current Timezone On AdminUi " + testData.getCurrentTimeZone());
		log("Confirmation Time is " + confirmationpage.timeConfirm());
		log("Is Confirmation time is Valid   " + psspatientutils.isValidTime(confirmationpage.timeConfirm()));
		log("Current time and lead time is  " + psspatientutils.leadTime(testData));
	}



	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(15000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());

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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
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
			Log4jUtil.log("going to choose an provider");
			assertTrue(provider.areBasicPageElementsPresent());
			Log4jUtil.log("basic element are present");
			AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
			Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
			assertTrue(appointmentpage.areBasicPageElementsPresent());
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
		Log4jUtil.log("going to choose an provider");
		assertTrue(provider.areBasicPageElementsPresent());
		Log4jUtil.log("basic element are present");
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointmentpage.areBasicPageElementsPresent());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDateforMaxPDay(testData.getIsNextDayBooking());
		Thread.sleep(6000);
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
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
			Log4jUtil.log("going to choose an provider");
			assertTrue(provider.areBasicPageElementsPresent());
			Log4jUtil.log("basic element are present");
			AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
			Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
			assertTrue(appointmentpage.areBasicPageElementsPresent());
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
		Log4jUtil.log("going to choose an provider");
		assertTrue(provider.areBasicPageElementsPresent());
		Log4jUtil.log("basic element are present");
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointmentpage.areBasicPageElementsPresent());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDateforMaxPDay(testData.getIsNextDayBooking());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
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
			Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
			Log4jUtil.log("going to choose an provider");
			assertTrue(provider.areBasicPageElementsPresent());
			Log4jUtil.log("basic element are present");
			AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
			Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
			assertTrue(appointmentpage.areBasicPageElementsPresent());
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
		Log4jUtil.log("going to choose an provider");
		assertTrue(provider.areBasicPageElementsPresent());
		Log4jUtil.log("basic element are present");
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointmentpage.areBasicPageElementsPresent());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDateforMaxPDay(testData.getIsNextDayBooking());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayGW() throws Exception {
		log("Test To Verify Accept For same day Functionality For GW Partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.acceptforsameDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(3000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		log("Selected Date is   " + date);
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayGE() throws Exception {
		log("Test To Verify Accept For same day Functionality For GE Partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.acceptforsameDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(3000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayAT() throws Exception {
		log("Test To Verify Accept For same day Functionality For AT Partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.acceptforsameDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
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
		log("Verify PSS2 patient portal elements");
		assertTrue(homepage.areBasicPageElementsPresent());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(3000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAcceptSameDayNG() throws Exception {
		log("Test To Verify Accept For same day Functionality For NG Partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Login to PSS 2.0 Admin portal");
		adminUtils.acceptforsameDay(driver, adminuser, testData);
		log("Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");

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

		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(3000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		if (testData.isAccepttoggleStatus() == false) {
			assertEquals(date, psspatientutils.numDate(testData));
		} else {
			assertEquals(date, psspatientutils.currentESTDate(testData));
		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenerationGW() throws Exception {
		log("Link Generation with location and Provider for GW");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		log("-----Loaded the test data for New Patient----------");
		pssNewPatient.createPatientDetails(testData);
		log(testData.getUrlLoginLess());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettingLinkGeneration(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		homepage.btnStartSchedClick();
		AppointmentPage appointment = homepage.skipInsurancepage(driver);
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());

		AppointmentDateTime aptDateTime = appointment.selectAppointmentandClick(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		aptDateTime.selectDate(testData.getIsNextDayBooking());

		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);

		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenerationGE() throws Exception {
		log("Link Generation with location and Provider for GE");
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
		adminUtils.adminSettingLinkGeneration(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		homepage.btnStartSchedClick();
		AppointmentPage appointment = homepage.skipInsurancepage(driver);
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());

		AppointmentDateTime aptDateTime = appointment.selectAppointmentandClick(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		aptDateTime.selectDate(testData.getIsNextDayBooking());

		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);

		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenerationAT() throws Exception {
		log("Link Generation with location and Provider for AT");
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
		adminUtils.adminSettingLinkGeneration(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		homepage.btnStartSchedClick();
		AppointmentPage appointment = homepage.skipInsurancepage(driver);
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());

		AppointmentDateTime aptDateTime = appointment.selectAppointmentandClick(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		aptDateTime.selectDate(testData.getIsNextDayBooking());

		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);

		Log4jUtil.log("Test Case Passed");
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELinkGenerationNG() throws Exception {
		log("Link Generation with location and Provider for NG");
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
		adminUtils.adminSettingLinkGeneration(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		log("Link GEneration link is   " + testData.getUrlLinkGen());
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLinkGen());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());
		log("Gender- " + testData.getGender());
		log("Email- " + testData.getEmail());
		log("Phone Number- " + testData.getPrimaryNumber());
		log("Date Of Birth- " + testData.getDob());
		Thread.sleep(3000);
		Boolean insuranceSelected = adminuser.getIsInsuranceDisplayed();
		log("insuranceSelected--> " + insuranceSelected);
		HomePage homepage;
		insuranceSelected = false;
		log("insuranceSelected--> " + insuranceSelected);
		if (insuranceSelected) {
			log("insuranceSelected--> ON");
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlessPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			homepage = newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			log("insuranceSelected--> OFF");
			homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		homepage.btnStartSchedClick();
		AppointmentPage appointment = homepage.skipInsurancepage(driver);
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());

		AppointmentDateTime aptDateTime = appointment.selectAppointmentandClick(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		aptDateTime.selectDate(testData.getIsNextDayBooking());

		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);

		Log4jUtil.log("Test Case Passed");
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
			AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
			assertTrue(aptDateTime.areBasicPageElementsPresent());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		} else {
			Log4jUtil.log("No Provider avaliable Beacuase age Rule");
		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
			Thread.sleep(15000);
			AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
			assertTrue(aptDateTime.areBasicPageElementsPresent());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		} else {
			Log4jUtil.log("No Provider avaliable Beacuase age Rule");
		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
			Thread.sleep(15000);
			AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
			assertTrue(aptDateTime.areBasicPageElementsPresent());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		} else {
			Log4jUtil.log("No Provider avaliable Beacuase age Rule");
		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
			Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
			Thread.sleep(15000);
			AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
			assertTrue(aptDateTime.areBasicPageElementsPresent());
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		} else {
			Log4jUtil.log("No Provider avaliable Beacuase age Rule");
		}
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		HomePage homepage =
				loginlessPatientInformation.fillNewPatientForm(testData.getFirstNameCarePatient(), testData.getLastNameCarePatient(), testData.getDobCarePatient(),
						testData.getEmailCarePatient(), testData.getGenderCarePatient(), testData.getZipCarePatient(), testData.getPhoneCarePatient());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(2000);
		provider.getProvider(testData.getCareProvider());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		HomePage homepage =
				loginlessPatientInformation.fillNewPatientForm(testData.getFirstNameCarePatient(), testData.getLastNameCarePatient(), testData.getDobCarePatient(),
						testData.getEmailCarePatient(), testData.getGenderCarePatient(), testData.getZipCarePatient(), testData.getPhoneCarePatient());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(2000);
		provider.getProvider(testData.getCareProvider());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		HomePage homepage =
				loginlessPatientInformation.fillNewPatientForm(testData.getFirstNameCarePatient(), testData.getLastNameCarePatient(), testData.getDobCarePatient(),
						testData.getEmailCarePatient(), testData.getGenderCarePatient(), testData.getZipCarePatient(), testData.getPhoneCarePatient());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(2000);
		provider.getProvider(testData.getCareProvider());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderRule() throws Exception {
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
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		speciality.selectSpeciality1(testData.getSpeciality());
		Log4jUtil.log("Specility Name is....." + speciality.selectSpeciality1(testData.getSpeciality()));
		assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
		Log4jUtil.log("Test Case Passed.....");
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testageRulewithSpeciality(String partnerPractice) throws Exception {
		log(" VeriFy Gender Rule with the Specility for GW PArtner");
		log("Step 1: Load test Data from External Property file.");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		adminuser.setIsExisting(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		pssadminutils.agerulewithSpeciality(driver, adminuser, testData);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Rule -" + rule);
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
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
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		log("Successfully upto Home page");
		homepage.btnStartSchedClick();
		Speciality speciality = null;
		speciality = homepage.skipInsuranceForSpeciality(driver);
		int i = Integer.parseInt(testData.getAgeRuleMonthFirst());
		int j = Integer.parseInt(testData.getAgeRuleMonthSecond());
		if (psspatientutils.ageCurrentmonths(testData) > i && psspatientutils.ageCurrentmonths(testData) < j) {
			speciality.selectSpeciality1(testData.getSpeciality());
			Log4jUtil.log("Specility Name is....." + speciality.selectSpeciality1(testData.getSpeciality()));
			assertEquals(speciality.selectSpeciality1(testData.getSpeciality()), testData.getSpeciality());
			Log4jUtil.log("Test Case Passed.....");
		} else {
			Log4jUtil.log("No Specility avaliable Beacuase age Rule");
		}
	}
}


