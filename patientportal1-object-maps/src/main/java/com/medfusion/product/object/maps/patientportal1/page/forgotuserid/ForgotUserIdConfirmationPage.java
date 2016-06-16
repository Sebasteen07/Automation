package com.medfusion.product.object.maps.patientportal1.page.forgotuserid;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class ForgotUserIdConfirmationPage extends BasePageObject {

    public static final String PAGE_NAME = "Forgot User Id Confirmation Page";

    @FindBy(xpath = ".//*[@id='content']/div/div[contains(text(),'Check your email')]")
    private WebElement confirmationText;

    public ForgotUserIdConfirmationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Indicates whether the confirmation page loaded successfully.
     * 
     * @return true or false
     */
    public boolean confirmationPageLoaded() {
        IHGUtil.PrintMethodName();
        PortalUtil.setPortalFrame(driver);

        boolean result = false;
        try {
            result = confirmationText.isDisplayed();
        } catch (Exception e) {
            // Catch any element not found errors
        }

        return result;
    }

}

// Expected subject in email: Your User ID for IHGQA Automation NonIntegrated
