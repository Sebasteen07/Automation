package com.intuit.ihg.product.integrationplatform.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.mu2.utils.MU2Constants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.practice.api.pojo.Practice;



public class StatementEventUtils {
	public List<String> eventList = new ArrayList<String>();
	String eventTime = null;
	String ActionTimestamp = null;
	List<String> portalTime = new ArrayList<String>();
	public static final String PracticePatientId = "PracticePatientId";
	public static final String FirstName = "FirstName";
	public static final String LastName = "LastName";
	public static final String PracticeResourceId = "PracticeResourceId";
	public static final String RESOURCE_TYPE = "EStatement";
	
	public void generateViewEvent(WebDriver driver,StatementEventData testData) throws Exception {
		
		StatementsMessagePayload SMPObj = new StatementsMessagePayload();
		String statement =SMPObj.getStatementsMessagePayload(testData);
		
		
		Log4jUtil.log("Statement Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken, testData.OAuthUsername,
				testData.OAuthPassword);
		
		Log4jUtil.log("Statement Step 2: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, statement, testData.ResponsePath);
		Log4jUtil.log("processing Status"+processingUrl);
		
		Log4jUtil.log("Statement Step 3: Get processing status until it is completed");
		
		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 60 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		Assert.assertTrue(completed, "Message processing was not completed in time");
		
		if(testData.StatementType.equalsIgnoreCase("NEW")) {
			Thread.sleep(2000);
			Log4jUtil.log("step 6: Login to Practice Portal");
			
			Practice practice = new Practice();
			practice.url = testData.portalURL; 
			practice.username = testData.practiceUserName; 
			practice.password = testData.practicePassword;
			
			// Now start login with practice data
			PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, practice.url);
			PracticeHomePage pPracticeHomePage = practiceLogin.login(practice.username, practice.password);

			Log4jUtil.log("step 7: Click on Patient Search Link");
			PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

			Log4jUtil.log("step 8:Set Patient Search Fields");
			pPatientSearchPage.searchForPatientInPatientSearch(testData.FirstName, testData.LastName);

			Log4jUtil.log("step 9:Verify the Search Result");
			IHGUtil.waitForElement(driver, 30, pPatientSearchPage.searchResult);
			
			String searchResult = "//*[@id=\"table-1\"]/tbody/tr/td[1]/a";
			driver.findElement(By.xpath(searchResult)).click();
			
			String editPatientD = "//*[@id=\"dashboard\"]/fieldset[1]/table/tbody/tr[5]/td[2]/a";
			driver.findElement(By.xpath(editPatientD)).click();
			
			String externalID = "//*[@id=\"content\"]/form/table/tbody/tr[7]/td[2]/input";
			String patientExternalID = driver.findElement(By.xpath(externalID)).getAttribute("value");
			
			Log4jUtil.log("Actual patient ID "+patientExternalID);
			Log4jUtil.log("Expected patient ID "+SMPObj.patientID);
			
			Assert.assertEquals(SMPObj.patientID, patientExternalID, "Patient External ID Matched !");

			Log4jUtil.log("On Demand PatientID "+patientExternalID);	
			
			pPracticeHomePage.logOut();
		}
		
		Mailinator mail = new Mailinator();
		String link = mail.getLinkFromEmail(testData.Email, testData.emailSubject,testData.PracticeName, 5);
		Assert.assertTrue(link!=null, "Statement Message link not found in mail.");
		Log4jUtil.log("statement link  "+link);
		
		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, link);
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.UserName, testData.Password);
		
		Log4jUtil.log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='messageActions']")));
		Assert.assertTrue(jalapenoMessagesPage.assessMessagesElements());
		
		Log4jUtil.log("Expect an estatement message");
		Assert.assertTrue(jalapenoMessagesPage.isMessageFromEstatementsDisplayed(driver));
		
		WebElement messageTimeStamp = driver.findElement(By.xpath("//*[@id=\"messageContainer\"]/div[3]/div[2]/div[1]/span[4]"));
		Log4jUtil.log("messageTimeStamp : "+messageTimeStamp.getText());
		
		long timeStamp = System.currentTimeMillis();
		
		Log4jUtil.log("Statement Link text");
		jalapenoMessagesPage.openPDFStatement();
		
		List<WebElement> frameList=driver.findElements(By.tagName("iframe"));
		Log4jUtil.log("iFrame "+frameList.get(1).getAttribute("id"));
		
		driver.switchTo().frame(frameList.get(1).getAttribute("id"));
		Log4jUtil.log("Frame Title is "+driver.getTitle());
		
		Log4jUtil.log("Wait for pdf container to load.");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@id,'pageContainer1')]")));
		
		//This is temporary Need to create a Page Object for PDF Viewer.
		WebElement amountDue = driver.findElement(By.xpath("//*[contains(@id,'pageContainer1')]/div[2]/div[7]"));
		WebElement paymentDueDate = driver.findElement(By.xpath("//*[contains(@id,'pageContainer1')]/div[2]/div[6]"));
		WebElement accountNumber = driver.findElement(By.xpath("//*[contains(@id,'pageContainer1')]/div[2]/div[5]"));
		WebElement statementComment = driver.findElement(By.xpath("//*[contains(@id,'pageContainer1')]/div[2]/div[12]"));
		WebElement dunningComment = driver.findElement(By.xpath("//*[contains(@id,'pageContainer1')]/div[2]/div[27]"));
		WebElement practiceName = driver.findElement(By.xpath("//*[contains(@id,'pageContainer1')]/div[2]/div[11]"));
		WebElement totalCharge = driver.findElement(By.xpath("//*[contains(@id,'pageContainer1')]/div[2]/div[15]"));
		
		Log4jUtil.log(amountDue.isDisplayed()+" Expected amount Due is :- "+amountDue.getText());	
		Log4jUtil.log("amountDue: "+SMPObj.amountDue);
		Log4jUtil.log("portal PaymentDueDate: "+paymentDueDate.getText()+" and DueDate in payload "+SMPObj.paymentPortalDueDate);
		Log4jUtil.log("billAccountNumber: "+accountNumber.getText());
		Log4jUtil.log("StatementComment: "+statementComment.getText());
		Log4jUtil.log("DunningMessage: "+dunningComment.getText());
		Log4jUtil.log("practiceName: "+practiceName.getText());
		Log4jUtil.log("totalCharge: "+totalCharge.getText());
		
		Assert.assertEquals(amountDue.getText(), SMPObj.amountDue);
		Assert.assertEquals(paymentDueDate.getText(), SMPObj.paymentPortalDueDate);
		Assert.assertEquals(accountNumber.getText(), SMPObj.billAccountNumber);
		Assert.assertEquals(statementComment.getText(), testData.StatementComment);
		Assert.assertEquals(dunningComment.getText(), testData.DunningMessage);
		Assert.assertEquals(practiceName.getText(), testData.PracticeName);
		Assert.assertEquals(totalCharge.getText(), "$"+testData.TotalCharges);
	
		driver.switchTo().defaultContent();
	    long transitTimeStamp1 = System.currentTimeMillis();
	    Thread.sleep(8000);
	    JalapenoPayBillsMakePaymentPage jalapenoMenuObj = new JalapenoPayBillsMakePaymentPage(driver);
	    jalapenoHomePage.clickOnMenuPayBills();
	    Thread.sleep(2000);
	   
	    WebElement paymentDue = driver.findElement(By.xpath("//*[@id=\"statementPanel\"]/div/div/div[1]/table/tfoot/tr/td[2]"));
	    Log4jUtil.log("due Amount "+paymentDue.getText());
	   
	    //long transitTimeStamp2 = System.currentTimeMillis();
	    jalapenoMenuObj.gotoStatementDetail();
	   
	    Thread.sleep(3000);
	   
	    driver.switchTo().defaultContent();
	    jalapenoHomePage.clickOnLogout();
	   
	    Log4jUtil.log("Waiting for Statement Events to Sync");
	    Thread.sleep(430000);	
	    String getEvent =testData.StatementEventURL+timeStamp;
	    Log4jUtil.log("Get Link"+getEvent);
	    RestUtils.setupHttpGetRequest(getEvent, testData.ResponsePath);
	   
	   
	    Log4jUtil.log("transitTimeStamp :- "+transitTimeStamp1);  
	    //Log4jUtil.log("transitTimeStamp2 :- "+transitTimeStamp2);
	    Thread.sleep(5000);
	   
		List<String> list = eventList();
		for (int i = 0; i < list.size(); i++) {
			// verify "View" event in response XML and return Action Time stamp
		Log4jUtil.log("Verification of CCD '" + list.get(i) + "' event present in Pull API response xml");
		ActionTimestamp = findEventInResonseXML(testData.ResponsePath, MU2Constants.EVENT, RESOURCE_TYPE, list.get(i),
				timeStamp, testData.MFPatientID,testData.PatientID,testData.FirstName,testData.LastName,transitTimeStamp1);
		Assert.assertNotNull(ActionTimestamp, "'" + list.get(i) + "' Event is not found in Response XML");
		Log4jUtil.log("ActionTimestamp: "+ActionTimestamp);
		Log4jUtil.log("TYPE FOUND: "+list.get(i));
		eventTime = generateDate(ActionTimestamp);
		portalTime.add(eventTime);
		Log4jUtil.log("EStatement '" + list.get(i) + "' event portal Time: " + eventTime);
		}
	}
	
	
	public String findEventInResonseXML(String xmlFileName, String event, String resourceType, String action, Long timeStamp, String practicePatientID,String patientExternalId,String firstName,String lastName,long transmitTimestamp) {
		IHGUtil.PrintMethodName();

		String ActionTimestamp = null;
		try {

			File stocks = new File(xmlFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName(event);
			for (int i = 0; i < nodes.getLength(); i++) {
				
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String readValue;
					readValue = getValue(MU2Constants.EVENT_RECORDED_TIMESTAMP, element);
					Long recordedTimeStamp = Long.valueOf(readValue);
					
					if(transmitTimestamp > recordedTimeStamp && action.contains("View")) {
						Log4jUtil.log("Event Generated From Email View");
					}
					if(transmitTimestamp < recordedTimeStamp && action.contains("View")) {
						Log4jUtil.log("Event Generated From Paybill View");
					}
					if (recordedTimeStamp >= timeStamp) {
						 
						if (getValue(MU2Constants.RESOURCE_TYPE_NODE, element).equalsIgnoreCase(resourceType)
								&& getValue(MU2Constants.ACTION_NODE, element).equalsIgnoreCase(action)
								&& getValue(MU2Constants.INTUIT_PATIENT_ID, element).equalsIgnoreCase(practicePatientID)
								&& getValue(PracticePatientId, element).equalsIgnoreCase(patientExternalId)
								&& getValue(FirstName, element).equalsIgnoreCase(firstName)
								&& getValue(LastName, element).equalsIgnoreCase(lastName)
								) {
								
								
								Log4jUtil.log("Matching response medfusionId "+getValue(MU2Constants.INTUIT_PATIENT_ID, element)+" with "+practicePatientID);
								Log4jUtil.log("Matching response patientExternalId "+getValue(PracticePatientId, element)+" with "+patientExternalId);
								Log4jUtil.log("Matching response firstName "+getValue(FirstName, element)+" with "+firstName);
								Log4jUtil.log("Matching response lastName "+getValue(LastName, element)+" with "+lastName);
								
								ActionTimestamp = getValue(MU2Constants.EVENT_RECORDED_TIMESTAMP, element);
							
							//break;
						}
					} else {
						 
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ActionTimestamp;
	}
	
	
	public List<String> eventList() {
		eventList.add(MU2Constants.VIEW_ACTION);	
		return eventList;
	}
	
	private String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
	
	public String generateDate(String actualTimeStamp) throws ParseException {
		long EventRecordedTimestamp = Long.parseLong(actualTimeStamp);
		Date date = new Date(EventRecordedTimestamp);
		DateFormat gmtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm ");
		TimeZone estTime = TimeZone.getTimeZone("America/New_York");
		gmtFormat.setTimeZone(estTime);
		String data[] = gmtFormat.format(date).split(" ");
		String joinedDate = new StringBuilder(data[0]).append(" at ").append(data[1]).toString();
		return joinedDate;
	}
	
	public String getMedfusionID(String email,String SearchStringFrom) throws IOException {
		String medfusionID ="";	
		String[] patientArray = SearchStringFrom.split("</Patient>");
		
		String batchSize = ExtractString("<BatchSize>(.*?)</BatchSize>",SearchStringFrom);
		Log4jUtil.log(" batchSize "+batchSize);
		int batchSizeInt = Integer.parseInt(batchSize);
		if (batchSizeInt == 1) {
			medfusionID = ExtractString("<MedfusionPatientId>(.*?)</MedfusionPatientId>", SearchStringFrom);
		} else {
			
		for(int i=0;i<patientArray.length;i++) {
			
			if(patientArray[i].contains(email)) {
				medfusionID = ExtractString("<MedfusionPatientId>(.*?)</MedfusionPatientId>", patientArray[i]);
			}
		}
		}
		
		return medfusionID;
	}
	
	public String ExtractString(String findStringBetween, String SearchStringFrom) {
		String PatternMatch = null;
		Pattern pattern = Pattern.compile(findStringBetween);
		Matcher matcher = pattern.matcher(SearchStringFrom);
		if (matcher.find()) {
			PatternMatch = matcher.group(1);
		}
		return PatternMatch;
	}

}
