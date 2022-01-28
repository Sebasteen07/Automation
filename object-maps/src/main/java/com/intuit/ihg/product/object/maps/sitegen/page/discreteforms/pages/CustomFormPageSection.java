//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;

public class CustomFormPageSection extends BasePageObject {

	private String sectionOrdinalString;
	private String sectionName;
	private static final String SECTION_IDENTIFIER = "//div[@data-section-id = 'custom%s_section']";
	private static final String ITEM_IDENTIFIER = SECTION_IDENTIFIER + "/ul/li[%d]";
	private static final String METADATA_PATH = "/fieldset/div[@class='metadata']/ul/li";

	public CustomFormPageSection(WebDriver driver, String sectionOrdinalString) {
		super(driver);
		this.sectionOrdinalString = sectionOrdinalString;
	}

	public void fillSectionName(String sectionName) {
		driver.findElement(By.id("custom_title_custom" + sectionOrdinalString + "_section_0")).sendKeys(sectionName);
		this.sectionName = sectionName;
	}

	public void addQuestionItem(String questionType, String title, boolean required, boolean prefilled) throws InterruptedException {
		driver.findElement(By.xpath(String.format(SECTION_IDENTIFIER, sectionOrdinalString) + "//a[contains(@class, 'insert_new_item')]")).click();
		int itemOrdinalNumber = getCountOfItems();
		selectItemType(itemOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		selectQuestionType(itemOrdinalNumber, questionType);
		fillQuestionTitle(itemOrdinalNumber, title);
		setQuestionRequiredOption(itemOrdinalNumber, required);
		setQuestionPrefillOption(itemOrdinalNumber, prefilled);
	}

	public void addEmptyFUP(int itemOrdinalNumber, int answerOrdinalNumber) {
		driver
				.findElement(By
						.xpath("(" + String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + "//a[@class='addFollowUp'])[" + (answerOrdinalNumber) + "]"))
				.click();
	}

	public void addQuestionFUP(int itemOrdinalNumber, int answerOrdinalNumber, String questionType, String title, boolean required, boolean prefilled)
			throws InterruptedException {
		addEmptyFUP(itemOrdinalNumber, answerOrdinalNumber);
		setItemAsQuestionFUP(itemOrdinalNumber, getCountOfItemsFUP(itemOrdinalNumber), questionType, title, required, prefilled);
	}

	public void addHeadingFUP(int itemOrdinalNumber, int answerOrdinalNumber, String title) {
		addEmptyFUP(itemOrdinalNumber, answerOrdinalNumber);
		setItemAsHeadingFUP(itemOrdinalNumber, getCountOfItemsFUP(itemOrdinalNumber), title);
	}

	public void removeItem(int itemOrdinalNumber) {
		driver.findElement(By.xpath(String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + "//a[@class='remove_item']")).click();
	}

	public void removeAnswer(int itemOrdinalNumber, int answerOrdinalNumber) {
		driver.findElement(By.xpath(
				String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + METADATA_PATH + "[" + answerOrdinalNumber + "]/a[@class='deleteAnswer']"));
	}

	public void removeFUP(int itemOrdinalNumber, int FUPOrdinalNumber) {
		driver
				.findElement(
						By.xpath("(" + String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + "//a[@class='deleteFollowUp'])[" + FUPOrdinalNumber + "]"))
				.click();
	}

	public void setItemAsQuestion(int itemOrdinalNumber, String questionType, String title, boolean required, boolean prefilled) {
		selectItemType(itemOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		selectQuestionType(itemOrdinalNumber, questionType);
		fillQuestionTitle(itemOrdinalNumber, title);
		setQuestionRequiredOption(itemOrdinalNumber, required);
		setQuestionPrefillOption(itemOrdinalNumber, prefilled);
	}

	public void setItemAsQuestionFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String questionType, String title, boolean required, boolean prefilled) {
		selectItemTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		selectQuestionTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, questionType);
		fillQuestionTitleFUP(itemOrdinalNumber, FUPOrdinalNumber, title);
		setQuestionRequiredOptionFUP(itemOrdinalNumber, FUPOrdinalNumber, required);
		setQuestionPrefillOptionFUP(itemOrdinalNumber, FUPOrdinalNumber, prefilled);
	}

