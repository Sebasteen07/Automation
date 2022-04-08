//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.CcdPage;

import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.MFDateUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

public class MedicalRecordSummariesPage extends JalapenoMenu {

	@FindBy(how = How.ID, using = "from-date")
	private WebElement fromDate;

	@FindBy(how = How.ID, using = "to-date")
	private WebElement toDate;

	@FindBy(how = How.ID, using = "select-all")
	private WebElement selectAll;

	@FindBy(how = How.ID, using = "emailButton")
	private WebElement emailButton;

	@FindBy(how = How.ID, using = "downloadButton")
	private WebElement downloadButton;

	@FindBy(how = How.ID, using = "healthRecordRequest")
	private WebElement healthRecordRequestButton;

	@FindBy(how = How.XPATH, using = "(//*[@type='checkbox'])[2]")
	private WebElement firstVisibleCCDCheckbox;

	@FindBy(how = How.XPATH, using = "(//*[@type='checkbox'])[3]")
	private WebElement secondVisibleCCDCheckbox;

	@FindBy(how = How.XPATH, using = "//*[@id=\"ccdTable\"]/tbody[1]/tr/td[2]/a")
	private WebElement firstVisibleCCDDate;

	@FindBy(how = How.XPATH, using = "//*[@id=\"ccdTable\"]/tbody[2]/tr/td[2]/a")
	private WebElement secondVisibleCCDDate;

	@FindBy(how = How.XPATH, using = "//*[@id=\"ccdTable\"]/tbody[3]/tr/td[2]/a")
	private WebElement thirdVisibleCCDDate;

	@FindBy(how = How.ID, using = "emailId")
	private WebElement emailAddressInput;

	@FindBy(how = How.ID, using = "transmitButton")
	private WebElement transmitButton;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Your health data has been successfully sent.')]")
	private WebElement successNotificationMessage;

	// Adding new radioButton and acknowledgement check box

	@FindBy(how = How.ID, using = "secureTransmit")
	private WebElement secureTransmit;

	@FindBy(how = How.ID, using = "unsecureTransmit")
	private WebElement unsecureTransmit;

	@FindBy(how = How.ID, using = "acknowledgementValue")
	private WebElement acknowledgement;

	@FindBy(how = How.ID, using = "plusLinkButton")
	private WebElement getStartedButton;
	
	@FindBy(how = How.ID, using = "viewBtn0")
	private WebElement viewButton;

	@FindBy(how = How.XPATH, using = "//*[@id=\"common-nav-container\"]/ul/li[2]/a")
	private WebElement otherDocument;

	@FindBy(how = How.XPATH, using = "//*[@id=\"documentsTable\"]")
	private WebElement secureMessageAttachmentData;

	@FindBy(how = How.XPATH, using = "//*[@id=\"documentsTable\"]/tbody[1]/tr/td[5]/button")
	private WebElement downloadAttachment;

	@FindBy(how = How.XPATH, using = "//div[@id=\"ccdModalDialog\"]/div[1]/button/span")
	private WebElement closeOnDemandPopUpButton;

	@FindBy(how = How.XPATH, using = "//span[@id='patientEducationText']")
	private WebElement patientEducationButton;

	@FindBy(how = How.XPATH, using = "//button[@class='patientEducationModalButton']")
	private WebElement launchMyEducationButton;

	@FindBy(how = How.XPATH, using = "//span[text()='Review Later']")
	private WebElement reviewLater;

	@FindBy(how = How.XPATH, using = "//span[text()='Unmatched Condition']")
	private WebElement unmatchedCondition;

	@FindBy(how = How.ID, using = "requestCcdContinueButton")
	private WebElement requestCcdContinueButton;

	@FindBy(how = How.XPATH, using = "//span[text()='Hypertensive Emergency']")
	private WebElement hypertensiveEmergency;

	@FindBy(how = How.XPATH, using = "//span[text()='Hypothyroidism']")
	private WebElement hypothyroidism;

	@FindBy(how = How.XPATH, using = "//span[text()='Organ Transplant Rejection']")
	private WebElement organTransplantRejection;

	@FindBy(how = How.XPATH, using = "//span[text()='Fever']")
	private WebElement fever;

	@FindBy(how = How.XPATH, using = "(//input[@id='select-all'])[2]")
	private WebElement requestCompleteRecord;

