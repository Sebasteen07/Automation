package com.intuit.ihg.product.mu2.test;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.product.mu2.utils.APIData;
import com.intuit.ihg.product.mu2.utils.APITestData;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessagePage;
import com.intuit.ihg.product.object.maps.portal.page.AChecker;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessageCenterInboxPage;

public class MU2Accessibility extends BaseTestNGWebDriver 
{
	private APIData testData;
	
	@BeforeClass
	public void Setup() throws Exception
	{
		APITestData apitestData = new APITestData();
		testData = new APIData(apitestData);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void TestLoginPage() throws Exception 
	{	
		// Open the API testing page
		new PortalLoginPage(driver, testData.getPortalURL());
		
		// Get source and iFrame HTML (AChecker cannot handle iFrames)
		StringSelection source = new StringSelection(driver.getPageSource());
		StringSelection iFrame = new StringSelection(driver.switchTo().frame(0).getPageSource());		
		
		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.Setup();
		
		// Paste the source code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(source, source);
		achecker.Validate();
		
		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.Validate();
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class )
	public void TestDashboardPage() throws Exception
	{
		// Open the API testing page and log in
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		WaitForPage(patientPage);
		
		// Get source and iFrame HTML (AChecker cannot handle iFrames)
		StringSelection source = new StringSelection(driver.getPageSource());
		StringSelection iFrame = new StringSelection(driver.switchTo().frame(0).getPageSource());		
		
		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.Setup();
		
		// Paste the source code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(source, source);
		achecker.Validate();
		
		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.Validate();
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void TestInboxPage() throws Exception
	{		
		// Open the API testing page, log in and go to Inbox
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		WaitForPage(patientPage);
		patientPage.clickViewAllMessagesInMessageCenter();
		
		// Get iFrame HTML (AChecker cannot handle iFrames)
		StringSelection iFrame = new StringSelection(driver.switchTo().frame(0).getPageSource());		
		
		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.Setup();
		
		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.Validate();
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void TestMessageDetailsPage() throws Exception
	{		
		// Open the API testing page, log in, go to Inbox and open a message
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage patientPage = loginPage.login(testData.getPortalUserName(), testData.getPortalPassword());
		WaitForPage(patientPage);
		MessageCenterInboxPage inboxPage = patientPage.clickViewAllMessagesInMessageCenter();
		inboxPage.openMessageInInbox("You have new health data");
		
		// The message body is the first iframe on the page
		StringSelection iFrame = new StringSelection(driver.switchTo().frame(0).getPageSource());		
		
		// Open AChecker page
		AChecker achecker = new AChecker(driver);
		achecker.Setup();
		
		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.Validate();
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void TestCCDPage() throws Exception
	{
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
		achecker.Setup();
		
		// Paste the iFrame code
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(iFrame, iFrame);
		achecker.Validate();
	}
	
	// Wait for the patient page to finish loading before grabbing the HTML
	private void WaitForPage(MyPatientPage patientPage)
	{
		driver.switchTo().frame(0);
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(patientPage.gettxtMyPatientPage()));
		driver.switchTo().defaultContent();
	}
}
