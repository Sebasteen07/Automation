//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.MerchantAccount;

import java.awt.AWTException;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayed;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class MerchantAccountSetUpPage extends BasePageObject {

	@FindBy(xpath = "//*[@name='merchAcctTypeWrapper:radioFilter'][1]")
	private WebElement practiceRadioButton;

	@FindBy(xpath = ".//select[@name='processor']")
	private WebElement processorDropDown;

	@FindBy(xpath = ".//select[@name='Partner']")
	private WebElement partnerDropDown;

	@FindBy(xpath = ".//input[@name='buttons:submit']")
	private WebElement saveChangesButton;

	@FindBy(xpath = ".//span[@class='feedbackPanelINFO']")
	private WebElement successMsg;

	@FindBy(xpath = ".//input[@name = 'qbmsFields:testTokenWrapper:merchantTestToken']")
	private WebElement tokenQbmsTestField;

	@FindBy(xpath = ".//select[@name='Status']")
	private WebElement statusField;

	@FindBy(xpath = ".//input[@name='payPalFields:username']")
	private WebElement payPalUsername;

	@FindBy(xpath = ".//input[@name='payPalFields:password']")
	private WebElement payPalPassword;

	@FindBy(xpath = ".//select[@name='payPalFields:Partner']")
	private WebElement payPalPartner;

	@FindBy(xpath = ".//input[@name = 'qbmsFields:tokenWrapper:merchantToken']")
	private WebElement tokenQbmsFields;

	public MerchantAccountSetUpPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void clickOnPracticeRadioButton() throws Exception {
		IHGUtil.PrintMethodName();
		waitForElement(practiceRadioButton, 30);
		practiceRadioButton.click();
	}

	public void selectProcessorValue(String value) throws InterruptedException, AWTException {
		IHGUtil.PrintMethodName();
		log("Waiting for page to load");
		Thread.sleep(4000);
		Select sel = new Select(processorDropDown);
		sel.selectByVisibleText(value);
		SitegenlUtil sUtil = new SitegenlUtil(driver);
		sUtil.pressTabKey();
		log("After selecting the processor value" + value);
	}

	public void selectPartnerValue(String value) {
		IHGUtil.PrintMethodName();
		Select sel = new Select(payPalPartner);
		sel.selectByVisibleText(value);
	}

	public void enterUsername(String userName) throws Exception {
		IHGUtil.PrintMethodName();
		payPalUsername.click();
		payPalUsername.sendKeys(userName);

	}

	public void enterPassword(String password) throws Exception {
		IHGUtil.PrintMethodName();
		waitForElement(payPalPassword, 30);
		payPalPassword.sendKeys(password);
	}

	public void clickOnSaveChanges() throws Exception {
		waitForElement(saveChangesButton, 30);
		saveChangesButton.click();
	}

	public String getAccountAddedSuccessMsg() {
		String text = "";
		try {
			text = successMsg.getText();
			log("Success Message From application :" + text);
		} catch (Exception e) {
			log("Couldn't read the success message");
		}
		return text;
	}

	public void enterMerchantToken(String token) throws Exception {
		waitForElement(tokenQbmsFields, 30);
		tokenQbmsFields.clear();
		this.tokenQbmsFields.sendKeys(token);
	}

	public void enterMerchantTestToken(String token) throws Exception {

		waitForElement(tokenQbmsTestField, 30);
		tokenQbmsTestField.clear();
		tokenQbmsTestField.sendKeys(token);
	}

	public void selectStatus(String value) throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(4000);
		for (int i = 0; i != 2; i++) {
			try {
				IHGUtil.waitForElement(driver, 20, statusField);
				Select sel = new Select(statusField);
				sel.selectByVisibleText(value);
				break;
			} catch (StaleElementReferenceException e) {
			}
		}
	}

	public boolean waitForElement(WebElement we, int Timeout_Seconds) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Timeout_Seconds);
		return wait.until(new WaitForWEIsDisplayed(we));
	}
}
