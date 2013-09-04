package com.intuit.ihg.product.portal.tests;

import org.openqa.selenium.WebDriver;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.createAccount.BetaSiteCreateAccountPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.tests.PatientActivationTest;
import com.intuit.ihg.product.practice.utils.PracticeTestData;

public class PatientActivationUtil extends BaseTestNGWebDriver {

	public void ActivatePatient(WebDriver driver, TestcasesData testcasesData, PatientActivationTest patientActivationTest,
			PracticeTestData practiceTestData, String sUnlockLink) throws Exception {

		BetaSiteCreateAccountPage betaSiteCreateAccountPage = new PortalLoginPage(driver).loadUnlockLink(sUnlockLink);

		log("Filing in short user registration");
		betaSiteCreateAccountPage.fillInShortPatientCreation(patientActivationTest.getFirstNameString(),
				patientActivationTest.getLastNameString(), PortalConstants.DateOfBirtSlashFormat, patientActivationTest.getZipCodeString(),
				testcasesData.getSSN(), patientActivationTest.getEmailAddressString());

		GmailBot gBot = new GmailBot();
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
		MyPatientPage myPatientPage = betaSiteCreateAccountPage.fillEmailActivaion(patientActivationTest.getLastNameString(),
				PortalConstants.DateOfBirtSlashFormat, patientActivationTest.getZipCodeString(), testcasesData.getSSN(),
				patientActivationTest.getEmailAddressString(), testcasesData.getPassword(), testcasesData.getSecretQuestion(),
				testcasesData.getAnswer());

		log("Signing out of the Patient Portal");
		myPatientPage.clickLogout(driver);

	}

	public void ActivatePatient(WebDriver driver, TestcasesData testcasesData, PatientActivationSearchTest patientActivationSearchTest,
			PracticeTestData practiceTestData, String sUnlockLink) throws Exception {

		BetaSiteCreateAccountPage betaSiteCreateAccountPage = new PortalLoginPage(driver).loadUnlockLink(sUnlockLink);
		log("Filing in short user registration");
		betaSiteCreateAccountPage
				.fillInShortPatientCreation(patientActivationSearchTest.getFirstNameString(),
						patientActivationSearchTest.getLastNameString(), PortalConstants.DateOfBirtSlashFormat,
						patientActivationSearchTest.getZipCodeString(), testcasesData.getSSN(),
						patientActivationSearchTest.getEmailAddressString());

		log("Checking for the activation link inside the patient Gmail inbox");
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
		MyPatientPage myPatientPage = betaSiteCreateAccountPage.fillEmailActivaion(patientActivationSearchTest.getLastNameString(),
				PortalConstants.DateOfBirtSlashFormat, patientActivationSearchTest.getZipCodeString(), testcasesData.getSSN(),
				patientActivationSearchTest.getEmailAddressString(), testcasesData.getPassword(), testcasesData.getSecretQuestion(),
				testcasesData.getAnswer());

		log("Signing out of the Patient Portal");
		myPatientPage.clickLogout(driver);

	}
}
