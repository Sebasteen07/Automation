package com.intuit.ihg.product.phr.test;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import com.medfusion.portal.utils.PortalConstants;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.MFDateUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.intuit.ihg.common.utils.ccd.CCDTest;
import com.intuit.ihg.product.object.maps.phr.page.PhrDocumentsPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrEmergencyAccessPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrEmergencyTermsOfUse;
import com.intuit.ihg.product.object.maps.phr.page.PhrHomePage;
import com.intuit.ihg.product.object.maps.phr.page.PhrLoginPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrManagePermissionsPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrSharingPage;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrInboxMessage;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrMessagesPage;
import com.intuit.ihg.product.object.maps.phr.page.portaltophr.IntuitAcceptPrivacyPolicy;
import com.intuit.ihg.product.object.maps.phr.page.profile.PhrProfilePage;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.inbox.MessagePage;
import com.medfusion.product.object.maps.patientportal1.page.inbox.MessageCenterInboxPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.MyAccountPage;
import com.medfusion.product.object.maps.patientportal1.page.newRxRenewalpage.NewRxRenewalPage;
import com.medfusion.product.object.maps.patientportal1.page.portaltophr.AcceptPhrTermsandConditions;
import com.intuit.ihg.product.phr.utils.Phr;
import com.intuit.ihg.product.phr.utils.PhrTestcasesData;
import com.intuit.ihg.product.phr.utils.PhrUtil;
import com.medfusion.product.patientportal1.flows.CreatePatientTest;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.TestcasesData;

public class PhrAcceptanceTests extends BaseTestNGWebDriver {

		private PropertyFileLoader testData;

		@BeforeMethod(alwaysRun = true)
		public void setUpPhrTest() throws Exception {
				log("Getting Test Data");
				testData = new PropertyFileLoader();
		}

