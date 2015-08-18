package com.medfusion.product.object.maps.provisioning.page;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ProvisioningLoginPage extends BasePageObject{
	
	@FindBy(how = How.ID, using="loginName")
	public WebElement nameInput;
	
	@FindBy(how = How.ID, using="loginPassword")
	public WebElement passwordInput;
	
	@FindBy(how = How.ID, using="loginButton")
	public WebElement loginButton;
	
	public ProvisioningLoginPage(WebDriver driver, String url) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading login page");
		String santiziedUrl = url.trim();
		log("URL:" + santiziedUrl);
		driver.get(santiziedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}
	
	public ProvisioningLoginPage(WebDriver driver){
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public boolean assessLoginPageElements() {
		boolean allElementDisplayed = false;
		
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(nameInput);
		webElementsList.add(passwordInput);
		webElementsList.add(loginButton);
		
		for (WebElement webElement : webElementsList) {
			try {
				IHGUtil.waitForElement(driver, 60, webElement);
				log("Checking WebElement" + webElement.toString());
				if (webElement.isDisplayed()) {
					log("WebElement" + webElement.toString() + " is displayed");
					allElementDisplayed = true;
				} else {
					log("WebElement" + webElement.toString() + " is NOT displayed");
					return false;
				}
			} catch (Throwable e) {
				log(e.getStackTrace().toString());
			}
		}
		
		return allElementDisplayed;
	}
	
	public ProvisioningDashboardPage login(String name, String password) {
		IHGUtil.PrintMethodName();
		log("Login Credentials: [" + name + "] [" + password + "]");
		try {
			nameInput.clear();
			nameInput.sendKeys(name);
		} catch(org.openqa.selenium.WebDriverException e) {
			nameInput.sendKeys(name);
		}
		passwordInput.sendKeys(password);
		loginButton.click();
		return PageFactory.initElements(driver, ProvisioningDashboardPage.class);
	}
	
}
