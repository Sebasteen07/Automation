// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import java.util.ArrayList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous.AnonymousDismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.CancelRescheduleDecisionPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.PatientIdentificationPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.settings.AdminAppointment;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;

public class PSS2PatientPortalAcceptanceTests02 extends BaseTestNGWebDriver {

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAnonymousAthena(String partnerPractice) throws Exception {
		
		log("Step 1: set test data for new patient ");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		testData.setAnonymousFlow(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);
		adminuser.setIsAnonymousFlow(true);
		adminuser.setIsExisting(true);
		
		testData.setFutureApt(true);
		
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		log("Step 3: Fetch the rules set in Admin");
		String rule = adminuser.getRule();

		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 4: Move to PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlAnonymous());
		AnonymousDismissPage anonymousDismissPage = new AnonymousDismissPage(driver, testData.getUrlAnonymous());
		HomePage homePage = anonymousDismissPage.clickDismiss();
		Thread.sleep(1000);
		psspatientutils.selectAFlow(driver, rule, homePage, testData);
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2EAnonymousAthena")
	public void testRescheduleAnonymousviaEmailAT() throws Exception {
		
		log("Test to verify if Reschedule an Appointment via Email Notification");		
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);
		
		log("Cancel Reason are as below-"+adminCancelReasonList);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);
		
		log(CancelReschedulelink+" ---This is cancel link");		
		
		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,CancelReschedulelink);
		
		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(2000);
		if (patientIdentificationPage.isPopUPAn()) {
			patientIdentificationPage.popUPAnClick();
		}
		
		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());		
		
		
		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage =patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.areBasicPageElementsPresent();
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT( testData, driver);

	}

	@DataProvider(name = "partnerType")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] {{"AT"}, {"NG"}};
		return obj;
	}



}
