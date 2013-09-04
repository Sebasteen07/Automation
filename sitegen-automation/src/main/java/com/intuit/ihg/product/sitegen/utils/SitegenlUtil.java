package com.intuit.ihg.product.sitegen.utils;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.page.SiteGenLoginPage;

/**
 * @author bkrishnankutty
 * @Date 6/10/2013
 * @Description :- Util class for the project site gen
 * @Note :
 */
public class SitegenlUtil extends IHGUtil {


	protected WebDriver driver;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public SitegenlUtil(WebDriver driver) {
		super(driver);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Get the driver
	 * @return WebDriver
	 * @param driver
	 */
	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Set the Site Gen Frame
	 * @return void
	 * @param driver
	 */
	public static void setSiteGenFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframebody");
	}
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- set Default Frame
	 * @return void
	 * @param driver
	 */
	public static void setDefaultFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setDefaultFrame(driver);
	}
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- For setting generic frame
	 * @return void
	 * @param driver
	 * @param frame
	 */
	public static void setFrame(WebDriver driver, String frame) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, frame);
	}
	/**
	 * @author bkrishnankutty
	 * @Desc:- For setting Consolidated frame ,Here 2 frames iframebody & externalframe
	 * @return void
	 * @param driver
	 * @param frame
	 */
	public static void setConsolidatedInboxFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();

		List<String> frames = new ArrayList<String>();
		frames.add("iframebody");
		frames.add("externalframe");

		IHGUtil.setFrameChain(driver, frames);
	}
	
	 /**
	    * @author bkrishnankutty
	    * @Desc:- Generic method for logging out from site gen
		* @return SiteGenLoginPage
	    * @param driver
	    * @param logout
	    * @return
	    * @throws InterruptedException
	    */
		public SiteGenLoginPage clickLogout(WebDriver driver, WebElement logout)
				throws InterruptedException {

			IHGUtil.PrintMethodName();
			IHGUtil util =new IHGUtil(driver);
			driver.switchTo().defaultContent();
			if (util.isRendered(logout)) {
				System.out.println("DEBUG: LOGOUT ELEMENT FOUND.");
				driver.manage()
						.timeouts()
						.implicitlyWait(
								SitegenConstants.SELENIUM_IMPLICIT_WAIT_SECONDS,
								TimeUnit.SECONDS);
				try{
					logout.click();
				} catch (Exception e) {
					Actions ac = new Actions(driver);
					ac.clickAndHold(logout).build().perform();
					log("Clicked on logout");
				}
				
			} 
			SiteGenLoginPage homePage = PageFactory.initElements(driver,
					SiteGenLoginPage.class);
			System.out.println("### DELETE ALL COOKIES");
			driver.manage().deleteAllCookies();
			return homePage;
		}
		
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- Verify text ,Note :- this fun is already present in IFS but cannot be used pages,So redefining it
	 * @return true or false
	 * 
	 * @param driver
	 * @param value
	 * @param waitTime
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyTextPresent(WebDriver driver, String value, int waitTime) throws Exception {
		Thread.sleep(waitTime);
		return driver.getPageSource().contains(value);
	}
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- for dealing with browser alerts
	 * @return void
	 * @param driver
	 */
	public void checkAlert(WebDriver driver) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 5);
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        log("Alert detected: {}" + alert.getText());
	        alert.accept();
	        
	    } catch (Exception e) {
	        //exception handling
	    	log("no alert was present");
	    }
	}

	/**
	 * @author :bbinisha
 	 * Description: This method switches the driver control to the print pop up window
 	 * @throws InterruptedException
 	 */
 	public void switchToNewWindow() throws InterruptedException {
 	 	Thread.sleep(2000);
 		Set<String> availableWindows = driver.getWindowHandles();
 		Object[] ls = availableWindows.toArray();
 		driver.switchTo().window((String) ls[1]);
 		Thread.sleep(2000);
 	}
 	
 	/**
 	 * @author :bbinisha
 	 * @Desc : Pressing Enter key action 
 	 * @throws Exception 
 	 * 
 	 */
 	public void pressEnterKey() throws Exception {
 		
 		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
 		rb.keyRelease(KeyEvent.VK_ENTER);
 	}
 	
 	/**
 	 * @author :bbinisha
 	 * @Desc : Pressing Tab key action 
 	 * @throws AWTException 
 	 * @throws InterruptedException 
 	 * 
 	 */
 	public void pressTabKey() throws AWTException, InterruptedException {
 		
 		Robot rb = new Robot();
		rb.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(2000);
 		rb.keyRelease(KeyEvent.VK_TAB);
 	}
	
	
}
