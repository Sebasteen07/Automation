package provisioningtests;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.*;
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

		Assert.assertTrue(generalMerchantInformationPage.isOwnershipTypeDisabled());
		Assert.assertTrue(generalMerchantInformationPage.verifyBusinessType(), "Business Type Name is missing");
		Assert.assertTrue(generalMerchantInformationPage.verifySICMCCCode(), "SIC/MCC Code is missing");

		logStep("Click Update General Merchant Info Button");
		merchantDetailsPage = generalMerchantInformationPage.clickUpdateGeneralMerchantInfoButton();

		logStep("Going to verify merchant details page & verify updated information");
		merchantDetailsPage.verifyPageTitle();
		merchantDetailsPage.verifyGeneralMerchantInformation(practiceID, customerAccNo, doingBusinessAs);

	}

	@Test
	public void testAddRemoveBeneficialOwner() throws IOException, InterruptedException {
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
		AddBeneficialOwnerPage addBeneficialOwnerPage;
		addBeneficialOwnerPage = merchantDetailsPage.clickAddNewBeneficialOwnerButton();
		Assert.assertTrue(addBeneficialOwnerPage.verifyFieldsOnEditAddbeneficialOwnerPage());

		logStep("Fill in Add Beneficial Owner Details");
		addBeneficialOwnerPage.editFirstName(testData.getProperty("customer.first.name"));
		addBeneficialOwnerPage.editLastName(testData.getProperty("customer.last.name"));
		addBeneficialOwnerPage.editPercentOwned(testData.getProperty("percent.owned"));
		addBeneficialOwnerPage.editPrincipalAddressLine1(testData.getProperty("merchant.address.line1"));
		addBeneficialOwnerPage.editPrincipalCity(testData.getProperty("merchant.city"));
		addBeneficialOwnerPage.selectState(testData.getProperty("merchant.state"));
		addBeneficialOwnerPage.editPrincipalZip(testData.getProperty("merchant.zip"));

		logStep("Click on Create Beneficial Owner Button");
		Assert.assertTrue(addBeneficialOwnerPage.verifyButtonsPresent());
		merchantDetailsPage = addBeneficialOwnerPage.clickCreateBeneficialOwnerBtn();

		logStep("Remove All Beneficial Owners");
		merchantDetailsPage.removeBeneficialOwner();
	}

}