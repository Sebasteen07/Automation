// Copyright 2016-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.patientSearch;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.familyManagement.PatientTrustedRepresentativePage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.medfusion.product.object.maps.practice.page.patientactivation.PatientActivationPage;
import com.medfusion.product.practice.api.utils.PracticeConstants;

public class PatientSearchPage extends BasePageObject {

	public PatientSearchPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='member_first_nme']")
	private WebElement firstName;

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='member_last_nme']")
	private WebElement lastName;

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='member_email']")
	private WebElement email;

	@FindBy(xpath = "//table[@class='searchForm']//input[@id='search_now']")
	private WebElement searchForPatient;

	@FindBy(linkText = "Patient Search")
	private WebElement patientSearchLinkText;

	@FindBy(xpath = "//table[@class='sort-table']/tbody/tr/td/a")
	public WebElement searchResult;

	@FindBy(linkText = "Add a New Patient")
	private WebElement addNewPatient;

	@FindBy(xpath = ".//input[@name='searchParams:0:input']")
	private WebElement firstNameField;

	@FindBy(xpath = ".//input[@name='searchParams:1:input']")
	private WebElement lastNameField;

	@FindBy(xpath = ".//input[@name='buttons:submit']")
	private WebElement searchButton;

	@FindBy(xpath = "//input[@value='Email Username']")
	private WebElement emailUserName;

	@FindBy(name = "email")
	private WebElement newEmail;

	@FindBy(name = "confirm_email")
	private WebElement newEmailConfirm;

	@FindBy(name = "submitted")
	private WebElement updateEmail;

	@FindBy(xpath = "//input[@name='firstName']")
	private WebElement editFirstName;

	@FindBy(xpath = "//input[@name='lastName']")
	private WebElement editLastName;

	@FindBy(xpath = "//input[@checked]")
	private WebElement checkedGender;

	@FindBy(xpath = "//input[@value = 1]")
	private WebElement radioFemale;

	@FindBy(xpath = "//input[@value = 2]")
	private WebElement radioMale;

	@FindBy(xpath = "//input[@name='member_zip']")
	private WebElement inputZip;

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='member_type']")
	private List<WebElement> patientStatus;

	@FindBy(linkText = "Delete")
	private WebElement deletePatient;

	@FindBy(xpath = "//input[@value='Delete']")
	private WebElement confirmdeletePatient;

	@FindBy(xpath = "//*[text()='No Records were found']")
	private WebElement checkdeletePatient;

	@FindBy(linkText = "Deactivate")
	private WebElement deactivatePatient;

	@FindBy(xpath = "//input[@value='Deactivate']")
	private WebElement confirmdeactivatePatient;

	private WebElement patient;

	@FindBy(xpath = "//*[@name='emrid']")
	private WebElement patientIdTextbox;

	@FindBy(xpath = "//*[@id='content']/form/table/tbody/tr[7]/td[2]/input")
	private WebElement updateInfo;

	@FindBy(xpath = "//*[@id='dashboard']/fieldset[1]/table/tbody/tr[7]/td[2]/a")
	private WebElement editPatientID;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement emailPasswordReset;

	@FindBy(linkText = "Invite New")
	private WebElement lnkInviteNewTrustedRepresentative;

	@FindBy(linkText = "Edit Access")
	private WebElement lnkEditAccess;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'A trusted representative invitation has been sent')]")
	private WebElement msgInviteTrustedRepresentative;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Trusted representative access has been updated')]")
	private WebElement msgUpdateTrustedRepresentative;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),\"Duplicate Patient ID\")]")
	private WebElement msgDuplicatePatientID;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),\"A not activated patient with the same Patient ID already exists. Please click\")]")
	private WebElement msgErrorPatientCreation;

	@FindBy(name = "patientid")
	private WebElement txtPatientID;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'No Records were found')]")
	private WebElement msgNoRecordsFound;

	public void setFirstName() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, firstName);
		firstName.clear();
		firstName.sendKeys(PracticeConstants.PATIENT_FIRST_NAME);
	}

	public void setLastName() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, lastName);
		lastName.clear();
		lastName.sendKeys(PracticeConstants.PATIENT_LAST_NAME);
	}

	public void setEmail() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, email);
		email.clear();
		email.sendKeys(PracticeConstants.PATIENT_EMAIL);
	}

	public void setPatientSearchFields() {
		IHGUtil.PrintMethodName();
		setFirstName();
		setLastName();
		searchForPatient.click();
	}

	public PatientActivationPage clickOnAddNewPatient() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, addNewPatient);
		addNewPatient.click();
		return PageFactory.initElements(driver, PatientActivationPage.class);
	}

	public void searchPatient(String fName, String lName) {
		IHGUtil.PrintMethodName();
		driver.switchTo().frame("iframe");
		IHGUtil.waitForElement(driver, 30, firstNameField);
		firstNameField.sendKeys(fName);
		lastNameField.sendKeys(lName);
		searchButton.click();

	}

	public void searchForPatientInPatientSearch(String fName, String lName) {
		IHGUtil.PrintMethodName();
		firstName.clear();
		firstName.sendKeys(fName);
		lastName.clear();
		lastName.sendKeys(lName);
		searchForPatient.click();
	}

	public PatientDashboardPage modifiedPatientSearch(String fName, String lName, String uFName, String uLName)
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		firstName.clear();
		firstName.sendKeys(fName);
		lastName.clear();
		lastName.sendKeys(lName);
		searchForPatient.click();
		try {
			log("Looking for Patient");
			patient = driver.findElement(By.xpath("//a[@title='Click to View/Edit " + lName + ", " + fName + "']"));
			IHGUtil.waitForElement(driver, 30, patient);
			patient.click();
			return PageFactory.initElements(driver, PatientDashboardPage.class);
		} catch (Exception e) {
			log("Search with updated name");
			IHGUtil.waitForElementInDefaultFrame(driver, 20, patientSearchLinkText);
			patientSearchLinkText.click();
			IHGUtil.waitForElementInDefaultFrame(driver, 20, firstName);
			firstName.clear();
			firstName.sendKeys(uFName);
			lastName.clear();
			searchForPatient.click();
			patient = driver.findElement(By.xpath("//a[@title='Click to View/Edit " + uLName + ", " + uFName + "']"));
			IHGUtil.waitForElement(driver, 30, patient);
			patient.click();
			return PageFactory.initElements(driver, PatientDashboardPage.class);

		}

	}

	public void searchForPatientInPatientSearch(String email) {
		IHGUtil.PrintMethodName();
		firstName.clear();
		this.email.sendKeys(email);
		searchForPatient.click();
	}

	public boolean isTransactionPresent(String amount, String fName, String lName) {
		IHGUtil.PrintMethodName();
		log("Amount searched for: " + amount);
		return driver
				.findElement(By.xpath("//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(), '" + amount
						+ "')]/ancestor::tr/td//a/span[contains(text(), '" + lName + ", " + fName + "')]"))
				.isDisplayed();
	}

	public PayMyBillOnlinePage selectTheTransaction(String amount, String fName, String lName)
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(), '" + amount
				+ "')]/ancestor::tr/td//a/span[contains(text(), '" + lName + ", " + fName + "')]")).click();
		return PageFactory.initElements(driver, PayMyBillOnlinePage.class);
	}

	public PatientDashboardPage clickOnPatient(String frstNm, String lstNm) throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, searchResult);
		patient = driver.findElement(By.xpath("//a[@title='Click to View/Edit " + lstNm + ", " + frstNm + "']"));
		IHGUtil.waitForElement(driver, 30, patient);
		patient.click();
		return PageFactory.initElements(driver, PatientDashboardPage.class);
	}

	public PatientDashboardPage sendUserNameEmail() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, emailUserName);
		emailUserName.click();
		return PageFactory.initElements(driver, PatientDashboardPage.class);
	}

	public PatientDashboardPage sendPasswordResetEmail() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, emailPasswordReset);
		emailPasswordReset.click();
		return PageFactory.initElements(driver, PatientDashboardPage.class);
	}

	public PatientDashboardPage changeEmail(String baseEmail) {
		// create new email string from baseEmail@something.com to
		// baseEmail+1234568@something.com
		String concatEmailWith = "+" + String.valueOf(System.currentTimeMillis());
		StringBuffer sbNewEmail = new StringBuffer(baseEmail);
		sbNewEmail.insert(baseEmail.indexOf("@"), concatEmailWith);

		log("Change email to: " + sbNewEmail.toString());

		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 15, newEmail);
		newEmail.sendKeys(sbNewEmail.toString());
		newEmailConfirm.sendKeys(sbNewEmail.toString());

		IHGUtil.waitForElement(driver, 15, updateEmail);
		updateEmail.click();

		return PageFactory.initElements(driver, PatientDashboardPage.class);

	}

	public String changeName(String fName, String lName, String uFName, String uLName) throws InterruptedException {

		IHGUtil.waitForElement(driver, 10, editFirstName);
		String currentFirstName = editFirstName.getAttribute("value");
		log(currentFirstName);
		if (currentFirstName.equals(fName)) {
			editFirstName.clear();
			Thread.sleep(1000); // Adding sleep, so that two actions doesn't overlap
			editFirstName.sendKeys(uFName);
			editLastName.clear();
			Thread.sleep(1000); // Adding sleep, so that two actions doesn't overlap
			editLastName.sendKeys(uLName);
			updateEmail.click();
			return (uFName);
		} else {
			editFirstName.clear();
			Thread.sleep(1000); // Adding sleep, so that two actions doesn't overlap
			editFirstName.sendKeys(fName);
			editLastName.clear();
			Thread.sleep(1000);
			editLastName.sendKeys(lName);
			updateEmail.click();
			return (fName);
		}

	}

	public String changeGender() throws InterruptedException {
		String val = checkedGender.getAttribute("Value");
		IHGUtil.waitForElement(driver, 15, checkedGender);
		if (Integer.parseInt(val) == 2) {
			radioFemale.click();
			updateEmail.click();
			return "Female";

		} else {
			radioMale.click();
			updateEmail.click();
			return "Male";
		}

	}

	public String changeZip() throws InterruptedException {
		String curZip = inputZip.getAttribute("value");
		int updatedZip1 = Integer.parseInt(curZip) + 1;
		String updatedZip = Integer.toString(updatedZip1);

		IHGUtil.waitForElement(driver, 15, inputZip);
		inputZip.clear();
		Thread.sleep(1000); // Adding sleep, so that two actions doesn't overlap
		inputZip.sendKeys(updatedZip);
		updateEmail.click();
		return updatedZip;

	}

	public PatientDashboardPage changeEmailWithoutModify(String baseEmail) {
		IHGUtil.PrintMethodName();

		IHGUtil.waitForElement(driver, 15, newEmail);
		newEmail.sendKeys(baseEmail);
		newEmailConfirm.sendKeys(baseEmail);

		IHGUtil.waitForElement(driver, 15, updateEmail);
		updateEmail.click();

		return PageFactory.initElements(driver, PatientDashboardPage.class);

	}

	public void searchAllPatientInPatientSearch(String fName, String lName, int status) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 15, firstName);
		firstName.clear();
		firstName.sendKeys(fName);
		IHGUtil.waitForElement(driver, 15, lastName);
		lastName.clear();
		lastName.sendKeys(lName);

		for (WebElement pstatus : patientStatus) {
			if (Integer.parseInt(pstatus.getAttribute("value")) == status) {
				pstatus.click();
			}
		}
		IHGUtil.waitForElement(driver, 10, searchForPatient);
		Thread.sleep(2000);
		searchForPatient.click();
	}

	public void deletePatient() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 15, deletePatient);
		deletePatient.click();
		IHGUtil.waitForElement(driver, 15, confirmdeletePatient);
		confirmdeletePatient.click();
	}

	public void verifyDeletedPatient(String fName, String lName) throws Exception {
		IHGUtil.PrintMethodName();
		searchForPatientInPatientSearch(fName, lName);
		Boolean status = IHGUtil.waitForElement(driver, 15, checkdeletePatient);
		if (status)
			log("Patient is deleted successfully");
		else {
			log("Patient is not deleted");
			assertTrue(status);
		}
	}

	public void deactivatePatient() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 15, deactivatePatient);
		deactivatePatient.click();
		IHGUtil.waitForElement(driver, 15, confirmdeactivatePatient);
		confirmdeactivatePatient.click();
	}

	public void verifyDeactivatedPatient(String fName, String lName) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement checkdeactivatePatient = driver
				.findElement((By.xpath("//*[contains(text(),'" + fName + " " + lName + "(Deactivated)')]")));
		Boolean status = IHGUtil.waitForElement(driver, 15, checkdeactivatePatient);
		if (status)
			log("Patient is deactivated successfully");
		else {
			log("Patient is not deactivated");
			assertTrue(status);
		}
	}

	public void sendPatientIDAndClickOnUpdate(String fName) {
		IHGUtil.PrintMethodName();
		patientIdTextbox.clear();
		patientIdTextbox.sendKeys(fName);
		IHGUtil.waitForElement(driver, 10, updateInfo);
		updateInfo.click();
	}

	public void clickOnSearch() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, searchResult);
		searchResult.click();
	}

	public void clickOnEdit() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, editPatientID);
		editPatientID.click();
	}

	public String verifypatientExternalID() {
		String patientExternalID = patientIdTextbox.getAttribute("value");
		return patientExternalID;
	}

	public PatientTrustedRepresentativePage clickInviteTrustedRepresentative() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, lnkInviteNewTrustedRepresentative);
		lnkInviteNewTrustedRepresentative.click();
		return PageFactory.initElements(driver, PatientTrustedRepresentativePage.class);
	}

	public PatientTrustedRepresentativePage editTrustedRepresentativeAccess() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, lnkEditAccess);
		lnkEditAccess.click();
		return PageFactory.initElements(driver, PatientTrustedRepresentativePage.class);
	}

	public boolean wasInviteTrustedRepresentativeSuccessful() {

		try {
			log("Looking for successful message after inviting trusted representative");
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(msgInviteTrustedRepresentative));
			return msgInviteTrustedRepresentative.isDisplayed();
		} catch (Exception e) {
			log("Invite TrustedRepresentative was unsuccessful");
			return false;
		}

	}

	public void searchForPatientWithPatientID(String id) {
		IHGUtil.PrintMethodName();
		firstName.clear();
		this.txtPatientID.sendKeys(id);
		searchForPatient.click();
	}

	public boolean isNoRecordsFoundMsgDisplayed() {

		try {
			log("Looking for No records message after searching for invalid patient ID");
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(msgNoRecordsFound));
			return msgNoRecordsFound.isDisplayed();
		} catch (Exception e) {
			log("No records were found message is not displayed");
			return false;
		}

	}

	public boolean isDuplicatePatientIDErrorDisplayed() {

		try {
			log("Looking For Duplicate PatientID Error");
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(msgDuplicatePatientID));
			return msgDuplicatePatientID.isDisplayed();
		} catch (Exception e) {
			log("Duplicate PatientID Error not displayed");
			return false;
		}
	}

	public boolean isPatientCreationErrorDisplayed() {

		try {
			log("Looking For Duplicate PatientID Error");
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(msgErrorPatientCreation));
			return msgErrorPatientCreation.isDisplayed();
		} catch (Exception e) {
			log("Duplicate PatientID Error not displayed");
			return false;
		}
	}

}
