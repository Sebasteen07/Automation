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
}
