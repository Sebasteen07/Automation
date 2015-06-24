package com.intuit.ihg.product.portal.utils;

import org.openqa.selenium.*;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.GmailBot;
//import com.intuit.ihg.product.portal.page.PortalLoginPage;

public class PortalUtil extends IHGUtil {

	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Dimension screenResolution = new Dimension((int) toolkit.getScreenSize()
			.getWidth(), (int) toolkit.getScreenSize().getHeight());

	Dimension halfWidthscreenResolution = new Dimension((int) toolkit
			.getScreenSize().getWidth() / 2, (int) toolkit.getScreenSize()
			.getHeight());

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

	public void maximize(WebDriver driver) {
		IHGUtil.PrintMethodName();
		// Not supported by Mobile driver
		driver.manage().window().setSize(screenResolution);
	}

	public boolean isFoundBasedOnCssSelector(final String cssPath,
			WebDriver driver) throws InterruptedException {

		return isFoundBasedOnCssSelector(cssPath,
				PortalConstants.FIND_ELEMENTS_MAX_WAIT_SECONDS, driver);
	}

	public boolean isFoundBasedOnCssSelector(final String cssPath,
			int timeOutInSeconds, WebDriver driver) throws InterruptedException {

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
		driver.manage()
				.timeouts()
				.implicitlyWait(PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS,
						TimeUnit.SECONDS);
		return found;
	}

	public static String createRandomEmailAddress(String email) {

		IHGUtil.PrintMethodName();

		Random randomNumbers = new Random();

		int rnd = randomNumbers.nextInt(999999999);

		String[] tmp = email.split("@");

		System.out.println("dynamic Email address" + tmp[0] + rnd + "@"
				+ tmp[1]);

		return tmp[0] + "+" + rnd + "@" + tmp[1];
	}

	public static int createRandomNumber() {

		IHGUtil.PrintMethodName();

		Random randomNumbers = new Random();

		int rnd = randomNumbers.nextInt(999999999);

		return rnd;
	}

	public static void acceptAlert(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		alert.accept();
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

//	public PortalLoginPage clickLogout(WebDriver driver, WebElement logout)
//			throws InterruptedException {
//
//		IHGUtil.PrintMethodName();
//		// IHGUtil.setDefaultFrame(driver);
//		driver.switchTo().defaultContent();
//		if (this.isFoundBasedOnCssSelector("a[href*='exit.cfm']", driver)) {
//			System.out.println("DEBUG: LOGOUT ELEMENT FOUND.");
//			// DEBUG
//			driver.manage()
//					.timeouts()
//					.implicitlyWait(
//							PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS,
//							TimeUnit.SECONDS);
//			logout.click();
//		} else {
//			// Look in frame.
//			PortalUtil.setPortalFrame(driver);
//			if (this.isFoundBasedOnCssSelector("a[href*='exit.cfm']", driver)) {
//				System.out.println("DEBUG: LOGOUT ELEMENT FOUND.");
//				// DEBUG
//				driver.manage()
//						.timeouts()
//						.implicitlyWait(
//								PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS,
//								TimeUnit.SECONDS);
//				logout.click();
//			}
//			System.out.println("### WARNING: LOGOUT ELEMENT NOT FOUND.");
//		}
//
//		PortalLoginPage homePage = PageFactory.initElements(driver,
//				PortalLoginPage.class);
//
//		System.out.println("### DELETE ALL COOKIES");
//		driver.manage().deleteAllCookies();
//		return homePage;
//	}

	/**
	 *
	 * Will return the URL to reset your password
	 *
	 *
	 * @param userId
	 * @param password
	 * @param sSubject
	 * @param sEmailLinkContains
	 * @return
	 * @throws Exception
	 */

	public String gmailVerification(String userId, String password,
			String sSubject, String sEmailLinkContains) throws Exception {

		GmailBot gbot = new GmailBot();
		log("subject of mail is " + sSubject);
		String sURL = gbot.findInboxEmailLink(userId, password, sSubject,
				sEmailLinkContains, 20, false, false);
		if (sURL.length() == 0) {
			log("### WARNING: Couldn't get URL from email");
			Assert.fail("NO email found in the trash");
			return null;
		}
		return sURL;
	}

	public void emailMessageRemover(String userId, String password,
			String sSubject) throws Exception {

		GmailBot gbot = new GmailBot();
		log("subject of mail is " + sSubject);
		gbot.deleteMessagesFromInbox(userId, password, sSubject);

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


	public static String createRandomNumericString() {

		PortalUtil.PrintMethodName();

		Random randomNumbers = new Random();

		int rnd = randomNumbers.nextInt(999999999);

		return ( "" + rnd );
	}

    public static String createRandomNumericString(int length){
    	PortalUtil.PrintMethodName();

        String nums = createRandomNumericString();
        if (length > nums.length()){
            System.out.println("### Requested random number string length ("+length+
                    ") is larger than what is generated ("+nums.length()+")");
            return nums;
        } else {
            return nums.substring(0,length);
        }
    }

	/**
	 * REMOVE ALL EMAILS FROM INBOX
	 * @param userId
	 * @param password
	 * @param sSubject
	 * @throws Exception
	 */
	public void cleanInbox(String userId, String password)
			throws Exception {
		GmailBot gbot = new GmailBot();
		gbot.deleteAllMessagesFromInbox(userId, password);

	}

}
