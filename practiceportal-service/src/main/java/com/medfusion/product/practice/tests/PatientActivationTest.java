package com.medfusion.product.practice.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.practice.api.pojo.PracticeTestData;

public class PatientActivationTest extends BaseTestNGWebDriver {

	private String unlockLink = "";
	private String firstNameString = "";
	private String lastNameString = "";
	private String patientIdString = "";
	private String zipCodeString = "";
	private String emailAddressString = "";

	public String getUnlockLink() {
		return unlockLink;
	}

	public String getFirstNameString() {
		return firstNameString;
	}

	public String getLastNameString() {
		return lastNameString;
	}

	public String getPatientIdString() {
		return patientIdString;
	}

	public String getZipCodeString() {
		return zipCodeString;
	}

	public String getEmailAddressString() {
		return emailAddressString;
	}

	public void PatientActivation(WebDriver driver, PracticeTestData practiceTestData, String email) throws Exception {

		log("Test Case: Patient Activation");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 2: Click on Patient Activation");
		PatientActivationPage patientactivation = practiceHome.clickPatientactivationTab();

		log("step 3: Click on New Patent link");
		patientactivation.clickAddNewPatient();

		log("step 3: Enter all the details and click on Register");
		patientactivation.setInitialDetails(email);

		log("Moving to linkUrl to finish Create Patient procedure");

		unlockLink = patientactivation.getUnlockLink();
		firstNameString = patientactivation.getFirstNameString();
		lastNameString = patientactivation.getLastNameString();
		patientIdString = patientactivation.getPatientIdString();
		zipCodeString = patientactivation.getZipCodeString();
		emailAddressString = patientactivation.getEmailAddressString();

		driver.switchTo().defaultContent();
	}


}
