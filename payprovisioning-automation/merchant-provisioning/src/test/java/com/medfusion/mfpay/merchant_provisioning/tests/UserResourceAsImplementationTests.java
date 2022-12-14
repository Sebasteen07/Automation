// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;

import java.io.IOException;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.helpers.UsersDetails;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

public class UserResourceAsImplementationTests extends BaseRest {
	
	protected PropertyFileLoader testData;
	
	
	@BeforeTest
	  public void setBaseUri() throws Exception{
		 testData = new PropertyFileLoader();
		 setupImplementationRequestSpecBuilder();
		 setupResponsetSpecBuilder();
	  }
	
	
	@Test(enabled = true, groups = { "MerchantProvisioningBEAcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	 public void getMerchantUsersAsImplementation() throws Exception, IOException { 
		UsersDetails usersdetails = new UsersDetails();
		String getusers = ProvisioningUtils.getUsers+testData.getProperty("static.merchant")+"/users";
		
		//Get users for a merchant GET
		usersdetails.getUsers(getusers);
		
		//Set roles for Merchant user POST
		usersdetails.createStaffUser(getusers,(testData.getProperty("staff.username")),(testData.getProperty("practice.staffid")));
		
		//Get roles for user GET
		usersdetails.getRolesForUser(getusers,(testData.getProperty("staff.username")),(testData.getProperty("practice.staffid")));
		
		
			
 }
	

}
