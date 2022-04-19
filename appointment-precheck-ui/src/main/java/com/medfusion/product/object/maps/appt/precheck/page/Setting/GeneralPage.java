// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.page.Setting;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
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
	
}