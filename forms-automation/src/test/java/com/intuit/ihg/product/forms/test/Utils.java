package com.intuit.ihg.product.forms.test;

import static com.intuit.ifs.csscat.core.utils.Log4jUtil.log;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
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
		// "Form submitted max. 5 minutes before not found");
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

	public static void logTestEnvironmentInfo(String testName) {
		log(testName);
		log("Environment on which Testcase is Running: " + IHGUtil.getEnvironmentType());
		log("Browser on which Testcase is Running: " + TestConfig.getBrowserType());
	}

	private static void logLogin(String url, String username, String password) {
		log("URL: " + url);
		log("username: " + username);
		log("password: " + password);
	}

}
