package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormFamilyHistoryPage extends PortalFormPage {

	public FormFamilyHistoryPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "idonot_familymedicalhistory")
	WebElement noFamilyHistory;

	@FindBy(id = "familymedicalhistory_other_field_autocomplete")
	WebElement familymedical;

	@FindBy(id = "familymedicalhistory_other_field_familyMember")
	WebElement familymember;
	
	@FindBy(xpath = "//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;
	/**
	 * @Description: Set No Family History
	 */
	public void setNoFamilyHistory() throws Exception {
		noFamilyHistory.click();
	}
	public void setrelation(String input)
	{

		Select selector=new Select(familymember);
		selector.selectByVisibleText(input);
	}
	public FormSocialHistoryPage SetFamilyHistory(String input, String input1) throws Exception
	{
		fillfamilydetails(input);
		setrelation(input1);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormSocialHistoryPage.class);
	}
	
	public void fillfamilydetails(String input) throws InterruptedException
	{
		familymedical.clear();
		familymedical.sendKeys(input);
		familymedical.sendKeys(Keys.TAB);
		Thread.sleep(2000);
		autoComplete.click();
	}
	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Family Medical History"))).isDisplayed();
	}

}