		/**
		 * @throws Exception
		 * @Author:- bkrishnankutty
		 * @Date:-
		 * @User Story ID in Rally :
		 * @StepsToReproduce: Login to Phr Portal Log Out from Phr Portal
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testPhrLoginLogout() throws Exception {
				driver.manage().deleteAllCookies();

				log("step 1: Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData testcasesData = new PhrTestcasesData(phr);

				log("URL: " + testcasesData.geturl());
				log("USER NAME: " + testcasesData.getUsername());
				log("Password: " + testcasesData.getPassword());

				log("step 2:LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, testcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());
				Assert.assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

				log("step 3:Logout");
				loginpage = pPhrHomePage.clickLogout();
				Assert.assertTrue(loginpage.isSearchPageLoaded(), "Expected the PhrLoginPage to be loaded, but it was not.");
				assertTrue(loginpage.waitforTXTPassword(driver, 60), "There was an issue reloading the login page upon logout");
		}


		/**
		 * @throws Exception
		 * @Author: bkrishnankutty
		 * @Date: 5/16/2013
		 * @StepsToReproduce: Login to PHR Portal Click on BlueButton Download Pdf Link Simulate click of 'Download My Data' (PDF) Check that the HTTP Status Code 200
		 * @AreaImpacted :
		 */

		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testPhrBlueButtonDownloadPdf() throws Exception {
				log("step 1: Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData testcasesData = new PhrTestcasesData(phr);

				log("URL: " + testcasesData.geturl());
				log("USER NAME: " + testcasesData.getUsername());
				log("Password: " + testcasesData.getPassword());

				log("step 2:LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, testcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());
				Assert.assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");


				log("step 3: Download PDF version of Blue Button download -- validate HTTP Status Code");
				assertEquals(pPhrHomePage.clickBlueButtonDownloadPdf(), 200, "Download of Blue Button PDF returned unexpected HTTP status code");
		}


		/**
		 * @throws Exception
		 * @Author: bkrishnankutty
		 * @Date: 5/16/2013
		 * @StepsToReproduce: Login to PHR Portal Click on BlueButton Download text Link Simulate click of 'Download My Data' (PDF) Check that the HTTP Status Code 200
		 * @AreaImpacted :
		 */

		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testPhrBlueButtonDownloadtext() throws Exception {
				log("step 1: Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData testcasesData = new PhrTestcasesData(phr);

				log("URL: " + testcasesData.geturl());
				log("USER NAME: " + testcasesData.getUsername());
				log("Password: " + testcasesData.getPassword());

				log("step 2:LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, testcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());
				Assert.assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

				log("step 3: Download PDF version of Blue Button download -- validate HTTP Status Code");
				assertEquals(pPhrHomePage.clickBlueButtonDownloadtext(), 200, "Download of Blue Button PDF returned unexpected HTTP status code");
		}


		/**
		 * @throws Exception
		 * @Author:- bkrishnankutty
		 * @Date:-2/6/2013
		 * @User Story ID in Rally
		 * @StepsToReproduce: Click Sign-UP Create a new account Logout from patient portal Again Login to patient Portal click on phr link on home page Accept all
		 * terms and conditions Assert an element PHR home page Log out
		 */

		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void newPatientSignUpSsoToPhr() throws Exception {
				log("step 1: Get Data from Excel");

				Portal portal = new Portal();
				TestcasesData testcasesData = new TestcasesData(portal);

				log("URL: " + testcasesData.geturl());
				log("USER NAME: " + testcasesData.getUsername());
				log("Password: " + testcasesData.getPassword());
				log("URL: " + testcasesData.geturl());

				// Instancing CreatePatientTest
				CreatePatientTest createPatientTest = new CreatePatientTest();

				// Executing Test
				MyPatientPage pMyPatientPage = createPatientTest.createPatient(driver, testcasesData);

				// need to include a piece of code here for deleting sookieso that script works in IE works
				/*
				 * log("Clearing Browser cache"); PhrUtil.DeleteAllBrowsingDataIE(driver);
				 */

				log("step 8:Assert Webelements in MyPatientPage");
				assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

				log("step 9:Navigate to PHR and assert link");
				AcceptPhrTermsandConditions pAcceptPhrTermsandConditions = pMyPatientPage.clickViewMeaningfulUsePHRLink();
				assertTrue(verifyTextPresent(driver, "Personal Health Record"));

				log("step 10:Accept the Intuit terms and condition  from Portal side");
				driver = pAcceptPhrTermsandConditions.clickbtnAccept();

				log("step 11:Accept the Intuit terms and condition  from PHR side");
				// Since the back check is not active on QA1
				PhrHomePage pPhrHomePage;
				IntuitAcceptPrivacyPolicy pIntuitAcceptPrivacyPolicy = PageFactory.initElements(driver, IntuitAcceptPrivacyPolicy.class);
				Thread.sleep(20000);
				// this thread.sleep is purposeful otherwise script can fail here
				pPhrHomePage = pIntuitAcceptPrivacyPolicy.acceptIntuitTermsAndCondition();
				log("step 12:Assert profile link on PHR HOme page");
				Assert.assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");
				assertTrue(pPhrHomePage.waitforbtnProfile(driver, 6), "PhrHomePage doesn't loaded properly :Assertion failed");

				log("step 13:Logout");
				PhrLoginPage pPhrLoginPage = pPhrHomePage.clickLogout();
				Assert.assertTrue(pPhrLoginPage.isSearchPageLoaded(), "Expected the PhrLoginPage to be loaded, but it was not.");
				assertTrue(pPhrLoginPage.waitforTXTPassword(driver, 6), "There was an issue reloading the login page upon logout");
		}

		/**
		 * @throws Exception
		 * @Author:- bkrishnankutty
		 * @Date:-2/6/2013
		 * @User Story ID in Rally
		 * @StepsToReproduce: LogIn to Patient portal Go to My Account Change City,state,Phone number Save Change Go to PHR and Assert if the change got reflected
		 * there Change it Same fields in PHR portal Save the change Navigate back to Patient portal and Assert the change in My profile Log out
		 */
		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void demographicSyncFromPortalToPHR() throws Exception {

				String city = "";
				String zip = "";

				// This will not be used until son-555 is fixed
				// city = IHGUtil.createRandomCity();
				// zip = IHGUtil.createRandomZip();
				String phoneNumber = IHGUtil.createRandomNumericString(10);

				log("step 1: Get Data from  PHR Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrTestData = new PhrTestcasesData(phr);
				Portal portal = new Portal();
				TestcasesData portalTestData = new TestcasesData(portal);

				log("step 2:LogIn to Patient Portal ");

				log("URL: " + portalTestData.geturl());
				log("USER NAME: " + phrTestData.getsecondaryUser());
				log("Password: " + phrTestData.getsecondaryUserPwd());

				PortalLoginPage loginpage = new PortalLoginPage(driver, portalTestData.geturl());
				MyPatientPage pMyPatientPage = loginpage.login(phrTestData.getsecondaryUser(), phrTestData.getsecondaryUserPwd());

				log("step 3:Click on myaccountLink on MyPatientPage");
				MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();


				if (!city.isEmpty() && !zip.isEmpty()) {
						log("Optional Step A : Change City and Zipcode");
						pMyAccountPage.modifyCityAndZip(city, zip);
				}

				if (!phoneNumber.isEmpty()) {
						log("Optional Step B : Change Home Phone Number");
						pMyAccountPage.modifyHomePhone(phoneNumber);
				}

				log("Step 4: update communication method");
				pMyAccountPage.chooseCommunicationMethod("US mail");

				log("step 5: Redirect to PHR Portal");

				pMyPatientPage = pMyAccountPage.clickMyPatientPage();
				pMyPatientPage.clickViewMeaningfulUsePHRLink();
				PhrHomePage pPhrHomePage = new PhrHomePage(driver);
				assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");
				pPhrHomePage.waitforbtnProfile(driver, 6);

				log("step 6:Click on Profile Button in PHR HOME PAGE");
				PhrProfilePage pPhrProfilePage = pPhrHomePage.clickProfileButton();


				log("step 7:Assert If the data in PHR got updated with Portal modification");
				if (!city.isEmpty() && !zip.isEmpty()) {
						log("Optional Step A: Assert City and Zip");
						pPhrProfilePage.assertDataCityAndZip(city, zip);
				}
				if (!phoneNumber.isEmpty()) {
						log("Optional Step B: Assert Phone Number");
						pPhrProfilePage.assertHomePhoneNumber(phoneNumber);
				}

				log("step 8: Log out");
				pPhrProfilePage.clickLogout();

		}

		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void demographicSyncFromPHRtoPortal() throws Exception {

				String city = "";
				String zip = "";

				// This will not be used until son-555 is fixed
				// city = IHGUtil.createRandomCity();
				// zip = IHGUtil.createRandomZip();
				String phoneNumber = IHGUtil.createRandomNumericString(10);

				log("step 1: Get Data from  PHR Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrTestData = new PhrTestcasesData(phr);
				Portal portal = new Portal();
				TestcasesData portalTestData = new TestcasesData(portal);

				log("step 2: LogIn to PHR Portal");
				PhrLoginPage phrloginpage = new PhrLoginPage(driver, phrTestData.geturl());
				PhrHomePage pPhrHomePage = phrloginpage.login(phrTestData.getsecondaryUser(), phrTestData.getsecondaryUserPwd());
				Assert.assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");
				pPhrHomePage.waitforbtnProfile(driver, 6);

				log("step 3:Click on Profile Button in PHR HOME PAGE");
				PhrProfilePage pPhrProfilePage = pPhrHomePage.clickProfileButton();

				log("step 4:Modify Patient Data in PHR side");

				if (!city.isEmpty() && !zip.isEmpty()) {
						log("Optional Step A : Change City and Zipcode");
						pPhrProfilePage.setCityZip(city, zip);
				}

				if (!phoneNumber.isEmpty()) {
						log("Optional Step B : Change Mobile Phone Number");
						pPhrProfilePage.setMobilePhoneNumber(phoneNumber);
				}
				Thread.sleep(10000);
				assertTrue(verifyTextPresent(driver, "Registration Information Updated Successfully"));
				System.out.println("Registration Information Updated Successfully");

				log("step 5:Log out from PHR");
				pPhrProfilePage.clickLogout();

				log("step 6:LogIn to portal site");
				PortalLoginPage loginpage = new PortalLoginPage(driver, portalTestData.geturl());
				MyPatientPage pMyPatientPage = loginpage.login(phrTestData.getsecondaryUser(), phrTestData.getsecondaryUserPwd());

				log("step 7:Click on myaccountLink on MyPatientPage");
				MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

				log("step 8:Assert If the data in PORTAL got updated with PHR modification");
				if (!city.isEmpty() && !zip.isEmpty()) {
						log("Optional Step A: Assert City and Zip");
						pMyAccountPage.assertDataCityAndZip(city, zip);
				}

				if (!phoneNumber.isEmpty()) {
						log("Optional Step B: Assert Phone Number");
						pMyAccountPage.assertMobilePhoneNumber(phoneNumber);
				}

				log("step 9:Logout");
				pMyAccountPage.logout(driver);

		}


		/**
		 * @throws Exception
		 * @Author:- bkrishnankutty
		 * @Date:-5/30/2013
		 * @User Story ID in Rally
		 * @StepsToReproduce: LogIn to PHR portal using ccduser from excel Post CONSOLIDATED_CCD request Click on Document link Get first date from table list and
		 * assert it Click on it and then Click on View Health Information link click link on ShareWithADoctor Add Addresses and Validate Click On
		 * Close AfterSharingTheHealthInformation Click on Close Viewer Log into Patient Portal Go to inbox open the first mail and validate the
		 * subject and date click ReviewHealthInformation click link on ShareWithADoctor Add Addresses and Validate Click On Close
		 * AfterSharingTheHealthInformation Click on Close Viewer
		 */

		// Note testcase is not working on prod because step 13 :- css :- Identifer for View All message has a small diiference ['>a' not there]
		@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class)
		public void testCCDImportThroughEHDC() throws Exception {
				log("step 2: Get Data from Excel");

				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				log("step 3:LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				log("step 4:Post CONSOLIDATED_CCD request");
				pPhrHomePage.postCCdRequest(phrtestcasesData.getallScriptAdapterURL());

				log("step :Wait for page to be loaded completely");
				Assert.assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

				log("step 5:Click on Document link");
				PhrDocumentsPage pPhrDocumentsPage = pPhrHomePage.clickDocuments();

				log("step 6:Refresh Document page before checking the Table list ");
				pPhrDocumentsPage.refreshPage(driver);

				log("step 7:Get first date from table list ");
				pPhrDocumentsPage.getFirstDate();
				Assert.assertNotNull("### Couldn't find date column for new record", pPhrDocumentsPage.getFirstDate());
				log("getFirstDate>>" + pPhrDocumentsPage.getFirstDate() + "getPSTTiming>>" + pPhrDocumentsPage.getPstTimings());
				Assert.assertEquals(pPhrDocumentsPage.getPstTimings(), pPhrDocumentsPage.getFirstDate(), "### Can't find record with todays date");

				log("step 8:Click on first date on the list");
				pPhrDocumentsPage.clickFirstCcdInTheList();

				log("step 9:Click on View Health Information");
				pPhrDocumentsPage.clickViewHealthInformation();

				log("step 10:Share the address with the Doctor and click Close Viewer");
				pPhrDocumentsPage.closeViewer();

				log("step 11:Click Logout");
				loginpage = pPhrDocumentsPage.clickLogout();

				log("step 12:LogIn to Patient Portal ");
				Portal portal = new Portal();
				TestcasesData portalTestData = new TestcasesData(portal);
				PortalLoginPage portalloginpage = new PortalLoginPage(driver, portalTestData.geturl());
				MyPatientPage pMyPatientPage = portalloginpage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				log("step 13: Go to Inbox");
				MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
				assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

				log("step 14: Find message in Inbox");
				MessagePage pNewInboxMessage = inboxPage.clickFirstMessageRow();

				log("step 15: Validate message subject and send date");
				assertEquals(pNewInboxMessage.getPracticeReplyMessageTitle(), "New Health Information Import", "### Assertion failed for Message subject");
				log("######  Message Date :: " + IHGUtil.getEstTiming());
				assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

				log("step 16: Click on link ReviewHealthInformation");
				pNewInboxMessage.clickBtnReviewHealthInformation();

				log("step 17:Share the address with the Doctor and click Close Viewer");
				pNewInboxMessage.closeViewer();

		}


		/**
		 * @throws Exception
		 * @Author:- bkrishnankutty
		 * @Date:-5/30/2013
		 * @User Story ID in Rally
		 * @StepsToReproduce: LogIn to PHR portal using ccduser from excel Post CONSOLIDATED_CCD request Click on Document link Get first date from table list and
		 * assert it Click on it and then Click on View Health Information link Click on Close Viewer Log into Patient Portal Go to inbox open the
		 * first mail and validate the subject and date click ReviewHealthInformation click link on ShareWithADoctor Add Addresses and Validate
		 * Click On Close AfterSharingTheHealthInformation Click on Close Viewer
		 */
		@Test(enabled = false, retryAnalyzer = RetryAnalyzer.class)
		public void testNonCCDImportThroughEHDC() throws Exception {
				log("step 2: Get Data from Excel");

				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				log("step 3:LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				log("step 4:Post NON_CONSOLIDATED_CCD request");
				pPhrHomePage.postNonCCdRequest(phrtestcasesData.getallScriptAdapterURL());

				log("step :Wait for page to be loaded completely");
				Assert.assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

				log("step 5:Click on Document link");
				PhrDocumentsPage pPhrDocumentsPage = pPhrHomePage.clickDocuments();

				log("step 6:Refresh Document page before checking the Table list ");
				pPhrDocumentsPage.refreshPage(driver);

				log("step 7:Get first date from table list ");
				pPhrDocumentsPage.getFirstDate();
				Assert.assertNotNull("### Couldn't find date column for new record", pPhrDocumentsPage.getFirstDate());
				log("getFirstDate>>" + pPhrDocumentsPage.getFirstDate() + "getPSTTiming>>" + pPhrDocumentsPage.getPstTimings());
				Assert.assertEquals(pPhrDocumentsPage.getPstTimings(), pPhrDocumentsPage.getFirstDate(), "### Can't find record with todays date");

				log("step 8:Click on first date on the list");
				pPhrDocumentsPage.clickFirstCcdInTheList();

				log("step 9:Click on View Health Information");
				pPhrDocumentsPage.clickViewHealthInformation();

				log("step 10:Click on Close Viewer");
				pPhrDocumentsPage.closeViewer();// wont be able generalize it with portal side because of frames

				log("step 11:Click Logout");
				loginpage = pPhrDocumentsPage.clickLogout();

				log("step 12:LogIn to Patient Portal ");
				Portal portal = new Portal();
				TestcasesData portalTestData = new TestcasesData(portal);
				PortalLoginPage portalloginpage = new PortalLoginPage(driver, portalTestData.geturl());
				MyPatientPage pMyPatientPage = portalloginpage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				log("step 13: Go to Inbox");
				MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
				assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

				log("step 14: Find message in Inbox");
				MessagePage pInboxMessage = inboxPage.clickFirstMessageRow();

				log("step 15: Validate message subject and send date");
				Thread.sleep(1000);
				assertEquals(pInboxMessage.getPracticeReplyMessageTitle(), "New Health Information Import", "### Assertion failed for Message subject");
				log("######  Message Date :: " + IHGUtil.getEstTiming());
				assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

				log("step 15: Click on link ReviewHealthInformation");
				pInboxMessage.clickBtnReviewHealthInformation();

				log("step 10:Share the address with the Doctor and click Close Viewer");
				pInboxMessage.closeViewer();
		}



		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testMedSyncToPortal() throws Exception {
				log("step 1: Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getElektaUser());
				log("Password: " + phrtestcasesData.getElektaPassword());

				// trunctated and minus one minute to work around phr not returning seconds
				log("step 2: Get current time in UTC minus one minute");
				ZonedDateTime start = MFDateUtil.getCurrentTimeUTC().truncatedTo(ChronoUnit.MINUTES).minus(1, ChronoUnit.MINUTES);
				log("Expected message should arrive after: " + start);

				log("step 3: get a bearer token for the CCD send");
				String token = CCDTest
						.getAccessTokenForSystem(testData.getProperty("oauthTokenURL"), testData.getProperty("elektaClientId"), testData.getProperty("elektaClientSecret"),
								testData.getProperty("elektaPracticeSystemUsername"), testData.getProperty("elektaPracticeSystemPassword"));
				Assert.assertNotNull(token);

				log("step 4:Post ELEKTA_CCD request to " + testData.getProperty("EHDCAdapterURL"));
				PhrUtil.ccdImportFromElekta(testData, token);

				log("step 5:LogIn to phr");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getElektaUser(), phrtestcasesData.getElektaPassword());


				log("step 6:Wait for page to be loaded completely");
				Assert.assertTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

				log("step 7: Click on Messages");
				PhrMessagesPage messagesPage = pPhrHomePage.clickOnMyMessages();


				log("step 8: Check if the first message arrived after the test started ");
				PhrInboxMessage messagePage = messagesPage.clickOnFirstMessage();
				Assert.assertEquals(messagePage.getPhrMessageSubject(), "You have a new health data summary",
						"The message subject was NOT: \"You have a new health data summary\"");
				Assert.assertNotNull("Couldn't find date/time in the message page", messagePage.getPhrMessageDateTime());
				ZonedDateTime timeFound = MFDateUtil.parseDateToUTCZonedTime(messagePage.getPhrMessageDateTime(), ZoneId.of("America/New_York"));
				log("Datetime found:" + messagePage.getPhrMessageDateTime() + " parsed as:" + timeFound);

				Assert.assertTrue(MFDateUtil.compareDates(start, timeFound) < 0, " The newest message arrived before the test started");

				log("step 8:LogIn to Patient Portal ");
				PortalLoginPage portalloginpage = new PortalLoginPage(driver, phrtestcasesData.getElektaPracticeURL());
				MyPatientPage pMyPatientPage = portalloginpage.login(phrtestcasesData.getElektaUser(), phrtestcasesData.getElektaPassword());

				log("step 9: Go to Prescription Renewal");
				NewRxRenewalPage newRxRenewalPage = pMyPatientPage.clickPrescriptionRenewal();

				log("step 10: Check for medications");
				newRxRenewalPage.checkMedication();
				Assert.assertEquals(newRxRenewalPage.medicineName0.getText(), PortalConstants.MedicineNameOne);
				Assert.assertEquals(newRxRenewalPage.medicineName1.getText(), PortalConstants.MedicineNameTwo);


				log("step 11: Logout of Patient Portal");
				pMyPatientPage.logout(driver);
		}

		@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testEmergencyAccess() throws Exception {
				log("step 2: Get Data from Excel");

				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				log("step 3:LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				log("step 4:Click on sharing");
				PhrSharingPage pPhrSharingPage = pPhrHomePage.clickSharing();

				log("step 5:Go to manage permissions");
				PhrManagePermissionsPage pPhrManagePermissionspage = pPhrSharingPage.clickManagePermissionsPage();
				System.out.println("on manage permissions page");

				log("step 6: go to emergency access page for first time user ");

				if (pPhrManagePermissionspage.hasAllowEmergencyAccess()) {

						PhrEmergencyTermsOfUse pPhrEmergencyToU = pPhrManagePermissionspage.clickAllowEmergencyAccess();
						pPhrEmergencyToU.clickAcknowledgement();
						pPhrEmergencyToU.clickSubmit();

				} else {
						log("step 7: go to edit & click emergency responder card");
						PhrEmergencyAccessPage pPhrEmergencyAccessPage = pPhrManagePermissionspage.clickEdit();
						pPhrEmergencyAccessPage.clickEmergencyResponderCard();

						log("step 8: accept alert");
						driver.switchTo().alert().accept();
						// we can't wait for the print dialog precisely through webdriver
						Thread.sleep(3000);
						// jawa.awt.robot sends an escape keypress to the print dialog
						IHGUtil.hadlePrintDialog();
						// and another small wait until the system print dialog closes, the handle refreshes afterwards and that can race-break the following loop
						Thread.sleep(3000);
						log(driver.getWindowHandles().toString());

						log("step 10: switch to active window");
						for (String windowHandle : driver.getWindowHandles()) {
								driver.switchTo().window(windowHandle);
						}

						log("step 11: assert text present");
						Assert.assertTrue(verifyTextPresent(driver, "PIN"));

				}
		}
}


