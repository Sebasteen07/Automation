package com.intuit.ihg.product.sitegen;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.medfusion.common.utils.PropertyFileLoader;

import org.openqa.selenium.WebDriver;

public class SiteGenSteps extends BaseTestNGWebDriver {

	public static void logSGLoginInfo(PropertyFileLoader testData) {
		Log4jUtil.log("URL: " + testData.getProperty("sitegenUrl"));		
	}

	public SiteGenPracticeHomePage logInUserToSG(WebDriver driver, String login, String password) throws Exception {
		return logInUserToSG(driver, login, password, "");
	}
	//TODO breadcrumb check if this is in any use other than the local-run tests that require manual credentials, rewrite to only use test class propertyFileLoader
	public SiteGenPracticeHomePage logInUserToSG(WebDriver driver, String login, String password, String practiceName) throws Exception {
		log("step 1: Get Data from Excel ##########");
		PropertyFileLoader testData = new PropertyFileLoader();		
		
		log("Step 2: Opening sitegen home page");
		SiteGenLoginPage sloginPage = new SiteGenLoginPage(driver, testData.getProperty("sitegenUrl"));
		SiteGenHomePage sHomePage = sloginPage.login(login, password);

		log("step 3: navigate to SiteGen PracticeHomePage ##########");
		SiteGenPracticeHomePage pSiteGenPracticeHomePage;
		if (practiceName.isEmpty()) {
			// log in as normal user
			pSiteGenPracticeHomePage = sHomePage.clickLinkMedfusionSiteAdministration();
		} else {
			// log in as SuperUser
			pSiteGenPracticeHomePage = sHomePage.searchPracticeFromSGAdmin(practiceName);
		}
		assertTrue(pSiteGenPracticeHomePage.isSearchPageLoaded(), "Expected the SiteGen Practice HomePage  to be loaded, but it was not.");
		return pSiteGenPracticeHomePage;
	}

}
