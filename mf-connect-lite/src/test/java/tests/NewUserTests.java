package tests;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import objectmaps.HomePage;
import objectmaps.ModalPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.medfusion.listenerpackage.Listener.class)

public class NewUserTests extends BaseTestNGWebDriver {
	protected PropertyFileLoader testData;
	
	@BeforeMethod(enabled=true)
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		String url = testData.getProperty("mfconnectURL");
		new HomePage(driver, url);
	}
	
	@Test(enabled=true)
	public void assessNewUserElements() throws Exception {
		HomePage home = new HomePage(driver);
		home.newUserBtn.click();
		home.mfConnectBtn.click();
		ModalPage modal = new ModalPage(driver);
		log("Checking if buttons are present");
		Assert.assertTrue(modal.mfCloseButton.isDisplayed());
		Assert.assertTrue(modal.newAddConnectionButton.isDisplayed());
		log("Checking for connections");
		String bodyText = (driver.findElement(By.tagName("body"))).getText();
		Assert.assertFalse(bodyText.contains("Manage your portal connections"), "Manage your portal connections");
		
	}
	
	
}