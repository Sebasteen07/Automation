package com.intuit.ihg.product.integrationplatform.page;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;


import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *  
 * @author Vasudeo
 * 
 */

public class PatientPage  extends BasePageObject{
	
		public static final String PAGE_NAME = "Patient Page";
	 
		@FindBy(css="a[href*='exit.cfm']")
		private WebElement logout;
		
		@FindBy(xpath = "//a[@title='Prescription Renewal']")
	    private WebElement lnkPrescriptionRenewal;
		
		@FindBy(xpath = "//div[@id='iframecontent']/div/h2")
	    public WebElement txtPatientPage;
				
	    PortalUtil pPortalUtil=new PortalUtil(driver);
	    
		public PatientPage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
		}
		
	    public LoginPage logout(WebDriver driver) throws InterruptedException,IOException {
			
			IHGUtil.PrintMethodName();
			pPortalUtil.maximize(driver);
			clickLogout(driver);
	        // TODO - deal with random survey
	        return PageFactory.initElements(driver, LoginPage.class);
	       }
		
	
	public LoginPage clickLogout(WebDriver driver) throws InterruptedException {
		
		IHGUtil.PrintMethodName();
		//IHGUtil.setDefaultFrame(driver);
		driver.switchTo().defaultContent();
		if(pPortalUtil.isFoundBasedOnCssSelector( "a[href*='exit.cfm']",driver )) {
			System.out.println( "DEBUG: LOGOUT ELEMENT FOUND." );
			// DEBUG
			driver.manage().timeouts().implicitlyWait( PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
			logout.click();
		} else {
			// Look in frame.
			 PortalUtil.setPortalFrame(driver);
			if( pPortalUtil.isFoundBasedOnCssSelector( "a[href*='exit.cfm']",driver) ) {
				System.out.println( "DEBUG: LOGOUT ELEMENT FOUND." );
				// DEBUG
				driver.manage().timeouts().implicitlyWait( PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
				logout.click();
				}
			System.out.println( "### WARNING: LOGOUT ELEMENT NOT FOUND." );
		}
		
		LoginPage homePage = PageFactory.initElements(driver, LoginPage.class);
		
		System.out.println( "### DELETE ALL COOKIES" );
		driver.manage().deleteAllCookies();
		return homePage;
	}
				
	/**
	 * Click on Prescription Renewal link and redirect to page Rx Renewal Page
	 * @return RxRenewalPage
	 */
	 public RxRenewalPage clickPrescriptionRenewal() {
		 	IHGUtil.PrintMethodName();
			PortalUtil.setPortalFrame(driver);
			lnkPrescriptionRenewal.click();
			return PageFactory.initElements(driver, RxRenewalPage.class);
	    }
	

				
}
