package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PatientFlow extends SettingsTab {

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/div/main/div[2]/div/div/div/section/div/div/div[2]/div[3]/div[3]/div/table/tbody/tr/td[2]/span")
	private WebElement specialityRule;

	@FindBy(how = How.ID, using = "showinsurance")
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


	public PatientFlow(WebDriver driver) {
		super(driver);
	}

	public String getRule() {
		return specialityRule.getText();
	}

	public String isIsuranceDisplayed() {
		return insuranceToggle.getAttribute("ng-reflect-model");
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
}