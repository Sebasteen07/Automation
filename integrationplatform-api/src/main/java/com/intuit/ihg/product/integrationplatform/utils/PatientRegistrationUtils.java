package com.intuit.ihg.product.integrationplatform.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;
import com.intuit.ihg.product.integrationplatform.pojo.PatientDetail;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.createAccount.CreateAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.utils.PortalConstants;
import com.medfusion.common.utils.Mailinator;

public class PatientRegistrationUtils {
	public static void registerPatient(String activationUrl,String EmailID,String PatientPassword,String SecretQuestion,String SecretAnswer,String RegisterTelephone,WebDriver driver,String zip,String birthdate) throws InterruptedException {
		
		String[] Date = birthdate.split("/");
		Log4jUtil.log("Retrieved activation link is " + activationUrl);
		Log4jUtil.log("Step 5: Finishing of patient activation: step 1 - verifying identity");
		PatientVerificationPage patientActivationPage = new PatientVerificationPage(driver, activationUrl);
		SecurityDetailsPage accountDetailsPage = patientActivationPage.fillPatientInfoAndContinue(zip, Date[0], Date[1], Date[2]);
		Log4jUtil.log("Step 6: Finishing of patient activation: step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage =
				accountDetailsPage.fillAccountDetailsAndContinue(EmailID, PatientPassword, SecretQuestion, SecretAnswer,RegisterTelephone);
	
		Log4jUtil.log("Step 7: Detecting if Home Page is opened");
		Thread.sleep(2000);
		Assert.assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));
		
