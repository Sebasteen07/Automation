package com.intuit.ihg.product.mobile.test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.*;
import com.intuit.ihg.product.mobile.page.solutions.ccdviewer.CCDMessageDetailsPage;
import com.intuit.ihg.product.mobile.page.solutions.ccdviewer.CCDViewerDetailPage;
import com.intuit.ihg.product.mobile.page.solutions.ccdviewer.CCDViewerListPage;
import com.intuit.ihg.product.mobile.page.solutions.inbox.MessageInboxPage;
import com.intuit.ihg.product.mobile.utils.ExcelSheetUtil;
import com.intuit.ihg.product.mobile.utils.Filter;
import com.intuit.ihg.product.mobile.utils.TestObject;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/20/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileSmokeTestPlan extends BaseTestNGWebDriver {

    @DataProvider(name = "smokeData", parallel = true)
    public static Iterator<Object[]> fileDataProvider(Method m,

                                                      ITestContext testContext) throws Exception {
        Filter filter1 = Filter.equalsIgnoreCase(TestObject.TEST_METHOD,

                m.getName());
        Filter filter2 = Filter.equalsIgnoreCase(TestObject.TEST_ENV,

                TestConfig.getTestEnv());
        Filter filter = Filter.and(filter1, filter2);


        LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();

        classMap.put("TestObject", TestObject.class);

        Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(

                MobileSmokeTestPlan.class, classMap, "MobileTestData.csv", 0,

                null, filter);
          return it;

    }

    @Test(enabled = true, groups = {"SmokeTests", "testLogin"}, dataProvider = "smokeData")
    public void testMobileLogin(String url, String userName, String password, TestObject to) throws Exception {

        log("**INFO:: TestMobileLogin ");
        log("**INFO:: Environment on which Testcase is Running " + to.getTestEnv());
        log("**INFO:: Browser on which Testcase is Running " + TestConfig.getBrowserType());

        log("URL++++++++++++++++" + url);
        log("USER NAME++++++++++++++++" + userName);
        log("Password++++++++++++++++" + password);

        log("step 1:LogIn");
        MobileSignInPage mloginpage = new MobileSignInPage(driver, url);
        MobileHomePage pMyPatientPage = mloginpage.login(userName, password);

        pMyPatientPage.waitForlogoutLink(driver, 10);

        log("step 2:Assert Welcome text");
        assertTrue(verifyTextPresent(driver, "Welcome"));

        log("step 3:LogOut");
        pMyPatientPage.clickLogout();


    }

    @Test(enabled = true, groups = {"SmokeTests"}, dataProvider = "smokeData")
    public void testTabMyMessages(String url, String userName, String password, TestObject to) throws IOException, InterruptedException {

        IHGUtil.PrintMethodName();

        log("**INFO:: TestMobileLogin ");
        log("**INFO:: Environment on which Testcase is Running " + to.getTestEnv());
        log("**INFO:: Browser on which Testcase is Running " + TestConfig.getBrowserType());

        log("URL++++++++++++++++" + url);
        log("USER NAME++++++++++++++++" + userName);
        log("Password++++++++++++++++" + password);

        log("step 1:LogIn");
        MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, url);
        MobileHomePage pMyPatientPage = mobileSignInPage.login(userName, password);

        log("step 2: Click MyMessages");
        MessageInboxPage messageInboxPage = pMyPatientPage.clickMyMessages();

        log("step 3: Click Home");
        messageInboxPage.clickHome();

    }

    @Test(enabled = true, groups = {"SmokeTests"}, dataProvider = "smokeData")
    public void testTabAppointmentRequest(String url, String userName, String password, TestObject to) throws IOException, InterruptedException {

        IHGUtil.PrintMethodName();

        log("**INFO:: TestMobileLogin ");
        log("**INFO:: Environment on which Testcase is Running " + to.getTestEnv());
        log("**INFO:: Browser on which Testcase is Running " + TestConfig.getBrowserType());

        log("URL++++++++++++++++" + url);
        log("USER NAME++++++++++++++++" + userName);
        log("Password++++++++++++++++" + password);

        log("step 1:LogIn");
        MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, url);
        MobileHomePage pMyPatientPage = mobileSignInPage.login(userName, password);

        log("step 2: Click ApptReqTab");
        MobileBasePage mobileBasePage = pMyPatientPage.clickARLink();

        log("step 3: Click Home");
        mobileBasePage.clickHome();

      }


    @Test(enabled = true, groups = {"SmokeTests"}, dataProvider = "smokeData")
    public void testTabRxRenewal(String url, String userName, String password, TestObject to) throws IOException, InterruptedException {

        IHGUtil.PrintMethodName();

        log("**INFO:: TestMobileLogin ");
        log("**INFO:: Environment on which Testcase is Running " + to.getTestEnv());
        log("**INFO:: Browser on which Testcase is Running " + TestConfig.getBrowserType());

        log("URL++++++++++++++++" + url);
        log("USER NAME++++++++++++++++" + userName);
        log("Password++++++++++++++++" + password);

        log("step 1:LogIn");
        MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, url);
        MobileHomePage pMyPatientPage = mobileSignInPage.login(userName, password);

        log("step 2: Click RxRenewalTab");
        MobileBasePage mobileBasePage = pMyPatientPage.clickRXLink();

        log("step 3: Click Home");
        mobileBasePage.clickHome();
    }

    @Test(enabled = true, groups = {"SmokeTests"}, dataProvider = "smokeData")
    public void testTabAsk(String url, String userName, String password, TestObject to) throws IOException, InterruptedException {

        IHGUtil.PrintMethodName();

        log("**INFO:: TestMobileLogin ");
        log("**INFO:: Environment on which Testcase is Running " + to.getTestEnv());
        log("**INFO:: Browser on which Testcase is Running " + TestConfig.getBrowserType());

        log("URL++++++++++++++++" + url);
        log("USER NAME++++++++++++++++" + userName);
        log("Password++++++++++++++++" + password);

        log("step 1:LogIn");
        MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, url);
        MobileHomePage pMyPatientPage = mobileSignInPage.login(userName, password);

        log("step 2: Click AAQTab");
        MobileBasePage mobileBasePage = pMyPatientPage.clickAAQLink();

        log("step 3: Click Home");
        mobileBasePage.clickHome();

        IHGUtil.PrintMethodName();

    }

    @Test(enabled = true, groups = {"SmokeTests"}, dataProvider = "smokeData")
	public void testTabMakeAPayment(String url, String userName, String password, TestObject to) throws IOException, InterruptedException  {

        IHGUtil.PrintMethodName();

        log("**INFO:: TestMobileLogin ");
        log("**INFO:: Environment on which Testcase is Running " + to.getTestEnv());
        log("**INFO:: Browser on which Testcase is Running " + TestConfig.getBrowserType());

        log("URL++++++++++++++++" + url);
        log("USER NAME++++++++++++++++" + userName);
        log("Password++++++++++++++++" + password);

        log("step 1:LogIn");
        MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, url);
        MobileHomePage pMyPatientPage = mobileSignInPage.login(userName, password);

        log("step 2: Click MakeAPaymentTab");
        MobileBasePage mobileBasePage = pMyPatientPage.clickBillPayLink();

        log("step 3: Click Home");
        mobileBasePage.clickHome();

        IHGUtil.PrintMethodName();
	}

    @Test(enabled = true, groups = {"SmokeTests"}, dataProvider = "smokeData")
    public void testCCDViewer(String url, String userName, String password, TestObject to) throws IOException, InterruptedException {

        IHGUtil.PrintMethodName();

        log("**INFO:: TestMobile CCDViewer ");
        log("**INFO:: Environment on which Testcase is Running " + to.getTestEnv());
        log("**INFO:: Browser on which Testcase is Running " + TestConfig.getBrowserType());

        log("URL++++++++++++++++" + url);
        log("USER NAME++++++++++++++++" + userName);
        log("Password++++++++++++++++" + password);

        log("step 1:LogIn");
        MobileSignInPage mobileSignInPage = new MobileSignInPage(driver, url);
        MobileHomePage pMyPatientPage = mobileSignInPage.login(userName, password);

        log("step 2: Click MyMessages");
        MessageInboxPage messageInboxPage = pMyPatientPage.clickMyMessages();

        log("step 3: Open CCD Message");
        CCDMessageDetailsPage messageDetailsPage =(CCDMessageDetailsPage) messageInboxPage.clickMessage("New Health Information Import");

        log("step 4: Click Review Health Info");
        CCDViewerListPage msgViewerPage = messageDetailsPage.clickReviewHealthInfo();

        log("step 5: Click Health Overview tab");
        CCDViewerDetailPage ccdViewerDetailPage = msgViewerPage.clickHealthOverview();

        log("step 6: Assert content");
        ccdViewerDetailPage.assertHealthOverview("Blood Pressure","Height & Weight");
        ccdViewerDetailPage.assertHealthOverview("An adult's blood pressure is recommended to be 120/80","The recommended BMI range for an adult is 18-25");


    }
}
