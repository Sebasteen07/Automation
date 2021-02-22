package com.mfpay.merchant_provisioning.tests;


import java.io.IOException;

import helpers.PartnersInfo;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utils.Data;
import utils.ProvisioningUtils;

import com.medfusion.common.utils.PropertyFileLoader;

public class Partners {
	protected PropertyFileLoader testData;
	private String baseUrl = Data.get("baseurl");
	
	 @BeforeTest
	 public void setBaseUri() throws Exception{
		testData = new PropertyFileLoader();
			
		}

	
	 @Test
	 public void createPartnerCredentialsAsFinance() throws Exception, IOException { 
		PartnersInfo partnersinfo = new PartnersInfo();
		String postpartners = baseUrl+ProvisioningUtils.postPartner+testData.getProperty("staticMerchant")+"/partners";
		
		//Get partners for a mmid GET
		partnersinfo.getPartners(postpartners,(testData.getProperty("partnerusername")),(testData.getProperty("partnerpassword")));
		
		//Create a partner POST
		String postpassword = partnersinfo.createPartner(postpartners,(testData.getProperty("partnerusername")),(testData.getProperty("partnerpassword")));
		
		//Check username and password GET
		partnersinfo.checkUserNamePassword(postpartners,testData.getProperty("partnerusername"),postpassword);
		
		//Update partner credentials username and password PUT
		String updatedpassword = partnersinfo.updateUserNamePassword(postpartners,testData.getProperty("usernameupdate"),(testData.getProperty("passwordupdate")));
		
		//Reset partner credentials POST
		partnersinfo.resetPartnerCredentials(postpartners, testData.getProperty("usernameupdate"),updatedpassword );
		
		//Delete partner DELETE
		partnersinfo.deletePartner(postpartners);
			
  }
}
