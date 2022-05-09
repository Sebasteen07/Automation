//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.practice.tests;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.medfusion.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.medfusion.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;

public class AppoitmentRequest extends BaseTestNGWebDriver {

	public long ProceedAppoitmentRequest(WebDriver driver, Boolean checkDetails, String appointmentReason,
			String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

		log("Choose process option and respond to patient");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();

		return detailStep1.getCreatedTs();
	}

	public long ProceedAppoitmentRequesAttachmentt(WebDriver driver, Boolean checkDetails, String appointmentReason,
			String portalUrl, String doctorLogin, String doctorPassword, String MessageErrorfilePath,
			String MessagefilePath) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

		log("Choose process option and respond to patient");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmitAttachment(MessageErrorfilePath,
				MessagefilePath);

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();

		return detailStep1.getCreatedTs();
	}

	public long ProceedAppoitmentRequestcancel(WebDriver driver, Boolean checkDetails, String appointmentReason,
			String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForApprovedApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

		log("Choose process option and respond to patient");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));
		Thread.sleep(8000);
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseRequestCancelAndSubmit();

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();

		return detailStep1.getCreatedTs();
	}

	public long ProceedAppoitmentRequestCommunicateOnly(WebDriver driver, Boolean checkDetails,
			String appointmentReason, String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForCancelledApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

		log("Choose process option and respond to patient");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));
		Thread.sleep(8000);
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseCommunicateAndSubmit();

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();

		return detailStep1.getCreatedTs();
	}

	public long ProceedAppoitmentRequestUpdate(WebDriver driver, Boolean checkDetails, String appointmentReason,
			String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForApprovedApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

		log("Choose process option and respond to patient");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));
		Thread.sleep(8000);
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseUpdateAppointmentAndSubmit();

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();

		return detailStep1.getCreatedTs();
	}

	public long ProceedAppoitmentProcessExternallyRequest(WebDriver driver, Boolean checkDetails,
			String appointmentReason, String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

		log("Choose process option and respond to patient");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseprocessedExternallyAndSubmit();

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();

		return detailStep1.getCreatedTs();
	}

	public void ProceedAppoitmentRequestExternalProcess(WebDriver driver, Boolean checkDetails,
			String appointmentReason, String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForExternalProcessApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		
		log("Choose process option and Verify the Request is present ");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));

	}
	
	public void ProceedAppoitmentRequestSetToPending(WebDriver driver, Boolean checkDetails,
			String appointmentReason, String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForPendingApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");
		
		log("Choose process option and Verify the Request is present ");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));

	}
	
	public long ProceedAppoitmentSetToPendingRequest(WebDriver driver, Boolean checkDetails,
			String appointmentReason, String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

		log("Choose process option and respond to patient");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday",
					"Early Morning, Late Afternoon", appointmentReason));
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseSetToPendingAndSubmit();

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();

		return detailStep1.getCreatedTs();
	}
	
	public long ProceedFirstAvailableAppoitmentRequest(WebDriver driver, Boolean checkDetails, String appointmentReason,
			String portalUrl, String doctorLogin, String doctorPassword) throws Exception {
		IHGUtil.PrintMethodName();

		log("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, portalUrl);
		PracticeHomePage practiceHome = practiceLogin.login(doctorLogin, doctorPassword);

		log("Click Appt Request tab");
		ApptRequestSearchPage apptSearch = practiceHome.clickApptRequestTab();

		log("Search for appt requests");
		apptSearch.searchForApptRequests();
		ApptRequestDetailStep1Page detailStep1 = apptSearch.getRequestDetails(appointmentReason);
		assertNotNull(detailStep1, "The submitted patient request was not found in the practice");

		log("Choose process option and respond to patient");
		Thread.sleep(1000);
		if (checkDetails)
			assertTrue(detailStep1.checkAppointmentDetails("First Available", "Monday,Tuesday,Wednesday,Thursday,Friday",
					"Any Time Of Day", appointmentReason));

		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(), "Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();

		return detailStep1.getCreatedTs();
	}
}