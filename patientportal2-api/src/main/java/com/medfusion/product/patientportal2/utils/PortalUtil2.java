//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.patientportal2.utils;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import org.openqa.selenium.*;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import com.intuit.ihg.common.utils.WebPoster;

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

	public static String generateUniqueUsername(String username, PropertyFileLoader testData) throws Exception {
		long generatedTS = System.currentTimeMillis();
		username = username + generatedTS;
		int i = 0;

		while (!PortalUtil2.checkUsernameEmailIsUnique(username, testData.getProperty("credentialsMatcherUrl"))) {
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
	 * @return
	 * @throws Exception
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
