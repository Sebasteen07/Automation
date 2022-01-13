package com.medfusion.mfpay.merchant_provisioning.utils;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.tests.BaseRest;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class MPTestData extends BaseRest {

	@DataProvider(name = "practice_role_test")
	public Object[][] dpMethodForGetTxn() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] {
				{ testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
						testData.getProperty("staff.username"), testData.getProperty("practice.role") },
				{ testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
						testData.getProperty("staff.username"), "ABCDE" },
				{ testData.getProperty("practice.staffid"), testData.getProperty("practice.id"), "Test Random Username",
						testData.getProperty("practice.role") },
				{ testData.getProperty("practice.staffid"), "12345", testData.getProperty("staff.username"),
						testData.getProperty("practice.role") },
				{ "82309", testData.getProperty("practice.id"), testData.getProperty("staff.username"),
						testData.getProperty("practice.role") },
				{ testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
						testData.getProperty("staff.username"), "" },
				{ testData.getProperty("practice.staffid"), testData.getProperty("practice.id"),
						testData.getProperty("staff.username"), "POINTOFSALE" }, };
	}

	@DataProvider(name = "mmids_for_bank_details")
	public Object[][] dpMethodForGetBankAccounts() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] {
				{ ProvisioningUtils.postMerchant + "/" + testData.getProperty("multiple.bank.accounts.mmid"),
						testData.getProperty("multiple.bank.accounts.mmid") },
				{ ProvisioningUtils.postMerchant + "/" + testData.getProperty("mmid"), testData.getProperty("mmid") },
				{ (ProvisioningUtils.v9_endpoint + "merchants/" + testData.getProperty("multiple.bank.accounts.mmid")),
						testData.getProperty("multiple.bank.accounts.mmid") },
				{ (ProvisioningUtils.v9_endpoint + "merchants/" + testData.getProperty("mmid")),
						testData.getProperty("mmid") } };
	}

	@DataProvider(name = "edit_account_details")
	public Object[][] dpMethodEditAccountDetails() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] {

				{ "true", testData.getProperty("edit.multiple.bank.accounts.fee.routing.number"),
						testData.getProperty("edit.multiple.bank.accounts.fee.account.type"),
						testData.getProperty("edit.multiple.bank.accounts.fee.account.number"),
						testData.getProperty("edit.multiple.bank.accounts.routing.number"),
						testData.getProperty("edit.multiple.bank.accounts.account.type"),
						testData.getProperty("edit.multiple.bank.accounts.account.number") },

				{ "false", null, null, null, testData.getProperty("edit.multiple.bank.accounts.routing.number"),
						testData.getProperty("edit.multiple.bank.accounts.account.type"),
						testData.getProperty("edit.multiple.bank.accounts.account.number") },

		};
	}

	@DataProvider(name = "instamed_create_with_invalid")
	public Object[][] dpMethodForCreateInstamed() throws IOException {
		testData = new PropertyFileLoader();
		return new Object[][] {

				{ "", testData.getProperty("external.merchantid"), testData.getProperty("customer.account.number"),
						Double.parseDouble(testData.getProperty("mid.qfee.percent")),
						Double.parseDouble(testData.getProperty("nqfee.percent")),
						Double.parseDouble(testData.getProperty("per.transaction.authfee")),
						Double.parseDouble(testData.getProperty("qfee.percent")),
						testData.getProperty("instamed.preferred.processor"),
						testData.getProperty("instamed.merchant.id"), testData.getProperty("instamed.store.id"),
						testData.getProperty("instamed.virtual.visit"), testData.getProperty("instamed.patient.portal"),
						testData.getProperty("instamed.precheck"), "400", "Merchant Name cannot be blank" },

				{ testData.getProperty("merchant.name"), testData.getProperty("external.merchantid"),
						testData.getProperty("customer.account.number"),
						Double.parseDouble(testData.getProperty("mid.qfee.percent")),
						Double.parseDouble(testData.getProperty("nqfee.percent")),
						Double.parseDouble(testData.getProperty("per.transaction.authfee")),
						Double.parseDouble(testData.getProperty("qfee.percent")), "",
						testData.getProperty("instamed.merchant.id"), testData.getProperty("instamed.store.id"),
						testData.getProperty("instamed.virtual.visit"), testData.getProperty("instamed.patient.portal"),
						testData.getProperty("instamed.precheck"), "400", "Preferred Processor cannot be blank" },

				{ testData.getProperty("merchant.name"), testData.getProperty("external.merchantid"),
						testData.getProperty("customer.account.number"),
						Double.parseDouble(testData.getProperty("mid.qfee.percent")),
						Double.parseDouble(testData.getProperty("nqfee.percent")),
						Double.parseDouble(testData.getProperty("per.transaction.authfee")),
						Double.parseDouble(testData.getProperty("qfee.percent")),
						testData.getProperty("instamed.preferred.processor"), "",
						testData.getProperty("instamed.store.id"), testData.getProperty("instamed.virtual.visit"),
						testData.getProperty("instamed.patient.portal"), testData.getProperty("instamed.precheck"),
						"400", "Merchant ID cannot be blank" },

				{ testData.getProperty("merchant.name"), testData.getProperty("external.merchantid"),
						testData.getProperty("customer.account.number"),
						Double.parseDouble(testData.getProperty("mid.qfee.percent")),
						Double.parseDouble(testData.getProperty("nqfee.percent")),
						Double.parseDouble(testData.getProperty("per.transaction.authfee")),
						Double.parseDouble(testData.getProperty("qfee.percent")),
						testData.getProperty("instamed.preferred.processor"),
						testData.getProperty("instamed.merchant.id"), "",
						testData.getProperty("instamed.virtual.visit"), testData.getProperty("instamed.patient.portal"),
						testData.getProperty("instamed.precheck"), "400", "Store ID cannot be blank" },

				{ testData.getProperty("merchant.name"), testData.getProperty("external.merchantid"),
						testData.getProperty("customer.account.number"),
						Double.parseDouble(testData.getProperty("mid.qfee.percent")),
						Double.parseDouble(testData.getProperty("nqfee.percent")),
						Double.parseDouble(testData.getProperty("per.transaction.authfee")),
						Double.parseDouble(testData.getProperty("qfee.percent")),
						testData.getProperty("instamed.preferred.processor"),
						testData.getProperty("instamed.merchant.id"), testData.getProperty("instamed.store.id"), null,
						null, null, "400", "Atleast one terminalId is required" },

				{ testData.getProperty("merchant.name"), testData.getProperty("external.merchantid"),
						testData.getProperty("customer.account.number"),
						Double.parseDouble(testData.getProperty("mid.qfee.percent")),
						Double.parseDouble(testData.getProperty("nqfee.percent")),
						Double.parseDouble(testData.getProperty("per.transaction.authfee")), null,
						testData.getProperty("instamed.preferred.processor"),
						testData.getProperty("instamed.merchant.id"), testData.getProperty("instamed.store.id"),
						testData.getProperty("instamed.virtual.visit"), testData.getProperty("instamed.patient.portal"),
						testData.getProperty("instamed.precheck"), "400", "Qualified Fee Percent cannot be null" },

				{ testData.getProperty("merchant.name"), testData.getProperty("external.merchantid"),
						testData.getProperty("customer.account.number"),
						Double.parseDouble(testData.getProperty("mid.qfee.percent")),
						Double.parseDouble(testData.getProperty("nqfee.percent")), null,
						Double.parseDouble(testData.getProperty("qfee.percent")),
						testData.getProperty("instamed.preferred.processor"),
						testData.getProperty("instamed.merchant.id"), testData.getProperty("instamed.store.id"),
						testData.getProperty("instamed.virtual.visit"), testData.getProperty("instamed.patient.portal"),
						testData.getProperty("instamed.precheck"), "400", "Per Transaction Auth Fee cannot be null" },

				{ testData.getProperty("merchant.name"), testData.getProperty("external.merchantid"),
						testData.getProperty("customer.account.number"), null,
						Double.parseDouble(testData.getProperty("nqfee.percent")),
						Double.parseDouble(testData.getProperty("per.transaction.authfee")),
						Double.parseDouble(testData.getProperty("qfee.percent")),
						testData.getProperty("instamed.preferred.processor"),
						testData.getProperty("instamed.merchant.id"), testData.getProperty("instamed.store.id"),
						testData.getProperty("instamed.virtual.visit"), testData.getProperty("instamed.patient.portal"),
						testData.getProperty("instamed.precheck"), "400", "Mid Qualified Fee Percent cannot be null" },

				{ testData.getProperty("merchant.name"), testData.getProperty("external.merchantid"),
						testData.getProperty("customer.account.number"),
						Double.parseDouble(testData.getProperty("mid.qfee.percent")), null,
						Double.parseDouble(testData.getProperty("per.transaction.authfee")),
						Double.parseDouble(testData.getProperty("qfee.percent")),
						testData.getProperty("instamed.preferred.processor"),
						testData.getProperty("instamed.merchant.id"), testData.getProperty("instamed.store.id"),
						testData.getProperty("instamed.virtual.visit"), testData.getProperty("instamed.patient.portal"),
						testData.getProperty("instamed.precheck"), "400", "Non Qualified Fee Percent cannot be null" },

		};
	}
}
