// Copyright 2018-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2adminportal.test;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.pss2.page.Login.PSS2AdminLogin;
import com.medfusion.product.object.maps.pss2.page.settings.AdminAppointment;
import com.medfusion.product.object.maps.pss2.page.settings.PSS2PracticeConfiguration;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PSS2AdminPortalAcceptanceTests extends BaseTestNGWebDriver {

	@DataProvider(name = "staffPractice")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] {{"GW"}, {"GE"}, {"NG"}, {"AT"}};		
		return obj;
	}

	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminLoginForExistingStaffAndPractice(String staffPracitceName) throws Exception {
		log("Test To verify if existing staff and practice can Login" + staffPracitceName);
		log("Step 1: set test data for existing patient ");	
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		AdminUser adminuser = pssadminutils.setPracticeAdminAccount(staffPracitceName);
		log("Step 2: Login to Admin portal ");
		PSS2AdminLogin pssadminlogin = new PSS2AdminLogin(driver, adminuser.getAdminUrl());
		PSS2PracticeConfiguration psspracticeConfig = pssadminlogin.login(adminuser.getUser(), adminuser.getPassword());
		Log4jUtil.log("refreshing admin page after login");
		pssadminutils.pageRefresh(driver);
		log("Theme Selected for the practice is = " + psspracticeConfig.getSelectedColor());
		log("Step 3 : Verify client logo link " + psspracticeConfig.checkLogoLink());
		assertTrue(psspracticeConfig.checkLogoLink().contains(adminuser.getPracticeId()));
		log("Step 4 : Verify client Practice ID= " + psspracticeConfig.practiceIDLinkText());
		assertTrue(psspracticeConfig.practiceIDLinkText().contains(adminuser.getPracticeId()), "Staff Practice ID not found");
		log("Step 5: Logout from PSS Admin Portal");
		psspracticeConfig.logout();
	}
	

	@Test(enabled = true, dataProvider = "staffPractice", groups = {
			"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI03_lastQuestionEnable(String staffPracitceName) throws Exception {
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();

		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);

		pssAdminUtils.lastQuestionEnable(driver, adminUser, testData, PSSConstants.LOGINLESS);
	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI02_adminSettingsLoginless(String staffPracitceName) throws Exception {
		
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.adminSettingsLoginless(driver, adminUser,  testData, PSSConstants.LOGINLESS);

	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI01_adminSettingsAnonymousAT(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData("AT", testData, adminUser);		
		pssAdminUtils.adminSettingsAnonymous(driver, adminUser,  testData, PSSConstants.LOGINLESS);

	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminU01_adminSettingsAnonymousING(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData("NG", testData, adminUser);		
		pssAdminUtils.adminSettingsAnonymous(driver, adminUser,  testData, PSSConstants.LOGINLESS);

	}

	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI04_ageRule(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.ageRule(driver, adminUser,  testData);

	}
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI05_ageRuleWithSpeciality(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.ageRuleWithSpeciality(driver, adminUser,  testData);

	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI06_allowpcp(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.allowpcp(driver, adminUser,  testData);

	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI07_genderRule(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.genderRule(driver, adminUser,  testData);

	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI08_getCancelRescheduleSettings(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		 AdminAppointment adminAppointment = new  AdminAppointment(driver);		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.getCancelRescheduleSettings(driver, adminUser,  testData, adminAppointment);

	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI09_acceptforsameDay(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.acceptforsameDay(driver, adminUser,  testData);

	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI10_adminSettingLinkGenandDeleteLink(String staffPracitceName) throws Exception {	
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.adminSettingLinkGenandDeleteLink(driver, adminUser,  testData, PSSConstants.LOGINLESS);
	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI11_adminSettingLinkGeneration(String staffPracitceName) throws Exception {
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.adminSettingLinkGeneration(driver, adminUser,  testData, PSSConstants.LOGINLESS);
	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI12_reserveforDay(String staffPracitceName) throws Exception {
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.reserveforDay(driver, adminUser,  testData);
	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI13_leadTimenotReserve(String staffPracitceName) throws Exception {
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);
		String leadTimeValue="0";
		pssAdminUtils.leadTimenotReserve(driver, adminUser,  testData,leadTimeValue);
	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAdminUI14_getRescheduleSettings(String staffPracitceName) throws Exception {
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSAdminUtils pssAdminUtils = new PSSAdminUtils();	
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		AdminAppointment adminAppointment = new AdminAppointment(driver);		
		pssPatientUtils.setTestData(staffPracitceName, testData, adminUser);		
		pssAdminUtils.getRescheduleSettings(driver, adminUser,  testData, adminAppointment);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentDuration() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		String appType = propertyData.getProperty("stacking.apptype.ng");
		String providerName = propertyData.getProperty("stacking.provider.ng");
		String appointmentDuration =propertyData.getProperty("app.duration.digit");
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.appointmentDuration(driver, adminuser, testData, appType, providerName,appointmentDuration);		
	}
	
	@Test(enabled = true, dataProvider = "staffPractice", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAccessRulesBookmarkUrlForExistingStaffAndPractice(String staffPracitceName) throws Exception {
		log("Test To verify if existing staff and practice can Login" + staffPracitceName);
		log("Step 1: set test data for existing patient ");	
		PSSAdminUtils pssadminutils = new PSSAdminUtils();
		AdminUser adminuser = pssadminutils.setPracticeAdminAccount(staffPracitceName);
		log("Step 2: Login to Admin portal ");
		PSS2AdminLogin pssadminlogin = new PSS2AdminLogin(driver, adminuser.getAdminUrl());
		PSS2PracticeConfiguration psspracticeConfig = pssadminlogin.login(adminuser.getUser(), adminuser.getPassword());

		AccessRules pssaccessrules = new AccessRules(driver);
		var url = pssaccessrules.getLoginlessURL();

		// Log4jUtil.log("refreshing admin page after login");
		// pssadminutils.pageRefresh(driver);
		// log("Theme Selected for the practice is = " + psspracticeConfig.getSelectedColor());
		// log("Step 3 : Verify client logo link " + psspracticeConfig.checkLogoLink());
		// assertTrue(psspracticeConfig.checkLogoLink().contains(adminuser.getPracticeId()));
		// log("Step 4 : Verify client Practice ID= " + psspracticeConfig.practiceIDLinkText());
		// assertTrue(psspracticeConfig.practiceIDLinkText().contains(adminuser.getPracticeId()), "Staff Practice ID not found");
		// log("Step 5: Logout from PSS Admin Portal");
		// psspracticeConfig.logout();
	}
}
