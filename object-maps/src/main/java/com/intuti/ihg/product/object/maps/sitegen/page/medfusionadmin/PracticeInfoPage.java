package com.intuti.ihg.product.object.maps.sitegen.page.medfusionadmin;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenPracticeHomePage;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;

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
	
	@FindBy(name = "associatedEnterprise")
	private WebElement associatedEnterprise;
	
	@FindBy(xpath ="//*[@value=\"Confirm Your Changes\"]")
	private WebElement btnConfirmChanges;
	
	@FindBy(xpath="//div[@class='content']//table//tr//td[@id='associatedEnterprise']")
	private WebElement existingEnterpriseName;

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
	
	public void updateEnterprise()throws InterruptedException, IOException {
	IHGUtil.PrintMethodName();
	PropertyFileLoader testData = new PropertyFileLoader();

		Select newEnterprise = new Select(associatedEnterprise);
		newEnterprise.selectByVisibleText(testData.getProperty("enterpriseTwo"));

	
	log("Click on Confirm your changes to save");
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript("window.scrollBy(0,250)", "");
	IHGUtil.waitForElement(driver, 10, btnConfirmChanges);
	btnConfirmChanges.click();	
	
}
}
