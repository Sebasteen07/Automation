package com.medfusion.tests.provisioning;

import com.medfusion.factory.ProvisioningFactory;
import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.factory.pojos.provisioning.Role;
import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.pages.provisioning.partials.Section;
import com.medfusion.tests.BaseWebdriverTest;
import com.medfusion.teststeps.LoginSteps;
import com.medfusion.teststeps.MerchantSteps;
import com.medfusion.util.Data;
import com.medfusion.util.ProvisioningAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lhrub on 07.12.2015.
 */
public class CreateUpdateMerchantAsFinanceTest extends BaseWebdriverTest {

    private LoginSteps loginStep;
    private MerchantSteps merchantStep;

    @Before
    public void setUp(){
        loginStep = new LoginSteps(webDriver, logger);
        merchantStep = new MerchantSteps(webDriver, logger);
    }

    @Test
    public void createMerchantAsFinance() {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()), Role.FINANCE);
        Merchant expectedMerchant = ProvisioningFactory.getMerchant(Data.getMapFor("staticMerchant"), Role.FINANCE);
        ProvisioningFactory.randomizeMerchantIdentifiers(expectedMerchant);

        loginStep.openProvisioningAs(financeUser);
        //TODO Page should be shared in different way
        merchantStep.setMerchantSearchPage(loginStep.getMerchantSearchPage());
        merchantStep.openAddNewMerchantPage();
        merchantStep.saveNewMerchant(expectedMerchant);

        Section[] sectionsToUpdate = {
                Section.ACCOUNTS_IDS,
                Section.STATEMENT_OPTIONS,
                Section.RATES,
                Section.FRAUDS_RISKS};
        merchantStep.updateMerchant(expectedMerchant, sectionsToUpdate);
        Merchant actualMerchant = merchantStep.getDisplayedMerchant();

        Assert.assertNotNull(actualMerchant.mmid);
        Assert.assertEquals(actualMerchant.mmid.length(), 10);
        expectedMerchant.mmid = actualMerchant.mmid;
        ProvisioningAssert.assertMerchant(expectedMerchant, actualMerchant);
    }


}
