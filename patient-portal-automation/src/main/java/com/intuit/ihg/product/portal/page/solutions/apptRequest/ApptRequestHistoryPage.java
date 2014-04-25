package com.intuit.ihg.product.portal.page.solutions.apptRequest;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.testng.Assert.*;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class ApptRequestHistoryPage extends BasePageObject{
	
	public static final String PAGE_NAME = "Appt Request History Page";
	
	@FindBy(xpath = ".//table[@id='MfAjaxFallbackDefaultDataTable']/tbody/tr/td//a/span")
	private WebElement lnkApptRequestHistoryDetailItem1;

	public ApptRequestHistoryPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Clicks the first Appt Request history link on the page.
	 * 
	 * @return ApptRequestHistoryDetailPage
	 * @throws Exception 
	 */
	public ApptRequestHistoryDetailPage clickApptRequestHistoryDetailLink() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(10000);
		PortalUtil.setPortalFrame(driver);
		// This assert will explain why this action would fail if left unchecked
		assertTrue(lnkApptRequestHistoryDetailItem1.isDisplayed(), 
				"There is no Appt Request History Detail link on the Appt Request History page");
		
		IHGUtil.waitForElement(driver, 30, lnkApptRequestHistoryDetailItem1);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", lnkApptRequestHistoryDetailItem1);
		//lnkApptRequestHistoryDetailItem1.click();
		return PageFactory.initElements(driver, ApptRequestHistoryDetailPage.class);
	}

}
