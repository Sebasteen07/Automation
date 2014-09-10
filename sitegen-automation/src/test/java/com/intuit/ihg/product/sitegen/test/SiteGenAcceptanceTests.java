package com.intuit.ihg.product.sitegen.test;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.healthform.HealthFormPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormBasicInfoPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormCurrentSymptomsPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormIllnessConditionsPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormMedicationsPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormSocialHistoryPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormWelcomePage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.SpecialCharFormFirstPage;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.SpecialCharFormSecondPage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.customform.SearchPatientFormsPage;
import com.intuit.ihg.product.object.maps.practice.page.customform.SearchPatientFormsResultPage;
import com.intuit.ihg.product.object.maps.practice.page.customform.ViewPatientFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.Integrations.CreateIntegrationStep1Page;
import com.intuit.ihg.product.object.maps.sitegen.page.Integrations.CreateIntegrationStep2Page;
import com.intuit.ihg.product.object.maps.sitegen.page.Integrations.ViewIntegrationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.InterfaceSetUp.InterfaceAdministrationPage;
import com.intuit.ihg.product.object.maps.sitegen.page.MerchantAccount.MerchantAccountPage;
import com.intuit.ihg.product.object.maps.sitegen.page.MerchantAccount.MerchantAccountSetUpPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.AddQuestionsToCategoryPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomFormPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomForms;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormAddCategoriesPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormLayoutPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CustomFormPreviewPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.ManageYourFormsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsPage;
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
import com.intuit.ihg.product.portal.tests.CheckOldCustomFormTest;
import com.intuit.ihg.product.portal.tests.CreatePatientTest;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;
//import com.intuit.ihg.common.utils.ccd.CCDTest;

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
		SitegenTestData testcasesData = new SitegenTestData(sitegen);

		logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage=loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());

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
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage=loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 5: naviagate to ManageYour Locations Page ##########");
		ManageYourLocationsPage pManageYourLocationsPage= pSiteGenPracticeHomePage.clickLnkLocations();
