// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.tests;


import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.helpers.MerchantInfo;
import com.medfusion.mfpay.merchant_provisioning.helpers.PaypalDetails;

public class MerchantResourceAsFinance extends BaseRest{
	protected PropertyFileLoader testData;
	
  @BeforeTest
  public void setBaseUri() throws Exception{
	  testData = new PropertyFileLoader();
	  setupFinanceRequestSpecBuilder();
	  setupResponsetSpecBuilder();
  }

  //Creates a new element merchant as Finance user.
  @Test
  public void createNewElementMerchantAsFinance() throws IOException {
	  
	  MerchantInfo merchantinfo = new MerchantInfo();
	  merchantinfo.createUpdateElementMerchant();  
	  
  } 
    
  //Update general merchant details for the merchant created as finance
  @Test
  public void updateGeneralMerchantInfo() throws IOException {
	  MerchantInfo merchantinfo = new MerchantInfo();
	  merchantinfo.updateGeneralMerchantDetails(testData.getProperty("mmid"));
    
  }
  
  //Get details of the merchant created as finance
  @Test
  public void getMerchantById() throws IOException {
	  MerchantInfo merchantinfo = new MerchantInfo();
	  merchantinfo.getMerchantDetails(testData.getProperty("mmid"));
    
  }
  
  //Creates a new paypal merchant as Finance user.
  @Test
  public void createNewPaypalMerchantAsFinance() throws IOException {
	  
	  PaypalDetails merchantdetails = new PaypalDetails();
	  merchantdetails.createUpdatePaypalMerchant();  
	  
  } 

   
   
   
 
  
  

  
 
}
