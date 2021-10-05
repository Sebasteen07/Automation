// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.helpers;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.pojos.ContractedRates;
import com.medfusion.mfpay.merchant_provisioning.pojos.Merchant;
import com.medfusion.mfpay.merchant_provisioning.tests.BaseRest;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

public class MerchantInfo extends BaseRest {
	protected PropertyFileLoader testData;

	public Response createUpdateElementMerchant() throws IOException {

		testData = new PropertyFileLoader();
		Map<String, Object> merchantdetails = Merchant.getMerchantMap((testData.getProperty("merchant.name")),
				testData.getProperty("doing.business.as"), testData.getProperty("external.merchantid"),
				testData.getProperty("customer.account.number"), testData.getProperty("merchant.phonenumber"),
				testData.getProperty("transaction.limit"), testData.getProperty("primary.firstname"),
				testData.getProperty("primary.lastname"), testData.getProperty("primary.phonenumber"),
				testData.getProperty("primary.email"), testData.getProperty("merchant.address1"),
				testData.getProperty("merchant.city"), testData.getProperty("merchant.state"),
				testData.getProperty("merchant.zip"), testData.getProperty("account.number"),
				testData.getProperty("routing.number"), testData.getProperty("federal.taxid"),
				testData.getProperty("business.established.date"), testData.getProperty("business.type"),
				testData.getProperty("mcccode"), testData.getProperty("ownership.type"),
				testData.getProperty("website.url"), testData.getProperty("amex.percent"),
				testData.getProperty("mid.qfee.percent"), testData.getProperty("mid.qupper.fee.percent"),
				testData.getProperty("nqfee.percent"), testData.getProperty("nqupper.fee.percent"),
				testData.getProperty("per.transaction.authfee"), testData.getProperty("per.transaction.refund.fee"),
				testData.getProperty("qfee.percent"), testData.getProperty("qupper.percent"));

		return given().spec(requestSpec).body(merchantdetails).when().post(ProvisioningUtils.postMerchant).then()
				.spec(responseSpec).and().extract().response();

	}

	public void getMerchantDetails(String mmid) {

		String getmerchant = ProvisioningUtils.postMerchant + "/" + mmid;
		given().spec(requestSpec).when().get(getmerchant).then().spec(responseSpec);

	}

	public Response updateGeneralMerchantDetails(String mmid) throws IOException {

		testData = new PropertyFileLoader();
		Map<String, Object> merchantdetails = Merchant.getMerchantMap((testData.getProperty("merchant.name.update")),
				testData.getProperty("doing.business.as"), testData.getProperty("external.merchantid.update"),
				testData.getProperty("customer.account.number"), testData.getProperty("merchant.phonenumber"),
				testData.getProperty("transaction.limit.update"), testData.getProperty("primary.firstname"),
				testData.getProperty("primary.lastname"), testData.getProperty("primary.phonenumber"),
				testData.getProperty("primary.email"), testData.getProperty("merchant.address1"),
				testData.getProperty("merchant.city"), testData.getProperty("merchant.state"),
				testData.getProperty("merchant.zip"), testData.getProperty("account.number"),
				testData.getProperty("routing.number"), testData.getProperty("federal.taxid"),
				testData.getProperty("business.established.date"), testData.getProperty("business.type"),
				testData.getProperty("mcccode.update"), testData.getProperty("ownership.type"),
				testData.getProperty("website.urlupdate"), testData.getProperty("amex.percent"),
				testData.getProperty("mid.qfee.percent"), testData.getProperty("mid.qupper.fee.percent"),
				testData.getProperty("nqfee.percent"), testData.getProperty("nqupper.fee.percent"),
				testData.getProperty("per.transaction.authfee"), testData.getProperty("per.transaction.refund.fee"),
				testData.getProperty("qfee.percent"), testData.getProperty("qupper.percent"));

		ObjectMapper objectMapper = new ObjectMapper();
		String convertTOJson = objectMapper.writeValueAsString(merchantdetails);

		String putmerchant = ProvisioningUtils.postMerchant + "/" + mmid + "/wpSubMerchant?updateType=GENERAL_INFO";
		return given().spec(requestSpec).body(convertTOJson).when().put(putmerchant).then().spec(responseSpec).and()
				.extract().response();
	}

	public Response getMerchantFeeType(String mmid) {

		String getmerchant = ProvisioningUtils.postMerchant + "/" + mmid + "/rates";
		Response response = given().spec(requestSpec).when().get(getmerchant).then().and().extract().response();
		return response;
	}

	public Response updateMerchantFeeType(String mmid) throws IOException {

		testData = new PropertyFileLoader();

		Map<String, Object> feedetails = ContractedRates.updateMerchantfeesMap(testData.getProperty("amex.percent"),
				testData.getProperty("fee.settlement.type"), testData.getProperty("mid.qfee.percent"),
				testData.getProperty("mid.qupper.fee.percent"), testData.getProperty("nqfee.percent"),
				testData.getProperty("nqupper.fee.percent"), testData.getProperty("per.transaction.authfee"),
				testData.getProperty("per.transaction.refund.fee"), testData.getProperty("qfee.percent"),
				testData.getProperty("qupper.percent"), testData.getProperty("suppress.fee.settlement"));

		ObjectMapper objectMapper = new ObjectMapper();
		String convertTOJson = objectMapper.writeValueAsString(feedetails);

		String updatemerchant = ProvisioningUtils.postMerchant + "/" + mmid + "/rates";
		Response response = given().spec(requestSpec).body(convertTOJson).when().put(updatemerchant).then()
				.spec(responseSpec).extract().response();
		ContractedRates readJSON = objectMapper.readValue(response.asString(), ContractedRates.class);
		return response;
	}

}
