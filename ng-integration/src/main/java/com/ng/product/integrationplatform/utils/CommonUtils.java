package com.ng.product.integrationplatform.utils;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CommonUtils {

	public static void VerifyTwoValues(String strValueToVerify, String strVerificationCondition, String strValueToCompareWith) throws Throwable {
		if (strVerificationCondition.equalsIgnoreCase("equals")) {
			if (strValueToVerify.equals(strValueToCompareWith))
				System.out.println("The text Value " + strValueToCompareWith + " matches as expected");
			else{
				System.out.println("The text value " + strValueToVerify + " does not matches as expected " + strValueToCompareWith + " value.");
				Assert.assertEquals(strValueToVerify, strValueToCompareWith);
			}
		} else if (strVerificationCondition.equalsIgnoreCase("contains")) {
			if (strValueToVerify.contains(strValueToCompareWith))
				System.out.println("The text Value " + strValueToCompareWith + " matches as expected");
			else{
				System.out.println("The text value " + strValueToVerify + " does not matches as expected " + strValueToCompareWith + " value.");
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
	System.out.println("Value of "+keyName+" is "+value);
    }
	else
		System.out.println("Response is empty");
	return value;
	}
	
}
