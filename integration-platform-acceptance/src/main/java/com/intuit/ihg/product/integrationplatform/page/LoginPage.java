package com.intuit.ihg.product.integrationplatform.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.common.utils.IHGUtil;

/** @author vasudeo
 *  @Date 28-04-2014
 *  @Description :- Page Object for Patient Portal LoginPage
 */

public class LoginPage extends BasePageObject {
	
	public static final String PAGE_NAME = "Patient Portal Login Page";
	
	@FindBy( how = How.NAME, using="loginButtonField:_body:loginButton")
	private WebElement login;
	
	@FindBy( how = How.NAME, using="passwordField:_body:password")
	private WebElement password;
	
	@FindBy( how = How.NAME, using="usernameField:_body:username")
	private WebElement username;
	
	@FindBy(linkText="Home")
	private WebElement lnkHome;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method will Open the Application
	 * @param driver
	 * @param baseURL
	 */
	public LoginPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		String sanitizedUrl = baseURL.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	
	/** login the user into the patient portal
	 * 
	 * @param sUsername
	 * @param sPassword
	 * @return
	 * @throws InterruptedException 
	 */
	public PatientPage login( String sUsername, String sPassword ) throws InterruptedException {	
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		log( "Patient Login Credentials: [" + sUsername + "] [" + sPassword + "]" );	
		log("Waiting for Username element, max wait time is 60 seconds");
		IHGUtil.waitForElement(driver, 60, username);
		username.sendKeys( sUsername );
		password.sendKeys( sPassword );
		login.click();
		Thread.sleep(10000);
		return PageFactory.initElements(driver, PatientPage.class);
	}
	public boolean isLoginPageLoaded() {
		IHGUtil.PrintMethodName();
		
		return IHGUtil.waitForElement(driver, 5, lnkHome);		
	}
	
}
