package com.medfusion.product.object.maps.patientportal2.page.AccountPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;

public class JalapenoAccountPage extends BasePageObject {

    @FindBy(how = How.XPATH, using = "//*[@id='frame']/div[2]/ul/li/div/div[3]/button")
    private WebElement editMyAccountButton;

    public JalapenoAccountPage(WebDriver driver) {
        super(driver);
        IHGUtil.PrintMethodName();
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
    }

    public JalapenoMyAccountProfilePage clickOnEditMyAccount() {

        log("Trying to click on Edit button for My Account");
        editMyAccountButton.click();

        return PageFactory.initElements(driver, JalapenoMyAccountProfilePage.class);
    }
}