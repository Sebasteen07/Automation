
// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONArray;
import org.openqa.selenium.WebDriver;
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
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
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
}