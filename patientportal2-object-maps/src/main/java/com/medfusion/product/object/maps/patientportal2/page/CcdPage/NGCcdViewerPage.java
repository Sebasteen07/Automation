// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.CcdPage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;


public class NGCcdViewerPage extends MedfusionPage {

		@FindBy(how = How.ID, using = "closeCcd")
		private WebElement closeButton;

		@FindBy(how = How.ID, using = "savePdf")
		private WebElement savePdfButton;

		@FindBy(how = How.ID, using = "saveRaw")
		private WebElement saveRawButton;

		@FindBy(how = How.ID, using = "secured_share")
		private WebElement sendDirectInformationLink;

		@FindBy(how = How.ID, using = "non_secured_share")
		private WebElement sendUnsecureInformationLink;

		@FindBy(how = How.ID, using = "directAddr")
		private WebElement directAddressBox;

		@FindBy(how = How.ID, using = "emailAddr")
		private WebElement unsecureAddressBox;

		@FindBy(how = How.ID, using = "emailAddrConfirm")
		private WebElement unsecureConfirmationAddressBox;

		@FindBy(how = How.ID, using = "secureSubmitButton")
		private WebElement sendDirectInformationButton;

		@FindBy(how = How.ID, using = "unsecureSubmitButton")
		private WebElement sendUnsecureInformationButton;

		@FindBy(how = How.XPATH, using = "//div[@id='direct_share']//span[@class='success']")
		private WebElement resultMessageDirect;

		@FindBy(how = How.XPATH, using = "//div[@id='email_share']//span[@class='success']")
		private WebElement resultMessageUnsecure;

		@FindBy(how = How.XPATH, using = "//h2[text()='Basic Information About You']")
		private WebElement basicInformation;

		@FindBy(how = How.XPATH, using = "//h2[text()='Care Team Members']")
		private WebElement careTeamMembers;

		@FindBy(how = How.XPATH, using = "//h2[text()='History Of Present Illness']")
		private WebElement history;

		@FindBy(how = How.XPATH, using = "//h2[text()='Problems']")
		private WebElement problems;

		@FindBy(how = How.XPATH, using = "//h2[text()='Medications']")
		private WebElement medication;

		@FindBy(how = How.XPATH, using = "//h2[text()='Allergies, Adverse Reactions, Alerts']")
		private WebElement allergies;

		@FindBy(how = How.XPATH, using = "//h2[text()='Past Medical History']")
		private WebElement pastMedicalHistory;

		@FindBy(how = How.XPATH, using = "//h2[text()='Procedures']")
		private WebElement procedures;

		@FindBy(how = How.XPATH, using = "//h2[text()='Vital Signs']")
		private WebElement vitalSigns;

		@FindBy(how = How.XPATH, using = "//h2[text()='Results']")
		private WebElement results;

