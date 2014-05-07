package com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class AskAStaffStep3Page extends BasePageObject {
	
	public static final String PAGE_NAME = "Ask A Staff Page - Step 3";
	
	@FindBy(linkText="Back to My Patient Page")
	private WebElement lnkBackToMyPatientPage;
	
	@FindBy(xpath=".//a[contains(@href, 'AskaReqHistory')]")
	private WebElement lnkHistory;

	public AskAStaffStep3Page(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Sends user back to My Patient Page
	 * 
	 * @return MyPatientPage
	 */
	public MyPatientPage clickBackToMyPatientPage() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		lnkBackToMyPatientPage.click();
		
		return PageFactory.initElements(driver, MyPatientPage.class);
	}
	
	/**
	 * Sends user to Ask A Staff History page
	 * 
	 * @return AskAStaffHistoryPage
	 */
	public AskAStaffHistoryPage clickAskAStaffHistory() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		lnkHistory.click();
		return PageFactory.initElements(driver, AskAStaffHistoryPage.class);
	}

}
