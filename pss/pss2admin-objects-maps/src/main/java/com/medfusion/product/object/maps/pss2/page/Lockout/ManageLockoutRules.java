//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Lockout;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class ManageLockoutRules extends PSS2MenuPage {
	
	@FindBy(how = How.XPATH, using = "//a[@title='Add Lockout Rule']//*[name()='svg']")
	private WebElement addAlertsNotifications;
	
	@FindBy(how = How.XPATH, using = "//div[@class='col-lg-6 col-12 ng-untouched ng-pristine ng-valid ng-star-inserted']//header//legend[1]")
	private WebElement pageHeading;
	
	@FindBy(how = How.ID, using = "simple-select")
	private WebElement typeDropdown;
	
	@FindBy(how = How.XPATH, using = "//input[@id='name']")
	private WebElement ruleTextBox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='key']")
	private WebElement keyTextBox;	
	
	@FindBy(how = How.XPATH, using = "	//textarea[@id='lockout.messagesEN']")
	private WebElement messageBoxEn;
	
	@FindBy(how = How.CSS, using = "button[type='submit']")
	private WebElement saveLockout;

	@FindBy(how = How.XPATH, using = "//input[@value='ALERTS']")
	private WebElement alertCheckBox;

	@FindBy(how = How.XPATH, using = "//div[@aria-label='Please enter Alert Message']")
	private WebElement blankAlertErrorMessage;
	
	@FindBy(how = How.XPATH, using = "//div[@id='toast-container']/div")
	private WebElement successMessage;
	
	@FindAll({ @FindBy(css = ".nav-chang-color") })
	private List<WebElement> colorType;

	public ManageLockoutRules(WebDriver driver) {
		super(driver);
	}
	
	CommonMethods commonMethods= new CommonMethods(driver);	
	
	public void addAlertWithoutMsg() {

		commonMethods.highlightElement(addAlertsNotifications);
		addAlertsNotifications.click();
		commonMethods.highlightElement(pageHeading);

		log("Heading of the page- " + pageHeading.getText());

		alertCheckBox.click();
		commonMethods.highlightElement(alertCheckBox);

		Select s = new Select(typeDropdown);
		s.selectByValue("NOTES");

		ruleTextBox.sendKeys("Lockout without message");
		keyTextBox.sendKeys("Lockout without message");

		commonMethods.highlightElement(saveLockout);
		saveLockout.click();

		IHGUtil.waitForElement(driver, 2, blankAlertErrorMessage);
		String errorMsg = blankAlertErrorMessage.getText();

		log("Error Message- " + errorMsg);

	}
	
	public void addLockoutWithoutMsg() {

		commonMethods.highlightElement(addAlertsNotifications);
		addAlertsNotifications.click();
		commonMethods.highlightElement(pageHeading);

		log("Heading of the page- " + pageHeading.getText());

		Select s = new Select(typeDropdown);
		s.selectByValue("NOTES");

		ruleTextBox.sendKeys("Lockout without message");
		keyTextBox.sendKeys("Lockout without message");

		commonMethods.highlightElement(saveLockout);
		saveLockout.click();

		IHGUtil.waitForElement(driver, 2, successMessage);
		String successMsg = successMessage.getText();

		log("Success Message- " + successMsg);
	}

}
