// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.forms.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGConstants;
import com.medfusion.product.forms.pojo.Question;
import com.medfusion.product.forms.pojo.SelectQuestion;
import com.medfusion.product.forms.pojo.SelectableAnswer;
import com.medfusion.product.forms.pojo.TextQuestion;

public class QuestionsService {
	public static final String QUESTIONS_BASE_XPATH = "//form[not(./div[contains(@style,'none')])]//fieldset/ul[not(contains(@class, 'hiddenFUP'))]";
	private static final String TXT_QUESTIONS_FILTER = "[count(./li)=1]";
	private static final String SEL_QUESTIONS_FILTER = "[./label]";

	public static Set<Question> getSetOfVisibleQuestions(WebDriver driver) {
		Set<Question> result = new HashSet<Question>();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
		Log4jUtil.log("parse visible text questions");
		List<WebElement> textQuestions = driver.findElements(By.xpath(QUESTIONS_BASE_XPATH + TXT_QUESTIONS_FILTER));
		for (WebElement textQuestion : textQuestions) {
			result.add(parseTextQuestion(textQuestion));
		}
		Log4jUtil.log("parse visible select questions");
		List<WebElement> selQuestions = driver.findElements(By.xpath(QUESTIONS_BASE_XPATH + SEL_QUESTIONS_FILTER));
		for (WebElement selQuestion : selQuestions) {
			result.add(parseSelectQuestion(selQuestion));
		}
		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
		return result;
	}

	public static Question parseQuestion(WebElement question) {
		String elClass = question.getAttribute("class");
		if (elClass != null && elClass.contains("list_spacing")) {
			return parseSelectQuestion(question);
		}
		return parseTextQuestion(question);
	}

	public static TextQuestion parseTextQuestion(WebElement question) {
		String title = question.findElement(By.xpath("./li/label")).getText();
		Log4jUtil.log("parsing:" + title);
		String answer;
		try {
			answer = question.findElement(By.xpath("./li/input")).getAttribute("value");
		} catch (NoSuchElementException e) {
			answer = question.findElement(By.xpath("./li/textarea")).getText();
		}
		return new TextQuestion(title, answer);
	}

	public static SelectQuestion parseSelectQuestion(WebElement question) {
		Set<SelectableAnswer> answers = new HashSet<SelectableAnswer>();
		String title = question.findElement(By.xpath("./h3")).getText();
		Log4jUtil.log("parsing:" + title);
		List<WebElement> answersElements = question.findElements(By.xpath("./label"));
		String tmpAnswer;
		WebElement tmpInput;
		boolean tmpIsSelected;
		for (WebElement selAnswer : answersElements) {
			tmpAnswer = selAnswer.findElement(By.xpath("./span")).getText();
			tmpInput = selAnswer.findElement(By.xpath("./input"));
			if (tmpInput.isSelected()) {
				tmpIsSelected = true;
			} else {
				tmpIsSelected = false;
			}
			answers.add(new SelectableAnswer(tmpAnswer, tmpIsSelected));
		}
		return new SelectQuestion(title, answers);
	}

}
