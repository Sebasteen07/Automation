//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;

public class SocialHistoryPage extends ConfiguratorFormPage {

	@FindBy(id = "hide_socialhistory_section_check")
	private WebElement hideSocialhistorySectionCheck;

	@FindBy(id = "culturalbelief_field")
	private WebElement culturalbeliefField;

	@FindBy(id = "occupation_field")
	private WebElement occupationField;

	@FindBy(id = "education_field")
	private WebElement educationField;

	@FindBy(id = "smokealarms_personalsafety")
	private WebElement smokealarmsPersonalsafety;

	@FindBy(id = "firearms_personalsafety")
	private WebElement firearmsPersonalsafety;

	@FindBy(id = "seatbelt_personalsafety")
	private WebElement seatbeltPersonalsafety;

	@FindBy(id = "recreationaldrugs_personalsafety")
	private WebElement recreationaldrugsPersonalsafety;

	@FindBy(id = "violenceconcerns_personalsafety")
	private WebElement violenceconcernsPersonalsafety;

	@FindBy(id = "traveledoutsidecountry_personalsafety")
	private WebElement traveledoutsidecountryPersonalsafety;

	@FindBy(id = "livingwith_field")
	private WebElement livingwithField;

	@FindBy(id = "exercise_healthhabits")
	private WebElement exerciseHealthhabits;

	@FindBy(id = "alcohol_healthhabits")
	private WebElement alcoholHealthhabits;

	@FindBy(id = "teacoffee_healthhabits")
	private WebElement teacoffeeHealthhabits;

	@FindBy(id = "sodaenergydrinks_healthhabits")
	private WebElement sodaenergydrinksHealthhabits;

	@FindBy(id = "cigarettes_healthhabits")
	private WebElement cigarettesHealthhabits;

	@FindBy(id = "tobacco_healthhabits")
	private WebElement tobaccoHealthhabits;

	@FindBy(id = "socialhistory_anythingelse_line")
	private WebElement socialhistoryComments;

	@FindBy(xpath = "//div[@class='configuration_section socialhistory_section']/p[@class='custom']/a")
	private WebElement supplementalQuestion;

	@FindBy(id = "custom_title_socialhistory_section_0")
	private WebElement customSectionName;

	@FindBy(linkText = "Insert An Item")
	private WebElement insertItemButton;

	@FindBy(id = "custom_questiontitle_socialhistory_section_0")
	private WebElement questionNameField;

	@FindBy(id = "custom_questiontype_socialhistory_section_0")
	private WebElement questionTypeSelect;

	@FindBy(linkText = "Add Answer")
	private WebElement addAnswerButton;

	@FindBy(xpath = "//div[@class='notifications']/div[@class='error']/h3[contains(text(), 'Sorry, some required fields are missing.')]")
	private WebElement errorNotification;

	public static enum QuestionType {
		shortText, longText, multiSelect, singleSelect
	}

	public SocialHistoryPage(WebDriver driver) {
		super(driver);
		jse = (JavascriptExecutor) driver;
	}

	public void showThisPage() {
		if (hideSocialhistorySectionCheck.isSelected()) {
			hideSocialhistorySectionCheck.click();
			teacoffeeHealthhabits.click();
		}
	}

	public void setSectionName(String newName) throws InterruptedException {
		scrollAndWait(0, 0, 500);
		customSectionName.sendKeys(newName);
	}

	public void setQuestionName(String newName) {
		IHGUtil.PrintMethodName();
		questionNameField.sendKeys(newName);
	}

	public void setQuestionType(QuestionType type) throws Exception {
		Select questionTypeSel = new Select(questionTypeSelect);

		switch (type) {
			case shortText:
				questionTypeSel.selectByIndex(1);
				break;
			case longText:
				questionTypeSel.selectByIndex(2);
				break;
			case multiSelect:
				questionTypeSel.selectByIndex(3);
				break;
			case singleSelect:
				questionTypeSel.selectByIndex(4);
				break;
			default:
				throw new Exception();
		}
	}

	public void addPossibleAnswer(String newAnswerText) {
		addAnswerButton.click();

		List<WebElement> answers = driver.findElements(By.className("available_answers"));
		WebElement lastAnswer = answers.get(answers.size() - 1);

		lastAnswer.sendKeys(newAnswerText);
	}

	public void errorMessageAppearedTest() {
		assertTrue(errorNotification.isDisplayed());
	}

	public void clickInsertItemButton() {
		insertItemButton.click();
	}
}
