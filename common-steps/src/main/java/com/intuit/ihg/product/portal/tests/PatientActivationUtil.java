package com.intuit.ihg.product.portal.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.createAccount.CreateAccountPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.tests.PatientActivationTest;
import com.intuit.ihg.product.practice.utils.PracticeTestData;

public class PatientActivationUtil extends BaseTestNGWebDriver {

	public void ActivatePatient(WebDriver driver, TestcasesData testcasesData, PatientActivationTest patientActivationTest,
			PracticeTestData practiceTestData, String sUnlockLink) throws Exception {

		CreateAccountPage pCreateAccountPage = new PortalLoginPage(driver).loadUnlockLink(sUnlockLink);

		log("Filing in short user registration");
		pCreateAccountPage.fillInShortPatientCreation(patientActivationTest.getFirstNameString(),
				patientActivationTest.getLastNameString(), PortalConstants.DateOfBirtSlashFormat, patientActivationTest.getZipCodeString(),
				testcasesData.getSSN(), patientActivationTest.getEmailAddressString());

		GmailBot gBot = new GmailBot();
		Thread.sleep(10000);
		log("Checking for the activation link inside the patient Gmail inbox");

		// Searching for the link for password reset in the Gmail Trash
		// folder
		String activationCode = "";
		activationCode = gBot.findInboxEmailLink(testcasesData.getEmail(), testcasesData.getPassword(),
				PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLink, 2, false, true);

		log("Moving to the link obtained from the email message");
		// Moving to the Link from email
		driver.get(activationCode);

		log("Filling in user credentials and finishing the registration");
		// Filing the User credentials
		MyPatientPage myPatientPage = pCreateAccountPage.fillEmailActivaion(patientActivationTest.getLastNameString(),
				PortalConstants.DateOfBirtSlashFormat, patientActivationTest.getZipCodeString(), testcasesData.getSSN(),
				patientActivationTest.getEmailAddressString(), testcasesData.getPassword(), testcasesData.getSecretQuestion(),
				testcasesData.getAnswer());

		log("Signing out of the Patient Portal");
		myPatientPage.clickLogout(driver);

	}

	public void ActivatePatient(WebDriver driver, TestcasesData testcasesData, PatientActivationSearchTest patientActivationSearchTest,
			PracticeTestData practiceTestData, String sUnlockLink) throws Exception {

		CreateAccountPage pCreateAccountPage = new PortalLoginPage(driver).loadUnlockLink(sUnlockLink);
		log("Filing in short user registration");
		pCreateAccountPage
				.fillInShortPatientCreation(patientActivationSearchTest.getFirstNameString(),
						patientActivationSearchTest.getLastNameString(), PortalConstants.DateOfBirtSlashFormat,
						patientActivationSearchTest.getZipCodeString(), testcasesData.getSSN(),
						patientActivationSearchTest.getEmailAddressString());

		log("Checking for the activation link inside the patient Gmail inbox");
		Thread.sleep(10000);
		GmailBot gBot = new GmailBot();

		// Searching for the link for password reset in the Gmail Trash
		// folder
		String activationCode = "";
		activationCode = gBot.findInboxEmailLink(testcasesData.getEmail(), testcasesData.getPassword(),
				PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLink, 2, false, true);

		log("Moving to the link obtained from the email message");
		// Moving to the Link from email
		driver.get(activationCode);

		log("Filling in user credentials and finishing the registration");
		// Filing the User credentials
		MyPatientPage myPatientPage = pCreateAccountPage.fillEmailActivaion(patientActivationSearchTest.getLastNameString(),
				PortalConstants.DateOfBirtSlashFormat, patientActivationSearchTest.getZipCodeString(), testcasesData.getSSN(),
				patientActivationSearchTest.getEmailAddressString(), testcasesData.getPassword(), testcasesData.getSecretQuestion(),
				testcasesData.getAnswer());

		log("Signing out of the Patient Portal");
		myPatientPage.clickLogout(driver);

	}
}
