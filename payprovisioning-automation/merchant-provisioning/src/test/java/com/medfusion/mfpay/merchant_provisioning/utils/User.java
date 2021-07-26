// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.utils;
import java.util.Base64;

public class User extends ProvisioningUtils{

    public static String getCredentialsEncodedInBase(String role) {
        if(role.equalsIgnoreCase("FINANCE")){
           return "Basic " + Base64.getEncoder().encodeToString((FINANCE_USER + ":" + FINANCE_PASS).getBytes());	
        }
        else if (role.equalsIgnoreCase("IMPLEMENTATION")){
      return "Basic " + Base64.getEncoder().encodeToString((IMPLEMENTATION_USER + ":" + IMPLEMENTATION_PASS).getBytes());

        }
        else if(role.equalsIgnoreCase("ADMIN")){
         return "Basic " + Base64.getEncoder().encodeToString((ADMIN_USER + ":" + ADMIN_PASS).getBytes());
        }
		return null;
       
    }
}
