package tests;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import objectmaps.HomePage;
import objectmaps.ModalPage;
import objectmaps.CreatePortalConnectionPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import objectmaps.AddConnectionPage;

@Listeners(com.medfusion.listenerpackage.Listener.class)

public class PreselectedPortalTests extends BaseTestNGWebDriver {
	protected PropertyFileLoader testData;
	
	@BeforeMethod(enabled=true)
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		String url = testData.getProperty("mfconnectURL");
		new HomePage(driver, url);
	}
	
	@Test(enabled=true)
	public void assessPreselectedPortal() throws Exception {
		HomePage home = new HomePage(driver);
		home.portalPreselectCheck.click();
		home.mfConnectBtn.click();
		CreatePortalConnectionPage cpcp = new CreatePortalConnectionPage(driver);
		log("Check if login page is displayed after preselecting portal");
		Assert.assertTrue(cpcp.ccPassword.isDisplayed());
		Assert.assertTrue(cpcp.ccUsername.isDisplayed());
	}
	
	@Test(enabled=true)
	public void assessRecommendedPortals() throws Exception {
		HomePage home = new HomePage(driver);
		home.recEnableCheck1.click();
		home.recEnableCheck2.click();
		home.recEnableCheck3.click();
		home.recEnableCheck4.click();
		home.recEnableCheck5.click();
		home.mfConnectBtn.click();
		ModalPage modal = new ModalPage(driver);
		modal.existingAddConnectionButton.click();
		log("Checking if recommended portals are displayed");
		AddConnectionPage acp = new AddConnectionPage(driver);
		String bodyText = (driver.findElement(By.tagName("body"))).getText();
		Assert.assertFalse(bodyText.contains("Top recommended portals"), "Top recommended portals");
		acp.firstRecPortal.click();
		log("Checking if login page is displayed after clicking a recommended portal");
		CreatePortalConnectionPage cpcp = new CreatePortalConnectionPage(driver);
		Assert.assertTrue(cpcp.ccPassword.isDisplayed());
		Assert.assertTrue(cpcp.ccUsername.isDisplayed());
	}
	
}