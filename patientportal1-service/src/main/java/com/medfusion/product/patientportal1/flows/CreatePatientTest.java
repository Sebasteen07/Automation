package com.medfusion.product.patientportal1.flows;

import org.openqa.selenium.WebDriver;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.createAccount.CreateAccountPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;
import com.medfusion.product.patientportal1.utils.TestcasesData;

public class CreatePatientTest extends BaseTestNGWebDriver {

    private String email = "";
    private String password = "";
    private String url = "";
    private String firstName = "";
    private String lastName = "";

    // Getters for getting the email and password value and reusing in other tests
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

    public CreatePatientTest(String email, String password, String url) {
        super();
        this.email = email;
        this.password = password;
        this.url = url;
    }

    public CreatePatientTest() {
        super();
    }

    public MyPatientPage createPatient(WebDriver driver, TestcasesData testcasesData) throws Exception {

        log("Test Case: testCreatePatient");
        log("Execution Environment: " + IHGUtil.getEnvironmentType());
        log("Execution Browser: " + TestConfig.getBrowserType());

        /*
         * Not very elegant way of changing URL the method uses
         * If someone sets the URL in the object before calling this method then the set URL is be used
         * otherwise (the default way) the URL from testcasesData.geturl() is used
         */
        if (url.isEmpty()) {
            url = testcasesData.geturl();
        }

        log("step 1: Get Data from Excel");
        log("URL: " + url);

        log("step 2: Click Create An Account");
        PortalLoginPage loginpage = new PortalLoginPage(driver, url);
        CreateAccountPage pCreateAccountPage = loginpage.signUp();

        log("step 3: Fill details in Create Account Page");
        // Setting the variables for user in other tests
        email = PortalUtil.createRandomEmailAddress(testcasesData.getEmail());
        firstName = testcasesData.getFirstName() + PortalUtil.createRandomNumber();
        lastName = testcasesData.getLastName() + PortalUtil.createRandomNumber();
        password = testcasesData.getPassword();
        log("email:-" + email);
        MyPatientPage pMyPatientPage = pCreateAccountPage.createAccountPage(firstName, lastName, email,
                testcasesData.getPhoneNumber(), testcasesData.getZip(), testcasesData.getAddress(),
                testcasesData.getPassword(), testcasesData.getSecretQuestion(), testcasesData.getAnswer(),
                testcasesData.getAddressState(), testcasesData.getAddressCity());

        return this.loginAsNewPatient(driver, pMyPatientPage);
    }

    public MyPatientPage loginAsNewPatient(WebDriver driver, MyPatientPage pMyPatientPage) throws InterruptedException {

        log("step 4: Assert Webelements in MyPatientPage");
        assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

        log("step 5: Logout");
        pMyPatientPage.clickLogout(driver);

        log("step 6: Login as new user");
        PortalLoginPage loginpage = new PortalLoginPage(driver, url);
        pMyPatientPage = loginpage.login(email, password);

        log("step 7: Assert Webelements in MyPatientPage");
        assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

        log("Patient successfully created");
        log("Username: " + email);
        log("Password: " + password);
        return pMyPatientPage;
    }

}
