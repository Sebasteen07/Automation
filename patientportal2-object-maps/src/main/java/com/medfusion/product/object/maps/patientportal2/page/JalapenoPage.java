package com.medfusion.product.object.maps.patientportal2.page;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;

public class JalapenoPage extends IHGUtil {

    @FindBy(how = How.LINK_TEXT, using = "Account")
    private WebElement accountButton;

    @FindBy(how = How.ID, using = "open-top-loggedIn-btn")
    private WebElement rightDropdownButton;

    @FindBy(how = How.ID, using = "signout_dropdown")
    private WebElement signoutDropdownButton;

    @FindBy(how = How.ID, using = "signout")
    private WebElement signoutButton;

    public JalapenoPage(WebDriver driver) {
        super(driver);
        IHGUtil.PrintMethodName();
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
    }

    public JalapenoAccountPage clickOnAccount(WebDriver driver) {

        log("Trying to click on Account button - regular resolution");

        try {
            accountButton.click();
        } catch (NoSuchElementException ex) {
            log("Did not find Account button, trying mobile version size");
            rightDropdownButton.click();
            accountButton.click();
        }

        return PageFactory.initElements(driver, JalapenoAccountPage.class);
    }

    public JalapenoLoginPage logout(WebDriver driver) {

        IHGUtil.PrintMethodName();
        log("Trying to click on Logout button - regular resolution");

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
