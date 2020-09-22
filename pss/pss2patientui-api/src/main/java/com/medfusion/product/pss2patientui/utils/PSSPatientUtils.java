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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous.AnonymousPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePageSpeciality;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.OnlineAppointmentScheduling;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PrivacyPolicy;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.Appointment.Speciality.Speciality;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Insurance.UpdateInsurancePage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointmentAnonymous;
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
		Log4jUtil.log("startOrderOn " + startOrderOn);
		if (startOrderOn.equalsIgnoreCase("true")) {

			Log4jUtil.log("Insurance is enabled");
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		} else {
			Log4jUtil.log("Insurance is Disabled");
			location = homepage.selectLocation(PSSConstants.START_LOCATION);
		}
		Log4jUtil.log("Step 9: Verfiy location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		Provider provider = location.searchProvider(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Provider Page and provider to be selected = " + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentPage appointment = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 11: Verfiy Appointment Page and Appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectTypeOfAppointment(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 12: Select avaiable Date ");
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		Log4jUtil.log("date- " + date);
		Thread.sleep(6000);
		bookAppointment(false, aptDateTime, testData, driver);
	}

	public void BLTFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		if (startOrderOn.equalsIgnoreCase("true")) {
			Boolean insuranceEnabled = true;
			if (insuranceEnabled) {
				Thread.sleep(3500);
			}
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		} else {
			provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		}
		Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		Location location = provider.selectLocation(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Location Page and location to be selected = " + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointmentpage = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 11: Verfiy Appointment Page and Appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointmentpage.selectTypeOfAppointment(testData.getAppointmenttype(), false);
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);
		bookAppointment(false, aptDateTime, testData, driver);
	}

	public void BTLFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		if (startOrderOn.equalsIgnoreCase("true")) {
			Boolean insuranceEnabled = true;
			if (insuranceEnabled) {
				Thread.sleep(3500);
			}
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		} else {
			provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		}
		Log4jUtil.log("Step 9: Verfiy Provider Page and provider =" + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointmentpage.areBasicPageElementsPresent());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 11: Verfiy Location Page and location = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);
		bookAppointment(false, aptDateTime, testData, driver);
	}

	public void LTBFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Location for appointment.");
		Location location = null;
		if (startOrderOn.equalsIgnoreCase("true")) {
			Boolean insuranceEnabled = true;
			if (insuranceEnabled) {
				Thread.sleep(3500);
			}
			StartAppointmentInOrder startappointmentInOrder = homepage.skipInsurance(driver);
			location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		} else {
			location = homepage.selectLocation(PSSConstants.START_LOCATION);
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
		AppointmentDateTime aptDateTime = provider.selectDateTime(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);
		bookAppointment(false, aptDateTime, testData, driver);
	}

	public void TLBFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment. startOrderon? " + startOrderOn);
		Log4jUtil.log("--------Flow Starts---------------");
		AppointmentPage appointment;
		if (startOrderOn.equalsIgnoreCase("true")) {
			Boolean insuranceEnabled = true;
			if (insuranceEnabled) {
				Thread.sleep(3500);
			}


			StartAppointmentInOrder startappointmentInOrder = homepage.skipInsurance(driver);
			Thread.sleep(1000);
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		} else {
			appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
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
		AppointmentDateTime aptDateTime = provider.searchForProviderFromList(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
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
		Log4jUtil.log("--------Flow Starts---------------");
		AppointmentPage appointment;
		if (startOrderOn.equalsIgnoreCase("true")) {
			Boolean insuranceEnabled = true;
			if (insuranceEnabled) {
				Thread.sleep(3500);
			}

			StartAppointmentInOrder startappointmentInOrder = homepage.updateInsuranceInfo(driver, PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID,
					PSSConstants.INSURANCE_GROUPID, PSSConstants.INSURANCE_PRIMARYPHONE);
			Thread.sleep(1000);
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		} else {
			appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
		}
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Log4jUtil.log("does apt has a pop up? " + testData.getIsAppointmentPopup());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 10: Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());
		Thread.sleep(9000);
		Log4jUtil.log("Step 11: Verfiy Location Page and location to be selected = " + testData.getLocation());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Log4jUtil.log("current URL " + driver.getCurrentUrl());
		bookAppointment(false, aptDateTime, testData, driver);
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
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);
		bookAppointment(false, aptDateTime, testData, driver);
	}

	public void STBLFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		Log4jUtil.log("--------Flow Starts---------------");
		AppointmentPage appointment;
		if (startOrderOn.equalsIgnoreCase("true")) {
			Boolean insuranceEnabled = true;
			if (insuranceEnabled) {
				Thread.sleep(3500);
			}
			Speciality speciality = homepage.skipInsuranceForSpeciality(driver);
			StartAppointmentInOrder startappointmentInOrder = speciality.selectSpeciality(testData.getSpeciality());

			Thread.sleep(1000);
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		} else {
			appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
		}
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Log4jUtil.log("does apt has a pop up? " + testData.getIsAppointmentPopup());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));

		Log4jUtil.log("Step 10: Verfiy Provider Page and Provider = " + testData.getProvider());
		Log4jUtil.log(">>>>>>>>>> provider image present " + provider.providerImageSize());
		Location location = provider.selectLocation(testData.getProvider());
		Thread.sleep(9000);
		testData.setIsSearchLocationDisplayed(location.isSearchLocationEnabled());
		Log4jUtil.log(">>>>>>>>>> Is Location Search Enabled? " + location.isSearchLocationEnabled());

		Log4jUtil.log("Step 11: Verfiy Location Page and location to be selected = " + testData.getLocation());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Log4jUtil.log("current URL " + driver.getCurrentUrl());
		bookAppointment(false, aptDateTime, testData, driver);
	}

	public void STLBFlow(HomePage homepage, Appointment testData, String startOrderOn, WebDriver driver) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		Log4jUtil.log("--------Flow Starts---------------");
		AppointmentPage appointment = null;



		appointment = homepage.selectAppointment(testData.getSpeciality());

		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		Thread.sleep(1000);
		appointment.selectTypeOfAppointment(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Boolean Value of getIsAppointmentPopup   = " + Boolean.valueOf(testData.getIsAppointmentPopup()));

		Location location = appointment.selectTypeOfLocation(testData.getLocation(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		Log4jUtil.log("Step 10: Verfiy Location Page and location to be selected = " + testData.getLocation().trim());
		String loc = testData.getLocation();
		Log4jUtil.log(">>>>>>>>>> Is Location Search Enabled? " + location);

		Provider provider = location.searchProvider(loc);
		Log4jUtil.log("address = " + location.getAddressValue());
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Log4jUtil.log(">>>>>>>>>> provider image present " + provider.providerImageSize());


		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentDateTime aptDateTime = provider.searchForProviderFromList(testData.getProvider());



		aptDateTime.selectDate(testData.getIsNextDayBooking());
		Thread.sleep(6000);


		clickOnSubmitAppt(true, aptDateTime, testData, driver);

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
			ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime(testData.getIsNextDayBooking());
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


		HomePage homePage = scheduledappointment.backtoHomePage();
		Log4jUtil.log("home page loaded ? " + homePage.areBasicPageElementsPresent());

		homePage.bookedAppointmentInUpcomingList(testData.getAppointmentScheduledFromPM());

		Log4jUtil.log("Verify if appointment Scheduled from PM system does not have cancel button.");

		homePage.verifyAppointmentScheduledInPMSystem(testData.getAppointmentScheduledFromPM());

		if (testData.getIsCancelApt()) {
			Log4jUtil.log("Cancel the appointment which was booked");
			homePage.cancelAppointment(testData.getCancellationPolicyText());

		}

		Thread.sleep(8000);
		Log4jUtil.log("Step 14: Logout from PSS 2.0 Patient UI ");

		OnlineAppointmentScheduling onlineappointmentscheduling = homePage.logout();

	}

	public void selectAFlow(WebDriver driver, String rule, HomePage homepage, Appointment testData) throws Exception {

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

			testData.setIsInsuranceEnabled(true);

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
		/* @not sure about closing patient portal */
		driver.close();
		driver.switchTo().window(browserTabs.get(1));
		System.setProperty("current.window.handle", browserTabs.get(1));
		String pssPatientUrl = driver.getCurrentUrl();
		/* @ code only for dev3 */
		pssPatientUrl = pssPatientUrl.replaceAll("https", "http");
		Log4jUtil.log("Url = " + driver.getCurrentUrl());
		return pssPatientUrl;
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
		if (partnerPractice.equalsIgnoreCase(PSSConstants.ATHENA)) {
			propertyData.setAdminAthena(adminuser);
			propertyData.setAppointmentResponseAthena(testData);;
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

}
