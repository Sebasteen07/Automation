package com.intuit.ihg.product.phr.test;


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.EnvironmentTypeUtil.EnvironmentType;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.phr.page.PhrDocumentsPage;
import com.intuit.ihg.product.phr.page.PhrEmergencyAccessPage;
import com.intuit.ihg.product.phr.page.PhrEmergencyTermsOfUse;
import com.intuit.ihg.product.phr.page.PhrHomePage;
import com.intuit.ihg.product.phr.page.PhrLoginPage;
import com.intuit.ihg.product.phr.page.PhrManagePermissionsPage;
import com.intuit.ihg.product.phr.page.PhrSharingPage;
import com.intuit.ihg.product.phr.page.portaltophr.IntuitAcceptPrivacyPolicy;
import com.intuit.ihg.product.phr.page.profile.PhrProfilePage;
import com.intuit.ihg.product.phr.utils.Phr;
import com.intuit.ihg.product.phr.utils.PhrTestcasesData;
import com.intuit.ihg.product.phr.utils.PhrUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.createAccount.BetaSiteCreateAccountPage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxMessage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxPage;
import com.intuit.ihg.product.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.portal.page.newRxRenewalpage.NewRxRenewalPage;
import com.intuit.ihg.product.portal.page.portaltophr.AcceptPhrTermsandConditions;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class PhrAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-
	 * @User Story ID in Rally :
	 * @StepsToReproduce: 
	 * Login to Phr Portal 
	 * Log Out from Phr Portal
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPhrLoginLogout() throws Exception {
		log("Test Case: TestPhrLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Phr phr = new Phr();
		PhrTestcasesData testcasesData = new PhrTestcasesData(phr);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,
				testcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(testcasesData.getUsername(),
				testcasesData.getPassword());
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

		log("step 3:Logout");
		loginpage = pPhrHomePage.clickLogout();
		verifyTrue(loginpage.isSearchPageLoaded(), "Expected the PhrLoginPage to be loaded, but it was not.");
		assertTrue(loginpage.waitforTXTPassword(driver, 60),
		"There was an issue reloading the login page upon logout");
	}


	/**
	 * @Author: bkrishnankutty
	 * @Date: 5/16/2013
	 * @StepsToReproduce:
	 * Login to PHR Portal
	 * Click on BlueButton Download Pdf Link
	 * Simulate click of 'Download My Data' (PDF)
	 * Check that the HTTP Status Code == 200
	 * =============================================================
	 * @AreaImpacted : 
	 * @throws Exception
	 */

	@Test (enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testPhrBlueButtonDownloadPdf() throws Exception {
		log("testPhrBlueButtonDownloadPdf");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Phr phr = new Phr();
		PhrTestcasesData testcasesData = new PhrTestcasesData(phr);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,
				testcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(testcasesData.getUsername(),
				testcasesData.getPassword());
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");


		log("step 3: Download PDF version of Blue Button download -- validate HTTP Status Code");
		assertEquals(pPhrHomePage.clickBlueButtonDownloadPdf(),
				200, 
		"Download of Blue Button PDF returned unexpected HTTP status code");


	}


	/**
	 * @Author: bkrishnankutty
	 * @Date: 5/16/2013
	 * @StepsToReproduce:
	 * Login to PHR Portal
	 * Click on BlueButton Download text Link
	 * Simulate click of 'Download My Data' (PDF)
	 * Check that the HTTP Status Code == 200
	 * =============================================================
	 * @AreaImpacted : 
	 * @throws Exception
	 */

	@Test (enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testPhrBlueButtonDownloadtext() throws Exception {
		log("testPhrBlueButtonDownloadtext");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Phr phr = new Phr();
		PhrTestcasesData testcasesData = new PhrTestcasesData(phr);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,
				testcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(testcasesData.getUsername(),
				testcasesData.getPassword());
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

		log("step 3: Download PDF version of Blue Button download -- validate HTTP Status Code");
		assertEquals(pPhrHomePage.clickBlueButtonDownloadtext(),
				200, 
		"Download of Blue Button PDF returned unexpected HTTP status code");


	}


	/**
	 * @Author:- bkrishnankutty
	 * @Date:-2/6/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce:
	 * Click Sign-UP
	 * Create a new account 
	 * Logout from patient portal
	 * Again Login to patient Portal
	 * click on phr link on home page
	 * Accept all terms and conditions 
	 * Assert an element PHR home page 
	 * Log out
	 * =============================================================
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void newPatientSignUpSsoToPhr() throws Exception {

		log("Test Case: newPatientSignUpSsoToPhr");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());
		log("URL: " + testcasesData.geturl());

		log("step 2:Click Sign-UP");
		PortalLoginPage loginpage = new PortalLoginPage(driver,
				testcasesData.geturl());
		BetaSiteCreateAccountPage pBetaSiteCreateAccountPage = loginpage
		.signUpIntoBetaSite();

		log("step 3:Fill detials in Create Account Page");
		String email = PortalUtil.createRandomEmailAddress(testcasesData
				.getEmail());
		log("email:-" + email);
		MyPatientPage pMyPatientPage = pBetaSiteCreateAccountPage
		.BetaSiteCreateAccountPage(testcasesData.getFirstName(),
				testcasesData.getLastName(), email,
				testcasesData.getPhoneNumber(),
				testcasesData.getDob_Month(),
				testcasesData.getDob_Day(),
				testcasesData.getDob_Year(), testcasesData.getZip(),
				testcasesData.getSSN(), testcasesData.getAddress(),
				testcasesData.getPassword(),
				testcasesData.getSecretQuestion(),
				testcasesData.getAnswer(),
				testcasesData.getAddressState(),
				testcasesData.getAddressCity());

		log("step 5:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 6:Logout");
		pMyPatientPage.clickLogout(driver);

		//need to include a piece of code here for deleting sookieso that script works in IE works 
		/*log("Clearing Browser cache");
		PhrUtil.DeleteAllBrowsingDataIE(driver);*/

		log("step 7:Login as new user");
		loginpage.navigateTo(driver, testcasesData.geturl());
		pMyPatientPage = loginpage.login(email, testcasesData.getPassword());

		log("step 8:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 9:Navigate to PHR and assert link");
		AcceptPhrTermsandConditions pAcceptPhrTermsandConditions = pMyPatientPage
		.clickViewMeaningfulUsePHRLink();
		assertTrue(verifyTextPresent(driver, "Personal Health Record"));

		log("step 10:Accept the Intuit terms and condition  from Portal side");
		driver = pAcceptPhrTermsandConditions.clickbtnAccept();

		log("step 11:Accept the Intuit terms and condition  from PHR side");
		IntuitAcceptPrivacyPolicy pIntuitAcceptPrivacyPolicy = PageFactory
		.initElements(driver, IntuitAcceptPrivacyPolicy.class);
		Thread.sleep(20000);//this thread.sleep is purposeful otherwise script  can fail here
		PhrHomePage pPhrHomePage = pIntuitAcceptPrivacyPolicy
		.acceptIntuitTermsAndCondition();

		log("step 12:Assert profile link on PHR HOme page");
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");
		assertTrue(pPhrHomePage.waitforbtnProfile(driver, 6),
		"PhrHomePage doesn't loaded properly :Assertion failed");

		log("step 13:Logout");
		PhrLoginPage pPhrLoginPage = pPhrHomePage.clickLogout();
		verifyTrue(pPhrLoginPage.isSearchPageLoaded(), "Expected the PhrLoginPage to be loaded, but it was not.");
		assertTrue(pPhrLoginPage.waitforTXTPassword(driver, 6),
		"There was an issue reloading the login page upon logout");

	}



	/**
	 * Note :- Two Testcases are combined together.
	 * 
	 * @Author:- bkrishnankutty
	 * @Date:-2/6/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce:
	 * LogIn to Patient portal
	 * Go to My Account
	 * Change City,state,Phone number
	 * Save Change
	 * Go to PHR and Assert if the change got reflected there
	 * Change it Same fields in PHR portal
	 * Save the change
	 * Navigate back to Patient portal and Assert the change in My profile
	 * Log out
	 * =============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void demographicSyncBetweenPortalAndPHR() throws Exception {

		log("Test Case: demographicSyncBetweenPortalAndPHR");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from  Excel");

		log("step A: Get Data from  PHR Excel");
		Phr phr = new Phr();
		PhrTestcasesData phrTestData = new PhrTestcasesData(phr);

		log("step A: Get Data from  PORTAL Excel");
		Portal portal = new Portal();
		TestcasesData portalTestData = new TestcasesData(portal);

		log("URL: " + portalTestData.geturl());
		log("USER NAME: " + phrTestData.getsecondaryUser());
		log("Password: " + phrTestData.getsecondaryUserPwd());


		log("step 2:LogIn to Patient Portal ");
		PortalLoginPage loginpage = new PortalLoginPage (driver,portalTestData.geturl());
		MyPatientPage pMyPatientPage= loginpage.login(phrTestData.getsecondaryUser(),
				phrTestData.getsecondaryUserPwd());

		log("step 3:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage=pMyPatientPage.clickMyAccountLink();

		log("step 4: Compare Excel data and Application data");
		System.out.println("portal address is"+portalTestData.getAddressCity());
		System.out.println("portal zip is"+portalTestData.getZip());
//		pMyAccountPage.assertDataWithExcel(portalTestData.getAddressCity(), portalTestData.getZip());

		log("step 5: Change City and Zipcode");
		pMyAccountPage.modifyCityAndZip(phrTestData.getsecondaryCity(), phrTestData.getsecondaryUserZipCode());
		log("Step: update communication method");
		pMyAccountPage.chooseCommunicationMethod("US mail");
		log("Step: updated communication method successfully");
		//assertTrue(verifyTextPresent(driver,"Profile"));
		
		
		log("step 6: logout from patient portal");
		pMyAccountPage.logout(driver);
		
		Thread.sleep(12000);

		log("step 7:LogIn to PHR Portal");
		PhrLoginPage phrloginpage = new PhrLoginPage(driver,
				phrTestData.geturl());
		PhrHomePage pPhrHomePage = phrloginpage.login(phrTestData.getsecondaryUser(),phrTestData.getsecondaryUserPwd());	
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");
		pPhrHomePage.waitforbtnProfile(driver, 6);
				
		log("step 8:Click on Profile Button in PHR HOME PAGE");
		PhrProfilePage pPhrProfilePage=pPhrHomePage.clickProfileButton();

		log("step 9:Assert If the data in PHR got updated with Portal modification");
		pPhrProfilePage.assertDataCityAndZip(phrTestData.getsecondaryCity(), phrTestData.getsecondaryUserZipCode());

		log("step 10:Modify Patient Data in PHR side");
		pPhrProfilePage.modifyPatientInfoInPhr(portalTestData.getAddressCity(), portalTestData.getZip());
		assertTrue(verifyTextPresent(driver,"Registration Information Updated Successfully"));
		System.out.println("Registration Information Updated Successfully");

		log("step 11:Log out from PHR");
		pPhrProfilePage.clickLogout();
		
		Thread.sleep(8000);

		log("step 12:LogIn to portal site");
		loginpage = new PortalLoginPage (driver,portalTestData.geturl());
		pMyPatientPage= loginpage.login(phrTestData.getsecondaryUser(),
				phrTestData.getsecondaryUserPwd());

		log("step 13:Click on myaccountLink on MyPatientPage");
		pMyAccountPage=pMyPatientPage.clickMyAccountLink();

		log("step 14:Assert If the data in PORTAL got updated with PHR modification");
		pMyAccountPage.assertDataCityAndZipInPortal(portalTestData.getAddressCity(), portalTestData.getZip());

		log("step 15:Logout");
		pMyAccountPage.logout(driver);

	}




	/**
	 * @Author:- bkrishnankutty
	 * @Date:-5/30/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce:
	 * LogIn to PHR portal using ccduser from excel
	 * Post CONSOLIDATED_CCD request
	 * Click on Document link
	 * Get first date from table list and assert it
	 * Click on it and then Click on View Health Information link
	 * click link on ShareWithADoctor
	 * Add Addresses and Validate
	 * Click On Close AfterSharingTheHealthInformation
	 * Click on Close Viewer
	 * Log into Patient Portal
	 * Go to inbox
	 * open the first mail and validate the subject and date
	 * click ReviewHealthInformation
	 * click link on ShareWithADoctor
	 * Add Addresses and Validate
	 * Click On Close AfterSharingTheHealthInformation
	 * Click on Close Viewer
	 * =============================================================
	 * @throws Exception
	 */

	//Note testcase is not working on prod because step 13 :- css :- Identifer for View All message has a small diiference ['>a' not there]

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDImportThroughEHDC() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Phr phr = new Phr();
		PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

		log("URL: " + phrtestcasesData.geturl());
		log("USER NAME: " + phrtestcasesData.getccdUserName());
		log("Password: " + phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,
				phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(
				phrtestcasesData.getccdUserName(),
				phrtestcasesData.getccdUserPassword());

		log("step 4:Post CONSOLIDATED_CCD request");
		pPhrHomePage.postCCdRequest(phrtestcasesData.getallScriptAdapterURL());

		log("step :Wait for page to be loaded completely");
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

		log("step 5:Click on Document link");
		PhrDocumentsPage pPhrDocumentsPage = pPhrHomePage.clickDocuments();

		log("step 6:Refresh Document page before checking the Table list ");
		pPhrDocumentsPage.refreshPage(driver);

		log("step 7:Get first date from table list ");
		pPhrDocumentsPage.getFirstDate();
		Assert.assertNotNull("### Couldn't find date column for new record",
				pPhrDocumentsPage.getFirstDate());
		log("getFirstDate>>" + pPhrDocumentsPage.getFirstDate()
				+ "getPSTTiming>>" + pPhrDocumentsPage.getPstTimings());
		Assert.assertEquals(pPhrDocumentsPage.getPstTimings(),
				pPhrDocumentsPage.getFirstDate(),
		"### Can't find record with todays date");

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
		PortalLoginPage portalloginpage = new PortalLoginPage(driver,
				portalTestData.geturl());
		MyPatientPage pMyPatientPage = portalloginpage.login(
				phrtestcasesData.getccdUserName(),
				phrtestcasesData.getccdUserPassword());

		log("step 13: Go to Inbox");
		ConsolidatedInboxPage inboxPage = pMyPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 14: Find message in Inbox");
		ConsolidatedInboxMessage pConsolidatedInboxMessage = inboxPage
		.clickFirstMessageRow();

		log("step 15: Validate message subject and send date");
		Thread.sleep(1000);
		assertEquals(pConsolidatedInboxMessage.getMessageSubject(),
				"New Health Information Import",
		"### Assertion failed for Message subject");
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("step 16: Click on link ReviewHealthInformation");
		pConsolidatedInboxMessage.clickBtnReviewHealthInformation();

		log("step 17:Share the address with the Doctor and click Close Viewer");
		pConsolidatedInboxMessage.closeViewer();


	}


	/**
	 * @Author:- bkrishnankutty
	 * @Date:-5/30/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce:
	 * LogIn to PHR portal using ccduser from excel
	 * Post CONSOLIDATED_CCD request
	 * Click on Document link
	 * Get first date from table list and assert it
	 * Click on it and then Click on View Health Information link
	 * Click on Close Viewer
	 * Log into Patient Portal
	 * Go to inbox
	 * open the first mail and validate the subject and date
	 * click ReviewHealthInformation
	 * click link on ShareWithADoctor
	 * Add Addresses and Validate
	 * Click On Close AfterSharingTheHealthInformation
	 * Click on Close Viewer
	 * =============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNonCCDImportThroughEHDC() throws Exception {

		log("Test Case: testNonCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(),phrtestcasesData.getccdUserPassword());

		log("step 4:Post NON_CONSOLIDATED_CCD request");
		pPhrHomePage.postNonCCdRequest(phrtestcasesData.getallScriptAdapterURL());

		log("step :Wait for page to be loaded completely");
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

		log("step 5:Click on Document link");
		PhrDocumentsPage pPhrDocumentsPage= pPhrHomePage.clickDocuments();

		log("step 6:Refresh Document page before checking the Table list ");
		pPhrDocumentsPage.refreshPage(driver);

		log("step 7:Get first date from table list ");
		pPhrDocumentsPage.getFirstDate();
		Assert.assertNotNull( "### Couldn't find date column for new record", pPhrDocumentsPage.getFirstDate());
		log("getFirstDate>>"+pPhrDocumentsPage.getFirstDate() +"getPSTTiming>>"+pPhrDocumentsPage.getPstTimings());
		Assert.assertEquals(pPhrDocumentsPage.getPstTimings() , pPhrDocumentsPage.getFirstDate(),"### Can't find record with todays date" );

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
		PortalLoginPage portalloginpage = new PortalLoginPage(driver,
				portalTestData.geturl());
		MyPatientPage pMyPatientPage = portalloginpage.login(
				phrtestcasesData.getccdUserName(),
				phrtestcasesData.getccdUserPassword());

		log("step 13: Go to Inbox");
		ConsolidatedInboxPage inboxPage  = pMyPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 14: Find message in Inbox");
		ConsolidatedInboxMessage pConsolidatedInboxMessage = inboxPage
		.clickFirstMessageRow();

		log("step 15: Validate message subject and send date");
		Thread.sleep(1000);
		assertEquals(pConsolidatedInboxMessage.getMessageSubject(),
				"New Health Information Import",
		"### Assertion failed for Message subject");
		log("######  Message Date :: " + IHGUtil.getEstTiming());
		assertTrue(verifyTextPresent(driver, IHGUtil.getEstTiming()));

		log("step 15: Click on link ReviewHealthInformation");
		pConsolidatedInboxMessage.clickBtnReviewHealthInformation();

		log("step 10:Share the address with the Doctor and click Close Viewer");
		pConsolidatedInboxMessage.closeViewer();
	}



	/**
	 * @Author:- pjhinjha
	 * @Date:-7/10/2013
	 * @User Story ID in Rally-US6324
	 * @StepsToReproduce:
	 * LogIn to PHR portal using ccduser from excel
	 * Post CCD request with Medicines
	 * Login to Patient Portal
	 * Go to Prescription renewal
	 * Click on it and see the medications part in prescription renewal
	 * Click on logout
	 * =============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMedSyncToPortal() throws Exception {

		log("Test Case: testMedSyncToPortal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(),phrtestcasesData.getccdUserPassword());

		log("step 4:Post NON_CONSOLIDATED_CCD request");
		pPhrHomePage.postNonCCdRequest(phrtestcasesData.getallScriptAdapterURL());

		log("step :Wait for page to be loaded completely");
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

		log("step 5:LogIn to Patient Portal ");
		Portal portal = new Portal();
		TestcasesData portalTestData = new TestcasesData(portal);
		PortalLoginPage portalloginpage = new PortalLoginPage(driver,
				portalTestData.geturl());
		MyPatientPage pMyPatientPage = portalloginpage.login(
				phrtestcasesData.getccdUserName(),
				phrtestcasesData.getccdUserPassword());

		log("step 6: Go to Prescription Renewal");
		NewRxRenewalPage newRxRenewalPage = pMyPatientPage.clickPrescriptionRenewal();

		log("step 7: Select provider");
		newRxRenewalPage.chooseProvider("Geisel");

		log("step 8: Click on continue button");
		newRxRenewalPage.clickContinuebtn();

		log("step 9: Check for medications");
		newRxRenewalPage.checkMedication();
		if(IHGUtil.getEnvironmentType().equals(EnvironmentType.PROD)){
			Assert.assertEquals(PortalConstants.MedicineNameOne, newRxRenewalPage.medicineName0.getText());
			Assert.assertEquals(PortalConstants.MedicineNameTwo, newRxRenewalPage.medicineName1.getText());
		}
		else{
			Assert.assertEquals(PortalConstants.MedicineName1, newRxRenewalPage.medicineName1.getText());
			Assert.assertEquals(PortalConstants.MedicineName2, newRxRenewalPage.medicineName2.getText());
		}

		log("step 10: Logout of Patient Portal");
		pMyPatientPage.logout(driver);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEmergencyAccess() throws Exception {

		log("Test Case: testEmergencyAccess");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(),phrtestcasesData.getccdUserPassword());

		log("step 4:Click on sharing");
		PhrSharingPage pPhrSharingPage = pPhrHomePage.clickSharing();

		log("step 5:Go to manage permissions");
		PhrManagePermissionsPage pPhrManagePermissionspage = pPhrSharingPage.clickManagePermissionsPage();
		System.out.println("on manage permissions page");

		log("step 6: go to emergency access page for first time user ");

		if(pPhrManagePermissionspage.hasAllowEmergencyAccess())
		{

			PhrEmergencyTermsOfUse pPhrEmergencyToU = pPhrManagePermissionspage.clickAllowEmergencyAccess();
			pPhrEmergencyToU.clickAcknowledgement();
			pPhrEmergencyToU.clickSubmit();

		}

		else
		{
			log("step 7: go to edit & click emergency responder card");
			PhrEmergencyAccessPage pPhrEmergencyAccessPage = pPhrManagePermissionspage.clickEdit();
			pPhrEmergencyAccessPage.clickEmergencyResponderCard();

			log("step 8: accept alert");
			driver.switchTo().alert().accept();

			PhrUtil phrUtil=new PhrUtil(driver);
			String[] args={"ClickCancel.exe","FF","20000"};
			phrUtil.setExeArg(args);
			phrUtil.run();
			Thread.sleep(20000);
			
//			log("step 9: load the autoIt file");
//			Runtime.getRuntime().exec("D:\\IHG\\autoit\\ClickCancel.exe");

			log("step 10: switch to active window");
			for(String windowHandle : driver.getWindowHandles() )
			{
				driver.switchTo().window(windowHandle);
			}

			log("step 11: assert text present");
			Assert.assertTrue(verifyTextPresent(driver,"PIN"));
			
		}
	}

}



