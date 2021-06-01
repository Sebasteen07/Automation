// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.utils;

import io.restassured.path.json.JsonPath;
import java.io.IOException;
import org.testng.Assert;
import com.medfusion.common.utils.PropertyFileLoader;

public class Validations {

	protected static PropertyFileLoader testData;


	public void verifyTransactionDetails(String response) throws IOException {
		   JsonPath jsonpath = new JsonPath(response);
		   Assert.assertNotNull(jsonpath, "Response was null");
		   Assert.assertEquals(jsonpath.get("responseCode"), "000","Response code was: "+jsonpath.get("responseCode"));
		   Assert.assertTrue(jsonpath.get("message").equals("Success") || jsonpath.get("message").equals("Approved"),"Response message was: "+jsonpath.get("message"));
		   Assert.assertTrue(!jsonpath.get("responseTime").toString().isEmpty(), "Response time was not found in the response");
		   Assert.assertTrue(!jsonpath.get("orderId").toString().isEmpty(), "Order id was not found in the response");
			
	   }


	public void verifyChargebackTransactionDetails(String response) {
		   JsonPath jsonpath = new JsonPath(response);
		   Assert.assertNotNull(jsonpath, "Response was null");
		   Assert.assertTrue(!jsonpath.get("mfTransactionId").toString().isEmpty());
		   Assert.assertTrue(!jsonpath.get("orderId").toString().isEmpty());
		
	}


	public void verifyIfNetworkIdPresent(String response) {
		 JsonPath jsonpath = new JsonPath(response);
		 Assert.assertTrue(!jsonpath.get("expressNetworkTransactionId").toString().isEmpty());
		
	}
}
