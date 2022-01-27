// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.tests;

import com.medfusion.common.utils.IHGUtil;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.helpers.GatewayProxyChargeBackResource;
import com.medfusion.gateway_proxy.helpers.GatewayProxyTransactionResource;
import com.medfusion.payment_modulator.utils.Validations;
import com.medfusion.gateway_proxy.utils.GatewayProxyTestData;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
import com.medfusion.gateway_proxy.utils.MPUsersUtility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GatewayProxyChargebackTests extends GatewayProxyBaseTest {

	protected static PropertyFileLoader testData;
	protected static String token;
	protected static String financeUser;

	@BeforeTest
	public void setUp() throws Exception {
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		token = MPUsersUtility.getCredentialsEncodedInBase("FINANCE");
		financeUser = MPUsersUtility.FINANCE_USER;

	}

	@Test(enabled = true)
	public void testCreateChargeBack() throws Exception {

		String token1 = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String transanctionAmount = IHGUtil.createRandomNumericString(5);
		String diffrence = IHGUtil.createRandomNumericString(2);

		Integer chargeBackAmount = Integer.parseInt(transanctionAmount) - Integer.parseInt(diffrence);

		Response responseSale = transaction.makeASale(token1, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), transanctionAmount);

		JsonPath jsonPath = new JsonPath(responseSale.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(responseSale.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		GatewayProxyChargeBackResource chargeBack = new GatewayProxyChargeBackResource();

		Response response = chargeBack.createChargeBack(token, testData.getProperty("proxy.chargeback.url"),
				testData.getProperty("proxy.mmid"), externalTransactionId, orderId, chargeBackAmount.toString());

		JsonPath jsonPathCB = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPathCB, "Response was null");
		Assert.assertTrue(!jsonPathCB.get("mfTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPathCB.get("orderId").toString().isEmpty());

		Assert.assertEquals(externalTransactionId, jsonPathCB.get("parentExternalTransactionId"));
		Assert.assertEquals(orderId, jsonPathCB.get("parentOrderId"));
		Assert.assertEquals(chargeBackAmount, jsonPathCB.get("chargebackAmount"));
		Assert.assertEquals(financeUser, jsonPathCB.get("chargebackIssuer"));
	}

	@Test(dataProvider = "CB_data_inavild_create", dataProviderClass = GatewayProxyTestData.class, enabled = true)

	public void testCreateChargeBackInvalidData(String token, String chargeBackUrl, String mmid,
			String externalTransactionId, String orderId, String chargeBackAmount, String verifyErrorMessage,
			int statusCodeVerify) throws Exception {

		GatewayProxyChargeBackResource chargeBack = new GatewayProxyChargeBackResource();

		Response response = chargeBack.createChargeBack(token, chargeBackUrl, mmid, externalTransactionId, orderId,
				chargeBackAmount);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("message") != null) {

			Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));
		}

	}

	@Test(enabled = true)
	public void testGetChargeBack() throws Exception {

		GatewayProxyChargeBackResource chargeBack = new GatewayProxyChargeBackResource();

		String token1 = GatewayProxyUtils.getTokenForCustomer();
		GatewayProxyTransactionResource transaction = new GatewayProxyTransactionResource();
		String transanctionAmount = IHGUtil.createRandomNumericString(4);
		String diffrence = IHGUtil.createRandomNumericString(3);

		Integer chargeBackAmount = Integer.parseInt(transanctionAmount) - Integer.parseInt(diffrence);

		Response responseSale = transaction.makeASale(token1, testData.getProperty("proxy.mmid"),
				testData.getProperty("test.pay.customer.uuid"), transanctionAmount);

		JsonPath jsonPath = new JsonPath(responseSale.asString());
		Validations validate = new Validations();
		validate.verifyTransactionDetails(responseSale.asString());

		String externalTransactionId = jsonPath.get("externalTransactionId").toString();
		String orderId = jsonPath.get("orderId").toString();

		Response response = chargeBack.createChargeBack(token, testData.getProperty("proxy.chargeback.url"),
				testData.getProperty("proxy.mmid"), externalTransactionId, orderId, chargeBackAmount.toString());

		JsonPath jsonPathCB = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPathCB, "Response was null");
		Assert.assertTrue(!jsonPathCB.get("mfTransactionId").toString().isEmpty());
		Assert.assertTrue(!jsonPathCB.get("orderId").toString().isEmpty());

		Assert.assertEquals(externalTransactionId, jsonPathCB.get("parentExternalTransactionId"));
		Assert.assertEquals(orderId, jsonPathCB.get("parentOrderId"));
		Assert.assertEquals(chargeBackAmount, jsonPathCB.get("chargebackAmount"));
		Assert.assertEquals(financeUser, jsonPathCB.get("chargebackIssuer"));

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		String chargeBackDate = dateFormatGmt.format(new Date());

		String parentTransactionId = jsonPathCB.get("parentExternalTransactionId");
		String mfTransactionId = jsonPathCB.get("mfTransactionId");
		String chargebackIssuer = jsonPathCB.get("chargebackIssuer");
		String chargebackAmount = String.valueOf(jsonPathCB.get("chargebackAmount"));

		Map<String, String> chargeBackMP = chargeBack.getCBdetails(jsonPathCB);

		Response getCBResponce = chargeBack.getChargeBack(token, testData.getProperty("proxy.chargeback.url"),
				testData.getProperty("proxy.mmid"));

		List<HashMap<String, String>> responseMap = getCBResponce.jsonPath().getList("$");

		for (HashMap<String, String> singleCb : responseMap) {

			if (singleCb.get("mfTransactionId").equals(mfTransactionId)) {

				Assert.assertEquals(singleCb.get("parentExternalTransactionId"), parentTransactionId);
				Assert.assertEquals(singleCb.get("mfTransactionId"), mfTransactionId);
				Assert.assertEquals(String.valueOf(singleCb.get("chargebackAmount")), chargebackAmount);
				Assert.assertEquals(singleCb.get("chargebackIssuer"), chargebackIssuer);
				Assert.assertEquals(singleCb.get("chargebackDate"), chargeBackDate);
				Assert.assertEquals(singleCb.get("status"), "Pending");

			}

		}

	}

	@Test(dataProvider = "CB_data_invaild_get", dataProviderClass = GatewayProxyTestData.class, enabled = true)

	public void testGetChargeBackInvalidData(String token, String mmid, String verifyErrorMessage, int statusCodeVerify)
			throws Exception {

		GatewayProxyChargeBackResource chargeBack = new GatewayProxyChargeBackResource();
		Response response = chargeBack.getChargeBack(token, testData.getProperty("proxy.chargeback.url"), mmid);

		JsonPath jsonPath = new JsonPath(response.asString());
		Assert.assertNotNull(jsonPath, "Response was null");
		Assert.assertEquals(response.getStatusCode(), statusCodeVerify);

		if (jsonPath.get("message") != null) {

			Assert.assertTrue(jsonPath.get("message").toString().contains(verifyErrorMessage));
		}

	}

	@Test(enabled = true)

	public void testGetChargeBackValidEmptyList() throws Exception {
		GatewayProxyChargeBackResource chargeBack = new GatewayProxyChargeBackResource();

		Response response = chargeBack.getChargeBack(token, testData.getProperty("proxy.chargeback.url"), "2560809338");
		JsonPath jsonPath = new JsonPath(response.asString());

		List<String> emptyList = response.jsonPath().getList("$");

		Assert.assertTrue(emptyList.isEmpty());
	}

}