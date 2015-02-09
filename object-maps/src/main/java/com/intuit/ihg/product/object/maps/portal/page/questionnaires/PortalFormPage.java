package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

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
	
	public PortalFormPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * 		  continueButton WebElement of the continue button
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends PortalFormPage> T clickSaveAndContinueButton(Class<T> nextPageClass, WebElement continueButton) throws Exception {
		IHGUtil.PrintMethodName();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		continueButton.click();
		if (nextPageClass == null)
			return null;
		else
			return PageFactory.initElements(driver, nextPageClass);
	}
	
	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends PortalFormPage> T clickSaveAndContinueButton(Class<T> nextPageClass) throws Exception {
		return clickSaveAndContinueButton(nextPageClass, this.btnContinue);
	}

	/**
	 * @brief Click on Continue Button
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends PortalFormPage> T clickSaveAndContinueButton() throws Exception {
		return clickSaveAndContinueButton(null, this.btnContinue);
	}

	/**
	 * @Description Click on Submit Form Button
	 * @return
	 * @throws Exception
	 */
	public void submitForm() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 25);
		
		submitForm.click();
		wait.until(ExpectedConditions.elementToBeClickable(closeButton));	
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
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        String continueButtonID = previousPageButton.getAttribute("id");

        jsExecutor.executeScript("document.getElementById('" + continueButtonID + "').scrollIntoView();");
    }
    
    public void goToFirstPage() {
        scrollToFooter();
        while (previousPageButton.isDisplayed()) {
            previousPageButton.click();
            scrollToFooter();
        }
    }
}
