// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import java.util.Calendar;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.medfusion.product.object.maps.appt.precheck.Main.ApptPrecheckMainPage;

public class CommonMethods extends ApptPrecheckMainPage {

	public CommonMethods(WebDriver driver) {
		super(driver);
	}

	public void highlightElement(WebElement element) {
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
