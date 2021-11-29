package com.medfusion.tests.provisioning;

import com.medfusion.factory.ProvisioningFactory;
import com.medfusion.factory.pojos.provisioning.Role;
import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.tests.BaseWebdriverTest;
import com.medfusion.teststeps.LoginSteps;
import com.medfusion.util.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class LoginLogoutAsFinanceTest extends BaseWebdriverTest {

    private LoginSteps loginStep;

    @Before
    public void setUp() {
        loginStep = new LoginSteps(webDriver, logger);
    }

    @Test
    public void loginLogoutAsFinance() {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()),
                Role.FINANCE);

        loginStep.gotoProvisioningLoginPage();
        loginStep.login(financeUser);

        String welcomeMessage = loginStep.getWelcomeMessage();
        List<String> menuItems = loginStep.getMenuItems();
        loginStep.logout();

        Assert.assertEquals("Proper welcome message is present", "Welcome " + financeUser.getUsername(),
                welcomeMessage);
        Assert.assertEquals("Proper menu links are available", financeUser.getExpectedMenuItems(),
                menuItems.toString());

    }
}