	@FindBy(how = How.ID, using = "requestCcdContinueButton")
	private WebElement requestRecord;
	
	@FindBy(how = How.XPATH, using = "(//input[@id='from-date'])[2]")
	private WebElement requestHealthRecordFromDate;

	@FindBy(how = How.XPATH, using = "(//input[@id='to-date'])[2]")
	private WebElement requestHealthRecordToDate;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Request complete record')]")
	private WebElement requestHealthRecord;
	
	@FindBy(how = How.ID, using = "greenLightLinkButton")
	private WebElement btnGreenLight;

	@FindBy(how = How.XPATH, using = "//span[text()='Greenlight Login']")
	private WebElement btnGreenLightLogin;
		
	@FindBy(how = How.XPATH, using = "//img[@class='greenlight-header__logo']")
	private WebElement imgGreenlightLogo;
		
	@FindBy(how = How.XPATH, using = "//*[text()='Create your new account']")
	private WebElement lblCreateYourNewAccount;
	
	@FindBy(how = How.ID, using = "healthRecordRequest")
	private WebElement requestConsolidatedHealthRecord;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Request complete record.')]/../input")
	private WebElement chkRequestcompleterecord;
	
	@FindBy(how = How.XPATH, using = "//button[@id=\"requestCcdContinueButton\"]")
	private WebElement btnRequestRecord;
	
	@FindBy(how = How.XPATH, using = "//h3[text()='Request received']")
	private WebElement notificationMessage;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Please enter a valid date range.')]")
	private WebElement txtDateErrorMessage;

	public MedicalRecordSummariesPage(WebDriver driver) {
		super(driver);
	}

	public void sendCCDIfNewestIsOlderThan(int days) {
		// added for investigation why it is failing on locals outside of US and what
		// zone is used on Jenkins where it is working
		log("First ccd date: " + firstVisibleCCDDate.getText());
		log("System default time zone: " + ZoneId.systemDefault().toString());

		ZonedDateTime ccdDateUTC = MFDateUtil.parseDateToUTCZonedTime(firstVisibleCCDDate.getText());
		log("date after parsing: " +ccdDateUTC);
		ZonedDateTime weekAgoDateUTC = MFDateUtil.getCurrentTimeUTC().minus(days, ChronoUnit.DAYS);
		log("week later: " +weekAgoDateUTC);

		log("CCD Date found:" + firstVisibleCCDDate.getText() + " parsed from local to utc as: "
				+ ccdDateUTC.toString());
		log("Today - 7 days found and parsed from local to utc as: " + weekAgoDateUTC.toString());

		if (MFDateUtil.compareDates(weekAgoDateUTC, ccdDateUTC) > 0) {
			log("CCD Date found was older than 7 days, parsed from local to utc as: " + ccdDateUTC.toString());
			// TODO send a new CCD to patient;

		} else {
			log("CCD Date found is younger than 7 days, parsed from local to utc as: " + ccdDateUTC.toString());
			// log("The newest CCD isn't older than " + days + " days.");
		}
	}

	public void setFilterToThirdCCDDate() {
		setFilterToElementsDate(thirdVisibleCCDDate);
	}

