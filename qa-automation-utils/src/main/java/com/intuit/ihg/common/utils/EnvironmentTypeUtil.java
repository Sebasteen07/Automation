package com.intuit.ihg.common.utils;

public class EnvironmentTypeUtil {
	
 public enum EnvironmentType {
			DEV3, QA1, P10INT, DEMO, PROD
		               }

	/**
	 * Author:- bkrishnankutty Date:-3-4-2013 
	 * 
	 * Desc:-Method will get the DEV3 as
	 * default ie if there is no value for test.environment in property file
	 * else it will return the environment which is mentioned in the property
	 * 
	 * @param env
	 * @return
	 */	
 	public static EnvironmentType getEnvironmentType(String env){
			if (env == null)
				return EnvironmentType.DEV3;
			if (env.equalsIgnoreCase("DEMO"))
				return EnvironmentType.DEMO;
			else if (env.equalsIgnoreCase("QA1"))
				return EnvironmentType.QA1;
			else if (env.equalsIgnoreCase("P10INT"))
				return EnvironmentType.P10INT;
			else if (env.equalsIgnoreCase("PROD"))
				return EnvironmentType.PROD;
			else if (env.equalsIgnoreCase("DEV3"))
				return EnvironmentType.DEV3;
			else 
				return EnvironmentType.DEV3;
	}
 	
}
