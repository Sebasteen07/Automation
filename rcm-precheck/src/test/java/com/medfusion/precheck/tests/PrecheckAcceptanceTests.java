package com.medfusion.precheck.tests;



import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.mail.Mailinator;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.product.object.maps.precheck.page.DashboardLoginPage;
import com.medfusion.product.object.maps.precheck.page.AppointmentDetails.AppointmentDetailsPage;
import com.medfusion.product.object.maps.precheck.page.HomePage.HomePage;
import com.medfusion.product.precheck.PrecheckConstans;
import com.medfusion.product.precheck.PrecheckPatient;



/**
 * @Author:Jakub Calabek
 * @Date:05.06.2015
 */

@Test
public class PrecheckAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendAppoitnment() throws Exception {

		log(this.getClass().getName());
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();	
		
		log("Initialize Patient");
		PrecheckPatient patient =  new PrecheckPatient();
		patient.initPatientData();
		
		log("Loading Pre-Check login page");
		DashboardLoginPage dashboardLoginPage = new DashboardLoginPage(driver, testData.getUrl());
		
		HomePage homePage = dashboardLoginPage.login(testData.getDoctorLogin(), testData.getDoctorPassword());
		AppointmentDetailsPage appointmentDetailsPage = homePage.addNewAppointment();
		
		log(patient.getPatientId());
		
		appointmentDetailsPage.scheduleAppointment(PrecheckConstans.AppointmentDate, PrecheckConstans.ProviderName, patient.getPatientId(),
				patient.getFirstName(), patient.getMiddleName(), patient.getLastName(), PrecheckConstans.DOB, PrecheckConstans.PatientMailingAddress1,
				PrecheckConstans.PatientMailingAddress2, PrecheckConstans.PatientCity, PrecheckConstans.PatientZip,
				PrecheckConstans.PatientPhoneNumber, patient.getEmail(), "100", "300");
		
		
		log("Logging into Mailinator and getting PrecheckApp url");
		Mailinator mail = new Mailinator();
		String emailSubject = "You have an upcoming appointment!";
		String inEmail = " Check in online ";
		String url = mail.getLinkFromEmail(patient.getEmail(), emailSubject, inEmail);
		log(url);
		
	}
	
}
