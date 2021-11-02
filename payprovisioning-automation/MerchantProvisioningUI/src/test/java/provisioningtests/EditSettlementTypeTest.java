package provisioningtests;

import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.EditRatesAndContractPage;
import pageobjects.GeneralMerchantInformationPage;
import pageobjects.MerchantDetailsPage;
import pageobjects.MerchantSearchPage;

import java.io.IOException;

public class EditSettlementTypeTest extends ProvisioningBaseTest {

	@Test(dataProvider = "edit_settlement_type", enabled = true)
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

	@DataProvider(name = "edit_settlement_type")
	public static Object[][] dpMethod() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] {

				{ testData.getProperty("settlement.type.monthly") },

		};

	}

	@Test(dataProvider = "edit_settlement_type", enabled = true)
	public void testEditGeneralMerchantInfo(String settlementType)
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

		logStep("Going to click on Edit General Merchant Info Button");
		GeneralMerchantInformationPage generalMerchantInformationPage;
		generalMerchantInformationPage = merchantDetailsPage.clickEditGeneralMerchantInfoButton();
		generalMerchantInformationPage.verifyFieldsOnEditGeneralMerchantInfoPage();

		logStep("Edit Doing Business Name");
		generalMerchantInformationPage.editDoingBusinessAs("NEXTGEN");
		generalMerchantInformationPage.editPracticeID("23456");
		generalMerchantInformationPage.editCustomerAccountNumber("98765");
		generalMerchantInformationPage.editPhoneNumber("98765412");
		generalMerchantInformationPage.editMaxTransactionLimit("1000");
		generalMerchantInformationPage.editBillingDescriptor("Test");
		generalMerchantInformationPage.editBusinessEstablishedDate("21/02/2000");
		generalMerchantInformationPage.editWebsiteURL("www.google.com");

		Assert.assertTrue(generalMerchantInformationPage.verifyBusinessType(), "Business Type Name is missing");
		Assert.assertTrue(generalMerchantInformationPage.verifySICMCCCode(), "SIC/MCC Code is missing");


	}

}