package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;

/**
 * 
 * @author Jan Tomasek ordinal numbers are indexes of elements on the page,
 *         numbered from 1
 */
public class CustomFormPageSection extends BasePageObject {

	private String sectionOrdinalString;
	private String sectionName;

	/**
	 * 
	 * @param driver
	 * @param sectionOrdinalString
	 *            one of: 'first'/'second'/'third'
	 */
	public CustomFormPageSection(WebDriver driver, String sectionOrdinalString) {
		super(driver);
		this.sectionOrdinalString = sectionOrdinalString;
	}

	public void fillSectionName(String sectionName) {
		driver.findElement(By.id("custom_title_custom" + sectionOrdinalString + "_section_0")).sendKeys(sectionName);
		this.sectionName = sectionName;
	}

	public void addEmptyItem() {
		driver.findElement(By.xpath("//div[@data-section-id = 'custom" + sectionOrdinalString
				+ "_section']//a[contains(@class, 'insert_new_item')]")).click();
	}

	public void addFUP(int itemOrdinalNumber, int answerOrdinalNumber) {
		driver.findElement(By.xpath(
				"(//div[@data-section-id = 'custom" + sectionOrdinalString + "_section']//ul[@class='questions']/li["
						+ (itemOrdinalNumber) + "]//a[@class='addFollowUp'])[" + (answerOrdinalNumber) + "]"))
				.click();
	}

	public void removeItem(int itemOrdinalNumber) {
		driver.findElement(By.xpath("//div[@data-section-id ='custom" + sectionOrdinalString
				+ "_section']//ul[@class='questions']/li[" + (itemOrdinalNumber) + "]//a[@class='remove_item']"))
				.click();
	}

	public void removeAnswer(int itemOrdinalNumber, int answerOrdinalNumber) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	public void removeFUP(int itemOrdinalNumber, int FUPOrdinalNumber) {
		driver.findElement(By.xpath(
				"(//div[@data-section-id ='custom" + sectionOrdinalString + "_section']//ul[@class='questions']/li["
						+ (itemOrdinalNumber) + "]//a[@class='deleteFollowUp'])[" + FUPOrdinalNumber + "]"))
				.click();
	}

