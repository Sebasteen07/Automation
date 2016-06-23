package com.medfusion.product.object.maps.patientportal2.page;

import java.util.ArrayList;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage.JalapenoAppointmentsPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;

/**
 * Contains menu elements that are on every page after login
 */
public abstract class JalapenoMenu extends MedfusionPage {

    @FindBy(how = How.XPATH, using = "//*[@id='home']/a/span")
    private WebElement homeMenu;

    @FindBy(how = How.XPATH, using = "//*[@id='messages_lhn']/a/span")
    private WebElement messagesMenu;

    @FindBy(how = How.XPATH, using = "//*[@id='appointments_lhn']/a/span")
    private WebElement appointmentsMenu;

    @FindBy(how = How.XPATH, using = "//*[@id='aska_lhn']/a/span")
    private WebElement askAQuestionMenu;

    @FindBy(how = How.XPATH, using = "//*[@id='prescriptions_lhn']/a/span")
    private WebElement prescriptionsMenu;

    @FindBy(how = How.XPATH, using = "//*[@id='payments_lhn']/a/span")
    private WebElement payBillsMenu;

    @FindBy(how = How.XPATH, using = "//*[@id='forms_lhn']/a/span")
    private WebElement healthFormsMenu;

    @FindBy(how = How.LINK_TEXT, using = "Account")
    private WebElement accountButton;

    @FindBy(how = How.ID, using = "open-top-loggedIn-btn")
    private WebElement rightDropdownButton;

    @FindBy(how = How.ID, using = "signout_dropdown")
    private WebElement signoutDropdownButton;

    @FindBy(how = How.ID, using = "signout")
    private WebElement signoutButton;

    public JalapenoMenu(WebDriver driver) {
        super(driver);
    }

    public boolean assessMenuElements() {
        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

        webElementsList.add(homeMenu);
        webElementsList.add(messagesMenu);
        webElementsList.add(appointmentsMenu);
        webElementsList.add(askAQuestionMenu);
        webElementsList.add(prescriptionsMenu);
        webElementsList.add(payBillsMenu);
        webElementsList.add(healthFormsMenu);
        webElementsList.add(accountButton);
        webElementsList.add(signoutButton);

        return assessPageElements(webElementsList);
    }

    public JalapenoHomePage clickOnMenuHome() {
        log("Clicking on Home menu button");
        homeMenu.click();
        return PageFactory.initElements(driver, JalapenoHomePage.class);
    }

    public JalapenoAppointmentsPage clickOnMenuMessages() {
        log("Clicking on Messages menu button");
        messagesMenu.click();
        return PageFactory.initElements(driver, JalapenoAppointmentsPage.class);
    }

    public JalapenoAppointmentsPage clickOnMenuAppointments() {
        log("Clicking on Appointments menu button");
        appointmentsMenu.click();
        return PageFactory.initElements(driver, JalapenoAppointmentsPage.class);
    }

    public JalapenoAskAStaffPage clickOnMenuAskAQuestionMenu() {
        log("Clicking on Ask a Question menu button");
        askAQuestionMenu.click();
        return PageFactory.initElements(driver, JalapenoAskAStaffPage.class);
    }

    public JalapenoPrescriptionsPage clickOnMenuPrescriptions() {
        log("Clicking on Prescriptions menu button");
        prescriptionsMenu.click();
        return PageFactory.initElements(driver, JalapenoPrescriptionsPage.class);
    }

    public JalapenoPayBillsMakePaymentPage clickOnMenuPayBills() {
        log("Clicking on Pay Bills menu button");
        payBillsMenu.click();
        return PageFactory.initElements(driver, JalapenoPayBillsMakePaymentPage.class);
    }

    // TODO JalapenoHealthForms page does not exists
    // public JalapenoHealthForms clickOnMenuHealthForms() {
    // log("Clicking on Health Forms menu button");
    // healthFormsMenu.click();
    // return PageFactory.initElements(driver, JalapenoHealthForms.class);
    // }

    public JalapenoMyAccountPage goToMyAccountPage() {
        JalapenoAccountPage accountPage = clickOnAccount(driver);
        return accountPage.clickOnEditMyAccount();
    }

    public JalapenoAccountPage clickOnAccount(WebDriver driver) {

        log("Clicking on Account button - regular resolution");

        try {
            accountButton.click();
        } catch (NoSuchElementException ex) {
            log("Did not find Account button, trying mobile version size");
            rightDropdownButton.click();
            accountButton.click();
        }

        return PageFactory.initElements(driver, JalapenoAccountPage.class);
    }

    public JalapenoLoginPage clickOnLogout() {

        log("Clicking on Logout button - regular resolution");

        try {
            signoutButton.click();
        } catch (NoSuchElementException ex) {
            log("Did not find Logout button, trying mobile version size");
            rightDropdownButton.click();
            signoutDropdownButton.click();
        } catch (ElementNotVisibleException ex) {
            log("Element is not currently visible, trying mobile version size");
            rightDropdownButton.click();
            signoutDropdownButton.click();
        }

        return PageFactory.initElements(driver, JalapenoLoginPage.class);
    }

}
