//Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Specialty;

import org.openqa.selenium.WebDriver;

import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;

public class ManageSpecialty extends PSS2MenuPage {

	public ManageSpecialty(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

}
