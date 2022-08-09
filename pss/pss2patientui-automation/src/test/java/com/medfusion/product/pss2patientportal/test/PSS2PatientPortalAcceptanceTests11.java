// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
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
import com.medfusion.product.oject.maps.pss2.page.CareTeam.ManageCareTeam;
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
		String specialty = propertyData.getProperty("ct.specialty.ng");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		patientflow.turnOnProvider();
		adminUtils.setRulesWithSpeciality(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());
		log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			log("Status of PCP  OFF and Clicked on ON");
		} else {
			log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clickSpecialtyTab();
		manageResource.resourceSearchSpecialty(specialty);
		manageResource.enableSpecialty();
		manageResource.clearSearchResource();
		manageResource.selectResource(providerName1);
		manageResource.clickGeneralTab();
		manageResource.enableSharePatient();
		manageResource.clickSpecialtyTab();
		manageResource.resourceSearchSpecialty(specialty);
		manageResource.enableSpecialty();
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
		StartAppointmentInOrder startAppointmentInOrder = specialityObj.selectSpeciality(specialty);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();	
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(providerName1));
		assertTrue(bookList.contains(providerName));
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
		String specialty = propertyData.getProperty("ct.specialty.ge");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesWithSpeciality(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());
		log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			log("Status of PCP  OFF and Clicked on ON");
		} else {
			log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clickSpecialtyTab();
		manageResource.resourceSearchSpecialty(specialty);
		manageResource.enableSpecialty();
		manageResource.clearSearchResource();
		manageResource.selectResource(providerName1);
		manageResource.clickGeneralTab();
		manageResource.enableSharePatient();
		manageResource.clickSpecialtyTab();
		manageResource.resourceSearchSpecialty(specialty);
		manageResource.enableSpecialty();
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
		StartAppointmentInOrder startAppointmentInOrder = specialityObj.selectSpeciality(specialty);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();	
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(providerName1));
		assertTrue(bookList.contains(providerName));
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
		String specialty = propertyData.getProperty("ct.specialty.gw");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesWithSpeciality(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());
		log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			log("Status of PCP  OFF and Clicked on ON");
		} else {
			log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clickSpecialtyTab();
		manageResource.resourceSearchSpecialty(specialty);
		manageResource.enableSpecialty();
		manageResource.clearSearchResource();
		manageResource.selectResource(providerName1);
		manageResource.clickGeneralTab();
		manageResource.enableSharePatient();
		manageResource.clickSpecialtyTab();
		manageResource.resourceSearchSpecialty(specialty);
		manageResource.enableSpecialty();
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
		StartAppointmentInOrder startAppointmentInOrder = specialityObj.selectSpeciality(specialty);
		Location location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();	
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(providerName1));
		assertTrue(bookList.contains(providerName));
		logStep("Verify Provider Page and PCP = " + providerName);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);
		log("Test Case Passed");
	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void VerifyProvidersAreDisplayedWhoseSharePatientIsOnWhenPCPIsNotPartOfAnyCareTeam_NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("bct.book.name.ng");
		String providerName1 = propertyData.getProperty("bct.book.name1.ng");
		String providerName2 = propertyData.getProperty("bct.book.name2.ng");
		String appointmentTypeName = propertyData.getProperty("bct.appointment.type.ng");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		patientflow.turnOnProvider();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());
		log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			log("Status of PCP  OFF and Clicked on ON");
		} else {
			log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		
		log("Is Force Care team toggle enabled" + appointment.toggleAllowFCTONOF());
		if (appointment.toggleAllowFCTONOF() == false) {
			log("Status of PCP  OFF");
			appointment.fcttoggleclick();
			log("Status of FCT  OFF and Clicked on ON");
		} else {
			log("Status of FCT is Already ON");
		}
		String fctDuration = propertyData.getProperty("bct.fct.days.ng");
		String pcpDuration = propertyData.getProperty("bct.pcp.days.ng");
		appointment.forceCareteamDuration(fctDuration);
		appointment.pCPAvailabilityDuration(pcpDuration);
		String careTeam = propertyData.getProperty("bct.ct.name.ng");
		String careTeamBook = propertyData.getProperty("bct.book.name1.ng");
		String careTeamBook2 = propertyData.getProperty("bct.book.name2.ng");
		ManageCareTeam manageCareTeam = pssPracticeConfig.gotoCareTeam();
		adminUtils.pageRefresh(driver);
		manageCareTeam.manageAddCareTeam();
		manageCareTeam.enterGeneralInfoName(careTeam);
		manageCareTeam.gotoGeneralInforamtion();
		manageCareTeam.searchResource(careTeamBook,careTeamBook2);
		Thread.sleep(1000);
		manageCareTeam.back();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clearSearchResource();
		Thread.sleep(5000);
		manageResource.selectResource(providerName1);
		manageResource.clearSearchResource();
		manageResource.disableSharePatient();
		Thread.sleep(5000);
		manageResource.selectResource(providerName2);
		manageResource.enableSharePatient();
		manageResource.clearSearchResource();
		Thread.sleep(5000);
		pssPracticeConfig.gotoSettings();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();	
		adminUtils.pageRefresh(driver);
		manageResource.logout();
    	
		String fn = propertyData.getProperty("ct.fn.pm11.ng");
		String ln = propertyData.getProperty("ct.ln.pm11.ng");
		String dob = propertyData.getProperty("ct.dob.pm11.ng");
		String email = propertyData.getProperty("ct.email.pm11.ng");
		String gender = propertyData.getProperty("ct.gender.pm11.ng");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm11.ng");
		String zipCode = propertyData.getProperty("ct.zip.code.pm11.ng");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();	
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(providerName));
		assertFalse(bookList.contains(providerName1));
		assertTrue(bookList.contains(providerName2));
		logStep("Verify Provider Page and PCP = " + providerName2);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName2);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);		
		log("Test Case Passed");
		
		//Reset care team data
		adminUtils.resetCareTeamData(driver, adminUser, testData, appointment, careTeam, providerName1, providerName2);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void VerifyProvidersAreDisplayedWhoseSharePatientIsOnWhenPCPIsNotPartOfAnyCareTeam_GW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("bct.book.name.gw");
		String providerName1 = propertyData.getProperty("bct.book.name1.gw");
		String providerName2 = propertyData.getProperty("bct.book.name2.gw");
		String appointmentTypeName = propertyData.getProperty("bct.appointment.type.gw");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());		log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			log("Status of PCP  OFF and Clicked on ON");
		} else {
			log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		
		log("Is Force Care team toggle enabled" + appointment.toggleAllowFCTONOF());
		if (appointment.toggleAllowFCTONOF() == false) {
			log("Status of PCP  OFF");
			appointment.fcttoggleclick();
			log("Status of FCT  OFF and Clicked on ON");
		} else {
			log("Status of FCT is Already ON");
		}
		String fctDuration = propertyData.getProperty("bct.fct.days.gw");
		String pcpDuration = propertyData.getProperty("bct.pcp.days.gw");
		appointment.forceCareteamDuration(fctDuration);
		appointment.pCPAvailabilityDuration(pcpDuration);
		String careTeam = propertyData.getProperty("bct.ct.name.gw");
		String careTeamBook = propertyData.getProperty("bct.book.name1.gw");
		String careTeamBook2 = propertyData.getProperty("bct.book.name2.gw");		
		ManageCareTeam manageCareTeam = pssPracticeConfig.gotoCareTeam();
		adminUtils.pageRefresh(driver);
		manageCareTeam.manageAddCareTeam();
		manageCareTeam.enterGeneralInfoName(careTeam);
		manageCareTeam.gotoGeneralInforamtion();
		manageCareTeam.searchResource(careTeamBook,careTeamBook2);
		Thread.sleep(1000);
		manageCareTeam.back();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);	
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clearSearchResource();
		Thread.sleep(5000);
		manageResource.selectResource(providerName1);
		manageResource.clearSearchResource();
		manageResource.disableSharePatient();
		Thread.sleep(5000);
		manageResource.selectResource(providerName2);
		manageResource.enableSharePatient();
		manageResource.clearSearchResource();
		Thread.sleep(5000);
		pssPracticeConfig.gotoSettings();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();	
		adminUtils.pageRefresh(driver);
		manageResource.logout();
    	
		String fn = propertyData.getProperty("ct.fn.pm11.gw");
		String ln = propertyData.getProperty("ct.ln.pm11.gw");
		String dob = propertyData.getProperty("ct.dob.pm11.gw");
		String email = propertyData.getProperty("ct.email.pm11.gw");
		String gender = propertyData.getProperty("ct.gender.pm11.gw");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm11.gw");
		String zipCode = propertyData.getProperty("ct.zip.code.pm11.gw");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();	
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(providerName));
		assertFalse(bookList.contains(providerName1));
		assertTrue(bookList.contains(providerName2));
		logStep("Verify Provider Page and PCP = " + providerName2);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName2);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);		
		log("Test Case Passed");
	
		//Reset care team data
		adminUtils.resetCareTeamData(driver, adminUser, testData, appointment, careTeam, providerName1, providerName2);	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void VerifyProvidersAreDisplayedWhoseSharePatientIsOnWhenPCPIsNotPartOfAnyCareTeam_GE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		PSSPatientUtils patientUtils = new PSSPatientUtils();
		String providerName = propertyData.getProperty("bct.book.name.ge");
		String providerName1 = propertyData.getProperty("bct.book.name1.ge");
		String providerName2 = propertyData.getProperty("bct.book.name2.ge");
		String appointmentTypeName = propertyData.getProperty("bct.appointment.type.ge");
		
		logStep("Login to PSS Admin portal");
		PSS2PracticeConfiguration pssPracticeConfig = adminUtils.loginToAdminPortal(driver, adminUser);
		PatientFlow patientflow = pssPracticeConfig.gotoPatientFlowTab();
		adminUtils.setRulesNoSpecialitySet1(patientflow);
		AdminAppointment appointment = pssPracticeConfig.gotoAdminAppointmentTab();
		log("Is Care team toggle enabled" + appointment.toggleAllowPCPONOF());
		testData.setPcptoggleState(appointment.toggleAllowPCPONOF());
		log("Status of PCP is " + testData.isPcptoggleState());
		if (testData.isPcptoggleState() == false) {
			log("Status of PCP  OFF");
			appointment.pcptoggleclick();
			log("Status of PCP  OFF and Clicked on ON");
		} else {
			log("Status of PCP is Already ON");
		}
		appointment.selectPrimaryCareProvider();
		
		log("Is Force Care team toggle enabled" + appointment.toggleAllowFCTONOF());
		if (appointment.toggleAllowFCTONOF() == false) {
			log("Status of PCP  OFF");
			appointment.fcttoggleclick();
			log("Status of FCT  OFF and Clicked on ON");
		} else {
			log("Status of FCT is Already ON");
		}
		String fctDuration = propertyData.getProperty("bct.fct.days.ge");
		String pcpDuration = propertyData.getProperty("bct.pcp.days.ge");
		appointment.forceCareteamDuration(fctDuration);
		appointment.pCPAvailabilityDuration(pcpDuration);
		String careTeam = propertyData.getProperty("bct.ct.name.ge");
		String careTeamBook = propertyData.getProperty("bct.book.name1.ge");
		String careTeamBook2 = propertyData.getProperty("bct.book.name2.ge");
		ManageCareTeam manageCareTeam = pssPracticeConfig.gotoCareTeam();
		adminUtils.pageRefresh(driver);
		manageCareTeam.manageAddCareTeam();
		manageCareTeam.enterGeneralInfoName(careTeam);
		manageCareTeam.gotoGeneralInforamtion();
		manageCareTeam.searchResource(careTeamBook,careTeamBook2);
		Thread.sleep(1000);
		manageCareTeam.back();
		ManageResource manageResource = pssPracticeConfig.gotoResource();
		adminUtils.pageRefresh(driver);
		manageResource.selectResource(providerName);
		manageResource.enableSharePatient();
		manageResource.clearSearchResource();
		Thread.sleep(5000);
		manageResource.selectResource(providerName1);
		manageResource.clearSearchResource();
		manageResource.disableSharePatient();
		Thread.sleep(5000);
		manageResource.selectResource(providerName2);
		manageResource.enableSharePatient();
		manageResource.clearSearchResource();
		Thread.sleep(5000);
		pssPracticeConfig.gotoSettings();
		AdminPatientMatching adminpatientmatching = pssPracticeConfig.gotoPatientMatchingTab();
		adminpatientmatching.patientMatchingSelection();
		adminUtils.pageRefresh(driver);
		manageResource.logout();
    	
		String fn = propertyData.getProperty("ct.fn.pm11.ge");
		String ln = propertyData.getProperty("ct.ln.pm11.ge");
		String dob = propertyData.getProperty("ct.dob.pm11.ge");
		String email = propertyData.getProperty("ct.email.pm11.ge");
		String gender = propertyData.getProperty("ct.gender.pm11.ge");
		String phoneNumber = propertyData.getProperty("ct.phone.number.pm11.ge");
		String zipCode = propertyData.getProperty("ct.zip.code.pm11.ge");	
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, zipCode, phoneNumber);
		homePage.btnStartSchedClick();
		StartAppointmentInOrder startappointmentInOrder = homePage.skipInsurance(driver);
		Location location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verify Location Page and location =" + testData.getLocation());
		AppointmentPage appointmentPage = location.selectAppointment(testData.getLocation());
		logStep("Verify Appointment Page and appointment to be selected = " + appointmentTypeName);
		Provider provider = appointmentPage.selectTypeOfProvider(appointmentTypeName,
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		ArrayList<String> bookList= new ArrayList<String>();
		bookList=provider.getBookList();	
		log("List of book from Patient UI- " + bookList);
		assertTrue(bookList.contains(providerName));
		assertFalse(bookList.contains(providerName1));
		assertTrue(bookList.contains(providerName2));	
		logStep("Verify Provider Page and PCP = " + providerName2);
		AppointmentDateTime aptDateTime = provider.getProviderAndClick1(providerName2);
		aptDateTime.selectFutureDate(testData.getIsNextDayBooking());
		patientUtils.clickOnSubmitAppt1(testData.isInsuranceAtEnd(), aptDateTime, testData, driver);		
		log("Test Case Passed");
		
		//Reset care team data
		adminUtils.resetCareTeamData(driver, adminUser, testData, appointment, careTeam, providerName1, providerName2);
	}
}


