package com.medfusion.teststeps;

import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.pages.provisioning.ProvisioningLoginPage;
import com.medfusion.pages.provisioning.ProvisioningMerchantSearchPage;
import com.medfusion.util.Data;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.util.List;

/**
 * Created by lubson on 03.12.15.
 */
public class LoginSteps extends BaseSteps {

    private ProvisioningLoginPage loginPage;
    private ProvisioningMerchantSearchPage merchantSearchPage;

    public LoginSteps(WebDriver webDriver, Logger logger) {
        super(webDriver, logger);
    }

    public ProvisioningMerchantSearchPage getMerchantSearchPage() {
        return merchantSearchPage;
    }

    public void gotoProvisioningLoginPage() {
        logger.info("Go to Provisioning Login Page on url '" + Data.get("url") + "'");
        loginPage = new ProvisioningLoginPage(webDriver, Data.get("url"));
    }

    public void login(User user) {
        logger.info("Log in with credentials: " + user.getUsername() + "/" + user.getPassword());
        merchantSearchPage = loginPage.login(user);
        Assert.assertFalse(merchantSearchPage.isErrorDisplayed());
    }

    public List<String> getMenuItems() {
        return merchantSearchPage.getVisibleMenuItems();
    }

    public String getWelcomeMessage() {
        return merchantSearchPage.getWelcomeMessage();
    }

    public void logout() {
        logger.info("Log out Provisioning.");
        loginPage = merchantSearchPage.logout();
    }

    public void openProvisioningAs(User user) {
        gotoProvisioningLoginPage();
        login(user);
    }
}
