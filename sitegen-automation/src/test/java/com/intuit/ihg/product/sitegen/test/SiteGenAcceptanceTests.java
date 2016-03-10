package com.intuit.ihg.product.sitegen.test;

import com.intuit.ihg.product.sitegen.SiteGenSteps;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.Integrations.CreateIntegrationStep1Page;
import com.intuit.ihg.product.object.maps.sitegen.page.Integrations.CreateIntegrationStep2Page;
import com.intuit.ihg.product.object.maps.sitegen.page.Integrations.ViewIntegrationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.InterfaceSetUp.InterfaceAdministrationPage;
import com.intuit.ihg.product.object.maps.sitegen.page.MerchantAccount.MerchantAccountPage;
import com.intuit.ihg.product.object.maps.sitegen.page.MerchantAccount.MerchantAccountSetUpPage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.location.AddLocationPage;
import com.intuit.ihg.product.object.maps.sitegen.page.location.ManageYourLocationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.permissionsAndPersonnelTypes.ManageUserPermissionsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.permissionsAndPersonnelTypes.ManageYourGroupPersonnelTypesPage;
import com.intuit.ihg.product.object.maps.sitegen.page.personnel.ExportPersonnelPage;
import com.intuit.ihg.product.object.maps.sitegen.page.personnel.ImportOrExportProgressPage;
import com.intuit.ihg.product.object.maps.sitegen.page.personnel.ImportPersonnelAndPhysiciansPage;
import com.intuit.ihg.product.object.maps.sitegen.page.personnel.ManageYourPersonnelPage;
import com.intuit.ihg.product.object.maps.sitegen.page.physicians.AddPhysicianPage;
import com.intuit.ihg.product.object.maps.sitegen.page.physicians.AddPhysicianStep2EditLocationInfoPage;
import com.intuit.ihg.product.object.maps.sitegen.page.physicians.ManageYourPhysiciansPage;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;

public class SiteGenAcceptanceTests extends BaseTestNGWebDriver {

