package com.intuit.ihg.product.integrationplatform.test;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.flows.iEHDCSendCCD;
import com.intuit.ihg.product.integrationplatform.flows.iPIDCSendPatientInvite;
import com.intuit.ihg.product.integrationplatform.implementedExternals.SendCCD;
import com.intuit.ihg.product.integrationplatform.implementedExternals.SendPatientInvite;
import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;
import com.intuit.ihg.product.integrationplatform.utils.AMDC;
import com.intuit.ihg.product.integrationplatform.utils.AMDCPayload;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentData;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentDataUtils;
import com.intuit.ihg.product.integrationplatform.utils.AppointmentTypePayload;
import com.intuit.ihg.product.integrationplatform.utils.BalancePayLoad;
import com.intuit.ihg.product.integrationplatform.utils.BulkAdmin;
import com.intuit.ihg.product.integrationplatform.utils.BulkMessagePayload;
import com.intuit.ihg.product.integrationplatform.utils.CCDPayload;
import com.intuit.ihg.product.integrationplatform.utils.DirectorySearchUtils;
import com.intuit.ihg.product.integrationplatform.utils.EHDC;
import com.intuit.ihg.product.integrationplatform.utils.ExternalFileReader;
import com.intuit.ihg.product.integrationplatform.utils.FormsExportUtils;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.LoadPreTestData;
import com.intuit.ihg.product.integrationplatform.utils.MU2GetEventData;
import com.intuit.ihg.product.integrationplatform.utils.MU2Utils;
import com.intuit.ihg.product.integrationplatform.utils.P2PUnseenMessageList;
import com.intuit.ihg.product.integrationplatform.utils.PatientFormsExportInfo;
import com.intuit.ihg.product.integrationplatform.utils.PatientRegistrationUtils;
import com.intuit.ihg.product.integrationplatform.utils.Pharmacies;
import com.intuit.ihg.product.integrationplatform.utils.PharmacyPayload;
import com.intuit.ihg.product.integrationplatform.utils.PrecheckAppointmentUtils;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.integrationplatform.utils.SendDirectMessage;
import com.intuit.ihg.product.integrationplatform.utils.SendDirectMessageUtils;
import com.intuit.ihg.product.integrationplatform.utils.StatementEventData;
import com.intuit.ihg.product.integrationplatform.utils.StatementEventUtils;
import com.intuit.ihg.product.integrationplatform.utils.StatementsMessagePayload;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.portal.utils.PortalConstants;
import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages.FormBasicInfoPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.DocumentsPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientDemographicPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPreferencesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck.MyAppointmentPage;
import com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck.MyDemoGraphicsDetailPage;
import com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck.MyInsuranceDetailPage;
import com.medfusion.product.object.maps.precheck.page.myAppointmentsList.AppointmentsListPage;
import com.medfusion.product.object.maps.precheck.page.myInsurance.PrimaryInsurancePage;
import com.medfusion.product.object.maps.precheck.page.myInsurance.SecondaryInsurancePage;
import com.medfusion.product.object.maps.precheck.page.myInsurance.TertiaryInsurancePage;
import com.medfusion.product.object.maps.precheck.page.myInsuranceImage.InsuranceImagePage;
import com.medfusion.product.object.maps.precheck.page.verifyIdentity.VerifyIdentityPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import com.medfusion.product.practice.api.pojo.Practice;

/**
 * @author rkhambe
 * @Date 22/Nov/2016
 * @Description :-Regression Test for pi-integration
 * @Note : Optimizing scripts to incorporate different patient, practice and
 *       staff.
 */

public class IntegrationPlatformRegressionTests extends BaseTestNGWebDriver {
	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEHDCSendCCD(String version,Method method) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: send a CCD and check in patient Portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		String ccd;
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		EHDC testData = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(testData);
		log("Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);
		if (version.equals("v1")) {
			ccd = CCDPayload.getCCDPayload(testData);
			Thread.sleep(6000);
			log("Payload" + ccd);
			log("Wait to generate CCD Payload");
			log("Step 2: Do Message Post Request");
			log("ResponsePath: " + testData.ResponsePath);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, ccd, testData.ResponsePath);

			log("Processing URL: " + processingUrl);
			log("Step 3: Get processing status until it is completed");
			Thread.sleep(60000);
		} else {
			ccd = CCDPayload.getCCDPayloadV3(testData,method.getName());
			Thread.sleep(6000);
			log("Payload" + ccd);
			log("Wait to generate CCD Payload");
			log("Step 2: Do Message Post Request");
			log("ResponsePath: " + testData.ResponsePath);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrlV3, ccd, testData.ResponsePath);

