package com.intuit.ihg.product.practice.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.patientSearch.PatientSearchPage;
import com.intuit.ihg.product.practice.page.patientactivation.PatientactivationPage;
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

	public String PatientActivation(WebDriver driver, PracticeTestData practiceTestData,String email) throws Exception {
		
		log("Test Case: Patient Activation");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 2: Click on Patient Search");
		PatientSearchPage patientSearchPage=practiceHome.clickPatientSearchLink();

		log("step 3: Click on Add new Patient");
		PatientactivationPage patientactivationPage = patientSearchPage.clickOnAddNewPatient();

		log("step 3: Enter all the details and click on Register");
		patientactivationPage.setinitialdetails(email);
		
		log("Moving to linkUrl to finish Create Patient procedure");
			
		unlockLink = patientactivationPage.getUnlockLink();
		firstNameString=patientactivationPage.getFirstNameString();
		lastNameString=patientactivationPage.getLastNameString();
		patientIdString=patientactivationPage.getPatientIdString();
		zipCodeString=patientactivationPage.getZipCodeString();
		emailAddressString=patientactivationPage.getEmailAddressString();
		
		driver.switchTo().defaultContent();
		
		return firstNameString;
		
	}
	
 
}
