package com.medfusion.product.object.maps.forms.page.questionnaires;

import java.rmi.UnexpectedException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class PortalFormPage extends BasePageObject {

	@FindBy(xpath = "//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy(id = "closeFormButton")
	private WebElement closeButton;

	@FindBy(id = "nextPageButton")
	private WebElement btnContinue;

	@FindBy(id = "prevPageButton")
	private WebElement previousPageButton;

	@FindBy(className = "save")
	private WebElement saveAndFinishLink;

	@FindBy(id = "errorDialog")
	private WebElement errorDialog;

	@FindBy(id = "modalButton")
	private WebElement tryAgainButton;

	public PortalFormPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass
	 *            Class of the following page in the form continueButton
	 *            WebElement of the continue button
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
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
		continueButton.click();
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
				continueButton.click();
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

	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass
	 *            Class of the following page in the form
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass) throws Exception {
		return clickSaveContinue(nextPageClass, this.btnContinue);
	}

	/**
	 * @brief Click on Continue Button
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends PortalFormPage> T clickSaveContinue() throws Exception {
		return clickSaveContinue(null, this.btnContinue);
	}

	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass, int maxRetry) throws Exception {
		return clickSaveContinueWithRetry(nextPageClass, this.btnContinue, maxRetry);
	}

	public void clickSaveContinueSamePage(int retries) throws Exception {
		clickSaveContinueWithRetry(null, this.btnContinue, retries);
	}

	/**
	 * @Description Click on Submit Form Button
	 * @return
	 * @throws Exception
	 * 
	 */
	public void submitForm() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 25);

		submitForm.click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(closeButton));
		} catch (NoSuchElementException ex) {
			log("Close button is not displayed");
			if (errorDialog.isDisplayed()) {
				throw new UnexpectedException("***Oops, something went wrong*** dialog is displayed");
			}
		}
		closeButton.click();
	}

	public void clickSaveAndFinishAnotherTime() {
		saveAndFinishLink.click();
		try {
			TimeUnit.SECONDS.sleep(5);
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
			previousPageButton.click();
			scrollToFooter(3);
		}
	}
}