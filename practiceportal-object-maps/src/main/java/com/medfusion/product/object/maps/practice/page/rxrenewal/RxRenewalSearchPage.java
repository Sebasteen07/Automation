//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.rxrenewal;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class RxRenewalSearchPage extends BasePageObject {

	@FindBy(id = "id81")
	private WebElement monthPicker;

	@FindBy(name = "buttons:submit")
	private WebElement getPayments;

	@FindBy(id = "MfAjaxFallbackDefaultDataTable")
	private WebElement searchResults;

	@FindBy(name = "searchParams:0:input:Date Begin:month")
	private WebElement startMonth;

	@FindBy(name = "searchParams:0:input:Date End:month")
	private WebElement endMonth;

	@FindBy(name = "searchParams:0:input:Date Begin:day")
	private WebElement startDay;

	@FindBy(name = "searchParams:0:input:Date End:day")
	private WebElement endDay;

	@FindBy(name = "searchParams:0:input:Date Begin:year")
	private WebElement startYear;

	@FindBy(name = "searchParams:0:input:Date End:year")
	private WebElement endYear;

	@FindBy(xpath = ".//table/tbody/tr/td[4]/span")
	private List<WebElement> searchResultReason;

	@FindBy(name = "rxrs:0:rxPanel:container:table:quantity")
	private WebElement setQuantity;

	@FindBy(xpath = "//select[@name='rxrs:0:rxPanel:container:table:frequency']")
	private WebElement setFrequency;

	@FindBy(xpath = "//input[@name='sendmessage:subject']")
	private WebElement setSubject;

	@FindBy(xpath = "//textarea[@name='sendmessage:body']")
	private WebElement setSubjectBody;

	@FindBy(xpath = "//input[@name='communicateAndProcessRxRenewal']")
	private WebElement processRxRenewalbtn;

	@FindBy(xpath = "//span[@fieldid='drugName']")
	private WebElement mediactionName;

	@FindBy(xpath = "//span[@fieldid='dosage']")
	private WebElement drug;

	@FindBy(xpath = "//span[@fieldid='quantity']")
	private WebElement quantity;

	@FindBy(xpath = "//span[@fieldid='frequency']")
	private WebElement frequency;

	@FindBy(xpath = "//select[@name='rxrs:0:rxPanel:container:table:frequency']")
	private WebElement frequencySelect;

	@FindBy(xpath = "//input[@name='rxrs:0:rxPanel:container:table:quantity']")
	private WebElement quantityTextField;

	@FindBy(xpath = "//input[@name='sendmessage:subject']")
	private WebElement subjectTextField;

	@FindBy(xpath = "//textarea[@name='sendmessage:body']")
	private WebElement bodyTextField;

	@FindBy(xpath = "//input[@name='communicateAndProcessRxRenewal']")
	private WebElement communicateAndProcessRxRenewalButton;

	@FindBy(xpath = "	(//a[@class='MfSecureLink'])[1]")
	private WebElement goToRxRenewalPageButton;

	@FindBy(xpath = "//span[@fieldid='subject']")
	private WebElement subjectMessage;

	@FindBy(xpath = "//span[@fieldid='body']")
	private WebElement bodyMessage;

	@FindBy(xpath = "//input[@name='confirmAction']")
	private WebElement confirmAction;

	@FindBy(xpath = "//input[@name='submit:submit']")
	private WebElement continueButton;

	@FindBy(xpath = "//div[@id='content']/div[2]/span")
	public WebElement processingCompletedtxt;

	@FindBy(name = "searchParams:1:input")
	private List<WebElement> status;

	private long createdTs;

	private String subject;

	public String getSubject() {
		IHGUtil.PrintMethodName();
		return subject;
	}

	public long getCreatedTs() {
		IHGUtil.PrintMethodName();
		return createdTs;
	}

	public RxRenewalSearchPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		createdTs = System.currentTimeMillis();
	}

	public void searchForRxRenewalToday() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		Select endMonthSelect = new Select(endMonth);
		Select startMonthSelect = new Select(startMonth);
		Select endDaySelect = new Select(endDay);
		Select startDaySelect = new Select(startDay);
		Select endYearSelect = new Select(endYear);
		Select startYearSelect = new Select(startYear);

		String index = endMonthSelect.getFirstSelectedOption().getAttribute("index");
		startMonthSelect.selectByIndex(Integer.parseInt(index));

		Thread.sleep(2000);

		String index2 = endDaySelect.getFirstSelectedOption().getAttribute("index");
		startDaySelect.selectByIndex(Integer.parseInt(index2));
		Thread.sleep(2000);

		String index3 = endYearSelect.getFirstSelectedOption().getAttribute("index");
		startYearSelect.selectByIndex(Integer.parseInt(index3));
		Thread.sleep(2000);

		getPayments.click();
		Thread.sleep(8000);

	}

	public RxRenewalDetailPage getRxRenewalDetails() throws Exception {
		IHGUtil.PrintMethodName();
//		PracticeUtil.setPracticeFrame(driver);

		try {
			searchResults.isDisplayed();
		} catch (Exception e) {
			throw new Exception("RxRenewall search result table is not found. Ensure a search was completed first.");
		}

		// Selecting the first one row from the search
		WebElement rxrenewal = searchResultReason.get(0);
		rxrenewal.click();

		driver.switchTo().defaultContent();
		Thread.sleep(10000);
		return PageFactory.initElements(driver, RxRenewalDetailPage.class);
	}

	public void setRxRenewalFields() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		setQuantity.clear();
		setQuantity.sendKeys(PracticeConstants.QUANTITY);
		Select startDaySelect = new Select(setFrequency);
		startDaySelect.selectByVisibleText(PracticeConstants.FREQUENCY);
		subject = createdTs + PracticeConstants.MESSAGE_SUBJECT;
		setSubject.sendKeys(subject);
		setSubjectBody.sendKeys(PracticeConstants.MESSAGE_BODY);
	}

	public void setRxRenewalFields(String user) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		setQuantity.clear();
		setQuantity.sendKeys(PracticeConstants.QUANTITY);
		Select startDaySelect = new Select(setFrequency);
		startDaySelect.selectByVisibleText(PracticeConstants.FREQUENCY);
		subject = createdTs + PracticeConstants.MESSAGE_SUBJECT + user;
		setSubject.sendKeys(subject);
		setSubjectBody.sendKeys(PracticeConstants.MESSAGE_BODY);
	}

	public void clickProcessRxRenewal() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		processRxRenewalbtn.click();
		Thread.sleep(2000);
	}

	public void verifyPrescriptionConfirmationSection(String subject) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver, 30, mediactionName);
		assertEquals(drug.getText(), PracticeConstants.DRUG);
		assertEquals(quantity.getText(), PracticeConstants.QUANTITY);
		wait.until(ExpectedConditions.visibilityOf(frequency));
		assertEquals(frequency.getText(), PracticeConstants.FREQUENCY);
		assertEquals(subjectMessage.getText(), subject);
		assertEquals(bodyMessage.getText(), PracticeConstants.MESSAGE_BODY);
	}

	public void setActionRadioButton() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		confirmAction.click();
		IHGUtil.waitForElement(driver, 10, continueButton);
		continueButton.click();
	}

	public void verifyProcessCompleted() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		assertEquals(processingCompletedtxt.getText(), PracticeConstants.PROCESSING_COMPLETED_TEXT);
	}

	public void searchForRxRenewalToday(int value) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		Thread.sleep(10000);

		Select endMonthSelect = new Select(endMonth);
		Select startMonthSelect = new Select(startMonth);
		Select endDaySelect = new Select(endDay);
		Select startDaySelect = new Select(startDay);
		Select endYearSelect = new Select(endYear);
		Select startYearSelect = new Select(startYear);

		String index = endMonthSelect.getFirstSelectedOption().getAttribute("index");
		startMonthSelect.selectByIndex(Integer.parseInt(index));
		Thread.sleep(2000);

		String index2 = endDaySelect.getFirstSelectedOption().getAttribute("index");
		startDaySelect.selectByIndex(Integer.parseInt(index2));
		Thread.sleep(2000);
		String index3 = endYearSelect.getFirstSelectedOption().getAttribute("index");
		startYearSelect.selectByIndex(Integer.parseInt(index3));
		Thread.sleep(2000);

		for (WebElement s : status) {
			if (Integer.parseInt(s.getAttribute("value")) == value) {
				s.click();
			}
		}
		getPayments.click();
		Thread.sleep(8000);
	}

	public void updateMedicationDetails(String SigCodeAbbreviation) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		Select frequency = new Select(frequencySelect);
		frequency.selectByVisibleText(SigCodeAbbreviation);
		quantityTextField.sendKeys("2");
		subjectTextField.sendKeys(PracticeConstants.MESSAGE_SUBJECT);
		bodyTextField.sendKeys(PracticeConstants.MESSAGE_BODY);
		communicateAndProcessRxRenewalButton.click();
		setActionRadioButton();
		goToRxRenewalPageButton.click();
	}


	public void checkMedicationDetails(String medicationName, String sigCode) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		Log4jUtil.log("Searching: Mediaction Name is:" + medicationName + ", and Actual Medication Name is:"
				+ mediactionName.getText().toString());
		Log4jUtil
				.log("Searching: SigCode Abbreviation & Meaning is:" + sigCode + ", and Actual SigCode Abbreviation & Meaning is:" + frequency.getText().toString());
		assertEquals(mediactionName.getText(), medicationName, "Invalid Medication Name was found");
		// assertEquals(frequency.getText(), sigCode, "Invalid SigCode Abbreviation & Meaning were found");
	}

	public void verifyPrescriptionConfirmationSection(String subject2, String drugDosage) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver, 30, mediactionName);
		assertEquals(drug.getText(), drugDosage);
		assertEquals(quantity.getText(), PracticeConstants.QUANTITY);
		wait.until(ExpectedConditions.visibilityOf(frequency));
		assertEquals(frequency.getText(), PracticeConstants.FREQUENCY);
		assertEquals(subjectMessage.getText(), subject);
		assertEquals(bodyMessage.getText(), PracticeConstants.MESSAGE_BODY);
	}
}
