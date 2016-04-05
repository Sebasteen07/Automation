package com.intuit.ihg.product.practice.tests;

import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep1Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestDetailStep2Page;
import com.intuit.ihg.product.object.maps.practice.page.apptrequest.ApptRequestSearchPage;

public class AppoitmentRequest extends BaseTestNGWebDriver{
	
	public long ProceedAppoitmentRequest(WebDriver driver, Boolean checkDetails, String appointmentReason, String portalUrl, 
			String doctorLogin, String doctorPassword) throws Exception {
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
		if (checkDetails) assertTrue(detailStep1.checkAppointmentDetails("Any", "Monday,Tuesday,Wednesday,Thursday", 
				"Early Morning, Late Afternoon", appointmentReason));
		ApptRequestDetailStep2Page detailStep2 = detailStep1.chooseApproveAndSubmit();

		log("Confirm response details to patient");
		apptSearch = detailStep2.processApptRequest();
		assertTrue(apptSearch.isSearchPageLoaded(),"Expected the Appt Search Page to be loaded, but it was not.");

		log("Logout of Practice Portal");
		practiceHome.logOut();
		
		return detailStep1.getCreatedTs();
	}
}