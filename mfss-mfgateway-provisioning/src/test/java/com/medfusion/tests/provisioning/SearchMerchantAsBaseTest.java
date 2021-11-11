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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchMerchantAsBaseTest extends BaseWebdriverTest {

    LoginSteps loginStep;
    MerchantSteps merchantStep;

    @Before
    public void setUp() {
        loginStep = new LoginSteps(webDriver, logger);
        merchantStep = new MerchantSteps(webDriver, logger);
    }

    @Test
    public void searchMerchantasAsAdmin() throws Exception {
        User adminUser = ProvisioningFactory.getUser(Data.getMapFor(Role.ADMIN.toString().toLowerCase()), Role.ADMIN);
        Merchant expectedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("staticMerchant"), Role.ADMIN);


        loginStep.openProvisioningAs(adminUser);
        //TODO Page should be shared in different way
        merchantStep.setMerchantSearchPage(loginStep.getMerchantSearchPage());
        merchantStep.searchMerchant(expectedMerchant.mmid);
        Assert.assertTrue("Search by mmid contains Merchant ",merchantStep.resultContainsMerchant(expectedMerchant.mmid));
        merchantStep.searchMerchant(expectedMerchant.name.toUpperCase().substring(0,expectedMerchant.name.length()-4));
        Assert.assertTrue("Search by partial name contains Merchant ",merchantStep.resultContainsMerchant(expectedMerchant.mmid));
        merchantStep.searchMerchant(expectedMerchant.externalId);
        Assert.assertTrue("Search by external id contains Merchant ",merchantStep.resultContainsMerchant(expectedMerchant.mmid));
        //TODO Add searches by AKA and "Doing business as"
        merchantStep.openMerchantDetail(expectedMerchant.mmid);
        ProvisioningAssert.assertMerchantGeneralInfoEquals(merchantStep.getDisplayedMerchant(), expectedMerchant);
        merchantStep.backToSearchPage();

        Assert.assertTrue("Results from previous search should be visible",merchantStep.resultContainsMerchant(expectedMerchant.mmid));

       

    }
}