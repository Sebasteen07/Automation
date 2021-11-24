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

public class LoginLogoutAsImplementationTest extends BaseWebdriverTest {

    private LoginSteps loginStep;

    @Before
    public void setUp() {
        loginStep = new LoginSteps(webDriver, logger);
    }

    @Test
    public void loginLogoutAsImplementation() {
        User implementationUser = ProvisioningFactory.getUser(Data.getMapFor(Role.IMPLEMENTATION.toString().toLowerCase()), Role.IMPLEMENTATION);

        loginStep.gotoProvisioningLoginPage();
        loginStep.login(implementationUser);
        String welcomeMessage = loginStep.getWelcomeMessage();
        List<String> menuItems = loginStep.getMenuItems();
        loginStep.logout();

        Assert.assertEquals("Proper welcome message is present", "Welcome " + implementationUser.getUsername(), welcomeMessage);
        Assert.assertEquals("Proper menu links are available", implementationUser.getExpectedMenuItems(), menuItems.toString());

    }
}