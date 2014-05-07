package com.intuit.ihg.product.practice.page.customform;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class ViewPatientFormPage extends BasePageObject {
	
	@FindBy(css = "td.searchResultsDetails > a")
	private WebElement lnkViewDetails;
	
	@FindBy(xpath = "//td[strong[contains(text(), 'Last updated on: ')]]")
	private WebElement lastUpdatedDate;
	
	@FindBy(xpath = "//a[contains(text(), 'View as PDF')]")
	private WebElement linkPDF; 
	
	public String getDownloadURL() {
		return linkPDF.getAttribute("href");
	}
	
	public ViewPatientFormPage(WebDriver driver)
	{
		super(driver);
	}
	
	public WebElement getLastUpdatedDate()
	{
		return lastUpdatedDate;
	}

}
