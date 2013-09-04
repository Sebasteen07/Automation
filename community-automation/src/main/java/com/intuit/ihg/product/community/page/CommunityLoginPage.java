package com.intuit.ihg.product.community.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.community.page.CreateAnAccount.CreateAnAccountHandleIframe;

public class CommunityLoginPage extends BasePageObject {

	public static final String PAGE_NAME = "Community Login Page";
	// Page Objects

	@FindBy(how = How.ID, using = "user_id")
	private WebElement User_ID;

	@FindBy(how = How.ID, using = "password")
	private WebElement User_Password;

	@FindBy(how = How.ID, using = "signin_btn")
	public WebElement btn_Sign_In;

	// Cant single quote is skipped due the Xpath single quote escape issue
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'t access your account?')]")
	public WebElement link_Cant_Acces_Your_Account;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Sign up now')]")
	public WebElement btn_Sign_Up_Now;

	public CommunityLoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method will Open the Application
	 * 
	 * @param driver
	 * @param baseURL
	 */

	public CommunityLoginPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		String sanitizedUrl = baseURL.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public CommunityHomePage LoginToCommunity(String Suserid, String Spassword)
			throws InterruptedException {

		User_ID.sendKeys(Suserid);
		User_Password.sendKeys(Spassword);
		btn_Sign_In.click();
		return PageFactory.initElements(driver, CommunityHomePage.class);
	}

	public boolean validatePageElements() {

		if (User_ID.isDisplayed() && User_Password.isDisplayed()
				&& btn_Sign_In.isDisplayed()) {
			return true;
		}
		return false;
	}

	public CreateAnAccountHandleIframe clickSignupNow()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath("//a[contains(text(),'Sign up now')]"))
				.click();
		return PageFactory.initElements(driver,
				CreateAnAccountHandleIframe.class);

	}
}
