package com.medfusion.product.pss2patientui.utils;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import net.fortuna.ical4j.data.ParserException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Insurance.UpdateInsurancePage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.pss2patientui.pojo.Appointment;

public class PSSPatientUtils {

	public void readICSFile(String path) throws IOException, ParserException {
		Log4jUtil.log("path :" + path);
		ICSFileReader icsfilereader = new ICSFileReader();
		icsfilereader.ICSFile(path);
	}

	public String filePath() {
		String home = System.getProperty("user.home");
		File latestFile = lastFileModified(home + PSSConstants.DOWNLOADFILENAME);
		return latestFile.getAbsolutePath();
	}

	public void confirmationFlow(AppointmentDateTime aptDateTime, Appointment testData) throws IOException, ParserException {
		ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime();
		assertTrue(confirmationpage.areBasicPageElementsPresent());
		ScheduledAppointment scheduledappointment = confirmationpage.appointmentConfirmed();
		assertTrue(scheduledappointment.areBasicPageElementsPresent());
		scheduledappointment.downloadCalander();
		readICSFile(testData.getIcsFilePath());
	}

	public void confirmationFlowFromInsurance(AppointmentDateTime aptDateTime, Appointment testData) throws IOException, ParserException {
		UpdateInsurancePage updateinsurancePage = aptDateTime.selectAppointmentTimeIns();
		ConfirmationPage confirmationpage = updateinsurancePage.skipInsuranceUpdate();
		assertTrue(confirmationpage.areBasicPageElementsPresent());
		ScheduledAppointment scheduledappointment = confirmationpage.appointmentConfirmed();
		assertTrue(scheduledappointment.areBasicPageElementsPresent());
		scheduledappointment.downloadCalander();
		readICSFile(testData.getIcsFilePath());
	}

	public void locationFlow(HomePage homepage, Appointment testData, AppointmentDateTime aptDateTime) {
		Log4jUtil.log("Step 11: Select Location in the patient appointment flow");
		Location location = homepage.selectLocation(PSSConstants.START_LOCATION);
		Log4jUtil.log("Step 12: Verifying basic elements for location");
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 13: Verifying basic elements for appointment");
		assertTrue(appointment.areBasicPageElementsPresent());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), false);
		aptDateTime = provider.selectDateTime(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate();
	}

	public void providerFlow(HomePage homepage, Appointment testData, AppointmentDateTime aptDateTime) {
		Provider provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		Location location = provider.selectLocation(testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		aptDateTime = appointment.selectTypeOfAppointment(testData.getAppointmenttype(), false);
		aptDateTime.selectDate();
		ConfirmationPage confirmationPage = aptDateTime.selectAppointmentDateTime();
		ScheduledAppointment scheduledappointment = confirmationPage.appointmentConfirmed();
		scheduledappointment.downloadCalander();
	}

	public void appointmentFlow(HomePage homepage, Appointment testData, AppointmentDateTime aptDateTime) {

		AppointmentPage appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
		Provider provider = appointment.selectTypeOfProvider(testData.getProvider(), false);
		Location location = provider.selectLocation(testData.getLocation());
		aptDateTime = location.selectDatTime(testData.getDatetime());
		aptDateTime.selectDate();
		ConfirmationPage confirmationPage = aptDateTime.selectAppointmentDateTime();
		ScheduledAppointment scheduledappointment = confirmationPage.appointmentConfirmed();
		scheduledappointment.downloadCalander();
	}

	public void LBTFlow(HomePage homepage, Appointment testData, AppointmentDateTime aptDateTime, String startOrderOn) throws Exception {
		Log4jUtil.log("In LBT flow..");
		Location location;
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		} else {
			location = homepage.selectLocation(PSSConstants.START_LOCATION);
		}
		Log4jUtil.log("Step 12: Verifying basic elements for location");
		assertTrue(location.areBasicPageElementsPresent());
		Provider provider = location.searchProvider(testData.getLocation());
		Log4jUtil.log("Step 13: Verifying basic elements for appointment");
		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentPage appointment = provider.selectAppointment(testData.getProvider());
		aptDateTime = appointment.selectTypeOfAppointment(testData.getAppointmenttype(), false);
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime);

	}

	public void BLTFlow(HomePage homepage, Appointment testData, String startOrderOn) throws Exception {
		Provider provider = null;
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		} else {
			provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		}
		Location location = provider.selectLocation(testData.getProvider());
		AppointmentPage appointmentpage = location.selectAppointment(testData.getLocation());
		AppointmentDateTime aptDateTime = appointmentpage.selectTypeOfAppointment(testData.getAppointmenttype(), true);
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime);
	}

	public void BTLFlow(HomePage homepage, Appointment testData, String startOrderOn) throws Exception {
		Provider provider = null;
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			provider = startappointmentInOrder.selectFirstProvider(PSSConstants.START_PROVIDER);
		} else {
			provider = homepage.selectProvider(PSSConstants.START_PROVIDER);
		}
		AppointmentPage appointmentpage = provider.selectAppointment(testData.getProvider());
		Location location = appointmentpage.selectTypeOfLocation(testData.getAppointmenttype(), true);
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime);
	}

	public void LTBFlow(HomePage homepage, Appointment testData, String startOrderOn) {
		Location location;
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		} else {
			Log4jUtil.log("Step 11: Select Location in the patient appointment flow");
			location = homepage.selectLocation(PSSConstants.START_LOCATION);
		}
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		assertTrue(appointment.areBasicPageElementsPresent());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), false);
		AppointmentDateTime aptDateTime = provider.selectDateTime(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate();
	}

	public Boolean deleteFile(String fileName) {
		Boolean isFileDeleted = false;
		try {
			File file = new File(fileName);
			if (file.delete()) {
				Log4jUtil.log(file.getName() + " is deleted!");
				isFileDeleted = true;
			} else {
				Log4jUtil.log("Delete operation is failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFileDeleted;
	}

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
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

	public void bookAppointment(Boolean isInsuranceDisplated, AppointmentDateTime aptDateTime) throws Exception {
		if (isInsuranceDisplated) {
			UpdateInsurancePage updateinsurancePage = aptDateTime.selectAppointmentTimeIns();
			ConfirmationPage confirmationpage = updateinsurancePage.skipInsuranceUpdate();
			appointmentToScheduled(confirmationpage);
		} else {
			ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime();
			appointmentToScheduled(confirmationpage);
		}
	}

	public void appointmentToScheduled(ConfirmationPage confirmationpage) throws Exception {
		assertTrue(confirmationpage.areBasicPageElementsPresent());
		ScheduledAppointment scheduledappointment = confirmationpage.appointmentConfirmed();
		assertTrue(scheduledappointment.areBasicPageElementsPresent());
		scheduledappointment.downloadCalander();
		Thread.sleep(900);
		readICSFile(filePath());
	}
}
