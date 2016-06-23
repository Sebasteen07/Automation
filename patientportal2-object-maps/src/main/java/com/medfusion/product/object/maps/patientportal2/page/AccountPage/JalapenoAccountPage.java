package com.medfusion.product.object.maps.patientportal2.page.AccountPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPage;

public class JalapenoAccountPage extends JalapenoMenu {

    @FindBy(how = How.XPATH, using = "//*[@id='frame']/div[2]/ul/li/div/div[3]/button")
    private WebElement editMyAccountButton;

    public JalapenoAccountPage(WebDriver driver) {
        super(driver);
    }

    public JalapenoMyAccountPage clickOnEditMyAccount() {

        log("Trying to click on Edit button for My Account");
        editMyAccountButton.click();

        return PageFactory.initElements(driver, JalapenoMyAccountPage.class);
    }

    @Override
    public boolean assessBasicPageElements() {

        ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
        webElementsList.add(editMyAccountButton);

        return assessPageElements(webElementsList);
    }
}