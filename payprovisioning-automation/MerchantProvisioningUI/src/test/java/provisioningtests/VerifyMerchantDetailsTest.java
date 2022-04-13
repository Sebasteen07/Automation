package provisioningtests;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pageobjects.MerchantDetailsPage;
import pageobjects.MerchantSearchPage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VerifyMerchantDetailsTest extends ProvisioningBaseTest {

	@Test(enabled = true, groups = { "MerchantProvisioningAcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testVerifyMerchant() throws IOException, NullPointerException, InterruptedException {

		PropertyFileLoader testData = new PropertyFileLoader();
		logStep("Navigating to search Merchant");
		MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
		Thread.sleep(2000);
		merchantSearchPage.findByMerchantId(testData.getProperty("merchant.id.get"));
		merchantSearchPage.searchButtonClick();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		logStep("Going to click on view Merchant details page");
		merchantSearchPage.viewDetailsButtonClick();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		logStep("Going to verify title of merchant details page");
		MerchantDetailsPage merchantDetailsPage = PageFactory.initElements(driver, MerchantDetailsPage.class);
		merchantDetailsPage.verifyPageTitle();

		logStep("Going to verify payment processor information");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		merchantDetailsPage.verifyProcessorInformation(testData.getProperty("merchant.processor.get"));

		logStep("Going to verify general merchant information");
		merchantDetailsPage.verifyGeneralMerchantInformation(testData.getProperty("merchant.practice.id"),
				testData.getProperty("customer.account.number"), testData.getProperty("doing.business.as"));

		logStep("Going to verify Accepted cards ");
		merchantDetailsPage.verifyAcceptedCards();

		logStep("Going to verify Statement Options ");
		merchantDetailsPage.verifyStatementOption();

		logStep("Going to verify Rates Contract Information");
		merchantDetailsPage.verifyRatesContractInformation();

		logStep("Going to verify Percentage fee tiers");
		merchantDetailsPage.verifyPercentageFeeTiers();

		logStep("Going to verify Chargeback");
		merchantDetailsPage.verifyChargeback();

		logStep("Going to verify FraudNRisk");
		merchantDetailsPage.verifyFraudRiskVariable();

		logStep("Going to verify userRoles");
		merchantDetailsPage.verifyUserRoles();

		logStep("Merchant Details Verified successfully");

	}
}