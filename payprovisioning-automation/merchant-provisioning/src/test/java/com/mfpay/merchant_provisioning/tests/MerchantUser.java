package com.mfpay.merchant_provisioning.tests;

import helpers.UsersDetails;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utils.Data;
import utils.ProvisioningUtils;

import com.medfusion.common.utils.PropertyFileLoader;

public class MerchantUser {
	protected PropertyFileLoader testData;
	private String baseUrl = Data.get("baseurl");
	
	@BeforeTest
	 public void setBaseUri() throws Exception{
		testData = new PropertyFileLoader();
			
		}
	
	
	@Test
	 public void getMerchantUsersAsImplementation() throws Exception, IOException { 
		UsersDetails usersdetails = new UsersDetails();
		String getusers = baseUrl+ProvisioningUtils.getUsers+testData.getProperty("staticMerchant")+"/users";
		
		//Get users for a merchant GET
		usersdetails.getUsers(getusers);
		
		//Set roles for Merchant user POST
		usersdetails.createStaffUser(getusers,(testData.getProperty("staffusername")),(testData.getProperty("practicestaffid")));
		
		//Get roles for user GET
		usersdetails.getRolesForUser(getusers,(testData.getProperty("staffusername")),(testData.getProperty("practicestaffid")));
		
		
			
 }
	

}
