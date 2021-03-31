// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;


import java.io.IOException;

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
	 public void createPartnerCredentialsAsFinance() throws Exception, IOException { 
		PartnersInfo partnersinfo = new PartnersInfo();
		String postpartners = ProvisioningUtils.postPartner+testData.getProperty("staticMerchant")+"/partners";
		
		//Create a partner POST
		String partnerid = partnersinfo.createPartner(postpartners,(testData.getProperty("partnerusername")),(testData.getProperty("partnerpassword")));
		
		//Get partners for a mmid GET
		partnersinfo.getPartners(postpartners,(testData.getProperty("partnerusername")),(testData.getProperty("partnerpassword")));
		
		//Update partner credentials username and password PUT
		String updatedpassword = partnersinfo.updateUserNamePassword(partnerid,postpartners,testData.getProperty("usernameupdate"),(testData.getProperty("passwordupdate")));
		
		//Reset partner credentials POST
		partnersinfo.resetPartnerCredentials(partnerid,postpartners, testData.getProperty("usernameupdate"),updatedpassword );
		
		//Delete partner DELETE
		partnersinfo.deletePartner(postpartners, partnerid);
			
  }
	
	 
}
