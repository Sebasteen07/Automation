package com.medfusion.product.object.maps.patientportal1.page.questionnaires;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
			radioButton.click();
		}

		IHGUtil iHGUtil = new IHGUtil(driver);
		if (iHGUtil.exists(leftmostRadioInFieldset)) {
			leftmostRadioInFieldset.click();
		}
	}

	public void fillFormRightmostAnswer() {
		log("Filling the form with the rightmost answers.");
		for (WebElement radioButton : rightmostRadioTableList) {
			radioButton.sendKeys(" "); // radioButton.click(); doesn't work with
										// ADHD Forms
		}

		IHGUtil iHGUtil = new IHGUtil(driver);
		if (iHGUtil.exists(rightmostRadioInFieldset)) {
			rightmostRadioInFieldset.click();
		}
	}

	public void fillFormExcludingLastQuestion() {
		log("Filling the form with the rightmost answers except for the last compulsory answer.");
		for (int i = 0; i <= rightmostRadioTableList.size() - 2; i++) {
			rightmostRadioTableList.get(i).click();
		}

	}

	public boolean isValidationErrorDisplayed() throws InterruptedException {
		log("Checking if error message was displayed.");
		IHGUtil iHGUtil = new IHGUtil(driver);
		Thread.sleep(1000);
		if (iHGUtil.exists(errorMsg)) {
			return errorMsg.isDisplayed();
		} else {
			return false;
		}
	}

}
