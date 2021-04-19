// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.SelectProfilePage;

public class ExistingPatientIDP extends PSS2MainPage {

	@FindBy(how = How.ID, using = "loginUsername")
	private WebElement inputLoginUsername;

	@FindBy(how = How.ID, using = "loginPassword")
	private WebElement inputLoginPassword;

	@FindBy(how = How.XPATH, using = "/html/body/div[3]/div[1]/div[1]/form/a/span")
	private WebElement buttonSignIn;

	public ExistingPatientIDP(WebDriver driver) {
		super(driver);
	}



	public ExistingPatientIDP(WebDriver driver, String url) {
		super(driver, url);
		PageFactory.initElements(driver, this);
	}

	public HomePage patientSignIn1(String uname, String pwd) throws InterruptedException {
		inputLoginUsername.sendKeys(uname);
		inputLoginPassword.sendKeys(pwd);
		buttonSignIn.click();
		Thread.sleep(4000);
		log("Click on Submit Button for IDP");
		return PageFactory.initElements(driver, HomePage.class);
	}

	public SelectProfilePage patientSignIn(String uname, String pwd) throws InterruptedException {
		inputLoginUsername.sendKeys(uname);
		inputLoginPassword.sendKeys(pwd);
		buttonSignIn.click();
		Thread.sleep(4000);
		log("Click on Submit Button for IDP");
		return PageFactory.initElements(driver, SelectProfilePage.class);
	}
}
