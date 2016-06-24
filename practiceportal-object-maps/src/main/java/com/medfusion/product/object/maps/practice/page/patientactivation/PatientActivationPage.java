package com.medfusion.product.object.maps.practice.page.patientactivation;

import static org.testng.AssertJUnit.*;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.integrationplatform.utils.PIDCTestData;
import com.medfusion.product.patientportal1.utils.PortalConstants;
import com.medfusion.product.practice.api.utils.PracticeConstants;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

public class PatientActivationPage extends BasePageObject {

    @FindBy(css = "a[href*='activate.newpat']")
    private WebElement addNewPatient;

    // Patient registration page
    @FindBy(css = "input[name='firstname']")
    private WebElement firstName;

    @FindBy(css = "input[name='lastname']")
    private WebElement lastName;

    @FindBy(css = "input[name='member_gender'][value='M']")
    private WebElement male;

    @FindBy(css = "input[name='member_gender'][value='F']")
    private WebElement female;

    @FindBy(css = "input[name='member_emrid']")
    private WebElement patientId;

    @FindBy(css = "input[name='email']")
    private WebElement email;

    @FindBy(css = "input[name='cemail']")
    private WebElement confirmEmail;

    @FindBy(css = "input[name='member_zip']")
    private WebElement zip;

    @FindBy(css = "input[type='submit']")
    private WebElement btnSubmit;

    @FindBy(css = "input[onclick*='checkVerified()']")
    private WebElement btnVerified;

    @FindBy(css = "input[onclick*='checkGenKey()']")
    private WebElement btnGenerateKey;

    @FindBy(css = "a[href*='activationCode']")
    private WebElement unlockLink;

    @FindBy(how = How.XPATH, using = "//input[@value='Done']")
    private WebElement btnDone;

    @FindBy(how = How.NAME, using = "birthday")
    private WebElement birthday;

    @FindBy(how = How.NAME, using = "zipcode")
    private WebElement zipcode;

    @FindBy(how = How.NAME, using = "ssn")
    private WebElement ssn;

    @FindBy(how = How.NAME, using = "buttons:submit")
    private WebElement Submit;

    @FindBy(css = "input[name='member_ssn1']")
    private WebElement SSN1;

    @FindBy(css = "input[name='member_ssn2']")
    private WebElement SSN2;

    @FindBy(css = "input[name='member_ssn3']")
    private WebElement SSN3;

    @FindBy(css = "input[name='member_home_ac']")
    private WebElement Home_No1;

    @FindBy(css = "input[name='member_home_pre']")
    private WebElement Home_No2;

    @FindBy(css = "input[name='member_home_suff']")
    private WebElement Home_No3;

    @FindBy(css = "input[name='member_addr1']")
    private WebElement AddLine1;

    @FindBy(css = "input[name='member_addr2']")
    private WebElement AddLine2;

    @FindBy(css = "input[name='member_city']")
    private WebElement City;

    @FindBy(name = "member_state")
    private WebElement State;

    @FindBy(xpath = ".//*[@id='content']/form/table/tbody/tr[8]/td[2]")
    private WebElement unlockCode;

    private String firstNameString = "";
    private String lastNameString = "";
    private String patientIdString = "";
    private String zipCodeString = "";
    private String emailAddressString = "";
    private String unlocklink = "";

    public String getFirstNameString() {
        return firstNameString;
    }

    public String getLastNameString() {
        return lastNameString;
    }

    public String getPatientIdString() {
        return patientIdString;
    }

    public String getZipCodeString() {
        return zipCodeString;
    }

    public String getEmailAddressString() {
        return emailAddressString;
    }

    public String getUnlockLink() {
        return unlocklink;
    }

    public PatientActivationPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddNewPatient() {
        IHGUtil.PrintMethodName();
        addNewPatient.click();
    }

    public void setInitialDetails(String sEmail) {
        firstNameString = "Beta" + IHGUtil.createRandomNumericString();
        lastNameString = "Tester";
        emailAddressString = sEmail;
        patientIdString = emailAddressString;

        IHGUtil.PrintMethodName();
        Log4jUtil.log("New Random First Name is " + firstNameString);
        firstName.sendKeys(firstNameString);
        lastName.sendKeys(lastNameString);
        male.click();
        Log4jUtil.log("New Random patientid is " + patientIdString);
        patientId.sendKeys(patientIdString);

        Log4jUtil.log("New Random Email is " + emailAddressString);
        email.sendKeys(emailAddressString);
        confirmEmail.sendKeys(emailAddressString);

        setDOB(PortalConstants.DateOfBirthMonth, PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);

        AddLine1.sendKeys("5501 Dillard Dr");
        City.sendKeys("Cary");
        zip.sendKeys(PracticeConstants.Zipcode);

        clickRegPatient();
        clickVerify();

        IHGUtil.waitForElement(driver, 10, unlockLink);
        unlocklink = unlockLink.getText().trim();
        Assert.assertTrue("### ERROR: Couldn't get unlock link", !unlocklink.isEmpty());

        Log4jUtil.log("#### The unlock link exists and the link is:" + unlocklink);
        clickDone();

    }

