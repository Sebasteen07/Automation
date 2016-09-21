package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

/**
 * @author Adam Warzel @ Date 29/10.2014.
 */
public class ConfiguratorFormPage extends BasePageObject {

	@FindBy(className = "back")
	private WebElement backToTheList;

	@FindBy(id = "save_config_form")
	private WebElement saveButton;

	@FindBy(xpath = "//button[@class='green yes backToList']")
	private WebElement dialogSaveButton;

	@FindBy(xpath = "//a[@class='button blue backFloating']")
	private WebElement floatingBackButton;

	@FindBy(id = "save_config_form_floating")
	private WebElement floatingSaveButton;

	@FindBy(xpath = "//button[@class='blue no backToList']")
	private WebElement dialogCloseFormButton;

	@FindBy(xpath = "//a[@class='closeDialog red']")
	private WebElement closeDialogButton;

	@FindBy(id = "loading")
	private WebElement loadingNotification;

	public ConfiguratorFormPage(WebDriver driver) {
		super(driver);
		jse = (JavascriptExecutor) driver;
	}

	/*
	 * In case the screen is scrolled down it is possible that save button and back link are not visible when that happens we can use the floating panel with save
	 * and back buttons
	 */

	public void clickBackToTheList() throws InterruptedException {
		scrollAndWait(0, 0, 500);
		backToTheList.click();
	}

	public void clickSaveButton() throws InterruptedException {
		scrollAndWait(0, 0, 500);
		saveButton.click();
	}

	public void clickDialogSaveButton() {
		dialogSaveButton.click();
	}

	public void clickDialogCloseFormButton() {
		dialogCloseFormButton.click();
	}

	public void clickCloseDialogButton() {
		closeDialogButton.click();
	}

	public ConfiguratorFormPage saveOpenedForm() throws InterruptedException {
		IHGUtil utils = new IHGUtil(driver);
		// it's passing here too quickly so we need explicit wait
		Thread.sleep(5000);
		clickSaveButton();
		utils.waitForElementToDisappear(loadingNotification, 2000, 10);

		return this;
	}

}
