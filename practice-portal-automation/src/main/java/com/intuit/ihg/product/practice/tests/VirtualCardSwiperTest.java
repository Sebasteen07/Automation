package com.intuit.ihg.product.practice.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;

public class VirtualCardSwiperTest extends BaseTestNGWebDriver{
	

	private String swipeString="";
	
	public void setSwipeString(String swipe) {
		swipeString= swipe;
	}

	public void virtualCardSwipeTest(WebDriver driver, PracticeTestData practiceTestData, String cardType) throws Exception {
		
		log("Test Case: TestLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");	

		log("step 2: Navigate to Login page"); 
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());

		log("step 3: Enter credentials and login");
		PracticeHomePage practiceHome;
		if (cardType.equalsIgnoreCase("Visa")){
			practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());
		} else {
			practiceHome = practiceLogin.login(practiceTestData.getPayPalDoctor(), practiceTestData.getPayPalPassword());
		}

		log("Step 4 : Navigate to Virtual Card Swiper page.");
		VirtualCardSwiperPage virtualCardSwiper = practiceHome.clickOnVirtualCardSwiper();

		log("verify whether Virtual Card Swiper page is displayed.");
		verifyTrue(virtualCardSwiper.checkVirtualCardSwiperPage(), "Virtual Card Swiper page is not displayed properly.");

		String Amount = IHGUtil.createRandomNumericString().substring(0,2);

		log(" Step 5 : Add card info and click on 'Click Here To Charge Card' button.  ");
		if (swipeString.isEmpty()){
			virtualCardSwiper.addCreditCardInfo(PracticeConstants.ccName, PracticeConstants.ccNum, PracticeConstants.cardType, PracticeConstants.expMonth, PracticeConstants.expYear, 
					Amount, PracticeConstants.cvv, PracticeConstants.zip, PracticeConstants.comment);
		} else {
			virtualCardSwiper.addCreditCardMandatoryInfo(PracticeConstants.ccName, PracticeConstants.ccNumMasterCard, PracticeConstants.cardTypeMaster, PracticeConstants.expMonth, PracticeConstants.expYear, Amount, PracticeConstants.zip, PracticeConstants.swipeStringMaster);
		}
		log("Verify whether the payment is completed successfully.");
		verifyEquals(virtualCardSwiper.getPayementCompletedSuccessMsg().contains(PracticeConstants.paymentCompletedSuccessMsg),
				true, "The payment is completed properly.");
		
		
	}
	
 
}
