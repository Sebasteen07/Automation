//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.sitegen.test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.onlinesolutions.ManageSolutionsPage;
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
import com.intuit.ihg.product.object.maps.sitegen.page.pharmacy.AddPharmacyPage;
import com.intuit.ihg.product.object.maps.sitegen.page.pharmacy.ManageYourPharmacies;
import com.intuit.ihg.product.object.maps.sitegen.page.physicians.AddPhysicianPage;
import com.intuit.ihg.product.object.maps.sitegen.page.physicians.AddPhysicianStep2EditLocationInfoPage;
import com.intuit.ihg.product.object.maps.sitegen.page.physicians.ManageYourPhysiciansPage;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuti.ihg.product.object.maps.sitegen.page.medfusionadmin.PracticeInfoPage;

import static com.intuit.ihg.product.sitegen.utils.SitegenlUtil.verifyTextPresent;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class SiteGenAcceptanceTests extends BaseTestNGWebDriver {
	private PropertyFileLoader testData;

	@BeforeClass(alwaysRun = true)
	public void prepareTestData() throws IOException {
		log("Getting Test Data");
		testData = new PropertyFileLoader();
	}

	/**
	 * @throws Exception
	 * @Author:-bkrishnankutty
	 * @Date:-6/9/2013
	 * @User Story ID in Rally : US6142
	 * @StepsToReproduce: List steps here
	 * @AreaImpacted :- Description
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSiteGenLoginLogout() throws Exception {
		logStep("LogIn");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
				testData.getProperty("automation.password"));
		IHGUtil util = new IHGUtil(driver);
		logStep("Check if SiteGen Homepage elements are present ");
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
				"Expected the SiteGen HomePage  to be loaded, but it was not.");
		assertTrue(util.isRendered((pSiteGenHomePage.lnkHome)), "lnk Home not displayed");
		assertTrue(util.isRendered((pSiteGenHomePage.lnkHelp)), "lnk Help not displayed");
		assertTrue(util.isRendered((pSiteGenHomePage.btnlogout)), "button logout not displayed");
		logStep("Navigate to SiteGen PracticeHomePage");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		logStep("Check if SiteGen Practice Homepage elements are present ");
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, "Administrator - Setup/Access", 15), "Text not found!");
		assertTrue(verifyTextPresent(driver, "Portal Solutions", 15), "Text not found!");
		assertTrue(verifyTextPresent(driver, "Additional Configurations", 15), "Text not found!");
		logStep("Logout");
		loginpage = pSiteGenPracticeHomePage.clicklogout();
		assertTrue(loginpage.isSearchPageLoaded(), "Expected the SiteGen login Page  to be loaded, but it was not.");
	}

	/**
	 * @throws Exception
	 * @Author:-bkrishnankutty
	 * @Date:-6/12/2013
	 * @User Story ID in Rally :US6144
	 * @StepsToReproduce: Go to siteGen
	 *                    [https://dev3.dev.medfusion.net/admin/generator/index.cfm]
	 *                    Enter the credentials Search for the practice Click on
	 *                    Locations Add a location Enter the details of location &
	 *                    Add Location Assert if Location is successfully added or
	 *                    not
	 * @AreaImpacted :- Description
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocation() throws Exception {
		logStep("LogIn");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
				testData.getProperty("automation.password"));
		logStep("Navigate to SiteGen PracticeHomePage");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();

		logStep("Navigate to ManageYour Locations Page");
		ManageYourLocationsPage pManageYourLocationsPage = pSiteGenPracticeHomePage.clickLnkLocations();
		assertTrue(pManageYourLocationsPage.isSearchPageLoaded(),
				"Expected the SiteGen Manage location Page to be loaded, but it was not.");

		logStep("Clean test data - remove all locations");
		pManageYourLocationsPage.cleaningTestdata(SitegenConstants.PRACTICENAME, SitegenConstants.STATE);

		logStep("Navigate to AddLocationPage");
		AddLocationPage pAddLocationPage = pManageYourLocationsPage.clicklnkAddLocation();
		assertTrue(pAddLocationPage.isSearchPageLoaded(),
				"Expected the SiteGen Add location Page  to be loaded, but it was not.");

		logStep("Add Location");
		pManageYourLocationsPage = pAddLocationPage.addLocation(SitegenConstants.PRACTICENAME, SitegenConstants.ADDRESS,
				SitegenConstants.CITY, SitegenConstants.STATE, SitegenConstants.COUNTRY, SitegenConstants.ZIPCODE,
				SitegenConstants.TELEPHONE, SitegenConstants.CONTACT, SitegenConstants.EMAIL);

		logStep("Verify if the Location got added");
		assertTrue(pManageYourLocationsPage.isSearchPageLoaded(),
				"Expected the SiteGen Manage your location Page  to be loaded, but it was not.");
		assertTrue(verifyTextPresent(driver, SitegenConstants.STATE, 15), "Text not found!");
		assertTrue(verifyTextPresent(driver, SitegenConstants.PRACTICENAME, 15), "Text not found!");

		logStep("Test case passed, cleaning test data - remove all locations");
		pManageYourLocationsPage.cleaningTestdata(SitegenConstants.PRACTICENAME, SitegenConstants.STATE);
	}

	/**
	 * @throws Exception
	 * @Author:-bkrishnankutty
	 * @Date:-6/18/2013
	 * @User Story ID in Rally : US6145
	 * @StepsToReproduce: Go to siteGen Enter the credentials Click on
	 *                    Physician/Providers Add a physician Enter the details of
	 *                    physician Assert if physician is added or not
	 */
	private void testPhysicianBoth(boolean su) throws Exception {
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage;
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = new SiteGenPracticeHomePage(driver);
		if (su) {
			pSiteGenHomePage = loginpage.clickOnLoginAsInternalEmployee();
			logStep("navigate to SiteGen PracticeHomePage");
			assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
					"Expected the SiteGen HomePage  to be loaded, but it was not.");
			pSiteGenHomePage.searchPracticeFromSGAdmin(testData.getProperty("sitegen.automation.practice"));
		} else {
			pSiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
					testData.getProperty("automation.password"));
			logStep("navigate to SiteGen PracticeHomePage");
			pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		}
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		logStep("click Link Physicians and navigate to Manage Your Physicians Page");
		ManageYourPhysiciansPage pManageYourPhysiciansPage = pSiteGenPracticeHomePage.clickLnkPhysicians();
		assertTrue(pManageYourPhysiciansPage.isSearchPageLoaded(),
				"Expected the Manage Your Physicians Page  to be loaded, but it was not.");

		logStep("Cleaning the testdata - delete all physicians");
		pManageYourPhysiciansPage.cleanTestPhysiciansData();

		logStep("click Link Add Physicians and navigate to Add Physicians Page");
		AddPhysicianPage pAddPhysicianPage = pManageYourPhysiciansPage.clicklnkAddPhysician();
		assertTrue(pAddPhysicianPage.isSearchPageLoaded(),
				"Expected the Add Physician Page to be loaded, but it was not.");

		// creating dynamic last name , user Id and email , Sp case so adding logic in
		// testcase itself
		String lastName = SitegenConstants.LASTNAME + IHGUtil.createRandomNumber();
		String email = IHGUtil.createRandomEmailAddress(SitegenConstants.EMAIL);

		logStep("Add Physicians details");
		if (su) {
			assertFalse(pAddPhysicianPage.isActiveGroupMemberYesOptionDisabled(),
					"Activating Physician should be enabled");
		} else {
			assertTrue(pAddPhysicianPage.isActiveGroupMemberYesOptionDisabled(),
					"Activating Physician should be disabled");
		}

		AddPhysicianStep2EditLocationInfoPage pAddPhysicianStep2EditLocationInfoPage = pAddPhysicianPage.addPhysician(
				SitegenConstants.FIRSTNAME, lastName, SitegenConstants.TITLE, SitegenConstants.DEANUMBER, email,
				lastName, SitegenConstants.PASSWORD);
		assertTrue(pAddPhysicianStep2EditLocationInfoPage.isSearchPageLoaded(),
				"Expected the Add Physician Step2 Edit Location Information Page  to be loaded, but it was not.");

		logStep("Assert if  Physicians added or not");
		assertTrue(verifyTextPresent(driver, "Information Updated", 15), "Physician was not added correctly");
		String provider = SitegenConstants.FIRSTNAME + " " + lastName;
		assertTrue(verifyTextPresent(driver, "Edit Location Information for " + provider, 15),
				"Physician was not added correctly");

		logStep("Test case passed, cleaning test data");
		logStep("Test data cleaning process is going to start");

		logStep("Click Button GoBackToManagePhysicians");
		pManageYourPhysiciansPage = pAddPhysicianStep2EditLocationInfoPage.clickBtnGoBackToManagePhysicians();

		logStep("Click link EditPhysician");
		pAddPhysicianPage = pManageYourPhysiciansPage.clicklnkEditPhysician();
		assertTrue(pAddPhysicianPage.isSearchPageLoaded(),
				"Expected the Add Physician Page  to be loaded, but it was not.");

		logStep("Click on button Delete Physician");
		pAddPhysicianStep2EditLocationInfoPage = pAddPhysicianPage.deletePhysician();
		logStep("Verify if confirmation of deleting physician is shown");
		assertTrue(verifyTextPresent(driver, "Are you sure you wish to permanently delete: " + provider + "?", 15),
				"Text not found!");

		logStep("Confirm delete operation");
		pManageYourPhysiciansPage = pAddPhysicianStep2EditLocationInfoPage.deletePhysican();
		assertTrue(pManageYourPhysiciansPage.isSearchPageLoaded(),
				"Expected the Manage Your Physicians Page  to be loaded, but it was not.");

		logStep("Assert if physician deleted or not");
		assertTrue(verifyTextPresent(driver, "Information Updated", 15), "Text not found!");
	}

	/**
	 * @throws Exception
	 * @author phajek Run testPhysician as non-SU and check if activating is
	 *         disabled
	 */

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPhysician() throws Exception {
		testPhysicianBoth(false);
	}

	/**
	 * @throws Exception
	 * @author phajek Run testPhysician as SU and check if activating is enabled
	 */

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPhysicianSU() throws Exception {
		testPhysicianBoth(true);
	}

	/**
	 * @throws Exception
	 * @Author:-bkrishnankutty
	 * @Date:- 6-21-2013
	 * @User Story ID in Rally : US6146
	 * @StepsToReproduce: Go to siteGen Enter the credentials Search for the
	 *                    practice Add a Nurse Click on Permissions & Personnel
	 *                    types Go to manage permission for the personnel for whom
	 *                    we want to change permissions Give permissions
	 *                    <p>
	 *                    === Prerequisite for the test case to run========= Nurse
	 *                    Named :- "Auto, Sitegen: Nurse" should exist
	 *                    <p>
	 *                    === Test will only work in DEMO ,In DEV3 is having a bug
	 *                    in application
	 * @AreaImpacted :- Description
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPermission() throws Exception {
		logStep("LogIn");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
				testData.getProperty("automation.password"));
		logStep("Navigate to SiteGen PracticeHomePage");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();

		logStep("Click on Link Permissions and Personnel Types");
		ManageYourGroupPersonnelTypesPage pManageYourGroupPersonnelTypesPage = pSiteGenPracticeHomePage
				.clickLnkPermissions();
		assertTrue(pManageYourGroupPersonnelTypesPage.isSearchPageLoaded(),
				"Expected the Manage Your Group Personnel Types to be loaded, but it was not.");

		logStep("Click on Link Manage Permissions for Nurses");
		ManageUserPermissionsPage pManageUserPermissionsPage = pManageYourGroupPersonnelTypesPage
				.clicklnkManagePermissions4Nurses();
		assertTrue(pManageUserPermissionsPage.isSearchPageLoaded(),
				"Expected the Manage User Permissions Page to be loaded, but it was not.");

		logStep("Click on Link Manage Permissions for Nurses");
		pSiteGenPracticeHomePage = pManageUserPermissionsPage.givePermission2Nurse(SitegenConstants.PERSONNELTYPE1,
				SitegenConstants.PERSONNELTYPE2, SitegenConstants.SOLUTIONS, SitegenConstants.LOCATIONS,
				SitegenConstants.USERS);

		pSiteGenPracticeHomePage.clicklogout();
		assertTrue(PageFactory.initElements(driver, SiteGenLoginPage.class).isSearchPageLoaded(),
				"Expected the SiteGen login Page  to be loaded, but it was not.");

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				testData.getProperty("sitegen.automation.practice.url"));
		PracticeHomePage practiceHome = practiceLogin.login(testData.getProperty("personnel.type.username"),
				testData.getProperty("personnel.type.password"));

		logStep("Verify AptRequest Tab in Practice Portal");
		assertTrue(practiceHome.verifyAptRequestTab(), "Appointment tab not displayed");

		logStep("Logout");
		practiceHome.logOut();
	}

	/**
	 * @throws Exception
	 * @Author:-bkrishnankutty
	 * @Date:- 6-21-2013
	 * @User Story ID in Rally : US6153 && US6154
	 * @StepsToReproduce: ===========US6154 :- Integration Set UP===============
	 *                    <p>
	 *                    LogIn to SiteGen PracticeHomePage Click on Interface set
	 *                    up link Add new Integration Engine Assert the newly added
	 *                    Integration Engine Go back to Sitegen home page
	 *                    <p>
	 *                    ==========US6153 :- Integration
	 *                    Engine=====================
	 *                    <p>
	 *                    click on Link MedfusionSiteAdministration click Link
	 *                    IntegrationEngine click Link CreateIntegrationEngine Add
	 *                    new IntegrationEngine Assert if New IntegrationEngine is
	 *                    added or not
	 *                    <p>
	 *                    ================================================== Clean
	 *                    the data
	 *                    <p>
	 *                    === Prerequisite for the test case to run=========
	 *                    EXTERNAL_SYSTEM = "Allscripts Practice Management System"
	 *                    Should be there for the Integration Engine testcase to run
	 * @AreaImpacted :- Description
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testIntegrationEngAndInterfaceSetUp() throws Exception {
		logStep("LogIn");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage p1SiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
				testData.getProperty("automation.password"));
		logStep("Navigate to SiteGen PracticeHomePage");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = p1SiteGenHomePage.clickLinkMedfusionSiteAdministration();

		logStep("Click on Interface set up link");
		InterfaceAdministrationPage pInterfaceAdministrationPage = pSiteGenPracticeHomePage.clickLnkInterfaceSetup();
		assertTrue(pInterfaceAdministrationPage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		logStep("Cleaning the testdata for Integration Type");
		pInterfaceAdministrationPage.cleanTestDataIntegrationEng();

		logStep("Add new Integration Engine");
		String integrationEngine = pInterfaceAdministrationPage.AddIntegrationEngine();
		assertEquals(integrationEngine, "Integration Engine", "Wrong Integration type added");

		logStep("Go back to Sitegen Home page");
		SiteGenHomePage pSiteGenHomePage = pInterfaceAdministrationPage.clickLinkHome();
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		logStep("Click on Link MedfusionSiteAdministration");
		pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the View Integrations Page to be loaded, but it was not.");

		logStep("Click Link IntegrationEngine");
		ViewIntegrationsPage pViewIntegrationsPage = pSiteGenPracticeHomePage.clickLnkIntegrationEngine();
		assertTrue(pViewIntegrationsPage.isSearchPageLoaded(),
				"Expected the View Integrations Page to be loaded, but it was not.");

		logStep("Click Link Create IntegrationEngine");
		CreateIntegrationStep1Page pCreateIntegrationPage = pViewIntegrationsPage.clickLnkCreateIntegration();
		assertTrue(pCreateIntegrationPage.isSearchPageLoaded(),
				"Expected the View Integrations Page to be loaded, but it was not.");

		logStep("Add New IntegrationEngine");
		CreateIntegrationStep2Page pCreateIntegrationStep2Page = pCreateIntegrationPage.addNewIntegrationEng(
				SitegenConstants.EXTERNAL_SYSTEM, SitegenConstants.CHANNEL, SitegenConstants.INTEGRATION_NAME,
				SitegenConstants.REVIEWTYPE);
		assertTrue(pCreateIntegrationStep2Page.isSearchPageLoaded(),
				"Expected the View Integrations Page to be loaded, but it was not.");
		logStep("Click btn SaveAndContinue");
		pViewIntegrationsPage = pCreateIntegrationStep2Page.clickbtnSaveAndContinue();
		logStep("Assert if New IntegrationEngine is added or not");
		assertTrue(
				verifyTextPresent(driver,
						"Integration '" + SitegenConstants.INTEGRATION_NAME + "' updated successfully.", 15),
				"Text not found!");

		logStep("Verify if Integrations Engine is added or not");
		assertTrue(pViewIntegrationsPage.verifyIfIntegrationsEngineIsAdded(SitegenConstants.INTEGRATION_NAME),
				"INTEGRATION_Engine not added");

		logStep("Clean Integration TestData");
		pViewIntegrationsPage.cleanIntegrationTestData();
	}

	/**
	 * @throws Exception
	 * @Author:-bbinisha
	 * @Date :- 07-03-2013
	 * @UserStory ID in Rally : US6150
	 * @StepsToReproduce: Login to Sitegen platform Select any Practice Select
	 *                    MerchantAccount solution Select Paypal in the options Goto
	 *                    Merchant Account Setup Provide data and save
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMerchantAccountSetUpViaPaypal() throws Exception {
		logStep("LogIn");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
				testData.getProperty("automation.password"));
		logStep("Navigate to SiteGen PracticeHomePage");
		SiteGenPracticeHomePage practiseHome = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		logStep("Navigate to MerchantAccountPage");
		MerchantAccountPage merchantAcctPage = practiseHome.clickOnMerchantAccountLink();
		logStep("Delete existing merchant account");
		merchantAcctPage.deleteExistingMerchantAcct();
		logStep("Navigating to the Merchant Account Set Up Page");
		MerchantAccountSetUpPage merchantAcctSetUp = merchantAcctPage.clickOnMerchantAccountSetUp();
		merchantAcctSetUp.clickOnPracticeRadioButton();
		logStep("Entering Account details");
		merchantAcctSetUp.selectProcessorValue(SitegenConstants.PROCESSORVALUE1);
		merchantAcctSetUp.enterUsername(SitegenConstants.SetUPPracticeUserName);
		merchantAcctSetUp.enterPassword(SitegenConstants.SetUPPracticePassword);
		merchantAcctSetUp.selectPartnerValue(SitegenConstants.PartnerValue3);
		logStep("Saving the account details");
		merchantAcctSetUp.clickOnSaveChanges();

		logStep("Verify whether the Merchant Account added successfully");
		assertEquals(merchantAcctSetUp.getAccountAddedSuccessMsg(), SitegenConstants.expSuccessMessage,
				"Merchant Account added successfully message is not getting displayed");
		logStep("Navigate to MerchantAccountPage");
		practiseHome.clickOnMerchantAccountLink();

		logStep("Verify whether the Account is Added in the Merchant Account List");
		assertTrue(merchantAcctPage.verifyAcctInMerchantAcctList(),
				"Merchant Account not added in the Merchant Account List");
	}

	/**
	 * @throws Exception
	 * @Author:-bbinisha
	 * @Date :- 07-03-2013
	 * @UserStrory ID in Rally : US6407
	 * @StepsToReproduce: Login to Sitegen platform as SU Select the Practice Select
	 *                    MerchantAccount solution Select QBMS in the options Goto
	 *                    Merchant Account Setup Provide data and save Verify
	 *                    whether the Merchant Account is added in Merchant Account
	 *                    List
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testMerchantAccountSetUpViaQBMS() throws Exception {
		logStep("Login to SG as superuser - THIS REQUIRES MANUAL INPUT");
		SiteGenHomePage pSiteGenHomePage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"))
				.clickOnLoginAsInternalEmployee();
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
				"Expected the SiteGen HomePage  to be loaded, but it was not.");
		logStep("Navigate to SiteGen PracticeHomePage");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage
				.searchPracticeFromSGAdmin(testData.getProperty("sitegen.automation.practice"));

		logStep("Navigating to the Merchant Account List page.");
		MerchantAccountPage merchantAcctPage = pSiteGenPracticeHomePage.clickOnMerchantAccountLink();

		logStep("Delete Existing Merchant Account.");
		merchantAcctPage.deleteExistingMerchantAcct();

		logStep("Navigate to the Merchant Account Set Up Page.");
		MerchantAccountSetUpPage merchantAcctSetUp = merchantAcctPage.clickOnMerchantAccountSetUp();
		merchantAcctSetUp.clickOnPracticeRadioButton();

		logStep("Enter Account details");
		merchantAcctSetUp.selectStatus(SitegenConstants.statusValue2);
		merchantAcctSetUp.selectProcessorValue(SitegenConstants.PROCESSORVALUE2);
		merchantAcctSetUp.enterMerchantToken(SitegenConstants.merchantAcctQBMSToken);
		merchantAcctSetUp.enterMerchantTestToken(SitegenConstants.merchantAcctQBMSToken);

		logStep("Save the account details");
		merchantAcctSetUp.clickOnSaveChanges();

		logStep("Verify whether the Merchant Account added successfully");
		assertEquals(merchantAcctSetUp.getAccountAddedSuccessMsg(), SitegenConstants.expSuccessMessage,
				"Merchant Account is not getting added");

		logStep("Navigate to merchant account List");
		pSiteGenPracticeHomePage.clickOnMerchantAccountLink();

		logStep("Verify whether the Account is Added in the Merchant Account List");
		assertTrue(merchantAcctPage.verifyAcctInMerchantAcctList(),
				"Merchant Account not added in the Merchant Account List");

		logStep("Test case passed, clean test data - remove existing merchant account");
		merchantAcctPage.deleteExistingMerchantAcct();
	}

	/**
	 * @throws Exception
	 * @Author:-bkrishnankutty
	 * @Date:-7/8/2013
	 * @User Story ID in Rally : US6148 & US6147
	 * @StepsToReproduce: ===######Export Staff#####===== Go to siteGen Enter the
	 *                    credentials Search for the practice Click on
	 *                    Physician/Providers Click on Export Personel Click on
	 *                    Export Staff Click on Downloaded Export File Assert if
	 *                    "csv file should be downloaded with the information"
	 *                    <p>
	 *                    ===######Import Staff#####===== Go to siteGen Enter the
	 *                    credentials Search for the practice Click on
	 *                    Physician/Providers Click on import personnel and
	 *                    physicians Select the file that need to be imported Assert
	 *                    the information in the file
	 *                    <p>
	 *                    <p>
	 *                    =============================================================
	 * @AreaImpacted :- Description
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testImportAndExportStaff() throws Exception {
		logStep("LogIn");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
				testData.getProperty("automation.password"));
		logStep("Navigate to SiteGen PracticeHomePage");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();

		logStep("Click on Link Permissions and Personnel Types");
		ManageYourPersonnelPage pManageYourPersonnelPage = pSiteGenPracticeHomePage.clickLnkPersonnelNonPhysicians();
		assertTrue(pManageYourPersonnelPage.isSearchPageLoaded(),
				"Expected the Manage Your Personnel Page to be loaded, but it was not.");

		logStep("Click on Link Import Personnel And Physicians");
		ImportPersonnelAndPhysiciansPage pImportPersonnelAndPhysicians = pManageYourPersonnelPage
				.clickBtnImportPersonnelAndPhysicians();
		assertTrue(pImportPersonnelAndPhysicians.isSearchPageLoaded(),
				"Expected the Import Personnel And Physicians Page to be loaded, but it was not.");

		logStep("Import the csv file from the test\resource\testfile location");
		ImportOrExportProgressPage pImportOrExportProgressPage = pImportPersonnelAndPhysicians
				.clickbtnimportStaffFile();
		assertTrue(verifyTextPresent(driver, "Import/Export Progress", 15),
				"Import/Export Progress text is not present on Import/Export Progress");

		logStep("Wait so it can be imported and click on Link List All Personnel");
		Thread.sleep(3000);
		pImportPersonnelAndPhysicians = pImportOrExportProgressPage.clickLinkImportPersonnelAndPhysicians();

		logStep("Verify Import staff data");
		assertTrue(pImportPersonnelAndPhysicians.verifyImportStaffCreationDate(
				pImportPersonnelAndPhysicians.getImportStaffCreateDate(), IHGUtil.getEstTimingWithTime()));
		assertEquals(pImportPersonnelAndPhysicians.getFileName(), SitegenConstants.IMPORTSTAFFFILENAME);
		assertEquals(pImportPersonnelAndPhysicians.getStatus(), SitegenConstants.FILEIMPORTSTATUS);

		logStep("Import staff testcase passed");
		logStep("Export staff testcase Getting Started");

		logStep("Click on link ExportPersonnel");
		ExportPersonnelPage pExportPersonnel = pImportPersonnelAndPhysicians.clickLinkExportPersonnel();
		assertTrue(pExportPersonnel.isSearchPageLoaded(),
				"Expected the SiteGen Export Personnel page to be loaded, but it was not.");
		pExportPersonnel.clickBtnExportStaff();

		logStep("Click on Download link (Export staff) -- validate HTTP Status Code");
		assertEquals(pExportPersonnel.clickLinkDownloadExportStaff(), 200,
				"Download of Export staff returned unexpected HTTP status code");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPharmacyAndValidateExternalSystemID() throws Exception {

		logStep("LogIn");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
				testData.getProperty("automation.password"));
		logStep("Navigate to SiteGen HomePage");
		SiteGenPracticeHomePage practiseHome = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();
		logStep("Navigate to Manage Your Pharamcies Page");
		ManageYourPharmacies managePharmacyPage = practiseHome.clickOnPharmacy();
		logStep("Click on Pharmacies link in Sitegen Home Page");

		logStep("Click on Add New Pharmacy button");
		AddPharmacyPage addPharmaPage = managePharmacyPage.clickOnAddPharmacyButton();

		String externalid = IHGUtil.createRandomNumericString(12);

		String message = addPharmaPage.fillPharmacyDetails(externalid, true);
		assertTrue(message.contains("success"));

		logStep("Add one more Pharmacy with same External Pharmacy ID for same External System and Validate");
		AddPharmacyPage addPharmaPage2 = managePharmacyPage.clickOnAddPharmacyButton();

		String message2 = addPharmaPage2.fillPharmacyDetails(externalid, false);
		assertTrue(message2.contains("The External PharmacyID already exists for 22, please enter a unique value"));

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEditAndDeletePharmacy() throws Exception {

		logStep("Log In");
		SiteGenLoginPage loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = loginpage.login(testData.getProperty("automation.user"),
				testData.getProperty("automation.password"));

		logStep("Navigate to SiteGen HomePage");
		SiteGenPracticeHomePage practiceHome = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();

		logStep("Navigate to Manage Your Pharamcies Page");
		ManageYourPharmacies managePharmacyPage = practiceHome.clickOnPharmacy();

		logStep("Click on Edit pharmacy Link and Edit the External Id");

		String PharmacyName = managePharmacyPage.clickonEditPharmacyLink();
		String externalid = IHGUtil.createRandomNumericString(12);
		AddPharmacyPage addPharma = new AddPharmacyPage(driver);
		addPharma.editPharmacy(externalid);

		logStep("Click on Edit Pharmacy Link and Delete the Pharmacy");
		managePharmacyPage.clickonEditPharmacyLink();
		boolean isDeleted = addPharma.deletePharmacy(PharmacyName);
		if (isDeleted) {
			logStep("Pharmacy is deleted succesfully");
		}
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientSupport() throws Exception {

		logStep("Log In to SiteGen");
		SiteGenLoginPage sitegenloginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage pSiteGenHomePage = sitegenloginpage.login(testData.getProperty("automation.user1"),
				testData.getProperty("automation.password1"));

		logStep("Navigate to SiteGen HomePage");
		SiteGenPracticeHomePage practiceHome = pSiteGenHomePage.clickLinkMedfusionSiteAdministration();

		logStep("Navigate to Online Solutions Page");
		ManageSolutionsPage managesolutionspage = practiceHome.clickOnOnlineSolutions();

		logStep("Click on Edit button");
		managesolutionspage.clickOnEdit();

		logStep("Select Patient Solution checkbox");
		managesolutionspage.clickActivateCheckbox();

		logStep("Click on Confirm Changes button");
		managesolutionspage.confirmChanges();

		logStep("Log In to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage homePage = loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Verify Patient Support");
		assertTrue(homePage.isLiveChatDisplayed());

		logStep("Log In to SiteGen");
		sitegenloginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		sitegenloginpage.login(testData.getProperty("automation.user1"), testData.getProperty("automation.password1"));

		logStep("Navigate to SiteGen HomePage");
		pSiteGenHomePage.clickLinkMedfusionSiteAdministration();

		logStep("Navigate to Online Solutions Page");
		practiceHome.clickOnOnlineSolutions();

		logStep("Click on Edit button");
		managesolutionspage.clickOnEdit();

		logStep("Click on DeActivateCheckbox");
		managesolutionspage.clickDeActivateCheckbox();

		logStep("Save Changes");
		managesolutionspage.confirmChanges();

		logStep("Login to patient portal");
		loginPage = new JalapenoLoginPage(driver, testData.getUrl());
		loginPage.login(testData.getUserId(), testData.getPassword());

		logStep("Verify that LiveChat is not displayed");
		assertFalse(homePage.isLiveChatDisplayed());
	}

	@Test(enabled = true, retryAnalyzer = RetryAnalyzer.class)
	public void testPortalURL() throws Exception {

		SiteGenLoginPage loginpage;
		SiteGenHomePage pSiteGenHomePage;
		SiteGenPracticeHomePage pSiteGenPracticeHomePage;
		PracticeInfoPage pInfoPage;
		loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));

		pSiteGenPracticeHomePage = new SiteGenPracticeHomePage(driver);
		pSiteGenHomePage = loginpage.clickOnLoginAsInternalEmployee();
		logStep("navigate to SiteGen PracticeHomePage");
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
				"Expected the SiteGen HomePage  to be loaded, but it was not.");
		pSiteGenHomePage.searchPracticeFromSGAdmin(testData.getProperty("sitegen.automation.practice"));

		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");

		logStep("click Link Physicians and navigate to practice Information Page");
		pSiteGenPracticeHomePage.clickPracticeInformation();

		logStep("click on edit practice Information Page");
		pInfoPage = new PracticeInfoPage(driver);
		pInfoPage.edit();

		pInfoPage.urlTextBox("ihgqaautomation");
		String urlName = pInfoPage.getURLName();
		
		logStep("update the Url name and link");
		pInfoPage.urlTextBox("japanese");
		
		logStep("Save the update Url name and link");
		pInfoPage.saveEdit();

		logStep("Login patient with the updated URl");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("updatedUrl"));
		loginPage.login(testData.getProperty("user.id"), testData.getProperty("password"));

		loginpage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));

		pSiteGenPracticeHomePage = new SiteGenPracticeHomePage(driver);

		pSiteGenHomePage = loginpage.clickOnLoginAsInternalEmployee();
		logStep("navigate to SiteGen PracticeHomePage");
		assertTrue(pSiteGenHomePage.isSearchPageLoaded(),
				"Expected the SiteGen HomePage  to be loaded, but it was not.");
		pSiteGenHomePage.searchPracticeFromSGAdmin(testData.getProperty("sitegen.automation.practice"));

		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
				"Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
		
		logStep("click practice information and navigate to practice information Page");
		pSiteGenPracticeHomePage.clickPracticeInformation();
		
		logStep("click on edit practice Information Page");
		pInfoPage.edit();
		
		logStep("update the url name and link with the older value");
		pInfoPage.urlTextBox(urlName);
		pInfoPage.saveEdit();

	}
}