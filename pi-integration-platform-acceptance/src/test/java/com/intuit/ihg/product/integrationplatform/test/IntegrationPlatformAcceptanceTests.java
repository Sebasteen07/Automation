package com.intuit.ihg.product.integrationplatform.test;



import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.GmailBot;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoPatientActivationPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

import static org.testng.Assert.assertNotNull;

/**
 * @author dsalaskar
 * @Date 28/Aug/2015
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformAcceptanceTests extends BaseTestNGWebDriver {

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientRegistration() throws Exception {
		log("Test Case: PIDC Patient Registration");
		PIDCTestData testData = loadDataFromExcel();		
		//PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();
		
		log("Step 1: Prepare patient to post");
		Long timestamp = System.currentTimeMillis();
		String practicePatientId = "Patient" + timestamp;
		String firstName = "Name" + timestamp;
		String lastName = "TestPatient1" + timestamp;
		String email = IHGUtil.createRandomEmailAddress(testData.getEmail());				
		log("Created Email address: " + email);
		log("Practice Patient ID: " + practicePatientId);
		String patient = RestUtils.preparePatient(testData.getPatientPath(),
				practicePatientId, firstName, lastName, email, null);

		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),
				testData.getOAuthProperty(), testData.getOAuthAppToken(),
				testData.getOAuthUsername(), testData.getOAuthPassword());

		log("Step 3: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpPostRequest(
				testData.getRestUrl(), patient, testData.getResponsePath());

		log("Step 4: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 60 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl,
					testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData
					.getResponsePath())) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");
		// comment code for optimization
		GmailBot gBot = new GmailBot();
		log("Step 5: Checking for the activation link inside the patient Gmail inbox");

		// Searching for the link for patient activation in the Gmail Inbox
		String activationUrl = gBot
				.findInboxEmailLink(testData.getGmailUsername(),
						testData.getGmailPassword(),
						PortalConstants.NewPatientActivationMessage,
						"portal/#/user/activate", 3,
						false, true);

		log("Step 6: Moving to the link obtained from the email message");
		assertNotNull(activationUrl, "Error: Activation link not found.");
		log("Retrieved activation link is " + activationUrl);

		log("Finishing of patient activation: step 1 - verifying identity");
		JalapenoPatientActivationPage patientActivationPage =
				new JalapenoPatientActivationPage(driver, activationUrl);

		patientActivationPage.verifyPatientIdentity(testData.getZipCode(), PortalConstants.DateOfBirthMonth,
				PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);
	
		log("Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = patientActivationPage.fillInPatientActivation(
				email, testData.getPatientPassword(), testData.getSecretQuestion(),
				testData.getSecretAnswer(), testData.getHomePhoneNo());
		
		log("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));
		
		log("Logging out");
		jalapenoHomePage.logout(driver);
				
		log("Step 10: Do a GET on PIDC Url to get registered patient");
		// get only patients from last day in epoch time to avoid transferring
		// lot of data
		Long since = timestamp / 1000L - 60 * 24;

		log("Getting patients since timestamp: " + since);

		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since
				+ ",0", testData.getResponsePath());

		log("Step 11: Find the patient and check if he is registered");
		RestUtils.isPatientRegistered(testData.getResponsePath(),
				practicePatientId, firstName, lastName, null);
	}

	private PIDCTestData loadDataFromExcel() throws Exception {
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

	
}
																					