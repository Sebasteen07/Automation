//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payreporting.serviceE2E.ReportingE2EUtil;


@Listeners(com.medfusion.listenerpackage.Listener.class)

public class ReportingE2EAcceptanceTests extends BaseTestNGWebDriver {
	protected PropertyFileLoader testData;

	@BeforeClass(enabled = true, groups = {"ReportingE2EAcceptanceTests", "practice", "portal2", "element", "qbms", "paypal"})
	public void loadData() throws IOException{
		testData = new PropertyFileLoader();
	}
	
	//PRACTICE VCS ELEMENT
    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests" })
	public void testPracticeVCSElementPay() throws NullPointerException, RuntimeException, InterruptedException {
		logStep("Setting up data for payments via client, generating random amount to charge & account number");
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		String accountNumber = IHGUtil.createRandomNumericString(8);

		logStep("Running practice vcs payment for merchant: " + testData.getProperty("merchant.element"));
		assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount, accountNumber,testData.getProperty("merchant.element"),
				testData.getProperty("location.element"), false, false, true, true), "Payment failed to process in PP or GWR");
		logStep("Payment for merchant " + testData.getProperty("merchant.element") + ": SUCCESS. Processed and found in GWR");
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "practice", "element" })
	public void testPracticeVCSElementVoid() throws NullPointerException, RuntimeException, InterruptedException {
		logStep("Setting up data for payments via client, generating random amount to charge & account number");
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		String accountNumber = IHGUtil.createRandomNumericString(8);

		logStep("Running practice vcs payment for merchant: " + testData.getProperty("merchant.element"));
        assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount,accountNumber,
		testData.getProperty("merchant.element"), testData.getProperty("location.element"), true, false, true, true),
        "Payment failed to process in PP or GWR");
		logStep("Payment for merchant " + testData.getProperty("merchant.element") + ": SUCCESS. Processed and found in GWR");
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "practice", "element" })
	public void testPracticeVCSElementRefund() throws NullPointerException, RuntimeException, InterruptedException {
		logStep("Setting up data for payments via client, generating random amount to charge & account number");
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		String accountNumber = IHGUtil.createRandomNumericString(8);

		logStep("Running practice vcs payment for merchant: " + testData.getProperty("merchant.element"));
		assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount,accountNumber,
				testData.getProperty("merchant.element"),testData.getProperty("location.element"),
				false, true, true, true), "Payment failed to process in PP or GWR");
		logStep("Payment for merchant " + testData.getProperty("merchant.element") + ": SUCCESS. Processed and found in GWR");
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests" })
	public void testPatient2PayBillsElementPay() throws Exception {	
		logStep("Setting up data for payment via client, generating random amount to charge & account number");
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		String accountNumber = IHGUtil.createRandomNumericString(8);

		logStep("Running portal2 payment for merchant: " + testData.getProperty("merchant.element"));
		assertTrue(ReportingE2EUtil.payPortalOLBPGwrVoidRefund (driver, testData,amount, accountNumber,testData.getProperty("merchant.element"),
				testData.getProperty("location.element"),false, false, true, true),"Payment failed to process in Portal2 or GWR");
		logStep("Payment for merchant " + testData.getProperty("merchant.element") + ": Processed and found in GWR. SUCCESS. ");
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "portal2", "element" })
	public void testPatient2PayBillsElementVoid() throws Exception {
		logStep("Setting up data for payment via client, generating random amount to charge & account number");
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		String accountNumber = IHGUtil.createRandomNumericString(8);

		logStep("Running portal2 payment for merchant: " + testData.getProperty("merchant.element"));
		assertTrue(ReportingE2EUtil.payPortalOLBPGwrVoidRefund(driver, testData, amount, accountNumber,testData.getProperty("merchant.element"),
		testData.getProperty("location.element"), true, false, true, true), "Payment failed to process in Portal2 or GWR");
		logStep("Payment for merchant " + testData.getProperty("merchant.element") + ": Processed and found in GWR. SUCCESS. ");
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "portal2", "element" })
    public void testPatient2PayBillsElementRefund() throws Exception {
		logStep("Setting up data for payment via client, generating random amount to charge & account number");
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		String accountNumber = IHGUtil.createRandomNumericString(8);

		logStep("Running portal2 payment for merchant: " + testData.getProperty("merchant.element"));
		assertTrue(ReportingE2EUtil
				.payPortalOLBPGwrVoidRefund(driver, testData, amount, accountNumber,testData.getProperty("merchant.element"),
		testData.getProperty("location.element"), false, true, true, true), "Payment failed to process in Portal2 or GWR");
		logStep("Payment for merchant " + testData.getProperty("merchant.element") + ": Processed and found in GWR. SUCCESS. ");
	}

}
