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
import com.intuit.ihg.product.mobile.page.solutions.apptrequest.ARSubmissionPage;
import com.intuit.ihg.product.mobile.page.solutions.common.AllDoctorsPage;
import com.intuit.ihg.product.mobile.page.solutions.common.SelectADoctorPage;
import com.intuit.ihg.product.mobile.page.solutions.common.SelectALocationPage;
import com.intuit.ihg.product.mobile.page.solutions.common.SubmissionConfirmationPage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageDetailsPage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageInboxPage;
import com.intuit.ihg.product.mobile.utils.Mobile;
import com.intuit.ihg.product.mobile.utils.MobileConstants;
import com.intuit.ihg.product.mobile.utils.MobileTestCaseData;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestSearchPage;
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
   @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"},retryAnalyzer = RetryAnalyzer.class)
    public void testMobileARSelectDocOneLocation() throws Exception {

	    log("step 1: Get Data from Excel");
	    Date currDate = new Date();
        String testcaseName = "testMobileARSelectDocOneLocation";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);
        
		logTestInfo(testcasesData);
		
		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());
		
        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select different doc");
        AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

        log("step 4:Select Doc from List");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pAllDocs.selectDocFromList(testcasesData.getPractice_DocSearchString());

        String reason = Long.toString(System.currentTimeMillis());
        log("step 5:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,reason,MobileConstants.APPOINTMENT_TIME);

        pMyPatientPage = pSubconfirm.clickClose();
        pMyPatientPage.clickLogout();
        Thread.sleep(2000);
       
      // Load up practice test data
       Practice practice =new Practice();
   	   PracticeTestData practiceTestData = new PracticeTestData(practice, testcaseName);
      
   	   // Now start login with practice data
     	PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
     	PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());
     	
        log("step 6: Click Appt Request tab");
        ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

        log("step 7: Search for appt requests");
        apptSearch.searchForApptRequestsForToday();
        ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(reason);
        assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

        log("step 8: Choose process option and respond to patient");
        ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();

        log("step 9: Confirm response details to patient");
        apptSearch = detailStep2.processApptRequest();
        assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

        log("step 10: Logout of Practice Portal");
        practiceHome.logOut();

        log("step 11: Verify Gmail");
        Gmail gmail = new Gmail(MobileConstants.PATIENT_GMAILUNAME, MobileConstants.PATIENT_GMAILPASSWORD);
        Message[] msgs = gmail.findInNewMessages(MobileConstants.PATIENT_GMAILUNAME,"Appointment",currDate);

        log("step 12: Access Gmail and check for received email");
		boolean foundEmail = CheckEmail.validateEmail(
                gmail,
                currDate,
                MobileConstants.PATIENT_GMAILUNAME,
                "appointment",
                MobileConstants.PATIENT_GMAILUNAME);
		assertTrue(foundEmail, "Appointment Request email wasn't received.");

        log("step 12: LogIn to verify secure message in mobile");
        log("step 1:LogIn");
		mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
		pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());

        MessageInboxPage mInbox = pMyPatientPage.clickMyMessages();
        MessageDetailsPage mDetails = mInbox.clickMessage("Approved "+detailStep1.getCreatedTs());

        assertTrue(mDetails.getSubject().equalsIgnoreCase("Approved "+detailStep1.getCreatedTs()));
    }

   
   
    /**
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
     @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"}, retryAnalyzer = RetryAnalyzer.class)
    public void testMobileARSelectDocMultiLocation() throws Exception {

    	 log("step 1: Get Data from Excel");
         String testcaseName = "testMobileARSelectDocMultiLocation";
 		Mobile mobile = new Mobile();
 		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);
         
 		logTestInfo(testcasesData);
 		
 		log("step 1:LogIn");
 		MobileSignInPage mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
 		MobileHomePage pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());
 		
        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select different doc");
        AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

        log("step 4:Select Doc From List");
        SelectALocationPage pSelLoc = (SelectALocationPage) pAllDocs.selectDocFromList(testcasesData.getPractice_DocSearchString());

        log("step 5:Select Location");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectLocation(Integer.parseInt(MobileConstants.PRACTICE_LOCATIONID));

        log("step 6:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,MobileConstants.APPOINTMENT_REASON,MobileConstants.APPOINTMENT_TIME);

        log("Step 5:clickClose");
        pSubconfirm.clickClose();


        //TODO Add prac side and mail and secure message verification

/*
        Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());

        Message[] msgs = gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);

        System.out.println("test");*/

    }

    /**
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
 @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"},retryAnalyzer = RetryAnalyzer.class)
    public void testMobileARSearchDocOneLocation() throws Exception {

	 log("step 1: Get Data from Excel");
     String testcaseName = "testMobileARSearchDocOneLocation";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);
     
		logTestInfo(testcasesData);
		
		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select different doc");
        AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

        log("step 4:Select Doc from List");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pAllDocs.searchForAndSelectDoc(testcasesData.getPractice_DocSearchString());

        log("step 5:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,MobileConstants.APPOINTMENT_REASON,MobileConstants.APPOINTMENT_TIME);

        log("Step 5:clickClose");
        pSubconfirm.clickClose();


        //TODO Add prac side and mail and secure message verification

/*
        Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());

        Message[] msgs = gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);

        System.out.println("test");*/

    }

   /**
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
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"},retryAnalyzer = RetryAnalyzer.class)
    public void testMobileARSearchDocMultiLocation() throws Exception {

    	log("step 1: Get Data from Excel");
        String testcaseName = "testMobileARSearchDocMultiLocation";
		Mobile mobile = new Mobile();
		MobileTestCaseData testcasesData = new MobileTestCaseData(mobile, testcaseName);
        
		logTestInfo(testcasesData);
		
		log("step 1:LogIn");
		MobileSignInPage mloginpage = new MobileSignInPage(driver,testcasesData.getUrl());
		MobileHomePage pMyPatientPage = mloginpage.login(testcasesData.getUserName(),testcasesData.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select different doc");
        AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

        log("step 4:Select Doc From List");
        SelectALocationPage pSelLoc = (SelectALocationPage) pAllDocs.searchForAndSelectDoc(testcasesData.getPractice_DocSearchString());

        log("step 5:Select Location");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectLocation(Integer.parseInt(MobileConstants.PRACTICE_LOCATIONID));

        log("step 6:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,MobileConstants.APPOINTMENT_REASON,MobileConstants.APPOINTMENT_TIME);

        log("Step 5:clickClose");
        pSubconfirm.clickClose();


        //TODO Add prac side and mail and secure message verification

/*
        Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());

        Message[] msgs = gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);

        System.out.println("test");*/

    }

  /**
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
    @Test(enabled = false, groups = {"AcceptanceTests", "Negative", "AppointmentRequest"},retryAnalyzer = RetryAnalyzer.class)
    public void testMobileARNegative() throws Exception {

    	log("step 1: Get Data from Excel");
        String testcaseName = "testMobileARNegative";
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
        MobileBasePage mbPage = (MobileBasePage) pARSubmit.fillWithDataAndSubmit(MobileConstants.APPOINTMENT_DATE,MobileConstants.APPOINTMENT_REASON,MobileConstants.APPOINTMENT_TIME);

        assertEquals(mbPage.getErrorMsg(), testcasesData.getTest_ErrorMessage(), "Verify Error Message is present as expected");

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
