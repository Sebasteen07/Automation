package com.medfusion.product.object.maps.appt.precheck.page.CurbsideCheckIn;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.appt.precheck.util.BaseTest;

public class CurbsideCheckInPage extends BaseTest {

	public CurbsideCheckInPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

}
