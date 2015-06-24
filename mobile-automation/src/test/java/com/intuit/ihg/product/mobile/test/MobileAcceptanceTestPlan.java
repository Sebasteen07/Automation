package com.intuit.ihg.product.mobile.test;

import static org.testng.Assert.assertNotNull;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.utils.Mobile;
import com.intuit.ihg.product.mobile.utils.MobileConstants;
import com.intuit.ihg.product.mobile.utils.MobileTestCaseData;
import com.intuit.ihg.product.mobile.utils.MobileUtil;
import com.intuit.ihg.product.object.maps.mobile.page.MobileBasePage;
import com.intuit.ihg.product.object.maps.mobile.page.MobileHomePage;
import com.intuit.ihg.product.object.maps.mobile.page.MobileSignInPage;
import com.intuit.ihg.product.object.maps.mobile.page.forgotuserid.ForgotUserIdEnterEmailPage;
import com.intuit.ihg.product.object.maps.mobile.page.forgotuserid.ForgotUserIdEnterSecretAnswerPage;
import com.intuit.ihg.product.object.maps.mobile.page.makepayment.MakeAPayment;
import com.intuit.ihg.product.object.maps.mobile.page.makepayment.NewCard;
import com.intuit.ihg.product.object.maps.mobile.page.makepayment.NewCreditCardBillingInformation;
import com.intuit.ihg.product.object.maps.mobile.page.makepayment.PaymentConfirmationPage;
import com.intuit.ihg.product.object.maps.mobile.page.resetpassword.ResetPasswordEnterNewPasswordPage;
import com.intuit.ihg.product.object.maps.mobile.page.resetpassword.ResetPasswordEnterSecurityCodePage;
import com.intuit.ihg.product.object.maps.mobile.page.resetpassword.ResetPasswordEnterUserIdPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.apptrequest.ARSubmissionPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.askaquestion.AskAQuestionPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.askaquestion.SelectAQuestionPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.ccdviewer.CCDMessageDetailsPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.ccdviewer.CCDViewerDetailPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.ccdviewer.CCDViewerListPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.AllDoctorsPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SelectADoctorPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SelectALocationPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SelectAPracticePage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.common.SubmissionConfirmationPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.inbox.MessageDetailsPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.inbox.MessageInboxPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.pharmacy.AddPharmacyPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.pharmacy.ChooseLocationPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.pharmacy.PharmaciesListPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.pharmacy.PharmacyDetailsPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.rxrenewal.RequestRenewalPage;
import com.intuit.ihg.product.object.maps.mobile.page.solutions.rxrenewal.SelectAMedicationPage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;
import com.intuit.ihg.product.object.maps.practice.page.rxrenewal.RxRenewalSearchPage;
import com.intuit.ihg.product.practice.tests.BillPaymentTest;
import com.intuit.ihg.product.practice.utils.*;

public class MobileAcceptanceTestPlan extends BaseTestNGWebDriver {
	
	
	/**
	 * @Author:-bkrishnankutty
	 * @Date:-18/10/2013
	 * @User Story ID in Rally : US6142
	 * @StepsToReproduce:
	 * Launch Mobile URL and Login. Validate Home Page and
	 * Log Out from Mobile
	 * steps
	 * here
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"SmokeTests"}, retryAnalyzer=RetryAnalyzer.class)
	public void testMobileLoginLogout() throws Exception {

		log("testMobileLoginLogout");
		log("Environment on which Testcase is Running: "+IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: "+TestConfig.getBrowserType());
        
				
		log("step 1: Get Data from Excel ##########");
		Mobile mobile=new Mobile();
		MobileTestCaseData testcasesData=new MobileTestCaseData(mobile);

		log("URL: "+testcasesData.getUrl());
		log("USER NAME: "+testcasesData.getUserName());
		log("Password: "+testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");
		
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3:Assert Welcome text");
		assertTrue(verifyTextPresent(driver, "Welcome"));

		log("step 4:LogOut");
		pMyPatientPage.clickLogout();
	}
	
	
	/**
	 * @Author:-bkrishnankutty
	 * @Date:-25/10/2013
	 * @User Story ID in Rally : US6142
	 * @StepsToReproduce:
	 * Launch Mobile URL and Login. Click on Appointment Tab
	 * Log Out from Mobile
	 * steps
	 * here
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTabAppointmentRequest() throws Exception {

		log("Test Case: testTabAppointmentRequest");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");

		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click ApptReqTab");
		MobileBasePage mobileBasePage = pMyPatientPage.clickARLink();

		log("step 4: Click Home");
		mobileBasePage.clickHome();

		log("step 5:LogOut");
		pMyPatientPage.clickLogout();

	}
	
	
	/**
	 * @Author:-bkrishnankutty
	 * @Date:-25/10/2013
	 * @User Story ID in Rally : US6142
	 * @StepsToReproduce:
	 * Launch Mobile URL and Login. Click on RxRenewal Tab
	 * Log Out from Mobile
	 * steps
	 * here
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */


	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTabRxRenewal() throws Exception {

		log("Test Case: testTabRxRenewal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click RxRenewalTab");
		MobileBasePage mobileBasePage = pMyPatientPage.clickRXLink();

		log("step 4: Click Home");
		mobileBasePage.clickHome();

		log("step 5:LogOut");
		pMyPatientPage.clickLogout();

	}

