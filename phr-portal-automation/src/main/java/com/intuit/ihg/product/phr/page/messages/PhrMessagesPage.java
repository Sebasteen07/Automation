package com.intuit.ihg.product.phr.page.messages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.phr.utils.PhrUtil;

public class PhrMessagesPage  extends BasePageObject{

	public PhrMessagesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	@FindBy(id="inboxtab")
	private WebElement inboxTab;
			
	@FindBy(xpath = "//table[@id='row']/tbody/tr[1]/td[7]/a")
	private WebElement firstMessageRow;
	
	@FindBy(xpath = "//div[@class='testcontent']")
	private WebElement smHealthContent;
		
	
	/**
	 * Gives indication if the Inbox page loaded correctly
	 * @return true or false
	 */
	public boolean isInboxLoaded() {
		IHGUtil.PrintMethodName();
		boolean result = false;
		try {
			result = inboxTab.isDisplayed();
		} catch (Exception e) {
			// Catch no element found error
		}
		
		return result;
	}
	
	
	
	/**
	 * @Descripton:Click on first row
	 */
	public void clickOnFirstMessage() {
		PhrUtil.PrintMethodName();
		firstMessageRow.click();
		
	}

	

	/**
	 * @Descripton:Method to check PHR inbox message opened or not.
	 */
	public boolean isMessageDisplayed() {
		PhrUtil.PrintMethodName();
		boolean result = false;
		try {
			result = smHealthContent.isDisplayed();
		} catch (Exception e) {
			// Catch no element found error
		}
		
		return result;
	}
		
	}


	
	
	
	
	
	
	
	
	
	
	
	

