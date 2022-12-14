//Copyright 2013-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires;

import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;

public class CalculatedFormPage extends PortalFormPage {

	@FindBy(xpath = "//tr/td[2]/label/input")
	private List<WebElement> leftmostRadioTableList;

	@FindBy(xpath = "//tr/td[last()]/label/input")
	private List<WebElement> rightmostRadioTableList;

	@FindBy(xpath = "//fieldset/ul/label[2]//input")
	private WebElement leftmostRadioInFieldset;

	@FindBy(xpath = "//fieldset/ul/label[last()]//input")
	private WebElement rightmostRadioInFieldset;

	@FindBy(id = "errorContainer")
	private WebElement errorMsg;

	@FindBy(id = "nextPageButton")
	private WebElement btnContinue;

	public CalculatedFormPage(WebDriver driver) {
		super(driver);
	}

	public boolean isPageLoaded() {
		log("Checking if Form page is loaded.");
		IHGUtil iHGUtil = new IHGUtil(driver);
		if (iHGUtil.exists(btnContinue)) {
			return btnContinue.isDisplayed();
		} else {
			return false;
		}
	}

	public void fillFormLeftmostAnswer() throws Exception {
		log("Filling the form with the lefttmost answers.");
		for (WebElement radioButton : leftmostRadioTableList) {
			javascriptClick(radioButton);
		}

		IHGUtil iHGUtil = new IHGUtil(driver);
		if (iHGUtil.exists(leftmostRadioInFieldset)) {
		    javascriptClick(leftmostRadioInFieldset);
		}
	}

	public void fillFormRightmostAnswer() {
		log("Filling the form with the rightmost answers.");
		for (WebElement radioButton : rightmostRadioTableList) {
		    javascriptClick(radioButton);
		}

		IHGUtil iHGUtil = new IHGUtil(driver);
		if (iHGUtil.exists(rightmostRadioInFieldset)) {
		    javascriptClick(rightmostRadioInFieldset);
		}
	}

	public void fillFormExcludingLastQuestion() {
		log("Filling the form with the rightmost answers except for the last compulsory answer.");
		for (int i = 0; i <= rightmostRadioTableList.size() - 2; i++) {
		    javascriptClick(rightmostRadioTableList.get(i));
		}

	}

	public boolean isValidationErrorDisplayed() throws InterruptedException {
		log("Checking if error message was displayed.");
		try {
			new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(errorMsg));
			return true;
		} catch (TimeoutException ex) {
			return false;
		}
	}

}
