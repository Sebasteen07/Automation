package provisioningtests;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.AddNewMerchantPage;
import pageobjects.MerchantDetailsPage;
import pageobjects.NavigationMenu;
import utils.MerchantEntity;
import java.io.IOException;

@Listeners(listenerpackage.Listener.class)

public class AddMerchantTest extends ProvisioningBaseTest{

    protected PropertyFileLoader testData;

    final static private MerchantEntity merchantEntityElement = new MerchantEntity("Element",
            "Test Element Merchant-"+ IHGUtil.createRandomNumericString(3),
            "Practice"+IHGUtil.createRandomNumericString(3), "43215",
            "Test Pay Customer", "DAILY");

    final static private MerchantEntity merchantEntityPaypal = new MerchantEntity("Paypal",
            "Test Paypal Merchant-"+ IHGUtil.createRandomNumericString(3),
            "54321", "43215", "1000",
            "Medfusion", "TestUserPaypal", "AutomationUser");

    @Test
    public void testAddPaypalMerchant() throws IOException, InterruptedException {

        testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        NavigationMenu navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);

        logStep("Open Add Merchant Page");
        Thread.sleep(2000);
        AddNewMerchantPage addNewMerchantPage = navigationMenu.openAddMerchantPage();

        logStep("Create Element Merchant");
        addNewMerchantPage.addMerchant("Paypal", merchantEntityPaypal,
                testData.getProperty("paypal.card.not.present.password"),
                testData.getProperty("paypal.password"));

        log("Click Create Merchant Button");
        MerchantDetailsPage merchantDetailsPage = addNewMerchantPage.clickCreateMerchantButton();

        logStep("Get the mmid generated for the above merchant");
        log(merchantDetailsPage.getMMID());
        log("Merchant successfully created");
    }

    @Test
    public void testAddElementMerchant() throws IOException, InterruptedException {

        testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        NavigationMenu navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);

        logStep("Open Add Merchant Page");
        Thread.sleep(2000);
        AddNewMerchantPage addNewMerchantPage = navigationMenu.openAddMerchantPage();

        logStep("Create Element Merchant");
        log("Select Element Merchant");
        addNewMerchantPage.selectVendorType("Element");
        log("Fill General Merchant Information");
        addNewMerchantPage.fillGeneralMerchantInformation(merchantEntityElement, testData.getProperty("customer.account.number"),
                testData.getProperty("phone.number"), testData.getProperty("transaction.limit"),
                testData.getProperty("billing.descriptor"), testData.getProperty("ownership.type"),
                testData.getProperty("business.type"), testData.getProperty("sic.mcc.code"),
                testData.getProperty("estb.date"), testData.getProperty("website.url"));

        log("Fill Customer Contact Information");
        addNewMerchantPage.fillCustomerContact(testData.getProperty("customer.first.name"), testData.getProperty("customer.last.name"),
                testData.getProperty("customer.email"), testData.getProperty("phone.number"));

        log("Fill Merchant Address Information");
        addNewMerchantPage.fillMerchantAddressInformation(testData.getProperty("merchant.address.line1"), testData.getProperty("merchant.city"),
                testData.getProperty("merchant.state"), testData.getProperty("merchant.zip"), testData.getProperty("country"));
        addNewMerchantPage.fillRemitToAddressIfTrue(false);

        log("Fill Legal Entity");
        addNewMerchantPage.fillLegalEntityName(testData.getProperty("customer.first.name"));

        log("Fill Beneficial Owners Info");
        addNewMerchantPage.fillBeneficialOwnerInformation(10, testData.getProperty("customer.first.name"),
                testData.getProperty("customer.last.name"), "5", testData.getProperty("merchant.address.line1"),
                testData.getProperty("merchant.city"), testData.getProperty("merchant.state"), testData.getProperty("merchant.zip"),
                testData.getProperty("country"), testData.getProperty("beneficial.ownership.type"));

        log("Fill Bank Account Info");
        addNewMerchantPage.fillBankAccountDetails(testData.getProperty("account.usage.combined"), testData.getProperty("account.type"),
                testData.getProperty("routing.number"), testData.getProperty("bank.account.number"),
                testData.getProperty("federal.tax.id"));

        log("Fill Rates and Fees Info");
        addNewMerchantPage.fillRatesAndFees(testData.getProperty("perTransactionAuthFee"), testData.getProperty("perTransactionRefundFee"),
                testData.getProperty("qTierUpper"), testData.getProperty("qTierFee"),
                testData.getProperty("mTierUpper"), testData.getProperty("mTierFee"),
                testData.getProperty("nonQTierUpper"), testData.getProperty("nonQTierFee"));

        log("Fill Fee Settlement Type");
        addNewMerchantPage.selectFeeSettlementType(merchantEntityElement.getFeeSettlementType());

        log("Click Create Merchant Button");
        MerchantDetailsPage merchantDetailsPage = addNewMerchantPage.clickCreateMerchantButton();

        logStep("Get the mmid generated for the above merchant");
        log(merchantDetailsPage.getMMID());
        log("Merchant successfully created");
    }
}
