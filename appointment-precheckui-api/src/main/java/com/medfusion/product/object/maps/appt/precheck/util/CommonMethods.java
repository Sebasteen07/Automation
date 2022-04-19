// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import java.util.Calendar;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

public class CommonMethods extends BaseTestNGWebDriver{
    JavascriptExecutor jse;
	public void highlightElement(WebElement element) {
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('style','border: solid 6px red');", element);
	}

	public String getDate(Calendar cal) {
		return "" + cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);
	}

	public String generateRandomNum() {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		return String.valueOf(randamNo);
	}

}
