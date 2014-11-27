package com.intuit.ihg.product.object.maps.practice.page.customform;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewPatientFormPage extends BasePageObject {
	
	@FindBy(css = "td.searchResultsDetails > a")
	private WebElement lnkViewDetails;
	
	@FindBy(xpath = "//td[strong[contains(text(), 'Last updated on: ')]]")
	private WebElement lastUpdatedDate;

    @FindBy(xpath = "//td[strong[contains(text(), 'Last saved on: ')]]")
    private WebElement lastSavedDate;

	@FindBy(xpath = "//a[contains(text(), 'View as PDF')]")
	private WebElement linkPDF; 
	
	public String getDownloadURL() {
		return linkPDF.getAttribute("href");
	}
	
	public ViewPatientFormPage(WebDriver driver) {
		super(driver);
	}
	
	public String getLastUpdatedDateText() {
        try {
            return lastUpdatedDate.getText();
        } catch (NoSuchElementException e) {
            return lastSavedDate.getText();
        }
	}

	public String getLastUpdatedDateFormatted() throws IllegalStateException {
        // tries to find and return date from last updated date text
        Pattern pattern = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");
        Matcher matcher = pattern.matcher(getLastUpdatedDateText());

        if (matcher.find())
		    return matcher.group();
        else {
            IllegalStateException e = new IllegalStateException("No match found");
            throw e;
        }
	}
}
