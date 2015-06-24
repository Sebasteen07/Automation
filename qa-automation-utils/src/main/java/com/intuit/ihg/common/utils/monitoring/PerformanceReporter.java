package com.intuit.ihg.common.utils.monitoring;


import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class PerformanceReporter {
	// https://dvcs.w3.org/hg/webperf/raw-file/tip/specs/NavigationTiming/Overview.html#processing-model
	// Section 5.1 - Processing Model has an image depicting all states
	private static String NAVIGATION_START = "navigationStart";
	private static String LOAD_EVENT_END = "loadEventEnd";
	
	private static final Logger WILY = Logger.getLogger("wily");
	
	public static long getPageLoadDuration(WebDriver driver, String pageName) throws InterruptedException {
		long navigationStart;
		long loadEventEnd;
		
		Map<String, Long> timingData = PerformanceReporter.getPerformanceTimings(driver);
		navigationStart = (Long) timingData.get(NAVIGATION_START);
		loadEventEnd = (Long) timingData.get(LOAD_EVENT_END);
		long duration = loadEventEnd - navigationStart;
		
		if (pageName != null && !pageName.isEmpty()) {
			WILY.info(pageName + ",ResponseTime," + duration);
		}
		return duration;
	}
	
	public static void printBrowserPerformanceTimings(WebDriver driver) throws InterruptedException {		
//		Map<String, Long> timingData = PerformanceReporter.getPerformanceTimings(driver);		
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Long> getPerformanceTimings(WebDriver driver) throws InterruptedException {
		Map<String, Long> timingData = null;

		do {
			timingData = (Map<String, Long>) ((JavascriptExecutor)driver).executeScript("var performance = window.performance || {};" + 
	                "var timings = performance.timing || {};"+
	                "return timings;");
			
			Thread.sleep(1000);
		} while ((Long) timingData.get("loadEventEnd") == 0);
		
		Log4jUtil.log("Web Browser Performance Timings:\nCurrent URL: " 
				+ driver.getCurrentUrl() + "\n" +
				timingData.toString() + 
				"\nGet more information here: " + 
				"https://dvcs.w3.org/hg/webperf/raw-file/tip/specs/NavigationTiming/Overview.html#processing-model");
		
		return timingData;		
	}

}
