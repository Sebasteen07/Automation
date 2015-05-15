package com.medfusion.rcm.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.mail.Harakirimail;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.PayBillsStatementPage.JalapenoPayBillsStatementPage;
import com.medfusion.rcm.utils.RCMUtil;

/**
 * @Author:Jakub Odvarka
 * @Date:24.4.2015
 */

@Test
public class RcmAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendStmtVerifyNotificationsMessagesBalance() throws Exception {

		log(this.getClass().getName());
		RCMUtil util = new RCMUtil(driver);
		Harakirimail mail = new Harakirimail(driver);
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();	
		
		log("Post eStatement");
		util.postStatementToPatient(testData.getRcmStatementRest(), IHGUtil.getEnvironmentType().toString());
		
		log("Check email notification and URL");
		String box = testData.getEmail().split("@")[0];		
		assertTrue(mail.catchNewMessageCheckLinkUrl(box, "Your patient eStatement is now available","Visit our website",testData.getUrl(), 50));
		
		log("Log in");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver,testData.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());		
		
		log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		log("Expect an estatement message");
		assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));
		
		log("Archive the message");
		jalapenoMessagesPage.archiveOpenMessage();
					
		jalapenoMessagesPage.goToPayBillsPage(driver);
		log("Check expected balance");
		String balance  = getBalanceDue(driver);
		assertTrue(testData.getStatementBalanceDue().equals(balance));
		log("Balance checks out!");
		
	}
	
	public String getBalanceDue(WebDriver driver){
		try{
			log("Waiting for balance element.");
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='balanceDue']/span/span")));
			WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/span"));
			log("Displayed: " + balance.isDisplayed() + " amount: " + balance.getText());
			return balance.getText();
		}
		catch (Exception ex) {
			log("Exception from element caught, rechecking");
			WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/span"));
			log("Displayed: " + balance.isDisplayed() + " amount: " + balance.getText());
			return balance.getText();
		}
	}
}
