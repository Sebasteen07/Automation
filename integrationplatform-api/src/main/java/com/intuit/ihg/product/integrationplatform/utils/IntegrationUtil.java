// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayedEnabled;
import com.medfusion.common.utils.IHGUtil;

public class IntegrationUtil extends IHGUtil {


	protected WebDriver driver;

	public IntegrationUtil(WebDriver driver) {
		super(driver);
	}

	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	public static void setSiteGenFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframebody");
	}

	public static void setDefaultFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setDefaultFrame(driver);
	}

	public static void setFrame(WebDriver driver, String frame) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, frame);
	}

	public static void setConsolidatedInboxFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();

		List<String> frames = new ArrayList<String>();
		frames.add("iframebody");
		frames.add("externalframe");

		IHGUtil.setFrameChain(driver, frames);
	}

	public static boolean verifyTextPresent(WebDriver driver, String value, int waitTime) throws IllegalArgumentException, InterruptedException {
		if (waitTime == 0) {
			throw new IllegalArgumentException("YOU CAN'T DO THIS USING THIS METHOD");
		}
		Thread.sleep(waitTime);
		return driver.getPageSource().contains(value);
	}

	public void checkAlert(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			log("Alert detected: {}" + alert.getText());
			alert.accept();

		} catch (Exception e) {
			// exception handling
			log("no alert was present");
		}
	}

	 public static boolean waitForElement(WebDriver driver, int n, WebElement ele) {
	        IHGUtil.PrintMethodName();
	        WebDriverWait wait = new WebDriverWait(driver, n);
	        boolean found = false;
	        try {
	            found = wait.until(ExpectedConditions.elementToBeClickable(ele)) != null;
	        } catch (Exception e) {
	        	Log4jUtil.log("exception found - "+ e);
	        }
	        return found;
	    }

}
