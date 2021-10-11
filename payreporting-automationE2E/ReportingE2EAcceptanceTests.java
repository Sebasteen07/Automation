// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payreporting.tests;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
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
        log("Test Case: Registering Simple Practice Portal VCS Payment on the Element merchant");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.element"));
		assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount, testData.getProperty("merchant.element"),
				testData.getProperty("location.element"), false, false, true, true), "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.element") + ": SUCCESS. Processed and found in GWR");		
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "practice", "element" })
	public void testPracticeVCSElementVoid() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the Element merchant, processing a void.");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.element"));
        assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount,
				testData.getProperty("merchant.element"), testData.getProperty("location.element"), true, false, true, true),
                "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.element") + ": SUCCESS. Processed and found in GWR");		
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "practice", "element" })
	public void testPracticeVCSElementRefund() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the Element merchant, processing a refund");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.element"));
		assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount, testData.getProperty("merchant.element"),
				testData.getProperty("location.element"), false, true, true, true), "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.element") + ": SUCCESS. Processed and found in GWR");		
	}
	
	//PRACTICE VCS LITLE
    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests" })
	public void testPracticeVCSLitlePay() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the Litle merchant");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.litle"));
		assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount, testData.getProperty("merchant.litle"), testData.getProperty("location.litle"),
				false, false, true, true), "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.litle") + ": SUCCESS. Processed and found in GWR");		
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "practice", "litle" })
	public void testPracticeVCSLitleVoid() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the Litle merchant, processing a void.");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.litle"));
        assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount,
				testData.getProperty("merchant.litle"), testData.getProperty("location.litle"), true, false, true, true),
                "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.litle") + ": SUCCESS. Processed and found in GWR");		
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "practice", "litle" })
	public void testPracticeVCSLitleRefund() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the Litle merchant, processing a refund");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.litle"));
		assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount, testData.getProperty("merchant.litle"), testData.getProperty("location.litle"),
				false, true, true, true), "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.litle") + ": SUCCESS. Processed and found in GWR");		
	}
	
	//PRACTICE VCS PAYPAL
	@Test(enabled = true, groups = {"ReportingE2EAcceptanceTests", "paypal"})
	public void testPracticeVCSPaypalPay() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the Paypal merchant");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.paypal"));
		assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount, testData.getProperty("merchant.paypal"),
				testData.getProperty("location.paypal"), false, false, true, false), "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.paypal") + ": SUCCESS. Processed and found in GWR");		
	}

	@Test(enabled = true, groups = {"ReportingE2EAcceptanceTests", "practice", "paypal"})
	public void testPracticeVCSPaypalVoid() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the Paypal merchant, processing a void.");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.paypal"));
        assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount,
				testData.getProperty("merchant.paypal"), testData.getProperty("location.paypal"), true, false, true, false),
                "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.paypal") + ": SUCCESS. Processed and found in GWR");		
	}

	//PRACTICE VCS QBMS
    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests" })
	public void testPracticeVCSQBMSPay() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the QBMS merchant");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.qbms"));
		assertTrue(ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount, testData.getProperty("merchant.qbms"), testData.getProperty("location.qbms"),
				false, false, true, false), "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.qbms") + ": SUCCESS. Processed and found in GWR");		
	}

	@Test(enabled = true, groups = {"ReportingE2EAcceptanceTests", "practice", "qbms"})
	public void testPracticeVCSQBMSVoid() throws NullPointerException, RuntimeException, InterruptedException {	
        log("Test Case: Registering Simple Practice Portal VCS Payment on the QBMS merchant, processing a void");			
		
		log("Setting up data for payments via client, generating random amount to charge");					
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running practice vcs payment for merchant: " + testData.getProperty("merchant.qbms"));
        assertTrue(
                ReportingE2EUtil.payPracticeVCSProcessGwr(driver, testData, amount,
						testData.getProperty("merchant.qbms"), testData.getProperty("location.qbms"), true, false, true, false),
                "Payment failed to process in PP or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.qbms") + ": SUCCESS. Processed and found in GWR");		
	}
	
	//PATIENT 2 PAY BILLS ELEMENT
	
	/*
     * Test for declined payment, not needed at the moment
     * @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "portal2" })
     * public void testPatient2PayBillsElementPayDeclined() throws Exception {
     * log(
     * "Test Case: Registering a Portal2 Pay Bills payment on the Element merchant, expecting a declined on a testcard"
     * );
     * log("Setting up data for payment via client, generating random amount to charge");
     * String amount = ReportingE2EUtil.getTwoDigitAmount();
     * log("Running portal2 payment for merchant: " + testData.getProperty("merchant.element"));
     * assertTrue(ReportingE2EUtil.payPortal2ExpectDeclinedInGwr(driver, testData, amount,
     * testData.getProperty("merchant.element"), testData.getProperty("location.element")),
     * "Payment failed to process in Portal2 or GWR");
     * log("Payment for merchant " + testData.getProperty("merchant.element") + ": Processed and found in GWR. SUCCESS. "
     * );
     * }
     */
    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests" })
	public void testPatient2PayBillsElementPay() throws Exception {	
        log("Test Case: Registering a Portal2 Pay Bills payment on the Element merchant");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.element"));		
		assertTrue(ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount, testData.getProperty("merchant.element"),
				testData.getProperty("location.element"), false, false, true, true), "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.element") + ": Processed and found in GWR. SUCCESS. ");		
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "portal2", "element" })
	public void testPatient2PayBillsElementVoid() throws Exception {	
        log("Test Case: Registering a Portal2 Pay Bills payment on the Element merchant, processing a void");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.element"));		
		assertTrue(ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount, testData.getProperty("merchant.element"),
				testData.getProperty("location.element"), true, false, true, true), "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.element") + ": Processed and found in GWR. SUCCESS. ");		
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "portal2", "element" })
    public void testPatient2PayBillsElementRefund() throws Exception {
        log("Test Case: Registering a Portal2 Pay Bills payment on the Element merchant, processing a refund");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.element"));		
		assertTrue(ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount, testData.getProperty("merchant.element"),
				testData.getProperty("location.element"), false, true, true, true), "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.element") + ": Processed and found in GWR. SUCCESS. ");		
	}
	
	//PATIENT 2 PAY BILLS LITLE
    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests" })
    public void testPatient2PayBillsLitlePay() throws Exception {
        log("Test Case: Registering a Portal2 Pay Bills payment on the Litle merchant");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.litle"));		
		assertTrue(ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount, testData.getProperty("merchant.litle"),
				testData.getProperty("location.litle"), false, false, true, true), "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.litle") + ": Processed and found in GWR. SUCCESS. ");		
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "portal2", "litle" })
    public void testPatient2PayBillsLitleVoid() throws Exception {
        log("Test Case: Registering a Portal2 Pay Bills payment on the Litle merchant, processing a void");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.litle"));
		assertTrue(ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount, testData.getProperty("merchant.litle"),
				testData.getProperty("location.litle"), true, false, true, true), "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.litle") + ": Processed and found in GWR. SUCCESS. ");		
	}

    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests", "portal2", "litle" })
    public void testPatient2PayBillsLitleRefund() throws Exception {
        log("Test Case: Registering a Portal2 Pay Bills payment on the Litle merchant, processing a refund");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.litle"));		
		assertTrue(ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount, testData.getProperty("merchant.litle"),
				testData.getProperty("location.litle"), false, true, true, true), "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.litle") + ": Processed and found in GWR. SUCCESS. ");		
	}
	
	//PATIENT 2 PAY BILLS PAYPAL
	@Test(enabled = true, groups = {"ReportingE2EAcceptanceTests", "paypal"})
    public void testPatient2PayBillsPaypalPay() throws Exception {
        log("Test Case: Registering a Portal2 Pay Bills payment on the Paypal merchant");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.paypal"));		
		assertTrue(ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount, testData.getProperty("merchant.paypal"),
				testData.getProperty("location.paypal"), false, false, true, false), "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.paypal") + ": Processed and found in GWR. SUCCESS. ");		
	}

	@Test(enabled = true, groups = {"ReportingE2EAcceptanceTests", "portal2", "paypal"})
    public void testPatient2PayBillsPaypalVoid() throws Exception {
        log("Test Case: Registering a Portal2 Pay Bills payment on the Paypal merchant, processing a void");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.paypal"));
        assertTrue(ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount,
				testData.getProperty("merchant.paypal"), testData.getProperty("location.paypal"), true, false, true, false),
                "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.paypal") + ": Processed and found in GWR. SUCCESS. ");		
	}

		
	//PATIENT 2 PAY BILLS QBMS
    @Test(enabled = true, groups = { "ReportingE2EAcceptanceTests" })
    public void testPatient2PayBillsQBMSPay() throws Exception {
        log("Test Case: Registering a Portal2 Pay Bills payment on the QBMS merchant");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.qbms"));		
        assertTrue(
                ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount,
						testData.getProperty("merchant.qbms"), testData.getProperty("location.qbms"), true, false, true, false),
                "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.qbms") + ": Processed and found in GWR. SUCCESS. ");		
	}

	@Test(enabled = true, groups = {"ReportingE2EAcceptanceTests", "portal2", "qbms"})
    public void testPatient2PayBillsQBMSVoid() throws Exception {
        log("Test Case: Registering a Portal2 Pay Bills payment on the QBMS merchant, processing a void");

		log("Setting up data for payment via client, generating random amount to charge");	
		String amount = ReportingE2EUtil.getTwoDigitAmount();
		log("Running portal2 payment for merchant: " + testData.getProperty("merchant.qbms"));
        assertTrue(
                ReportingE2EUtil.payPortal2ProcessGwrVoidRefund(driver, testData, amount,
						testData.getProperty("merchant.qbms"), testData.getProperty("location.qbms"), true, false, true, false),
                "Payment failed to process in Portal2 or GWR");
		log("Payment for merchant " + testData.getProperty("merchant.qbms") + ": Processed and found in GWR. SUCCESS. ");		
	}
}
