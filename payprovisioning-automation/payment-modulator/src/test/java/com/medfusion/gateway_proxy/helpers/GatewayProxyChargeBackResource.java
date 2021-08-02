// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.helpers;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.gateway_proxy.tests.GatewayProxyBaseTest;
import com.medfusion.gateway_proxy.utils.GatewayProxyUtils;
//import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;
//import com.medfusion.mfpay.merchant_provisioning.utils.User;
import com.medfusion.payment_modulator.pojos.BillToAddress;
import com.medfusion.payment_modulator.pojos.Chargeback;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import com.medfusion.payment_modulator.utils.Validations;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GatewayProxyChargeBackResource extends GatewayProxyBaseTest {
	
	protected PropertyFileLoader testData;
	
	public Response createChargeBack(String token,String proxychargebackurl, String mmid, String transactionid, String orderid,
			String chargeBackAmount) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> chargeBack = PayloadDetails.getPayloadForCreatingChargeBack(transactionid, orderid,chargeBackAmount);

		Response response = given().spec(requestSpec).header("Authorization", token).body(chargeBack).when().post(proxychargebackurl +"/merchant/" + mmid +
				  "/chargebacks").then().extract().response();
					
		return response;
	}	
	
	public Map<String,String>  getCBdetails (JsonPath jsonPathCB) { 
		
		Map<String,String> chargeBackMP = new HashMap<String,String>() ;
		chargeBackMP.put("parentExternalTransactionId",jsonPathCB.get("parentExternalTransactionId").toString());
		chargeBackMP.put("mfTransactionId",jsonPathCB.get("mfTransactionId").toString());
		chargeBackMP.put("chargebackAmount",jsonPathCB.get("chargebackAmount").toString());
		chargeBackMP.put("chargebackIssuer", jsonPathCB.get("chargebackIssuer").toString());
		
	  return chargeBackMP;
	}
		
	public Response getChargeBack(String token,String proxychargebackurl, String mmid) throws IOException {
		
		  Response response = given().spec(requestSpec).header("Authorization",
		  token).when().get(proxychargebackurl +"/merchant/" + mmid +
		  "/chargebacks").then().extract().response();
		  return response;		
	}
}
