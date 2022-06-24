// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.helpers;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.pojos.PaypalMerchantInfo;
import com.medfusion.mfpay.merchant_provisioning.tests.BaseRest;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

public class PaypalDetails extends BaseRest{
protected PropertyFileLoader testData;
	

	public Response createUpdatePaypalMerchant() throws IOException {
			testData = new PropertyFileLoader();
			Map<String, Object> paypalmerchantdetails = PaypalMerchantInfo.getMerchantMap((testData.getProperty("paypal.merchant.name")),
					testData.getProperty("external.merchantid"),testData.getProperty("customer.account.number"),
					testData.getProperty("transaction.limit"),testData.getProperty("paypal.cnp.username"),testData.getProperty("paypal.cnp.password"));

			return given().spec(requestSpec).
			body(paypalmerchantdetails).when().post(ProvisioningUtils.postMerchant)
			.then().spec(responseSpec).and().extract().response();

	}
	
	

}
