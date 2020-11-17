package com.intuit.ihg.product.forms.test;

import static com.intuit.ifs.csscat.core.utils.Log4jUtil.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intuit.ihg.common.utils.PatientFactory;
import com.medfusion.pojos.Patient;
import com.medfusion.product.patientportal2.implementedExternals.CreatePatient;
import com.medfusion.product.patientportal2.utils.PortalUtil;
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
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal1.pojo.Portal;
import com.medfusion.product.patientportal1.utils.TestcasesData;

public class Utils {

		/**
		 * @param driver
		 * @return
		 * @throws Exception
		 */
		public static HealthFormListPage loginPIAndOpenFormsList(WebDriver driver, PracticeType practiceType, PropertyFileLoader testData) throws Exception {
				return loginPI(driver, practiceType, testData).clickOnHealthForms();
		}

		public static JalapenoHomePage loginPI(WebDriver driver, PracticeType practiceType, Patient patient, PropertyFileLoader testData) {
				return loginPI(driver, practiceType, patient.getUsername(), patient.getPassword(), testData);
		}

		public static JalapenoHomePage loginPI(WebDriver driver, PracticeType practiceType, PropertyFileLoader testData) {
				String url = getPortalURL(practiceType, true, testData);
				return loginPI(driver, url, testData.getProperty("patientUsername"), testData.getProperty("patientPassword"));
		}

		public static JalapenoHomePage loginPI(WebDriver driver, PracticeType practiceType, String username, String password, PropertyFileLoader testData) {
				String url = getPortalURL(practiceType, true, testData);
				return new JalapenoLoginPage(driver, url).login(username, password);
		}

		public static JalapenoHomePage loginPI(WebDriver driver, String url, String username, String password) {
				log("Login to PI");
				logLogin(url, username, password);
				return new JalapenoLoginPage(driver, url).login(username, password);
		}

		public static Patient createPatientPI(WebDriver driver, String username, String url, PropertyFileLoader testData) throws Exception {
				Patient patient = PatientFactory.createJalapenoPatient(username, testData);
				patient = new CreatePatient().selfRegisterPatient(driver, patient, url);
				return patient;
		}

		public static JalapenoHomePage createAndLoginPatientPI(WebDriver driver, PropertyFileLoader testData, PracticeType practiceType) throws Exception {
				String url = getPortalURL(practiceType, true, testData);
				String username = PortalUtil.generateUniqueUsername(testData.getProperty("userid"), testData);

				log("Create patient");
				Patient patient = createPatientPI(driver, username, url, testData);
				logLogin(url, patient.getUsername(), patient.getPassword());

				log("Login");
				JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
				JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
				return homePage;
		}

		public static void verifyFormsDatePatientPortal(HealthFormListPage formsPage, String formName, WebDriver driver) throws Exception {
				IHGUtil.setFrame(driver, "iframe");
				Thread.sleep(8000);
				Date now = getCurrentESTTime();
				Date submittedDate = formsPage.getSubmittedDate(formName);
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

		public static int getAutomationPracticeID() throws Exception {
				return getAutomationPracticeID(PracticeType.PRIMARY);
		}

		public static int getAutomationPracticeID(PracticeType practiceType) throws Exception {
				TestcasesData data = new TestcasesData(new Portal());
				switch (practiceType) {
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

		public static String getPortalURL(PracticeType practiceType, boolean PI, PropertyFileLoader testData) {
				switch (practiceType) {
						case PRIMARY:
								if (PI)
										return testData.getProperty("portal2Url1");
								return testData.getProperty("portal1Url1");
						case SECONDARY:
								if (PI)
										return testData.getProperty("portal2Url2");
								return testData.getProperty("portal1Url2");
						default:
								throw new IllegalArgumentException("Invalid practiceType");
				}
		}
}
