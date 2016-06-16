package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class JalapenoForgotPasswordPage3 extends BasePageObject {

    @FindBy(how = How.LINK_TEXT, using = "Sign In Now.")
    public WebElement signInButton;

    @FindBy(how = How.ID, using = "secretAnswer_forgot")
    public WebElement secretAnswer;

    @FindBy(how = How.ID, using = "forgotEnterSecretAnswerFormContinueButton")
    public WebElement continueAndResetButton;

    public JalapenoForgotPasswordPage3(WebDriver driver, String url) {
        super(driver);
        IHGUtil.PrintMethodName();
        log("Loading ForgotPasswordPage3");
        String sanitizedUrl = url.trim();
        log("URL: " + sanitizedUrl);
        driver.get(sanitizedUrl);
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
    }

    public boolean assessForgotPasswordPage3Elements() {

        boolean allElementsDisplayed = false;

        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
        webElementsList.add(signInButton);
        webElementsList.add(secretAnswer);
        webElementsList.add(continueAndResetButton);

        for (WebElement w : webElementsList) {

            try {
                IHGUtil.waitForElement(driver, 20, w);
                log("Checking WebElement" + w.toString());
                if (w.isDisplayed()) {
                    log("WebElement " + w.toString() + "is displayed");
                    allElementsDisplayed = true;
                } else {
                    log("WebElement " + w.toString() + "is NOT displayed");
                    return false;
                }
            }

            catch (Throwable e) {
                log(e.getStackTrace().toString());
            }

        }
        return allElementsDisplayed;
    }

    public JalapenoForgotPasswordPage4 fillInSecretAnswer(String answer) {

        // catching webdriver exception which started to show up after selenium 2.45 and firefox 36 updates
        // try removing the try catch once newrelic is deprecated and fully removed
        try {
            secretAnswer.sendKeys(answer);
        } catch (org.openqa.selenium.WebDriverException e) {
            secretAnswer.sendKeys(answer);
        }
        continueAndResetButton.click();

        return PageFactory.initElements(driver, JalapenoForgotPasswordPage4.class);
    }

}
