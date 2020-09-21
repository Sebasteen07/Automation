package com.medfusion.product.pss2patientportal.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous.AnonymousDismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PSS2PatientPortalAcceptanceTests02 extends BaseTestNGWebDriver {



	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAnonymousAthena(String partnerPractice) throws Exception {

		log("Step 1: set test data for new patient ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();

		testData.setAnonymousFlow(true);

		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.setTestData(partnerPractice, testData, adminuser);

		// propertyData.setAdminAthena(adminuser);
		// propertyData.setAppointmentResponseAthena(testData);

		adminuser.setIsAnonymousFlow(true);
		adminuser.setIsExisting(true);

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		log("Step 2: Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.adminSettingsAnonymous(driver, adminuser, testData, PSSConstants.ANONYMOUS);
		log("Step 3: Fetch the rules set in Admin");
		String rule = adminuser.getRule();
		//String rule = "T, L, B";
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		// PSSPatientUtils psspatientutils = new PSSPatientUtils();
		// psspatientutils.setTestData(partnerPractice, testData, adminuser);
		log("Step 4: Move to PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlAnonymous());

		AnonymousDismissPage anonymousDismissPage = new AnonymousDismissPage(driver, testData.getUrlAnonymous());
		HomePage homePage = anonymousDismissPage.clickDismiss();
		Thread.sleep(1000);
		psspatientutils.selectAFlow(driver, rule, homePage, testData);
	}

	@DataProvider(name = "partnerType")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] {{"ATHENA"}, {"NG"}};
		//Object[][] obj = new Object[][] {{"ATHENA"}};
		return obj;
	}



}
