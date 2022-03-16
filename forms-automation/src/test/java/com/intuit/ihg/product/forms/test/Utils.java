//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.

package com.intuit.ihg.product.forms.test;

import static com.intuit.ifs.csscat.core.utils.Log4jUtil.log;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.EncryptionUtils;
import com.intuit.ihg.common.utils.PatientFactory;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.util.CreatePatient;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class Utils {

	public static HealthFormListPage loginPIAndOpenFormsList(WebDriver driver, PracticeType practiceType,
			PropertyFileLoader testData) throws Exception {
		return loginPI(driver, practiceType, testData).clickOnHealthForms();
	}

	public static JalapenoHomePage loginPI(WebDriver driver, PracticeType practiceType, Patient patient,
			PropertyFileLoader testData) {
		return loginPI(driver, practiceType, patient.getUsername(), patient.getPassword(), testData);
	}

	public static JalapenoHomePage loginPI(WebDriver driver, PracticeType practiceType, PropertyFileLoader testData) {
		String url = getPortalURL(practiceType, true, testData);
		return loginPI(driver, url, testData.getProperty("patient.username"), EncryptionUtils.decrypt(testData.getProperty("patient.password")));
	}

	public static JalapenoHomePage loginPI(WebDriver driver, PracticeType practiceType, String username,
			String password, PropertyFileLoader testData) {
		String url = getPortalURL(practiceType, true, testData);
		return new JalapenoLoginPage(driver, url).login(username, password);
	}

	public static JalapenoHomePage loginPI(WebDriver driver, String url, String username, String password) {
		log("Login to PI");
		logLogin(url, username, password);
		return new JalapenoLoginPage(driver, url).login(username, password);
	}

	public static Patient createPatientPI(WebDriver driver, String username, String url, PropertyFileLoader testData)
			throws Exception {
		Patient patient = PatientFactory.createJalapenoPatient(username, testData);
		patient = new CreatePatient().selfRegisterPatient(driver, patient, url);
		return patient;
	}

	public static JalapenoHomePage createAndLoginPatientPI(WebDriver driver, PropertyFileLoader testData,
			PracticeType practiceType) throws Exception {
		String url = getPortalURL(practiceType, true, testData);
		String username = PortalUtil2.generateUniqueUsername(testData.getProperty("user.id"), testData);

		log("Create patient");
		Patient patient = createPatientPI(driver, username, url, testData);
		logLogin(url, patient.getUsername(), patient.getPassword());

		log("Login");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(patient.getUsername(), patient.getPassword());
		return homePage;
	}

	public static void verifyFormsDatePatientPortal(HealthFormListPage formsPage, String formName, WebDriver driver)
			throws Exception {
		IHGUtil.setFrame(driver, "iframe");
		Thread.sleep(8000);
		Date now = getCurrentESTTime();
		Date submittedDate = formsPage.getSubmittedDate(formName);
		log("Date from web: " + submittedDate);
		log("Current US date: " + now);
		// date on web is max. 5 min after submit date
		assertTrue(submittedDate.getTime() > (now.getTime() - 1000 * 60 * 5));
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
			pdfLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(linkText)))
					.getAttribute("href");
		} catch (StaleElementReferenceException e) {
			pdfLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(linkText)))
					.getAttribute("href");
		} catch (NoSuchElementException f) {
			log("PDF not found!");
			throw f;
		}
		assertEquals(status.getDownloadStatusCode(pdfLink, RequestMethod.GET), 200);
	}

	public static int getAutomationPracticeID(PracticeType practiceType) throws Exception {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		switch (practiceType) {
		case PRIMARY:
			return getPracticeIDFromPIUrl(propertyData.getProperty("forms.url"));
		case SECONDARY:
			log(propertyData.getProperty("forms.secondary.url"));
			return getPracticeIDFromPIUrl(propertyData.getProperty("forms.secondary.url"));
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
				return testData.getProperty("portal2.url1");
			return testData.getProperty("portal1.url1");
		case SECONDARY:
			if (PI)
				return testData.getProperty("portal2.url2");
			return testData.getProperty("portal1.url2");
		default:
			throw new IllegalArgumentException("Invalid practiceType");
		}
	}
}
