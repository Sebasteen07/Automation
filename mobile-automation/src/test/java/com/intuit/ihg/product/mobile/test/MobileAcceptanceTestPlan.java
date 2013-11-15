package com.intuit.ihg.product.mobile.test;

import static org.testng.Assert.assertNotNull;
import java.util.Date;
import javax.mail.Message;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.CheckEmail;
import com.intuit.ihg.common.utils.mail.Gmail;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.MobileHomePage;
import com.intuit.ihg.product.mobile.page.MobileSignInPage;
import com.intuit.ihg.product.mobile.page.forgotuserid.ForgotUserIdEnterEmailPage;
import com.intuit.ihg.product.mobile.page.forgotuserid.ForgotUserIdEnterSecretAnswerPage;
import com.intuit.ihg.product.mobile.page.makepayment.MakeAPayment;
import com.intuit.ihg.product.mobile.page.makepayment.NewCard;
import com.intuit.ihg.product.mobile.page.makepayment.NewCreditCardBillingInformation;
import com.intuit.ihg.product.mobile.page.makepayment.PaymentConfirmationPage;
import com.intuit.ihg.product.mobile.page.resetpassword.ResetPasswordEnterNewPasswordPage;
import com.intuit.ihg.product.mobile.page.resetpassword.ResetPasswordEnterSecurityCodePage;
import com.intuit.ihg.product.mobile.page.resetpassword.ResetPasswordEnterUserIdPage;
import com.intuit.ihg.product.mobile.page.solutions.apptrequest.ARSubmissionPage;
import com.intuit.ihg.product.mobile.page.solutions.common.AllDoctorsPage;
import com.intuit.ihg.product.mobile.page.solutions.common.SelectADoctorPage;
import com.intuit.ihg.product.mobile.page.solutions.common.SelectALocationPage;
import com.intuit.ihg.product.mobile.page.solutions.common.SelectAPracticePage;
import com.intuit.ihg.product.mobile.page.solutions.common.SubmissionConfirmationPage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageDetailsPage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageInboxPage;
import com.intuit.ihg.product.mobile.page.solutions.rxrenewal.RequestRenewalPage;
import com.intuit.ihg.product.mobile.page.solutions.rxrenewal.SelectAMedicationPage;
import com.intuit.ihg.product.mobile.utils.Mobile;
import com.intuit.ihg.product.mobile.utils.MobileConstants;
import com.intuit.ihg.product.mobile.utils.MobileTestCaseData;
import com.intuit.ihg.product.mobile.utils.MobileUtil;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestSearchPage;
import com.intuit.ihg.product.practice.page.rxrenewal.RxRenewalSearchPage;
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
        String testcaseName = "testMobileLoginLogout";
		Mobile mobile=new Mobile();
		MobileTestCaseData testcasesData=new MobileTestCaseData(mobile, testcaseName);

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
		String testcaseName = "testTabAppointmentRequest";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);

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
		String testcaseName = "testTabRxRenewal";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);

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
		String testcaseName = "testTabAsk";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);

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
		String testcaseName = "testTabMakeAPayment";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);

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
        String testcaseName = "testMobileARPreferredDocOneLocation";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);
        
		logTestInfo(testcasesData);
		
		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());
	
        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select preferred doc");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelPage.selectDoctor(testcasesData.getPractice_DocSearchString());


        log("step 4:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,MobileConstants.APPOINTMENT_REASON,MobileConstants.APPOINTMENT_TIME);

        log("Step 5:clickClose");
        pSubconfirm.clickClose();


        //TODO Add prac side and mail and secure message verification
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
    @Test(enabled = true, groups = {"DeploymentAcceptanceTests", "AcceptanceTests", "Positive", "AppointmentRequest"},retryAnalyzer = RetryAnalyzer.class)
    public void testMobileARPreferredDocMultiLocation() throws Exception {

    	log("step 1: Get Data from Excel");
        String testcaseName = "testMobileARPreferredDocMultiLocation";
 		Mobile mobile = new Mobile();
 		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);
         
 		logTestInfo(testcasesData);
 		
 		log("step 1:LogIn");
 		MobileSignInPage mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
 		MobileHomePage pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());
 		        
        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select preferred doc");
        SelectALocationPage pSelLoc = (SelectALocationPage) pSelPage.selectDoctor(testcasesData.getPractice_DocSearchString());

        log("step 4:Select Location");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectLocation(Integer.parseInt(MobileConstants.PRACTICE_LOCATIONID));

        log("step 5:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,MobileConstants.APPOINTMENT_REASON,MobileConstants.APPOINTMENT_TIME);
        
        log("Step 5:clickClose");
        pSubconfirm.clickClose();


        //TODO Add prac side and mail and secure message verification
/*

        Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());

        Message[] msgs = gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);

        System.out.println("test");
*/
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
	@Test(enabled = true, groups = { "AcceptanceTests", "Positive",
			"AppointmentRequest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileARSelectDocOneLocation() throws Exception {

		log("step 1: Get Data from Excel");
		Date currDate = new Date();
		String testcaseName = "testMobileARSelectDocOneLocation";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);

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
				.selectDocFromList(testcasesData.getPractice_DocSearchString());

		String reason = Long.toString(System.currentTimeMillis());
		log("step 5:Fill data and submit");
		SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit
				.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,
						reason, MobileConstants.APPOINTMENT_TIME);

		pMyPatientPage = pSubconfirm.clickClose();
		pMyPatientPage.clickLogout();
		Thread.sleep(2000);

		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice,
				testcaseName);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver,
				practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(
				practiceTestData.getUsername(), practiceTestData.getPassword());

		log("step 6: Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("step 7: Search for appt requests");
		apptSearch.searchForApptRequestsForToday();
		ApptRequestDetailStep1Page detailStep1 = apptSearch
				.getRequestDetails(reason);
		assertNotNull(detailStep1,
				"The submitted patient request was not found in the practice");

		log("step 8: Choose process option and respond to patient");
		ApptRequestDetailStep2Page detailStep2 = detailStep1
				.chooseApproveAndSubmit();

		log("step 9: Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(),
				"Expected the Appt Search Page to be loaded, but it was not.");

		log("step 10: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 11: Verify Gmail");
		Gmail gmail = new Gmail(MobileConstants.PATIENT_GMAILUNAME,
				MobileConstants.PATIENT_GMAILPASSWORD);
		Message[] msgs = gmail.findInNewMessages(
				MobileConstants.PATIENT_GMAILUNAME, "Appointment", currDate);

		log("step 12: Access Gmail and check for received email");
		boolean foundEmail = CheckEmail.validateEmail(gmail, currDate,
				MobileConstants.PATIENT_GMAILUNAME, "appointment",
				MobileConstants.PATIENT_GMAILUNAME);
		assertTrue(foundEmail, "Appointment Request email wasn't received.");

		log("step 12: LogIn to verify secure message in mobile");
		log("step 1:LogIn");
		mloginpage = new MobileSignInPage(driver, testcasesData.getUrl());
		pMyPatientPage = mloginpage.login(testcasesData.getUserName(),
				testcasesData.getPassword());

		MessageInboxPage mInbox = pMyPatientPage.clickMyMessages();
		MessageDetailsPage mDetails = mInbox.clickMessage("Approved "
				+ detailStep1.getCreatedTs());

		assertTrue(mDetails.getSubject().equalsIgnoreCase(
				"Approved " + detailStep1.getCreatedTs()));
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
		String testcaseName = "testMobileARSelectDocMultiLocation";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);

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
				.selectDocFromList(testcasesData.getPractice_DocSearchString());

		log("step 5:Select Location");
		ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc
				.selectLocation(Integer
						.parseInt(MobileConstants.PRACTICE_LOCATIONID));

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
		String testcaseName = "testMobileARSearchDocOneLocation";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);

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
				.searchForAndSelectDoc(testcasesData
						.getPractice_DocSearchString());

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
		String testcaseName = "testMobileARSearchDocMultiLocation";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);

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
				.searchForAndSelectDoc(testcasesData
						.getPractice_DocSearchString());

		log("step 5:Select Location");
		ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc
				.selectLocation(Integer
						.parseInt(MobileConstants.PRACTICE_LOCATIONID));

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
		String testcaseName = "testMobileARNegative";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);

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
				.selectDoctor(testcasesData.getPractice_DocSearchString());

		log("step 4:Fill data and submit");
		MobileBasePage mbPage = (MobileBasePage) pARSubmit
				.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,
						MobileConstants.APPOINTMENT_REASON,
						MobileConstants.APPOINTMENT_TIME);

		assertEquals(mbPage.getErrorMsg(),
				testcasesData.getTest_ErrorMessage(),
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
		String testcaseName = "testMobileRxRenewal";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);

		logTestInfo(testcasesData);

		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 3: Click RxRenewalTab");
		SelectAMedicationPage pSelectAMedicationPage = (SelectAMedicationPage) pMyPatientPage
				.clickRXLink();

		log("step 4: select Medication");
		RequestRenewalPage pRequestRenewalPage = pSelectAMedicationPage
				.selMedication(MobileConstants.RXRENEWAL_MEDICATION);
		pRequestRenewalPage.selectPharmacy(MobileConstants.RXRENEWAL_PHARMACY);

		pRequestRenewalPage.clickButtonSubmit();

		pMyPatientPage = pRequestRenewalPage.clickClose();
		pMyPatientPage.clickLogout();
		Thread.sleep(2000);

		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice,
				testcaseName);

		// Now start login with practice data
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
		log("Verify Prescription Confirmation in Practice Portal");
		rxRenewalSearchPage.verifyPrescriptionConfirmationSection(subject);

		log("Set Action Radio Button in Practice Portal");
		rxRenewalSearchPage.setActionRadioButton();

		log("Verify Process Completed Text in Practice Portal");
		rxRenewalSearchPage.verifyProcessCompleted();

		log("step 10: Logout of Practice Portal");
		practiceHome.logOut();

		log("step 12: LogIn to verify secure message in mobile");
		log("step 1:LogIn");
		mloginpage = new MobileSignInPage(driver, testcasesData.getUrl());
		pMyPatientPage = mloginpage.login(testcasesData.getUserName(),
				testcasesData.getPassword());

		log("subject##########################" + subject);
		MessageInboxPage mInbox = pMyPatientPage.clickMyMessages();
		MessageDetailsPage mDetails = mInbox.clickMessage(subject);

		assertTrue(mDetails.getSubject().equalsIgnoreCase(subject));

	}
	
	/**
	 * @Type :-Deployment Acceptance [DEV3, DEMO, PROD]
     * @Author:-bkrishnankutty
	 * @Date:-11/14/2013
	 * @User Story ID in Rally : NA 
	 * 
	 * @StepsToReproduce:
	 * 
	 * @throws Exception
	 */
    
	@Test(enabled = true, groups = { "DeploymentAcceptanceTests",
			"AcceptanceTests", "Positive", "RxRenewal" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMobileAsk() throws Exception {

		log("step 1: Get Data from Excel");
		String testcaseName = "testMobileAsk";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);

		logTestInfo(testcasesData);

		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(
				testcasesData.getUserName(), testcasesData.getPassword());

		log("step 3: Click RxRenewalTab");
		pMyPatientPage.clickAAQLink();
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
		String testcaseName = "testMobileMakeAPayment";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);
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
		MakeAPayment pMakeAPayment = pSelectAPracticePage
				.selectPracticeUsingString("IHGQA Automation NonIntegrated");

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
		PaymentConfirmationPage pPaymentConfirmationPage = pMakeAPayment
				.makePayment("30", "12345");

		log("step 9: Assert confirm message");
		assertTrue(pPaymentConfirmationPage.isPageLoaded(),
				"Page Not loaded still");
		assertEquals(pPaymentConfirmationPage.getConfirmationTitle(),
				"Payment has been sent",
				"Assertion for getConfirmationTitle Failed");

		log("step 10: Close the project and log out");
		MobileHomePage pMobileHomePage = pPaymentConfirmationPage.clickClose();
		pMobileHomePage.clickLogout();
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
		String testcaseName = "testMobileForgotUserID";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);
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
				.enterEmailAndSubmit(testcasesData.getUserName());
		
		log("step 5:Enter the answer your security ??");
		SubmissionConfirmationPage pSubmissionConfirmationPage = (SubmissionConfirmationPage) pForgotUserIdEnterSecretAnswerPage
				.enterSecretAnsSubmit("Luke");
		
		log("step 6:Assert confirmation message");
		assertEquals(pSubmissionConfirmationPage.getForgotUserIdTitle(),
				"Check Your Email", "Assertion for title failed");
		assertEquals(pSubmissionConfirmationPage.getForgotUserIdEmailText(),
				testcasesData.getUserName(), "Assertion for email failed");
		
		log("step 7:Close the app");
		MobileHomePage pMobileHomePage = pSubmissionConfirmationPage
				.clickClose();
		// pMobileHomePage.clickLogout();

		log("step 8:Get UserID from the email");
		String userID = util.getUserEmailFromGmail(testcasesData.getUserName(),
				testcasesData.getPassword(), "Your User ID");
		log("userID :=================" + userID);
		assertEquals(userID, testcasesData.getUserName(),
				"UserId is not matching");
		
		log("step 9:Login with userId");
		mloginpage = new MobileSignInPage(driver, testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(userID,
				testcasesData.getPassword());
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
		String testcaseName = "testMobileForgotPassword";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile,
				testcaseName);

		log("step 2: Clean the Gmail Inbox");
		String sSubject = String.format("Your password has been reset");
		MobileUtil util = new MobileUtil(driver);
		util.cleanInbox(testcasesData.getUserName(),
		 testcasesData.getPassword(), sSubject);

		logTestInfo(testcasesData);

		log("step 1:open Browser");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,
				testcasesData.getUrl());
		
		log("step 2:Click cant access link");
		ResetPasswordEnterUserIdPage pResetPasswordEnterUserIdPage = mloginpage
				.clickCantAccessLink();
		
		log("step 3:Enter user Id and submit");
		ResetPasswordEnterNewPasswordPage pResetPasswordEnterNewPasswordPage = (ResetPasswordEnterNewPasswordPage) pResetPasswordEnterUserIdPage
				.enterUserIdAndSubmit(testcasesData.getUserName());
		
		log("step 4:Enter new password and submit");
		ResetPasswordEnterSecurityCodePage pResetPasswordEnterSecurityCodePage = (ResetPasswordEnterSecurityCodePage) pResetPasswordEnterNewPasswordPage
				.enterNewPasswordAndSubmit("Luke", testcasesData.getPassword(),
						testcasesData.getPassword());
		
		log("step 5: Fecth Security code from the gmail");
		String secCode = util.getSecurityCodeFromGmail(
				testcasesData.getUserName(), testcasesData.getPassword(),
				"Your password has been reset");
		
		log("step 6: Enter the security code and submit");
		MobileHomePage pMobileHomePage = (MobileHomePage) pResetPasswordEnterSecurityCodePage
				.enterSecCodeAndSubmit(secCode);
		
		/*log("step 7: Assert sucess message");
		assertEquals(pMobileHomePage.getPasswordResetSuccessMsg(),
				"Your password has been updated.");*/
         
		log("step 7: logout");
		MobileSignInPage pMobileSignInPage = pMobileHomePage.clickLogout();

	}
	
	
	
    private void logTestInfo(MobileTestCaseData testObject) {
    
        log("INFO:: TESTCASE DETAILS - TestMethod : " + testObject.getTestMethodName() + "| Environment : " + IHGUtil.getEnvironmentType() + " | Browser : " + TestConfig.getBrowserType());
        log("URL: " + testObject.getUrl());
		log("USER NAME: " + testObject.getUserName());
		log("Password: " + testObject.getPassword());
		log ("practice_docsearchstring:"+testObject.getPractice_DocSearchString());
		log ("test_errormessage:"+testObject.getTest_ErrorMessage());
			
    }

}
