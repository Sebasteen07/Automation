package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
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
	
	@FindBy(xpath = "//div[@class='done_frame']/a")
	private WebElement submitSocialForm;

	public void fillOutDefaultExerciseLength() {
		Select exerciseFreqSelect = new Select(exerciseFrequency);
		exerciseLength.sendKeys("20");
		exerciseFreqSelect.selectByVisibleText("day");
	}
	public void fillOutDefaultExerciseLength_20(String exerciseMin,String day) throws InterruptedException {
		Select exerciseFreqSelect = new Select(exerciseFrequency);
		exerciseLength.clear();
		exerciseLength.sendKeys(exerciseMin);
		exerciseFreqSelect.selectByVisibleText(day);
		saveAndContinuebtn.click();
		IHGUtil.waitForElement(driver, 60, submitSocialForm);
		submitSocialForm.click();

	}
	public FormFamilyHistoryPage fillExerciseDetails(String exerciseMin,String day) throws InterruptedException
	{
		fillOutDefaultExerciseLength_20(exerciseMin, day);
		return PageFactory.initElements(driver, FormFamilyHistoryPage.class);
		
	}
	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Social History"))).isDisplayed();
	}
}
