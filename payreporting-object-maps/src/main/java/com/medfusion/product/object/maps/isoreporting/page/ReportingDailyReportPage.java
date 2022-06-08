//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.medfusion.common.utils.PropertyFileLoader;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.payreporting.pojo.GWRTransactionLine;
import com.medfusion.payreporting.pojo.Total;

public class ReportingDailyReportPage extends ReportingNavigationMenu {

	public ReportingDailyReportPage(WebDriver driver) {
		super(driver);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	// drr

	@FindBy(how = How.ID, using = "merchantSelect")
	private WebElement Merchant_name;

	@FindBy(how = How.ID, using = "runSearch")
	private WebElement reportButton;

	@FindBy(how = How.ID, using = "dailyReportTotalsDownload")
	private WebElement downloadButton;

	@FindBy(how = How.ID, using = "merchantSelect")
	private WebElement merchantSelect;

	@FindBy(how = How.XPATH, using = "//*[@id='dateFrom']")
	private WebElement dateFrom;

	@FindBy(how = How.ID, using = "dateTo")
	private WebElement dateTo;

	@FindBy(how = How.ID, using = "statusSelection")
	private WebElement statusSelect;

	@FindBy(how = How.ID, using = "transactionId")
	private WebElement transactionId;

	@FindBy(how = How.ID, using = "printReport")
	private WebElement printReportButton;

	@FindBy(how = How.ID, using = "fundedRadio")
	private WebElement radioFundedButton;

	@FindBy(how = How.ID, using = "dailyRadio")
	private WebElement radioDailyButton;

	@FindBy(how = How.XPATH, using = "//*[@id='pageHeader']/nav/div[2]/ul/li/a/span[1]")
	private WebElement signoutButton;

	@FindBy(how = How.XPATH, using = "//*[@id='toast-container']/div/button")
	private WebElement toastCloseButton;

	// header
	@FindBy(how = How.ID, using = "headerDateRange")
	private WebElement dateRange;

	@FindBy(how = How.ID, using = "headerRunAt")
	private WebElement reportRunAt;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyHeader']/div[2]/div[1]")
	private WebElement merchantName;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyHeader']/div[2]/div[3]")
	private WebElement customerID;

	// Filter

	@FindBy(how = How.ID, using = "clrFilters")
	private WebElement clearFilters;

	@FindBy(how = How.ID, using = "globalSearch")
	private WebElement globalFilterSearch;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[1]/st-select-date-time/select")
	private WebElement activityDateFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[2]/st-select-str/select")
	private WebElement fundedDateFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[3]/st-select-sorted/select")
	private WebElement paymentSourceFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[4]/st-select-sorted/select")
	private WebElement locationFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[5]/st-select-sorted/select")
	private WebElement staffNameFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[6]/st-select-str/select")
	private WebElement patientNameFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[7]/st-select-str/select")
	private WebElement accountNumberFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[8]/st-select-sorted/select")
	private WebElement cardTypeFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[9]/st-select-str/select")
	private WebElement cardHolderFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[10]/st-select-str/select")
	private WebElement cardNumberFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[13]/st-select-str/select")
	private WebElement statusFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[14]/st-select-sorted/select")
	private WebElement paymentAmountFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/thead/tr[3]/th[15]/st-select-sorted/select")
	private WebElement refundAmountFilter;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr[1]/td[11]")
	private WebElement getTransactionID;

	// Transaction receipt

	@FindBy(how = How.ID, using = "closeBtn")
	private WebElement closeReceiptButton;

	@FindBy(how = How.ID, using = "email")
	private WebElement emailField;

	@FindBy(how = How.XPATH, using = "//*[@id='rcptEmailForm']/div[2]/button")
	private WebElement sendEmail;

	@FindBy(how = How.ID, using = "printReceiptButton")
	private WebElement printTransactionReceipt;

	// Daily Totals Report

	@FindBy(how = How.NAME, using = "filteredTotalCount")
	private WebElement numberOfPayments;

	@FindBy(how = How.NAME, using = "filteredTotalAmount")
	private WebElement totalPaymentAmount;

	@FindBy(how = How.NAME, using = "filteredRefundCount")
	private WebElement NumberOfRefunds;

	@FindBy(how = How.NAME, using = "filteredRefundAmount")
	private WebElement totalRefundAmount;

	@FindBy(how = How.NAME, using = "filteredNet")
	private WebElement netAmount;

	// Daily Transaction Report

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[1]")
	private WebElement activityDate;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[2]")
	private WebElement fundedDate;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[3]")
	private WebElement paymentSource;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[4]")
	private WebElement location;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[5]")
	private WebElement staffName;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[6]")
	private WebElement patientName;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[7]")
	private WebElement accountNumber;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[8]")
	private WebElement cardType;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[9]")
	private WebElement cardHolder;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[10]")
	private WebElement cardNumber;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[11]")
	private WebElement transactionID;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[12]")
	private WebElement orderId;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[13]")
	private WebElement status;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[14]")
	private WebElement paymentamount;

	@FindBy(how = How.XPATH, using = "//*[@id='dailyTransactionsTable']/tbody/tr/td[15]")
	private WebElement refundamount;

	// Daily Transaction Report - Last Transaction shortcuts
	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[1])[last()]")
	private WebElement activityDateLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[2])[last()]")
	private WebElement fundedDateLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[3])[last()]")
	private WebElement paymentSourceLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[4])[last()]")
	private WebElement locationLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[5])[last()]")
	private WebElement staffNameLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[6])[last()]")
	private WebElement patientNameLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[7])[last()]")
	private WebElement accountNumberLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[8])[last()]")
	private WebElement cardTypeLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[9])[last()]")
	private WebElement cardHolderLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[10])[last()]")
	private WebElement cardNumberLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[11])[last()]")
	private WebElement transactionIDLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[12])[last()]")
	private WebElement orderIdLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[13])[last()]")
	private WebElement statusLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[14])[last()]")
	private WebElement paymentAmountLast;

	@FindBy(how = How.XPATH, using = "(//*[@id='dailyTransactionsTable']/tbody/tr/td[15])[last()]")
	private WebElement refundAmountLast;

	@FindBy(how = How.ID, using = "toast-container")
	private WebElement errorMsg;

	public void clickSearch() {
		reportButton.click();
		waitFor();
	}

	public void clickCloseToast() {
		toastCloseButton.click();
	}

	public void clickFundedRadio() {
		radioFundedButton.click();
		Assert.assertTrue("Funded radio button is not selected", radioFundedButton.isSelected());
		clickSearch();
	}

	public void clickDailyRadio() {
		radioDailyButton.click();
		clickSearch();
	}

	public void selectMerchant(String merchant) {
		Select select = new Select(merchantSelect);
		select.selectByVisibleText(merchant);
	}

	public void fillDateFrom(String fromdate) {
		dateFrom.clear();
		dateFrom.sendKeys(fromdate);
	}

	public void fillTransactionDateTodayAndMerchant(String merchant) throws Exception {
		String date = getCurrentDate();
		searchWithMerchantAndDateDetails(date, date, merchant);
	}

	public String getCurrentDate() throws Exception {
		String dateformat = "MM/dd/yyyy";
		String date = IHGUtil.getFormattedCurrentDate(dateformat);
		return date;
	}

	public void assertOnDeclinedTransactions() {
		assertThatNoTransactions();
		int rowcount = countRows();
		for (int count = 1; count <= rowcount; count++) {
			try {
				WebElement eye = driver.findElement(
						By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[15]/a/span"));
				eye.click();
				Thread.sleep(5000);
			} catch (Exception e) {
			}
		}
	}

	public void fillDateTo(String todate) {
		dateTo.clear();
		dateTo.sendKeys(todate);
	}

	public void selectStatusAll() {
		Select select = new Select(statusSelect);
		select.selectByVisibleText("All");
		waitFor();
		clickSearch();
	}

	public void runDRRFor(String fromdate, String todate, String merchant) {
		searchWithMerchantAndDateDetails(fromdate, todate, merchant);
	}

	public void searchWithMerchantAndDateDetails(String fromdate, String todate, String merchant) {
		fillDateFrom(fromdate);
		fillDateTo(todate);
		selectMerchant(merchant);
		clickSearch();
		waitFor();
		assertDRRDetailsPresent(merchant);
		Assert.assertTrue("Either the merchant is not selected or date range set was invalid",
				!driver.getPageSource().contains("Data Error"));
		Assert.assertTrue("Unexpected error message is seen", !driver.getPageSource().contains("Unexpected Error"));
		Assert.assertTrue("System error message is seen", !driver.getPageSource().contains("System Error"));

	}

	public void selectStatusDeclined() {
		Select select = new Select(statusSelect);
		waitFor();
		select.selectByVisibleText("Declined");
		Assert.assertTrue("Declined status could not be selected!",
				statusSelect.getAttribute("value").equalsIgnoreCase("Declined"));
		clickSearch();
	}

	public void selectStatusVoid() {
		Select select = new Select(statusSelect);
		waitFor();
		select.selectByVisibleText("Void");
		Assert.assertTrue("Void status is not selected!", statusSelect.getAttribute("value").equalsIgnoreCase("Void"));
		clickSearch();
	}

	public void verifyVoidStatus() {
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		for (int count = 1; count <= rowcount; count++) {
			try {
				String voidstatus = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[12]"))
						.getText();
				Assert.assertEquals("Filter on status 'Void' is not working ", "Void", voidstatus);
			} catch (Exception e) {
			}
		}
	}

	public void selectStatusPending() {
		Select select = new Select(statusSelect);
		waitFor();
		select.selectByVisibleText("Pending");
		Assert.assertTrue("Pending status is not selected!",
				statusSelect.getAttribute("value").equalsIgnoreCase("Pending"));
		clickSearch();
	}

	public void verifyPendingStatus() {
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		for (int count = 1; count <= rowcount; count++) {
			try {
				String statuspending = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[12]"))
						.getText();
				Assert.assertEquals("Filter on status 'Pending' is not working ", "Pending", statuspending);
			} catch (Exception e) {
			}
		}

	}

	public void waitFor() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void printReport() {
		printReportButton.click();
		printReportButton.sendKeys(Keys.ENTER);
	}

	public void downloadCsv() throws InterruptedException {
		downloadButton.click();
		downloadButton.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		Assert.assertTrue("The csv file could not be downloaded", isFileDownloaded(downloadPath, extension));
	}

	public int countRows() {
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		return rowcount;
	}

	private boolean isFileDownloaded(String downloadPath, String extension) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			flag = false;
		}

		for (int i = 1; i < files.length; i++) {
			if (files[i].getName().contains(extension)) {
				flag = true;
			}
		}
		return flag;
	}

	public File getLatestFilefromDir(String downloadPath) {
		File dir = new File(downloadPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	// Filter

	public void clearFilters() {
		clearFilters.click();
	}

	// payment filter gimmick, all amounts should be there, even the new one once it
	// arrives
	public boolean selectCheckPaymentAmountFilter(String amount) {
		Select select = new Select(paymentAmountFilter);
		try {
			select.selectByVisibleText(amount);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	// refund filter gimmick, all amounts should be there, even the new one once it
	// arrives
	public boolean selectCheckRefundAmountFilter(String amount) {
		Select select = new Select(refundAmountFilter);
		try {
			select.selectByVisibleText(amount);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public void selectActivityDate(String fromdate) {
		Select select = new Select(activityDateFilter);
		waitFor();
		List<WebElement> options = select.getOptions();
		int length = options.size();
		for (int i = 0; i < length; i++) {
			String values = options.get(i).getText();
			if (values.equals(fromdate)) {
				select.selectByIndex(i);
				Assert.assertTrue("The activity date selected is incorerct!",
						activityDateFilter.getAttribute("value").equalsIgnoreCase(fromdate));
				break;
			}
		}
		Assert.assertTrue("Verification Failed: The selected activity date option " + fromdate + "is not available",
				activityDateFilter.getAttribute("value").equals(fromdate));
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		for (int count = 1; count <= rowcount; count++) {
			try {
				String date = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[1]"))
						.getText();
				String activitydate = date.substring(0, 10).trim();
				Assert.assertEquals(
						"Filter on activity date is not working.Values are not filtered by the selected activity date ",
						fromdate, activitydate);
			} catch (Exception e) {
			}
		}
	}

	public void selectFundedDate(String fundedDate) {
		Select select = new Select(fundedDateFilter);
		waitFor();
		List<WebElement> options = select.getOptions();
		int length = options.size();
		for (int i = 0; i < length; i++) {
			String values = options.get(i).getText();
			if (values.equals(fundedDate)) {
				select.selectByIndex(i);
				Assert.assertTrue("The funded date selected is incorerct!",
						fundedDateFilter.getAttribute("value").equalsIgnoreCase(fundedDate));
				break;
			}
		}
		Assert.assertTrue("Verification Failed: The selected funded date option " + fundedDate + "is not available!",
				fundedDateFilter.getAttribute("value").equals(fundedDate));
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		for (int count = 1; count <= rowcount; count++) {
			try {
				String date = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[2]"))
						.getText();
				Assert.assertEquals(
						"Filter on funded date is not working!Values are not filtered by the selected funded date ",
						fundedDate, date);
			} catch (Exception e) {
			}
		}
	}

	public void selectPaymentSource(String paysource) {
		Select select = new Select(paymentSourceFilter);
		List<WebElement> options = select.getOptions();
		int length = options.size();
		if (length > 1) {
			for (int i = 0; i < length; i++) {
				String values = options.get(i).getText();
				if (values.equals(paysource)) {
					select.selectByIndex(i);
					Assert.assertTrue("The payment source selection is incorerct!",
							paymentSourceFilter.getAttribute("value").equalsIgnoreCase(paysource));
					break;
				}
			}
		}
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		for (int count = 1; count <= rowcount; count++) {
			try {
				String source = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[3]"))
						.getText();
				Assert.assertEquals(
						"Filter on payment source is not working! Values are not filtered by the selected payment source",
						paysource, source);
			} catch (Exception e) {
			}
		}
	}

	public void selectLocation(String location) {
		Select select = new Select(locationFilter);
		waitFor();
		List<WebElement> options = select.getOptions();
		int length = options.size();
		for (int i = 0; i < length; i++) {
			String values = options.get(i).getText();
			if (values.equals(location)) {
				select.selectByIndex(i);
				Assert.assertTrue("The location selection is incorerct!",
						locationFilter.getAttribute("value").equalsIgnoreCase(location));
				break;
			}
		}
		Assert.assertTrue("Verification Failed: The selected location option " + location + " is not available!",
				locationFilter.getAttribute("value").equals(location));
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		for (int count = 1; count <= rowcount; count++) {
			try {
				String locationvalue = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[4]"))
						.getText();
				Assert.assertEquals(
						"Filter on location is not working! Values are not filtered by the selected location! ",
						location, locationvalue);
			} catch (Exception e) {
			}
		}

	}

	public void selectStaffName(String staff) {
		Select select = new Select(staffNameFilter);
		waitFor();
		List<WebElement> options = select.getOptions();
		int length = options.size();
		for (int i = 0; i < length; i++) {
			String values = options.get(i).getText();
			if (values.equals(staff)) {
				select.selectByIndex(i);
				Assert.assertTrue("The staff name selection is incorerct!",
						staffNameFilter.getAttribute("value").equalsIgnoreCase(staff));
				break;
			}
		}
		Assert.assertTrue("Verification Failed: The selected staff name option " + staff + " is not available",
				staffNameFilter.getAttribute("value").equals(staff));
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		for (int count = 1; count <= rowcount; count++) {
			try {
				String staffname = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[5]"))
						.getText();
				Assert.assertEquals(
						"Filter on staff name is not working ! Values are not filterd by the selected staff name",
						staff, staffname);
			} catch (Exception e) {
			}
		}
	}

	public void selectCardType(String cardtype) {
		Select select = new Select(cardTypeFilter);
		waitFor();
		List<WebElement> options = select.getOptions();
		int length = options.size();
		for (int i = 0; i < length; i++) {
			String values = options.get(i).getText();
			if (values.equals(cardtype)) {
				select.selectByIndex(i);
				Assert.assertTrue("The card type selection is incorerct!",
						cardTypeFilter.getAttribute("value").equalsIgnoreCase(cardtype));
				break;
			}
		}
		Assert.assertTrue("Verification Failed: The selected card type option " + cardtype + " is not available!",
				cardTypeFilter.getAttribute("value").equals(cardtype));
		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		for (int count = 1; count <= rowcount; count++) {
			try {
				String card = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[8]"))
						.getText();
				Assert.assertEquals(
						"Filter on card type is not working ! 	Values are not filtered by the selected card type! ",
						cardtype, card);
			} catch (Exception e) {
			}
		}

	}

	public void globalSearch(String searchfor) {
		globalFilterSearch.click();
		waitFor();
		globalFilterSearch.sendKeys(searchfor);
	}

	public void fillTransactionId() {
		String transaction = getTransactionID.getText();
		transactionId.clear();
		transactionId.click();
		transactionId.sendKeys(transaction);
		clickSearch();
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(reportButton));
	}

	public void fillTransanctionID(String transaction) {
		transactionId.sendKeys(transaction);
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(reportButton));
		reportButton.click();
	}

	public String getTransactionReportActivity() {
		return activityDate.getText();
	}

	public String getTransactionReportFunded() {
		return fundedDate.getText();
	}

	public String getTransactionReportSource() {
		return paymentSource.getText();
	}

	public String getTransactionReportLocation() {
		return location.getText();
	}

	public String getTransactionReportStaff() {
		return staffName.getText();
	}

	public String getTransactionReportPatient() {
		return patientName.getText();
	}

	public String getTransactionReportAccount() {
		return accountNumber.getText();
	}

	public String getTransactionReportCardType() {
		return cardType.getText();
	}

	public String getTransactionReportCardHolder() {
		return cardHolder.getText();
	}

	public String getTransactionReportCardNumber() {
		return cardNumber.getText();
	}

	public String getTransactionReportTransactionId() {
		return transactionID.getText();
	}

	public String getTransactionReportStatus() {
		return status.getText();
	}

	public String getOrderId() {
		return orderId.getText();
	}

	public String getTransactionReportPayment() {
		return paymentamount.getText();
	}

	public String getTransactionReportRefund() {
		return refundamount.getText();
	}

	public void clearTransactionId() {
		transactionId.click();
		transactionId.clear();
		clickSearch();
	}

	public String getTotalPayment() {
		return totalPaymentAmount.getText();
	}

	public String getTotalRefund() {
		return totalRefundAmount.getText();

	}

	public String getNetAmount() {
		return netAmount.getText();

	}

	public String getNoOfPayments() {
		return numberOfPayments.getText();
	}

	public String getNoOfRefunds() {
		return NumberOfRefunds.getText();
	}

	public void compareDailyTotalsWithDailyTransactions() {
		String noofpayments = getNoOfPayments();
		String paymentamount = getTotalPayment();
		String noofrefunds = getNoOfRefunds();
		String refundamount = getTotalRefund();
		String netamount = getNetAmount();
		System.out.println("Total number of payments :" + noofpayments);
		System.out.println("Total payments amount:" + paymentamount);
		System.out.println("Total number of refunds :" + noofrefunds);
		System.out.println("Total refund amount :" + refundamount);
		System.out.println("Net amount :" + netamount);

		double paymenttotal = getTotalOfPayments();
		NumberFormat payment = NumberFormat.getCurrencyInstance(Locale.US);
		System.out
				.println("The Total Payment Amount under Daily Transaction Report is:" + payment.format(paymenttotal));
		Assert.assertEquals(payment.format(paymenttotal), paymentamount);

		double refundtotal = getTotalOfRefunds();
		NumberFormat refund = NumberFormat.getCurrencyInstance(Locale.US);
		System.out.println("The Total Refund Amount under Daily Transaction Report is:" + refund.format(refundtotal));
		Assert.assertEquals(refund.format(refundtotal), refundamount);

		double nettotal = paymenttotal - refundtotal;
		DecimalFormat net = new DecimalFormat("$#,##0.00;$-#,##0.00");
		System.out.println("The Net Amount under Daily Transaction Report is:" + net.format(nettotal));
		Assert.assertEquals(net.format(nettotal), netamount);
	}

	public void compareDailyTotalsWithTransactionsForFilters() {
		String noofpayments = getNoOfPayments();
		String paymentamount = getTotalPayment();
		String noofrefunds = getNoOfRefunds();
		String refundamount = getTotalRefund();
		String netamount = getNetAmount();
		System.out.println("Total number of payments :" + noofpayments);
		System.out.println("Total payment amount:" + paymentamount);
		System.out.println("Total number of refunds:" + noofrefunds);
		System.out.println("Total refund amount :" + refundamount);
		System.out.println("Net amount :" + netamount);

		double paymenttotal = getTotalOfPayments();
		NumberFormat payment = NumberFormat.getCurrencyInstance(Locale.US);
		System.out
				.println("The Total Payment Amount under Daily Transaction Report is:" + payment.format(paymenttotal));
		Assert.assertEquals(payment.format(paymenttotal), paymentamount);

		double refundtotal = getTotalOfRefunds();
		NumberFormat refund = NumberFormat.getCurrencyInstance(Locale.US);
		System.out.println("The Total Refund Amount under Daily Transaction Report is:" + refund.format(refundtotal));
		Assert.assertEquals(refund.format(refundtotal), refundamount);

		double nettotal = paymenttotal - refundtotal;
		NumberFormat net = NumberFormat.getCurrencyInstance(Locale.US);
		System.out.println("The Net Amount under Daily Transaction Report is:" + net.format(nettotal));
		Assert.assertEquals(net.format(nettotal), netamount);
	}

	public void assertThatNoTransactions() {
		String payments = numberOfPayments.getText();
		org.testng.Assert.assertEquals(payments, "0");
		String paymentamount = totalPaymentAmount.getText();
		org.testng.Assert.assertEquals(paymentamount, "$0.00");
		String refunds = NumberOfRefunds.getText();
		org.testng.Assert.assertEquals(refunds, "0");
		String refundamount = totalRefundAmount.getText();
		org.testng.Assert.assertEquals(refundamount, "$0.00");
		String net = netAmount.getText();
		org.testng.Assert.assertEquals(net, "$0.00");
	}

	public ReportingLoginPage logout(WebDriver driver) {
		signoutButton.click();
		return PageFactory.initElements(driver, ReportingLoginPage.class);
	}

	public void assertDRRDetailsPresent(String merchant) {
		Assert.assertNotNull("Date Range is not present", dateRange);
		Assert.assertNotNull("Report run at header is not present", reportRunAt);
		Assert.assertEquals(merchant, merchantName.getText());
		Assert.assertNotNull("Customer ID is not present in the DRR", customerID);

	}

	public double getTotalOfPayments() {

		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		double payment = 0;
		for (int count = 0; count <= rowcount; count++) {
			try {
				String status = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[13]"))
						.getText();
				if (!status.equalsIgnoreCase("Declined")) {
					String paymentamount = driver
							.findElement(
									By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[14]"))
							.getText();
					System.out.println(paymentamount.substring(1) + "  (at row: " + count + ")");
					payment = payment + Double.parseDouble(paymentamount.substring(1).trim());
				}
			} catch (Exception e) {
			}
		}
		return payment;
	}

	public double getTotalOfRefunds() {

		int rowcount = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[*]")).size();
		double refund = 0;
		for (int count = 0; count <= rowcount; count++) {
			try {
				String status = driver
						.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[13]"))
						.getText();
				if (!status.equalsIgnoreCase("Declined")) {
					String refundamount = driver
							.findElement(
									By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[" + count + "]/td[15]"))
							.getText();
					System.out.println(refundamount.substring(1) + "  (at row: " + count + ")");
					refund = refund + Double.parseDouble(refundamount.substring(1).trim());
				}
			} catch (Exception e) {
			}
		}
		return refund;
	}

	public ReportingTransactionDetail openLastTransaction() throws InterruptedException {
		Actions action = new Actions(driver);
		WebElement lastrow = driver
				.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[last()]/td[16]/a/span"));
		action.doubleClick(lastrow).perform();
		return PageFactory.initElements(driver, ReportingTransactionDetail.class);
	}

	public String getLastTransactionPaymentAmount() throws InterruptedException {
		return paymentAmountLast.getText().substring(1);
	}

	public String getLastTransactionRefundAmount() throws InterruptedException {
		return refundAmountLast.getText().substring(1);
	}

	/*
	 * This method will open a transaction detail from a list of found transactions
	 * 
	 */
	public ReportingTransactionDetail openTransactionDetail(String transactionID) throws InterruptedException {
		WebElement transactionDetailButton = driver.findElement(
				By.xpath("//*[@id=\"" + transactionID + "\"]/td/a[contains(@data-ng-click,'VIEW_DETAIL')]"));
		transactionDetailButton.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", transactionDetailButton);
		Thread.sleep(5000);
		return PageFactory.initElements(driver, ReportingTransactionDetail.class);

	}

	public void selectStatus(String status) throws InterruptedException {

		Thread.sleep(3000);

		Select select = new Select(statusSelect);
		select.selectByVisibleText(status);
		select.selectByValue(status);
		Thread.sleep(3000);
		reportButton.click();
		waitFor();
	}

	public boolean isErrorPresent() {
		return errorMsg.isDisplayed();
	}

	public void enterTransactionId(String transaction) {
		transactionId.clear();
		transactionId.click();
		transactionId.sendKeys(transaction);
	}

	/*
	 * Search for transactions Merchant is compulsory, rest can be left as null
	 */
	public void searchTransactions(String merchant, String transactionfrom, String transactionto, String status,
			String transactionID, boolean daily) throws InterruptedException {
		selectMerchant(merchant);
		if (transactionfrom != null) {
			fillDateFrom(transactionfrom);
		}
		if (transactionto != null) {
			fillDateTo(transactionto);
		}
		if (!daily) {
			clickFundedRadio();
		}
		if (status != null) {
			selectStatus(status);
		}
		if (transactionID != null) {
			enterTransactionId(transactionID);
		}
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(reportButton));
		// It sometimes does not click the button without this
		reportButton.click();
		Thread.sleep(5000);
	}

	public void searchTransactions(String merchant, String transactionfrom, String transactionto)
			throws InterruptedException {
		searchTransactions(merchant, transactionfrom, transactionto, null, null, true);
	}

	// There should be just one result found when searching by Transaction ID
	public void searchByTransactionID(String merchant, String transactionID) throws InterruptedException {
		searchTransactions(merchant, null, null, null, transactionID, true);
		Assert.assertTrue("There are no transactions present.The transaction ID entered cannot be found",
				countRows() == 1);
	}

	public void searchTransactionAssertPending(String merchant, String transactionID)
			throws InterruptedException, IOException {
		searchByTransactionID(merchant, transactionID);
		String transactionstatus = getTransactionReportStatus();

		Assert.assertTrue("The transaction status should be pending!", transactionstatus.equalsIgnoreCase("Pending"));
		Assert.assertFalse("Either the merchant is not selected or date range set was invalid",
				driver.getPageSource().contains("Data Error"));

	}

	public boolean assesDRRPageElements() {
		ArrayList<WebElement> reportingDailyPageElements = new ArrayList<WebElement>();
		reportingDailyPageElements.add(logoutButton);
		reportingDailyPageElements.add(menuBarButton);
		reportingDailyPageElements.add(reportButton);
		IHGUtil util = new IHGUtil(driver);
		return util.assessAllPageElements(reportingDailyPageElements, ReportingDailyReportPage.class);
	}

	public GWRTransactionLine getLastTransactionInfoArray() {
		if (countRows() < 1)
			throw new IllegalArgumentException("The table is empty!");
		return new GWRTransactionLine(activityDateLast.getText(), fundedDateLast.getText(), paymentSourceLast.getText(),
				location.getText(), staffNameLast.getText(), patientNameLast.getText(), accountNumberLast.getText(),
				cardTypeLast.getText(), cardHolderLast.getText(), cardNumberLast.getText(), transactionIDLast.getText(),
				orderIdLast.getText(), statusLast.getText(), paymentAmountLast.getText(), refundAmountLast.getText());
	}

	public void waitForRowsToLoad() throws IOException {
		PropertyFileLoader testData = new PropertyFileLoader();
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(testData.getProperty("timeout")));
		wait.until(ExpectedConditions.visibilityOf(activityDate));
	}

	public Total getTotals() {
		Total total = new Total();
		System.out.println("Fetching values from daily report.. ");
		total.paymentsCount = this.getNoOfPayments();
		System.out.println("Number of payments in DRR:" + total.paymentsCount);
		total.paymentsAmount = this.getTotalPayment();
		System.out.println("Total payment in DRR:" + total.paymentsAmount);
		total.refundsCount = this.getNoOfRefunds();
		System.out.println("Number of refunds in DRR:" + total.refundsCount);
		total.refundsAmount = this.getTotalRefund();
		System.out.println("Total refund amount in DRR:" + total.refundsAmount);
		total.netAmount = this.getNetAmount();
		System.out.println("Net amount in DRR:" + total.netAmount);
		return total;

	}

	public void saveOrderIdFromDRR() throws IOException {
		String orderid = getOrderId();
		Properties configProperty = new Properties();
		File file = new File("configurations.properties");
		FileInputStream fileIn = new FileInputStream(file);
		configProperty.load(fileIn);
		configProperty.setProperty("orderid", orderid);
		FileOutputStream fileOut = new FileOutputStream(file);
		configProperty.store(fileOut, "save order id");
		fileOut.close();

	}

	public void saveTransactionIdFromDRR() throws IOException {
		String transactionid = getTransactionReportTransactionId();
		Properties configProperty = new Properties();
		File file = new File("configurations.properties");
		FileInputStream fileIn = new FileInputStream(file);
		configProperty.load(fileIn);
		configProperty.setProperty("transactionid", transactionid);
		FileOutputStream fileOut = new FileOutputStream(file);
		configProperty.store(fileOut, "save transaction id");
		fileOut.close();

	}

	public void saveOrderIdAndTransactionIdFromDRR() throws IOException {
		saveOrderIdFromDRR();
		saveTransactionIdFromDRR();

	}

	public String getActivityDate(String date) throws ParseException {

		String[] splitString = date.split(" ");
		String datePart = splitString[0];
		String extractedValue = datePart.replace("/", "");
		return extractedValue;
	}

	public String getAmountInPennies(String amount) throws ParseException {

		String amountPlain = amount.replace("$", "");
		String amt = amountPlain.substring(0, amountPlain.indexOf("."));
		int amountInPennies = Integer.parseInt(amt) * 100;
		String amountStrInPennies = String.valueOf(amountInPennies);

		return amountStrInPennies;
	}

	public void saveInfoToProp(String key, String value) throws IOException {

		Properties configProperty = new Properties();
		File file = new File("configurations.properties");
		FileInputStream fileIn = new FileInputStream(file);
		configProperty.load(fileIn);
		configProperty.put(key, value);
		FileOutputStream fileOut = new FileOutputStream(file);
		configProperty.store(fileOut, "save transaction id");
		fileOut.close();
	}

	public void saveFundingInfo(String transactionID, String orderID, String activityDate, String amountForVcs)
			throws ParseException, IOException {

		String date = getActivityDate(activityDate);
		String amountToSave = getAmountInPennies(amountForVcs);

		saveInfoToProp("transactionid", transactionID);
		saveInfoToProp("orderid", orderID);
		saveInfoToProp("activitydate", date);
		saveInfoToProp("amount", amountToSave);

	}

}
