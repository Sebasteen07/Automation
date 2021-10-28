package com.medfusion.mfpay.merchant_provisioning.utils;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.tests.BaseRest;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class MPTestData extends BaseRest {

    @DataProvider(name = "practice_role_test")
    public Object[][] dpMethodForGetTxn() throws IOException {
        testData = new PropertyFileLoader();
        return new Object[][]{
                {testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
                        testData.getProperty("staff.username"), testData.getProperty("practice.role")},
                {testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
                    testData.getProperty("staff.username"), "ABCDE"},
                {testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
                    "Test Random Username", testData.getProperty("practice.role")},
                {testData.getProperty("practice.staffid"), "12345",
                        testData.getProperty("staff.username"), testData.getProperty("practice.role")},
                {"82309", testData.getProperty("practice.id"),
                        testData.getProperty("staff.username"), testData.getProperty("practice.role")},
                {testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
                        testData.getProperty("staff.username"), ""},
                {testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
                        testData.getProperty("staff.username"), "POINTOFSALE"},
        };
    }

    @DataProvider(name = "mmids_for_bank_details")
    public Object[][] dpMethodForGetBankAccounts() throws IOException {
        testData = new PropertyFileLoader();
        return new Object[][]{
                {testData.getProperty("multiple.bank.accounts.mmid")},
                {testData.getProperty("mmid")},
        };
    }
}
