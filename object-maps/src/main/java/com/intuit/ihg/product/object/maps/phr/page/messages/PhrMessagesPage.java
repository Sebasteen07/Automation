// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.phr.page.messages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.phr.utils.PhrUtil;

public class PhrMessagesPage extends BasePageObject {

	public PhrMessagesPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "inboxTab")
	private WebElement inboxTab;

	@FindBy(xpath = ".//*[@id='row']/tbody/tr[1]/td[6]/a")
	private WebElement firstMessageRow;

	/**
	 * Gives indication if the Inbox page loaded correctly
	 */
	public boolean isInboxLoaded() {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("externalframe");
		boolean result = false;
		try {
			result = inboxTab.isDisplayed();
		} catch (Exception e) {
			// Catch no element found error
		}
		return result;
	}

	public PhrInboxMessage clickOnFirstMessage() {
		PhrUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		// driver.switchTo().frame(0);
		IHGUtil.waitForElement(driver, 10, firstMessageRow);
		firstMessageRow.click();
		return PageFactory.initElements(driver, PhrInboxMessage.class);
	}

}
