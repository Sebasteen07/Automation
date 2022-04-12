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
	private WebElement clickOnprecheckPage;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[1]")
	private WebElement addPatientModeCompletionMessagetextboxarea;
	
	@FindBy(how = How.XPATH, using = "//button[@id='preCheckSettingsFormSubmitButton']")
	private WebElement saveChanges;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='mf-form__input--text'])[4]")
	private WebElement addPhonenumber;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='mf-form__input--text'])[4]")
	private WebElement editPhonenumber;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='mf-form__input--text'])[4]")
	private WebElement clearPhonenumber;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[2]")
	private WebElement primaryInstructionsmessage;
	
	@FindBy(how = How.XPATH, using = "//button[text()='English']")
	private WebElement primaryInstructionsmessageinEnglish;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[2]")
	private WebElement clearprimaryInstructionsmessage;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Save changes']")
	private WebElement saveChangesbutton;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Spanish']")
	private WebElement clickprimaryInstructionsmessageinSpanish;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[3]")
	private WebElement primaryInstructionsmessageinSpanish;
	
	@FindBy(how = How.XPATH, using = "(//textarea[@class='mf-form__input--text-area'])[3]")
	private WebElement clearprimaryInstructionsmessageinSpanish;

	public PreCheckPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnprecheckPage() {
		IHGUtil.waitForElement(driver, 10, clickOnprecheckPage);
		jse.executeScript("arguments[0].click();", clickOnprecheckPage);
	}
	
	public void addPatientModeCompletionMessagetextboxarea(String patientmodeCompletionMessage) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, addPatientModeCompletionMessagetextboxarea);
		addPatientModeCompletionMessagetextboxarea.sendKeys(patientmodeCompletionMessage);
	}
	
	public void clearPatientModeCompletionMessagetextboxarea() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, addPatientModeCompletionMessagetextboxarea);
		addPatientModeCompletionMessagetextboxarea.clear();
	}
	
	public void clickOnsaveChanges() {
		IHGUtil.waitForElement(driver, 10, saveChanges);
		jse.executeScript("arguments[0].click();", saveChanges);
	}
	
	public boolean visibilityOfPatientmodecompletionmessage() {
		IHGUtil.waitForElement(driver, 10,addPatientModeCompletionMessagetextboxarea);
		if( addPatientModeCompletionMessagetextboxarea.isDisplayed()) {
			log("patient mode completion message is displayed");
		return true;
		}
		else {
			log("patient mode completion message is not displayed");
		return false;
		}
	}
	
	public void clearPhonenumber() {
		IHGUtil.waitForElement(driver, 10, clearPhonenumber);
		clearPhonenumber.clear();
	}
	
	public void addPhonenumber() {
		IHGUtil.waitForElement(driver, 10, addPhonenumber);
		addPhonenumber.sendKeys("9198822881");
	}
	
	public void editPhonenumber() {
		IHGUtil.waitForElement(driver, 10, editPhonenumber);
		editPhonenumber.sendKeys("9198822881");
	}
	
	public boolean visbilityofaddPhonenumber() {
		IHGUtil.waitForElement(driver, 10, addPhonenumber);
		if(editPhonenumber.isDisplayed()) {
			log("phone number is displayed");
			return true;
		}
		else {
			log("phone number is not displayed");
			return false;
		}
	}
	
	public boolean visbilityofeditPhonenumber() {
		IHGUtil.waitForElement(driver, 10, editPhonenumber);
		if(editPhonenumber.isDisplayed()) {
			log("edited phone number is displayed");
			return true;
		}
		else {
			log("edited phone number is not displayed");
			return false;
		}
	}
	
	public void primaryInstructionsmessage(String primaryinstructionmessage) {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsmessage);
		primaryInstructionsmessage.sendKeys(primaryinstructionmessage);
	}
	
	public void primaryInstructionsmessageinEnglish() {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsmessageinEnglish);
		primaryInstructionsmessageinEnglish.click();
	}
	
	public void clearprimaryInstructionsmessage() {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsmessage);
		primaryInstructionsmessage.clear();
	}
	
	public void saveChangesbutton() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, saveChangesbutton);
		saveChangesbutton.click();
		Thread.sleep(20000);
	}
	
	public void clickprimaryInstructionsmessageinSpanish() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, clickprimaryInstructionsmessageinSpanish);
		clickprimaryInstructionsmessageinSpanish.click();
	}
	
	public void primaryInstructionsmessageinSpanish(String primaryinstructionmessagespanish) {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsmessageinSpanish);
		primaryInstructionsmessageinSpanish.sendKeys(primaryinstructionmessagespanish);
	}
	
	public void clearprimaryInstructionsmessageinSpanish() {
		IHGUtil.waitForElement(driver, 10, clearprimaryInstructionsmessageinSpanish);
		clearprimaryInstructionsmessageinSpanish.clear();
	}
	
	public boolean visibilityofprimaryInstructionsmessageinEnglish() {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsmessageinEnglish);
		if(primaryInstructionsmessageinEnglish.isDisplayed()) {
			log("primary instructions displayed in english");
		return true;
		}
		else
		{
			log("primary instructions not displayed in english");
			return false;
		}
	}
	
	public boolean visibilityofprimaryInstructionsmessageinSpanish() {
		IHGUtil.waitForElement(driver, 10, primaryInstructionsmessageinSpanish);
		if(primaryInstructionsmessageinSpanish.isDisplayed()) {
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
