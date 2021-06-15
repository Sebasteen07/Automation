// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class CommonMethods extends PSS2MainPage {

	public CommonMethods(WebDriver driver) {
		super(driver);
	}

	public void highlightElement(WebElement element) {
		jse.executeScript("arguments[0].setAttribute('style','border: solid 6px red');", element);
	}
	
	public void pageDown(int d) throws InterruptedException {
		Thread.sleep(1000);
		// This will scroll down the page by 800 pixel vertical
		jse.executeScript("window.scrollBy(0," + d + ")");
		Thread.sleep(1000);
	}

	public void pageUp(int t) throws InterruptedException {
		Thread.sleep(1000);
		// This will scroll up the page by 600 pixel vertical
		jse.executeScript("window.scrollBy(" + t + ",0)");
		Thread.sleep(1000);
	}

}
