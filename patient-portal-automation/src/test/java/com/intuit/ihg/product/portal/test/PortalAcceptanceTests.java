package com.intuit.ihg.product.portal.test;

import java.util.Calendar;
import java.util.Date;

import javax.mail.SendFailedException;
import javax.print.attribute.standard.Finishings;

import bsh.TokenMgrError;

import com.google.inject.ProvidedBy;
import com.intuit.ihg.product.practice.page.customform.SearchPatientFormsPage;
import com.intuit.ihg.product.practice.page.customform.SearchPatientFormsResultPage;
import com.intuit.ihg.product.practice.page.customform.ViewPatientFormPage;

import org.apache.tools.ant.types.selectors.DifferentSelector;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.ITestResult;

import static org.testng.Assert.*;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.product.portal.page.makePaymentpage.MakePaymentPage;
import com.intuit.ihg.product.portal.page.myAccount.insurance.InsurancePage;
import com.intuit.ihg.product.portal.page.createAccount.BetaSiteCreateAccountPage;
import com.intuit.ihg.product.portal.page.createAccount.CreateAccountPage;
import com.intuit.ihg.product.portal.page.createAccount.CreateAccountPasswordPage;
import com.intuit.ihg.product.portal.page.forgotPassword.ActivatePasswordChangePage;
import com.intuit.ihg.product.portal.page.forgotPassword.ResetYourPasswordPage;
import com.intuit.ihg.product.portal.page.forgotuserid.ForgotUserIdConfirmationPage;
import com.intuit.ihg.product.portal.page.forgotuserid.ForgotUserIdEnterEmailPage;
import com.intuit.ihg.product.portal.page.forgotuserid.ForgotUserIdSecretAnswerPage;
import com.intuit.ihg.product.portal.page.healthform.HealthFormPage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxMessage;
import com.intuit.ihg.product.portal.page.inbox.ConsolidatedInboxPage;
import com.intuit.ihg.product.portal.page.myAccount.*;
import com.intuit.ihg.product.portal.page.myAccount.familyAccount.CreatefamilymemberPage;
import com.intuit.ihg.product.portal.page.myAccount.familyAccount.PatientsOnThisAccountPage;
import com.intuit.ihg.product.portal.page.myAccount.familyAccount.UnlinkPatientFromFamilyPage;
import com.intuit.ihg.product.portal.page.myAccount.manageHealthInfo.ManageHealthInfoPage;
import com.intuit.ihg.product.portal.page.myAccount.preferences.PreferencesPage;
import com.intuit.ihg.product.portal.page.myAccount.wallet.WalletPage;
import com.intuit.ihg.product.portal.page.newRxRenewalpage.NewRxRenewalPage;
import com.intuit.ihg.product.portal.page.phr.PHRPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormAllergiesPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormBasicInfoPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormCurrentSymptomsPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormEmergencyContactPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormFamilyHistoryPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormIllnessConditionsPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormInsurancePage;
import com.intuit.ihg.product.portal.page.questionnaires.FormMedicationsPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormOtherProvidersPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormPreviousExamsPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormSocialHistoryPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormSurgeriesHospitalizationsPage;
import com.intuit.ihg.product.portal.page.questionnaires.FormVaccinePage;
import com.intuit.ihg.product.portal.page.questionnaires.FormWelcomePage;
import com.intuit.ihg.product.portal.page.solutions.apptRequest.*;
import com.intuit.ihg.product.portal.page.solutions.askstaff.*;
import com.intuit.ihg.product.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitConfirmationPage;
import com.intuit.ihg.product.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitHistoryDetailPage;
import com.intuit.ihg.product.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitHistoryPage;
import com.intuit.ihg.product.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitPharmacyPage;
import com.intuit.ihg.product.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitProviderPage;
import com.intuit.ihg.product.portal.page.solutions.virtualofficevisit.VirtualOfficeVisitQuestionnairePage;
import com.intuit.ihg.product.portal.page.symptomAssessment.NewSymptomAssessmentPage;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.tests.CreatePatientTest;
import com.intuit.ihg.product.portal.tests.FamilyAccountTest;
import com.intuit.ihg.product.portal.tests.PatientActivationUtil;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestSearchPage;
import com.intuit.ihg.product.practice.page.askstaff.*;
import com.intuit.ihg.product.practice.page.messages.PracticeMessagePage;
import com.intuit.ihg.product.practice.page.messages.PracticeMessagesSearchPage;
import com.intuit.ihg.product.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalSearchPage;
import com.intuit.ihg.product.practice.page.symptomassessment.SymptomAssessmentDetailsPage;
import com.intuit.ihg.product.practice.page.symptomassessment.SymptomAssessmentFilterPage;
import com.intuit.ihg.product.practice.page.virtualofficevisit.VirtualOfficeVisitOnlineVisitAndMedicationPage;
import com.intuit.ihg.product.practice.page.virtualofficevisit.VirtualOfficeVisitPracticeConfirmationPage;
import com.intuit.ihg.product.practice.page.virtualofficevisit.VirtualOfficeVisitSearchPage;
import com.intuit.ihg.product.practice.page.virtualofficevisit.VirtualOfficeVisitSummaryPage;
import com.intuit.ihg.product.practice.page.virtualofficevisit.VirtualOfficeVisitTakeActionPage;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.tests.PatientActivationTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.common.utils.mail.CheckEmail;
import com.intuit.ihg.common.utils.mail.Gmail;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;

