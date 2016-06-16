package com.medfusion.product.object.maps.patientportal1.page.forgotuserid;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class ForgotUserIdSecretAnswerPage extends BasePageObject {

    public static final String PAGE_NAME = "Patient Portal Forgot User ID Secret Answer Page";

    @FindBy(xpath = ".//*[@fieldid='security.secretAnswer']")
    private WebElement securityAnswer;

    @FindBy(name = "buttons:submit")
    private WebElement btnSendEmail;

    @FindBy(xpath = ".//select[@name='inputs:0:input:input:month']")
    private WebElement monthDropDown;

    @FindBy(xpath = ".//input[@name='inputs:0:input:input:day']")
    private WebElement dateDropDown;

    @FindBy(xpath = ".//input[@name='inputs:0:input:input:year']")
    private WebElement yearDropDown;

    @FindBy(xpath = ".//input[@name = 'buttons:submit']")
    private WebElement continueButton;

    public ForgotUserIdSecretAnswerPage(WebDriver driver) {
        super(driver);
        // TODO Auto-generated constructor stub
    }

    /**
     * Indicates whether the page is loaded by checking for a specific element on the screen.
     * 
     * @return
     */
    public boolean isPageLoaded() {
        IHGUtil.PrintMethodName();
        PortalUtil.setPortalFrame(driver);

        boolean result = false;
        try {
            result = securityAnswer.isDisplayed();
        } catch (Exception e) {
            // Catch any element not found errors
        }

        return result;
    }

    /**
     * Will answer the patient's security question with the supplied answer.
     * 
     * @param answer
     * @return step 3 in forgot user id process
     */
    public ForgotUserIdConfirmationPage answerSecurityQuestion(String answer) {
        IHGUtil.PrintMethodName();
        PortalUtil.setPortalFrame(driver);

        securityAnswer.sendKeys(answer);
        btnSendEmail.click();
        return PageFactory.initElements(driver, ForgotUserIdConfirmationPage.class);
    }

    public void selectDOB(String day, String month, String year) {

        Select selMonth = new Select(monthDropDown);
        selMonth.selectByVisibleText(month);
        dateDropDown.sendKeys(day);
        yearDropDown.sendKeys(year);
        continueButton.click();
    }

}
