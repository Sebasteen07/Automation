package provisioningtests;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;

import pageobjects.InstaMedMerchantDetailsPage;
import pageobjects.MerchantSearchPage;

public class VerifyInstaMedMerchantDetailsTest extends ProvisioningBaseTest  {
	@Test
	
	public void testVerifyInstaMedMerchant() throws IOException, NullPointerException, InterruptedException {
		
		
		PropertyFileLoader testData = new PropertyFileLoader();
		logStep("Navigating to search Merchant");
		MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
		Thread.sleep(2000);
		
		merchantSearchPage.findByMerchantId(testData.getProperty("instamedmerchant.id"));
		merchantSearchPage.searchButtonClick();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		logStep("Going to click on view Merchant details page");
		merchantSearchPage.viewDetailsButtonClick();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Thread.sleep(2000);
		
		logStep("Going to verify title of merchant details page");
		InstaMedMerchantDetailsPage instamedMerchantDetailsPage = PageFactory.initElements(driver, InstaMedMerchantDetailsPage.class);
		instamedMerchantDetailsPage.verifyPageTitle();
		
		

	}

}
