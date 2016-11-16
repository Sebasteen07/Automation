package com.medfusion.product.object.maps.forms.page.questionnaires.supplemental_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class IllnessesSupplementalPage extends PortalFormPage {

	@FindBy(id = "custom_mappingid_conditions_section_1")
	private WebElement firstQuestion;

	@FindBy(id = "custom_mappingid_conditions_section_2")
	private WebElement secondQuestion;

	@FindBy(xpath = "//label[span[contains(text(), 'Mononucleosis')]]/input")
	private WebElement monoCheckbox;

	public IllnessesSupplementalPage(WebDriver driver) {
		super(driver);
	}

	public void answerFirst(String answer) {
		firstQuestion.sendKeys(answer);
	}

	public void answerSecond(String answer) {
		secondQuestion.sendKeys(answer);
	}

	public void selectMono() {
		monoCheckbox.click();
	}

	public void fillOut() {
		selectMono();
		answerFirst("test answer 1");
		answerSecond("test answer 2");
	}

	@Override
	public boolean isPageLoaded() {
		throw new UnsupportedOperationException();
	}
}