	/**
	 * @Author:-bkrishnankutty
	 * @Date:-25/10/2013
	 * @User Story ID in Rally : US6142
	 * @StepsToReproduce:
	 * Launch Mobile URL and Login. Click on Ask a Question Tab
	 * Log Out from Mobile
	 * steps
	 * here
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTabAsk() throws Exception {
		log("Test Case: testTabAsk");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click Ask a Question Tab");
		MobileBasePage mobileBasePage = pMyPatientPage.clickAAQLink();

		log("step 4: Click Home");
		mobileBasePage.clickHome();

		log("step 5:LogOut");
		pMyPatientPage.clickLogout();

	}

	/**
	 * @Author:-bkrishnankutty
	 * @Date:-25/10/2013
	 * @User Story ID in Rally : US6142
	 * @StepsToReproduce:
	 * Launch Mobile URL and Login. Click on Bill Pay Tab
	 * Log Out from Mobile
	 * steps
	 * here
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "SmokeTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTabMakeAPayment() throws Exception {

		log("Test Case: testTabMakeAPayment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click Bill Pay Tab");
		MobileBasePage mobileBasePage = pMyPatientPage.clickBillPayLink();

		log("step 4: Click Home");		
		mobileBasePage.clickHome();

		log("step 5:LogOut");
		pMyPatientPage.clickLogout();

	}
	
	
	
	/**
     * 
     * @StepsToReproduce:
     * Select Appointment Request in mobile
     * Select Preferred Doc [ who works at one location]
     * Submit valid data
     *
     * @param errorMessage
     * @param to           - TestObject
     * @param apptReqData
     * @param pat          - Patient
     * @param prac         - Practice
     * @throws Exception
     */
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"}, retryAnalyzer = RetryAnalyzer.class)
    public void testMobileARPreferredDocOneLocation() throws Exception {

    	        
        log("step 1: Get Data from Excel");
      
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);
        
		logTestInfo(testcasesData);
		
		log("step 2:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());
	
        log("step 3:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 4:Select preferred doc");
        log("doctor: "+testcasesData.getAppointmentDoctorSingleLoc());
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelPage.selectDoctor(testcasesData.getAppointmentDoctorSingleLoc());


        String reason = Long.toString(System.currentTimeMillis());
		log("step 5:Fill data and submit");
		SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit
				.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,
						reason, MobileConstants.APPOINTMENT_TIME);

		pMyPatientPage = pSubconfirm.clickClose();
		pMyPatientPage.clickLogout();
		//Let's leave this here just in case it falls apart on Jenkins
		//Thread.sleep(2000);

