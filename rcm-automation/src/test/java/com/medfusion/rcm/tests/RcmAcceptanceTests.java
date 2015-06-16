package com.medfusion.rcm.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.WebPoster;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.common.utils.mail.Harakirimail;
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
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.MyAccountPage.JalapenoMyAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.PatientActivationPage.JalapenoPatientActivationPage;
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
	public void testSendStmtVerifyNotificationsMessagesBalance() throws Exception {

		log(this.getClass().getName());
		RCMUtil util = new RCMUtil(driver);
		Harakirimail mail = new Harakirimail(driver);
		
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
	
	//It's ugly, I'm very, very aware of that, on the other hand keeping in intact lets us react and modify the test much faster and easier.
	//Will be split under utilities with only the main steps here, once the logic is stable for at least a whole release. 
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testActivatePatientSendStatement() throws Exception {	    
		
		log(this.getClass().getName());
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		//RCMUtil util = new RCMUtil(driver);
		Harakirimail mail = new Harakirimail(driver);
		PatientActivationSearchTest patientActivationSearchTest = new PatientActivationSearchTest();

		log("Getting Test Data");
		Practice practice = new Practice();
		PracticeTestData practiceTestData = new PracticeTestData(practice);

		//Randomize balance, insert decimal dot
		Random rand = new Random();
		int minBal = 100;
		int maxBal = 150000;
		String newBal = Integer.toString((rand.nextInt(maxBal - minBal) + minBal + 1));	
		newBal = new StringBuffer(newBal).insert(newBal.length()-2, ".").toString();
		
		PropertyFileLoader testDataFromProp = new PropertyFileLoader();
	
		log("Patient Activation on Practice Portal");
		String unlockLink = patientActivationSearchTest.PatientActivation(driver, practiceTestData, "eStMf@harakirimail.com", 
				testDataFromProp.getDoctorLogin(), testDataFromProp.getDoctorPassword(), testDataFromProp.getPortalUrl());
		JalapenoPatientActivationPage jalapenoPatientActivationPage;
		JalapenoHomePage jalapenoHomePage;
		try	{
			log("Finishing of patient activation: step 1 - verifying identity");
			jalapenoPatientActivationPage = new JalapenoPatientActivationPage(driver, unlockLink);
			jalapenoPatientActivationPage.verifyPatientIdentity(PracticeConstants.Zipcode, PortalConstants.DateOfBirthMonth,
			PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);

			log("Finishing of patient activation: step 2 - filling patient data");
			jalapenoHomePage = jalapenoPatientActivationPage.fillInPatientActivation("",
				testDataFromProp.getPassword(), testDataFromProp.getSecretQuestion(), 
				testDataFromProp.getSecretAnswer(), testDataFromProp.getphoneNumer());
		}
		catch(Exception e){
			e.printStackTrace();
			log("Retrying");
			log("Finishing of patient activation: step 1 - verifying identity");
			jalapenoPatientActivationPage = new JalapenoPatientActivationPage(driver, unlockLink);
			Thread.sleep(5000);
			jalapenoPatientActivationPage.verifyPatientIdentity(PracticeConstants.Zipcode, PortalConstants.DateOfBirthMonth,
				PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);

			log("Finishing of patient activation: step 2 - filling patient data");
			jalapenoHomePage = jalapenoPatientActivationPage.fillInPatientActivation(patientActivationSearchTest.getPatientIdString(),
					testDataFromProp.getPassword(), testDataFromProp.getSecretQuestion(), 
					testDataFromProp.getSecretAnswer(), testDataFromProp.getphoneNumer());
			
		}
		log("Expecting preference dialog, waiting up to 40s");
		WebElement elem = (new WebDriverWait(driver, 40))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id("paymentPreference_Paper")));
		assertTrue(elem.isEnabled());		
		driver.findElement(By.id("paymentPreference_Electronic")).click();
		driver.findElement(By.id("updateStatementPrefButton")).click();
		
		
		log("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.assessHomePageElements());
	
		log("Checking if address in My Account is filled");
		JalapenoMyAccountPage jalapenoMyAccountPage = jalapenoHomePage.clickOnMyAccount(driver);
		assertTrue(jalapenoMyAccountPage.checkForAddress(driver, "5501 Dillard Dr", "Cary", PracticeConstants.Zipcode));
	
		log("Logging out");
		JalapenoLoginPage jalapenoLoginPage = jalapenoHomePage.logout(driver);
		
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
		WebElement patMailElement = driver.findElement(By.xpath("//table[@class='demographics']/tbody/tr[6]/td[2]"));
		String patMail = patMailElement.getText().trim();
		log("@@@@@@@@@@@@@@@@@ " + patMail);
		
		log("Click Edit ID");
		List<WebElement> editButtons = driver.findElements(By.linkText("Edit"));
        editButtons.get(3).click();
        
		log("Update ID");
        WebElement rsdkId = driver.findElement(By.name("patientid_78"));
        rsdkId.sendKeys(patientActivationSearchTest.getFirstNameString());
        driver.findElement(By.name("submitted")).click();;
		verifyEquals(true,pPatientDashboardPage.getFeedback().contains("Patient Id(s) Updated"));				
			
		log("Setting up a modified statement");
		postModifiedStatementToPatient(testDataFromProp.getRcmStatementRest(), IHGUtil.getEnvironmentType().toString(),patientActivationSearchTest.getFirstNameString(),newBal,true);
		log("Checking mail");
		
		log("Check email notification and URL");
		
		String box = patMail.split("@")[0];		
		assertTrue(mail.catchNewMessageCheckLinkUrl(box, "Your patient eStatement is now available","Visit our website",testDataFromProp.getUrl(), 50));
		
		log("Log in");
		jalapenoLoginPage = new JalapenoLoginPage(driver,testDataFromProp.getUrl());
		jalapenoHomePage = jalapenoLoginPage.login(testDataFromProp.getUserId(), testDataFromProp.getPassword());		
		
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
		assertTrue(("$"+newBal).equals(balance));
		
		log("Balance checks out, yay!");
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
	protected void postModifiedStatementToPatient(String rcmStatementRest, String env, String practicePatientId, String patientBalance, boolean randomize) throws Exception {

		IHGUtil.PrintMethodName();
		int min = 111111;
		int max = 999999;
		try {
			System.out.println("Modifying XML resource for " + env + " , setting patient to " + practicePatientId + ", balance " + patientBalance);
			URL url = ClassLoader.getSystemResource("testfiles/"+ env + "/statementEdited.xml");
			System.out.println(url.toString());
			File file = new File(url.getPath());
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			doc.getDocumentElement().normalize();
			
			Node patientIdNode = doc.getElementsByTagName("PracticePatientId").item(0);
			Node patientBalanceNode = doc.getElementsByTagName("PatientBalance").item(0);
			Node billingAccountNumberNode = doc.getElementsByTagName("StatementBillingAccountNumber").item(0);
			
			
			System.out.println("###  PracticePatientId found: " + patientIdNode.getTextContent());
			patientIdNode.setTextContent(practicePatientId);
			System.out.println("###    changing to: " + patientIdNode.getTextContent());
			System.out.println("###  PatientBalance found: " + patientBalanceNode.getTextContent());
			patientBalanceNode.setTextContent(patientBalance);			
			System.out.println("###    changing to: " + patientBalanceNode.getTextContent());
			if(randomize){				
				Random rand = new Random();
				String newBillingNumber = Integer.toString((rand.nextInt((max - min) + 1) + min));
				System.out.println("###  New billing account number: " + newBillingNumber);
				billingAccountNumberNode.setTextContent(newBillingNumber);
			}
			else{
				System.out.println("###  Using last billing account number : " + billingAccountNumberNode.getTextContent());
			}
			
			System.out.println("###  saving changes to classloaded file");
			TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            DOMSource source = new DOMSource(doc);

            transformer.transform(source, result);

            String strTemp = writer.toString();

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(strTemp);
            bufferedWriter.flush();
            bufferedWriter.close();
			
            System.out.println("### Great success!");
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();			
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();			
		}
		
		WebPoster poster = new WebPoster();
				
		Assert.assertNotNull( 
				"### Test property rcmStatementRest not defined", 
				rcmStatementRest);
		poster.setServiceUrl( rcmStatementRest.trim() );
		
		poster.setContentType( "application/xml;" );
		poster.addHeader( "requestId", "d3baaa87-1010-1010-1010-123456789000" );
		poster.addHeader( "Authentication-Type", "2wayssl" );
		log("Expected Status Code =#####");
		poster.setExpectedStatusCode( 202 );	// HTTP Status Code
		log("send Statement to patient #####");
		poster.postFromResourceFile( 
					"testfiles/" 
					+ env
					+ "/statementEdited.xml" );			
				
		
	}
}
