package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;

public class CalculatedFormPage extends PortalFormPage {

	@FindBy(xpath = "//tr/td[2]/label/input")
	private List<WebElement> leftmostRadioTableList;

	@FindBy(xpath = "//tr/td[last()]/label/input")
	private List<WebElement> rightmostRadioTableList;

	@FindBy(xpath = "//fieldset/ul/label[2]//input")
	private WebElement leftmostRadioInFieldset;

	@FindBy(xpath = "//fieldset/ul/label[last()]//input")
	private WebElement rightmostRadioInFieldset;

	public CalculatedFormPage(WebDriver driver) {
		super(driver);
	}

	public boolean isPageLoaded() {
		log("Checking if page is loaded.");
		return true;
	}

	public void fillFormLefmostAnswer() throws Exception {
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
			radioButton.click();
		}

		IHGUtil iHGUtil = new IHGUtil(driver);
		if (iHGUtil.exists(rightmostRadioInFieldset)) {
			rightmostRadioInFieldset.click();
		}
	}

}