		log("step 6: Get Practice Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		log("step 7: LogIn to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(
				practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 8: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("step 9: Search for appt requests");
		apptSearch.searchForApptRequestsForToday();
		ApptRequestDetailStep1Page detailStep1 = apptSearch
				.getRequestDetails(reason);
		assertNotNull(detailStep1,
				"The submitted patient request was not found in the practice");

		log("step 10: Choose process option and respond to patient");
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();

		log("step 11: Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(),
				"Expected the Appt Search Page to be loaded, but it was not.");

		log("step 12: Logout of Practice Portal");
		practiceHome.logOut();
		
		/*
		log("step 13: Verify Gmail");
		Gmail gmail = new Gmail(testcasesData.getGmailUName(),testcasesData.getGmailPassword());
		Message[] msgs = gmail.findInNewMessages(testcasesData.getUserName(), "Appointment", currDate);

		log("step 14: Access Gmail and check for received email");
		boolean foundEmail = CheckEmail.validateEmail(gmail, currDate,
				testcasesData.getUserName(), "appointment",
				testcasesData.getUserName());
		assertTrue(foundEmail, "Appointment Request email wasn't received.");
		
		*/
		
		log("step 15: LogIn to verify secure message in mobile");
		mloginpage = new MobileSignInPage(driver, testcasesData.getUrl());

		MessageInboxPage mInbox = pMyPatientPage.clickMyMessages();
		MessageDetailsPage mDetails = mInbox.clickMessage("Approved "
				+ detailStep1.getCreatedTs());

		assertTrue(mDetails.getSubject().equalsIgnoreCase(
				"Approved " + detailStep1.getCreatedTs()));
    }
    

    /**
     * @StepsToReproduce:
     * Select Appointment Request in mobile
     * Select Preferred Doc
     * Select Location
     * Submit valid data
     *
     * @param errorMessage
     * @param to           - TestObject
     * @param apptReqData
     * @param pat          - Patient
     * @param prac         - Practice
     * @throws Exception
     */
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"},retryAnalyzer = RetryAnalyzer.class)
    public void testMobileARPreferredDocMultiLocation() throws Exception {

    	log("step 1: Get Data from Excel");
    	
 		Mobile mobile = new Mobile();
 		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);
         
 		logTestInfo(testcasesData);
 		
 		log("step 1:LogIn");
 		MobileSignInPage mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
 		MobileHomePage pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());
 		        
        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select preferred doc");
        SelectALocationPage pSelLoc = (SelectALocationPage) pSelPage.selectDoctor(testcasesData.getAppointmentDoctor());

        log("step 4:Select Location");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectFirstLocation();

        log("step 5:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,MobileConstants.APPOINTMENT_REASON,MobileConstants.APPOINTMENT_TIME);
        
        log("Step 5:clickClose");
        pSubconfirm.clickClose();
        
   }

    /**
     * 
     * @StepsToReproduce:
     * Select Appointment Request in mobile
     * Select A Different Doc
     * Select a doc from the list of all doctors[ who works at one location]
     * Submit valid data
     *
     * @param errorMessage
     * @param to           - TestObject
     * @param apptReqData
     * @param pat          - Patient
     * @param prac         - Practice
     * @throws Exception
     */
	@Test(enabled = true, groups = { "DeploymentAcceptanceTests","AcceptanceTests", "Positive",
			"AppointmentRequest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileARSelectDocOneLocation() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		logTestInfo(testcasesData);

		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 2:click AR link");
		SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage
				.clickARLink();

		log("step 3:Select different doc");
		AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

		log("step 4:Select Doc from List");
		ARSubmissionPage pARSubmit = (ARSubmissionPage) pAllDocs
				.selectDocFromList(testcasesData.getAppointmentDoctorSingleLoc());

		String reason = Long.toString(System.currentTimeMillis());
		log("step 5:Fill data and submit");
		SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit
				.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,
						reason, MobileConstants.APPOINTMENT_TIME);

		pMyPatientPage = pSubconfirm.clickClose();
		pMyPatientPage.clickLogout();

	}
   
   
    /**
     * @StepsToReproduce:
     * Select Appointment Request in mobile
     * Select A Different Doc
     * Select a doc from the list of all doctors
     * Select Location
     * Submit valid data
     *
     * @param errorMessage
     * @param to           - TestObject
     * @param apptReqData
     * @param pat          - Patient
     * @param prac         - Practice
     * @throws Exception
     */
	@Test(enabled = true, groups = { "AcceptanceTests", "Positive",
			"AppointmentRequest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileARSelectDocMultiLocation() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		logTestInfo(testcasesData);

		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 2:click AR link");
		SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage
				.clickARLink();

		log("step 3:Select different doc");
		AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

		log("step 4:Select Doc From List");
		SelectALocationPage pSelLoc = (SelectALocationPage) pAllDocs
				.selectDocFromList(testcasesData.getAppointmentDoctor());

		log("step 5:Select Location");
		ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectFirstLocation();

		String reason = Long.toString(System.currentTimeMillis());
		log("step 5:Fill data and submit");
		SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit
				.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,
						reason, MobileConstants.APPOINTMENT_TIME);

		pMyPatientPage = pSubconfirm.clickClose();
		pMyPatientPage.clickLogout();

	}
	

