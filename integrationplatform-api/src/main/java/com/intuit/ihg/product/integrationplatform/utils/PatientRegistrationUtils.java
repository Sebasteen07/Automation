// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;
import com.intuit.ihg.product.integrationplatform.pojo.PatientDetail;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.AuthUserLinkAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;

public class PatientRegistrationUtils {
	public static void registerPatient(String activationUrl, String EmailID, String PatientPassword, String SecretQuestion, String SecretAnswer,
			String RegisterTelephone, WebDriver driver, String zip, String birthdate) throws InterruptedException {

		String[] Date = birthdate.split("/");
		Log4jUtil.log("Retrieved activation link is " + activationUrl);
		Log4jUtil.log("Step 5: Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientActivationPage = new PatientVerificationPage(driver, activationUrl);
		SecurityDetailsPage accountDetailsPage = patientActivationPage.fillPatientInfoAndContinue(zip, Date[0], Date[1], Date[2]);
		Log4jUtil.log("Step 6: Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage =
				accountDetailsPage.fillAccountDetailsAndContinue(EmailID, PatientPassword, SecretQuestion, SecretAnswer, RegisterTelephone);

		Log4jUtil.log("Step 7: Detecting if Home Page is opened");
		Thread.sleep(2000);
		assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));

		Log4jUtil.log("Logging out");
		Thread.sleep(9000);
		jalapenoHomePage.clickOnLogout();
	}

	public static void underAgeRegisterPatient(String activationUrl, String EmailID, String PatientPassword, String SecretQuestion, String SecretAnswer,
			String RegisterTelephone, WebDriver driver, String zip, String birthdate) throws InterruptedException {
		String[] Date = birthdate.split("/");
		Log4jUtil.log("Retrieved activation link is " + activationUrl);
		Log4jUtil.log("Step 5: Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientActivationPage = new PatientVerificationPage(driver, activationUrl);
		AuthUserLinkAccountPage accountDetailsPage = patientActivationPage.fillDependentInfoAndContinue(zip, Date[0], Date[1], Date[2]);
		Log4jUtil.log("Step 6: Finishing of patient activation: step 2 - filling patient data");
		SecurityDetailsPage accountDetailsPage1 = accountDetailsPage.continueToCreateGuardianOnly("Guardian", "TestPatient01", "Parent");

		JalapenoHomePage jalapenoHomePage =
				accountDetailsPage1.fillAccountDetailsAndContinue(EmailID, PatientPassword, SecretQuestion, SecretAnswer, RegisterTelephone);
		Log4jUtil.log("Step 7: Detecting if Home Page is opened");
		Thread.sleep(7000);
		assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));