		@FindBy(how = How.XPATH, using = "//h2[text()='Advance Directives']")
		private WebElement advanceDirectives;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Encounters']")
		private WebElement encounter;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Family History']")
		private WebElement familyHistory;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Immunizations']")
		private WebElement immunizations;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Payers']")
		private WebElement payer;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Social History']")
		private WebElement socialHistory;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Chief Complaint And Reason For Visit']")
		private WebElement chiefComplaintAndReasonForVisit;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Reason For Referral']")
		private WebElement reasonForReferral;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Plan Of Treatment']")
		private WebElement planOfTreatment;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Functional Status']")
		private WebElement functionalStatus;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Medications Administered']")
		private WebElement medicationAdministered;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Instructions']")
		private WebElement instructions;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Assessments']")
		private WebElement assessments;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Goals']")
		private WebElement goals;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Medical Equipment']")
		private WebElement medicalEquipment;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Mental Status']")
		private WebElement mentalStatus;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Health Concerns']")
		private WebElement healthConcerns;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='General Status']")
		private WebElement generalStatus;
		
		@FindBy(how = How.XPATH, using = "//h2[text()='Review of Systems']")
		private WebElement reviewOfSystem;

		@FindBy(how = How.XPATH, using = "//*[@id=\"messageContainer\"]/div[3]/div[2]/div[2]/div/div/a")
		private WebElement BtnViewHealthData;

		@FindBy(id = "basicInfo")
		private WebElement ccdBasicInfo;

		public NGCcdViewerPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();;
		}

		public JalapenoMessagesPage closeCcd(WebDriver driver) {
				log("Closing Ccd form, returning to Messages page");

				closeButton.click();

				return PageFactory.initElements(driver, JalapenoMessagesPage.class);
		}

		public boolean sendInformationToDirectEmail(String emailAddress) {
				log("Click on sending information");
				sendDirectInformationLink.click();

				log("Input the direct email address: " + emailAddress);
				directAddressBox.sendKeys(emailAddress);

				log("Send the information");
				sendDirectInformationButton.click();

				new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(resultMessageDirect));
				log("Result: " + resultMessageDirect.getText());

				return resultMessageDirect.getText().equals("Your health information was sent to " + emailAddress + "!");
		}

		public boolean sendInformationToUnsecureEmail(String emailAddress) {
				log("Click on sending information");
				sendUnsecureInformationLink.click();

				log("Input the unsecure email address: " + emailAddress + " and wrong confirmation e-mail");
				unsecureAddressBox.sendKeys(emailAddress);
				unsecureConfirmationAddressBox.sendKeys("WRONG" + emailAddress);

				log("Try to send the information");
				sendUnsecureInformationButton.click();

				new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(resultMessageUnsecure));
				log("Result: " + resultMessageUnsecure.getText());
				Assert.assertEquals(resultMessageUnsecure.getText(), "E-mail address fields must match.");

				log("Input the unsecure email address: " + emailAddress + " and correct confirmation e-mail");
				unsecureAddressBox.clear();
				unsecureConfirmationAddressBox.clear();
				unsecureAddressBox.sendKeys(emailAddress);
				unsecureConfirmationAddressBox.sendKeys(emailAddress);

				log("Send the information");
				sendUnsecureInformationButton.click();

				new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(resultMessageUnsecure));
				log("Result: " + resultMessageUnsecure.getText());
				return resultMessageUnsecure.getText().equals("Your health information was sent to " + emailAddress + "!");
		}

		public boolean checkPdfToDownload(WebDriver driver) throws IOException, URISyntaxException {
				IHGUtil.PrintMethodName();
				String pdfUrl = savePdfButton.getAttribute("href");

				URLStatusChecker status = new URLStatusChecker(driver);
				status.setURIToCheck(pdfUrl);
				status.setHTTPRequestMethod(RequestMethod.GET);

				Integer statusResult = status.getDownloadStatusCode(pdfUrl, RequestMethod.GET);

				if (statusResult.equals(200)) {
						log("The PDF file is available");
						return true;
				} else {
						log("The file is not available, return code: " + statusResult);
						return false;
				}
		}

		public boolean checkRawToDownload(WebDriver driver) throws IOException, URISyntaxException {
				IHGUtil.PrintMethodName();
				String rawUrl = saveRawButton.getAttribute("href");

				URLStatusChecker status = new URLStatusChecker(driver);
				status.setURIToCheck(rawUrl);
				status.setHTTPRequestMethod(RequestMethod.GET);

				Integer statusResult = status.getDownloadStatusCode(rawUrl, RequestMethod.GET);

				if (statusResult.equals(200)) {
						log("The RAW file is available");
						return true;
				} else {
						log("The file is not available, return code: " + statusResult);
						return false;
				}
		}

		@Override
		public boolean areBasicPageElementsPresent() {

				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

				webElementsList.add(closeButton);
				webElementsList.add(savePdfButton);
				webElementsList.add(saveRawButton);
				webElementsList.add(sendDirectInformationLink);
				//webElementsList.add(sendUnsecureInformationLink);
//				webElementsList.add(healthOverview);
				webElementsList.add(basicInformation);
				webElementsList.add(careTeamMembers);

				return assessPageElements(webElementsList);
		}


		/**
		 * Click on the View health data
		 */
		public void clickBtnViewHealthData() throws InterruptedException {
				IHGUtil.PrintMethodName();
				// PortalUtil.setPortalFrame(driver);
				IHGUtil.waitForElement(driver, 60, BtnViewHealthData);
				BtnViewHealthData.click();
		}

		public void verifyCCDViewerAndClose() throws InterruptedException {
				IHGUtil.PrintMethodName();
				driver.switchTo().defaultContent();

				if (ccdBasicInfo.isDisplayed() && closeButton.isDisplayed()) {
						closeButton.click();
				} else {
						Assert.fail("CCD Viewer not present: Could not find CCD Basic Info/Close Viewer Button");
				}
		}

		public void savePdf() {
				savePdfButton.click();
		}

		public void saveRawXML() {
				saveRawButton.click();
		}
		
		public boolean verifyCCDPageElementsPresent() {

			ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

			webElementsList.add(basicInformation);
			webElementsList.add(careTeamMembers);
			webElementsList.add(history);
			webElementsList.add(problems);
			webElementsList.add(medication);
			webElementsList.add(allergies);
			webElementsList.add(pastMedicalHistory);
			webElementsList.add(procedures);
			webElementsList.add(vitalSigns);
			webElementsList.add(results);
			webElementsList.add(advanceDirectives);
			webElementsList.add(encounter);
			webElementsList.add(familyHistory);
			webElementsList.add(immunizations);
			webElementsList.add(payer);
			webElementsList.add(socialHistory);
			webElementsList.add(chiefComplaintAndReasonForVisit);
			webElementsList.add(reasonForReferral);
			webElementsList.add(planOfTreatment);
			webElementsList.add(functionalStatus);
			webElementsList.add(medicationAdministered);
			webElementsList.add(instructions);
			webElementsList.add(assessments);
			webElementsList.add(goals);
			webElementsList.add(medicalEquipment);
			webElementsList.add(mentalStatus);
			webElementsList.add(healthConcerns);
			webElementsList.add(generalStatus);
			webElementsList.add(reviewOfSystem);

			return assessPageElements(webElementsList);
	}
}
