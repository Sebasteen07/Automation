package com.intuit.ihg.product.mu2.test;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.mu2.utils.APIData;
import com.intuit.ihg.product.mu2.utils.APITestData;
import com.medfusion.product.object.maps.patientportal1.page.inbox.MessagePage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.MyAccountPage;
import com.medfusion.product.object.maps.patientportal1.page.myAccount.AccountActivity.ViewAccountActivityPage;
import com.medfusion.product.object.maps.patientportal1.page.AChecker;
import com.medfusion.product.object.maps.patientportal1.page.AChecker.LevelOfWCAG;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.inbox.MessageCenterInboxPage;

public class MU2Accessibility extends BaseTestNGWebDriver {
	private APIData testData;
	LevelOfWCAG level = LevelOfWCAG.AA;
  
	@BeforeMethod(alwaysRun = true)
	public void Setup() throws Exception {
		log("Setting up MU2 Test data for " + IHGUtil.getEnvironmentType());
		APITestData apitestData = new APITestData();
		testData = new APIData(apitestData);
	}

	@Test(enabled = true, groups = {"AccessibilityTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginPage() throws Exception {
		// Open the API testing page
		new PortalLoginPage(driver, testData.getPortalURL());

		// Get source and iFrame HTML (AChecker cannot handle iFrames)
		StringSelection source = new StringSelection(driver.getPageSource());
		StringSelection iFrame = new StringSelection(driver.switchTo().frame(0).getPageSource());
		
		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		// Paste the source code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(source, source);
		achecker.validate();

		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.validate();
	}

	@Test(enabled = true, groups = {"AccessibilityTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDashboardPage() throws Exception {
		// Open the API testing page and log in
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		waitForPage(patientPage);

		// Get source and iFrame HTML (AChecker cannot handle iFrames)
		StringSelection source = new StringSelection(driver.getPageSource());
		StringSelection iFrame = new StringSelection(driver.switchTo().frame(0).getPageSource());

		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		// Paste the source code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(source, source);
		achecker.validate();

		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.validate();
	}

	@Test(enabled = true, groups = {"AccessibilityTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxPage() throws Exception {
		// Open the API testing page, log in and go to Inbox
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		waitForPage(patientPage);
		MessageCenterInboxPage inbox = patientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inbox.isInboxLoaded());

		// Get iFrame HTML (AChecker cannot handle iFrames)
		StringSelection iFrame = new StringSelection(driver.getPageSource());

		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.validate();
	}

	@Test(enabled = true, groups = {"AccessibilityTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMessageDetailsPage() throws Exception {
		// Open the API testing page, log in, go to Inbox and open a message
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		waitForPage(patientPage);
		MessageCenterInboxPage inboxPage = patientPage.clickViewAllMessagesInMessageCenter();
		inboxPage.openMessageInInbox("You have new health data");

		// The message body is the first iframe on the page
		StringSelection iFrame = new StringSelection(driver.switchTo().frame(0).getPageSource());

		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.validate();
	}

	@Test(enabled = true, groups = {"AccessibilityTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDPage() throws Exception {
		// Open the API testing page, log in, go to Inbox and open a CCD from a message
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		MessageCenterInboxPage inboxPage = patientPage.clickViewAllMessagesInMessageCenter();
		MessagePage message = inboxPage.openMessageInInbox("You have new health data");
		Thread.sleep(1000);
		message.clickBtnReviewHealthInformation();

		// The CCD iframe is now the active frame so we can get the source directly
		// The CCD must be closed or the AChecker subroutine won't work properly
		StringSelection iFrame = new StringSelection(driver.getPageSource());
		message.verifyCCDViewerAndClose();
		driver.switchTo().defaultContent();

		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.validate();
	}

	@Test(enabled = true, groups = {"AccessibilityTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMyAccountPage() throws Exception {
		// Open the API testing page, log in and go to My Account
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		waitForPage(patientPage);
		patientPage.clickMyAccountLink();

		// Get iFrame HTML (AChecker cannot handle iFrames)
		StringSelection iFrame = new StringSelection(driver.switchTo().frame(0).getPageSource());

		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.validate();
	}

	@Test(enabled = true, groups = {"AccessibilityTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAccountActivity() throws Exception {
		log("TestCase: account activity");
		// Open the API testing page, log in and go to My Account > Account Activity
		log("Opening portal page");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		waitForPage(patientPage);
		MyAccountPage myAccount = patientPage.clickMyAccountLink();
		ViewAccountActivityPage accountActivity = myAccount.addAccountActivityLink();

		log("View Account Activity and select the source code of the lightbox iframe");
		accountActivity.clickOnViewAccountActivity();
		assertTrue(accountActivity.isAccountActivityDisplayed());
		StringSelection iFrame = new StringSelection(driver.getPageSource());

		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.setupLevel(level);

		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.validate();
	}

	// Wait for the patient page to finish loading before grabbing the HTML
	private void waitForPage(MyPatientPage patientPage) {
		driver.switchTo().frame(0);
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(patientPage.gettxtMyPatientPage()));
		driver.switchTo().defaultContent();
	}
}
