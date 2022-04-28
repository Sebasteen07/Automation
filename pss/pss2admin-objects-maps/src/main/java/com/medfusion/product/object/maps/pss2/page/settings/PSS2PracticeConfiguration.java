// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class PSS2PracticeConfiguration extends SettingsTab {

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/div/main/div[2]/div/div/div/section/div/div/div[2]/div[1]/div[1]/div[1]/img")
	private WebElement clientlogo;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/nav[1]/div/div[1]/header/a/img")
	private WebElement companyLogo;

	@FindBy(how = How.CSS, using = ".badge.badge-primary")
	private WebElement practiceText;

	@FindAll({ @FindBy(css = ".nav-chang-color") })
	private List<WebElement> colorType;

	@FindBy(how = How.NAME, using = "zone")
	private WebElement clientTimeZone;

	@FindBy(how = How.ID, using = "simple-select")
	private WebElement partnerName;
	
	@FindBy(how = How.XPATH, using = "//*[@id='basic']/form/div[3]/div/div[2]/input")
	private WebElement spanishLanguageLabel;

	@FindAll({ @FindBy(xpath = "//*[@id=\"simple-select\"]/option") })
	private List<WebElement> partnerOptionValue;

	@FindBy(how = How.ID, using = "frompractice")
	private WebElement businessHoursStartTime;

	@FindBy(how = How.ID, using = "topractice")
	private WebElement businessHoursEndTime;

	@FindBy(how = How.XPATH, using = "//*[@id=\"basic\"]/fieldset/div/div/button")
	private WebElement saveButton;
	
	@FindBy(how = How.XPATH, using = "//*[@id='basic']/form/fieldset/div/div/button")
	private WebElement saveBtnBusinessConfig;

	@FindBy(how = How.XPATH, using = "(//div[@class='pull-right']/button)[1]")
	private WebElement savePracticeConfigBtn;		
			
	public PSS2PracticeConfiguration(WebDriver driver) {
		super(driver);
	}

	public void clickOnLogo() {
		companyLogo.click();
	}

	public String checkLogoLink() {
		return clientlogo.getAttribute("src");
	}

	public String practiceIDLinkText() {
		return practiceText.getText();
	}

	public String clientTimeZone() {
		return clientTimeZone.getAttribute("ng-reflect-model");
	}

	public String getPartnerValue() {
		return partnerName.getAttribute("ng-reflect-model");
	}

	public String getSelectedColor() {
		log("color length : " + colorType.size());
		for (int i = 0; i < colorType.size(); i++) {
			if (colorType.get(i).getAttribute("ng-reflect-ng-class").contains("active")) {
				return colorType.get(i).getCssValue("background-color");

			}
		}
		log("active color not found.");
		return null;
	}

	public String matchPartnerName() {
		for (int i = 0; i < partnerOptionValue.size(); i++) {
			if (partnerOptionValue.get(i).getAttribute("ng-reflect-ng-value").equalsIgnoreCase(getPartnerValue())) {
				return partnerOptionValue.get(i).getText();
			}
		}
		return null;
	}

	public void busineesHours(String startTime, String endTime) throws InterruptedException {
		Thread.sleep(2000);
		businessHoursStartTime.clear();
		businessHoursStartTime.click();
		businessHoursStartTime.sendKeys(startTime);
		Thread.sleep(2000);
		businessHoursEndTime.clear();
		businessHoursEndTime.click();
		businessHoursEndTime.sendKeys(endTime);
		saveBtnBusinessConfig.click();
		log("Successfully send the keys");
	}

	public String gettextbusineesHourStarttime() {
		String starttime = businessHoursStartTime.getAttribute("value");
		log("Business hour Starttime is" + starttime);
		return starttime;
	}

	public String gettextbusineesHourEndtime() {
		String endtime = businessHoursEndTime.getAttribute("value");
		log("Business hour businesshourEnd is" + endtime);
		return endtime;

	}
	
	public void clickOnSpanishLanguage() throws InterruptedException {
		Thread.sleep(3000);
		javascriptClick(spanishLanguageLabel);
		javascriptClick(savePracticeConfigBtn);
	}

}
