package com.medfusion.product.pss2patientportal.test;


import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.ExistingPatient;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.ExistingPatientIDP;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.OnlineAppointmentScheduling;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PrivacyPolicy;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
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

		log("Step 4: Move to PSS patient Portal 2.0 to book an Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());

		log("Step 5: Select Existing patient from the list");
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 6: Set the Patient criteria");
		Boolean isPrivacyPageEnabled = true;
		HomePage homepage = null;
		if(isPrivacyPageEnabled) {
			log("Privacy Enabled");
			PrivacyPolicy privacypolicy =  existingpatient.loginPatient(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
							testData.getZipCode());
			homepage =	privacypolicy.submitPrivacyPage();
		} else {
			log("Privacy Disabled");
		 homepage =
					existingpatient.login(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
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
		adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);

		String rule = adminuser.getRule();
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

			NewPatientInsuranceInfo newpatientinsuranceinfo =
					loginlesspatientinformation.fillPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
							testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber(), testData.getCity(), testData.getStreet());
			homepage =
					newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
							PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			homepage =
					loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
							testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber(), testData.getCity(), testData.getStreet());
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

		log("Step 6: Move to PSS patient Portal 2.0 to book an Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlIPD());
		log("Step 7: Select Existing patient");
		ExistingPatientIDP existingpatient = onlineappointmentschedulingPage.selectExistingPatientIDP();
		log("Step 8: Fill Patient criteria");
		HomePage homepage = existingpatient.patientSignIn(testData.getPatientUserName(), testData.getPatientPassword());

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

			NewPatientInsuranceInfo newpatientinsuranceinfo =
					loginlesspatientinformation.fillPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
							testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber(), testData.getCity(), testData.getStreet());
			homepage =
					newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
							PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			homepage =
					loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
							testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber(), testData.getCity(), testData.getStreet());
		}

		psspatientutils.selectAFlow(driver, rule, homepage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2ELoginlessForNewPatientGE() throws Exception {
		log("E2E test to verify loginless appointment for a New patient for GE");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);

		String rule = adminuser.getRule();
		rule = rule.replaceAll(" ", "");

		log("Step 6: Move to PSS patient Portal 2.0 to book an Appointment");
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSNewPatient pssnewpatient = new PSSNewPatient();
		pssnewpatient.createPatientDetails(testData);
		log("Step 2: Login to PSS Appointment");
		log("url=" + testData.getUrlLoginLess());
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());
		log("Step 3: Select New Patient");
		LoginlessPatientInformation loginlesspatientinformation = onlineappointmentschedulingPage.selectNewPatientLoginLess();

		log("Step 8: Fill Patient criteria");
		Thread.sleep(9000);
		Boolean insuranceSelected = true;
		HomePage homepage;
		if (insuranceSelected) {

			NewPatientInsuranceInfo newpatientinsuranceinfo =
					loginlesspatientinformation.fillPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
							testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber(), testData.getCity(), testData.getStreet());
			homepage =
					newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
							PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			homepage =
					loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
							testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber(), testData.getCity(), testData.getStreet());
		}

		StartAppointmentInOrder startappointmentinorder = homepage.selectSpeciality(testData.getSpeciality());
		startappointmentinorder.selectFirstProvider(PSSConstants.START_PROVIDER);
		//startappointmentinorder.selectFirstLocation(PSSConstants.START_LOCATION);
		//startappointmentinorder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		
		//psspatientutils.selectAFlow(driver, rule, homepage, testData);
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
		
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPatientPortalURL());

		JalapenoHomePage homePage = loginPage.login(testData.getPatientPortalUserName(), testData.getPatientPortalPassword());
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		homePage.clickFeaturedAppointmentsReq();
		log("Wait for PSS 2.0 Patient UI to be loaded.");
		Thread.sleep(6000);

		log("Switching tabs");
		String currentUrl =psspatientutils.switchtabs(driver);
		HomePage homepage = new HomePage(driver,currentUrl);
		Thread.sleep(15000);
		if(homepage.isPopUP()) {
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
		if((rule.charAt(0) == 'S')) {
			subRule=rule.substring(2, 7);
			log("subRule = "+subRule);	
		} else {
			subRule =rule;
		}
		
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		log("Step 4: Move to PSS patient Portal 2.0 to book an Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());

		log("Step 5: Select Existing patient from the list");
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 6: Set the Patient criteria");
		Boolean isPrivacyPageEnabled = false;
		HomePage homepage = null;
		if(isPrivacyPageEnabled) {
			log("Privacy Enabled");
			PrivacyPolicy privacypolicy =  existingpatient.loginPatient(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
							testData.getZipCode());
			homepage =	privacypolicy.submitPrivacyPage();
			
		} else {
			log("Privacy Disabled");
		 homepage =
					existingpatient.login(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
							testData.getZipCode());	
		}
		Thread.sleep(11000);
		log("getSpeciality = "+testData.getSpeciality());
		StartAppointmentInOrder startappointmentinorder = homepage.selectSpeciality(testData.getSpeciality());
		AppointmentPage appointmentpage =startappointmentinorder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		log("appointmentpage "+appointmentpage.getClass());
		log("Step 7: Start the Booking appointment flow=" + rule);
		psspatientutils.selectAFlow(driver, subRule, homepage, testData,appointmentpage);
		Thread.sleep(11000);
	}
	
}