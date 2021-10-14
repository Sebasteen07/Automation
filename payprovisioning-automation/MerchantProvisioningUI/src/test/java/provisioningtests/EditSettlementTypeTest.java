package provisioningtests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;

import pageobjects.EditRatesAndContractPage;
import pageobjects.MerchantDetailsPage;
import pageobjects.MerchantSearchPage;

public class EditSettlementTypeTest extends ProvisioningBaseTest {

	@Test(dataProvider = "edit_settlement_type", enabled = true)
	public void testEditSettlemntType(String settlementType)
			throws IOException, NullPointerException, InterruptedException {

		PropertyFileLoader testData = new PropertyFileLoader();
		logStep("Navigating to search Merchant");
		MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
		Thread.sleep(2000);

		merchantSearchPage.findByMerchantId(testData.getProperty("merchant.id.settlement.edit"));
		merchantSearchPage.searchButtonClick();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		logStep("Going to click on view Merchant details page");
		merchantSearchPage.viewDetailsButtonClick();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		logStep("Going to verify title of merchant details page");
		MerchantDetailsPage merchantDetailsPage = PageFactory.initElements(driver, MerchantDetailsPage.class);
		merchantDetailsPage.verifyPageTitle();

		logStep("Going to click on Edit rates & Contract");
		merchantDetailsPage.clickOnRatesNContractButton();
		driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

		// Thread.sleep(10000);
		logStep("Going to change settlemet type");
		EditRatesAndContractPage editRatesAndContractPage = PageFactory.initElements(driver,
				EditRatesAndContractPage.class);
		editRatesAndContractPage.editSettlementType(settlementType);

		logStep("Going to verify merchant details page");
		MerchantDetailsPage merchantDetailsPageNew = PageFactory.initElements(driver, MerchantDetailsPage.class);
		merchantDetailsPageNew.verifyPageTitle();
		merchantDetailsPageNew.waitToCheckEditRatesContractButton();

		logStep("Going to verifysettment edit of settlement type");
		merchantDetailsPageNew.verifySettlementType(settlementType);

	}

	@DataProvider(name = "edit_settlement_type")
	public static Object[][] dpMethod() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] { { testData.getProperty("settlement.type.monthly") },
				{ testData.getProperty("settlement.type.daily") } };
		// }
	}
//	

}