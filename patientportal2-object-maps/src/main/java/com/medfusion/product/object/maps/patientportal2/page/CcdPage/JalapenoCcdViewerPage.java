// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.CcdPage;

import static org.testng.Assert.fail;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ihg.common.utils.downloads.RequestMethod;
import com.intuit.ihg.common.utils.downloads.URLStatusChecker;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;


public class JalapenoCcdViewerPage extends MedfusionPage {
	// page does not contain JalapenoMenu

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

	// This isn't present for all CCDs
	@FindBy(how = How.ID, using = "healthOverview")
	private WebElement healthOverview;

	@FindBy(how = How.ID, using = "basicInfo")
	private WebElement basicInformation;

	@FindBy(how = How.ID, using = "careteam")
	private WebElement careTeamMembers;

	@FindBy(how = How.ID, using = "idp5824848")
	private WebElement history;

	@FindBy(how = How.ID, using = "idp5829488")
	private WebElement problems;

	@FindBy(how = How.ID, using = "idp6074208")
	private WebElement medication;

	@FindBy(how = How.ID, using = "idp6124944")
	private WebElement allergies;

	@FindBy(how = How.ID, using = "idp6147776")
	private WebElement pastMedicalHistory;

	@FindBy(how = How.ID, using = "idp6173024")
	private WebElement procedures;

	@FindBy(how = How.ID, using = "idp6205744")
	private WebElement vitalSigns;

	@FindBy(how = How.ID, using = "idp6335632")
	private WebElement results;

	@FindBy(how = How.ID, using = "idp6433952")
	private WebElement advanceDirectives;

	@FindBy(how = How.ID, using = "idp6454816")
	private WebElement insurance;
	
	@FindBy(how = How.ID, using = "personalHeader")
	private WebElement headerName;
	
	@FindBy(how = How.XPATH, using = "//dd[text()='Name']/following-sibling::dt")
	private WebElement patientName;
	
	@FindBy(how = How.XPATH, using = "//dl[dd[text()='Date Of Birth']]/dt/span")
	private WebElement patientDOB;
	
	@FindBy(how = How.XPATH, using = "//dd[text()='Race']/following-sibling::dt")
	private WebElement patientRace;
	
	@FindBy(how = How.XPATH, using = "//dd[text()='Ethnicity']/following-sibling::dt")
	private WebElement patientEthnicity;
	
	@FindBy(how = How.XPATH, using = "//dl[dt[text()='Sex']]/dd/span")
	private WebElement patientSex;
	
	@FindBy(how = How.XPATH, using = "//dl[dt[text()='Marital Status']]/dd/span")
	private WebElement patientMaritialStatus;
	
	@FindBy(how = How.XPATH, using = "//div[dd[text()='Email Address']]/following-sibling::table/tbody/tr/td/div/dt")
	private WebElement patientEmail;
	
	@FindBy(how = How.XPATH, using = "//ul[@class='telecom performers clearfix']/li/dt")
	private WebElement patientPhone;
	
	@FindBy(how = How.XPATH, using = "//ul[@class='careteam performers clearfix']/li/dl/dt")
	private WebElement careTeamMember;
	
	
	@FindBy(how = How.XPATH, using = "//*[@class='messageContent']//*[contains(text(),'View health data')]")
	private WebElement btnViewHealthData;
	
	@FindBy(id = "basicInfo")
	private WebElement ccdBasicInfo;

	public JalapenoCcdViewerPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	public JalapenoMessagesPage closeCcd(WebDriver driver) {
		log("Closing Ccd form, returning to Messages page");
		closeButton.click();
		IHGUtil.waitForElement(driver, 30, btnViewHealthData);
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
		assertEquals(resultMessageUnsecure.getText(), "E-mail address fields must match.");

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

	public void clickBtnViewHealthData() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, btnViewHealthData);
		btnViewHealthData.click();
	}

	public void verifyCCDViewerAndClose() throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();

		if (ccdBasicInfo.isDisplayed() && closeButton.isDisplayed()) {
			closeButton.click();
		} else {
			fail("CCD Viewer not present: Could not find CCD Basic Info/Close Viewer Button");
		}
	}

	public void savePdf() {
		savePdfButton.click();
	}

	public void saveRawXML() {
		saveRawButton.click();
	}
	
	public String getPatientName() {
		return patientName.getText();
	}
	public String getHeaderName() {
		return headerName.getText();
	}

	public String getPatientDOB() {
		return patientDOB.getText();
	}
	
	public String getPatientRace() {
		return patientRace.getText();
	}
	
	public String getPatientEthnicity() {
		return patientEthnicity.getText();
	}
	
	public String getPatientSex() {
		return patientSex.getText();
	}
	
	public String getPatientMaritialStatus() {
		return patientMaritialStatus.getText();
	}
	
	public String getPatientEmail() {
		return patientEmail.getText();
	}
	
	public String getPatientPhoneNumber() {
		return patientPhone.getText();
	}
	
	public String getPatientCareTeamMember() {
		return careTeamMember.getText();
	}
}
