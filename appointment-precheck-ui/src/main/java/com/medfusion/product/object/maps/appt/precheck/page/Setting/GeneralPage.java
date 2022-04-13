// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.page.Setting;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.appt.precheck.util.BaseTest;

public class GeneralPage extends BaseTest {

	@FindBy(how = How.XPATH, using = "//*[text()='General settings']")
	private WebElement generalSetting;

	@FindBy(how = How.XPATH, using = "//*[@id=\"react-tabs-1\"]/section/div/h2")
	private WebElement manageSolution;

	@FindBy(how = How.CSS, using = "#react-tabs-1 > section > div > div > h3:nth-child(2) > span")
	private WebElement notificationsCheckbox;

	@FindBy(how = How.XPATH, using = "//*[text()='Update settings']")
	private WebElement updateSettingButton;
	
	@FindBy(how = How.CSS, using = "#react-tabs-1 > section > div > div > div > h3:nth-child(2) > span")                   
	private WebElement textCheckbox;
	
	@FindBy(how = How.XPATH, using = "//*[text()='General']")
	private WebElement generalTab;
	
	@FindBy(how = How.CSS, using = "#react-tabs-1 > section > div > div > div > h3:nth-child(1) > span")
	private WebElement emailCheckbox;
	
	@FindBy(how = How.XPATH, using ="//div[@class='mf-mid-page-tabs']//ul//li[contains(text(),'Manage solutions')]")
	private WebElement manageSolutionTab;
	
	@FindBy(how=How.XPATH, using ="//input[@name='displayName']")
	private WebElement practiceDisplayName;
	
	@FindBy(how = How.XPATH, using = "//label[contains(text(),'This is a required field. Please enter a valid display name')]")
	private WebElement practiceDisplayNameError;
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Forms')]")
	private WebElement formsTab;
	
	@FindBy(how=How.XPATH, using ="//li[text() ='Logo']")
	private WebElement logoTab;
	
	@FindBy(how=How.XPATH, using ="(//label[@class='mf-file-input'])[2]")
	private WebElement uploadButton;

	@FindBy(how=How.XPATH, using ="//img[@class='cadence-img']")
	private WebElement logoImage;
	
	@FindBy(how=How.XPATH, using ="//li[text()='Providers']")
	private WebElement clickOnProvidersTab;
	
	@FindBy(how=How.XPATH, using ="//button[@id='primaryAddanewprovider']")
	private WebElement clickOnAddnewProviderButton;
	
	@FindBy(how=How.XPATH, using ="//input[@id='providerId']")
	private WebElement providerId;
	
	@FindBy(how=How.XPATH, using ="//input[@id='firstName']")
	private WebElement firstName;
	
	@FindBy(how=How.XPATH, using ="//input[@id='middleName']")
	private WebElement middleName;
	
	@FindBy(how=How.XPATH, using ="//input[@id='lastName']")
	private WebElement lastName;
	
	@FindBy(how=How.XPATH, using ="//input[@id='title']")
	private WebElement title;
	
	@FindBy(how=How.XPATH, using ="//input[@value='Save']")
	private WebElement saveButton;
	
	@FindBy(how=How.XPATH, using ="//input[@class='mf-form__input--text']")
	private WebElement Providerfilter;
	
	@FindBy(how=How.XPATH, using ="(//span[@class='mf-icon mf-icon__x mf-color__negative'])[1]")
	private WebElement deleteProvider;
	
	@FindBy(how=How.XPATH, using ="//label[contains(text(),'Choose file')]")
	private WebElement chooseFile;
	
	@FindBy(how=How.XPATH, using ="//button[@id='undefinedRemove']")
	private WebElement clickOnRemoveButton;
	
	@FindBy(how=How.XPATH, using ="//button[@class='mf-cta__secondary']")
	private WebElement chooseFileforProvider;

	private static GeneralPage generalPage = new GeneralPage();

	public GeneralPage() {
		PageFactory.initElements(driver, this);
	}

	public static GeneralPage getGeneralPage() {
		return generalPage;
	}

	public String generalSettingTitle() {
		IHGUtil.waitForElement(driver, 10, generalSetting);
		return generalSetting.getText();
	}

	public String manageSolutionTab() {
		return manageSolution.getText();
	}

	public void uncheckingNotificationsCheckbox() {
		IHGUtil.waitForElement(driver, 5, notificationsCheckbox);
		notificationsCheckbox.click();
	}

	public void clickOnUpdateSettingbutton() throws InterruptedException {
		updateSettingButton.click();
		Thread.sleep(5000);
	}
	
	public void enableAndDisableTextCheckbox() {
		driver.navigate().refresh();
		IHGUtil.waitForElement(driver, 10, textCheckbox);
		boolean selected = textCheckbox.isSelected();
		if (selected) {
			textCheckbox.click();
		}else if(!selected){
			textCheckbox.click();
		}
	}
	public void clickOnGeneralTab() throws InterruptedException {
		generalTab.click();
		log("Switch on general tab");
		Thread.sleep(5000);
	}
	
	public void enableAndDisableEmailCheckbox() {
		driver.navigate().refresh();
		IHGUtil.waitForElement(driver, 10, emailCheckbox);
		boolean selected = emailCheckbox.isSelected();
		if (selected) {
			emailCheckbox.click();
			log("Deselect Notification checkbox");
		}else if(!selected){
			emailCheckbox.click();
			log("Select Notification checkbox");
		}
	}
	
