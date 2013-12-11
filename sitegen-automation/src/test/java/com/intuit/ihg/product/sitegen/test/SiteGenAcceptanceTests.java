package com.intuit.ihg.product.sitegen.test;


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.WebDriverFactory;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.healthform.CustomFormPageForSitegen;
import com.intuit.ihg.product.portal.page.healthform.HealthFormPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormSocialHistoryPage;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.customform.SearchPatientFormsPage;
import com.intuit.ihg.product.practice.page.customform.SearchPatientFormsResultPage;
import com.intuit.ihg.product.practice.page.customform.ViewPatientFormPage;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.intuit.ihg.product.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.sitegen.page.Integrations.CreateIntegrationStep1Page;
import com.intuit.ihg.product.sitegen.page.Integrations.CreateIntegrationStep2Page;
import com.intuit.ihg.product.sitegen.page.Integrations.ViewIntegrationsPage;
import com.intuit.ihg.product.sitegen.page.InterfaceSetUp.InterfaceAdministrationPage;
import com.intuit.ihg.product.sitegen.page.MerchantAccount.MerchantAccountPage;
import com.intuit.ihg.product.sitegen.page.MerchantAccount.MerchantAccountSetUpPage;
import com.intuit.ihg.product.sitegen.page.customforms.AddQuestionsToCategoryPage;
import com.intuit.ihg.product.sitegen.page.customforms.CreateCustomFormPage;
import com.intuit.ihg.product.sitegen.page.customforms.CreateCustomForms;
import com.intuit.ihg.product.sitegen.page.customforms.CustomFormAddCategoriesPage;
import com.intuit.ihg.product.sitegen.page.customforms.CustomFormLayoutPage;
import com.intuit.ihg.product.sitegen.page.customforms.CustomFormPreviewPage;
import com.intuit.ihg.product.sitegen.page.customforms.ManageYourFormsPage;
import com.intuit.ihg.product.sitegen.page.discreteforms.CustomFormPage;
import com.intuit.ihg.product.sitegen.page.discreteforms.DiscreteFormsPage;
import com.intuit.ihg.product.sitegen.page.home.SiteGenAdminHomePage;
import com.intuit.ihg.product.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.page.location.AddLocationPage;
import com.intuit.ihg.product.sitegen.page.location.ManageYourLocationsPage;
import com.intuit.ihg.product.sitegen.page.permissionsAndPersonnelTypes.ManageUserPermissionsPage;
import com.intuit.ihg.product.sitegen.page.permissionsAndPersonnelTypes.ManageYourGroupPersonnelTypesPage;
import com.intuit.ihg.product.sitegen.page.personnel.ExportPersonnelPage;
import com.intuit.ihg.product.sitegen.page.personnel.ImportOrExportProgressPage;
import com.intuit.ihg.product.sitegen.page.personnel.ImportPersonnelAndPhysiciansPage;
import com.intuit.ihg.product.sitegen.page.personnel.ManageYourPersonnelPage;
import com.intuit.ihg.product.sitegen.page.physicians.AddPhysicianPage;
import com.intuit.ihg.product.sitegen.page.physicians.AddPhysicianStep2EditLocationInfoPage;
import com.intuit.ihg.product.sitegen.page.physicians.ManageYourPhysiciansPage;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;



public class SiteGenAcceptanceTests extends BaseTestNGWebDriver {

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