	private void logTestEvironmentInfo(String testName) {
		log(testName);
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());
	}
	
	private void logSGLoginInfo(SitegenTestData testData) {
		log("URL: "+testData.getSiteGenUrl());
		log("Username: "+testData.getAutomationUser());
		log("Password: "+testData.getAutomationUserPassword());
	}

	
	/**
	 * @Author:-bkrishnankutty
	 * @Date:-6/9/2013
	 * @User Story ID in Rally : US6142
	 * @StepsToReproduce:
	 * List
	 * steps
	 * here
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testSiteGenLoginLogout() throws Exception {

		logTestEvironmentInfo("testSiteGen");

		log("step 1: Get Data from Excel ##########");
		
		Sitegen sitegen = new Sitegen();
		SitegenTestData testData = new SitegenTestData(sitegen);

		logSGLoginInfo(testData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getAutomationUser(), testData.getAutomationUserPassword());

		IHGUtil util =new IHGUtil(driver);
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");
		assertTrue(util.isRendered((pSiteGenHomePage.lnkHome)), "lnk Home not displayed");		
		assertTrue(util.isRendered((pSiteGenHomePage.lnkHelp)), "lnk Help not displayed");
		assertTrue(util.isRendered((pSiteGenHomePage.btnlogout)), "button logout not displayed");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();

		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver,"Administrator - Setup/Access"));
		assertTrue(verifyTextPresent(driver,"Portal Solutions"));
		assertTrue(verifyTextPresent(driver,"Additional Configurations"));

		loginpage = pSiteGenPracticeHomePage.clicklogout();
		assertTrue(loginpage.isSearchPageLoaded(), "Expected the SiteGen login Page  to be loaded, but it was not.");

	}

	/**
	 * @Author:-bkrishnankutty
	 * @Date:-6/12/2013
	 * @User Story ID in Rally :US6144
	 * @StepsToReproduce:
	 * Go to siteGen [https://dev3.dev.medfusion.net/admin/generator/index.cfm] 
	 * Enter the credentials
	 * Search for the practice
	 * Click on Locations
	 * Add a location
	 * Enter the details of location & Add Location
	 * Assert if Location is successfully added or not
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testLocation() throws Exception {

		logTestEvironmentInfo("testLocation");

		log("step 1: Get Data from Excel ##########");

		Sitegen sitegen=new Sitegen();
		SitegenTestData testData=new SitegenTestData(sitegen);

		logSGLoginInfo(testData);

		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps()
                .logInUserToSG(driver, testData.getAutomationUser(), testData.getAutomationUserPassword());

		log("step 5: navigate to ManageYour Locations Page ##########");
		ManageYourLocationsPage pManageYourLocationsPage= pSiteGenPracticeHomePage.clickLnkLocations();
//		assertTrue(pManageYourLocationsPage.isSearchPageLoaded(), "Expected the SiteGen Manage location Page  to be loaded, but it was not.");

		log("Check if the data is clean or not ##########");
		pManageYourLocationsPage.cleaningTestdata(SitegenConstants.PRACTICENAME, SitegenConstants.STATE);

		log("step 6: navigate to AddLocationPage ##########");
		AddLocationPage pAddLocationPage = pManageYourLocationsPage.clicklnkAddLocation();
		assertTrue(pAddLocationPage.isSearchPageLoaded(), "Expected the SiteGen Add location Page  to be loaded, but it was not.");

		log("step 7: Add Location ##########");
		pManageYourLocationsPage=pAddLocationPage.addLocation(SitegenConstants.PRACTICENAME, SitegenConstants.ADDRESS, SitegenConstants.CITY, SitegenConstants.STATE, SitegenConstants.COUNTRY, SitegenConstants.ZIPCODE, SitegenConstants.TELEPHONE, SitegenConstants.CONTACT, SitegenConstants.EMAIL);

		log("step 8: Assert if the Location got added ##########");
		assertTrue(pManageYourLocationsPage.isSearchPageLoaded(), "Expected the SiteGen Manage your location Page  to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver,SitegenConstants.STATE));
		assertTrue(verifyTextPresent(driver,SitegenConstants.PRACTICENAME));

		log("step 9:Test case Passed, Now Cleaning Test Data ##########");
		pManageYourLocationsPage.cleaningTestdata(SitegenConstants.PRACTICENAME, SitegenConstants.STATE);


	}


	/**
	 * @Author:-bkrishnankutty
	 * @Date:-6/18/2013
	 * @User Story ID in Rally : US6145
	 * @StepsToReproduce:
	 *                    Go to siteGen
	 *                    Enter the credentials
	 *                    Click on Physician/Providers
	 *                    Add a physician
	 *                    Enter the details of physician
	 *                    Assert if physician is added or not
	 * @throws Exception
	 */


	private void testPhysicianBoth(boolean su) throws Exception {

		logTestEvironmentInfo("testPhysician");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testData = new SitegenTestData(sitegen);

		logSGLoginInfo(testData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage = new SiteGenHomePage(driver);
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenPracticeHomePage(driver);
		if (su) {
			pSiteGenHomePage = loginpage
					.login(testData.getAdminUser(), testData.getAdminPassword());
			assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
					"Expected the SiteGen HomePage  to be loaded, but it was not.");
			log("step 3: navigate to SiteGen PracticeHomePage ##########");
			pSiteGenHomePage.searchPracticeFromSGAdmin(testData.getAutomationPracticeName());
		} else {
			pSiteGenHomePage = loginpage.login(testData.getAutomationUser(),
					testData.getAutomationUserPassword());
			log("step 3: navigate to SiteGen PracticeHomePage ##########");
			pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		}
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 4: click Link Physicians and navigate to Manage Your Physicians Page ##########");
		ManageYourPhysiciansPage pManageYourPhysiciansPage = pSiteGenPracticeHomePage
				.clickLnkPhysicians();
		assertTrue(pManageYourPhysiciansPage.isSearchPageLoaded(),
				"Expected the Manage Your Physicians Page  to be loaded, but it was not.");

		log("#####CHECK IF TESTDATA IS CLEAN ##########");
		pManageYourPhysiciansPage.cleanTestPhysiciansData();

		log("step 5: click Link Add Physicians and navigate to Add Physicians Page ##########");
		AddPhysicianPage pAddPhysicianPage = pManageYourPhysiciansPage.clicklnkAddPhysician();
		assertTrue(pAddPhysicianPage.isSearchPageLoaded(),
				"Expected the Add Physician Page to be loaded, but it was not.");

		// creating dynamic last name , user Id and email , Sp case so adding logic in testcase itself
		String lastName = SitegenConstants.LASTNAME + IHGUtil.createRandomNumber();
		String email = IHGUtil.createRandomEmailAddress(SitegenConstants.EMAIL);

		log("step 6: Add Physicians details ##########");
		if (su) {
			assertFalse(pAddPhysicianPage.isActiveGroupMemberYesOptionDisabled(),
					"Activating Physician should be enabled");
		} else {
			assertTrue(pAddPhysicianPage.isActiveGroupMemberYesOptionDisabled(),
					"Activating Physician should be disabled");
		}

		AddPhysicianStep2EditLocationInfoPage pAddPhysicianStep2EditLocationInfoPage = pAddPhysicianPage
				.addPhysician(SitegenConstants.FIRSTNAME, lastName, SitegenConstants.TITLE,
						SitegenConstants.DEANUMBER, email, lastName, SitegenConstants.PASSWORD);
		assertTrue(pAddPhysicianStep2EditLocationInfoPage.isSearchPageLoaded(),
				"Expected the Add Physician Step2 Edit Location Information Page  to be loaded, but it was not.");

		log("step 7: Assert if  Physicians added or not ##########");
		assertTrue(verifyTextPresent(driver, "Information Updated"));
		String provider = SitegenConstants.FIRSTNAME + " " + lastName;
		assertTrue(verifyTextPresent(driver, "Edit Location Information for " + provider));

		log("####### PHYSICAN ADDED AND TEST CASE PASSED ##########");
		log("####### TEST DATA CLEANING PROCESS  IS GOING START ##########");

		log("step 8: click Button GoBackToManagePhysicians ##########");
		pManageYourPhysiciansPage = pAddPhysicianStep2EditLocationInfoPage
				.clickBtnGoBackToManagePhysicians();

		log("step 9:click link EditPhysician ##########");
		pAddPhysicianPage = pManageYourPhysiciansPage.clicklnkEditPhysician();
		assertTrue(pAddPhysicianPage.isSearchPageLoaded(),
				"Expected the Add Physician Page  to be loaded, but it was not.");

		log("step 10:click on button Delete Physician ##########");
		pAddPhysicianStep2EditLocationInfoPage = pAddPhysicianPage.deletePhysician();
		/*
		 * log("Are you sure you wish to permanently delete: "+ provider+"?");
		 * assertTrue(verifyTextPresent(driver,"Are you sure you wish to permanently delete: "+ provider+"?"));
		 */

		log("step 11:give Confirmation to delete operation ##########");
		pManageYourPhysiciansPage = pAddPhysicianStep2EditLocationInfoPage.deletePhysican();
		assertTrue(pManageYourPhysiciansPage.isSearchPageLoaded(),
				"Expected the Manage Your Physicians Page  to be loaded, but it was not.");

		log("step 12:Assert if deleted or not ##########");
		assertTrue(verifyTextPresent(driver, "Information Updated"));
		// assertFalse(verifyTextPresent(driver,pManageYourPhysiciansPage.getProviderName(lastName,
		// SitegenConstants.FIRSTNAME, SitegenConstants.TITLE)));
		verifyTextPresent(driver, "Edit Physician");
	}

	/**
	 * @author phajek
	 *         Run testPhysician as non-SU and check if activating is disabled
	 * @throws Exception
	 */

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPhysician() throws Exception {

		testPhysicianBoth(false);
	}

	/**
	 * @author phajek
	 *         Run testPhysician as SU and check if activating is enabled
	 * @throws Exception
	 */

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPhysicianSU() throws Exception {

		testPhysicianBoth(true);
	}


	/**
	 * @Author:-bkrishnankutty
	 * @Date:- 6-21-2013
	 * @User Story ID in Rally : US6146
	 * @StepsToReproduce:
	 *Go to siteGen 
	 *Enter the credentials
	 *Search for the practice
	 *Add a Nurse
	 *Click on Permissions & Personnel types
	 *Go to manage permission for the personnel for whom we want to change permissions
	 *Give permissions
	 *
	 *=== Prerequisite for the test case to run=========
	 * Nurse Named :- "Auto, Sitegen: Nurse" should exist
	 *
	 * === Test will only work in DEMO ,In DEV3 is having a bug in application===
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testPermission() throws Exception {

		log("@@@@@@ WARNING 6-25-2013 ISSUES in DEV3 build so this testcase wont be working there ######");
		log("@@@@@@ Also do check the status of ISSUES DE1178,DE1179 ######");

		logTestEvironmentInfo("testPermission");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testData=new SitegenTestData(sitegen);

		logSGLoginInfo(testData);

        SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps()
                .logInUserToSG(driver, testData.getAutomationUser(), testData.getAutomationUserPassword());

		log("step 4: Click on Link Permissions and Personnel Types");
		ManageYourGroupPersonnelTypesPage pManageYourGroupPersonnelTypesPage=pSiteGenPracticeHomePage.clickLnkPermissions();
		assertTrue(pManageYourGroupPersonnelTypesPage.isSearchPageLoaded(), "Expected the Manage Your Group Personnel Types to be loaded, but it was not.");

		log("step 5: Click on Link Manage Permissions for Nurses");
		ManageUserPermissionsPage pManageUserPermissionsPage= pManageYourGroupPersonnelTypesPage.clicklnkManagePermissions4Nurses();
		assertTrue(pManageUserPermissionsPage.isSearchPageLoaded(), "Expected the Manage User Permissions Page to be loaded, but it was not.");

		log("step 6: Click on Link Manage Permissions for Nurses");
		pSiteGenPracticeHomePage=pManageUserPermissionsPage.givePermission2Nurse(SitegenConstants.PERSONNELTYPE1,SitegenConstants.PERSONNELTYPE2,SitegenConstants.SOLUTIONS,SitegenConstants.LOCATIONS,SitegenConstants.USERS);

		pSiteGenPracticeHomePage.clicklogout();
		assertTrue(PageFactory.initElements(driver, SiteGenLoginPage.class).isSearchPageLoaded(),
                "Expected the SiteGen login Page  to be loaded, but it was not.");

		log("step 7: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData=new PracticeTestData(practice);
		log("practiceTestData.getUrl()"+practiceTestData.getUrl());

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testData.getpersonnelTypeUserName(), testData.getpersonnelTypePswd());

		log("step 8: verify AptRequest Tab in Practice Portal");
		assertTrue(practiceHome.verifyAptRequestTab(),"Appointment tab not displayed");

		log("step 9: Logout");
		practiceHome.logOut();

	}



	/**
	 * @Author:-bkrishnankutty
	 * @Date:- 6-21-2013
	 * @User Story ID in Rally : US6153 && US6154
	 * @StepsToReproduce:
	 *===========US6154 :- Integration Set UP===============

	 *LogIn to SiteGen PracticeHomePage
	 *Click on Interface set up link 
	 *Add new Integration Engine
	 *Assert the newly added Integration Engine
	 *Go back to Sitegen home page

	 *==========US6153 :- Integration Engine=====================

	 *click on Link MedfusionSiteAdministration
	 *click Link IntegrationEngine
	 *click Link CreateIntegrationEngine
	 *Add new IntegrationEngine
	 *Assert if New IntegrationEngine is added or not

	 *==================================================
	 *Clean the data

	 *=== Prerequisite for the test case to run=========
	 * EXTERNAL_SYSTEM = "Allscripts Practice Management System" 
	 * Should be there for the Integration Engine testcase to run

	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testIntegrationEngAndInterfaceSetUp() throws Exception {

		logTestEvironmentInfo("testIntergationEngAndInterfaceSetUp");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testData=new SitegenTestData(sitegen);

		logSGLoginInfo(testData);

        SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps()
                .logInUserToSG(driver, testData.getAutomationUser(), testData.getAutomationUserPassword());

		log("step 4: Click on Interface set up link ##########");
		InterfaceAdministrationPage pInterfaceAdministrationPage=pSiteGenPracticeHomePage.clickLnkInterfaceSetup();
		assertTrue(pInterfaceAdministrationPage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("###### Cleaning the testdata for Integration Type##########");
		pInterfaceAdministrationPage.cleanTestDataIntegrationEng();

		log("step 5: Add new Integration Engine##########");
		String  integrationEngine=pInterfaceAdministrationPage.AddIntegrationEngine();
		assertEquals(integrationEngine, "Integration Engine", "Wrong Integration type added");

		log("step 6: go back to Sitgen Home page ##########");
		SiteGenHomePage pSiteGenHomePage= pInterfaceAdministrationPage.clickLinkHome();
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 7: click on Link MedfusionSiteAdministration ##########");
		pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the View Integrations Page to be loaded, but it was not.");

		log("step 8: click Link IntegrationEngine ##########");
		ViewIntegrationsPage pViewIntegrationsPage = pSiteGenPracticeHomePage.clickLnkIntegrationEngine();
		assertTrue(pViewIntegrationsPage.isSearchPageLoaded(), "Expected the View Integrations Page to be loaded, but it was not.");

		log("step 9: click Link Create IntegrationEngine ##########");
		CreateIntegrationStep1Page pCreateIntegrationPage = pViewIntegrationsPage.clickLnkCreateIntegration();
		assertTrue(pCreateIntegrationPage.isSearchPageLoaded(), "Expected the View Integrations Page to be loaded, but it was not.");

		log("step 10: Add New IntegrationEngine ##########");
		CreateIntegrationStep2Page pCreateIntegrationStep2Page = pCreateIntegrationPage.addNewIntegrationEng(SitegenConstants.EXTERNAL_SYSTEM, SitegenConstants.CHANNEL, SitegenConstants.INTEGRATION_NAME, SitegenConstants.REVIEWTYPE);
		assertTrue(pCreateIntegrationStep2Page.isSearchPageLoaded(), "Expected the View Integrations Page to be loaded, but it was not.");
		log("step 11: click btn SaveAndContinue ##########");
		pViewIntegrationsPage = pCreateIntegrationStep2Page.clickbtnSaveAndContinue();
		log("step 12: Assert if New IntegrationEngine is added or not##########");
		assertTrue(verifyTextPresent(driver,"Integration '"+SitegenConstants.INTEGRATION_NAME+"' updated successfully."));

		log("step 13: verify if Integrations Engine is added or not ##########");
		assertTrue(pViewIntegrationsPage.verifyIfIntegrationsEngineIsAdded(SitegenConstants.INTEGRATION_NAME),"INTEGRATION_Engine not added");

		log("#######Testcase passed ##########");
		log("#######clean Integration TestData ##########");
		pViewIntegrationsPage.cleanIntegrationTestData();		
	}

	/**
	 * @throws Exception 
	 * @Author:-bbinisha
	 * @Date :- 07-03-2013
	 * @UserStory ID in Rally : US6150
	 * @StepsToReproduce:
	 * Login to Sitegen platform
	 * Select any Practice
	 * Select MerchantAccount solution
	 * Select Paypal in the options
	 * Goto Merchant Account Setup
	 * Provide data and save
	 * 
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMerchantAccountSetUpViaPaypal() throws Exception{

		logTestEvironmentInfo("testMerchantAccountSetUpViaPaypal");

		log("Step1 :- Getting data from excel.");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testCaseData = new SitegenTestData(sitegen);

		logSGLoginInfo(testCaseData);

        SiteGenPracticeHomePage practiseHome = new SiteGenSteps()
                .logInUserToSG(driver, testCaseData.getAutomationUser(), testCaseData.getAutomationUserPassword());

		MerchantAccountPage merchantAcctPage = practiseHome.clickOnMerchantAccountLink();

		merchantAcctPage.deleteExistingMerchantAcct();

		MerchantAccountSetUpPage merchantAcctSetUp = merchantAcctPage.clickOnMerchantAccountSetUp();

		merchantAcctSetUp.clickOnPracticeRadioButton();
		merchantAcctSetUp.selectProcessorValue(SitegenConstants.PROCESSORVALUE1);
		merchantAcctSetUp.enterUsername(SitegenConstants.SetUPPracticeUserName);
		merchantAcctSetUp.enterPassword(SitegenConstants.SetUPPracticePassword);
		merchantAcctSetUp.selectPartnerValue(SitegenConstants.PartnerValue3);
		merchantAcctSetUp.clickOnSaveChanges();

		log("Verify whether the Merchant Account added successfully");
		assertEquals(merchantAcctSetUp.getAccountAddedSuccessMsg(), SitegenConstants.expSuccessMessage, "Merchant Account added successfully message is not getting displayed");

		practiseHome.clickOnMerchantAccountLink();

		log("Verify whether the Account is Added in the Merchant Account List");
		assertTrue(merchantAcctPage.verifyAcctInMerchantAcctList(), "Merchant Account not added in the Merchant Account List");

	}
	
	/**
	 * @Author:-bkrishnankutty
	 * @Date:-7/8/2013
	 * @User Story ID in Rally : US6148 & US6147
	 * @StepsToReproduce:
	 * 
	 *===######Export Staff#####=====
	 *Go to siteGen 
	 *Enter the credentials
	 *Search for the practice
	 *Click on Physician/Providers
	 *Click on Export Personel
	 *Click on Export Staff
	 *Click on Downloaded Export File
	 *Assert if "csv file should be downloaded with the information"
	 *
	 *===######Import Staff#####=====
	 *Go to siteGen 
	 *Enter the credentials
	 *Search for the practice
	 *Click on Physician/Providers
	 *Click on import personnel and physicians
	 *Select the file that need to be imported
	 *Assert the information in the file


	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testImportAndExportStaff() throws Exception {

		logTestEvironmentInfo("testImportAndExportStaff");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testData=new SitegenTestData(sitegen);

		logSGLoginInfo(testData);

        SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenSteps()
                .logInUserToSG(driver, testData.getAutomationUser(), testData.getAutomationUserPassword());

		log("step 4: Click on Link Permissions and Personnel Types ###########");
		ManageYourPersonnelPage pManageYourPersonnelPage=pSiteGenPracticeHomePage.clickLnkPersonnelNonPhysicians();
		assertTrue(pManageYourPersonnelPage.isSearchPageLoaded(), "Expected the Manage Your Personnel Page to be loaded, but it was not.");

		log("step 5: Click on Link Import Personnel And Physicians ##########");
		ImportPersonnelAndPhysiciansPage pImportPersonnelAndPhysicians = pManageYourPersonnelPage.clickBtnImportPersonnelAndPhysicians();
		assertTrue(pImportPersonnelAndPhysicians.isSearchPageLoaded(), "Expected the Import Personnel And Physicians Page to be loaded, but it was not.");

		log("step 6: Import the csv file from the test\resource\testfile location ##########");
		ImportOrExportProgressPage pImportOrExportProgressPage = pImportPersonnelAndPhysicians.clickbtnimportStaffFile();
		assertTrue(verifyTextPresent(driver,"Import/Export Progress"),"Import/Export Progress text is not present on Import/Export Progress");

		log("step 7: Wait so it can be imported and click on Link List All Personnel");
		Thread.sleep(3000);
		pImportPersonnelAndPhysicians = pImportOrExportProgressPage.clickLinkImportPersonnelAndPhysicians();

		log("#####  Assertions For Import staff ###########");
		assertTrue(pImportPersonnelAndPhysicians.verifyImportStaffCreationDate(pImportPersonnelAndPhysicians.getImportStaffCreateDate(), IHGUtil.getEstTimingWithTime()));
		assertEquals(pImportPersonnelAndPhysicians.getFileName(), SitegenConstants.IMPORTSTAFFFILENAME);
		assertEquals(pImportPersonnelAndPhysicians.getStatus(), SitegenConstants.FILEIMPORTSTATUS);

		log("@@@@@@@@@@@ Import staff testcase passed @@@@@@@@@@@@@@@@@@");
		log("@@@@@@@@@@@ Export staff testcase Getting Started @@@@@@@@@@@@@@@@@@");

		log("step 8: Click on link ExportPersonnel");
		ExportPersonnelPage pExportPersonnel = pImportPersonnelAndPhysicians.clickLinkExportPersonnel();
		assertTrue(pExportPersonnel.isSearchPageLoaded(), "Expected the SiteGen Export Personnel page to be loaded, but it was not.");
		pExportPersonnel.clickBtnExportStaff();

		log("step 9: click on Download link (Export staff) -- validate HTTP Status Code");
		assertEquals(pExportPersonnel.clickLinkDownloadExportStaff(), 200,
				"Download of Export staff returned unexpected HTTP status code");

	}
	

	/**
	 * @throws Exception 
	 * @Author:-bbinisha
	 * @Date :- 07-03-2013
	 *  @UserStrory ID in Rally : US6407
	 * @StepsToReproduce:
	 * Login to Sitegen platform as SU
	 * Select the Practice
	 * Select MerchantAccount solution
	 * Select QBMS in the options
	 * Goto Merchant Account Setup
	 * Provide data and save
	 * Verify whether the Merchant Account is added in Merchant Account List
	 * ================================================================================
	 * 
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testMerchantAccountSetUpViaQBMS() throws Exception{

		log("testMerchantAccountSetUpViaQBMS");
		log("Envronment on which test is running is :"+IHGUtil.getEnvironmentType());
		log("Browser on which Test is running :"+TestConfig.getBrowserType());

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testData = new SitegenTestData(sitegen);

		logSGLoginInfo(testData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage = new SiteGenHomePage(driver);
		pSiteGenHomePage = loginpage
				.login(testData.getAdminUser(), testData.getAdminPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
				"Expected the SiteGen HomePage  to be loaded, but it was not.");
		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage practiceHome = pSiteGenHomePage.searchPracticeFromSGAdmin(testData.getAutomationPracticeName());

		log("Step 6 :- Navigating to the Merchant Account List page.");
		MerchantAccountPage merchantAcctPage = practiceHome.clickOnMerchantAccountLink();

		log("Step 7 :- Delete Existing Merchant Account.");
		merchantAcctPage.deleteExistingMerchantAcct();

		log("Step 8 :- Navigating to the Merchant Account Set Up Page.");
		MerchantAccountSetUpPage merchantAcctSetUp = merchantAcctPage.clickOnMerchantAccountSetUp();
		merchantAcctSetUp.clickOnPracticeRadioButton();	

		log("Step 9 : Entering Account details");
		merchantAcctSetUp.selectStatus(SitegenConstants.statusValue2);
		merchantAcctSetUp.selectProcessorValue(SitegenConstants.PROCESSORVALUE2);
		merchantAcctSetUp.enterMerchantToken(SitegenConstants.merchantAcctQBMSToken);
		merchantAcctSetUp.enterMerchantTestToken(SitegenConstants.merchantAcctQBMSToken);

		log("Step 10 : Saving the account details");
		merchantAcctSetUp.clickOnSaveChanges();

		log("Verify whether the Merchant Account added successfully");
		assertEquals(merchantAcctSetUp.getAccountAddedSuccessMsg(), SitegenConstants.expSuccessMessage, "Merchant Account is not getting added");

		log("Step 11 : Navigate to merchant account List");
		practiceHome.clickOnMerchantAccountLink();

		log("Verify whether the Account is Added in the Merchant Account List");
		assertTrue(merchantAcctPage.verifyAcctInMerchantAcctList(), "Merchant Account not added in the Merchant Account List");

		merchantAcctPage.deleteExistingMerchantAcct();
	}
}