			log("Processing URL: " + processingUrl);
			log("Step 3: Get processing status until it is completed");
			Thread.sleep(60000);
		}

		log("Step 4: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		log("Step 5: Validate message subject and send date");
		Thread.sleep(1000);
		log("Message Date" + IHGUtil.getEstTiming());
		assertTrue(messagesPage.isMessageDisplayed(driver, "You have a new health data summary"));
		log("CCD sent date & time is : " + messagesPage.returnMessageSentDate());

		log("Step 6: Click on link View health data");
		JalapenoCcdViewerPage jalapenoCcdPage = messagesPage.findCcdMessage(driver);

		log("Step 7: Verify if CCD Viewer is loaded and click Close Viewer");
		assertTrue(jalapenoCcdPage.areBasicPageElementsPresent());
		messagesPage = jalapenoCcdPage.closeCcd(driver);

		log("Step 8: Logging out");
		assertTrue(messagesPage.areBasicPageElementsPresent());
		homePage = messagesPage.backToHomePage(driver);
		loginPage = homePage.clickOnLogout();
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCSecureMessages(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: AMDC Secure Message with Read Communication");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from property file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AMDC testData = new AMDC();

		LoadPreTestDataObj.loadAMDCDataFromProperty(testData);
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		testData.allowOnce = "true";
		long timestamp = System.currentTimeMillis();
		String messageID = null;

		if (version.equals("v1")) {
			log("Step 3: Fill Message data");
			String message = AMDCPayload.getAMDCPayload(testData);

			log("message :- " + message);
			messageID = AMDCPayload.messageID;
			log("Partner Message ID:" + messageID);
			log("Step 4: Do Message Post Request");
			log("responsePath: " + testData.ResponsePath);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, message, testData.ResponsePath);

			log("Step 5: Get processing status until it is completed");
			boolean completed = false;
			for (int i = 0; i < 3; i++) {
				// wait 10 seconds so the message can be processed
				Thread.sleep(60000);
				RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
					completed = true;
					break;
				}
			}
			assertTrue(completed, "Message processing was not completed in time");
		} else {
			log("Step 3: Fill Message data");
			String message = AMDCPayload.getAMDCV3Payload(testData);
			log("message :- " + message);
			messageID = AMDCPayload.messageID;
			log("Partner Message ID:" + messageID);
			log("message :- " + message);
			messageID = AMDCPayload.messageID;
			log("Partner Message ID:" + messageID);

			log("Step 4: Do Message Post Request");
			log("responsePath: " + testData.ResponsePath);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.RestV3Url, message, testData.ResponsePath);

			log("Step 5: Get processing status until it is completed");
			boolean completed = false;
			for (int i = 0; i < 3; i++) {
				// wait 10 seconds so the message can be processed
				Thread.sleep(60000);
				RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
					completed = true;
					break;
				}
			}
			assertTrue(completed, "Message processing was not completed in time");
		}
		log("Step 6: Check secure message in patient email inbox");
		String link = null;
		String emailType = testData.GmailUserName.substring(testData.GmailUserName.indexOf("@") + 1);
		emailType = emailType.substring(0, emailType.indexOf('.'));
		if (emailType.contains("gmail")) {
			link = RestUtils.verifyEmailNotification(testData.GmailUserName, testData.GmailPassword, testData.Sender3,
					3, "Portal 2.0");
		}
		if (emailType.contains("mailinator")) {
			Mailinator mail = new Mailinator();
			String subject = "New message from " + testData.Sender3;
			String messageLink = "Sign in to view this message";
			link = mail.getLinkFromEmail(testData.GmailUserName, subject, messageLink, 5);

		}
		// Wait so that Link can be retrieved from the Email.
		Thread.sleep(5000);
		assertTrue(link != null, "AMDC Secure Message link not found in mail.");
		link = link.replace("login?redirectoptout=true", "login");
		log("Step 7: Login to Patient Portal");
		log("Link is " + link);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, link);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");
		log("Step 8: Find message in Inbox");
		String messageIdentifier = AMDCPayload.messageIdentifier;
		log("message subject " + messageIdentifier);
		log("Step 9: Log the message read time ");
		long epoch = System.currentTimeMillis() / 1000;

		String readdatetimestamp = RestUtils.readTime(epoch);
		log("Message Read Time:" + readdatetimestamp);

		log("Step 10: Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageIdentifier));

		log("Step 11: Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		log("Step 12: Wait 1 min, so the message can be processed");
		Thread.sleep(60000);

		log("Getting messages since timestamp: " + since);
		if (version.equals("v1")) {
			RestUtils.setupHttpGetRequest(testData.ReadCommuniationURL + "?since=" + since + ",0",
					testData.ResponsePath);

			log("Step 13: Validate the message id and read time in response");
			RestUtils.isReadCommunicationMessage(testData.ResponsePath, messageID, readdatetimestamp);
		} else {
			RestUtils.setupHttpGetRequest(testData.ReadCommuniationURLV3 + "?since=" + since + ",0",
					testData.ResponsePath);

			log("Step 13: Validate the message id and read time in response");
			RestUtils.isReadCommunicationMessage(testData.ResponsePath, messageID, readdatetimestamp);

		}
		log("Step 14: Reply to the message");
		messagesPage.replyToMessage(driver);

		log("Step 15: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);
		if (version.equals("v1")) {
			log("Step 16: Do a GET and get the message");
			RestUtils.setupHttpGetRequest(testData.RestUrl + "?since=" + since + ",0", testData.ResponsePath);

			log("Step 17: Validate message reply");
			RestUtils.isReplyPresent(testData.ResponsePath, messageIdentifier);
		} else {
			log("Step 16: Do a GET and get the message");
			RestUtils.setupHttpGetRequest(testData.RestV3Url + "?since=" + since + ",0", testData.ResponsePath);

			log("Step 17: Validate message reply");
			RestUtils.isReplyPresent(testData.ResponsePath, messageIdentifier);
		}

		log("Step 18: Move to  Health Record page");

		messagesPage.clickOnMenuHome();
		Thread.sleep(4000);
		DocumentsPage MedicalRecordSummariesPageObject = homePage.goToDocumentsPage();

		log("Step 19: Open Other Documents and Verify name, from and catagory type " + testData.fileName);
		boolean attachmentData = MedicalRecordSummariesPageObject.checkLastImportedFileName(testData.fileName);
		log("attachment details " + attachmentData);
		Thread.sleep(5000);
		MedicalRecordSummariesPageObject.downloadSecureMessageAttachment();
		if (driver instanceof FirefoxDriver) {
			Robot rb = new Robot();
			Thread.sleep(2000);
			rb.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(100);
			rb.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(8000);
			String UIPDF = System.getProperty("user.dir");
			String downloadFile = UIPDF + testData.downloadLocation;
			File f = new File(downloadFile);
			ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
			String pdfFileLocation = downloadFile + names.get(0);
			String pdfFileOnPortal = ExternalFileReader.base64Encoder(pdfFileLocation, false);
			String attachmentInPayload = ExternalFileReader.readFromFile(testData.attachmentBody);

			Boolean pdfMatch = RestUtils.matchBase64String(pdfFileOnPortal, attachmentInPayload);
			Assert.assertTrue(pdfMatch, "PDF Filecontent did not matched.");
			log("Asserting for PDF match " + pdfMatch);
		}

		log("Step 20: Logging out");
		homePage.clickOnLogout();

	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDCSecureMessagesWithAllCategoryTypes(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: AMDC Secure Message with Read Communication");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Get Data from property file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AMDC testData = new AMDC();

		LoadPreTestDataObj.loadAMDCDataFromProperty(testData);
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		testData.allowOnce = "false";
		log("Step 3: Fill Message data");
		long timestamp = System.currentTimeMillis();
		String message;
		if (version.equals("v1")) {
			message = AMDCPayload.getAMDCPayload(testData);
		} else {
			message = AMDCPayload.getAMDCV3Payload(testData);
		}
		log("message :- " + message);
		String messageID = AMDCPayload.messageID;
		log("Partner Message ID:" + messageID);
		String processingUrl;
		log("Step 4: Do Message Post Request");
		log("responsePath: " + testData.ResponsePath);
		if (version.equals("v1")) {
			processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, message, testData.ResponsePath);
		} else {
			processingUrl = RestUtils.setupHttpPostRequest(testData.RestV3Url, message, testData.ResponsePath);

		}

		log("Step 5: Get processing status until it is completed");
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");

		log("Step 6: Check secure message in patient email inbox");
		String link = null;
		String emailType = testData.GmailUserName.substring(testData.GmailUserName.indexOf("@") + 1);
		emailType = emailType.substring(0, emailType.indexOf('.'));
		if (emailType.contains("gmail")) {
			link = RestUtils.verifyEmailNotification(testData.GmailUserName, testData.GmailPassword, testData.Sender3,
					3, "Portal 2.0");
		}
		if (emailType.contains("mailinator")) {
			Mailinator mail = new Mailinator();
			String subject = "New message from " + testData.Sender3;
			String messageLink = "Sign in to view this message";
			link = mail.getLinkFromEmail(testData.UserName, subject, messageLink, 5);

		}
		// Wait so that Link can be retrieved from the Email.
		Thread.sleep(5000);
		assertTrue(link != null, "AMDC Secure Message link not found in mail.");
		link = link.replace("login?redirectoptout=true", "login");
		log("Step 7: Login to Patient Portal");
		log("Link is " + link);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, link);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");
		log("Step 8: Find message in Inbox");
		String messageIdentifier = AMDCPayload.messageIdentifier;
		log("message subject " + messageIdentifier);
		log("Step 9: Log the message read time ");
		long epoch = System.currentTimeMillis() / 1000;

		String readdatetimestamp = RestUtils.readTime(epoch);
		log("Message Read Time:" + readdatetimestamp);

		log("Step 10: Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, messageIdentifier));

		log("Step 11: Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		log("Step 12: Wait 1 min, so the message can be processed");
		Thread.sleep(60000);

		log("Getting messages since timestamp: " + since);
		if (version.equals("v1")) {
			RestUtils.setupHttpGetRequest(testData.ReadCommuniationURL + "?since=" + since + ",0",
					testData.ResponsePath);
		} else {
			RestUtils.setupHttpGetRequest(testData.ReadCommuniationURLV3 + "?since=" + since + ",0",
					testData.ResponsePath);
		}

		log("Step 13: Validate the message id and read time in response");
		RestUtils.isReadCommunicationMessage(testData.ResponsePath, messageID, readdatetimestamp);

		log("Step 14: Reply to the message");
		messagesPage.replyToMessage(driver);

		log("Step 15: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		log("Step 16: Do a GET and get the message");
		if (version.equals("v1")) {
			RestUtils.setupHttpGetRequest(testData.RestUrl + "?since=" + since + ",0", testData.ResponsePath);
		} else {
			RestUtils.setupHttpGetRequest(testData.RestV3Url + "?since=" + since + ",0", testData.ResponsePath);
		}
		log("Step 17: Validate message reply");
		RestUtils.isReplyPresent(testData.ResponsePath, messageIdentifier);

		log("Step 18: Move to Health Record page ");
		messagesPage.menuHealthRecordClickOnly();

		log("Step 19: Open Other Documents");
		DocumentsPage MedicalRecordSummariesPageObject = homePage.goToDocumentsPageFromMenu();

		log("Step 20: Verify name, from and catagory type");
		MedicalRecordSummariesPageObject.verifyName_From_CategoryType(testData.From, testData.categoryType,
				testData.fileName);

		log("Logging out");
		homePage.clickOnLogout();

	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMU2GetEventForExistingPatient(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case (testMU2GetEventForExistingPatient): Consolidated CCD related events verification in Pull Events");

		log("Test case Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);

		MU2Utils MU2UtilsObj = new MU2Utils();
		MU2UtilsObj.mu2GetEvent(testData, driver, version);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMU2GetEventForNewPatient(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case (testMU2GetEventForNewPatient): Consolidated CCD related events verification for newly created patients");
		log("Environment " + IHGUtil.getEnvironmentType());
		log("Step 1:  Create Patient");
		long timestamp = System.currentTimeMillis();
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);

		EHDC EHDCObj = new EHDC();

		LoadPreTestDataObj.loadEHDCDataFromProperty(EHDCObj);
		iPIDCSendPatientInvite sendPatientInviteObj = new SendPatientInvite();
		ArrayList<String> patientDetail = sendPatientInviteObj.sendPatientInviteToPractice(
				testData.PATIENT_INVITE_RESTURL, testData.PATIENT_PRACTICEID, testData.PATIENT_EXTERNAL_ID,
				"01/01/1987", "27560", testData.token);

		log("Follwing are patient details");
		for (String values : patientDetail) {
			log(" " + values);
		}
		log("checking email for activation UrL link");
		Thread.sleep(5000);
		Mailinator mail = new Mailinator();
		String activationUrl = mail.getLinkFromEmail(patientDetail.get(4), PortalConstants.NewPatientActivationMessage,
				PortalConstants.NewPatientActivationMessageLinkText, 20);
		assertTrue(activationUrl != null, "Error: Activation link not found.");

		PatientRegistrationUtils.registerPatient(activationUrl, patientDetail.get(4), testData.PatientPassword,
				testData.SecretQuestion, testData.SecretAnswer, testData.HomePhoneNo, driver, patientDetail.get(2),
				patientDetail.get(3));

		Thread.sleep(12000);
		log("Step 2:  Send CCD to Patient");

		iEHDCSendCCD sendCCDObj = new SendCCD();

		log("Send 1st CCD to Patient");
		ArrayList<String> ccdDetail1 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, patientDetail.get(0), EHDCObj.ccdXMLPath, testData.PATIENT_EXTERNAL_ID,
				testData.token);
		log(ccdDetail1.get(0));
		Thread.sleep(8000);

		log("Send 2nd CCD to Patient");
		ArrayList<String> ccdDetail2 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, patientDetail.get(0), testData.CCDPATH1, testData.PATIENT_EXTERNAL_ID,
				testData.token);
		log(ccdDetail2.get(0));
		Thread.sleep(8000);

		log("Send 3rd CCD to Patient");
		ArrayList<String> ccdDetail3 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, patientDetail.get(0), testData.CCDPATH2, testData.PATIENT_EXTERNAL_ID,
				testData.token);
		log(ccdDetail3.get(0));
		Thread.sleep(8000);

		log("Send 4th CCD to Patient");
		ArrayList<String> ccdDetail4 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, patientDetail.get(0), testData.CCDPATH3, testData.PATIENT_EXTERNAL_ID,
				testData.token);
		log(ccdDetail4.get(0));

		log("Set username and password for MU2 : UserName " + patientDetail.get(4) + " password: "
				+ testData.PatientPassword);

		RestUtils.oauthSetup(testData.OAUTH_KEYSTORE, testData.OAUTH_PROPERTY, testData.OAUTH_APPTOKEN,
				testData.OAUTH_USERNAME, testData.OAUTH_PASSWORD);

		Long since = timestamp / 1000L - 60 * 24;
		log("Getting patients since timestamp: " + since);
		log("PUSH_RESPONSEPATH: " + testData.PUSH_RESPONSEPATH);
		RestUtils.setupHttpGetRequest(testData.PATIENT_INVITE_RESTURL + "?since=" + since + ",0",
				testData.PUSH_RESPONSEPATH);

		MU2Utils MU2UtilsObj = new MU2Utils();
		String patientID = MU2UtilsObj.getMedfusionID(testData.PUSH_RESPONSEPATH, patientDetail.get(0));

		log("patientID : " + patientID);

		log("waiting for CCD to reflect on portal 2.0 ");
		testData.PORTAL_USERNAME = patientDetail.get(4);
		testData.PORTAL_PASSWORD = testData.PatientPassword;
		testData.INTUIT_PATIENT_ID = patientID;
		testData.CCDMessageID1 = ccdDetail4.get(0);
		testData.CCDMessageID2 = ccdDetail3.get(0);
		testData.PatientExternalId_MU2 = patientDetail.get(0);
		testData.PatientFirstName_MU2 = patientDetail.get(0);
		testData.PatientLastName_MU2 = patientDetail.get(1);

		Thread.sleep(70000);

		log("Step 4:  Login Portal 2.0");

		MU2UtilsObj.mu2GetEvent(testData, driver, version);
	}

	@DataProvider(name = "portalVersion")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] { { "2.0" }, };
		return obj;
	}

	@Test(enabled = true, dataProvider = "portalVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientRegistrationV1(String portalVersion) throws Exception {
		log("Test Case: PIDC Patient Registration v1 channel for portal-" + portalVersion);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		PatientRegistrationUtils.pidcPatientRegistration("v1", driver, portalVersion);
	}

	@Test(enabled = true, dataProvider = "portalVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientRegistrationV2(String portalVersion) throws Exception {
		log("Test Case: PIDC Patient Registration v2 channel for portal-" + portalVersion);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		PatientRegistrationUtils.pidcPatientRegistration("v2", driver, portalVersion);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestForNewSelfPatient() throws Exception {
		log("Test Case: Appointment Request for New Patient From Partner");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		AppointmentDataUtils aDUtils = new AppointmentDataUtils();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AppointmentData testData = new AppointmentData();
		LoadPreTestDataObj.loadAppointmentDataFromProperty(testData);
		log("Step 1: Create patient");
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(),
				patient.getPassword(), testDataPFL);

		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		Thread.sleep(6000);
		homePage.clickOnLogout();

		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		RestUtils.setupHttpGetRequest(testData.PATIENT_INVITE_RESTURL + "?since=" + since + ",0",
				testData.ResponsePath);
		Thread.sleep(2000);
		// log("responsePath- "+RestUtils.prepareCCD(testData.ResponsePath));

		String responseXML = RestUtils.prepareCCD(testData.ResponsePath);
		String medfusionID = aDUtils.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID " + medfusionID);

		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.csvFilePath;
		aDUtils.csvFileReader(testData, workingDir);

		log("Step 3: Post New AppointMentData with MFPatientID");

		testData.FirstName = patient.getFirstName();
		testData.LastName = patient.getLastName();
		testData.EmailUserName = patient.getEmail();
		testData.PatientPracticeId = patient.getFirstName();
		testData.MFPatientId = medfusionID;
		testData.BatchSize = "1";
		testData.Status = testData.appointmentDetailList.get(1).getStatus(); // "NEW";
		testData.Time = testData.appointmentDetailList.get(1).getTime(); // "2017-02-13T21:30:00.000Z";
		testData.Location = testData.appointmentDetailList.get(1).getLocation();
		testData.appointmentType = "FUTURE";
		testData.UserName = patient.getEmail();
		testData.Password = patient.getPassword();

		testData.Type = testData.appointmentDetailList.get(1).getType();
		testData.Reason = testData.appointmentDetailList.get(1).getReason();
		testData.Description = testData.appointmentDetailList.get(1).getDescription();

		aDUtils.checkAppointment(testData, driver);
		Thread.sleep(6000);

		log("Step 4: Post UPDATE AppointMentData ");
		testData.Status = "UPDATE";
		testData.Time = testData.appointmentDetailList.get(3).getTime(); // "2017-03-13T16:30:59.999Z";
		testData.Location = testData.appointmentDetailList.get(3).getLocation();
		testData.appointmentType = "FUTURE";
		testData.PatientPracticeId = patient.getFirstName();
		testData.BatchSize = "1";
		testData.Type = testData.appointmentDetailList.get(3).getType();
		testData.Reason = testData.appointmentDetailList.get(3).getReason();
		testData.Description = testData.appointmentDetailList.get(3).getDescription();

		aDUtils.checkAppointment(testData, driver);

		log("Step 5: Post CANCEL AppointMentData ");
		Thread.sleep(3000);
		testData.Status = "CANCEL";
		testData.Time = testData.appointmentDetailList.get(4).getTime(); // "2017-03-13T16:30:59.999Z";
		testData.Location = testData.appointmentDetailList.get(4).getLocation();
		testData.appointmentType = "FUTURE";
		testData.PatientPracticeId = patient.getFirstName();
		testData.BatchSize = "1";

		testData.Type = testData.appointmentDetailList.get(4).getType();
		testData.Reason = testData.appointmentDetailList.get(4).getReason();
		testData.Description = testData.appointmentDetailList.get(4).getDescription();

		aDUtils.checkAppointment(testData, driver);

		log("step 6: Login to Practice Portal");

		Practice practice = new Practice();
		practice.url = testData.portalURL;
		practice.username = testData.practiceUserName;
		practice.password = testData.practicePassword;

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practice.url);
		PracticeHomePage pPracticeHomePage = practiceLogin.login(practice.username, practice.password);

		log("step 7: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		log("step 8:Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(patient.getFirstName(), patient.getLastName());

		log("step 9:Verify the Search Result");
		IHGUtil.waitForElement(driver, 60, pPatientSearchPage.searchResult);
		Assert.assertTrue(pPatientSearchPage.searchResult.getText().contains(patient.getFirstName()));

		String searchResult = "//*[@id=\"table-1\"]/tbody/tr/td[1]/a";
		driver.findElement(By.xpath(searchResult)).click();

		String editPatientID = "//*[@id=\"dashboard\"]/fieldset[1]/table/tbody/tr[7]/td[2]/a";
		driver.findElement(By.xpath(editPatientID)).click();
		Thread.sleep(3000);
		String onDemandID = "//*[@name=\"emrid\"]";
		String patientExternalID = driver.findElement(By.xpath(onDemandID)).getAttribute("value");

		log("Actual patient ID " + patientExternalID);
		log("Expected patient ID " + patient.getFirstName());

		Assert.assertEquals(patient.getFirstName(), patientExternalID, "Patient External ID Matched !");
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStatementEventForExistingPatient(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: Post Statment and verify its Event for Existing Patient From Partner");
		log("Recommended to use Firefox Browser for this test ");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		StatementEventData testData = new StatementEventData();
		log("Step 1: load from external property file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadStatementEventDataFromProperty(testData);
		log("url is " + testData.Url);
		log("Step 2: Call Statement Post");
		StatementEventUtils sEventObj = new StatementEventUtils();
		sEventObj.generateViewEvent(driver, testData, 'E', version);

	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStatementEventForNewSelfPatient(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: POST Statement and Get Statement Event for New Patient From Partner");
		log("Recommended to use Firefox Browser for this test ");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Create patient");
		StatementEventData testData = new StatementEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadStatementEventDataFromProperty(testData);
		log("Statement Event for Practice: " + testData.PracticeName);

		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		patient.setUrl(testData.Url);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		Thread.sleep(12000);
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(),
				patient.getPassword(), testDataPFL);

		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);

		StatementEventUtils sEventObj = new StatementEventUtils();
		Thread.sleep(14000);
		homePage.clickOnLogout();

		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		RestUtils.setupHttpGetRequest(testData.RestURLPIDC + "?since=" + since + ",0", testData.ResponsePath);
		Thread.sleep(2000);

		String responseXML = RestUtils.prepareCCD(testData.ResponsePath);
		String medfusionID = sEventObj.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID " + medfusionID);
		log("Step 3: set patient details ");
		testData.UserName = patient.getUsername();
		testData.Password = patient.getPassword();
		testData.FirstName = patient.getFirstName();
		testData.LastName = patient.getLastName();
		testData.Email = patient.getEmail();
		testData.PatientID = "";
		testData.MFPatientID = medfusionID;
		testData.UserName = patient.getEmail();
		testData.StatementType = "New";
		testData.since = since;
		log("Step 4: Call Statement Post");
		sEventObj.generateViewEvent(driver, testData, 'N', version);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBulkSecureMessage(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: Bulk Secure Message");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Get Data from property file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		BulkAdmin testData = new BulkAdmin();
		LoadPreTestDataObj.loadDataFromPropertyBulk(testData);
		Thread.sleep(3000);

		log("Step 2: Setup Oauth client");
		if (BulkMessagePayload.checkWithPrevioudBulkMessageID) {
			testData.PatientsUserNameArray[0] = testData.oUserName;
			testData.PatientsPasswordArray[0] = testData.oPassword;
			testData.PatientsIDArray[0] = testData.oPatientID;
			testData.PatientEmailArray[0] = testData.oEmailID;
			testData.AddAttachment = "no";
			testData.MaxPatients = "1";
			testData.NumberOfAttachments = "1";
		}
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		if (version.equals("v1")) {
			String messageID = BulkMessagePayload.messageId;
			log("Partner Message ID:" + messageID);
			log("Step 3: Fill Message data");
			String message = BulkMessagePayload.getBulkMessagePayload(testData);
			Thread.sleep(6000);
			log("message xml : " + message);
			log("Step 4: Do Message Post Request");
			log("ResponsePath:- " + testData.ResponsePath);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, message, testData.ResponsePath);
			log("Step 5: Get processing status until it is completed");
			boolean completed = false;
			for (int i = 0; i < 3; i++) {
				// wait 10 seconds so the message can be processed
				Thread.sleep(60000);
				RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
					completed = true;
					break;
				}
			}
			assertTrue(completed == true, "Message processing was not completed in time");
		} else {
			String messageID = BulkMessagePayload.messageId;
			log("Partner Message ID:" + messageID);
			log("Step 3: Fill Message data");
			String message = BulkMessagePayload.getBulkMessageV3Payload(testData);
			log("message xml : " + message);
			log("Step 4: Do Message Post Request");
			log("ResponsePath:- " + testData.ResponsePath);
			String processingUrl = RestUtils.setupHttpPostRequest(testData.RestV3Url, message, testData.ResponsePath);
			log("Step 5: Get processing status until it is completed");
			boolean completed = false;
			for (int i = 0; i < 3; i++) {
				// wait 10 seconds so the message can be processed
				Thread.sleep(60000);
				RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
					completed = true;
					break;
				}
			}
			assertTrue(completed == true, "Message processing was not completed in time");
		}
		log("testData.MaxPatients : " + testData.MaxPatients);

		for (int i = 1; i <= Integer.parseInt(testData.MaxPatients); i++) {
			// Loop through different patients email and login to view the message.
			log("Patient is - " + testData.PatientsUserNameArray[i - 1]);
			String subject = "New message from PI Automation rsdk Integrated";
			log("Step 6: Check secure message in patient Email inbox");

			String link = "";
			Mailinator mail = new Mailinator();
			String email = testData.PatientEmailArray[i - 1];
			String messageLink = "Sign in to view this message";
			link = mail.getLinkFromEmail(email, subject, messageLink, 20);

			link = link.replace("login?redirectoptout=true", "login");
			log("Step 7: Login to Patient Portal");
			log("Link is " + link);
			JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, link);
			JalapenoHomePage homePage = loginPage.login(testData.PatientsUserNameArray[i - 1],
					testData.PatientsPasswordArray[i - 1]);

			Thread.sleep(5000);
			log("Detecting if Home Page is opened");
			assertTrue(homePage.isHomeButtonPresent(driver));
			log("Click on messages solution");
			JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
			assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");
			long epoch = System.currentTimeMillis() / 1000;

			log("Step 8: Find message in Inbox");
			if (version.equals("v1")) {
				String messageIdentifier = BulkMessagePayload.subject;
				log("message subject " + messageIdentifier);
				log("Step 9: Log the message read time ");
				log("Step 10: Validate message loads and is the right message");
				assertTrue(messagesPage.isMessageDisplayed(driver, messageIdentifier));
			} else {
				String messageIdentifier = BulkMessagePayload.subject;
				log("message subject " + messageIdentifier);
				log("Step 9: Log the message read time ");
				log("Step 10: Validate message loads and is the right message");
				assertTrue(messagesPage.isMessageDisplayed(driver, messageIdentifier));
			}
			log("Step 11: Check if attachment is present or not");
			String readdatetimestamp = RestUtils.readTime(epoch);
			log("Message Read Time:" + readdatetimestamp);
			if (testData.AddAttachment.equalsIgnoreCase("yes")) {
				String attachmentFileName = driver.findElement(By.xpath("// a [contains(text(),'bulk1.pdf')]"))
						.getText();
				log("attachmentFileName " + attachmentFileName);
				assertFalse(attachmentFileName.equalsIgnoreCase("1.pdf"));
			}
			if (i == 1 && BulkMessagePayload.checkWithPrevioudBulkMessageID == false) {
				log("Step 12: Move to  Health Record page");
				messagesPage.backToHomePage(driver);
				MedicalRecordSummariesPage MedicalRecordSummariesPageObject = homePage
						.clickOnMedicalRecordSummaries(driver);

				log("Step 13: Open Other Documents");
				MedicalRecordSummariesPageObject.gotoOtherDocumentTab();

				log("Step 14: Verify name, from and catagory type");
				String attachmentData = MedicalRecordSummariesPageObject.getMessageAttachmentData();
				log("attachment details = " + MedicalRecordSummariesPageObject.getMessageAttachmentData());
				Assert.assertTrue(attachmentData.contains(testData.FileName + i + ".pdf"), "file name not found");
				MedicalRecordSummariesPageObject.downloadSecureMessageAttachment();
				String UIPDF = System.getProperty("user.dir");
				String home = System.getProperty("user.home");
				String fileName = "bulk1";

				if (driver instanceof FirefoxDriver) {
					Robot rb = new Robot();
					Thread.sleep(2000);
					rb.keyPress(KeyEvent.VK_ENTER);
					Thread.sleep(100);
					rb.keyRelease(KeyEvent.VK_ENTER);

					log("Wait for file to be downloaded");
					Thread.sleep(24000);

					String downloadFile = UIPDF + testData.downloadLocation;
					File f = new File(downloadFile);
					ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
					String pdfFileLocation = downloadFile + names.get(0);
					String pdfFileOnPortal = ExternalFileReader.base64Encoder(pdfFileLocation, false);
					String workingDir = UIPDF + testData.AttachmentLocation + i + ".txt";
					String attachmentInPayload = ExternalFileReader.readFromFile(workingDir);
					Boolean pdfMatch = RestUtils.matchBase64String(pdfFileOnPortal, attachmentInPayload);
					log("Is PDF Match " + pdfMatch);
					Assert.assertTrue(pdfMatch, "PDF Filecontent did not matched.");
				}
				if (driver instanceof ChromeDriver) {
					File file = new File(home + "/Downloads/" + fileName + ".pdf");
					String workingDir = UIPDF + testData.AttachmentLocation + i + ".txt";

					String attachmentInPayload = ExternalFileReader.readFromFile(workingDir);
					String downloadedFile = ExternalFileReader.readFromFile(workingDir);
					Thread.sleep(800);
					Boolean pdfMatch = RestUtils.matchBase64String(downloadedFile, attachmentInPayload);
					log("Is PDF Match " + pdfMatch);
					RestUtils.deleteFile(file.getPath());
				}

			}
			homePage.clickOnLogout();
		}
		if (testData.resendPreviousMessage.contains("yes") && BulkMessagePayload.messageIdCounter == 0) {

			BulkMessagePayload.checkWithPrevioudBulkMessageID = true;
			log("Step 12: Start Bulk mass admin for patient with  No attachment but previous Message ID");
			testBulkSecureMessage(version);
		}
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDirectorySearch() throws Exception {
		DirectorySearchUtils DirectorySearchUtilsObj = new DirectorySearchUtils();
		DirectorySearchUtilsObj.directorySearchParam("all");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDirectorySearchSingleValue() throws Exception {
		DirectorySearchUtils DirectorySearchUtilsObj = new DirectorySearchUtils();
		DirectorySearchUtilsObj.directorySearchParam("acceptance");
	}

	@DataProvider(name = "attachmentType")
	public Object[][] sendDirectAttachmentTypeUsed() {
		Object[][] obj = new Object[][] { { "xml" }, { "pdf" }, { "png" }, { "none" }, };
		return obj;
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendDirectMessageXML() throws Exception {
		log("Test Case: Send Secure Direct Message with XML as attachment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Set Test Data from Property file");
		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
		SendDirectMessageUtilsObj.sendSecureDirectMessage(driver, "xml");
	}

	@Test(enabled = true, dataProvider = "attachmentType", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendDirectMessageAll(String typeOfAttachmentUsed) throws Exception {
		log("Test Case: Send Secure Direct Message");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Set Test Data from Property file ");
		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
		SendDirectMessageUtilsObj.sendSecureDirectMessage(driver, typeOfAttachmentUsed);
	}

	@DataProvider(name = "channelVersion")
	public Object[][] channelVersionPIDC() {
		Object[][] obj = new Object[][] { { "v1" }, { "v2" }, { "v3" } };
		return obj;
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testOnDemandProvisionPIDC(String version) throws Exception {
		log("Test Case: Test OnDemand Provision with PIDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Set Test Data from Property file ");
		Long timestamp = System.currentTimeMillis();
		AppointmentDataUtils aDUtils = new AppointmentDataUtils();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		PIDCInfo testData = new PIDCInfo();
		LoadPreTestDataObj.loadDataFromProperty(testData, version, "2.0");

		log("Step 2: Create patient");
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(),
				patient.getPassword(), testDataPFL);
		Long since = timestamp / 1000L - 60 * 24;
		Thread.sleep(5000);
		homePage.clickOnLogout();

		log("Step 3: Setup Oauth client");
		RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(),
				testData.getoAuthUsername(), testData.getoAuthPassword());

		log("Step 4: Get request to fetch Medfusion ID");
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		Thread.sleep(2000);
		String responseXML = RestUtils.prepareCCD(testData.getResponsePath());
		String medfusionID = aDUtils.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID " + medfusionID);
		String practicePatientId = IHGUtil.createRandomNumericString();
		log("patientExternalID on Demand  " + practicePatientId);
		String patientPayload = RestUtils.preparePatient(testData.getPatientPath(), practicePatientId,
				patient.getFirstName(), patient.getLastName(), patient.getDOBDay(), patient.getDOBMonth(),
				patient.getDOBYear(), patient.getEmail(), patient.getZipCode(), medfusionID);
		if (version.equalsIgnoreCase("v2"))
			patientPayload = patientPayload.replaceAll("v1", "v2");

		Thread.sleep(600);

		log("Step 5: Post Patient");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), patientPayload,
				testData.getResponsePath());

		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 60 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequestExceptOauth(processingUrl, testData.getResponsePath());
			if (RestUtils.isMessageProcessingCompleted(testData.getResponsePath())) {
				completed = true;

			}
		}
		Assert.assertTrue(completed, "Message processing was not completed in time");

		log("Step 6: Do a GET on PIDC Url to get registered patient for version " + version);
		log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());

		log("Step 7: Find the patient and verify PracticePatientId/Medfusion Patient Id and Patient's demographics details");
		RestUtils.isPatientRegistered(testData.getResponsePath(), practicePatientId, patient.getFirstName(),
				patient.getLastName(), medfusionID);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientDemographicsUpdate(String version) throws Exception {
		log("Test Case: PIDC Patient Update for Race, Ethnicity, Gender and Language all the values for Version "
				+ version);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Set Test Data for Demographics update");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		PIDCInfo testData = new PIDCInfo();
		Long timestamp = System.currentTimeMillis();
		LoadPreTestDataObj.loadDataFromProperty(testData, version, "2.0");

		log("Step 2: LogIn to " + testData.getPortalURL());
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getUsername(), testData.getPassword());

		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoAccountPage accountPageObject = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage accountProfilePageObject = accountPageObject.clickOnEditMyAccount();
		RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(),
				testData.getoAuthUsername(), testData.getoAuthPassword());

		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.getCsvFilePath();
		Log4jUtil.log("Loading CSVfile : " + workingDir);
		PatientRegistrationUtils.csvFileReader(testData, workingDir);

		String dropValues[] = { "Race", "Ethnicity", "GenderIdentity", "SexualOrientation" };
		for (int k = 0; k < dropValues.length; k++) {
			log("Updating Values of '" + dropValues[k] + "' field");
			int count = accountProfilePageObject.countDropDownValue(dropValues[k].charAt(0));
			log("Total number of values in '" + dropValues[k] + "' field drop-down:" + count);
			for (int i = 0; i < count; i++) {
				String updatedValue = accountProfilePageObject.updateDropDownValue(i, dropValues[k].charAt(0));
				if (updatedValue.equalsIgnoreCase("Declined to Answer")
						&& dropValues[k].equalsIgnoreCase(IntegrationConstants.RACE)) {
					updatedValue = "Unreported or refused to report";
				}
				if (updatedValue.equalsIgnoreCase("Declined to Answer")
						&& dropValues[k].equalsIgnoreCase(IntegrationConstants.ETHINICITY)) {
					updatedValue = "Unreported";
				}
				Thread.sleep(40000);
				Long since = timestamp / 1000L - 60 * 24;
				char dropValue = dropValues[k].charAt(0);
				if (dropValue == 'G') {
					dropValue = 'I';
				}
				log("dropValue " + dropValue);
				if (!updatedValue.equalsIgnoreCase("Choose One")) {

					if (dropValues[k].equalsIgnoreCase(IntegrationConstants.GENDERIDENTITY)) {
						updatedValue = testData.patientDetailList.get(i).getGenderIdentity();
					}
					if (dropValues[k].equalsIgnoreCase(IntegrationConstants.SEXUALORIENTATION)) {
						updatedValue = testData.patientDetailList.get(i).getSexualOrientation();
					}
					log("Updated Value :" + updatedValue);
					log("Do a GET on PIDC Url to fetch updated patient for " + version);
					String responseCodeV = RestUtils.setupHttpGetRequestWithEmptyResponse(
							testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
					if (!responseCodeV.equalsIgnoreCase("204")) {
						Thread.sleep(800);
						RestUtils.validateNode(testData.getResponsePath(), updatedValue, dropValue,
								testData.getPracticeId_PIDC_20());
					}
				}
			}
		}

		String updatedGIValue = accountProfilePageObject.updateDropDownValue(0, 'G');
		String updatedSOValue = accountProfilePageObject.updateDropDownValue(0, 'S');
		log("Gender Identity is " + updatedGIValue + " and Sexual Orientation is " + updatedSOValue + " for version"
				+ version);
		timestamp = System.currentTimeMillis();
		int genderLength = 3;
		String[] gender = new String[genderLength];
		String gVal = null;
		for (int g = 1; g <= genderLength; g++) {
			gVal = testData.patientDetailList.get(g).getGender();
			if (gVal.equalsIgnoreCase("UNKNOWN")) {
				gVal = "Decline to answer";
			}
			gender[(g - 1)] = gVal;
		}
		Thread.sleep(300);
		for (int m = 0; m < gender.length; m++) {
			log("gender to update  : " + gender[m]);
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,350)", "");
			Thread.sleep(5000);
			String updatedValue = accountProfilePageObject.updateGenderValue(m, gender[m].charAt(0));
			if (updatedValue.equalsIgnoreCase("Decline to answer")) {
				updatedValue = "UNKNOWN";
			}
			log("Updated Value :" + updatedValue);
			Thread.sleep(40000);
			Long since = timestamp / 1000L - 60 * 24;
			log("Do a GET on PIDC Url to fetch updated patient for " + version);
			String responseCode = RestUtils.setupHttpGetRequestWithEmptyResponse(
					testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
			Thread.sleep(800);
			if (!responseCode.equalsIgnoreCase("204")) {
				if (version.equalsIgnoreCase("v1") && updatedValue == "UNKNOWN") {
					// --
				} else {
					RestUtils.validateNode(testData.getResponsePath(), updatedValue, 'G',
							testData.getPracticeId_PIDC_20());
				}
			} else {
				if (version.equalsIgnoreCase("v1") && updatedValue == "UNKNOWN") {
					log("Empty response for UNKNOWN value update with v1 api call");
				}
			}
		}

		Thread.sleep(8000);
		log("Step 4: Click on Preferences Tab");
		JalapenoMyAccountPreferencesPage myPreferencePage = accountProfilePageObject.goToPreferencesTab(driver);
		Thread.sleep(10000);
		String[] languageType = testData.getPreferredLanguageType().split(",");
		Long since1 = timestamp / 1000L - 60 * 24;
		for (int v = 0; v < languageType.length; v++) {
			myPreferencePage.setStatementLanguage(driver, languageType[v]);
			if (languageType[v].equalsIgnoreCase("Declined to Answer")) {
				languageType[v] = "Other";
			}
			Thread.sleep(40000);
			log("Do a GET on PIDC Url to fetch updated patient for " + version);
			String languageResponse = RestUtils.setupHttpGetRequestWithEmptyResponse(
					testData.getRestUrl() + "?since=" + since1 + ",0", testData.getResponsePath());
			if (!languageResponse.equalsIgnoreCase("204")) {
				Thread.sleep(800);
				RestUtils.validateNode(testData.getResponsePath(), languageType[v], 'L',
						testData.getPracticeId_PIDC_20());
			}
		}
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientDemographicsUpdateWithSpecialCharacter(String version) throws Exception {
		log("Step 1: Test Case: Patient Update with special character data");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		PIDCInfo testData = new PIDCInfo();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		Long timestamp = System.currentTimeMillis();
		LoadPreTestDataObj.loadDataFromProperty(testData, version, "2.0");

		log("Step 2: LogIn to ");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getPortalURL());
		JalapenoHomePage homePage = loginPage.login(testData.getUsername(), testData.getPassword());
		Thread.sleep(800);

		log("Step 3: Click on myaccountLink on MyPatientPage");
		JalapenoAccountPage accountPageObject = homePage.clickOnAccount();
		JalapenoMyAccountProfilePage accountProfilePageObject = accountPageObject.clickOnEditMyAccount();
		Thread.sleep(3000);
		List<String> patientData = new ArrayList<String>();
		String randomString = IHGUtil.createRandomNumericString();
		patientData.add("Fname" + "'" + randomString);
		patientData.add("TestPatient" + "'" + randomString);
		patientData.add("Line1" + "&" + randomString);
		patientData.add('"' + randomString + '"');
		patientData.add("1" + IHGUtil.createRandomNumericString());
		patientData.add("1/1/1987");
		patientData.add("2");
		patientData.add("White");
		patientData.add("Hispanic or Latino");
		patientData.add(null);
		patientData.add(null);
		patientData.add("12345");

		log("Step 4: Update patient demographics datails with special charcters data");
		accountProfilePageObject.updateDemographics(patientData);
		Thread.sleep(40000);
		Long since = timestamp / 1000L - 60 * 24;

		log("Step 5: Invoke Get PIDC and verify patient details for version " + version);
		RestUtils.setupHttpGetRequestExceptoAuth(testData.getRestUrl() + "?since=" + since + ",0",
				testData.getResponsePath());
		Thread.sleep(10000);
		RestUtils.checkPatientRegistered(testData.getResponsePath(), patientData);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientRegistrationfromPractice(String version) throws Exception {
		log("Test Case: Patient Registration from Practice Portal" + version);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		PIDCInfo testData = new PIDCInfo();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		Long timestamp = System.currentTimeMillis();

		log("Step 1: Load Data from Property file");
		LoadPreTestDataObj.loadDataFromProperty(testData, version, "2.0");

		log("Step 2: Login to Portal 2.0");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getPracticeURL());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getPracticeUserName(),
				testData.getPracticePassword());

		log("Step 3: Click on Patient Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();

		log("Step 4: Click on Add new Patient");
		PatientActivationPage patientActivationPage = patientSearchPage.clickOnAddNewPatient();

		log("Step 5: Enter all the details and click on Register");
		patientActivationPage.setFullDetails(testData.getEmail(), testData.getLastName(), testData.getHomePhoneNo(),
				testData.getAddress1(), testData.getAddress2(), testData.getCity(), testData.getState(),
				testData.getZipCode());
		String firstNameString = patientActivationPage.getFirstNameString();
		String patientIdString = patientActivationPage.getPatientIdString();
		String emailAddressString = patientActivationPage.getEmailAddressString();
		String firstName = "mf.patient" + IHGUtil.createRandomNumericString();
		;
		String unlocklink = patientActivationPage.getUnlockLink();

		log("Step 6: Logout of Practice Portal");
		practiceHome.logOut();
		String[] Date = testData.getBirthDay().split("/");

		log("Step 7: Moving to linkUrl to finish Create Patient procedure");
		PatientVerificationPage patientAccountActivationPage = new PatientVerificationPage(driver, unlocklink);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientAccountActivationPage
				.fillPatientInfoAndContinue(testData.getZipCode(), Date[1], Date[0], Date[2]);

		log("Step 8: Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(firstName,
				testData.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),
				testData.getHomePhoneNo());

		log("Step 9: Detecting if Home Page is opened");
		Thread.sleep(2000);
		Assert.assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));

		log("Step 10: Do a GET on PIDC Url to get registered patient for version " + version);
		Long since = timestamp / 1000L - 60 * 24;

		log("Step 11: Setup Oauth client");
		RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(),
				testData.getoAuthUsername(), testData.getoAuthPassword());

		RestUtils.setupHttpGetRequest(testData.getRestUrl_20() + "?since=" + since + ",0", testData.getResponsePath());

		firstNameString = firstNameString.replaceAll("&amp;", "&");
		List<String> patientData = new ArrayList<String>();
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

		log("Step 12: Find the patient and check if he is registered");
		RestUtils.isPatientRegistered(testData.getResponsePath(), patientIdString, patientData.get(0),
				patientData.get(1), null);

		log("Step 13: Verify patient Demographics Details");
		RestUtils.verifyPatientDetails(testData.getResponsePath(), patientIdString, patientData, null);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUnseenMessageList() throws Exception {
		log("Test Case: Get Unseen Messages");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		long epoch = System.currentTimeMillis();
		String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new java.util.Date(epoch));
		log("currentDate " + currentDate);

		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);

		log("Step 2 : Set up Oauth Token");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		log("Step 3 : Mark all UnseenMesaages as READ");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
		RestUtils.readUnseenMessages(testData.ResponsePath, testData.messageStatusUpdate);

		log("Step 4 : Check for 200 response when no UnseenMessages");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
		int NoOfUnreadMessage = RestUtils.readUnseenMessages(testData.ResponsePath, testData.messageStatusUpdate);
		log("NoOfUnreadMessage : " + NoOfUnreadMessage);
		Assert.assertEquals(NoOfUnreadMessage, 0);

		log("Step 5 : Post New Secure Message ");
		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
		SendDirectMessageUtilsObj.postSecureMessage(driver, testData, "xml");

		P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();

		P2PUnseenMessageListObject.verifyUnseenMessage(testData);
	}

	@DataProvider(name = "p2pattachmentType")
	public Object[][] sendDirectAttachment() {
		Object[][] obj = new Object[][] { { "none" }, { "pdf" }, { "xml" }, };
		return obj;
	}

	@Test(enabled = true, dataProvider = "p2pattachmentType", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUnseenMessageListAll(String attachment) throws Exception {
		log("Test Case: Get Unseen Messages with attachment type " + attachment);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);

		log("Step 2 : Set up Oauth Token");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		log("Step 3 : Mark all UnseenMesaages as READ");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
		RestUtils.readUnseenMessages(testData.ResponsePath, testData.messageStatusUpdate);

		log("Step 4 : Check for 200 response when no UnseenMessages");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
		int NoOfUnreadMessage = RestUtils.readUnseenMessages(testData.ResponsePath, testData.messageStatusUpdate);
		log("NoOfUnreadMessage : " + NoOfUnreadMessage);
		Assert.assertEquals(NoOfUnreadMessage, 0);
		P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();
		log("Step 5 : Post New Secure Message ");

		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();

		SendDirectMessageUtilsObj.postSecureMessage(driver, testData, attachment);
		P2PUnseenMessageListObject.verifyUnseenMessage(testData);

	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testErrorCasesUnseenMessageList() throws Exception {
		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
		P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();
		String invalidPracticeId = testData.messageHeaderURL + testData.invalidPracticeMessageHeaderURL
				+ "/directmessageheaders/" + testData.ToEmalID;

		log("Step 2 : Set up Oauth Token");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		log("Step 3 : Get Unseen Message Header and Verify For Invalid PracticeID");
		int responseCode = RestUtils.setupHttpGetRequestInvalid(invalidPracticeId, testData.ResponsePath);
		Assert.assertEquals(responseCode, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<body>(.+?)</body>",
				testData.invalidPracticeMessageHeaderURL);

		log("Step 4 : Get Unseen Message Header and Verify For Invalid Email ID");
		String invalidEmailID = testData.messageHeaderURL + testData.validPracticeID + "/directmessageheaders/"
				+ testData.invalidEmailMessageHeaderURL;
		int responseCodeE = RestUtils.setupHttpGetRequestInvalid(invalidEmailID, testData.ResponsePath);
		Assert.assertEquals(responseCodeE, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				testData.invalidEmailMessageHeaderURL);

		log("Step 5 : Get Unseen Message Body and Verify For Invalid Message Uid");
		String getMessageBody = testData.messageHeaderURL + testData.validPracticeID + "/directmessage/"
				+ testData.ToEmalID + "/message/" + testData.invalidUID;
		log("getMessageBody :" + getMessageBody);
		int responseCodeUid = RestUtils.setupHttpGetRequestInvalid(getMessageBody, testData.ResponsePath);
		Assert.assertEquals(responseCodeUid, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				testData.invalidUID);

		log("Step 6 : Get Unseen Message Body and Verify For Invalid Message Uid");
		String getMessageBodyIE = testData.messageHeaderURL + testData.validPracticeID + "/directmessage/"
				+ testData.invalidEmailMessageHeaderURL + "/message/1";
		log("getMessageBodyInvalidEmail :" + getMessageBodyIE);
		int responseCodeIEmail = RestUtils.setupHttpGetRequestInvalid(getMessageBodyIE, testData.ResponsePath);
		Assert.assertEquals(responseCodeIEmail, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				testData.invalidEmailMessageHeaderURL);

		log("Step 7 : Get Unseen Message Status Update and Verify For Invalid Message Status");
		String invalidMessageUpdateStatusURL = testData.messageHeaderURL + testData.validPracticeID + "/directmessage/"
				+ testData.ToEmalID + "/message/1/status/UNREAD";

		int invalidResponseStatus = RestUtils.setupHttpPostInvalidRequest(invalidMessageUpdateStatusURL, "",
				testData.ResponsePath);
		Assert.assertEquals(invalidResponseStatus, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				"UNREAD");

		log("Step 8 : Get Unseen Message Status Update and Verify For Invalid Message Uid");
		String invalidMessageUIDURL = testData.messageHeaderURL + testData.validPracticeID + "/directmessage/"
				+ testData.ToEmalID + "/message/" + testData.invalidUID + "/status/NEW";
		int invalidResponseUID = RestUtils.setupHttpPostInvalidRequest(invalidMessageUIDURL, "", testData.ResponsePath);
		Assert.assertEquals(invalidResponseUID, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				testData.invalidUID);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testErrorCasesDeleteMessage() throws Exception {

		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
		P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();

		log("Step 2 : Call Delete Message API with Invalid Message Uid");
		String invalidMessageUIDURLDelete = testData.messageHeaderURL + testData.validPracticeID + "/directmessage/"
				+ testData.ToEmalID + "/message/" + testData.invalidUID + "/delete";
		log(invalidMessageUIDURLDelete);
		int responseCodeInvalidMsgDelete = RestUtils.setupHttpDeleteRequestExceptOauth(invalidMessageUIDURLDelete,
				testData.ResponsePath, testData.token);
		log("responseCode for InvalidMsg Delete API is " + responseCodeInvalidMsgDelete);
		Assert.assertEquals(responseCodeInvalidMsgDelete, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				testData.invalidUID);

		log("Step 3 : Call Delete Message API with Invalid Email Address");
		String invalidEmailIDDelete = testData.messageHeaderURL + testData.validPracticeID + "/directmessage/"
				+ testData.invalidEmailMessageHeaderURL + "/message/1/delete";
		int responseCodeInvalidEmailDelete = RestUtils.setupHttpDeleteRequestExceptOauth(invalidEmailIDDelete,
				testData.ResponsePath, testData.token);
		log("responseCode for InvalidEmailDelete is " + responseCodeInvalidEmailDelete);
		Assert.assertEquals(responseCodeInvalidEmailDelete, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				testData.invalidEmailMessageHeaderURL);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteP2PMessageInMailBox() throws Exception {
		log("Test Case: To search for Deleted message in P2P MailBox");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
		P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();

		log("Step 2 : Post New Secure Message ");
		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
		SendDirectMessageUtilsObj.postSecureMessage(driver, testData, "none");

		log("Step 3 : Check for new Unseen Message ");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);

		log("Step 4 : Verify UnseenMessage and get its UID ");
		String msgUid = RestUtils.verifyUnseenMessageListAndGetMessageUID(testData.ResponsePath, testData.Subject);
		String getMessageBody = testData.unseenMessageBody;
		getMessageBody = getMessageBody + "/" + msgUid;
		log("msgUid is " + msgUid);

		log("Step 5 : Login to P2P MailBox and Delete the message");
		SendDirectMessageUtilsObj.deleteMessage(driver, testData.Subject, testData.SecureDirectMessageUsername,
				testData.SecureDirectMessagePassword, testData.SecureDirectMessageURL);

		log("Step 6 : execute Delete API and Verify the response");
		String messageDeleteURL = testData.messageStatusUpdate + "/" + msgUid + "/delete";
		int responseCode = RestUtils.setupHttpDeleteRequestExceptOauth(messageDeleteURL, testData.ResponsePath,
				testData.token);
		log("responseCode is " + responseCode + " message not found !!!");
		Assert.assertEquals(responseCode, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				msgUid);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteP2PMessage() throws Exception {
		log("Test Case: Delete P2P messages");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		long epoch = System.currentTimeMillis();
		String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new java.util.Date(epoch));
		log("currentDate " + currentDate);

		log("Step 1 : Set Test Data for UnseenMessageList");
		SendDirectMessage testData = new SendDirectMessage();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadSendDirectMessageDataFromProperty(testData);
		P2PUnseenMessageList P2PUnseenMessageListObject = new P2PUnseenMessageList();

		log("Step 2 : Post New Read Secure Message ");
		SendDirectMessageUtils SendDirectMessageUtilsObj = new SendDirectMessageUtils();
		SendDirectMessageUtilsObj.postSecureMessage(driver, testData, "none");
		String subject1 = testData.Subject;
		log("subject1 " + subject1);

		log("Step 3 : Check for new Unseen Message ");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);

		log("Step 4 : Verify UnseenMessage and get its UID ");
		String msgUid = RestUtils.verifyUnseenMessageListAndGetMessageUID(testData.ResponsePath, testData.Subject);
		String getMessageBody = testData.unseenMessageBody;
		getMessageBody = getMessageBody + "/" + msgUid;
		log("msgUid is " + msgUid);

		log("Step 5 : Post New Unread Secure Message ");
		SendDirectMessageUtilsObj.postSecureMessage(driver, testData, "none");
		String subject2 = testData.Subject;
		log("subject2 " + subject2);
		log("Step 6 : Check for new Unseen Message 2 ");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);
		Thread.sleep(15000);
		log("Step 7 : Verify UnseenMessage and get its UID ");
		String msgUid1 = RestUtils.verifyUnseenMessageListAndGetMessageUID(testData.ResponsePath, testData.Subject);
		String getMessageBody1 = testData.unseenMessageBody;
		getMessageBody1 = getMessageBody1 + "/" + msgUid1;
		log("msgUid1 is " + msgUid1);
		Assert.assertTrue(!msgUid1.isEmpty(), "Message UUID not found ");
		String messageUpdateURL1 = testData.messageStatusUpdate + "/" + msgUid + "/status/"
				+ testData.messageStatusToUpdate;
		log("Step 8 : read messageURL 1 : " + messageUpdateURL1);

		String messageUpdateURL2 = testData.messageStatusUpdate + "/" + msgUid1 + "/delete";
		log("Step 9 : unread messageURL 2 : " + messageUpdateURL2);

		log("Step 10 : Post  message to Update message Status to READ");
		RestUtils.setupHttpPostRequest(messageUpdateURL1, " ", testData.ResponsePath);

		log("Step 11: Post Read message to delete with message Status as DELETE");
		messageUpdateURL1 = messageUpdateURL1.replaceAll("status/READ", "delete");
		int responseCode1 = RestUtils.setupHttpDeleteRequestExceptOauth(messageUpdateURL1, testData.ResponsePath,
				testData.token);
		log("responseCode1 is " + responseCode1);
		Assert.assertEquals(responseCode1, 200);

		log("Step 12: Post Unread message to delete with message Status as DELETE");
		int responseCode2 = RestUtils.setupHttpDeleteRequestExceptOauth(messageUpdateURL2, testData.ResponsePath,
				testData.token);
		log("responseCode2 is " + responseCode2);
		Assert.assertEquals(responseCode2, 200);

		log("Step 13: Verify deletion of read message in get getMessageBody API " + messageUpdateURL1);
		int responseCodeE = RestUtils.setupHttpDeleteRequestExceptOauth(messageUpdateURL1, testData.ResponsePath,
				testData.token);
		log("responseCodeE is " + responseCodeE);
		String ErrorMsg1 = "Error GetMessage No Message for Message Uid = " + msgUid + ".";
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				ErrorMsg1);

		log("Step 14: Verify deletion of unread message message in getMessageBody API  " + messageUpdateURL2);
		int responseCodeE1 = RestUtils.setupHttpDeleteRequestExceptOauth(messageUpdateURL2, testData.ResponsePath,
				testData.token);
		log("responseCodeE1 is " + responseCodeE1);
		String ErrorMsg2 = "Error GetMessage No Message for Message Uid = " + msgUid1 + ".";
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				ErrorMsg2);

		log("Step 15 : Check for new Unseen Message ");
		RestUtils.setupHttpGetRequest(testData.unseenMessageHeader, testData.ResponsePath);

		log("Step 16 : Verify deleted read message in messageHeaders API");
		String msgReadUid = RestUtils.verifyUnseenMessageListAndGetMessageUID(testData.ResponsePath, subject1);
		log("Is Read Message UUID Empty :" + msgReadUid.isEmpty());
		Assert.assertTrue(msgReadUid.isEmpty());

		log("Step 17 : Verify deleted unread message in messageHeaders API");
		String msgUnreadUid = RestUtils.verifyUnseenMessageListAndGetMessageUID(testData.ResponsePath, subject2);
		log("Is Unread Message UUID Empty :" + msgUnreadUid.isEmpty());
		Assert.assertTrue(msgUnreadUid.isEmpty());

		log("Step 18 : Call Delete Message API from Sender Email Address");
		String senderEmail = testData.messageHeaderURL + testData.validPracticeID + "/directmessage/"
				+ testData.FromEmalID + "/message/" + msgUid + "/delete";
		log(senderEmail);
		int senderEmailID = RestUtils.setupHttpDeleteRequestExceptOauth(senderEmail, testData.ResponsePath,
				testData.token);
		log("responseCode for InvalidEmailDelete is " + senderEmailID);
		Assert.assertEquals(senderEmailID, 400);
		P2PUnseenMessageListObject.ExtractErrorMessage(testData.ResponsePath, "<ErrorResponse>(.+?)</ErrorResponse>",
				subject1);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDE2EFormExportfromPractice() throws Exception {
		log("Test Case: Fill CCD Form and Verify the Details in Export");
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);
		FormsExportUtils formUtilsObject = new FormsExportUtils();
		formUtilsObject.ccdExchangeFormsImport(driver, 0, testData);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDE2EFormExportfromPracticeRegression() throws Exception {
		log("Test Case: Fill CCD Form and Verify the Details in Export Regression");
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);

		FormsExportUtils formUtilsObject = new FormsExportUtils();
		formUtilsObject.ccdExchangeFormsImport(driver, 1, testData);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreCheckForms() throws Exception {
		Log4jUtil.log("Test Case: Fill Pre check CCD Form and Verify the Details in Export ");
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);
		FormsExportUtils formUtilsObject = new FormsExportUtils();
		Log4jUtil.log("Step 1: Load forms data from external files");
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.patientfilepath_FE;
		formUtilsObject.setFormsTestData(workingDir, testData);
		String randomString = IHGUtil.createRandomNumericString();
		Long timestamp = System.currentTimeMillis();

		Log4jUtil.log("Step 2: Fill in Pre check form 14 pages");
		String firstName = testData.patientFirstName_FE + randomString;
		log("firstName " + firstName);
		driver.get(testData.preCheckURL);
		driver.manage().window().maximize();

		Thread.sleep(6000);
		// Temp code until we have page objects
		WebElement pageNumber = driver.findElement(By.xpath("//*[@id=\"page-header-current\"]"));
		log("we are at page number " + pageNumber.getText());
		int size = 1;
		if (pageNumber.getText().equalsIgnoreCase("")) {
			driver.findElement(By.id("continueWelcomePageButton")).click();
			;
		} else {
			size = Integer.parseInt(pageNumber.getText());
		}
		if (!pageNumber.getText().equalsIgnoreCase("1")) {
			WebElement prevPage = driver.findElement(By.xpath("//*[@id=\"prevPageButton\"]"));
			for (int i = size; i > 1; i--) {
				prevPage.click();
			}
		}
		formUtilsObject.fillForm(driver, testData, firstName, true);

		Thread.sleep(8000);
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Step 3: Set up Oauth");
		RestUtils.oauthSetup(testData.oAuthKeyStore1_FE, testData.oAuthProperty1_FE, testData.oAuthAppTokenCCD1_FE,
				testData.oAuthUsernameCCD1_FE, testData.oAuthPasswordCCD1_FE);
		String getURL = testData.ccd_url1_FE + "Batch";
		Log4jUtil.log("CCD _URL is " + testData.ccd_url1_FE);
		RestUtils.setupHttpGetRequest(getURL + "?since=" + since + ",0", testData.responsePath_CCD1_FE);
		Thread.sleep(2000);
		long timeStamp204 = System.currentTimeMillis();
		Long since1 = timeStamp204 / 1000 - 60 * 24;

		Log4jUtil.log("Step 4: Verify patient Details in get ccdExchangeBatch API");
		RestUtils.verifyPatientCCDFormInfo(testData.responsePath_CCD1_FE, formUtilsObject.list);
		RestUtils.isPreCheckPatientAppeared(testData.responsePath_CCD1_FE, testData.preCheckPatientExternalID,
				firstName);
		String ccdExchangeBatchPdfLink = RestUtils.verifyCCDHeaderDetailsandGetURL(testData.responsePath_CCD1_FE,
				testData.preCheckPatientExternalID);
		getURL = testData.ccd_PDfUrl_FE + "Batch";
		log("Verify --- PDF " + getURL);
		Log4jUtil.log("Step 5: Verify patient Details in get ccdExchangePdfBatch API");
		RestUtils.setupHttpGetRequest(getURL + "?since=" + since1 + ",0", testData.responsePath_CCD1_FE);
		String PreCheckPdfLink = RestUtils.verifyPreCheckPDFBatchDetails(testData.responsePath_CCD1_FE,
				testData.preCheckPatientExternalID);
		Log4jUtil.log("Step 6: Download pre check form pdf");
		log("Download pdf link " + PreCheckPdfLink);
		log("Asserting FormsPdfLink for Precheck from ccdExchangeBatch and ccdExchangePdfBatch");
		Assert.assertEquals(ccdExchangeBatchPdfLink, PreCheckPdfLink);
		log("Do a ccdExchangePdf call with the link found in FormsPdfLink");
		RestUtils.setupHttpGetRequestForPDF(PreCheckPdfLink, testData.responsePDF_FE);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdatesOnlyDemographics() throws Exception {
		Log4jUtil.log("Test Case: Precheck Appointment ");
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);
		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log("Step 5: Create data for Pre check Appointment form to Fill ");
		List<String> patientData = new ArrayList<String>();
		String randomString = IHGUtil.createRandomNumericString();

		patientData.add("Fname" + "'" + randomString);
		patientData.add("TestPatient" + "'" + randomString);
		patientData.add("Line1" + "&" + randomString);
		patientData.add('"' + randomString + '"');
		patientData.add("MName" + randomString);
		patientData.add("01 01/1987");
		patientData.add("New York");
		patientData.add("NY");
		patientData.add("12345");
		patientData.add("9876543210");
		patientData.add("xyz" + randomString + "@mailinator.com");
		patientData.add("GUEST");
		patientData.add("PRECHECK");
		for (int i = 0; i <= 6; i++) {
			patientData.add(null);
		}

		Thread.sleep(6000);
		log("Step 6: Verify precheck patient zip and dateofBirth posted");
		log(testData.preCheckZip + "  =  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		log("Step 7: Select the appointment which is posted and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);
		Thread.sleep(6000);
		MyDemoGraphicsDetailPage myDemoGraphicPage = myAppointment.gotoDemographicsDetailPage();
		// Set DemoGraphic Data
		myDemoGraphicPage.setFirstName(patientData.get(0));
		myDemoGraphicPage.setLastName(patientData.get(1));
		myDemoGraphicPage.setStreet1Address(patientData.get(2));
		myDemoGraphicPage.setStreet2Address(patientData.get(3));
		myDemoGraphicPage.setMiddleName(patientData.get(4));
		myDemoGraphicPage.setDateOfBirth(patientData.get(5));
		myDemoGraphicPage.setCity(patientData.get(6));
		myDemoGraphicPage.setState(patientData.get(7));
		myDemoGraphicPage.setZip(patientData.get(8));
		myDemoGraphicPage.setPhone(patientData.get(9));
		myDemoGraphicPage.setEmail(patientData.get(10));
		myAppointment = myDemoGraphicPage.clickDemographicsSaveButton();

		preAppointmentObj.verifyPatientDetail(testData, patientData);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdatesOnlyInsurance() throws Exception {
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);

		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log("Step 5: Create data for Pre check Appointment form to Fill ");
		List<String> patientData = new ArrayList<String>();
		String randomString = IHGUtil.createRandomNumericString();
		for (int i = 0; i <= 10; i++) {
			patientData.add(null);
		}
		patientData.add("GUEST");
		patientData.add("PRECHECK");
		patientData.add("bajaj");
		patientData.add("alliance");
		patientData.add("0" + randomString);
		patientData.add("1" + randomString);
		patientData.add("01 01/1987");
		patientData.add("9876543210");

		Thread.sleep(6000);
		log("Step 6: Verify precheck patient zip and dateofBirth posted");
		log(testData.preCheckZip + "  =  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		log("Step 7: Select the appointment which is posted and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);
		MyInsuranceDetailPage myInsuranceDetailPage = myAppointment.gotoInsuranceDetailPage();
		PrimaryInsurancePage primaryInsurancePage = myInsuranceDetailPage.gotoInsuranceInfoPage();
		primaryInsurancePage.setPrimaryInsuranceName(patientData.get(13));
		primaryInsurancePage.setPrimarySubscriberName(patientData.get(14));
		primaryInsurancePage.setPrimarySubscriberID(patientData.get(15));
		primaryInsurancePage.setPrimaryGroupNumber(patientData.get(16));
		primaryInsurancePage.setPrimaryDateIssued(patientData.get(17));
		primaryInsurancePage.setClaimsPhoneNumberInput(patientData.get(18));
		primaryInsurancePage.submitPrimaryInsurance();

		preAppointmentObj.verifyPatientDetail(testData, patientData);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdates1stDemo2ndALLINS() throws Exception {
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);

		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log("Step 5: Create data for Pre check Appointment form to Fill ");
		List<String> patientData = new ArrayList<String>();
		String randomString = IHGUtil.createRandomNumericString();
		patientData.add("Fname" + "'" + randomString);
		patientData.add("TestPatient" + "'" + randomString);
		patientData.add("Line1" + "&" + randomString);
		patientData.add('"' + randomString + '"');
		patientData.add("MName" + randomString);
		patientData.add("01 01/1987");
		patientData.add("New York");
		patientData.add("NY");
		patientData.add("12345");
		patientData.add("9876543210");
		patientData.add("xyz" + randomString + "@mailinator.com");
		patientData.add("GUEST");
		patientData.add("PRECHECK");
		patientData.add("PrimaryBajaj");
		patientData.add("PrimaryAlliance");
		patientData.add("0" + randomString);
		patientData.add("1" + randomString);
		patientData.add("01 01/2017");
		patientData.add("9876543210");
		patientData.add("SecondaryBajaj");
		patientData.add("SecondaryAlliance");
		patientData.add("0" + randomString);
		patientData.add("1" + randomString);
		patientData.add("01 01/2017");
		patientData.add("7876543210");
		patientData.add("TertiaryBajaj");
		patientData.add("TertiaryAlliance");
		patientData.add("0" + randomString);
		patientData.add("1" + randomString);
		patientData.add("01 01/2017");
		patientData.add("8876543210");

		Thread.sleep(6000);
		log("Step 6: Verify precheck patient zip and dateofBirth posted");
		log(testData.preCheckZip + "  =  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		log("Step 7: Select the appointment which is posted and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);
		MyDemoGraphicsDetailPage myDemoGraphicPage = myAppointment.gotoDemographicsDetailPage();
		// Set DemoGraphic Detials
		log("Setting Demographics details");
		myDemoGraphicPage.setFirstName(patientData.get(0));
		myDemoGraphicPage.setLastName(patientData.get(1));
		myDemoGraphicPage.setStreet1Address(patientData.get(2));
		myDemoGraphicPage.setStreet2Address(patientData.get(3));
		myDemoGraphicPage.setMiddleName(patientData.get(4));
		myDemoGraphicPage.setDateOfBirth(patientData.get(5));
		myDemoGraphicPage.setCity(patientData.get(6));
		myDemoGraphicPage.setState(patientData.get(7));
		myDemoGraphicPage.setZip(patientData.get(8));
		myDemoGraphicPage.setPhone(patientData.get(9));
		myDemoGraphicPage.setEmail(patientData.get(10));

		myAppointment = myDemoGraphicPage.clickDemographicsSaveButton();
		Thread.sleep(6000);
		// Fill Insurance Details
		log("Setting Primary Insurance details");
		MyInsuranceDetailPage myInsuranceDetailPage = myAppointment.gotoInsuranceDetailPage();
		PrimaryInsurancePage primaryInsurancePage = myInsuranceDetailPage.gotoInsuranceInfoPage();
		primaryInsurancePage.setPrimaryInsuranceName(patientData.get(13));
		primaryInsurancePage.setPrimarySubscriberName(patientData.get(14));
		primaryInsurancePage.setPrimarySubscriberID(patientData.get(15));
		primaryInsurancePage.setPrimaryGroupNumber(patientData.get(16));
		primaryInsurancePage.setPrimaryDateIssued(patientData.get(17));
		primaryInsurancePage.setClaimsPhoneNumberInput(patientData.get(18));
		log("Setting Secondary Insurance details");
		SecondaryInsurancePage secondaryInsurancePage = primaryInsurancePage.gotoSecondaryInsurance();
		secondaryInsurancePage.setSecondaryInsuranceName(patientData.get(19));
		secondaryInsurancePage.setSecondarySubscriberName(patientData.get(20));
		secondaryInsurancePage.setSecondarySubscriberID(patientData.get(21));
		secondaryInsurancePage.setSecondaryGroupNumber(patientData.get(22));
		secondaryInsurancePage.setSecondaryDateIssued(patientData.get(23));
		secondaryInsurancePage.setClaimsPhoneNumber(patientData.get(24));
		log("Setting Tertiary Insurance details");
		TertiaryInsurancePage tertiaryInsurancePage = secondaryInsurancePage.gotoTertiaryInsurance();
		tertiaryInsurancePage.setTertiaryInsuranceName(patientData.get(25));
		tertiaryInsurancePage.setTertiarySubscriberName(patientData.get(26));
		tertiaryInsurancePage.setTertiarySubscriberID(patientData.get(27));
		tertiaryInsurancePage.setTertiaryGroupNumber(patientData.get(28));
		tertiaryInsurancePage.setTertiaryDateIssued(patientData.get(29));
		tertiaryInsurancePage.setTertiaryClaimsPhoneNumber(patientData.get(30));
		tertiaryInsurancePage.submitTertiaryInsurance();

		preAppointmentObj.verifyPatientDetail(testData, patientData);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdates1stPrimaryINS2ndDemo() throws Exception {
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);

		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log("Step 5: Create data for Pre check Appointment form to Fill ");
		List<String> patientData = new ArrayList<String>();
		String randomString = IHGUtil.createRandomNumericString();
		patientData.add("Fname" + "'" + randomString);
		patientData.add("TestPatient" + "'" + randomString);
		patientData.add("Line1" + "&" + randomString);
		patientData.add('"' + randomString + '"');
		patientData.add("MName" + randomString);
		patientData.add("01 01/1987");
		patientData.add("New York");
		patientData.add("NY");
		patientData.add("12345");
		patientData.add("9876543210");
		patientData.add("xyz" + randomString + "@mailinator.com");
		patientData.add("GUEST");
		patientData.add("PRECHECK");
		patientData.add("PrimaryBajaj");
		patientData.add("PrimaryAlliance");
		patientData.add("0" + randomString);
		patientData.add("1" + randomString);
		patientData.add("01 01/2017");
		patientData.add("9876543210");
		patientData.add("SecondaryBajaj");
		patientData.add("SecondaryAlliance");
		patientData.add("0" + randomString);
		patientData.add("1" + randomString);
		patientData.add("01 01/2017");
		patientData.add("9876543210");
		patientData.add("TertiaryBajaj");
		patientData.add("TertiaryAlliance");
		patientData.add("0" + randomString);
		patientData.add("1" + randomString);
		patientData.add("01 01/2017");
		patientData.add("9876543210");

		Thread.sleep(6000);
		log("Step 6: Verify precheck patient zip and dateofBirth posted");
		log(testData.preCheckZip + "  =  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		log("Step 7: Select the appointment created and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);

		MyInsuranceDetailPage myInsuranceDetailPage = myAppointment.gotoInsuranceDetailPage();
		PrimaryInsurancePage primaryInsurancePage = myInsuranceDetailPage.gotoInsuranceInfoPage();
		log("Setting Primary Insurance details");
		primaryInsurancePage.setPrimaryInsuranceName(patientData.get(13));
		primaryInsurancePage.setPrimarySubscriberName(patientData.get(14));
		primaryInsurancePage.setPrimarySubscriberID(patientData.get(15));
		primaryInsurancePage.setPrimaryGroupNumber(patientData.get(16));
		primaryInsurancePage.setPrimaryDateIssued(patientData.get(17));
		primaryInsurancePage.setClaimsPhoneNumberInput(patientData.get(18));
		log("Setting Secondary Insurance details");
		SecondaryInsurancePage secondaryInsurancePage = primaryInsurancePage.gotoSecondaryInsurance();
		secondaryInsurancePage.setSecondaryInsuranceName(patientData.get(19));
		secondaryInsurancePage.setSecondarySubscriberName(patientData.get(20));
		secondaryInsurancePage.setSecondarySubscriberID(patientData.get(21));
		secondaryInsurancePage.setSecondaryGroupNumber(patientData.get(22));
		secondaryInsurancePage.setSecondaryDateIssued(patientData.get(23));
		secondaryInsurancePage.setClaimsPhoneNumber(patientData.get(24));
		log("Setting Tertiary Insurance details");
		TertiaryInsurancePage tertiaryInsurancePage = secondaryInsurancePage.gotoTertiaryInsurance();
		tertiaryInsurancePage.setTertiaryInsuranceName(patientData.get(25));
		tertiaryInsurancePage.setTertiarySubscriberName(patientData.get(26));
		tertiaryInsurancePage.setTertiarySubscriberID(patientData.get(27));
		tertiaryInsurancePage.setTertiaryGroupNumber(patientData.get(28));
		tertiaryInsurancePage.setTertiaryDateIssued(patientData.get(29));
		tertiaryInsurancePage.setTertiaryClaimsPhoneNumber(patientData.get(30));
		myAppointment = tertiaryInsurancePage.submitTertiaryInsurance();

		Thread.sleep(6000);
		MyDemoGraphicsDetailPage myDemoGraphicPage = myAppointment.gotoDemographicsDetailPage();
		// Set DemoGraphic Detials
		log("Setting Demographics details");
		myDemoGraphicPage.setFirstName(patientData.get(0));
		myDemoGraphicPage.setLastName(patientData.get(1));
		myDemoGraphicPage.setStreet1Address(patientData.get(2));
		myDemoGraphicPage.setStreet2Address(patientData.get(3));
		myDemoGraphicPage.setMiddleName(patientData.get(4));
		myDemoGraphicPage.setDateOfBirth(patientData.get(5));
		myDemoGraphicPage.setCity(patientData.get(6));
		myDemoGraphicPage.setState(patientData.get(7));
		myDemoGraphicPage.setZip(patientData.get(8));
		myDemoGraphicPage.setPhone(patientData.get(9));
		myDemoGraphicPage.setEmail(patientData.get(10));

		myAppointment = myDemoGraphicPage.clickDemographicsSaveButton();
		Thread.sleep(6000);

		preAppointmentObj.verifyPatientDetail(testData, patientData);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdates1stDemo2ndPrimaryINS() throws Exception {
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);

		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log("Step 5: Create data for Pre check Appointment form to Fill ");
		List<String> patientData = new ArrayList<String>();
		String randomString = IHGUtil.createRandomNumericString();
		patientData.add("Fname" + "'" + randomString);
		patientData.add("TestPatient" + "'" + randomString);
		patientData.add("Line1" + "&" + randomString);
		patientData.add('"' + randomString + '"');
		patientData.add("MName" + randomString);
		patientData.add("01 01/1987");
		patientData.add("New York");
		patientData.add("NY");
		patientData.add("12345");
		patientData.add("9876543210");
		patientData.add("xyz" + randomString + "@mailinator.com");
		patientData.add("GUEST");
		patientData.add("PRECHECK");
		patientData.add("PrimaryBajaj");
		patientData.add("PrimaryAlliance");
		patientData.add("0" + randomString);
		patientData.add("1" + randomString);
		patientData.add(null);
		patientData.add("9876543210");

		Thread.sleep(6000);
		log("Step 6: Verify precheck patient zip and dateofBirth posted");
		log(testData.preCheckZip + "  =  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		log("Step 7: Select the appointment created and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);
		MyDemoGraphicsDetailPage myDemoGraphicPage = myAppointment.gotoDemographicsDetailPage();
		// Set DemoGraphic Detials
		log("Setting Demographics details");
		myDemoGraphicPage.setFirstName(patientData.get(0));
		myDemoGraphicPage.setLastName(patientData.get(1));
		myDemoGraphicPage.setStreet1Address(patientData.get(2));
		myDemoGraphicPage.setStreet2Address(patientData.get(3));
		myDemoGraphicPage.setMiddleName(patientData.get(4));
		myDemoGraphicPage.setDateOfBirth(patientData.get(5));
		myDemoGraphicPage.setCity(patientData.get(6));
		myDemoGraphicPage.setState(patientData.get(7));
		myDemoGraphicPage.setZip(patientData.get(8));
		myDemoGraphicPage.setPhone(patientData.get(9));
		myDemoGraphicPage.setEmail(patientData.get(10));

		myAppointment = myDemoGraphicPage.clickDemographicsSaveButton();
		Thread.sleep(6000);
		// Fill Insurance Details
		MyInsuranceDetailPage myInsuranceDetailPage = myAppointment.gotoInsuranceDetailPage();
		PrimaryInsurancePage primaryInsurancePage = myInsuranceDetailPage.gotoInsuranceInfoPage();
		log("Setting Primary Insurance details- SubscriberID, GroupNumber and ClaimsPhoneNumber");
		// primaryInsurancePage.setPrimaryInsuranceName(patientData.get(13));
		// primaryInsurancePage.setPrimarySubscriberName(patientData.get(14));
		primaryInsurancePage.setPrimarySubscriberID(patientData.get(15));
		primaryInsurancePage.setPrimaryGroupNumber(patientData.get(16));
		// primaryInsurancePage.setPrimaryDateIssued(patientData.get(17));
		primaryInsurancePage.setClaimsPhoneNumberInput(patientData.get(18));
		primaryInsurancePage.submitPrimaryInsurance();

		preAppointmentObj.verifyPatientDetail(testData, patientData);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdatesNoChangeInDemographics() throws Exception {
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);

		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log("Step 5: Create data for Pre check Appointment form to Fill ");
		List<String> patientData = new ArrayList<String>();
		for (int i = 0; i <= 10; i++) {
			patientData.add(null);
		}
		patientData.add("GUEST");
		patientData.add("PRECHECK");
		for (int i = 0; i <= 6; i++) {
			patientData.add(null);
		}
		Thread.sleep(6000);
		log("Step 6: Verify precheck patient zip and dateofBirth posted");
		log(testData.preCheckZip + "  =  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		log("Step 7: Select the appointment created and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);
		MyDemoGraphicsDetailPage myDemoGraphicPage = myAppointment.gotoDemographicsDetailPage();
		log("Submitting DemoGraphic");
		myAppointment = myDemoGraphicPage.clickDemographicsSaveButton();
		preAppointmentObj.verifyPatientDetail(testData, patientData);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdatesBLANKInsurance() throws Exception {
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);

		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log("Step 5: Create data for Pre check Appointment form to Fill ");
		List<String> patientData = new ArrayList<String>();
		for (int i = 0; i <= 10; i++) {
			patientData.add(null);
		}
		patientData.add("GUEST");
		patientData.add("PRECHECK");

		Thread.sleep(6000);
		log("Step 6: Verify precheck patient zip and dateofBirth posted");
		log(testData.preCheckZip + "  =  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		log("Step 7: Select the appointment created and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);
		MyInsuranceDetailPage myInsuranceDetailPage = myAppointment.gotoInsuranceDetailPage();
		PrimaryInsurancePage primaryInsurancePage = myInsuranceDetailPage.gotoInsuranceInfoPage();
		log("Submitting Blank Insurance");
		primaryInsurancePage.submitPrimaryInsurance();
		for (int i = 0; i <= 6; i++) {
			patientData.add(null);
		}
		preAppointmentObj.verifyPatientDetail(testData, patientData);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMUEventForGuardian(String version) throws Exception {
		Log4jUtil.log(
				"Test Case: Verification of CCD - VDT Events of patient account through Guardian account using ccd viewer.");
		log("Test case Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Read Test Data and set Values ");
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);

		EHDC EHDCObj = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(EHDCObj);
		log("Step 2: Send CCD to Patient");
		iEHDCSendCCD sendCCDObj = new SendCCD();
		ArrayList<String> ccdDetail = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, testData.patientUA_ExternalPatientID_MU2, EHDCObj.ccdXMLPath,
				testData.PATIENT_EXTERNAL_ID, testData.token);
		log(ccdDetail.get(0));
		Thread.sleep(8000);
		testData.CCDMessageID1 = ccdDetail.get(0);
		MU2Utils MU2UtilsObj = new MU2Utils();
		MU2UtilsObj.mu2GetEventGuardian(testData, driver, false, true, version);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMUEventForExistingGuardian(String version) throws Exception {
		Log4jUtil.log(
				"Test Case: Verification of CCD - VDT Events of patient account through an Existing Guardian account using ccd viewer.");
		log("Test case Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Read Test Data and set Values ");
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);

		EHDC EHDCObj = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(EHDCObj);
		log("Step 2: Post CCD to Patient");

		iEHDCSendCCD sendCCDObj = new SendCCD();
		log("Send CCD to Patient");
		ArrayList<String> ccdDetail = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, testData.patientUA_ExternalPatientID_MU2_Existing, EHDCObj.ccdXMLPath,
				testData.PATIENT_EXTERNAL_ID, testData.token);
		log(ccdDetail.get(0));
		Thread.sleep(8000);
		testData.CCDMessageID1 = ccdDetail.get(0);

		MU2Utils MU2UtilsObj = new MU2Utils();
		MU2UtilsObj.mu2GetEventGuardian(testData, driver, true, true, version);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMUEventForNewGuardian(String version) throws Exception {
		Long timestamp = System.currentTimeMillis();
		Log4jUtil.log(
				"Test Case : Verification of CCD - VDT Events of New patient account through Guardian account using ccd viewer.");
		log("Test case Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Read Test Data and set Values ");
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);

		EHDC EHDCObj = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(EHDCObj);
		log("Step 2: Invite Patient via PIDC ");
		iPIDCSendPatientInvite sendPatientInviteObj = new SendPatientInvite();
		ArrayList<String> patientDetail = sendPatientInviteObj.sendPatientInviteToPractice(
				testData.PATIENT_INVITE_RESTURL, testData.PATIENT_PRACTICEID, testData.PATIENT_EXTERNAL_ID,
				"01/01/2010", "27560", testData.token);

		log("Follwing are patient details");
		for (String values : patientDetail) {
			log(" " + values);
		}
		log("checking email for activation UrL link");
		Thread.sleep(5000);
		log("Step 3: Check and extract Invite link in patient Email");
		Mailinator mail = new Mailinator();
		String activationUrl = mail.getLinkFromEmail(patientDetail.get(4),
				"You are invited to create a Patient Portal guardian account at PI Automation rsdk Integrated",
				PortalConstants.NewPatientActivationMessageLinkText, 20);
		assertTrue(activationUrl != null, "Error: Activation link not found.");

		log("Step 4: Register under age patient");
		PatientRegistrationUtils.underAgeRegisterPatient(activationUrl, patientDetail.get(4), testData.PatientPassword,
				testData.SecretQuestion, testData.SecretAnswer, testData.HomePhoneNo, driver, patientDetail.get(2),
				patientDetail.get(3));

		Thread.sleep(12000);
		log("Step 2:  Send CCD to Patient");
		iEHDCSendCCD sendCCDObj = new SendCCD();

		log("Step 5: Post CCD to Patient");
		ArrayList<String> ccdDetail = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, patientDetail.get(0), EHDCObj.ccdXMLPath, testData.PATIENT_EXTERNAL_ID,
				testData.token);
		log(ccdDetail.get(0));
		Thread.sleep(8000);
		testData.CCDMessageID1 = ccdDetail.get(0);

		log("Step 6: Set Up oauth");
		RestUtils.oauthSetup(testData.OAUTH_KEYSTORE, testData.OAUTH_PROPERTY, testData.OAUTH_APPTOKEN,
				testData.OAUTH_USERNAME, testData.OAUTH_PASSWORD);

		log("Step 7: Get PIDC to extract patient medfusion ID");
		Long since = timestamp / 1000L - 60 * 24;
		log("Getting patients since timestamp: " + since);
		log("PUSH_RESPONSEPATH: " + testData.PUSH_RESPONSEPATH);
		RestUtils.setupHttpGetRequest(testData.PATIENT_INVITE_RESTURL + "?since=" + since + ",0",
				testData.PUSH_RESPONSEPATH);

		MU2Utils MU2UtilsObj = new MU2Utils();
		String patientID = MU2UtilsObj.getMedfusionID(testData.PUSH_RESPONSEPATH, patientDetail.get(0));
		log("patientID : " + patientID);

		log("Step 8: Set values related to new guardian");
		testData.intuit_PATIENT_ID_MU2_Guardian = patientID;
		testData.patientUA_ExternalPatientID_MU2 = patientDetail.get(0);
		testData.guardian_Password_MU2 = testData.PatientPassword;
		testData.guardian_UserName_MU2 = patientDetail.get(4);
		testData.PatientFirstName_MU2 = patientDetail.get(0);
		testData.patientUA_MU2_LastName = patientDetail.get(1);

		MU2UtilsObj.mu2GetEventGuardian(testData, driver, false, true, patientID);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMUEventForGuardianFromHealthRecord(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);

		Log4jUtil.log(
				"Test Case: Verification of CCD - VDT Events of patient account through Guardian account using Health Record Page.");
		log("Test case Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Read Test Data and set Values ");
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);

		EHDC EHDCObj = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(EHDCObj);
		log("Step 2: Send CCD to Patient");
		iEHDCSendCCD sendCCDObj = new SendCCD();
		ArrayList<String> ccdDetail = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, testData.patientUA_ExternalPatientID_MU2, EHDCObj.ccdXMLPath,
				testData.PATIENT_EXTERNAL_ID, testData.token);
		log(ccdDetail.get(0));
		Thread.sleep(8000);

		ArrayList<String> ccdDetail1 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, testData.patientUA_ExternalPatientID_MU2, EHDCObj.ccdXMLPath,
				testData.PATIENT_EXTERNAL_ID, testData.token);
		log(ccdDetail1.get(0));
		Thread.sleep(8000);
		testData.CCDMessageID1 = ccdDetail.get(0);
		testData.CCDMessageID2 = ccdDetail1.get(0);

		MU2Utils MU2UtilsObj = new MU2Utils();
		MU2UtilsObj.mu2GetEventGuardian(testData, driver, false, false, version);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMUEventForExistingGuardianFromHealthRecord(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		Log4jUtil.log(
				"Test Case: Verification of CCD - VDT Events of patient account through an Existing Guardian account using Health Record Page.");
		log("Test case Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Read Test Data and set Values ");
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);

		EHDC EHDCObj = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(EHDCObj);
		log("Step 2: Post CCD to Patient");

		iEHDCSendCCD sendCCDObj = new SendCCD();
		log("Send CCD to Patient");
		ArrayList<String> ccdDetail = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, testData.patientUA_ExternalPatientID_MU2_Existing, EHDCObj.ccdXMLPath,
				testData.PATIENT_EXTERNAL_ID, testData.token);
		log(ccdDetail.get(0));
		Thread.sleep(8000);

		ArrayList<String> ccdDetail1 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, testData.patientUA_ExternalPatientID_MU2, EHDCObj.ccdXMLPath,
				testData.PATIENT_EXTERNAL_ID, testData.token);
		log(ccdDetail1.get(0));
		Thread.sleep(8000);
		testData.CCDMessageID1 = ccdDetail.get(0);
		testData.CCDMessageID2 = ccdDetail1.get(0);

		MU2Utils MU2UtilsObj = new MU2Utils();
		//
		MU2UtilsObj.mu2GetEventGuardian(testData, driver, true, false, version);
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMUEventForNewGuardianFromHealthRecord(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);

		Long timestamp = System.currentTimeMillis();
		Log4jUtil.log(
				"Test Case : Verification of CCD - VDT Events of New patient account through Guardian account using Health Record Page.");
		log("Test case Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Read Test Data and set Values ");
		MU2GetEventData testData = new MU2GetEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadAPITESTDATAFromProperty(testData);

		EHDC EHDCObj = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(EHDCObj);
		log("Step 2: Invite Patient via PIDC ");
		iPIDCSendPatientInvite sendPatientInviteObj = new SendPatientInvite();
		ArrayList<String> patientDetail = sendPatientInviteObj.sendPatientInviteToPractice(
				testData.PATIENT_INVITE_RESTURL, testData.PATIENT_PRACTICEID, testData.PATIENT_EXTERNAL_ID,
				"01/01/2010", "27560", testData.token);

		log("Follwing are patient details");
		int i = 0;
		String[] patientObject = { "FirstName/PatientId", "LastName", "Zip", "DateOfBirth", "Email", "Response" };
		for (String values : patientDetail) {
			log(patientObject[i] + " = " + values);
			i++;
		}
		log("checking email for activation UrL link");
		Thread.sleep(5000);
		log("Step 3: Check and extract Invite link in patient Email");
		Mailinator mail = new Mailinator();
		String activationUrl = mail.getLinkFromEmail(patientDetail.get(4),
				"You are invited to create a Patient Portal guardian account at PI Automation rsdk Integrated",
				PortalConstants.NewPatientActivationMessageLinkText, 20);
		assertTrue(activationUrl != null, "Error: Activation link not found.");

		log("Step 4: Register under age patient");
		PatientRegistrationUtils.underAgeRegisterPatient(activationUrl, patientDetail.get(4), testData.PatientPassword,
				testData.SecretQuestion, testData.SecretAnswer, testData.HomePhoneNo, driver, patientDetail.get(2),
				patientDetail.get(3));

		Thread.sleep(12000);
		log("Step 2:  Send CCD to Patient");
		iEHDCSendCCD sendCCDObj = new SendCCD();

		log("Step 5: Post CCD to Patient");
		ArrayList<String> ccdDetail = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, patientDetail.get(0), EHDCObj.ccdXMLPath, testData.PATIENT_EXTERNAL_ID,
				testData.token);
		log(ccdDetail.get(0));
		Thread.sleep(8000);
		log("Send 2nd CCD to Patient");
		ArrayList<String> ccdDetail1 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, testData.patientUA_ExternalPatientID_MU2, EHDCObj.ccdXMLPath,
				testData.PATIENT_EXTERNAL_ID, testData.token);
		log(ccdDetail1.get(0));
		Thread.sleep(8000);

		log("Send 3rd CCD to Patient");
		ArrayList<String> ccdDetail3 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, patientDetail.get(0), testData.CCDPATH2, testData.PATIENT_EXTERNAL_ID,
				testData.token);
		log(ccdDetail3.get(0));
		Thread.sleep(8000);

		log("Send 4th CCD to Patient");
		ArrayList<String> ccdDetail4 = sendCCDObj.sendCCDToPractice(EHDCObj.RestUrl, EHDCObj.From,
				testData.PATIENT_PRACTICEID, patientDetail.get(0), testData.CCDPATH3, testData.PATIENT_EXTERNAL_ID,
				testData.token);
		log(ccdDetail4.get(0));

		testData.CCDMessageID1 = ccdDetail.get(0);
		testData.CCDMessageID2 = ccdDetail1.get(0);

		log("Step 6: Set Up oauth");
		RestUtils.oauthSetup(testData.OAUTH_KEYSTORE, testData.OAUTH_PROPERTY, testData.OAUTH_APPTOKEN,
				testData.OAUTH_USERNAME, testData.OAUTH_PASSWORD);

		log("Step 7: Get PIDC to extract patient medfusion ID");
		Long since = timestamp / 1000L - 60 * 24;
		log("Getting patients since timestamp: " + since);
		log("PUSH_RESPONSEPATH: " + testData.PUSH_RESPONSEPATH);
		RestUtils.setupHttpGetRequest(testData.PATIENT_INVITE_RESTURL + "?since=" + since + ",0",
				testData.PUSH_RESPONSEPATH);

		MU2Utils MU2UtilsObj = new MU2Utils();
		String patientID = MU2UtilsObj.getMedfusionID(testData.PUSH_RESPONSEPATH, patientDetail.get(0));
		log("patientID : " + patientID);

		log("Step 8: Set values related to new guardian");
		testData.intuit_PATIENT_ID_MU2_Guardian = patientID;
		testData.patientUA_ExternalPatientID_MU2 = patientDetail.get(0);
		testData.guardian_Password_MU2 = testData.PatientPassword;
		testData.guardian_UserName_MU2 = patientDetail.get(4);
		testData.PatientFirstName_MU2 = patientDetail.get(0);
		testData.patientUA_MU2_LastName = patientDetail.get(1);

		Thread.sleep(8000);
		MU2UtilsObj.mu2GetEventGuardian(testData, driver, false, false, version);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientEGQUpdatesFromForms() throws Exception {
		log("Test Case: Update patient EGQ from patientForm and verify in the ccdExchangeBatch and get PIDC api's response");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Read data from an external property file.");
		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);
		FormsExportUtils formUtilsObject = new FormsExportUtils();
		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.oAuthKeyStore1_FE, testData.oAuthProperty1_FE, testData.oAuthAppTokenCCD1_FE,
				testData.oAuthUsernameCCD1_FE, testData.oAuthPasswordCCD1_FE);

		log("Step 3: Get GI/SO corresponding values from an external file");
		PIDCInfo testDataPIDC = new PIDCInfo();
		loadFormsExportInfoobj.loadDataFromProperty(testDataPIDC, "v2", "2.0");
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testDataPIDC.getCsvFilePath();
		PatientRegistrationUtils.csvFileReader(testDataPIDC, workingDir);

		log("Step 4: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.url_FE);
		JalapenoHomePage jalapenoHomePage = loginPage.login(testData.patientuserid_FE, testData.patientPassword1_FE);
		Thread.sleep(6000);
		jalapenoHomePage.clickOnMenuHealthForms();

		log("Step 5: Click on Registration button ");
		int sizeOfDropDown = testDataPIDC.patientDetailList.size();
		String dropValues[] = { "GenderIdentity", "SexualOrientation" };
		HealthFormListPage healthListpage = new HealthFormListPage(driver);
		for (int k = 0; k < dropValues.length; k++) {
			log("Step " + (6 + k) + ": Updating Values of '" + dropValues[k] + "' field");
			int count = sizeOfDropDown;
			if (k == 1) {
				count = sizeOfDropDown - 1;
			}
			log("Total number of values in '" + dropValues[k] + "' field drop-down:" + count);
			for (int i = 0; i < count; i++) {
				Long timestamp = System.currentTimeMillis();
				Thread.sleep(8000);
				log("Navigate to HealthForms ");
				healthListpage.clickOnHealthFormsRegistrationLinkUpdated(k + i);
				Thread.sleep(1000);
				FormBasicInfoPage pFormBasicInfoPage = PageFactory.initElements(driver, FormBasicInfoPage.class);
				if (i == 0 && k == 0) {
					PortalUtil.setPortalFrame(driver);
					log("Fill in Patient GI/SO value");
					pFormBasicInfoPage.switchFrame();
				}
				WebElement dropDownEGQ;
				if (k == 0) {
					IHGUtil.waitForElement(driver, 60, pFormBasicInfoPage.getGenderIdentity());
					dropDownEGQ = pFormBasicInfoPage.getGenderIdentity();
				} else {
					IHGUtil.waitForElement(driver, 60, pFormBasicInfoPage.getSexualOrientation());
					dropDownEGQ = pFormBasicInfoPage.getSexualOrientation();
				}
				// log(i+" i:CharAt "+dropValues[k].charAt(0)+" :webElement= "+dropDownEGQ);
				String updatedValue = formUtilsObject.updateDropDownValue(i, dropValues[k].charAt(0), dropDownEGQ);

				log("updated Value on portal = " + updatedValue);
				Thread.sleep(2000);
				pFormBasicInfoPage.saveAndContinue();
				Thread.sleep(4000);
				pFormBasicInfoPage.submitForm();

				if (!updatedValue.equalsIgnoreCase("Choose...")) {
					log("Wait for Values to get reflected in the API Call.. ");
					Thread.sleep(12000);
					char dropValue = dropValues[k].charAt(0);
					if (dropValue == 'G') {
						dropValue = 'I';
					}
					if (dropValues[k].equalsIgnoreCase(IntegrationConstants.GENDERIDENTITY)) {
						updatedValue = testDataPIDC.patientDetailList.get(i).getGenderIdentity();
						if (i == 4) {
							updatedValue = testDataPIDC.patientDetailList.get(i - 1).getGenderIdentity();
						}
						if (i == 3) {
							updatedValue = testDataPIDC.patientDetailList.get(i + 1).getGenderIdentity();
						}
					}
					if (dropValues[k].equalsIgnoreCase(IntegrationConstants.SEXUALORIENTATION)) {
						updatedValue = testDataPIDC.patientDetailList.get(i).getSexualOrientation();
					}
					log("Updated Corresponding Expected Enum Value = " + updatedValue);
					Long since = timestamp / 1000L;
					String getURL = testData.ccd_url1_FE + "Batch";
					log("Do a Get CcdExchangeBatch API call");
					RestUtils.setupHttpGetRequest(getURL + "?since=" + since + ",0", testData.responsePath_CCD1_FE);
					log("Verify updated GI/SO values in Get CcdExchangeBatch API call response");
					RestUtils.verifyEGQUpdatedValuesInCCDExchangeBatch(testData.responsePath_CCD1_FE, updatedValue,
							dropValue);
					log("Do a Get PIDC API call");
					String responseCode = RestUtils.setupHttpGetRequestWithEmptyResponse(
							testData.preCheckGetPIDC + "?since=" + since + ",0", testData.responsePDFBatch_FE);
					if (responseCode.equalsIgnoreCase("200")) {
						Thread.sleep(800);
						log("Verify updated GI/SO values in Get PIDC API call response");
						RestUtils.validateNode(testData.responsePDFBatch_FE, updatedValue, dropValue,
								testData.patientFirstName_FE);
					}
					if (k == 0 && i == 1) {
						log("Verify no-updates for GI/SO values in Get PIDC v1 API call response");
						String getpidcUrlv1 = testData.preCheckGetPIDC;
						getpidcUrlv1 = getpidcUrlv1.replaceAll("v2", "v1");
						String responseCodeValue = RestUtils.setupHttpGetRequestWithEmptyResponse(
								getpidcUrlv1 + "?since=" + since + ",0", testData.responsePDFBatch_FE);
						Assert.assertTrue(responseCodeValue.equalsIgnoreCase("204"),
								"get pidc v1 api call without 204");
					}
					Thread.sleep(12000);
				}
			}
		}
		log("Logout from patient portal");
		driver.switchTo().defaultContent();
		Thread.sleep(3000);
		jalapenoHomePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdatesAllInsuranceImageCards() throws Exception {
		log("Test Case: To verify Precheck Insurance Front back Image uploaded in Insuranceimage detail API Call");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);
		Long timestamp = System.currentTimeMillis();
		String currentUsersHomeDir = System.getProperty("user.dir");
		String imageLocation = testData.patientfilepath_FE;
		imageLocation = imageLocation.replaceAll("FormExportData.txt", "");
		String frontImageLocation = imageLocation + "PInsuranceFront.jpg";
		String backImageLocation = imageLocation + "PInsuranceBack.png";
		String[] imgType = { "image/jpeg", "image/png" };
		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log(testData.preCheckZip + "  ,  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		log("Step 5: Verify precheck patient zip and dateofBirth posted");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		Thread.sleep(9000);
		log("Step 6: Select the appointment which is posted and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);
		Thread.sleep(9000);
		log("Step 7: Goto the appointments Insurance Detail page");
		MyInsuranceDetailPage myInsuranceDetailPage = myAppointment.gotoInsuranceDetailPage();
		log("Step 8: Goto the appointments Insurance Image Uploading page");
		InsuranceImagePage insuranceImagePage = myInsuranceDetailPage.gotoInsuranceImagePage();

		log("Step 9: Upload Insurance Image card");
		String insuranceFrontCardPath = currentUsersHomeDir + frontImageLocation;
		String insuranceBackCardPath = currentUsersHomeDir + backImageLocation;
		String insuranceFrontCardBase64Path = ExternalFileReader.base64Encoder(insuranceFrontCardPath, false);
		String insuranceBackCardBase64Path = ExternalFileReader.base64Encoder(insuranceBackCardPath, false);
		log("Step 10: Uploading Primary Insurance Front and Back Image card");
		insuranceImagePage.uploadPrimaryInsuranceFrontPhotoInput(insuranceFrontCardPath);
		insuranceImagePage.uploadPrimaryInsuranceBackPhotoInput(insuranceBackCardPath);
		insuranceImagePage.gotoSecondaryInsuranceImageTab();
		log("Step 11: Uploading Secondary Insurance Front and Back Image card");
		insuranceImagePage.uploadSecondaryInsuranceFrontPhotoInput(insuranceFrontCardPath);
		insuranceImagePage.uploadSecondaryInsuranceBackPhotoInput(insuranceBackCardPath);
		insuranceImagePage.gotoTertiaryInsuranceImageTab();
		log("Step 12: Uploading Tertiary Insurance Front and Back Image card");
		insuranceImagePage.uploadTertiaryInsuranceFrontPhotoInput(insuranceFrontCardPath);
		insuranceImagePage.uploadTertiaryInsuranceBackPhotoInput(insuranceBackCardPath);
		insuranceImagePage.submitInsuranceImage();
		Thread.sleep(9000);

		Long since = timestamp / 1000L;
		log("Setup Oauth client");
		Thread.sleep(9000);
		RestUtils.oauthSetup(testData.oAuthKeyStore1_FE, testData.oAuthProperty1_FE, testData.oAuthAppTokenCCD1_FE,
				testData.oAuthUsernameCCD1_FE, testData.oAuthPasswordCCD1_FE);
		log("Step 13: Verify precheck patient insurance image information updated in GET PIDC API call");
		RestUtils.setupHttpGetRequestWithEmptyResponse(testData.preCheckGetPIDC + "?since=" + since + ",0",
				testData.responsePath_CCD1_FE);
		ArrayList<String> insuranceImageLinks = RestUtils
				.verifyInsuranceCardImageInGetPIDC(testData.responsePath_CCD1_FE, testData.preCheckPatientExternalID);
		Assert.assertTrue(insuranceImageLinks.size() == 6, "link not found or more links found.");
		log("Step 14: Verify Front Image in the insuranceimage detail api call");
		RestUtils.setupHttpGetRequestWithEmptyResponse(insuranceImageLinks.get(0), testData.responsePath_CCD1_FE);
		String frontFileName = "PrimaryInsurance_Front_" + testData.preCheckPatientExternalID + "_";
		RestUtils.verifyInsuranceImageDetailsBase64(testData.responsePath_CCD1_FE, insuranceFrontCardBase64Path,
				frontFileName, imgType[0]);
		log("Step 15: Verify Back Image in the insuranceimage detail api call");
		RestUtils.setupHttpGetRequestWithEmptyResponse(insuranceImageLinks.get(1), testData.responsePath_CCD1_FE);
		String backFileName = "PrimaryInsurance_Back_" + testData.preCheckPatientExternalID + "_";
		RestUtils.verifyInsuranceImageDetailsBase64(testData.responsePath_CCD1_FE, insuranceBackCardBase64Path,
				backFileName, imgType[1]);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EPreCheckUpdatesSingleInsuranceImageCard() throws Exception {
		log("Test Case: To verify Precheck Single Insurance front back Image uploaded in Insuranceimage detail API Call");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		PatientFormsExportInfo testData = new PatientFormsExportInfo();
		LoadPreTestData loadFormsExportInfoobj = new LoadPreTestData();
		loadFormsExportInfoobj.loadFormsExportInfofromProperty(testData);
		Long timestamp = System.currentTimeMillis();
		String currentUsersHomeDir = System.getProperty("user.dir");
		String imageLocation = testData.patientfilepath_FE;
		imageLocation = imageLocation.replaceAll("FormExportData.txt", "");
		String frontImageLocation = imageLocation + "PInsuranceFront.jpg";
		String backImageLocation = imageLocation + "PInsuranceBack.png";
		String[] imgType = { "image/jpeg", "image/png" };

		PrecheckAppointmentUtils preAppointmentObj = new PrecheckAppointmentUtils();
		String[] links = preAppointmentObj.createTestData(testData);
		log(testData.preCheckZip + "  ,  " + testData.preCheckDOB);
		String[] dob = testData.preCheckDOB.split("-");
		log("Step 5: Verify precheck patient zip and dateofBirth posted");
		VerifyIdentityPage verificationPage = new VerifyIdentityPage(driver, links[0]);
		verificationPage.setZipCode(testData.preCheckZip);
		verificationPage.setDateOfBirthMonthInput(dob[0]);
		verificationPage.setDateOfBirthDayInput(dob[1]);
		verificationPage.setDateOfBirthYearInput(dob[2]);
		AppointmentsListPage appListPage = verificationPage.login();
		Thread.sleep(9000);
		log("Step 6: Select the appointment which is posted and submit the fill form");
		MyAppointmentPage myAppointment = appListPage.selectAppointment(links[1]);
		Thread.sleep(9000);
		log("Step 7: Goto the appointments Insurance Detail page");
		MyInsuranceDetailPage myInsuranceDetailPage = myAppointment.gotoInsuranceDetailPage();
		log("Step 8: Goto the appointments Insurance Image Uploading page");
		InsuranceImagePage insuranceImagePage = myInsuranceDetailPage.gotoInsuranceImagePage();

		log("Step 9: Upload Insurance Image card");
		String insuranceFrontCardPath = currentUsersHomeDir + frontImageLocation;
		String insuranceBackCardPath = currentUsersHomeDir + backImageLocation;
		String insuranceFrontCardBase64Path = ExternalFileReader.base64Encoder(insuranceFrontCardPath, false);
		String insuranceBackCardBase64Path = ExternalFileReader.base64Encoder(insuranceBackCardPath, false);
		String frontFileName = "";
		String backFileName = "";
		log("Step 10: Uploading Single Insurance Front and Back Image card");
		if (testData.preCheckInsuranceImageType.equalsIgnoreCase("primary")) {
			insuranceImagePage.uploadPrimaryInsuranceFrontPhotoInput(insuranceFrontCardPath);
			insuranceImagePage.uploadPrimaryInsuranceBackPhotoInput(insuranceBackCardPath);
			frontFileName = "PrimaryInsurance_Front_" + testData.preCheckPatientExternalID + "_";
			backFileName = "PrimaryInsurance_Back_" + testData.preCheckPatientExternalID + "_";

		}
		insuranceImagePage.gotoSecondaryInsuranceImageTab();
		if (testData.preCheckInsuranceImageType.equalsIgnoreCase("secondary")) {
			insuranceImagePage.uploadSecondaryInsuranceFrontPhotoInput(insuranceFrontCardPath);
			insuranceImagePage.uploadSecondaryInsuranceBackPhotoInput(insuranceBackCardPath);
			frontFileName = "SecondaryInsurance_Front_" + testData.preCheckPatientExternalID + "_";
			backFileName = "SecondaryInsurance_Back_" + testData.preCheckPatientExternalID + "_";
		}
		insuranceImagePage.gotoTertiaryInsuranceImageTab();
		if (testData.preCheckInsuranceImageType.equalsIgnoreCase("tertiary")) {
			insuranceImagePage.uploadTertiaryInsuranceFrontPhotoInput(insuranceFrontCardPath);
			insuranceImagePage.uploadTertiaryInsuranceBackPhotoInput(insuranceBackCardPath);
			backFileName = "TertiaryInsurance_Back_" + testData.preCheckPatientExternalID + "_";
			frontFileName = "TertiaryInsurance_Front_" + testData.preCheckPatientExternalID + "_";
		}
		insuranceImagePage.submitInsuranceImage();
		Thread.sleep(9000);
		Long since = timestamp / 1000L;
		log("Step 11: Wait for appointment to reflect in get api call");
		Thread.sleep(9000);
		log("Step 12: Setup Oauth client");
		RestUtils.oauthSetup(testData.oAuthKeyStore1_FE, testData.oAuthProperty1_FE, testData.oAuthAppTokenCCD1_FE,
				testData.oAuthUsernameCCD1_FE, testData.oAuthPasswordCCD1_FE);
		log("Step 13: Verify precheck patient insurance image information updated in GET PIDC API call");
		RestUtils.setupHttpGetRequestWithEmptyResponse(testData.preCheckGetPIDC + "?since=" + since + ",0",
				testData.responsePath_CCD1_FE);
		ArrayList<String> insuranceImageLinks = RestUtils
				.verifyInsuranceCardImageInGetPIDC(testData.responsePath_CCD1_FE, testData.preCheckPatientExternalID);
		Assert.assertTrue(insuranceImageLinks.size() == 2, "link not found or more links found.");

		log("Step 14: Verify Front Image in the insuranceimage detail api call");
		RestUtils.setupHttpGetRequestWithEmptyResponse(insuranceImageLinks.get(0), testData.responsePath_CCD1_FE);
		RestUtils.verifyInsuranceImageDetailsBase64(testData.responsePath_CCD1_FE, insuranceFrontCardBase64Path,
				frontFileName, imgType[0]);
		log("Step 15: Verify Back Image in the insuranceimage detail api call");
		RestUtils.setupHttpGetRequestWithEmptyResponse(insuranceImageLinks.get(1), testData.responsePath_CCD1_FE);
		RestUtils.verifyInsuranceImageDetailsBase64(testData.responsePath_CCD1_FE, insuranceBackCardBase64Path,
				backFileName, imgType[1]);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EBalancePresentmentAndStatement() throws Exception {
		log("Test Case: Posting of Balance Presentment and statement to same patient and verify on Portal2.0");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		StatementEventData testData = new StatementEventData();
		log("Step 1: load from external property file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadStatementEventDataFromProperty(testData);
		StatementEventUtils sEventObj = new StatementEventUtils();

		log("Statement Event for Practice: " + testData.PracticeName);
		log("Step 2: Create a new patient from portal");
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		patient.setUrl(testData.Url);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L;
		Log4jUtil.log("Getting patients since timestamp: " + since);

		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(),
				patient.getPassword(), testDataPFL);

		log("Step 3: logout");
		Thread.sleep(6000);
		homePage.clickOnLogout();

		log("Step 4: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		log("Step 5: Do a get pidc call to get medfusion member id");
		RestUtils.setupHttpGetRequest(testData.RestURLPIDC + "?since=" + since + ",0", testData.ResponsePath);
		Thread.sleep(2000);

		log("Step 6: Extract medfusion member id");
		String responseXML = RestUtils.prepareCCD(testData.ResponsePath);
		String medfusionID = sEventObj.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID " + medfusionID);
		log("Step 7: set patient details ");
		testData.UserName = patient.getUsername();
		testData.Password = patient.getPassword();
		testData.FirstName = patient.getFirstName();
		testData.LastName = patient.getLastName();
		testData.Email = patient.getEmail();
		testData.PatientID = "";
		testData.MFPatientID = medfusionID;
		testData.UserName = patient.getEmail();
		testData.StatementType = "New";
		testData.since = since;

		StatementsMessagePayload SMPObj = new StatementsMessagePayload();
		String statement = SMPObj.getStatementsMessagePayload(testData);
		log("Statement Payload----------------" + statement);
		log("Step 8: Post statement to patient");
		sEventObj.postStatement(testData, statement);

		loginPage = new JalapenoLoginPage(driver, testData.Url);
		homePage = loginPage.login(testData.UserName, testData.Password);
		Thread.sleep(6000);
		log("Step 9: Verify the balance amount");
		log("homepage patient Balance : - " + homePage.getOutstandingPatientBalance());
		log("paylaod amountDue :- " + SMPObj.amountDue);
		assertTrue(homePage.getOutstandingPatientBalance().contains(SMPObj.amountDue));
		Thread.sleep(7000);

		homePage.clickOnLogout();
		BalancePayLoad BalancePayLoadObject = new BalancePayLoad();
		String payload = BalancePayLoadObject.getBalancePayLoad(testData, 1, testData.PatientID);

		log("Step 10: Post balance Presentment to patient");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.balanceUrl, payload, testData.ResponsePath);
		log("Step 11: Get processing status until it is completed");
		boolean completed = false;
		for (int j = 0; j < 3; j++) {
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		assertTrue(completed);
		driver.navigate().refresh();
		Thread.sleep(6000);
		log("Step 12: Loginto Portal 2.0");
		loginPage = new JalapenoLoginPage(driver, testData.Url);
		homePage = loginPage.login(testData.UserName, testData.Password);

		log("Step 13: Verify the balance amount");
		log(homePage.getOutstandingPatientBalance());

		JalapenoPayBillsMakePaymentPage PayBillsMakePaymentPageObject = homePage.clickOnMenuPayBills();

		assertTrue(
				PayBillsMakePaymentPageObject.getAccountNumber().contains(BalancePayLoadObject.balanceAccountNumber));
		assertTrue(
				PayBillsMakePaymentPageObject.getBalanceDue().contains(BalancePayLoadObject.patientOutstandingBalance));
		assertTrue(PayBillsMakePaymentPageObject.getOutstandingInsuranceBalance()
				.contains(BalancePayLoadObject.amountDue));
		log("Balance account number in payload " + BalancePayLoadObject.balanceAccountNumber + " and on Portal "
				+ PayBillsMakePaymentPageObject.getAccountNumber());
		log("Balance due amount in payload " + BalancePayLoadObject.patientOutstandingBalance + " and on Portal "
				+ PayBillsMakePaymentPageObject.getBalanceDue());
		log("Balance due date in payload " + BalancePayLoadObject.formattedUTCTime + " and on Portal "
				+ PayBillsMakePaymentPageObject.getBalanceDueDate());
		log("Amount due in payload " + BalancePayLoadObject.amountDue + " and on Portal "
				+ PayBillsMakePaymentPageObject.getOutstandingInsuranceBalance());

		Thread.sleep(6000);
		log("Step 14: Logout");
		homePage.clickOnLogout();
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EBalancePresentmentOnDemand() throws Exception {
		log("Test Case: Posting of Balance Presentment with onDemand Provisioning");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		StatementEventData testData = new StatementEventData();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		LoadPreTestDataObj.loadStatementEventDataFromProperty(testData);
		log("Statement Event for Practice: " + testData.PracticeName);

		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		patient.setUrl(testData.Url);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		Thread.sleep(12000);
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(),
				patient.getPassword(), testDataPFL);

		StatementEventUtils sEventObj = new StatementEventUtils();
		Thread.sleep(12000);
		homePage.clickOnLogout();

		log("Step 2: Setup Oauth client");
		Thread.sleep(10000);
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		RestUtils.setupHttpGetRequest(testData.RestURLPIDC + "?since=" + since + ",0", testData.ResponsePath);
		Thread.sleep(2000);

		String responseXML = RestUtils.prepareCCD(testData.ResponsePath);
		String medfusionID = sEventObj.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID " + medfusionID);
		log("Step 3: set patient details ");
		testData.UserName = patient.getUsername();
		testData.Password = patient.getPassword();
		testData.FirstName = patient.getFirstName();
		testData.LastName = patient.getLastName();
		testData.Email = patient.getEmail();
		testData.PatientID = "";
		testData.MFPatientID = medfusionID;
		testData.UserName = patient.getEmail();
		testData.StatementType = "New";
		testData.since = since;

		BalancePayLoad BalancePayLoadObject = new BalancePayLoad();
		String payload = BalancePayLoadObject.getBalancePayLoad(testData, 1, testData.PatientID);
		// log(payload);
		log("Step 4: Post balance Presentment to patient");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.balanceUrl, payload, testData.ResponsePath);
		log("Step 5: Get processing status until it is completed");
		boolean completed = false;
		for (int j = 0; j < 3; j++) {
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		assertTrue(completed);
		driver.navigate().refresh();
		Thread.sleep(6000);

		loginPage = new JalapenoLoginPage(driver, testData.Url);
		homePage = loginPage.login(testData.UserName, testData.Password);

		log("Step 7: Verify the balance amount due, account number ");
		log("Outstanding Patient Balance on home page = " + homePage.getOutstandingPatientBalance());
		assertTrue(homePage.getOutstandingPatientBalance().contains(BalancePayLoadObject.patientOutstandingBalance));

		JalapenoPayBillsMakePaymentPage PayBillsMakePaymentPageObject = homePage.clickOnMenuPayBills();

		assertTrue(
				PayBillsMakePaymentPageObject.getAccountNumber().contains(BalancePayLoadObject.balanceAccountNumber));
		assertTrue(
				PayBillsMakePaymentPageObject.getBalanceDue().contains(BalancePayLoadObject.patientOutstandingBalance));
		assertTrue(PayBillsMakePaymentPageObject.getOutstandingInsuranceBalance()
				.contains(BalancePayLoadObject.amountDue));
		log("Balance account number in payload " + BalancePayLoadObject.balanceAccountNumber + " and on Portal "
				+ PayBillsMakePaymentPageObject.getAccountNumber());
		log("Balance due amount in payload " + BalancePayLoadObject.patientOutstandingBalance + " and on Portal "
				+ PayBillsMakePaymentPageObject.getBalanceDue());
		log("Balance due date in payload " + BalancePayLoadObject.formattedUTCTime + " and on Portal "
				+ PayBillsMakePaymentPageObject.getBalanceDueDate());
		log("Amount due in payload " + BalancePayLoadObject.amountDue + " and on Portal "
				+ PayBillsMakePaymentPageObject.getOutstandingInsuranceBalance());
		log("Step 8: Logout");
		Thread.sleep(6000);
		homePage.clickOnLogout();

		if (testData.StatementType.equalsIgnoreCase("NEW")) {
			Thread.sleep(2000);
			log("Step 9: Login to Practice Portal.");
			Practice practice = new Practice();
			practice.url = testData.portalURL;
			practice.username = testData.practiceUserName;
			practice.password = testData.practicePassword;

			PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practice.url);
			PracticeHomePage pPracticeHomePage = practiceLogin.login(practice.username, practice.password);

			Log4jUtil.log("Step 10: Click on Patient Search Link");
			PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

			Log4jUtil.log("Step 11: Set Patient Search Fields");
			pPatientSearchPage.searchForPatientInPatientSearch(testData.FirstName, testData.LastName);

			Log4jUtil.log("Step 12: Click on Patient");
			PatientDashboardPage patientPage = pPatientSearchPage.clickOnPatient(testData.FirstName, testData.LastName);

			Thread.sleep(12000);
			Log4jUtil.log("Step 13: Get External Patient ID");
			String externalPatientID = patientPage.getExternalPatientID();

			Log4jUtil
					.log("Step 14: Verify patient External Id in practice portal with external Id in balance payload.");
			log("On Demand PatientID " + externalPatientID);
			log("Expected patient ID " + testData.PatientID);
			assertEquals(testData.PatientID, externalPatientID, "Patient External ID not Matched !");

			log("Step 15: Logout of Practice Portal");
			pPracticeHomePage.logOut();

		}

		log("Step 16: Prepare Statement Payload");
		StatementsMessagePayload SMPObj = new StatementsMessagePayload();
		String statement = SMPObj.getStatementsMessagePayload(testData);

		log("Step 17: Post statement to patient");
		sEventObj.postStatement(testData, statement);
		testData.MFPatientID = "";
		log("MFPatientID = " + testData.MFPatientID.isEmpty());

		log("Step 18: Login into patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.Url);
		homePage = loginPage.login(testData.UserName, testData.Password);
		Thread.sleep(7000);
		log("Step 19: Verify the balance amount");
		log("homepage patient Balance : - " + homePage.getOutstandingPatientBalance());
		log("paylaod amountDue :- " + SMPObj.amountDue);
		assertTrue(homePage.getOutstandingPatientBalance().contains(SMPObj.amountDue));
		Thread.sleep(7000);

		log("Step 20: Logout");
		homePage.clickOnLogout();
	}

	@Test(enabled = true, dataProvider = "channelVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testOnDemandHealthData(String version) throws Exception {
		if (version.equals("v2"))
			throw new SkipException("Test skipped as version is:" + version);
		log("Test Case: Request health data OnDemand in patient Portal 2.0");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Step 1: Load Data from External file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		EHDC testData = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(testData);

		String restApiCall = "";
		if (version.equals("v1")) {
			log("For Channel version V1");
			restApiCall = testData.RestUrl;
			restApiCall = restApiCall.replace("ccd", "healthdata");
			log("restApiCall=" + restApiCall);
		} else {
			log("For Channel version V3");
			restApiCall = testData.RestUrlV3;
			restApiCall = restApiCall.replace("ccd", "healthdata");
			log("restApiCall=" + restApiCall);
		}

		log("Step 2: Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long millis = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight" + millis);

		log("Step 3: Login to Portal 2.0");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);

		log("Step 4: Go to  Health Record Summaries");
		MedicalRecordSummariesPage MedicalRecordSummariesPageObject = homePage.clickOnMedicalRecordSummaries(driver);
		Assert.assertTrue(MedicalRecordSummariesPageObject.areBasicPageElementsPresent(),
				"Failed to Load Health Record Summaries ");

		log("Step 5: Click on Request Health Record");
		MedicalRecordSummariesPageObject.selectHealthRecordRequestButton();
		Thread.sleep(6000);

		log("Step 6: Selecting the date range for the health Data Request");

		MedicalRecordSummariesPageObject.filterCCDs(
				MedicalRecordSummariesPageObject.get3MonthsOldDateinYYYY_MM_DDFormat(),
				MedicalRecordSummariesPageObject.getTodaysDateinYYYY_MM_DDFormat());
		log(MedicalRecordSummariesPageObject.get3MonthsOldDateinYYYY_MM_DDFormat());
		log(MedicalRecordSummariesPageObject.getTodaysDateinYYYY_MM_DDFormat());
		MedicalRecordSummariesPageObject.requestCcdOnDemandFromPopUp();
		Thread.sleep(5000);

		log("Step 7: Close the onDemand PopUp ");
		MedicalRecordSummariesPageObject.closeOnDemandPopUpButton();

		log("Step 8: Logout");
		homePage.clickOnLogout();

		log("Step 9: Setup Oauth Token");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		log("Step 10: Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(restApiCall + "?since=" + millis + ",0", testData.ResponsePath);

		log("Step 11: Verify Patient Details in the Get Api Call.");
		RestUtils.isOnDemandRequestSubmitted(testData.ResponsePath, testData.PracticePatientId);

		log("Step 12: verify the start date and the End date of the Request for health data");
		RestUtils.verifyRequestStartDateAndEndDate(testData.ResponsePath,
				MedicalRecordSummariesPageObject.get3MonthsOldDateinYYYY_MM_DDFormat(),
				MedicalRecordSummariesPageObject.getTodaysDateinYYYY_MM_DDFormat());
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestForExistingPatient() throws Exception {
		log("Test Case: Appointment Request for Existing Patient From Partner");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AppointmentData testData = new AppointmentData();
		LoadPreTestDataObj.loadAppointmentDataFromProperty(testData);
		AppointmentDataUtils aDUtils = new AppointmentDataUtils();

		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.csvFilePath;
		aDUtils.csvFileReader(testData, workingDir);

		testData.Status = "NEW";
		testData.FirstName = testData.FirstName;
		testData.LastName = testData.LastName;
		testData.EmailUserName = testData.EmailUserName;
		testData.BatchSize = "2";

		testData.Time = testData.appointmentDetailList.get(1).getTime();
		testData.appointmentType = "FUTURE";
		testData.Location = "NEW";

		testData.Type = testData.appointmentDetailList.get(1).getType();
		testData.Reason = testData.appointmentDetailList.get(1).getReason();
		testData.Description = testData.appointmentDetailList.get(1).getDescription();

		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		aDUtils.checkAppointment(testData, driver);
		Thread.sleep(6000);
		// homePage.clickOnLogout();
		Thread.sleep(3000);

		testData.Status = "UPDATE";
		testData.Time = testData.appointmentDetailList.get(3).getTime();
		testData.Location = "Update";
		testData.appointmentType = "FUTURE";

		testData.Type = testData.appointmentDetailList.get(3).getType();
		testData.Reason = testData.appointmentDetailList.get(3).getReason();
		testData.Description = testData.appointmentDetailList.get(3).getDescription();
		testData.BatchSize = "1";

		aDUtils.checkAppointment(testData, driver);
		Thread.sleep(3000);

		testData.Status = "CANCEL";
		testData.Time = testData.appointmentDetailList.get(4).getTime();
		testData.Location = "Cancel";
		testData.appointmentType = "FUTURE";

		testData.Type = testData.appointmentDetailList.get(4).getType();
		testData.Reason = testData.appointmentDetailList.get(4).getReason();
		testData.Description = testData.appointmentDetailList.get(4).getDescription();
		testData.BatchSize = "1";

		aDUtils.checkAppointment(testData, driver);

	}

	@Test(enabled = true, dataProvider = "portalVersion", groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCPatientRegistrationV3(String portalVersion) throws Exception {
		log("Test Case: PIDC Patient Registration v3 channel for portal-" + portalVersion);
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		PatientRegistrationUtils.pidcPatientRegistration("v3", driver, portalVersion);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppoinmentType() throws Exception {
		log("Test Case: To POST Appointment type");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AppointmentData testData = new AppointmentData();
		LoadPreTestDataObj.loadAppointmentTypeFromProperty(testData);
		AppointmentDataUtils aDUtils = new AppointmentDataUtils();
		log("POST URL" + testData.AppointmentTypeUrl);

		log("Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);
		String appointmentType = AppointmentTypePayload.getAppointmentTypePayload(testData);
		Thread.sleep(6000);
		log("Wait to generate AppointmentType Payload");
		log("Step 2: Do Message Post Request");
		log("ResponsePath: " + testData.ResponsePath);
		RestUtils.setupHttpPostRequest(testData.AppointmentTypeUrl, appointmentType, testData.ResponsePath);
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPharmacies() throws Exception {
		log("Test Case: Add Pharmacy");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		Pharmacies testData = new Pharmacies();
		LoadPreTestDataObj.loadPharmaciesFromProperty(testData);
		log("POST URL: " + testData.PharmacyRenewalUrl);

		log("Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		testData.Status = "NEW";
		testData.PharmacyName = "AddedNewPharmacy";

		PharmacyPayload pharmacyObj = new PharmacyPayload();
		String ExternalPharmacyId = PharmacyPayload.randomNumbers(14);
		log("ExternalPharmacyId posted is : " + ExternalPharmacyId);
		String pharmacyRenewal = pharmacyObj.getPharmacyAddPayload(testData, ExternalPharmacyId);
		log("Payload: " + pharmacyRenewal);
		Thread.sleep(6000);
		log("Wait to generate Pharmacy Renewal Payload");

		log("Step 2: Do Message Post Request");
		log("ResponsePath: " + testData.ResponsePath);
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.PharmacyRenewalUrl, pharmacyRenewal,
				testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		Assert.assertTrue(completed, "Message processing was not completed in time");

		log("Step 3: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		JalapenoPrescriptionsPage JalapenoPrescriptionsPageObject = homePage.clickOnPrescriptions(driver);

		log("Step 4: Click on Prescription and go to Prescription Page");
		Assert.assertTrue(JalapenoPrescriptionsPageObject.areBasicPageElementsPresent(),
				"Failed to Load Health Record Summaries ");

		log("Step 5: Click on Continue ");
		JalapenoPrescriptionsPageObject.clickContinueButton(driver);
		Thread.sleep(60000);

		log("Step 6:verify newly added pharmacy in the list");
		String addedPharamacy = testData.PharmacyName + ", " + testData.Line1 + ", " + testData.City + ", "
				+ testData.State;
		log("Added Pharamacy :- " + addedPharamacy);
		JalapenoPrescriptionsPageObject.areBasicPageElementsPresent();
		String env = IHGUtil.getEnvironmentType().toString();

		if (env == "DEV3") {
			String pharmacyFristWord = "Added";
			JalapenoPrescriptionsPageObject.verifyPharamcy(addedPharamacy, pharmacyFristWord);
		} else {
			JalapenoPrescriptionsPageObject.clickOnChooseOneDrpdown();
			Thread.sleep(6000);
			JalapenoPrescriptionsPageObject.verifyPharmacy(addedPharamacy);
		}
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdatePharmacies() throws Exception {
		log("Test Case: Update Pharmacy");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		Pharmacies testData = new Pharmacies();
		LoadPreTestDataObj.loadPharmaciesFromProperty(testData);
		log("POST URL: " + testData.PharmacyRenewalUrl);

		log("Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		log("Add Pharmacy with status 'NEW' ");
		testData.Status = "NEW";
		testData.PharmacyName = "AddedNewPharmacy";

		PharmacyPayload pharmacyObj = new PharmacyPayload();
		String ExternalPharmacyId = PharmacyPayload.randomNumbers(14);
		log("ExternalPharmacyId posted is : " + ExternalPharmacyId);
		String pharmacyNewPayload = pharmacyObj.getPharmacyAddPayload(testData, ExternalPharmacyId);
		log("Payload: " + pharmacyNewPayload);
		Thread.sleep(6000);
		log("Wait to generate Pharmacy New Payload");

		log("Step 2: Do NEW Pharmacy Post Request");
		log("ResponsePath: " + testData.ResponsePath);
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.PharmacyRenewalUrl, pharmacyNewPayload,
				testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		Assert.assertTrue(completed, "Message processing was not completed in time");

		log("Update Pharmacy with status 'UPDATE' ");
		testData.Status = "UPDATE";
		testData.PharmacyName = "UpdateAddedPharmacy";

		log("ExternalPharmacyId posted is : " + ExternalPharmacyId);
		String updatePayload = pharmacyObj.getPharmacyAddPayload(testData, ExternalPharmacyId);
		log("Payload: " + updatePayload);
		Thread.sleep(6000);
		log("Wait to generate Pharmacy Payload");

		log("Step 3: Do UPDATE Pharmacy Post Request");
		log("ResponsePath: " + testData.ResponsePath);
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		String processingUpdateUrl = RestUtils.setupHttpPostRequest(testData.PharmacyRenewalUrl, updatePayload,
				testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUpdateUrl);

		Boolean completedStatus = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		Assert.assertTrue(completed, "Message processing was not completed in time");

		log("Step 4: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		JalapenoPrescriptionsPage JalapenoPrescriptionsPageObject = homePage.clickOnPrescriptions(driver);

		log("Step 5: Click on Prescription and go to Prescription Page");
		Assert.assertTrue(JalapenoPrescriptionsPageObject.areBasicPageElementsPresent(),
				"Failed to Load Health Record Summaries ");

		log("Step 6: Click on Continue ");
		JalapenoPrescriptionsPageObject.clickContinueButton(driver);
		Thread.sleep(60000);

		log("Step 7:verify added pharmacy is updated in the list");
		String addedPharamacy = testData.PharmacyName + ", " + testData.Line1 + ", " + testData.City + ", "
				+ testData.State;
		log("Added Pharamacy :- " + addedPharamacy);
		JalapenoPrescriptionsPageObject.areBasicPageElementsPresent();
		String env = IHGUtil.getEnvironmentType().toString();

		if (env == "DEV3") {
			String pharmacyFristWord = "Update";
			JalapenoPrescriptionsPageObject.verifyPharamcy(addedPharamacy, pharmacyFristWord);
		} else {
			JalapenoPrescriptionsPageObject.clickOnChooseOneDrpdown();
			Thread.sleep(6000);
			JalapenoPrescriptionsPageObject.verifyPharmacy(addedPharamacy);
		}
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletePharmacies() throws Exception {
		log("Test Case: Delete Pharmacy");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		Pharmacies testData = new Pharmacies();
		LoadPreTestDataObj.loadPharmaciesFromProperty(testData);
		log("POST URL: " + testData.PharmacyRenewalUrl);

		log("Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		log("Add Pharmacy with status 'NEW' ");
		testData.Status = "NEW";
		testData.PharmacyName = "AddedNewPharmacy";

		PharmacyPayload pharmacyObj = new PharmacyPayload();
		String ExternalPharmacyId = PharmacyPayload.randomNumbers(14);
		log("ExternalPharmacyId posted is : " + ExternalPharmacyId);
		String pharmacyNewPayload = pharmacyObj.getPharmacyAddPayload(testData, ExternalPharmacyId);
		log("Payload: " + pharmacyNewPayload);
		Thread.sleep(6000);
		log("Wait to generate Pharmacy New Payload");

		log("Step 2: Do NEW Pharmacy Post Request");
		log("ResponsePath: " + testData.ResponsePath);
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.PharmacyRenewalUrl, pharmacyNewPayload,
				testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		Assert.assertTrue(completed, "Message processing was not completed in time");

		log("Delete Pharmacy with status 'DELETE' ");
		testData.Status = "DELETE";

		log("ExternalPharmacyId posted is : " + ExternalPharmacyId);
		String deletePayload = pharmacyObj.getPharmacyAddPayload(testData, ExternalPharmacyId);
		log("Payload: " + deletePayload);
		Thread.sleep(6000);
		log("Wait to generate Pharmacy Payload");

		log("Step 3: Do DELETE Pharmacy Post Request");
		log("ResponsePath: " + testData.ResponsePath);
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		String processingDeleteUrl = RestUtils.setupHttpPostRequest(testData.PharmacyRenewalUrl, deletePayload,
				testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingDeleteUrl);

		Boolean completedStatus = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		Assert.assertTrue(completed, "Message processing was not completed in time");

		log("Step 4: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		JalapenoPrescriptionsPage JalapenoPrescriptionsPageObject = homePage.clickOnPrescriptions(driver);

		log("Step 5: Click on Prescription and go to Prescription Page");
		Assert.assertTrue(JalapenoPrescriptionsPageObject.areBasicPageElementsPresent(),
				"Failed to Load Health Record Summaries ");

		log("Step 6: Click on Continue ");
		JalapenoPrescriptionsPageObject.clickContinueButton(driver);
		Thread.sleep(60000);

		log("Step 7:verify added pharmacy is deleted in the list");
		String deletedPharamacy = testData.PharmacyName + ", " + testData.Line1 + ", " + testData.City + ", "
				+ testData.State;
		log("Added Pharamacy :- " + deletedPharamacy);
		JalapenoPrescriptionsPageObject.areBasicPageElementsPresent();
		String env = IHGUtil.getEnvironmentType().toString();
		if (env == "DEV3") {
			String pharmacyFristWord = "Added";
			JalapenoPrescriptionsPageObject.verifyDeletedPharamcy(deletedPharamacy, pharmacyFristWord);
			log("Pharamacy is not visible on the Portal");
		} else {
			JalapenoPrescriptionsPageObject.clickOnChooseOneDrpdown();
			Thread.sleep(6000);
			JalapenoPrescriptionsPageObject.verifyDeletedPharmacy(deletedPharamacy);
			log("Pharamacy is not visible on the Portal");
		}
	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestForExistingPatientV3() throws Exception {
		log("Test Case: Appointment Request for Existing Patient From Partner");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Get Data from property file");
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AppointmentData testData = new AppointmentData();
		LoadPreTestDataObj.loadAppointmentDataFromProperty(testData);
		AppointmentDataUtils aDUtils = new AppointmentDataUtils();

		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.csvFilePath;
		aDUtils.csvFileReader(testData, workingDir);

		log("Step 2: Post NEW AppointMentData ");
		testData.Status = "NEW";
		testData.FirstName = testData.FirstName;
		testData.LastName = testData.LastName;
		testData.EmailUserName = testData.EmailUserName;
		testData.BatchSize = "2";

		testData.Time = testData.appointmentDetailList.get(1).getTime();
		testData.appointmentType = "FUTURE";
		testData.Location = "NEW";

		testData.Type = testData.appointmentDetailList.get(1).getType();
		testData.Reason = testData.appointmentDetailList.get(1).getReason();
		testData.Description = testData.appointmentDetailList.get(1).getDescription();

		log("Step 3: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		aDUtils.checkAppointmentV3(testData, driver);
		Thread.sleep(6000);

		log("Step 4: Post UPDATE AppointMentData ");
		testData.Status = "UPDATE";
		testData.Time = testData.appointmentDetailList.get(3).getTime();
		testData.Location = "Update";
		testData.appointmentType = "FUTURE";

		testData.Type = testData.appointmentDetailList.get(3).getType();
		testData.Reason = testData.appointmentDetailList.get(3).getReason();
		testData.Description = testData.appointmentDetailList.get(3).getDescription();
		testData.BatchSize = "1";

		aDUtils.checkAppointmentV3(testData, driver);
		Thread.sleep(3000);

		log("Step 5: Post CANCEL AppointMentData ");
		testData.Status = "CANCEL";
		testData.Time = testData.appointmentDetailList.get(4).getTime();
		testData.Location = "Cancel";
		testData.appointmentType = "FUTURE";

		testData.Type = testData.appointmentDetailList.get(4).getType();
		testData.Reason = testData.appointmentDetailList.get(4).getReason();
		testData.Description = testData.appointmentDetailList.get(4).getDescription();
		testData.BatchSize = "1";

		aDUtils.checkAppointmentV3(testData, driver);

	}

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestForNewSelfPatientV3() throws Exception {
		log("Test Case: Appointment Request for New Patient From Partner");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		AppointmentDataUtils aDUtils = new AppointmentDataUtils();
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		AppointmentData testData = new AppointmentData();
		LoadPreTestDataObj.loadAppointmentDataFromProperty(testData);

		log("Step 1: Create patient");
		PropertyFileLoader testDataPFL = new PropertyFileLoader();
		JalapenoPatient patient = new JalapenoPatient(testDataPFL);
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, patient.getUrl());
		Thread.sleep(5000);
		PatientDemographicPage patientDemographicPage = loginPage.clickCreateANewAccountButton();
		patientDemographicPage.fillInPatientData(patient);
		SecurityDetailsPage accountDetailsPage = patientDemographicPage.continueToSecurityPage();
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(patient.getEmail(),
				patient.getPassword(), testDataPFL);

		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		Thread.sleep(6000);
		homePage.clickOnLogout();

		log("Step 2: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		RestUtils.setupHttpGetRequest(testData.PATIENT_INVITE_RESTV3URL + "?since=" + since + ",0",
				testData.ResponsePath);
		Thread.sleep(2000);

		String responseXML = RestUtils.prepareCCD(testData.ResponsePath);
		String medfusionID = aDUtils.getMedfusionID(patient.getEmail(), responseXML);
		log("medfusionID " + medfusionID);

		log("step 3: Login to Practice Portal");
		Practice practice = new Practice();
		practice.url = testData.portalURL;
		practice.username = testData.practiceUserName;
		practice.password = testData.practicePassword;

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practice.url);
		PracticeHomePage pPracticeHomePage = practiceLogin.login(practice.username, practice.password);

		log("step 4: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		log("step 5:Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(patient.getFirstName(), patient.getLastName());

		log("step 6:Verify the Search Result");
		IHGUtil.waitForElement(driver, 60, pPatientSearchPage.searchResult);
		Assert.assertTrue(pPatientSearchPage.searchResult.getText().contains(patient.getFirstName()));
		pPatientSearchPage.clickOnSearch();
		Thread.sleep(3000);
		pPatientSearchPage.clickOnEdit();
		Thread.sleep(3000);
		pPatientSearchPage.sendPatientIDAndClickOnUpdate(patient.getFirstName());
		Thread.sleep(3000);
		pPatientSearchPage.clickOnEdit();
		Thread.sleep(3000);
		String patientExternalID = pPatientSearchPage.verifypatientExternalID();
		log("Actual patient ID " + patientExternalID);
		log("Expected patient ID " + patient.getFirstName());
		Assert.assertEquals(patient.getFirstName(), patientExternalID, "Patient External ID Matched !");

		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.csvFilePath;
		aDUtils.csvFileReader(testData, workingDir);
		;

		log("Step 7: Post New AppointMentData with MFPatientID");
		testData.FirstName = patient.getFirstName();
		testData.LastName = patient.getLastName();
		testData.EmailUserName = patient.getEmail();
		testData.PatientPracticeId = patient.getFirstName();
		testData.MFPatientId = medfusionID;
		testData.BatchSize = "1";
		testData.Status = testData.appointmentDetailList.get(1).getStatus(); // "NEW";
		testData.Time = testData.appointmentDetailList.get(1).getTime(); // "2017-02-13T21:30:00.000Z";
		testData.Location = testData.appointmentDetailList.get(1).getLocation();
		testData.appointmentType = "FUTURE";
		testData.UserName = patient.getEmail();
		testData.Password = patient.getPassword();

		testData.Type = testData.appointmentDetailList.get(1).getType();
		testData.Reason = testData.appointmentDetailList.get(1).getReason();
		testData.Description = testData.appointmentDetailList.get(1).getDescription();

		aDUtils.checkAppointmentV3(testData, driver);
		Thread.sleep(6000);

		log("Step 8: Post UPDATE AppointMentData ");
		testData.Status = "UPDATE";
		testData.Time = testData.appointmentDetailList.get(3).getTime(); // "2017-03-13T16:30:59.999Z";
		testData.Location = testData.appointmentDetailList.get(3).getLocation();
		testData.appointmentType = "FUTURE";
		testData.PatientPracticeId = patient.getFirstName();
		testData.BatchSize = "1";
		testData.Type = testData.appointmentDetailList.get(3).getType();
		testData.Reason = testData.appointmentDetailList.get(3).getReason();
		testData.Description = testData.appointmentDetailList.get(3).getDescription();

		aDUtils.checkAppointmentV3(testData, driver);

		log("Step 9: Post CANCEL AppointMentData ");
		Thread.sleep(3000);
		testData.Status = "CANCEL";
		testData.Time = testData.appointmentDetailList.get(4).getTime(); // "2017-03-13T16:30:59.999Z";
		testData.Location = testData.appointmentDetailList.get(4).getLocation();
		testData.appointmentType = "FUTURE";
		testData.PatientPracticeId = patient.getFirstName();
		testData.BatchSize = "1";

		testData.Type = testData.appointmentDetailList.get(4).getType();
		testData.Reason = testData.appointmentDetailList.get(4).getReason();
		testData.Description = testData.appointmentDetailList.get(4).getDescription();

		aDUtils.checkAppointmentV3(testData, driver);

	}

	@Test(enabled = true, groups = {
			"RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEHDCSendCCDLargeSize(Method  method) throws Exception {
		log("Test Case: send a CCD and check in patient Portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Method Name: " + method.getName());

		String ccd;
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		EHDC testData = new EHDC();
		LoadPreTestDataObj.loadEHDCDataFromProperty(testData);
		log("Attachment File Path: " + testData.ccdXMLPathLargeSize);
		log("Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		ccd = CCDPayload.getCCDPayloadV3(testData,method.getName());
		Thread.sleep(6000);
		log("Payload" + ccd);
		log("Wait to generate CCD Payload");
		log("Step 2: Do Message Post Request");
		log("ResponsePath: " + testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrlV3, ccd, testData.ResponsePath);

		log("Processing URL: " + processingUrl);
		log("Step 3: Get processing status until it is completed");
		Thread.sleep(60000);
		log("Step 4: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);

		log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		log("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		log("Step 5: Validate message subject and send date");
		Thread.sleep(1000);
		log("Message Date" + IHGUtil.getEstTiming());
		assertTrue(messagesPage.isMessageDisplayed(driver, "You have a new health data summary"));
		log("CCD sent date & time is : " + messagesPage.returnMessageSentDate());

		log("Step 6: Click on link View health data");
		JalapenoCcdViewerPage jalapenoCcdPage = messagesPage.findCcdMessage(driver);

		log("Step 7: Verify if CCD Viewer is loaded and click Close Viewer");
		assertTrue(jalapenoCcdPage.areBasicPageElementsPresent());
		messagesPage = jalapenoCcdPage.closeCcd(driver);

		log("Step 8: Logging out");
		assertTrue(messagesPage.areBasicPageElementsPresent());
		homePage = messagesPage.backToHomePage(driver);
		loginPage = homePage.clickOnLogout();
	}

}
