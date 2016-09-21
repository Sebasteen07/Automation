package com.intuit.ihg.product.practice.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.referrals.ReferralsPage;
import com.medfusion.product.practice.api.pojo.Practice;
import com.medfusion.product.practice.api.pojo.PracticeTestData;
import com.medfusion.product.practice.api.utils.ReadFilePath;

public class ReferralsAcceptanceTests extends BaseTestNGWebDriver {
	ReadFilePath path = new ReadFilePath();
	PropertyFileLoader testReferralsData = new PropertyFileLoader();

	public ReferralsAcceptanceTests() throws Exception {
		path.getFilepath("documents");
	}

	/*
	 * @Author: dtilser User Story: SON-1754
	 * 
	 * @Date: 07/21/2016
	 * 
	 * @Types of patients
	 * 
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testReferralsActivePatient() throws Exception {
		testSendReferrals(testReferralsData.getProperty("firstNameActive"), testReferralsData.getProperty("lastNameActive"),
				testReferralsData.getProperty("practice"), true);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testReferralsDeactivatedPatient() throws Exception {
		testSendReferrals(testReferralsData.getProperty("firstNameDeactivated"), testReferralsData.getProperty("lastNameDeactivated"),
				testReferralsData.getProperty("practice"), true);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"})
	public void testReferralsNonexistingPatient() throws Exception {
		testSendReferrals(testReferralsData.getProperty("firstNameNoExist"), testReferralsData.getProperty("lastNameNoExist"),
				testReferralsData.getProperty("practice"), false);
	}

	private void testSendReferrals(String patientFirstName, String patientLastName, String practiceName, boolean patientExists) throws Exception {
		log("Test Case: TestSendReferrals");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Login to Practice Portal");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testReferralsData.getProperty("userid"), testReferralsData.getProperty("password"));

		log("step 2: Go into Referrals");
		ReferralsPage referralsPage = practiceHome.clickOnReferrals();

		log("step 3: Send Referral");
		referralsPage.sendReferralToAnotherPractice(patientFirstName, patientLastName, practiceName, patientExists);

		log("step 4: Go into other practice and go into Referrals");
		practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());
		practiceHome.clickOnReferrals();

		log("step 4: Check if referral arrived");
		referralsPage.checkReferalArrived(patientFirstName, patientLastName);
	}
}
