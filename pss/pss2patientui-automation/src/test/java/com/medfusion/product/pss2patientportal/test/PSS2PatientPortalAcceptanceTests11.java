package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.Appointment.Speciality.Speciality;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.Resource.ManageResource;
import com.medfusion.product.object.maps.pss2.page.settings.AdminAppointment;
import com.medfusion.product.object.maps.pss2.page.settings.AdminPatientMatching;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.object.maps.pss2.page.settings.PatientFlow;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PSS2PatientPortalAcceptanceTests11 extends BaseTestNGWebDriver {

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void VerifyPcpDisplayedOnTheUIwhenSharePatientIsOnWithSpecialty_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("ct.book.name.ng");
		String providerName1 = propertyData.getProperty("ct.book.name1.ng");
		String appointmentTypeName = propertyData.getProperty("ct.appointment.type.ng");
		String speciality = propertyData.getProperty("ct.speciality.ng");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		patientflow.turnOnProvider();
		adminUtils.setRulesWithSpeciality(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());
		Log4jUtil.log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			Log4jUtil.log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			Log4jUtil.log("Status of PCP  OFF and Clicked on ON");
		} else {
			Log4jUtil.log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clickSpecialityTab();
		manageResource.resourceSearchSpeciality(speciality);
		Thread.sleep(1000);
		manageResource.enableSpeciality();
		manageResource.clearSearchResource();
		manageResource.selectResource(providerName1);
		manageResource.clickGeneralTab();
		manageResource.enableSharePatient();
		manageResource.clickSpecialityTab();
		manageResource.resourceSearchSpeciality(speciality);
		Thread.sleep(1000);
		manageResource.enableSpeciality();
		adminUtils.pageRefresh(driver);
		manageResource.logout();
		
		String fn = propertyData.getProperty("ct.fn.pm10.ng");
		String ln = propertyData.getProperty("ct.ln.pm10.ng");
		String dob = propertyData.getProperty("ct.dob.pm10.ng");
		String email = propertyData.getProperty("ct.email.pm10.ng");
		String gender = propertyData.getProperty("ct.gender.pm10.ng");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.ng");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.ng");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		Speciality specialityObj = homePage.skipInsuranceForSpeciality(driver);
		StartAppointmentInOrder startAppointmentInOrder = specialityObj.selectSpeciality(speciality);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> l2= new ArrayList<String>();
		l2=provider.getBookList();	
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.contains(providerName1));
		assertTrue(l2.contains(providerName));
		logStep("Verify Provider Page and PCP = " + providerName);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void VerifyPcpDisplayedOnTheUIwhenSharePatientIsOnWithSpecialty_GE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("ct.book.name.ge");
		String providerName1 = propertyData.getProperty("ct.book.name1.ge");
		String appointmentTypeName = propertyData.getProperty("ct.appointment.type.ge");
		String speciality = propertyData.getProperty("ct.speciality.ge");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesWithSpeciality(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());
		Log4jUtil.log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			Log4jUtil.log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			Log4jUtil.log("Status of PCP  OFF and Clicked on ON");
		} else {
			Log4jUtil.log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clickSpecialityTab();
		manageResource.resourceSearchSpeciality(speciality);
		Thread.sleep(1000);
		manageResource.enableSpeciality();
		manageResource.clearSearchResource();
		manageResource.selectResource(providerName1);
		manageResource.clickGeneralTab();
		manageResource.enableSharePatient();
		manageResource.clickSpecialityTab();
		manageResource.resourceSearchSpeciality(speciality);
		Thread.sleep(1000);
		manageResource.enableSpeciality();
		adminUtils.pageRefresh(driver);
		manageResource.logout();
		
		String fn = propertyData.getProperty("ct.fn.pm10.ge");
		String ln = propertyData.getProperty("ct.ln.pm10.ge");
		String dob = propertyData.getProperty("ct.dob.pm10.ge");
		String email = propertyData.getProperty("ct.email.pm10.ge");
		String gender = propertyData.getProperty("ct.gender.pm10.ge");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.ge");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.ge");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		Speciality specialityObj = homePage.skipInsuranceForSpeciality(driver);
		StartAppointmentInOrder startAppointmentInOrder = specialityObj.selectSpeciality(speciality);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> l2= new ArrayList<String>();
		l2=provider.getBookList();	
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.contains(providerName1));
		assertTrue(l2.contains(providerName));
		logStep("Verify Provider Page and PCP = " + providerName);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void VerifyPcpDisplayedOnTheUIwhenSharePatientIsOnWithSpecialty_GW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("ct.book.name.gw");
		String providerName1 = propertyData.getProperty("ct.book.name1.gw");
		String appointmentTypeName = propertyData.getProperty("ct.appointment.type.gw");
		String speciality = propertyData.getProperty("ct.speciality.gw");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesWithSpeciality(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());
		Log4jUtil.log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			Log4jUtil.log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			Log4jUtil.log("Status of PCP  OFF and Clicked on ON");
		} else {
			Log4jUtil.log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clickSpecialityTab();
		manageResource.resourceSearchSpeciality(speciality);
		Thread.sleep(1000);
		manageResource.enableSpeciality();
		manageResource.clearSearchResource();
		manageResource.selectResource(providerName1);
		manageResource.clickGeneralTab();
		manageResource.enableSharePatient();
		manageResource.clickSpecialityTab();
		manageResource.resourceSearchSpeciality(speciality);
		Thread.sleep(1000);
		manageResource.enableSpeciality();
		adminUtils.pageRefresh(driver);
		manageResource.logout();
		
		String fn = propertyData.getProperty("ct.fn.pm10.gw");
		String ln = propertyData.getProperty("ct.ln.pm10.gw");
		String dob = propertyData.getProperty("ct.dob.pm10.gw");
		String email = propertyData.getProperty("ct.email.pm10.gw");
		String gender = propertyData.getProperty("ct.gender.pm10.gw");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm10.gw");
		String zipCode = propertyData.getProperty("ct.zip.code.pm10.gw");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		Speciality specialityObj = homePage.skipInsuranceForSpeciality(driver);
		StartAppointmentInOrder startAppointmentInOrder = specialityObj.selectSpeciality(speciality);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> l2= new ArrayList<String>();
		l2=provider.getBookList();	
		log("List of book from Patient UI- " + l2);
		assertTrue(l2.contains(providerName1));
		assertTrue(l2.contains(providerName));
		logStep("Verify Provider Page and PCP = " + providerName);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	
	}
}


