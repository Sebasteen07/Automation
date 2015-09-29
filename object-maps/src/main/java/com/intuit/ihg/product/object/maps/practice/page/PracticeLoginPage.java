package com.intuit.ihg.product.object.maps.practice.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

/**
 * @author rperkins
 * @Date 3/27/2013
 * @Description :- Page Object for Practice Portal Login Page
 * @Note :- Old Frame work this page is
 *       com.intuit.ihg.product.portal.desktop.main.beta.PortalLoginPage
 */

public class PracticeLoginPage extends BasePageObject {

	public static final String PAGE_NAME = "Practice Login Page";
	public static final String pageUrl = "https://dev3.dev.medfusion.net/secure/groupadmin/ops/index.cfm";

	@FindBy(name = "username")
	private WebElement userName;

	@FindBy(name = "password")
	private WebElement password;

	@FindBy(xpath = ".//input[@value='Login']")
	private WebElement btnLogin;

	public PracticeLoginPage(WebDriver driver) {
		super(driver);
	}

	public PracticeLoginPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("URL: " + baseURL);
		driver.get(baseURL);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public PracticeLoginPage(WebDriver driver, String env, String sURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		sURL = sURL != null ? sURL : processUrlForEnv(pageUrl, env);
		log("URL " + sURL);
		driver.get(sURL);
		maxWindow();
		PageFactory.initElements(driver, this);
	}

	/**
	 * Login the user into the practice portal
	 * 
	 * @param providedUerName
	 *               the staff user id
	 * @param providedPassword
	 *               the staff password
	 * 
	 * @return the practice home page
	 * @see PracticeHomePage
	 * 
	 * @throws InterruptedException
	 */
	public PracticeHomePage login(String providedUerName, String providedPassword) throws InterruptedException {

		IHGUtil.PrintMethodName();
		log("Practice Portal Login Credentials: " + providedUerName + " / " + providedPassword);

		log("Waiting for the Username element, max wait time is 60 seconds");
		IHGUtil.waitForElement(driver, 60, userName);
		userName.sendKeys(providedUerName);
		password.sendKeys(providedPassword);
		btnLogin.click();
		IHGUtil.printCookies(driver);
		return PageFactory.initElements(driver, PracticeHomePage.class);
	}

	/**
	 * Gives indication of whether expected elements are found on the login
	 * page
	 * 
	 * @return true or false
	 */
	public boolean isLoginPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = userName.isDisplayed();
		} catch (Exception e) {
			// catch any element not found errors
		}

		return result;
	}

	public String processUrlForEnv(String url, String env) {
		log("input parameters: "+url+" env "+env);
		if (env.equalsIgnoreCase("qa3")) {
			log("qa3 match");
			url = url.replace("dev3", "qa3");
		} else if (env.equalsIgnoreCase("prod")) {
			log("prod match");
			url = url.replace("dev3.dev", "www");
		} else if (env.equalsIgnoreCase("demo")) {
			log("demo match");
			url = url.replace("dev3.dev", "demo");
		} else if (env.equalsIgnoreCase("int")) {
			log("int match");
			url = url.replace("dev3.dev", "int");
		} else if (env.equalsIgnoreCase("p10int")) {
			log("p10int match");
			url = url.replace("dev3.dev", "int");
		}
		log("no match");
		return url;
	}

}
