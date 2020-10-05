package com.medfusion.product.object.maps.pss2.page.Dashboard;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;


public class PSS2AdminDashboard extends PSS2MainPage {

	public PSS2AdminDashboard(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

}