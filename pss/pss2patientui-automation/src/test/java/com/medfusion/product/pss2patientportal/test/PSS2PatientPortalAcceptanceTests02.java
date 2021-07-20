// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
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
	public void testE2EAnonymousAT(String partnerPractice) throws Exception {
		
		log("Step 1: set test data for new patient ");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		testData.setAnonymousFlow(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.setTestData("AT", testData, adminuser);
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
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2EAnonymousAT")
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
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT( testData, driver);

	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAnonymousNG() throws Exception {
		
		log("Step 1: set test data for new patient ");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		testData.setAnonymousFlow(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.setTestData("NG", testData, adminuser);
		adminuser.setIsAnonymousFlow(true);
		adminuser.setIsExisting(true);

		testData.setAnonymousFlow(true);
		
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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2EAnonymousNG")
	public void testRescheduleAnonymousviaEmailNG() throws Exception {
		
		log("Test to verify if Reschedule an Appointment via Email Notification");		
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

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
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT( testData, driver);

	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2EAnonymousNG")
	public void testCancelAnonymousviaEmailNG() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();
		
		testData.setFutureApt(true);

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		Thread.sleep(1000);
		
		HomePage homepage;

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(2000);
		if (patientIdentificationPage.isPopUPAn()) {
			patientIdentificationPage.popUPAnClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getGmailUserName());
	
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void TestDeleteEmail() throws InterruptedException {
		
		PSSPatientUtils psspatientutils= new PSSPatientUtils();
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", "ast@mailinator.com");
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAnonymousviaEmailAT() throws Exception {
		log("Test to verify if Cancel Appointment for anonymous flow for the Athena Partners.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();
		
		testData.setFutureApt(true);

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		Thread.sleep(1000);
		
		HomePage homepage;

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmaiSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(2000);
		if (patientIdentificationPage.isPopUPAn()) {
			patientIdentificationPage.isPopUPAn();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com", testData.getGmailUserName());
	}




	@DataProvider(name = "partnerType")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] {{"AT"}};
		return obj;
	}



}
