package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.mu2.utils.MU2Constants;
import com.medfusion.common.utils.EncryptionUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsStatementPdfPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientDashboardPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;

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

	public void generateViewEvent(WebDriver driver, StatementEventData testData, char type, String version)
			throws Exception {

		StatementsMessagePayload SMPObj = new StatementsMessagePayload();

		if (version.equals("v1")) {
			String statement = SMPObj.getStatementsMessagePayload(testData);
			Log4jUtil.log("Statement Step 1: Setup Oauth client");
			RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
					testData.OAuthUsername, testData.OAuthPassword);
			Log4jUtil.log("Payload :" + statement);
			Log4jUtil.log("Statement Step 2: Do a POST call and get processing status URL");
			String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, statement, testData.ResponsePath);
			Log4jUtil.log("processing Status" + processingUrl);

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
			assertTrue(completed, "Message processing was not completed in time");
		} else {
			String statementV3 = SMPObj.getStatementsMessageV3Payload(testData);
			Log4jUtil.log("Statement Step 1: Setup Oauth client");
			RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
					testData.OAuthUsername, testData.OAuthPassword);
			Log4jUtil.log("Payload :" + statementV3);
			Log4jUtil.log("Statement Step 2: Do a POST call and get processing status URL");
			String processingUrl = RestUtils.setupHttpPostRequest(testData.RestV3Url, statementV3,
					testData.ResponsePath);
			Log4jUtil.log("processing Status" + processingUrl);

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
			assertTrue(completed, "Message processing was not completed in time");
		}

		Thread.sleep(2000);
		Log4jUtil.log("step 6: Login to Practice Portal");

		// Now start login with practice data
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.PracticeURL);
		PracticeHomePage pPracticeHomePage = practiceLogin.login(testData.practiceUserName, EncryptionUtils.decrypt(testData.practicePassword));

		Log4jUtil.log("step 7: Click on Patient Search Link");
		PatientSearchPage pPatientSearchPage = pPracticeHomePage.clickPatientSearchLink();

		Log4jUtil.log("step 8:Set Patient Search Fields");
		pPatientSearchPage.searchForPatientInPatientSearch(testData.FirstName, testData.LastName);

		Log4jUtil.log("Step 12: Click on Patient");
		PatientDashboardPage patientPage = pPatientSearchPage.clickOnPatient(testData.FirstName, testData.LastName);

		Thread.sleep(12000);
		Log4jUtil.log("Step 13: Get External Patient ID");
		String externalPatientID = patientPage.getExternalPatientID();

		Log4jUtil.log("On Demand PatientID " + externalPatientID);
		Log4jUtil.log("Expected patient ID " + SMPObj.patientID);

		assertEquals(SMPObj.patientID, externalPatientID, "Patient External ID Matched !");
		pPracticeHomePage.logOut();
		
		long timeStamp = System.currentTimeMillis();

		YopMailUtils mail = new YopMailUtils(driver);
		String link = mail.getLinkFromEmail(testData.Email, testData.emailSubject, testData.PracticeName, 20);
		assertTrue(link != null, "Statement Message link not found in mail.");
		Log4jUtil.log("statement link  " + link);

		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, link);
		JalapenoHomePage jalapenoHomePage = jalapenoLoginPage.login(testData.UserName, testData.Password);

		Log4jUtil.log("Click on messages solution");
		JalapenoMessagesPage jalapenoMessagesPage = jalapenoHomePage.showMessages(driver);
		Thread.sleep(8000);


		JalapenoPayBillsStatementPdfPage statementPdfPageObject = jalapenoMessagesPage.openPDFStatement();
		Thread.sleep(2000);
		if (driver instanceof FirefoxDriver) {
			List<WebElement> frameList = driver.findElements(By.tagName("iframe"));
			Log4jUtil.log("iFrame " + frameList.get(1).getAttribute("id"));

			driver.switchTo().frame(frameList.get(1).getAttribute("id"));
			Log4jUtil.log("Frame Title is " + driver.getTitle());

			Log4jUtil.log("account number " + statementPdfPageObject.getAccountNumber());
			Log4jUtil.log("practice Name " + statementPdfPageObject.getPracticeName());
			Log4jUtil.log("Amount Due " + statementPdfPageObject.getAmountDue());
			Log4jUtil.log("Due Date " + statementPdfPageObject.getDueDate());
			Log4jUtil.log("TotalCharges " + statementPdfPageObject.getTotalCharges());

			assertEquals(statementPdfPageObject.getAmountDue(), SMPObj.amountDue);
			assertEquals(statementPdfPageObject.getDueDate(), SMPObj.paymentPortalDueDate);
			assertEquals(statementPdfPageObject.getAccountNumber(), SMPObj.billAccountNumber);
			assertEquals(statementPdfPageObject.getPracticeName(), testData.PracticeName);
		}
		driver.switchTo().defaultContent();
		long transitTimeStamp1 = System.currentTimeMillis();
		Thread.sleep(8000);
		JalapenoPayBillsMakePaymentPage makebillpayments = jalapenoHomePage.clickOnMenuPayBills();

		Thread.sleep(3000);
		if (type == 'E') {
			makebillpayments.gotoStatementDetail();
		}
		Thread.sleep(3000);

		driver.switchTo().defaultContent();
		jalapenoHomePage.clickOnLogout();

		Log4jUtil.log("Waiting 10 minutes for Statement Events to Sync");
		Thread.sleep(600000);
		
		if (version.equals("v1")) {
			String getEvent = testData.StatementEventURL + timeStamp;
			Log4jUtil.log("Get Link" + getEvent);
			RestUtils.setupHttpGetRequest(getEvent, testData.ResponsePath);
		} else {
			String getEvent = testData.StatementEventV3URL + timeStamp;
			Log4jUtil.log("Get Link :" + getEvent);
			RestUtils.setupHttpGetRequest(getEvent, testData.ResponsePath);
		}
		Log4jUtil.log("transitTimeStamp :- " + transitTimeStamp1);
		Thread.sleep(5000);

		List<String> list = eventList();
		for (int i = 0; i < list.size(); i++) {
			// verify "View" event in response XML and return Action Time stamp
			Log4jUtil.log("Verification of EStatement '" + list.get(i) + "' event present in Pull API response xml");
			ActionTimestamp = findEventInResonseXML(testData.ResponsePath, MU2Constants.EVENT, RESOURCE_TYPE,
					list.get(i), timeStamp, testData.MFPatientID, testData.PatientID, testData.FirstName,
					testData.LastName, transitTimeStamp1, version);
			assertNotNull(ActionTimestamp, "'" + list.get(i) + "' Event is not found in Response XML");
			Log4jUtil.log("ActionTimestamp: " + ActionTimestamp);
			Log4jUtil.log("TYPE FOUND: " + list.get(i));
			eventTime = generateDate(ActionTimestamp);
			portalTime.add(eventTime);
			Log4jUtil.log("EStatement '" + list.get(i) + "' event portal Time: " + eventTime);
		}
	}

	public String findEventInResonseXML(String xmlFileName, String event, String resourceType, String action,
			Long timeStamp, String practicePatientID, String patientExternalId, String firstName, String lastName,
			long transmitTimestamp, String version) {
		IHGUtil.PrintMethodName();

		String ActionTimestamp = null;
		try {

			File stocks = new File(xmlFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
			doc.getDocumentElement().normalize();

			NodeList nodes = doc.getElementsByTagName(event);

			Log4jUtil.log("Node length   " + nodes.getLength());

			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String readValue;
					readValue = getValue(MU2Constants.EVENT_RECORDED_TIMESTAMP, element);
					Long recordedTimeStamp = Long.valueOf(readValue);

					Log4jUtil.log(recordedTimeStamp + "  >   " + timeStamp);

					if (recordedTimeStamp >= timeStamp) {

						if (getValue(MU2Constants.RESOURCE_TYPE_NODE, element).equalsIgnoreCase(resourceType)
								&& getValue(MU2Constants.ACTION_NODE, element).equalsIgnoreCase(action)
								|| getValue(MU2Constants.INTUIT_PATIENT_ID, element).equalsIgnoreCase(practicePatientID)
										&& getValue(PracticePatientId, element).equalsIgnoreCase(patientExternalId)
										&& getValue(FirstName, element).equalsIgnoreCase(firstName)
										&& getValue(LastName, element).equalsIgnoreCase(lastName)) {

							if (transmitTimestamp > recordedTimeStamp && action.contains("View")) {
								Log4jUtil.log("Event Generated From Email View");
							}
							if (transmitTimestamp < recordedTimeStamp && action.contains("View")) {
								Log4jUtil.log("Event Generated From Paybill View");
							}
							if (version.equals("v1")) {
								Log4jUtil.log("Matching response medfusionId "
										+ getValue(MU2Constants.INTUIT_PATIENT_ID, element) + " with "
										+ practicePatientID);
							}
							Log4jUtil.log("Matching response patientExternalId " + getValue(PracticePatientId, element)
									+ " with " + patientExternalId);
							Log4jUtil.log("Matching response firstName " + getValue(FirstName, element) + " with "
									+ firstName);
							Log4jUtil.log(
									"Matching response lastName " + getValue(LastName, element) + " with " + lastName);

							ActionTimestamp = getValue(MU2Constants.EVENT_RECORDED_TIMESTAMP, element);
							// break;
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

	public String getMedfusionID(String email, String SearchStringFrom) throws IOException {
		String medfusionID = "";
		String[] patientArray = SearchStringFrom.split("</Patient>");

		String batchSize = ExtractString("<BatchSize>(.*?)</BatchSize>", SearchStringFrom);
		Log4jUtil.log(" batchSize " + batchSize);
		int batchSizeInt = Integer.parseInt(batchSize);
		if (batchSizeInt == 1) {
			medfusionID = ExtractString("<MedfusionPatientId>(.*?)</MedfusionPatientId>", SearchStringFrom);
		} else {

			for (int i = 0; i < patientArray.length; i++) {

				if (patientArray[i].contains(email)) {
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

	public void postStatement(StatementEventData testData, String statement) throws Exception {
		Log4jUtil.log("Statement Step 1: Setup Oauth client");
		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.OAuthAppToken,
				testData.OAuthUsername, testData.OAuthPassword);

		Log4jUtil.log("Statement Step 2: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.RestUrl, statement, testData.ResponsePath);
		Log4jUtil.log("processing Status" + processingUrl);

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
		assertTrue(completed, "Message processing was not completed in time");
	}

	public void postStatementV3(StatementEventData testData, String statement) throws Exception {
		Log4jUtil.log("Statement Step 1: Setup Oauth client");

		RestUtils.oauthSetup(testData.OAuthKeyStore, testData.OAuthProperty, testData.oAuthAppToken_PatientSt,
				testData.oAuthAppUsername_PatientSt, testData.oAuthAppPw_PatientSt);

		Log4jUtil.log("Statement Step 2: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.restUrlV3_Statement, statement,
				testData.ResponsePath);
		Log4jUtil.log("processing Status" + processingUrl);

		Log4jUtil.log("Statement Step 3: Get processing status until it is completed");

		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");
	}
}
