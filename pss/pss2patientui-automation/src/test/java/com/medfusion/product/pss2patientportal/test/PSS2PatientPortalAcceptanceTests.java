// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.SelectProfilePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.CreateNewAccountIDP;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.ExistingPatient;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.ExistingPatientIDP;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientIDP;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.OnlineAppointmentScheduling;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PrivacyPolicy;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
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
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		log("Step 1: set test data for existing patient ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		adminuser.setIsLoginlessFlow(true);
		adminuser.setIsExisting(true);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);
		log("Step 3: Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Move to PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlLoginLess());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());
		log("Step 5: Select Existing patient from the list");
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 6: Set the Patient criteria");
		Boolean isPrivacyPageEnabled = true;
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
		log("Wait for patient HomePage to be loaded..");
		Thread.sleep(14000);
		log("Step 7: Start the Booking appointment flow=" + rule);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGW() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		// adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);

		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSNewPatient pssnewpatient = new PSSNewPatient();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		log("Step 6: Fill Patient criteria");
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
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGE() throws Exception {
		log("E2E test to verify loginless appointment for a New patient for GE");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		log("Set the Test Data for GE ADMIN");
		propertyData.setAdminGE(adminuser);
		log("Set the Test Data for GE APPOINTMENT");
		propertyData.setAppointmentResponseGE(testData);

		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		// adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);

		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");

		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSNewPatient pssnewpatient = new PSSNewPatient();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");

		log("Step 6: Fill Patient criteria");
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

		log("Set the Test Data for ATHENA ADMIN");
		propertyData.setAdminAT(adminuser);

		log("Set the Test Data for ATHENA APPOINTMENT");
		propertyData.setAppointmentResponseAT(testData);
		pssNewPatient.createPatientDetails(testData);


		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		// adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);

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
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test
	public void testRough() {
		
		ArrayList<String> list= new ArrayList<String>();
		list.add("Preferred Phone Number");
		list.add("Insurance ID");
		list.add("Zip Code");
		
		log("List ------>"+list);
//		PSSNewPatient pssNewPatient = new PSSNewPatient();
//		Appointment testData = new Appointment();
//
//		pssNewPatient.createPatientDetails(testData);
//		log("" + testData.getFirstName());
//		log("" + testData.getLastName());
//		log("" + testData.getGender());
//		log(testData.getEmail());
//		log(testData.getPrimaryNumber());
//		log(testData.getDob());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientNG() throws Exception {

		log("-----------------------NextGen----------------------");
		log("E2E test to verify loginless appointment for a New patient for NG");
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		log("Set the Test Data for NG ADMIN");
		propertyData.setAdminNG(adminuser);
		log("Set the Test Data for GE APPOINTMENT");
		propertyData.setAppointmentResponseNG(testData);

		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		// adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);
		adminUtils.adminSettingsLoginless(driver, adminuser, testData, PSSConstants.LOGINLESS);
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");

		log("Step 3: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSNewPatient pssnewpatient = new PSSNewPatient();
		log("Step 4: Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		log("***LoginlessPatientInformation****");
		log("Clicked on Dismiss");

		log("Step 6: Fill Patient criteria");
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
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EIDPForExistingPatientGW() throws Exception {
		log("Test To View if configuration change from Admin is reflected in PSS patient portal");
		log("Step 1: set test data for existing patient ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		adminuser.setIsExisting(true);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.IDP);
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 6: Move to PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlIPD());
		log("Step 7: Select Existing patient----Enter the IDP Credentials");
		ExistingPatientIDP existingpatient = new ExistingPatientIDP(driver, testData.getUrlIPD());
		log("Step 8: Fill Patient criteria with username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		SelectProfilePage selectProfilePage = existingpatient.patientSignIn(testData.getPatientUserName(), testData.getPatientPassword());
		log("Step 9: Select the Primary Profile now");
		HomePage homepage = selectProfilePage.selectProfile();
		log("rule from admin=" + rule);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EIDPForNewPatientGW() throws Exception {
		log("E2E Test Book a appointment for an Existing patient through IDP work flow in GW");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.IDP);
		String rule = adminuser.getRule();
		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 6: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSNewPatient pssnewpatient = new PSSNewPatient();
		pssnewpatient.createPatientDetails(testData);
		log("Step 2: Login to PSS Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());
		log("Step 3: Select New Patient");
		LoginlessPatientInformation loginlesspatientinformation = onlineappointmentschedulingPage.selectNewPatientLoginLess();
		log("Step 8: Fill Patient criteria");
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
		Provider provider = homepage.selectStartPoint(PSSConstants.START_PROVIDER);
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), false);
		AppointmentDateTime appointmentdatetime = location.selectDatTime(testData.getLocation());
		appointmentdatetime.selectDate(testData.getIsNextDayBooking());
		psspatientutils.bookAppointment(false, appointmentdatetime, testData, driver);
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
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOFlowGE() throws Exception {
		log("Test To Verify if a Patient is able to login via SSO Flow from Patient 2.0 portal GE.");
		log("Step 1: Login to Patient Portal 2.0");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
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
		log("Test To View if configuration change from Admin is reflected in PSS patient portal for GE");
		log("Step 1: set test data for existing patient ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		adminuser.setIsLoginlessFlow(true);
		adminuser.setIsExisting(true);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);
		log("Step 3: Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");
		log("rule are " + rule);
		log("char At start is=" + (rule.charAt(0) == 'S'));
		String subRule;
		if ((rule.charAt(0) == 'S')) {
			subRule = rule.substring(2, 7);
			log("subRule = " + subRule);
		} else {
			subRule = rule;
		}
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		log("Step 4: Move to PSS patient Portal 2.0 to book an Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());
		log("Step 5: Select Existing patient from the list");
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 6: Set the Patient criteria");
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
		Thread.sleep(11000);
		log("getSpeciality = " + testData.getSpeciality());
		StartAppointmentInOrder startappointmentinorder = homepage.selectSpeciality(testData.getSpeciality());
		AppointmentPage appointmentpage = startappointmentinorder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		log("appointmentpage " + appointmentpage.getClass());
		log("Step 7: Start the Booking appointment flow=" + rule);
		psspatientutils.selectAFlow(driver, subRule, homepage, testData, appointmentpage);
		Thread.sleep(11000);
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
	public void testIDPLoginWithExistingPatient_1_07(String partnerPractice) throws Exception {
		log("Test To verify if existing IDP Patient can login to PSS 2.0 Patient portal for " + partnerPractice);
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
		log("Step 2: Move to PSS patient Portal 2.0 to book an Appointment");
		log("url =" + testData.getUrlIPD());
		log("Step 3: Verify that New and Existing Patient button is displayed");
		log("Step 4: Select Existing patient");
		ExistingPatientIDP existingpatient = new ExistingPatientIDP(driver, testData.getUrlIPD());
		log("Step 5: Fill Patient criteria with username=" + testData.getPatientUserName() + " and password=" + testData.getPatientPassword());
		SelectProfilePage selectProfilePage = existingpatient.patientSignIn(testData.getPatientUserName(), testData.getPatientPassword());
		HomePage homepage = selectProfilePage.selectProfile();
		if (homepage.isIDPPopUp()) {
			homepage.popUPIDPClick();
		}
		log("Step 6: Assert If user is on PSS2.0 HomePage");
		assertTrue(homepage.areBasicPageElementsPresent());
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testIDPLoginWithNewPatient_1_10(String partnerPractice) throws Exception {
		log("Test To Verify if PSS 2.0 IDP New Patient is able to create account and login. " + partnerPractice);
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
		PSSNewPatient pssnewpatient = new PSSNewPatient();
		pssnewpatient.createPatientDetails(testData);
		log("Step 2: Move to PSS patient Portal 2.0 to book an Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Step 3: Verify that New and Existing Patient button is displayed");
		assertTrue(onlineappointmentschedulingPage.areBasicPageElementsPresent());
		log("Step 4: Select Existing patient");
		NewPatientIDP newpatientidp = onlineappointmentschedulingPage.selectNewPatientIDP();
		CreateNewAccountIDP createnewaccountidp = newpatientidp.createNewAccount();
		createnewaccountidp.createNewAccount(testData.getEmail(), testData.getPassword());
		log("Step 8: Fill Patient criteria");
		Thread.sleep(9000);
		Mailinator mailinator = new Mailinator();
		String url = mailinator.getLinkFromEmail(testData.getEmail(), "Email Verification", "Confirm email address", 5);
		log("url in email " + url);
		Thread.sleep(12000);
		ExistingPatientIDP existingpatientidp = new ExistingPatientIDP(driver, url);
		existingpatientidp.patientSignIn(testData.getEmail(), testData.getPassword());
		log("existingpatientidp page " + existingpatientidp.areBasicPageElementsPresent());
		Thread.sleep(6000);
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateIDPPatientWithExistingCredentials(String partnerPractice) throws Exception {
		log("Test To Verify appropriate error message when creating new IDP patient with existing credentials for " + partnerPractice);
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
		log("Step 2: Move to PSS patient Portal 2.0 to book an Appointment");
		log("IDP Url= " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Step 3: Verify that New and Existing Patient button is displayed");
		assertTrue(onlineappointmentschedulingPage.areBasicPageElementsPresent());
		log("Step 4: Select New patient");
		NewPatientIDP newpatientidp = onlineappointmentschedulingPage.selectNewPatientIDP();
		log("Step 5: Select New patient Account");
		CreateNewAccountIDP createnewaccountidp = newpatientidp.createNewAccount();
		log("Step 6: Enter Existing patient Details");
		log("username=" + testData.getEmail() + "  password=" + testData.getPatientPassword());
		createnewaccountidp.createNewAccount(testData.getEmail(), testData.getPatientPassword());
		String emessage = "Username " + testData.getEmail() + " is already taken";
		log("EWebMessage = " + createnewaccountidp.errorMessageText());
		assertTrue(createnewaccountidp.errorMessageText().contains(emessage), "Error message did not showed up");
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateIDPPatientWithInvalidCredentials(String partnerPractice) throws Exception {
		log("Test To Verify appropriate error message when creating new IDP patient with existing credentials " + partnerPractice);
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
		log("Step 2: Move to PSS patient Portal 2.0 to book an Appointment");
		log("IDP Url= " + testData.getUrlIPD());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Step 3: Verify that New and Existing Patient button is displayed");
		assertTrue(onlineappointmentschedulingPage.areBasicPageElementsPresent());
		log("Step 4: Select New patient");
		NewPatientIDP newpatientidp = onlineappointmentschedulingPage.selectNewPatientIDP();
		log("Step 5: Select New patient Account");
		CreateNewAccountIDP createnewaccountidp = newpatientidp.createNewAccount();
		log("Step 6: Enter Existing patient Details");
		log("username=" + PSSConstants.INCORRECT_USERNAME + "  password=" + testData.getPassword());
		createnewaccountidp.createNewAccount(PSSConstants.NON_EXISTENCE_PATIENT_EMAIL, PSSConstants.INCORRECT_USERNAME, testData.getPassword());
		log("EWebMessage = " + createnewaccountidp.errorMessageText());
		assertTrue(createnewaccountidp.errorMessageText().contains(PSSConstants.IDP_INCORRECT_USERNAME_MESSAGE), "Error message did not showed up");
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
	public void testCancelAppointment(String partnerPractice) throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
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
		adminappointment.updateCancelAppointmentSettings(PSSConstants.CANCEL_APT_UPTO_HRS);
		adminappointment.saveSlotSettings();
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
		testData.setIsCancelApt(true);
		psspatientutils.selectAFlow(driver, rule, homepage, testData);
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
		PatientFlow patientflow = adminappointment.gotoPatientFlowTab();
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
}
