package com.medfusion.product.object.maps.patientportal2.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage.JalapenoForgotPasswordPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoLoginPage extends MedfusionPage {

    @FindBy(how = How.ID, using = "userid")
    public WebElement inputUserId;

    @FindBy(how = How.ID, using = "password")
    public WebElement inputPassword;

    @FindBy(how = How.ID, using = "signin_btn")
    public WebElement buttonSignIn;

    @FindBy(how = How.ID, using = "create_btn")
    public WebElement buttonCreateANewAccount;

    @FindBy(how = How.ID, using = "remember")
    public WebElement rememberUserNameCheckbox;

    @FindBy(how = How.ID, using = "paynow_button")
    public WebElement buttonPayNow;

    @FindBy(how = How.PARTIAL_LINK_TEXT, using = "I forgot my user name and/or password.")
    public WebElement forgotUserOrPasswordButton;

    @FindBy(how = How.ID, using = "paymentPreference_Electronic")
    private WebElement electronicPaymentPreference;

    @FindBy(how = How.ID, using = "updateMissingInfoButton")
    private WebElement okButton;

    public JalapenoLoginPage(WebDriver driver, String url) {
        super(driver, url);
    }

    public JalapenoLoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean assessBasicPageElements() {

        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
        webElementsList.add(inputUserId);
        webElementsList.add(inputPassword);
        webElementsList.add(forgotUserOrPasswordButton);
        webElementsList.add(buttonSignIn);
        webElementsList.add(buttonCreateANewAccount);
        webElementsList.add(rememberUserNameCheckbox);
        webElementsList.add(buttonPayNow);

        return assessPageElements(webElementsList);
    }

    /**
     * Login successfully or throws error
     * 
     * @param username
     * @param password
     * @return
     * @throws UnsupportedOperationException
     */
    public JalapenoHomePage login(String username, String password) throws UnsupportedOperationException {

        log("Login with credentials: [" + username + "] [" + password + "]");
        updateWebElement(inputUserId, username);
        updateWebElement(inputPassword, password);
        clickOnElement(buttonSignIn);

        selectStatementIfRequired();
        return PageFactory.initElements(driver, JalapenoHomePage.class);
    }

    // TODO make method for unsuccessful login

    public JalapenoCreateAccountPage clickCreateANewAccountButton() {

        clickOnElement(buttonCreateANewAccount);
        return PageFactory.initElements(driver, JalapenoCreateAccountPage.class);
    }

    public JalapenoForgotPasswordPage clickOnForgotPasswordButton() {
        clickOnElement(forgotUserOrPasswordButton);
        return PageFactory.initElements(driver, JalapenoForgotPasswordPage.class);
    }

    private void selectStatementIfRequired() {
        if (new IHGUtil(driver).exists(electronicPaymentPreference)) {
            electronicPaymentPreference.click();
            okButton.click();
        }
    }

}
