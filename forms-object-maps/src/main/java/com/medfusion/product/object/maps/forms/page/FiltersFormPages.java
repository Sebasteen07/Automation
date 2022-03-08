// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class FiltersFormPages extends BasePageObject {

	
	public FiltersFormPages(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	
	@FindBy(xpath = "//div[@id='s2id_locationFilter']/a[@class='select2-choice']")
	private WebElement locationComboBox;

	@FindBy(xpath = "//*[@id=\'select2-drop\']/ul/li[1]")
	private WebElement selectFirstLocationComboBox;

	@FindBy(xpath = "//input[@name='filterSubmit:submit']")
	private WebElement selectLocationButton;

	@FindBy(xpath = "//div[@id='s2id_providerFilter']/a[@class='select2-choice']")
	private WebElement providerComboBox;

	@FindBy(xpath = "//ul[@class='select2-results']/li[2]")
	private WebElement selectFirstProviderComboBox;

	@FindBy(xpath = "//input[@name='filterSubmit:submit']")
	private WebElement selectProviderButton;
	
	private static final By iFrameLocationComboBoxXpath = By.xpath("//div[@id='s2id_locationFilter']/a[@class='select2-choice']");	
	private static final By iFrameProviderComboBoxXpath = By.xpath("//div[@id='s2id_providerFilter']/a[@class='select2-choice']"); 
	
	/**
	 * Select Location DropDown
	 */
	public void selectFirstOptionLocationComboBox() {
		IHGUtil.PrintMethodName();
		locationComboBox.click();
		selectFirstLocationComboBox.click();
	}

	/**
	 * Submit Location Button
	 */
	public void selectLocationButton() {
		IHGUtil.PrintMethodName();
		javascriptClick(selectLocationButton);
	}

	/**
	 * Select Provider DropDown
	 */
	public void selectFirstOptionProviderComboBox() {
		IHGUtil.PrintMethodName();
		providerComboBox.click();
		selectFirstProviderComboBox.click();
	}

	/**
	 * Submit Provider Button
	 */
	public void selectProviderButton() {
		IHGUtil.PrintMethodName();
		javascriptClick(selectProviderButton);
	}

	/**
	 * If multiple locations and providers are configured for a pratice This method
	 * will select first option from dropdown and click on select button
	 */
	public HealthFormListPage selectfilterforms() throws InterruptedException {
		IHGUtil iHGUtil = new IHGUtil(driver);
		driver.switchTo().frame("iframe");

		if (iHGUtil.isRendered(iFrameLocationComboBoxXpath)) {
			log("Check if Location Dropdown and Location Button are available");
			log("Selecting Location ComboBox");
			selectFirstOptionLocationComboBox();
			selectLocationButton();
		}
		if (iHGUtil.isRendered(iFrameProviderComboBoxXpath)) {
			log("Check if Provider Dropdown and Location Button are available");
			log("Selecting Provider ComboBox");
			selectFirstOptionProviderComboBox();
			selectProviderButton();
		} else {
			driver.switchTo().defaultContent();
		}
		
		return PageFactory.initElements(driver, HealthFormListPage.class);
	}

}