//		assertTrue(pManageYourLocationsPage.isSearchPageLoaded(), "Expected the SiteGen Manage location Page  to be loaded, but it was not.");

		log("Check if the data is clean or not ##########");
		pManageYourLocationsPage.cleaningTestdata(SitegenConstants.PRACTICENAME, SitegenConstants.STATE);

		log("step 6: naviagate to AddLocationPage ##########");
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
	 *Go to siteGen 
	 *Enter the credentials
	 *Search for the practice
	 *Click on Physician/Providers
	 *Add a physician
	 *Enter the details of physician
	 *Assert if  physician is added or not

	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testPhysician() throws Exception {

		logTestEvironmentInfo("testPhysician");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenPracticeHomePage(driver);
		SiteGenHomePage pSiteGenHomePage = new SiteGenHomePage(driver);
		
		if( IHGUtil.getEnvironmentType().toString().equals("DEV3")) {
		
		loginpage.login(testcasesData.getadminUser(), testcasesData.getadminPassword());
		
		log("step 3: navigate to SiteGen PracticeHomePage ##########");	
		pSiteGenHomePage.searchPracticeFromSGAdmin("Bangalore Test Practice");
		
		} else {
			
		loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");
		
		log("step 3: navigate to SiteGen PracticeHomePage ##########");	
		pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
		}
		
		
		log("step 4: click Link Physicians and navigate to Manage Your Physicians Page ##########");
		ManageYourPhysiciansPage pManageYourPhysiciansPage=pSiteGenPracticeHomePage.clickLnkPhysicians();
		assertTrue(pManageYourPhysiciansPage.isSearchPageLoaded(), "Expected the Manage Your Physicians Page  to be loaded, but it was not.");

		log("#####CHECK IF TESTDATA IS CLEAN ##########");
		pManageYourPhysiciansPage.cleanTestPhysiciansData();

		log("step 5: click Link Add Physicians and navigate to Add Physicians Page ##########");
		AddPhysicianPage pAddPhysicianPage=pManageYourPhysiciansPage.clicklnkAddPhysician();
		assertTrue(pAddPhysicianPage.isSearchPageLoaded(), "Expected the Add Physician Page to be loaded, but it was not.");

		//creating dynamic last name , user Id and email , Sp case so adding logic in testcase itself
		String lastName = SitegenConstants.LASTNAME + IHGUtil.createRandomNumber();
		String email= IHGUtil.createRandomEmailAddress(SitegenConstants.EMAIL);

		log("step 6: Add Physicians details ##########");
		AddPhysicianStep2EditLocationInfoPage pAddPhysicianStep2EditLocationInfoPage = pAddPhysicianPage.addPhysician(SitegenConstants.FIRSTNAME, lastName, SitegenConstants.TITLE, SitegenConstants.DEANUMBER, email, lastName, SitegenConstants.PASSWORD);
		assertTrue(pAddPhysicianStep2EditLocationInfoPage.isSearchPageLoaded(), "Expected the Add Physician Step2 Edit Location Information Page  to be loaded, but it was not.");

		log("step 7: Assert if  Physicians added or not ##########");
		assertTrue(verifyTextPresent(driver,"Information Updated"));
		String provider= SitegenConstants.FIRSTNAME + " " + lastName; 
		assertTrue(verifyTextPresent(driver,"Edit Location Information for "+provider));

		log("####### PHYSICAN ADDED AND TEST CASE PASSED ##########");
		log("####### TEST DATA CLEANING PROCESS  IS GOING START ##########");

		log("step 8: click Button GoBackToManagePhysicians ##########");
		pManageYourPhysiciansPage = pAddPhysicianStep2EditLocationInfoPage.clickBtnGoBackToManagePhysicians();

		log("step 9:click link EditPhysician ##########");
		pAddPhysicianPage = pManageYourPhysiciansPage.clicklnkEditPhysician();
		assertTrue(pAddPhysicianPage.isSearchPageLoaded(), "Expected the Add Physician Page  to be loaded, but it was not.");

		log("step 10:click on button Delete Physician ##########");
		pAddPhysicianStep2EditLocationInfoPage=pAddPhysicianPage.deletePhysician();
		/*log("Are you sure you wish to permanently delete: "+ provider+"?");
		assertTrue(verifyTextPresent(driver,"Are you sure you wish to permanently delete: "+ provider+"?"));*/

		log("step 11:give Confirmation to delete operation ##########");
		pManageYourPhysiciansPage = pAddPhysicianStep2EditLocationInfoPage.deletePhysican();
		assertTrue(pManageYourPhysiciansPage.isSearchPageLoaded(), "Expected the Manage Your Physicians Page  to be loaded, but it was not.");

		log("step 12:Assert if deleted or not ##########");
		assertTrue(verifyTextPresent(driver,"Information Updated"));
//		assertFalse(verifyTextPresent(driver,pManageYourPhysiciansPage.getProviderName(lastName, SitegenConstants.FIRSTNAME, SitegenConstants.TITLE)));
		verifyTextPresent(driver,"Edit Physician");
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
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage=loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 4: Click on Link Permissions and Personnel Types");
		ManageYourGroupPersonnelTypesPage pManageYourGroupPersonnelTypesPage=pSiteGenPracticeHomePage.clickLnkPermissions();
		assertTrue(pManageYourGroupPersonnelTypesPage.isSearchPageLoaded(), "Expected the Manage Your Group Personnel Types to be loaded, but it was not.");

		log("step 5: Click on Link Manage Permissions for Nurses");
		ManageUserPermissionsPage pManageUserPermissionsPage= pManageYourGroupPersonnelTypesPage.clicklnkManagePermissions4Nurses();
		assertTrue(pManageUserPermissionsPage.isSearchPageLoaded(), "Expected the Manage User Permissions Page to be loaded, but it was not.");

		log("step 6: Click on Link Manage Permissions for Nurses");
		pSiteGenPracticeHomePage=pManageUserPermissionsPage.givePermission2Nurse(SitegenConstants.PERSONNELTYPE1,SitegenConstants.PERSONNELTYPE2,SitegenConstants.SOLUTIONS,SitegenConstants.LOCATIONS,SitegenConstants.USERS);

		pSiteGenPracticeHomePage.clicklogout();
		assertTrue(loginpage.isSearchPageLoaded(), "Expected the SiteGen login Page  to be loaded, but it was not.");

		log("step 7: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData=new PracticeTestData(practice);
		log("practiceTestData.getUrl()"+practiceTestData.getUrl());

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testcasesData.getpersonnelTypeUserName(), testcasesData.getpersonnelTypePswd());

		log("step 8: verify AptRequest Tab in Practice Portal");
		assertTrue(practiceHome.verifyAptRequestTab(),"Apointment tab not displayed");

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
	public void testIntergationEngAndInterfaceSetUp() throws Exception {

		logTestEvironmentInfo("testIntergationEngAndInterfaceSetUp");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage=loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 4: Click on Interface set up link ##########");
		InterfaceAdministrationPage pInterfaceAdministrationPage=pSiteGenPracticeHomePage.clickLnkInterfaceSetup();
		assertTrue(pInterfaceAdministrationPage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("###### Cleaning the testdata for Integration Type##########");
		pInterfaceAdministrationPage.cleanTestDataIntegrationEng();

		log("step 5: Add new Integration Engine##########");
		String  integrationEngine=pInterfaceAdministrationPage.AddIntegrationEngine();
		assertEquals(integrationEngine, "Integration Engine", "Wrong Integration type added");

		log("step 6: go back to Sitgen Home page ##########");
		pSiteGenHomePage= pInterfaceAdministrationPage.clickLinkHome();
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
	 * @Author:-Shanthala  : Modified :bbinisha : Modified-Modified: Prokop Rehacek
	 * @Date:- 07-03-2013
	 * @User Story ID in Rally :  US6152 and US6151 and US7626
	 * @StepsToReproduce:
	 *Go to siteGen 
	 *Enter the credentials
	 *Search for the practice
	 *Click on Custom Form	
	 *Click on Create Custom Form
	 *Publish Custom Form and check for preview
	 *Unpublish Custom Form, check for Preview and delete unpublished custom form
	 *
	 *=== Prerequisite for the test case to run=========
	 * Nurse Named :- 
	 *
	 *====Valid Custom Form details required. Test data would be updated after getting proper test data
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testCustomFormPublished() throws Exception {

		logTestEvironmentInfo("testCustomFormPublished");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage=loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 4: Click on Custom Forms");
		String winHandleSiteGen = driver.getWindowHandle();
		CreateCustomForms pManageCustomForms=pSiteGenPracticeHomePage.clickCustomForms();
		String winHandleCustomBuilder = driver.getWindowHandle();

		log("step 5:Click on Manage Custom Form and delete custom form 'Automation Custom Form' if present");
		ManageYourFormsPage plinkOnManageCustomForm = pManageCustomForms.clicklnkManageCustomForm();
		verifyEquals(plinkOnManageCustomForm.isSearchPageLoaded(), true, "Expected SiteGen Manage custom form page to be loaded to unpublish the published form, but itwas not.");

		if(plinkOnManageCustomForm.isUnPublished(SitegenConstants.FORMTITLE)) {
			log("There is a form with the name 'Automation Custom Form' and is unpublished");

			verifyTrue(plinkOnManageCustomForm.isSearchPageLoadedForUnpublishedTable(), "Expected the SiteGen Manage custom form page to be loaded to delete unpublishedform , but it was not.");
			plinkOnManageCustomForm.deleteUnpublishedForm(SitegenConstants.FORMTITLE);
			log("Existing custom form named 'Automation Custom Form deleted");

		}else {
			log("step 6: Click on Create Custom Form");
			CreateCustomFormPage plinkOnCustomForm = pManageCustomForms.clicklnkCreateCustomForm();

			String customFormTitle = SitegenConstants.FORMTITLE+IHGUtil.createRandomNumber();

			log("step 7: Enter Custom Form details");
			assertTrue(plinkOnCustomForm.isSearchPageLoaded(), "Expected the SiteGen Create a Custom Form page to be loaded to create a new custom form with details, but it was not.");
			CustomFormAddCategoriesPage pCustomFormAddCategories = plinkOnCustomForm.enterCustomFormDetails(SitegenConstants.FORMTYPE,customFormTitle,SitegenConstants.FORMINSTRUCTIONS,SitegenConstants.FORMMESSAGE);

			log("step 8: Build a Custom Form");
			assertTrue(pCustomFormAddCategories.isSearchPageLoaded(), "Expected the SiteGen Build a Custom Form page to be loaded to add categories into the custom form, but it was not.");
			AddQuestionsToCategoryPage pAddCAtegories = pCustomFormAddCategories.addCategoriesDetails(SitegenConstants.FORMCATEGORY);
			
			log("step 9A: Add Question1 to category 1");
			verifyTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add questions to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories.addQuestion1ToCategory(SitegenConstants.FORMQUESTION1), "Custom Form question1 and answerset1 did not updated successfully.");
			pAddCAtegories.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET1);
			pAddCAtegories.saveCategoryQuestions();
			
			CustomFormAddCategoriesPage pCustomFormAddCategories2 = pAddCAtegories.clickCustomFormAddCategoriesPage();
			AddQuestionsToCategoryPage pAddCAtegories2 = pCustomFormAddCategories2.addCategoriesDetails(SitegenConstants.FORMCATEGORY2);

			log("step 9B: Add Question2 to category 2");
			verifyTrue(pAddCAtegories2.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories2.addQuestion1ToCategory(SitegenConstants.FORMQUESTION2), "Custom Form question2 and answerset2 did not updated successfully.");
			pAddCAtegories2.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET2);
			pAddCAtegories2.saveCategoryQuestions();

			CustomFormAddCategoriesPage pCustomFormAddCategories3 = pAddCAtegories2.clickCustomFormAddCategoriesPage();
			AddQuestionsToCategoryPage pAddCAtegories3 = pCustomFormAddCategories3.addCategoriesDetails(SitegenConstants.FORMCATEGORY3);
			
			log("step 9C: Add Question3 to category 3");
			verifyTrue(pAddCAtegories3.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories3.addQuestion1ToCategory(SitegenConstants.FORMQUESTION3), "Custom Form question3 and answerset3 did not updated successfully.");
			pAddCAtegories3.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET3);

			log("step 9D: Save added questions to category");
			verifyTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),"Questions are not getting added to expected Category");
			pAddCAtegories.saveCategoryQuestions();
			
			CustomFormLayoutPage pAddQuestionsToCategory = pAddCAtegories.clickCustomFormLayoutPage();
			
			log("step 10: Set Custom Form Layout");
			verifyTrue(pAddQuestionsToCategory.isSearchPageLoaded(), "Expected the SiteGen form Layout page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Form Layout is not set for Expected Category");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),"Form Layout is not set for Expected Category");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY3),"Form Layout is not set for Expected Category");
			pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE, SitegenConstants.FORMCATEGORY);
			pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE2, SitegenConstants.FORMCATEGORY2);
			pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE3, SitegenConstants.FORMCATEGORY3);
			
			
			CustomFormPreviewPage pCustomFormPreview = pAddQuestionsToCategory.saveFormLayout();
			
			
			Thread.sleep(8000);
			log("step 11: Custom Form Preview Page to click on publish");
			verifyTrue(pCustomFormPreview.isSearchPageLoaded(), "Expected the SiteGen create custom form page preview with publish link to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY2),"Form Layout is not set for Expected Category");
			verifyTrue(verifyTextPresent(driver,customFormTitle),"Vewing custom form is not expected custom form");
			Thread.sleep(8000);
			//This assert statements can be changed after getting standard valid custom form from Richard/Don B
			//verifyEquals(verifyTextPresent(driver,"Insurance Type"),true,"Insurance Type is not present in form preview");
			verifyEquals(verifyTextPresent(driver,"First Name"),true, "Demographic information is not present in form preview");
			//verifyEquals(verifyTextPresent(driver,"Vital"),true, "Vital information is not present in form preview");
			ManageYourFormsPage pManageForm = pCustomFormPreview.clickOnPublishLink(); 

			log("step 12: Manage your forms -Check custom Form published successfully");
			verifyEquals(pManageForm.checkForPublishedPage(customFormTitle), true, "Custom Form did not published successfully and not present in published forms table");
			
			driver.switchTo().window(winHandleSiteGen);
			
			// Instancing CreatePatientTest
			CheckOldCustomFormTest checkOldCustomFormTest =  new CheckOldCustomFormTest();
			
			// Setting data provider
			Portal portal = new Portal();
			TestcasesData portalTestcasesData = new TestcasesData(portal);
				
			// Executing Test
			checkOldCustomFormTest.setUrl(pSiteGenPracticeHomePage.getPatientPortalUrl());
			String winHandlePatientPortal = driver.getWindowHandle();
			HealthFormPage page = checkOldCustomFormTest.checkOldCustomForm(driver, portalTestcasesData, customFormTitle);
		
			
			driver.switchTo().window(winHandleCustomBuilder);
			
			
			log("step 13: Manage your forms -Check published Form Preview by clicking on Preview link");
			verifyEquals(pManageForm.isSearchPageLoaded(),true, "Expected the SiteGen Manage your Forms -> published form preview page to be loaded, but it was not.");
			log("step 13a: Delete 2 pages");
			pManageForm.clickOnPublishedFormPreviewLink(customFormTitle);
			pAddCAtegories.clickCustomFormLayoutPage();
			pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE0, SitegenConstants.FORMCATEGORY);
			pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE0, SitegenConstants.FORMCATEGORY3);
			pAddQuestionsToCategory.categorySequence();
			
			pAddQuestionsToCategory.saveFormLayout();
			driver.switchTo().window(winHandlePatientPortal);
			
			checkOldCustomFormTest.checkDeletedPages(driver, page, customFormTitle);
			
			driver.switchTo().window(winHandleCustomBuilder);
			pCustomFormPreview.clickOnUnPublishLink(); 	

			log("step 14: Manage your forms -Check unpublished Form Preview");
			verifyEquals(pManageForm.isSearchPageLoaded(), true, "Expected the SiteGen Manage your Forms -> published form preview page to be loaded, but it was not.");
			pManageForm.clickOnUnpublishedFormPreviewLink(customFormTitle);

			log("step 15: Manage your forms -Click on publish link");
			verifyEquals(pCustomFormPreview.isSearchPageLoaded(),true, "Expected the SiteGen create custom form page preview with publish link to be loaded, but it was not.");
			pCustomFormPreview.clickOnPublishLink(); 

			log("step 16: Manage your forms -Check custom Form published was able to unpublish successfully");
			verifyEquals(pManageForm.isSearchPageLoadedForUnpublishedTable(),true, "Expected the SiteGen Manage your Forms -> unpublished form page to be loaded to unpublish the published form, but it was not.");
			pManageForm.unPublishThepublishedForm(customFormTitle);

			log("step 17: Manage your forms -Check custom Form unpublished was able to delete successfully");
			pManageForm.deleteUnpublishedForm(customFormTitle);
		}
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


		log("Step2 :- Opening sitegen home page");
		SiteGenLoginPage sloginPage= new SiteGenLoginPage (driver,testCaseData.getSiteGenUrl());
		SiteGenHomePage sHomePage = sloginPage.login(testCaseData.getAutomationUser(), testCaseData.getAutomationUserPassword());

		log("Step3 :- Navigating to the Practise page.");
		SiteGenPracticeHomePage practiseHome = sHomePage.clickLinkMedfusionSiteAdministration();

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
		verifyEquals(merchantAcctSetUp.getAccountAddedSuccessMsg().equals(SitegenConstants.expSuccessMessage), true, "Merchant Account added successfully message is not getting displayed");

		practiseHome.clickOnMerchantAccountLink();

		log("Verify whether the Account is Added in the Merchant Account List");
		verifyEquals(merchantAcctPage.verifyAcctInMerchantAcctList(), true, "Merchant Account not added in the Merchant Account List");

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
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		logSGLoginInfo(testcasesData);

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage=loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 4: Click on Link Permissions and Personnel Types ###########");
		ManageYourPersonnelPage pManageYourPersonnelPage=pSiteGenPracticeHomePage.clickLnkPersonnelNonPhysicians();
		assertTrue(pManageYourPersonnelPage.isSearchPageLoaded(), "Expected the Manage Your Personnel Page to be loaded, but it was not.");

		log("step 5: Click on Link Import Personnel And Physicians ##########");
		ImportPersonnelAndPhysiciansPage pImportPersonnelAndPhysicians = pManageYourPersonnelPage.clickBtnImportPersonnelAndPhysicians();
		assertTrue(pImportPersonnelAndPhysicians.isSearchPageLoaded(), "Expected the Import Personnel And Physicians Page to be loaded, but it was not.");

		log("step 6: Import the csv file from the test\resource\testfile location ##########");
		ImportOrExportProgressPage pImportOrExportProgressPage = pImportPersonnelAndPhysicians.clickbtnimportStaffFile();
		assertTrue(verifyTextPresent(driver,"Import/Export Progress"),"Import/Export Progress text is not present on Import/Export Progress");

		log("step 7: Click on Link List All Personnel");
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
	 * Login to Sitegen platform
	 * Select any Practice
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

		Sitegen sitegen = new Sitegen();
		SitegenTestData testCaseData = new SitegenTestData(sitegen);

		String userName = testCaseData.getAutomationUser();
		String password = testCaseData.getAutomationUserPassword();

		log("Step1 :- Getting data from excel.");
		logSGLoginInfo(testCaseData);

		log("Step2 :- Opening sitegen home page");
		SiteGenLoginPage sloginPage= new SiteGenLoginPage (driver,testCaseData.getSiteGenUrl());
		SiteGenHomePage sHomePage = sloginPage.login(userName, password);

		log("Step3 :- Navigating to the Practise page.");
		SiteGenPracticeHomePage practiseHome = new SiteGenPracticeHomePage(driver);

		log("Step 5 : Navigate to the Practice Page.");
		practiseHome = sHomePage.clickLinkMedfusionSiteAdministration();

		log("Step 6 :- Navigating to the Merchant Account List page.");
		MerchantAccountPage merchantAcctPage = practiseHome.clickOnMerchantAccountLink();

		log("Step 7 :- Delete Existing Merchant Account.");
		merchantAcctPage.deleteExistingMerchantAcct();

		log("Step 8 :- Navigating to the Merchant Account Set Up Page.");
		MerchantAccountSetUpPage merchantAcctSetUp = merchantAcctPage.clickOnMerchantAccountSetUp();
		merchantAcctSetUp.clickOnPracticeRadioButton();	

		log("Step 9 : Entering Account details");
		merchantAcctSetUp.selectProcessorValue(SitegenConstants.PROCESSORVALUE2);
		merchantAcctSetUp.enterMerchantToken(SitegenConstants.merchantAcctQBMSTokenForPROD);

		log("Step 10 : Saving the account details");
		merchantAcctSetUp.clickOnSaveChanges();

		log("Verify whether the Merchant Account added successfully");
		verifyEquals(merchantAcctSetUp.getAccountAddedSuccessMsg().equals(SitegenConstants.expSuccessMessage), true, "Merchant Account is not getting added");

		log("Step 11 : Navigate to merchant account List");
		practiseHome.clickOnMerchantAccountLink();

		log("Verify whether the Account is Added in the Merchant Account List");
		verifyEquals(merchantAcctPage.verifyAcctInMerchantAcctList(), true, "Merchant Account not added in the Merchant Account List");

		merchantAcctPage.deleteExistingMerchantAcct();
	}
	
	/**
	 * @User Story ID in Rally: US544 - TA30648
	 * @StepsToReproduce: TODO
	 * === Prerequisite for the test case to run=========
	 * Practice configured
	 * Practices configured on: DEV3, DEMO
	 * ============================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testDiscreteFormDeleteCreatePublish() throws Exception {	
		logTestEvironmentInfo("testDiscreteForm");

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testcasesData = new SitegenTestData(sitegen);
	
		logSGLoginInfo(testcasesData);
	
		log("Step 2: Opening sitegen home page");
		SiteGenLoginPage sloginPage= new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage sHomePage = sloginPage.login(testcasesData.getFormUser(), testcasesData.getFormPassword());
	
		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = sHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
		
		String parentHandle = driver.getWindowHandle(); // Get the current window handle before opening new window
				
		log("step 4: Click on Patient Forms");
		DiscreteFormsPage pManageDiscreteForms = pSiteGenPracticeHomePage.clickLnkDiscreteForms();
		
		assertTrue(pManageDiscreteForms.isPageLoaded());
		
		log("step 5: Unpublish and delete all forms and create a new one");
		String discreteFormName = pManageDiscreteForms.initializePracticeForNewForm();
		log("@@discrete form name@@" + discreteFormName);	
		
		log("step 6: Initialize the new form");
		pManageDiscreteForms.prepareFormForPracticeTest(discreteFormName);
		
		log("step 7: Publish the saved Discrete Form");
		pManageDiscreteForms.publishTheSavedForm(discreteFormName);
		
		log("step 8: Close the window and logout from SiteGenerator");
		// Switching back to original window using previously saved handle descriptor
		driver.close();
		driver.switchTo().window(parentHandle);
		pSiteGenPracticeHomePage.clicklogout();
		
		log("step 9: Go to Patient Portal using the original window");
						
		Portal portal = new Portal();
		TestcasesData portalTestcasesData = new TestcasesData(portal);
		log("URL: " + portalTestcasesData.getFormsUrl());
		
		log("step 10:LogIn");  
		PortalLoginPage loginpage = new PortalLoginPage(driver, portalTestcasesData.getFormsUrl());
		MyPatientPage pMyPatientPage = loginpage.login(portalTestcasesData.getUsername(), portalTestcasesData.getPassword());
	
		log("step 11: Click On Start Registration Button and verify welcome page of the previously created form");
		FormWelcomePage pFormWelcomePage = pMyPatientPage.clickStartRegistrationButton(driver);
		assertTrue( pFormWelcomePage.welcomeMessageContent( pManageDiscreteForms.getWelcomeMessage() ));
	}
	
	@Test(enabled = false, groups = {"AcceptanceTests"})
	public void testFormPracticePortal() throws Exception {
		String currentDate = IHGUtil.getFormattedCurrentDate("yyyy-MM-dd"); // Will be used to validate forms update date
		String discreteFormName = "Form for Practice view test"; 
		
		logTestEvironmentInfo("testFormPracticePortal");

		log("Step 1: Get Data from Excel ##########");
		Portal portal = new Portal();
		TestcasesData portalTestcasesData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalTestcasesData.getFormsAltUrl());
		
		log("Step 2: Log in to Patient Portal");  
		PortalLoginPage loginpage = new PortalLoginPage(driver, portalTestcasesData.getFormsAltUrl());
		MyPatientPage pMyPatientPage = loginpage.login(portalTestcasesData.getUsername(), portalTestcasesData.getPassword());
		
		log("Step 3: Go to forms page");
		HealthFormPage formPage = pMyPatientPage.clickFillOutFormsLink();
		
		log("Step 4: Open the right form");
		formPage.openDiscreteForm("practiceForm");
		
		log("Step 5: Fill out the form");
		
		FormBasicInfoPage demographPage = PageFactory.initElements(driver, FormBasicInfoPage.class);
		FormMedicationsPage medsPage = demographPage.clickSaveAndContinueButton(FormMedicationsPage.class);
		medsPage.setNoMedications();
		FormIllnessConditionsPage illsPage = medsPage.clickSaveAndContinueButton(FormIllnessConditionsPage.class);
		illsPage.checkMononucleosis();
		illsPage.clickSaveAndContinueButton(null);
		illsPage.submitForm();
		
		log("Step 5: Logout of patient portal");
		pMyPatientPage.logout(driver);
	
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		
		log("Step 6: Login to Practice Portal");
		
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());	
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getFormUser(), practiceTestData.getFormPassword());
		
		log("step 7: On Practice Portal Home page Click CustomFormTab");
		SearchPatientFormsPage pSearchPatientFormsPage = practiceHome.clickCustomFormTab();
		verifyTrue(pSearchPatientFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");
	
		log("step 8: Search for PatientForms With Status Open");
		SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage.SearchDiscreteFormsWithOpenStatus(discreteFormName);
		
		log("step 9: View the Result");
		ViewPatientFormPage pViewPatientFormPage = pSearchPatientFormsResultPage.clickViewLink();
		
		log("step 10: Verify date and download code");
		// take the year, month and day (yyyy-MM-dd - 10 chars) of form submission
		String submittedDate = pViewPatientFormPage.getLastUpdatedDateFormatted();
		assertEquals(submittedDate, currentDate, "Form submitted today not found");
		
		log("Download URL: " + pViewPatientFormPage.getDownloadURL());
		URLStatusChecker status = new URLStatusChecker(driver);
		assertEquals(status.getDownloadStatusCode(pViewPatientFormPage.getDownloadURL(), RequestMethod.GET), 200);
	}


	@Test(enabled = false)
	public void testQuotationMarksInForm() throws Exception {
		logTestEvironmentInfo("testDiscreteFormFill");

		log("Step 1: Get Data from Excel ##########");
		Portal portal = new Portal();
		TestcasesData portalTestcasesData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalTestcasesData.getFormsAltUrl());
		
		log("Step 2: Log in to Patient Portal");  
		PortalLoginPage loginpage = new PortalLoginPage(driver, portalTestcasesData.getFormsAltUrl());
		MyPatientPage pMyPatientPage = loginpage.login(portalTestcasesData.getUsername(), portalTestcasesData.getPassword());
		
		log("Step 3: Go to forms page");
		HealthFormPage formPage = pMyPatientPage.clickFillOutFormsLink();
		
		log("Step 4: Open the right form");
		formPage.openDiscreteForm("specialChars");
		
		log("Step 5: Fill the form out with values containing quotes");
		SpecialCharFormFirstPage customPage1 = PageFactory.initElements(driver, SpecialCharFormFirstPage.class);
		customPage1.selectQuotatedAnswers();
		SpecialCharFormSecondPage customPage2 = customPage1.clickSaveAndContinueButton(SpecialCharFormSecondPage.class);
		customPage2.selectAnswerQuoteMark();
		customPage2.signConsent();
		customPage2.clickSaveAndContinueButton(null);
		customPage2.submitForm();
	}
	
	/**
	 * @Author: Adam Warzel
	 * @Date: April-01-2014
	 * @UserStory: US7083
	 * 
	 * Tests if filling out a form generates a PDF, if link for downloading
	 * the PDF appears in Patient Portal and if the link is working.
	 * 
	 * Creates new patient
	 */
	
	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testDiscreteFormPDF() throws Exception {
		
//		long timestamp = System.currentTimeMillis() / 1000L;
//		String xml = new String();
//		String easyBruisingString = new String("Easy bruising");
		String diacriticString = new String("¿¡eñÑeŘ");
		
		logTestEvironmentInfo("testDiscreteFormPDF");
		
		Portal portal = new Portal();
		TestcasesData portalTestcasesData = new TestcasesData(portal);
		log("Patient Portal URL: " + portalTestcasesData.getFormsAltUrl());
	
		log("step 1: Click on Sign Up Fill detials in Create Account Page");
		String email = PortalUtil.createRandomEmailAddress(portalTestcasesData.getEmail());
		log("email:-" + email);
		
		CreatePatientTest createPatient = new CreatePatientTest();
		createPatient.setUrl(portalTestcasesData.getFormsAltUrl());
		MyPatientPage pMyPatientPage = createPatient.createPatient(driver, portalTestcasesData);
	
		log("step 2: Click on forms and open the form");
		HealthFormPage formsPage = pMyPatientPage.clickFillOutFormsLink();
		FormWelcomePage pFormWelcomePage = formsPage.openDiscreteForm("pdfForm");
		
		log("Step 3: Click through the form flow to Current Symptoms");
		FormBasicInfoPage basicInfoPage = pFormWelcomePage.clickContinueButton(FormBasicInfoPage.class);		
		FormCurrentSymptomsPage currentSymptomsPage = basicInfoPage.clickSaveAndContinueButton(FormCurrentSymptomsPage.class);
		
		log("Step 4: Select symptoms for the patient");
		currentSymptomsPage.setBasicSymptoms();
		currentSymptomsPage.enterComment(diacriticString);
		
		log("Step 5: Close and reopen the form");
		currentSymptomsPage.closeForm();
		formsPage.openDiscreteForm("pdfForm");
		
		log("Test if correct values are prefilled");
		verifyEquals(currentSymptomsPage.getCommentTextContent(), diacriticString);
		assertTrue( currentSymptomsPage.getCheckEarache().isSelected(),
					"The earache checkbox was not prefilled as expected" );
		
		log("Step 5: Go through the rest of the form and submit it");
		FormMedicationsPage pFormMedicationsPage = currentSymptomsPage.clickSaveAndContinueButton();
		pFormMedicationsPage.setMedicationFormFields();
	
		log("step 7: Set Social History Form Fields and submit the form");
		FormSocialHistoryPage pFormSocialHistoryPage = PageFactory.initElements(driver, FormSocialHistoryPage.class);
		pFormSocialHistoryPage.setSocialHistoryFormFields();
		
		log("Step 8: Test if PDF is downloadable");
		PortalUtil.setPortalFrame(driver);
		URLStatusChecker status = new URLStatusChecker(driver);
		assertTrue(formsPage.isPDFLinkPresent(), "PDF link not found, PDF not generated");
		assertEquals(status.getDownloadStatusCode(formsPage.getPDFDownloadLink(), RequestMethod.GET), 200);
		
//		log("Step 9: Test if CCD is produced");
//		log("Calling rest");
//		xml = CCDTest.getFormCCD(timestamp, portalTestcasesData.getRestUrl());
//		assertTrue(xml.contains(easyBruisingString));
	}
}
