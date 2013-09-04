package com.intuit.ihg.product.mu2.utils;

import com.intuit.api.security.client.TokenManager;


public class OAuthClientBootstrapMain {

    public static void main (String [] args) throws Exception
    {
    	String appToken="";
    	String username="";
    	String password="";
    	
        try{
        	
            APITestData apitestData = new APITestData();
        	APIData testData = new APIData(apitestData);
        
    
          	appToken = testData.getOauthAppToken();
        	username = testData.getOauthUsername();
        	password = testData.getOauthPassword();

        
            TokenManager.initializeTokenStore(appToken, username, password);
        }
       catch (Exception e)
        {
            System.err.println("Exception encountered while bootstrapping client environment.");
            e.printStackTrace(System.err);
        }
    }


}
