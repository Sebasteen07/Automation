//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.practice.tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPageChargeHistory;
import com.medfusion.product.practice.api.pojo.PracticeTestData;

public class RecivePayNowTest extends BaseTestNGWebDriver {



	public void PayNowVerify(WebDriver driver, PracticeTestData practiceTestData, String amount) throws Exception {

		log("Test Case: PayNow Verification");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getPracticeUsername(), practiceTestData.getPracticePassword());

		log("step 2: Click on Virtual card swiper");
		VirtualCardSwiperPage pVirtualCardSwiperTab = practiceHome.clickVirtualCardSwiperTab();
		log("Step3: Click on Charge History");
		VirtualCardSwiperPageChargeHistory pVirtualCardSwiperPageChargeHistory = pVirtualCardSwiperTab.lnkChargeHistoryclick(driver);
		pVirtualCardSwiperPageChargeHistory.SearchPayment(1);
		log("Step 4: Verify payment recieved");
		assertTrue(pVirtualCardSwiperPageChargeHistory.VerifyAmount(amount));

	}
}
