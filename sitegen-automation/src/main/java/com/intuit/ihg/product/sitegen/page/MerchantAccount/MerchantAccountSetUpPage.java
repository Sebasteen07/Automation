package com.intuit.ihg.product.sitegen.page.MerchantAccount;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayed;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class MerchantAccountSetUpPage extends BasePageObject{

	@FindBy(xpath = ".//input[@value='radio8']")
	private WebElement practiceRadioButton;

	@FindBy(xpath = ".//select[@name='processor']")
	private WebElement processorDropDown;

	@FindBy(xpath = ".//input[@name='username']")
	private WebElement userNameField;

	@FindBy(xpath = ".//input[@name='password']")
	private WebElement passwordField;

	@FindBy(xpath = ".//select[@name='Partner']")
	private WebElement partnerDropDown;

	@FindBy(xpath = ".//input[@name='buttons:submit']")
	private WebElement saveChangesButton;

	@FindBy( xpath = ".//span[@class='feedbackPanelINFO']")
	private WebElement successMsg;

	@FindBy (xpath = ".//input[@name = 'tokenWrapper:merchantToken']")
	private WebElement tokenField;

	@FindBy (xpath = ".//input[@name = 'testTokenWrapper:merchantTestToken']" )
	private WebElement tokenTestFeild;

	@FindBy(xpath = ".//select[@name='Status']")
	private WebElement statusField;

	@FindBy(xpath = ".//input[@name='payPalFields:username']")
	private WebElement userNameFieldDEV3;

	@FindBy(xpath = ".//input[@name='payPalFields:password']")
	private WebElement passwordFieldDEV3;

	@FindBy(xpath = ".//select[@name='payPalFields:Partner']")
	private WebElement partnerDropDownDEV3;

	@FindBy( xpath = ".//input[@name = 'qbmsFields:tokenWrapper:merchantToken']")
	private WebElement tokenFieldDEV3;

	public MerchantAccountSetUpPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}


	/**
	 * @throws Exception 
	 * 
	 */
	public void clickOnPracticeRadioButton() throws Exception {
		IHGUtil.PrintMethodName();
		waitForElement(practiceRadioButton, 30);
		practiceRadioButton.click();	
	}

	/**
	 * 
	 * @param value
	 * @throws InterruptedException 
	 * @throws AWTException 
	 */
	public void selectProcessorValue(String value) throws InterruptedException, AWTException{
		IHGUtil.PrintMethodName();	
		Select sel = new Select(processorDropDown);
		sel.selectByVisibleText(value);
		SitegenlUtil sUtil = new SitegenlUtil(driver);
		sUtil.pressTabKey();
	}

	/**
	 * Selet Partner Dropdown value
	 * @param value
	 */
	public void selectPartnerValue(String value){
		IHGUtil.PrintMethodName();
		if(IHGUtil.getEnvironmentType().toString().equalsIgnoreCase("DEV3")) {
			Select sel = new Select(partnerDropDownDEV3);
			sel.selectByVisibleText(value);
		}else{
			Select sel = new Select(this.partnerDropDown);
			sel.selectByVisibleText(value);
		}
	}

	/**
	 * To enter user name
	 * @param value
	 * @throws Exception 
	 */
	public void enterUsername(String userName) throws Exception{
		IHGUtil.PrintMethodName();
		if(IHGUtil.getEnvironmentType().toString().equalsIgnoreCase("DEV3")) {
			userNameFieldDEV3.click();
			userNameFieldDEV3.sendKeys(userName);
		}else{
			userNameField.click();
			userNameField.sendKeys(userName);
		}
	}

	/**
	 * To enter password
	 * @param value
	 * @throws Exception 
	 */
	public void enterPassword(String password) throws Exception{
		IHGUtil.PrintMethodName();
		if(IHGUtil.getEnvironmentType().toString().equalsIgnoreCase("DEV3")) {
			waitForElement(passwordFieldDEV3, 30);
			passwordFieldDEV3.sendKeys(password);	
		}else{
			waitForElement(passwordField, 30);
			passwordField.sendKeys(password);	
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void clickOnSaveChanges() throws Exception { 
		waitForElement(saveChangesButton, 30);
		saveChangesButton.click();
	}

	/**
	 * 
	 * @return
	 */
	public String getAccountAddedSuccessMsg() {
		String text ="";
		try{
			text = successMsg.getText();
			log("Success Message From application :"+text);
		}catch (Exception e) {
			log("Couldn't read the success message");
		}
		return text;
	}

	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	public void enterMerchantToken(String token) throws Exception {


		if(IHGUtil.getEnvironmentType().toString().equalsIgnoreCase("DEV3")) {
			waitForElement(tokenFieldDEV3, 30);
			tokenFieldDEV3.clear();
			this.tokenFieldDEV3.sendKeys(token);
		}else {
			waitForElement(tokenField, 30);
			tokenField.clear();
			tokenField.sendKeys(token);
		}

	}

	/**
	 * 
	 * @param token
	 * @throws Exception
	 */
	public void enterMerchantTestToken(String token) throws Exception {

		waitForElement(tokenField, 30);
		tokenTestFeild.clear();
		tokenTestFeild.sendKeys(token);
	}

	/**
	 * 
	 * @param value
	 */
	public void selectStatus(String value) {
		IHGUtil.PrintMethodName();
		Select sel = new Select(statusField);
		sel.selectByVisibleText(value);
	}

	/**
	 * 
	 * @param we :WebElement
	 * @param Timeout_Seconds : Preferred Waiting Time in Seconds.
	 * @return
	 * @throws Exception
	 */
	public boolean waitForElement(WebElement we, int Timeout_Seconds)throws Exception{
		WebDriverWait wait = new WebDriverWait(driver, Timeout_Seconds);
		return wait.until(new WaitForWEIsDisplayed(we));
	}
}
