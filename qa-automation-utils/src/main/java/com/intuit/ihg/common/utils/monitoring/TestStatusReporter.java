package com.intuit.ihg.common.utils.monitoring;

import org.apache.log4j.Logger;
import org.testng.ITestResult;

public class TestStatusReporter {
	private static final Logger WILY = Logger.getLogger("wily");
	
	public static void logTestStatus(String testName, int testStatus) {
		if(testStatus == ITestResult.FAILURE) {
			WILY.info(testName + ",TestStatus,0");
		} else if (testStatus == ITestResult.SUCCESS) {
			WILY.info(testName + ",TestStatus,1");
		}
	}

}
