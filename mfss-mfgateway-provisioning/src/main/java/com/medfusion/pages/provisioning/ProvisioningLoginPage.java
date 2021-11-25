package com.medfusion.pages.provisioning;

import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by lubson on 23.11.15.
 */
public class ProvisioningLoginPage extends BasePage {

    @FindBy(how = How.ID, using="loginName")
    public WebElement loginInput;

    @FindBy(how = How.ID, using="loginPassword")
    public WebElement passwordInput;

    @FindBy(how = How.ID, using="loginButton")
    public WebElement loginButton;

    public ProvisioningLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public ProvisioningLoginPage(WebDriver webDriver, String url) {
        super(webDriver);
        webDriver.get(url);
    }

    public ProvisioningMerchantSearchPage login(User user) {

        setText(loginInput, user.getUsername());
        setText(passwordInput, user.getPassword());
        click(loginButton);
        ProvisioningMerchantSearchPage merchantSearchPage = PageFactory.initElements(webDriver, ProvisioningMerchantSearchPage.class);
        merchantSearchPage.loggedAsText.isDisplayed(); //This one is here to make sure the page is properly loaded
        return merchantSearchPage;
    }
}