    public String setInitialDetailsAllFields(String firstName, String lastName, String gender, String patientID,
            String homePhone, String email, String month, String day, String year, String address1, String address2,
            String city, String state, String zipCode) {
        IHGUtil.PrintMethodName();

        log("Patient Name is " + firstName + " " + lastName);
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        if (gender.equals("M"))
            this.male.click();
        else
            this.female.click();

        log("PatientID " + patientID);
        this.patientId.sendKeys(patientID);
        this.Home_No1.sendKeys(homePhone.substring(0, 3));
        this.Home_No2.sendKeys(homePhone.substring(3, 6));
        this.Home_No3.sendKeys(homePhone.substring(6, 10));

        log("Patient email is " + email);
        this.email.sendKeys(email);
        this.confirmEmail.sendKeys(email);

        log("Patient DOB is " + month + "/" + day + "/" + year);
        Select dobMonth = new Select(driver.findElement(By.name("dob_m")));
        dobMonth.selectByValue(month);
        Select dobDay = new Select(driver.findElement(By.name("dob_d")));
        dobDay.selectByVisibleText(day);
        Select dobYear = new Select(driver.findElement(By.name("dob_y")));
        dobYear.selectByVisibleText(year);

        log("Patient ZIP is " + zipCode);
        this.AddLine1.sendKeys(address1);
        this.AddLine2.sendKeys(address2);
        this.City.sendKeys(city);
        Select stateSelect = new Select(State);
        stateSelect.selectByVisibleText(state);
        this.zip.sendKeys(zipCode);

        clickRegPatient();
        clickVerify();

        IHGUtil.waitForElement(driver, 10, unlockLink);
        unlocklink = unlockLink.getText().trim();
        assertNotNull("### ERROR: Couldn't get unlock link", unlocklink);

        log("#### The unlock link exists and the link is:" + unlocklink);
        clickDone();
        return unlocklink;
    }

    public void setDOB(String month, String day, String year) {

        Select dobMonth = new Select(driver.findElement(By.name("dob_m")));
        dobMonth.selectByVisibleText(month);

        Select dobDay = new Select(driver.findElement(By.name("dob_d")));
        dobDay.selectByVisibleText(day);

        Select dobYear = new Select(driver.findElement(By.name("dob_y")));
        dobYear.selectByVisibleText(year);
    }

    public void clickRegPatient() {
        IHGUtil.PrintMethodName();
        btnSubmit.click();
    }

    public void clickVerify() {
        IHGUtil.PrintMethodName();
        btnVerified.click();
    }

    public void clickGetKey() {
        IHGUtil.PrintMethodName();
        btnGenerateKey.click();
    }

    public void clickDone() {
        IHGUtil.PrintMethodName();
        btnDone.click();
    }

    public String setFullDetails(PIDCTestData testData) {
        firstNameString = "MF" + IHGUtil.createRandomNumericString();
        patientIdString = IHGUtil.createRandomNumericString();
        emailAddressString = IHGUtil.createRandomEmailAddress(testData.getEmail());

        IHGUtil.PrintMethodName();
        Log4jUtil.log("First Name is :" + firstNameString);
        firstName.sendKeys(firstNameString);

        lastName.sendKeys(testData.getLastName());
        male.click();
        /*
         * SSN1.sendKeys(testData.getSSN().subSequence(0, 3));
         * SSN2.sendKeys(testData.getSSN().subSequence(3, 5));
         * SSN3.sendKeys(testData.getSSN().subSequence(5, 9));
         */
        Log4jUtil.log("Patientid is :" + patientIdString);
        patientId.sendKeys(patientIdString);
        Home_No1.sendKeys(testData.getHomePhoneNo().substring(0, 3));
        Home_No2.sendKeys(testData.getHomePhoneNo().substring(3, 6));
        Home_No3.sendKeys(testData.getHomePhoneNo().substring(6, 10));
        Log4jUtil.log("Email is :" + emailAddressString);
        email.sendKeys(emailAddressString);
        confirmEmail.sendKeys(emailAddressString);
        setDOB(PortalConstants.DateOfBirthMonth, PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);
        AddLine1.sendKeys(testData.getAddress1());
        AddLine2.sendKeys(testData.getAddress2());
        City.sendKeys(testData.getCity());
        Select stateSelect = new Select(State);
        stateSelect.selectByVisibleText(testData.getState());
        zip.sendKeys(testData.getZipCode());

        clickRegPatient();
        IHGUtil.waitForElement(driver, 30, btnVerified);
        clickVerify();

        IHGUtil.waitForElement(driver, 30, unlockLink);
        unlocklink = unlockLink.getText().trim();
        Assert.assertTrue("### ERROR: Couldn't get unlock link", !unlocklink.equals(""));
        String activationCode = unlockCode.getText();
        Log4jUtil.log("Unlock Code :" + activationCode);

        Log4jUtil.log("#### The unlock link exists and the link is :" + unlocklink);
        clickDone();
        return activationCode;
    }

    public boolean checkGuardianUrl(String url) {
        IHGUtil.PrintMethodName();
        return url.contains("guardian");
    }

}