	public void selectFirstVisibleCCD() {
		new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(firstVisibleCCDCheckbox));
		firstVisibleCCDCheckbox.click();
	}

	public void selectSecondVisibleCCD() {
		secondVisibleCCDCheckbox.click();
	}

	public void selectAllVisibleCCD() {
		selectAll.click();
	}

	public void selectDirectProtocol() {
		secureTransmit.click();
	}

	public void selectStandardEmail() {
		unsecureTransmit.click();
	}

	public void acknowledgeToSendUnsecureEmail() {
		acknowledgement.click();
	}

	public void clickPatientEducation() throws InterruptedException {
		javascriptClick(patientEducationButton);
		javascriptClick(launchMyEducationButton);
		Thread.sleep(5000);//Waiting for the next page to load 
		careNexisValidation();

	}

	public void careNexisValidation() {
		driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	public void sendFirstVisibleCCDUsingDirectProtocol(String directEmailAddress) {
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(emailButton));
		emailButton.click();
		assertTrue(areEmailLightboxElementsPresent());
		selectDirectProtocol();
		emailAddressInput.sendKeys(directEmailAddress);
		transmitButton.click();
		log("Wait for success notification message");
		new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElement(successNotificationMessage,
				"Your health data has been successfully sent"));
	}

	public void sendFirstVisibleCCDUsingStandardEmail(String directEmailAddress) {
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(emailButton));
		emailButton.click();
		assertTrue(areEmailLightboxElementsPresent());
		selectStandardEmail();
		emailAddressInput.sendKeys(directEmailAddress);
		acknowledgement.click();
		transmitButton.click();
		log("Wait for success notification message");
		new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElement(successNotificationMessage,
				"Your health data has been successfully sent"));
	}

	public void setFilterToDefaultPositionAndCheckElements() {
		IHGUtil.waitForElement(driver, 60, firstVisibleCCDDate);
		filterCCDs(MFDateUtil.parseDateToUTCZonedTime(firstVisibleCCDDate.getText()).toInstant(), Instant.now());
	}

	private boolean areEmailLightboxElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(secureTransmit);
		webElementsList.add(unsecureTransmit);
		if (unsecureTransmit.isSelected()) {
			webElementsList.add(acknowledgement);
		}
		webElementsList.add(emailAddressInput);
		webElementsList.add(transmitButton);

		return assessPageElements(webElementsList);
	}

	private void setFilterToElementsDate(WebElement element) {
		Instant inst = MFDateUtil.parseDateToUTCZonedTime(element.getText()).toInstant();
		filterCCDs(inst, inst);
		new WebDriverWait(driver, 30).until(ExpectedConditions
				.invisibilityOfElementLocated(By.xpath("//*[@data-ng-repeat='ccd in vm.ccdList'][3]//a")));
		assertTrue(MFDateUtil.parseDateToUTCZonedTime(firstVisibleCCDDate.getText()).toInstant().equals(inst), 
				"The first element in the list does not satisfy the filter!");
	}

	private String getDateFromTimeStamp(long timeStamp) {
		Date dateToConvert = new Date(timeStamp);
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
		String convertedDate = formatter.format(dateToConvert);
		return convertedDate;
	}

	public void filterCCDs(String fromDate, String toDate) {
		updateWebElement(this.fromDate, fromDate);
		updateWebElement(this.toDate, toDate);
	}
	
	public void onDemandFilterCCDs(String fromDate, String toDate) {
		updateWebElement(requestHealthRecordFromDate, fromDate);
		updateWebElement(requestHealthRecordToDate, toDate);
		requestHealthRecordToDate.sendKeys(Keys.TAB);
	}
	
	private void filterCCDs(Instant from, Instant to) {
		String fromString = MFDateUtil.getShortUSDateLocal(from);
		String toString = MFDateUtil.getShortUSDateLocal(to);

		log("Instant TO ISO:" + from.toString() + " parsed to:" + fromString.toString());
		log("Instant FROM ISO:" + to.toString() + " parsed to:" + toString.toString());

		updateWebElement(this.fromDate, fromString);
		log("From Date is updated to Past Date");
		fromDate.sendKeys(Keys.ENTER);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateWebElement(this.toDate, toString);
		log("To Date is updated to Current Date");
		toDate.sendKeys(Keys.ENTER);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void selectDownload() {
		downloadButton.click();
	}

	public void selectHealthRecordRequestButton() {
		IHGUtil.waitForElement(driver, 60, healthRecordRequestButton);
		healthRecordRequestButton.click();
	}

	public void selectFirstVisibleCCDDate() {
		firstVisibleCCDDate.click();
	}

	public void selectSecondVisibleCCDDate() {
		secondVisibleCCDDate.click();
	}

	public void setFilterToDefaultPositionAndCheckElementsNew() {
		filterCCDs(getOnlyDateFromElementNew(firstVisibleCCDDate), getDateFromTimeStamp(System.currentTimeMillis()));
	}

	private String getOnlyDateFromElementNew(WebElement element) {
		return element.getText().substring(0, element.getText().length() - 9);
	}

	public void downloadSecureMessageAttachment() {
		downloadAttachment.click();
	}

	public DocumentsPage gotoOtherDocumentTab() {
		IHGUtil.waitForElement(driver, 60, otherDocument);
		javascriptClick(otherDocument);
		return PageFactory.initElements(driver, DocumentsPage.class);
	}

	public String getMessageAttachmentData() {
		IHGUtil.waitForElement(driver, 60, secureMessageAttachmentData);
		return secureMessageAttachmentData.getText();
	}

	public void closeOnDemandPopUpButton() {
		IHGUtil.waitForElement(driver, 60, closeOnDemandPopUpButton);
		closeOnDemandPopUpButton.click();
	}

	public String getUnmatchedCondition() {
		IHGUtil.waitForElement(driver, 60, unmatchedCondition);
		return unmatchedCondition.getText();
	}

	public String gethypertensiveEmergency() {
		IHGUtil.waitForElement(driver, 60, hypertensiveEmergency);
		return hypertensiveEmergency.getText();
	}

	public String getHypothyroidism() {
		IHGUtil.waitForElement(driver, 60, hypothyroidism);
		return hypothyroidism.getText();
	}

	public String getOrganTransplantRejection() {
		IHGUtil.waitForElement(driver, 60, organTransplantRejection);
		return organTransplantRejection.getText();
	}

	public String getFever() {
		IHGUtil.waitForElement(driver, 60, fever);
		return fever.getText();

	}

	public String getTodaysDateinYYYY_MM_DDFormat() {
		LocalDateTime currentdatetime = LocalDateTime.now();
		String TodaysDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(currentdatetime);
		return TodaysDate;
	}

	public String get3MonthsOldDateinYYYY_MM_DDFormat() {
		LocalDateTime ThreeMonthsOldTodaysDate = LocalDateTime.now().minusMonths(3);
		String ThreeMonthsOldDate = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
				.format(ThreeMonthsOldTodaysDate);
		return ThreeMonthsOldDate;
	}

	public void requestCcdOnDemandFromPopUp() {
		IHGUtil.waitForElement(driver, 60, requestCcdContinueButton);
		requestCcdContinueButton.click();
		log("Clicked on the Request Record button");
	}

	public void requestCompleteRecord() {
		IHGUtil.waitForElement(driver, 60, requestCompleteRecord);
		requestCompleteRecord.click();
		IHGUtil.waitForElement(driver, 60, requestRecord);
		requestRecord.click();
	}
	
	public void clickRequestHealthRecord() {
		requestHealthRecord.click();
	}
	
	public void clickGreenLight() throws InterruptedException {
		javascriptClick(btnGreenLight);
		javascriptClick(btnGreenLightLogin);
		Thread.sleep(5000);//Waiting for the next page to load 
	}

	public boolean isGreenLightLogoDisplayed() throws TimeoutException {
		log("Verify Green Light Logo");
		try {
			return imgGreenlightLogo.isDisplayed();
		}
		catch(NoSuchElementException e){
			log("Green Light Logo is not displayed");

			return false;
		}
	}

	public String getCreateYourNewAccount() {
		IHGUtil.waitForElement(driver, 60, lblCreateYourNewAccount);
		return lblCreateYourNewAccount.getText();
	}
	public boolean isViewButtonDisplayed() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)", "");
		try {
			return viewButton.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Verify Appointment Solution  for Trusted Rep shoud not display");
			return false;
		}
	}
	public void clickOnConsolidatedHealthRecordBtn() throws InterruptedException {
		IHGUtil.waitForElement(driver, 60, requestConsolidatedHealthRecord);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", requestConsolidatedHealthRecord);
		requestConsolidatedHealthRecord.click();
	}
	
	public void selectCheckBox() throws InterruptedException {
		new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(chkRequestcompleterecord));
		chkRequestcompleterecord.click();
		}
	
	public void clickOnRequestRecordButton() throws InterruptedException {
		new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(btnRequestRecord));
		btnRequestRecord.click();
		Thread.sleep(9000);
		}
	public boolean isRequestRecivedMessageDisplayed() {
		try {
			log("Looking for the Request Recived message");
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(notificationMessage));
			return notificationMessage.isDisplayed();
		} catch (Exception e) {
		}
		return false;
	}
	
	public boolean isDateErrorMessageDisplayed() {
		try {
			log("Looking for the date error message");
			new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(txtDateErrorMessage));
			return txtDateErrorMessage.isDisplayed();
		} catch (Exception e) {
		}
		return false;
	}

}
