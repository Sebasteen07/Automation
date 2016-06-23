package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoPatientCreateSecurityDetailsPage extends MedfusionPage {

    @FindBy(how = How.XPATH, using = "(//input[@id='userid'])[2]")
    private WebElement inputUserId;

    @FindBy(how = How.XPATH, using = "(//input[@id='password'])[2]")
    private WebElement inputPassword;

    @FindBy(how = How.ID, using = "secretQuestion")
    private WebElement inputSecretQuestion;

    @FindBy(how = How.ID, using = "secretAnswer")
    private WebElement inputSecretAnswer;

    @FindBy(how = How.ID, using = "phone1")
    private WebElement inputPhone1;

    @FindBy(how = How.ID, using = "phone2")
    private WebElement inputPhone2;

    @FindBy(how = How.ID, using = "phone3")
    private WebElement inputPhone3;

    @FindBy(how = How.ID, using = "phone_type")
    private WebElement inputPhoneType;

    @FindBy(how = How.XPATH, using = "(//a[@id='prevStep'])[2]")
    private WebElement prevStep;

    @FindBy(how = How.ID, using = "finishStep")
    private WebElement finishStep;

    @Deprecated
    @FindBy(how = How.XPATH, using = "(//input[@id='postalCode'])[1]")
    private WebElement postalCode;

    @Deprecated
    @FindBy(how = How.XPATH, using = "(//select[@id='birthDate_month'])[1]")
    private WebElement birthDate_month;

    @Deprecated
    @FindBy(how = How.XPATH, using = "(//input[@id='birthDate_day'])[1]")
    private WebElement birthDate_day;

    @Deprecated
    @FindBy(how = How.XPATH, using = "(//input[@id='birthDate_year'])[1]")
    private WebElement birthDate_year;

    @Deprecated
    @FindBy(how = How.XPATH, using = "(//button[@id='nextStep'])[1]")
    private WebElement nextStep;

    @FindBy(how = How.XPATH, using = "(//select[@id='preferredLocationId'])[2]")
    private WebElement primaryLocationElement;

    @FindBy(how = How.ID, using = "paymentPreference_Electronic")
    private WebElement electronicPaymentPreference;

    @FindBy(how = How.ID, using = "paymentPreference_Paper")
    private WebElement paperPaymentPreference;

    // Both option currently commented out in lightbox and not supported (12/09/2015)
    @FindBy(how = How.ID, using = "paymentPreference_Both")
    private WebElement bothPaymentPreference;

    @FindBy(how = How.ID, using = "updateStatementPrefButton")
    private WebElement okButton;

    public JalapenoPatientCreateSecurityDetailsPage(WebDriver driver) {
        super(driver);
    }

    public JalapenoPatientCreateSecurityDetailsPage(WebDriver driver, String url) {
        super(driver, url);
    }

    @Override
    public boolean assessBasicPageElements() {
        return assessBasicPageElements(false);
    }

    public boolean assessBasicPageElements(boolean checkPrevStepButton) {

        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
        webElementsList.add(inputUserId);
        webElementsList.add(inputPassword);
        webElementsList.add(inputSecretQuestion);
        webElementsList.add(inputSecretAnswer);
        webElementsList.add(inputPhone1);
        webElementsList.add(inputPhone2);
        webElementsList.add(inputPhone3);
        webElementsList.add(inputPhoneType);
        webElementsList.add(finishStep);
        if (checkPrevStepButton) {
            webElementsList.add(prevStep);
        }

        return assessPageElements(webElementsList);
    }

    // TODO functionality from other page - move it there
    @Deprecated
    public void verifyPatientIdentity(String zipCode, String month, String day, String year) {
        IHGUtil.PrintMethodName();

        log("Verifying identity");
        log("Setting zipCode as " + zipCode);
        postalCode.sendKeys(zipCode);

        log("Setting DOB month as " + month);
        birthDate_month.sendKeys(month);

        log("Setting DOB day as " + day);
        birthDate_day.sendKeys(day);

        log("Setting DOB year as " + year);
        birthDate_year.sendKeys(year);

        log("Click on continue button");
        nextStep.click();

    }

    public JalapenoHomePage fillInPatientActivation(String userId, String password, String secretQuestion,
            String secretAnswer, String phoneNumber) {
        return fillInPatientActivationWithDeliveryPreference(userId, password, secretQuestion, secretAnswer,
                phoneNumber, 2);

    }

    public JalapenoHomePage fillInPatientActivationWithDeliveryPreference(String userId, String password,
            String secretQuestion, String secretAnswer, String phoneNumber, int deliveryPref) {
        IHGUtil.PrintMethodName();

        log("Setting User Name as " + userId);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(inputUserId));
        inputUserId.sendKeys(userId);

        log("Setting Password as " + password);
        inputPassword.sendKeys(password);

        log("Secret Question as " + secretQuestion);
        inputSecretQuestion.sendKeys(secretQuestion);

        log("Secret Answer as " + secretAnswer);
        inputSecretAnswer.sendKeys(secretAnswer);

        log("Phone number as " + phoneNumber);

        // splitting phone to the text boxes
        String phone1 = phoneNumber.substring(0, 3);
        String phone2 = phoneNumber.substring(3, 6);
        String phone3 = phoneNumber.substring(6, 10);

        inputPhone1.sendKeys(phone1);
        inputPhone2.sendKeys(phone2);
        inputPhone3.sendKeys(phone3);

        // if there is a dropdown for selecting location, pick the first one
        if (IHGUtil.exists(driver, 2, primaryLocationElement)) {
            Select primaryLocationSelect = new Select(primaryLocationElement);
            primaryLocationSelect.selectByIndex(1);
        }

        finishStep.click();
        selectStatementIfRequired(deliveryPref);

        return PageFactory.initElements(driver, JalapenoHomePage.class);
    }

    public JalapenoHomePage fillInSecurityDetails(String userId, String password, PropertyFileLoader testData) {
        return fillInPatientActivation(userId, password, testData.getSecretQuestion(), testData.getSecretAnswer(),
                testData.getPhoneNumber());
    }

    // delivery pref as in sitegen, 1 = paper, 2 = electronic, 3 = both
    public void selectStatementIfRequired(int deliveryPref) {

        if (new IHGUtil(driver).exists(electronicPaymentPreference)) {
            log("Statement delivery preference lightbox is displayed");

            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(okButton));
            if (deliveryPref == 1)
                paperPaymentPreference.click();
            else if (deliveryPref == 2)
                electronicPaymentPreference.click();
            else if (deliveryPref == 3)
                bothPaymentPreference.click();
            okButton.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Deprecated
    public boolean assessPatientActivationVerifyPageElements() {

        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
        webElementsList.add(postalCode);
        webElementsList.add(birthDate_day);
        webElementsList.add(birthDate_month);
        webElementsList.add(birthDate_year);

        return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
    }

}
