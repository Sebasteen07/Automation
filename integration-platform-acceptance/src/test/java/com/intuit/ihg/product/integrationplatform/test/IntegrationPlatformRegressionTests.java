package com.intuit.ihg.product.integrationplatform.test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCTestData;
import com.intuit.ihg.product.integrationplatform.utils.PIDC;
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
import com.medfusion.product.patientportal1.utils.PortalUtil;

/**
 * @author Vasudeo P
 * @Date 29/Oct/2013
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformRegressionTests extends BaseTestNGWebDriver {

	public static final String newPassword = "P@ssw0rd";

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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
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
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserName(), testData.getPassword());

		log("Step 3: Click Ask Ur Doc");
		JalapenoAskAStaffPage askStaff1 = homePage.clickOnAskADoc(driver);

		Thread.sleep(8000);
		log("Step 4: fill and complete the of Ask A Staff");
		boolean askStaff2 = askStaff1.fillAndSubmitAskyourDocUnpaid(driver);


		log("Step 6: Validate entry is on Ask A Staff History page");
		homePage.clickOnAskADoc(driver);
		boolean aasHistory = askStaff1.checkHistory(driver);
		Thread.sleep(7000);

		log("Step 7: Logout of Patient Portal");
		homePage.clickOnLogout();

		log("Step 8: Setup Oauth client");
		RestUtils.oauthSetup(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword());

		log("Step 9: Get AMDC Rest call");
		// get only messages from last hour in epoch time to avoid transferring lot of data
		Long since = askStaff1.getCreatedTimeStamp() / 1000L - 60 * 60 * 24;

		log("Getting messages since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 10: Checking validity of the response xml");
		RestUtils.isQuestionResponseXMLValid(testData.getResponsePath(), askStaff1.getCreatedTimeStamp());

		String messageThreadID = RestUtils.gnMessageThreadID;
		log("Message Thread ID :" + messageThreadID);

		String reply_Subject = "Test" + IHGUtil.createRandomNumericString();
		String message =
				RestUtils.prepareSecureMessage(testData.getSecureMessage_AskaStaffXML(), testData.getFrom(), testData.getUserName(), reply_Subject, messageThreadID);

		String messageID = RestUtils.newMessageID();
		log("Partner Message ID:" + messageID);

		log("Step 11: Do Message Post Request");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), message, testData.getResponsePath());

		log("Step 12: Get processing status until it is completed");
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
		verifyTrue(completed, "Message processing was not completed in time");

		log("Step 13: Login to Patient Portal");
		JalapenoLoginPage loginPage1 = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePageP = loginPage1.login(testData.getUserName(), testData.getPassword());


		log("Step 14: Go to Inbox");
		JalapenoMessagesPage inboxPage = homePageP.showMessages(driver);
		assertTrue(inboxPage.isMessageDisplayed(driver, reply_Subject));

		Thread.sleep(12000);
		log("Step 16: Reply to the message");
		inboxPage.replyToMessage(driver);

		log("Step 17: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		log("Step 18: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 19: Validate message reply");
		RestUtils.isReplyPresent(testData.getResponsePath(), reply_Subject);
	}

	@DataProvider(name = "channelVersion")
	 public Object[][] channelVersionPIDC() {
		Object[][] obj = new Object[][] { {"v1"}, {"v2"}};
			return obj;
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCBatch() throws Exception {
		log("Test Case: Regression Test for Post AMDC (batch size of 3 secure messages)");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from Excel");
		AMDC AMDCData = new AMDC();
		AMDCTestData testData = new AMDCTestData(AMDCData);

		List<String> newData = new ArrayList<String>();
		newData.add(testData.getFrom());
		newData.add(testData.getUserName());
		newData.add("Test" + IHGUtil.createRandomNumericString());
		newData.add("This is auto-generated message" + IHGUtil.createRandomNumericString());
		newData.add(testData.getFrom1());
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
		verifyTrue(completed, "Message processing was not completed in time");

		for (int j = 0; j < 3; j++) {
			log("Step 5: Login to Patient Portal");
			JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
			JalapenoHomePage homePage = loginPage.login(valuedData.get(1), testData.getPassword());

			log("Step 6: Go to Inbox");

			JalapenoMessagesPage inboxPage = homePage.clickOnMenuMessages();
			assertTrue(inboxPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

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
		log("Test Case: Patient logs in first time as healthkey patient in NEW practice");

		PIDCTestData testData = loadDataFromExcel();
		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000;
		String FirstName = "MFPatient" + IHGUtil.createRandomNumericString();
		String email = PortalUtil.createRandomEmailAddress(testData.getEmail());
		String[] GI = testData.getGenderIdentityValues().split(",");
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
		verifyEquals(true, pPatientSearchPage.searchResult.getText().contains(FirstName));

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
		verifyTrue(completed, "Message processing was not completed in time");

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
		log("Test Case: Healthkey Patient updates demographics details in Practice 1 from Patient Portal");

		PIDCTestData testData = loadDataFromExcel();
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


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthKeyPatientAddInsurance() throws Exception {
		log("Test Case: Practice 1 supports Insurance and Practice 2 does not support Insurance");

		PIDCTestData testData = loadDataFromExcel();
		Long timestamp = System.currentTimeMillis();

		Long since = timestamp / 1000;

		log("Step 2: LogIn Health Key patient into second practice which does not support Insurance");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getInsurancePortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getInsuranceHealthKeyPatientUserName(), testData.getPassword());

		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoMyAccountProfilePage pMyAccountPage = homePage.clickOnMyAccount();

		log("Step 4: Create random  addresses to update");
		Random random = new Random();
		String firstLine = "Street " + random.nextInt(1000);
		String secondLine = "Street " + random.nextInt(1000);

		log("Address 1: " + firstLine + "\nAddress 2: " + secondLine);

		log("Step 5: Modify address line 1 and 2 on MyAccountPage");
		pMyAccountPage.modifyAndSubmitAddressLines(firstLine, secondLine);

		log("Step 6: Logout from Patient portal");
		pMyAccountPage.clickOnLogout();


		log("Step 7: LogIn Health Key patient into first practice");
		loginPage = new JalapenoLoginPage(driver, testData.getPortalURL());
		homePage = loginPage.login(testData.getInsuranceHealthKeyPatientUserName(), testData.getPassword());

		log("Step 8: Click on myaccountLink on MyPatientPage");
		pMyAccountPage = homePage.clickOnMyAccount();

		List<String> insuranceData = new ArrayList<String>();
		insuranceData.addAll(Arrays.asList(new String[20]));
		insuranceData.add(3, testData.getAddress1()); // insurance address1
		insuranceData.add(4, testData.getAddress2()); // insurance address2
		insuranceData.add(5, testData.getCity()); // insurance city
		insuranceData.add(6, testData.getZipCode()); // insurance zip
		insuranceData.add(14, testData.getHomePhoneNo()); // insurance homephone
		insuranceData.add(16, IHGUtil.createRandomNumericString()); // insurance policy number
		insuranceData.add(17, IHGUtil.createRandomNumericString()); // insurance Group number

		log("Step 9: Click on insuranceLink on MyAccountPage");
		// InsurancePage pinsuranceDetailsPage = pMyAccountPage.addInsuranceLink();

		log("Step 10: Delete existing insurance");
		// pinsuranceDetailsPage.deleteAllInsurances();

		log("Step 11: Start to add Insurance details");
		// pinsuranceDetailsPage.allInsuranceDetails(testData.getInsurance_Name(), testData.getInsurance_Type(), testData.getRelation(), insuranceData);

		log("Step 12: Asserting for Insurance Name ,Insurance Type and Policy number");
		Thread.sleep(60000);
		assertTrue(verifyTextPresent(driver, testData.getInsurance_Name()));
		assertTrue(verifyTextPresent(driver, testData.getInsurance_Type()));
		assertTrue(verifyTextPresent(driver, insuranceData.get(16)));

		log("Step 13: Invoke Get PIDC for Practice in which patient has add insurance in (Practice 1) to verify insurance details ");
		String[] subString = testData.getPortalRestUrl().split("/");
		log("Integration ID (Practice 1) :" + subString[subString.length - 2]);
		RestUtils.setupHttpGetRequestExceptoAuth(testData.getPortalRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 14: Check patient Demographics Details & Insurance Details");
		if (RestUtils.responseCode == 200) {
			RestUtils.verifyHealthPatientInsuranceDetails(testData.getResponsePath(), testData.getInsurancePatientID(), insuranceData, testData.getInsurance_Name());
		}

		log("Step 15: Invoke Get PIDC for Practice to which patient insurance updates should not be sent (Practice 2).");
		subString = testData.getInsurancePortalRestURL().split("/");
		log("Integration ID (Practice 2) :" + subString[subString.length - 2]);
		RestUtils.setupHttpGetRequestExceptoAuth(testData.getInsurancePortalRestURL() + "?since=" + since + ",0", testData.getResponsePath());

		if (RestUtils.responseCode == 200) {
			insuranceData.set(3, ""); // insurance address1
			insuranceData.set(4, ""); // insurance address2
			insuranceData.set(5, ""); // insurance city
			insuranceData.set(6, ""); // insurance zip
			insuranceData.set(14, ""); // insurance homephone
			insuranceData.set(16, ""); // insurance policy number
			insuranceData.set(17, ""); // insurance Group number
			RestUtils.verifyHealthPatientInsuranceDetails(testData.getResponsePath(), testData.getInsurancePatientID(), insuranceData, "");
		}

		log("Step 16: Delete insurance");
		// pinsuranceDetailsPage.deleteAllInsurances();
		Thread.sleep(60000);

		log("Step 17: Logout from Patient portal");
		homePage.clickOnLogout();

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthKeyPatientUpdateInsurance() throws Exception {
		log("Test Case: HealthKey patient insurance update in practice A is only returned to Practice A");

		PIDCTestData testData = loadDataFromExcel();
		Long timestamp = System.currentTimeMillis();

		Long since = timestamp / 1000;

		log("Step 2: LogIn Health Key patient into second practice an update demographics so patient gets available in Get response");

		JalapenoLoginPage loginpage = new JalapenoLoginPage(driver, testData.getPortalURL());
		JalapenoHomePage pMyPatientPage = loginpage.login(testData.getInsuranceHealthKeyPatientUserName1(), testData.getPassword());

		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoMyAccountProfilePage pMyAccountPage = pMyPatientPage.clickOnMyAccount();

		log("Step 4: Create random  addresses to update");
		Random random = new Random();
		String firstLine = "Street " + random.nextInt(1000);
		String secondLine = "Street " + random.nextInt(1000);

		log("Address 1: " + firstLine + "\nAddress 2: " + secondLine);

		log("Step 5: Modify address line 1 and 2 on MyAccountPage");
		pMyAccountPage.modifyAndSubmitAddressLines(firstLine, secondLine);

		log("Step 6: Logout from Patient portal");
		pMyAccountPage.clickOnLogout();

		log("Step 7: LogIn Health Key patient into first practice and add insurance '" + testData.getInsurance_Name() + "'");
		loginpage = new JalapenoLoginPage(driver, testData.getUrl());
		pMyPatientPage = loginpage.login(testData.getInsuranceHealthKeyPatientUserName1(), testData.getPassword());

		log("Step 8: Click on myaccountLink on MyPatientPage");
		pMyAccountPage = pMyPatientPage.clickOnMyAccount();

		List<String> insuranceData = new ArrayList<String>();
		insuranceData.addAll(Arrays.asList(new String[20]));
		insuranceData.add(3, testData.getAddress1()); // insurance address1
		insuranceData.add(4, testData.getAddress2()); // insurance address2
		insuranceData.add(5, testData.getCity()); // insurance city
		insuranceData.add(6, testData.getZipCode()); // insurance zip
		insuranceData.add(14, testData.getHomePhoneNo()); // insurance homephone
		insuranceData.add(16, IHGUtil.createRandomNumericString()); // insurance policy number
		insuranceData.add(17, IHGUtil.createRandomNumericString()); // insurance Group number

		log("Step 9: Click on insuranceLink on MyAccountPage");
		// InsurancePage pinsuranceDetailsPage = pMyAccountPage.addInsuranceLink();

		log("Step 10: Delete existing insurance");
		// pinsuranceDetailsPage.deleteAllInsurances();

		log("Step 11: Start to add Insurance details");
		// pinsuranceDetailsPage.allInsuranceDetails(testData.getInsurance_Name(), testData.getInsurance_Type(), testData.getRelation(), insuranceData);

		log("Step 12: Asserting for Insurance Name ,Insurance Type and Policy number");
		Thread.sleep(60000);
		assertTrue(verifyTextPresent(driver, testData.getInsurance_Name()));
		assertTrue(verifyTextPresent(driver, testData.getInsurance_Type()));
		assertTrue(verifyTextPresent(driver, insuranceData.get(16)));

		log("Step 13: Invoke Get PIDC for Practice in which patient has add insurance in (Practice 1) to verify insurance details ");
		String[] subString = testData.getRestUrl().split("/");
		log("Integration ID (Practice 1) :" + subString[subString.length - 2]);
		String lastTimestamp = RestUtils.setupHttpGetRequestExceptoAuth(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 14: Check patient Demographics Details & Insurance Details");
		if (RestUtils.responseCode == 200) {
			RestUtils.verifyHealthPatientInsuranceDetails(testData.getResponsePath(), testData.getInsurancePatientID1(), insuranceData, testData.getInsurance_Name());
		}

		log("Step 15: Invoke Get PIDC for Practice to which patient insurance updates should not be sent (Practice 2).");
		subString = testData.getPortalRestUrl().split("/");
		log("Integration ID (Practice 2) :" + subString[subString.length - 2]);
		String lastTimestamp1 = RestUtils.setupHttpGetRequestExceptoAuth(testData.getPortalRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 16: Check patient Demographics Details & Insurance Details");
		if (RestUtils.responseCode == 200) {
			insuranceData.set(3, ""); // insurance address1
			insuranceData.set(4, ""); // insurance address2
			insuranceData.set(5, ""); // insurance city
			insuranceData.set(6, ""); // insurance zip
			insuranceData.set(14, ""); // insurance homephone
			insuranceData.set(16, ""); // insurance policy number
			insuranceData.set(17, ""); // insurance Group number
			RestUtils.verifyHealthPatientInsuranceDetails(testData.getResponsePath(), testData.getInsurancePatientID1(), insuranceData, "");
		}


		log("Step 17: Delete insurance");
		// pinsuranceDetailsPage.deleteAllInsurances();
		Thread.sleep(60000);

		log("Step 18: Logout from first Patient portal");
		pMyAccountPage.clickOnLogout();

		log("Step 19: LogIn Health Key patient into second practice and add insurance '" + testData.getSecondInsuranceName() + "'");
		loginpage = new JalapenoLoginPage(driver, testData.getPortalURL());
		pMyPatientPage = loginpage.login(testData.getInsuranceHealthKeyPatientUserName1(), testData.getPassword());

		log("Step 20: Click on myaccountLink on MyPatientPage");
		pMyAccountPage = pMyPatientPage.clickOnMyAccount();

		insuranceData = new ArrayList<String>();
		insuranceData.addAll(Arrays.asList(new String[20]));
		insuranceData.set(3, testData.getAddress1()); // insurance address1
		insuranceData.set(4, testData.getAddress2()); // insurance address2
		insuranceData.set(5, testData.getCity()); // insurance city
		insuranceData.set(6, testData.getZipCode()); // insurance zip
		insuranceData.set(14, testData.getHomePhoneNo()); // insurance homephone
		insuranceData.set(16, IHGUtil.createRandomNumericString()); // insurance policy number
		insuranceData.set(17, IHGUtil.createRandomNumericString()); // insurance Group number

		log("Step 21: Click on insuranceLink on MyAccountPage");
		// pinsuranceDetailsPage = pMyAccountPage.addInsuranceLink();

		log("Step 22: Delete existing insurance");
		// pinsuranceDetailsPage.deleteAllInsurances();

		log("Step 23: Start to add Insurance details");
		// pinsuranceDetailsPage.allInsuranceDetails(testData.getSecondInsuranceName(), testData.getInsurance_Type(), testData.getRelation(), insuranceData);

		log("Step 24: Asserting for Insurance Name ,Insurance Type and Policy number");
		Thread.sleep(60000);
		assertTrue(verifyTextPresent(driver, testData.getSecondInsuranceName()));
		assertTrue(verifyTextPresent(driver, testData.getInsurance_Type()));
		assertTrue(verifyTextPresent(driver, insuranceData.get(16)));

		log("Step 25: Invoke Get PIDC for Practice in which patient has add insurance in (Practice 2) to verify insurance details using the Timestamp received in response of Step 15");
		subString = testData.getPortalRestUrl().split("/");
		log("Integration ID (Practice 2) :" + subString[subString.length - 2]);
		RestUtils.setupHttpGetRequestExceptoAuth(testData.getPortalRestUrl() + "?since=" + lastTimestamp1, testData.getResponsePath());

		log("Step 26: Check patient Demographics Details & Insurance Details");
		if (RestUtils.responseCode == 200) {
			RestUtils.verifyHealthPatientInsuranceDetails(testData.getResponsePath(), testData.getInsurancePatientID1(), insuranceData,
					testData.getSecondInsuranceName());
		}

		log("Step 27: Invoke Get PIDC for Practice to which patient insurance updates should not be sent (Practice 1) using the Timestamp received in response of Step 13");
		subString = testData.getRestUrl().split("/");
		log("Integration ID (Practice 1) :" + subString[subString.length - 2]);
		RestUtils.setupHttpGetRequestExceptoAuth(testData.getRestUrl() + "?since=" + lastTimestamp, testData.getResponsePath());

		log("Step 28: Check patient Demographics Details & Insurance Details");
		if (RestUtils.responseCode == 200) {
			insuranceData.set(3, ""); // insurance address1
			insuranceData.set(4, ""); // insurance address2
			insuranceData.set(5, ""); // insurance city
			insuranceData.set(6, ""); // insurance zip
			insuranceData.set(14, ""); // insurance homephone
			insuranceData.set(16, ""); // insurance policy number
			insuranceData.set(17, ""); // insurance Group number
			RestUtils.verifyHealthPatientInsuranceDetails(testData.getResponsePath(), testData.getInsurancePatientID1(), insuranceData, "");
		}

		log("Step 29: Delete insurance");
		// pinsuranceDetailsPage.deleteAllInsurances();
		Thread.sleep(60000);

		log("Step 30: Logout from Patient portal");
		pMyAccountPage.clickOnLogout();

	}

}
