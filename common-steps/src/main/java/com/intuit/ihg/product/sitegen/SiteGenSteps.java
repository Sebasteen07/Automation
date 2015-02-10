package com.intuit.ihg.product.sitegen;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.intuit.ihg.product.sitegen.utils.Sitegen;
import com.intuit.ihg.product.sitegen.utils.SitegenTestData;
import org.openqa.selenium.WebDriver;

/**
 * Created by Adam W on 10.2.2015.
 */
public class SiteGenSteps extends BaseTestNGWebDriver {

    public static void logSGLoginInfo(SitegenTestData testData) {
        Log4jUtil.log("URL: " + testData.getSiteGenUrl());
        Log4jUtil.log("Username: " + testData.getAutomationUser());
        Log4jUtil.log("Password: " + testData.getAutomationUserPassword());
    }

    public SiteGenPracticeHomePage logInSpecificAdminToSG(WebDriver driver, String login, String password) throws Exception {
        log("step 1: Get Data from Excel ##########");
        Sitegen sitegen = new Sitegen();
        SitegenTestData testcasesData = new SitegenTestData(sitegen);
        logSGLoginInfo(testcasesData);

        log("Step 2: Opening sitegen home page");
        SiteGenLoginPage sloginPage= new SiteGenLoginPage (driver, testcasesData.getSiteGenUrl());
        SiteGenHomePage sHomePage = sloginPage.login(login, password);

        log("step 3: navigate to SiteGen PracticeHomePage ##########");
        SiteGenPracticeHomePage pSiteGenPracticeHomePage = sHomePage.clickLinkMedfusionSiteAdministration();
        assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(),
                "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
        return pSiteGenPracticeHomePage;
    }

}
