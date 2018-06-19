package com.medfusion.product.pss2patientui.utils;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

import net.fortuna.ical4j.data.ParserException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.PrivacyPolicy;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Insurance.UpdateInsurancePage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.pss2patientui.pojo.Appointment;

public class PSSPatientUtils {

	public void readICSFile(String path) throws IOException, ParserException {
		Log4jUtil.log("Reading ICS file for the given path. " + path);
		ICSFileReader icsfilereader = new ICSFileReader();
		icsfilereader.ICSFile(path);
		Log4jUtil.log("Is file deleted ? "+deleteFile(path));
	}

	public String filePath() {
		String home = System.getProperty("user.home");
		File latestFile = lastFileModified(home + PSSConstants.DOWNLOADFILENAME);
		assertTrue(latestFile.getAbsolutePath().contains("ics"), "ICS file not found.");

		return latestFile.getAbsolutePath();
	}

	public void LBTFlow(HomePage homepage, Appointment testData, String startOrderOn) throws Exception {
		Log4jUtil.log("Step 8: Select location for appointment.");
		Location location;
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		} else {
			location = homepage.selectLocation(PSSConstants.START_LOCATION);
		}
		Log4jUtil.log("Step 9: Verfiy location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		Provider provider = location.searchProvider(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Provider Page and provider to be selected = " + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentPage appointment = provider.selectAppointment(testData.getProvider());
		Log4jUtil.log("Step 11: Verfiy Appointment Page and Appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectTypeOfAppointment(testData.getAppointmenttype(), false);
		Log4jUtil.log("Step 12: Select avaiable Date ");
		String date = aptDateTime.selectDate();
		Log4jUtil.log("date- "+date);
		bookAppointment(true, aptDateTime, testData);
	}

	public void BLTFlow(HomePage homepage, Appointment testData, String startOrderOn) throws Exception {
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		if (startOrderOn.equalsIgnoreCase("true")) {
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
		AppointmentDateTime aptDateTime = appointmentpage.selectTypeOfAppointment(testData.getAppointmenttype(), true);
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime,testData);
	}

	public void BTLFlow(HomePage homepage, Appointment testData, String startOrderOn) throws Exception {
		Log4jUtil.log("Step 8: Select Provider for appointment.");
		Provider provider = null;
		if (startOrderOn.equalsIgnoreCase("true")) {
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
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime, testData);
	}

	public void LTBFlow(HomePage homepage, Appointment testData, String startOrderOn) throws Exception {
		Log4jUtil.log("Step 8: Select Location for appointment.");
		Location location;
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		} else {
			location = homepage.selectLocation(PSSConstants.START_LOCATION);
		}
		Log4jUtil.log("Step 9: Verfiy Location Page and location =" + testData.getLocation());
		assertTrue(location.areBasicPageElementsPresent());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		Log4jUtil.log("Step 10: Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Thread.sleep(8000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), false);
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		Thread.sleep(22000);
		AppointmentDateTime aptDateTime = provider.selectDateTime(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime, testData);
	}

	public void TLBFlow(HomePage homepage, Appointment testData, String startOrderOn) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		AppointmentPage appointment;
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		} else {
			Log4jUtil.log(" PSSConstants: "+PSSConstants.START_APPOINTMENT);
			appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
		}
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		assertTrue(appointment.areBasicPageElementsPresent());
		Location location = appointment.selectTypeOfLocation(testData.getAppointmenttype(), false);
		Log4jUtil.log("Step 10: Verfiy Location Page and location to be selected = " + testData.getLocation());
		
