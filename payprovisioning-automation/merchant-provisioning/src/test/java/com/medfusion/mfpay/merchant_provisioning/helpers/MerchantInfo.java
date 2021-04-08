// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.helpers;

import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.pojos.Merchant;
import com.medfusion.mfpay.merchant_provisioning.tests.BaseRest;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

public class MerchantInfo extends BaseRest {
	 protected PropertyFileLoader testData;
	
	
	public String createUpdateElementMerchant() throws IOException {
		
			testData = new PropertyFileLoader();
		  	Map<String, Object> merchantdetails = Merchant.getMerchantMap((testData.getProperty("merchantname")),
			testData.getProperty("doingbusinessas"),testData.getProperty("externalmerchantid"),testData.getProperty("customeraccountnumber"),
			testData.getProperty("merchantphonenumber"),testData.getProperty("transactionlimit"),testData.getProperty("primaryfirstname"),
			testData.getProperty("primarylastname"),testData.getProperty("primaryphonenumber"),testData.getProperty("primaryemail"),
			testData.getProperty("merchantaddress1"),testData.getProperty("merchantcity"),testData.getProperty("merchantstate"),
			testData.getProperty("merchantzip"),testData.getProperty("accountnumber"),testData.getProperty("routingnumber"),
			testData.getProperty("federaltaxid"),testData.getProperty("businessestablisheddate"),testData.getProperty("businesstype"),
			testData.getProperty("mcccode"),testData.getProperty("ownershiptype"),testData.getProperty("websiteurl"),
			testData.getProperty("amexpercent"),testData.getProperty("midqfeepercent"),testData.getProperty("midqupperfeepercent"),
			testData.getProperty("nqfeepercent"),testData.getProperty("nqupperfeepercent"),testData.getProperty("pertransactionauthfee"),
			testData.getProperty("pertransactionrefundfee"),testData.getProperty("qfeepercent"), testData.getProperty("qupperpercent"));
	
		  	Response response = given().spec(requestSpec).
		  	body(merchantdetails).when().post(ProvisioningUtils.postMerchant)
		  	.then().spec(responseSpec).and().extract().response();
		  	 
		  	JsonPath jsonpath = new JsonPath(response.asString());
		  	Validations validate = new Validations();
			validate.verifyMerchantDetails(response.asString());
		  	ProvisioningUtils.saveMMID(jsonpath.get("id").toString());
		  	return response.asString();
 
	  }

	 public void getMerchantDetails(String mmid) {
		 
		 	String getmerchant = ProvisioningUtils.postMerchant+"/"+mmid;
		  	given().spec(requestSpec).
		  	when().get(getmerchant).then().spec(responseSpec);
			
		  
	}

	 public void updateGeneralMerchantDetails(String mmid) throws IOException {
		 
		 	testData = new PropertyFileLoader();
		  	Map<String, Object> merchantdetails = Merchant.getMerchantMap((testData.getProperty("merchantnameupdate")),
			testData.getProperty("doingbusinessas"),testData.getProperty("externalmerchantidupdate"),testData.getProperty("customeraccountnumber"),
			testData.getProperty("merchantphonenumber"),testData.getProperty("transactionlimitupdate"),testData.getProperty("primaryfirstname"),
			testData.getProperty("primarylastname"),testData.getProperty("primaryphonenumber"),testData.getProperty("primaryemail"),
			testData.getProperty("merchantaddress1"),testData.getProperty("merchantcity"),testData.getProperty("merchantstate"),testData.getProperty("merchantzip"),
			testData.getProperty("accountnumber"),testData.getProperty("routingnumber"),testData.getProperty("federaltaxid"),
			testData.getProperty("businessestablisheddate"),testData.getProperty("businesstype"),testData.getProperty("mcccodeupdate"),
			testData.getProperty("ownershiptype"),testData.getProperty("websiteurlupdate"),testData.getProperty("amexpercent"),
			testData.getProperty("midqfeepercent"),testData.getProperty("midqupperfeepercent"),testData.getProperty("nqfeepercent"),
			testData.getProperty("nqupperfeepercent"),testData.getProperty("pertransactionauthfee"),testData.getProperty("pertransactionrefundfee"),
			testData.getProperty("qfeepercent"),testData.getProperty("qupperpercent"));
		  	
		  	ObjectMapper objectMapper = new ObjectMapper();
		  	String convertTOJson = objectMapper.writeValueAsString(merchantdetails);

			String putmerchant = ProvisioningUtils.postMerchant+"/"+mmid+"/wpSubMerchant?updateType=GENERAL_INFO";
			Response response = given().spec(requestSpec).
			body(convertTOJson).when().put(putmerchant).then().
			spec(responseSpec).and().extract().response();
			
			Merchant readJSON = objectMapper.readValue(response.asString(), Merchant.class);
			Validations validate = new Validations();
			validate.verifyMerchantDetailsOnUpdate(readJSON.getExternalMerchantId().toString(),readJSON.getMaxTransactionLimit().toString(),readJSON.getAccountDetails());
			 
	}


}