		log("testSiteGen");
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());

		log("step 1: Get Data from Excel ##########");

		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		log("URL: "+testcasesData.getSiteGenUrl());
		log("USER NAME: "+testcasesData.getAutomationUser());
		log("Password: "+testcasesData.getAutomationUserPassword());

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

		log("testLocation");
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());

		log("step 1: Get Data from Excel ##########");

		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		log("URL: "+testcasesData.getSiteGenUrl());
		log("USER NAME: "+testcasesData.getAutomationUser());
		log("Password: "+testcasesData.getAutomationUserPassword());

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

		log("testPhysician");
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		log("URL: "+testcasesData.getSiteGenUrl());
		log("USER NAME: "+testcasesData.getAutomationUser());
		log("Password: "+testcasesData.getAutomationUserPassword());

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

		log("testPermission");
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		log("URL: "+testcasesData.getSiteGenUrl());
		log("USER NAME: "+testcasesData.getAutomationUser());
		log("Password: "+testcasesData.getAutomationUserPassword());

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

		log("testIntergationEngAndInterfaceSetUp");
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		log("URL: "+testcasesData.getSiteGenUrl());
		log("USER NAME: "+testcasesData.getAutomationUser());
		log("Password: "+testcasesData.getAutomationUserPassword());

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
	 * @Author:-Shanthala  : Modified :bbinisha
	 * @Date:- 07-03-2013
	 * @User Story ID in Rally :  US6152 and US6151
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
		log("testCustomFormPublished");
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		log("URL: "+testcasesData.getSiteGenUrl());
		log("USER NAME: "+testcasesData.getAutomationUser());
		log("Password: "+testcasesData.getAutomationUserPassword());

		log("step 2:LogIn ##########");
		SiteGenLoginPage loginpage = new SiteGenLoginPage (driver,testcasesData.getSiteGenUrl());
		SiteGenHomePage pSiteGenHomePage=loginpage.login(testcasesData.getAutomationUser(), testcasesData.getAutomationUserPassword());
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(), "Expected the SiteGen HomePage  to be loaded, but it was not.");

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		log("step 4: Click on Custom Forms");
		CreateCustomForms pManageCustomForms=pSiteGenPracticeHomePage.clickCustomForms();

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

			log("step 9A: Add Question1 to category");
			verifyTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add questions to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories.addQuestion1ToCategory(SitegenConstants.FORMQUESTION1), "Custom Form question1 and answerset1 did not updated successfully.");
			pAddCAtegories.addAnswerForQuestion1(SitegenConstants.FORMANSWERSET1);

			log("step 9B: Add Question2 to category");
			verifyTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories.addQuestion2ToCategory(SitegenConstants.FORMQUESTION2), "Custom Form question2 and answerset2 did not updated successfully.");
			pAddCAtegories.addAnswerForQuestion2(SitegenConstants.FORMANSWERSET2);

			log("step 9C: Add Question3 to category");
			verifyTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Questions are not getting added to expected Category");
			verifyTrue(pAddCAtegories.addQuestion3ToCategory(SitegenConstants.FORMQUESTION3), "Custom Form question3 and answerset3 did not updated successfully.");
			pAddCAtegories.addAnswerForQuestion3(SitegenConstants.FORMANSWERSET3);

			log("step 9D: Save added questions to category");
			verifyTrue(pAddCAtegories.isSearchPageLoaded(), "Expected the SiteGen Add question to the category page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Questions are not getting added to expected Category");
			CustomFormLayoutPage pAddQuestionsToCategory = pAddCAtegories.saveCategoryQuestions();

			log("step 10: Set Custom Form Layout");
			verifyTrue(pAddQuestionsToCategory.isSearchPageLoaded(), "Expected the SiteGen form Layout page to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Form Layout is not set for Expected Category");
			CustomFormPreviewPage pCustomFormPreview = pAddQuestionsToCategory.addFormLayout(SitegenConstants.FORMLAYOUTPAGE, SitegenConstants.FORMCATEGORY);
			Thread.sleep(8000);
			log("step 11: Custom Form Preview Page to click on publish");
			verifyTrue(pCustomFormPreview.isSearchPageLoaded(), "Expected the SiteGen create custom form page preview with publish link to be loaded, but it was not.");
			verifyTrue(verifyTextPresent(driver, SitegenConstants.FORMCATEGORY),"Form Layout is not set for Expected Category");
			verifyTrue(verifyTextPresent(driver,customFormTitle),"Vewing custom form is not expected custom form");
			Thread.sleep(8000);
			//This assert statements can be changed after getting standard valid custom form from Richard/Don B
			verifyEquals(verifyTextPresent(driver,"Insurance Type"),true,"Insurance Type is not present in form preview");
			verifyEquals(verifyTextPresent(driver,"First Name"),true, "Demographic information is not present in form preview");
			verifyEquals(verifyTextPresent(driver,"Vital"),true, "Vital information is not present in form preview");
			ManageYourFormsPage pManageForm = pCustomFormPreview.clickOnPublishLink(); 

			log("step 12: Manage your forms -Check custom Form published successfully");
			verifyEquals(pManageForm.checkForPublishedPage(customFormTitle), true, "Custom Form did not published successfully and not present in published forms table");

			log("step 13: Manage your forms -Check published Form Preview by clicking on Preview link");
			verifyEquals(pManageForm.isSearchPageLoaded(),true, "Expected the SiteGen Manage your Forms -> published form preview page to be loaded, but it was not.");
			pManageForm.clickOnPublishedFormPreviewLink(customFormTitle);
			ManageYourFormsPage pManageFormTest = pCustomFormPreview.clickOnUnPublishLink(); 

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
	 * @UserStrory ID in Rally : US6150
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

		log("testMerchantAccountSetUpViaPaypal");
		log("Envronment on which test is running is :"+IHGUtil.getEnvironmentType());
		log("Browser on which Test is running :"+TestConfig.getBrowserType());


		log("Step1 :- Getting data from excel.");
		Sitegen sitegen = new Sitegen();
		SitegenTestData testCaseData = new SitegenTestData(sitegen);

		log("URL :"+testCaseData.getSiteGenUrl());
		log("USERNAME :"+testCaseData.getAutomationUser());
		log("PASSWORD :"+testCaseData.getAutomationUserPassword());


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

	@Test(enabled = false, groups = {"AcceptanceTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testImportAndExportStaff() throws Exception {

		log("testImportAndExportStaff");
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());

		log("step 1: Get Data from Excel ##########");
		Sitegen sitegen=new Sitegen();
		SitegenTestData testcasesData=new SitegenTestData(sitegen);

		log("URL: "+testcasesData.getSiteGenUrl());
		log("USER NAME: "+testcasesData.getAutomationUser());
		log("Password: "+testcasesData.getAutomationUserPassword());

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
		log("URL :"+testCaseData.getSiteGenUrl());
		log("USERNAME :"+testCaseData.getAutomationUser());
		log("PASSWORD :"+testCaseData.getAutomationUserPassword());

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
	 * @throws Exception 
	 * @Author:-bbinisha
	 * @Date :- 07-03-2013
	 *  @UserStrory ID in Rally : US7093
	 * @StepsToReproduce:
	 * Login to Sitegen platform
	 * Select 'Medfusion Site Administration' Practice
	 * Select 'Discrete Form' solution
	 * Remove all published forms from the published section (we don't want to create excessive amount of forms)
	 * Click on Custom Form button
	 * Click on the newly created form
	 * Change the form name to a unique name
	 * Make a heading item in the first section, write a heading title (A Custom Form)
	 * Write 2 required questions in the second section, the second one with single select
	 * Write 1 question to the third part. Make this question as unrequired.
	 * Save form
	 * Click on Publish button for the corresponding form
	 * Move to Patient Portal URL: https://dev3.dev.medfusion.net/secure/welcome.cfm?gid=11264&muu=3424
	 * Sign in with a patient
	 * Go through the form flow and fill in the necessary sections
	 * Submit the form
	 * Go to the health form section in the Patient Portal
	 * Assure that PDF is displayed under the form name
	 * Log in to practice portal - ajohnson / medfusion123
	 * Click on the custom forms tab
	 * Find the previously submitted form
	 * ====================================================================================================================
	 * ********** Invalid for PROD and DEMO environment since both requirements is only for DEV3. ***************
	 * ====================================================================================================================
	 * 
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testCustomForms() throws Exception{

		log("testCustomForms");
		log("Envronment on which test is running is :"+IHGUtil.getEnvironmentType());
		log("Browser on which Test is running :"+TestConfig.getBrowserType());

		Sitegen sitegen = new Sitegen();
		SitegenTestData testCaseData = new SitegenTestData(sitegen);

		log("Step1 :- Getting data from excel.");
		log("URL :"+testCaseData.getSiteGenUrl());
		log("USERNAME :"+testCaseData.getFormUser());
		log("PASSWORD :"+testCaseData.getFormPassword());

		log("Step 2 :- Opening sitegen home page");
		SiteGenLoginPage sloginPage= new SiteGenLoginPage (driver,testCaseData.getSiteGenUrl());
		SiteGenHomePage sHomePage = sloginPage.login(testCaseData.getFormUser(), testCaseData.getFormPassword());

		log("Step 3 :- Navigating to the Practice page.");
		SiteGenPracticeHomePage practiseHome = new SiteGenPracticeHomePage(driver);
		practiseHome = sHomePage.clickLinkMedfusionSiteAdministration();

		log("Step 4 : Navigating to 'Discrete Forms'");
		DiscreteFormsPage discreteForm = practiseHome.clickOnDiscreteForms();
		String parent_window = driver.getWindowHandle();
		SitegenlUtil.switchToNewWindow(driver);
		
		log("Step 5 : Delete All Published Forms.'");
		discreteForm.deleteAllPublishedForms();	
		discreteForm.deleteAllUnPublishedForms();

		log("Step 6 : Add a new Custom Form");
		discreteForm.createANewCustomForm();
		
		log("Step 7 : Open the newly created Custom Form");
		CustomFormPage customFormPage = discreteForm.openCustomForm();
		
		String formName = SitegenConstants.CUSTOMFORMNAME+IHGUtil.createRandomNumericString().substring(0, 4);
		log("Form Name *****"+formName);
		
		log("Step 8 : Change the custom form name to a uniquename");
		customFormPage.renameCustomForm(formName);
		
		log("Step 9 : Fill the Custom Form details ");
		customFormPage.fillCustomFormDetails(SitegenConstants.HEADINGTITLE, SitegenConstants.QUESTIONTITLE1, SitegenConstants.QUESTIONTITLE2, SitegenConstants.AVAILABLE_ANSWERS);
		
		log("Step 10 : Publish the saved Custom Form");
		customFormPage.publishTheSavedForm(formName);
	
		driver.switchTo().defaultContent();
		driver.switchTo().window(parent_window);
	
		log("Step 11 : Login to Patient portal");
		PortalLoginPage loginpage = new PortalLoginPage(driver, "https://dev3.dev.medfusion.net/secure/welcome.cfm?gid=11264&muu=3424");
		MyPatientPage pMyPatientPage = loginpage.login("autouser123","medfusion123");
		
		log("Step 12 : Click on 'Fill Out' link under 'Custom Form' section");
		HealthFormPage pHealthForm = pMyPatientPage.clickFillOutFormsLink();
		
		log("Step 13 : Select "+formName+" custom form.");
		CustomFormPageForSitegen pCustomForm = pHealthForm.selectCustomForm(formName);
		
		log("Step 14 : Fill out all the details");
		pCustomForm.fillOutCustomForm(formName);
		
		log("Step 15 : Submit the Form.");
		pCustomForm.submitCustomForm();
		
		log("Step 16 : Verify whether the form is completed and is displayed as PDF.");
		pCustomForm.isFormDisplayedAsPDF(formName);
		
		log("Step 17 : Logout of patient portal");
		pMyPatientPage.logout(driver);
		driver.close();
		WebDriver driver = WebDriverFactory.getWebDriver();
		
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		
		log("Step 18 : Login to Practice Portal");
		PracticeLoginPage practiceLogin =new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getFormUser(), practiceTestData.getFormPassword());		
		
		log("Step 19 : Verify whether the practice home page is loaded.");
		verifyTrue(practiceHome.isHomePageLoaded(), "Expected to see 'Recent Activity' on home page, but it was not found");
		
		log("step 20: On Practice Portal Home page Click CustomFormTab");
		SearchPatientFormsPage pSearchPatientFormsPage = practiceHome.clickCustomFormTab();
		verifyTrue(pSearchPatientFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");

		log("step 21 : Search for PatientForms With Status Open");
		SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage.SearchPatientFormsWithOpenStatus(
				SitegenConstants.PATIENT_FIRSTNAME, SitegenConstants.PATIENT_LASTTNAME, SitegenConstants.PATIENT_DOBMONTH, SitegenConstants.PATIENT_DOBDAY,
				SitegenConstants.PATIENT_DOBYEAR);

		log("step 22: View the Result");
		ViewPatientFormPage pViewPatientFormPage = pSearchPatientFormsResultPage.clickOnSitegenCustomForm(formName);
		
		log("step 23 : Verify the Result");
		HealthFormPage pHealthForm1 = new HealthFormPage(driver);
		String actualPatientName = pHealthForm1.Patientname.getText().trim();

		log("Displayed patient name is :"+actualPatientName);
		verifyEquals(pHealthForm1.Patientname.getText().trim().contains(" Patient Name : AutoPatient  Medfusion "), true);
		
		driver.close();
	}

}