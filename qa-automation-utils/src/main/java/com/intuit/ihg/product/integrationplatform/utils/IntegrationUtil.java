package com.intuit.ihg.product.integrationplatform.utils;


import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.intuit.ihg.common.utils.IHGUtil;


/**
 * @author bkrishnankutty
 * @Date 5/Aug/2013
 * @Description :-
 * @Note :
 */
public class IntegrationUtil extends IHGUtil {


	protected WebDriver driver;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public IntegrationUtil(WebDriver driver) {
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

	
	
}