    /**
     * @StepsToReproduce:
     * Select Appointment Request in mobile
     * Select A Different Doc
     * Search for and select a doc from the list of all doctors[ who works at one location]
     * Submit valid data
     *
     * @param errorMessage
     * @param to           - TestObject
     * @param apptReqData
     * @param pat          - Patient
     * @param prac         - Practice
     * @throws Exception
     */
	@Test(enabled = true, groups = { "AcceptanceTests", "Positive",
			"AppointmentRequest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileARSearchDocOneLocation() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		logTestInfo(testcasesData);

		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 2:click AR link");
		SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage
				.clickARLink();

		log("step 3:Select different doc");
		AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

		log("step 4:Select Doc from List" + testcasesData.getAppointmentDoctorSingleLoc());
		ARSubmissionPage pARSubmit = (ARSubmissionPage) pAllDocs
				.searchForAndSelectDoc(testcasesData.getAppointmentDoctorSingleLoc());

		log("step 5:Fill data and submit");
		SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit
				.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,
						MobileConstants.APPOINTMENT_REASON,
						MobileConstants.APPOINTMENT_TIME);

		log("Step 5:clickClose");
		pSubconfirm.clickClose();

		// TODO Add prac side and mail and secure message verification

		/*
		 * Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());
		 * 
		 * Message[] msgs =
		 * gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);
		 * 
		 * System.out.println("test");
		 */

	}
	

   /**
     * @StepsToReproduce:
     * Select Appointment Request in mobile
     * Select A Different Doc
     * Search for and select a doc from the list of all doctors
     * Select Location
     * Submit valid data
     *
     * @param errorMessage
     * @param to           - TestObject
     * @param apptReqData
     * @param pat          - Patient
     * @param prac         - Practice
     * @throws Exception
     */
	@Test(enabled = true, groups = { "AcceptanceTests", "Positive",
			"AppointmentRequest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileARSearchDocMultiLocation() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		logTestInfo(testcasesData);

		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 2:click AR link");
		SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage
				.clickARLink();

		log("step 3:Select different doc");
		AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

		log("step 4:Select Doc From List");
		SelectALocationPage pSelLoc = (SelectALocationPage) pAllDocs
				.searchForAndSelectDoc(testcasesData.getAppointmentDoctor());

		log("step 5:Select Location");
		ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectFirstLocation();

		log("step 6:Fill data and submit");
		SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit
				.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,
						MobileConstants.APPOINTMENT_REASON,
						MobileConstants.APPOINTMENT_TIME);

		log("Step 5:clickClose");
		pSubconfirm.clickClose();

		// TODO Add prac side and mail and secure message verification

		/*
		 * Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());
		 * 
		 * Message[] msgs =
		 * gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);
		 * 
		 * System.out.println("test");
		 */

	}

  /**
     * @StepsToReproduce:
     * Select Appointment Request in mobile
     * Select A Preferred Doc
     * Submit invalid data
     * Assert the error message
     *
     * @param errorMessage
     * @param to           - TestObject
     * @param apptReqData
     * @param pat          - Patient
     * @param prac         - Practice
     * @throws Exception
     */
    
    // Need proper Test Data for this
	@Test(enabled = false, groups = { "AcceptanceTests", "Negative",
			"AppointmentRequest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileARNegative() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		logTestInfo(testcasesData);

		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 2:click AR link");
		SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage
				.clickARLink();

		log("step 3:Select preferred doc");
		ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelPage
				.selectDoctor(testcasesData.getAppointmentDoctor());

		log("step 4:Fill data and submit");
		MobileBasePage mbPage = pARSubmit
				.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,
						MobileConstants.APPOINTMENT_REASON,
						MobileConstants.APPOINTMENT_TIME);

		assertEquals(mbPage.getErrorMsg(),"Please enter the reason for your visit.",
				"Verify Error Message is present as expected");

	}
   
