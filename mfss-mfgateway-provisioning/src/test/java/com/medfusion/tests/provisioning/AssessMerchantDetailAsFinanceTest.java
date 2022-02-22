package com.medfusion.tests.provisioning;

import com.medfusion.factory.ProvisioningFactory;
import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.factory.pojos.provisioning.Role;
import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.tests.BaseWebdriverTest;
import com.medfusion.teststeps.LoginSteps;
import com.medfusion.teststeps.MerchantSteps;
import com.medfusion.util.Data;
import com.medfusion.util.ProvisioningAssert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lhrub on 02.12.2015.
 */
public class AssessMerchantDetailAsFinanceTest extends BaseWebdriverTest {

    private LoginSteps loginStep;
    private MerchantSteps merchantStep;

    @Before
    public void setUp(){
        loginStep = new LoginSteps(webDriver, logger);
        merchantStep = new MerchantSteps(webDriver, logger);
    }

    @Test
    public void assessMerchantDetailAsFinance() throws Exception {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()), Role.FINANCE);
        Merchant expectedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("staticMerchant"), Role.FINANCE);

        loginStep.openProvisioningAs(financeUser);
        Merchant actualMerchant = merchantStep.getMerchant(expectedMerchant.mmid);

        ProvisioningAssert.assertMerchant(expectedMerchant, actualMerchant);
    }
}
