package provisioningtests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;

import pageobjects.MerchantDetailsPage;
import pageobjects.MerchantSearchPage;

public class SearchMerchantTest extends ProvisioningBaseTest {

	@Test
	public void testDuplicateRecords() throws IOException, NullPointerException, InterruptedException {

		PropertyFileLoader testData = new PropertyFileLoader();
		logStep("Navigating to search Merchant");
		MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
		Thread.sleep(2000);
		merchantSearchPage.findByPracticeID(testData.getProperty("practice.id"));
		merchantSearchPage.searchButtonClick();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		merchantSearchPage.duplicateRecords();
	}





}