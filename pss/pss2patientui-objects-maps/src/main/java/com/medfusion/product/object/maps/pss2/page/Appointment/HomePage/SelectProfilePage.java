// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.HomePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;

public class SelectProfilePage extends PSS2MainPage {

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'PSS2MainPage')]")
	WebElement selectProfileLabel;

	@FindBy(how = How.XPATH, using = "//ul[@class=\"mf-list--modern mf-list--letter-circles\"]/li[1]")
	WebElement primaryProfile;

	public SelectProfilePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		Assert.assertEquals("PSS2MainPage", selectProfileLabel.getText());
		return true;
	}

	public HomePage selectProfile() {
		log("On Select Profile Page");
		primaryProfile.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
}
