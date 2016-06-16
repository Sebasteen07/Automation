package com.medfusion.product.patientportal2.tests;

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

public class JalapenoHealthKey6Of6DifferentPractice extends BaseTestNGWebDriver {

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

    public JalapenoHomePage healthKey6Of6DifferentPractice(WebDriver driver, PropertyFileLoader testData)
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

        JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getDifferentUrl());
        JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickCreateANewAccountButton();

        jalapenoCreateAccountPage.fillInDataPage(testData.getHealthKey6Of6FirstnameSamePractice(),
                testData.getHealthKey6Of6LastnameSamePractice(), testData.getHealthKey6Of6EmailSamePractice(),
                testData.getHealthKey6Of6DOBMonthSamePractice(), testData.getHealthKey6Of6DOBDaySamePractice(),
                testData.getHealthKey6Of6DOBYearSamePractice(), IHGUtil.Gender.MALE,
                testData.getHealthKey6Of6ZipSamePractice());
        assertTrue(jalapenoLoginPage.isTextVisible(JalapenoConstants.HEALTHKEY_MATCH_DIFFERENT_PRACTICE_MESSAGES));

        return PageFactory.initElements(driver, JalapenoHomePage.class);
    }

}
