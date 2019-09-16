package tests;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import objectmaps.HomePage;
import objectmaps.ModalPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import objectmaps.AddConnectionPage;
import objectmaps.ConnectionDoctorPage;
import objectmaps.ConnectionLocationPage;
import objectmaps.ConnectionPortalPage;
import objectmaps.OfficeSelectPage;
import objectmaps.ConfirmDoctorPortalPage;
import objectmaps.CreatePortalConnectionPage;
import objectmaps.PortalLauncher;
import objectmaps.SelectDoctorPage;
import objectmaps.DoctorLocationPage;
import objectmaps.SelectPortalPage;

public class AddingConnectionsTests extends BaseTestNGWebDriver {
	protected PropertyFileLoader testData;
	
	@BeforeMethod(enabled=true)
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		String url = testData.getProperty("mfconnectURL");
		HomePage home = new HomePage(driver, url);
		home.existingUserBtn.click();
		home.mfConnectBtn.click();
		ModalPage modal = new ModalPage(driver);
		log("Waiting for modal page to load");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(modal.existingAddConnectionButton));
		modal.existingAddConnectionButton.click();
	}
	
	@Test(enabled=true)
	public void assessAddConnectionPageElements() throws Exception {
		AddConnectionPage acp = new AddConnectionPage(driver);
		log("Checking for Add A Connection page buttons");
		Assert.assertTrue(acp.mfPracticeBtn.isDisplayed());
		Assert.assertTrue(acp.mfDoctorBtn.isDisplayed());
		Assert.assertTrue(acp.mfPortalBtn.isDisplayed());
	}
	
	@Test(enabled=true)
	public void assessConnectionLocationPageElements() throws Exception {
		AddConnectionPage acp = new AddConnectionPage(driver);
		acp.mfPracticeBtn.click();
		log("Checking for office name and zip code search bars");
		ConnectionLocationPage clp = new ConnectionLocationPage(driver);
		Assert.assertTrue(clp.searchBy.isDisplayed());
		Assert.assertTrue(clp.searchZip.isDisplayed());
	}
	
	@Test(enabled=true)
	public void assessConnectionDoctorPageElements() throws Exception {
		AddConnectionPage acp = new AddConnectionPage(driver);
		acp.mfPracticeBtn.click();
		log("Checking for doctor name and zip code search bars");
		ConnectionDoctorPage clp = new ConnectionDoctorPage(driver);
		Assert.assertTrue(clp.searchBy.isDisplayed());
		Assert.assertTrue(clp.searchZip.isDisplayed());
	}
	
	@Test(enabled=true)
	public void assessConnectionPortalPageElements() throws Exception {
		AddConnectionPage acp = new AddConnectionPage(driver);
		acp.mfPracticeBtn.click();
		log("Checking for url search bar");
		ConnectionPortalPage cpp = new ConnectionPortalPage(driver);
		Assert.assertTrue(cpp.primaryURL.isDisplayed());
	}
	
	@Test(enabled=true)
	public void searchForOffice() throws Exception {
		AddConnectionPage acp = new AddConnectionPage(driver);
		acp.mfPracticeBtn.click();
		ConnectionLocationPage clp = new ConnectionLocationPage(driver);
		String office = testData.getProperty("office");
		String zip = testData.getProperty("zip");
		clp.searchBy.sendKeys(office);
		clp.searchZip.sendKeys(zip);
		clp.directorySearchBtn.click();
		
		log("Checking for search results page");
		OfficeSelectPage osp = new OfficeSelectPage(driver);
		log("Waiting for elements to load");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(osp.firstElement));
		osp.firstElement.click();
		
		log("Checking for doctor's portal page");
		wait.until(ExpectedConditions.visibilityOf(osp.mfConnectBack));
		ConfirmDoctorPortalPage cdpp = new ConfirmDoctorPortalPage(driver);
		
		log("Connecting to a connected portal");
		cdpp.firstElement.click();
		CreatePortalConnectionPage cpcp1 = new CreatePortalConnectionPage(driver);
		Assert.assertTrue(cpcp1.createConnectionBtn.isDisplayed());
		Assert.assertTrue(cpcp1.resetPassword.isDisplayed());
		cpcp1.resetPassword.click();
		
		log("Testing portal launcher cancel button");
		PortalLauncher pl1 = new PortalLauncher(driver);
		pl1.cancelBtn.click();
		CreatePortalConnectionPage cpcp2 = new CreatePortalConnectionPage(driver);
		Assert.assertTrue(cpcp2.createConnectionBtn.isDisplayed());
		Assert.assertTrue(cpcp2.resetPassword.isDisplayed());
		
		log("Testing portal launcher launch button");
		cpcp2.resetPassword.click();
		PortalLauncher pl2 = new PortalLauncher(driver);
		pl2.launchBtn.click();
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).sendKeys(Keys.TAB).build().perform();
		log("Checking parent tab if page navigated back to previous page");
		CreatePortalConnectionPage cpcp3 = new CreatePortalConnectionPage(driver);
		Assert.assertTrue(cpcp3.createConnectionBtn.isDisplayed());
		Assert.assertTrue(cpcp3.resetPassword.isDisplayed());
		
	}
	
	@Test(enabled=true)
	public void searchForDoctor() throws Exception {
		AddConnectionPage acp = new AddConnectionPage(driver);
		acp.mfDoctorBtn.click();
		ConnectionDoctorPage cdp = new ConnectionDoctorPage(driver);
		String doctor = testData.getProperty("doctor");
		String zip = testData.getProperty("zip");
		cdp.searchBy.sendKeys(doctor);
		cdp.searchZip.sendKeys(zip);
		cdp.directorySearchBtn.click();
		
		log("Checking for search results page and click first entry");
		SelectDoctorPage sdp = new SelectDoctorPage(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(sdp.firstElement));
		sdp.firstElement.click();
		log("Choosing a doctor location");
		DoctorLocationPage dlp = new DoctorLocationPage(driver);
		wait.until(ExpectedConditions.visibilityOf(dlp.mfConnectBack));
		dlp.firstElement.click();
		log("Choosing a sample portal");
		ConfirmDoctorPortalPage cdpp = new ConfirmDoctorPortalPage(driver);
		wait.until(ExpectedConditions.visibilityOf(cdpp.mfConnectBack));
		cdpp.mfConnectBack.click();
		log("Assessing create portal connection elements");
		CreatePortalConnectionPage cpcp = new CreatePortalConnectionPage(driver);
		Assert.assertTrue(cpcp.ccUsername.isDisplayed());
		Assert.assertTrue(cpcp.ccPassword.isDisplayed());
	}
	
	@Test(enabled=true)
	public void searchForWebsite() throws Exception {
		AddConnectionPage acp = new AddConnectionPage(driver);
		acp.mfPortalBtn.click();
		ConnectionPortalPage cpp = new ConnectionPortalPage(driver);
		String url = testData.getProperty("url");
		cpp.primaryURL.sendKeys(url);
		cpp.directorySearchBtn.click();
		
		log("Picking a portal from search results");
		SelectPortalPage spp = new SelectPortalPage(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(spp.firstElement));
		spp.firstElement.click();
		log("Assessing create portal connection elements");
		CreatePortalConnectionPage cpcp = new CreatePortalConnectionPage(driver);
		Assert.assertTrue(cpcp.ccUsername.isDisplayed());
		Assert.assertTrue(cpcp.ccPassword.isDisplayed());
	}
}
