package com.medfusion.product.object.maps.practice.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

/**
 * @author rperkins
 * @Date 3/27/2013
 * @Description :- Page Object for Practice Portal Login Page
 * @Note :- Old Frame work this page is com.intuit.ihg.product.portal.desktop.main.beta.PortalLoginPage
 */

public class PracticeLoginPage extends BasePageObject {

		public static final String PAGE_NAME = "Practice Login Page";
		public static final String pageUrl = "https://dev3.dev.medfusion.net/secure/groupadmin/ops/index.cfm"; //TODO remove?

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
				try{
						driver.manage().window().maximize(); // known Selenium issue: org.openqa.selenium.WebDriverException: unknown error: failed to change window state to normal, current state is maximized
				}catch (WebDriverException ex){
						//just catch an exception trowed by known Selenium error to continue, it is not an issue which should fail the test
						log("Window is already maximized");
						log("Known Selenium issue: "+ ex.getMessage());
				}
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
		 * @param providerUsername the staff user id
		 * @param providerPassword the staff password
		 * @return the practice home page
		 * @throws InterruptedException
		 * @see PracticeHomePage
		 */
		public PracticeHomePage login(String providerUsername, String providerPassword) throws InterruptedException {

				IHGUtil.PrintMethodName();
				log("Practice Portal Login Credentials: " + providerUsername + " / " + providerPassword);

				log("Waiting for the Username element, max wait time is 60 seconds");
				IHGUtil.waitForElement(driver, 60, userName);
				userName.sendKeys(providerUsername);
				password.sendKeys(providerPassword);
				btnLogin.click();
				IHGUtil.printCookies(driver);
				return PageFactory.initElements(driver, PracticeHomePage.class);
		}

		/**
		 * Gives indication of whether expected elements are found on the login page
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
				log("input parameters: " + url + " env " + env);
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
