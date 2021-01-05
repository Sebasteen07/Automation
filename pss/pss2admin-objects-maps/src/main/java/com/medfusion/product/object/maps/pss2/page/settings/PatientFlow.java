// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class PatientFlow extends SettingsTab {


	@FindBy(how = How.XPATH, using = "/html/body/app/layout/div/main/div[2]/div/div/div/section/div/div/div[2]/div[3]/div[3]/div/table/tbody/tr/td[2]/span")
	private WebElement specialityRule;

	@FindBy(how = How.XPATH, using = "//div[@class='form-group row']//div[@class='col-md-12 col-xs-12']//input")
	private WebElement insuranceToggle;

	@FindBy(how = How.XPATH, using = "//div[@id='flow']//div//div[@class='col-md-12 col-xs-12']/div/label/i")
	private WebElement insuranceToggleCheckBox;

	@FindBy(how = How.XPATH, using = "//div[@id='flow']//div//div[@class='col-md-12 col-xs-12']/div/label[2]")
	private WebElement insuranceToggleLabe;

	@FindBy(how = How.XPATH, using = "//*[@id=\"flow\"]/div[2]/div/h5/strong/a/i")
	private WebElement addRule;

	@FindBy(how = How.XPATH, using = "//*[@id=\"flow\"]/div[3]/div[2]/form/div[1]/div/input")
	private WebElement ruleName;

	@FindBy(how = How.NAME, using = "profile")
	private WebElement selectRuleType;

	@FindBy(how = How.XPATH, using = ".//form[@class='ng-touched ng-dirty ng-valid']/div/div/a/i")
	private WebElement addRuleInOrder;

	@FindAll({@FindBy(xpath = ".//select[@name=\"profile\"]/option")})
	private List<WebElement> ruleList;

	@FindAll({@FindBy(xpath = "//*[@id=\"flow\"]/div[3]/div/table/tbody/tr")})
	private List<WebElement> ruleLength;

	@FindAll({@FindBy(xpath = "//*[@id=\"flow\"]/div[3]/div/table/tbody/tr/td[3]/a")})
	private List<WebElement> deleteAllRules;

	@FindBy(how = How.XPATH, using = "//*[@id=\"flow\"]/div[2]/div/h5/strong/a")
	private WebElement addRuleLink;

	@FindBy(how = How.XPATH, using = "//*[@id=\"flow\"]/div[3]/div[2]/form/div[1]/div/input")
	private WebElement ruleNameInput;

	@FindBy(how = How.XPATH, using = "//*[@id=\"flow\"]/div[3]/div[2]/form/div[2]/div/select")
	private WebElement ruleTypeSelect;

	@FindBy(how = How.XPATH, using = "//*[@id=\"flow\"]/div[3]/div[2]/form/div[2]/div/a")
	private WebElement ruleAddLink;

	@FindBy(how = How.XPATH, using = "//*[@id=\"flow\"]/div[3]/div[2]/form/fieldset/div/div/button")
	private WebElement saveRuleButton;

	public PatientFlow(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public String getRule() {
		commonMethods.highlightElement(specialityRule);
		return specialityRule.getText();
	}

	public String isIsuranceDisplayed() {
		commonMethods.highlightElement(insuranceToggleLabe);
		commonMethods.highlightElement(insuranceToggleCheckBox);
		log(insuranceToggle.getAttribute("ng-reflect-model"));
		return insuranceToggle.getAttribute("ng-reflect-model");
	}

	public Boolean isInsuranceToBeDisplayed() {
		commonMethods.highlightElement(insuranceToggle);
		return insuranceToggle.isSelected();
	}
	public void selectInsurance() {
		log("insuranceToggle = " + insuranceToggle);
		commonMethods.highlightElement(insuranceToggleLabe);
		commonMethods.highlightElement(insuranceToggleCheckBox);
		insuranceToggle.click();
		log("Click on insuranceToggle");
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(specialityRule);
		return assessPageElements(webElementsList);
	}

	public void addNewRules(String[] ruleNameValue, String[] ruleNameType) {
		addRule.click();

		for (String ruleOption : ruleNameType) {
			ruleName.sendKeys(ruleNameValue);
			selectRuleType.click();
			selectValueFromDropDown(ruleOption);
		}
		addRuleInOrder.click();
	}

	public void selectValueFromDropDown(String ruleNameType) {
		for (WebElement ruleOption : ruleList) {
			if (ruleOption.getText().equalsIgnoreCase(ruleNameType)) {
				ruleOption.click();
				break;
			}
		}
	}

	public int ruleLength() {
		return ruleLength.size();
	}

	public void removeAllRules() {
		if (deleteAllRules.size() > 0) {
			for (int i = 0; i < deleteAllRules.size(); i++) {
				deleteAllRules.get(i).click();
			}
		} else {
			log("No Rules are applied.");
		}
	}

	public void addNewRulesButton() {
		log("adding new rule...");
		IHGUtil.waitForElement(driver, 60, addRuleLink);
		log("is Rule link displayed.? " + addRuleLink.isDisplayed());
		javascriptClick(addRuleLink);
	}

	public void selectRuleName(String ruleName) {
		log("Rule Name Input is displayed ? " + ruleNameInput.isDisplayed());
		ruleNameInput.sendKeys(Keys.CONTROL + "a");
		ruleNameInput.sendKeys(Keys.BACK_SPACE);
		ruleNameInput.sendKeys(ruleName);
	}

	public void addNewRules(String ruleValue) {
		Select oSelect = new Select(ruleTypeSelect);
		oSelect.selectByValue(ruleValue);
		javascriptClick(ruleAddLink);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void saveRule() {
		log("Saving above selected rule..");
		javascriptClick(saveRuleButton);
	}

	public boolean insuracetogglestatus() throws InterruptedException {

		commonMethods.highlightElement(insuranceToggleLabe);
		commonMethods.highlightElement(insuranceToggleCheckBox);
		log(insuranceToggle.getAttribute("ng-reflect-model"));
		boolean bool = Boolean.parseBoolean(insuranceToggle.getAttribute("ng-reflect-model"));
		return bool;
	}

	public boolean isstartpagepresent() {
		if (ruleLength.size() > 1) {
			return true;
		} else {
			return false;
		}

	}
}
