package com.intuit.ihg.product.fundamentalArchitects.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.product.community.utils.Community;
import com.intuit.ihg.product.community.utils.CommunityTestData;
import com.intuit.ihg.product.object.maps.community.page.CreatePatientBeta;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.tests.CreatePatientTest;
import com.intuit.ihg.product.portal.tests.FamilyAccountTest;
import com.intuit.ihg.product.portal.tests.PatientActivationUtil;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.tests.PatientActivationTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeTestData;

public class CreatePatient extends BaseTestNGWebDriver {

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void selfRegistrationPatientPortal() throws Exception {

		// Instancing CreatePatientTest
		CreatePatientTest createPatientTest = new CreatePatientTest();

		// Setting data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		// Executing Test
		createPatientTest.createPatient(driver, testcasesData);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void SelfRegistrationCommunity() throws Exception {

		// Instancing Test method from Community automation
		CreatePatientBeta createPatientBeta = new CreatePatientBeta();

		// Setting data provider
		Community community = new Community();
		CommunityTestData communityTestData = new CommunityTestData(community);

		createPatientBeta.CreatePatientTest(driver, communityTestData);
	}

	// Waiting for Practices on PROD and P10INT
	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void selfRegistrationPatientPortalHelthKey() throws Exception {

		// Instancing CreatePatientTest
		CreatePatientTest createPatientTest = new CreatePatientTest();

		// Setting data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		// Executing Test
		createPatientTest.createPatient(driver, testcasesData);

		// Variable with email and password for login to another practice
		// via healthKey
		String email = "";
		String password = "";
		String url = "";

		email = createPatientTest.getEmail();
		password = createPatientTest.getPassword();
		url = testcasesData.getHealthKeyPracticeUrl();

		// Logging into another practice
		PortalLoginPage portalLoginPage = new PortalLoginPage(driver, url);
		portalLoginPage.login(email, password);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFamilyAccount() throws Exception {

		// Instancing FamilyAccountTest
		FamilyAccountTest familyAccountTest = new FamilyAccountTest();

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		// Executing test
		familyAccountTest.FamilyAccount(driver, testcasesData);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddNewPatientSearch() throws Exception {

		PatientActivationTest patientActivationTest = new PatientActivationTest();

		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		patientActivationTest.PatientActivation(driver, practiceTestData, testcasesData.getEmail());

		// Moving to the Unlock Link get from the Creation on the
		// PracticePortal

		PatientActivationUtil patientActivation = new PatientActivationUtil();

		patientActivation.ActivatePatient(driver, testcasesData, patientActivationTest, practiceTestData, patientActivationTest.getUnlockLink());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddNewPatientActivation() throws Exception {

		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		patientActivationSearchTest.PatientActivation(driver, practiceTestData, testcasesData.getEmail());

		// Moving to the Unlock Link get from the Creation on the
		// PracticePortal
		
		PatientActivationUtil patientActivation = new PatientActivationUtil();

		patientActivation.ActivatePatient(driver, testcasesData, patientActivationSearchTest, practiceTestData, patientActivationSearchTest.getUnlockLink());

	}
}