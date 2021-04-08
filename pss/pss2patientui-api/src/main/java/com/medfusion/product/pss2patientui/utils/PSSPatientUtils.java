// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientui.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous.AnonymousPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.PatientIdentificationPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePageSpeciality;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PrivacyPolicy;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.Appointment.Speciality.Speciality;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Insurance.UpdateInsurancePage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointmentAnonymous;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;

import net.fortuna.ical4j.data.ParserException;

public class PSSPatientUtils {

	public void readICSFile(String path) throws IOException, ParserException {
		Log4jUtil.log("Reading ICS file for the given path. " + path);
		ICSFileReader icsfilereader = new ICSFileReader();
		icsfilereader.ICSFile(path);
		Log4jUtil.log("Is file deleted ? " + deleteFile(path));
	}

	public String filePath() {
		String home = System.getProperty("user.home");
		File latestFile = lastFileModified(home + PSSConstants.DOWNLOADFILENAME);
		Log4jUtil.log("latestFile " + latestFile.getAbsolutePath());
		return latestFile.getAbsolutePath();
	}

	public void LBTFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("LBTFlow");
		Log4jUtil.log("Step 8: Select location for appointment.");
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startPage is Visible " + testData.isIsstartpointPresent());

		if (testData.isIsinsuranceVisible()) {
			Thread.sleep(3500);
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			startappointmentInOrder = homepage.skipInsurance(driver);
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_LOCATION);
			} else {
				location = homepage.locationpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}

		} else if (testData.isIsstartpointPresent()) {
			startappointmentInOrder = homepage.startpage();
			Log4jUtil.log("in else part  click on  " + PSSConstants.START_LOCATION);
			location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
			Log4jUtil.log("clicked on location ");
		} else {
			Log4jUtil.log("Start point not present");
			location = homepage.locationpage();
		}

		Log4jUtil.log("Step 9: Verfiy location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		Provider provider = location.searchProvider(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Provider Page and provider to be selected = " + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentPage appointment = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 11: Verfiy Appointment Page and Appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.gettypeOfAppointment(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 12: Select avaiable Date ");

		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");
	}

	public void BLTFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startpage is Visible " + testData.isIsstartpointPresent());

		if (testData.isIsinsuranceVisible()) {
			Thread.sleep(3500);
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			startappointmentInOrder = homepage.skipInsurance(driver);
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_PROVIDER);
			} else {
				provider = homepage.providerpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}
		} else if (testData.isIsstartpointPresent()) {
			startappointmentInOrder = homepage.startpage();
			Log4jUtil.log("in else part  click on  " + PSSConstants.START_PROVIDER);
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
			Log4jUtil.log("clicked on provider ");
		} else {
			Log4jUtil.log("Start point not present");
			provider = homepage.providerpage();
		}

		Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		Location location = provider.selectLocation(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Location Page and location to be selected = " + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointmentpage = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 11: Verfiy Appointment Page and Appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointmentpage.gettypeOfAppointment(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}

	}

	public void BTLFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startpage is Visible " + testData.isIsstartpointPresent());
		if (testData.isIsinsuranceVisible()) {
			Thread.sleep(3500);
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			startappointmentInOrder = homepage.skipInsurance(driver);
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_PROVIDER);
			} else {
				provider = homepage.providerpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}
		}

		else if (testData.isIsstartpointPresent()) {
			startappointmentInOrder = homepage.startpage();
			Log4jUtil.log("in else part  click on  " + PSSConstants.START_PROVIDER);
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
			Log4jUtil.log("clicked on provider ");
		} else {
			Log4jUtil.log("Start point not present");
			provider = homepage.providerpage();
		}

		Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
		Log4jUtil.log("going to choose an provider");
		assertTrue(provider.areBasicPageElementsPresent());
		Log4jUtil.log("basic element are present");
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointmentpage.areBasicPageElementsPresent());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}
		Thread.sleep(6000);

		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
	}

	public void LTBFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Location for appointment.");
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;

		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("start is Visible " + testData.isIsstartpointPresent());

		if (testData.isIsinsuranceVisible()) {
			Thread.sleep(3500);
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			startappointmentInOrder = homepage.skipInsurance(driver);
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_LOCATION);
			} else {
				location = homepage.locationpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}

		} else if (testData.isIsstartpointPresent()) {
			startappointmentInOrder = homepage.startpage();
			Log4jUtil.log("in else part  click on  " + PSSConstants.START_LOCATION);
			location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
			Log4jUtil.log("clicked on location ");
		} else {
			Log4jUtil.log("Start point not present");
			location = homepage.locationpage();

		}
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(2000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");
	}

	public void TLBFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		AppointmentPage appointment;
		StartAppointmentInOrder startappointmentInOrder = null;
		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startpage is Visible " + testData.isIsstartpointPresent());
		if (testData.isIsinsuranceVisible()) {
			Thread.sleep(3500);
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			startappointmentInOrder = homepage.skipInsurance(driver);
			if (testData.isIsstartpointPresent()) {

				Log4jUtil.log("Starting point is present after insurance skipped ");
				appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_APPOINTMENT);
			} else {
				appointment = homepage.appointmentpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}

		}

		else if (testData.isIsstartpointPresent()) {
			startappointmentInOrder = homepage.startpage();
			Log4jUtil.log("in else part  click on  " + PSSConstants.START_APPOINTMENT);
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
			Log4jUtil.log("clicked on Appointment ");
		} else {
			Log4jUtil.log("Start point not present");
			appointment = homepage.appointmentpage();
		}
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 10: Verfiy Location Page and location to be selected = " + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		Provider provider = location.searchProvider(testData.getLocation());
		Log4jUtil.log("address = " + location.getAddressValue());
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());

		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");
	}

	public void TBLFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		AppointmentPage appointment;
		StartAppointmentInOrder startappointmentInOrder = null;
		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startPage is Visible " + testData.isIsstartpointPresent());
		if (testData.isIsinsuranceVisible()) {
			Thread.sleep(3500);
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			startappointmentInOrder = homepage.skipInsurance(driver);
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_APPOINTMENT);
			} else {
				appointment = homepage.appointmentpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}

		}

		else if (testData.isIsstartpointPresent()) {
			startappointmentInOrder = homepage.startpage();
			Log4jUtil.log("select the starting point & click on  " + PSSConstants.START_APPOINTMENT);
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
			Log4jUtil.log("clicked on Appointment ");
		} else {
			Log4jUtil.log("Start point not present");
			appointment = homepage.appointmentpage();

		}

		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Log4jUtil.log("does apt has a pop up? " + testData.getIsAppointmentPopup());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 10: Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());
		Thread.sleep(9000);
		Log4jUtil.log("Step 11: Verfiy Location Page and location to be selected = " + testData.getLocation());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Log4jUtil.log("current URL " + driver.getCurrentUrl());
		clickOnSubmitAppt(false, aptDateTime, testData, driver);
		Log4jUtil.log("Test Case Passed");
	}

	public void TBLFlow(HomePage homepage, Appointment testData, String startOrderOn, AppointmentPage appointment, WebDriver driver) throws Exception {
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.skipInsurance(driver);
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		} else {
			appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
		}
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 10: Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());
		Log4jUtil.log("Step 11: Verfiy Location Page and location to be selected = " + testData.getLocation());
		Log4jUtil.log("Location Page loaded ? " + location.areBasicPageElementsPresent());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		Log4jUtil.log("aptDateTime Page loaded ? " + aptDateTime.areBasicPageElementsPresent());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		bookAppointment(false, aptDateTime, testData, driver);
	}

	public void STBLFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		Log4jUtil.log("--------STBL Flow Starts---------------");
		AppointmentPage appointment = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		Speciality speciality = null;
		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startPage is Visible " + testData.isIsstartpointPresent());

		if (testData.isIsinsuranceVisible()) {
			speciality = homepage.skipInsuranceForSpeciality(driver);
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			Log4jUtil.log("clicked on specility");
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("StartPage is Present after clicked ok skip insurance");
				appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
			} else {
				Log4jUtil.log("StartPage Not Present");
				appointment = homepage.appointmentpage();
			}
		} else {
			Log4jUtil.log("Insurance is not present on homepage the checking for specility");
			speciality = homepage.specilitypage();
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Insurance is not present on homepage the checking for specility");
				appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

			} else {
				Log4jUtil.log("Insurance and startpage both not present");
				speciality = homepage.specilitypage();
				appointment = homepage.appointmentpage();
				startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			}
		}

		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Log4jUtil.log("does apt has a pop up? " + testData.getIsAppointmentPopup());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 10: Verfiy Provider Page and Provider = " + testData.getProvider());
		Log4jUtil.log(">>>>>>>>>> provider image present " + provider.providerImageSize());
		Thread.sleep(5000);
		Location location = provider.selectLocation(testData.getProvider());
		Thread.sleep(15000);
		Thread.sleep(25000);
		testData.setIsSearchLocationDisplayed(location.isSearchLocationEnabled());
		Log4jUtil.log(">>>>>>>>>> Is Location Search Enabled? " + location.isSearchLocationEnabled());
		Log4jUtil.log("Step 11: Verfiy Location Page and location to be selected = " + testData.getLocation());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Log4jUtil.log("current URL " + driver.getCurrentUrl());
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");
	}

	public void STLBFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		Log4jUtil.log("--------Flow Starts---------------");
		AppointmentPage appointment;
		StartAppointmentInOrder startappointmentInOrder;
		Speciality speciality;
		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startPage is Visible " + testData.isIsstartpointPresent());

		if (testData.isIsinsuranceVisible()) {
			speciality = homepage.skipInsuranceForSpeciality(driver);
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			Log4jUtil.log("clicked on specility");
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("StartPage is Present after clicked ok skip insurance");
				appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
			} else {
				Log4jUtil.log("StartPage Not Present");
				appointment = homepage.appointmentpage();

			}
		}

		else {
			Log4jUtil.log("Insurance is not present on homepage the checking for specility");
			speciality = homepage.specilitypage();
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());

			if (testData.isIsstartpointPresent()) {

				Log4jUtil.log("Insurance is not present on homepage the checking for specility");
				appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);

			} else {
				Log4jUtil.log("Insurance and startpage both not present");
				speciality = homepage.specilitypage();
				appointment = homepage.appointmentpage();
				startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			}
		}
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 10: Verfiy Location Page and location to be selected = " + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		Provider provider = location.searchProvider(testData.getLocation());
		Log4jUtil.log("address = " + location.getAddressValue());
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");
	}

	public void SLTBFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Location for appointment.");
		Location location = null;
		Speciality speciality = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startpage is Visible " + testData.isIsstartpointPresent());
		if (testData.isIsinsuranceVisible()) {
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			speciality = homepage.skipInsuranceForSpeciality(driver);
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			Log4jUtil.log("clicked on specility");
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_LOCATION);
			} else {
				location = homepage.locationpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}

		}

		else {
			Log4jUtil.log("Insurance is not present on homepage the checking for specility");
			speciality = homepage.specilitypage();
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			if (testData.isIsstartpointPresent()) {
				startappointmentInOrder = homepage.startpage();
				Log4jUtil.log("in else part  click on  " + PSSConstants.START_LOCATION);
				location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
				Log4jUtil.log("clicked on location ");
			} else {
				Log4jUtil.log("Start point not present");
				speciality = homepage.specilitypage();
				location = homepage.locationpage();
			}

		}
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(15000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(22000);
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");
	}

	public void SLBTFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Location for appointment.");
		Location location = null;
		Speciality speciality = null;
		StartAppointmentInOrder startappointmentInOrder = null;

		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startpage is Visible " + testData.isIsstartpointPresent());
		if (testData.isIsinsuranceVisible()) {
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			speciality = homepage.skipInsuranceForSpeciality(driver);
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			Log4jUtil.log("clicked on specility");
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_LOCATION);
			} else {
				location = homepage.locationpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}

		}

		else {
			Log4jUtil.log("Insurance is not present on homepage the checking for specility");
			speciality = homepage.specilitypage();
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			if (testData.isIsstartpointPresent()) {
				startappointmentInOrder = homepage.startpage();
				Log4jUtil.log("in else part  click on  " + PSSConstants.START_LOCATION);
				location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
				Log4jUtil.log("clicked on location ");
			} else {
				Log4jUtil.log("Start point not present");
				speciality = homepage.specilitypage();
				location = homepage.locationpage();
			}

		}
		Log4jUtil.log("Step 9: Verfiy location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		Provider provider = location.searchProvider(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Provider Page and provider to be selected = " + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentPage appointment = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 11: Verfiy Appointment Page and Appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.gettypeOfAppointment(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 12: Select avaiable Date ");

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");
	}

	public void SBTLFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Location for appointment.");
		Provider provider = null;
		Speciality speciality = null;
		StartAppointmentInOrder startappointmentInOrder = null;

		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startpage is Visible " + testData.isIsstartpointPresent());
		if (testData.isIsinsuranceVisible()) {
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			speciality = homepage.skipInsuranceForSpeciality(driver);
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			Log4jUtil.log("clicked on specility");
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_PROVIDER);
			} else {
				provider = homepage.providerpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}

		}

		else {
			Log4jUtil.log("Insurance is not present on homepage the checking for specility");
			speciality = homepage.specilitypage();
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			if (testData.isIsstartpointPresent()) {
				startappointmentInOrder = homepage.startpage();
				Log4jUtil.log("in else part  click on  " + PSSConstants.START_LOCATION);
				provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_PROVIDER);
			} else {
				Log4jUtil.log("Start point not present");
				speciality = homepage.specilitypage();
				provider = homepage.providerpage();
			}

		}
		Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
		Log4jUtil.log("going to choose an provider");
		assertTrue(provider.areBasicPageElementsPresent());
		Log4jUtil.log("basic element are present");
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointmentpage.areBasicPageElementsPresent());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");

	}

	public void SBLTFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Location for appointment.");
		Provider provider = null;
		Speciality speciality = null;
		StartAppointmentInOrder startappointmentInOrder = null;

		Log4jUtil.log("Insurance is Enabled " + testData.isIsinsuranceVisible());
		Log4jUtil.log("startpage is Visible " + testData.isIsstartpointPresent());
		if (testData.isIsinsuranceVisible()) {
			Log4jUtil.log("insurance is present on home Page going to skip insurance page");
			speciality = homepage.skipInsuranceForSpeciality(driver);
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			Log4jUtil.log("clicked on specility");
			if (testData.isIsstartpointPresent()) {
				Log4jUtil.log("Starting point is present after insurance skipped ");
				provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
				Log4jUtil.log("Successfully clicked on  " + PSSConstants.START_PROVIDER);
			} else {
				provider = homepage.providerpage();
				Log4jUtil.log("Starting point not Present going to select next provider ");
			}

		}

		else {
			Log4jUtil.log("Insurance is not present on homepage the checking for specility");
			speciality = homepage.specilitypage();
			startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());
			if (testData.isIsstartpointPresent()) {
				startappointmentInOrder = homepage.startpage();
				Log4jUtil.log("in else part  click on  " + PSSConstants.START_LOCATION);
				provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
				Log4jUtil.log("clicked on location ");
			} else {
				Log4jUtil.log("Start point not present");
				speciality = homepage.specilitypage();
				provider = homepage.providerpage();
			}

		}
		Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		Location location = provider.selectLocation(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Location Page and location to be selected = " + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointmentpage = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 11: Verfiy Appointment Page and Appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointmentpage.gettypeOfAppointment(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));

		Log4jUtil.log("Step 12: Select avaiable Date ");
		if (testData.isFutureApt()) {
			aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		} else {
			aptDateTime.selectDate(testData.getIsNextDayBooking());
		}

		Thread.sleep(6000);
		if (testData.isAnonymousFlow()) {
			Log4jUtil.log(" isAnonymousFlow is TRUE ");
			bookAnonymousApt(aptDateTime, testData, driver);
		} else {
			Log4jUtil.log("This is not an Anonymous flow so comes is else block");
			clickOnSubmitAppt(false, aptDateTime, testData, driver);
		}
		Log4jUtil.log("Test Case Passed");
	}

	public Boolean deleteFile(String fileName) {
		Boolean isFileDeleted = false;
		Log4jUtil.log("filePath= " + fileName);
		try {
			File fileToDelete = new File(fileName);
			Log4jUtil.log("filePath= " + fileToDelete.exists());
			if (fileToDelete.delete()) {
				Log4jUtil.log(fileToDelete.getName() + " is deleted!");
				isFileDeleted = true;
			} else {
				Log4jUtil.log("Delete operation is failed please delete file manually.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFileDeleted;
	}

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}

	public void bookAppointment(Boolean isInsuranceDisplated, AppointmentDateTime aptDateTime, Appointment testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 12: Verify Confirmation page and Scheduled page");
		Log4jUtil.log("Is Insurance Page Displated= " + isInsuranceDisplated);
		Thread.sleep(2000);
		if (isInsuranceDisplated) {
			UpdateInsurancePage updateinsurancePage = aptDateTime.selectAppointmentTimeIns();
			ConfirmationPage confirmationpage = updateinsurancePage.skipInsuranceUpdate();
			appointmentToScheduled(confirmationpage, testData);
		} else {
			ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
			appointmentToScheduled(confirmationpage, testData);
		}
	}

	public void reBookAppointment(Boolean isInsuranceDisplated, AppointmentDateTime aptDateTime, Appointment testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 12: Verify Confirmation page and Scheduled page");
		Log4jUtil.log("Is Insurance Page Displated= " + isInsuranceDisplated);
		Thread.sleep(2000);
		if (testData.isIsinsuranceVisible()) {
			UpdateInsurancePage updateinsurancePage = aptDateTime.selectAppointmentTimeIns();
			ConfirmationPage confirmationpage = updateinsurancePage.skipInsuranceUpdate();

			if (testData.isShowCancellationRescheduleReason() == false & testData.isShowCancellationReasonPM() == false) {

				appointmentToRescheduled(confirmationpage, testData);

			} else if (testData.isShowCancellationRescheduleReason() == true & testData.isShowCancellationReasonPM() == false) {

				apptRescheduledwithTextReason(confirmationpage, testData);

			} else if (testData.isShowCancellationRescheduleReason() == true & testData.isShowCancellationReasonPM() == true) {

				apptRescheduledDropdownReason(confirmationpage, testData);
			}

		} else {
			ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());

			if (testData.isShowCancellationRescheduleReason() == false & testData.isShowCancellationReasonPM() == false) {

				appointmentToRescheduled(confirmationpage, testData);

			} else if (testData.isShowCancellationRescheduleReason() == true & testData.isShowCancellationReasonPM() == false) {

				apptRescheduledwithTextReason(confirmationpage, testData);

			} else if (testData.isShowCancellationRescheduleReason() == true & testData.isShowCancellationReasonPM() == true) {

				apptRescheduledDropdownReason(confirmationpage, testData);
			}
		}
	}

	public void fillRescheduleDetails(Boolean isInsuranceDisplated, AppointmentDateTime aptDateTime, Appointment testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 12: Verify Confirmation page and Scheduled page");
		Log4jUtil.log("Is Insurance Page Displated= " + isInsuranceDisplated);
		Thread.sleep(2000);
		if (isInsuranceDisplated) {
			UpdateInsurancePage updateinsurancePage = aptDateTime.selectAppointmentTimeIns();
			updateinsurancePage.skipInsuranceUpdate();

		} else {
			aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());

		}
	}

	public void bookAnonymousApt(AppointmentDateTime aptDateTime, Appointment testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 12: Verify Confirmation page and Scheduled page");
		Thread.sleep(2000);
		AnonymousPatientInformation anonymousPatientInformation = aptDateTime.selectAppointmentTimeSlot(testData.getIsNextDayBooking());
		ConfirmationPage confirmationpage = anonymousPatientInformation.fillPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(),
				testData.getEmail(), testData.getGender(), testData.getPrimaryNumber());
		appointmentToScheduledAnonymous(confirmationpage, testData);
	}

	public void clickOnSubmitAppt(Boolean isInsuranceDisplated, AppointmentDateTime aptDateTime, Appointment testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 12: Verify Confirmation page and Scheduled page");
		Log4jUtil.log("Is Insurance Page Displated= " + isInsuranceDisplated);
		Thread.sleep(2000);
		if (isInsuranceDisplated) {
			UpdateInsurancePage updateinsurancePage = aptDateTime.selectAppointmentDateAndTime(driver);
			ConfirmationPage confirmationpage = updateinsurancePage.skipInsuranceUpdate();
			appointmentToScheduled(confirmationpage, testData);
		} else {
			ConfirmationPage confirmationpage;
			if (testData.isFutureApt()) {
				confirmationpage = aptDateTime.selectFutureApptDateTime(testData.getIsNextDayBooking());

			} else {
				confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
			}

			appointmentToScheduledAnonymous(confirmationpage, testData);
		}
	}

	public void appointmentToScheduledAnonymous(ConfirmationPage confirmationpage, Appointment testData) throws Exception {
		Log4jUtil.log("Step 13: Verify if Appointment is scheduled and download ics file");
		assertTrue(confirmationpage.areBasicPageElementsPresent());
		String aptScheduledAt = confirmationpage.getAppointmentDetails().get((confirmationpage.getAppointmentDetails().size() - 1)).getText();
		Log4jUtil.log(">> " + aptScheduledAt);
		for (WebElement ele : confirmationpage.getAppointmentDetails()) {
			Log4jUtil.log("apt Details= " + ele.getText());
		}
		ScheduledAppointmentAnonymous scheduledAppointmentAnonymous = confirmationpage.appointmentConfirmedAnonymous();
		Log4jUtil.log("appointment ID = " + scheduledAppointmentAnonymous.getAppointmentID());
		assertTrue(scheduledAppointmentAnonymous.areBasicPageElementsPresent());
		Log4jUtil.log("Add to calendar option is displayed and is clickable.");
		scheduledAppointmentAnonymous.downloadCalander();
		Thread.sleep(2000);
		readICSFile(filePath());
	}

	public void appointmentToScheduled(ConfirmationPage confirmationpage, Appointment testData) throws Exception {
		Log4jUtil.log("Step 13: Verify if Appointment is scheduled and download ics file");
		assertTrue(confirmationpage.areBasicPageElementsPresent());
		String aptScheduledAt = confirmationpage.getAppointmentDetails().get((confirmationpage.getAppointmentDetails().size() - 1)).getText();
		Log4jUtil.log(">> " + aptScheduledAt);
		for (WebElement ele : confirmationpage.getAppointmentDetails()) {
			Log4jUtil.log("apt Details= " + ele.getText());
		}
		ScheduledAppointment scheduledappointment = confirmationpage.appointmentConfirmed();
		Log4jUtil.log("appointment ID = " + scheduledappointment.getAppointmentID());
		assertTrue(scheduledappointment.areBasicPageElementsPresent());
		Log4jUtil.log("Add to calendar option is displayed and is clickable.");
		scheduledappointment.downloadCalander();
		Thread.sleep(2000);
		readICSFile(filePath());
	}

	public void appointmentToRescheduled(ConfirmationPage confirmationpage, Appointment testData) throws Exception {

		Log4jUtil.log("Step 13: Verify if Appointment is scheduled and download ics file");
		assertTrue(confirmationpage.areBasicPageElementsPresent());

		String aptScheduledAt = confirmationpage.getAppointmentDetails().get((confirmationpage.getAppointmentDetails().size() - 1)).getText();
		Log4jUtil.log(">> " + aptScheduledAt);
		for (WebElement ele : confirmationpage.getAppointmentDetails()) {
			Log4jUtil.log("apt Details= " + ele.getText());

		}

		ScheduledAppointment scheduledappointment = confirmationpage.rescheduleAppointmentConfirmed();
		Log4jUtil.log("appointment ID = " + scheduledappointment.getAppointmentID());
		assertTrue(scheduledappointment.areBasicPageElementsPresent());
		Log4jUtil.log("Add to calendar option is displayed and is clickable.");
		scheduledappointment.downloadCalander();
		Thread.sleep(2000);
		readICSFile(filePath());
	}

	public void apptRescheduledwithTextReason(ConfirmationPage confirmationpage, Appointment testData) throws Exception {

		Log4jUtil.log("Step 13: Verify if Appointment is scheduled and download ics file");
		assertTrue(confirmationpage.areBasicPageElementsPresent());

		String aptScheduledAt = confirmationpage.getAppointmentDetails().get((confirmationpage.getAppointmentDetails().size() - 1)).getText();
		Log4jUtil.log(">> " + aptScheduledAt);
		for (WebElement ele : confirmationpage.getAppointmentDetails()) {
			Log4jUtil.log("apt Details= " + ele.getText());

		}

		Log4jUtil.log("Enter the Reschedule Reason");
		confirmationpage.sendRescheduleReason();
		assertEquals(confirmationpage.maxLengthRescheduleReason(), 500, "The max length of Reschedule reason is not 500, so test case failed");

		ScheduledAppointment scheduledappointment = confirmationpage.rescheduleAppointmentConfirmed();
		Log4jUtil.log("appointment ID = " + scheduledappointment.getAppointmentID());
		assertTrue(scheduledappointment.areBasicPageElementsPresent());
		Log4jUtil.log("Add to calendar option is displayed and is clickable.");
		scheduledappointment.downloadCalander();
		Thread.sleep(2000);
		readICSFile(filePath());
	}

	public void apptRescheduledDropdownReason(ConfirmationPage confirmationpage, Appointment testData) throws Exception {

		Log4jUtil.log("Step 13: Verify if Appointment is scheduled and download ics file");
		assertTrue(confirmationpage.areBasicPageElementsPresent());

		String aptScheduledAt = confirmationpage.getAppointmentDetails().get((confirmationpage.getAppointmentDetails().size() - 1)).getText();
		Log4jUtil.log(">> " + aptScheduledAt);
		for (WebElement ele : confirmationpage.getAppointmentDetails()) {
			Log4jUtil.log("apt Details= " + ele.getText());
		}

		Log4jUtil.log("Select reason from drop down list");
		confirmationpage.selectRescheduleReason();

		ScheduledAppointment scheduledappointment = confirmationpage.rescheduleAppointmentConfirmed();
		Log4jUtil.log("appointment ID = " + scheduledappointment.getAppointmentID());
		assertTrue(scheduledappointment.areBasicPageElementsPresent());
		Log4jUtil.log("Add to calendar option is displayed and is clickable.");
		scheduledappointment.downloadCalander();
		Thread.sleep(2000);
		readICSFile(filePath());
	}

	public ScheduledAppointment selectAFlow(WebDriver driver, String rule, HomePage homepage, Appointment testData) throws Exception {
		Log4jUtil.log("selectAFlow method started");
		Thread.sleep(1000);
		testData.setIsInsuranceEnabled(false);
		Thread.sleep(1000);
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			LBTFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.LTB)) {
			LTBFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			BLTFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.BTL)) {
			BTLFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.TLB)) {
			TLBFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.TBL)) {
			TBLFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.STBL)) {
			STBLFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.STLB)) {
			Log4jUtil.log("Method STLBFlow will start now......");
			STLBFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.SLTB)) {
			SLTBFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.SLBT)) {
			SLBTFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.SBTL)) {
			SBTLFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.SBLT)) {
			SBLTFlow(homepage, testData, Boolean.toString(testData.getIsInsuranceEnabled()), driver);
		}
		return PageFactory.initElements(driver, ScheduledAppointment.class);
	}

	public void selectAFlow(WebDriver driver, String rule, HomePage homepage, Appointment testData, AppointmentPage appointment) throws Exception {
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(7000);
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			LBTFlow(homepage, testData, "false", driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.LTB)) {
			LTBFlow(homepage, testData, "false", driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			BLTFlow(homepage, testData, "false", driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.BTL)) {
			BTLFlow(homepage, testData, "false", driver);
		}
		if (rule.equalsIgnoreCase(PSSConstants.TBL)) {
			TBLFlow(homepage, testData, "false", appointment, driver);
		}
	}

	public void checkPrivacyPage(WebDriver driver) {
		PrivacyPolicy privacypolicy = new PrivacyPolicy(driver);
		privacypolicy.submitPrivacyPage();
	}

	public void dismissPopup(WebDriver driver) {
		PrivacyPolicy privacypolicy = new PrivacyPolicy(driver);
		privacypolicy.closePopup();
	}

	public void dismissPopupExistingPatient(WebDriver driver) {
		PrivacyPolicy privacypolicy = new PrivacyPolicy(driver);
		privacypolicy.submitPrivacyForExistingPatient();

	}

	public String switchtabs(WebDriver driver) {
		ArrayList<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
		driver.switchTo().window(browserTabs.get(1));
		System.setProperty("current.window.handle", browserTabs.get(1));
		String pssPatientUrl = driver.getCurrentUrl();
		/* @ code only for dev3 */
		pssPatientUrl = pssPatientUrl.replaceAll("https", "http");
		Log4jUtil.log("Url = " + driver.getCurrentUrl());
		return pssPatientUrl;
	}

	public PatientIdentificationPage newtabs(WebDriver driver, String url, String email) throws InterruptedException {

		driver.manage().deleteAllCookies(); // delete all cookies
		Thread.sleep(7000); // wait 7 seconds to clear cookies.
		driver.get(url);
		driver.findElement(By.xpath("//input[@id='addOverlay']")).sendKeys(email);
		driver.findElement(By.xpath("//button[@id='go-to-public']")).click();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='ng-binding']")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@class='ng-binding']")).click();
		Thread.sleep(2000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,550)", "");
		Thread.sleep(1000);

		driver.switchTo().frame("msg_body");
		js.executeScript("window.scrollBy(0,950)", "");
		driver.manage().deleteAllCookies();
		driver.findElement(By.xpath("//a[contains(text(),'Reschedule or cancel')]")).click();

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("Number of Tabs are " + tabs.size());

		driver.switchTo().window((String) tabs.get(1));
		Thread.sleep(2000);

		return PageFactory.initElements(driver, PatientIdentificationPage.class);
	}

	public void deleteEmail_Mailinator(WebDriver driver, String url, String email) throws InterruptedException {

		driver.manage().deleteAllCookies(); // delete all cookies

		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(2000);

		CommonMethods cm = new CommonMethods(driver);

		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(email);
		driver.findElement(By.xpath("//button[@id='go-to-public']")).click();

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr//td[@class='ng-binding'][2]")));
		Thread.sleep(2000);

		List<WebElement> subList = driver.findElements(By.xpath("//tr//td[@class='ng-binding'][2]"));
		List<WebElement> chkList = driver.findElements(By.xpath("//tr/td/div"));

		WebElement btndelete = driver.findElement(By.xpath("//button[@aria-label='Delete Button']"));

		String subject_line = "Your appointment is now scheduled";
		for (int i = 0; i < subList.size(); i++) {

			if (subList.get(i).getText().contains(subject_line)) {

				Log4jUtil.log(subList.get(i).getText() + "---Text");

				cm.highlightElement(chkList.get(i));
				chkList.get(i).click();
			}
		}

		Thread.sleep(2000);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(550,0)", "");
		Thread.sleep(1000);

		driver.manage().deleteAllCookies();
		cm.highlightElement(btndelete);
		btndelete.click();
		Thread.sleep(2000);

	}

	public void fillPatientDetails(Boolean insuranceSelected, Appointment testData, LoginlessPatientInformation loginlesspatientinformation)
			throws InterruptedException {
		if (insuranceSelected) {
			Log4jUtil.log("InsurancePage=" + insuranceSelected);
			NewPatientInsuranceInfo newpatientinsuranceinfo = loginlesspatientinformation.fillPatientForm(testData.getFirstName(), testData.getLastName(),
					testData.getDob(), testData.getEmail(), testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
			newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			Log4jUtil.log("InsurancePage=" + insuranceSelected);
			loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		}
	}

	public void navigateFromHomePage(String ruleType, WebDriver driver, Appointment testData, String toSelect) throws InterruptedException {
		if (!ruleType.equalsIgnoreCase(PSSConstants.SPECIALITY)) {
			HomePage homepage = new HomePage(driver);
			homepage.areBasicPageElementsPresent();
			if (toSelect.equalsIgnoreCase(PSSConstants.START_PROVIDER)) {
				homepage.selectProvider(testData.getProvider());
			}
			if (toSelect.equalsIgnoreCase(PSSConstants.START_APPOINTMENT)) {
				homepage.selectAppointment(testData.getAppointmenttype());
			}
			if (toSelect.equalsIgnoreCase(PSSConstants.START_LOCATION)) {
				homepage.selectLocation(testData.getLocation());
			}
		} else {
			HomePageSpeciality homepage = new HomePageSpeciality(driver);
			homepage.areBasicPageElementsPresent();
			StartAppointmentInOrder startappointmentinorder = homepage.selectSpeciality(testData.getSpeciality());
			startappointmentinorder.areBasicPageElementsPresent();

			if (toSelect.equalsIgnoreCase(PSSConstants.START_APPOINTMENT)) {
				startappointmentinorder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
			}
			if (toSelect.equalsIgnoreCase(PSSConstants.START_PROVIDER)) {
				startappointmentinorder.selectFirstProvider(PSSConstants.START_PROVIDER);
			}
			if (toSelect.equalsIgnoreCase(PSSConstants.START_LOCATION)) {
				startappointmentinorder.selectFirstLocation(PSSConstants.START_LOCATION);
			}
		}
	}

	public void check_Provider_Apt_Loc_List(WebDriver driver, HomePage homepage, Appointment testData) throws Exception {

		Log4jUtil.log("Is Home page welcome pop up? " + homepage.isPopUP());
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);
		Collection<String> partnerProviderC = Arrays.asList(testData.getProviderList().split(","));
		Collection<String> pssProviderC = new ArrayList<String>();
		Log4jUtil.log("In Provider page " + partnerProviderC.size());
		Provider provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		for (int i = 0; i < provider.getProviderNames().size(); i++) {
			Log4jUtil.log("Total Provider names = " + provider.getProviderNames().get(i).getText());
			pssProviderC.add(provider.getProviderNames().get(i).getText());
		}
		Collection<String> similar = new HashSet<String>(partnerProviderC);
		similar.retainAll(pssProviderC);
		assertTrue(partnerProviderC.size() == pssProviderC.size(), "provider names did not matched.");
		System.out.printf("partner:%s%npss:%s%nSimilar:%s%n", partnerProviderC, pssProviderC, similar);
		Log4jUtil.log("similar provider size " + similar.size());
		Log4jUtil.log("pssProviderC size " + pssProviderC.size());
		homepage.companyLogoClick();
		Thread.sleep(8000);
		Collection<String> partnerLocationC = Arrays.asList(testData.getLocationList().split(","));
		Collection<String> pssLocationC = new ArrayList<String>();
		Location location = homepage.selectLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("In Location page ");
		for (int i = 0; i < location.getLocationNames().size(); i++) {
			Log4jUtil.log("Total Location names = " + location.getLocationNames().get(i).getText());
			String locName = location.getLocationNames().get(i).getText();
			locName = locName.substring(locName.indexOf(".") + 2, (locName.length()));
			pssLocationC.add(locName);
		}
		Collection<String> similarL = new HashSet<String>(partnerLocationC);
		similarL.retainAll(pssLocationC);

		System.out.printf("partner:%s%npss:%s%nSimilarLoc:%s%n", partnerLocationC, pssLocationC, similarL);
		Log4jUtil.log("similarL size " + similarL.size());
		Log4jUtil.log("pssLocationC size " + pssLocationC.size());
		assertTrue(partnerLocationC.size() == pssLocationC.size(), "Location names did not matched.");
		homepage.companyLogoClick();
		Thread.sleep(8000);
		AppointmentPage appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
		Collection<String> partnerappointmentC = Arrays.asList(testData.getAppointmentList().split(","));
		Collection<String> pssappointmentC = new ArrayList<String>();
		Log4jUtil.log("In Appointments page ");
		for (int i = 0; i < appointment.getAppointmentNames().size(); i++) {
			Log4jUtil.log("Total Appointment names = " + appointment.getAppointmentNames().get(i).getText());
			pssappointmentC.add(appointment.getAppointmentNames().get(i).getText());
		}
		Collection<String> similarApt = new HashSet<String>(partnerappointmentC);
		similarApt.retainAll(pssappointmentC);
		System.out.printf("One:%s%nTwo:%s%nSimilar:%s%n", partnerappointmentC, pssappointmentC, similarApt);
		Log4jUtil.log("similarApt size " + similarApt.size());
		Log4jUtil.log("pssappointmentC size " + pssappointmentC.size());
		assertTrue(partnerappointmentC.size() == pssappointmentC.size(), "Appointment type names did not matched.");
		appointment.selectTypeOfLocation(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		location.searchProvider(testData.getLocation());
		AppointmentDateTime aptDateTime = provider.selectDateTime(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(9000);
		aptDateTime.getScrollBarDetails();
		aptDateTime.selectAppointmentTimeIns();
		homepage.companyLogoClick();
		Thread.sleep(8000);
	}

	public void ageRule(WebDriver driver, HomePage homepage, Appointment testData, Boolean isUnderAge, String rule) throws Exception {
		Log4jUtil.log("Is Home page welcome pop up? " + homepage.isPopUP());
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);
		Log4jUtil.log("Select Provider page ");
		Provider provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		Log4jUtil.log("Search for Provider with Age Rule Applied :" + provider.areBasicPageElementsPresent());
		Thread.sleep(6000);
		int providerSize = provider.searchForProviderFromListt(testData.getProvider());
		if (isUnderAge) {
			assertEquals(providerSize, 0);
			Log4jUtil.log("Unable to schedule appointment for UnderAge patient.");
			homepage.logout();
		} else {
			assertEquals(providerSize, 1);
			homepage.companyLogoClick();
			testData.setIsCancelApt(false);
			selectAFlow(driver, rule, homepage, testData);
			Log4jUtil.log("Appointment scheduled for patient within Age criteria.");
		}
	}

	public void verifyProviderAssociation(WebDriver driver, HomePage homepage, Appointment testData, String rule) throws Exception {
		Log4jUtil.log("Is Home page welcome pop up? " + homepage.isPopUP());
		closeHomePagePopUp(homepage);
		selectFlowBasedOnProvider(testData, homepage);
	}

	public void selectFlowBasedOnProvider(Appointment testData, HomePage homepage) throws Exception {
		Log4jUtil.log("In Provider page ");
		Provider provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		Log4jUtil.log("Search for Provider with Age Rule Applied :" + provider.areBasicPageElementsPresent());
		Thread.sleep(6000);
		String[] providerType = testData.getAssociatedProvider1().split(",");
		AppointmentPage appointment = provider.selectAppointment(providerType[0]);
		String[] aptType = testData.getAssociatedApt1().split(",");
		Log4jUtil.log("Apt to be selected. = " + aptType[0]);
		Location location = appointment.selectTypeOfLocation(aptType[0], Boolean.valueOf(testData.getIsAppointmentPopup()));
		String[] LocType = testData.getAssociatedLocation1().split(",");
		Log4jUtil.log("Location to be selected. = " + LocType[0]);
		AppointmentDateTime aptDateTime = location.selectDatTime(LocType[0]);
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		Log4jUtil.log("Appropriate Scheduling Calander is displayed.");
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(9000);
		aptDateTime.selectAppointmentTimeIns();
		Log4jUtil.log("Appropriate Slots are Displayed.");
		gotoHomePage(homepage);
		String[] providerType2 = testData.getAssociatedProvider2().split(",");
		provider.selectAppointment(providerType2[0]);
		String[] aptType2 = testData.getAssociatedApt2().split(",");
		Log4jUtil.log("Apt to be selected. = " + aptType2[0]);
		appointment.selectTypeOfLocation(aptType2[0], Boolean.valueOf(testData.getIsAppointmentPopup()));
		String[] LocType2 = testData.getAssociatedLocation2().split(",");
		Log4jUtil.log("Location to be selected. = " + LocType2[0]);
		location.selectDatTime(LocType2[0]);
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		Log4jUtil.log("Appropriate Scheduling Calander is displayed.");
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(9000);
		aptDateTime.selectAppointmentTimeIns();
		Log4jUtil.log("Appropriate Slots are Displayed.");
		gotoHomePage(homepage);
		String[] providerType3 = testData.getAssociatedProvider3().split(",");
		provider.selectAppointment(providerType3[0]);
		String[] aptType3 = testData.getAssociatedApt3().split(",");
		Log4jUtil.log("Apt to be selected. = " + aptType3[0]);
		appointment.selectTypeOfLocation(aptType3[0], Boolean.valueOf(testData.getIsAppointmentPopup()));
		String[] LocType3 = testData.getAssociatedLocation1().split(",");
		Log4jUtil.log("Location to be selected. = " + LocType3[0]);
		location.selectDatTime(LocType3[0]);
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		Log4jUtil.log("Appropriate Scheduling Calander is displayed.");
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(9000);
		aptDateTime.selectAppointmentTimeIns();
		Log4jUtil.log("Appropriate Slots are Displayed.");
	}

	public void gotoHomePage(HomePage homepage) throws Exception {
		homepage.companyLogoClick();
		Thread.sleep(8000);
	}

	public void closeHomePagePopUp(HomePage homepage) throws Exception {
		if (homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);
	}

	public void skipInsuranceForHomePage(HomePage homepage, WebDriver driver) throws InterruptedException {
		homepage.skipInsurance(driver);
	}

	public void setTestData(String partnerPractice, Appointment testData, AdminUser adminuser) throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GW)) {
			propertyData.setAdminGW(adminuser);
			propertyData.setAppointmentResponseGW(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.GE)) {
			propertyData.setAdminGE(adminuser);
			propertyData.setAppointmentResponseGE(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.NG)) {
			propertyData.setAdminNG(adminuser);
			propertyData.setAppointmentResponseNG(testData);
		}
		if (partnerPractice.equalsIgnoreCase(PSSConstants.AT)) {
			propertyData.setAdminAT(adminuser);
			propertyData.setAppointmentResponseAT(testData);
		}
	}

	public String getURLResponse(String url) throws Exception {
		Log4jUtil.log("Getting processingurl Status:- " + url);
		URLConnection getConnection = null;
		String output = "";
		try {
			URL processingURL1 = new URL(url);
			getConnection = processingURL1.openConnection();
			((HttpURLConnection) getConnection).setRequestMethod("GET");
			getConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
			getConnection.setRequestProperty("Upgrade-Insecure-Requests", "1");
			int responseCode = ((HttpURLConnection) getConnection).getResponseCode();
			Log4jUtil.log("Get Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(getConnection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			output = response.toString();
			((HttpURLConnection) getConnection).disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			((HttpURLConnection) getConnection).disconnect();
		}
		return output;
	}

	public void leadtimefeature(Appointment testData) {
		SimpleDateFormat f1 = new SimpleDateFormat("dd");
		SimpleDateFormat f2 = new SimpleDateFormat("MMM");
		String currentDate = "Current Date is :" + f1.format(new Date()) + " " + f2.format(new Date());
		Log4jUtil.log("Current Date is   " + currentDate);
		String Nextbook = currentDate + testData.getLeadtimeDay();
		Log4jUtil.log("Next Avaliable Date is   " + Nextbook);
	}

	public String currentDateandLeadDay(Appointment testData) {
		TimeZone timeZone = TimeZone.getTimeZone(testData.getCurrentTimeZone());
		String dateFormat = "dd MMMM,yyyy";
		SimpleDateFormat f1 = new SimpleDateFormat(dateFormat);
		Calendar c = Calendar.getInstance();
		TimeZone time_zone = TimeZone.getTimeZone(testData.getCurrentTimeZone());
		f1.setTimeZone(timeZone);
		c.setTimeZone(time_zone);
		c.add(Calendar.DATE, testData.getLeadtimeDay());
		String currentDate = f1.format(c.getTime());
		Log4jUtil.log("Current Date is " + currentDate);
		String currentleddate = currentDate.substring(0, 2);
		return currentleddate;
	}

	public String currentESTTimeandLeadTime(Appointment testData) {
		DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
		Calendar now = Calendar.getInstance();
		TimeZone time_zone = TimeZone.getTimeZone(testData.getCurrentTimeZone());
		dateFormat.setTimeZone(time_zone);
		now.setTimeZone(time_zone);
		int ampm = now.get(Calendar.AM_PM);
		String am_pm = "ampm";
		if (ampm == 0) {
			am_pm = "AM";
		} else {
			am_pm = "PM";
		}
		String time1 = now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE) + am_pm;
		Log4jUtil.log("Time Before the lead time   " + time1);
		now.add(Calendar.HOUR, testData.getLeadtimeHour());
		now.add(Calendar.MINUTE, testData.getLeadtimeMinute());
		String timeplusleadmin = +now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + am_pm;
		return timeplusleadmin;
	}

	public String currentESTDate(Appointment testData) {
		TimeZone timeZone = TimeZone.getTimeZone(testData.getCurrentTimeZone());
		String dateFormat = "dd";
		SimpleDateFormat f1 = new SimpleDateFormat(dateFormat);
		Calendar c = Calendar.getInstance();
		TimeZone time_zone = TimeZone.getTimeZone(testData.getCurrentTimeZone());
		f1.setTimeZone(timeZone);
		c.setTimeZone(time_zone);
		String currentDate = f1.format(c.getTime());
		return currentDate;
	}

	public void rescheduleAPT(Appointment testData, WebDriver driver) throws Exception {
		AppointmentDateTime aptDateTime = new AppointmentDateTime(driver);
		aptDateTime = aptDateTime.selectDt(testData.getIsNextDayBooking());
		Thread.sleep(1000);
		reBookAppointment(true, aptDateTime, testData, driver);
	}

	public String numDate(Appointment testData) {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		Log4jUtil.log("current est date is  " + currentDateandLeadDay(testData));
		String date = currentDateandLeadDay(testData);
		String dateFormat = "dd";
		SimpleDateFormat f1 = new SimpleDateFormat(dateFormat);
		java.util.Date dateSelectedFrom = null;
		java.util.Date dateNextDate = null;
		String date2 = "";
		try {
			dateSelectedFrom = f1.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String nextDate = f1.format(dateSelectedFrom.getTime() + MILLIS_IN_DAY);
		try {
			dateNextDate = f1.parse(nextDate);
			String date1 = dateNextDate.toString();
			date2 = date1.substring(8, 10);
			Log4jUtil.log("Next day's date: " + dateNextDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date2;
	}

	public long timeDifferenceendTime(Appointment testData) throws ParseException {
		Log4jUtil.log("Bussiness Hour Endtime is  " + testData.getBusinesshourEndTime());
		Calendar now = Calendar.getInstance();
		TimeZone time_zone = TimeZone.getTimeZone(testData.getCurrentTimeZone());
		now.setTimeZone(time_zone);
		now.add(Calendar.HOUR, testData.getLeadtimeHour());
		now.add(Calendar.MINUTE, testData.getLeadtimeMinute());
		String currenttime = +now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
		Log4jUtil.log("Afetr Add leadTime : " + currenttime);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date time1 = format.parse(currenttime);
		Date time2 = format.parse(testData.getBusinesshourEndTime());
		long difference = time2.getTime() - time1.getTime();
		Log4jUtil.log("Time Differnce is  " + difference);
		return difference;
	}

	public String leadTime(Appointment testData) throws ParseException {
		String currentleadtime = null;
		String getBusinesshourStartTime = testData.getBusinesshourStartTime();
		String hour = getBusinesshourStartTime.substring(0, 2);
		String min = getBusinesshourStartTime.substring(3, 5);
		int i = Integer.parseInt(hour);
		int j = Integer.parseInt(min);
		Calendar now = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, i);
		cal.set(Calendar.MINUTE, j);
		String fixedtime = +cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
		Log4jUtil.log("Fixed time is" + fixedtime);
		TimeZone time_zone = TimeZone.getTimeZone(testData.getCurrentTimeZone());
		now.setTimeZone(time_zone);
		cal.setTimeZone(time_zone);
		String currenttime = +now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
		Log4jUtil.log("Current Time is : " + currenttime);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date time1 = format.parse(currenttime);
		Date time2 = format.parse(fixedtime);
		cal.getTime();
		cal.setTime(time2);
		long difference = time2.getTime() - time1.getTime();
		Log4jUtil.log("Time Differnce is  " + difference);
		if (difference < 0) {
			currentleadtime = currentESTTimeandLeadTime(testData);
			Log4jUtil.log("Lead Time Added in the current time  " + currentleadtime);
			return currentleadtime;
		} else {
			cal.add(Calendar.HOUR, testData.getLeadtimeHour());
			cal.add(Calendar.MINUTE, testData.getLeadtimeMinute());
			String time3 = cal.getTimeInMillis() + " -> " + cal.getTime();
			currentleadtime = time3.substring(22, 28);
			Log4jUtil.log("Lead Time in the Businessstart time  " + currentleadtime);
			return currentleadtime;
		}
	}

	public boolean isValidTime(String time) {
		String regexPattern = "(1[012]|[1-9]):" + "[0-5][0-9](\\s)" + "?(?i)(am|pm)";
		Pattern compiledPattern = Pattern.compile(regexPattern);
		if (time == null) {
			return false;
		}
		Matcher m = compiledPattern.matcher(time);
		return m.matches();
	}

	public void clickOnSubmitAppt1(Boolean isInsuranceDisplated, AppointmentDateTime aptDateTime, Appointment testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 12: Verify Confirmation page and Scheduled page");
		Log4jUtil.log("Is Insurance Page Displated= " + isInsuranceDisplated);
		Thread.sleep(2000);
		if (isInsuranceDisplated) {
			UpdateInsurancePage updateinsurancePage = aptDateTime.selectAppointmentDateAndTime(driver);
			ConfirmationPage confirmationpage = updateinsurancePage.skipInsuranceUpdate();
			appointmentToScheduled(confirmationpage, testData);
		} else {
			ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
			backtohomePage(confirmationpage, testData);
		}
	}

	public void backtohomePage(ConfirmationPage confirmationpage, Appointment testData) throws Exception {
		assertTrue(confirmationpage.areBasicPageElementsPresent());
		ScheduledAppointmentAnonymous scheduledAppointmentAnonymous = confirmationpage.appointmentConfirmedAnonymous();
		scheduledAppointmentAnonymous.backtoHomePage();
	}

	public int ageCurrentmonths(Appointment testData) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		String date = testData.getDob();
		// "23-Mar-2019";
		LocalDate pdate = LocalDate.parse(date, formatter);
		LocalDate now = LocalDate.now();
		Period diff = Period.between(pdate, now);
		int yearmonth = diff.getYears() * 12;
		int month = yearmonth + diff.getMonths();
		Log4jUtil.log("Total Month of patient from date of Birth is  " + month);
		return month;

	}
}
