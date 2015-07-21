package com.intuit.ihg.product.practice.tests;

import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.patientactivation.PatientactivationPage;
import com.intuit.ihg.product.practice.utils.PracticeTestData;

public class PatientActivationSearchTest extends BaseTestNGWebDriver{
	
	private String unlockLink ="";
	private String firstNameString="";
	private String lastNameString="";
	private String patientIdString="";
	private String zipCodeString="";
	private String emailAddressString="";
	
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

	public String getPatientActivationLink(WebDriver driver, PracticeTestData practiceTestData, String email,
			String doctorLogin, String doctorPassword, String url) throws Exception {
		
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
		PatientSearchPage patientSearchPage=practiceHome.clickPatientSearchLink();

		log("step 3: Click on Add new Patient");
		PatientactivationPage patientactivationPage = patientSearchPage.clickOnAddNewPatient();

		log("step 4: Enter all the details and click on Register");
		patientactivationPage.setinitialdetails(email);
				
		log("Moving to linkUrl to finish Create Patient procedure");
		unlockLink = patientactivationPage.getUnlockLink();
		firstNameString = patientactivationPage.getFirstNameString();
		lastNameString = patientactivationPage.getLastNameString();
		patientIdString = patientactivationPage.getPatientIdString();
		zipCodeString = patientactivationPage.getZipCodeString();
		emailAddressString = patientactivationPage.getEmailAddressString();
		
		driver.switchTo().defaultContent();
		
		return unlockLink;	
	}

	public String getPatientActivationLink(WebDriver driver, PracticeTestData practiceTestData,
			String email, PropertyFileLoader testData) throws Exception {
		return getPatientActivationLink(driver, practiceTestData, email, testData.getDoctorLogin(),
				testData.getDoctorPassword(), testData.getUrl());
	}
 
}
