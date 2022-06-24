// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

public class WPTestsAsImplementationTests extends BaseRest{
	protected PropertyFileLoader testData;
	
	
	@BeforeTest
	  public void setBaseUri() throws Exception{
		 testData = new PropertyFileLoader();
		 setupImplementationRequestSpecBuilder();
		 setupResponsetSpecBuilder();
	  }
	
	
	@Test
	public void getWorldPayStatusAsImplementation() throws Exception{
		given().spec(requestSpec).when().
		get(ProvisioningUtils.getWPStatus).then().spec(responseSpec).
		and().body(("status").toString(),equalTo("UP"));
	}
 
}
