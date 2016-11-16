package com.intuit.ihg.product.forms.test;

import static com.intuit.ifs.csscat.core.utils.Log4jUtil.log;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.portal.utils.Portal;
import com.medfusion.portal.utils.TestcasesData;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class Utils {

	/**
	 * 
	 * @param driver
	 * @param persistentFormsPractice False for tests which do sitegen management, true otherwise. Tests which do sitegen management operates on own practice
	 *        because they can damage forms.
	 * @return
	 * @throws Exception
	 */
	public static HealthFormListPage loginPIAndOpenFormsList(WebDriver driver, boolean persistentFormsPractice) throws Exception {
		return loginPI(driver, persistentFormsPractice).clickOnHealthForms();
	}

	public static HealthFormListPage loginPortal1AndOpenFormsList(WebDriver driver, boolean persistentFormsPractice) throws Exception {
		return loginPortal1(driver, persistentFormsPractice).clickOnHealthForms();
	}

	public static JalapenoHomePage loginPI(WebDriver driver, boolean persistentFormsPractice) throws Exception {
		String url;
		TestcasesData portalData = new TestcasesData(new Portal());
		if (persistentFormsPractice) {
			url = portalData.getPIFormsAltUrl();
		} else {
			url = portalData.getPIFormsUrl();
		}
		log("Login to PI");
		logLogin(url, portalData.getUsername(), portalData.getPassword());
		return new JalapenoLoginPage(driver, url).login(portalData.getUsername(), portalData.getPassword());
	}

	public static MyPatientPage loginPortal1(WebDriver driver, boolean persistentFormsPractice) throws Exception {
		String url;
		TestcasesData portalData = new TestcasesData(new Portal());
		if (persistentFormsPractice) {
			url = portalData.getFormsAltUrl();
		} else {
			url = portalData.getFormsUrl();
		}
		log("Login to Portal 1");
		logLogin(url, portalData.getUsername(), portalData.getPassword());
		return new PortalLoginPage(driver, url).login(portalData.getUsername(), portalData.getPassword());
	}

	public static JalapenoHomePage loginPI(WebDriver driver, boolean persistentFormsPractice, String userName, String password) throws Exception {
		String url;
		TestcasesData portalData = new TestcasesData(new Portal());
		if (persistentFormsPractice) {
			url = portalData.getPIFormsAltUrl();
		} else {
			url = portalData.getPIFormsUrl();
		}
		log("Login to PI");
		logLogin(url, userName, password);
		return new JalapenoLoginPage(driver, url).login(userName, password);
	}

	public static MyPatientPage loginPortal1(WebDriver driver, boolean persistentFormsPractice, String userName, String password) throws Exception {
		String url;
		TestcasesData portalData = new TestcasesData(new Portal());
		if (persistentFormsPractice) {
			url = portalData.getFormsAltUrl();
		} else {
			url = portalData.getFormsUrl();
		}
		log("Login to Portal 1");
		logLogin(url, userName, password);
		return new PortalLoginPage(driver, url).login(userName, password);
	}



	public static void verifyFormsDatePatientPortal(HealthFormListPage formsPage, String formName, WebDriver driver) throws Exception {
		IHGUtil.setFrame(driver, "iframe");
		Thread.sleep(8000);
		Date submittedDate = formsPage.getSubmittedDate(formName);
		Date now = getCurrentTimeGMT(-4);
		log("Date from web: " + submittedDate);
		log("Current US date: " + now);
		// date on web is max. 5 min after submit date
		Assert.assertTrue(submittedDate.getTime() > (now.getTime() - 1000 * 60 * 5));
	}

	public static Date getCurrentTimeGMT(int timeZoneShift) {
		Calendar c = Calendar.getInstance();
		TimeZone z = c.getTimeZone();
		int offset = z.getRawOffset();
		if (z.inDaylightTime(new Date())) {
			offset = offset + z.getDSTSavings();
		}
		int offsetHrs = offset / 1000 / 60 / 60;
		int offsetMins = offset / 1000 / 60 % 60;
		c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
		c.add(Calendar.MINUTE, (-offsetMins));
		return new Date(c.getTime().getTime() + 1000 * 3600 * timeZoneShift);
	}

	public static void checkIfPDFCanBeDownloaded(String linkText, WebDriver driver) throws Exception {
		URLStatusChecker status = new URLStatusChecker(driver);
		String pdfLink;
		WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
		try {
			pdfLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(linkText))).getAttribute("href");
		} catch (StaleElementReferenceException e) {
			pdfLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(linkText))).getAttribute("href");
		} catch (NoSuchElementException f) {
			log("PDF not found!");
			throw f;
		}
		Assert.assertEquals(status.getDownloadStatusCode(pdfLink, RequestMethod.GET), 200);
	}

	/**
	 * Initializes and returns Firefox specific driver which allows downloading All downloaded files will be present directly in working directory (usually the
	 * root directory of project).
	 * 
	 * @return Firefox specific driver which allows downloading
	 */
	public static WebDriver getFirefoxDriverForDownloading() {
		FirefoxProfile fxProfile = new FirefoxProfile();
		fxProfile.setPreference("browser.download.folderList", 2);
		fxProfile.setPreference("browser.download.manager.showWhenStarting", false);
		fxProfile.setPreference("browser.download.dir", System.getProperty("user.dir"));
		fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain");
		FirefoxDriver driver = new FirefoxDriver(fxProfile);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}

	public static void logTestEnvironmentInfo(String testName) {
		log("Test name: " + testName);
		log("Environment on which Testcase is Running: " + IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: " + TestConfig.getBrowserType());
	}

	public static int getAutomationPracticeID(boolean persistentFormsPractice) throws Exception {
		TestcasesData portalData = new TestcasesData(new Portal());
		if (persistentFormsPractice) {
			return getPracticeIDFromPIUrl(portalData.getPIFormsAltUrl());
		} else {
			return getPracticeIDFromPIUrl(portalData.getPIFormsUrl());
		}
	}

	public static int getPracticeIDFromPIUrl(String PIurl) {
		Pattern p = Pattern.compile(".+-(\\d+)/portal.+");
		Matcher m = p.matcher(PIurl);
		m.find();
		return Integer.parseInt(m.group(1));
	}

	public static void logLogin(String url, String username, String password) {
		log("URL: " + url);
		log("username: " + username);
		log("password: " + password);
	}
}