	public void enableTextCheckbox() {
		driver.navigate().refresh();
		IHGUtil.waitForElement(driver, 10, textCheckbox);
		boolean selected = textCheckbox.isSelected();
		if (selected) {
			log("Text checkbox is already enable");
		}else if(!selected){
			textCheckbox.click();
			log("Enable Text checkbox");
		}
	}
	public void enableEmailCheckbox() {
		driver.navigate().refresh();
		IHGUtil.waitForElement(driver, 10, emailCheckbox);
		boolean selected = emailCheckbox.isSelected();
		if (selected) {
			log("Email checkbox is already enable");
		}else if(!selected){
			emailCheckbox.click();
			log("Enable Email checkbox");
		}
	}
	
	public void clickOnTextCheckbox() {
		driver.navigate().refresh();
		IHGUtil.waitForElement(driver, 10, textCheckbox);
			textCheckbox.click();
			log("Disable text checkbox");
	}
	
	public void clickOnManageSolutionsTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, manageSolutionTab);
		manageSolutionTab.click();
		log("Switch on Manage Solutions tab");
		driver.navigate().refresh();
	}
	
	public void clearPracticeDisplayName() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, practiceDisplayName);
		log("Practice display name is displayed.");
		practiceDisplayName.clear();
	}
	
	public String visibilityOfPracticeDisplayNameError() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, practiceDisplayNameError);
		log("Error message is displayed");
		return practiceDisplayNameError.getText();
	}
	
	public void clickOnFormsTab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, formsTab);
		formsTab.click();
		Thread.sleep(5000);
	}
	
	public String getFormsText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, formsTab);
		return formsTab.getText();
	}
	
	public String visibilityOfPracticeDisplayName() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, practiceDisplayName);
		log("Practice display name is displayed.");
		return practiceDisplayName.getAttribute("value");
	}
	
	public void savePracticeDisplayName() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, practiceDisplayName);
		log("Practice display name is displayed.");
		practiceDisplayName.sendKeys(" ");
		
	}
	
	public void clickOnlogoTab() throws InterruptedException {
		IHGUtil.waitForElement(driver, 5, logoTab);
		logoTab.click();
	}
	
	public void chooseFileforLogo(String imagePath) throws InterruptedException, AWTException {
		IHGUtil.PrintMethodName();
		Actions act = new Actions(driver);
		act.moveToElement(chooseFile).click().perform();
		Thread.sleep(2000);
		uploadFile(System.getProperty("user.dir")+imagePath);
	}
	
	public void setClipboard(String file) {
		StringSelection obj = new StringSelection(file);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(obj, null);
	}
	
	public void uploadFile(String filePath) throws AWTException {
		setClipboard(filePath);
		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void clickOnuploadButton() {
		IHGUtil.waitForElement(driver, 10, uploadButton);
		uploadButton.click();
	}
	
	public boolean visibilityOfLogoImage() {
		IHGUtil.PrintMethodName();
		if(logoImage.isDisplayed()) {
			log("logo image is changed");
			return true;
		}
		else {
			log("logo image is not changed");
			return false;
			
		}
		
	}
	
	public void clickOnProvidersTab() {
		IHGUtil.waitForElement(driver, 10, clickOnProvidersTab);
		clickOnProvidersTab.click();
	}
	
	public void clickOnAddnewProviderbutton() {
		IHGUtil.waitForElement(driver, 10, clickOnAddnewProviderButton);
		clickOnAddnewProviderButton.click();
	}
	
	public void providerId(String providerid) throws IOException {
		IHGUtil.waitForElement(driver, 10, providerId);
		providerId.sendKeys(providerid);
	}
	
	public void firstName(String firstname) throws IOException {
		IHGUtil.waitForElement(driver, 10, firstName);
		firstName.sendKeys(firstname);
	}
	
	public void middleName(String middlename) {
		IHGUtil.waitForElement(driver, 10, middleName);
		middleName.sendKeys(middlename);
	}
	
	public void lastName(String lastname) {
		IHGUtil.waitForElement(driver, 10, lastName);
		lastName.sendKeys(lastname);
	}
	
	public void title(String providertitle) {
		IHGUtil.waitForElement(driver, 10, title);
		title.sendKeys(providertitle);
	}
	public void saveButton() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, saveButton);
		saveButton.click();
		Thread.sleep(5000);
	}
	
	public void Providerfilter() {
		IHGUtil.waitForElement(driver, 10, Providerfilter);
		Providerfilter.sendKeys("James");
	}
	public void deleteProvider() {
		IHGUtil.waitForElement(driver, 10, deleteProvider);
		deleteProvider.click();
	}
	
	public void clickOnRemoveButton() {
		IHGUtil.waitForElement(driver, 10, clickOnRemoveButton);
		clickOnRemoveButton.click();
	}
	
	public boolean visibilityofProviderfilter() {
		IHGUtil.waitForElement(driver, 10, Providerfilter);
		if(Providerfilter.isDisplayed())
		{
			log("new provider is added");
			return true;
		}
		else {
			log("new provider not added");
			return false;
		}
	}
	
	public boolean visibilityofdeletedProvider() {
		IHGUtil.waitForElement(driver, 10, Providerfilter);
		if(Providerfilter.isDisplayed())
		{
			log("provider deleted");
			return true;
		}
		else {
			log("provider is not deleted");
			return false;
		}
	}

	public void providerImage(String providerImagePath) throws InterruptedException, AWTException {
		IHGUtil.PrintMethodName();
		Actions act = new Actions(driver);
		act.moveToElement(chooseFileforProvider).click().perform();
		Thread.sleep(2000);
		uploadFile(System.getProperty("user.dir")+providerImagePath);
	}
	
	public void addPracticeDisplayName(String practiceName) throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, practiceDisplayName);
		practiceDisplayName.sendKeys(practiceName);
	}


	
}
