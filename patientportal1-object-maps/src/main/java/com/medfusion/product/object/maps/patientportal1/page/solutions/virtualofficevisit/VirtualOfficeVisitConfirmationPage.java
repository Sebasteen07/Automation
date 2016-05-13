package com.medfusion.product.object.maps.patientportal1.page.solutions.virtualofficevisit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class VirtualOfficeVisitConfirmationPage extends BasePageObject {
	
	public static final String PAGE_NAME = "Virtual Office Visit Confirmation Page";
	
	@FindBy(linkText="Visit History")
	private WebElement visitHistory;
	
	@FindBy(linkText="Click here to return to the My Patient Page")
	private WebElement returnToMyPatientPage;
	
	public VirtualOfficeVisitConfirmationPage(WebDriver driver) {
		super(driver);
	}

	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		
		boolean result = false;
		try {
			result = returnToMyPatientPage.isDisplayed();
		} catch (Exception e) {
			// Catch element not found error
		}
		
		return result;
	}
	
	/**
	 * Click the link to go to the Virtul Office Visit History page
	 * @return the Vov History page
	 */
	public VirtualOfficeVisitHistoryPage visitVirtualOfficeVistHistory() {
		IHGUtil.PrintMethodName();
		
		visitHistory.click();
		
		return PageFactory.initElements(driver, VirtualOfficeVisitHistoryPage.class);
	}
}
