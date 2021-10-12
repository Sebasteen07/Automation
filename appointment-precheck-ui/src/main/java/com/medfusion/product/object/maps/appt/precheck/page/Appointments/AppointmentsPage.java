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
				"//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div/div[2]/div[2]//div[text()="
						+ "'" + dd + "'" + "]"));
		date.click();
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
		actionDropdown.click();
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
		jse.executeScript("arguments[0].scrollIntoView(true);", jumpToNextPage);
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

	public boolean sendRemibderTextColoumn() {
		try {
			reminderTextColumn.isDisplayed();
			log("Send remibder text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Send remibder text coloumn is is not displayed");
			return false;
		}
	}
	public boolean broadcastMessageTextColoumn() {
		try {
			broadcastTextColoumn.isDisplayed();
			log("Broadcast text coloumn is displayed");
			return true;
		} catch (NoSuchElementException e) {
			log("Broadcast text coloumn is not displayed");
			return false;
		}
	}
}
