package com.medfusion.product.practice.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.practice.api.utils.PracticeConstants;

public class VirtualCardSwiperTest extends BaseTestNGWebDriver {
		// TODO move stuff around stepCounter to BaseTestNGWebDriver
		private int stepCounter = 0;

		private String swipeString = "";

		public void setSwipeString(String swipe) {
				swipeString = swipe;
		}

		private void logStep(String logText) {
				log("STEP " + ++stepCounter + ": " + logText);
		}

		public void virtualCardSwipeTest(WebDriver driver, PropertyFileLoader testData, String cardType) throws InterruptedException {
				logStep("Navigate to Login page");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());

				logStep("Enter credentials and login");
				PracticeHomePage practiceHome;
				if (cardType.equalsIgnoreCase(PracticeConstants.CARD_TYPE_VISA)) {
						practiceHome = practiceLogin.login(testData.getProperty("doctorLogin"), testData.getProperty("doctorPassword"));
				} else {
						practiceHome = practiceLogin.login(testData.getProperty("payPalDoctor"), testData.getProperty("payPalPassword"));
				}

				logStep("Navigate to Virtual Card Swiper page.");
				VirtualCardSwiperPage virtualCardSwiper = practiceHome.clickOnVirtualCardSwiper();

				logStep("Verify whether Virtual Card Swiper page is displayed.");
				assertTrue(virtualCardSwiper.checkVirtualCardSwiperPage(), "Virtual Card Swiper page is not displayed properly.");

				String amount = IHGUtil.createRandomNumericString().substring(0, 2);

				logStep("Add card info and click on 'Click Here To Charge Card' button.  ");
				if (swipeString.isEmpty()) {
						virtualCardSwiper
								.addCreditCardInfo(PracticeConstants.CARD_NAME, PracticeConstants.CARD_NUMBER, PracticeConstants.CARD_TYPE_VISA, PracticeConstants.EXP_MONTH,
										PracticeConstants.EXP_YEAR, amount, PracticeConstants.CVV, PracticeConstants.ZIP, PracticeConstants.PATIENT_ACCOUNT,
										PracticeConstants.PATIENT_NAME, PracticeConstants.COMMENT);
				} else {
						virtualCardSwiper
								.addCreditCardMandatoryInfo(PracticeConstants.CARD_NAME, PracticeConstants.CARD_NUM_MASTERCARD, PracticeConstants.CARD_TYPE_MASTERCARD,
										PracticeConstants.EXP_MONTH, PracticeConstants.EXP_YEAR, amount, PracticeConstants.ZIP, PracticeConstants.SWIPE_STRING_MASTERCARD);
				}
				logStep("Verify whether the payment is completed successfully.");
				assertEquals(virtualCardSwiper.getPaymentCompletedSuccessMsg().contains(PracticeConstants.PAYMENT_COMPLETED_SUCCESS_MSG), true,
						"The payment is completed properly.");

				stepCounter = 0; //TODO
		}

		/**
		 * @param driver
		 * @param testData
		 * @return
		 * @throws Exception
		 * @brief Returns the generated amount, formatted as $ amount displayed
		 */
		public String virtualCardSwipeTest(WebDriver driver, PropertyFileLoader testData) throws Exception {
				logStep("Navigate to Login page");
				PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getUrl());

				logStep("Enter credentials and login");
				PracticeHomePage practiceHome = practiceHome = practiceLogin.login(testData.getProperty("doctorLogin"), testData.getProperty("doctorPassword"));

				logStep("Navigate to Virtual Card Swiper page.");
				VirtualCardSwiperPage virtualCardSwiper = practiceHome.clickOnVirtualCardSwiper();

				logStep("Verify whether Virtual Card Swiper page is displayed.");
				assertTrue(virtualCardSwiper.checkVirtualCardSwiperPage(), "Virtual Card Swiper page is not displayed properly.");

				String amount = IHGUtil.createRandomNumericString().substring(0, 2);

				logStep("Add card info and click on 'Click Here To Charge Card' button.  ");
				if (swipeString.isEmpty()) {
						virtualCardSwiper
								.addCreditCardInfo(PracticeConstants.CARD_NAME, PracticeConstants.CARD_NUMBER, PracticeConstants.CARD_TYPE_VISA, PracticeConstants.EXP_MONTH,
										PracticeConstants.EXP_YEAR, amount, PracticeConstants.CVV, PracticeConstants.ZIP, PracticeConstants.PATIENT_ACCOUNT,
										PracticeConstants.PATIENT_NAME, PracticeConstants.COMMENT);
				} else {
						virtualCardSwiper
								.addCreditCardMandatoryInfo(PracticeConstants.CARD_NAME, PracticeConstants.CARD_NUM_MASTERCARD, PracticeConstants.CARD_TYPE_MASTERCARD,
										PracticeConstants.EXP_MONTH, PracticeConstants.EXP_YEAR, amount, PracticeConstants.ZIP, PracticeConstants.SWIPE_STRING_MASTERCARD);
				}
				logStep("Verify whether the payment is completed successfully.");
				assertEquals(virtualCardSwiper.getPaymentCompletedSuccessMsg().contains(PracticeConstants.PAYMENT_COMPLETED_SUCCESS_MSG), true,
						"The payment is completed properly.");

				stepCounter = 0; //TODO
				return IHGUtil.formatNumber(Integer.parseInt(amount) * 100);
		}
}
