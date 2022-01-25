// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.onlinebillpay;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class OnlineBillPaySearchPage extends BasePageObject {

	@FindBy(id = "id6")
	private WebElement monthPicker;

	@FindBy(id = "id1a")
	private WebElement inputAccountNumber;

	@FindBy(name = "buttons:submit")
	private WebElement getPayments;

	@FindBy(id = "MfAjaxFallbackDefaultDataTable")
	private WebElement searchResults;

	@FindBy(id = "id2a")
	private WebElement billpaydetail;

	@FindBy(name = "searchParams:0:input:Date Begin:month")
	private WebElement startMonth;

	@FindBy(name = "searchParams:0:input:Date End:month")
	private WebElement endMonth;

	@FindBy(name = "searchParams:0:input:Date Begin:year")
	private WebElement startYear;

	@FindBy(name = "searchParams:0:input:Date End:year")
	private WebElement endYear;

	@FindBy(xpath = "//input[@name='secureComm:subject']")
	private WebElement setSubject;

	@FindBy(xpath = "//textarea[@name='secureComm:body']")
	private WebElement setSubjectBody;

	@FindBy(xpath = "//input[@name='secureComm:sendComm']")
	private WebElement sendCommunicationbtn;

	@FindBy(xpath = ".//a[contains(@href, 'paymybill.statementUpload')]")
	private WebElement eStatementUploadLink;

	@FindBy(xpath = ".//a[contains(@href, 'paymybill.patsearch_wicket')]")
	private WebElement patientSearchLink;

	@FindBy(xpath = ".//tbody/tr[1]/td[9]/span/span")
	private WebElement post;

	@FindBy(name = "searchParams:1:input")
	private List<WebElement> status;

	private long createdTs;

	public long getCreatedTs() {
		IHGUtil.PrintMethodName();
		return createdTs;
	}

	public OnlineBillPaySearchPage(WebDriver driver) {
		super(driver);
		createdTs = System.currentTimeMillis();
		PageFactory.initElements(driver, this);
	}

	public void searchForBillPayToday() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		Select endMonthSelect = new Select(endMonth);
		Select startMonthSelect = new Select(startMonth);
		Select endYearSelect = new Select(endYear);
		Select startYearSelect = new Select(startYear);

		String index = endMonthSelect.getFirstSelectedOption().getAttribute("index");
		startMonthSelect.selectByIndex(Integer.parseInt(index));
		Thread.sleep(2000);

		String index3 = endYearSelect.getFirstSelectedOption().getAttribute("index");
		startYearSelect.selectByIndex(Integer.parseInt(index3));
		Thread.sleep(2000);

	}

	public OnlineBillPayDetailPage searchForBillPayment(String SaccountNumber) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		inputAccountNumber.sendKeys(SaccountNumber);
		getPayments.click();

		return PageFactory.initElements(driver, OnlineBillPayDetailPage.class);
	}

	public OnlineBillPayDetailPage getBillPayDetails() throws Exception {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		try {
			IHGUtil.waitForElement(driver, 30, searchResults);
			searchResults.isDisplayed();
		} catch (Exception e) {
			throw new Exception(
					"Online Bill pay search result table is not found. Ensure a search was completed first.");
		}
		// Selecting the first one due the fact that the filter is setup to day and
		// number which is generated during the test

		IHGUtil.waitForElement(driver, 30, billpaydetail);
		billpaydetail.click();
		driver.switchTo().defaultContent();
		Thread.sleep(10000);

		return PageFactory.initElements(driver, OnlineBillPayDetailPage.class);

	}

	public void setPaymentCommunicationDetails() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");
		setSubject.sendKeys(createdTs + PracticeConstants.BILL_PAYMENT_SUBJECT);
		setSubjectBody.sendKeys(PracticeConstants.BILL_PAYMENT_BODY);
		sendCommunicationbtn.click();
	}

	public PatientSearchPage clickOnPatientSearchLink() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElementInDefaultFrame(driver, 30, patientSearchLink);
		patientSearchLink.click();
		return PageFactory.initElements(driver, PatientSearchPage.class);
	}

	public String getBillDetails() throws Exception {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		try {
			searchResults.isDisplayed();
		} catch (Exception e) {
			throw new Exception("Ask A Staff search result table is not found. Ensure a search was completed first.");
		}
		// Selecting the first one due the fact that the filter is setup to day and
		// number which is generated during the test
		Thread.sleep(10000);
		String title = post.getAttribute("title").toString();
		return title;

	}

	public OnlineBillPayDetailPage searchForBillStatus(int value) throws Exception {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		for (WebElement pstatus : status) {
			if (Integer.parseInt(pstatus.getAttribute("value")) == value) {
				pstatus.click();
			}
		}
		return PageFactory.initElements(driver, OnlineBillPayDetailPage.class);
	}

}
