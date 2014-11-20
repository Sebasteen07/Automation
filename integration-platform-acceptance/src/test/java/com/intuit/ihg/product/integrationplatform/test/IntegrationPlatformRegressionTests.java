package com.intuit.ihg.product.integrationplatform.test;


import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.GW;
import com.intuit.ihg.product.integrationplatform.utils.GWTestData;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.createAccount.CreateAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessageCenterInboxPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessagePage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.insurance.InsurancePage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffHistoryPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep1Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep2Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep3Page;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.patientactivation.PatientactivationPage;
import com.intuit.ihg.product.object.maps.smintegration.page.BetaCreateNewPatientPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;



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
			RestUtils.isPatientRegistered(testData.getResponsePath(), patientIdString,patientData.get(0),patientData.get(1),null);
			
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
		@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
		public void testAMDCRegression() throws Exception {
		
			log("Test Case: AMDC Ask Question Regression");
			
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());
			
			log("Step 1: Get Data from Excel");
			AMDC AMDCData = new AMDC();
			AMDCTestData testData = new AMDCTestData(AMDCData);

			
			log("Url: " + testData.getUrl());
			log("User Name: " + testData.getUserName());
			log("Password: " + testData.getPassword());
			log("Rest Url: " + testData.getRestUrl());
			log("Response Path: " + testData.getResponsePath());
			log("From: " + testData.getFrom());
			log("SecureMessagePath: " + testData.getSecureMessage_AskaStaffXML());
			log("OAuthProperty: " + testData.getOAuthProperty());
			log("OAuthKeyStore: " + testData.getOAuthKeyStore());
			log("OAuthAppToken: " + testData.getOAuthAppToken());
			log("OAuthUsername: " + testData.getOAuthUsername());
			log("OAuthPassword: " + testData.getOAuthPassword());
			

			log("Step 2: LogIn");
			PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
			assertTrue(loginPage.isLoginPageLoaded(), "There was an error loading the login page");
			MyPatientPage myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

			log("Step 3: Click Ask A Staff");
			AskAStaffStep1Page askStaff1 = myPatientPage.clickAskAStaffLink();

			log("Step 4: Complete Step 1 of Ask A Staff");
			AskAStaffStep2Page askStaff2 = askStaff1.askQuestion("Test", "This is generated from the AMDCAskQuestion automation test case.");

			log("Step 5: Complete Step 2 of Ask A Staff");
			AskAStaffStep3Page askStaff3 = askStaff2.submitUnpaidQuestion();

			log("Step 6: Validate entry is on Ask A Staff History page");
			AskAStaffHistoryPage aasHistory = askStaff3.clickAskAStaffHistory();
			verifyTrue(aasHistory.isAskAStaffOnHistoryPage(Long.toString(askStaff1.getCreatedTimeStamp())), "Expected to see a subject containing "
							+ askStaff1.getCreatedTimeStamp() + " on the Ask A Staff History page. None were found.");

			log("Step 7: Logout of Patient Portal");
			myPatientPage.logout(driver);
			
			log("Step 8: Setup Oauth client"); 
			RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
			
			log("Step 9: Get AMDC Rest call");
			//get only messages from last hour in epoch time to avoid transferring lot of data
			Long since = askStaff1.getCreatedTimeStamp() /1000L - 60*60*24;
			
			log("Getting messages since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			
			log("Step 10: Checking validity of the response xml");
			RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());
			
			String messageThreadID=RestUtils.gnMessageThreadID;
			log("Message Thread ID :"+messageThreadID);
			
			String reply_Subject="Test"+IHGUtil.createRandomNumericString();
			String message = RestUtils.prepareSecureMessage(testData.getSecureMessage_AskaStaffXML(), testData.getFrom(), testData.getUserName(),reply_Subject,messageThreadID);
			
			log("Step 11: Do Message Post Request");
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), message, testData.getResponsePath());

			log("Step 12: Get processing status until it is completed");
			boolean completed = false;
			for (int i = 0; i < 3; i++) {
				// wait 10 seconds so the message can be processed
				Thread.sleep(120000);
				RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
				if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
					completed = true;
					break;
				}
			}
			verifyTrue(completed, "Message processing was not completed in time");
			
			log("Step 13: Login to Patient Portal");
			loginPage = new PortalLoginPage(driver, testData.getUrl());
             myPatientPage = loginPage.login(testData.getUserName(), testData.getPassword());

			log("Step 14: Go to Inbox");
			MessageCenterInboxPage inboxPage = myPatientPage.clickViewAllMessagesInMessageCenter();
			assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
			
			MessagePage msg = inboxPage.openMessageInInbox(reply_Subject);

			log("Step 15: Validate message loads and is the right message");
			assertTrue(msg.isSubjectLocated(reply_Subject));
			
			Thread.sleep(120000);
			log("Step 16: Reply to the message");
			msg.replyToMessage(IntegrationConstants.MESSAGE_REPLY);
			
			log("Step 17: Wait 60 seconds, so the message can be processed");
			Thread.sleep(60000);  
			
			log("Step 18: Do a GET and get the message");
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			
			log("Step 19: Validate message reply");
			RestUtils.isReplyPresent(testData.getResponsePath(), reply_Subject);
	}
		@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
		public void testOnDemandProvisionCCD() throws Exception {
		log("Test Case: OnDemandProvision with Inbound CCD");
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("Step 1: Get Data from Excel");
		GW GWData = new GW();
		GWTestData testData = new GWTestData(GWData);
		
		log("Url: " + testData.getUrl());
		log("Rest Url: " + testData.getRestUrl());
		log("Response Path: " + testData.getResponsePath());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());
			
		
		log("Step 2: Click Sign-UP");
		PortalLoginPage loginpage = new PortalLoginPage(driver,
				testData.getUrl());
		loginpage
				.signUp();

		BetaCreateNewPatientPage createNewPatientPage = new BetaCreateNewPatientPage(
				driver);
		log("Step 3: Fill details in Create Account Page");
		// Setting the variables for user in other tests
		String email = PortalUtil.createRandomEmailAddress(testData
				.getEmail());
		log("Email as well as UserName:-" + email);
		MyPatientPage pMyPatientPage = createNewPatientPage.BetaSiteCreateAccountPage(testData.getFirstName(),
						testData.getLastName(), email,
						testData.getPhoneNumber(),
						testData.getDob_Month(), testData.getDob_Day(),
						testData.getDob_Year(), testData.getZip(),
						testData.getSSN(), testData.getAddress(),
						testData.getPassword(),
						testData.getSecretQuestion(),
						testData.getAnswer(),
						testData.getAddressState(),
						testData.getAddressCity());
		
		String firstName=createNewPatientPage.FName;
		String lastName=createNewPatientPage.LName;
		
		
		log("Step 4: Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("Step 5: Logout");
		pMyPatientPage.clickLogout(driver);
		
		log("Step 6: Login to Practice Portal to fetch Medfusion Member ID");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getPracticeUsername(),testData.getPracticePassword());

		log("Step 7: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();

		log("Step 8: Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(firstName,lastName);
	
		log("Step 9: Verify the Search Result");
		IHGUtil.waitForElement(driver,30,pPatientSearchPage.searchResult);
		verifyEquals(true,pPatientSearchPage.searchResult.getText().contains(firstName));
		
		log("Step 10: Click on Patient");
		PatientDashboardPage patientPage=pPatientSearchPage.clickOnPatient(firstName, lastName);
		
		log("Step 11: Click on Edit Patient ID Link");
		patientPage.editPatientLink();
		String patientID=patientPage.medfusionID();
		log("Medfusion Patient ID:-"+patientID);
		
		log("Step 12: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		String ExternalID=IHGUtil.createRandomNumericString();
		String ccd = RestUtils.prepareCCD(testData.getCCDPath(),ExternalID,patientID);
		
		log("Step 13: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), ccd, testData.getResponsePath());	
		log("processingUrl:-"+processingUrl);

	/*	log("Step 15: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(180000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		verifyTrue(completed, "Message processing was not completed in time");*/
		
		log("Step 14: Click on Patient Search Link to verify External Patient ID");
		pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();

		log("Step 15: Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(firstName,lastName);
	
		log("Step 16: Verify the Search Result");
		IHGUtil.waitForElement(driver,30,pPatientSearchPage.searchResult);
		verifyEquals(true,pPatientSearchPage.searchResult.getText().contains(firstName));
		
		log("Step 17: Click on Patient");
		patientPage=pPatientSearchPage.clickOnPatient(firstName, lastName);
		
		String ExternalpatientID=patientPage.externalID(ExternalID);
		log("External Patient ID:-"+ExternalpatientID);

		log("Step 18: Logout of Practice Portal");
		pPracticeHomePage.logOut();
		
		log("Step 19: Login to Patient Portal ");
		PortalLoginPage portalloginpage = new PortalLoginPage(driver,
				testData.getUrl());
		pMyPatientPage = portalloginpage.login(
				email,
				testData.getPassword());
		
		log("Step 20: Go to Inbox");
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("Step 21: Find message in Inbox");
		MessagePage pMessage = inboxPage.clickFirstMessageRow();

		log("Step 22: Validate message subject and send date");
		Thread.sleep(1000);
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(pMessage.isSubjectLocated("New Health Information Import"));
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("Step 23: Click on link ReviewHealthInformation");
		pMessage.clickBtnReviewHealthInformation();
		
		log("Step 24: Verify if CCD Viewer is loaded and click Close Viewer");
		pMessage.verifyCCDViewerAndClose();
		
		log("Step 25: Logout of Patient Portal");
		pMyPatientPage.logout(driver);
		
		}
		@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
			public void testOnDemandProvisionPIDC() throws Exception {
			log("Test Case: OnDemandProvision with Inbound PIDC");
			
			log("Execution Environment: " + IHGUtil.getEnvironmentType());
			log("Execution Browser: " + TestConfig.getBrowserType());
			
			log("Step 1: Get Data from Excel");
			PIDCTestData testData = loadDataFromExcel();
			Long timestamp = System.currentTimeMillis();
			
			log("Url: " + testData.getUrl());
			log("Rest Url: " + testData.getRestUrl());
			log("Response Path: " + testData.getResponsePath());
			log("OAuthProperty: " + testData.getOAuthProperty());
			log("OAuthKeyStore: " + testData.getOAuthKeyStore());
			log("OAuthAppToken: " + testData.getOAuthAppToken());
			log("OAuthUsername: " + testData.getOAuthUsername());
			log("OAuthPassword: " + testData.getOAuthPassword());
				
			
			log("Step 2: Click Sign-UP");
			PortalLoginPage loginpage = new PortalLoginPage(driver,
					testData.getUrl());
			loginpage
					.signUp();
		
			BetaCreateNewPatientPage createNewPatientPage = new BetaCreateNewPatientPage(
					driver);
			log("Step 3: Fill details in Create Account Page");
			// Setting the variables for user in other tests
			String email = PortalUtil.createRandomEmailAddress(testData
					.getEmail());
			log("Email as well as UserName:-" + email);
			String FirstName="MFPatient"+IHGUtil.createRandomNumericString();
			MyPatientPage pMyPatientPage = createNewPatientPage.BetaSiteCreateAccountPage(FirstName,
							testData.getLastName(), email,
							testData.getHomePhoneNo(),
							"January", "11",
							"1987", testData.getZipCode(),
							testData.getSSN(), testData.getAddress1(),
							testData.getPassword(),
							testData.getSecretQuestion(),
							testData.getSecretAnswer(),
							testData.getState(),
							testData.getCity());
			
			String firstName=createNewPatientPage.FName;
			String lastName=createNewPatientPage.LName;
			
			
			log("Step 4: Assert Webelements in MyPatientPage");
			assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));
		
			log("Step 5: Logout");
			pMyPatientPage.clickLogout(driver);
			
			log("Step 6: Login to Practice Portal to fetch Medfusion Member ID");
			PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
			PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getPracticeUserName(),testData.getPracticePassword());
		
			log("Step 7: Click on Patient Search Link");
			PatientSearchPage pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();
		
			log("Step 8: Set Patient Search Fields");
			pPatientSearchPage.searchForPatientInPatientSearch(firstName,lastName);
		
			log("Step 9: Verify the Search Result");
			IHGUtil.waitForElement(driver,30,pPatientSearchPage.searchResult);
			verifyEquals(true,pPatientSearchPage.searchResult.getText().contains(firstName));
			
			log("Step 10: Click on Patient");
			PatientDashboardPage patientPage=pPatientSearchPage.clickOnPatient(firstName, lastName);
			
			log("Step 11: Click on Edit Patient ID Link");
			patientPage.editPatientLink();
			String patientID=patientPage.medfusionID();
			log("Medfusion Member ID:-"+patientID);
						
			log("Step 12: Logout of Practice Portal");
			pPracticeHomePage.logOut();
			
			String practicePatientId=IHGUtil.createRandomNumericString();
			String patient = RestUtils.preparePatient(testData.getPatientPath(), practicePatientId, firstName, lastName, email,patientID);
			
			log("Step 13: Setup Oauth client"); 
			RestUtils.oauthSetup(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());

			
			log("Step 14: Post PIDC with PracticePatientId (On Demand Provision)");
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), patient, testData.getResponsePath());	
		
			log("Step 15: Get processing status until it is completed");
			boolean completed = false;
			for (int i = 0; i < 3; i++) {
				// wait 10 seconds so the message can be processed
				Thread.sleep(180000);
				RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
				if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
					completed = true;
					break;
				}
			}
			verifyTrue(completed, "Message processing was not completed in time");
			
			log("Step 16: Do a GET on PIDC Url to get registered patient");
			// get only patients from last hour in epoch time to avoid transferring lot of data
			Long since = timestamp / 1000L - 60 * 24;

			log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			
			log("Step 17: Find the patient and verify PracticePatientId/Medfusion Patient Id and Patient's demographics details");
			RestUtils.isPatientRegistered(testData.getResponsePath(), practicePatientId,firstName,lastName,patientID);
			
			
			}
}