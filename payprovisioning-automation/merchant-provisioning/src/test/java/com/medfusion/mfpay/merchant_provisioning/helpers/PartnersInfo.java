// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.helpers;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.tests.BaseRest;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

public class PartnersInfo extends BaseRest {
	
	protected PropertyFileLoader testData;
	
	 
	 public String createPartner(String postpartnerurl, String partnerusername, String partnerpassword) throws IOException {
		 
		 Map<String,String> credentials = new HashMap<String,String>();
		 credentials.put("username", partnerusername);
		 credentials.put("password", partnerpassword);
		 
		 Response response =  given().spec(requestSpec).
		 body(credentials).when().post(postpartnerurl).then().
		 spec(responseSpec).and().extract().response();
		 
		 JsonPath jsonpath = new JsonPath(response.asString());
		 Assert.assertNotNull(jsonpath, "Response was null. Partner not created!");
		 System.out.println("Partner created successfully:" + jsonpath.get("id").toString());
		 ProvisioningUtils.savePartner(jsonpath.get("id").toString(),jsonpath.get("username").toString(),jsonpath.get("password").toString());
		 return jsonpath.get("id").toString();
		
		 
	 }

	 public void checkUserNamePassword(String postpartners, String username, String password) {
		 given().spec(requestSpec)
		 .param("username",username)
		 .param("password",password).when().
		 get(postpartners+"/check").then(). statusCode(200);
	}

	 
	 public String updateUserNamePassword(String partnerid,String postpartners, String usernameupdate, String passwordupdate) {
		 Response response = given().spec(requestSpec).when()
		.param("username",usernameupdate)
		.param("password",passwordupdate)
		.param("id",partnerid).
		 when().put(postpartners+"/"+partnerid).then().
		 spec(responseSpec).and().extract().response();
		 
		 JsonPath jsonpath = new JsonPath(response.asString());
		 Assert.assertNotNull(jsonpath, "Response was null. Partner was not updated!");
		 Assert.assertEquals(jsonpath.get("id").toString(), partnerid);
		 Assert.assertEquals(jsonpath.get("username").toString(), usernameupdate);
		 return jsonpath.get("password").toString();
		
	}

	  public void resetPartnerCredentials(String partnerid,String postpartners, String usernameupdate, String updatedpassword) {
		  Response response = given().spec(requestSpec).when().
		  post(postpartners+"/"+partnerid).then().
		  spec(responseSpec).and().extract().response();
			 
		  JsonPath jsonpath = new JsonPath(response.asString());
		  Assert.assertNotNull(jsonpath, "Response was null. Password update for partner was not successful");
		  Assert.assertEquals(jsonpath.get("id").toString(), partnerid);
		  Assert.assertEquals(jsonpath.get("username").toString(), usernameupdate);
		  Assert.assertNotSame(jsonpath.get("password").toString(), updatedpassword, "Password reset not successful");
		
	}

	  public void deletePartner(String postpartners, String partnerid) {
		  given().spec(requestSpec).when().
		  delete(postpartners+"/"+partnerid).then().statusCode(200);
		
	}

	  public Response getPartners(String postpartners, String username, String password) {
		  
		  return given().spec(requestSpec).when().
		  get(postpartners).then().
		  spec(responseSpec).and().extract().response();

	}

}
