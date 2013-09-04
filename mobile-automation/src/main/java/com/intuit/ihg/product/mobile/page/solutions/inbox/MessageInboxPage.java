package com.intuit.ihg.product.mobile.page.solutions.inbox;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.solutions.apptrequest.ARSubmissionPage;
import com.intuit.ihg.product.mobile.page.solutions.ccdviewer.CCDMessageDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA. User: vvalsan Date: 3/21/13 Time: 4:13 PM To change
 * this template use File | Settings | File Templates.
 */
public class MessageInboxPage extends MobileBasePage {

	@FindBy(xpath = "//a/span/span[text()[contains(.,'Home')]]")
	private WebElement home;

	@FindBy(xpath = "//a[@id='inboxMore']")
	private WebElement loadMoreMessages;

	// Xpath for subject on message
	// //a[contains(@href,'msgDetail')]/h4[text()='html attachment']
	private WebElement message;

	public MessageInboxPage(WebDriver driver) {
		super(driver);
	}

	public MessageDetailsPage clickMessage(String sSubject) throws InterruptedException {
		Thread.sleep(2000);
		message = driver.findElement(By.xpath("//a[contains(@href,'msgDetail')]/h4[text()='" + sSubject + "']")); // 40205
		message.click();
		Thread.sleep(2000);
		if (getHeaderText().contains("New Health Information Import"))
			return PageFactory.initElements(driver, CCDMessageDetailsPage.class);
		return PageFactory.initElements(driver, MessageDetailsPage.class);
	}

	// Looking for the message with specific subject in inbox, returns true
	// when message is located.
	public boolean findMessage(String sSubject) throws InterruptedException {

		boolean messageFind = false;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		List<WebElement> message = driver.findElements(By.cssSelector("h4"));

		for (WebElement webElement : message) {

			if (webElement.getText().contains(sSubject)) {
				messageFind = true;
			}
		}
		// Setting back to 30 as it is default value
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return messageFind;

	}

	// Looking for the message with specific subject in inbox, returns true
	// when finds message with the CSS selector unread.
	public boolean findUnreadMessage(String sSubject) throws InterruptedException {

		boolean unreadMessageFound = false;

		// Looking for the unread messages with specific which can be
		// located by specific CSS selector
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		List<WebElement> message = driver.findElements(By.cssSelector("li.unread a.ui-link"));

		for (WebElement webElement : message) {

			if (webElement.getText().contains(sSubject)) {
				unreadMessageFound = true;
			}
		}
		// Setting back to 30 as it is default value
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return unreadMessageFound;
	}
}
