// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyTransactionResource;

public class GatewayProxyTransactionTests extends GatewayProxyBaseTest {

	protected static PropertyFileLoader testData;

	@BeforeTest
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();

	}

	@Test
	public void makeGatewayProxySale() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		transaction.makeASale(testData.getProperty("proxy.mmid"));

	}

	@Test
	public void makeGatewayAuthorize() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		transaction.makeAuthorizeTransaction("75164da9-5747-449f-be45-c2e28f3ff77b","2560807797" );

	}

	@Test
	public void makeGatewayCapture() throws Exception {
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		transaction.makeAuthorizeTransaction("75164da9-5747-449f-be45-c2e28f3ff77b","2560807797" );

	}
}