@Test
public class PortalAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-2/6/2013
	 * @User Story ID in Rally : US5372
	 * @StepsToReproduce: Login to Patient Portal Click on My Account Click
	 *                    Preferences Select Preferred Provider from select box
	 *                    This will add the provider above the select box Click
	 *                    Update Your Preferences We should also ensure the
	 *                    script selects a value for the items below since these
	 *                    are required fields: Statement Delivery Preference:
	 *                    Electronic statement Email Format Preference: HTML
	 *                    Check Preferences saved successfully is displayed
	 *                    Remove provider by finding Remove link next to
	 *                    provider that was added in previous steps Click Update
	 *                    Your Preferences Check Preferences saved successfully
	 *                    is displayed Check that the provider name that was
	 *                    removed is not shown
	 *                    ==========================================
	 *                    ===================
	 * @AreaImpacted :- If a practice requires the user to select a preferred
	 *               provider at login, that Provider will already be listed
	 *               above the select box when this screen is pulled up. We
	 *               would want to ensure when we remove a provider that we are
	 *               removing the one that was added, not the existing provider
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddandRemovePreferences() throws Exception {

		log("Test Case: testAddandRemovePreferences");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

		log("step 4:Click on preferencesLink  on MyAccountPage");
		PreferencesPage pPreferencesPage = pMyAccountPage.clickpreferencesLink();

		log("step 5:searching the dynamic table and check if the data is there or not ");
		pPreferencesPage.searchAndRemovethePreferences(testcasesData.getPreferredDoctor());

		log("step 6:updateYourPreferences and verify the text ++++Preferences saved successfully+++");
		log("testcasesData.getPreferredDoctor()===" + testcasesData.getPreferredDoctor());
		String lastNameOfDoctor = pPreferencesPage.updateYourPreferencesProvider(testcasesData.getPreferredDoctor());

		// The portal is inconsistent in how it formats the name of
		// providers.
		// so it is best to search for a substring of the name.
		assertTrue(verifyTextPresent(driver, lastNameOfDoctor));
		pPreferencesPage.statementDeliveryNEmailFormatText();
		pPreferencesPage.clickupdateYourPreferences();
		assertTrue(verifyTextPresent(driver, "Preferences saved successfully"));

		log("step 7:Remove the added Preferences, update it and then Again verify the text ++==Preferences saved successfully==++");
		pPreferencesPage.searchAndRemovethePreferences(testcasesData.getPreferredDoctor());
		pPreferencesPage.clickupdateYourPreferences();
		pPreferencesPage.waitforbut_updateYourPreferences(driver, 2);
		assertTrue(verifyTextPresent(driver, "Preferences saved successfully"));

		// The portal is inconsistent in how it formats the name of
		// providers.
		// so it is best to search for a substring of the name.
		assertFalse(verifyTextNotPresent(driver, lastNameOfDoctor));
	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-
	 * @User Story ID in Rally :
	 * @StepsToReproduce: Login to Patient Portal Log Out from Patient Portal
	 *                    Delete the account created
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginLogout() throws Exception {
		log("Test Case: TestLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());
		PerformanceReporter.getPageLoadDuration(driver, MyPatientPage.PAGE_NAME);
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver),
				"There was an issue with login or loading the home page. Expected to see 'View All Messages' link, but it was not found.");

		log("step 3:Logout");
		loginpage = pMyPatientPage.logout(driver);

	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-3/29/2013
	 * @User Story ID in Rally :
	 * @StepsToReproduce: Login to Patient Portal Click on My Account Click
	 *                    FamilyLink Click on Add a Family MemberLink on
	 *                    CreatefamilymemberPage createFamilyMember Delete the
	 *                    account created
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFamilyAccount() throws Exception {

		// Instancing FamilyAccountTest
		FamilyAccountTest familyAccountTest = new FamilyAccountTest();

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		// Executing test
		familyAccountTest.FamilyAccount(driver, testcasesData);

	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-2/6/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce: Login to Patient Portal Click on My Account Click
	 *                    Wallet Link Add credit Card Remove added Credit Card
	 *                    ================================================
	 *                    =============
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddandChangeCreditCard() throws Exception {

		log("Test Case: testAddandChangeCreditCard");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 3:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 4:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

		log("step 5:Click on Wallet Link  on MyAccountPage");
		WalletPage pWalletPage = pMyAccountPage.clickWalletLink();

		log("step 6:Add credit card ");
		pWalletPage.addCreditCardDetails(PortalConstants.CardholderName, PortalConstants.CreditCardType, PortalConstants.CreditCardNumber,
				testcasesData.getDob_Month(), PortalConstants.Year, testcasesData.getZip());

		log("step 7:Verify correct CC details get added");
		pWalletPage.verifyCreditCardDetails(PortalConstants.CardholderName, PortalConstants.CreditCardType, PortalConstants.Month,
				PortalConstants.Year);

		log("Delete the new added Credit card");
		pWalletPage.removeCreditCard(PortalConstants.CreditCardType);

		log("step 13:Click on Add a Family MemberLink  on CreatefamilymemberPage ");
		pWalletPage.logout(driver);
	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-2/6/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce: Click Sign-UP Fill detials in Create Account Page
	 *                    Click Wallet Link Fill security detials in Create
	 *                    Account Page 2 Logout LogIn with the New User ======
	 *                    ==============================================
	 *                    =========
	 * @throws Exception
	 */

	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatient() throws Exception {

		log("Test Case: testCreatePatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("step 2:Click Sign-UP");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		CreateAccountPage pCreateAccountPage = loginpage.signUp();

		log("step 3:Fill detials in Create Account Page");
		String email = PortalUtil.createRandomEmailAddress(testcasesData.getEmail());
		log("email:-" + email);
		CreateAccountPasswordPage pCreateAccountPasswordPage = pCreateAccountPage.createAccount(testcasesData.getFirstName(),
				testcasesData.getLastName(), email, testcasesData.getEmail(), testcasesData.getPhoneNumber(), testcasesData.getPhoneType(),
				testcasesData.getDob_Month(), testcasesData.getDob_Day(), testcasesData.getDob_Year(), testcasesData.getZip(),
				testcasesData.getSSN());

		log("step 4:Fill security detials in Create Account  Page 2");
		MyPatientPage pMyPatientPage = pCreateAccountPasswordPage.createPasswordSecurity(email, testcasesData.getPassword(),
				testcasesData.getSecretQuestion(), testcasesData.getAnswer(), testcasesData.getPreferredLocation(),
				testcasesData.getPreferredDoctor());

		log("step 5:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 6:Logout");
		pMyPatientPage.clickLogout(driver);

		log("step 7:Login as new user");
		loginpage.navigateTo(driver, testcasesData.geturl());
		pMyPatientPage = loginpage.login(email, testcasesData.getPassword());

		log("step 8:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 9: Logout ");
		pMyPatientPage.clickLogout(driver);

	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-2/6/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce: Click Sign-UP Create a new account Logout from patient
	 *                    portal Again Login to patient Portal with the newly
	 *                    created patient Log out ==================
	 *                    ===========================================
	 * 
	 *                    Note :- //need to include a piece of code here for
	 *                    deleting cookies so that script works in IE works
	 * 
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreatePatientOnBetaSite() throws Exception {

		// Instancing CreatePatientTest
		CreatePatientTest createPatientTest = new CreatePatientTest();

		// Setting data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		// Executing Test
		createPatientTest.CreatePatient(driver, testcasesData);

	}

	/**
	 * @Author: rperkinsjr
	 * @Date: 4/15/2013
	 * @StepsToReproduce: Login to Patient Portal Click on Appointment Button on
	 *                    My Patient Page Complete Appointment Request Step1
	 *                    Page Complete Appointment Request Step2 Page Complete
	 *                    Appointment Request Step3 Page Complete Appointment
	 *                    Request Step4 Page Navigate to Appt Request History
	 *                    Page Logout from Patient Portal Login to Practice
	 *                    Portal Click Appt Request tab Search for appt requests
	 *                    Choose process option and respond to patient Confirm
	 *                    response details to patient Logout of Practice Portal
	 *                    Login to Patient Portal Go to Inbox Find message in
	 *                    Inbox Validate message loads and is the right message
	 *                    ==============
	 *                    ===============================================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentRequestEnd2End() throws Exception {

		log("Test Case: testAppointmentRequestEnd2End");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Click on Appointment Button on My Patient Page");
		AppointmentRequestStep1Page apptRequestStep1 = myPatientPage.clickAppointmentRequestTab();

		log("step 4: Complete Appointment Request Step1 Page  ");
		AppointmentRequestStep2Page apptRequestStep2 = apptRequestStep1.requestAppointment(testcasesData.getTitle(),
				testcasesData.getPreferredLocation(), testcasesData.getPreferredDoctor(), null);

		log("step 5: Complete Appointment Request Step2 Page  ");
		AppointmentRequestStep3Page apptRequestStep3 = apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame,
				PortalConstants.PreferredDay, PortalConstants.ChoosePreferredTime, PortalConstants.ApptReason,
				PortalConstants.WhichIsMoreImportant, testcasesData.getPhoneNumber());

		log("step 6: Complete Appointment Request Step3 Page  ");
		AppointmentRequestStep4Page apptRequestStep4 = apptRequestStep3.clickSubmit();

		log("step 7: Complete Appointment Request Step4 Page  ");
		myPatientPage = apptRequestStep4.clickBackToMyPatientPage();

		log("step 8: Navigate to Appt Request History Page");
		ApptRequestHistoryPage apptRequestHistory = myPatientPage.clickApptRequestHistoryLink();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestHistoryPage.PAGE_NAME);

		log("step 9 :Logout from Patient Portal ");
		myPatientPage.logout(driver);

		log("step 10: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 11: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestSearchPage.PAGE_NAME);

		log("step 12: Search for appt requests");
		apptSearch.searchForApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(Long.toString(apptRequestStep2.getCreatedTs()));
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep1Page.PAGE_NAME);

		log("step 13: Choose process option and respond to patient");
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestDetailStep2Page.PAGE_NAME);

		log("step 15: Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("step 16: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 17: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 18: Go to Inbox");
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		log("step 19: Find message in Inbox");
		String uniquePracticeResponse = Long.toString(detailStep1.getCreatedTs());
		ConsolidatedInboxMessage message = inboxPage.openMessageInInbox(uniquePracticeResponse);

		log("step 20: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
				+ uniquePracticeResponse + "] but actual subject was [" + actualSubject + "]");
	}

	/**
	 * @Author: rperkinsjr
	 * @Date: 4/15/2013
	 * @StepsToReproduce: Login to Patient Portal Click on My Account Click
	 *                    Manage Health Information Simulate click of 'Download
	 *                    My Data' (PDF) Check that the HTTP Status Code == 200
	 *                    ========================================
	 *                    =====================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBlueButtonDownloadPdf() throws Exception {
		log("testBlueButtonDownloadPdf");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Login");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Click on My Account link on MyPatientPage");
		MyAccountPage myAccountPage = myPatientPage.clickMyAccountLink();
		PerformanceReporter.getPageLoadDuration(driver, MyAccountPage.PAGE_NAME);

		log("step 4: Click on Manage Health Information link");
		ManageHealthInfoPage healthInfo = myAccountPage.clickManageHealthInfoLink();
		PerformanceReporter.getPageLoadDuration(driver, ManageHealthInfoPage.PAGE_NAME);

		log("step 5: Download PDF version of Blue Button download -- validate HTTP Status Code");
		assertEquals(healthInfo.clickBlueButtonDownloadPdf(), 200, "Download of Blue Button PDF returned unexpected HTTP status code");
	}

	/**
	 * @Author: rperkinsjr
	 * @Date: 4/15/2013
	 * @StepsToReproduce: Login to Patient Portal Click on My Account Click
	 *                    Manage Health Information Simulate click of 'Download
	 *                    My Data' (PDF) Check that the HTTP Status Code == 200
	 *                    ========================================
	 *                    =====================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = false, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBlueButtonDownloadText() throws Exception {
		log("Test Case: testBlueButtonDownloadText");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Login");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Click on My Account link on MyPatientPage");
		MyAccountPage myAccountPage = myPatientPage.clickMyAccountLink();

		log("step 4: Click on Manage Health Information link");
		ManageHealthInfoPage healthInfo = myAccountPage.clickManageHealthInfoLink();

		log("step 5: Download Text version of Blue Button download -- validate HTTP Status Code");
		assertEquals(healthInfo.clickBlueButtonDownloadText(), 200, "Download of Blue Button PDF returned unexpected HTTP status code");
	}

	/**
	 * @Author: rperkinsjr
	 * @Date: 4/15/2013
	 * @StepsToReproduce: Login to Patient Portal Click on Appt Request Fill out
	 *                    Appt Request details Submit Appt Request Navigate back
	 *                    to My Patient Page Click on Appt Request History
	 *                    Select 1st Appt Request History item Click on 'View As
	 *                    PDF' link Get contents of embedded PDF Validate
	 *                    contents ======================
	 *                    =======================================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testViewAsPdf() throws Exception {

		log("Test Case: testViewAsPdf");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Click on Appointment Button on My Patient Page");
		AppointmentRequestStep1Page apptRequestStep1 = myPatientPage.clickAppointmentRequestTab();

		log("step 4: Complete Appointment Request Step1 Page  ");
		AppointmentRequestStep2Page apptRequestStep2 = apptRequestStep1.requestAppointment(testcasesData.getTitle(),
				testcasesData.getPreferredLocation(), testcasesData.getPreferredDoctor(), null);

		log("step 5: Complete Appointment Request Step2 Page  ");
		AppointmentRequestStep3Page apptRequestStep3 = apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame,
				PortalConstants.PreferredDay, PortalConstants.ChoosePreferredTime, PortalConstants.ApptReason,
				PortalConstants.WhichIsMoreImportant, testcasesData.getPhoneNumber());

		log("step 6: Complete Appointment Request Step3 Page  ");
		AppointmentRequestStep4Page apptRequestStep4 = apptRequestStep3.clickSubmit();

		log("step 7: Complete Appointment Request Step4 Page  ");
		myPatientPage = apptRequestStep4.clickBackToMyPatientPage();

		log("step 8: Navigate to Appt Request History Page");
		ApptRequestHistoryPage apptRequestHistory = myPatientPage.clickApptRequestHistoryLink();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestHistoryPage.PAGE_NAME);

		log("step 9: Select 1st appt request item");
		ApptRequestHistoryDetailPage apptRequestHistoryDetail = apptRequestHistory.clickApptRequestHistoryDetailLink();
		PerformanceReporter.getPageLoadDuration(driver, ApptRequestHistoryDetailPage.PAGE_NAME);

		log("step 10: Click 'View As PDF' link");
		String pdfContent = apptRequestHistoryDetail.clickViewAsPdfLink();
		// apptRequestHistoryDetail.clickViewAsPdfLink();
		log(pdfContent, "DEBUG");
		assertTrue(pdfContent.contains("pdf"), "There is an error an with the Appt Request History Detail PDF content");
	}

	/**
	 * @Author: rperkinsjr
	 * @Date: 4/16/2013
	 * @StepsToReproduce: Login to Patient Portal Click on Ask A Staff/Doctor
	 *                    Fill out Ask A Staff details Submit Ask A Staff Go to
	 *                    Ask A Staff History page Validate submitted question
	 *                    is listed on History page Logout of Patient Portal
	 *                    Login to Practice Portal Search Ask A Questions Access
	 *                    details for submitted question Respond to question
	 *                    Logout of Practice Portal Login to Patient Portal
	 *                    Access Patient Inbox Validate Ask A Staff response is
	 *                    listed ==========================
	 *                    ===================================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAskAStaffEnd2End() throws Exception {
		log("Test Case: testAskAStaff");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		assertTrue(loginPage.isLoginPageLoaded(), "There was an error loading the login page");
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Click Ask A Staff");
		AskAStaffStep1Page askStaff1 = myPatientPage.clickAskAStaffLink();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffStep1Page.PAGE_NAME);

		log("step 4: Complete step 1 of Ask A Staff");
		AskAStaffStep2Page askStaff2 = askStaff1.askQuestion("Test", "This is generated from the testAskAStaff automation test case.");
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffStep2Page.PAGE_NAME);

		log("step 5: Complete step 2 of Ask A Staff");
		AskAStaffStep3Page askStaff3 = askStaff2.submitQuestion();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffStep3Page.PAGE_NAME);

		log("step 6: Validate entry is on Ask A Staff History page");
		AskAStaffHistoryPage aasHistory = askStaff3.clickAskAStaffHistory();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffHistoryPage.PAGE_NAME);
		assertTrue(aasHistory.isAskAStaffOnHistoryPage(Long.toString(askStaff1.getCreatedTimeStamp())),
				"Expected to see a subject containing " + askStaff1.getCreatedTimeStamp()
						+ " on the Ask A Staff History page. None were found.");

		log("step 7: Logout of Patient Portal");
		myPatientPage.logout(driver);

		log("step 8: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 9: Click Ask A Staff tab");
		AskAStaffSearchPage aasSearch = practiceHome.clickAskAStaffTab();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffSearchPage.PAGE_NAME);

		log("step 10: Search for questions");
		aasSearch.searchForQuestions();
		AskAStaffQuestionDetailStep1Page detailStep1 = aasSearch.getQuestionDetails(Long.toString(askStaff1.getCreatedTimeStamp()));
		assertNotNull(detailStep1, "The submitted patient question was not found in the practice");
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep1Page.PAGE_NAME);

		log("step 11: Choose action on patient question");
		AskAStaffQuestionDetailStep2Page detailStep2 = detailStep1.chooseProvideAdviceOnly();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep2Page.PAGE_NAME);

		log("step 12: Respond to patient question");
		AskAStaffQuestionDetailStep3Page detailStep3 = detailStep2.processAndCommunicate("Automated Test",
				"This message was generated by an automated test");
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep3Page.PAGE_NAME);

		log("step 13: Confirm response details to patient");
		AskAStaffQuestionDetailStep4Page detailStep4 = detailStep3.confirmProcessedQuestion();
		PerformanceReporter.getPageLoadDuration(driver, AskAStaffQuestionDetailStep4Page.PAGE_NAME);

		log("step 14: Validate submit of confirmation");
		detailStep4.isQuestionDetailPageLoaded();

		log("step 15: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 16: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 17: Go to Inbox");
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		log("step 18: Find message in Inbox");
		String uniquePracticeResponse = Long.toString(detailStep2.getCreatedTimeStamp());
		ConsolidatedInboxMessage message = inboxPage.openMessageInInbox(uniquePracticeResponse);

		log("step 19: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
				+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");
	}

	/**
	 * @Author: rperkinsjr
	 * @Date: 4/22/2013
	 * @StepsToReproduce: Access Patient Portal Login page Click Forgot ID link
	 *                    Enter email address and continue Answer security
	 *                    question and continue Access personal email inbox to
	 *                    check for email Validate email contents: subject, user
	 *                    id, site link ==============
	 *                    ===============================================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientForgotUserId() throws Exception {
		log("Test Case: testPatientForgotUserId");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData patientData = new TestcasesData(portal);

		// Some other data setup for email searching:
		Date startEmailSearchDate = new Date();
		Gmail gmail = new Gmail(patientData.getEmail(), patientData.getPassword());
		int unSeenEmailCount = gmail.getInboxNewMessageCount();

		log("step 2: Navigate to login page");
		PortalLoginPage loginPage = new PortalLoginPage(driver, patientData.geturl());

		log("step 3: Click forgot user id link");
		ForgotUserIdEnterEmailPage step1 = loginPage.forgotUserId();
		assertTrue(step1.isPageLoaded(), "There was an error loading the first page in the Forgot UserId workflow.");
		PerformanceReporter.getPageLoadDuration(driver, ForgotUserIdEnterEmailPage.PAGE_NAME);

		log("step 4: Enter patient email");
		ForgotUserIdSecretAnswerPage step2 = step1.enterEmail(patientData.getEmail());

		if(IHGUtil.getEnvironmentType().toString().equals("PROD")||IHGUtil.getEnvironmentType().toString().equals("DEMO")) {
			
			log("Enter Patient's DOB");
			step2.selectDOB(patientData.getDob_Day(), patientData.getDob_Month(), patientData.getDob_Year());
		}
		log("step 5: Answer patient security question");
		ForgotUserIdConfirmationPage step3 = step2.answerSecurityQuestion(patientData.getAnswer());
		assertTrue(step3.confirmationPageLoaded(), "There was an error loading the confirmation page in the Forgot UserId workflow");
		PerformanceReporter.getPageLoadDuration(driver, ForgotUserIdConfirmationPage.PAGE_NAME);

		log("step 6: Access Gmail and check for received email");
		int count = 1;
		boolean flag = false;
		do {
			boolean foundEmail = CheckEmail.validateForgotPassword(gmail, startEmailSearchDate, patientData.getEmail(), "Your User ID for",
					patientData.getUsername());
			if (foundEmail) {
				assertTrue(foundEmail, "The Forgot User ID email wasn't received.");
				System.out.println("The User ID email receiced In between :" + count * 60 + "seconds");
				flag = true;
				break;
			} else {
				Thread.sleep(60000);
				count++;
			}

		} while (count < 21);
		if (!flag) {
			log("The User ID email wasn't received even after Five minutes of wait");
		}
	}


	/**
	 * @Author:- shanthala
	 * @Date:-4/April/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce: Login to Patient Portal Click on myaccountLink on
	 *                    MyPatientPage Click on insuranceLink on MyAccountPage
	 *                    Add Insurance Assert it Remove Insurance
	 *                    ==========================================
	 *                    ===================
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddInsurance() throws Exception {
		log("Test Case: testAddInsurance");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 3:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 4:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

		log("step 5:Click on insuranceLink  on MyAccountPage");
		InsurancePage pinsuranceDetailsPage = pMyAccountPage.addInsuranceLink();

		log("step 6:check for existing insurances and delete");
		pinsuranceDetailsPage.deleteAllInsurances();

		log("step 7:Start to add Insurance details");
		pinsuranceDetailsPage.addInsuranceDetails();

		log("step 8:asserting for Insurance Name and Insurance Type");
		Thread.sleep(20000);
		assertTrue(verifyTextPresent(driver, PortalConstants.InsuranceName));
		assertTrue(verifyTextPresent(driver, PortalConstants.InsuranceType));

		log("step 9:Click on delete button to delete Insurance added");
		pinsuranceDetailsPage.deleteInsurance();

		log("step 10:asserting for Insurance Name and Insurance Type not present after Insurance deleted");
		Thread.sleep(20000);
		assertFalse(verifyTextNotPresent(driver, PortalConstants.InsuranceName));
		assertFalse(verifyTextNotPresent(driver, PortalConstants.InsuranceType));
	}

	/**
	 * @Author:- rperkinsjr
	 * @Date:-30/April/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce: Login to Patient Portal Initiate new Virtual Office
	 *                    Vist Complete virtual office visit View Virtual Office
	 *                    Visit history Ensure item is listed in history
	 *                    Navigate to Practice Portal and login Go to Virtual
	 *                    Office Visit search page Initiate search Click on
	 *                    specific item process the item Logout of practice
	 *                    portal login to patient portal go to inbox find secure
	 *                    message open secure message ============
	 *                    =================================================
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testVirtualOfficeVisitEnd2End() throws Exception {
		log("Test Case: testVirtualOfficeVisitEnd2End");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Retrieving test case data");
		Portal portal = new Portal();
		TestcasesData patientData = new TestcasesData(portal);

		log("URL: " + patientData.geturl());
		log("USER NAME: " + patientData.getUsername());
		log("Password: " + patientData.getPassword());

		log("step 1: Patient login");
		PortalLoginPage login = new PortalLoginPage(driver, patientData.geturl());
		MyPatientPage home = login.login(patientData.getUsername(), patientData.getPassword());

		log("step 2: Start Virtual Office Visit");
		VirtualOfficeVisitProviderPage vovProvider = home.clickVirtualOfficeVisitLink();
		assertTrue(vovProvider.isPageLoaded(), VirtualOfficeVisitProviderPage.PAGE_NAME + " failed to load");
		PerformanceReporter.getPageLoadDuration(driver, VirtualOfficeVisitProviderPage.PAGE_NAME);

		log("step 3: Choose Vov Provider");
		// VirtualOfficeVisitPharmacyPage vovPharmacy =
		// vovProvider.chooseProviderAndContinue(patientData.getPreferredDoctor());
		VirtualOfficeVisitPharmacyPage vovPharmacy = vovProvider.chooseProviderAndContinue("");
		assertTrue(vovPharmacy.isPageLoaded(), VirtualOfficeVisitPharmacyPage.PAGE_NAME + " failed to load");
		PerformanceReporter.getPageLoadDuration(driver, VirtualOfficeVisitPharmacyPage.PAGE_NAME);

		log("step 4: Choose Vov Pharmacy");
		VirtualOfficeVisitQuestionnairePage vovQuestionnaire = vovPharmacy.choosePharmacyAndContinue("");

		log("step 5: Complete Vov Questionnaire and verify it submitted correctly");
		// Setup the date for searching later
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -1); // need to subtract one minute to
		// make
		// sure the search works.
		Date sentDate = cal.getTime();

		// Now go and complete the form
		VirtualOfficeVisitConfirmationPage vovConfirmation = vovQuestionnaire.completeQuestionnaire();
		assertTrue(vovConfirmation.isPageLoaded(), VirtualOfficeVisitConfirmationPage.PAGE_NAME + " failed to load.");
		assertTrue(verifyTextPresent(driver, "Thank you for submitting your Virtual Office Visit."));

		log("step 6: Check Vov History page for new item");
		VirtualOfficeVisitHistoryPage vovHistory = vovConfirmation.visitVirtualOfficeVistHistory();

		log("step 7: Open Vov History item");
		VirtualOfficeVisitHistoryDetailPage vovHistoryDetail = vovHistory.viewVirtualOfficeVisitHistoryDetails(sentDate);

		log("step 8: Validate Vov History item opened correctly");
		assertTrue(vovHistoryDetail.didHistoryDetailsLoad(), VirtualOfficeVisitHistoryDetailPage.PAGE_NAME + " failed to load");

		log("step 9: Logout of Patient Portal");
		home.logout(driver);

		log("step 10: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 11: Click Vov tab");
		VirtualOfficeVisitSearchPage vovSearch = practiceHome.clickVirtualOfficeVisitTab();
		PerformanceReporter.getPageLoadDuration(driver, VirtualOfficeVisitSearchPage.PAGE_NAME);
		assertTrue(vovSearch.isPageLoaded(), VirtualOfficeVisitSearchPage.PAGE_NAME + " failed to load");

		log("step 12: initiate search");
		vovSearch.doBasicSearch();

		log("step 13: select item from results");
		VirtualOfficeVisitTakeActionPage vovAction = vovSearch.getDetails(sentDate);
		assertTrue(vovAction.isPageLoaded(), VirtualOfficeVisitTakeActionPage.PAGE_NAME + " failed to load");

		log("step 14: choose Vov processing action");
		VirtualOfficeVisitOnlineVisitAndMedicationPage vovPrescribe = vovAction.takeActionOfOnlineVisitAndPrescribeMedication(null);
		assertTrue(vovPrescribe.isPageLoaded(), VirtualOfficeVisitOnlineVisitAndMedicationPage.PAGE_NAME + " failed to load");

		log("step 15: enter prescription and secure message details");
		VirtualOfficeVisitPracticeConfirmationPage vovConfirm = vovPrescribe.completePrescriptionAndCommunication("IHGQA Auto");
		assertTrue(vovConfirm.isPageLoaded(), VirtualOfficeVisitPracticeConfirmationPage.PAGE_NAME + " failed to load");

		log("step 16: confirm and submit");
		VirtualOfficeVisitSummaryPage vovSummary = vovConfirm.confirmAndSubmit();
		assertTrue(verifyTextPresent(driver, "Your prescription and communication have been posted and the visit has been closed."));

		log("step 17: logout of practice portal");
		practiceHome.logOut();

		log("step 18: login to patient portal (again)");
		login = new PortalLoginPage(driver, patientData.geturl());
		home = login.login(patientData.getUsername(), patientData.getPassword());

		log("step 19: Go to Inbox");
		ConsolidatedInboxPage inboxPage = home.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		log("step 20: Find message in Inbox");
		String uniquePracticeResponse = vovPrescribe.getCreatedTs();
		ConsolidatedInboxMessage message = inboxPage.openMessageInInbox(uniquePracticeResponse);

		log("step 21: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
				+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");

		log("step 22: Reply back to practice");
		inboxPage = message.replyToMessage(null);
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		Thread.sleep(10000);
		log("step 23: Logout of Patient Portal");
		home.logout(driver);

		log("step 24: Login to Practice Portal");
		Thread.sleep(10000);
		// Load up practice test data
		practice = new Practice();
		practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 25: Click Vov tab");
		vovSearch = practiceHome.clickVirtualOfficeVisitTab();
		assertTrue(vovSearch.isPageLoaded(), VirtualOfficeVisitSearchPage.PAGE_NAME + " failed to load");

		log("step 26: Click My Messages");
		PracticeMessagesSearchPage messageSearch = vovSearch.goToMyMessages();
		assertTrue(messageSearch.isPageLoaded(), PracticeMessagesSearchPage.PAGE_NAME + " failed to load");

		log("step 27: Search for messages");
		messageSearch.searchForVirtualOfficeVisitMessages();

		log("step 28: Find message in search results");
		PracticeMessagePage theMessage = messageSearch.retrieveMessage(uniquePracticeResponse);
		assertNotNull(theMessage, "The Patients reply to the Virtual Office Visit message was not received by the practice.");
		assertTrue(theMessage.isPageLoaded(), PracticeMessagePage.PAGE_NAME + " failed to load");

	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-April/4/2013
	 * @User Story ID in Rally
	 * @StepsToReproduce:
	 * 
	 *                    Click on Forgot Password Link on LogIn Page On the
	 *                    Reset Password Page Give UserId and other details
	 *                    Verify Gmail Click on Gmail link and confirm the
	 *                    password ============================================
	 *                    =================
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testForgotPassword() throws Exception {

		log("**INFO:: TestForgotPassword");
		log("**INFO:: Environment on which Testcase is Running:-" + IHGUtil.getEnvironmentType());
		log("**INFO:: Browser on which Testcase is Running :-" + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("step 2: Clean the Gmail Inbox");
		String sSubject = String.format(PortalConstants.EMAIL_ForgotPassword_SUBJECT.trim(), PortalConstants.PORTAL_TITLE.trim());
		PortalUtil pPortalUtil = new PortalUtil(driver);
		pPortalUtil.emailMessageRemover(testcasesData.getUsername(), testcasesData.getPassword(), sSubject);

		log("URL++++++++++++++++" + testcasesData.geturl());
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());

		log("step 3:Click Forgot Password");
		ResetYourPasswordPage pResetYourPasswordPage = loginpage.clickForgotYourPasswordLink();

		log("step 4:Enter your gmail,security Answer and check your gmail account");
		ActivatePasswordChangePage pActivatePasswordChangePage = pResetYourPasswordPage.resetYourPasswordPage(testcasesData.getUsername(),
				testcasesData.getAnswer(), testcasesData.getPassword());

		log("step 5:reactivate  your new password");
		MyPatientPage pMyPatientPage = pActivatePasswordChangePage.activatePasswordChangePage(driver, testcasesData.getUsername(),
				testcasesData.getPassword());
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));
		pMyPatientPage.logout(driver);

	}

	/**
	 * @Author: bkrishnankutty
	 * @Date: 05/4/2013
	 * @StepsToReproduce: Login to Patient Portal Click on CustomForm Fill
	 *                    CustomForm and submit Download InsuranceHealthForm
	 *                    pdf-- validate HTTP Status Code Logout from Patient
	 *                    Portal Login to Practice Portal On Practice Portal
	 *                    Home page Click on CustomFormTab Search for
	 *                    PatientForms With Status Open View and Validate the
	 *                    Result ==============================================
	 *                    ===============
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCustomForms() throws Exception {

		log("Test Case: testCustomForms");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Retrieving test case data");
		Portal portal = new Portal();
		TestcasesData patientData = new TestcasesData(portal);

		log("URL: " + patientData.geturl());
		log("USER NAME: " + patientData.getUsername());
		log("Password: " + patientData.getPassword());

		log("step 1: Patient login");
		PortalLoginPage login = new PortalLoginPage(driver, patientData.geturl());
		MyPatientPage pMyPatientPage = login.login(patientData.getUsername(), patientData.getPassword());

		log("step 2: Click on CustomForm");
		HealthFormPage pHealthForm = pMyPatientPage.clickFillOutFormsLink();

		log("step 3: Fill CustomForm");
		pHealthForm.fillInsuranceHealthForm();
		Thread.sleep(10000);
		verifyEquals(pHealthForm.InsuranceHelthform.getText(), "Thank you for completing our Insurance Health Form ( Testing).");
		// assertTrue(verifyTextPresent(driver,"Thank you for completing our Insurance Health Form ( Testing)."));

		log("step 4: Download InsuranceHealthForm -- validate HTTP Status Code");
		assertEquals(pHealthForm.clickInsuranceHealthFormDownloadText(), 200,
				"Download of InsuranceHealth Form PDF returned unexpected HTTP status code");

		log("step 7: Logout of Patient Portal");
		pMyPatientPage.logout(driver);

		log("step 8: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 8: On Practice Portal Home page Click CustomFormTab");
		SearchPatientFormsPage pSearchPatientFormsPage = practiceHome.clickCustomFormTab();
		assertTrue(pSearchPatientFormsPage.isPageLoaded(), SearchPatientFormsPage.PAGE_NAME + " failed to load.");

		log("step 9: Search for PatientForms With Status Open");
		SearchPatientFormsResultPage pSearchPatientFormsResultPage = pSearchPatientFormsPage.SearchPatientFormsWithOpenStatus(
				patientData.getFirstName(), patientData.getLastName(), patientData.getDob_Month(), patientData.getDob_Day(),
				patientData.getDob_Year());

		log("step 10: View the Result");
		ViewPatientFormPage pViewPatientFormPage = pSearchPatientFormsResultPage.clickViewLink();

		log("step 11: Verify the Result");
		String actualPatientName = pHealthForm.Patientname.getText().trim();
		
		log("Displayed patient name is :"+actualPatientName);
		verifyEquals(pHealthForm.Patientname.getText().trim().contains("Patient Name : ihgqa automation"), true);
		/*
		 * assertTrue(verifyTextPresent(driver,
		 * "Patient Name : ihgqa  automation "));
		 * assertTrue(verifyTextPresent(driver, "Patient DOB : 01/11/1987"));
		 */
		// assertTrue(verifyTextPresent(driver,
		// "Patient SSN : 987-65-4322"));
	}

	/**
	 * @Author: bkrishnankutty
	 * @Date: 05/6/2013
	 * @StepsToReproduce: Patient login Click on SymptomAssessment and Select
	 *                    your doctor Give Your Symptom and submit Answer all
	 *                    the Question in coming up in the further pages??
	 *                    Assert Text after completing Symptom Assessment Logout
	 *                    of Patient Portal Login to Practice Portal and Click
	 *                    on SymptomAssessmentTab Verify the information on
	 *                    SymptomAssessmentDetailsPage and Sent Message to
	 *                    Patient Logout from the Practice Portal Login to
	 *                    Patient Portal Go to Inbox and Find message Validate
	 *                    message ============================
	 *                    =================================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSymptomAssessment() throws Exception {

		log("Test Case: testSymptomAssessment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Retrieving test case data");
		Portal portal = new Portal();
		TestcasesData patientData = new TestcasesData(portal);

		log("URL: " + patientData.geturl());
		log("USER NAME: " + patientData.getUsername());
		log("Password: " + patientData.getPassword());

		log("step 1: Patient login");
		PortalLoginPage loginPage = new PortalLoginPage(driver, patientData.geturl());
		MyPatientPage pMyPatientPage = loginPage.login(patientData.getUsername(), patientData.getPassword());

		log("step 2: Click on SymptomAssessment");
		NewSymptomAssessmentPage pNewSymptomAssessmentPage = pMyPatientPage.clickNewSymptomAssessmentLink();
		assertTrue(pNewSymptomAssessmentPage.isPageLoaded(), NewSymptomAssessmentPage.PAGE_NAME + " failed to load.");

		log("step 3: Select your doctor");
		pNewSymptomAssessmentPage.selectProvider(patientData.getPreferredDoctor());

		log("step 4: type Your Symptom and submit");
		pNewSymptomAssessmentPage.typeYourSymptom(PortalConstants.Symptom);

		log("step 5: DoYouHaveSymptom Now ?? Answer :-NO ");
		pNewSymptomAssessmentPage.answerDoYouHaveSymptom();

		log("step 6: Assert Text after  completing  Symptom Assessment ");
		assertTrue(verifyTextPresent(driver, "Thank you for "));
		/*
		 * completing our Symptom Assessment Patient Care Form."));
		 * assertTrue(verifyTextPresent(driver,
		 * "Your information has been sent directly to your healthcare provider."
		 * )); assertTrue(verifyTextPresent(driver,
		 * "Please feel free to contact us if you have any further questions about your Symptom Assessment."
		 * ));
		 */

		log("step 7: Logout of Patient Portal");
		pMyPatientPage.logout(driver);

		log("step 8: Login to Practice Portal");
		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 9: On Practice Portal Home page Click SymptomAssessmentTab");
		SymptomAssessmentFilterPage pSymptomAssessmentFilter = practiceHome.clicksymptomAssessmentTab();

		log("step 10: On Practice Portal Home page Click SymptomAssessmentTab");
		SymptomAssessmentDetailsPage pSymptomAssessmentDetailsPage = pSymptomAssessmentFilter.searchSymptomAssessment();

		log("step 11: Verification on SymptomAssessmentDetailsPage");
		assertTrue(verifyTextPresent(driver, "Date of Birth : 01/11/1987"));
		/*
		 * assertTrue(verifyTextPresent(driver, "Home Phone : (958) 963-1234"));
		 */
		assertTrue(verifyTextPresent(driver, "His reason for visit is \"Cough\"."));
		log("step 12: Sent Message to Patient");
		String practiceResponse = pSymptomAssessmentDetailsPage.sentMessage();

		log("step 13: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 14: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, patientData.geturl());
		pMyPatientPage = loginPage.login(patientData.getUsername(), patientData.getPassword());

		log("step 15: Go to Inbox");
		ConsolidatedInboxPage inboxPage = pMyPatientPage.clickViewAllMessages();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		log("step 16: Find message in Inbox");
		ConsolidatedInboxMessage message = inboxPage.openMessageInInbox(practiceResponse);

		log("step 17: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(practiceResponse), "Expected subject containting [" + practiceResponse
				+ "but actual subject was [" + actualSubject + "]");

	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/10/2013
	 * @StepsToReproduce: Patient login Click on RxRenewal and Select your
	 *                    Provider Give Prescription Details Submit RxRenewal
	 *                    Details Assert Text after completing RxRenewal
	 *                    Assessment Logout of Patient Portal Login to Practice
	 *                    Portal and Click on RxRenewalTab Verify the
	 *                    information on RxRenewalDetailsPage and Sent Message
	 *                    to Patient Logout from the Practice Portal Login to
	 *                    Patient Portal Go to Inbox and Find message Validate
	 *                    message ============================
	 *                    =================================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRxRenewalEnd2End() throws Exception {

		log("Test Case: testRxRenewalEnd2End");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Verify for My Patient Page ");
		PortalUtil.setPortalFrame(driver);
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

		log("step 4: Click on PrescriptionRenewal Link ");
		NewRxRenewalPage newRxRenewalPage = myPatientPage.clickPrescriptionRenewal();

		log("step 5: Enter Details");
		newRxRenewalPage.chooseProvider(testcasesData.getpreferredprovider());

		log("set Medication Fields in NewRxRenewal Page");
		newRxRenewalPage.setMedicationFields();

		log("set Pharmacy Fields in NewRxRenewal Page");
		newRxRenewalPage.setPharmacyFields();

		log("Verify RxRenewal Confirmation Message");
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 5, newRxRenewalPage.renewalConfirmationmessage);
		verifyEquals(newRxRenewalPage.renewalConfirmationmessage.getText(), PortalConstants.RenewalConfirmation);

		log("step 6: Logout of Patient Portal");
		myPatientPage.logout(driver);

		// Load up practice test data
		log("step 7: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 8:Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome.clickonRxRenewal();

		log("step 9:Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday();

		log("Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		log("Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.setRxRenewalFields();

		log("Click On Process RxRenewal Button in Practice Portal");
		rxRenewalSearchPage.clickProcessRxRenewal();

		String subject = rxRenewalSearchPage.getSubject();
		log("Verify Prescription Confirmation in Practice Portal");
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject);

		log("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		log("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		log("step 10: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 11: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());
		Thread.sleep(6000);
		
		log("step 12: Go to Inbox");
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		String uniquePracticeResponse = Long.toString(rxRenewalSearchPage.getCreatedTs())+PortalConstants.RxRenewalSubject;
		
		log("step 13: Find message in Inbox And Validate Message Subject");
		ConsolidatedInboxMessage message = inboxPage.clickMessageLinkOpenMessageInInbox(uniquePracticeResponse);

		log("step 14: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
				+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");

	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/10/2013
	 * @StepsToReproduce: Patient login Click on Make Payment Give Payment
	 *                    Details Submit Payment Details Logout of Patient
	 *                    Portal Login to Practice Portal and Click on Online
	 *                    Bill Payment Set Details in
	 *                    PaymentCommunicationDetails and Sent Message to
	 *                    Patient Logout from the Practice Portal Login to
	 *                    Patient Portal Go to Inbox and Find message Validate
	 *                    message ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBillPaymentEnd2End() throws Exception {

		log("Test Case: testAppointmentRequest");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Verify for My Patient Page ");
		PortalUtil.setPortalFrame(driver);
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

		log("step 4: Click on Make Payment Link ");
		MakePaymentPage makePaymentPage = myPatientPage.clickMakePaymentLnk();

		log("step 5: Set Make Payments Fields");
		makePaymentPage.setMakePaymentFields();

		log("step 6: Logout of Patient Portal");
		myPatientPage.logout(driver);

		// Load up practice test data
		log("step 7: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 8:Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		log("step 9:Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		log("Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(PortalConstants.PatientAccountNumber);

		log("Get Bill Details");
		onlineBillPaySearchPage.getBillPayDetails();

		log("Set Payment Communication Details");
		onlineBillPaySearchPage.setPaymentCommunicationDetails();

		log("step 10: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 11: Login to Patient Portal");
		loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 12: Go to Inbox");
		ConsolidatedInboxPage inboxPage = myPatientPage.clickViewAllMessages();
		PerformanceReporter.getPageLoadDuration(driver, ConsolidatedInboxPage.PAGE_NAME);

		String uniquePracticeResponse = Long.toString(onlineBillPaySearchPage.getCreatedTs())+PracticeConstants.BillPaymentSubject;
		
		log("step 13: Find message in Inbox");
		ConsolidatedInboxMessage message = inboxPage.clickMessageLinkOpenMessageInInbox(uniquePracticeResponse);

		log("step 14: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().contains(uniquePracticeResponse), "Expected subject containting ["
				+ uniquePracticeResponse + "but actual subject was [" + actualSubject + "]");

	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/17/2013
	 * @StepsToReproduce: Patient login Click on Create Account Give New User
	 *                    Details Click on Create Account Button to Submit User
	 *                    Details Click On Start Registration Button in My
	 *                    Patient Page Fill the Required Details in each from
	 *                    Click on save and continue button to save the filled
	 *                    details in the form Validate Registration Confirmation
	 *                    message ================================
	 *                    =============================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDiscreteForms() throws Exception {

		log("Test Case: testCreatePatientOnBetaSite");
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
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		BetaSiteCreateAccountPage pBetaSiteCreateAccountPage = loginpage.signUpIntoBetaSite();

		log("step 3:Fill detials in Create Account Page");
		String email = PortalUtil.createRandomEmailAddress(testcasesData.getEmail());
		log("email:-" + email);
		MyPatientPage pMyPatientPage = pBetaSiteCreateAccountPage.BetaSiteCreateAccountPage(testcasesData.getFirstName(),
				testcasesData.getLastName(), email, testcasesData.getPhoneNumber(), testcasesData.getDob_Month(),
				testcasesData.getDob_Day(), testcasesData.getDob_Year(), testcasesData.getZip(), testcasesData.getSSN(),
				testcasesData.getAddress(), testcasesData.getPassword(), testcasesData.getSecretQuestion(), testcasesData.getAnswer(),
				testcasesData.getAddressState(), testcasesData.getAddressCity());

		log("step 4:Click On Start Registration Button");
		FormWelcomePage pFormWelcomePage = pMyPatientPage.clickStartRegistrationButton(driver);

		log("Click On Continue Button");
		FormBasicInfoPage pFormBasicInfoPage = pFormWelcomePage.clickContinueButton();

		log("step 5:Set Basic Information Form Fields");
		FormEmergencyContactPage pFormEmergencyContactPage = pFormBasicInfoPage.setBasicInfoFromFields();

		log("step 6:Set Emergency Contact Form Fields");
		FormInsurancePage pFormInsurancePage = pFormEmergencyContactPage.setEmergencyContactFormFields(testcasesData.getEmail());

		log("step 7:Set Insurance Form Fields");
		FormOtherProvidersPage pFormOtherProvidersPage = pFormInsurancePage.setInsuranceFormFields();

		log("step 8:Set Providers Form Fields");
		FormCurrentSymptomsPage pFormCurrentSymptomsPage = pFormOtherProvidersPage.setProvidersFormFields();

		log("step 9:Set Current Symptoms Form Fields");
		FormMedicationsPage pFormMedicationsPage = pFormCurrentSymptomsPage.setCurrentSymptomsFormFields();

		log("step 10:Set Medication Form Fields");
		FormAllergiesPage pFormAllergiesPage = pFormMedicationsPage.setMedicationFormFields();

		log("step 11:Set Allergies Form Fields");
		FormVaccinePage pFormVaccinePage = pFormAllergiesPage.setAllergiesFormFields();

		log("step 12:Set Vaccine Form Fields");
		FormSurgeriesHospitalizationsPage pFormSurgeriesHospitalizationsPage = pFormVaccinePage.setVaccineFormFields();

		log("step 13:Set Surgeries Form Fields");
		FormPreviousExamsPage pFormPreviousExamsPage = pFormSurgeriesHospitalizationsPage.setSurgeriesFormFields();

		log("step 14:Set Previous Exams Form Fields");
		FormIllnessConditionsPage pFormIllnessConditionsPage = pFormPreviousExamsPage.setPreviousExamsFormFields();

		log("step 15:Set IllnessCondition Form Fields");
		FormFamilyHistoryPage pFormFamilyHistoryPage = pFormIllnessConditionsPage.setIllnessConditionFormFields();

		log("step 16:Set Family History Form Fields");
		FormSocialHistoryPage pFormSocialHistoryPage = pFormFamilyHistoryPage.setFamilyHistoryFormFields();

		log("step 17:Set Social History Form Fields");
		pFormSocialHistoryPage.setSocialHistoryFormFields();

		log("step 18:Verify Registration Confirmation Text");
		pMyPatientPage.verifyRegistrationConfirmationText();
	}

	/**
	 * @Author: Gajendran
	 * @Date: 07/29/2013
	 * 
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMyMessagesRefreshButton() throws Exception {

		log("Test Case: testAppointmentRequest");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3:Click on My Message Link");
		ConsolidatedInboxPage Inboxpage = myPatientPage.clickMymessages();

		log("step 4:Check for Refresh Button");
		Inboxpage.ClickRefreshButton();

		log("step 5:Check for New Button");
		Inboxpage.ClickNewButton();
	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/30/2013
	 * @StepsToReproduce: Patient login Click on PHR Link Page will redirected
	 *                    to PHR by accepting terms and conditions Verify for
	 *                    PHR page and Logout form PHR
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPhrPortalSSO() throws Exception {

		log("Test Case: testPhrPortalSSO");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2: LogIn");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage myPatientPage = loginPage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 3: Verify for My Patient Page ");
		PortalUtil.setPortalFrame(driver);
		verifyEquals(myPatientPage.txtMyPatientPage.getText(), PortalConstants.MyPatientPage);

		log("step 4: Click PHR Link");
		PHRPage pPHRPage = myPatientPage.clickPHR(driver);

		log("Accept Terms and Conditions");
		pPHRPage.clickAccept();

		log("step 5:Verify for PHR Page");
		verifyEquals(true, pPHRPage.phrHomeLogo.isDisplayed());

		log("step 6:Logout from PHR");
		pPHRPage.clickLogout();

	}

	/**
	 * @Author: Jakub Calabek
	 * @Date: 08/12/2013
	 * @StepsToReproduce: Doctor inviting patient from the Practice Portal site.
	 *                    Specifying all necessary data. Then withdrawing link
	 *                    to patient activation. User is finishing registration
	 *                    by email which is send to patient patient gmail.
	 * 
	 *                    account. ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddNewPatientSearch() throws Exception {

		PatientActivationTest patientActivationTest = new PatientActivationTest();

		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		patientActivationTest.PatientActivation(driver, practiceTestData, testcasesData.getEmail());

		// Moving to the Unlock Link get from the Creation on the
		// PracticePortal

		PatientActivationUtil patientActivation = new PatientActivationUtil();

		patientActivation.ActivatePatient(driver, testcasesData, patientActivationTest, practiceTestData,
				patientActivationTest.getUnlockLink());
	}

	/**
	 * @Author: Jakub Calabek
	 * @Date: 08/12/2013
	 * @StepsToReproduce: Doctor inviting patient from the Practice Portal site.
	 *                    Specifying all necessary data. Then withdrawing link
	 *                    to patient activation. User is finishing registration
	 *                    by email which is send to patient patient gmail. Very
	 *                    similar flow to the previous test. Only different
	 *                    thing is on the Practice site when the patient
	 *                    activation is provided via {@link DifferentSelector}
	 *                    flow.
	 * 
	 *                    account. ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddNewPatientActivation() throws Exception {

		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Creating data provider
		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		patientActivationSearchTest.PatientActivation(driver, practiceTestData, testcasesData.getEmail());

		// Moving to the Unlock Link get from the Creation on the
		// PracticePortal

		PatientActivationUtil patientActivation = new PatientActivationUtil();

		patientActivation.ActivatePatient(driver, testcasesData, patientActivationSearchTest, practiceTestData,
				patientActivationSearchTest.getUnlockLink());

	}
}