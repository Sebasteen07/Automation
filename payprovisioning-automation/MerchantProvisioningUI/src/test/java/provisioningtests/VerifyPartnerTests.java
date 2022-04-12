package provisioningtests;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pageobjects.MerchantDetailsPage;
import pageobjects.MerchantSearchPage;
import pageobjects.PartnerPage;

import java.io.IOException;

public class VerifyPartnerTests extends ProvisioningBaseTest{

    protected static String partnerName;

    @Test
    public void testVerifyAddPartnerToMerchant() throws IOException, NullPointerException, InterruptedException {

        PropertyFileLoader testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);

        merchantSearchPage.findByMerchantId(testData.getProperty("merchant.id.settlement.edit"));
        merchantSearchPage.searchButtonClick();

        logStep("Going to click on view Merchant details page");
        merchantSearchPage.viewDetailsButtonClick();

        logStep("Going to verify title of merchant details page");
        MerchantDetailsPage merchantDetailsPage = PageFactory.initElements(driver, MerchantDetailsPage.class);
        merchantDetailsPage.verifyPageTitle();

        logStep("Click on edit partner link");
        PartnerPage partnerPage = merchantDetailsPage.clickOnEditpartner();

        logStep("Navigating to partners page");
        partnerPage.verifyNavigatedToPartnerPage();

        logStep("Click Add partner button");
        partnerPage.clickOnAddPartner();

        logStep("Click Enter username & click on Add button");
        partnerPage.addUsernameClickOnAdd();

        logStep("Verifying added partner");
        partnerPage.verifyAddPartner();

        logStep("Click on delete button");
        partnerPage.clickOnDelete();

        logStep("Click on delete confirmation button");
        partnerPage.clickOnDeleteDialogue();

        logStep("Verify delete partner");
        partnerPage.verifyDeletePartner();
    }

    @Test
    public void testVerifyEditPartnerOfMerchant() throws IOException, NullPointerException, InterruptedException {

        PropertyFileLoader testData = new PropertyFileLoader();
        logStep("Navigating to search Merchant");
        MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);

        merchantSearchPage.findByMerchantId(testData.getProperty("merchant.id.settlement.edit"));
        merchantSearchPage.searchButtonClick();

        logStep("Going to click on view Merchant details page");
        merchantSearchPage.viewDetailsButtonClick();

        logStep("Going to verify title of merchant details page");
        MerchantDetailsPage merchantDetailsPage = PageFactory.initElements(driver, MerchantDetailsPage.class);
        merchantDetailsPage.verifyPageTitle();

        logStep("Click on edit partner link");
        PartnerPage partnerPage = merchantDetailsPage.clickOnEditpartner();

        logStep("Navigating to partners page");
        partnerPage.verifyNavigatedToPartnerPage();

        logStep("Click Add partner button");
        partnerPage.clickOnAddPartner();

        logStep("Enter username & click on Add button");
        partnerPage.addUsernameClickOnAdd();

        logStep("Verifying added partner");
        partnerPage.verifyAddPartner();

        logStep("Click on edit partner Button");
        partnerPage.clickOnEditButton();

        logStep("Edit username & click on edit button");
        partnerPage.editUsernameClickOnEdit();

        logStep("verify username got edited correctly");
        partnerPage.verifyEditedPartner();

        logStep("Click on delete button");
        partnerPage.clickOnDelete();

        logStep("Click on delete confirmation button");
        partnerPage.clickOnDeleteDialogue();

        logStep("Verify partner deleted message");
        partnerPage.verifyDeletePartner();

    }

}
