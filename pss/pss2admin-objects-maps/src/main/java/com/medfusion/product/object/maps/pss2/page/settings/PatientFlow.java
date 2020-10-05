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

public class PatientFlow extends SettingsTab {

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/div/main/div[2]/div/div/div/section/div/div/div[2]/div[3]/div[3]/div/table/tbody/tr/td[2]/span")
	private WebElement specialityRule;

	// Code changed by SS
	@FindBy(how = How.XPATH, using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[1]/label[1]/i[1]")
	private WebElement insuranceToggle;

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

	public String getRule() {
		return specialityRule.getText();
	}

	public String isIsuranceDisplayed() {
		log(insuranceToggle.getAttribute("ng-reflect-model"));
		return insuranceToggle.getAttribute("ng-reflect-model");
	}

	public Boolean isInsuranceToBeDisplayed() {
		log("inside the isInsuranceToBeDisplayed");
		return insuranceToggle.isSelected();
	}

	public void selectInsurance() {
		log("insuranceToggle = " + insuranceToggle);
		insuranceToggle.click();
		log("Click on insuranceToggle ");
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
}
