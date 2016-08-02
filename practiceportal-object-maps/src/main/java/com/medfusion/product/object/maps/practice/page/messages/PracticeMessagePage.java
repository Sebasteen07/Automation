package com.medfusion.product.object.maps.practice.page.messages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class PracticeMessagePage extends BasePageObject {

	public static final String PAGE_NAME = "Practice Message Page";
	
	@FindBy(name="message:subject")
	private WebElement subject;
	
	@FindBy(name="message:body")
	private WebElement body;
	
	@FindBy(name="button:submit")
	private WebElement btnSubmitMessage;
	
	public PracticeMessagePage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Gives an indication if the page is loaded
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		boolean result = false;
		try {
			result = subject.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}

}
