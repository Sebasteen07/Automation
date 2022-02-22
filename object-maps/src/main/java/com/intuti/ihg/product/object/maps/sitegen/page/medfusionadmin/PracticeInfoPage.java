package com.intuti.ihg.product.object.maps.sitegen.page.medfusionadmin;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.medfusion.common.utils.IHGUtil;

public class PracticeInfoPage extends BasePageObject {

	@FindBy(xpath = "//td[./text()='Enable Extended Gender Questions:']/following-sibling::td")
	private WebElement genderQuestionsEnabledCell;

	@FindBy(name = "edit")
	private WebElement editButton;

	@FindBy(name = "save")
	private WebElement saveButton;

	@FindBy(xpath = "//input[@name='patientPortal:piPortalURLName']")
	private WebElement portalUrlName;

	@FindBy(xpath = "//*[@id='portalInspiredUrlLink']")
	private WebElement portalUrlLink;

	@FindBy(name = "enableExtendedGender")
	private WebElement genderQuestionsCheckbox;

	@FindBy(className = "return")
	private WebElement returnToMainButton;

	public PracticeInfoPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean areGenderQuestionsEnabled() {
		if ("yes".equals(genderQuestionsEnabledCell.getText().trim().toLowerCase()))
			return true;
		return false;
	}

	public PracticeInfoPage enableGenderQuestions() {
		if (!areGenderQuestionsEnabled()) {
			edit();
			genderQuestionsCheckbox.click();
			saveEdit();
		}
		return this;
	}

	public PracticeInfoPage disableGenderQuestions() {
		if (areGenderQuestionsEnabled()) {
			edit();
			genderQuestionsCheckbox.click();
			saveEdit();
		}
		return this;
	}

	public SiteGenPracticeHomePage returnToPracticeHomePage() {
		returnToMainButton.click();
		return PageFactory.initElements(driver, SiteGenPracticeHomePage.class);
	}

	public void edit() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");
		editButton.click();
		IHGUtil.waitForElement(driver, 10, saveButton);
	}

	public void saveEdit() {
		saveButton.click();
	}

	public String getURLName() {
		return portalUrlName.getAttribute("value");
	}

	public String getPortalURL() {
		return portalUrlLink.getText();
	}

	public void urlTextBox(String newUrlName) {
		portalUrlName.clear();
		portalUrlName.sendKeys(newUrlName);

	}
}
