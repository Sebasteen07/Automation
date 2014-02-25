package com.intuit.ihg.product.sitegen.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.page.home.SiteGenHomePage;

/**
 * @author bkrishnankutty
 * @Date 6/10/2013
 * @Description :- Page Object for SiteGen Login Page
 * @Note :
 */

public class SiteGenLoginPage extends BasePageObject {

	@FindBy(how = How.NAME, using = "btn_login")
	private WebElement login;

	@FindBy(how = How.ID, using = "password")
	private WebElement txtpassword;

	@FindBy(how = How.ID, using = "userid")
	private WebElement txtusername;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public SiteGenLoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author bkrishnankutty
	 * @Desc :- Open the Site gen Log In Page
	 * @param driver
	 * @param baseURL
	 */
	public SiteGenLoginPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("URL: " + baseURL);
		driver.get(baseURL);
		maxWindow();
		PageFactory.initElements(driver, this);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc :-Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = login.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- login into the sitegen portal
	 * @param username
	 * @param password
	 * @return
	 * @throws InterruptedException
	 */

	public SiteGenHomePage login(String username, String password)
			throws InterruptedException {

		IHGUtil.PrintMethodName();
		System.out.println("### DEBUG LOGIN: [" + username + "] [" + password
				+ "]");
		txtusername.sendKeys(username);
		txtpassword.sendKeys(password);
		login.click();
		Thread.sleep(5000);
		return PageFactory.initElements(driver, SiteGenHomePage.class);
	}

}
