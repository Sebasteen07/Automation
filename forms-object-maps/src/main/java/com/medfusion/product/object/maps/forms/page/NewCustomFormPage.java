package com.medfusion.product.object.maps.forms.page;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.medfusion.common.utils.IHGConstants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.forms.services.QuestionsService;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class NewCustomFormPage extends PortalFormPage {

	@FindBy(id = "continueWelcomePageButton")
	private WebElement continueWelcomePageButton;

	@FindBy(css = ".save")
	private WebElement saveAndFinishButton;

	@FindBy(id = "prevPageButton")
	private WebElement prevPageButton;

	@FindBy(id = "nextPageButton")
	private WebElement saveAndContinueButton;

	@FindBy(css = ".submit")
	private WebElement submitFormButton;

	@FindBy(id = "chooseSectionMenu")
	private WebElement chooseSectionMenuSelect;

	@FindBy(id = "section0")
	private WebElement welcomePageDiv;

	@FindBy(id = "section1")
	private WebElement section1Div;

	@FindBy(id = "section2")
	private WebElement section2Div;

	@FindBy(id = "section3")
	private WebElement section3Div;

	@FindBy(id = "errorContainer")
	private WebElement errorContainer;

	private int currentSection;
	private static final String FUP_QUESTIONS_FILTER = "[@data-parent-answer-id]";
	private static final String TOPLVL_QUESTIONS_FILTER = "[not(@data-parent-answer-id)]";

	public NewCustomFormPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

		if (isSectionDisplayed(1)) {
			currentSection = 1;
			return;
		}
		if (isSectionDisplayed(2)) {
			currentSection = 2;
			return;
		}
		if (isSectionDisplayed(3)) {
			currentSection = 3;
			return;
		}
		currentSection = 0;
	}

	public void clickOnSaveAndFinishButton() {
		saveAndFinishButton.click();
	}

	public void clickOnGoToPrevious() throws InterruptedException {
		prevPageButton.click();
		Thread.sleep(1000);
		currentSection--;
	}

	public void goToFirstSection() throws InterruptedException {
		currentSection = 1;
		if (isSectionDisplayed(1)) {
			return;
		}
		if (isSectionDisplayed(0)) {
			continueWelcomePageButton.click();
			return;
		}
		if (IHGUtil.waitForElement(driver, 1, prevPageButton)) {
			while (prevPageButton.isDisplayed()) {
				prevPageButton.click();
				Thread.sleep(2000);
			}
		}
	}

	public boolean clickOnSaveAndContinueButton() throws InterruptedException {
		saveAndContinueButton.click();
		Thread.sleep(3000);
		if (!isSectionDisplayed(currentSection)) {
			Assert.assertFalse(isErrorMessageDisplayed());
			currentSection++;
			return true;
		}
		Assert.assertTrue(isErrorMessageDisplayed());
		return false;
	}

	public void clickOnSubmitFormButton() {
		submitFormButton.click();
	}

	public boolean isErrorMessageDisplayed() {
		return errorContainer.isDisplayed();
	}

	public boolean isSectionDisplayed(int sectionOrdinal) {
		switch (sectionOrdinal) {
			case 0:
				return welcomePageDiv.isDisplayed();
			case 1:
				return section1Div.isDisplayed();
			case 2:
				return section2Div.isDisplayed();
			case 3:
				return section3Div.isDisplayed();
		}
		throw new IllegalArgumentException();
	}

	public List<WebElement> getVisibleQuestions() {
		return driver.findElements(By.xpath(String.format(QuestionsService.QUESTIONS_BASE_XPATH, currentSection) + "[(count(./li)=1 or ./label)]"));
	}

	/**
	 * returns nth visible question of current section
	 * 
	 * @param sectionOrdinalString
	 * @param questionOrdinal
	 * @return
	 */
	public WebElement getVisibleQuestion(int questionOrdinal) {
		return driver
				.findElement(By.xpath(String.format(QuestionsService.QUESTIONS_BASE_XPATH, currentSection) + "[(count(./li)=1 or ./label)])[" + questionOrdinal + "]"));
	}

	public boolean isHeadingDisplayed(String headingTitle) {
		List<WebElement> headings = driver.findElements(By.xpath("//form[" + currentSection + "]//fieldset//h3[not(parent::*[contains(@class,'hiddenFUP')])]"));
		for (WebElement h3 : headings) {
			if (headingTitle.equals(h3.getText())) {
				return true;
			}
		}
		return false;
	}

	public boolean isTextDisplayed(String text) {
		List<WebElement> textDivs = driver.findElements(By.xpath(String.format(QuestionsService.QUESTIONS_BASE_XPATH, currentSection) + "/div"));
		for (WebElement div : textDivs) {
			if (text.equals(div.getText())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param questionOfSelectType WebElement &lt;ul&gt; object
	 * @param answerIndex index from 1
	 */
	public void selectAnswer(WebElement questionOfSelectType, int answerIndex) {
		questionOfSelectType.findElement(By.xpath("((./label)[" + answerIndex + "])/input")).click();
	}

	public void selectAnswers(WebElement questionOfSelectType, List<Integer> answersIndexes) {
		for (Integer index : answersIndexes) {
			questionOfSelectType.findElement(By.xpath("((./label)[" + index + "])/input")).click();
		}
	}

	public void fillMultiLineAnswer(WebElement questionOfFillType, String answer) {
		questionOfFillType.findElement(By.xpath("./li/textarea")).sendKeys(answer);
	}

	public void fillSingleLineAnswer(WebElement questionOfFillType, String answer) {
		questionOfFillType.findElement(By.xpath("./li/input")).sendKeys(answer);
	}

	public void consentAllVisibleStatements(String consentString) {
		for (WebElement consentInput : driver
				.findElements(By.xpath(String.format(QuestionsService.QUESTIONS_BASE_XPATH, currentSection) + "[count(./li)=2]/li/input"))) {
			consentInput.sendKeys(consentString);
		}
	}

	/**
	 * clears all inputs of specified section, in case of radio buttons it always selects the last option
	 * 
	 * @throws InterruptedException
	 */
	public void clearAllInputs() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		for (WebElement fupsel : driver
				.findElements(By.xpath(String.format(QuestionsService.QUESTIONS_BASE_XPATH, currentSection) + FUP_QUESTIONS_FILTER + "/label/input"))) {
			if ("checkbox".equals(fupsel.getAttribute("type")) && fupsel.isSelected()) {
				fupsel.click();
			} else if ("radio".equals(fupsel.getAttribute("type")) && !fupsel.isSelected()) {
				fupsel.click();
			}
		}
		for (WebElement fuptxt : driver.findElements(
				By.xpath(String.format(QuestionsService.QUESTIONS_BASE_XPATH, currentSection) + FUP_QUESTIONS_FILTER + "/li/*[self::input|self::textarea]"))) {
			fuptxt.clear();
		}
		for (WebElement sel : driver
				.findElements(By.xpath(String.format(QuestionsService.QUESTIONS_BASE_XPATH, currentSection) + TOPLVL_QUESTIONS_FILTER + "/label/input"))) {
			scrollAndWait(0, 200, 0);
			if ("checkbox".equals(sel.getAttribute("type")) && sel.isSelected()) {
				sel.click();
			} else if ("radio".equals(sel.getAttribute("type")) && !sel.isSelected()) {
				sel.click();
			}
		}
		for (WebElement txt : driver.findElements(
				By.xpath(String.format(QuestionsService.QUESTIONS_BASE_XPATH, currentSection) + TOPLVL_QUESTIONS_FILTER + "/li/*[self::input|self::textarea]"))) {
			txt.clear();
		}
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
	}

	public void selectSectionToUpdate(int sectionIndex) {
		currentSection = sectionIndex;
		new Select(chooseSectionMenuSelect).selectByIndex(sectionIndex);
	}

	@Override
	public boolean isPageLoaded() {
		throw new UnsupportedOperationException();
	}
}
