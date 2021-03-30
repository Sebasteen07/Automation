//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class CommonUtils {

	public static void VerifyTwoValues(String strValueToVerify, String strVerificationCondition, String strValueToCompareWith) throws Throwable {
		if (strVerificationCondition.equalsIgnoreCase("equals")) {
			if (strValueToVerify.equals(strValueToCompareWith))
				Log4jUtil.log("The text Value " + strValueToCompareWith + " matches as expected");
			else{
				Log4jUtil.log("The text value " + strValueToVerify + " does not matches as expected " + strValueToCompareWith + " value.");
				assertEquals(strValueToVerify, strValueToCompareWith);
			}
		} else if (strVerificationCondition.equalsIgnoreCase("contains")) {
			if (strValueToVerify.contains(strValueToCompareWith))
				Log4jUtil.log("The text Value " + strValueToCompareWith + " matches as expected");
			else{
				Log4jUtil.log("The text value " + strValueToVerify + " does not matches as expected " + strValueToCompareWith + " value.");
				assertTrue(strValueToVerify.equals(strValueToCompareWith));
			}
		}
	}
	
	public static String getResponseKeyValue(String response, String keyName){
	String value="";
	if(!response.isEmpty()){
	JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();        
	value = jsonObject.get(keyName).toString();
	if(value.contains("\"")){
		value= value.replace("\"", "");
	}
	Log4jUtil.log("Value of "+keyName+" is "+value);
    }
	else
		Log4jUtil.log("Response is empty");
	return value;
	}
	
	public static String changeESTtoIST(String dateInput) throws ParseException {
		TimeZone sourceTimeZone = TimeZone.getTimeZone("America/New_York");
	    TimeZone targetTimeZone = TimeZone.getTimeZone("Asia/Calcutta");
	    
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy hh:mm");
	    formatter.setTimeZone(sourceTimeZone);
	    Date date = formatter.parse(dateInput);
	    formatter.setTimeZone(targetTimeZone);
	    Log4jUtil.log("IST Date "+formatter.format(date));
	    return formatter.format(date);
	}
}