	public void setItemAsQuestion(int itemOrdinalNumber, String questionType, String title, boolean required,
			boolean prefilled) {
		selectItemType(itemOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		selectQuestionType(itemOrdinalNumber, questionType);
		fillQuestionTitle(itemOrdinalNumber, title);
		setQuestionRequiredOption(itemOrdinalNumber, required);
		setQuestionPrefillOption(itemOrdinalNumber, prefilled);
	}

	public void setItemAsQuestionFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String questionType, String title,
			boolean required, boolean prefilled) {
		selectItemTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		selectQuestionTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, questionType);
		fillQuestionTitleFUP(itemOrdinalNumber, FUPOrdinalNumber, title);
		setQuestionRequiredOptionFUP(itemOrdinalNumber, FUPOrdinalNumber, required);
		setQuestionPrefillOptionFUP(itemOrdinalNumber, FUPOrdinalNumber, prefilled);
	}

	public void setItemAsHeading(int itemOrdinalNumber, String title) {
		selectItemType(itemOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE2);
		driver.findElement(
				By.id("custom_headingtitle_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1)))
				.sendKeys(title);
	}

	public void setItemAsHeadingFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String title) {
		selectItemTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, SitegenConstants.CUSTOMFORM_ITEM_TYPE2);
		driver.findElement(By.id("custom_headingtitle_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1)
				+ "-" + (FUPOrdinalNumber - 1))).sendKeys(title);
	}

	public void setItemAsText(int itemOrdinalNumber, String text) {
		selectItemType(itemOrdinalNumber, "Text");
		driver.findElement(By.id("custom_text_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1)))
				.sendKeys(text);
	}

	public void setItemAsTextFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String text) {
		selectItemTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, "Text");
		driver.findElement(By.id("custom_text_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1) + "-"
				+ (FUPOrdinalNumber - 1))).sendKeys(text);
	}

	public void setItemAsConsent(int itemOrdinalNumber, String title, String statement) {
		selectItemType(itemOrdinalNumber, "Consent");
		driver.findElement(
				By.id("custom_consenttitle_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1)))
				.sendKeys(title);
		driver.findElement(
				By.id("custom_consenttext_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1)))
				.sendKeys(statement);
	}

	public void setItemAsConsentFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String title, String statement) {
		selectItemTypeFUP(itemOrdinalNumber, FUPOrdinalNumber, "Consent");
		driver.findElement(By.id("custom_consenttitle_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1)
				+ "-" + (FUPOrdinalNumber - 1))).sendKeys(title);
		driver.findElement(By.id("custom_consenttext_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1)
				+ "-" + (FUPOrdinalNumber - 1))).sendKeys(statement);
	}

	public void addAnswer(int itemOrdinalNumber, String answer) {
		driver.findElement(By.xpath("(//div[@data-section-id='custom" + sectionOrdinalString + "_section']/ul/li["
				+ (itemOrdinalNumber) + "]//a[@class='addAnswer'])[1]")).click();
		getLastAnswerFieldOfQuestion(itemOrdinalNumber).sendKeys(answer);
	}

	public void addAnswerFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String answer) {
		driver.findElement(By.xpath("(//div[@data-section-id='custom" + sectionOrdinalString + "_section']/ul/li["
				+ (itemOrdinalNumber) + "]//li[contains(@class,'followUpQuestion')])[" + FUPOrdinalNumber
				+ "]//a[@class='addAnswer']")).click();
		getLastAnswerFieldOfQuestionFUP(itemOrdinalNumber, FUPOrdinalNumber).sendKeys(answer);
	}

	public int getCountOfItems() {
		return driver.findElements(By.xpath(
				"//div[@data-section-id = 'custom" + sectionOrdinalString + "_section']/ul/li[@data-identifier]"))
				.size();
	}

	public int getCountOfItemsFUP(int itemOrdinalNumber) {
		return driver.findElements(By.xpath("//div[@data-section-id = 'custom" + sectionOrdinalString
				+ "_section']/ul/li[" + (itemOrdinalNumber) + "]//ul[@class='followUpQuestions']/li")).size();
	}

	/**
	 * returns count of FUPs attached to specific answer
	 * 
	 * @param itemOrdinalNumber
	 * @param answerOrdinalNumber
	 * @return
	 */
	public int getCountOfFUPsOfAnswer(int itemOrdinalNumber, int answerOrdinalNumber) {
		return driver.findElements(By.xpath("(//div[@data-section-id = 'custom" + sectionOrdinalString
				+ "_section']//ul[@class='questions']/li[" + (itemOrdinalNumber)
				+ "]/fieldset/div[@class='metadata']/ul/li)[" + answerOrdinalNumber + "]/ul/li")).size();
	}

	/**
	 * minimizes/maximizes FUP (by default they are maximized)
	 * 
	 * @param itemOrdinalNumber
	 * @param answerOrdinalNumber
	 */
	public void toogleFUPs(int itemOrdinalNumber, int answerOrdinalNumber) {
		driver.findElement(By
				.xpath("//li[@id='custom_questionselect_" + (answerOrdinalNumber - 1) + "_custom" + sectionOrdinalString
						+ "_section_" + (itemOrdinalNumber - 1) + "']/img[contains(@class,'followUpAnswerToggle')]"))
				.click();
	}

	public boolean areFUPsMinimized(int itemOrdinalNumber, int answerOrdinalNumber) {
		int countOfHiddenFUPs = driver.findElements(By.xpath("//li[@id='custom_questionselect_"
				+ (answerOrdinalNumber - 1) + "_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1)
				+ "']/ul/li/fieldset[contains(@class,'hiddenFollowUpStyles')]")).size();
		if (getCountOfFUPsOfAnswer(itemOrdinalNumber, answerOrdinalNumber) == countOfHiddenFUPs) {
			return true;
		}
		return false;
	}

	public String getTitleOfFUPQuestion(int itemOrdinalNumber, int FUPOrdinalNumber) {
		return driver.findElement(By.id("custom_questiontitle_custom" + sectionOrdinalString + "_section_"
				+ (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1))).getText();
	}

	public List<String> getAnswersOfFUPQuestion(int itemOrdinalNumber, int FUPOrdinalNumber) {
		List<String> answers = new ArrayList<String>();
		for (WebElement answer : getAnswerFieldsOfFUPQuestion(itemOrdinalNumber, FUPOrdinalNumber)) {
			answers.add(answer.getAttribute("value"));
			System.out.println("t");
			System.out.println(answer.getAttribute("value"));
		}
		return answers;
	}

	private void selectItemType(int itemOrdinalNumber, String itemType) {
		new Select(driver.findElement(
				By.id("custom_itemtype_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))))
						.selectByVisibleText(itemType);
		;
	}

	private void selectItemTypeFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String itemType) {
		new Select(driver.findElement(By.id("custom_itemtype_custom" + sectionOrdinalString + "_"
				+ (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1)))).selectByVisibleText(itemType);
	}

	private void selectQuestionType(int itemOrdinalNumber, String questionType) {
		new Select(driver.findElement(
				By.id("custom_questiontype_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))))
						.selectByVisibleText(questionType);
	}

	private void selectQuestionTypeFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String questionType) {
		new Select(driver.findElement(By.id("custom_questiontype_custom" + sectionOrdinalString + "_"
				+ (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1)))).selectByVisibleText(questionType);
	}

	private void fillQuestionTitle(int itemOrdinalNumber, String title) {
		driver.findElement(
				By.id("custom_questiontitle_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1)))
				.sendKeys(title);
	}

	private void fillQuestionTitleFUP(int itemOrdinalNumber, int FUPOrdinalNumber, String title) {
		driver.findElement(By.id("custom_questiontitle_custom" + sectionOrdinalString + "_" + (itemOrdinalNumber - 1)
				+ "-" + (FUPOrdinalNumber - 1))).sendKeys(title);
	}

	private void setQuestionPrefillOption(int itemOrdinalNumber, boolean prefilled) {
		new Select(driver.findElement(
				By.id("custom_questionprefill_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))))
						.selectByVisibleText(prefilled ? "Yes" : "No");
	}

	private void setQuestionPrefillOptionFUP(int itemOrdinalNumber, int FUPOrdinalNumber, boolean prefilled) {
		new Select(driver.findElement(By.id("custom_questionprefill_custom" + sectionOrdinalString + "_"
				+ (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1))))
						.selectByVisibleText(prefilled ? "Yes" : "No");
	}

	private void setQuestionRequiredOption(int itemOrdinalNumber, boolean required) {
		new Select(driver.findElement(
				By.id("custom_questionrequired_custom" + sectionOrdinalString + "_section_" + (itemOrdinalNumber - 1))))
						.selectByVisibleText(required ? "Yes" : "No");
	}

	private void setQuestionRequiredOptionFUP(int itemOrdinalNumber, int FUPOrdinalNumber, boolean required) {
		new Select(driver.findElement(By.id("custom_questionrequired_custom" + sectionOrdinalString + "_"
				+ (itemOrdinalNumber - 1) + "-" + (FUPOrdinalNumber - 1))))
						.selectByVisibleText(required ? "Yes" : "No");
	}

	private List<WebElement> getAnswerFieldsOfFUPQuestion(int itemOrdinalNumber, int FUPOrdinalNumber) {
		return driver.findElements(By.xpath(
				"(//div[@data-section-id='custom" + sectionOrdinalString + "_section']/ul/li[" + (itemOrdinalNumber)
						+ "]//div[@class='followUpContainer'])[" + FUPOrdinalNumber + "]//li[@class='answer']/input"));
	}

	private WebElement getLastAnswerFieldOfQuestion(int itemOrdinalNumber) {
		return driver
				.findElement(By.xpath("(//div[@data-section-id='custom" + sectionOrdinalString + "_section']/ul/li["
						+ (itemOrdinalNumber) + "]/fieldset/div[@class='metadata']/ul/li)[last()]/input"));
	}

	private WebElement getLastAnswerFieldOfQuestionFUP(int itemOrdinalNumber, int FUPOrdinalNumber) {
		return driver.findElement(By.xpath("((//div[@data-section-id='custom" + sectionOrdinalString
				+ "_section']/ul/li[" + (itemOrdinalNumber) + "]//div[@class='followUpContainer'])[" + FUPOrdinalNumber
				+ "]//li[@class='answer'])[last()]/input"));
	}
}
