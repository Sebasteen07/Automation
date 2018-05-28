package com.medfusion.product.object.maps.pss2.page.Login;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PSS2MainPage;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;


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

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserName);
		webElementsList.add(inputPassword);
		webElementsList.add(buttonLogin);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public PSS2PracticeConfiguration login(String username, String pass) {
		inputUserName.sendKeys(username);
		inputPassword.sendKeys(pass);
		buttonLogin.click();
		return PageFactory.initElements(driver, PSS2PracticeConfiguration.class);
	}

}