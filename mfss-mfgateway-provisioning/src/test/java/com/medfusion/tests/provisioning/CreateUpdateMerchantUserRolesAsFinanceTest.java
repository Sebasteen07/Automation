package com.medfusion.tests.provisioning;

import com.medfusion.factory.ProvisioningFactory;
import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.factory.pojos.provisioning.Role;
import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.factory.pojos.provisioning.UserRoles;
import com.medfusion.tests.BaseWebdriverTest;
import com.medfusion.teststeps.LoginSteps;
import com.medfusion.teststeps.MerchantSteps;
import com.medfusion.util.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by lubson on 17.01.16.
 */
public class CreateUpdateMerchantUserRolesAsFinanceTest extends BaseWebdriverTest {

    private LoginSteps loginStep;
    private MerchantSteps merchantStep;

    @Before
    public void setUp(){
        loginStep = new LoginSteps(webDriver, logger);
        merchantStep = new MerchantSteps(webDriver, logger);
    }

    @Test
    public void createUpdateMerchantUserRolesAsFinance() {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()), Role.FINANCE);
        Merchant expectedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("staticMerchant"), Role.FINANCE);
        ProvisioningFactory.randomizeMerchantIdentifiers(expectedMerchant);
        UserRoles expectedNewUserRoles = ProvisioningFactory.getUserRoles(Data.getMapFor("userRoles"));
        UserRoles expectedUpdatedUserRoles = ProvisioningFactory.getUserRoles(Data.getMapFor("userRoles"));
        expectedUpdatedUserRoles.setRoles("VOIDREFUND");

        loginStep.openProvisioningAs(financeUser);
        //TODO Page should be shared in different way
        merchantStep.setMerchantSearchPage(loginStep.getMerchantSearchPage());
        merchantStep.createNewMerchant(expectedMerchant);
        merchantStep.openUserRolesSection();
        
        merchantStep.addNewUserRoles(expectedNewUserRoles);
        List<UserRoles> actualCreatedUserRoles = merchantStep.getAllUserRoles();
        Assert.assertTrue("New User with roles should be created", actualCreatedUserRoles.contains(expectedNewUserRoles));

        logger.info("Add new user role");
        merchantStep.updateUserRoles(expectedUpdatedUserRoles);
        List<UserRoles> actualUpdatedUserRoles = merchantStep.getAllUserRoles();
        Assert.assertTrue("Existing User with roles should be updated", actualUpdatedUserRoles.contains(expectedUpdatedUserRoles));

        merchantStep.removeUserRoles(expectedUpdatedUserRoles);
        List<UserRoles> emptyUserRoles = merchantStep.getAllUserRoles();
        Assert.assertTrue("Existing User should be removed", emptyUserRoles.isEmpty());
    }
}
