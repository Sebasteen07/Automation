// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.page.Appointments;

import java.util.Calendar;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;

public class AppointmentsPage extends BasePageObject {

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[1]/div")
	private WebElement startTime;

	@FindBy(how = How.ID, using = "filter-end-time")
	private WebElement endTime;

	@FindBy(how = How.CSS, using = "#mf-nav__brand")
	private WebElement practiceName;

	@FindBy(how = How.CSS, using = "#page-content-container > div > header > h1")
	private WebElement appointmentTitle;

	@FindBy(how = How.ID, using = "filter-patient-id")
	private WebElement patientIdFilter;

	@FindBy(how = How.CSS, using = "div.mf-appointments-filters > div:nth-child(4)")
	private WebElement patientFilter;

	@FindBy(how = How.XPATH, using = "//*[text()='All providers']")
	private WebElement providerFilter;

	@FindBy(how = How.XPATH, using = "//*[text()='All locations']")
	private WebElement locationFilter;

	@FindBy(how = How.CSS, using = "#select-all")
	private WebElement allCheckboxes;

	@FindBy(how = How.XPATH, using = "//select[@class='react-datepicker__month-select']")
	private WebElement months;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div/div[2]/div[2]/div[1]/div[5]")
	private WebElement date;

	@FindBy(how = How.XPATH, using = "//*[@class='react-datepicker__year-select']")
	private WebElement years;

	@FindBy(how = How.XPATH, using = "//*[text()='Refresh']")
	private WebElement refreshTab;

	@FindBy(how = How.ID, using = "actionDropdown")
	private WebElement actionDropdown;

	@FindBy(how = How.CSS, using = "#broadcastMessage")
	private WebElement broadcastMessage;

	@FindBy(how = How.CSS, using = "#sendReminder")
	private WebElement sendReminder;

	@FindBy(how = How.XPATH, using = "//textarea[@name='broadcastEnMsg']")
	private WebElement broadcastMessageInEn;

	@FindBy(how = How.XPATH, using = "//textarea[@name='broadcastEsMsg']")
	private WebElement broadcastMessageInEs;

	@FindBy(how = How.XPATH, using = "//span[@class='mf-icon mf-icon__checkbox--unchecked mf-color__disabled mf-icon-pointer mf-notification-checkbox']")
	private WebElement confirmThisMsgCheckbox;

	@FindBy(how = How.XPATH, using = "//*[@class=\"mf-cta__primary\"]")
	private WebElement sendMessageButton;

	@FindBy(how = How.XPATH, using = "//*[@class=\"mf-color__positive\"]")
	private WebElement broadcastMessageStatus;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div[1]/span/b[1]")
	private WebElement broadcastMessageSuccessStatus;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div[1]/span/b[2]")
	private WebElement broadcastMessageFailedStatus;

	@FindBy(how = How.CSS, using = "#mf-navbar__menu--profile")
	private WebElement menuProfile;

	@FindBy(how = How.XPATH, using = "//*[text()='Sign Out']")
	private WebElement signOut;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div/div[1]/div/div[3]")
	private WebElement reminders;

	@FindBy(how = How.XPATH, using = "//*[text()='Broadcasts']")
	private WebElement broadcasts;

	@FindBy(how = How.CSS, using = "#alert > p > span > a")
	private WebElement bannerMessage;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div[1]/span/b[1]")
	private WebElement broadcastMessageSuccessCount;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div[1]/span/b[2]")
	private WebElement broadcastMessageFailedCount;

	@FindBy(how = How.CSS, using = "#plus")
	private WebElement jumpToNextPage;

	@FindBy(how = How.CSS, using = "form > input[type=number]")
	private WebElement jumpToPage;

	@FindBy(how = How.CSS, using = "#alert > div > div > span")
	private WebElement selectedPatientBannerMessage;

	@FindBy(how = How.CSS, using = "div.alert-dismissible.text-center.alert-success > span")
	private WebElement bannerAftersendBroadcast;

	@FindBy(how = How.XPATH, using = "//*[text()='Appointments']")
	private WebElement appointmentsTab;

	@FindBy(how = How.XPATH, using = "//*[@id='select-13131-9999']")
	private WebElement selectPatient;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div[1]/span")
	private WebElement broadcastBannerMessage;

	@FindBy(how = How.XPATH, using = "//*[@id='select-45789-18902']")
	private WebElement selectPatientWithoutEmailAndPhone;

	@FindBy(how = How.XPATH, using = "//*[@id='select-20000-3000']")
	private WebElement selectPatientWithInvalidEmailAndPhone;

	@FindBy(how = How.XPATH, using = "//*[@id='select-44444-3434']")
	private WebElement selectPatientWithInvalidPhoneAndBlankEmail;

