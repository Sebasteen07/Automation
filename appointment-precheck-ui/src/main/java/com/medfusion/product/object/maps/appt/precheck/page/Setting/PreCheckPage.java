package com.medfusion.product.object.maps.appt.precheck.page.Setting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PreCheckPage extends BasePageObject {
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'PreCheck')]")
	private WebElement clickOnPrecheckPage;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[1]")
	private WebElement  addPatientModeCompletionMessageTextBoxArea;
	
	@FindBy(how = How.XPATH, using = "//button[@id='preCheckSettingsFormSubmitButton']")
	private WebElement saveChanges;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='mf-form__input--text'])[4]")
	private WebElement addPhoneNumber;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='mf-form__input--text'])[4]")
	private WebElement editPhoneNumber;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='mf-form__input--text'])[4]")
	private WebElement clearPhoneNumber;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[2]")
	private WebElement primaryInstructionsMessage;
	
	@FindBy(how = How.XPATH, using = "//button[text()='English']")
	private WebElement primaryInstructionsMessageInEnglish;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[2]")
	private WebElement clearprimaryInstructionsMessage;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Save changes']")
	private WebElement saveChangesButton;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Spanish']")
	private WebElement clickprimaryInstructionsMessageinSpanish;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[3]")
	private WebElement primaryInstructionsMessageinSpanish;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[3]")
	private WebElement clearprimaryInstructionsMessageinSpanish;

	public PreCheckPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnPrecheckPage() {
		IHGUtil.waitForElement(driver, 10, clickOnPrecheckPage);
		jse.executeScript("arguments[0].click();", clickOnPrecheckPage);
	}
	
	public void  addPatientModeCompletionMessageTextBoxArea(String patientmodeCompletionMessage) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10,  addPatientModeCompletionMessageTextBoxArea);
		 addPatientModeCompletionMessageTextBoxArea.sendKeys(patientmodeCompletionMessage);
	}
	
	public void clearPatientModeCompletionMessagetextboxarea() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10,  addPatientModeCompletionMessageTextBoxArea);
		 addPatientModeCompletionMessageTextBoxArea.clear();
	}
	
	public void clickOnsaveChanges() {
		IHGUtil.waitForElement(driver, 10, saveChanges);
		jse.executeScript("arguments[0].click();", saveChanges);
	}
	
	public boolean visibilityOfPatientmodecompletionmessage() {
		IHGUtil.waitForElement(driver, 10, addPatientModeCompletionMessageTextBoxArea);
		if(  addPatientModeCompletionMessageTextBoxArea.isDisplayed()) {
			log("patient mode completion message is displayed");
		return true;
		}
		else {
			log("patient mode completion message is not displayed");
		return false;
		}
	}
	
	public void clearPhoneNumber() {
		IHGUtil.waitForElement(driver, 10, clearPhoneNumber);
		clearPhoneNumber.clear();
	}
	
	public void addPhoneNumber(String phonenumber) {
		IHGUtil.waitForElement(driver, 10, addPhoneNumber);
		addPhoneNumber.sendKeys(phonenumber);
	}
	
	public void editPhoneNumber(String phonenumber) {
		IHGUtil.waitForElement(driver, 10, editPhoneNumber);
		editPhoneNumber.sendKeys(phonenumber);
	}
	
	public boolean visbilityofaddPhoneNumber() {
		IHGUtil.waitForElement(driver, 10, addPhoneNumber);
		if(editPhoneNumber.isDisplayed()) {
			log("phone number is displayed");
			return true;
		}
		else {
			log("phone number is not displayed");
			return false;
		}
	}
	
	public boolean visbilityofeditPhoneNumber() {
		IHGUtil.waitForElement(driver, 10, editPhoneNumber);
		if(editPhoneNumber.isDisplayed()) {
			log("edited phone number is displayed");
			return true;
		}
		else {
			log("edited phone number is not displayed");
			return false;
		}
	}
	
	public void primaryInstructionsMessage(String primaryinstructionmessage) {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsMessage);
		primaryInstructionsMessage.sendKeys(primaryinstructionmessage);
	}
	
	public void primaryInstructionsMessageInEnglish() {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsMessageInEnglish);
		primaryInstructionsMessageInEnglish.click();
	}
	
	public void clearprimaryInstructionsmessage() {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsMessage);
		primaryInstructionsMessage.clear();
	}
	
	public void saveChangesButton() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, saveChangesButton);
		saveChangesButton.click();
		Thread.sleep(20000);
	}
	
	public void clickprimaryInstructionsMessageinSpanish() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, clickprimaryInstructionsMessageinSpanish);
		clickprimaryInstructionsMessageinSpanish.click();
	}
	
	public void primaryInstructionsmessageinSpanish(String primaryinstructionmessagespanish) {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsMessageinSpanish);
		primaryInstructionsMessageinSpanish.sendKeys(primaryinstructionmessagespanish);
	}
	
	public void clearprimaryInstructionsmessageinSpanish() {
		IHGUtil.waitForElement(driver, 10, clearprimaryInstructionsMessageinSpanish);
		clearprimaryInstructionsMessageinSpanish.clear();
	}
	
	public boolean visibilityofprimaryInstructionsMessageInEnglish() {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsMessageInEnglish);
		if(primaryInstructionsMessageInEnglish.isDisplayed()) {
			log("primary instructions displayed in english");
		return true;
		}
		else
		{
			log("primary instructions not displayed in english");
			return false;
		}
	}
	
	public boolean visibilityofprimaryInstructionsMessageinSpanish() {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsMessageinSpanish);
		if(primaryInstructionsMessageinSpanish.isDisplayed()) {
			log("primary instructions displayed in spanish");
			return true;
		}
		else
		{
			log("primary instructions not displayed in spanish");
			return false;
		}
	}
	
}