		Log4jUtil.log("Logging out");
		jalapenoHomePage.clickOnLogout();
	}
		
	public static void csvFileReader(PIDCInfo testData,String csvFilePath) throws IOException {
		
		String[][] patientValues = new String[500][500];
		int[] maxValue = new int[500];
		BufferedReader bufRdr = new BufferedReader(new FileReader(csvFilePath));
		String line1 = null;
		int row = 0;
		int col = 0;
		int nextLine = 0;
		while ((line1 = bufRdr.readLine()) != null ) {
			StringTokenizer st = new StringTokenizer(line1, ",");
			
			nextLine=0;
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
		int maxLength = (maxValue[maxValue.length - 1]-1);
		int counter = 0;
		String[][] setValues = new String[500][500];
		for(int i =0;i< row;i++) {
			for(int j=0;j<=maxLength;j++) {
				setValues[i][j] = patientValues[i][counter];
				//System.out.println("i: "+i+"  j: "+j+"  patientValues : "+patientValues[i][counter]);				
				counter++;
			}
			testData.patientDetailList.add(new PatientDetail(setValues[i][0], setValues[i][1], setValues[i][2], setValues[i][3], setValues[i][4], setValues[i][5], setValues[i][6]));
		}
		
		updateCommasWithInValues1(testData.patientDetailList);
		
		int batchSize =(row-1);
		if(testData.getBatchSize()==null) {
			testData.setBatchSize(Integer.toString(batchSize));
		}
		if(testData.getBatchSize().length() == 0) {
			testData.setBatchSize(Integer.toString(batchSize));
		}	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateCommasWithInValues1(ArrayList csvValues) {
		String oldString="";
		for(int i=0;i<csvValues.size();i++) {
			if(((PatientDetail) csvValues.get(i)).getGenderIdentity().toString().contains("~!")) {
				 oldString = ((PatientDetail) csvValues.get(i)).getGenderIdentity().toString().replace("~!", ",");
				 ((PatientDetail) csvValues.set(i, csvValues.get(i))).setGenderIdentity(oldString);
			}
			if(((PatientDetail) csvValues.get(i)).getSexualOrientation().toString().contains("~!")) {
				 oldString = ((String) csvValues.get(i)).replace("~!", ",");
				 oldString = ((PatientDetail) csvValues.get(i)).getSexualOrientation().toString().replace("~!", ",");
				 ((PatientDetail) csvValues.set(i, csvValues.get(i))).setSexualOrientation(oldString);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void updateCommasWithInValues(ArrayList csvValues) {
		String oldString="";
		for(int i=0;i<csvValues.size();i++) {
			if(csvValues.get(i).toString().contains("~!")) {
				 oldString = ((String) csvValues.get(i)).replace("~!", ",");
				 csvValues.set(i, oldString);
			}
		}
	}
	
	public static void registerPatient10(String activationUrl, WebDriver driver,String zip,String email,String password,String secretQuestion,String secretAnswer) throws InterruptedException {
		Log4jUtil.log("Portal 1.0 patient registration");
		CreateAccountPage pCreateAccountPage = new PortalLoginPage(driver).loadUnlockLink(activationUrl);

		Log4jUtil.log("Step 5: Filling in user credentials and finishing the registration");
		// Filing the User credentials
		MyPatientPage myPatientPage =
				pCreateAccountPage.fillPatientActivaion(zip, email, password, secretQuestion, secretAnswer);

		Log4jUtil.log("Step 6: Assert Webelements in MyPatientPage");
		Assert.assertTrue(myPatientPage.isViewallmessagesButtonPresent(driver));

		Log4jUtil.log("Step 7: Signing out of the Patient Portal");
		myPatientPage.clickLogout(driver);
	}
	
	public static void pidcPatientRegistration(String ChannelVersion,WebDriver driver,String portalVersion) throws Exception {
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		PIDCInfo testData = new PIDCInfo();
		Long timestamp = System.currentTimeMillis();
		LoadPreTestDataObj.loadDataFromProperty(testData, ChannelVersion,portalVersion);
		
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.getCsvFilePath();
		Log4jUtil.log("Loading CSVfile : "+workingDir);
		csvFileReader(testData,workingDir);
		
		Thread.sleep(600);
		if (ChannelVersion.contains("v1")) {
			replaceUnknownForv1(ChannelVersion, testData);
		}
		Log4jUtil.log("Payload Batchsize :"+testData.getBatchSize());
		sendPatientInvitePayload payloadObj = new sendPatientInvitePayload();
		String patient = payloadObj.getPIDCPayload(testData,portalVersion);
		
		Thread.sleep(600);
		
		Log4jUtil.log("Step 2: Setup Oauth client" + testData.getResponsePath());
		RestUtils.oauthSetup(testData.getoAuthKeyStore(), testData.getoAuthProperty(), testData.getoAuthAppToken(), testData.getoAuthUsername(), testData.getoAuthPassword());
		
		Log4jUtil.log("Step 3: Do a POST call and get processing status URL");
		String processingUrl = RestUtils.setupHttpPostRequest(testData.getRestUrl(), patient, testData.getResponsePath());
		
		Boolean completed = checkMessageProcessingOntime(processingUrl, testData.getResponsePath());
		Assert.assertTrue(completed, "Message processing was not completed in time");
		
		Mailinator mail = new Mailinator();
		
		for (int i=0;i<Integer.parseInt(testData.getBatchSize());i++) {
			Thread.sleep(15000);
			Log4jUtil.log("Patient No: "+(i+1));
			Log4jUtil.log(payloadObj.emailGroup.get(i) + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			String activationUrl = mail.getLinkFromEmail(payloadObj.emailGroup.get(i), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
			Log4jUtil.log("Step 4: Moving to the link obtained from the email message");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			//if(portal1 == true)
			if(portalVersion.contains("1.0")) {
				registerPatient10(activationUrl, driver,payloadObj.zipGroup.get(i),payloadObj.emailGroup.get(i),testData.getPatientPassword(),testData.getSecretQuestion(),testData.getSecretAnswer());
			}
			if(portalVersion.contains("2.0")) {
				registerPatient(activationUrl, payloadObj.emailGroup.get(i), testData.getPatientPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getHomePhoneNo(), driver, payloadObj.zipGroup.get(i),testData.getBirthDay());
			}
			Thread.sleep(10000);
		}
		
		
		Log4jUtil.log("Step 8: Do a GET on PIDC Url to get registered patient");
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		Thread.sleep(2000);

		Log4jUtil.log("Step 9: Find the patient and check if he is registered");

		RestUtils.isPatientRegistered(testData.getResponsePath(), payloadObj.firstNameGroup, payloadObj.firstNameGroup, payloadObj.lastNameGroup, null ,testData);
	}
	

	public static void replaceUnknownForv1(String ChannelVersion, PIDCInfo testData) {
		if (ChannelVersion.contains("v1")) {
			for (int i = 0; i < testData.patientDetailList.size(); i++) {
				if (testData.patientDetailList.get(i).getGender().equals("UNKNOWN")) {
					testData.patientDetailList.get(i).setGender("MALE");
				}
			}
		}
	}
	
	public static Boolean checkMessageProcessingOntime(String processingUrl, String ResponsePath) throws InterruptedException, ParserConfigurationException,SAXException,IOException, URISyntaxException {
		boolean completed = false;
		for (int i = 0; i < 1; i++) {
			// wait 60 seconds so the message can be processed
			Thread.sleep(60000);
			RestUtils.setupHttpGetRequestExceptOauth(processingUrl, ResponsePath);
			if (RestUtils.isMessageProcessingCompleted(ResponsePath)) {
				completed = true;
				return completed;
			}
		}
		return completed;
		
	}
}