	@FindBy(how = How.XPATH, using = "//*[@class='close']")
	private WebElement BannerCloseButton;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-th precheck-header'][3]")
	private WebElement reminderTextColumn;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-th precheck-header'][4]")
	private WebElement broadcastTextColoumn;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-th precheck-header first-reminders-column'][1]")
	private WebElement reminderEmailColumn;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-th precheck-header first-reminders-column'][2]")
	private WebElement broadcastEmailColoumn;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div[1]/div[2]/div/div[17]/div")
	private WebElement broadcastMessageTextColoumn;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div[1]/div[2]/div/div[16]/div")
	private WebElement sendReminderTextColoumn;

	@FindBy(how = How.XPATH, using = "//*[text()='Emails']")
	private WebElement sendReminderEmailColumn;

	@FindBy(how = How.XPATH, using = "//*[@id='select-39393-7534']")
	private WebElement selectPatientWithNewAppt;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div/div[3]/div[2]/div/div[18]/div/span[2]")
	private WebElement broadcastEmail;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div/div[3]/div[1]/div/div[19]/div/span[2]")
	private WebElement broadcastText;

	@FindBy(how = How.XPATH, using = "//*[text()='Broadcast logs']")
	private WebElement broadcastLogs;

	@FindBy(how = How.XPATH, using = "//*[@class='patient-name']")
	private WebElement patientName;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-resizable-header-content']/b[text()='Time']")
	private WebElement time;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-resizable-header-content']/b[text()='Message']")
	private WebElement messageText;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-resizable-header-content']/b[text()='Status']")
	private WebElement statusText;

	@FindBy(how = How.XPATH, using = "//*[@id=\"closeReminderStatusesModal\"]")
	private WebElement closeBroadcastEmailBox;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div/div[3]/div[1]/div/div[16]/div")
	private WebElement afterEmailEnableBroadcastTextColoumn;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div/div[3]/div[1]/div/div[17]/div/span[1]")
	private WebElement afterEmailEnableBroadcastText;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div/div[2]/div/div[18]/div")
	private WebElement braodcastEmailColoumn;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div/div[2]/div/div[19]/div")
	private WebElement broadcastTextColumn;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div[2]/div/div/div[3]/div[1]/div[1]/div[17]/div/span[2]")
	private WebElement broadcastTextCount;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div/div/div/div[2]/div/div[17]/div")
	private WebElement emailColoumnAfterTextDisable;

	@FindBy(how = How.CSS, using = "#remove")
	private WebElement removeButton;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-modal-remove-body']/p[1]")
	private WebElement removeButtonMessage;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-modal-remove-body']/p[2]")
	private WebElement removeButtonQuestion;

	@FindBy(how = How.XPATH, using = "//*[text()='Cancel']")
	private WebElement cancelButtonFromRemove;

	@FindBy(how = How.XPATH, using = "//*[text()='Confirm']")
	private WebElement confirmButtonFromRemove;

	@FindBy(how = How.CSS, using = "#sendReminder")
	private WebElement sendReminderButton;

	@FindBy(how = How.CSS, using = "#broadcastMessage")
	private WebElement broadcastMessageButton;

	@FindBy(how = How.CSS, using = "#create")
	private WebElement createButton;

	@FindBy(how = How.XPATH, using = "//*[@id=\"closeCreateanewappointment\"]//following-sibling::h2")
	private WebElement createNewAppointment;

	@FindBy(how = How.XPATH, using = "//*[@name=\"locationName\"]")
	private WebElement locationName;

	@FindBy(how = How.XPATH, using = "//*[@name=\"appointmentType\"]")
	private WebElement appointmentType;

	@FindBy(how = How.XPATH, using = "//*[@name=\"pmExternalId\"]")
	private WebElement patientId;

	@FindBy(how = How.XPATH, using = "//*[@name=\"firstName\"]")
	private WebElement firstName;

	@FindBy(how = How.XPATH, using = "//*[@name=\"middleName\"]")
	private WebElement middleName;

	@FindBy(how = How.XPATH, using = "//*[@name=\"lastName\"]")
	private WebElement lastName;

	@FindBy(how = How.XPATH, using = "//*[@name=\"dob\"]")
	private WebElement dateOfBirth;

	@FindBy(how = How.XPATH, using = "//*[@name=\"phone\"]")
	private WebElement phone;

	@FindBy(how = How.XPATH, using = "//*[@name=\"email\"]")
	private WebElement email;

	@FindBy(how = How.XPATH, using = "//*[@name=\"address\"]")
	private WebElement addressline1;

	@FindBy(how = How.XPATH, using = "//*[@name=\"address2\"]")
	private WebElement addressline2;

	@FindBy(how = How.XPATH, using = "//*[@name=\"city\"]")
	private WebElement patientCity;

	@FindBy(how = How.XPATH, using = "//*[@class=\"mf-select-dropdown\"]")
	private WebElement patientStateDropdown;

	@FindBy(how = How.XPATH, using = "//*[@name=\"zip\"]")
	private WebElement patientZip;

	@FindBy(how = How.XPATH, using = "//*[@name=\"providerName\"]")
	private WebElement providerName;

	@FindBy(how = How.XPATH, using = "//*[@name=\"copay\"]")
	private WebElement copay;

	@FindBy(how = How.XPATH, using = "//*[@name=\"balance\"]")
	private WebElement balance;

