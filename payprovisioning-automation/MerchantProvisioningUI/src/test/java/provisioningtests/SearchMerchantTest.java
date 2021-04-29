package provisioningtests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;

import pageobjects.LoginPage;
import pageobjects.MerchantSearchPage;

public class SearchMerchantTest extends ProvisioningBaseTest {

	@Test
	public void navigatingToMerchantSearch() throws IOException, NullPointerException, InterruptedException {

		PropertyFileLoader testData = new PropertyFileLoader();
		log("step 3: Navigating to search Merchant");
		MerchantSearchPage merchantSearchPage = PageFactory.initElements(driver, MerchantSearchPage.class);
		assertTrue(merchantSearchPage.assessSearchMerchantElements());
		merchantSearchPage.findByPracticeID(testData.getProperty("practiceID"));
		merchantSearchPage.searchButtonClick();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		merchantSearchPage.duplicateRecords();
		Thread.sleep(1000);

	}

}
