package com.medfusion.product.patientportal2.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.patientportal2.utils.PortalUtil;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoHealthKey6Of6Inactive extends BaseTestNGWebDriver {

    private String email = "";
    private String password = "";
    private String url = "";
    private String firstName = "";
    private String lastName = "";

    // Getters for getting the email and password value and reusing in other
    // tests
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String str) {
        url = str;
    }

    public void setFirstName(String str) {
        firstName = str;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String str) {
        lastName = str;
    }

    public String getLastName() {
        return lastName;
    }

    public JalapenoHomePage healthKey6Of6Inactive(WebDriver driver, PropertyFileLoader testData)
            throws InterruptedException {
        IHGUtil.PrintMethodName();
        int randomize = PortalUtil.createRandomNumber();

        // Setting data according to test purpose
        if (email.isEmpty()) {
            email = IHGUtil.createRandomEmailAddress(testData.getEmail());
        }

        if (firstName.isEmpty()) {
            firstName = testData.getFirstName() + randomize;
        }

        if (lastName.isEmpty()) {
            lastName = testData.getLastName() + randomize;
        }

        JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
        JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();

        jalapenoCreateAccountPage.fillInDataPage(testData.getHealthKey6Of6FirstnameInactive(),
                testData.getHealthKey6Of6LastnameInactive(), testData.getHealthKey6Of6EmailInactive(),
                testData.getHealthKey6Of6DOBMonthInactive(), testData.getHealthKey6Of6DOBDayInactive(),
                testData.getHealthKey6Of6DOBYearInactive(), IHGUtil.Gender.MALE,
                testData.getHealthKey6Of6ZipInactive());
        driver.findElement(By.xpath(
                "// * [contains(text(),'" + JalapenoConstants.HEALTHKEY_MATCH_SAME_PRACTICE_INACTIVE_MESSAGES + "')]"))
                .isDisplayed();
        return PageFactory.initElements(driver, JalapenoHomePage.class);
    }

}
