//Copyright 2020 NXGN Management, LLC. All Rights Reserved.
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

	@Override
	public boolean areBasicPageElementsPresent() {
		// TODO Auto-generated method stub
		return true;
	}

}
