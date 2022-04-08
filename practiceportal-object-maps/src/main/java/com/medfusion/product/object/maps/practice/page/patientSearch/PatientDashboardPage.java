//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.patientSearch;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.customform.ViewPatientFormPage;

public class PatientDashboardPage extends BasePageObject {
	@FindBy(xpath = "//a[contains(.,'Send Password Reset Email To Patient')]")
	private WebElement userPasswordEmail;

	@FindBy(xpath = "//td/strong[text()='Email']/../following-sibling::node()/a")
	private WebElement editEmail;
	
	@FindBy(xpath = "//a[contains(@href,\"msrch.editname\")]")
	private WebElement editName;
	
	@FindBy(xpath = "//a[contains(@href,\"msrch.editgender\")]")
	private WebElement editGender;
	
	@FindBy(xpath = "//a[contains(@href,\"msrch.editzip\")]")
	private WebElement editZip;

	@FindBy(xpath = "//a[contains(.,'Send email with the username to the patient')]")
	private WebElement userIdEmail;

	@FindBy(xpath = "//a[contains(.,'Send Password Reset Email To Patient')]")
	private WebElement passwordResetLink;

	@FindBy(xpath = "//strong[contains(text(),'Patient Id')]/../../td/a")
	private WebElement editPatientID;

	@FindBy(xpath = ".//*[@id='content']/form/table/tbody/tr[5]/td[2]")
	private WebElement txtMedfusionID;

	@FindBy(name = "emrid")
	private WebElement txtexternalID;

	@FindBy(name = "submitted")
	private WebElement btnUpdateInfo;

	@FindBy(xpath = ".//table[@class='demographics']/tbody/tr[5]/td[2]")
	private WebElement lblPatientID;

	@FindBy(linkText = "Unlock Link")
	private WebElement lblunlockLink;

	@FindBy(xpath = ".//*[@id='dashboard']/fieldset[1]/table/tbody/tr[13]/td[2]")
	private WebElement lblactivationCode;

	@FindBy(xpath = ".//table[@width='650px']/tbody/tr[7]/td[2]/input")
	private WebElement externalID;

	@FindBy(xpath = ".//table[@width='650px']/tbody/tr[8]/td[2]/input")
	private WebElement externalID1;

	@FindBy(xpath = ".//table[@class='demographics']/tbody/tr[2]/td[2]")
	private WebElement patientName;

	@FindBy(xpath = ".//table[@class='demographics']/tbody/tr[7]/td[2]")
	private WebElement lblPatientSource;

	@FindBy(xpath = ".//table[@class='demographics']/tbody/tr[8]/td[2]")
	private WebElement lblPatientStatus;

	@FindBy(linkText = "Send post age-out invite")
	private WebElement postAgeOutInviteButton;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement sendPostAgeOutInviteButton;

	@FindBy(xpath = "//*[contains(text(),'Post age-out invitation has been sent successfully')]")
	private WebElement postAgeOutInvitationInfoMessage;

	@FindBy(xpath = "//strong[contains(text(),'Patient Id')]/../../td/a")
	private WebElement ediPatientID1;

	@FindBy(xpath = "//td/b[contains(text(),'Patient Id for')]/../../td[2]/input")
	private WebElement txtexternalIDPM;

	@FindBy(xpath = "//strong[contains(text(),'Patient Id')]/../../td[2]")
	private WebElement lblPatientID1;
	
	@FindBy(linkText = "Send password reset")
	private WebElement trPasswordResetLink;
	
	private WebElement feedback;

	public static String medfusionID = null;
	public String patientID = null;
	public String patientSource = "Locked - Integration";
	public String patientStatus = "Invitation Sent";

