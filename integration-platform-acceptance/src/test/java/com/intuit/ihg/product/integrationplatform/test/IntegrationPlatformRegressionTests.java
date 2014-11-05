package com.intuit.ihg.product.integrationplatform.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.createAccount.CreateAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.insurance.InsurancePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.patientactivation.PatientactivationPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;



/**
 * @author Vasudeo P
 * @Date 29/Oct/2013
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformRegressionTests extends BaseTestNGWebDriver{

	
	
	private PIDCTestData loadDataFromExcel() throws Exception{
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		PIDC PIDCData = new PIDC();
		PIDCTestData testData = new PIDCTestData(PIDCData);

		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Patient XML Path: " + testData.getPatientPath());
		log("Response Path: " + testData.getResponsePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());
		log("BirthDay: " + testData.getBirthDay());
		log("ZipCode: " + testData.getZipCode());
		log("SSN: " + testData.getSSN());
		log("Email: " + testData.getEmail());
		log("PatientPassword: " + testData.getPatientPassword());
		log("SecretQuestion: " + testData.getSecretQuestion());
		log("SecretAnswer: " + testData.getSecretAnswer());
		
		return testData;
	}
		@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
			public void testPatientRegistrationfromPractice() throws Exception {
			
			log("Test Case: Patient Registration from Practice Portal");
			PIDCTestData testData = loadDataFromExcel();
			Long timestamp = System.currentTimeMillis();
			log("Step 2: Login to Practice Portal");

			PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,testData.getPracticeURL());
			PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(),testData.getPracticePassword());

			log("Step 3: Click on Patient Search");
			PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

			log("Step 4: Click on Add new Patient");
			PatientactivationPage patientactivationPage = patientSearchPage.clickOnAddNewPatient();
			
			log("Step 5: Enter all the details and click on Register");
			String unlockcode=patientactivationPage.setFullDetails(testData);

			String firstNameString = patientactivationPage.getFirstNameString();
			String patientIdString = patientactivationPage.getPatientIdString();
			String emailAddressString = patientactivationPage.getEmailAddressString();
			String unlocklink =patientactivationPage.getUnlockLink();
			log("Step 6: Logout of Practice Portal");
			practiceHome.logOut();
			
			log("Moving to linkUrl to finish Create Patient procedure");
			CreateAccountPage pCreateAccountPage = new PortalLoginPage(driver).loadUnlockLink(unlocklink);
			
			log("Step 7: Filling in user credentials and finishing the registration");
			// Filing the User credentials
			MyPatientPage myPatientPage = pCreateAccountPage.fillPatientActivaion(firstNameString, testData.getLastName(), testData.getBirthDay(), testData.getZipCode(), testData.getSSN(), emailAddressString, testData.getPatientPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),unlockcode);

			log("Step 8: Assert Webelements in MyPatientPage");
			assertTrue(myPatientPage.isViewallmessagesButtonPresent(driver));
			
			log("Step 9: Do a GET on PIDC Url to get registered patient");
			// get only patients from last day in epoch time to avoid transferring lot of data
			Long since = timestamp / 1000L - 60 * 24;

			log("Getting patients since timestamp: " + since);
			
			log("Step 10: Setup Oauth client"); 
			RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
			
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			
			List<String> patientData=new ArrayList<String>();
			patientData.add(firstNameString);
			patientData.add(testData.getLastName());
			patientData.add(testData.getHomePhoneNo());
			patientData.add(testData.getAddress1());
			patientData.add(testData.getAddress2());
			patientData.add(testData.getCity());
			patientData.add(testData.getZipCode());
			
			patientData.add(testData.getSSN());
			patientData.add("MALE");
			patientData.add(emailAddressString);
			//to do State
		
			log("Patient Data:"+patientData);
			
			log("Step 11: Find the patient and check if he is registered");
			RestUtils.isPatientRegistered(testData.getResponsePath(), patientIdString);
			
			log("Step 12: Verify patient Demographics Details");
			RestUtils.verifyPatientDetails(testData.getResponsePath(), patientIdString,patientData,null);

			log("Step 13: Click on myaccountLink on MyPatientPage");
			MyAccountPage pMyAccountPage = myPatientPage.clickMyAccountLink();
					
			List<String> updateData=RestUtils.genrateRandomData(testData.getSSN(),emailAddressString,"MALE");
			
			updateData.add(testData.getPreferredLanguage());
			updateData.add(testData.getRace());
			updateData.add(testData.getEthnicity());
			updateData.add(testData.getMaritalStatus());
			updateData.add(testData.getChooseCommunication());
			updateData.add(testData.getState());
						
			log("Step 14: Update Patient Profile Page");
			pMyAccountPage.fillPatientDetails(updateData);
			
			log("Step 15: Click on insuranceLink on MyAccountPage");
			InsurancePage pinsuranceDetailsPage = pMyAccountPage.addInsuranceLink();
			
			updateData.add(testData.getRelation());
			
			log("Update Data:"+updateData);
			
    		log("Step 16: Start to add Insurance details");
			pinsuranceDetailsPage.allInsuranceDetails(testData.getInsurance_Name(),testData.getInsurance_Type(),testData.getRelation(),updateData);

			log("Step 17: Asserting for Insurance Name and Insurance Type");
			Thread.sleep(60000);
			assertTrue(verifyTextPresent(driver, testData.getInsurance_Name()));
			assertTrue(verifyTextPresent(driver, testData.getInsurance_Type()));
			
			log("Step 18: Again do a GET on PIDC to verify Patient Update & Insurance Details");
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			
			RestUtils.isPatientUpdated(testData.getResponsePath(), patientIdString , updateData.get(3), updateData.get(4));
			
			log("Step 19: Check patient Demographics Details & Insurance Details");
			RestUtils.verifyPatientDetails(testData.getResponsePath(), patientIdString,updateData,testData.getInsurance_Name());
			
			log("Step 20: Logout from Patient portal");
			pMyAccountPage.logout(driver);
			
			
		}
}