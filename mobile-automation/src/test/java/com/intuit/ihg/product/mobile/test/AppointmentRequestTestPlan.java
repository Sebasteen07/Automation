package com.intuit.ihg.product.mobile.test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.entities.AppointmentRequest;
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.Practice;
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
import com.intuit.ihg.common.utils.dataprovider.ExcelSheetUtil;
import com.intuit.ihg.common.utils.dataprovider.Filter;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageDetailsPage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageInboxPage;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestSearchPage;
import org.junit.Ignore;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.mail.Message;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import static org.testng.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 4/23/13
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class AppointmentRequestTestPlan extends BaseTestNGWebDriver {

    @DataProvider(name = "AppointmentRequestData")
    public static Iterator<Object[]> fileDataProvider(Method m,

                                                      ITestContext testContext) throws Exception {
        Filter filter1 = Filter.equalsIgnoreCase(TestObject.TEST_METHOD,

                m.getName());
        Filter filter2 = Filter.equalsIgnoreCase(TestObject.TEST_ENV,

                TestConfig.getTestEnv());
        Filter filter = Filter.and(filter1, filter2);


        LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();

        classMap.put("TestObject", TestObject.class);
        classMap.put("AppointmentRequest", AppointmentRequest.class);
        classMap.put("Patient", Patient.class);
        classMap.put("Practice", Practice.class);

        Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(

                AppointmentRequestTestPlan.class, classMap, "AppointmentRequestData.csv", 0,

                null, filter);
        return it;

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
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"}, dataProvider = "AppointmentRequestData")
    public void testMobileARPreferredDocOneLocation(String errorMessage, TestObject to, AppointmentRequest apptReqData, Patient pat, Practice prac) throws Exception {

        logTestInfo(to);

        log("USER NAME++++++++++++++++" + pat.getUserName());
        log("Password++++++++++++++++" + pat.getPassword());

        log("step 1:LogIn");
        Date currDate = new Date();
        MobileSignInPage mloginpage = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage = mloginpage.login(pat.getUserName(), pat.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select preferred doc");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelPage.selectDoctor(prac.getDocSearchString());


        log("step 4:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(apptReqData);

        //TODO fix close button xpath
        //pSubconfirm.clickClose();


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
    @Test(enabled = true, groups = {"DeploymentAcceptanceTests", "AcceptanceTests", "Positive", "AppointmentRequest"}, dataProvider = "AppointmentRequestData")
    public void testMobileARPreferredDocMultiLocation(String errorMessage, TestObject to, AppointmentRequest apptReqData, Patient pat, Practice prac) throws Exception {

        logTestInfo(to);

        log("USER NAME++++++++++++++++" + pat.getUserName());
        log("Password++++++++++++++++" + pat.getPassword());

        log("step 1:LogIn");
        Date currDate = new Date();
        MobileSignInPage mloginpage = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage = mloginpage.login(pat.getUserName(), pat.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select preferred doc");
        SelectALocationPage pSelLoc = (SelectALocationPage) pSelPage.selectDoctor(prac.getDocSearchString());

        log("step 4:Select Location");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectLocation(Integer.parseInt(prac.getLocationId()));

        log("step 5:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(apptReqData);

        //TODO fix close button xpath
        //pSubconfirm.clickClose();


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
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"}, dataProvider = "AppointmentRequestData")
    public void testMobileARSelectDocOneLocation(String errorMessage, TestObject to, AppointmentRequest apptReqData, Patient pat, Practice prac) throws Exception {

        logTestInfo(to);

        log("USER NAME++++++++++++++++" + pat.getUserName());
        log("Password++++++++++++++++" + pat.getPassword());

        log("step 1:LogIn");
        Date currDate = new Date();
        MobileSignInPage mloginpage = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage = mloginpage.login(pat.getUserName(), pat.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select different doc");
        AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

        log("step 4:Select Doc from List");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pAllDocs.selectDocFromList(prac.getDocSearchString());

        String reason = Long.toString(System.currentTimeMillis());
        apptReqData.setReason(reason);

        log("step 5:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(apptReqData);

        pMyPatientPage = pSubconfirm.clickClose();
        pMyPatientPage.clickLogout();
        Thread.sleep(2000);

        // Now start login with practice data
        log("step 5: Login to Practice Portal");
        PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, to.getTestEnv(), null);
        PracticeHomePage practiceHome = practiceLogin.login(prac.getDocUName(), prac.getDocPassword());

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
        Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());
        Message[] msgs = gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);

        log("step 12: Access Gmail and check for received email");
		boolean foundEmail = CheckEmail.validateEmail(
                gmail,
                currDate,
                pat.getEmailId(),
                "appointment",
                pat.getGmailUName());
		assertTrue(foundEmail, "Appointment Request email wasn't received.");

        log("step 12: LogIn to verify secure message in mobile");
        MobileSignInPage mloginpage1 = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage1 = mloginpage1.login(pat.getUserName(), pat.getPassword());

        MessageInboxPage mInbox = pMyPatientPage1.clickMyMessages();
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
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"}, dataProvider = "AppointmentRequestData")
    public void testMobileARSelectDocMultiLocation(String errorMessage, TestObject to, AppointmentRequest apptReqData, Patient pat, Practice prac) throws Exception {

        logTestInfo(to);

        log("USER NAME++++++++++++++++" + pat.getUserName());
        log("Password++++++++++++++++" + pat.getPassword());

        log("step 1:LogIn");
        Date currDate = new Date();
        MobileSignInPage mloginpage = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage = mloginpage.login(pat.getUserName(), pat.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select different doc");
        AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

        log("step 4:Select Doc From List");
        SelectALocationPage pSelLoc = (SelectALocationPage) pAllDocs.selectDocFromList(prac.getDocSearchString());

        log("step 5:Select Location");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectLocation(Integer.parseInt(prac.getLocationId()));

        log("step 6:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(apptReqData);

        //TODO fix close button xpath
        //pSubconfirm.clickClose();


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
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"}, dataProvider = "AppointmentRequestData")
    public void testMobileARSearchDocOneLocation(String errorMessage, TestObject to, AppointmentRequest apptReqData, Patient pat, Practice prac) throws Exception {

        logTestInfo(to);

        log("USER NAME++++++++++++++++" + pat.getUserName());
        log("Password++++++++++++++++" + pat.getPassword());

        log("step 1:LogIn");
        Date currDate = new Date();
        MobileSignInPage mloginpage = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage = mloginpage.login(pat.getUserName(), pat.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select different doc");
        AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

        log("step 4:Select Doc from List");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pAllDocs.searchForAndSelectDoc(prac.getDocSearchString());

        log("step 5:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(apptReqData);

        //TODO fix close button xpath
        //pSubconfirm.clickClose();


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
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"}, dataProvider = "AppointmentRequestData")
    public void testMobileARSearchDocMultiLocation(String errorMessage, TestObject to, AppointmentRequest apptReqData, Patient pat, Practice prac) throws Exception {

        logTestInfo(to);

        log("USER NAME++++++++++++++++" + pat.getUserName());
        log("Password++++++++++++++++" + pat.getPassword());

        log("step 1:LogIn");
        Date currDate = new Date();
        MobileSignInPage mloginpage = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage = mloginpage.login(pat.getUserName(), pat.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select different doc");
        AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();

        log("step 4:Select Doc From List");
        SelectALocationPage pSelLoc = (SelectALocationPage) pAllDocs.searchForAndSelectDoc(prac.getDocSearchString());

        log("step 5:Select Location");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelLoc.selectLocation(Integer.parseInt(prac.getLocationId()));

        log("step 6:Fill data and submit");
        SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) pARSubmit.fillWithDataAndSubmit(apptReqData);

        //TODO fix close button xpath
        //pSubconfirm.clickClose();


        //TODO Add prac side and mail and secure message verification
/*

        Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());

        Message[] msgs = gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);

        System.out.println("test");
*/
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
    @Test(enabled = true, groups = {"AcceptanceTests", "Negative", "AppointmentRequest"}, dataProvider = "AppointmentRequestData")
    public void testMobileARNegative(String errorMessage, TestObject to, AppointmentRequest apptReqData, Patient pat, Practice prac) throws Exception {

        logTestInfo(to);

        log("USER NAME++++++++++++++++" + pat.getUserName());
        log("Password++++++++++++++++" + pat.getPassword());

        log("step 1:LogIn");
        Date currDate = new Date();
        MobileSignInPage mloginpage = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage = mloginpage.login(pat.getUserName(), pat.getPassword());

        log("step 2:click AR link");
        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();

        log("step 3:Select preferred doc");
        ARSubmissionPage pARSubmit = (ARSubmissionPage) pSelPage.selectDoctor(prac.getDocSearchString());

        log("step 4:Fill data and submit");
        MobileBasePage mbPage = (MobileBasePage) pARSubmit.fillWithDataAndSubmit(apptReqData);

        assertEquals(mbPage.getErrorMsg(), errorMessage, "Verify Error Message is present as expected");

    }

    @Ignore
    @Test(enabled = true, groups = {"AcceptanceTests", "Positive", "AppointmentRequest"}, dataProvider = "AppointmentRequestData")
    public void testMobileAppointmentRequest(ITestContext ctx, String errorMessage, TestObject to, AppointmentRequest apptReqData, Patient pat, Practice prac) throws Exception {

        logTestInfo(to);

        log("USER NAME++++++++++++++++" + pat.getUserName());
        log("Password++++++++++++++++" + pat.getPassword());

        log("step 1:LogIn");
        Date currDate = new Date();
        MobileSignInPage mloginpage = new MobileSignInPage(driver, to.getTestEnv(), null);
        MobileHomePage pMyPatientPage = mloginpage.login(pat.getUserName(), pat.getPassword());

        SelectADoctorPage pSelPage = (SelectADoctorPage) pMyPatientPage.clickARLink();
        Thread.sleep(1000);

        ARSubmissionPage pARSubmit;

        if ((to.getTestTitle().contains("PreferredDoc"))) {
            pARSubmit = (ARSubmissionPage) pSelPage.selectDoctor(prac.getDocId());
        } else {
            AllDoctorsPage pAllDocs = pSelPage.selectDiffDoc();
            Thread.sleep(1000);
            if (to.getTestTitle().contains("Search Doc")) {
                pARSubmit = (ARSubmissionPage) pAllDocs.searchForAndSelectDoc(prac.getDocSearchString());
            } else pARSubmit = (ARSubmissionPage) pAllDocs.selectDocFromList(prac.getDocSearchString());
        }
        MobileBasePage mbPage = pARSubmit.fillWithDataAndSubmit(apptReqData);

        if (!(errorMessage.equalsIgnoreCase("null"))) {
            SubmissionConfirmationPage pSubconfirm = (SubmissionConfirmationPage) mbPage;
        } else {
            assertEquals(mbPage.getErrorMsg(), errorMessage, "Verify Error Message is present as expected");
        }

        //TODO fix close button xpath
        //pSubconfirm.clickClose();


        //TODO Add prac side and mail and secure message verification
/*

        Gmail gmail = new Gmail(pat.getGmailUName(), pat.getGmailPassword());

        Message[] msgs = gmail.findInNewMessages(pat.getEmailId(),"Appointment",currDate);

        System.out.println("test");
*/


    }

    private void logTestInfo(TestObject testObject) {

        log("INFO:: TESTCASE DETAILS - TestMethod : " + testObject.getTestMethod() + "| Environment : " + testObject.getTestEnv() + " | Browser : " + TestConfig.getBrowserType());
    }
}
