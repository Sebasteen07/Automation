//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage.JalapenoAppointmentsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class AppointmentDataUtils {
	public String updatedDay = "";
	public String updatedTime = "";
	public String updatedLocation = "";
	public String updatedProvider = "";

	public void checkAppointment(AppointmentData testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		AppointmentDataPayload apObj = new AppointmentDataPayload();
		String appointmentDataPayload = apObj.getAppointmentDataPayload(testData);
		Log4jUtil.log("appointmentDataPayload " + appointmentDataPayload);

		Thread.sleep(10000);

		Log4jUtil.log("Get Processing URL status");
		Log4jUtil.log("RestURL : " + testData.AppointmentPath);
		Log4jUtil.log("ResponsePath : " + testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.AppointmentPath, appointmentDataPayload,
				testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(10000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if(testData.BatchSize.equalsIgnoreCase("1")) {
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
				}
			}
			else if(testData.BatchSize.equalsIgnoreCase("2")) {
				if (RestUtils.isResponseContainsErrorNode(testData.ResponsePath)) {
					completed = true;
					break;
			}
			}
		}
		assertTrue(completed, "Message processing was not completed in time");
		Log4jUtil.log("Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		Log4jUtil.log(" Fetch Dashboard Next Time slot");
		if (!testData.Status.equalsIgnoreCase("CANCEL")) {
			Log4jUtil.log("Next Time slot is from Dashboard: " + homePage.getNextScheduledApptDate());

		}
		Log4jUtil.log(" Goto Appointments Page");
		homePage.goToAppointmentsPage(testData.URL);
		JalapenoAppointmentsPage JAPage = new JalapenoAppointmentsPage(driver);
		Thread.sleep(8000);

		Log4jUtil.log("appointmentType " + testData.appointmentType);
		if (testData.appointmentType == "FUTURE") {
			futureAppointment(JAPage, apObj, testData);
			Thread.sleep(8000);
		}

		homePage.clickOnLogout();
	}

	public void pastAppointment(JalapenoAppointmentsPage JAPage, AppointmentDataPayload apObj, AppointmentData testData)
			throws InterruptedException {
		JAPage.goToPastAppointments();
		Log4jUtil.log("Match Past Appointment Details");
		Thread.sleep(7000);
		List<WebElement> appointMentListPast = JAPage.getAppointments();
		Log4jUtil.log("Past list size = " + appointMentListPast.size());
		for (int i = 0; i < appointMentListPast.size(); i++) {
			if (appointMentListPast.get(i).getText().contains(apObj.providerNamePlace[1])
					&& !testData.Status.equalsIgnoreCase("CANCEL")) {
				Log4jUtil.log("providerName = " + apObj.providerNamePlace[1]);
				Log4jUtil.log("Past appointment Data Matched ! ");
			}
		}
	}

	public void futureAppointment(JalapenoAppointmentsPage JAPage, AppointmentDataPayload apObj,
			AppointmentData testData) {
		JAPage.goToUpcomingAppointments();
		Log4jUtil.log("Match Future Appointment Details");
		List<WebElement> appointMentList = JAPage.getAppointments();
		Log4jUtil.log("Future list size = " + appointMentList.size());
		for (int i = 0; i < appointMentList.size(); i++) {
			if (appointMentList.get(i).getText().contains(apObj.providerNamePlace[0])
					&& !testData.Status.equalsIgnoreCase("CANCEL")) {
				Log4jUtil.log("posted appointment data Matched ! ");
				Log4jUtil.log("appointMentList.get(i).getText() " + appointMentList.get(i).getText());

				Log4jUtil.log(" ProviderNamePlace " + apObj.providerNamePlace[0]);
				Log4jUtil.log(" LocationIdentifier " + apObj.locationIdentifier[0]);
				Log4jUtil.log(" LocalDay " + apObj.localDay[0]);
				Log4jUtil.log(" Time " + apObj.localTime[0]);
				Boolean provider = appointMentList.get(i).getText().contains(apObj.providerNamePlace[0]);
				Boolean location = appointMentList.get(i).getText().contains(apObj.locationIdentifier[0]);
				Boolean Day = appointMentList.get(i).getText().contains(apObj.localDay[0]);
				Boolean Time = appointMentList.get(i).getText().contains(apObj.localTime[0]);

				assertTrue(location, "appointment Location is matched");
				assertTrue(provider, "appointment Provdier is matched");

				Log4jUtil.log(provider + " " + location + " " + Day + " " + Time);

				if (testData.Status.equalsIgnoreCase("UPDATE")) {
					updatedTime = apObj.localTime[0];
					updatedDay = apObj.localDay[0];
					updatedLocation = apObj.locationIdentifier[0];
					updatedProvider = apObj.providerNamePlace[0];
				}
				
			} else {
				if (testData.Status.equalsIgnoreCase("CANCEL")
						&& appointMentList.get(i).getText().contains(updatedProvider)) {
					Log4jUtil.log("In cancel ");
					Log4jUtil.log(" updatedTime  " + updatedTime);
					Log4jUtil.log(" updatedDay name  " + updatedDay);
					Log4jUtil.log(" updatedLocation  " + updatedLocation);
					Log4jUtil.log(" updatedProvider  " + updatedProvider);
					String headerColor = appointMentList.get(i).getCssValue("border-bottom-color");
					assertEquals("rgba(255, 0, 0, 1)", headerColor, "Color Matched !");
					Log4jUtil.log(" Canceled Color code  " + headerColor);
				} else {
					 //assertTrue(false, "appointmentData not found");
				}
			}
		}
	}

	public void csvFileReader(AppointmentData testData, String csvFilePath) throws IOException {

		String[][] appointmentDataValue = new String[10000][10000];
		int[] maxValue = new int[10000];
		BufferedReader bufRdr = new BufferedReader(new FileReader(csvFilePath));
		String line1 = null;
		int row = 0;
		int col = 0;
		int nextLine = 0;
		while ((line1 = bufRdr.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line1, ",");

			nextLine = 0;
			while (st.hasMoreTokens()) {
				appointmentDataValue[row][col] = st.nextToken();
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
		String[][] setValues = new String[10000][10000];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j <= maxLength; j++) {
				setValues[i][j] = appointmentDataValue[i][counter];
				counter++;
			}
			testData.appointmentDetailList.add(new AppointmentDetail(setValues[i][0], setValues[i][1], setValues[i][2],
					setValues[i][3], setValues[i][4], setValues[i][5], setValues[i][6], setValues[i][7],
					setValues[i][8], setValues[i][9], setValues[i][10], setValues[i][11]));
		}
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

	public String getMedfusionID(String email, String SearchStringFrom) throws IOException {
		String medfusionID = "";
		String[] patientArray = SearchStringFrom.split("</Patient>");
		// Log4jUtil.log(" patientArray length "+patientArray.length);
		String batchSize = ExtractString("<BatchSize>(.*?)</BatchSize>", SearchStringFrom);
		Log4jUtil.log(" batchSize " + batchSize);
		int batchSizeInt = Integer.parseInt(batchSize);
		if (batchSizeInt == 1) {
			medfusionID = ExtractString("<MedfusionPatientId>(.*?)</MedfusionPatientId>", SearchStringFrom);
		} else {
			// Log4jUtil.log(" ID "+email);

			for (int i = 0; i < patientArray.length; i++) {
				// Log4jUtil.log(" patientArray[i] "+patientArray[i]);
				if (patientArray[i].contains(email)) {
					medfusionID = ExtractString("<MedfusionPatientId>(.*?)</MedfusionPatientId>", patientArray[i]);
				}
			}
		}
		// Log4jUtil.log("medfusionID "+medfusionID);
		return medfusionID;
	}

	public String getPatientID(String medfusionID, String SearchStringFrom) throws IOException {
		String patientID = "";
		String[] patientArray = SearchStringFrom.split("</Patient>");
		Log4jUtil.log(" patientArray length " + patientArray.length);
		String batchSize = ExtractString("<BatchSize>(.*?)</BatchSize>", SearchStringFrom);
		Log4jUtil.log(" batchSize " + batchSize);
		int batchSizeInt = Integer.parseInt(batchSize);
		if (batchSizeInt == 1) {
			patientID = ExtractString("<PracticePatientId>(.*?)</PracticePatientId>", SearchStringFrom);
		} else {
			Log4jUtil.log(" ID " + medfusionID);

			for (int i = 0; i < patientArray.length; i++) {
				Log4jUtil.log(" patientArray[i] " + patientArray[i]);
				if (patientArray[i].contains(medfusionID)) {
					patientID = ExtractString("<PracticePatientId>(.*?)</PracticePatientId>", patientArray[i]);
				}
			}
		}
		Log4jUtil.log("PracticePatientId " + patientID);
		return patientID;
	}

	public void checkAppointmentV3(AppointmentData testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		AppointmentDataPayload apObj = new AppointmentDataPayload();
		String appointmentDataPayload = apObj.getAppointmentDataV3Payload(testData);
		Log4jUtil.log("appointmentDataPayload " + appointmentDataPayload);

		Thread.sleep(10000);
		Log4jUtil.log("Do Post message call");
		Log4jUtil.log("Get Processing URL status");
		Log4jUtil.log("RestURL : " + testData.AppointmentRequestV3URL);
		Log4jUtil.log("ResponsePath : " + testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.AppointmentRequestV3URL, appointmentDataPayload,
				testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(10000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if(testData.BatchSize.equalsIgnoreCase("1")) {
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
				}
			}
			else if(testData.BatchSize.equalsIgnoreCase("2")) {
				if (RestUtils.isResponseContainsErrorNode(testData.ResponsePath)) {
					completed = true;
					break;
			}
			}
		}
		assertTrue(completed, "Message processing was not completed in time");

		Log4jUtil.log("Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		Log4jUtil.log("Fetch Dashboard Next Time slot");
		if (!testData.Status.equalsIgnoreCase("CANCEL")) {
			Log4jUtil.log("Next Time slot is from Dashboard: " + homePage.getNextScheduledApptDate());
		}
		
		Log4jUtil.log("Goto Appointments Page");
		homePage.goToAppointmentsPage(testData.URL);
		JalapenoAppointmentsPage JAPage = new JalapenoAppointmentsPage(driver);
		Thread.sleep(8000);

		Log4jUtil.log("Check Posted Future Appointment data");
		Log4jUtil.log("appointmentType " + testData.appointmentType);
		if (testData.appointmentType == "FUTURE") {
			futureAppointment(JAPage, apObj, testData);
			Thread.sleep(8000);
		}

		Log4jUtil.log("Logout");
		homePage.clickOnLogout();
	}

	public void checkAppointmentV3Batch(AppointmentData testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		AppointmentDataPayload apObj = new AppointmentDataPayload();
		String appointmentDataPayload = apObj.getAppointmentDataV3PayloadBatch(testData);
		Log4jUtil.log("appointmentDataPayload " + appointmentDataPayload);

		Thread.sleep(10000);
		Log4jUtil.log("Do Post message call");
		Log4jUtil.log("Get Processing URL status");
		Log4jUtil.log("RestURL : " + testData.AppointmentRequestV3URL);
		Log4jUtil.log("ResponsePath : " + testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.AppointmentRequestV3URL, appointmentDataPayload, testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(10000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (testData.BatchSize.equalsIgnoreCase("1")) {
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
					completed = true;
					break;
				}
			} else if (Integer.parseInt(testData.BatchSize) >= 2) {
				if (RestUtils.isResponseContainsErrorNodeBatch(testData.ResponsePath)) {
					completed = true;
					break;
				}
			}
		}
		assertTrue(completed, "Message processing was not completed in time");

		Log4jUtil.log("Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		Log4jUtil.log("Fetch Dashboard Next Time slot");
		if (!testData.Status.equalsIgnoreCase("CANCEL")) {
			Log4jUtil.log("Next Time slot is from Dashboard: " + homePage.getNextScheduledApptDate());
		}

		Log4jUtil.log("Goto Appointments Page");
		homePage.goToAppointmentsPage(testData.URL);
		JalapenoAppointmentsPage JAPage = new JalapenoAppointmentsPage(driver);
		Thread.sleep(8000);

		Log4jUtil.log("Check Posted Future Appointment data");
		Log4jUtil.log("appointmentType " + testData.appointmentType);
		if (testData.appointmentType == "FUTURE") {
			futureAppointment(JAPage, apObj, testData);
			Thread.sleep(8000);
		}

		Log4jUtil.log("Logout");
		homePage.clickOnLogout();
	}

	public void checkAppointmentV4(AppointmentData testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		AppointmentDataPayload apObj = new AppointmentDataPayload();
		String appointmentDataPayload = apObj.getAppointmentDataV4Payload(testData);
		Log4jUtil.log("appointmentDataPayload " + appointmentDataPayload);

		Thread.sleep(10000);
		Log4jUtil.log("Do Post message call");
		Log4jUtil.log("Get Processing URL status");
		Log4jUtil.log("RestURL : " + testData.AppointmentRequestV4URL);
		Log4jUtil.log("ResponsePath : " + testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.AppointmentRequestV4URL, appointmentDataPayload,
				testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(10000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if(testData.BatchSize.equalsIgnoreCase("1")) {
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
				completed = true;
				break;
				}
			}
			else if (testData.BatchSize.equalsIgnoreCase("2")) {
				if (RestUtils.isResponseContainsErrorNode(testData.ResponsePath)) {
					completed = true;
					break;
			}
			}
		}
		assertTrue(completed, "Message processing was not completed in time");

		Log4jUtil.log("Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		Log4jUtil.log("Fetch Dashboard Next Time slot");
		if (!testData.Status.equalsIgnoreCase("CANCEL")) {
			Log4jUtil.log("Next Time slot is from Dashboard: " + homePage.getNextScheduledApptDate());
		}
		
		Log4jUtil.log("Goto Appointments Page");
		homePage.goToAppointmentsPage(testData.URL);
		JalapenoAppointmentsPage JAPage = new JalapenoAppointmentsPage(driver);
		Thread.sleep(8000);

		Log4jUtil.log("Check Posted Future Appointment data");
		Log4jUtil.log("appointmentType " + testData.appointmentType);
		if (testData.appointmentType == "FUTURE") {
			futureAppointment(JAPage, apObj, testData);
			Thread.sleep(8000);
		}

		Log4jUtil.log("Logout");
		homePage.clickOnLogout();
	}

	public void checkAppointmentV4Batch(AppointmentData testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		AppointmentDataPayload apObj = new AppointmentDataPayload();
		String appointmentDataPayload = apObj.getAppointmentDataV4PayloadBatch(testData);
		Log4jUtil.log("appointmentDataPayload " + appointmentDataPayload);

		Thread.sleep(10000);
		Log4jUtil.log("Do Post message call");
		Log4jUtil.log("Get Processing URL status");
		Log4jUtil.log("RestURL : " + testData.AppointmentRequestV4URL);
		Log4jUtil.log("ResponsePath : " + testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.AppointmentRequestV4URL, appointmentDataPayload, testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(10000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (testData.BatchSize.equalsIgnoreCase("1")) {
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
					completed = true;
					break;
				}
			} else if (Integer.parseInt(testData.BatchSize) >= 2) {
				if (RestUtils.isResponseContainsErrorNodeBatch(testData.ResponsePath)) {
					completed = true;
					break;
				}
			}
		}
		assertTrue(completed, "Message processing was not completed in time");

		Log4jUtil.log("Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		Log4jUtil.log("Fetch Dashboard Next Time slot");
		if (!testData.Status.equalsIgnoreCase("CANCEL")) {
			Log4jUtil.log("Next Time slot is from Dashboard: " + homePage.getNextScheduledApptDate());
		}

		Log4jUtil.log("Goto Appointments Page");
		homePage.goToAppointmentsPage(testData.URL);
		JalapenoAppointmentsPage JAPage = new JalapenoAppointmentsPage(driver);
		Thread.sleep(8000);

		Log4jUtil.log("Check Posted Future Appointment data");
		Log4jUtil.log("appointmentType " + testData.appointmentType);
		if (testData.appointmentType == "FUTURE") {
			futureAppointment(JAPage, apObj, testData);
			Thread.sleep(8000);
		}

		Log4jUtil.log("Logout");
		homePage.clickOnLogout();
	}



	public void checkAppointmentBatch(AppointmentData testData, WebDriver driver) throws Exception {
		Log4jUtil.log("Generate Payload with Status as " + testData.Status);
		AppointmentDataPayload apObj = new AppointmentDataPayload();
		String appointmentDataPayload = apObj.getAppointmentDataPayloadBatch(testData);
		Log4jUtil.log("appointmentDataPayload " + appointmentDataPayload);

		Thread.sleep(10000);

		Log4jUtil.log("Get Processing URL status");
		Log4jUtil.log("RestURL : " + testData.AppointmentPath);
		Log4jUtil.log("ResponsePath : " + testData.ResponsePath);
		String processingUrl = RestUtils.setupHttpPostRequest(testData.AppointmentPath, appointmentDataPayload, testData.ResponsePath);
		Log4jUtil.log("processingUrl " + processingUrl);

		Boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(10000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.ResponsePath);
			if (testData.BatchSize.equalsIgnoreCase("1")) {
				if (RestUtils.isMessageProcessingCompleted(testData.ResponsePath)) {
					completed = true;
					break;
				}
			} else if (Integer.parseInt(testData.BatchSize) >= 2) {
				if (RestUtils.isResponseContainsErrorNodeBatch(testData.ResponsePath)) {
					completed = true;
					break;
				}
			}
		}
		assertTrue(completed, "Message processing was not completed in time");
		Log4jUtil.log("Login to Patient Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.URL);
		JalapenoHomePage homePage = loginPage.login(testData.UserName, testData.Password);
		// String[] timeArray = null;
		Log4jUtil.log(" Fetch Dashboard Next Time slot");
		if (!testData.Status.equalsIgnoreCase("CANCEL")) {
			Log4jUtil.log("Next Time slot is from Dashboard: " + homePage.getNextScheduledApptDate());

		}
		Log4jUtil.log(" Goto Appointments Page");
		homePage.goToAppointmentsPage(testData.URL);
		JalapenoAppointmentsPage JAPage = new JalapenoAppointmentsPage(driver);
		Thread.sleep(8000);

		Log4jUtil.log("appointmentType " + testData.appointmentType);
		if (testData.appointmentType == "FUTURE") {
			futureAppointment(JAPage, apObj, testData);
			Thread.sleep(8000);
		}

		homePage.clickOnLogout();
	}
}