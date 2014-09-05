package com.intuit.ihg.product.object.maps.portal.page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.createAccount.CreateAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.createAccount.CreateAccountPageOnBetaSite;
import com.intuit.ihg.product.object.maps.portal.page.forgotPassword.ResetYourPasswordPage;
import com.intuit.ihg.product.object.maps.portal.page.forgotuserid.ForgotUserIdEnterEmailPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.common.utils.IHGUtil;

/** @author bkrishnankutty
 *  @Date 2-12-2013
 *  @Description :- Page Object for Patient Portal LoginPage
 *  @Note :- Old Frame work this page is com.intuit.ihg.product.portal.desktop.main.beta.PortalLoginPage
 */

public class PortalLoginPage extends BasePageObject {
	
	public static final String PAGE_NAME = "Patient Portal Login Page";
	
	@FindBy( how = How.NAME, using="loginButtonField:_body:loginButton")
	private WebElement login;
	
	@FindBy( how = How.NAME, using="passwordField:_body:password")
	private WebElement password;
	
	@FindBy( how = How.NAME, using="usernameField:_body:username")
	private WebElement username;
	
	@FindBy(css="input[name*='createAccount2']")
	private WebElement butsignup;
	
	@FindBy(css="a[href*='form:createAccount']")
	private WebElement butsignup2;
	
	@FindBy(css="button[id*='createAccount']")
	private WebElement butsignup3;
	
    @FindBy(id="createAccount")
    private WebElement butCreateAccount;
	
	@FindBy(linkText="Home")
	private WebElement lnkHome;
	
	@FindBy(linkText="Forgot Your User ID?")
	private WebElement forgotUserId;
	
	@FindBy(linkText="Forgot Your Password?")
	private WebElement forgotUserPwd;
		
	
	public PortalLoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method will Open the Application
	 * @param driver
	 * @param baseURL
	 */
	public PortalLoginPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		String sanitizedUrl = baseURL.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Initiates forgot user ID workflow
	 * @return the forgot user id page
	 */
	public ForgotUserIdEnterEmailPage forgotUserId() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		forgotUserId.click();
		return PageFactory.initElements(driver, ForgotUserIdEnterEmailPage.class);
	}
	
	/** login the user into the patient portal
	 * 
	 * @param sUsername
	 * @param sPassword
	 * @return
	 * @throws InterruptedException 
	 */
	public MyPatientPage login( String sUsername, String sPassword ) throws InterruptedException {	
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		log( "Patient Login Credentials: [" + sUsername + "] [" + sPassword + "]" );	
		log("Waiting for Username element, max wait time is 60 seconds");
		IHGUtil.waitForElement(driver, 60, username);
		username.sendKeys( sUsername );
		password.sendKeys( sPassword );
		login.click();
		Thread.sleep(10000);
		return PageFactory.initElements(driver, MyPatientPage.class);
	}
	
	public MyPatientPage navigateTo(WebDriver driver,String url)
	{
		driver.navigate().to(url);
		return PageFactory.initElements(driver, MyPatientPage.class);
		
	}
	
	public boolean isLoginPageLoaded() {
		IHGUtil.PrintMethodName();
		
		return IHGUtil.waitForElement(driver, 5, lnkHome);		
	}
	

	public CreateAccountPageOnBetaSite signUpToBetaSite() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
	
		if (butsignup.isDisplayed()) {
			butsignup.click();
		} else if (butsignup2.isDisplayed()) {
			butsignup2.click();
		} else if (butsignup3.isDisplayed()) {
			butsignup3.click();
		} else {
			log("Create Account or Sign Up button not found on the log in Page ");
		}
		
		return PageFactory.initElements(driver, CreateAccountPageOnBetaSite.class);
	}
	
	public ResetYourPasswordPage clickForgotYourPasswordLink() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		forgotUserPwd.click();
		return PageFactory.initElements(driver, ResetYourPasswordPage.class);
	}
	
	public CreateAccountPage signUp() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		butCreateAccount.click();
	   return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	public CreateAccountPage loadUnlockLink(String sLink) {
		driver.get(sLink);
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		return PageFactory.initElements(driver, CreateAccountPage.class);
	}
	
	/**
	 * Robot script for copying content
	 * 
	 * @throws Exception
	 */

	/*
	 * public static void closeModaldialog1() throws Exception{
	 * Thread.sleep(2000); Robot rb = new Robot();
	 * rb.keyPress(KeyEvent.VK_ENTER); rb.keyRelease(KeyEvent.VK_ENTER);
	 * Thread.sleep(2000); }
	 */

	public PortalLoginPage clickLogout(WebDriver driver, WebElement logout)
			throws InterruptedException {

		PortalUtil portalUtil = new PortalUtil(driver);
		IHGUtil.PrintMethodName();
		// IHGUtil.setDefaultFrame(driver);
		driver.switchTo().defaultContent();
		if (portalUtil.isFoundBasedOnCssSelector("a[href*='exit.cfm']", driver)) {
			System.out.println("DEBUG: LOGOUT ELEMENT FOUND.");
			// DEBUG
			driver.manage()
					.timeouts()
					.implicitlyWait(
							PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS,
							TimeUnit.SECONDS);
			logout.click();
		} else {
			// Look in frame.
			PortalUtil.setPortalFrame(driver);
			if (portalUtil.isFoundBasedOnCssSelector("a[href*='exit.cfm']", driver)) {
				System.out.println("DEBUG: LOGOUT ELEMENT FOUND.");
				// DEBUG
				driver.manage()
						.timeouts()
						.implicitlyWait(
								PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS,
								TimeUnit.SECONDS);
				logout.click();
			}
			System.out.println("### WARNING: LOGOUT ELEMENT NOT FOUND.");
		}

		PortalLoginPage homePage = PageFactory.initElements(driver,
				PortalLoginPage.class);

		System.out.println("### DELETE ALL COOKIES");
		driver.manage().deleteAllCookies();
		return homePage;
	}
}
