// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;


import io.restassured.http.ContentType;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;
import com.medfusion.mfpay.merchant_provisioning.utils.User;

public class ProvisioningRoles {
	protected PropertyFileLoader testData;
	


	public String getBaseUrl() throws IOException {
		testData = new PropertyFileLoader();
		String baseurl = testData.getProperty("base.url");
		return baseurl;
	}
	
	
	//Verify finance user returns finance role
	@Test
	public void getFinanceRole() throws Exception {
		given().header("Authorization", User.getCredentialsEncodedInBase("FINANCE")).
		contentType(ContentType.JSON).and().log().all().when().
		get(getBaseUrl()+ProvisioningUtils.getRoles).then().assertThat().
		statusCode(200).and().body(("[0]").toString(),equalTo("merchant_provisioning_finance"));
	}

	
	//Verify Implementation user returns Implementation role
	@Test
	public void getImplementationRole() throws Exception {
		given().header("Authorization", User.getCredentialsEncodedInBase("IMPLEMENTATION")).
		contentType(ContentType.JSON).and().log().all().when().
		get(getBaseUrl()+ProvisioningUtils.getRoles).then().assertThat().
		statusCode(200).and().body(("[0]").toString(),equalTo("merchant_provisioning_implementation"));
	}
		
	
	//Verify admin user returns admin role
	@Test
	public void getAdminRole() throws Exception {
		given().header("Authorization", User.getCredentialsEncodedInBase("ADMIN")).
		contentType(ContentType.JSON).and().log().all().when().
		get(getBaseUrl()+ProvisioningUtils.getRoles).then().assertThat().
		statusCode(200).and().body(("[0]").toString(),equalTo("merchant_provisioning_admin"));
	}

	
	//Verify invalid user gets Unauthorized message
	@Test
	public void getInvalidUserRole() throws Exception {
		given().header("Authorization", ProvisioningUtils.invalidAuthorization).
		contentType(ContentType.JSON).and().log().all().when().
		get(getBaseUrl()+ProvisioningUtils.getRoles).then().assertThat().statusCode(401);
	}


}
