// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.gateway_proxy.utils;

import com.medfusion.common.utils.PropertyFileLoader;
import com.intuit.ifs.csscat.core.utils.*;
import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.util.StringUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Base64;


public class MPUsersUtility {

	public static final String FINANCE_USER = "testuserFinance1";
	public static final String FINANCE_PASS = "Fs3Jn2&M";
	public static final String IMPLEMENTATION_USER = "testImplementation1";
	public static final String IMPLEMENTATION_PASS = "nG9;&(L&39mj";
	public static final String ADMIN_USER = "testuser1";
	public static final String ADMIN_PASS = "M3dfusion!";
	
	
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