package com.medfusion.product.object.maps.patientportal1.page.questionnaires;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGConstants;

public class FormWelcomePage extends PortalFormPage {

    @FindBy(id = "continueWelcomePageButton")
    private WebElement btnContinue;

    @FindBy(xpath = "//section[@class='content indented']/p[1]")
    private WebElement welcomeMessage;

    public FormWelcomePage(WebDriver driver) {
        super(driver);
    }

    public String getMessageText() {
        try {
            return welcomeMessage.getText();
        } catch (WebDriverException e) {
            return welcomeMessage.getText();
        }
    }

    /**
     * Checks if the Welcome page of the form is loaded
     * 
     * @return True if Continue button (which is the main functional part of the page) is loaded, otherwise false
     */
    public boolean isWelcomePageLoaded() {
        boolean result = false;

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.switchTo().activeElement();
        try {
            result = btnContinue.isEnabled();
        } catch (NoSuchElementException e) {
            log("Welcome page of forms is not loaded");
        }
        driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
        return result;
    }

    @Override
    public <T extends PortalFormPage> T clickSaveContinue(Class<T> nextPageClass) throws Exception {
        return super.clickSaveContinue(nextPageClass, this.btnContinue);
    }

    /**
     * @param nextPageClass
     *            - class of the page that follows immediately after the welcome page
     * @return Initiated object for the next page
     * @throws Exception
     */
    public <T extends PortalFormPage> T initToFirstPage(Class<T> nextPageClass) throws Exception {
        if (isWelcomePageLoaded()) {
            return clickSaveContinue(nextPageClass);
        } else {
            goToFirstPage();
            return PageFactory.initElements(driver, nextPageClass);
        }
    }
}
