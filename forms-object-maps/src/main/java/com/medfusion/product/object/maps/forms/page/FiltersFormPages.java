package com.medfusion.product.object.maps.forms.page;

import java.util.concurrent.TimeUnit;

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

	}

	@FindBy(xpath = "//form[@id='iframe']")
	private WebElement filtersForm;

	@FindBy(xpath = "//div[@id='s2id_locationFilter']/a[@class='select2-choice']")
	private WebElement locationComboBox;

	@FindBy(xpath = "//*[@id=\'select2-drop\']/ul/li[1]")
	private WebElement selectLocationComboBox;

	@FindBy(xpath = "//input[@name='filterSubmit:submit']")
	private WebElement selectLocationButton;

	@FindBy(xpath = "//div[@id='s2id_providerFilter']/a[@class='select2-choice']")
	private WebElement providerComboBox;

	@FindBy(xpath = "//ul[@class='select2-results']/li[2]")
	private WebElement selectProviderComboBox;

	@FindBy(xpath = "//input[@name='filterSubmit:submit']")
	private WebElement selectProviderButton;

	/**
	 * Select Location DropDown
	 */
	public void selectLocationComboBox() {
		IHGUtil.PrintMethodName();
		locationComboBox.click();
		selectLocationComboBox.click();
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
	public void selectProviderComboBox() {
		IHGUtil.PrintMethodName();
		providerComboBox.click();
		selectProviderComboBox.click();
	}
    
	/**
	 * Submit Provider Button
	 */
	public void selectProviderButton() {
		IHGUtil.PrintMethodName();
		javascriptClick(selectProviderButton);
	}

	/**
	 * If multiple locations and providers are configured for a pratice
	 * This method will select first option from dropdown and
	 * click on select button 
	 * @throws InterruptedException
	 */
	public void selectfilterforms() throws InterruptedException {
		Thread.sleep(3000);
		if (!IHGUtil.exists(driver, filtersForm)) {
			driver.switchTo().defaultContent();
		}
		driver.switchTo().frame("iframe");
		FiltersFormPages filtersFormPages = PageFactory.initElements(driver, FiltersFormPages.class);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log("Selecting Location ComboBox");
		filtersFormPages.selectLocationComboBox();
		filtersFormPages.selectLocationButton();
		log("Selecting Provider ComboBox");
		filtersFormPages.selectProviderComboBox();
		filtersFormPages.selectProviderButton();

	}
}
