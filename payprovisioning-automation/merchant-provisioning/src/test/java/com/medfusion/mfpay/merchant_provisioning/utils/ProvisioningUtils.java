// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random; 


public class ProvisioningUtils {

	
	
	public static String getRoles = "/v3/application/rcm/user/roles";
	public static String invalidAuthorization = "Basic TWVkZnVzaW9uVGVzdDoxTWVkZnVzaW9uVGVzdCo=";
	public static String postMerchant = "/internal/merchants";
	public static String getUsers = "/internal/merchants/";
	public static String getWPStatus = "/internal/merchants/wphealthcheck";
	public static String getUnderwritingStatus = "/internal/merchants/status";
	public static String postPartner = "/v6/application/rcm/merchants/";
	public static final String FINANCE_USER = "testuserFinance1";
	public static final String FINANCE_PASS = "Fs3Jn2&M";
	public static final String IMPLEMENTATION_USER = "testImplementation1";
	public static final String IMPLEMENTATION_PASS = "nG9;&(L&39mj";
	public static final String ADMIN_USER = "testuser1";
	public static final String ADMIN_PASS = "M3dfusion!";
	public static String PRACTICE_ROLE = "/internal/merchantUser/";
	
	
	
	public static void saveMMID(String value) throws IOException {
		Properties testConfig = new Properties();
        testConfig.load(new FileInputStream("testConfig.properties"));
        String env = testConfig.getProperty("test.environment");
        testConfig.load(new FileInputStream("src/test/resources/data-driven/" + env + ".properties"));
        testConfig.setProperty("mmid", value);
		FileOutputStream fileOut = new FileOutputStream("src/test/resources/data-driven/" + env + ".properties");
		testConfig.store(fileOut, null);
		fileOut.close();
		
	}
	

	public static void savePartner(String partnerid, String partnerusername, String password) throws IOException {
		Properties testConfig = new Properties();
		testConfig.load(new FileInputStream("testConfig.properties"));
	    String env = testConfig.getProperty("test.environment");
	    testConfig.load(new FileInputStream("src/test/resources/data-driven/" + env + ".properties"));
	    testConfig.setProperty("partner", partnerid);
		testConfig.setProperty("partner.username", partnerusername);
		testConfig.setProperty("partner.password", password);
		FileOutputStream fileOut = new FileOutputStream("src/test/resources/data-driven/" + env + ".properties");
		testConfig.store(fileOut, null);
		fileOut.close();
		
	}
	
	public static String randomizeMerchantIdentifiers() {
        Random random = new Random();
        int randomStamp =  random.nextInt(899998) + 100000;
		return String.valueOf(randomStamp);
        
    }
	
	

	


		
	
	
}
