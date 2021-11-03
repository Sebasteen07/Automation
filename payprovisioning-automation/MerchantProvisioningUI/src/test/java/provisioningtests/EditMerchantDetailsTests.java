package provisioningtests;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.EditRatesAndContractPage;
import pageobjects.GeneralMerchantInformationPage;
import pageobjects.MerchantDetailsPage;
import pageobjects.MerchantSearchPage;
import utils.MPUITestData;

import java.io.IOException;

public class EditMerchantDetailsTests extends ProvisioningBaseTest {

	@Test(dataProvider = "edit_settlement_type", dataProviderClass = MPUITestData.class, enabled = true)
	public void testEditSettlementType(String settlementType)
			throws IOException, NullPointerException, InterruptedException {

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

		logStep("Going to click on Edit rates & Contract");
		merchantDetailsPage.clickOnRatesNContractButton();

		logStep("Going to change settlemet type");
		EditRatesAndContractPage editRatesAndContractPage = PageFactory.initElements(driver,
				EditRatesAndContractPage.class);
		editRatesAndContractPage.editSettlementType(settlementType);

		System.out.println("" + settlementType);
		logStep("Going to verify merchant details page");
		MerchantDetailsPage merchantDetailsPageNew = PageFactory.initElements(driver, MerchantDetailsPage.class);
		merchantDetailsPageNew.verifyPageTitle();
		merchantDetailsPageNew.waitToCheckEditRatesContractButton();

		logStep("Going to verify settlement type after edit operation");
		merchantDetailsPageNew.verifySettlementType(settlementType);

	}


	@Test(dataProvider = "edit_general_merchant_info", dataProviderClass = MPUITestData.class, enabled = true)
	public void testEditGeneralMerchantInfo(String doingBusinessAs, String practiceID, String customerAccNo, String phoneNo)
			throws IOException, NullPointerException, InterruptedException {

		PropertyFileLoader testData = new PropertyFileLoader();
		logStep("Navigating to search Merchant");
		MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);

		merchantSearchPage.findByMerchantId(testData.getProperty("edit.merchant.id"));
		merchantSearchPage.searchButtonClick();

		logStep("Going to click on view Merchant details page");
		merchantSearchPage.viewDetailsButtonClick();

		logStep("Going to verify title of merchant details page");
		MerchantDetailsPage merchantDetailsPage = PageFactory.initElements(driver, MerchantDetailsPage.class);
		merchantDetailsPage.verifyPageTitle();

		logStep("Going to click on Edit General Merchant Info Button");
		GeneralMerchantInformationPage generalMerchantInformationPage;
		generalMerchantInformationPage = merchantDetailsPage.clickEditGeneralMerchantInfoButton();
		generalMerchantInformationPage.verifyFieldsOnEditGeneralMerchantInfoPage();

		logStep("Edit Doing Business Name");
		generalMerchantInformationPage.editDoingBusinessAs(doingBusinessAs);

		logStep("Edit Practice ID");
		generalMerchantInformationPage.editPracticeID(practiceID);

		logStep("Edit Customer Account Numer");
		generalMerchantInformationPage.editCustomerAccountNumber(customerAccNo);

		logStep("Edit Phone Number");
		generalMerchantInformationPage.editPhoneNumber(phoneNo);

		Assert.assertTrue(generalMerchantInformationPage.verifyOwnershipTypeIsDisabled());
		Assert.assertTrue(generalMerchantInformationPage.verifyBusinessType(), "Business Type Name is missing");
		Assert.assertTrue(generalMerchantInformationPage.verifySICMCCCode(), "SIC/MCC Code is missing");

		logStep("Click Update General Merchant Info Button");
		merchantDetailsPage = generalMerchantInformationPage.clickUpdateGeneralMerchantInfoButton();

		logStep("Going to verify merchant details page & verify updated information");
		merchantDetailsPage.verifyPageTitle();
		merchantDetailsPage.verifyGeneralMerchantInformation(practiceID, customerAccNo, doingBusinessAs);

	}

}