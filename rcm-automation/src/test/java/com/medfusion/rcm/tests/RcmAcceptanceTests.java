// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.rcm.tests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.intuit.ihg.common.utils.WebPoster;
import com.medfusion.common.utils.PropertyFileLoader;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.medfusion.product.practice.api.flows.IPatientActivation;
import com.medfusion.product.practice.api.pojo.PatientInfo;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.implementedExternals.PatientActivation;
import com.medfusion.rcm.utils.RCMUtil;

import java.io.FileInputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @Author:Jakub Odvarka
 * @Date:24.4.2015
 */

@Test
public class RcmAcceptanceTests extends BaseTestNGWebDriver {

	@AfterMethod
	protected void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testRcmDedicatedRestsGET() throws Exception {

		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		String doctorAuth = testData.getDoctorBase64AuthString();
		String billingAccountRest = testData.getRcmBillingAccountRest();
		String billingNumber = testData.getBillingAccountNumber();


		WebPoster billingAccountsPractice = new WebPoster();

		log("Requesting from billing accounts - practice user");
		billingAccountsPractice.setServiceUrl(billingAccountRest.trim() + billingNumber);
		billingAccountsPractice.setContentType("application/json;");
		billingAccountsPractice.addHeader("Authorization", "Basic " + doctorAuth);
		log("Set Expected Status Code = 200");
		billingAccountsPractice.setExpectedStatusCode(200);
		assertTrue(billingAccountsPractice.get());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSendStmtVerifyNotificationsMessagesBalance() throws Exception {

		log(this.getClass().getName());
		RCMUtil util = new RCMUtil(driver);
		Mailinator mail = new Mailinator();

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();

		log("Post eStatement");
		util.postStatementToPatient(testData.getRcmStatementRest() + testData.getPracticeId() + "/patients/" + testData.getMemberId() + "/statements",
				IHGUtil.getEnvironmentType().toString());

		log("Check email notification and URL");
		String box = testData.getEmail().split("@")[0];
		assertTrue(mail.catchNewMessageCheckLinkUrl(box, "Your patient eStatement is now available", "Visit our website", testData.getUrl(), 50));

		log("Log in");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());

		log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='messageActions']")));

