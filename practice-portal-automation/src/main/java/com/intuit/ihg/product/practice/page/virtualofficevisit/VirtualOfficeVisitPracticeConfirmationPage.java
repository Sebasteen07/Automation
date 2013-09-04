package com.intuit.ihg.product.practice.page.virtualofficevisit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class VirtualOfficeVisitPracticeConfirmationPage extends BasePageObject {
	
	public static final String PAGE_NAME = "Virtual Office Visit Practice Confirmation Page";
	
	@FindBy(xpath=".//input[@value='Confirm and submit']")
	private WebElement btnConfirmAndSubmit;
	
	public VirtualOfficeVisitPracticeConfirmationPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Gives indication of whether page loaded correctly
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		//PracticeUtil.setPracticeFrame(driver);
		
		boolean result = false;
		try {
			result = btnConfirmAndSubmit.isDisplayed();
		} catch (Exception e) {
			// Catch element not found error
		}
		
		return result;
	}
	
	/**
	 * Confirms and submits request, which, if successful, will mark this specific transaction closed.
	 * @return final page in workflow
	 */
	public VirtualOfficeVisitSummaryPage confirmAndSubmit() {
		IHGUtil.PrintMethodName();
		//PracticeUtil.setPracticeFrame(driver);
		
		btnConfirmAndSubmit.click();		
		return PageFactory.initElements(driver, VirtualOfficeVisitSummaryPage.class);
	}

}
