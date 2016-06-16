package com.medfusion.product.object.maps.patientportal1.page.createAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class CreatePatientVerifyPhonePage extends BasePageObject {

    @FindBy(name = "inputs:0:input:input")
    private WebElement homePhone;

    @FindBy(name = "buttons:submit")
    private WebElement btnSubmit;

    public CreatePatientVerifyPhonePage(WebDriver driver) {
        super(driver);
        // TODO Auto-generated constructor stub
    }

    public CreateAccountExistingUserPage fillPhone(WebDriver driver, String phone) {

        PortalUtil.setPortalFrame(driver);
        IHGUtil.waitForElement(driver, 6, homePhone);
        homePhone.sendKeys(phone);

        btnSubmit.click();

        return PageFactory.initElements(driver, CreateAccountExistingUserPage.class);
    }

}
