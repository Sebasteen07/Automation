//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.patientportal2.utils;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.intuit.ihg.common.utils.WebPoster;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;

public class PortalUtil2 extends IHGUtil {
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screenResolution = new Dimension((int) toolkit.getScreenSize().getWidth(),
			(int) toolkit.getScreenSize().getHeight());

	Dimension halfWidthscreenResolution = new Dimension((int) toolkit.getScreenSize().getWidth() / 2,
			(int) toolkit.getScreenSize().getHeight());

	protected WebDriver driver;

	public PortalUtil2(WebDriver driver) {
		super(driver);
	}

	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	public static void setPortalFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframebody");
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
	
	public static void acceptAlert(WebDriver driver) {
		try {
		Alert alert = driver.switchTo().alert();
		alert.accept();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		}
	
	public static void setquestionnarieFrame(WebDriver pDriver) {
		IHGUtil.PrintMethodName();		
		try {
			pDriver.switchTo().frame(pDriver.findElement(By.xpath("//div[@id='lightbox']/iframe[@title='Forms']")));
		} catch (StaleElementReferenceException e) {
			System.out.println("Stale element exception caught as expected. Frame should be correctly switched");
		}
	}

	public static String generateUniqueUsername(String username, PropertyFileLoader testData) throws Exception {
		long generatedTS = System.currentTimeMillis();
		username = username + generatedTS;
		int i = 0;

		while (!PortalUtil2.checkUsernameEmailIsUnique(username, testData.getProperty("credentials.matcher.url"))) {
			if (i++ > 9)
				throw new RuntimeException("Username was not unique after 10 attempts");
			generatedTS = System.currentTimeMillis();
			username = username + generatedTS;
		}
		return username;
	}

	/**
	 * An exception thrown means the format was wrong, mfss-user is down, or other
	 * unrecoverable issue
	 *
	 * @param username
	 * @param url
	 * 
	 * @return boolean
	 */
	public static boolean checkUsernameEmailIsUnique(String username, String url) throws Exception {
		IHGUtil.PrintMethodName();
		WebPoster poster = new WebPoster();
		System.out.println("Calling patient matcher for username =\"" + username + "\", url= \"" + url + "\"");
		poster.setServiceUrl(url);
		poster.setContentType("application/json;");
		poster.setExpectedStatusCode(200);
		return poster.postFromString("{\"emailOrUserName\":\"" + username + "\"}").contains("NO_MATCH");
	}

}
