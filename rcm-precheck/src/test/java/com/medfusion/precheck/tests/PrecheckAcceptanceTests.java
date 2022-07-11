//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.precheck.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.integrationplatform.utils.YopMailUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.precheck.page.DashboardLoginPage;
import com.medfusion.product.object.maps.precheck.page.AppointmentDetails.AppointmentDetailsPage;
import com.medfusion.product.object.maps.precheck.page.HomePage.HomePage;
import com.medfusion.product.object.maps.precheck.page.patient.DemographicsPage;
import com.medfusion.product.object.maps.precheck.page.patient.InsurancePage;
import com.medfusion.product.object.maps.precheck.page.patient.PatientHomePage;
import com.medfusion.product.object.maps.precheck.page.patient.PatientLoginPage;
import com.medfusion.product.precheck.PrecheckConstants;
import com.medfusion.product.precheck.PrecheckPatient;

@Test
public class PrecheckAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	public HomePage dashboardLogin(PropertyFileLoader testData) throws IOException {
		log(this.getClass().getName());

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		DashboardLoginPage dashboardLoginPage = new DashboardLoginPage(driver, testData.getUrl());
		assertTrue(dashboardLoginPage.assessLoginPageElements(), "Login page not loaded properly.");
		return dashboardLoginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSendFillAppoitnmentE2E() throws Exception {

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Initialize Patient");
		PrecheckPatient patient = new PrecheckPatient();
		patient.initPatientData();

		HomePage homePage = this.dashboardLogin(testData);
		AppointmentDetailsPage appointmentDetailsPage = homePage.addNewAppointment();

		log("ID: " + patient.getPatientId());
		String dobDashboard = PrecheckConstants.DOB_YEAR + PrecheckConstants.DOB_MONTH + PrecheckConstants.DOB_DAY;
		log("ID: " + patient.getPatientId());

		appointmentDetailsPage.scheduleAppointment(PrecheckConstants.AppointmentDate, PrecheckConstants.Location, PrecheckConstants.ProviderName,
				patient.getPatientId(), patient.getFirstName(), patient.getMiddleName(), patient.getLastName(), dobDashboard, PrecheckConstants.PatientMailingAddress1,
				PrecheckConstants.PatientMailingAddress2, PrecheckConstants.PatientCity, PrecheckConstants.PatientZip, PrecheckConstants.PatientPhoneNumber,
				patient.getEmail(), "100", "300");


		log("Logging into Yopmail and getting PrecheckApp url");
		YopMailUtils mail = new YopMailUtils(driver);
		String emailSubject = "You have an upcoming appointment!";
		String inEmail = "Check in";
		String url = mail.getLinkFromEmail(patient.getEmail(), emailSubject, inEmail, 10);
		assertTrue(url != null, "Invite e-mail not found.");
		log(url);

		log("Opening the Appointment link from the e-mail.");
		PatientLoginPage patientLoginPage = new PatientLoginPage(driver, url);
		assertTrue(patientLoginPage.assessLoginPageElements(), "Login Page not loaded properly.");
		String dobPatientLogin = PrecheckConstants.DOB_DAY + PrecheckConstants.DOB_MONTH + PrecheckConstants.DOB_YEAR;
		PatientHomePage patientHomePage = patientLoginPage.login(PrecheckConstants.PatientZip, dobPatientLogin);
		log("Appointment created for: " + patientHomePage.getAppointmentDateTime());

		log("Verify Demographics.");
		DemographicsPage demographicsPage = patientHomePage.clickDemographics();
		demographicsPage.fillInDemogprahicsData(patient.getFirstName(), patient.getMiddleName(), patient.getLastName(), dobPatientLogin,
				PrecheckConstants.PatientMailingAddress1, PrecheckConstants.PatientMailingAddress2, PrecheckConstants.PatientCity, PrecheckConstants.PatientState,
				PrecheckConstants.PatientZip, PrecheckConstants.PatientPhoneNumber, patient.getEmail(), PrecheckConstants.PatientPharmacy,
				PrecheckConstants.PatientPharmacyNumber);
		patientHomePage = demographicsPage.clickConfirmDemographics();
		assertTrue(patientHomePage.isDemographicsFinished(), "Demographics were not filled out.");

		log("Provide Insurance");
		InsurancePage insurancePage = patientHomePage.clickInsurance();
		insurancePage.clickNoInsurance();
		assertTrue(patientHomePage.isInsuranceFinished(), "Insurance was not filled out.");

		log("Log out");
		patientLoginPage = patientHomePage.logout();
		assertTrue(patientLoginPage.assessLoginPageElements(), "Logging out was not successful.");
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDashboardLoginLogout() throws IOException {
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		HomePage homePage = this.dashboardLogin(testData);
		DashboardLoginPage dashboardLoginPage = homePage.logOut();
		assertTrue(dashboardLoginPage.assessLoginPageElements(), "Log out was not succesful.");


	}

}
