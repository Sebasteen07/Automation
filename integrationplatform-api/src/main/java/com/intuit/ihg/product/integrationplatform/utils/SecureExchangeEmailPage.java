//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;

public class SecureExchangeEmailPage {
	WebDriver driver;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Date, Descending')]")
	public WebElement searchOrder;

	@FindBy(how = How.XPATH, using = "//i[@class='fas fa-folder-open fa-lg']")
	public WebElement viewTOC;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Hi pmemrqa')]")
	public WebElement userName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"divMessageSelected\"]/section/div/article/div[3]/header/span")
	public WebElement attachmentBody;

	@FindBy(how = How.XPATH, using = "//i[@class=\"fas fa-sign-out-alt icon-size\"]")
	public WebElement signOut;

	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[4]/div[3]/span[1]/a")
	public WebElement fromEmailID;

	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[4]/div[3]/span[3]/span")
	public WebElement toEmailID;

	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[5]/ul/li/span[2]")
	public WebElement attachmentName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"layout_3pane\"]/div/div[5]/div/div[3]/div[6]/div[3]/div")
	public WebElement subjectBody;

	@FindBy(how = How.XPATH, using = "//*[@data-original-title='Move to trash']")
	public WebElement deleteMessage;

	@FindBy(how = How.XPATH, using = "//div[@class='modal-body']/button/following-sibling::div")
	public WebElement deleteConf;

	@FindBy(how = How.XPATH, using = "//div[@class='modal-body']/button")
	public WebElement deleteConfPopupClose;
	
	@FindBy(how = How.ID, using = "Trash")
	public WebElement trashFolder;
	
	@FindBy(how = How.XPATH, using = "//*[@data-original-title='Permanent Delete']")
	public WebElement permanentDeleteBtn;
	
	@FindBy(how = How.XPATH, using = "//button[@data-bb-handler='confirm']")
	public WebElement confirmPermanentDeleteOnPopup;
	
	
	public SecureExchangeEmailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public SecureExchangeEmailPage verifySecureEmail(String subject, String AttachmentType, String fileName, String toEmail, String fromEmail,
			String attachTOCName) {

		WebElement secureEmail = driver.findElement(By.xpath("//*[contains(text(),'" + subject + "')]"));
		Log4jUtil.log("Verify Subject if matched actual " + secureEmail.getText() + " expected " + subject);
		assertEquals(secureEmail.getText(), subject);

		Log4jUtil.log("Secure Exchange Step 1: Verify Secure Message ");
		secureEmail.click();
		if (AttachmentType != null && !AttachmentType.isEmpty() && !AttachmentType.equalsIgnoreCase("none")) {
			WebElement attachmentName = driver.findElement(By.xpath("//*[contains(text(),'" + fileName + "')]"));
			Log4jUtil.log("Actual name is " + attachmentName.getText() + " should contain name: " + fileName);
			assertTrue(attachmentName.getText().contains(fileName), "filename not matched");
		}

		if (AttachmentType.equalsIgnoreCase("xml")) {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Log4jUtil.log("body=" + attachmentBody.isEnabled());

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,450)", "");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			WebElement TOCOpenNewWindow = driver.findElement(By.xpath("//i[@class='fas fa-folder-open fa-lg']"));
			jse.executeScript("arguments[0].click();", TOCOpenNewWindow);
			Log4jUtil.log("TOCOpenNewWindow");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String winHandleBefore = driver.getWindowHandle();
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}

			// Perform the actions on new window
			Log4jUtil.log("Secure Exchange Step 2: Verfiy if attachment is present");
			// wait for TOC to load
			WebElement tocName = driver.findElement(By.xpath("//*[@id=\"contentDiv_1_SET_1_XML_FILE_1\"]/h1"));
			Log4jUtil.log("Verify the TOC name Actual " + tocName.getText() + " expected name is " + attachTOCName);
			assertEquals(tocName.getText(), attachTOCName);
			driver.close();
			driver.switchTo().window(winHandleBefore);
		}

		return PageFactory.initElements(driver, SecureExchangeEmailPage.class);
	}

	public SecureExchangeEmailPage SignOut() {
		driver.switchTo().defaultContent();
		IHGUtil.waitForElement(driver, 30, signOut);
		signOut.click();
		return PageFactory.initElements(driver, SecureExchangeEmailPage.class);
	}

	public SecureExchangeEmailPage serchForDeleteMessage(String subject) throws InterruptedException {
		Log4jUtil.log("Switching frame");
		Log4jUtil.log("Searching for Email with subject " + subject);
		try {
			WebElement secureSendEmail = driver.findElement(By.xpath("//*[contains(text(),'" + subject + "')]"));
			IHGUtil.waitForElement(driver, 80, secureSendEmail);
			Log4jUtil.log("Verify Subject if matched actual " + secureSendEmail.getText() + " expected " + subject);
			assertEquals(secureSendEmail.getText(), subject);
			WebElement secureSendEmailSelectCheckbox =
					driver.findElement(By.xpath("//*[contains(text(),'" + subject + "')]/preceding::input[contains(@class,'ptSelectConversation-input')][1]"));
			secureSendEmailSelectCheckbox.click();
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,400)", "");
			IHGUtil.waitForElement(driver, 80, deleteMessage);
			deleteMessage.click();
			Log4jUtil.log("Clicked on delete button successfully and popup appeared");
			IHGUtil.waitForElement(driver, 80, deleteConf);
			String deletedMessage = deleteConf.getText();
			deleteConfPopupClose.click();
			Log4jUtil.log("Delete Message :   " + subject + "  " + deletedMessage);
			assertEquals(deletedMessage, "Message moved successfully.");
			trashFolder.click();
			Thread.sleep(3000);
			WebElement secureSendEmailInTrash = driver.findElement(By.xpath("//*[contains(text(),'" + subject + "')]"));
			Log4jUtil.log("Verify Subject if matched actual " + secureSendEmailInTrash.getText() + " expected " + subject);
			assertEquals(secureSendEmailInTrash.getText(), subject);
			WebElement secureSendEmailSelectCheckboxInTrash =
					driver.findElement(By.xpath("//*[contains(text(),'" + subject + "')]/preceding::input[contains(@class,'ptSelectConversation-input')][1]"));
			jse.executeScript("arguments[0].click();", secureSendEmailSelectCheckboxInTrash);
			IHGUtil.waitForElement(driver, 80, permanentDeleteBtn);
			permanentDeleteBtn.click();
			IHGUtil.waitForElement(driver, 80, deleteConf);
			Log4jUtil.log("Clicked on delete button successfully and popup appeared");
			String permanentDeleteMessage  = deleteConf.getText();
			assertEquals(permanentDeleteMessage, "Do you want to permanently delete this email? You will not be able to retrieve them once it has been deleted.");
			Thread.sleep(2000);
			jse.executeScript("arguments[0].click();", confirmPermanentDeleteOnPopup);
			Thread.sleep(2000);
			Log4jUtil.log("Clicked on Confirm delete button successfully and popup appeared");
			String permanentDeleteConfMessage  = deleteConf.getText();
			Log4jUtil.log("Delete Message :   " + subject + "  " + permanentDeleteConfMessage);
			assertEquals(permanentDeleteConfMessage, "Message deleted successfully.");
			deleteConfPopupClose.click();
			Log4jUtil.log("Clicked on Close btn on popup");
			//Wait for half minute to sync the deleted message
			Thread.sleep(30000);
			
		} catch (Exception E) {
			Log4jUtil.log("Exception caught " + E);
			assertTrue(false);
		}
		return PageFactory.initElements(driver, SecureExchangeEmailPage.class);
	}
}
