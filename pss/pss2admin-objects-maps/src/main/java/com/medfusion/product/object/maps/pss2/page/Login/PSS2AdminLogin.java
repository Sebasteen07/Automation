// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class PSS2AdminLogin extends PSS2MainPage {

	@FindBy(how = How.ID, using = "username")
	private WebElement inputUserName;

	@FindBy(how = How.ID, using = "password")
	private WebElement inputPassword;

	@FindBy(how = How.LINK_TEXT, using = "Login")
	private WebElement buttonLogin;

	public PSS2AdminLogin(WebDriver driver) {
		super(driver);
	}

	public PSS2AdminLogin(WebDriver driver, String url) {
		super(driver, url);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public PSS2PracticeConfiguration login(String username, String pass) {
		commonMethods.highlightElement(inputUserName);
		inputUserName.sendKeys(username);
		commonMethods.highlightElement(inputPassword);
		inputPassword.sendKeys(pass);
		buttonLogin.click();
		return PageFactory.initElements(driver, PSS2PracticeConfiguration.class);
	}

}
