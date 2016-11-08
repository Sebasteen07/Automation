package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class CustomFormPage extends BasePageObject {

	@FindBy(xpath = ".//input[@name = 'custom_form_name']")
	private WebElement customFormNameFiled;

	@FindBy(xpath = ".//ul/li[@data-section='customfirst_section']/a")
	private WebElement firstSectionElement;

	@FindBy(xpath = ".//ul/li[@data-section='customsecond_section']/a")
	private WebElement secondSectionElement;

	@FindBy(xpath = ".//ul/li[@data-section='customthird_section']/a")
	private WebElement thirdSectionElement;

	@FindBy(xpath = ".//a[@id = 'save_config_form']")
	private WebElement saveFormButton;

	@FindBy(id = "save_config_form_floating")
	private WebElement saveFormButtonFloating;

	@FindBy(css = ".backFloating")
	private WebElement backToTheListButtonFloating;

	@FindBy(css = ".back")
	private WebElement backToTheListButton;

	@FindBy(id = "loading")
	private WebElement loadingNotification;

	private CustomFormPageSection firstSection;
	private CustomFormPageSection secondSection;
	private CustomFormPageSection thirdSection;
	private IHGUtil utils;

	public CustomFormPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		utils = new IHGUtil(driver);
		jse = (JavascriptExecutor) driver;
		firstSection = new CustomFormPageSection(driver, "first");
		secondSection = new CustomFormPageSection(driver, "second");
		thirdSection = new CustomFormPageSection(driver, "third");
	}

	public CustomFormPageSection getFirstSection() {
		return firstSection;
	}

	public CustomFormPageSection getSecondSection() {
		return secondSection;
	}

	public CustomFormPageSection getThirdSection() {
		return thirdSection;
	}

	public void clickOnSection(int sectionNumber) {
		switch (sectionNumber) {
		case 1:
			firstSectionElement.click();
			break;
		case 2:
			secondSectionElement.click();
			break;
		case 3:
			thirdSectionElement.click();
		}
	}

	public void saveForm() throws InterruptedException {
		Thread.sleep(1000);
		scrollAndWait(0, 0, 500);
		saveFormButton.click();
		utils.waitForElementToDisappear(loadingNotification, 500, 20);
	}

	public void leaveFormPage() throws InterruptedException {
		scrollAndWait(0, 0, 500);
		backToTheListButton.click();
	}
}
