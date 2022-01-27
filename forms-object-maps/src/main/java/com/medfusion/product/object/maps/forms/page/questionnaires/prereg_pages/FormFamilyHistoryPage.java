//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormFamilyHistoryPage extends PortalFormPage {

	public FormFamilyHistoryPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_familymedicalhistory")
	WebElement noFamilyHistory;

	@FindBy(id = "familymedicalhistory_other_field_autocomplete")
	WebElement familyMedical;

	@FindBy(id = "familymedicalhistory_other_field_familyMember")
	WebElement familyMember;

	@FindBy(xpath = "//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinueButton;

	public void setNoFamilyHistory() throws Exception {
		noFamilyHistory.click();
	}

	public void setRelation(String input) {
		Select selector = new Select(familyMember);
		selector.selectByVisibleText(input);
	}

	public FormSocialHistoryPage setFamilyHistory(String familyDetail, String relation) throws Exception {
		fillFamilyDetails(familyDetail);
		setRelation(relation);
		saveAndContinueButton.click();
		return PageFactory.initElements(driver, FormSocialHistoryPage.class);
	}

	public void fillFamilyDetails(String input) throws InterruptedException {
		familyMedical.clear();
		familyMedical.sendKeys(input);
		familyMedical.sendKeys(Keys.TAB);
		IHGUtil.waitForElement(driver, 20, autoComplete);
		autoComplete.click();
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Family Medical History")))
				.isDisplayed();
	}

}