	public PatientDashboardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public PatientSearchPage sendEmailUserID() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, userIdEmail);
		userIdEmail.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);

	}

	public PatientSearchPage sendResetPasswordLink() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, passwordResetLink);
		passwordResetLink.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
	}

	public String getFeedback() throws InterruptedException {
		feedback = driver.findElement(By.xpath("//li[@class='feedbackPanelINFO']"));
		IHGUtil.waitForElement(driver, 15, feedback);
		return feedback.getText();
	}

	public PatientSearchPage clickEditEmail() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, editEmail);
		editEmail.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
	}
	
	public PatientSearchPage clickEditName() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, editName);
		editName.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
	}
	
	public PatientSearchPage clickEditGender() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, editGender);
		editGender.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
	}
	
	public PatientSearchPage clickEditZip() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, editZip);
		editZip.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
	}

	public String setExternalPatientID() {
		IHGUtil.PrintMethodName();
		editPatientLink();
		medfusionID();
		String emrID = IHGUtil.createRandomNumericString();
		txtexternalID.clear();
		txtexternalID.sendKeys(emrID);
		btnUpdateInfo.click();
		IHGUtil.waitForElement(driver, 30, lblPatientID);
		assertTrue(lblPatientID1.getText().contains(emrID), "patient ID is not set");
		return emrID;
	}

	public String setExternalPatientID_20() {
		IHGUtil.PrintMethodName();
		ediPatientID1.click();
		medfusionID();
		String emrID = IHGUtil.createRandomNumericString();
		txtexternalID.clear();
		txtexternalID.sendKeys(emrID);
		try {
		txtexternalIDPM.clear();
		txtexternalIDPM.sendKeys(emrID);
	} catch (Exception e) {
		log(e.getMessage());
	}
		btnUpdateInfo.click();
		IHGUtil.waitForElement(driver, 60, lblPatientID1);
		assertTrue(lblPatientID1.getText().contains(emrID), "patient ID is not set");
		return emrID;
	}

	public String medfusionID() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, txtMedfusionID);
		patientID = txtMedfusionID.getText().toString();
		return patientID;
	}

	public void editPatientLink() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, editPatientID);
		editPatientID.click();
	}

	public void editPatientLink_20() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, editPatientID);
		ediPatientID1.click();
	}

	public String unlockLink() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, lblunlockLink);
		return lblunlockLink.getAttribute("href").toString();

	}

	public String activationCode() {
		IHGUtil.waitForElement(driver, 60, lblactivationCode);
		return lblactivationCode.getText().toString();

	}

	public ViewPatientFormPage openFormDetails(String formName) {
		driver.findElement(By.xpath("//*[contains(./text(),'" + formName + "')]")).click();
		return PageFactory.initElements(driver, ViewPatientFormPage.class);
	}

	public String externalID(String configExternalID) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 120, editPatientID);
		editPatientID.click();
		IHGUtil.waitForElement(driver, 120, externalID);
		String External_ID = externalID.getAttribute("value").toString() + externalID1.getAttribute("value").toString();
		assertEquals(External_ID, configExternalID,
				"Patient has different External patient ID than expected. External patient ID is: " + External_ID);
		assertTrue(External_ID.equalsIgnoreCase(configExternalID), "External patient ID is not set");
		return externalID.getAttribute("value").toString();
	}

	public void verifyDetails(String patientID, String fName, String lName) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, patientName);
		assertEquals(patientName.getText(), (fName + " " + lName + "   Edit"),
				"First Name & Last Name did not matched");
		assertEquals(lblPatientID.getText(), (patientID + "   Edit"), "PatientID did not matched");
		assertEquals(lblPatientSource.getText(), patientSource, "Patient Source did not matched");
		assertEquals(lblPatientStatus.getText(), patientStatus, "Patient Status did not matched");
	}

	public void sendPostAgeOutInvitation() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(postAgeOutInviteButton));
		javascriptClick(postAgeOutInviteButton);
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(sendPostAgeOutInviteButton));
		javascriptClick(sendPostAgeOutInviteButton);
		log("Check if info message is present");
		assertTrue(new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(postAgeOutInvitationInfoMessage))
				.isDisplayed());
	}

	public boolean verifySubmittedForm(String formName) {
		try {
			return driver.findElement(By.xpath("//table[@class='encounters']//td[contains(text(),'" + formName + "')]"))
					.isDisplayed();
		} catch (NoSuchElementException ex) {
			return false;
		}
	}

	public String readExternalPatientID() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 120, externalID);
		patientID = txtexternalIDPM.getAttribute("value").toString();
		return patientID;
	}

	public String getExternalPatientID() {
		IHGUtil.PrintMethodName();
		ediPatientID1.click();
		IHGUtil.waitForElement(driver, 120, txtexternalIDPM);
		patientID = txtexternalIDPM.getAttribute("value").toString();
		return patientID;
	}
	
	public PatientSearchPage trustedRepSendResetPasswordLink() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, trPasswordResetLink);
		trPasswordResetLink.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
	}
}

