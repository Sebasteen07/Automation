package com.intuit.ihg.product.object.maps.portal.page.inbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

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
	 * Search inbox and open message based on unique string in message
	 * subject
	 * 
	 * @param uniqueSubString
	 *               used to find specific message in Inbox
	 * @return New Inbox Message or null if no message is found
	 * @throws InterruptedException
	 */
	public MessagePage openMessageInInbox(String uniqueSubString) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 10, inboxTab);
		log("Opening message with subject "+uniqueSubString);
		WebElement message = driver.findElement(By.xpath("//span[contains(.,'"+uniqueSubString+"')]/../.."));
		IHGUtil.waitForElement(driver, 10, message);
		message.click();
		IHGUtil.waitForElement(driver, 10, inboxTab);
		
		return PageFactory.initElements(driver, MessagePage.class);
	}
	
	public MessagePage clickFirstMessageRow() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 10, firstMessageRow);
		firstMessageRow.click();
		
		return PageFactory.initElements(driver, MessagePage.class);
	}

}
