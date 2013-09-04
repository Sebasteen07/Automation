package com.intuit.ihg.product.practice.page.customform;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class ViewPatientFormPage extends BasePageObject {
	
	@FindBy(css="td.searchResultsDetails > a")
	private WebElement lnkViewDetails;

	
	public ViewPatientFormPage(WebDriver driver) {
		super(driver);
	}

}
