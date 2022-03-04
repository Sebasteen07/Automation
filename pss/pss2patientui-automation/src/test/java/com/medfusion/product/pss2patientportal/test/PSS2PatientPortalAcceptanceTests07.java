
// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.eh.core.dto.Timestamp;

import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.ManageAppointmentType;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.Scheduled.ScheduledAppointment;
import com.medfusion.product.object.maps.pss2.page.settings.AdminPatientMatching;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.object.maps.pss2.page.settings.PatientFlow;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;


public class PSS2PatientPortalAcceptanceTests07 extends BaseTestNGWebDriver {
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void excudeSlotsWithShowProviderOFF_NG() throws Exception {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		String appointmentType = propertyData.getProperty("appointment.type.ng");
		String firstValue = propertyData.getProperty("firstvalue.excludeslot.pm01");
		String secondValue = propertyData.getProperty("secondvalue.excludeslot.pm01");
		adminUtils.exclueSlotsWithShowProviderOff(driver, adminUser, testData, appointmentType, firstValue, secondValue);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptTyper(testData.getAppointmenttype(),
		Boolean.valueOf(testData.getIsAppointmentPopup()));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App " + date);	
		String time = aptDateTime.getfirsttime();
		log("First time is   " + time);
		assertEquals(time, firstValue);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void excudeSlotsWithShowProviderOFF_AT() throws Exception {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		String appointmentType = propertyData.getProperty("appointmenttype.at");
		String firstValue = propertyData.getProperty("firstvalue.excludeslot.pm01");
		String secondValue = propertyData.getProperty("secondvalue.excludeslot.pm01");
		adminUtils.exclueSlotsWithShowProviderOff(driver, adminUser, testData, appointmentType, firstValue, secondValue);
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());

		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		AppointmentDateTime aptDateTime = appointment.selectAptTyper(testData.getAppointmenttype(),
		Boolean.valueOf(testData.getIsAppointmentPopup()));
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App " + date);	
		String time = aptDateTime.getfirsttime();
		log("First time is   " + time);
		assertEquals(time, firstValue);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientMatching_NG() throws Exception {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String appointmentType = propertyData.getProperty("appointment.type.ng07");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientFlow = pssPracticeConfig.gotoPatientFlowTab();
		AdminPatientMatching adminPatientMatching = patientFlow.gotoPatientMatchingTab();
		adminPatientMatching.patientMatchingSelection();
		adminUtils.pageRefresh(driver);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		String firstName = propertyData.getProperty("firstname.ng07");
		String lastName = propertyData.getProperty("lastname.ng07");
		String dob = propertyData.getProperty("dob.ng07");
		String email = propertyData.getProperty("email.ng07");
		String gender = propertyData.getProperty("gender.ng07");
		String zipCodeValue = propertyData.getProperty("zip.code.ng07");
		String phoneNumber = propertyData.getProperty("primary.number.ng07");
		String alternateNumber = propertyData.getProperty("alternate.number.ng07");
		
		HomePage homePage = loginlessPatientInformation.fillPatientFormWithAlternateNumber(firstName, lastName, dob, email, gender, 
							zipCodeValue, phoneNumber, alternateNumber);

		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Thread.sleep(10000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		homePage.logout();
		HomePage homePage1 = loginlessPatientInformation.fillPatientFormWithAlternateNumber(firstName, lastName, dob, email, gender, 
				zipCodeValue, phoneNumber, ""); 
		List<WebElement> selectUpcomingApptList = driver.findElements(By.xpath("//*[@id='upcomingappoitment']/div"));
		if(selectUpcomingApptList.size()>0) {
			log("Pass");
			Assert.assertTrue(true);
		}else {
			log("Fail");
			Assert.assertTrue(false);
		}
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientMatching_GE() throws Exception {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String appointmentType = propertyData.getProperty("appointment.type.ge07");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientFlow = pssPracticeConfig.gotoPatientFlowTab();
		AdminPatientMatching adminPatientMatching = patientFlow.gotoPatientMatchingTab();
		adminPatientMatching.patientMatchingSelection();
		adminUtils.pageRefresh(driver);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		String firstName = propertyData.getProperty("firstname.ge07");
		String lastName = propertyData.getProperty("lastname.ge07");
		String dob = propertyData.getProperty("dob.ge07");
		String email = propertyData.getProperty("email.ge07");
		String gender = propertyData.getProperty("gender.ge07");
		String zipCodeValue = propertyData.getProperty("zip.code.ge07");
		String phoneNumber = propertyData.getProperty("primary.number.ge07");
		String alternateNumber = propertyData.getProperty("alternate.number.ge07");
		
		HomePage homePage = loginlessPatientInformation.fillPatientFormWithAlternateNumber(firstName, lastName, dob, email, gender, 
							zipCodeValue, phoneNumber, alternateNumber);

		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Thread.sleep(10000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		homePage.logout();
		HomePage homePage1 = loginlessPatientInformation.fillPatientFormWithAlternateNumber(firstName, lastName, dob, email, gender, 
				zipCodeValue, phoneNumber, ""); 
		List<WebElement> selectUpcomingApptList = driver.findElements(By.xpath("//*[@id='upcomingappoitment']/div"));
		if(selectUpcomingApptList.size()>0) {
			log("Pass");
			Assert.assertTrue(true);
		}else {
			log("Fail");
			Assert.assertTrue(false);
		}
		
	}
	
}