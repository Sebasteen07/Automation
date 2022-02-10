package com.medfusion.product.object.maps.appt.precheck.page.CurbsideCheckIn;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class CurbsideCheckInPage extends BasePageObject {

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Curbside Check-ins')]")
	private WebElement curbsideTitle;

	@FindBy(how = How.XPATH, using = "//div[text()='All locations']")
	private WebElement locationFilter;

	@FindBy(how = How.XPATH, using = "//input[@id='filter-start-time']")
	private WebElement startTimeField;

	@FindBy(how = How.XPATH, using = "//div/label[text()='Start time']")
	private WebElement startTimeLabel;

	@FindBy(how = How.XPATH, using = "//input[@id='filter-end-time']")
	private WebElement endTimeField;

	@FindBy(how = How.XPATH, using = "//div/label[text()='End time']")
	private WebElement endTimeLabel;

	@FindBy(how = How.XPATH, using = "//input[@id='filter-patient-id']")
	private WebElement patientIdField;

	@FindBy(how = How.XPATH, using = "//div/label[text()='Patient Id']")
	private WebElement patientIdLabel;

	@FindBy(how = How.CSS, using = "div.mf-appointments-filters > div:nth-child(6)")
	private WebElement patientNameField;

	@FindBy(how = How.XPATH, using = "//div/label[text()='Patient name']")
	private WebElement patientNameLabel;

	@FindBy(how = How.CSS, using = "div.mf-appointments-filters > div:nth-child(7)")
	private WebElement providerField;

	@FindBy(how = How.XPATH, using = "//div/label[text()='Provider']")
	private WebElement providerLabel;

	@FindBy(how = How.XPATH, using = "//button[@id='filter-reset-button']/span")
	private WebElement clearFilterButton;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Check-In')]")
	private WebElement checkInButton;

	@FindBy(how = How.XPATH, using = "//input[@id='select-all']")
	private WebElement selectAllCheckbox;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Appt time')]")
	private WebElement apptTime;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Wait time')]")
	private WebElement waitTime;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Messages')]")
	private WebElement msgs;

	@FindBy(how = How.XPATH, using = "//div[text()='Send message']")
	private WebElement sendMsgText;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'History')]")
	private WebElement history;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Patient name')]")
	private WebElement patientName;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Patient ID')]")
	private WebElement patientId;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Provider')]")
	private WebElement provider;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Location')]")
	private WebElement location;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Email')]")
	private WebElement email;

	@FindBy(how = How.XPATH, using = "//b[contains(text(),'Phone')]")
	private WebElement phone;

	@FindBy(how = How.XPATH, using = "//div[@class='navbar-right-arrivals-number']")
	private WebElement notifIcon;

	@FindBy(how = How.XPATH, using = "//select[@name='messages']")
	private WebElement sendMsgDropdown;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Send')]")
	private WebElement sendButton;

	@FindBy(how = How.XPATH, using = "//div[@class='rt-td history-cell patient-name-cell']/div/img")
	private WebElement historyButton;

	@FindBy(how = How.XPATH, using = "//h2[text()='Curbside notification logs']")
	private WebElement curbsideNotifLogPopup;
	
	@FindBy(how = How.XPATH, using = "//h2[text()='Curbside notification logs']/span")
	private WebElement iconOnCurbsideNotifLogPopup;
	
	@FindBy(how = How.XPATH, using = "//h5[text()='AppScheduler One']")
	private WebElement patientNameOnCurbsideNotifLogPopup;
	
	@FindBy(how = How.XPATH, using = "//div[@class='history-time-cell-date']")
	private WebElement dateOnCurbsideNotifLogPopup;
	
	@FindBy(how = How.XPATH, using = "//div[@class='history-time-cell-time']")
	private WebElement timeOnCurbsideNotifLogPopup;
	
	@FindBy(how = How.XPATH, using = "//span[text()='wait in the parking lot until we send you a message to come in.']")
	private WebElement messageOnCurbsideNotifLogPopup;
	
	@FindBy(how = How.XPATH, using = "//div[text()='Email/Text']")
	private WebElement emailAndTextOnCurbsideNotifLogPopup;
	
	@FindBy(how = How.XPATH, using = "//button[@class='close']")
	private WebElement closeCurbsideNotifLogPopup;
	
	@FindBy(how = How.XPATH, using = "//select[@class='react-datepicker__month-select']")
	private WebElement months;
	
	@FindBy(how = How.XPATH, using = "//*[@class='react-datepicker__year-select']")
	private WebElement years;
	
	@FindBy(how = How.XPATH, using = "(//input[@id='select-all'])")
	private WebElement selectAllCheckinAppt;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "(//input[@type='checkbox'])") })
	public List<WebElement> allAppointment;
	 
	@FindBy(how = How.XPATH, using = "//*[@class=\"mf-arrivals-input-text\"]")
	public WebElement otherMessageTextbox;
	
	@FindBy(how = How.XPATH, using = "//input[@class='mf-arrivals-input-text']")
	public WebElement sendButtonForOtherTextMsg;
	
	@FindBy(how=How.XPATH, using ="//a[contains(text(),'Curbside Check-in')]")
	private WebElement curbsideCheckinPage;
	
	@FindBy(how=How.XPATH, using ="(//div[@class=' css-tlfecz-indicatorContainer'])[1]")
	private WebElement curbsideCheckinLocationDropDown;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text() ,'River Oaks Main')]")
	private WebElement selectLocationinDropDown;
	
	@FindBy(how=How.XPATH, using ="//div[@class='navbar-right-arrivals-number']")
	private WebElement notificationCount;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(), 'River Oaks Main')]")
	private WebElement selectLocationL1inDropDown;
	
	public CurbsideCheckInPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public String getCurbsideTitle() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, curbsideTitle);
		return curbsideTitle.getText();
	}

	public String visibiltyOfLocationFilter() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, locationFilter);
		boolean locFilter = locationFilter.isDisplayed();
		if (locFilter == true)
			log(" Location Field Label is displayed");
		else {
			log("Location Field Label is not displayed");
		}
		return locationFilter.getText();
	}

	public boolean visibiltyOfStartTimeFilter() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, startTimeField);
		if (startTimeField.isDisplayed()) {
			log(" Start Time Field is displayed");
			return true;
		} else
			log("Start Time Field is not displayed");
		return false;
	}

	public String getStartTimeFilterLabel() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, startTimeLabel);
		log("Start Time Label is displayed");
		return startTimeLabel.getText();
	}

	public boolean visibiltyOfEndTimeFilter() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, endTimeField);
		if (endTimeField.isDisplayed()) {
			log(" End Time Field is displayed");
			return true;
		} else {
			log("End Time Field is not displayed");
			return false;
		}
	}

	public String getEndTimeFilterLabel() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, endTimeLabel);
		log("End Time Label is displayed");
		return endTimeLabel.getText();
	}

	public boolean visibiltyOfPatientIdFilter() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, patientIdField);
		if (patientIdField.isDisplayed()) {
			log(" Patient Id Field is displayed");
			return true;
		} else {
			log("patient Id Field is not displayed");
			return false;
		}
	}

	public String getPatientIdLabel() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, patientIdLabel);
		log("PatientId Label is displayed");
		return patientIdLabel.getText();
	}

	public boolean visibiltyOfPatientNameFilter() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, patientNameField);
		if (patientNameField.isDisplayed()) {
			log(" Patient name Field is displayed");
			return true;
		} else {
			log("patient name Field is not displayed");
			return false;
		}
	}

	public String getPatientNameLabel() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, patientNameLabel);
		log("Patient Name Label is displayed");
		return patientNameLabel.getText();
	}

	public boolean visibiltyOfProviderFilter() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, providerField);
		if (providerField.isDisplayed()) {
			log(" Provider Field is displayed");
			return true;
		} else {
			log("provider Field is not displayed");
			return false;
		}
	}

	public String getProviderLabel() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, providerLabel);
		log("Provider Label is displayed");
		return providerLabel.getText();
	}

	public boolean visibilityOfClearFilterButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, clearFilterButton);
		if (clearFilterButton.isDisplayed()) {
			log("Clear Filter Button  is displayed");
			return true;
		} else {
			log("Clear Filter Button  is  not displayed");
			return false;
		}
	}

	public boolean visibilityOfCheckInButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, checkInButton);
		if (checkInButton.isDisplayed()) {
			log(" Check-In button  is displayed");
			return true;
		} else {
			log("Check-In button is not displayed");
			return false;
		}
	}

	public boolean visibilityOfSelectAllCheckbox() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, selectAllCheckbox);
		if (selectAllCheckbox.isDisplayed()) {
			log(" Select All Checkbox  is displayed");
			return true;
		} else {
			log(" Select All Checkbox  is not selected");
			return false;
		}
	}

	public String getApptTimeText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, apptTime);
		return apptTime.getText();
	}

	public String getWaitTimeText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, waitTime);
		return waitTime.getText();
	}

	public String getMessagesText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, msgs);
		return msgs.getText();
	}

	public String getSendMessagesText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, sendMsgText);
		return sendMsgText.getText();
	}

	public String getHistoryText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, history);
		return history.getText();
	}

	public String getPatientNameText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, patientName);
		return patientName.getText();
	}

	public String getPatientIdText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, patientId);
		return patientId.getText();
	}

	public String getProviderText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, provider);
		return provider.getText();
	}

	public String getLocationText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, location);
		return location.getText();
	}

	public String getEmailText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, email);
		return email.getText();
	}

	public String getPhoneText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, phone);
		return phone.getText();
	}

	public boolean visibilityOfNotifIcon() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, notifIcon);
		if (notifIcon.isDisplayed()) {
			log("Notification icon is Visible");
			return true;
		} else {
			log("Notification icon is not Visible");
			return true;
		}
	}

	public String getNotificationCount() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, notifIcon);
		return notifIcon.getText();
	}

	public void selectPatient(String patientId, String practiceId) {
		IHGUtil.PrintMethodName();
		WebElement selectPatient = driver
				.findElement(By.xpath("//*[@id='select-" + patientId + "-" + practiceId + "'" + "]"));
		IHGUtil.waitForElement(driver, 10, selectPatient);
		selectPatient.click();
	}

	public void clickOnSelectedPatientDropdown(String patientId) throws InterruptedException {
		IHGUtil.PrintMethodName();
		WebElement selectPatient = driver.findElement(By.xpath("//select[@id='" + patientId + "']"));
		IHGUtil.waitForElement(driver, 10, selectPatient);
		selectPatient.click();
		Thread.sleep(5000);
	}

	public String visibilityOfDefaultMessage(String patientId) {
		IHGUtil.PrintMethodName();
		WebElement selectPatient = driver
				.findElement(By.xpath("//select[@id='" + patientId + "']/option[text()='Select Message']"));
		IHGUtil.waitForElement(driver, 10, selectPatient);
		return selectPatient.getText();
	}

	public String visibilityOfParkingLotMsgInSendMsg(String patientId) {
		IHGUtil.PrintMethodName();
		WebElement selectPatient = driver.findElement(By.xpath("//select[@id='" + patientId
				+ "']/option[text()='Wait in the parking lot until we send you a message to come in.']"));
		IHGUtil.waitForElement(driver, 10, selectPatient);
		return selectPatient.getText();
	}

	public String visibilityOfInsuranceInstMsg(String patientId) {
		IHGUtil.PrintMethodName();
		WebElement selectPatient = driver.findElement(By.xpath("//select[@id='" + patientId
				+ "']/option[text()='We will call you shortly to collect your insurance information.']"));
		IHGUtil.waitForElement(driver, 10, selectPatient);
		return selectPatient.getText();
	}

	public String visibilityOfComeInOfficeMsg(String patientId) {
		IHGUtil.PrintMethodName();
		WebElement selectPatient = driver
				.findElement(By.xpath("//select[@id='" + patientId + "']/option[text()='Come in the office now.']"));
		IHGUtil.waitForElement(driver, 10, selectPatient);
		return selectPatient.getText();
	}

	public String visibilityOfOtherMsg(String patientId) {
		IHGUtil.PrintMethodName();
		WebElement selectPatient = driver
				.findElement(By.xpath("//select[@id='" + patientId + "']/option[text()='Other']"));
		IHGUtil.waitForElement(driver, 10, selectPatient);
		return selectPatient.getText();
	}

	public boolean visibilityOfSendMessageDropdown() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, sendMsgDropdown);
		if (sendMsgDropdown.isDisplayed()) {
			log(" Send Message dropdown is displayed");
			return true;
		} else {
			log("Send Message dropdown is not displayed");
			return false;
		}
	}

	public boolean visibilityOfSendButton(String patientId) {
		IHGUtil.PrintMethodName();
		WebElement sendButton = driver
				.findElement(By.xpath("//select[@id='" + patientId + "']/following-sibling::button"));
		IHGUtil.waitForElement(driver, 5, sendButton);
		if (sendButton.isDisplayed()) {
			log(" Send Button is displayed");
			return true;
		} else {
			log("Send Button is not displayed");
			return false;
		}
	}

	public boolean visibilityOfHistory(String patientId) {
		IHGUtil.PrintMethodName();
		WebElement historyButton = driver
				.findElement(By.xpath("//select[@id='" + patientId + "']/following::div[2]/div/img"));
		IHGUtil.waitForElement(driver, 5, historyButton);
		if (historyButton.isDisplayed()) {
			log("History link is displayed");
			return true;
		} else {
			log("History link is not displayed");
			return false;
		}
	}

	public boolean visibilityOfCheckinButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, checkInButton);
		if (checkInButton.isDisplayed()) {
			log(" Check-In Button is displayed");
			return true;
		} else {
			log("Check-In Button is not displayed");
			return false;
		}
	}

	public String sendMsgFromDropdown(String patientId) throws InterruptedException {
		IHGUtil.PrintMethodName();
		WebElement message = driver.findElement(By.xpath("//select[@id='"+patientId+"']"));
		IHGUtil.waitForElement(driver, 10, message);
		log("Select message from drodown");
		Select selectMessage = new Select(message);
		selectMessage.selectByIndex(1);
		log("Click on send button");
		driver.findElement(By.xpath("//select[@id='" + patientId + "']/following-sibling::button")).click();
		Thread.sleep(5000);
		return message.getText();
	}
	
	public String getLastSendMessage(String patientId) throws InterruptedException {
		IHGUtil.PrintMethodName();
		WebElement lastSendMessage= driver.findElement(By.xpath("//select[@id='" + patientId + "']/following-sibling::div/p"));
		IHGUtil.waitForElement(driver, 10, lastSendMessage);
		Thread.sleep(5000);
		return lastSendMessage.getText();
	}
	
	
	public void clickOnHistoryLink(String patientId) throws InterruptedException {
		IHGUtil.PrintMethodName();
		WebElement historyButton = driver
				.findElement(By.xpath("//select[@id='" + patientId + "']/following::div[2]/div/img"));
		IHGUtil.waitForElement(driver, 5, historyButton);
		historyButton.click();
		Thread.sleep(5000);
	}
	
	public boolean visibilityOfCurbsideNotificationLogsPopup() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, curbsideNotifLogPopup);
		if (curbsideNotifLogPopup.isDisplayed()) {
			log("Curbside Notification Log Popup is displayed");
			return true;
		} else {
			log("Curbside Notification Log Popup is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfIconOnCurbsideNotificationLogsPopup(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, iconOnCurbsideNotifLogPopup);
		if (iconOnCurbsideNotifLogPopup.isDisplayed()) {
			log("Icon on Curbside Notification Log Popup is displayed");
			return true;
		} else {
			log("Icon on  Curbside Notification Log Popup is not displayed");
			return false;
		}
	}
	
	public String patientNameOnOnCurbsideNotificationLogsPopup(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, patientNameOnCurbsideNotifLogPopup);
		if (patientNameOnCurbsideNotifLogPopup.isDisplayed()) {
			log("patient Name on Curbside Notification Log Popup is displayed");
			return patientNameOnCurbsideNotifLogPopup.getText();
		} else {
			log("patient Name on  Curbside Notification Log Popup is not displayed");
			return null;
		}
	}
	
	public boolean visibilityOfDateOnCurbsideNotifLogPopup(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, dateOnCurbsideNotifLogPopup);
		if (dateOnCurbsideNotifLogPopup.isDisplayed()) {
			log("Date: "+dateOnCurbsideNotifLogPopup.getText());
			log("Date on Curbside Notification Log Popup is displayed");
			return true;
		} else {
			log("Date on  Curbside Notification Log Popup is not displayed");
			return false;
		}
	}

	public boolean visibilityOfTimeOnCurbsideNotifLogPopup(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, timeOnCurbsideNotifLogPopup);
		if (timeOnCurbsideNotifLogPopup.isDisplayed()) {
			log("Time: "+timeOnCurbsideNotifLogPopup.getText());
			log("Time on Curbside Notification Log Popup is displayed");
			return true;
		} else {
			log("Time on  Curbside Notification Log Popup is not displayed");
			return false;
		}
	}
	
	public String getMessageFromCurbsideNotifLogPopup(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, messageOnCurbsideNotifLogPopup);
		if (messageOnCurbsideNotifLogPopup.isDisplayed()) {
			log("Message on Curbside Notification Log Popup is displayed");
			return messageOnCurbsideNotifLogPopup.getText();
		} else {
			log("Message on  Curbside Notification Log Popup is not displayed");
			return null;
		}
	}

	public boolean visibilityOfEmailAndTextOnCurbsideNotifLogPopup(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, emailAndTextOnCurbsideNotifLogPopup);
		if (emailAndTextOnCurbsideNotifLogPopup.isDisplayed()) {
			log("Email/Text "+emailAndTextOnCurbsideNotifLogPopup.getText());
			log("Email/Text on Curbside Notification Log Popup is displayed");
			return true;
		} else {
			log("Email/Text on  Curbside Notification Log Popup is not displayed");
			return false;
		}
	}
	
	public void closeCurbsideNotifLogPopup(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, closeCurbsideNotifLogPopup);
		closeCurbsideNotifLogPopup.click();
		}
	
	public String selectOneDayBeforeDate(String currentYear,String time) throws InterruptedException {
		IHGUtil.PrintMethodName();
		log("Select one day before date");
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		int dd = cal.get(Calendar.DATE) - 1;
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH)+1;

		log("One day before date : " + dd + "-" + mm + "-" + yyyy);
		endTimeField.click();

		log("Select Month");
		Select selectMonth = new Select(months);
		selectMonth.selectByIndex((cal.get(Calendar.MONTH)));
		Thread.sleep(3000);

		log("Select Year");
		String year = Integer.toString(yyyy);
		Select selectYear = new Select(years);
		selectYear.selectByVisibleText(year);
		log("Year : " + (cal.get(Calendar.YEAR)));

		log("Select Date");
		WebElement date = driver.findElement(By.xpath(
				"(//*[@id=\"page-content-container\"]/div/header/div[2]/div[4]/div[2]/div[2]/div/div/div[2]/div[2]//div[text()="
						+ "'" + dd + "'" + "])[1]"));
		log("Date : " + dd);
		date.click();
		Thread.sleep(10000);
		return mm+"/"+dd+"/"+currentYear+" "+time;
	}
	
	public String getCurrentEndDateAndTime() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, endTimeField);
		return endTimeField.getAttribute("value");
	}
	
	public String getDesectedEndDateColor() throws InterruptedException {
		IHGUtil.PrintMethodName();
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));

		int dd = cal.get(Calendar.DATE) - 1;
		WebElement date = driver.findElement(By.xpath(
				"(//*[@id=\"page-content-container\"]/div/header/div[2]/div[4]/div[2]/div[2]/div/div/div[2]/div[2]//div[text()="
						+ "'" + dd + "'" + "])[1]"));
		String color = date.getCssValue("color");
	    String deselectedDateColor = Color.fromString(color).asHex();
	    log("Deselected date color  "+deselectedDateColor);
        return deselectedDateColor;
	    
	}
	
	public int countOfCurbsideCheckinPatient() {
		IHGUtil.PrintMethodName();
		int patientSize = allAppointment.size();
		log("Size of checkin Appointments " + patientSize);
		return patientSize - 1;
	}

	public void selectTwoPatient() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(10000);
		IHGUtil.PrintMethodName();
		int patientSize = allAppointment.size();
		for (int i = 1; i <= patientSize - 9; i++) {
			WebElement twoPatient = allAppointment.get(i);
			twoPatient.click();
		}
	}
	
	public void selectAllAppointment() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, selectAllCheckinAppt);
		selectAllCheckinAppt.click();
	}
	
	public void deselectAllAppointment() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, selectAllCheckinAppt);
		selectAllCheckinAppt.click();
	}
	
	public void selectMultiplePatients(int selectPatients) throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(10000);
		IHGUtil.PrintMethodName();
		int patientSize = allAppointment.size();
		log("Total patient size: "+patientSize);
		for (int i = 1; i <= patientSize - selectPatients; i++) {
			WebElement patients = allAppointment.get(i);
			patients.click();
		}
	}
	
	public boolean getVisibilityOfMultiplePatient(int deselectPatients) throws InterruptedException {
		IHGUtil.PrintMethodName();
		int patientSize = allAppointment.size();
		boolean visibility = false;
		log("Total patient size: "+patientSize);
		for (int i = 1; i <= patientSize - deselectPatients; i++) {
			WebElement patients = allAppointment.get(i);
			visibility = patients.isSelected();
		}
		return visibility;
	}
	
	public boolean getVisibilityOfAllCheckbox() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectAllCheckinAppt);
		boolean visibility = false;
		visibility = selectAllCheckinAppt.isSelected();
		return visibility;
	}

	
	public void selectMessageFromDropdown(String patientId,String message) {
		IHGUtil.PrintMethodName();
		WebElement selectedPatientDropdown = driver.findElement(By.xpath("//select[@id='" + patientId + "']"));
		Select selectedDropdown = new Select(selectedPatientDropdown);
		selectedDropdown.selectByVisibleText(message);
	}
	
	public void clickOnSendButtonOfSelectedPatient(String patientId) throws InterruptedException {
		IHGUtil.PrintMethodName();
		WebElement selectedPatientSendButton= driver.findElement(By.xpath("//select[@id='"+ patientId +"']/following-sibling::button"));
		selectedPatientSendButton.click();
		Thread.sleep(5000);
	}
	
	public String getSentMessageSelectedPatient(String patientId){
		IHGUtil.PrintMethodName();
		WebElement getSentMessage= driver.findElement(By.xpath("//select[@id='"+ patientId +"']/following-sibling::div/p"));
		return getSentMessage.getText();
	}
	
	public void clickOnCheckInButton(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, checkInButton);
		jse.executeScript("arguments[0].click();", checkInButton);
	}
	
	public void selectOtherOptionFromDropdown(String patientId,String other) throws InterruptedException {
		IHGUtil.PrintMethodName();
		WebElement message = driver.findElement(By.xpath("//select[@id='"+patientId+"']"));
		IHGUtil.waitForElement(driver, 10, message);
		log("Select message from drodown");
		Select selectMessage = new Select(message);
		selectMessage.selectByVisibleText(other);
	}
		public String sendCustomizedMessage(String patientId,String CustomizedMsg) throws InterruptedException {
		log("Click on send button");
		otherMessageTextbox.sendKeys(CustomizedMsg);
		driver.findElement(By.xpath("//select[@id='" + patientId + "']/following-sibling::button[text()='Send']")).click();
		Thread.sleep(5000);
		WebElement lastSendMessage= driver.findElement(By.xpath("//select[@id='" + patientId + "']/following-sibling::div/p"));
		return lastSendMessage.getText();
	}
		
		public void clickOncurbsideCheckinPage() {
			IHGUtil.waitForElement(driver, 5, curbsideCheckinPage);
			curbsideCheckinPage.click();
		}
		public void selectLocationinDropDown() {
			IHGUtil.waitForElement(driver, 5, selectLocationinDropDown);
			selectLocationinDropDown.click();
		}
		public void clickOncurbsideCheckinLocationDropDown() {
			IHGUtil.waitForElement(driver, 5, curbsideCheckinLocationDropDown);
			curbsideCheckinLocationDropDown.click();
		}
		
		public boolean visibiliyOfnotificationCount() {
			IHGUtil.waitForElement(driver, 5, notificationCount);
			notificationCount.isDisplayed();
			if (notificationCount.isDisplayed()) {
				log("Notification count Icon is visible");
				return true;
			} else {
				log("Notification count Icon is not visible");
				return false;
			}
		}
		
		public void selectLocationL1inDropDown() {
			IHGUtil.waitForElement(driver, 5, selectLocationL1inDropDown);
			selectLocationL1inDropDown.click();
			
		}
}
