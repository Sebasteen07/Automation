package com.medfusion.testsuites;

import com.medfusion.tests.provisioning.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by lubson on 27.11.15.
 */
    @RunWith(Suite.class)
    @Suite.SuiteClasses({
    	LoginLogoutAsBaseTest.class,
        LoginLogoutAsFinanceTest.class,
        LoginLogoutAsImplementationTest.class,
        SearchMerchantAsBaseTest.class,
        AssessMerchantDetailAsFinanceTest.class,
        CallAllRestsAsFinanceTest.class,
        CreateUpdateMerchantAsFinanceTest.class,
        CreateUpdateMerchantUserRolesAsFinanceTest.class,
            
    })
    public class ProvisioningAcceptanceSuite {

}