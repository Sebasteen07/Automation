//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package provisioningtests;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pageobjects.MerchantSearchPage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SearchMerchantTest extends ProvisioningBaseTest {

	@Test(enabled = true, groups = { "MerchantProvisioningAcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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