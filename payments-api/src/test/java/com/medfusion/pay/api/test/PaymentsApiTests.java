//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.pay.api.test;

import java.io.IOException;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.pay.api.util.Validations;
import com.medfusion.pay.api.helpers.ApiConstructor;

import io.restassured.response.Response;

public class PaymentsApiTests extends ApiConstructor {

	protected static PropertyFileLoader testData;
	Validations validate = new Validations();

	@BeforeTest
	public void setUp() throws IOException {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder(testData.getProperty("base.url.bin.lookup"));
	}

	@Test(enabled = true)
	public void testGetCardInfoForValidBin() throws Exception {
		logStep("Execute get card info for bin");
		Response response = getCardInfo(testData.getProperty("valid.bin.no"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyCardInfoOnBin(response, testData.getProperty("valid.bin.no"), testData.getProperty("card.type"));
	}
	
	@Test(enabled = true)
	public void testGetCardInfoForInvalidBin() throws Exception {
		logStep("Execute get card info for bin");
		Response response = getCardInfo(testData.getProperty("invalid.bin.no"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		validate.verifyBinNotFound(response, testData.getProperty("invalid.bin.no"));
	}
	
	@Test(enabled = true)
	public void testUpdateBinData() throws Exception {
		logStep("Execute update bin data");
		Response response = updateBinData();

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
}