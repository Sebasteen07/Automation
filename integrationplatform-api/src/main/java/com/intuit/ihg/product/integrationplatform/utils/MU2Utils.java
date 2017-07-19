package com.intuit.ihg.product.integrationplatform.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.mu2.utils.MU2Constants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;

public class MU2Utils {
	public static List<String> eventList = new ArrayList<String>();
	public ArrayList<String> ccdMessageList = new ArrayList<String>(10);
	public ArrayList<String> messageTimeStampList = new ArrayList<String>(10);
	public static final String PracticePatientId = "PracticePatientId";
	public static final String FirstName = "FirstName";
	public static final String LastName = "LastName";
	public static final String PracticeResourceId = "PracticeResourceId";
	private String eventTime = null;
	private String ActionTimestamp = null;
	private List<String> portalTime = new ArrayList<String>();
	
	public void mu2GetEvent(MU2GetEventData testData,WebDriver driver)  throws Exception {

		long timestamp = System.currentTimeMillis();
		Log4jUtil.log("TIME STAMP for MU2 Pull API SinceTime: " + Long.toString(timestamp));
		
		Log4jUtil.log("URL:"+testData.PORTAL_URL);
		Log4jUtil.log("PORTAL_USERNAME:"+testData.PORTAL_USERNAME);
		Log4jUtil.log("PORTAL_PASSWORD:"+testData.PORTAL_PASSWORD);
		
		ccdMessageList.add(testData.CCDMessageID1);
		ccdMessageList.add(testData.CCDMessageID2);
		
		Log4jUtil.log("MU2GetEvent Step 1: LogIn");
		Log4jUtil.log("Practice URL: " + testData.PORTAL_URL);
		
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.PORTAL_URL);
		JalapenoHomePage homePage = loginPage.login(testData.PORTAL_USERNAME, testData.PORTAL_PASSWORD);
		
		Thread.sleep(10000);
		
		Log4jUtil.log("Detecting if Home Page is opened");
		Assert.assertTrue(homePage.isHomeButtonPresent(driver));
		
		Log4jUtil.log("MU2GetEvent Step 2: Go to Health Record Summaries");
		MedicalRecordSummariesPage MedicalRecordSummariesPageObject = homePage.clickOnMedicalRecordSummaries(driver);
		Assert.assertTrue(MedicalRecordSummariesPageObject.areBasicPageElementsPresent(), "Failed to Load Health Record Summaries ");
		
