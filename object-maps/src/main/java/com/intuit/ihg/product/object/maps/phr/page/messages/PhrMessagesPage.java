package com.intuit.ihg.product.object.maps.phr.page.messages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.phr.utils.PhrUtil;

public class PhrMessagesPage  extends BasePageObject{

	public PhrMessagesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	@FindBy(id="inboxTab")
	private WebElement inboxTab;

	//@FindBy(xpath = ".//*[@id='msgInboxList']/div[5]/div/div[1]/div[3]")
	@FindBy(xpath = ".//*[@id='row']/tbody/tr[1]/td[6]/a")
	private WebElement firstMessageRow;
		
	
	/**
	 * Gives indication if the Inbox page loaded correctly
	 * @return true or false
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
	
	

	
	/**
	 *  
	 * @Descripton:Click on first row
	 */
	public PhrInboxMessage clickOnFirstMessage() {
		PhrUtil.PrintMethodName();
        driver.switchTo().defaultContent();
        //driver.switchTo().frame(0);
        IHGUtil.waitForElement(driver,10,firstMessageRow);
        firstMessageRow.click();
        return PageFactory.initElements(driver, PhrInboxMessage.class);
		}

}
	
	
	
	
	
	
	
	
	
	
	

