//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires;

import java.rmi.UnexpectedException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public abstract class PortalFormPage extends BasePageObject {

	@FindBy(xpath = "//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy(id = "closeFormButton")
	private WebElement closeButton;

	@FindBy(id = "nextPageButton")
	private WebElement btnContinue;

	@FindBy(id = "prevPageButton")
	private WebElement previousPageButton;

	@FindBy(xpath = "//a[@class='save']")
	private WebElement saveAndFinishLink;

	@FindBy(id = "errorDialog")
	private WebElement submittErrorDialog;

	@FindBy(id = "modalButton")
	private WebElement tryAgainButton;

	@FindBy(id = "errorContainer")
	private WebElement inputErrorMessage;

	@FindBy(id = "chooseSectionMenu")
	private WebElement chooseSectionMenuSelect;

	public static final String PAGE_LOADED_XPATH_TEMPLATE = "//span[./text()='%s']";

	public PortalFormPage(WebDriver driver) {
		super(driver);
	}

	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass, WebElement continueButton)
			throws Exception {
		IHGUtil.PrintMethodName();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		// we need this here as demo is acting up sometimes with longer delays
		// and newrelic, doing this twice blocks the occasional
		// org.openqa.selenium.WebDriverException: Permission denied to access
		// property 'handleEvent'
		try {

			wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		} catch (org.openqa.selenium.WebDriverException e) {
			wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		}
		javascriptClick(continueButton);
		TimeUnit.SECONDS.sleep(3);
		if (nextPageClass == null)
			return null;
		else
			return PageFactory.initElements(driver, nextPageClass);
	}

	public <T extends PortalFormPage> T clickSaveContinueWithRetry(Class<T> nextPageClass, WebElement continueButton,
			int maxRetry) throws Exception {
		IHGUtil.PrintMethodName();
		WebDriverWait wait = new WebDriverWait(driver, 10);

		int attempt;
		for (attempt = 1; attempt <= maxRetry; attempt++) {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(continueButton));
				javascriptClick(continueButton);
				break;
			} catch (org.openqa.selenium.WebDriverException e) {
				log("Exception was thrown, retry: " + attempt + "/" + maxRetry);
			}
		}

		if (attempt > maxRetry) {
			log("Exception is still present, element is not displayed");
		}
		if (nextPageClass == null)
			return null;
		else
			return PageFactory.initElements(driver, nextPageClass);
	}

	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass) throws Exception {
		return clickSaveContinue(nextPageClass, this.btnContinue);
	}

	public <T extends PortalFormPage> T clickSaveContinue() throws Exception {
		return clickSaveContinue(null, this.btnContinue);
	}

	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass, int maxRetry) throws Exception {
		return clickSaveContinueWithRetry(nextPageClass, this.btnContinue, maxRetry);
	}

	public void clickSaveContinueSamePage(int retries) throws Exception {
		clickSaveContinueWithRetry(null, this.btnContinue, retries);
	}

	public <T extends PortalFormPage> T goBack(Class<T> previousPageClass) {
		javascriptClick(previousPageButton);
		// not the cleanest solution, but one that makes most sense
		// the javascript action can get queued up too fast after page load and get to
		// negative page numbers (and then it loops...)
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return PageFactory.initElements(driver, previousPageClass);
	}

	public void submitForm() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 25);
		wait.until(ExpectedConditions.visibilityOf(submitForm));
		submitForm.click();
		try {
			wait.until(ExpectedConditions.visibilityOf(closeButton));
		} catch (NoSuchElementException ex) {
			log("Close button is not displayed");
			if (submittErrorDialog.isDisplayed()) {
				throw new UnexpectedException("***Oops, something went wrong*** dialog is displayed");
			}
		}
		closeButton.click();
		IHGUtil.setDefaultFrame(driver);
		IHGUtil.setFrame(driver, "iframe");
	}

	public void clickSaveAndFinishAnotherTime() throws InterruptedException {
		scrollAndWait(0, 0, 500);
		javascriptClick(saveAndFinishLink);
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void scrollToFooter() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String continueButtonID;

		// we need this here as demo is acting up sometimes with longer delays
		// and newrelic, doing this twice blocks the occasional
		// org.openqa.selenium.WebDriverException: Permission denied to access
		// property 'handleEvent'
		try {
			continueButtonID = previousPageButton.getAttribute("id");
		} catch (org.openqa.selenium.WebDriverException e) {
			continueButtonID = previousPageButton.getAttribute("id");
		}

		jsExecutor.executeScript("document.getElementById('" + continueButtonID + "').scrollIntoView();");
	}

	public void scrollToFooter(int maxAttempt) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String continueButtonID = null;

		int attempt;
		for (attempt = 0; attempt <= maxAttempt; attempt++) {
			try {
				continueButtonID = previousPageButton.getAttribute("id");
				break;
			} catch (org.openqa.selenium.WebDriverException e) {
				log("Wasn't able to get attribute of previousPageButton, attempt: " + attempt + "/" + maxAttempt);
			}
		}

		if (attempt > maxAttempt) {
			throw new NoSuchElementException("PreviousPageButton is no longer visible");
		}

		jsExecutor.executeScript("document.getElementById('" + continueButtonID + "').scrollIntoView();");
	}

	public void goToFirstPage() {
		scrollToFooter();
		while (previousPageButton.isDisplayed()) {
			javascriptClick(previousPageButton);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scrollToFooter(3);
		}
	}

	public void checkAnswer(String answerLabel) {
		WebElement input = getAnswerInputElement(answerLabel);
		if (!input.isSelected())
			input.click();
	}

	public void uncheckAnswer(String answerLabel) {
		WebElement input = getAnswerInputElement(answerLabel);
		if (input.isSelected())
			input.click();
	}

	private WebElement getAnswerInputElement(String answerLabel) {
		return driver.findElement(By.xpath("//span[text()='" + answerLabel + "']"));
	}

	public <T extends PortalFormPage> T selectSectionToUpdate(int sectionIndex, Class<T> nextPageClass) {
		new Select(chooseSectionMenuSelect).selectByIndex(sectionIndex);
		return PageFactory.initElements(driver, nextPageClass);
	}

	public abstract boolean isPageLoaded();

	public boolean isInputErrorMessageDisplayed() {
		return IHGUtil.waitForElement(driver, 10, inputErrorMessage);
	}
}