		MedicalRecordSummariesPageObject.setFilterToDefaultPositionAndCheckElementsNew();
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400)", "");
		
		Log4jUtil.log("MU2GetEvent Step 3: Select first and second CCD from the list");
		MedicalRecordSummariesPageObject.selectFirstVisibleCCD();
		MedicalRecordSummariesPageObject.selectSecondVisibleCCD();
		Log4jUtil.log("MU2GetEvent Step 4: Transmit Email Direct Protocol and Standard Email");
		MedicalRecordSummariesPageObject.sendFirstVisibleCCDUsingDirectProtocol(testData.TRANSMIT_EMAIL);
		Thread.sleep(5000);
		long transmitTimestamp = System.currentTimeMillis();
		Log4jUtil.log("TransmitTimestamp :"+transmitTimestamp);
		MedicalRecordSummariesPageObject.sendFirstVisibleCCDUsingStandardEmail(testData.Standard_Email);
		
		Thread.sleep(5000);
		
		Log4jUtil.log("Page refreshing...");
		
		jse.executeScript("window.scrollBy(0,200)", "");
		
		//code to Download CCD on fire fox 
		Log4jUtil.log("Step 6: Download CCD");
		
		MedicalRecordSummariesPageObject.selectDownload();
		Thread.sleep(8000);
		performRobotTask(driver,true);
		
		Log4jUtil.log("====== Consolidated CCD - VDT events generated successfully ======");
		Thread.sleep(5000);
		
		homePage.clickOnLogout();
		
		getMUEventAndVerify(testData, timestamp,transmitTimestamp);
	}
	
	public void getMUEventAndVerify(MU2GetEventData testData,Long timestamp,Long transmitTimestamp) throws Exception {
		
		Log4jUtil.log("MU2GetEvent Step 7: Waiting for Events sync in DWH");
		Thread.sleep(720000);
		
		Log4jUtil.log("MU2GetEvent Step 8: Setup Oauth client 2.O");
		RestUtils.oauthSetup(testData.OAUTH_KEYSTORE, testData.OAUTH_PROPERTY, testData.OAUTH_APPTOKEN, testData.OAUTH_USERNAME,testData.OAUTH_PASSWORD);
		
		// Build new Rest URL with epoch milliseconds
		Log4jUtil.log("Original PULL API URl: " + testData.PULLAPI_URL);

		String restPullUrl = new StringBuilder(testData.PULLAPI_URL).append("&sinceTime=").append(timestamp).append("&maxEvents=40").toString();
		Log4jUtil.log("Updated PULL API URL: " + restPullUrl);

		Log4jUtil.log("Step 9: Send Pull API HTTP GET Request and save response to " +testData.PUSH_RESPONSEPATH);
		
		RestUtils.setupHttpGetRequest(restPullUrl, testData.PUSH_RESPONSEPATH);
		
		Thread.sleep(5000);
		
		String intuitPatientID1 = testData.INTUIT_PATIENT_ID;
		Log4jUtil.log("First practicePatientID: " + intuitPatientID1);
		Log4jUtil.log("External ID: " + testData.PatientExternalId_MU2);
		Log4jUtil.log("FirstName: " + testData.PatientFirstName_MU2);
		Log4jUtil.log("LastName: " + testData.PatientLastName_MU2);
		
		List<String> list = eventList();
		for (int i = 0; i < list.size(); i++) {
			// verify "View" event in response XML and return Action Time stamp
			Log4jUtil.log("Verification of CCD '" + list.get(i) + "' event present in Pull API response xml");
			ActionTimestamp = findEventInResonseXML(testData.PUSH_RESPONSEPATH, MU2Constants.EVENT, MU2Constants.RESOURCE_TYPE, list.get(i),
					timestamp, intuitPatientID1,testData.PatientExternalId_MU2,testData.PatientFirstName_MU2,testData.PatientLastName_MU2,transmitTimestamp);
			if(!list.get(i).equalsIgnoreCase(MU2Constants.DOWNLOAD_ACTION)) {
				Assert.assertNotNull(ActionTimestamp, "'" + list.get(i) + "' Event is not found in Response XML");
			}
			
			Log4jUtil.log("ActionTimestamp: "+ActionTimestamp);
			Log4jUtil.log("TYPE FOUND: "+list.get(i));
			eventTime = generateDate(ActionTimestamp);
			portalTime.add(eventTime);
			Log4jUtil.log("CCD '" + list.get(i) + "' event portal Time: " + eventTime);
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

					if (recordedTimeStamp >= timeStamp) {
						
						if (getValue(MU2Constants.RESOURCE_TYPE_NODE, element).equalsIgnoreCase(resourceType)
								&& getValue(MU2Constants.ACTION_NODE, element).equalsIgnoreCase(action)
								&& getValue(MU2Constants.INTUIT_PATIENT_ID, element).equalsIgnoreCase(practicePatientID)
								&& getValue(PracticePatientId, element).equalsIgnoreCase(patientExternalId)
								&& getValue(FirstName, element).equalsIgnoreCase(firstName)
								&& getValue(LastName, element).equalsIgnoreCase(lastName)
								) {
							
							if(action.contains("Transmit")){
								Log4jUtil.log("transmitTimestamp "+transmitTimestamp+" compare with recordedTimeStamp "+recordedTimeStamp);
							}
							if (transmitTimestamp > recordedTimeStamp && action.contains("Transmit")) {
								Log4jUtil.log("Direct Email");
							}
							if (transmitTimestamp < recordedTimeStamp && action.contains("Transmit")) {
								Log4jUtil.log("Standard Protocol");
							}

								for(int j=0;j<ccdMessageList.size();j++) {
									if(getValue(PracticeResourceId, element).equalsIgnoreCase(ccdMessageList.get(j)) ) {
										Log4jUtil.log("Matching response practiceResourceId (CCDMessageId) "+getValue(PracticeResourceId, element)+" with "+ccdMessageList.get(j));
									}
								}
								
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
	
	public static List<String> eventList() {
		eventList.add(MU2Constants.TRANSMIT_ACTION);
		eventList.add(MU2Constants.VIEW_ACTION);
		eventList.add(MU2Constants.DOWNLOAD_ACTION);
		return eventList;
	}
	
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
	
	public static String generateDate(String actualTimeStamp) throws ParseException {
		long EventRecordedTimestamp = Long.parseLong(actualTimeStamp);
		Date date = new Date(EventRecordedTimestamp);
		DateFormat gmtFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a");
		//replacing "America/New_York" with local zone id.
		TimeZone estTime = TimeZone.getTimeZone(TimeZone.getDefault().toZoneId());
		gmtFormat.setTimeZone(estTime);
		String data[] = gmtFormat.format(date).split(" ");
		String joinedDate = new StringBuilder(data[0]).append(" at ").append(data[1]).toString();
		return joinedDate;
	}
	
	public static String ExtractString(String findStringBetween, String SearchStringFrom) {
		String PatternMatch = null;
		Pattern pattern = Pattern.compile(findStringBetween);
		Matcher matcher = pattern.matcher(SearchStringFrom);
		if (matcher.find()) {
			PatternMatch = matcher.group(1);
		}
		return PatternMatch;
	}
	
	public String readFromFile(String filePath) throws IOException {
		String textFileString = null;
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			textFileString = sb.toString();
		} finally {
			br.close();
		}
		textFileString = StringEscapeUtils.unescapeXml(textFileString);
		return textFileString;
	}
	
	public String getMedfusionID(String filePath,String patientDetail) throws IOException {
		String getResponse = readFromFile(filePath);
		String[] patientArray = getResponse.split("</Patient>");
		String batchSize = ExtractString("<BatchSize>(.*?)</BatchSize>",getResponse);
		String medfusionID ="";	
		int batchSizeInt = Integer.parseInt(batchSize);
		if (batchSizeInt == 1) {
			medfusionID = ExtractString("<MedfusionPatientId>(.*?)</MedfusionPatientId>", getResponse);
		} else {
			medfusionID = ExtractString("<MedfusionPatientId>(.*?)</MedfusionPatientId>", patientArray[patientArray.length - 2]);
		}
		return medfusionID;
	}
	
	public void mu2GetEventGaurdian(MU2GetEventData testData,WebDriver driver,Boolean existingGaurdian) throws Exception {
		Long timestamp = System.currentTimeMillis();
		String currentDate = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm a").format(new java.util.Date (timestamp));
		Log4jUtil.log(currentDate);
		String username = "";
		if(existingGaurdian) {
			testData.INTUIT_PATIENT_ID = testData.intuit_PATIENT_ID_MU2_Gaurdian_Existing;
			testData.PatientExternalId_MU2=testData.patientUA_ExternalPatientID_MU2_Existing;
			testData.PatientFirstName_MU2=testData.patientUA_ExternalPatientID_MU2_Existing;
			testData.PatientLastName_MU2=testData.patientUA_MU2_LastName_Existing;
			testData.PatientPassword=testData.gaurdian_Password_MU2_Existing;
			username = testData.gaurdian_UserName_MU2_Existing;
		}
		else {
			testData.INTUIT_PATIENT_ID = testData.intuit_PATIENT_ID_MU2_Gaurdian;
			testData.PatientExternalId_MU2=testData.patientUA_ExternalPatientID_MU2;
			testData.PatientFirstName_MU2=testData.patientUA_ExternalPatientID_MU2;
			testData.PatientLastName_MU2=testData.patientUA_MU2_LastName;
			testData.PatientPassword=testData.gaurdian_Password_MU2;
			username = testData.gaurdian_UserName_MU2;
		}
		
		Log4jUtil.log("mu2GetEventGaurdian Step 1: Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.PORTAL_URL);
		JalapenoHomePage homePage = loginPage.login(username, testData.PatientPassword);
		ccdMessageList.add(testData.CCDMessageID1);
		Log4jUtil.log("Detecting if Home Page is opened");
		Assert.assertTrue(homePage.isHomeButtonPresent(driver));
		
		if(existingGaurdian) {
			homePage.faChangePatient();
			Assert.assertTrue(homePage.assessFamilyAccountElements(true));
		}
		
		Log4jUtil.log("mu2GetEventGaurdian Step 2: Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		Assert.assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		Log4jUtil.log("mu2GetEventGaurdian Step 3: Validate message subject and send date");
		Thread.sleep(1000);
		Log4jUtil.log("Message Date" + IHGUtil.getEstTiming());
		Assert.assertTrue(messagesPage.isMessageDisplayed(driver, "You have a new health data summary"));
		Log4jUtil.log("CCD sent date & time is : " + messagesPage.returnMessageSentDate());

		Log4jUtil.log("mu2GetEventGaurdian Step 4: Click on link View health data");
		JalapenoCcdViewerPage jalapenoCcdPage = messagesPage.findCcdMessage(driver);
		
		Log4jUtil.log("mu2GetEventGaurdian Step 5: Verify if CCD Viewer is loaded and click Close Viewer");
		Assert.assertTrue(jalapenoCcdPage.areBasicPageElementsPresent());
		
		Log4jUtil.log("mu2GetEventGaurdian Step 6: SecureEmail event Generated");
		Boolean isSecureMessageTransmit = jalapenoCcdPage.sendInformationToDirectEmail(testData.TRANSMIT_EMAIL);
		Assert.assertTrue(isSecureMessageTransmit);
		Log4jUtil.log("isSecureMessageTransmit :"+isSecureMessageTransmit);
		Long transmitTimestamp = System.currentTimeMillis();
		Log4jUtil.log("transmitTimestamp "+transmitTimestamp);
		Thread.sleep(800);
		Log4jUtil.log("mu2GetEventGaurdian Step 7: UnsecureEmail event Generated");
		jalapenoCcdPage.sendInformationToUnsecureEmail(testData.Standard_Email);
		
		Log4jUtil.log("mu2GetEventGaurdian Step 8: Save pdf event ");
		jalapenoCcdPage.savePdf();
		Thread.sleep(4000);
		performRobotTask(driver,false);
		Log4jUtil.log("mu2GetEventGaurdian Step 9: Save raw xml event ");
		jalapenoCcdPage.saveRawXML();
		Thread.sleep(4000);
		performRobotTask(driver,true);
		messagesPage = jalapenoCcdPage.closeCcd(driver);
		Assert.assertTrue(messagesPage.areBasicPageElementsPresent());
		homePage = messagesPage.backToHomePage(driver);
		
		getMUEventAndVerify(testData, timestamp,transmitTimestamp);
		
		Log4jUtil.log("mu2GetEventGaurdian Step 10: Move to Account Activity page ");
		
		JalapenoAccountPage accountPage= homePage.clickOnAccount();
		JalapenoMyAccountProfilePage accountProfilePage= accountPage.clickOnEdiDependentAccount();
		Thread.sleep(4000);
		accountProfilePage.goToActivityTab(driver);
		Thread.sleep(7000);
					
		Log4jUtil.log("portalTime transmit "+portalTime.get(0));
		Log4jUtil.log("portalTime view "+portalTime.get(1));
		Log4jUtil.log("portalTime download "+portalTime.get(2));
		
		Log4jUtil.log("mu2GetEventGaurdian Step 11: Validate 'View' Event in Account Activty Page list ");
		List<Object> viewList = IHGUtil.searchResultsSubstring(driver, "//*[@id='frame']/table/tbody",
				new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_VIEWED, portalTime.get(1))));
		if (!viewList.isEmpty()) {
			BaseTestSoftAssert.assertTrue(((Boolean) viewList.get(1)).booleanValue());

		} else {
			BaseTestSoftAssert.assertTrue(false, "Health Information Viewed event not present");
		}
		Log4jUtil.log("mu2GetEventGaurdian Step 12: Validate 'Download' Event in Account Activty list");
		List<Object> downloadList = IHGUtil.searchResultsSubstring(driver, "//*[@id='frame']/table/tbody",
				new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_DOWNLOADED, portalTime.get(2))));
		if (!downloadList.isEmpty()) {
			BaseTestSoftAssert.assertTrue(((Boolean) downloadList.get(1)).booleanValue());
		} else {
			BaseTestSoftAssert.assertTrue(false, "Health Information Downloaded event not present");
		}

		Log4jUtil.log("mu2GetEventGaurdian Step 13: Validate 'Secure Email Transmit' Event in Account Activty Page list ");
		List<Object> transmitListSecure = IHGUtil.searchResultsSubstring(driver, "//*[@id='frame']/table/tbody",
				new ArrayList<String>(Arrays.asList(testData.secureEmailTransmitActivity+" "+testData.TRANSMIT_EMAIL+".", portalTime.get(0))));
		if (!transmitListSecure.isEmpty()) {
			BaseTestSoftAssert.assertTrue(((Boolean) transmitListSecure.get(1)).booleanValue());
		} else {
			BaseTestSoftAssert.assertTrue(false, "Health Information Transmitted event not present");
		}
		
		Log4jUtil.log("mu2GetEventGaurdian Step 14: Validate 'Standard Email Transmit' Event in Account Activty Page list ");
		List<Object> transmitListStandard = IHGUtil.searchResultsSubstring(driver, "//*[@id='frame']/table/tbody",
				new ArrayList<String>(Arrays.asList(testData.standardEmailTransmitActivity+" "+testData.Standard_Email+".", portalTime.get(0))));
		if (!transmitListStandard.isEmpty()) {
			BaseTestSoftAssert.assertTrue(((Boolean) transmitListStandard.get(1)).booleanValue());
		} else {
			BaseTestSoftAssert.assertTrue(false, "Health Information Transmitted event not present");
		}

		
		Log4jUtil.log("mu2GetEventGaurdian Step 15: Logging out");
		loginPage = homePage.clickOnLogout();
		
	}
	
	public void performRobotTask(WebDriver driver,Boolean downloadTypeXML) throws AWTException, InterruptedException {
		if( driver instanceof FirefoxDriver) {
			Robot rb = new Robot();
			if(downloadTypeXML) {
				Thread.sleep(2000);
				rb.keyPress(KeyEvent.VK_DOWN);
				rb.keyRelease(KeyEvent.VK_DOWN);
			}
			Thread.sleep(2000);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			
		}
	}
}