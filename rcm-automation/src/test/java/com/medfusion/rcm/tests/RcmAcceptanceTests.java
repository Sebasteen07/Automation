package com.medfusion.rcm.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


import org.junit.Assert;
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
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.WebPoster;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.mail.Mailinator;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.object.maps.practice.page.PracticeHomePage;
import com.intuit.ihg.product.object.maps.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.intuit.ihg.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.practice.tests.PatientActivationSearchTest;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoPatientActivationPage;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.rcm.utils.PatientStatementInfo;
import com.medfusion.rcm.utils.RCMUtil;
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

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRcmDedicatedRestsGET() throws Exception {

		log(this.getClass().getName());		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();	
		
		String doctorAuth = testData.getDoctorBase64AuthString();
		String bearerAuth = testData.getBearerOAuthString();
		String practiceAuth = testData.getPracticeOAuthString();
		String billingAccountRest = testData.getRcmBillingAccountRest();
		String billingAccountGeneral = testData.getRcmBillingAccountGeneralRest();
		String merchantRest = testData.getRcmMerchantRest();
		String merchantLogoRest = testData.getRcmMerchantLogoRest();
		String merchantID = testData.getRcmMerchantID();			
		String billingNumber = testData.getBillingAccountNumber();
		String statementPDFRest = testData.getRcmStatementsPDFRest();
		
		
		WebPoster billingAccountsPractice = new WebPoster();
		WebPoster billingAccountsSyslevel = new WebPoster();
		WebPoster merchantPractice = new WebPoster();
		WebPoster merchantSyslevel = new WebPoster();
		WebPoster merchantLogo = new WebPoster();
		WebPoster statementsPractice = new WebPoster();		
		
		log("Requesting from billing accounts - practice user");
		billingAccountsPractice.setServiceUrl(billingAccountRest.trim()+billingNumber);		
		billingAccountsPractice.setContentType( "application/json;" );
		billingAccountsPractice.addHeader( "Authorization", "Basic " + doctorAuth );
		log("Set Expected Status Code = 200");
		billingAccountsPractice.setExpectedStatusCode( 200 );
		assertTrue(billingAccountsPractice.get());
		
		log("Requesting from billing accounts - practice oauth");
		billingAccountsSyslevel.setServiceUrl(billingAccountGeneral);		
		billingAccountsSyslevel.setContentType( "application/json;" );
		billingAccountsSyslevel.addHeader( "Authorization", "bearer " + practiceAuth );
		log("Set Expected Status Code = 200");
		billingAccountsSyslevel.setExpectedStatusCode( 200 );		
		assertTrue(billingAccountsSyslevel.get());
		
		log("Requesting from merchant info - practice");
		merchantPractice.setServiceUrl(merchantRest.trim()+"me");		
		merchantPractice.setContentType( "application/json;" );
		merchantPractice.addHeader( "Authorization", "Basic " + doctorAuth );
		log("Set Expected Status Code = 200");
		merchantPractice.setExpectedStatusCode( 200 );		
		assertTrue(merchantPractice.get());
		
		if (IHGUtil.getEnvironmentType().toString().equals("PROD")){
			log("Prod system oath inaccessible, skipping");
		}		
		else {
			log("Requesting from merchant info - system level");
			merchantSyslevel.setServiceUrl(merchantRest.trim()+merchantID);		
			merchantSyslevel.setContentType( "application/json;" );
			merchantSyslevel.addHeader( "Authorization", "Bearer " + bearerAuth );
			log("Set Expected Status Code = 200");
			merchantSyslevel.setExpectedStatusCode( 200 );		
			assertTrue(merchantSyslevel.get());
		}
		log("Requesting from merchant logo");
		merchantLogo.setServiceUrl(merchantLogoRest);		
		merchantLogo.setContentType( "application/json;" );		
		log("Set Expected Status Code = 200");
		merchantLogo.setExpectedStatusCode( 200 );		
		assertTrue(merchantLogo.get("image/jpeg"));		
		
		log("Requesting from statements PDF fetch");
		statementsPractice.setServiceUrl(statementPDFRest);		
		statementsPractice.addHeader( "Authorization", "bearer " + practiceAuth );
		statementsPractice.setContentType( "application/json;" );		
		log("Set Expected Status Code = 200");
		statementsPractice.setExpectedStatusCode( 200 );		
		assertTrue(statementsPractice.get("application/pdf"));	
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendStmtVerifyNotificationsMessagesBalance() throws Exception {

		log(this.getClass().getName());
		RCMUtil util = new RCMUtil(driver);
		Mailinator mail = new Mailinator();
		
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Getting Test Data");
		PropertyFileLoader testData = new PropertyFileLoader();	
		
		log("Post eStatement");
		util.postStatementToPatient(testData.getRcmStatementRest(), IHGUtil.getEnvironmentType().toString());
		
		log("Check email notification and URL");
		String box = testData.getEmail().split("@")[0];		
		assertTrue(mail.catchNewMessageCheckLinkUrl(box, "Your patient eStatement is now available","Visit our website",testData.getUrl(), 50));
		
		log("Log in");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver,testData.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.getUserId(), testData.getPassword());		
		
		log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		log("Expect an estatement message");
		assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));
		
		log("Archive the message");
		jalapenoMessagesPage.archiveOpenMessage();
					
		jalapenoMessagesPage.goToPayBillsPage(driver);
		log("Check expected balance");
		String balance  = getBalanceDue(driver);
		assertTrue(testData.getStatementBalanceDue().equals(balance));
		log("Balance checks out!");
		
	}
	
	//
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSendDynamicElectronicFormat1() throws Exception {	    
		log(this.getClass().getName());
		log("Getting Test Data");
		
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
		
		//set up patient
		PatientStatementInfo newPat = activateNewPatientPrepareForStatement(driver, testDataFromProp,2);
		
		//Randomize balance, insert decimal dot
		Random rand = new Random();
		int minBal = 100;
		int maxBal = 99900;
		int ran = ((rand.nextInt(maxBal - minBal) + minBal + 1));
		if (ran % 10 == 0) ran++;
		String tmpBal = Integer.toString(ran);	
		newPat.balance = new StringBuffer(tmpBal).insert(tmpBal.length()-2, ".").toString();
		log("Setting up a modified statement");
		String endpoint = testDataFromProp.getRcmStatementRest() + testDataFromProp.getPracticeId() + "/patients/" + newPat.memberId + "/statements";
		newPat.billingAccountNumber = postModifiedStatementToPatient(endpoint, IHGUtil.getEnvironmentType().toString(), newPat.practicePatientId , newPat.balance);
		assertFalse(newPat.billingAccountNumber == -1);
		log("Statement was successfuly posted to user rest, to the following billing account number: " + newPat.billingAccountNumber);
		
		log("Waiting out 20s for the statement to arrive");
		Thread.sleep(20000);
						
		log("Log in back to patient portal");
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver,testDataFromProp.getUrl());
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(newPat.username, newPat.password);
		
		//check presence of badge (last payment date < newest statement date || (no payments present && statement arrived))
		log("No payments present, expect a badge with balance on Pay Bills");
		String badge = driver.findElement(By.xpath("//a[@id='feature_bill_pay']/span[@class='badge amountDue ng-binding']")).getText();
		log("Badge retrieved, expected balance: $" + newPat.balance  + " , found balance: " + badge);		
		assertTrue(badge.trim().equals("$"+newPat.balance));					
		
		log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		
		assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		log("Expect an estatement message");
		assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));
		
		log("Archive the message");
		jalapenoMessagesPage.archiveOpenMessage();
					
		jalapenoMessagesPage.goToPayBillsPage(driver);
		log("Check expected balance");
		String balance  = getBalanceDue(driver);
		assertTrue(("$"+newPat.balance).equals(balance));
		log("Balance checks out, yay!");
		log("Does it match from practice PoV as well though?");
		assertTrue(getBillingAccountInfoComparePatientBalance(testDataFromProp.getRcmBillingAccountRest(),Integer.toString(newPat.billingAccountNumber),testDataFromProp.getDoctorBase64AuthString(),newPat.balance));
		log("It also checks out with practice admin!");			
	}
	
	protected String getBalanceDue(WebDriver driver){
		try{
			log("Waiting for balance element.");
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='balanceDue']/span/span")));
			WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/span"));
			log("Displayed: " + balance.isDisplayed() + " amount: " + balance.getText());
			return balance.getText();
		}
		catch (Exception ex) {
			log("Exception from element caught, rechecking");
			WebElement balance = driver.findElement(By.xpath("//div[@id='balanceDue']/span/span"));
			log("Displayed: " + balance.isDisplayed() + " amount: " + balance.getText());
			return balance.getText();
		}
	}
	//This method is specifically intended for new patient test, does not guarantee the found balance is found in summary and not old statements (but new patients will only have one statement)
	protected boolean getBillingAccountInfoComparePatientBalance(String rcmBillingAccountRest, String billingAccountNumber, String staffAuthString, String balanceToFind){
		WebPoster poster = new WebPoster();		
		poster.setServiceUrl(rcmBillingAccountRest.trim()+billingAccountNumber);		
		poster.setContentType( "application/json;" );
		poster.addHeader( "Authorization", "Basic " + staffAuthString );
		log("Set Expected Status Code = 200");
		poster.setExpectedStatusCode( 200 );	// HTTP Status Code
		return poster.getAndSearchForMatch("\"customerBalance\":"+balanceToFind);
	}
	
	protected int postModifiedStatementToPatient(String endpoint, String env, String practicePatientId, String patientBalance) throws Exception {
		Assert.assertNotNull( 
				"### Endpoint is null!", 
				endpoint);
		IHGUtil.PrintMethodName();
		int min = 111111;
		int max = 999999;
		Random rand = new Random();
		String newBillingNumber = Integer.toString((rand.nextInt((max - min) + 1) + min));
		String payload;
		try {
			System.out.println("Building statement for " + env + " , setting patient to " + practicePatientId + ", generated balance " + patientBalance + " , new account number " + newBillingNumber);					
			URL url = ClassLoader.getSystemResource("testfiles/"+ env + "/statementEdited.txt");
			System.out.println(url.toString());
			Scanner scan = new Scanner(new File(url.getPath()));
			payload = scan.useDelimiter("\\Z").next();
			scan.close();
			payload = payload.replaceAll("BANPLACEHOLDER", newBillingNumber);
			payload = payload.replace("MRNPLACEHOLDER", practicePatientId);
			payload = payload.replace("BALANCEPLACEHOLDER", patientBalance);
			payload = payload.replace("STMTIDPLACEHOLDER", "DynTestPatientNum" + newBillingNumber);
			System.out.println("Stmt id: AutoBAN" + newBillingNumber);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return -1;
		}		
		WebPoster poster = new WebPoster();						
		poster.setServiceUrl(endpoint);		
		poster.setContentType( "application/json;" );
		poster.addHeader( "requestId", "rcmAutomationPost" + newBillingNumber );
		System.out.println("Using requestId: rcmAutomationPost" + newBillingNumber );
		poster.addHeader( "Authentication-Type", "2wayssl" );
		log("Expected Status Code = 200");
		poster.setExpectedStatusCode( 200 );	// HTTP Status Code
		log("send Statement to patient #####");
		poster.postFromString(payload);	
		return Integer.parseInt(newBillingNumber);		
	}
	
	protected PatientStatementInfo activateNewPatientPrepareForStatement(WebDriver driver, PropertyFileLoader testDataFromProp, int deliveryPref) throws Exception{
		log(this.getClass().getName());
		log("Creating a new patient to use in statements");
		
		//RCMUtil util = new RCMUtil(driver);
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		log("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);				
	
		log("Patient Activation on Practice Portal");
		String patMail = "eStMf."+IHGUtil.createRandomNumericString(6)+"@mailinator.com";
		String unlockLink = patientActivationSearchTest.PatientActivation(driver, practiceTestData, patMail, 
				testDataFromProp.getDoctorLogin(), testDataFromProp.getDoctorPassword(), testDataFromProp.getPortalUrl());
		JalapenoPatientActivationPage jalapenoPatientActivationPage;
		JalapenoHomePage jalapenoHomePage;		
		try	{
			log("Finishing of patient activation: step 1 - verifying identity");
			jalapenoPatientActivationPage = new JalapenoPatientActivationPage(driver, unlockLink);
			log("  Waiting up to 50 sec for 1st step activation page to load");
			@SuppressWarnings("unused")
			WebElement activationZipCode = (new WebDriverWait(driver, 50))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id("postalCode")));
			driver.manage().window().maximize();
			jalapenoPatientActivationPage.verifyPatientIdentity(PracticeConstants.Zipcode, PortalConstants.DateOfBirthMonth,
			PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);

			log("Finishing of patient activation: step 2 - filling patient data");
			jalapenoHomePage = jalapenoPatientActivationPage.fillInPatientActivationWithDeliveryPreference(patientActivationSearchTest.getFirstNameString(),
				testDataFromProp.getPassword(), testDataFromProp.getSecretQuestion(), 
				testDataFromProp.getSecretAnswer(), "1234567890", deliveryPref);
		}
		catch(Exception e){
			e.printStackTrace();
			log("Retrying");
			log("Finishing of patient activation: step 1 - verifying identity AGAIN.");
			jalapenoPatientActivationPage = new JalapenoPatientActivationPage(driver, unlockLink);
			log("  Waiting up to 50 sec for 1st step activation page to load.");
			@SuppressWarnings("unused")
			WebElement activationZipCode = (new WebDriverWait(driver, 50))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id("postalCode")));
			driver.manage().window().maximize();
			jalapenoPatientActivationPage.verifyPatientIdentity(PracticeConstants.Zipcode, PortalConstants.DateOfBirthMonth,
				PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);

			log("Finishing of patient activation: step 2 - filling patient data");
			jalapenoHomePage = jalapenoPatientActivationPage.fillInPatientActivationWithDeliveryPreference(patientActivationSearchTest.getFirstNameString(),
					testDataFromProp.getPassword(), testDataFromProp.getSecretQuestion(), 
					testDataFromProp.getSecretAnswer(), "1234567890", deliveryPref);
			
		}		
		
		log("Logging out");
		jalapenoHomePage.logout(driver);
		
		log("Back to Practice Portal to assign external ID");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practiceTestData.getUrl());
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testDataFromProp.getDoctorLogin(), testDataFromProp.getDoctorPassword());

		log("Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage= pPracticeHomePage.clickPatientSearchLink();

		log("Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(patientActivationSearchTest.getFirstNameString(), patientActivationSearchTest.getLastNameString());
		
		log("Open Patient Dashboard");
		PatientDashboardPage pPatientDashboardPage =  pPatientSearchPage.clickOnPatient(patientActivationSearchTest.getFirstNameString(), patientActivationSearchTest.getLastNameString());

		//save email
		log("@@@@@@@@@@@@@@@@@ " + patMail);
		
		log("Click Edit ID");
		List<WebElement> editButtons = driver.findElements(By.linkText("Edit"));
        editButtons.get(3).click();
        
        //read memberid and update MRN ( = external memberid)
		log("Update ID");
		String memberId = driver.findElement(By.xpath("//form[@name = 'edituserinfo']/table/tbody/tr[5]/td[2]")).getText();
		System.out.println("Found memberId: " + memberId);
        WebElement rsdkId = driver.findElement(By.name("patientid_78"));
        rsdkId.sendKeys(patientActivationSearchTest.getFirstNameString());
        driver.findElement(By.name("submitted")).click();        
		verifyEquals(true,pPatientDashboardPage.getFeedback().contains("Patient Id(s) Updated"));
				
		PatientStatementInfo result = new PatientStatementInfo(memberId, patientActivationSearchTest.getFirstNameString(), -1, "", "", patientActivationSearchTest.getFirstNameString(), testDataFromProp.getPassword(), deliveryPref);		
		return result;
	}
}