		log("Expect an estatement message");
		assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));

		log("Archive the message");
		jalapenoMessagesPage.archiveOpenMessage();

		jalapenoMessagesPage.goToPayBillsPage(driver);
		log("Check expected balance");
		String balance = getBalanceDue(driver);
		assertTrue(testData.getStatementBalanceDue().equals(balance));
		log("Balance checks out!");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSendStmtE2ESftpGWMirth() throws Exception {

		log(this.getClass().getName());
		Mailinator mail = new Mailinator();
		Session session;
		Channel channel;
		ChannelSftp channelSftp;

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();
		URL url = ClassLoader.getSystemResource("testfiles/" + IHGUtil.getEnvironmentType().toString() + "/" + "gwayestAUTO.002");

		log("Sending statment file via SFTP to " + testData.getSftpHost());
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(testData.getSftpUser(), testData.getSftpHost(), 22);
			session.setPassword(testData.getSftpPassword());
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			File f = new File(url.getPath());
			channelSftp.put(new FileInputStream(f), f.getName());
		} catch (Exception ex) {
			System.out.println("SFTP File Upload attempt failed!");
			ex.printStackTrace();
		}

		log("Check email notification and URL");
		String box = testData.getEmail().split("@")[0];
		assertTrue(mail.catchNewMessageCheckLinkUrl(box, "Your patient eStatement is now available", "Visit our website", testData.getUrl(), 108));

		log("Log in");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());

		log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='messageActions']")));

		log("Expect an estatement message");
		assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));

		log("Archive the message");
		jalapenoMessagesPage.archiveOpenMessage();

		jalapenoMessagesPage.goToPayBillsPage(driver);
		log("Check expected balance");
		String balance = getBalanceDue(driver);
		assertTrue(testData.getSftpBalance().equals(balance));
		log("Balance checks out!");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSendDynamicElectronicFormat1() throws Exception {
		sendDynamicByFileAndPreference("statementDF1.txt", "EF1", 2);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSendDynamicElectronicFormat2() throws Exception {
		sendDynamicByFileAndPreference("statementDF2.txt", "EF2", 2);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testSendDynamicPaperFormat1() throws Exception {
		// TODO remove after paper patient access fix, refactor as above
		log(this.getClass().getName());
		log("Getting Test Data");

		PropertyFileLoader testData = new PropertyFileLoader();

		// set up patient
		PatientInfo newPat = activateNewPatientPrepareForStatement(driver, testData, 1);

		// Randomize balance, insert decimal dot
		Random rand = new Random();
		int minBal = 100;
		int maxBal = 99900;
		int ran = ((rand.nextInt(maxBal - minBal) + minBal + 1));
		if (ran % 10 == 0)
			ran++;
		String tmpBal = Integer.toString(ran);
		newPat.balance = new StringBuffer(tmpBal).insert(tmpBal.length() - 2, ".").toString();
		log("Setting up a modified paper statement");
		String endpoint = testData.getRcmStatementRest() + testData.getPracticeId() + "/patients/" + newPat.memberId + "/statements";
		newPat.billingAccountNumber =
				postModifiedStatementToPatient("statementDF1.txt", endpoint, IHGUtil.getEnvironmentType().toString(), newPat.practicePatientId, newPat.balance, "PF1");
		assertFalse(newPat.billingAccountNumber == -1);
		log("Statement was successfuly posted to user rest, to the following billing account number: " + newPat.billingAccountNumber);

		log("Waiting out 20s for the statement to arrive");
		Thread.sleep(20000);

		log("Checking practice billing account info access?");
		assertTrue(getBillingAccountInfoComparePatientBalance(testData.getRcmBillingAccountRest(), Integer.toString(newPat.billingAccountNumber),
				testData.getDoctorBase64AuthString(), newPat.balance));
		log("Checks out!");
	}
	/*
	 * public void testSendDynamicPaperFormat2() throws Exception{}
	 */

	protected String getBalanceDue(WebDriver driver) {
		try {
			log("Waiting for balance element.");
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='balanceDue']/span/strong")));
			WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/strong"));
			log("Displayed: " + balance.isDisplayed() + " amount: " + balance.getText());
			return balance.getText();
		} catch (Exception ex) {
			log("Exception from element caught, rechecking");
			WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/strong"));
			log("Displayed: " + balance.isDisplayed() + " amount: " + balance.getText());
			return balance.getText();
		}
	}

	// This method is specifically intended for new patient test, does not guarantee the found balance is found in summary and not old statements (but new
	// patients will only have one statement)
	protected boolean getBillingAccountInfoComparePatientBalance(String rcmBillingAccountRest, String billingAccountNumber, String staffAuthString,
			String balanceToFind) {
		WebPoster poster = new WebPoster();
		poster.setServiceUrl(rcmBillingAccountRest.trim() + billingAccountNumber);
		poster.setContentType("application/json;");
		poster.addHeader("Authorization", "Basic " + staffAuthString);
		log("Set Expected Status Code = 200");
		poster.setExpectedStatusCode(200); // HTTP Status Code
		return poster.getAndSearchForMatch("\"customerBalance\":" + balanceToFind);
	}

	protected void sendDynamicByFileAndPreference(String filename, String idPrefix, int preference) throws Exception {
		log(this.getClass().getName());
		log("Getting Test Data");

		PropertyFileLoader testData = new PropertyFileLoader();

		// set up patient
		PatientInfo newPat = activateNewPatientPrepareForStatement(driver, testData, preference);

		// Randomize balance, insert decimal dot
		Random rand = new Random();
		int minBal = 100;
		int maxBal = 99900;
		int ran = ((rand.nextInt(maxBal - minBal) + minBal + 1));
		if (ran % 10 == 0)
			ran++;
		String tmpBal = Integer.toString(ran);
		newPat.balance = new StringBuffer(tmpBal).insert(tmpBal.length() - 2, ".").toString();
		log("Setting up a " + filename);
		String endpoint = testData.getRcmStatementRest() + testData.getPracticeId() + "/patients/" + newPat.memberId + "/statements";
		newPat.billingAccountNumber =
				postModifiedStatementToPatient(filename, endpoint, IHGUtil.getEnvironmentType().toString(), newPat.practicePatientId, newPat.balance, idPrefix);
		assertFalse(newPat.billingAccountNumber == -1);
		log("Statement was successfuly posted to user rest, to the following billing account number: " + newPat.billingAccountNumber);

		log("Waiting out 20s for the statement to arrive");
		Thread.sleep(20000);

		log("Log in back to patient portal");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(newPat.username, newPat.password);

		// check presence of badge (electronic preference &&(last payment date < newest statement date || (no payments present && statement arrived)))
		if (preference == 2) {
			log("No payments present, expect a badge with balance on Pay Bills");
			String badge = driver.findElement(By.xpath("//a[@id='feature_bill_pay']/span[@class='badge amountDue ng-binding']")).getText();
			log("Badge retrieved, expected balance: $" + newPat.balance + " , found balance: " + badge);
			assertTrue(badge.trim().equals("$" + newPat.balance));
			// paper patients don't receive a sm either
			log("Click on messages solution");
			JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);

			log("Expect an estatement message");
			assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));

			log("Archive the message");
			jalapenoMessagesPage.archiveOpenMessage();
			Thread.sleep(2000);
			log("Check expected balance");
			jalapenoMessagesPage.goToPayBillsPage(driver);
		} else {
			log("Badge and SM omitted, check expected balance");
			jalapenoHomePage.clickOnPayBills(driver);
		}
		String balance = getBalanceDue(driver);
		assertTrue(("$" + newPat.balance).equals(balance));

		log("Balance checks out, yay!");

		log("Does it match from practice PoV as well though?");
		assertTrue(getBillingAccountInfoComparePatientBalance(testData.getRcmBillingAccountRest(), Integer.toString(newPat.billingAccountNumber),
				testData.getDoctorBase64AuthString(), newPat.balance));

		log("It also checks out with practice admin!");
	}

	protected PatientInfo activateNewPatientPrepareForStatement(WebDriver driver, PropertyFileLoader testData, int deliveryPref) throws Exception {
		log(this.getClass().getName());
		log("Creating a new patient to use in statements");
		PatientInfo newPat = new PatientInfo();
		newPat.email = "eStMf." + IHGUtil.createRandomNumericString(6) + "@mailinator.com";

		IPatientActivation act = new PatientActivation();
		newPat = act.activatePatient(driver, testData, newPat.email);
		PatientVerificationPage patientVerificationPage;
		SecurityDetailsPage accountDetailsPage;
		JalapenoHomePage jalapenoHomePage;
		try {
			log("Finishing of patient activation: step 1 - verifying identity");
			patientVerificationPage = new PatientVerificationPage(driver, newPat.unlockLink);
			log("  Waiting up to 50 sec for 1st step activation page to load");
			@SuppressWarnings("unused")
			WebElement activationZipCode = (new WebDriverWait(driver, 50)).until(ExpectedConditions.presenceOfElementLocated(By.id("postalCode")));
			driver.manage().window().maximize();
			accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO,
					JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);
			checkAlert(driver);


			log("Finishing of patient activation: step 2 - filling patient data");
			jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(newPat.firstName, testData.getPassword(), testData.getSecretQuestion(),
					testData.getSecretAnswer(), "1234567890", deliveryPref);
		} catch (Exception e) {
			e.printStackTrace();
			log("Retrying");
			log("Finishing of patient activation: step 1 - verifying identity AGAIN.");
			patientVerificationPage = new PatientVerificationPage(driver, newPat.unlockLink);
			log("  Waiting up to 50 sec for 1st step activation page to load.");
			@SuppressWarnings("unused")
			WebElement activationZipCode = (new WebDriverWait(driver, 50)).until(ExpectedConditions.presenceOfElementLocated(By.id("postalCode")));
			driver.manage().window().maximize();
			accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(PracticeConstants.ZIP_CODE, JalapenoConstants.DATE_OF_BIRTH_MONTH_NO,
					JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);

			log("Finishing of patient activation: step 2 - filling patient data");
			jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(newPat.firstName, testData.getPassword(), testData.getSecretQuestion(),
					testData.getSecretAnswer(), "1234567890", deliveryPref);
		}

		log("Logging out");
		jalapenoHomePage.clickOnLogout();

		log("Back to Practice Portal to assign external ID");
		return act.editPatientRSDKExternalID(driver, testData, newPat);
	}

	protected int postModifiedStatementToPatient(String filename, String endpoint, String env, String practicePatientId, String patientBalance, String prefix)
			throws Exception {
		assertNotNull("### Endpoint is null!", endpoint);
		IHGUtil.PrintMethodName();
		int min = 111111;
		int max = 999999;
		Random rand = new Random();
		String newBillingNumber = Integer.toString((rand.nextInt((max - min) + 1) + min));
		String payload;
		try {
			System.out.println("Building statement for " + env + " , setting patient to " + practicePatientId + ", generated balance " + patientBalance
					+ " , new account number " + newBillingNumber);
			URL url = ClassLoader.getSystemResource("testfiles/" + env + "/" + filename);
			System.out.println(url.toString());
			Scanner scan = new Scanner(new File(url.getPath()));
			payload = scan.useDelimiter("\\Z").next();
			scan.close();
			payload = payload.replaceAll("BANPLACEHOLDER", newBillingNumber);
			payload = payload.replace("MRNPLACEHOLDER", practicePatientId);
			payload = payload.replace("BALANCEPLACEHOLDER", patientBalance);
			payload = payload.replace("STMTIDPLACEHOLDER", prefix + "DynPatient" + newBillingNumber);
			System.out.println("Stmt id: AutoBAN" + newBillingNumber);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return -1;
		}
		WebPoster poster = new WebPoster();
		poster.setServiceUrl(endpoint);
		poster.setContentType("application/json;");
		poster.addHeader("requestId", "rcmAutomationPost" + newBillingNumber);
		System.out.println("Using requestId: rcmAutomationPost" + newBillingNumber);
		poster.addHeader("Authentication-Type", "2wayssl");
		log("Expected Status Code = 200");
		poster.setExpectedStatusCode(200); // HTTP Status Code
		log("send Statement to patient #####");
		poster.postFromStringExplicitTimeout(payload, 5000);
		return Integer.parseInt(newBillingNumber);
	}

	public void checkAlert(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			// exception handling
		}
	}
}
