// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;

import java.io.IOException;

import com.medfusion.mfpay.merchant_provisioning.helpers.Validations;
import com.medfusion.mfpay.merchant_provisioning.pojos.Merchant;
import com.medfusion.mfpay.merchant_provisioning.utils.DBUtils;
import com.medfusion.mfpay.merchant_provisioning.utils.MPTestData;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.helpers.MerchantInfo;
import com.medfusion.mfpay.merchant_provisioning.helpers.PaypalDetails;

public class MerchantResourceAsFinanceTest extends BaseRest {
	protected PropertyFileLoader testData;

	@BeforeTest
	public void setUp() throws IOException {
		testData = new PropertyFileLoader();
		setupFinanceRequestSpecBuilder();
		setupResponsetSpecBuilder();
	}
	// Creates a new element merchant as Finance user.
	@Test
	public void testCreateNewElementMerchantAsFinance() throws IOException {

		MerchantInfo merchantinfo = new MerchantInfo();
		Response response = merchantinfo.createUpdateElementMerchant();

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyMerchantDetails(response.asString());
		ProvisioningUtils.saveMMID(jsonpath.get("id").toString());
	}

	// Update general merchant details for the merchant created as finance
	@Test
	public void testUdateGeneralMerchantInfo() throws IOException {
		MerchantInfo merchantinfo = new MerchantInfo();
		Response response = merchantinfo.updateGeneralMerchantDetails(testData.getProperty("mmid"));

		ObjectMapper objectMapper = new ObjectMapper();
		Merchant readJSON = objectMapper.readValue(response.asString(), Merchant.class);
		Validations validate = new Validations();
		validate.verifyMerchantDetailsOnUpdate(readJSON.getExternalMerchantId().toString(),
				readJSON.getMaxTransactionLimit().toString(), readJSON.getAccountDetails());
	}

	// Get details of the merchant created as finance
	@Test
	public void getMerchantById() throws IOException {
		MerchantInfo merchantinfo = new MerchantInfo();
		merchantinfo.getMerchantDetails(testData.getProperty("mmid"));

	}

	// Creates a new paypal merchant as Finance user.
	@Test
	public void testCreateNewPaypalMerchantAsFinance() throws IOException {

		PaypalDetails merchantdetails = new PaypalDetails();
		Response response = merchantdetails.createUpdatePaypalMerchant();
		JsonPath jsonpath = new JsonPath(response.asString());

		Validations validate = new Validations();
		validate.verifyMerchantDetailsForPaypal(jsonpath.get("externalMerchantId").toString(),
				jsonpath.get("maxTransactionLimit").toString(),
				jsonpath.get("accountDetails.preferredProcessor").toString(),
				jsonpath.get("customerAccountNumber").toString());

	}

	// Creates a new paypal merchant as Finance user.
	@Test
	public void createNewPaypalMerchantAsFinance() throws IOException {

		PaypalDetails merchantdetails = new PaypalDetails();
		merchantdetails.createUpdatePaypalMerchant();

	}

