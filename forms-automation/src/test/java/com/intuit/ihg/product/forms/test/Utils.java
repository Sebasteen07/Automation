package com.intuit.ihg.product.forms.test;

import static com.intuit.ifs.csscat.core.utils.Log4jUtil.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal1.flows.CreatePatientTest;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.TestcasesData;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;
import com.medfusion.product.patientportal2.tests.CommonSteps;

public class Utils {

	public static String newPatientDOBYear = "1900";

	/**
	 * 
	 * @param driver
	 * @param persistentFormsPractice False for tests which do sitegen management, true otherwise. Tests which do sitegen management operates on own practice
	 *        because they can damage forms.
	 * @return
	 * @throws Exception
	 */
	public static HealthFormListPage loginPIAndOpenFormsList(WebDriver driver) throws Exception {
		return loginPI(driver).clickOnHealthForms();
	}

	public static HealthFormListPage loginPortal1AndOpenFormsList(WebDriver driver) throws Exception {
		return loginPortal1(driver).clickOnHealthForms();
	}

	public static JalapenoHomePage loginPI(WebDriver driver) throws Exception {
		TestcasesData portalData = new TestcasesData(new Portal());
		String url = portalData.getFormsPIUrlPrimary();
		log("Login to PI");
		logLogin(url, portalData.getUsername(), portalData.getPassword());
		return new JalapenoLoginPage(driver, url).login(portalData.getUsername(), portalData.getPassword());
	}

	public static MyPatientPage loginPortal1(WebDriver driver) throws Exception {
		TestcasesData portalData = new TestcasesData(new Portal());
		String url = portalData.getFormsPortal1UrlPrimary();
		log("Login to Portal 1");
		logLogin(url, portalData.getUsername(), portalData.getPassword());
		return new PortalLoginPage(driver, url).login(portalData.getUsername(), portalData.getPassword());
	}

	public static JalapenoHomePage loginPI(WebDriver driver, String userName, String password) throws Exception {
		String url = new TestcasesData(new Portal()).getFormsPIUrlPrimary();
		log("Login to PI");
		logLogin(url, userName, password);
		return new JalapenoLoginPage(driver, url).login(userName, password);
	}

	public static MyPatientPage loginPortal1(WebDriver driver, String userName, String password) throws Exception {
		String url = new TestcasesData(new Portal()).getFormsPortal1UrlPrimary();
		log("Login to Portal 1");
		logLogin(url, userName, password);
		return new PortalLoginPage(driver, url).login(userName, password);
	}

	public static JalapenoHomePage createAndLoginPatientPI(WebDriver driver, PatientData p) throws Exception {
		return createAndLoginPatientPI(driver, PracticeType.PRIMARY, p);
	}

	public static MyPatientPage createAndLoginPatientPortal1(WebDriver driver, PatientData p) throws Exception {
		return createAndLoginPatientPortal1(driver, PracticeType.PRIMARY, p);
	}

	public static MyPatientPage createAndLoginPatientPortal1(WebDriver driver, PracticeType practiceType, PatientData p) throws Exception {
		String url = getPortalURL(practiceType, false);
		CreatePatientTest patientCreation = new CreatePatientTest(null, null, url);
		MyPatientPage home = patientCreation.createPatient(driver, new com.medfusion.product.patientportal1.utils.TestcasesData(new com.medfusion.product.patientportal1.pojo.Portal()));
		logLogin(url, patientCreation.getEmail(), patientCreation.getPassword());
		p.setDob(patientCreation.getDob());
		p.setEmail(patientCreation.getEmail());
		p.setFirstName(patientCreation.getFirstName());
		p.setLastName(patientCreation.getLastName());
		p.setPassword(patientCreation.getPassword());
		p.setUrl(patientCreation.getUrl());
		return home;
	}

	public static JalapenoHomePage createAndLoginPatientPI(WebDriver driver, PracticeType practiceType, PatientData p) throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();
		JalapenoPatient jp = new JalapenoPatient(testData);
		String url = getPortalURL(practiceType, true);
		jp.setUrl(url);
		jp.setDOBYear(newPatientDOBYear);
		JalapenoHomePage home = CommonSteps.createAndLogInPatient(jp, testData, driver);
		String patientDOB = jp.getDOBMonthText() + "/" + jp.getDOBDay() + "/" + jp.getDOBYear();
		logLogin(jp.getUrl(), jp.getEmail(), jp.getPassword());
		p.setDob(patientDOB);
		p.setEmail(jp.getEmail());
		p.setFirstName(jp.getFirstName());
		p.setLastName(jp.getLastName());
		p.setPassword(jp.getPassword());
		p.setUrl(jp.getUrl());
		return home;
	}

	public static void verifyFormsDatePatientPortal(HealthFormListPage formsPage, String formName, WebDriver driver) throws Exception {
		IHGUtil.setFrame(driver, "iframe");
		Thread.sleep(8000);
		Date submittedDate = formsPage.getSubmittedDate(formName);
		Date now = getCurrentESTTime();
		log("Date from web: " + submittedDate);
		log("Current US date: " + now);
		// date on web is max. 5 min after submit date
		Assert.assertTrue(submittedDate.getTime() > (now.getTime() - 1000 * 60 * 5));
	}

	public static Date getCurrentESTTime() throws ParseException {
		SimpleDateFormat ESTdate = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
		ESTdate.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
		return parser.parse(ESTdate.format(new Date()));
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

	public static int getAutomationPracticeID() throws Exception {
		return getAutomationPracticeID(PracticeType.PRIMARY);
	}

	public static int getAutomationPracticeID(PracticeType practiceType) throws Exception {
		TestcasesData data = new TestcasesData(new Portal());
		switch(practiceType){
			case PRIMARY:
				return getPracticeIDFromPIUrl(data.getFormsPIUrlPrimary());
			case SECONDARY:
				return getPracticeIDFromPIUrl(data.getFormsPIUrlSecondary());
			default:
				throw new IllegalArgumentException("unknown practice type");
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

	private static String getPortalURL(PracticeType practiceType, boolean PI) throws Exception {
		TestcasesData data = new TestcasesData(new Portal());
		switch (practiceType) {
			case PRIMARY:
				if (PI)
					return data.getFormsPIUrlPrimary();
				return data.getFormsPortal1UrlPrimary();
			case SECONDARY:
				if (PI)
					return data.getFormsPIUrlSecondary();
				return data.getFormsPortal1UrlSecondary();
			default:
				throw new IllegalArgumentException("ïnvalid practiceType");
		}
	}

}
