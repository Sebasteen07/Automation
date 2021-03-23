// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Specialty;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;

public class ManageSpecialty extends PSS2MenuPage {

	@FindBy(how = How.XPATH, using = "//*[@ng-reflect-ng-class='[object Object]']")
	private WebElement specilityList;


	@FindBy(how = How.ID, using = "search-specialty")
	private WebElement searchSpecility;

	@FindBy(how = How.ID, using = "onoffgender")
	private WebElement genderRuleStatus;

	@FindBy(how = How.XPATH, using = "//*[@id='F']")
	private WebElement genderRuleFeMaleStatus;

	@FindBy(how = How.XPATH, using = "//*[@id='M']")
	private WebElement genderRuleMaleStatus;

	@FindBy(how = How.XPATH, using = "//*[@id='U']")
	private WebElement genderRuleUnknownStatus;

	@FindBy(how = How.XPATH, using = "//*[@id='O']")
	private WebElement genderRuleOtherStatus;

	@FindBy(how = How.XPATH, using = "//*[@for='M']")
	private WebElement genderMale;

	@FindBy(how = How.XPATH, using = "//*[@for='F']")
	private WebElement genderfeMale;

	@FindBy(how = How.XPATH, using = "//*[@for='O']")
	private WebElement genderother;

	@FindBy(how = How.XPATH, using = "//*[@for='U']")
	private WebElement gendeunknown;

	@FindBy(how = How.XPATH, using = "//*[@id='content']/div[2]/div/div/div[2]/section/div/form/fieldset[2]/div/div/button[1]")
	private WebElement saveButton;


	@FindBy(how = How.XPATH, using = "//*[@id=\"content\"]/div[2]/div/div/div[2]/section/div/form/div[2]/label/strong")
	private WebElement genderRuleClick;

	@FindBy(how = How.XPATH, using = "//*[@ng-reflect-ng-class='[object Object]']/td/span")
	private WebElement searchSpecilityName;

	@FindBy(how = How.XPATH, using = "//*[@ng-reflect-ng-class='[object Object]']/td/span")
	private WebElement searchSpecilityClick;

	public ManageSpecialty(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public void searchSpecility(String specilityName) {
		searchSpecility.sendKeys(specilityName);
	}

	public void selectSpecility(String resourceName) {
		searchSpecility(resourceName);
		IHGUtil.waitForElement(driver, 60, searchSpecilityName);
		searchSpecilityClick.click();
		log("clicked on Specility  ");
	}

	public Boolean isgenderRuleTrue() {
		return Boolean.valueOf(genderRuleStatus.getAttribute("ng-reflect-model"));
	}

	public Boolean isMaleTrue() {
		return Boolean.valueOf(genderRuleMaleStatus.getAttribute("ng-reflect-model"));
	}

	public Boolean isFemaleTrue() {
		return Boolean.valueOf(genderRuleFeMaleStatus.getAttribute("ng-reflect-model"));
	}

	public Boolean isOtherTrue() {
		return Boolean.valueOf(genderRuleOtherStatus.getAttribute("ng-reflect-model"));
	}

	public Boolean isUnknownTrue() {
		return Boolean.valueOf(genderRuleUnknownStatus.getAttribute("ng-reflect-model"));
	}


	public void clickGender() {
		genderRuleClick.click();
		log("Clicked On Gender Rule");
		saveButton.click();
	}

	public void selectgender() {
		if (isMaleTrue() == false) {
			genderMale.click();
			log("male Checkbox Selected");

		} else {
			log("Male is already selected");
		}
		if (isFemaleTrue() == false) {
			genderfeMale.click();
			log("female Checkbox Selected");
		} else {
			log("female Male is already selected");

		}
		saveButton.click();
		log("Clicked on Submit");
	}



	public void maleClick() {
		genderMale.click();
		saveButton.click();
	}

	public void femaleClick() {
		genderfeMale.click();
		saveButton.click();
	}

	public void otherClick() {
		genderother.click();
		saveButton.click();
	}

	public void unknownClick() {
		gendeunknown.click();
		saveButton.click();
	}
}
