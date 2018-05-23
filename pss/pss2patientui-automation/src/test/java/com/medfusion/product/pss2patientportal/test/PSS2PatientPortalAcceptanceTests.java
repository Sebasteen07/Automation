package com.medfusion.product.pss2patientportal.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.ExistingPatient;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.ExistingPatientIDP;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.OnlineAppointmentScheduling;
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

		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put(PSSConstants.START_LOCATION, PSSConstants.LOCATION);
		hm.put(PSSConstants.START_APPOINTMENT, PSSConstants.APPOINTMENT);
		hm.put(PSSConstants.START_PROVIDER, PSSConstants.PROVIDER);
		for (@SuppressWarnings("rawtypes")
		Map.Entry m : hm.entrySet()) {
			log(m.getKey() + " " + m.getValue());
		}

		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettings(driver, adminuser, testData, PSSConstants.LOGINLESS);

		String rule = adminuser.getRule();
		log("rule is--- " + rule);
		rule = rule.replaceAll(" ", "");

		propertyData.setAppointmentResponseGW(testData);
		log(testData.getUrlLoginLess());
		log(testData.getAppointmenttype());
		PSSPatientUtils psspatientutils = new PSSPatientUtils();

		log("Step 6: Move to PSS patient Portal 2.0 to book an Appointment");
		OnlineAppointmentScheduling onlineappointmentschedulingPage = new OnlineAppointmentScheduling(driver, testData.getUrlLoginLess());

		log("Step 7: Select Existing patient");
		ExistingPatient existingpatient = onlineappointmentschedulingPage.selectExistingPatient();
		log("Step 8: Fill Patient criteria");
		HomePage homepage =
				existingpatient.login(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
						testData.getZipCode());
		AppointmentDateTime aptDateTime = null;

		log("rule" + rule.equalsIgnoreCase(PSSConstants.LBT));
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			psspatientutils.LBTFlow(homepage, testData, aptDateTime, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			psspatientutils.BLTFlow(homepage, testData, "false");
		}
		log("Step 9: Based on Rule set in Admin flow.");

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

		AppointmentDateTime aptDateTime = new AppointmentDateTime(driver);
		log("rule" + rule);
		log("rule" + rule.equalsIgnoreCase(PSSConstants.LBT));
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			psspatientutils.LBTFlow(homepage, testData, aptDateTime, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			psspatientutils.BLTFlow(homepage, testData, "false");
		}
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
		AppointmentDateTime aptDateTime = null;

		log("rule from admin=" + rule.equalsIgnoreCase(PSSConstants.LBT));
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			psspatientutils.LBTFlow(homepage, testData, aptDateTime, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			psspatientutils.BLTFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BTL)) {
			psspatientutils.BTLFlow(homepage, testData, "false");
		}
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

		AppointmentDateTime aptDateTime = new AppointmentDateTime(driver);
		log("rule" + rule);
		log("rule" + rule.equalsIgnoreCase(PSSConstants.LBT));
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			psspatientutils.LBTFlow(homepage, testData, aptDateTime, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			psspatientutils.BLTFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BTL)) {
			psspatientutils.BTLFlow(homepage, testData, "false");
		}
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

		AppointmentDateTime aptDateTime = new AppointmentDateTime(driver);
		log("rule" + rule);
		log("rule" + rule.equalsIgnoreCase(PSSConstants.LBT));
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			psspatientutils.LBTFlow(homepage, testData, aptDateTime, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			psspatientutils.BLTFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BTL)) {
			psspatientutils.BTLFlow(homepage, testData, "false");
		}
	}

}