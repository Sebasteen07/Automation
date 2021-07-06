// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.helpers;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.pojos.PaypalMerchantInfo;
import com.medfusion.mfpay.merchant_provisioning.tests.BaseRest;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

public class PaypalDetails extends BaseRest{
protected PropertyFileLoader testData;
	

	public void createUpdatePaypalMerchant() throws IOException {
			testData = new PropertyFileLoader();
			Map<String, Object> paypalmerchantdetails = PaypalMerchantInfo.getMerchantMap((testData.getProperty("paypal.merchant.name")),
					testData.getProperty("external.merchantid"),testData.getProperty("customer.account.number"),
					testData.getProperty("transaction.limit"),testData.getProperty("paypal.cnp.username"),testData.getProperty("paypal.cnp.password"));
			
			ObjectMapper objectMapper = new ObjectMapper();
		  	String convertTOJson = objectMapper.writeValueAsString(paypalmerchantdetails);
		  	objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		  	
			Response response = given().spec(requestSpec).
			body(convertTOJson).when().post(ProvisioningUtils.postMerchant)
			.then().spec(responseSpec).and().extract().response();
			
			PaypalMerchantInfo readJSON = objectMapper.readValue(response.asString(), PaypalMerchantInfo.class);
			Validations validate = new Validations();
			validate.verifyMerchantDetailsForPaypal(readJSON.getExternalMerchantId().toString(),readJSON.getMaxTransactionLimit().toString(),
			readJSON.getAccountDetails(),readJSON.getCustomerAccountNumber());
		
	}
	
	

}
