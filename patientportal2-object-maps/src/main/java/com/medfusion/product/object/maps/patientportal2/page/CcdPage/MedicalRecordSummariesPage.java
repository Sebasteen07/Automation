package com.medfusion.product.object.maps.patientportal2.page.CcdPage;

import static org.testng.AssertJUnit.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.MFDateUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;





public class MedicalRecordSummariesPage extends MedfusionPage {

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

	@FindBy(how = How.XPATH, using = "//*[@id=\"ccdTable\"]/tbody[1]/tr/td[1]/input")
	private WebElement firstVisibleCCDCheckbox;

	@FindBy(how = How.XPATH, using = "//*[@id=\"ccdTable\"]/tbody[3]/tr/td[1]/input")
	private WebElement secondVisibleCCDCheckbox;

	@FindBy(how = How.XPATH, using = "//*[@id=\"ccdTable\"]/tbody[1]/tr/td[2]/a")
	private WebElement firstVisibleCCDDate;

	@FindBy(how = How.XPATH, using = "//*[@id=\"ccdTable\"]/tbody[3]/tr/td[2]/a")
	private WebElement secondVisibleCCDDate;

	@FindBy(how = How.XPATH, using = "//*[@id=\"ccdTable\"]/tbody[3]/tr/td[2]/a")
	private WebElement thirdVisibleCCDDate;

	@FindBy(how = How.ID, using = "emailAddress")
	private WebElement emailAddressInput;

	@FindBy(how = How.ID, using = "transmitButton")
	private WebElement transmitButton;

	@FindBy(how = How.XPATH, using = "//*[@class='notification-message']/p[@data-ng-if='vm.transmitSuccess']")
	private WebElement successNotificationMessage;

	// Adding new radioButton and acknowledgement check box

	@FindBy(how = How.ID, using = "secureTransmit")
	private WebElement secureTransmit;

	@FindBy(how = How.ID, using = "unsecureTransmit")
	private WebElement unsecureTransmit;

	@FindBy(how = How.ID, using = "acknowledgement")
	private WebElement acknowledgement;

	@FindBy(how = How.ID, using = "plusLinkButton")
	private WebElement getStartedButton;

	@FindBy(how = How.XPATH, using = "//*[@id=\"common-nav-container\"]/ul/li[2]/a")
	private WebElement otherDocument;

	@FindBy(how = How.XPATH, using = "//*[@id=\"documentsTable\"]")
	private WebElement secureMessageAttachmentData;

	@FindBy(how = How.XPATH, using = "//*[@id=\"documentsTable\"]/tbody[1]/tr/td[5]/button")
	private WebElement downloadAttachment;
	
	
	public MedicalRecordSummariesPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(fromDate);
		webElementsList.add(toDate);
		webElementsList.add(selectAll);
		webElementsList.add(emailButton);
		webElementsList.add(downloadButton);
		webElementsList.add(firstVisibleCCDCheckbox);
		webElementsList.add(firstVisibleCCDDate);

		return assessPageElements(webElementsList);
	}

	public void sendCCDIfNewestIsOlderThan(int days) {
		ZonedDateTime ccdDateUTC = MFDateUtil.parseDateToUTCZonedTime(firstVisibleCCDDate.getText());		
		ZonedDateTime weekAgoDateUTC = MFDateUtil.getCurrentTimeUTC().minus(days, ChronoUnit.DAYS);
		
		log("CCD Date found:" + firstVisibleCCDDate.getText()+ " parsed from local to utc as: " + ccdDateUTC.toString());
		log("Today - 7 days found and parsed from local to utc as: " + weekAgoDateUTC.toString());
				
		if (MFDateUtil.compareDatesFromZonedDateTime(weekAgoDateUTC, ccdDateUTC) > 0) {
			log("CCD Date found was older than 7 days, parsed from local to utc as: " + ccdDateUTC.toString());
			//TODO send a new CCD to patient;			
			
			
		} else {
			log("CCD Date found is younger than 7 days, parsed from local to utc as: " + ccdDateUTC.toString());
			// log("The newest CCD isn't older than " + days + " days.");
		}
	}	
	public void setFilterToThirdCCDDate() {
		setFilterToElementsDate(thirdVisibleCCDDate);
	}

	public void selectFirstVisibleCCD() {
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



	public void sendFirstVisibleCCDUsingDirectProtocol(String directEmailAddress) {
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(emailButton));
		emailButton.click();
		assertTrue(areEmailLightboxElementsPresent());		
		selectDirectProtocol();
		emailAddressInput.sendKeys(directEmailAddress);
		transmitButton.click();
		log("Wait for success notification message");
		new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElement(successNotificationMessage, "Your health data has been successfully sent"));
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
		new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElement(successNotificationMessage, "Your health data has been successfully sent"));
	}

	public void setFilterToDefaultPositionAndCheckElements() {
		filterCCDs(MFDateUtil.parseDateToUTCZonedTime(firstVisibleCCDDate.getText()).toInstant(), Instant.now());
		assertTrue(areBasicPageElementsPresent());
	}

	private boolean areEmailLightboxElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(secureTransmit);
		webElementsList.add(unsecureTransmit);
		if (unsecureTransmit.isSelected() == true) {
			webElementsList.add(acknowledgement);
		}
		webElementsList.add(emailAddressInput);
		webElementsList.add(transmitButton);

		return assessPageElements(webElementsList);
	}

	private void setFilterToElementsDate(WebElement element) {		
		Instant inst = MFDateUtil.parseDateToUTCZonedTime(element.getText()).toInstant();
		filterCCDs(inst, inst);
		new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@data-ng-repeat='ccd in vm.ccdList'][3]//a")));
		assertTrue("The first element in the list does not satisfy the filter!", MFDateUtil.parseDateToUTCZonedTime(firstVisibleCCDDate.getText()).toInstant().truncatedTo(ChronoUnit.DAYS).equals(inst));
		
	}

	private String getDateFromTimeStamp(long timeStamp) {
		Date dateToConvert = new Date(timeStamp);
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
		String convertedDate = formatter.format(dateToConvert);
		return convertedDate;
	}

	private void filterCCDs(String fromDate, String toDate) {
		updateWebElement(this.fromDate, fromDate);
		updateWebElement(this.toDate, toDate);
	}
	
	private void filterCCDs(Instant from, Instant to) {		
		String fromString = MFDateUtil.getShortUSDateLocal(from);
		String toString = MFDateUtil.getShortUSDateLocal(to);
		
		log("Instant TO ISO:" + from.toString() + " parsed to:" + fromString.toString());
		log("Instant FROM ISO:" + to.toString() + " parsed to:" + toString.toString());
		
		updateWebElement(this.fromDate, fromString);
		fromDate.sendKeys(Keys.ENTER);
		// I'm afraid it's vital to wait a little bit here, to allow update after the first element is set
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateWebElement(this.toDate, toString);
		toDate.sendKeys(Keys.ENTER);
	}

	public void selectDownload() {
		downloadButton.click();
	}

	public void selectFirstVisibleCCDDate() {
		firstVisibleCCDDate.click();
	}

	public void selectSecondVisibleCCDDate() {
		secondVisibleCCDDate.click();
	}

	public void setFilterToDefaultPositionAndCheckElementsNew() {
		filterCCDs(getOnlyDateFromElementNew(firstVisibleCCDDate), getDateFromTimeStamp(System.currentTimeMillis()));
		assertTrue(areBasicPageElementsPresent());
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
	
	
}