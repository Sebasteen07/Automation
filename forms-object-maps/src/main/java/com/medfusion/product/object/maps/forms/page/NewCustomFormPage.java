// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGConstants;
import com.medfusion.product.forms.services.QuestionsService;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class NewCustomFormPage extends PortalFormPage {

	private static final String FUP_QUESTIONS_FILTER = "[@data-parent-answer-id]";
	private static final String TOPLVL_QUESTIONS_FILTER = "[not(@data-parent-answer-id)]";

	public NewCustomFormPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public List<WebElement> getVisibleQuestions() {
		return driver.findElements(By.xpath(QuestionsService.QUESTIONS_BASE_XPATH + "[(count(./li)=1 or ./label)]"));
	}

	public WebElement getVisibleQuestion(int questionOrdinal) {
		return driver
				.findElement(By.xpath(QuestionsService.QUESTIONS_BASE_XPATH + "[(count(./li)=1 or ./label)])[" + questionOrdinal + "]"));
	}

	public boolean isHeadingDisplayed(String headingTitle) {
		List<WebElement> headings =
				driver.findElements(By.xpath("//form[not(./div[contains(@style,'none')])]//fieldset//h3[not(parent::*[contains(@class,'hiddenFUP')])]"));
		for (WebElement h3 : headings) {
			if (headingTitle.equals(h3.getText())) {
				return true;
			}
		}
		return false;
	}

	public boolean isTextDisplayed(String text) {
		List<WebElement> textDivs = driver.findElements(By.xpath(QuestionsService.QUESTIONS_BASE_XPATH + "/div"));
		for (WebElement div : textDivs) {
			if (text.equals(div.getText())) {
				return true;
			}
		}
		return false;
	}

	public void selectAnswer(WebElement questionOfSelectType, int answerIndex) throws InterruptedException {
		scrollAndWait(0, 0, 500);
		javascriptClick(questionOfSelectType.findElement(By.xpath("((./label)[" + answerIndex + "])/input")));
	}

	public void selectAnswers(WebElement questionOfSelectType, List<Integer> answersIndexes) {
		for (Integer index : answersIndexes) {
		    javascriptClick(questionOfSelectType.findElement(By.xpath("((./label)[" + index + "])/input")));
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
				.findElements(By.xpath(QuestionsService.QUESTIONS_BASE_XPATH + "[count(./li)=2]/li/input"))) {
			consentInput.sendKeys(consentString);
		}
	}

	public void clearAllInputs() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		for (WebElement fupsel : driver
				.findElements(By.xpath(QuestionsService.QUESTIONS_BASE_XPATH + FUP_QUESTIONS_FILTER + "/label/input"))) {
			scrollAndWait(0, 0, 500);
			if ("checkbox".equals(fupsel.getAttribute("type")) && fupsel.isSelected()) {
				javascriptClick(fupsel);
			} else if ("radio".equals(fupsel.getAttribute("type")) && !fupsel.isSelected()) {
			    javascriptClick(fupsel);
			}
		}
		for (WebElement fuptxt : driver.findElements(
				By.xpath(QuestionsService.QUESTIONS_BASE_XPATH + FUP_QUESTIONS_FILTER + "/li/*[self::input|self::textarea]"))) {
			scrollAndWait(0, 0, 500);
			fuptxt.clear();
		}
		for (WebElement sel : driver
				.findElements(By.xpath(QuestionsService.QUESTIONS_BASE_XPATH + TOPLVL_QUESTIONS_FILTER + "/label/input"))) {
			scrollAndWait(0, 0, 500);
			if ("checkbox".equals(sel.getAttribute("type")) && sel.isSelected()) {
			    javascriptClick(sel);
			} else if ("radio".equals(sel.getAttribute("type")) && !sel.isSelected()) {
			    javascriptClick(sel);
			}
		}
		for (WebElement txt : driver.findElements(
				By.xpath(QuestionsService.QUESTIONS_BASE_XPATH + TOPLVL_QUESTIONS_FILTER + "/li/*[self::input|self::textarea]"))) {
			scrollAndWait(0, 0, 500);
			txt.clear();
		}
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
	}

	@Override
	public boolean isPageLoaded() {
		throw new UnsupportedOperationException();
	}
}
