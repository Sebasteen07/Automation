package com.medfusion.product.object.maps.patientportal1.page.inbox;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class MessageCenterInboxPage extends BasePageObject {

	public static final String PAGE_NAME = "Consolidated Inbox Page";

	@FindBy(xpath = ".//a[contains(@class, 'messageTab') and text() = 'Inbox']")
	private WebElement inboxTab;

	@FindBy(xpath = ".//table[@class='fixedDataTable']/tbody/tr[1]")
	private WebElement firstMessageRow;


	public MessageCenterInboxPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gives indication if the Inbox page loaded correctly
	 * 
	 * @return true or false
	 */
	public boolean isInboxLoaded() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		boolean result = false;
		try {
			result = inboxTab.isDisplayed();
		} catch (Exception e) {
			// Catch no element found error
		}
		return result;
	}

	/**
	 * Search inbox and open message based on unique string in message subject
	 * 
	 * @param uniqueSubString used to find specific message in Inbox
	 * @return New Inbox Message or null if no message is found
	 * @throws InterruptedException
	 */
	public MessagePage openMessageInInbox(String uniqueSubString, int maxCount) {
		IHGUtil.PrintMethodName();
		log("Opening message with subject " + uniqueSubString);

		for (int i = 1; i <= (maxCount + 1); i++) {
			try {
				PortalUtil.setPortalFrame(driver);
				IHGUtil.waitForElement(driver, 10, inboxTab);
				WebElement message = driver.findElement(By.xpath("//span[contains(.,'" + uniqueSubString + "')]"));
				message.click();
				IHGUtil.waitForElement(driver, 10, inboxTab);
				return PageFactory.initElements(driver, MessagePage.class);
			} catch (NoSuchElementException ex) {
				log("Message didn't arrive. Attempt " + i + "/" + maxCount + ". Refreshing page.");
				driver.navigate().refresh();
			}
		}
		throw new NoSuchElementException("Message was not found after " + maxCount + " attempts.");
	}

	public MessagePage openMessageInInbox(String uniqueSubString) {
		return openMessageInInbox(uniqueSubString, 10);
	}

	public MessagePage clickFirstMessageRow() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 10, firstMessageRow);
		firstMessageRow.click();

		return PageFactory.initElements(driver, MessagePage.class);
	}

}
