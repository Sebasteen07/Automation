//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.pay.api.test;

import java.io.IOException;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.PropertyFileLoader;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class paymentsApiTests extends BaseTestNG {

	protected static PropertyFileLoader testData;

	@BeforeTest
	public void setUp() throws IOException {
		setupRequestSpecBuilder(propertyData.getProperty("base.url.bin.lookup"));
	}

	@Test(enabled = true)
	public void testGetCardInfoForValidBin() throws Exception {
		logStep("Execute post credentials with valid username and password");
		Response response = getCardInfo(testData.getProperty("valid.bin.no"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyCardInfoOnBin(response);
	}
	
	@Test(enabled = true)
	public void testGetCardInfoForInvalidBin() throws Exception {
		logStep("Execute post credentials with valid username and password");
		Response response = getCardInfo(testData.getProperty("invalid.bin.no"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		validate.verifyBinNotFound(response);
	}
	
	@Test(enabled = true)
	public void testUpdateBinData() throws Exception {
		logStep("Execute post credentials with valid username and password");
		Response response = updateBinData();

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
}