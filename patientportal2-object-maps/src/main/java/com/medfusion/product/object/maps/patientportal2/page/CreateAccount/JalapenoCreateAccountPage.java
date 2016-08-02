package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;

public class JalapenoCreateAccountPage extends MedfusionPage {

    /**
     * @Author:Jakub Calabek
     * @Date:24.7.2013
     */

    @FindBy(how = How.ID, using = "firstName")
    private WebElement inputPatientFirstName;

    @FindBy(how = How.ID, using = "lastName")
    private WebElement inputPatientLastName;

    @FindBy(how = How.ID, using = "email")
    private WebElement inputEmailAddresss;

    @FindBy(how = How.XPATH, using = "(//select[@id='birthDate_month'])[4]")
    private WebElement inputDateOfBirthMonth;

    @FindBy(how = How.XPATH, using = "(//input[@id='birthDate_day'])[4]")
    private WebElement inputDateOfBirthDay;

    @FindBy(how = How.XPATH, using = "(//input[@id='birthDate_year'])[4]")
    private WebElement inputDateOfBirthYear;

    @FindBy(how = How.ID, using = "gender_male")
    private WebElement maleGender;

    @FindBy(how = How.ID, using = "gender_female")
    private WebElement femaleGender;

    @FindBy(how = How.ID, using = "address1")
    private WebElement inputAddress1;

    @FindBy(how = How.ID, using = "address2")
    private WebElement inputAddress2;

    @FindBy(how = How.ID, using = "city")
    private WebElement inputCity;

    @FindBy(how = How.ID, using = "state")
    private WebElement inputState;

    @FindBy(how = How.XPATH, using = "(//input[@id='postalCode'])[4]")
    private WebElement inputZipCode;

    @FindBy(how = How.XPATH, using = "(//a[@id='cancelStep'])[4]")
    private WebElement buttonCancel;

    @FindBy(how = How.XPATH, using = "(//button[@id='nextStep'])[4]")
    private WebElement buttonChooseUserId;

    public JalapenoCreateAccountPage(WebDriver driver) {
        super(driver);
    }

    public JalapenoPatientActivationPage fillInDataPage(String firstName, String lastName, String email, String month,
            String day, String year, Gender gender, String zipCode) {
        IHGUtil.PrintMethodName();

        setName(firstName, lastName);
        setEmail(email);
        setDateOfBirth(month, day, year);
        setGender(gender);
        setZipCode(zipCode);

        updateWebElement(inputAddress1, "5501 Dillard Dr");
        updateWebElement(inputAddress2, "");
        updateWebElement(inputCity, "Cary");
        updateWebElement(inputState, "Alabama");
        updateWebElement(inputZipCode, "94043");

        return goToNextPage();
    }

    public JalapenoCreateAccountPage2 fillInDataPage1(String firstName, String lastName, String email, String month,
            String day, String year, boolean gender, String zipCode) {
        IHGUtil.PrintMethodName();

        log("Setting Firstname as " + firstName);
        inputPatientFirstName.sendKeys(firstName);
        log("Setting LastName as " + lastName);
        inputPatientLastName.sendKeys(lastName);
        log("Setting email address as " + email);
        inputEmailAddresss.sendKeys(email);

        log("Setting up month of birth as " + month);
        Select selectMonth = new Select(inputDateOfBirthMonth);
        selectMonth.selectByValue(month);

        log("Setting up day of birth as " + day);
        inputDateOfBirthDay.sendKeys(day);
        log("Setting up day of year as " + year);
        inputDateOfBirthYear.sendKeys(year);

        log("Setting up gender as " + gender);
        if (gender) {
            maleGender.click();
        } else {
            femaleGender.click();
        }

        log("Setting up ZipCode as " + zipCode);
        inputZipCode.sendKeys(zipCode);

        buttonChooseUserId.click();

        return PageFactory.initElements(driver, JalapenoCreateAccountPage2.class);
    }

    public JalapenoPatientActivationPage fillInDataPage(String firstName, String lastName, String email,
            PropertyFileLoader testData) {

        return fillInDataPage(firstName, lastName, email, testData.getDOBMonthText(), testData.getDOBDay(),
                testData.getDOBYear(), Gender.MALE, testData.getZipCode());
    }

    public JalapenoPatientActivationPage goToNextPage() {
        buttonChooseUserId.click();

        return new JalapenoPatientActivationPage(driver);
    }

    public JalapenoCreateAccountPage setZipCode(String zipCode) {
        updateWebElement(inputZipCode, zipCode);
        return this;
    }

    public JalapenoCreateAccountPage setGender(Gender gender) {

        log("Setting up gender as " + gender);
        if (gender == Gender.MALE) {
            maleGender.click();
        } else {
            femaleGender.click();
        }
        return this;
    }

    public JalapenoCreateAccountPage setDateOfBirth(String month, String day, String year) {
        updateWebElement(inputDateOfBirthMonth, month);
        updateWebElement(inputDateOfBirthDay, day);
        updateWebElement(inputDateOfBirthYear, year);
        return this;
    }

    public JalapenoCreateAccountPage setDateOfBirth(PropertyFileLoader testData) {
        return setDateOfBirth(testData.getDOBMonthText(), testData.getDOBDay(), testData.getDOBYear());
    }

    public JalapenoCreateAccountPage setEmail(String email) {
        updateWebElement(inputEmailAddresss, email);
        return this;
    }

    public JalapenoCreateAccountPage setName(String firstName, String lastName) {
        updateWebElement(inputPatientFirstName, firstName);
        updateWebElement(inputPatientLastName, lastName);
        return this;
    }

    public boolean assessCreateAccountPageElements() {

        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
        webElementsList.add(inputPatientFirstName);
        webElementsList.add(inputPatientLastName);
        webElementsList.add(inputEmailAddresss);
        webElementsList.add(inputDateOfBirthMonth);
        webElementsList.add(inputDateOfBirthDay);
        webElementsList.add(inputDateOfBirthYear);
        webElementsList.add(maleGender);
        webElementsList.add(femaleGender);
        webElementsList.add(inputAddress1);
        webElementsList.add(inputAddress2);
        webElementsList.add(inputCity);
        webElementsList.add(inputState);
        webElementsList.add(inputZipCode);
        webElementsList.add(buttonCancel);
        webElementsList.add(buttonChooseUserId);

        return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
    }

    public boolean isTextVisible(String text) {
        return driver.findElement(By.xpath("// * [contains(text(),'" + text + "')]")).isDisplayed();
    }

    public JalapenoCreateAccountPage fillInPatientData(JalapenoPatient patient) {

        updateWebElement(inputPatientFirstName, patient.getFirstName());
        updateWebElement(inputPatientLastName, patient.getLastName());
        updateWebElement(inputEmailAddresss, patient.getEmail());

        updateWebElement(inputDateOfBirthMonth, patient.getDOBMonthText());
        updateWebElement(inputDateOfBirthDay, patient.getDOBDay());
        updateWebElement(inputDateOfBirthYear, patient.getDOBYear());

        if (patient.getGender() == Gender.MALE) {
            maleGender.click();
        } else {
            femaleGender.click();
        }

        updateWebElement(inputAddress1, patient.getAddress1());
        updateWebElement(inputAddress2, patient.getAddress2());
        updateWebElement(inputCity, patient.getCity());
        updateWebElement(inputState, patient.getState());
        updateWebElement(inputZipCode, patient.getZipCode());

        return this;
    }
}
