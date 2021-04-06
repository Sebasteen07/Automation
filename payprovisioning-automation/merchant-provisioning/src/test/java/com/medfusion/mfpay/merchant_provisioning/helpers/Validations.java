// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.helpers;

import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.pojos.AccountDetails;
import com.medfusion.mfpay.merchant_provisioning.pojos.ContractedRates;
import com.medfusion.mfpay.merchant_provisioning.pojos.PaypalMerchantAccountDetails;
import com.medfusion.mfpay.merchant_provisioning.pojos.RemitToAddress;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;

public class Validations {
	
	protected static PropertyFileLoader testData;	


	   public void verifyMerchantDetails(String merchantdetails) throws IOException {
		   
		   testData = new PropertyFileLoader();
		   JsonPath jsonpath = new JsonPath(merchantdetails);
		   Assert.assertNotNull(jsonpath, "Response was null");
		   Assert.assertNotNull(jsonpath.get("id"), "Merchant id was not in the response");
		   Assert.assertEquals(jsonpath.get("doingBusinessAs"), (testData.getProperty("doingbusinessas")));
		   Assert.assertEquals(jsonpath.get("customerAccountNumber"), (testData.getProperty("customeraccountnumber")));
		   Assert.assertEquals(jsonpath.get("primaryContactFirstName"), (testData.getProperty("primaryfirstname")));
		   Assert.assertEquals(jsonpath.get("primaryContactLastName"), (testData.getProperty("primarylastname")));
		   Assert.assertEquals(jsonpath.get("primaryContactEmail"), (testData.getProperty("primaryemail")));
		   Assert.assertEquals(jsonpath.get("primaryContactPhoneNumber"), (testData.getProperty("primaryphonenumber")));
		   Assert.assertEquals(jsonpath.get("phoneNumber"), (testData.getProperty("merchantphonenumber")));
		   Assert.assertEquals(jsonpath.get("merchantAddress.address1"), (testData.getProperty("merchantaddress1")));
		   Assert.assertEquals(jsonpath.get("merchantAddress.city"), (testData.getProperty("merchantcity")));
		   Assert.assertEquals(jsonpath.get("merchantAddress.state"), (testData.getProperty("merchantstate")));
		   Assert.assertEquals(jsonpath.get("merchantAddress.zip"), (testData.getProperty("merchantzip")));
		   Assert.assertEquals(jsonpath.get("merchantAddress.country"), "US");
		   Assert.assertEquals(jsonpath.get("customerAccountNumber"), (testData.getProperty("customeraccountnumber")));
		   Assert.assertEquals(jsonpath.get("externalMerchantId"), Integer.parseInt((testData.getProperty("externalmerchantid"))));
		   Assert.assertEquals(jsonpath.get("maxTransactionLimit"), Integer.parseInt((testData.getProperty("transactionlimit"))));
		   Assert.assertEquals(jsonpath.get("accountDetails.preferredProcessor"), (PracticeConstants.PREFERRED_PROCESSOR_ELEMENT));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.backingWPMerchantExists"), true);
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.elementAccountId"), (testData.getProperty("elementaccountid")));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.mccCode"), (testData.getProperty("mcccode")));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.ownershipType"), (testData.getProperty("ownershiptype")));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.businessType"), (testData.getProperty("businesstype")));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.businessEstablishedDate"), (testData.getProperty("businessestablisheddate")));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.websiteURL"), (testData.getProperty("websiteurl")));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.billingDescriptor"), "PFA*"+(testData.getProperty("doingbusinessas")));
		   Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.elementAcceptorId"), "Acceptor id was null");
		   Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.elementTerminalId"), "Element Terminal id was null");
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.elementAccountToken"), (testData.getProperty("elementaccounttoken")));
		   Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.vantivIbmMid"), "IBM Mid was null");
		   Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.subMerchantId"), "Submerchant id was null");

		
	   }



	    public static void validateStaffUser(String staffusername,String practicestaffid, String response) throws IOException {
	       testData = new PropertyFileLoader();
	       JsonPath jsonpath = new JsonPath(response);
	       Assert.assertNotNull(jsonpath, "Response was null.Adding user was not successful");
	       Assert.assertEquals(jsonpath.get("practicestaffId").toString(), (testData.getProperty("practicestaffid")));
	       Assert.assertEquals(jsonpath.get("userName"), (testData.getProperty("staffusername")));
			
	}


		public void verifyMerchantDetailsOnUpdate(String externalMerchantId, String maxTransactionLimit,
				AccountDetails accountDetails) throws IOException {
			
		  testData = new PropertyFileLoader();
		  Assert.assertEquals(externalMerchantId, (testData.getProperty("externalmerchantidupdate")));
		  Assert.assertEquals(maxTransactionLimit, testData.getProperty("transactionlimitupdate"));
		  Assert.assertEquals(accountDetails.getPreferredProcessor(), PracticeConstants.PREFERRED_PROCESSOR_ELEMENT);
		  Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getBillingDescriptor(),"PFA*"+testData.getProperty("doingbusinessas"));
		  Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getOwnershipType(),testData.getProperty("ownershiptype"));
		  Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getBusinessType(),testData.getProperty("businesstype"));
		  Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getMccCode(),testData.getProperty("mcccodeupdate"));
		  Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getBusinessEstablishedDate(),testData.getProperty("businessestablisheddate"));
		  Assert.assertEquals(accountDetails.getWorldPayAccountDetails().getWebsiteURL(),testData.getProperty("websiteurlupdate"));
		  Assert.assertEquals(accountDetails.getCheckingDepositType(),PracticeConstants.CHECKING_TYPE);
		 	  
			
		}


		public void verifyMerchantDetailsForPaypal(String externalmerchantid,String transactionlimit,
			AccountDetails accountDetails,String customeraccountnumber) throws IOException {
			
			testData = new PropertyFileLoader();
			Assert.assertEquals(externalmerchantid, testData.getProperty("externalmerchantid"));
			Assert.assertEquals(transactionlimit, testData.getProperty("transactionlimit"));
			Assert.assertEquals(accountDetails.getPreferredProcessor(),PracticeConstants.PREFERRED_PROCESSOR_PAYPAL) ;
			Assert.assertEquals(customeraccountnumber, testData.getProperty("customeraccountnumber"));
		}


}
