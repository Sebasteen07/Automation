package instamedtests;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.InstaMedMerchantDetailsPage;
import pageobjects.MerchantSearchPage;
import provisioningtests.ProvisioningBaseTest;

import java.io.IOException;

public class VerifyInstaMedMerchantDetailsTests  extends ProvisioningBaseTest {

    @Test
    public void testVerifyMerchant() throws IOException, NullPointerException, InterruptedException {

        PropertyFileLoader testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
        merchantSearchPage.findByMerchantId(testData.getProperty("instamed.merchant.id.get"));
        merchantSearchPage.searchButtonClick();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        logStep("Going to click on view Merchant details page");
        merchantSearchPage.viewDetailsButtonClick();
        InstaMedMerchantDetailsPage instaMedMerchantDetailsPage = PageFactory.initElements(driver, InstaMedMerchantDetailsPage.class);

        logStep("Going to verify payment processor information");
        Assert.assertEquals(instaMedMerchantDetailsPage.getProcessorInformation(),"InstaMed");
        Assert.assertTrue(instaMedMerchantDetailsPage.verifyMmidPresent());

        logStep("Going to verify General Merchant Info");
        Assert.assertEquals(instaMedMerchantDetailsPage.getMerchantName(),"InstaMed - Pay");
        Assert.assertEquals(instaMedMerchantDetailsPage.getPracticeID(),"24109");
        Assert.assertEquals(instaMedMerchantDetailsPage.getAccountNumber(),"12345");

        logStep("Going to verify InstaMed AccountDetails");
        Assert.assertEquals(instaMedMerchantDetailsPage.getMIDText(),"10212021");
        Assert.assertEquals(instaMedMerchantDetailsPage.getClientID(),"34cbb6dd5c6f405fa73c3e8abe54e34f");
        Assert.assertEquals(instaMedMerchantDetailsPage.getClientSecret(),"PbuTeWqEmD+b1uFM");
        Assert.assertEquals(instaMedMerchantDetailsPage.getStoreID(),"0001");
        Assert.assertEquals(instaMedMerchantDetailsPage.getTerminalIdPatientPortal(),"0001");
        Assert.assertEquals(instaMedMerchantDetailsPage.getTerminalIdPreCheck(),"0001");
        Assert.assertEquals(instaMedMerchantDetailsPage.getTerminalIdVirtualVisits(),"0001");

        logStep("Going to verify Rates and Contract Information");
        Assert.assertEquals(instaMedMerchantDetailsPage.getPlatformFeeAuth(),"$ 10");

        logStep("Going to verify Percentage Fee Tiers");
        Assert.assertEquals(instaMedMerchantDetailsPage.getQTierQFee(),"10");
        Assert.assertEquals(instaMedMerchantDetailsPage.getMidTierQFee(),"10");
        Assert.assertEquals(instaMedMerchantDetailsPage.getNonTierQFee(),"10");

    }
}