	public void setItemAsHeading(int itemOrdinalNumber, String title) {
		selectItemType(itemOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE2);
		driver.findElement(By.id("custom_headingtitle_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))).sendKeys(title);
	}

	public void setItemAsHeadingFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String title) {
		selectItemTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE2);
		driver.findElement(By.id("custom_headingtitle_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1)))
				.sendKeys(title);
	}

	public void setItemAsText(int itemOrdinalNumber, String text) {
		selectItemType(itemOrdinalNumber, "Text");
		driver.findElement(By.id("custom_text_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))).sendKeys(text);
	}

	public void setItemAsTextFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String text) {
		selectItemTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, "Text");
		driver.findElement(By.id("custom_text_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1))).sendKeys(text);
	}

	public void setItemAsConsent(int itemOrdinalNumber, String title, String statement) {
		selectItemType(itemOrdinalNumber, "Consent");
		driver.findElement(By.id("custom_consenttitle_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))).sendKeys(title);
		driver.findElement(By.id("custom_consenttext_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))).sendKeys(statement);
	}

	public void setItemAsConsentFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String title, String statement) {
		selectItemTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, "Consent");
		driver.findElement(By.id("custom_consenttitle_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1)))
				.sendKeys(title);
		driver.findElement(By.id("custom_consenttext_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1)))
				.sendKeys(statement);
	}

	public void addAnswer(int itemOrdinalNumber, String answer) {
		driver.findElement(By.xpath("(" + String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + "//a[@class='addAnswer'])[1]")).click();
		getLastAnswerFieldOfQuestion(itemOrdinalNumber).sendKeys(answer);
	}

	public void addAnswers(int itemOrdinalNumber, List<String> answers) {
		for (int i = 0; i < answers.size(); i++) {
			addAnswer(itemOrdinalNumber, answers.get(i));
		}
	}

	public void addAnswerFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String answer) {
		driver.findElement(By.xpath("(" + String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + "//li[contains(@class,'followUpQuestion')])["
				+ FUPOrdinalNumber + "]//a[@class='addAnswer']")).click();
		getLastAnswerFieldOfQuestionFUP(itemOrdinalNumber, FUPOrdinalNumber).sendKeys(answer);
	}

	public void addAnswersFUP(int itemOrdinalNumber, int FUPOrdinalNumber, List<String> answers) {
		for (int i = 0; i < answers.size(); i++) {
			addAnswerFUP(itemOrdinalNumber, FUPOrdinalNumber, answers.get(i));
		}
	}

	public int getCountOfItems() {
		return driver.findElements(By.xpath(String.format(SECTION_IDENTIFIER, sectionOrdinalString) + "/ul/li[not(contains(@class,'firstHiddenItem'))]")).size();
	}

	public int getCountOfItemsFUP(int itemOrdinalNumber) {
		return driver.findElements(By.xpath(String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + "//ul[@class='followUpQuestions']/li"))
				.size();
	}

	public int getCountOfFUPsOfAnswer(int itemOrdinalNumber, int answerOrdinalNumber) {
		return driver
				.findElements(
						By.xpath("(" + String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + METADATA_PATH + ")[" + answerOrdinalNumber + "]/ul/li"))
				.size();
	}

	public void toogleFUPs(int itemOrdinalNumber, int answerOrdinalNumber) throws InterruptedException {
		driver.findElement(By.xpath(String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + METADATA_PATH + "[" + answerOrdinalNumber
				+ "]/img[contains(@class,'followUpAnswerToggle')]")).click();
	}

	public boolean areFUPsMinimized(int itemOrdinalNumber, int answerOrdinalNumber) {
		int countOfHiddenFUPs = driver.findElements(By.xpath(String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + METADATA_PATH + "["
				+ answerOrdinalNumber + "]/ul/li/fieldset[contains(@class,'hiddenFollowUpStyles')]")).size();
		if (getCountOfFUPsOfAnswer(itemOrdinalNumber, answerOrdinalNumber) == countOfHiddenFUPs) {
			return true;
		}
		return false;
	}

	public String getTitleOfFUPQuestion(int itemOrdinalNumber, int FUPOrdinalNumber) {
		return driver
				.findElement(By.id("custom_questiontitle_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1)))
				.getText();
	}

	public List<String> getAnswersOfFUPQuestion(int itemOrdinalNumber, int FUPOrdinalNumber) {
		List<String> answers = new ArrayList<String>();
		for (WebElement answer : getAnswerFieldsOfFUPQuestion(itemOrdinalNumber, FUPOrdinalNumber)) {
			answers.add(answer.getAttribute("value"));
		}
		return answers;
	}

	private void selectItemType(int itemOrdinalNumber, String itemType) {
		focusSelectAndSelectByValue(driver.findElement(By.id("custom_itemtype_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))),itemType);
	}

	private void selectItemTypeFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String itemType) {
		focusSelectAndSelectByValue(driver.findElement(By.id("custom_itemtype_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1))),itemType);
	}

	private void selectQuestionType(int itemOrdinalNumber, String questionType) {
		focusSelectAndSelectByValue(driver.findElement(By.id("custom_questiontype_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))),questionType);
	}

	private void selectQuestionTypeFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String questionType) {
		focusSelectAndSelectByValue(driver.findElement(By.id("custom_questiontype_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1))),questionType);
	}

	private void fillQuestionTitle(int itemOrdinalNumber, String title) {
		driver.findElement(By.id("custom_questiontitle_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))).sendKeys(title);
	}

	private void fillQuestionTitleFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String title) {
		driver.findElement(By.id("custom_questiontitle_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1)))
				.sendKeys(title);
	}

	private void setQuestionPrefillOption(int itemOrdinalNumber, boolean prefilled) {
		focusSelectAndSelectByValue(driver.findElement(By.id("custom_questionprefill_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))),prefilled ? "Yes" : "No");
	}

	private void setQuestionPrefillOptionFUP(int itemOrdinalNumber, int FUPOrdinalNumber, boolean prefilled) {
		focusSelectAndSelectByValue(driver.findElement(By.id("custom_questionprefill_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1))),prefilled ? "Yes" : "No");
	}

	private void setQuestionRequiredOption(int itemOrdinalNumber, boolean required) {
		focusSelectAndSelectByValue(driver.findElement(By.id("custom_questionrequired_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))),required ? "Yes" : "No");
	}

	private void setQuestionRequiredOptionFUP(int itemOrdinalNumber, int FUPOrdinalNumber, boolean required) {
		focusSelectAndSelectByValue(driver.findElement(By.id("custom_questionrequired_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1))),required ? "Yes" : "No");
	}

	private List<WebElement> getAnswerFieldsOfFUPQuestion(int itemOrdinalNumber, int FUPOrdinalNumber) {
		return driver.findElements(By.xpath("(" + String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + "//div[@class='followUpContainer'])["
				+ FUPOrdinalNumber + "]//li[@class='answer']/input"));
	}

	private WebElement getLastAnswerFieldOfQuestion(int itemOrdinalNumber) {
		return driver.findElement(By.xpath("(" + String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + METADATA_PATH + ")[last()]/input"));
	}

	private WebElement getLastAnswerFieldOfQuestionFUP(int itemOrdinalNumber, int FUPOrdinalNumber) {
		return driver.findElement(By.xpath("((" + String.format(ITEM_IDENTIFIER, sectionOrdinalString, itemOrdinalNumber) + "//div[@class='followUpContainer'])["
				+ FUPOrdinalNumber + "]//li[@class='answer'])[last()]/input"));
	}
}
