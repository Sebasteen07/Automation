package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormSocialHistoryPage extends PortalFormPage {

	public FormSocialHistoryPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(xpath = "//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy(xpath = ".//iframe[@title ='Forms']")
	private WebElement iframe;

	@FindBy(id = "exercise_healthhabits")
	private WebElement exerciseLength;

	@FindBy(id = "exercise_healthhabits_type")
	private WebElement exerciseFrequency;

	public void fillOutDefaultExerciseLength() {
		Select exerciseFreqSelect = new Select(exerciseFrequency);
		exerciseLength.sendKeys("20");
		exerciseFreqSelect.selectByVisibleText("day");
	}
}