	@Test
	public void testGetSettlementType() throws Throwable {

		MerchantInfo merchantinfo = new MerchantInfo();
		Response response = merchantinfo.getMerchantFeeType(testData.getProperty("mmid"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 200);

		String feeSettlementTypeInDB = DBUtils.executeQueryOnDB("rcm",
				"SELECT fee_settlement_type FROM public.merchant where mmid = " + testData.getProperty("mmid"));

		Assert.assertEquals(jsonPath.get("feeSettlementType"), feeSettlementTypeInDB);

		String qualifiedUpperBoundaryPercent = DBUtils.executeQueryOnDB("rcm",
				"SELECT qualified_ubound_pct FROM public.merchant where mmid = " + testData.getProperty("mmid"));
		
		String midQualifiedUpperBoundaryPercent = DBUtils.executeQueryOnDB("rcm",
				"SELECT mid_qualified_ubound_pct FROM public.merchant where mmid = " + testData.getProperty("mmid"));

		String nonQualifiedUpperBoundaryPercent = DBUtils.executeQueryOnDB("rcm",
				"SELECT non_qualified_ubound_pct FROM public.merchant where mmid = " + testData.getProperty("mmid"));

		Assert.assertNotNull(jsonPath.get("qualifiedUpperBoundaryPercent"));
		Assert.assertEquals(jsonPath.get("qualifiedUpperBoundaryPercent").toString(),
				qualifiedUpperBoundaryPercent.toString());

		Assert.assertNotNull(jsonPath.get("midQualifiedUpperBoundaryPercent"));
		Assert.assertEquals(jsonPath.get("midQualifiedUpperBoundaryPercent").toString(),
				midQualifiedUpperBoundaryPercent.toString());

		Assert.assertNotNull(jsonPath.get("nonQualifiedUpperBoundaryPercent"));
		Assert.assertEquals(jsonPath.get("nonQualifiedUpperBoundaryPercent").toString(),
				nonQualifiedUpperBoundaryPercent.toString());
	}

	@Test
	public void updateMerchantFeeInfo() throws IOException {

		MerchantInfo merchantinfo = new MerchantInfo();
		Response response = merchantinfo.updateMerchantFeeType(testData.getProperty("mmid"));

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertTrue(response.getStatusCode() == 200);
		Assert.assertEquals(jsonPath.get("feeSettlementType").toString(),
				testData.getProperty("fee.settlement.type").toString());
	}
	
	
	@Test
	public void testCreateNewMerchantDiffAccounts() throws IOException {

		MerchantInfo merchantinfo = new MerchantInfo();
		Response response = merchantinfo.createMerchantDiffAccounts();

		JsonPath jsonpath = new JsonPath(response.asString());
		Validations validate = new Validations();
		validate.verifyMerchantWithDiffAccounts(response.asString());
		ProvisioningUtils.saveMMID(jsonpath.get("id").toString());
	}

	@Test(dataProvider = "mmids_for_bank_details", dataProviderClass = MPTestData.class)
	public void testGetMerchantsBankAccountDetails(String url, String mmid) throws IOException {
		MerchantInfo merchantinfo = new MerchantInfo();
		Response response = merchantinfo.getMerchantBankDetails(url, mmid);

		Validations validate = new Validations();
		validate.verifyMerchantBankAccounts(url, response.asString());
	}
	
	//Edit merchant account details single to multiple & vice versa
	@Test(dataProvider = "edit_account_details", dataProviderClass = MPTestData.class, enabled = true)

	public void testEditMerchantAccount(String seprateFunding, String feeRoutingNumber, String feeAccountType,
			String feeAccountNumber, String accountRoutingNumber, String accountType, String accountNumber)
			throws IOException {

		MerchantInfo merchantinfo = new MerchantInfo();
		Response response = merchantinfo.editAccountDetails(testData.getProperty("edit.multiple.bank.accounts.mmid"),
				testData.getProperty("edit.multiple.bank.accounts.accepted.cards"),
				testData.getProperty("edit.multiple.bank.accounts.account.number"),
				testData.getProperty("edit.multiple.bank.accounts.account.type"),
				testData.getProperty("edit.multiple.bank.accounts.checking.deposit"),
				testData.getProperty("edit.multiple.bank.accounts.fee.account.number"),
				testData.getProperty("edit.multiple.bank.accounts.fee.account.type"),
				testData.getProperty("edit.multiple.bank.accounts.fee.routing.number"),
				testData.getProperty("edit.multiple.bank.accounts.preferred.processor"), seprateFunding,
				testData.getProperty("edit.multiple.bank.accounts.routing.number"),
				testData.getProperty("edit.multiple.bank.accounts.merchant.name"),
				testData.getProperty("edit.multiple.bank.accounts.maxtransactionlimit"));

		JsonPath jsonPath = new JsonPath(response.asString());

		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertNotNull(jsonPath.get("merchantName"), "Response was null");
		Assert.assertEquals(jsonPath.get("accountDetails.feeAccountDetails.routingNumber"), feeRoutingNumber);
		Assert.assertEquals(jsonPath.get("accountDetails.feeAccountDetails.accountType"), feeAccountType);
		Assert.assertEquals(jsonPath.get("accountDetails.feeAccountDetails.accountNumber"), feeAccountNumber);
		Assert.assertEquals(jsonPath.get("accountDetails.separateFundingAccounts").toString(), seprateFunding);

		Assert.assertEquals(jsonPath.get("accountDetails.routingNumber"), accountRoutingNumber);
		Assert.assertEquals(jsonPath.get("accountDetails.accountType"), accountType);
		Assert.assertEquals(jsonPath.get("accountDetails.accountNumber"), accountNumber);

	}

}
