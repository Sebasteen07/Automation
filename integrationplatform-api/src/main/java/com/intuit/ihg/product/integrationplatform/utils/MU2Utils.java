package com.intuit.ihg.product.integrationplatform.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.mu2.utils.MU2Constants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.JalapenoCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class MU2Utils {
	public static List<String> eventList = new ArrayList<String>();
	public ArrayList<String> ccdMessageList = new ArrayList<String>(10);
	public static final String PracticePatientId = "PracticePatientId";
	public static final String FirstName = "FirstName";
	public static final String LastName = "LastName";
	public static final String PracticeResourceId = "PracticeResourceId";
	
	public void mu2GetEvent(MU2GetEventData testData,WebDriver driver)  throws Exception {

		long timestamp = System.currentTimeMillis();
		Log4jUtil.log("TIME STAMP for MU2 Pull API SinceTime: " + Long.toString(timestamp));
		
		String eventTime = null;
		String ActionTimestamp = null;
		List<String> portalTime = new ArrayList<String>();
		Log4jUtil.log("URL:"+testData.PORTAL_URL);
		Log4jUtil.log("PORTAL_USERNAME:"+testData.PORTAL_USERNAME);
		Log4jUtil.log("PORTAL_PASSWORD:"+testData.PORTAL_PASSWORD);
		
		ccdMessageList.add(testData.CCDMessageID1);
		ccdMessageList.add(testData.CCDMessageID2);
		ccdMessageList.add(testData.CCDMessageID1);
		ccdMessageList.add(testData.CCDMessageID2);
		ccdMessageList.add(testData.CCDMessageID1);
		ccdMessageList.add(testData.CCDMessageID2);
		ccdMessageList.add(testData.CCDMessageID1);
		ccdMessageList.add(testData.CCDMessageID2);
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
		
		Log4jUtil.log("MU2GetEvent Step 3: Select first and second CCD from the list");
		MedicalRecordSummariesPageObject.selectFirstVisibleCCD();
		MedicalRecordSummariesPageObject.selectSecondVisibleCCD();
		Log4jUtil.log("MU2GetEvent Step 4: Transmit Email Direct Protocol and Standard Email");
		MedicalRecordSummariesPageObject.sendFirstVisibleCCDUsingDirectProtocol(testData.TRANSMIT_EMAIL);
		Thread.sleep(5000);
		long transmitTimestamp = System.currentTimeMillis();
		Log4jUtil.log("TransmitTimestamp :"+transmitTimestamp);
		MedicalRecordSummariesPageObject.sendFirstVisibleCCDUsingStandardEmail(testData.Standard_Email);
		
		Log4jUtil.log("MU2GetEvent Step 5: view  First CCD");
		MedicalRecordSummariesPageObject.selectFirstVisibleCCDDate();
		
		Thread.sleep(5000);
		
		JalapenoCcdViewerPage jalapenoCcdPage = new JalapenoCcdViewerPage(driver);
		Log4jUtil.log("MU2GetEvent Step 6: Click on link View health data");
		//jalapenoCcdPage.clickBtnViewHealthData();
		Log4jUtil.log("MU2GetEvent Step 7: Verify if CCD Viewer is loaded and click Close Viewer");
		jalapenoCcdPage.verifyCCDViewerAndClose();
		Log4jUtil.log("Page refreshing...");
		
		//code to Download CCD on firefox 
		/*
		Log4jUtil.log("Step 8: Download CCD");
		Thread.sleep(4000);
		MedicalRecordSummariesPageObject.selectFirstVisibleCCD();
		MedicalRecordSummariesPageObject.selectDownload();
		Thread.sleep(2000);	
		IHGUtil.PrintMethodName();
		
		if( driver instanceof FirefoxDriver) {
			Robot rb = new Robot();
			Thread.sleep(2000);
			rb.keyPress(KeyEvent.VK_DOWN);
			rb.keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(2000);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
		}
		*/
		Log4jUtil.log("====== Consolidated CCD - VDT events generated successfully ======");
		Thread.sleep(5000);
		
		homePage.clickOnLogout();
		
		Log4jUtil.log("MU2GetEvent Step 8: Waiting for Events sync in DWH");
		Thread.sleep(430000);
		
		Log4jUtil.log("MU2GetEvent Step 9: Setup Oauth client 2.O");
		RestUtils.oauthSetup(testData.OAUTH_KEYSTORE, testData.OAUTH_PROPERTY, testData.OAUTH_APPTOKEN, testData.OAUTH_USERNAME,testData.OAUTH_PASSWORD);
		
		// Build new Rest URL with epoch milliseconds
		Log4jUtil.log("Original PULL API URl: " + testData.PULLAPI_URL);

		String restPullUrl = new StringBuilder(testData.PULLAPI_URL).append("&sinceTime=").append(timestamp).append("&maxEvents=40").toString();
		Log4jUtil.log("Updated PULL API URL: " + restPullUrl);

		Log4jUtil.log("Step 10: Send Pull API HTTP GET Request and save response to " +testData.PUSH_RESPONSEPATH);
		
		RestUtils.setupHttpGetRequest(restPullUrl, testData.PUSH_RESPONSEPATH);
		
		Thread.sleep(5000);
		
		String intuitPatientID1 = testData.INTUIT_PATIENT_ID;
		Log4jUtil.log("First practicePatientID: " + intuitPatientID1);
		
		List<String> list = eventList();
		for (int i = 0; i < list.size(); i++) {
			// verify "View" event in response XML and return Action Time stamp
			Log4jUtil.log("Verification of CCD '" + list.get(i) + "' event present in Pull API response xml");
			ActionTimestamp = findEventInResonseXML(testData.PUSH_RESPONSEPATH, MU2Constants.EVENT, MU2Constants.RESOURCE_TYPE, list.get(i),
					timestamp, intuitPatientID1,testData.PatientExternalId_MU2,testData.PatientFirstName_MU2,testData.PatientLastName_MU2,transmitTimestamp);
			Assert.assertNotNull(ActionTimestamp, "'" + list.get(i) + "' Event is not found in Response XML");
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
					
					if(transmitTimestamp > recordedTimeStamp && action.contains("Transmit")) {
						Log4jUtil.log("Standard Email");
					}
					if(transmitTimestamp < recordedTimeStamp && action.contains("Transmit")) {
						Log4jUtil.log("Direct Protocol");
					}
					if (recordedTimeStamp >= timeStamp) {
						 
						if (getValue(MU2Constants.RESOURCE_TYPE_NODE, element).equalsIgnoreCase(resourceType)
								&& getValue(MU2Constants.ACTION_NODE, element).equalsIgnoreCase(action)
								&& getValue(MU2Constants.INTUIT_PATIENT_ID, element).equalsIgnoreCase(practicePatientID)
								&& getValue(PracticePatientId, element).equalsIgnoreCase(patientExternalId)
								&& getValue(FirstName, element).equalsIgnoreCase(firstName)
								&& getValue(LastName, element).equalsIgnoreCase(lastName)
								&& getValue(PracticeResourceId, element).equalsIgnoreCase(ccdMessageList.get(i))) {
								
								
								Log4jUtil.log("Matching response medfusionId "+getValue(MU2Constants.INTUIT_PATIENT_ID, element)+" with "+practicePatientID);
								Log4jUtil.log("Matching response patientExternalId "+getValue(PracticePatientId, element)+" with "+patientExternalId);
								Log4jUtil.log("Matching response firstName "+getValue(FirstName, element)+" with "+firstName);
								Log4jUtil.log("Matching response lastName "+getValue(LastName, element)+" with "+lastName);
								Log4jUtil.log("Matching response practiceResourceId (CCDMessageId) "+getValue(PracticeResourceId, element)+" with "+ccdMessageList.get(i));
							
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
		DateFormat gmtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm ");
		TimeZone estTime = TimeZone.getTimeZone("America/New_York");
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
	
	public String getMedfusionID(String filePath) throws IOException {
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
}