	@FindBy(how = How.XPATH, using = "//*[@name=\"primaryInsuranceName\"]")
	private WebElement primaryInsuranceName;

	@FindBy(how = How.XPATH, using = "//*[@name=\"primaryInsuranceGroupNumber\"]")
	private WebElement primaryInsuranceGroupNumber;

	@FindBy(how = How.XPATH, using = "//*[@name=\"primaryInsuranceMemberId\"]")
	private WebElement primaryInsuranceMemberId;

	@FindBy(how = How.XPATH, using = "//*[text()=\"Create appointment\"]")
	private WebElement createAppointmentButton;

	@FindBy(how = How.XPATH, using = "//*[@class=\"rt-td\"][1]")
	private WebElement selectCreatedPatient;

	@FindBy(how = How.XPATH, using = "//*[@class=\"rt-td\"]//following-sibling::div[3][@class='rt-td patient-name-cell'][1]")
	private WebElement selectApptPrecheckPatient;

	@FindBy(how = How.XPATH, using = "//*[@id=\"app\"]/div/div[3]/div[2]/div/div[1]/div/div[1]/h2")
	private WebElement precheckApptname;

	@FindBy(how = How.CSS, using = "div.insurance-name-container > div.name-bold")
	private WebElement insuranceName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"Name\"]/div[2]/div/div/p[1]")
	private WebElement precheckPatientName;

	@FindBy(how = How.XPATH, using = "//*[@id=\"Email\"]/div[2]/div/div/p[1]")
	private WebElement precheckPatientEmail;

	@FindBy(how = How.XPATH, using = "//*[@id=\"ZipCode\"]/div[2]/div/div/p[1]")
	private WebElement precheckPatientZipcode;

	@FindBy(how = How.XPATH, using = "//*[text()='Close']")
	private WebElement closeApptDetail;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-icon mf-icon__check--small mf-color__positive time-cell-action-icon']")
	private WebElement confirmTickMark;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-icon mf-icon__checkin']")
	private WebElement curbsideArrivalMark;

	@FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/div[2]/div[1]/div/div[3]/div[1]/div/div[16]/div/span[2]/span/span[1]")
	private WebElement selectPaperPlaneSymbol;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-icon mf-icon__check-v01--exact mf-color__positive']")
	private WebElement sendReminderMessageTickMark;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-color__positive']")
	private WebElement sendReminderMessage;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-icon mf-icon__checkin']")
	private WebElement checkInMark;

	@FindBy(how = How.XPATH, using = "//*[@id=\"alert\"]/div/div/span")
	private WebElement selectBannerMessage;

	@FindBy(how = How.XPATH, using = "//*[@id='minus']")
	private WebElement previousPage;

	@FindBy(how = How.XPATH, using = "//div[@class='navbar-right-arrivals-number']")
	private WebElement notificationIcon;

	@FindBy(how = How.CSS, using = "#alert > p")
	private WebElement selectBannerMesssage;

	public AppointmentsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public void enterStartTimeWithinMonth() throws InterruptedException {
		log("Select month and date within month");
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		int dd = cal.get(Calendar.DATE) + 1;
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH);

		log("Within One month  date : " + dd + "-" + mm + "-" + yyyy);
		cal.add(Calendar.MONTH, -1);
		startTime.click();

		log("Select Month");
		IHGUtil.waitForElement(driver, 10, months);
		Select selectMonth = new Select(months);
		selectMonth.selectByIndex((cal.get(Calendar.MONTH)));
		log("Month : " + (cal.get(Calendar.MONTH) + 1));
		IHGUtil.waitForElement(driver, 10, years);

		log("Select Year");
		String year = Integer.toString(yyyy);
		Select selectYear = new Select(years);
		selectYear.selectByVisibleText(year);
		log("Year : " + (cal.get(Calendar.YEAR)));

		log("Select Date");
		WebElement date = driver.findElement(By.xpath(
				"//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/div[2]/div/div[text()="
						+ "'" + dd + "'" + "]"));
		jse.executeScript("arguments[0].click();", date);
		log("Date : " + dd);
	}

	public String getPracticeName() {
		return practiceName.getText();
	}

	public String getApptPageTitle() {
		return appointmentTitle.getText();

	}

	public void patientId(String id) {
		patientIdFilter.sendKeys(id);
	}

	public void enterPatientName(String endDate) {
		patientFilter.sendKeys(endDate);
	}

	public void clickOnRefreshTab() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, refreshTab);
		jse.executeScript("arguments[0].click();", refreshTab);
		Thread.sleep(10000);
	}

	public void enterProviderName(String providerName) throws Exception {
		Actions action = new Actions(driver);
		Thread.sleep(10000);
		commonMethods.highlightElement(providerFilter);
		action.sendKeys(providerFilter, providerName).sendKeys(Keys.ENTER).build().perform();
		clickOnRefreshTab();
	}

	public void enterLocationName(String locationName) throws InterruptedException {
		Thread.sleep(10000);
		jse.executeScript("arguments[0].click();", patientFilter);
		Actions action = new Actions(driver);
		commonMethods.highlightElement(locationFilter);
		action.sendKeys(locationFilter, locationName).sendKeys(Keys.ENTER).build().perform();
		clickOnRefreshTab();
	}

	public void selectAllCheckboxes() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, allCheckboxes);
		jse.executeScript("arguments[0].click();", allCheckboxes);
	}

	public void performAction() throws Exception {
		IHGUtil.waitForElement(driver, 5, actionDropdown);
		jse.executeScript("arguments[0].click();", actionDropdown);
		jse.executeScript("arguments[0].click();", broadcastMessage);
	}

	public void sendBroadcastMessage(String messageEn, String messageEs) throws Exception {
		IHGUtil.waitForElement(driver, 10, broadcastMessageInEn);
		broadcastMessageInEn.sendKeys(messageEn);
		broadcastMessageInEs.sendKeys(messageEs);
		jse.executeScript("arguments[0].click();", confirmThisMsgCheckbox);
		IHGUtil.waitForElement(driver, 10, sendMessageButton);
		jse.executeScript("arguments[0].click();", sendMessageButton);
	}

	public boolean broadcastMessageSuccessStatus() {
		try {
			IHGUtil.waitForElement(driver, 60, broadcastMessageSuccessStatus);
			broadcastMessageSuccessStatus.isDisplayed();
			log("Banner message succuss status is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Banner message succuss status is not displayed");
			return false;
		}
	}

	public boolean broadcastMessageFailedStatus() {
		try {
			broadcastMessageFailedStatus.isDisplayed();
			log("Banner message failed status is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Banner message failed status is not displayed");
			return false;
		}
	}

	public void signOut() throws InterruptedException {
		IHGUtil.waitForElement(driver, 5, menuProfile);
		jse.executeScript("arguments[0].click();", menuProfile);
		jse.executeScript("arguments[0].click();", signOut);
	}

	public boolean reminderColumn() {
		try {
			reminders.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			log("Reminder column is not visible");
			return false;
		}
	}

	public boolean broadcastColumn() {
		try {
			broadcasts.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast column is not visible");
			return false;
		}
	}

	public void clickOnActions() {
		IHGUtil.waitForElement(driver, 5, actionDropdown);
		jse.executeScript("arguments[0].click();", actionDropdown);
	}

	public boolean broadcastMessage() {
		try {
			broadcastMessage.isDisplayed();
			log("Broadcast message action is visible");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast message action is not visible");
			return false;
		}
	}

	public boolean sendReminder() {
		try {
			sendReminder.isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			log("Send reminder action is not visible");
			return false;
		}
	}

	public String getBannerMessage() {
		IHGUtil.waitForElement(driver, 5, bannerMessage);
		return bannerMessage.getText();
	}

	public int getBroadcastMessageSuccessStatus() {
		String broadcastSuccessCount = broadcastMessageSuccessCount.getText();
		int successCount = Integer.parseInt(broadcastSuccessCount);
		return successCount;
	}

	public int getBroadcastMessageFailedStatus() {
		String broadcastFailedCount = broadcastMessageFailedCount.getText();
		int failedCount = Integer.parseInt(broadcastFailedCount);
		return failedCount;
	}

	public void enterFutureStartTime() throws InterruptedException {
		log("Select future month and date");
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		int dd = cal.get(Calendar.DATE) + 2;
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;

		log("Future date : " + dd + "-" + mm + "-" + yyyy);
		log("Today's date : " + commonMethods.getDate(cal));
		startTime.click();

		log("Select Month");
		Select selectMonth = new Select(months);
		selectMonth.selectByIndex((cal.get(Calendar.MONTH)));
		log("Month : " + (cal.get(Calendar.MONTH) + 1));
		IHGUtil.waitForElement(driver, 10, years);

		log("Select Year");
		String year = Integer.toString(yyyy);
		Select selectYear = new Select(years);
		selectYear.selectByVisibleText(year);
		log("Year : " + (cal.get(Calendar.YEAR)));

		log("Select Date");
		WebElement date = driver.findElement(By.xpath(
				"//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div/div[2]/div[2]//div[text()="
						+ "'" + dd + "'" + "]"));
		date.click();
		log("Date : " + dd);
	}

	public void enterOneMonthBackdatedStartTime() throws InterruptedException {
		log("Select date for backdated greater than one month");
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		int dd = cal.get(Calendar.DATE) + 2;
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) - 1;

		log("One Month Backdated date : " + dd + "-" + mm + "-" + yyyy);
		cal.add(Calendar.MONTH, -2);
		startTime.click();

		log("Select Month");
		Select selectMonth = new Select(months);
		selectMonth.selectByIndex((cal.get(Calendar.MONTH)));
		log("Month : " + (cal.get(Calendar.MONTH) + 1));
		Thread.sleep(3000);

		log("Select Year");
		String year = Integer.toString(yyyy);
		Select selectYear = new Select(years);
		selectYear.selectByVisibleText(year);
		log("Year : " + (cal.get(Calendar.YEAR)));

		log("Select Date");
		WebElement date = driver.findElement(By.xpath(
				"//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div/div[2]/div[2]//div[text()="
						+ "'" + dd + "'" + "]"));
		date.click();
		log("Date : " + dd);
		Thread.sleep(10000);

	}

	public void enterEndtime() throws InterruptedException {
		log("Select End date");
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		int dd = cal.get(Calendar.DATE) - 2;
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH);

		log("Satrt date : " + dd + "-" + mm + "-" + yyyy);
		cal.add(Calendar.MONTH, -1);
		endTime.click();

		log("Select Month");
		Select selectMonth = new Select(months);
		selectMonth.selectByIndex((cal.get(Calendar.MONTH)));
		log("Month : " + (cal.get(Calendar.MONTH) + 1));
		Thread.sleep(3000);

		log("Select Year");
		String year = Integer.toString(yyyy);
		Select selectYear = new Select(years);
		selectYear.selectByVisibleText(year);
		log("Year : " + (cal.get(Calendar.YEAR)));

		log("Select Date");
		WebElement date = driver.findElement(By.xpath(
				"//*[@id=\"page-content-container\"]/div/header/div[2]/div[2]/div[2]/div/div[2]/div[2]//div[text()="
						+ "'" + dd + "'" + "]"));
		date.click();
		log("Date : " + dd);
		Thread.sleep(10000);
	}

	public boolean clickOnBannerMessage() throws InterruptedException {
		IHGUtil.waitForElement(driver, 5, bannerMessage);
		jse.executeScript("arguments[0].click();", bannerMessage);
		try {
			selectedPatientBannerMessage.isDisplayed();
			log("selected Patient Banner Message is displayed");
			return true;
		} catch (Exception e) {
			log("selected Patient Banner Message is not displayed");
			return false;
		}

	}

	public String jumpToNextPage() throws InterruptedException {
		log("Scroll down");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", jumpToNextPage);
		log("Click on jump to page and move to second page");
		jse.executeScript("arguments[0].click();", jumpToNextPage);
		String pageNo = jumpToPage.getAttribute("value");
		log("Scroll up");
		jse.executeScript("arguments[0].scrollIntoView(true);", appointmentsTab);
		return pageNo;
	}

	public String getPageNo() throws InterruptedException {
		jse.executeScript("arguments[0].scrollIntoView(true);", jumpToPage);
		Thread.sleep(500);
		String pageNo = jumpToPage.getAttribute("value");
		return pageNo;
	}

	public String getBannerAfterSendBroadcast() {
		return bannerAftersendBroadcast.getText();
	}

	public void selectPatient() {
		jse.executeScript("arguments[0].click();", selectPatient);
	}

	public String broadcastBannerMessage() {
		IHGUtil.waitForElement(driver, 60, broadcastBannerMessage);
		return broadcastBannerMessage.getText();
	}

	public void selectPatientWithoutEmailAndPhone() {
		jse.executeScript("arguments[0].click();", selectPatientWithoutEmailAndPhone);
	}

	public void selectPatientWithInvalidEmailAndPhone() {
		jse.executeScript("arguments[0].click();", selectPatientWithInvalidEmailAndPhone);
	}

	public void selectPatientWithInvalidPhoneAndBlankEmail() {
		jse.executeScript("arguments[0].click();", selectPatientWithInvalidPhoneAndBlankEmail);
	}

	public boolean visibilityOfBroadcastMessage() {
		try {
			IHGUtil.waitForElement(driver, 60, broadcastBannerMessage);
			broadcastBannerMessage.isDisplayed();
			log("Broadcast Banner Message is visible");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast Banner Message is not visible");
			return false;
		}
	}

	public void clickOnBannerCrossButton() {
		IHGUtil.waitForElement(driver, 5, BannerCloseButton);
		BannerCloseButton.click();
	}

	public String getbroadcastMessageText() {
		IHGUtil.waitForElement(driver, 5, broadcastMessage);
		return broadcastMessage.getText();
	}

	public String getSendReminderText() {
		IHGUtil.waitForElement(driver, 5, sendReminder);
		return sendReminder.getText();
	}

	public boolean sendRemibderTextColumn() {
		try {
			reminderTextColumn.isDisplayed();
			log("Send remibder text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Send remibder text coloumn is is not displayed");
			return false;
		}
	}

	public boolean broadcastMessageTextColumn() {
		try {
			broadcastTextColoumn.isDisplayed();
			log("Broadcast text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast text coloumn is not displayed");
			return false;
		}
	}

	public boolean broadcastMessageEmailColumn() {
		try {
			broadcastEmailColoumn.isDisplayed();
			log("Broadcast Email coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast Email coloumn is not displayed");
			return false;
		}
	}

	public boolean visibilityBroadcastMessageTextColumn() {
		try {
			IHGUtil.waitForElement(driver, 5, broadcastMessageTextColoumn);
			broadcastMessageTextColoumn.isDisplayed();
			log("Broadcast text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast text coloumn is not displayed");
			return false;
		}
	}

	public boolean visibilitySendReminderTextColumn() {
		try {
			IHGUtil.waitForElement(driver, 5, sendReminderTextColoumn);
			sendReminderTextColoumn.isDisplayed();
			log("Broadcast text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast text coloumn is not displayed");
			return false;
		}
	}

	public boolean sendReminderEmailColumn() {
		try {
			sendReminderEmailColumn.isDisplayed();
			log("Broadcast Email coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast Email coloumn is not displayed");
			return false;
		}
	}

	public void selectPatientWithNewAppt() {
		jse.executeScript("arguments[0].click();", selectPatientWithNewAppt);
	}

	public String broadcastEmailTextCount() {
		IHGUtil.waitForElement(driver, 10, broadcastEmail);
		log("Get broadcast email count" + broadcastEmail.getText());
		return broadcastEmail.getText();
	}

	public String broadcastTextsTextCount() {
		IHGUtil.waitForElement(driver, 10, broadcastText);
		log("Get broadcast text count");
		return broadcastText.getText();
	}

	public void clickOnBroadcastEmail() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, broadcastEmail);
		broadcastEmail.click();
		log("Switch on Broadcast email screen");
		Thread.sleep(5000);
	}

	public void clickOnBroadcastText() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, broadcastText);
		broadcastText.click();
		log("Switch on Broadcast text screen");
		Thread.sleep(5000);
	}

	public String broadcastLogsText() {
		IHGUtil.waitForElement(driver, 10, broadcastLogs);
		return broadcastLogs.getText();
	}

	public String getPatientName() {
		IHGUtil.waitForElement(driver, 10, patientName);
		return patientName.getText();
	}

	public String getMessageText() {
		IHGUtil.waitForElement(driver, 10, messageText);
		return messageText.getText();
	}

	public String getTimeText() {
		IHGUtil.waitForElement(driver, 10, time);
		return time.getText();
	}

	public String getStatusText() {
		IHGUtil.waitForElement(driver, 10, statusText);
		return statusText.getText();
	}

	public void closeBroadcastEmailandTextBox() {
		IHGUtil.waitForElement(driver, 10, statusText);
		closeBroadcastEmailBox.click();
	}

	public boolean visibilityBroadcastTextColumnAfterEmailEnable() {
		try {
			IHGUtil.waitForElement(driver, 5, afterEmailEnableBroadcastText);
			afterEmailEnableBroadcastText.isDisplayed();
			log("Broadcast text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast text coloumn is not displayed");
			return false;
		}
	}

	public void clickOnBroadcastTextAfterEmailEnable() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, afterEmailEnableBroadcastText);
		afterEmailEnableBroadcastText.click();
		log("Switch on Broadcast text screen");
		Thread.sleep(5000);
	}

	public boolean visibilityBroadcasrEmailColoumn() {
		try {
			braodcastEmailColoumn.isDisplayed();
			log("Broadcast Email coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast Email coloumn is not displayed");
			return false;
		}
	}

	public void selectPatient(String patientId, String practiceId) {
		driver.navigate().refresh();
		WebElement selectPatient = driver
				.findElement(By.xpath("//*[@id='select-" + patientId + "-" + practiceId + "'" + "]"));
		selectPatient.click();
	}

	public boolean visibilityBroadcastTextColumnAfterTextDisable() {
		try {
			IHGUtil.waitForElement(driver, 5, broadcastTextColumn);
			broadcastTextColumn.isDisplayed();
			log("Broadcast text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast text coloumn is not displayed");
			return false;
		}
	}

	public boolean visibilityBroadcastTextColumn() {
		try {
			IHGUtil.waitForElement(driver, 5, broadcastTextColumn);
			broadcastTextColumn.isDisplayed();
			log("Broadcast text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast text coloumn is not displayed");
			return false;
		}
	}

	public String getBroadcastTextsCount() {
		IHGUtil.waitForElement(driver, 10, broadcastTextCount);
		log("Get broadcast text count");
		return broadcastTextCount.getText();
	}

	public boolean visibilityBroadcasrEmailColoumnAfterTextDisable() {
		try {
			emailColoumnAfterTextDisable.isDisplayed();
			log("Broadcast Email coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast Email coloumn is not displayed");
			return false;
		}
	}

	public void selectRemoveButton() {
		IHGUtil.waitForElement(driver, 10, removeButton);
		removeButton.click();
	}

	public void clickOnRemoveButton() {
		IHGUtil.waitForElement(driver, 10, removeButton);
		removeButton.click();
	}

	public String removeButtonMessage() {
		IHGUtil.waitForElement(driver, 10, removeButtonMessage);
		return removeButtonMessage.getText();
	}

	public String removeButtonQues() {
		IHGUtil.waitForElement(driver, 10, removeButtonQuestion);
		return removeButtonQuestion.getText();
	}

	public void clickOnCancel() {
		IHGUtil.waitForElement(driver, 10, cancelButtonFromRemove);
		cancelButtonFromRemove.click();
	}

	public void clickOnConfirm() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, cancelButtonFromRemove);
		confirmButtonFromRemove.click();
		Thread.sleep(10000);
	}

	public String broadcastMessageStatus() {
		IHGUtil.waitForElement(driver, 10, broadcastMessageStatus);
		return broadcastMessageStatus.getText();
	}

	public boolean visibilityOfDeletedPatient(String patientId, String practiceId) {
		try {
			WebElement selectPatient = driver
					.findElement(By.xpath("//*[@id='select-" + patientId + "-" + practiceId + "'" + "]"));
			selectPatient.isDisplayed();
			log("Patient is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Patient is not displayed");
			return false;
		}
	}

	public boolean removeButton() {
		IHGUtil.waitForElement(driver, 10, removeButton);
		if (removeButton.isEnabled()) {
			System.out.print("Remove button is enabled. ");
			return true;
		} else {
			System.out.print("Remove button is not disabled.");
			return false;
		}
	}

	public boolean sendReminderButton() {
		IHGUtil.waitForElement(driver, 10, sendReminderButton);
		if (sendReminderButton.isEnabled()) {
			System.out.print("Send Reminder button is enabled. ");
			return true;
		} else {
			System.out.print("Send Reminder button is not disabled.");
			return false;
		}
	}

	public boolean broadcastMessageButton() {
		IHGUtil.waitForElement(driver, 10, broadcastMessageButton);
		if (broadcastMessageButton.isEnabled()) {
			System.out.print("Broadcast Message button is enabled.");
			return true;
		} else {
			System.out.print("Broadcast Message button is not disabled.");
			return false;
		}
	}

	public boolean createButton() {
		IHGUtil.waitForElement(driver, 10, createButton);
		if (createButton.isEnabled()) {
			System.out.print("Create button is enabled.");
			return true;
		} else {
			System.out.print("Create button is not disabled.");
			return false;
		}
	}

	public void clickOnPatientFilter() {
		patientIdFilter.click();
	}

	public void scrollUp() {
		jse.executeScript("arguments[0].scrollIntoView(true);", appointmentsTab);
	}

	public boolean visibilityOfRemoveButton() {
		IHGUtil.waitForElement(driver, 10, removeButton);
		if (removeButton.isDisplayed()) {
			System.out.print("Remove button is displayed.");
			return true;
		} else {
			System.out.print("Remove button is not displayed.");
			return false;
		}
	}

	public boolean visibilityOfCreateButton() {
		IHGUtil.waitForElement(driver, 10, createButton);
		if (createButton.isDisplayed()) {
			System.out.print("Create button is displayed.");
			return true;
		} else {
			System.out.print("Create button is not displayed.");
			return false;
		}
	}

	public void clickOnCreate() {
		jse.executeScript("arguments[0].click();", createButton);
	}

	public String createNewPatientText() {
		return createNewAppointment.getText();
	}

	public void createNewPatient(String lName, String apptType, String pId, String fName, String mName, String lstName,
			String dob, String phoneNo, String emailId, String address1, String city, String state, String zip,
			String pName, String copayAmount, String balanceAmount, String primaryInsName, String primaryInsGroupNo,
			String primaryInsMemberId) throws InterruptedException {
		log("Add patient details");
		locationName.sendKeys(lName);
		appointmentType.sendKeys(apptType);
		patientId.sendKeys(pId);
		firstName.sendKeys(fName);
		middleName.sendKeys(mName);
		lastName.sendKeys(lstName);
		dateOfBirth.sendKeys(dob);
		phone.click();
		phone.sendKeys(phoneNo);
		email.sendKeys(emailId);
		addressline1.sendKeys(address1);
		patientCity.sendKeys(city);
		patientStateDropdown.click();
		Select select = new Select(patientStateDropdown);
		select.selectByVisibleText(state);
		patientZip.sendKeys(zip);
		providerName.sendKeys(pName);
		copay.sendKeys(copayAmount);
		balance.sendKeys(balanceAmount);
		primaryInsuranceName.sendKeys(primaryInsName);
		primaryInsuranceGroupNumber.sendKeys(primaryInsGroupNo);
		primaryInsuranceMemberId.sendKeys(primaryInsMemberId);
		createAppointmentButton.click();
		Thread.sleep(10000);
	}

	public void selectCreatedPatient() {
		selectCreatedPatient.click();
	}

	public void selectApptPrecheckPatient() throws InterruptedException {
		selectApptPrecheckPatient.click();
		Thread.sleep(5000);
	}

	public String insuranceName() {
		return insuranceName.getText();
	}

	public String precheckPatientName() {
		return precheckPatientName.getText();
	}

	public String precheckPatientEmail() {
		return precheckPatientEmail.getText();
	}

	public String precheckPatientZipcode() {
		return precheckPatientZipcode.getText();
	}

	public void closeApptDetail() {
		closeApptDetail.click();
	}

	public void filterPatientId(String id) throws InterruptedException {
		patientIdFilter.sendKeys(id);
		Thread.sleep(5000);
	}

	public boolean displayComfirmTickMark() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, confirmTickMark);
		if (confirmTickMark.isDisplayed()) {
			log("Confirm Mark is displayed.");
			return true;
		} else {
			log("confirm Mark is not displayed.");
			return false;
		}
	}

	public boolean displaycurbsideArrivalMark() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, curbsideArrivalMark);
		if (curbsideArrivalMark.isDisplayed()) {
			log("Curbside Arrival Mark is displayed.");
			return true;
		} else {
			log("Curbside Arrival Mark is not displayed.");
			return false;
		}
	}

	public boolean displayCheckInMark() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, checkInMark);
		if (checkInMark.isDisplayed()) {
			log("Check In Mark is displayed.");
			return true;
		} else {
			log("Check In Mark is not displayed.");
			return false;
		}
	}

	public void clickOnSendReminder() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, sendReminder);
		jse.executeScript("arguments[0].click();", sendReminder);
	}

	public boolean displayPaperPlaneSymbol() {
		IHGUtil.waitForElement(driver, 10, selectPaperPlaneSymbol);
		if (selectPaperPlaneSymbol.isDisplayed()) {
			log("Paper Plane Symbol is displayed.");
			return true;
		} else {
			log("Paper Plane Symbol is not displayed.");
			return false;
		}
	}

	public String getReminderMessage() {
		IHGUtil.waitForElement(driver, 10, sendReminderMessage);
		return sendReminderMessage.getText();
	}

	public boolean displaySendReminderMessageTickMark() {
		IHGUtil.waitForElement(driver, 10, sendReminderMessageTickMark);
		if (sendReminderMessageTickMark.isDisplayed()) {
			log("Send Reminder Message Tick Mark is displayed.");
			return true;
		} else {
			log("Send Reminder Message Tick Mark is not displayed.");
			return false;
		}
	}

	public String getBroadcastEmailCountForSelectedPatient(String patientId) {
		WebElement getPatient = driver
				.findElement(By.xpath("//*[text()=" + "'" + patientId + "'" + "]/following::div[22]/div/span[2]"));
		IHGUtil.waitForElement(driver, 10, getPatient);
		log("Get broadcast email count" + getPatient.getText());
		return getPatient.getText();
	}

	public String getBroadcastTextCountForSelectedPatient(String patientId) {
		WebElement getPatient = driver
				.findElement(By.xpath("//*[text()=" + "'" + patientId + "'" + "]/following::div[24]/div/span[2]"));
		IHGUtil.waitForElement(driver, 10, getPatient);
		log("Get broadcast text count" + getPatient.getText());
		return getPatient.getText();
	}

	public void selectPatients(String patientId, String practiceId) {
		WebElement selectPatient = driver
				.findElement(By.xpath("//*[@id='select-" + patientId + "-" + practiceId + "'" + "]"));
		selectPatient.click();
	}

	public String getSelectedBannerMessage() {
		IHGUtil.waitForElement(driver, 10, selectBannerMessage);
		log("Get broadcast email count" + selectBannerMessage.getText());
		return selectBannerMessage.getText();
	}

	public String jumbToNextPage() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, jumpToNextPage);
		jse.executeScript("arguments[0].click();", jumpToNextPage);
		Thread.sleep(10000);
		String pageNo = jumpToPage.getAttribute("value");
		return pageNo;
	}

	public String jumbToPreviousPage() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, previousPage);
		jse.executeScript("arguments[0].click();", previousPage);
		Thread.sleep(10000);
		String pageNo = jumpToPage.getAttribute("value");
		return pageNo;
	}

	public String getBroadcastMessageButtonText() {
		IHGUtil.waitForElement(driver, 10, broadcastMessageButton);
		return broadcastMessageButton.getText();
	}

	public void clickOnPatientNameFilter() {
		jse.executeScript("arguments[0].click();", patientFilter);
	}

	public String getSendReminderButtonText() {
		IHGUtil.waitForElement(driver, 10, sendReminderButton);
		return sendReminderButton.getText();
	}

	public String getRemoveButtonText() {
		IHGUtil.waitForElement(driver, 10, removeButton);
		return removeButton.getText();
	}

	public boolean allCheckboxes() {
		IHGUtil.waitForElement(driver, 10, allCheckboxes);
		if (allCheckboxes.isSelected()) {
			log("Create button is enabled.");
			return true;
		} else {
			log("Create button is not disabled.");
			return false;
		}
	}

	public void clickOnNotifIcon() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(10000);
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, notificationIcon);
		jse.executeScript("arguments[0].click();", notificationIcon);
	}

	public boolean visibilityBannerMessage() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectBannerMesssage);
		boolean visibility = false;
		visibility = selectBannerMesssage.isDisplayed();
		return visibility;
	}

	public boolean visibilityOfBannerMessageBaseOnFilter() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, bannerMessage);
		boolean visibility = false;
		visibility = bannerMessage.isDisplayed();
		return visibility;
	}
}
