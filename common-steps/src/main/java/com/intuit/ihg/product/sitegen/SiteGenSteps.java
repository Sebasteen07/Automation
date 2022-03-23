//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
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
		Log4jUtil.log("URL: " + testData.getProperty("sitegen.url"));
	}

	public SiteGenPracticeHomePage logInUserToSG(WebDriver driver, String login, String password) throws Exception {
		logStep("Get Data from PropertyFile");
		PropertyFileLoader testData = new PropertyFileLoader();

		logStep("Opening SiteGen home page");
		SiteGenLoginPage sloginPage = new SiteGenLoginPage(driver, testData.getProperty("sitegen.url"));
		SiteGenHomePage sHomePage = sloginPage.login(login, password);

		logStep("Navigate to SiteGen PracticeHomePage");
		return sHomePage.clickLinkMedfusionSiteAdministration();
	}

}
