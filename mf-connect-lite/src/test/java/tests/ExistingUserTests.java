package tests;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import objectmaps.HomePage;
import objectmaps.ModalPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.medfusion.listenerpackage.Listener.class)

public class ExistingUserTests extends BaseTestNGWebDriver {
	protected PropertyFileLoader testData;
	
	@BeforeMethod(enabled=true)
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		String url = testData.getProperty("mfconnectURL");
		System.out.println(url);
		new HomePage(driver, url);
	}
	
	@Test(enabled=true)
	public void assessExistingUserElements() throws Exception {
		HomePage home = new HomePage(driver);
		home.existingUserBtn.click();
		home.mfConnectBtn.click();
		ModalPage modal = new ModalPage(driver);
		
		log("Waiting for modal page to load");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(modal.existingAddConnectionButton));
		
		log("Checking if buttons are present");
		Assert.assertTrue(modal.mfCloseButton.isDisplayed());
		Assert.assertTrue(modal.existingAddConnectionButton.isDisplayed());
		Assert.assertTrue(modal.doneMakingConnectionsButton.isDisplayed());
	}
	
	
}