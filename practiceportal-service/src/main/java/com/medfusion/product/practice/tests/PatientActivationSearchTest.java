package com.medfusion.product.practice.tests;

import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.practice.pojo.PatientInfo;
import com.medfusion.product.practice.pojo.PracticeTestData;

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
	
	public String PatientActivation(WebDriver driver, PracticeTestData practiceTestData,String email, 
			String doctorLogin, String doctorPassword, String url) throws InterruptedException{
		
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
		PatientActivationPage patientactivationPage = patientSearchPage.clickOnAddNewPatient();

		log("step 4: Enter all the details and click on Register");
		patientactivationPage.setInitialDetails(email);
				
		log("Moving to linkUrl to finish Create Patient procedure");
			
		unlockLink = patientactivationPage.getUnlockLink();
		firstNameString=patientactivationPage.getFirstNameString();
		lastNameString=patientactivationPage.getLastNameString();
		patientIdString=patientactivationPage.getPatientIdString();
		zipCodeString=patientactivationPage.getZipCodeString();
		emailAddressString=patientactivationPage.getEmailAddressString();
		
		driver.switchTo().defaultContent();
		
		return unlockLink;	
	}
	public PatientInfo PatientActivation(WebDriver driver, PracticeTestData practiceTestData,String email, 
			String doctorLogin, String doctorPassword, String url, PatientInfo patInfo) throws InterruptedException{
		
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

	public String getPatientActivationLink(WebDriver driver, PracticeTestData practiceTestData,
			String email, PropertyFileLoader testData) throws Exception {
		return getPatientActivationLink(driver, practiceTestData, email, testData.getDoctorLogin(),
				testData.getDoctorPassword(), testData.getPortalUrl());
	}
 
}
