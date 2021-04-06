// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.utils;

import org.openqa.selenium.internal.Base64Encoder;

public class User extends ProvisioningUtils{


    public static String getCredentialsEncodedInBase(String role) {
        Base64Encoder encoder = new Base64Encoder();
        if(role.equalsIgnoreCase("FINANCE")){
        return "Basic " + encoder.encode((FINANCE_USER + ":" + FINANCE_PASS).getBytes());
        }
        else if (role.equalsIgnoreCase("IMPLEMENTATION")){
        	return "Basic " + encoder.encode((IMPLEMENTATION_USER + ":" + IMPLEMENTATION_PASS).getBytes());
        }
        else if(role.equalsIgnoreCase("ADMIN")){
        	return "Basic " + encoder.encode((ADMIN_USER + ":" + ADMIN_PASS).getBytes());
        }
		return null;
       
    }
}
