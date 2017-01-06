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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void csvFileReader(PIDCInfo testData,String csvFilePath) throws IOException {
		
		String[][] arrayValues = new String[300][300];
		BufferedReader bufRdr = new BufferedReader(new FileReader(csvFilePath));
		String line = null;
		int row = 0;
		int col = 0;
		int max = 0;
		int nextLine = 0;
		int[] maxValue = new int[300];

		ArrayList race = new ArrayList();
		ArrayList ethenicity = new ArrayList();
		ArrayList gender = new ArrayList();
		ArrayList preferredLanguage = new ArrayList();
		ArrayList preferredCommunication = new ArrayList();
		ArrayList genderIdentity = new ArrayList();
		ArrayList sexualOrientation = new ArrayList();
		// read each line of text file
		while ((line = bufRdr.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ",");
			//System.out.println("row"+row);
			max = 0;
			while (st.hasMoreTokens()) {
				
				arrayValues[row][col] = st.nextToken();
				//System.out.println(nextLine+" values := "+arrayValues[row][col]);
				if(row==0 && nextLine !=0) {
					race.add(arrayValues[row][col]);
				}
				if(row==1 && nextLine !=0) {
					ethenicity.add(arrayValues[row][col]);
				}
				if(row==2 && nextLine !=0) {
					gender.add(arrayValues[row][col]);
				}
				if(row==3 && nextLine !=0) {
					preferredLanguage.add(arrayValues[row][col]);
				}
				if(row==4 && nextLine !=0) {
					preferredCommunication.add(arrayValues[row][col]);
				}
				if(row==5 && nextLine !=0) {
					genderIdentity.add(arrayValues[row][col]);
				}
				if(row==6 && nextLine !=0) {
					sexualOrientation.add(arrayValues[row][col]);
				}
				
				nextLine++;
				col++;
				max++;
			}
			nextLine=0;
			row++;
			maxValue[row] = max;
		}
		// close the file
		bufRdr.close();
		
		Arrays.sort(maxValue);
		int maxa = (maxValue[maxValue.length - 1]-1);
		
		testData.setRace(race);
		testData.setEthnicity(ethenicity);
		testData.setGender(gender);
		testData.setPreferredLanguage(preferredLanguage);
		testData.setPreferredCommunication(preferredCommunication);
		updateCommasWithInValues(genderIdentity);
		testData.setGenderIdentity(genderIdentity);
		updateCommasWithInValues(sexualOrientation);
		testData.setSexualOrientation(sexualOrientation);
		
		if(testData.getBatchSize()==null) {
			testData.setBatchSize(Integer.toString(maxa));
		}
		if(testData.getBatchSize().length() == 0) {
			testData.setBatchSize(Integer.toString(maxa));
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
	
	public static void pidcPatientRegistration(String ChannelVersion,WebDriver driver) throws Exception {
		LoadPreTestData LoadPreTestDataObj = new LoadPreTestData();
		PIDCInfo testData = new PIDCInfo();
		Long timestamp = System.currentTimeMillis();
		LoadPreTestDataObj.loadDataFromProperty(testData, ChannelVersion);
		
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.getCsvFilePath();
				
		Log4jUtil.log("Loading CSVfile : "+workingDir);
		csvFileReader(testData,workingDir);
		
		Thread.sleep(600);
		if (ChannelVersion.contains("v1")) {
			replaceUnknownForv1(ChannelVersion, testData.getGender());
		}
		Log4jUtil.log("Payload Batchsize :"+testData.getBatchSize());
		String patient = sendPatientInvitePayload.getPIDCPayload(testData);
		
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
			Log4jUtil.log(sendPatientInvitePayload.emailGroup.get(i) + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			String activationUrl = mail.getLinkFromEmail(sendPatientInvitePayload.emailGroup.get(i), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
			Log4jUtil.log("Step 4: Moving to the link obtained from the email message");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			
			if(testData.getPortalVersion().contains("1.0")) {
				registerPatient10(activationUrl, driver,sendPatientInvitePayload.zipGroup.get(i),sendPatientInvitePayload.emailGroup.get(i),testData.getPatientPassword(),testData.getSecretQuestion(),testData.getSecretAnswer());
			}
			if(testData.getPortalVersion().contains("2.0")) {
				registerPatient(activationUrl, sendPatientInvitePayload.emailGroup.get(i), testData.getPatientPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getHomePhoneNo(), driver, sendPatientInvitePayload.zipGroup.get(i),testData.getBirthDay());
			}
			Thread.sleep(10000);
		}
		
		
		Log4jUtil.log("Step 8: Do a GET on PIDC Url to get registered patient");
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(testData.getRestUrl() + "?since=" + since + ",0", testData.getResponsePath());
		Thread.sleep(2000);

		Log4jUtil.log("Step 9: Find the patient and check if he is registered");

		RestUtils.isPatientRegistered(testData.getResponsePath(), sendPatientInvitePayload.firstNameGroup, sendPatientInvitePayload.firstNameGroup, sendPatientInvitePayload.lastNameGroup, null ,testData.getGender(),testData.getRace(),testData.getEthnicity(),testData.getPreferredLanguage(),testData.getPreferredCommunication());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList replaceUnknownForv1(String ChannelVersion, ArrayList genderGroup) {
		if (ChannelVersion.contains("v1")) {
			for (int i = 0; i < genderGroup.size(); i++) {
				if (genderGroup.get(i).equals("UNKNOWN")) {
					genderGroup.set(i, "MALE");
				}
			}
		}
		return genderGroup;
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