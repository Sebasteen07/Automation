package com.intuit.ihg.product.practice.page.customform;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class SearchPatientFormsResultPage extends BasePageObject {

	@FindBy(css="td.searchResultsDetails > a")
	private WebElement lnkViewDetails;

	
	public SearchPatientFormsResultPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Click on View Link and go to Patient CustomForm Detail Page
	 * @return ViewPatientFormPage 
	 * @throws Exception
	 */
	public ViewPatientFormPage clickViewLink() throws Exception {
		IHGUtil.PrintMethodName();
		
		try {
			lnkViewDetails.isDisplayed();
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception("Either there was a timeout on loading the page, " +
					"the staff member is not permissioned for 'CustomForm' solution," +
					" or there was an error on login");	}
		lnkViewDetails.click();
		return PageFactory.initElements(driver, ViewPatientFormPage.class);
	}
	
}
