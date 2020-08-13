//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.practice.tests;

import com.medfusion.common.utils.PropertyFileLoader;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.practice.api.pojo.PatientInfo;
import com.medfusion.product.practice.api.pojo.PracticeTestData;

public class PatientActivationSearchTest extends BaseTestNGWebDriver {

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

	public PatientInfo PatientActivation(WebDriver driver, PracticeTestData practiceTestData, String email,
			String doctorLogin, String doctorPassword, String url, PatientInfo patInfo) throws InterruptedException {

		log("Test Case: Patient Activation");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");

		String tempUrl = (url == null) ? practiceTestData.getUrl() : url;
		String tempDocLogin = (doctorLogin == null) ? practiceTestData.getUsername() : doctorLogin;
		String tempDocPassword = (doctorPassword == null) ? practiceTestData.getPassword() : doctorPassword;

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, tempUrl);
		PracticeHomePage practiceHome = practiceLogin.login(tempDocLogin, tempDocPassword);

		log("step 2: Click on Patient Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		log("step 3: Click on Add new Patient");
		PatientActivationPage patientactivationPage = patientSearchPage.clickOnAddNewPatient();

		log("step 4: Enter all the details and click on Register");
		patientactivationPage.setInitialDetails(email);

		log("Moving to linkUrl to finish Create Patient procedure");

		patInfo.unlockLink = patientactivationPage.getUnlockLink();

		patInfo.firstName = patientactivationPage.getFirstNameString();
		patInfo.lastName = patientactivationPage.getLastNameString();
		patInfo.practicePatientId = patientactivationPage.getPatientIdString();
		patInfo.zipCode = patientactivationPage.getZipCodeString();
		patInfo.email = patientactivationPage.getEmailAddressString();

		driver.switchTo().defaultContent();

		return patInfo;
	}

	public String getPatientActivationLink(WebDriver driver, PropertyFileLoader testData, String email)
			throws Exception {
		String tempUrl = testData.getPortalUrl();
		String tempDocLogin = testData.getDoctorLogin();
		String tempDocPassword = testData.getDoctorPassword();

		return getPatientActivationLink(driver, email, tempDocLogin, tempDocPassword, tempUrl);
	}

	public String getPatientActivationPracticeLink(WebDriver driver, PropertyFileLoader testData, String email,
			String doctorlogin, String doctorPassword) throws Exception {
		String tempUrl = testData.getPortalUrl();
		String tempDocLogin = testData.getProperty(doctorlogin);
		String tempDocPassword = testData.getProperty(doctorPassword);
		return getPatientActivationLink(driver, email, tempDocLogin, tempDocPassword, tempUrl);
	}

	public String getPatientActivationLink(WebDriver driver, PracticeTestData practiceTestData, String email)
			throws Exception {
		String tempUrl = practiceTestData.getUrl();
		String tempDocLogin = practiceTestData.getUsername();
		String tempDocPassword = practiceTestData.getPassword();
		return getPatientActivationLink(driver, email, tempDocLogin, tempDocPassword, tempUrl);
	}

	public String getPatientActivationLink(WebDriver driver, String email, String doctorLogin, String doctorPassword,
			String url) throws Exception {
		log("step 1: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, url);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("step 2: Click on Patient Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		log("step 3: Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		log("step 4: Enter all the details and click on Register");
		patientActivationPage.setInitialDetails(email);

		log("Moving to linkUrl to finish Create Patient procedure");
		unlockLink = patientActivationPage.getUnlockLink();
		firstNameString = patientActivationPage.getFirstNameString();
		lastNameString = patientActivationPage.getLastNameString();
		patientIdString = patientActivationPage.getPatientIdString();
		zipCodeString = patientActivationPage.getZipCodeString();
		emailAddressString = patientActivationPage.getEmailAddressString();

		driver.switchTo().defaultContent();

		return unlockLink;
	}
	
	public String getPatientActivationPortalLink(int flag, WebDriver driver, String email, String doctorLogin, String doctorPassword, String url,String firstname) throws Exception {
		log("step 1: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, url);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("step 2: Click on Patient Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		log("step 3: Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		log("step 4: Enter all the details and click on Register");
		patientActivationPage.setInitialDetailsPortal2(flag,firstname,email);

		log("Moving to linkUrl to finish Create Patient procedure");
		unlockLink = patientActivationPage.getUnlockLink();
		firstNameString = patientActivationPage.getFirstNameString();
		lastNameString = patientActivationPage.getLastNameString();
		patientIdString = patientActivationPage.getPatientIdString();
		zipCodeString = patientActivationPage.getZipCodeString();
		emailAddressString = patientActivationPage.getEmailAddressString();

		driver.switchTo().defaultContent();

		return unlockLink;
}

}
