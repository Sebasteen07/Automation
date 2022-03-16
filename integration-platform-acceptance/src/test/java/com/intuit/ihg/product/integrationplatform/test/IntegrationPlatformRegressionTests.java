//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class IntegrationPlatformRegressionTests extends BaseTestNGWebDriver {

	public static final String newPassword = "P@ssw0rd";

	@DataProvider(name = "channelVersion")
	 public Object[][] channelVersionPIDC() {
		Object[][] obj = new Object[][] {{"v1"}, {"v2"}, {"v3"}};
			return obj;
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCRegression() throws Exception {
		logStep("Get Test Data");
		AMDCTestData testData = new AMDCTestData();
		log("Url: " + testData.getUrl());
		log("User Name: " + testData.getUserName());
		log("Password: " + testData.getPassword());
		log("Rest Url: " + testData.getRestUrl());
		log("Response Path: " + testData.getResponsePath());
		log("From: " + testData.getFrom1());
		log("SecureMessagePath: " + testData.getSecureMessage_AskaStaffXML());
		log("OAuthProperty: " + testData.getOAuthProperty());
		log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		log("OAuthAppToken: " + testData.getOAuthAppToken());
		log("OAuthUsername: " + testData.getOAuthUsername());
		log("OAuthPassword: " + testData.getOAuthPassword());

		logStep("LogIn");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		logStep("Click Ask Ur Doc");
		JalapenoAskAStaffPage askStaff1 = homePage.clickOnAskADoc(driver);
		Thread.sleep(8000);
		
		logStep("Fill and complete the of Ask A Staff");
		askStaff1.fillAndSubmitAskyourDocUnpaid(driver);

		logStep("Validate entry is on Ask A Staff History page");
		homePage.clickOnAskADoc(driver);
		askStaff1.checkHistory(driver);
		Thread.sleep(7000);

		logStep("Logout of Patient Portal");
		homePage.clickOnLogout();

		logStep("Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		logStep("Get AMDC Rest call");
		// get only messages from last hour in epoch time to avoid transferring lot of data
		Long since = askStaff1.getCreatedTimeStamp() / 1000L - 60 * 60 * 24;
		log("Getting messages since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		logStep("Checking validity of the response xml");
		RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());
		String messageThreadID = RestUtils.gnMessageThreadID;
		log("Message Thread ID :" + messageThreadID);
		String reply_Subject = "Test" + IHGUtil.createRandomNumericString();
		String message =
				RestUtils.prepareSecureMessage(testData.getSecureMessage_AskaStaffXML(), testData.getFrom1(), testData.getUserName(), reply_Subject, messageThreadID);
		String messageID = RestUtils.newMessageID();
		log("Partner Message ID:" + messageID);

		logStep("Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), message, testData.getResponsePath());

		logStep("Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(12000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");

		logStep("Login to Patient Portal");
		JalapenoLoginPage loginPage1 = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePageP = loginPage1.login(testData.getUserName(), testData.getPassword());


		logStep("Go to Inbox");
		JalapenoMessagesPage inboxPage = homePageP.showMessages(driver);
		assertTrue(inboxPage.isMessageDisplayed(driver, reply_Subject));
		Thread.sleep(12000);
		
		logStep("Reply to the message");
		inboxPage.replyToMessage(driver);
		log("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET and get the message");
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		logStep("Validate message reply");
		RestUtils.isReplyPresent(testData.getResponsePath(), reply_Subject);
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCBatch() throws Exception {
		logStep("Get Test Data");
		AMDCTestData testData = new AMDCTestData();

		List<String> newData = new ArrayList<String>();
		newData.add(testData.getFrom1());
		newData.add(testData.getUserName());
		newData.add("Test" + IHGUtil.createRandomNumericString());
		newData.add("This is auto-generated message" + IHGUtil.createRandomNumericString());
		newData.add(testData.getFrom2());
		newData.add(testData.getUserName1());
		newData.add("Test" + IHGUtil.createRandomNumericString());
		newData.add("This is auto-generated message" + IHGUtil.createRandomNumericString());
		newData.add(testData.getIntegrationPracticeID());
		newData.add(testData.getUserName2());
		newData.add("Test" + IHGUtil.createRandomNumericString());
		newData.add("This is auto-generated message" + IHGUtil.createRandomNumericString());

		List<String> senderData = new ArrayList<String>();
		senderData.add(testData.getSender1());
		senderData.add(testData.getSender2());
		senderData.add(testData.getSender3());

		List<String> recipientData = new ArrayList<String>();
		recipientData.add(testData.getPatientName1());
		recipientData.add(testData.getPatientName2());
		recipientData.add(testData.getPatientName3());

		log("Step 2: Generate POST AMDC payload with batch size 3");
		String message = RestUtils.generateBatchAMDC(testData.getBatch_SecureMessage(), newData);

		List<String> valuedData = RestUtils.patientDatails;

		log("Payload is :   " + message);

		log("Step 3: POST AMDC with batch size 3");
		String processingUrl = RestUtils.setupHttpPostRequestExceptOauth(testData.getRestUrl(), message, testData.getResponsePath(), null);

		log("Step 4: Get processing status until it is completed");
		boolean completed = false;
		// wait 10 seconds so the message can be processed
		Thread.sleep(120000);
		RestUtils.setupHttpGetRequestExceptOauth(processingUrl, testData.getResponsePath());
		if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
			completed = true;
		}
		assertTrue(completed, "Message processing was not completed in time");

		for (int j = 0; j < 3; j++) {
			log("Step 5: Login to Patient Portal");
			JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
			JalapenoHomePage homePage = loginPage.login(valuedData.get(1), testData.getPassword());

			log("Step 6: Go to Inbox");
			JalapenoMessagesPage inboxPage = homePage.clickOnMenuMessages();

			log("Step 7: Find message in Inbox");
			String messageIdentifier = valuedData.get(2).toString();

			log("Validate message loads and is the right message");
			assertTrue(inboxPage.isMessageDisplayed(driver, messageIdentifier));

			inboxPage.returnSubjectMessage();
			log("Step 8: Validate message loads and is the right message");
			assertEquals(inboxPage.returnSubjectMessage(), messageIdentifier);

			log("Validate the Message content ");
			assertEquals(inboxPage.getMessageBody(), valuedData.get(3));

			log("Step 9: Logout");
			homePage.clickOnLogout();

			for (int k = 0; k < 4; k++) {
				valuedData.remove(0);
			}
		}

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthKeyPatientLoginAndOnDemandProvision() throws Exception {
		logStep("Get Test Data");
		PIDCTestData testData = new PIDCTestData();
		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000;
		String FirstName = "MFPatient" + IHGUtil.createRandomNumericString();
		String email = PortalUtil2.createRandomEmailAddress(testData.getEmail());
		String[] GI = testData.GENDERIDENTITY.split(",");
		String zip = testData.getZipCode();
		String date = testData.getBirthDay();
		String dt = date.substring(0, 2);
		String month = date.substring(3, 5);
		String year = date.substring(6);

		log("Step 2: Click on Create An Account");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPortalURL());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientDataPortal2(FirstName, testData.getLastName(), email, "January", "11", "1987", testData.getZipCode(),
				testData.getAddress1(), testData.getAddress2(), testData.getState(), testData.getCity(), GI[1]);

		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();

		Thread.sleep(5000);
		JalapenoHomePage homePage =
				accountDetailsPage.fillAccountDetailsAndContinue(FirstName, newPassword, testData.getSecretQuestion(), testData.getSecretAnswer(),
				testData.getHomePhoneNo(), 3);

		List<String> updateData = new ArrayList<String>();
		updateData.add(FirstName);
		updateData.add(testData.getLastName());
		homePage.clickOnLogout();

		log("Step 6: Invoke Get PIDC for Practice in which patient is created (Practice 1) to verify patient details ");

		String lastTimeStamp = RestUtils.setupHttpGetRequestExceptoAuth(testData.getPortalRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		if (RestUtils.responseCode == 200) {
			RestUtils.checkPatientRegistered(testData.getResponsePath(), updateData);
		}

		log("Step 7: Login to Second Patient Portal");

		JalapenoLoginPage loginPage2 = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage2 = loginPage2.login(FirstName, newPassword);

		log("Step 11: Logout from Patient portal");
		homePage2.clickOnLogout();

		log("####### PIDC Inbound for ondemandprovision #######");

		log("Step 12: Login to Practice Portal to fetch Medfusion Member ID");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.getPracticeUserName(), testData.getPracticePassword());

		log("Step 13: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		log("Step 14: Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(FirstName, testData.getLastName());

		log("Step 15: Verify the Search Result");
		IHGUtil.waitForElement(driver, 30, pPatientSearchPage.searchResult);
		assertEquals(true, pPatientSearchPage.searchResult.getText().contains(FirstName));

		log("Step 16: Click on Patient");
		PatientDashboardPage patientPage = pPatientSearchPage.clickOnPatient(FirstName, testData.getLastName());

		log("Step 17: Click on Edit Patient ID Link");
		patientPage.editPatientLink();
		String patientID = patientPage.medfusionID();
		log("Medfusion Member ID:-" + patientID);

		log("Step 18: Logout of Practice Portal");
		pPracticeHomePage.logOut();

		String practicePatientId = IHGUtil.createRandomNumericString();
		String patient =
				RestUtils.preparePatient(testData.getHealthKeyPatientPath(), practicePatientId, FirstName, testData.getLastName(), dt, month, year, email, zip,
						patientID);

		log("Step 19: Post PIDC with PracticePatientId (On Demand Provision)");
		String processingUrl = RestUtils.setupHttpPostRequestExceptOauth(testData.getPortalRestUrl(), patient, testData.getResponsePath(), null);

		log("Step 20: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequestExceptoAuth(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");

		log("Step 21: Invoke Get PIDC for Practice in which patient has set External Patient ID in (Practice 1) to verify patient details ");

		RestUtils.setupHttpGetRequestExceptoAuth(testData.getPortalRestUrl() + "?since=" + lastTimeStamp, testData.getResponsePath());
		if (RestUtils.responseCode == 200) {
			RestUtils.isPatientRegistered(testData.getResponsePath(), practicePatientId, FirstName, testData.getLastName(), patientID);
		}

		log("Step 22: Invoke Get PIDC for Practice to which patient updates should not be sent (Practice 2) ");

		RestUtils.setupHttpGetRequestExceptoAuth(testData.getRestUrl() + "?since=" + lastTimeStamp, testData.getResponsePath());

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthKeyPatientUpdate() throws Exception {
		logStep("Get Test Data");
		PIDCTestData testData = new PIDCTestData();
		Long timestamp = System.currentTimeMillis();
		boolean found = false;

		Long since = timestamp / 1000;
		log("Step 2: LogIn Health Key patient into first practice");

		JalapenoLoginPage loginpage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage pMyPatientPage = loginpage.login(testData.getHealthKeyPatientUserName(), testData.getPassword());

		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoMyAccountProfilePage pMyAccountPage = pMyPatientPage.clickOnMyAccount();

		String randomData = IHGUtil.createRandomNumericString();
		List<String> updateData = new ArrayList<String>();
		updateData.add("FirstName" + randomData);
		updateData.add("LastName" + randomData);
		updateData.add("Street1 " + randomData);
		updateData.add("Street2 " + randomData);
		updateData.add("1" + IHGUtil.createRandomNumericString());
		updateData.add("01/01/2001");
		updateData.add("2");
		updateData.add(testData.getRace());
		updateData.add(testData.getEthnicity());
		updateData.add(testData.getChooseCommunication());
		updateData.add("MiddleName" + randomData);

		pMyAccountPage.updateDemographics(updateData);

		log("Step 4: Invoke Get PIDC for Practice to which patient updates should be sent (Practice 2) ");
		String[] subString = testData.getPortalRestUrl().split("/");
		log("Integration ID (Practice 2) :" + subString[subString.length - 2]);

		RestUtils.setupHttpGetRequestExceptoAuth(testData.getPortalRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		if (RestUtils.responseCode == 200) {
			RestUtils.checkPatientRegistered(testData.getResponsePath(), updateData);
		}

		log("Step 5: Invoke Get PIDC for Practice to which patient has updated details (Practice 1) ");
		subString = testData.getRestUrl().split("/");
		log("Integration ID (Practice 1) :" + subString[subString.length - 2]);
		RestUtils.setupHttpGetRequestExceptoAuth(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		if (RestUtils.responseCode == 200) {
			found = true;
			RestUtils.checkPatientRegistered(testData.getResponsePath(), updateData);
		}
		assertTrue(found, "Health Key Patient is not found in Get Response");

		log("Step 6: Logout from Patient portal");
		pMyAccountPage.clickOnLogout();
	}

}