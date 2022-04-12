package instamedtests;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pageobjects.AddInstaMedMerchantPage;
import pageobjects.AddNewMerchantPage;
import pageobjects.MerchantDetailsPage;
import pageobjects.NavigationMenu;
import provisioningtests.ProvisioningBaseTest;

import java.io.IOException;

public class CreateInstaMedMerchant extends ProvisioningBaseTest {

    protected PropertyFileLoader testData;

    @Test
    public void testAddInstaMedMerchant() throws IOException, InterruptedException {

        testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        NavigationMenu navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);

        logStep("Open Add Merchant Page");
        Thread.sleep(2000);
        AddNewMerchantPage addNewMerchantPage = navigationMenu.openAddMerchantPage();
        AddInstaMedMerchantPage addInstaMedMerchantPage = addNewMerchantPage.pageForAddingNewInstaMedMerchant("InstaMed");

        logStep("Create Element Merchant");
//        addInstaMedMerchantPage.addMerchant("InstaMed",
//                "Merchant - IM",
//                "12345",
//                "43254",
//                "clientid",
//                "secret",
//                "10212021",
//                "0001",
//                "0001",
//                "0001",
//                "0001",
//                "1",
//                "2",
//                "3",
//                "4");

        addInstaMedMerchantPage.addVendorAndGeneralMerchantInfo("InstaMed",
                "Merchant - IM",
                "12345",
                "43254");
        addInstaMedMerchantPage.addPaymentProcessorInformation("clientid",
                "secret",
                "10212021",
                "0001");
        addInstaMedMerchantPage.addTerminalInformation("0001",
                "0001",
                "0001");
        addInstaMedMerchantPage.addRatesAndFees("1",
                "2",
                "3",
                "4");
        addInstaMedMerchantPage.selectAcceptedCards(1);

        log("Click Create Merchant Button");
        MerchantDetailsPage merchantDetailsPage = addInstaMedMerchantPage.clickCreateMerchantButton();

        logStep("Get the mmid generated for the above merchant");
        log(merchantDetailsPage.getMMID());
        log("Merchant successfully created");
    }

}
