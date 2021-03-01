package com.mfpay.merchant_provisioning.tests;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Data;
import utils.ProvisioningUtils;
import utils.Role;
import utils.User;

import com.jayway.restassured.path.json.JsonPath;
import com.medfusion.common.utils.PropertyFileLoader;

public class WPTests {
	protected PropertyFileLoader testData;
	private String baseUrl = Data.get("baseurl");
	
	
	
	
	@Test
	public void getWorldPayStatusAsImplementation() throws Exception{
		Response response = given().header("Authorization", ProvisioningUtils.implementationAuthorization).
		contentType(ContentType.JSON).and().log().all().when().
		get(baseUrl+ProvisioningUtils.getWPStatus).then().assertThat().
		statusCode(200).and().extract().response();
		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertEquals(jsonpath.get("status"), "UP");
	}
  
	@Test
	public void getWorldPayUnderwritingStatus() {     
		Response response = given().header("Authorization", ProvisioningUtils.implementationAuthorization).
		contentType(ContentType.JSON).and().log().all().when().
		get(baseUrl+ProvisioningUtils.postMerchant+"/"+testData.getProperty("staticMerchant")+"/status").then().assertThat().
		statusCode(200).and().extract().response();
		JsonPath jsonpath = new JsonPath(response.asString());
		Assert.assertEquals(jsonpath.get("status"), "Pending");
	}
}