    /**
     * @Type :-Deployment Acceptance [DEV3, DEMO, PROD]
     * @Author:-bkrishnankutty
	 * @Date:-11/05/2013
	 * @User Story ID in Rally : NA
	 * 
	 * @StepsToReproduce:
     * Login to Medfusion mobile site
     * Go to mypatient page
     * Click on Prescription renewal
     * Selct an Rx
     * Add a New pharmacy and  submit
     * Log into practice portal and approve the Rx renewal
     * Login to Medfusion mobile site
     * Click on my message and verify the message 
     * 
  	 * @AreaImpacted :- 
	 * @Description
	 * @throws Exception
     */
	@Test(enabled = true, groups = { "DeploymentAcceptanceTests",
			"AcceptanceTests", "Positive", "RxRenewal" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileRxRenewal() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		logTestInfo(testcasesData);

		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 3: Click RxRenewalTab");
		pMyPatientPage.clickRXLink();		
		
		log("step 4: select Medication");
		SelectAMedicationPage pSelectAMedicationPage = PageFactory.initElements(driver, SelectAMedicationPage.class);
		Thread.sleep(2000);		
		RequestRenewalPage pRequestRenewalPage = pSelectAMedicationPage.selFirstMedication();
		
		if("QA1".equals(IHGUtil.getEnvironmentType().toString())){
			log("QA1 found -> Step 4b: Select Provider");
			SelectADoctorPage pSelPage = PageFactory.initElements(driver, SelectADoctorPage.class);
			Thread.sleep(2000);
			pSelPage.selectDoctor(40800);		
		}
		
		log("step 5: select first Pharmacy");
		pRequestRenewalPage.selectFirstPharmacy();
		pRequestRenewalPage.clickButtonSubmit();
		
		
		pMyPatientPage = pRequestRenewalPage.clickClose();
		pMyPatientPage.clickLogout();
		
		//Let's leave this here just in case it falls apart on Jenkins
		//Thread.sleep(2000);

		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		log("step 7: LogIn to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(
				practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 8:Click On RxRenewal in Practice Portal");
		RxRenewalSearchPage rxRenewalSearchPage = practiceHome
				.clickonRxRenewal();

		log("step 9:Search for Today's RxRenewal in Practice Portal");
		rxRenewalSearchPage.searchForRxRenewalToday();

		log("Get the RxRenewal Details in Practice Portal");
		rxRenewalSearchPage.getRxRenewalDetails();

		log("Set the RxRenewal Fields in Practice Portal");
		rxRenewalSearchPage.setRxRenewalFields();

		log("Click On Process RxRenewal Button in Practice Portal");
		rxRenewalSearchPage.clickProcessRxRenewal();

		String subject = rxRenewalSearchPage.getSubject();

		log("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		log("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		log("step 10: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 12: LogIn to verify secure message in mobile");
		log("step 1:LogIn");
		mloginpage = new MobileSignInPage(driver, testcasesData.getUrl());
		pMyPatientPage = mloginpage.login(testcasesData.getUserName(), testcasesData.getPassword());

		log("subject##########################" + subject);
		MessageInboxPage mInbox = pMyPatientPage.clickMyMessages();
		MessageDetailsPage mDetails = mInbox.clickMessage(subject);

		assertTrue(mDetails.getSubject().equalsIgnoreCase(subject));

	}
	
    /**
     * @Type :-Deployment Acceptance [DEV3, DEMO, PROD]
     * @Author:-prehacek
	 * @Date:-2/03/2014
	 * @User Story ID in Rally : NA
	 * 
	 * @StepsToReproduce:
     * Login to Medfusion mobile site
     * Go to mypatient page
     * Click on Prescription renewal
     * Selct an Rx
     * Add a New pharmacy and  submit
     * Log into practice portal and approve the Rx renewal
     * Login to Medfusion mobile site
     * Click on my message and verify the message 
     * 
  	 * @AreaImpacted :- 
	 * @Description
	 * @throws Exception
     */
	@Test(enabled = true, groups = { "DeploymentAcceptanceTests",
			"AcceptanceTests", "Positive", "PharmacyLookup" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobilePharmacyLookup() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		logTestInfo(testcasesData);

		log("step 1: LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());
		
		log("step 2: Click RxRenewalTab");
		pMyPatientPage.clickRXLink();
		SelectAMedicationPage pSelectAMedicationPage = PageFactory.initElements(driver, SelectAMedicationPage.class);
		  

		log("step 3: select Medication");
		Thread.sleep(2000);
		RequestRenewalPage pRequestRenewalPage = pSelectAMedicationPage.selFirstMedication();
		
		if("QA1".equals(IHGUtil.getEnvironmentType().toString())){
			log("QA1 found -> Step 3b: Select Provider");
			SelectADoctorPage pSelPage = PageFactory.initElements(driver, SelectADoctorPage.class);
			Thread.sleep(2000);
			pSelPage.selectDoctor(40800);		
		}
		
		log("step 4: Click Add New Pharmacy");
		AddPharmacyPage pAddPharmacyPage = pRequestRenewalPage.addNewPharmacy();
		
		log("step 5: Click Change current location");
		ChooseLocationPage pChooseLocationPage = pAddPharmacyPage.selectLocation();
		
		log("step 6: Search for location");
		pAddPharmacyPage = pChooseLocationPage.selectLocation("Cupertino 95014");
		
		log("step 7: Search for Pharmacies");
		PharmaciesListPage pPharmaciesListPage = pAddPharmacyPage.searchPharmacies();
		
		log("step 8: Select first Pharmacy");
		PharmacyDetailsPage pPharmacyDetailsPage = pPharmaciesListPage.selectFirstPharmacy();
		
		verifyTrue(pPharmacyDetailsPage.verifyPharmacyDetails(),
				"Informations about adress or telephone are missing");
		
		//pRequestRenewalPage.clickButtonSubmit();
		

	}
	
	/**
	 * @Type :-Deployment Acceptance [DEV3, DEMO, PROD]
     * @Author:-Prokop Rehacek
	 * @Date:-2/12/2013
	 * @User Story ID in Rally : NA 
	 * 
	 * @StepsToReproduce:
	 * 
	 * @throws Exception
	 */
    
	@Test(enabled = true, groups = { "DeploymentAcceptanceTests",
			"AcceptanceTests", "Positive", "mobileAsk" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileAsk() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		logTestInfo(testcasesData);

		log("step 2:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 3: Click Ask a Question");
		SelectAPracticePage pMySelectAPracticePage = (SelectAPracticePage) pMyPatientPage.clickAAQLink();
		
		log("step 4: Select a practice");
		SelectAQuestionPage pMySelectAQuestionPage = pMySelectAPracticePage.selectPractice(testcasesData.getAskAQuestionPractice());
		
		log("step 5: Select a Question");
		SelectALocationPage pMySelectALocationPage = pMySelectAQuestionPage.selectQuestion(testcasesData.getAskAQuestionType());
		
		log("step 6: Select a Location");
		AskAQuestionPage pMyAskAQuestionPage = pMySelectALocationPage.selectLocation(testcasesData.getAskAQuestionLocation());
		
		log("step 7: Fill and submit a Question");
		SubmissionConfirmationPage pSubconfirm = pMyAskAQuestionPage.fillAndSubmitQuestion(testcasesData.getAskAQuestionDoctor(), 
													"TestSubject", "TestBody");
		
		log("step 8: Close and logout");
		pMyPatientPage = pSubconfirm.clickClose();
		pMyPatientPage.clickLogout();
	}
	
	
	/**
     * @Author:-Prokop Rehacek
	 * @Date:-11/18/2013
	 * @User Story ID in Rally : NA 
	 * 
	 * @StepsToReproduce:
	 * Login to Medfusion Mobile App
	 * Go to My Patient Page
	 * Click on My Messages
	 * Select Any "New Health Information Import" message
	 * Verify the contents and each of the links
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "DeploymentAcceptanceTests",
			"AcceptanceTests", "Positive", "CCDViewer" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDViewer() throws Exception {

		log("Test Case: testCCDViewer");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getCCDUserName());
		log("Password: " + testcasesData.getCCDUserPassword());

		log("step 2: Load the Mobile  URL and Check the Page Title");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getCCDUserName(), testcasesData.getCCDUserPassword());

		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 3: Click My Messages");
		MessageInboxPage messageInboxPage = pMyPatientPage.clickMyMessages();
		
		String sHealthInfo = "New Health Information Import";
		log("step 4: Select Any \"New Health Information Import\" message");
		CCDMessageDetailsPage ccdMessageDetailsPage = messageInboxPage.clickMessageHealthInfo(sHealthInfo);
		
		log("step 5: Verify the contents and each of the links");
		CCDViewerListPage ccdViewerListPage = ccdMessageDetailsPage.clickReviewHealthInfo();
		
		log("step 5.1: Verify Basic Info");
		CCDViewerDetailPage ccdViewerDetailPage	= ccdViewerListPage.clicBasicInfo();

		verifyTrue(ccdViewerDetailPage.verifyBasicInfo(),
				"Informations about blood pressure and BMI are missing");
			
		log("step 6: Click Home");
		pMyPatientPage = ccdViewerDetailPage.clickHome();
		
		log("step 6: Click Logout");
		pMyPatientPage.clickLogout();

}
	
	
	/**
	 * @Type :-Deployment Acceptance [DEV3, DEMO, PROD]
     * @Author:-bkrishnankutty
	 * @Date:-11/12/2013
	 * @User Story ID in Rally : NA
	 * 
	 * @StepsToReproduce:
	 * Login to Medfusion Mobile App
	 * Go to My Patient Page
	 * Click online BillPayment
	 * Select Practice, Location, Discover card ,Enter Payment Details and Submit 
	 * Verify Payemnt Sucessful message with confirmation Number
	 * 
	 * =============================================================
	 * @AreaImpacted :- 
	 * Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "DeploymentAcceptanceTests",
			"AcceptanceTests", "Positive", "MakeAPayment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileMakeAPayment() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);
		logTestInfo(testcasesData);

		log("step 2:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 3: Click BillPayTAB");
		SelectAPracticePage pSelectAPracticePage = (SelectAPracticePage) pMyPatientPage
				.clickBillPayLink();

		log("step 4: Select practice");
		SelectALocationPage pSelectALocation = (SelectALocationPage) pSelectAPracticePage
				.selectPracticeUsingString(testcasesData.getBillPayPracticeName());
		
		log("step 5: Select location");
		MakeAPayment pMakeAPayment  = pSelectALocation.selectLocationPayment(testcasesData.getBillPayPracticeLocation());

		log("step 5: Add a new credit card");
		NewCard pNewCard = pMakeAPayment.clicklnkAddNewCard();

		log("step 6: Fill card details");
		NewCreditCardBillingInformation pNewCreditCardBillingInformation = pNewCard
				.fillCardDetails("4111111111111111", "234", "2019", "Nov");

		log("step 7: Fill card Informations");
		pMakeAPayment = pNewCreditCardBillingInformation
				.fillCardBillingInformation("AutoDemoLN AutoDemoFN",
						"123 XYZ Ave", "Mountain View", "94043");

		log("step 8: Enter the amount and account details");
		String accountNumber = IHGUtil.createRandomNumericString().substring(0,5);
		PaymentConfirmationPage pPaymentConfirmationPage = pMakeAPayment
				.makePayment("30", accountNumber);

		log("step 9: Assert confirm message");
		assertTrue(pPaymentConfirmationPage.isPageLoaded(),
				"Page Not loaded still");
		assertEquals(pPaymentConfirmationPage.getConfirmationTitle(),
				"Payment has been sent",
				"Assertion for getConfirmationTitle Failed");

		log("step 10: Close the project and log out");
		MobileHomePage pMobileHomePage = pPaymentConfirmationPage.clickClose();
		pMobileHomePage.clickLogout();
		
		// Instancing virtualCardSwiperTest
		BillPaymentTest billPaymentTest = new BillPaymentTest();
		
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);
		
		// Executing Test
		String uniquePracticeResponse = billPaymentTest.billPaymentTest(driver, practiceTestData, accountNumber);
		
		log("step 11: LogIn to verify secure message in mobile");
		mloginpage = new MobileSignInPage(driver, testcasesData.getUrl());
		pMyPatientPage = mloginpage.login(testcasesData.getUserName(), testcasesData.getPassword());

		MessageInboxPage mInbox = pMyPatientPage.clickMyMessages();
		MessageDetailsPage mDetails = mInbox.clickMessage(uniquePracticeResponse);

		assertTrue(mDetails.getSubject().equalsIgnoreCase(uniquePracticeResponse));
		
		
	}
	
	
	/**
	 * @Type :-Deployment Acceptance [DEV3, DEMO, PROD]
     * @Author:-bkrishnankutty
	 * @Date:-11/14/2013
	 * @User Story ID in Rally : NA 
	 * 
	 * @StepsToReproduce:
	 * Open Medfusion Mobile App
	 * Click can't access link
	 * Click Forgot userID link
	 * Enter email Id , Security answer and submit
	 * Get your userID from the Gmail
	 * Log in with this userId
	 * 
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "DeploymentAcceptanceTests",
			"AcceptanceTests", "Positive", "ForgotUserID" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileForgotUserID() throws Exception {
		
		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);
		MobileUtil util = new MobileUtil(driver);
		logTestInfo(testcasesData);
		
		log("step 1:open Browser");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		
		log("step 2:Click cant access link");
		ResetPasswordEnterUserIdPage pResetPasswordEnterUserIdPage = mloginpage
				.clickCantAccessLink();
		
		log("step 3:Click Forgot userid link");
		ForgotUserIdEnterEmailPage pForgotUserIdEnterEmailPage = pResetPasswordEnterUserIdPage
				.clickForgotUserId();
		
		log("step 4:Enter your Email ID");
		ForgotUserIdEnterSecretAnswerPage pForgotUserIdEnterSecretAnswerPage = (ForgotUserIdEnterSecretAnswerPage) pForgotUserIdEnterEmailPage
				.enterEmailAndSubmit(testcasesData.getForgotUserName());
		
		log("step 5:Enter the answer your security ??");
		SubmissionConfirmationPage pSubmissionConfirmationPage = (SubmissionConfirmationPage) pForgotUserIdEnterSecretAnswerPage
				.enterSecretAnsSubmit(testcasesData.getUserAnswer());
		
		log("step 6:Assert confirmation message");
		assertEquals(pSubmissionConfirmationPage.getForgotUserIdTitle(),
				"Check Your Email", "Assertion for title failed");
		assertEquals(pSubmissionConfirmationPage.getForgotUserIdEmailText(),
				testcasesData.getForgotUserName(), "Assertion for email failed");
		
		log("step 7:Close the app");
		pSubmissionConfirmationPage.clickClose();

		log("step 8:Get UserID from the email");
		String userID = util.getUserEmailFromGmail(testcasesData.getForgotUserName(),
				testcasesData.getForgotPassword(), "Your User ID");
		log("userID :=================" + userID);
		assertEquals(userID, testcasesData.getForgotUserName(),
				"UserId is not matching");
		
		log("step 9:Login with userId");
		mloginpage = new MobileSignInPage(driver, testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(userID,
				testcasesData.getForgotPassword());
		pMyPatientPage.waitForlogoutLink(driver, 60);

		log("step 10:Assert Welcome text");
		assertTrue(verifyTextPresent(driver, "Welcome"));

		log("step 11:LogOut");
		pMyPatientPage.clickLogout();
	}
	
	
	/**
	 * @Type :-Deployment Acceptance [DEV3, DEMO, PROD]
     * @Author:-bkrishnankutty
	 * @Date:-11/14/2013
	 * @User Story ID in Rally : NA 
	 * 
	 * @StepsToReproduce:
	 * open login page
	 * Click can't access link
	 * Enter user Id , new password and submit
	 * Get security code from the Gmail
	 * Enter the security code and submit
	 * Assert success
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "DeploymentAcceptanceTests",
			"AcceptanceTests", "Positive", "ForgotPassword" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileForgotPassword() throws Exception {

		log("step 1: Get Data from Excel");
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile);

		log("step 2: Clean the Gmail Inbox");
		String sSubject = String.format("Your password has been reset");
		MobileUtil util = new MobileUtil(driver);
		//util.emailMessageRemover(testcasesData.getForgotUserName(),
		//		testcasesData.getGmailPassword(), sSubject);
		
		logTestInfo(testcasesData);

		log("step 1:open Browser");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		
		log("step 2:Click cant access link");
		ResetPasswordEnterUserIdPage pResetPasswordEnterUserIdPage = mloginpage
				.clickCantAccessLink();
		
		log("step 3:Enter user Id and submit");
		ResetPasswordEnterNewPasswordPage pResetPasswordEnterNewPasswordPage = (ResetPasswordEnterNewPasswordPage) pResetPasswordEnterUserIdPage
				.enterUserIdAndSubmit(testcasesData.getForgotUserName());
		
		log("step 4:Enter new password and submit");
		ResetPasswordEnterSecurityCodePage pResetPasswordEnterSecurityCodePage = (ResetPasswordEnterSecurityCodePage) pResetPasswordEnterNewPasswordPage
				.enterNewPasswordAndSubmit(testcasesData.getUserAnswer(), testcasesData.getForgotPassword(), testcasesData.getForgotPassword());
		
		log("step 5: Fecth Security code from the gmail");
		String secCode = util.getSecurityCodeFromGmail(
				testcasesData.getForgotUserName(), testcasesData.getForgotPassword(),
				sSubject);
		
		log("step 6: Enter the security code and submit");
		MobileHomePage pMobileHomePage = (MobileHomePage) pResetPasswordEnterSecurityCodePage
				.enterSecCodeAndSubmit(secCode);
		
		/*log("step 7: Assert sucess message");
		assertEquals(pMobileHomePage.getPasswordResetSuccessMsg(),
				"Your password has been updated.");*/
         
		log("step 7: logout");
		pMobileHomePage.clickLogout();

	}
	
	
	
	
    private void logTestInfo(MobileTestCaseData testObject) {
    
        //log("INFO:: TESTCASE DETAILS - TestMethod : " + testObject.getTestMethodName() + "| Environment : " + IHGUtil.getEnvironmentType() + " | Browser : " + TestConfig.getBrowserType());
        log("URL: " + testObject.getUrl());
		log("USER NAME: " + testObject.getUserName());
		log("Password: " + testObject.getPassword());
		//log ("practice_docsearchstring:"+testObject.getPractice_DocSearchString());
		//log ("test_errormessage:"+testObject.getTest_ErrorMessage());
			
    }

}
