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




@Test
public void testVerifyMerchant() throws IOException, NullPointerException, InterruptedException {

	PropertyFileLoader testData = new PropertyFileLoader();
	logStep("Navigating to search Merchant");
	MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
	Thread.sleep(2000);
	merchantSearchPage.findByPracticeID("2560808937");
	merchantSearchPage.searchButtonClick();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	//merchantSearchPage.duplicateRecords();
	logStep("Going to click on view Merchant details page");
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	merchantSearchPage.viewDetailsButtonClick();
	//driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
	
	logStep("Going to verify title of merchant details page");
	MerchantDetailsPage merchantDetailsPage = PageFactory.initElements(driver, MerchantDetailsPage.class);
	merchantDetailsPage.verifyPageTitle();
	
	logStep("Going to verify payment processor information");
	
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	merchantDetailsPage.verifyProcessorInformation();
	
	
	



}
}