package com.medfusion.product.patientportal2.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ihg.common.utils.WebPoster;
import com.medfusion.common.utils.IHGUtil;

public class PortalUtil extends IHGUtil {

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screenResolution = new Dimension((int) toolkit.getScreenSize().getWidth(), (int) toolkit.getScreenSize().getHeight());

	Dimension halfWidthscreenResolution = new Dimension((int) toolkit.getScreenSize().getWidth() / 2, (int) toolkit.getScreenSize().getHeight());

	protected WebDriver driver;

	// PortalConstants pPortalConstants;

	public PortalUtil(WebDriver driver) {
		super(driver);
	}

	public WebDriver getDriver(WebDriver driver) {
		IHGUtil.PrintMethodName();
		return driver;
	}

	public static void setPortalFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframebody");
	}

	public static void setFrame(WebDriver driver, String frame) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, frame);
	}

	public static void setConsolidatedInboxFrame(WebDriver driver) {
		IHGUtil.PrintMethodName();

		List<String> frames = new ArrayList<String>();
		frames.add("iframebody");
		frames.add("externalframe");

		IHGUtil.setFrameChain(driver, frames);
	}


	public boolean isFoundBasedOnCssSelector(final String cssPath, WebDriver driver) throws InterruptedException {

		return isFoundBasedOnCssSelector(cssPath, PortalConstants.FIND_ELEMENTS_MAX_WAIT_SECONDS, driver);
	}

	public boolean isFoundBasedOnCssSelector(final String cssPath, int timeOutInSeconds, WebDriver driver) throws InterruptedException {

		IHGUtil.PrintMethodName();
		System.out.println("DEBUG: Looking for cssSelector: " + cssPath);
		return isFoundBy(By.cssSelector(cssPath), timeOutInSeconds, driver);
	}

	public static boolean isFoundBy(final By by, int timeOutInSeconds, WebDriver driver) {

		IHGUtil.PrintMethodName();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Boolean found = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			found = wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					// This causes problems for some drivers - need to use
					// plural (findElements) and check size.
					// return driver.findElement(by) != null;
					List<WebElement> list = driver.findElements(by);
					if (list != null) {
						if (list.size() > 0)
							return true;
					}
					return false;
				}
			});

		} catch (TimeoutException ex) {

			System.out.println("### element not found: " + ex.getMessage());
		}

		// Restore implicit wait.
		driver.manage().timeouts().implicitlyWait(PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
		return found;
	}


	public static void acceptAlert(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public static boolean isExistsElement(WebDriver driver, WebElement element) {
		try {
			Actions builder = new Actions(driver);
			builder.moveToElement(element).build().perform();
			Point p = element.getLocation();
			System.out.println("Where on the page is the top left-hand corner of the rendered element" + p);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public static void setquestionnarieFrame(WebDriver pDriver) throws Exception {
		IHGUtil.PrintMethodName();
		pDriver.switchTo().defaultContent();
		try {
			pDriver.switchTo().frame(pDriver.findElement(By.xpath("//div[@id='lightbox']/iframe[@title='Forms']")));
		} catch (StaleElementReferenceException e) {
			System.out.println("Stale element exception caught as expected. Frame should be correctly switched");
		}
	}
	
	/**
	 * An exception thrown means the format was wrong, mfss-user is down, or other unrecoverable issue
	 * @param username
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static boolean checkUsernameEmailIsUnique(String username, String url) throws Exception{
		IHGUtil.PrintMethodName();
		WebPoster poster = new WebPoster();
		System.out.println("Calling patient matcher for username =\"" + username + "\", url= \"" + url + "\"");				
		poster.setServiceUrl(url);
		poster.setContentType("application/json;");			
		poster.setExpectedStatusCode(200);
		return poster.postFromString("{\"emailOrUserName\":\"" + username + "\"}").contains("NO_MATCH");			
	}

}