		Log4jUtil.log("Logging out");
		Thread.sleep(9000);
		jalapenoHomePage.clickOnLogout();
	}

	public static void csvFileReader(PIDCInfo testData, String csvFilePath) throws IOException {

		String[][] patientValues = new String[800][800];
		int[] maxValue = new int[800];
		BufferedReader bufRdr = new BufferedReader(new FileReader(csvFilePath));
		String line1 = null;
		int row = 0;
		int col = 0;
		int nextLine = 0;
		while ((line1 = bufRdr.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line1, ",");

			nextLine = 0;
			while (st.hasMoreTokens()) {
				patientValues[row][col] = st.nextToken();
				col++;
				nextLine++;
			}

			row++;
			maxValue[row] = nextLine;
		}
		bufRdr.close();
		Arrays.sort(maxValue);
		int maxLength = (maxValue[maxValue.length - 1] - 1);
		int counter = 0;
		String[][] setValues = new String[500][500];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j <= maxLength; j++) {
				setValues[i][j] = patientValues[i][counter];
				counter++;
			}
			testData.patientDetailList.add(new PatientDetail(setValues[i][0], setValues[i][1], setValues[i][2], setValues[i][3], setValues[i][4], setValues[i][5],
					setValues[i][6], setValues[i][7]));
		}

		updateCommasWithInValues1(testData.patientDetailList);

		int batchSize = (row - 1);
		if (testData.getBatchSize() == null) {
			testData.setBatchSize(Integer.toString(batchSize));
		}
		if (testData.getBatchSize().length() == 0) {
			testData.setBatchSize(Integer.toString(batchSize));
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void updateCommasWithInValues1(ArrayList csvValues) {
		String oldString = "";
		for (int i = 0; i < csvValues.size(); i++) {
			if (((PatientDetail) csvValues.get(i)).getGenderIdentity().toString().contains("~!")) {
				oldString = ((PatientDetail) csvValues.get(i)).getGenderIdentity().toString().replace("~!", ",");
				((PatientDetail) csvValues.set(i, csvValues.get(i))).setGenderIdentity(oldString);
			}
			if (((PatientDetail) csvValues.get(i)).getSexualOrientation().toString().contains("~!")) {
				oldString = ((String) csvValues.get(i)).replace("~!", ",");
				oldString = ((PatientDetail) csvValues.get(i)).getSexualOrientation().toString().replace("~!", ",");
				((PatientDetail) csvValues.set(i, csvValues.get(i))).setSexualOrientation(oldString);
			}
		}
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void updateCommasWithInValues(ArrayList csvValues) {
		String oldString = "";
		for (int i = 0; i < csvValues.size(); i++) {
			if (csvValues.get(i).toString().contains("~!")) {
				oldString = ((String) csvValues.get(i)).replace("~!", ",");
				csvValues.set(i, oldString);
			}
		}
	}



	public static void pidcPatientRegistration(String ChannelVersion, WebDriver driver, String portalVersion,String token) throws Exception {
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		PIDCInfo testData = new PIDCInfo();
		Long timestamp = System.currentTimeMillis();
		LoadPreTestDataObj.loadDataFromProperty(testData, ChannelVersion, portalVersion);

		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.getCsvFilePath();
		Log4jUtil.log("Loading CSVfile : " + workingDir);
		csvFileReader(testData, workingDir);

		Thread.sleep(600);
		if (ChannelVersion.contains("v1") || ChannelVersion.contains("v2")) {
			replaceUnknownForv1(ChannelVersion, testData);
		}
		Log4jUtil.log("Payload Batchsize :" + testData.getBatchSize());

		if (ChannelVersion.contains("v3")) {
			sendPatientInvitePayloadV3 payloadObj = new sendPatientInvitePayloadV3();
			String patient = payloadObj.getPIDCPayload(testData, portalVersion);

			Log4jUtil.log(patient);

			Thread.sleep(600);

			Log4jUtil.log("Step 2: Setup Oauth client" + testData.getResponsePath());
			RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(), testData.getoAuthUsername(),
					testData.getoAuthPassword());

			Log4jUtil.log("Step 3: Do a POST call and get processing status URL");
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl_20(), patient, testData.getResponsePath());

			Boolean completed = checkMessageProcessingOntime(processingUrl, testData.getResponsePath());
			assertTrue(completed, "Message processing was not completed in time");

			YopMailUtils mail = new YopMailUtils(driver);

			for (int i = 0; i < Integer.parseInt(testData.getBatchSize()); i++) {
				Thread.sleep(15000);
				Log4jUtil.log("Patient No: " + (i + 1));
				Log4jUtil.log(payloadObj.emailGroup.get(i) + "   :    " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE + "     :   "
						+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
				String activationUrl = mail.getLinkFromEmail(payloadObj.emailGroup.get(i), JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE,
						JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 40);
				Log4jUtil.log("Step 4: Moving to the link obtained from the email message");
				assertNotNull(activationUrl, "Error: Activation link not found.");


				if (portalVersion.contains("2.0")) {
					registerPatient(activationUrl, payloadObj.emailGroup.get(i), testData.getPatientPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),
							testData.getHomePhoneNo(), driver, payloadObj.zipGroup.get(i), testData.getBirthDay());
				}
				Thread.sleep(10000);
			}


			Log4jUtil.log("Step 8: Do a GET on PIDC Url to get registered patient");
			Long since = timestamp / 1000L - 60 * 24;
			Log4jUtil.log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestUrl_20() + "?since=" + since + ",0", testData.getResponsePath());
			Thread.sleep(2000);

			Log4jUtil.log("Step 9: Find the patient and check if he is registered");

			RestUtils.isPatientRegistered(testData.getResponsePath(), payloadObj.firstNameGroup, payloadObj.firstNameGroup, payloadObj.lastNameGroup, null, testData);
		} else {
			sendPatientInvitePayload payloadObj = new sendPatientInvitePayload();
			String patient = payloadObj.getPIDCPayload(testData, portalVersion);

			Log4jUtil.log(patient);

			Thread.sleep(600);

			Log4jUtil.log("Step 2: Setup Oauth client" + testData.getResponsePath());
			RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(), testData.getoAuthUsername(),
					testData.getoAuthPassword());

			Log4jUtil.log("Step 3: Do a POST call and get processing status URL");
			String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl_20(), patient, testData.getResponsePath());

			Boolean completed = checkMessageProcessingOntime(processingUrl, testData.getResponsePath());
			assertTrue(completed, "Message processing was not completed in time");

			YopMailUtils mail = new YopMailUtils(driver);

			for (int i = 0; i < Integer.parseInt(testData.getBatchSize()); i++) {
				Thread.sleep(15000);
				Log4jUtil.log("Patient No: " + (i + 1));
				Log4jUtil.log(payloadObj.emailGroup.get(i) + "   :    " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE + "     :   "
						+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
				String activationUrl = mail.getLinkFromEmail(payloadObj.emailGroup.get(i), JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE,
						JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 40);
				Log4jUtil.log("Step 4: Moving to the link obtained from the email message");
				assertNotNull(activationUrl, "Error: Activation link not found.");


				if (portalVersion.contains("2.0")) {
					registerPatient(activationUrl, payloadObj.emailGroup.get(i), testData.getPatientPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),
							testData.getHomePhoneNo(), driver, payloadObj.zipGroup.get(i), testData.getBirthDay());
				}
				Thread.sleep(10000);
			}

			Log4jUtil.log("Step 8: Do a GET on PIDC Url to get registered patient");
			Long since = timestamp / 1000L - 60 * 24;
			Log4jUtil.log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestUrl_20() + "?since=" + since + ",0", testData.getResponsePath());
			Thread.sleep(2000);

			Log4jUtil.log("Step 9: Find the patient and check if he is registered");

			RestUtils.isPatientRegistered(testData.getResponsePath(), payloadObj.firstNameGroup, payloadObj.firstNameGroup, payloadObj.lastNameGroup, null, testData);
		}
	}


	public static void replaceUnknownForv1(String ChannelVersion, PIDCInfo testData) {
		if (ChannelVersion.contains("v1") || ChannelVersion.contains("v2")) {
			for (int i = 0; i < testData.patientDetailList.size(); i++) {
				if (testData.patientDetailList.get(i).getGender().equals("UNKNOWN") || testData.patientDetailList.get(i).getGender().equals("UNDIFFERENTIATED")) {
					testData.patientDetailList.get(i).setGender("MALE");
				}
			}
		}
	}

	public static Boolean checkMessageProcessingOntime(String processingUrl, String ResponsePath)
			throws InterruptedException, ParserConfigurationException, SAXException, IOException, URISyntaxException {
		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 60 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequest(processingUrl, ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(ResponsePath)) {
				completed = true;
				return completed;
			}
		}
		return completed;

	}
	
	public static void pidcPatientRegistrationJSONPayload(String ChannelVersion, WebDriver driver, String portalVersion) throws Exception {
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		PIDCInfo testData = new PIDCInfo();
		
		Long timestamp = System.currentTimeMillis();
		LoadPreTestDataObj.loadDataFromProperty(testData, ChannelVersion, portalVersion);
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.getCsvFilePath();
		Log4jUtil.log("Loading CSVfile : " + workingDir);
		csvFileReader(testData, workingDir);
		Log4jUtil.log("Payload Batchsize : "+testData.getBatchSize());
		sendPatientInvitePayloadV3 payloadObj = new sendPatientInvitePayloadV3();
		String patient = payloadObj.getPIDCJSONPayload(testData, portalVersion);

		Log4jUtil.log(patient);

		Log4jUtil.log("Step 2: Setup Oauth client" + testData.getResponsePath());
		RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(), testData.getoAuthUsername(),
					testData.getoAuthPassword());
		Log4jUtil.log(testData.getToken());

		Log4jUtil.log("Step 3: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpJSONPostRequest(testData.getRestUrl_20(), patient, testData.getResponsePath(), testData.getToken());

		Boolean completed = checkMessageProcessingOntime(processingUrl, testData.getResponsePath());
		assertTrue(completed, "Message processing was not completed in time");

		YopMailUtils mail = new YopMailUtils(driver);

		for (int i = 0; i < Integer.parseInt(testData.getBatchSize()); i++) {
			Thread.sleep(15000);
			Log4jUtil.log("Patient No: " + (i + 1));
			Log4jUtil.log(payloadObj.emailGroup.get(i) + "   :    " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE + "     :   "
					+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
			String activationUrl = mail.getLinkFromEmail(payloadObj.emailGroup.get(i), JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE,
						JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 40);
			Log4jUtil.log("Step 4: Moving to the link obtained from the email message");
			assertNotNull(activationUrl, "Error: Activation link not found.");

			if (portalVersion.contains("2.0")) {
				registerPatient(activationUrl, payloadObj.emailGroup.get(i), testData.getPatientPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(),
							testData.getHomePhoneNo(), driver, payloadObj.zipGroup.get(i), testData.getBirthDay());
				}
			Thread.sleep(10000);
			}

			Log4jUtil.log("Step 8: Do a GET on PIDC Url to get registered patient");
			Long since = timestamp / 1000L - 60 * 24;
			Log4jUtil.log("Getting patients since timestamp: " + since);
			RestUtils.setupHttpGetRequest(testData.getRestUrl_20() + "?since=" + since + ",0", testData.getResponsePath());
			Thread.sleep(2000);

			Log4jUtil.log("Step 9: Find the patient and check if he is registered");

			RestUtils.isPatientRegistered(testData.getResponsePath(), payloadObj.firstNameGroup, payloadObj.firstNameGroup, payloadObj.lastNameGroup, null, testData);
	}

	public static void PrecheckPatientSubscriberPayloadV4(String ChannelVersion, WebDriver driver, String portalVersion) throws Exception {
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		PIDCInfo testData = new PIDCInfo();
		
		Long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;

		LoadPreTestDataObj.loadDataFromProperty(testData, ChannelVersion, portalVersion);
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.getCsvFilePath();
		Log4jUtil.log("Loading CSVfile : " + workingDir);
		csvFileReader(testData, workingDir);
		Log4jUtil.log("Payload Batchsize : 1");
		Log4jUtil.log("SubscriberEndpoint : "+testData.getPrecheckSubscriberPatientRestURL());
		Log4jUtil.log("Patient V4 Endpoint : "+testData.getRestUrl_20());
		sendPrecheckPatientSubscriberPayloadV4 payloadObj = new sendPrecheckPatientSubscriberPayloadV4();
		String patient = payloadObj.getPIDCPayload(testData, portalVersion);

		Log4jUtil.log(patient);

		Log4jUtil.log("Step 2: Setup Oauth client" + testData.getResponsePath());
		RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(), testData.getoAuthUsername(),
					testData.getoAuthPassword());
		Log4jUtil.log(testData.getToken());

		Log4jUtil.log("Step 3: Do a POST call and get processing status URL");
		RestUtils.setupHttpPostRequest(testData.getPrecheckSubscriberPatientRestURL(), patient, testData.getResponsePath());

		Log4jUtil.log("Step 4: Do a GET on PIDC Url to get registered patient");
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl_20() + "?since=" + since + ",0", testData.getResponsePath());
		Thread.sleep(2000);
		RestUtils.isPatientPresent(testData.getResponsePath(), testData.getTestPatientIDUserName());
		
		}


}
