package com.intuit.ihg.product.phr.page;

import static org.testng.AssertJUnit.assertFalse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;


/** @author bkrishnankutty
 *  @Date 2-12-2013
 *  @Description :- Page Object for PHR Portal LoginPage
 *  
 */

public class PhrLoginPage extends BasePageObject {
	
	public static final String PAGE_NAME = "PHR Portal Login Page";
	
	@FindBy(xpath="//font[@id='error']")
	private WebElement loginError;
	
	@FindBy(xpath="//input[@name='loginName']")
	private WebElement txtloginName;
	
	@FindBy(xpath="//input[@name='password']")
	private WebElement txtpassword;
	
	// TODO - Not the best option for mapping.
	@FindBy(xpath="(//a)[1]")
	private WebElement btnLogin;

	
	public PhrLoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Type username
	 * @param sLogin
	 */
	public void setLoginName( String sLogin ) {
		IHGUtil.PrintMethodName();
		log( "LOGIN: [" + sLogin + "]" );
		txtloginName.sendKeys( sLogin );
	}
	
	/**
	 * Type PASSWORD
	 * @param sPassword
	 */
	public void setPassword( String sPassword ) {
		IHGUtil.PrintMethodName();
		log( "PASSWORD: [" + sPassword + "]" );
		txtpassword.sendKeys( sPassword );
	}
	
	
	
	/** login the user into the PHR portal
	 * 
	 * @param sUsername
	 * @param sPassword
	 * @return
	 * @throws InterruptedException 
	 */
	public PhrHomePage login(String sUser, String sPassword ) throws InterruptedException {
		
		IHGUtil.PrintMethodName();
		log("Waiting for the Login Name element, max wait time is 60 seconds");
		IHGUtil.waitForElement(driver, 60, txtloginName);
		this.setLoginName( sUser );
		this.setPassword( sPassword );
		btnLogin.click();
	    return PageFactory.initElements(driver, PhrHomePage.class);
	}
		
	
    /**
     * Loads the PHR Login Page
     * @param driver
     * @param baseURL
     */
	
	public PhrLoginPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		String sanitizedUrl = baseURL.trim();
		log("baseURL: " + baseURL);
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		maxWindow();
		// See if SSL setup properly
	   assertFalse( "### PHR SSL CERT ISSUE (Untrusted Connection) ?",driver.getTitle().contains("Untrusted Connection") );
		PageFactory.initElements(driver, this);
	}
	

	/**
	 * Verify if the password Test field is  presents on page or not
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 */
	
	public boolean waitforTXTPassword(WebDriver driver,int n) throws InterruptedException
	{   
		IHGUtil.PrintMethodName();
		return IHGUtil.waitForElement(driver,n, txtpassword);
	}
	
	
	/**
	 * @author bkrishnankutty
	 * @Desc:-Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 60, txtpassword);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
}
