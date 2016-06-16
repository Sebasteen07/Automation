package com.medfusion.product.practice.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.practice.api.pojo.PracticeTestData;
import com.medfusion.product.practice.api.utils.PracticeConstants;

public class VirtualCardSwiperTest extends BaseTestNGWebDriver {

    private String swipeString = "";

    public void setSwipeString(String swipe) {
        swipeString = swipe;
    }

    public void virtualCardSwipeTest(WebDriver driver, PracticeTestData practiceTestData, String cardType)
            throws InterruptedException {

        log("Test Case: TestLoginLogout");
        log("Execution Environment: " + IHGUtil.getEnvironmentType());
        log("Execution Browser: " + TestConfig.getBrowserType());

        log("step 1: Get Data from Excel");

        log("step 2: Navigate to Login page");
        PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());

        log("step 3: Enter credentials and login");
        PracticeHomePage practiceHome;
        if (cardType.equalsIgnoreCase("Visa")) {
            practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());
        } else {
            practiceHome = practiceLogin.login(practiceTestData.getPayPalDoctor(),
                    practiceTestData.getPayPalPassword());
        }

        log("Step 4 : Navigate to Virtual Card Swiper page.");
        VirtualCardSwiperPage virtualCardSwiper = practiceHome.clickOnVirtualCardSwiper();

        log("verify whether Virtual Card Swiper page is displayed.");
        assertTrue(virtualCardSwiper.checkVirtualCardSwiperPage(),
                "Virtual Card Swiper page is not displayed properly.");

        String amount = IHGUtil.createRandomNumericString().substring(0, 2);

        log(" Step 5 : Add card info and click on 'Click Here To Charge Card' button.  ");
        if (swipeString.isEmpty()) {
            virtualCardSwiper.addCreditCardInfo(PracticeConstants.ccName, PracticeConstants.ccNum,
                    PracticeConstants.cardType, PracticeConstants.expMonth, PracticeConstants.expYear, amount,
                    PracticeConstants.cvv, PracticeConstants.zip, PracticeConstants.patientAccount,
                    PracticeConstants.patientName, PracticeConstants.comment);
        } else {
            virtualCardSwiper.addCreditCardMandatoryInfo(PracticeConstants.ccName, PracticeConstants.ccNumMasterCard,
                    PracticeConstants.cardTypeMaster, PracticeConstants.expMonth, PracticeConstants.expYear, amount,
                    PracticeConstants.zip, PracticeConstants.swipeStringMaster);
        }
        log("Verify whether the payment is completed successfully.");
        assertEquals(virtualCardSwiper.getPaymentCompletedSuccessMsg()
                .contains(PracticeConstants.paymentCompletedSuccessMsg), true, "The payment is completed properly.");

    }

    /**
     * @brief Returns the generated amount, formatted as $ amount displayed
     * @param driver
     * @param practiceTestData
     * @return
     * @throws Exception
     */
    public String virtualCardSwipeTest(WebDriver driver, PracticeTestData practiceTestData) throws Exception {

        log("Test Case: TestLoginLogout");
        log("Execution Environment: " + IHGUtil.getEnvironmentType());
        log("Execution Browser: " + TestConfig.getBrowserType());

        log("step 1: Get Data from Excel");

        log("step 2: Navigate to Login page");
        PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());

        log("step 3: Enter credentials and login");
        PracticeHomePage practiceHome;
        practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

        log("Step 4 : Navigate to Virtual Card Swiper page.");
        VirtualCardSwiperPage virtualCardSwiper = practiceHome.clickOnVirtualCardSwiper();

        log("verify whether Virtual Card Swiper page is displayed.");
        verifyTrue(virtualCardSwiper.checkVirtualCardSwiperPage(),
                "Virtual Card Swiper page is not displayed properly.");

        String amount = IHGUtil.createRandomNumericString().substring(0, 2);

        log(" Step 5 : Add card info and click on 'Click Here To Charge Card' button.  ");
        if (swipeString.isEmpty()) {
            virtualCardSwiper.addCreditCardInfo(PracticeConstants.ccName, PracticeConstants.ccNum,
                    PracticeConstants.cardType, PracticeConstants.expMonth, PracticeConstants.expYear, amount,
                    PracticeConstants.cvv, PracticeConstants.zip, PracticeConstants.patientAccount,
                    PracticeConstants.patientName, PracticeConstants.comment);
        } else {
            virtualCardSwiper.addCreditCardMandatoryInfo(PracticeConstants.ccName, PracticeConstants.ccNumMasterCard,
                    PracticeConstants.cardTypeMaster, PracticeConstants.expMonth, PracticeConstants.expYear, amount,
                    PracticeConstants.zip, PracticeConstants.swipeStringMaster);
        }
        log("Verify whether the payment is completed successfully.");
        verifyEquals(virtualCardSwiper.getPaymentCompletedSuccessMsg()
                .contains(PracticeConstants.paymentCompletedSuccessMsg), true, "The payment is completed properly.");
        return IHGUtil.formatNumber(Integer.parseInt(amount) * 100);

    }

}
