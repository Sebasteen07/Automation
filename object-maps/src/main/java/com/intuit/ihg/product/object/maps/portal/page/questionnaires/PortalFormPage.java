package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import com.intuit.ihg.common.utils.IHGConstants;
import com.intuit.ihg.common.utils.IHGUtil;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PortalFormPage extends BasePageObject {
	
	@FindBy(xpath="//*[@id='container']//section/div[@class='done_frame']/a")
	private WebElement submitForm;

	@FindBy(id = "closeFormButton")
	private WebElement closeButton;
	
	@FindBy(id = "nextPageButton")
	private WebElement btnContinue;

    @FindBy(id = "prevPageButton")
    private WebElement previousPageButton;

    @FindBy(className = "save")
    private WebElement saveAndFinishLink;

	@FindBy(id = "page-header-title")
	private WebElement pageTitle;

	@FindBy(xpath = "//div[@id='errorContainer']")
	private WebElement validationErrorMessage;

	@FindBy(xpath = "//div[@id='errorContainer']/p/strong")
	private List<WebElement> errorSpecifications;

	@FindBy(id = "loadingPage")
	private WebElement loadingSpinner;

	public PortalFormPage(WebDriver driver) {
		super(driver);
	}


	public WebElement getErrorSpecificationElement(String errorSpecificationText) {
		for (WebElement errorSpecification : errorSpecifications) {
			// the staleElementException seems to be appearing a lot after upgrading selenium to 2.45
			// this loop tries 5 times

			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			for (int i = 0; i < 5; i++) {
				try {
					if (errorSpecification.getText().equals(errorSpecificationText)) {
						driver.manage().timeouts()
								.implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
						return errorSpecification;
					}
					else break;
				} catch (StaleElementReferenceException e) {
					log("Stale element reference caught, retrying");
				}
			}
		}

		return null;
	}

	/**
	 * Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * 		  continueButton WebElement of the continue button
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass, WebElement continueButton) {
		IHGUtil.PrintMethodName();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		//we need this here as demo is acting up sometimes with longer delays and newrelic, doing this twice blocks the occasional org.openqa.selenium.WebDriverException: Permission denied to access property 'handleEvent'
		try {
			wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		}
		catch(org.openqa.selenium.WebDriverException e){
			wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		}
		continueButton.click();
		if (nextPageClass == null)
			return null;
		else
			return PageFactory.initElements(driver, nextPageClass);
	}
	
	/**
	 * Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * @return initialized PageObject for the next page
	 */
	public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass) {
		return clickSaveContinue(nextPageClass, this.btnContinue);
	}

	/**
	 * Click on Continue Button
	 * @return initialized PageObject for the next page
	 */
	public <T extends PortalFormPage> T clickSaveContinue() {
		return clickSaveContinue(null, this.btnContinue);
	}

	/**
	 * Clicks the link that goes to previous page
	 */
	public void clickBackLink() {
		scrollToFooter();
		previousPageButton.click();
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
		wait.until(ExpectedConditions.elementToBeClickable(closeButton));	
		closeButton.click();
	}

    public void clickSaveAndFinishAnotherTime() throws InterruptedException {
        saveAndFinishLink.click();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
			throw e;
        }
    }

    public void scrollToFooter() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        String continueButtonID;
        
        // we need this here as demo is acting up sometimes with longer delays and newrelic,
        // doing this twice blocks the occasional org.openqa.selenium.WebDriverException: Permission
        // denied to access property 'handleEvent'
        try {
			continueButtonID = previousPageButton.getAttribute("id");
		} catch (org.openqa.selenium.WebDriverException e) {
			continueButtonID = previousPageButton.getAttribute("id");
		}        

        jsExecutor.executeScript("document.getElementById('" + continueButtonID + "').scrollIntoView();");
    }
    
    public void goToFirstPage() {
        scrollToFooter();
        while (previousPageButton.isDisplayed()) {
            previousPageButton.click();
            scrollToFooter();
        }
    }

	@Test
	protected void assertErrorMessageAfterContinuing() throws InterruptedException {
		clickSaveContinue();
		TimeUnit.SECONDS.sleep(2);
		Assert.assertTrue(validationErrorMessage.isDisplayed(),
				"Validation error message was supposed to appear.");
	}

	@Test
	protected void assertErrorMessageAfterContinuing(String errorSpecificationText) throws InterruptedException {
		assertErrorMessageAfterContinuing();
		Assert.assertTrue(getErrorSpecificationElement(errorSpecificationText).isDisplayed(),
				"Specified error message not displayed.");
	}

	public void testValidation() throws Exception {}
}
