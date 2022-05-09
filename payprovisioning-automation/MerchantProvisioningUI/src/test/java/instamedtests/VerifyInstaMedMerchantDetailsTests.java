package instamedtests;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.InstaMedMerchantDetailsPage;
import pageobjects.MerchantSearchPage;
import provisioningtests.ProvisioningBaseTest;
import java.io.IOException;

public class VerifyInstaMedMerchantDetailsTests  extends ProvisioningBaseTest {

    @Test(enabled = true, groups = {"MerchantProvisioningAcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
    public void testVerifyMerchant() throws IOException, NullPointerException, InterruptedException {

        PropertyFileLoader testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
        merchantSearchPage.findByMerchantId(testData.getProperty("instamed.merchant.id.get"));
        merchantSearchPage.searchButtonClick();

        logStep("Going to click on view Merchant details page");
        merchantSearchPage.viewDetailsButtonClick();
        InstaMedMerchantDetailsPage instaMedMerchantDetailsPage = PageFactory.initElements(driver, InstaMedMerchantDetailsPage.class);

        logStep("Going to verify payment processor information");
        Assert.assertEquals(instaMedMerchantDetailsPage.getProcessorInformation(),"InstaMed");
        Assert.assertTrue(instaMedMerchantDetailsPage.verifyMmidPresent());

        logStep("Going to verify General Merchant Info");
        Assert.assertEquals(instaMedMerchantDetailsPage.getMerchantName(), testData.getProperty("instamed.merchant.name.get"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getPracticeID(),testData.getProperty("instamed.practice.id.get"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getAccountNumber(),testData.getProperty("instamed.account.number.get"));

        logStep("Going to verify InstaMed AccountDetails");
        Assert.assertEquals(instaMedMerchantDetailsPage.getMIDText(), testData.getProperty("instamed.mid"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getClientID(), testData.getProperty("instamed.clientid"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getClientSecret(),testData.getProperty("instamed.secret"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getStoreID(), testData.getProperty("instamed.storeid"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getTerminalIdPatientPortal(), testData.getProperty("instamed.terminalid.portal"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getTerminalIdPreCheck(), testData.getProperty("instamed.terminalid.precheck"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getTerminalIdVirtualVisits(),
                testData.getProperty("instamed.terminalid.virtualvisits"));

        logStep("Going to verify Rates and Contract Information");
        Assert.assertEquals(instaMedMerchantDetailsPage.getPlatformFeeAuth(), testData.getProperty("instamed.per.transaction.auth.fee.get"));

        logStep("Going to verify Percentage Fee Tiers");
        Assert.assertEquals(instaMedMerchantDetailsPage.getQTierQFee(), testData.getProperty("instamed.qtier.fee"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getMidTierQFee(), testData.getProperty("instamed.mtier.fee"));
        Assert.assertEquals(instaMedMerchantDetailsPage.getNonTierQFee(), testData.getProperty("instamed.ntier.fee"));
        Assert.assertNotNull(instaMedMerchantDetailsPage.getAmexFee());
        Assert.assertEquals(instaMedMerchantDetailsPage.getAmexFee(), testData.getProperty("instamed.amex.fee.get"));

    }
}
