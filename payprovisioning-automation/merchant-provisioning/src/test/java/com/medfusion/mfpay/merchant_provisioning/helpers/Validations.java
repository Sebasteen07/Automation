// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.helpers;

import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.util.List;
import org.testng.Assert;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.pojos.AccountDetails;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;

public class Validations {

	protected static PropertyFileLoader testData;

	public void verifyMerchantDetails(String merchantdetails) throws IOException {

		testData = new PropertyFileLoader();
		JsonPath jsonpath = new JsonPath(merchantdetails);
		Assert.assertNotNull(jsonpath, "Response was null");
		Assert.assertNotNull(jsonpath.get("id"), "Merchant id was not in the response");
		Assert.assertEquals(jsonpath.get("doingBusinessAs"), (testData.getProperty("doing.business.as")));
		Assert.assertEquals(jsonpath.get("customerAccountNumber"), (testData.getProperty("customer.account.number")));
		Assert.assertEquals(jsonpath.get("primaryContactFirstName"), (testData.getProperty("primary.firstname")));
		Assert.assertEquals(jsonpath.get("primaryContactLastName"), (testData.getProperty("primary.lastname")));
		Assert.assertEquals(jsonpath.get("primaryContactEmail"), (testData.getProperty("primary.email")));
		Assert.assertEquals(jsonpath.get("primaryContactPhoneNumber"), (testData.getProperty("primary.phonenumber")));
		Assert.assertEquals(jsonpath.get("phoneNumber"), (testData.getProperty("merchant.phonenumber")));
		Assert.assertEquals(jsonpath.get("merchantAddress.address1"), (testData.getProperty("merchant.address1")));
		Assert.assertEquals(jsonpath.get("merchantAddress.city"), (testData.getProperty("merchant.city")));
		Assert.assertEquals(jsonpath.get("merchantAddress.state"), (testData.getProperty("merchant.state")));
		Assert.assertEquals(jsonpath.get("merchantAddress.zip"), (testData.getProperty("merchant.zip")));
		Assert.assertEquals(jsonpath.get("merchantAddress.country"), "US");
		Assert.assertEquals(jsonpath.get("customerAccountNumber"), (testData.getProperty("customer.account.number")));
		Assert.assertEquals(jsonpath.get("externalMerchantId"),
				Integer.parseInt((testData.getProperty("external.merchantid"))));
		Assert.assertEquals(jsonpath.get("maxTransactionLimit"),
				Integer.parseInt((testData.getProperty("transaction.limit"))));
		Assert.assertEquals(jsonpath.get("accountDetails.preferredProcessor"),
				(PracticeConstants.PREFERRED_PROCESSOR_ELEMENT));
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.backingWPMerchantExists"), true);
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.elementAccountId"),
				(testData.getProperty("element.accountid")));
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.mccCode"),
				(testData.getProperty("mcccode")));
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.ownershipType"),
				(testData.getProperty("ownership.type")));
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.businessType"),
				(testData.getProperty("business.type")));
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.businessEstablishedDate"),
				(testData.getProperty("business.established.date")));
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.websiteURL"),
				(testData.getProperty("website.url")));
		Assert.assertEquals(jsonpath.get("contractedRates.feeSettlementType"),
				(testData.getProperty("fee.settlement.type.daily")));
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.billingDescriptor"),
				"PFA*" + (testData.getProperty("doing.business.as")));
		Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.elementAcceptorId"),
				"Acceptor id was null");
		Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.elementTerminalId"),
				"Element Terminal id was null");
		Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.elementAccountToken"),
				(testData.getProperty("element.account.token")));
		Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.vantivIbmMid"), "IBM Mid was null");
		Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.subMerchantId"),
				"Submerchant id was null");

	}

	public static void validateStaffUser(String staffusername, String practicestaffid, String response)
			throws IOException {
		testData = new PropertyFileLoader();
		JsonPath jsonpath = new JsonPath(response);
		Assert.assertNotNull(jsonpath, "Response was null.Adding user was not successful");
		Assert.assertEquals(jsonpath.get("practicestaffId").toString(), (testData.getProperty("practice.staffid")));
		Assert.assertEquals(jsonpath.get("userName"), (testData.getProperty("staff.username")));

	}

	public void verifyMerchantDetailsOnUpdate(String externalMerchantId, String maxTransactionLimit,
			AccountDetails accountDetails) throws IOException {

		testData = new PropertyFileLoader();
		Assert.assertEquals(externalMerchantId, (testData.getProperty("external.merchantid.update")));
		Assert.assertEquals(maxTransactionLimit, testData.getProperty("transaction.limit.update"));
		Assert.assertEquals(accountDetails.getPreferredProcessor(), PracticeConstants.PREFERRED_PROCESSOR_ELEMENT);
		Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getBillingDescriptor(),
				"PFA*" + testData.getProperty("doing.business.as"));
		Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getOwnershipType(),
				testData.getProperty("ownership.type"));
		Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getBusinessType(),
				testData.getProperty("business.type"));
		Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getMccCode(),
				testData.getProperty("mcccode.update"));
		Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getBusinessEstablishedDate(),
				testData.getProperty("business.established.date"));
		Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getWebsiteURL(),
				testData.getProperty("website.urlupdate"));
		Assert.assertEquals(accountDetails.getCheckingDepositType(), PracticeConstants.CHECKING_TYPE);

	}

	public void verifyMerchantDetailsForPaypal(String externalmerchantid, String transactionlimit,
			String accountDetails, String customeraccountnumber) throws IOException {

		testData = new PropertyFileLoader();
		Assert.assertEquals(externalmerchantid, testData.getProperty("external.merchantid"));
		Assert.assertEquals(transactionlimit, testData.getProperty("transaction.limit"));
		Assert.assertEquals(accountDetails, PracticeConstants.PREFERRED_PROCESSOR_PAYPAL);
		Assert.assertEquals(customeraccountnumber, testData.getProperty("customer.account.number"));
	}

	public static void validatePracticeRoles(JsonPath jsonpath, String practiceStaffid, String practiceId,
			List<String> practiceRoles) throws IOException {
		testData = new PropertyFileLoader();
		Assert.assertNotNull(jsonpath, "Response was null.Adding user was not successful");
		Assert.assertEquals(jsonpath.get("practiceStaffId"), Integer.parseInt(practiceStaffid));
		Assert.assertEquals(jsonpath.get("userName"), testData.getProperty("staff.username"));
		Assert.assertEquals(jsonpath.get("practiceId").toString(), practiceId);
		Assert.assertEquals(jsonpath.get("practiceLevelRoles"), practiceRoles);
	}

	public void verifyMerchantWithDiffAccounts(String merchantdetails) throws IOException {

		testData = new PropertyFileLoader();
		JsonPath jsonpath = new JsonPath(merchantdetails);
		Assert.assertNotNull(jsonpath, "Response was null");
		Assert.assertNotNull(jsonpath.get("id"), "Merchant id was not in the response");
		Assert.assertEquals(jsonpath.get("doingBusinessAs"), (testData.getProperty("doing.business.as")));
		Assert.assertEquals(jsonpath.get("customerAccountNumber"), (testData.getProperty("customer.account.number")));
		Assert.assertEquals(jsonpath.get("primaryContactFirstName"), (testData.getProperty("primary.firstname")));
		Assert.assertEquals(jsonpath.get("primaryContactLastName"), (testData.getProperty("primary.lastname")));
		Assert.assertEquals(jsonpath.get("primaryContactEmail"), (testData.getProperty("primary.email")));
		Assert.assertEquals(jsonpath.get("primaryContactPhoneNumber"), (testData.getProperty("primary.phonenumber")));
		Assert.assertEquals(jsonpath.get("phoneNumber"), (testData.getProperty("merchant.phonenumber")));
		Assert.assertEquals(jsonpath.get("merchantAddress.address1"), (testData.getProperty("merchant.address1")));
		Assert.assertEquals(jsonpath.get("merchantAddress.city"), (testData.getProperty("merchant.city")));
		Assert.assertEquals(jsonpath.get("merchantAddress.state"), (testData.getProperty("merchant.state")));
		Assert.assertEquals(jsonpath.get("merchantAddress.zip"), (testData.getProperty("merchant.zip")));
		Assert.assertEquals(jsonpath.get("merchantAddress.country"), "US");
		Assert.assertEquals(jsonpath.get("customerAccountNumber"), (testData.getProperty("customer.account.number")));
		Assert.assertEquals(jsonpath.get("externalMerchantId"),
				Integer.parseInt((testData.getProperty("external.merchantid"))));
		Assert.assertEquals(jsonpath.get("maxTransactionLimit"),
				Integer.parseInt((testData.getProperty("transaction.limit"))));
		Assert.assertEquals(jsonpath.get("accountDetails.preferredProcessor"),
				(PracticeConstants.PREFERRED_PROCESSOR_ELEMENT));
		Assert.assertEquals(jsonpath.get("separateFundingAccounts"), testData.getProperty("separate.funding.account"));
	}

	public void verifyMerchantBankAccounts(String url, String merchantdetails) throws IOException {

		testData = new PropertyFileLoader();
		JsonPath jsonpath = new JsonPath(merchantdetails);
		Assert.assertNotNull(jsonpath, "Response was null");
		Assert.assertNotNull(jsonpath.get("id"), "Merchant id was not in the response");
		Assert.assertNotNull(jsonpath.get("accountDetails.routingNumber"), "Routing number was not in the response");
		Assert.assertNotNull(jsonpath.get("accountDetails.accountNumber"), "Account Number was not in the response");
		Assert.assertNotNull(jsonpath.get("accountDetails.accountType"), "Account Type was not in the response");
		Assert.assertTrue(jsonpath.get("accountDetails.accountType").toString().equalsIgnoreCase("C") ||
				jsonpath.get("accountDetails.accountType").toString().equalsIgnoreCase("S") );
		Assert.assertTrue(jsonpath.get("accountDetails.accountType").toString().equalsIgnoreCase("C"));
		if(jsonpath.get("accountDetails.separateFundingAccounts").equals(true)){
			System.out.println("The Merchant has a single bank account for deposits and fees");
			Assert.assertNotNull(jsonpath.get("accountDetails.feeAccountDetails.routingNumber"), "Fee Account Type's Routing Number was not in the response");
			Assert.assertNotNull(jsonpath.get("accountDetails.feeAccountDetails.accountNumber"), "Fee Account Type's Account Number was not in the response");
			Assert.assertNotNull(jsonpath.get("accountDetails.feeAccountDetails.accountType"), "Fee Account Type's Account Type was not in the response");
		}
		else {
			System.out.println("The Merchant has a two different bank account for deposits and fees");
			Assert.assertNull(jsonpath.get("accountDetails.feeAccountDetails.routingNumber"), "Fee Account Type's Routing Number was not NULL in the response");
			Assert.assertNull(jsonpath.get("accountDetails.feeAccountDetails.accountNumber"), "Fee Account Type's Account Number was not NULL in the response");
			Assert.assertNull(jsonpath.get("accountDetails.feeAccountDetails.accountType"), "Fee Account Type's Account Type was not NULL in the response");
		}

	}
}
