package com.medfusion.product.object.maps.patientportal1.page.createAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class CreateAccountPasswordPage extends BasePageObject {

    public static final String PAGE_NAME = "Create Account Password Page";

    @FindBy(how = How.NAME, using = "inputs:0:input:input")
    private WebElement txtuserid;

    @FindBy(how = How.NAME, using = "inputs:3:input:input")
    private WebElement txtconfirmPassword;

    @FindBy(how = How.NAME, using = "inputs:2:input:input")
    private WebElement txtpassword;

    @FindBy(how = How.NAME, using = "inputs:4:input:input")
    private WebElement dropDownsecretQuestion;

    @FindBy(how = How.NAME, using = "inputs:5:input:input")
    private WebElement txtsecurityAnswer;

    @FindBy(how = How.NAME, using = "inputs:6:input:input")
    private WebElement dropDownpreferredLocation;

    @FindBy(how = How.CSS, using = "span[class*='first_row']")
    private WebElement providerFirstRow;

    @FindBy(how = How.NAME, using = "buttons:submit")
    private WebElement submit;

    @FindBy(how = How.XPATH, using = "//input[@id='autocomplete']")
    private WebElement txtpreferredDoctor;

    public CreateAccountPasswordPage(WebDriver driver) {
        super(driver);
    }

    /**
     * @param sPassword
     * @param sConfirmPassword
     * @param sSecurityQuestion
     * @param sSecurityAnswer
     * @param bETA_PROVIDER
     * @param bETA_LOCATION
     * @param bAgreeTOS
     * @return
     * @throws Exception
     */
    public MyPatientPage createPasswordSecurity(String sUserId, String sPassword, String sSecurityQuestion,
            String sSecurityAnswer, String sLocation, String sProvider) throws Exception {

        IHGUtil.PrintMethodName();
        driver.switchTo().defaultContent();
        PortalUtil.setPortalFrame(driver);

        log("User ID: " + sUserId);
        log("User ID: " + sPassword);
        txtuserid.sendKeys(sUserId);
        txtpassword.sendKeys(sPassword);
        txtconfirmPassword.sendKeys(sPassword);

        log("Security Qestion: " + sSecurityQuestion);
        log("Security Answer: " + sSecurityAnswer);
        Select securityQuestion = new Select(dropDownsecretQuestion);
        securityQuestion.selectByVisibleText(sSecurityQuestion);
        txtsecurityAnswer.sendKeys(sSecurityAnswer);
        Select preferredLocation = new Select(dropDownpreferredLocation);
        preferredLocation.selectByVisibleText(sLocation);
        txtpreferredDoctor.sendKeys(sProvider);
        IHGUtil.waitForElement(driver, 10, providerFirstRow);
        providerFirstRow.click();
        submit.click();
        return PageFactory.initElements(driver, MyPatientPage.class);
    }

    public MyPatientPage createPasswordSecurityOnBeta(String sUserId, String sPassword, String sSecurityQuestion,
            String sSecurityAnswer, String sLocation, String sProvider) throws Exception {

        IHGUtil.PrintMethodName();
        driver.switchTo().defaultContent();
        PortalUtil.setPortalFrame(driver);

        log("User ID: " + sUserId);
        log("Password: " + sPassword);
        txtuserid.sendKeys(sUserId);
        txtpassword.sendKeys(sPassword);
        txtconfirmPassword.sendKeys(sPassword);

        log("Security Qestion: " + sSecurityQuestion);
        log("Security Answer: " + sSecurityAnswer);
        Select securityQuestion = new Select(dropDownsecretQuestion);
        securityQuestion.selectByValue(sSecurityQuestion);
        txtsecurityAnswer.sendKeys(sSecurityAnswer);
        IHGUtil.waitForElement(driver, 10, dropDownpreferredLocation);
        Select preferredLocation = new Select(dropDownpreferredLocation);

        preferredLocation.selectByVisibleText(sLocation);
        txtpreferredDoctor.sendKeys(sProvider);
        IHGUtil.waitForElement(driver, 10, providerFirstRow);
        providerFirstRow.click();
        submit.click();
        return PageFactory.initElements(driver, MyPatientPage.class);
    }

}
