package instamedtests;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.AddInstaMedMerchantPage;
import pageobjects.AddNewMerchantPage;
import pageobjects.MerchantDetailsPage;
import pageobjects.NavigationMenu;
import provisioningtests.ProvisioningBaseTest;

import java.io.IOException;

public class CreateInstaMedMerchant extends ProvisioningBaseTest {

    protected PropertyFileLoader testData;

    @Test(enabled = true, groups = {"MerchantProvisioningAcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
    public void testAddInstaMedMerchant() throws IOException, InterruptedException {

        testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        NavigationMenu navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);

        logStep("Open Add Merchant Page");
        Thread.sleep(2000);
        AddNewMerchantPage addNewMerchantPage = navigationMenu.openAddMerchantPage();
        AddInstaMedMerchantPage addInstaMedMerchantPage = addNewMerchantPage.pageForAddingNewInstaMedMerchant("InstaMed");

        logStep("Select Preferred Vendor and add General Merchant Information");
         addInstaMedMerchantPage.addVendorAndGeneralMerchantInfo("InstaMed",
                testData.getProperty("instamed.merchant.name") + " - " + IHGUtil.createRandomNumericString(3),
                testData.getProperty("instamed.practice.id"),
                testData.getProperty("instamed.customer.account"));

        logStep("Add Payment Processor Information");
        addInstaMedMerchantPage.addPaymentProcessorInformation(
                testData.getProperty("instamed.clientid"),
                testData.getProperty("instamed.secret"),
                testData.getProperty("instamed.mid"),
                testData.getProperty("instamed.storeid"));

        logStep("Add Terminal Information");
        addInstaMedMerchantPage.addTerminalInformation(
                testData.getProperty("instamed.terminalid.portal"),
                testData.getProperty("instamed.terminalid.precheck"),
                testData.getProperty("instamed.terminalid.virtualvisits"));

        logStep("Add Rates and Fees Information");
        addInstaMedMerchantPage.addRatesAndFees(
                testData.getProperty("instamed.perTransactionAuthFee"),
                testData.getProperty("instamed.qTierFee"),
                testData.getProperty("instamed.mTierFee"),
                testData.getProperty("instamed.nTierFee"));

        logStep("Select Visa card");
        addInstaMedMerchantPage.selectAnAcceptedCard(1);

        log("Click Create Merchant Button");
        MerchantDetailsPage merchantDetailsPage = addInstaMedMerchantPage.clickCreateMerchantButton();

        logStep("Get the mmid generated for the above merchant");
        log(merchantDetailsPage.getMMID());
        log("Merchant successfully created");
    }

    @Test(enabled = true, groups = {"MerchantProvisioningAcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
    public void testAddInstaMedMerchantValidationMessages() throws IOException, InterruptedException {

        testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        NavigationMenu navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);

        logStep("Open Add Merchant Page");
        Thread.sleep(2000);
        AddNewMerchantPage addNewMerchantPage = navigationMenu.openAddMerchantPage();
        AddInstaMedMerchantPage addInstaMedMerchantPage = addNewMerchantPage.pageForAddingNewInstaMedMerchant("InstaMed");
        addInstaMedMerchantPage.selectVendorType("InstaMed");

        log("Click Create Merchant Button so all the validation messages are shown");
        addInstaMedMerchantPage.createMerchantButton();

        Assert.assertEquals("Field is required.",
                addInstaMedMerchantPage.merchantNameMandatoryValidation(), "Merchant Name NOT is mandatory");

        Assert.assertEquals("Field is required.",
                addInstaMedMerchantPage.customerAccountNumberMandatoryValidation(), "Customer Account Number NOT is mandatory");

        Assert.assertEquals("Field is required.",
                addInstaMedMerchantPage.midMandatoryValidation(), "MID NOT is mandatory");

        Assert.assertEquals("Field is required.",
                addInstaMedMerchantPage.storeIDMandatoryValidation(), "Store ID NOT is mandatory");

        Assert.assertEquals("You must enter at least one terminal details",
                addInstaMedMerchantPage.terminalInformationMandatoryValidation(), "Atleast one terminal ID NOT is mandatory");

        Assert.assertEquals("You must select at least one card from the list!",
                addInstaMedMerchantPage.acceptedCardsMandatoryValidation(), "Accepted Cards NOT is mandatory");

        Assert.assertEquals("Field is required.",
                addInstaMedMerchantPage.perTransactionAuthFeeMandatoryValidation(), "Per Transaction Auth Fee NOT is mandatory");

        Assert.assertEquals("Field is required.",
                addInstaMedMerchantPage.qualifiedTierFeeMandatoryValidation(), "Qualified Tier Fee NOT is mandatory");

        Assert.assertEquals("Field is required.",
                addInstaMedMerchantPage.midQualifiedTierMandatoryValidation(), "Mid Qualified Tier Fee NOT is mandatory");

        Assert.assertEquals("Field is required.",
                addInstaMedMerchantPage.nonQualifiedTierMandatoryValidation(), "Non Qualified Tier Fee NOT is mandatory");

    }

    @Test(enabled = true, groups = {"MerchantProvisioningAcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
    public void testAmexCardValidation() throws IOException, InterruptedException {

        testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        NavigationMenu navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);

        logStep("Open Add Merchant Page");
        AddNewMerchantPage addNewMerchantPage = navigationMenu.openAddMerchantPage();
        AddInstaMedMerchantPage addInstaMedMerchantPage = addNewMerchantPage.pageForAddingNewInstaMedMerchant("InstaMed");
        addInstaMedMerchantPage.selectVendorType("InstaMed");

        logStep("Add Amex card and check amex mandatory message");
        Assert.assertTrue(addInstaMedMerchantPage.isAmexFeeErrorPresent());

    }

}
