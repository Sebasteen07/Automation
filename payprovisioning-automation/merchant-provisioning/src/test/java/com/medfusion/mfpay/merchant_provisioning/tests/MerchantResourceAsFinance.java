// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;

import java.io.IOException;

import com.medfusion.mfpay.merchant_provisioning.helpers.UsersDetails;
import com.medfusion.mfpay.merchant_provisioning.utils.DBUtils;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.helpers.MerchantInfo;
import com.medfusion.mfpay.merchant_provisioning.helpers.PaypalDetails;

public class MerchantResourceAsFinance extends BaseRest {
	protected PropertyFileLoader testData;

	@BeforeTest
	public void setBaseUri() throws Exception {
		testData = new PropertyFileLoader();
		setupFinanceRequestSpecBuilder();
		setupResponsetSpecBuilder();
	}

	// Creates a new element merchant as Finance user.
	@Test
	public void createNewElementMerchantAsFinance() throws IOException {

		MerchantInfo merchantinfo = new MerchantInfo();
		merchantinfo.createUpdateElementMerchant();

	}

	// Update general merchant details for the merchant created as finance
	@Test
	public void updateGeneralMerchantInfo() throws IOException {
		MerchantInfo merchantinfo = new MerchantInfo();
		merchantinfo.updateGeneralMerchantDetails(testData.getProperty("mmid"));

	}

	// Get details of the merchant created as finance
	@Test
	public void getMerchantById() throws IOException {
		MerchantInfo merchantinfo = new MerchantInfo();
		merchantinfo.getMerchantDetails(testData.getProperty("mmid"));

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

}
