// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import static io.restassured.path.json.JsonPath.from;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.helpers.PartnersInfo;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

public class PartnersAsFinance extends BaseRest{
	protected PropertyFileLoader testData;

	
	@BeforeTest
	  public void setBaseUri() throws Exception{
		 testData = new PropertyFileLoader();
		 setupFinanceRequestSpecBuilder();
		 setupResponsetSpecBuilder();
	  }

	
	 @Test
	 public void testCreatePartnerCredentialsAsFinance() throws Exception, IOException {
		PartnersInfo partnersinfo = new PartnersInfo();
		String postpartners = ProvisioningUtils.postPartner+testData.getProperty("static.merchant")+"/partners";
		
		//Create a partner POST
		String partnerid = partnersinfo.createPartner(postpartners,(testData.getProperty("partner.username")),(testData.getProperty("partner.password")));

		//Update partner credentials username and password PUT
		String updatedpassword = partnersinfo.updateUserNamePassword(partnerid,postpartners,testData.getProperty("username.update"),(testData.getProperty("password.update")));
		
		//Reset partner credentials POST
		partnersinfo.resetPartnerCredentials(partnerid,postpartners, testData.getProperty("username.update"),updatedpassword );
		
		//Delete partner DELETE
		partnersinfo.deletePartner(postpartners, partnerid);
			
  }

	@Test
	public void testGetPartnerCredentialsAsFinance(){
		PartnersInfo partnersinfo = new PartnersInfo();
		String postpartners = ProvisioningUtils.postPartner+testData.getProperty("static.merchant")+"/partners";

		Response response = partnersinfo.getPartners(postpartners,(testData.getProperty("partner.username")),
				(testData.getProperty("partner.password")));

		String partners = response.asString();
		ArrayList<Map<String,?>> jsonAsArrayList = from(partners).get("");
		System.out.println("MMID has:"+jsonAsArrayList.size()+" partners");

	}
	
	 
}