		assertTrue(location.areBasicPageElementsPresent());
		Provider provider = location.searchProvider(testData.getLocation());
		Log4jUtil.log("address = "+location.getAddressValue());
		Log4jUtil.log("Step 11: Verfiy Provider Page and Provider = " + testData.getProvider());
		assertTrue(provider.areBasicPageElementsPresent());
		AppointmentDateTime aptDateTime = provider.selectDateTime(testData.getProvider());
		assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime, testData);
	}
	
	public void TBLFlow(HomePage homepage, Appointment testData, String startOrderOn) throws Exception {
		Log4jUtil.log("Step 8: Select Appointment for appointment.");
		AppointmentPage appointment;
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		} else {
			appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
		}
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		//assertTrue(appointment.areBasicPageElementsPresent());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 10: Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());
		Log4jUtil.log("Step 11: Verfiy Location Page and location to be selected = " + testData.getLocation());
		//assertTrue(location.areBasicPageElementsPresent());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		//assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime, testData);
	}
	public void TBLFlow(HomePage homepage, Appointment testData, String startOrderOn, AppointmentPage appointment) throws Exception {
		if (startOrderOn.equalsIgnoreCase("true")) {
			StartAppointmentInOrder startappointmentInOrder = homepage.selectSpeciality(testData.getSpeciality());
			appointment = startappointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		} else {
			appointment = homepage.selectAppointment(PSSConstants.START_APPOINTMENT);
		}
		Log4jUtil.log("Step 9: Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		//assertTrue(appointment.areBasicPageElementsPresent());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), true);
		Log4jUtil.log("Step 10: Verfiy Provider Page and Provider = " + testData.getProvider());
		Location location = provider.selectLocation(testData.getProvider());
		Log4jUtil.log("Step 11: Verfiy Location Page and location to be selected = " + testData.getLocation());
		//assertTrue(location.areBasicPageElementsPresent());
		AppointmentDateTime aptDateTime = location.selectDatTime(testData.getLocation());
		//assertTrue(aptDateTime.areBasicPageElementsPresent());
		aptDateTime.selectDate();
		bookAppointment(true, aptDateTime, testData);
	}

	public Boolean deleteFile(String fileName) {
		Boolean isFileDeleted = false;
		Log4jUtil.log("filePath= "+fileName);

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

	public void bookAppointment(Boolean isInsuranceDisplated, AppointmentDateTime aptDateTime, Appointment testData) throws Exception {
		Log4jUtil.log("Step 12: Verify Confirmation page and Scheduled page");
		Log4jUtil.log("Time difference is "+aptDateTime.getTimeDifference()+" minutes");
		if (isInsuranceDisplated) {
			UpdateInsurancePage updateinsurancePage = aptDateTime.selectAppointmentTimeIns();
			ConfirmationPage confirmationpage = updateinsurancePage.skipInsuranceUpdate();
			appointmentToScheduled(confirmationpage, testData);
		} else {
			ConfirmationPage confirmationpage = aptDateTime.selectAppointmentDateTime();
			appointmentToScheduled(confirmationpage, testData);
		}
	}

	public void appointmentToScheduled(ConfirmationPage confirmationpage, Appointment testData) throws Exception {
		Log4jUtil.log("Step 13: Verify if Appointment is scheduled and download ics file");
		assertTrue(confirmationpage.areBasicPageElementsPresent());
		for(WebElement ele :confirmationpage.getAppointmentDetails()) {
			Log4jUtil.log("apt Details= "+ele.getText());
		}
		ScheduledAppointment scheduledappointment = confirmationpage.appointmentConfirmed();
		Log4jUtil.log("appointment ID = " + scheduledappointment.getAppointmentID());
		assertTrue(scheduledappointment.areBasicPageElementsPresent());
		scheduledappointment.downloadCalander();
		Thread.sleep(1500);
		readICSFile(filePath());

		// Mailinator email = new Mailinator();
		// email.getLinkFromEmail(testData.getEmail(), testData.getEmaiSubject(), testData.getFindInEmail(), Integer.parseInt(testData.getRetries()));

		HomePage homePage = scheduledappointment.backtoHomePage();
		// assertTrue(homePage.areBasicPageElementsPresent());
		Log4jUtil.log("Step 14: Logout from PSS 2.0 Patient UI ");
		Thread.sleep(8000);
		homePage.logout();
	}

	public void selectAFlow(WebDriver driver, String rule, HomePage homepage, Appointment testData) throws Exception {
		//checkPrivacyPage(driver);
		Log4jUtil.log("is pop up" + homepage.isPopUP());
		if(homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(12000);
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			LBTFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.LTB)) {
			LTBFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			BLTFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BTL)) {
			BTLFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.TLB)) {
			TLBFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.TBL)) {
			TBLFlow(homepage, testData, "false");
		}
	}

	public void selectAFlow(WebDriver driver, String rule, HomePage homepage, Appointment testData,AppointmentPage appointment) throws Exception {
		//checkPrivacyPage(driver);
		
		if(homepage.isPopUP()) {
			homepage.popUPClick();
		}
		Thread.sleep(7000);
		if (rule.equalsIgnoreCase(PSSConstants.LBT)) {
			LBTFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.LTB)) {
			LTBFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BLT)) {
			BLTFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.BTL)) {
			BTLFlow(homepage, testData, "false");
		}
		if (rule.equalsIgnoreCase(PSSConstants.TLB)) {
			//TLBFlow(homepage, testData, "false",appointment);
		}
		if (rule.equalsIgnoreCase(PSSConstants.TBL)) {
			TBLFlow(homepage, testData, "false",appointment);
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
		ArrayList<String> browserTabs = new ArrayList<String> (driver.getWindowHandles());
		
		/* @not sure about closing patient portal*/
		driver.close();
		driver.switchTo().window(browserTabs.get(1));
		System.setProperty("current.window.handle", browserTabs.get(1));
	    String pssPatientUrl = driver.getCurrentUrl();
	    
	    /* @ code only for dev3 */
	    pssPatientUrl = pssPatientUrl.replaceAll("https","http");	
	    //driver.get(pssPatientUrl);
	    Log4jUtil.log("Url = "+driver.getCurrentUrl());
	    
	    return pssPatientUrl;
	}
	
	public void fillPatientDetails(Boolean insuranceSelected, Appointment testData, LoginlessPatientInformation loginlesspatientinformation) {
		if (insuranceSelected) {
			Log4jUtil.log("InsurancePage=" + insuranceSelected);
			NewPatientInsuranceInfo newpatientinsuranceinfo =
					loginlesspatientinformation.fillPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
							testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber(), testData.getCity(), testData.getStreet());

			newpatientinsuranceinfo.fillNewPatientInsuranceInfo(PSSConstants.INSURANCE_CARRIER, PSSConstants.INSURANCE_MEMBERID, PSSConstants.INSURANCE_GROUPID,
					PSSConstants.INSURANCE_PRIMARYPHONE);
		} else {
			Log4jUtil.log("InsurancePage=" + insuranceSelected);
			loginlesspatientinformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
					testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber(), testData.getCity(), testData.getStreet());
		}
	}

}