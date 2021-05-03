package provisioningtests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;
import pageobjects.MerchantSearchPage;

public class SearchMerchantTest extends ProvisioningBaseTest {

	@Test
	public void testDuplicateRecords() throws IOException, NullPointerException, InterruptedException {

		PropertyFileLoader testData = new PropertyFileLoader();
		logStep("step 3: Navigating to search Merchant");
		MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
		Thread.sleep(2000);
		merchantSearchPage.findByPracticeID(testData.getProperty("practice_ID"));
		merchantSearchPage.searchButtonClick();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		merchantSearchPage.duplicateRecords();
	}
}