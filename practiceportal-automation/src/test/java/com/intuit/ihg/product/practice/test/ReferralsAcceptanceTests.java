//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.practice.test;

import com.medfusion.product.practice.api.utils.PracticeConstants;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.referrals.ReferralsPage;
import com.medfusion.product.practice.api.utils.ReadFilePath;

public class ReferralsAcceptanceTests extends BaseTestNGWebDriver {
	private ReadFilePath path = new ReadFilePath();
	private PropertyFileLoader testReferralsData = new PropertyFileLoader();

	public ReferralsAcceptanceTests() throws Exception {
		path.getFilepath(PracticeConstants.FILE_DIRECTORY);
	}

	/**
	 *
	 * @Author: dtilser User Story: SON-1754
	 * @Date: 07/21/2016
	 * @Types of patients
	 *
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testReferralsActivePatient() throws Exception {
		testSendReferrals(testReferralsData.getProperty("first.name.active"),
				testReferralsData.getProperty("last.name.active"), testReferralsData.getProperty("practice"), true);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testReferralsDeactivatedPatient() throws Exception {
		testSendReferrals(testReferralsData.getProperty("first.name.deactivated"),
				testReferralsData.getProperty("last.name.deactivated"), testReferralsData.getProperty("practice"), true);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" })
	public void testReferralsNonexistingPatient() throws Exception {
		testSendReferrals(testReferralsData.getProperty("first.name.no.exist"),
				testReferralsData.getProperty("last.name.no.exist"), testReferralsData.getProperty("practice"), false);
	}

	private void testSendReferrals(String patientFirstName, String patientLastName, String practiceName,
			boolean patientExists) throws Exception {
		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testReferralsData.getUrl());
		PracticeHomePage practiceHome = practiceLogin.login(testReferralsData.getProperty("ref.user.id"),
				testReferralsData.getProperty("ref.password"));

		logStep("Go into Referrals");
		ReferralsPage referralsPage = practiceHome.clickOnReferrals();

		logStep("Send Referral");
		referralsPage.sendReferralToAnotherPractice(patientFirstName, patientLastName, practiceName, patientExists);

		logStep("Go into other practice and go into Referrals");
		practiceLogin = new PracticeLoginPage(driver, testReferralsData.getUrl());
		practiceHome = practiceLogin.login(testReferralsData.getProperty("doctor.login"),
				testReferralsData.getProperty("doctor.password"));
		practiceHome.clickOnReferrals();

		logStep("Check if referral arrived");
		referralsPage.checkReferralArrived(patientFirstName, patientLastName);
	}
}
