// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.page.Appointments;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.appt.precheck.pojo.Appointment;
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

	@FindBy(how = How.XPATH, using = "//*[@name='city']")
	private WebElement patientCity;

	@FindBy(how = How.XPATH, using = "//*[@class= 'mf-select-dropdown']")
	private WebElement patientStateDropdown;

	@FindBy(how = How.XPATH, using = "//input[@name='zip']")
	private WebElement patientZip;

	@FindBy(how = How.XPATH, using = "//input[@name='providerName']")
	private WebElement providerName;

	@FindBy(how = How.XPATH, using = "//*[@name='copay']")
	private WebElement copay;

	@FindBy(how = How.XPATH, using = "//*[@name='balance']")
	private WebElement balance;

	@FindBy(how = How.XPATH, using = "//*[@name='primaryInsuranceName']")
	private WebElement primaryInsuranceName;

	@FindBy(how = How.XPATH, using = "//*[@name='primaryInsuranceGroupNumber']")
	private WebElement primaryInsuranceGroupNumber;

	@FindBy(how = How.XPATH, using = "//*[@name='primaryInsuranceMemberId']")
	private WebElement primaryInsuranceMemberId;

	@FindBy(how = How.XPATH, using = "//*[text()='Create appointment']")
	private WebElement createAppointmentButton;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-td'][1]")
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

	@FindBy(how = How.XPATH, using = "//h2[@class='modal-title']")
	private WebElement broadcastMessagePopup;

	@FindBy(how = How.XPATH, using = "//div[@class='appintments-broadcast-heading']")
	private WebElement broadcastMsgPopupInstruction;

	@FindBy(how = How.XPATH, using = "(//div[@class='appointment-broadcast-modal-body'])[1]")
	private WebElement broadcastMsgPopupSartTime;

	@FindBy(how = How.XPATH, using = "(//div[@class='appointment-broadcast-modal-body'])[2]")
	private WebElement broadcastMsgPopupEndTime;

	@FindBy(how = How.XPATH, using = "(//div[@class='appointment-broadcast-modal-body'])[3]")
	private WebElement broadcastMsgPopupPrivider;

	@FindBy(how = How.XPATH, using = "(//div[@class='appointment-broadcast-modal-body'])[4]")
	private WebElement broadcastMsgPopupLocation;

	@FindBy(how = How.XPATH, using = "//label[text()='Broadcast Message (English)']")
	private WebElement broadcastMsgPopupEnglishLabel;

	@FindBy(how = How.XPATH, using = "//label[text()='Broadcast Message (Spanish)']")
	private WebElement broadcastMsgPopupSpanishLabel;

	@FindBy(how = How.XPATH, using = "//div[@class='mf-notification-checkbox__container']")
	private WebElement confirmThisMsgLabel;

	@FindBy(how = How.XPATH, using = "//div[@class='mf-notification-checkbox__container']/span")
	private WebElement confirmThisMsgCheckboxLabel;

	@FindBy(how = How.XPATH, using = "//button[text()='Close']")
	private WebElement broadcastMsgPopupCloseButton;

	@FindBy(how = How.XPATH, using = "//div[@class='appointment-broadcast-modal-buttons']/button[2]")
	private WebElement broadcastMsgPopupSendButton;

	@FindBy(how = How.XPATH, using = "//span[@class='mf-icon mf-icon__x']")
	private WebElement broadcastMsgPopupCrossButton;

	@FindBy(how = How.XPATH, using = "//label[text()='Broadcast Message (English)']/following-sibling::label[1]")
	private WebElement englishIncrDecrChar;

	@FindBy(how = How.XPATH, using = "//label[text()='Broadcast Message (Spanish)']/following-sibling::label[1]")
	private WebElement spanishIncrDecrChar;

	@FindBy(how = How.XPATH, using = "//p[@class='ribbon']")
	private WebElement selectedPatientBanner;

	@FindBy(how = How.XPATH, using = "(//a[@class='close'])[1]")
	private WebElement bannerCloseButton;

	@FindBy(how = How.XPATH, using = "//div[@class='tooltiptext']")
	private WebElement broadcastEmailMsg;
	
	@FindBy(how = How.ID, using = "filter-start-time")
	private WebElement filterStartTime;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div[2]/div/div/div[3]/div[2]/div/ul/li[6]//following-sibling::li")})
	private List<WebElement> startDateTime;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id=\"page-content-container\"]/div/header/div[2]/div[2]/div[2]/div[2]/div/div/div[3]/div[2]/div/ul/li")})
	private List<WebElement> endDateTime;
	
	@FindBy(how = How.XPATH, using = "(//input[@type='checkbox'])[2]")
	private WebElement selectFirstPatient;
	
    @FindBy(how = How.CSS, using = "#alert > p>span>a")
	private WebElement bannerMesssageBaseOnAppointment;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "(//input[@type='checkbox'])") })
	public List<WebElement> allAppointment;
	
	@FindBy(how = How.XPATH, using = "//span[@class='title-text']")
	private WebElement reminderLogsPopupTitle;
	
	@FindBy(how = How.XPATH, using = "//tbody/tr/td[2]")
	private WebElement priorTypeEntry;
	
	@FindBy(how = How.XPATH, using = "//button[@id='closeReminderStatusesModal']")
	private WebElement reminderLogsCloseBtn;
	
	@FindBy(how = How.XPATH, using = "//h2[@class='modal-title']")
	private WebElement broadcastMsgLogs;
	
	@FindBy(how = How.XPATH, using = "//span[text()='All selected appointments have successfully been removed.']")
	private WebElement removeBannerMessage;
	
	@FindBy(how = How.XPATH, using = "//span[@class='-pageInfo']")
	private WebElement pageNo;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-tlfecz-indicatorContainer'])[3]")
	private WebElement locationDropdown;
	
	@FindBy(how = How.XPATH, using = "//div[text()='River Oaks Main']")
	private WebElement locationFilterSelected;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-tlfecz-indicatorContainer'])[2]")
	private WebElement providerDropdown;
	
	@FindBy(how = How.XPATH, using = "//div[text()='Brown, Jennifer']")
	private WebElement providerFilterSelected;

	@FindBy(how = How.XPATH, using = "//*[@class='rt-td history-medium-cell status-cell']")
	private WebElement messageStatus;
	
	@FindBy(how = How.XPATH, using = "(//td[text()='Failed'])[1]")
	private WebElement priorDayReminderStatus;
	
	@FindBy(how = How.XPATH, using = "(//td[text()='Failed'])[2]")
	private WebElement manualReminderStatus;
	
	@FindBy(how = How.XPATH, using = "//button[@id='closeReminderStatusesModal']")
	private WebElement closeReminderStatusesModal;
	
	@FindBy(how = How.XPATH, using = "(//td/span[@class='mf-icon mf-icon__item--error--small mf-color__negative'])[1]")
	private WebElement faildPriorDayReminderIcon;

	@FindBy(how = How.XPATH, using = "(//td/span[@class='mf-icon mf-icon__item--error--small mf-color__negative'])[2]")
	private WebElement faildManualReminderIcon;
	
	@FindBy(how = How.XPATH, using = "//button[@id='launchPatientModeButton']")
	private WebElement launchPatientModeButton;

	@FindBy(how = How.XPATH, using = "//button[text()='Continue']")
	private WebElement continueButton;

	@FindBy(how = How.XPATH, using = "//input[@name='firstName']")
	private WebElement fName;

	@FindBy(how = How.XPATH, using = "//input[@name='middleName']")
	private WebElement midName;

	@FindBy(how = How.XPATH, using = "//input[@name='lastName']")
	private WebElement lName;
	
	@FindBy(how = How.XPATH, using = "//input[@id='phone']")
	private WebElement phoneNo;

	@FindBy(how = How.XPATH, using = "//input[@id='email']")
	private WebElement mail;

	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	private WebElement submit;

	@FindBy(how = How.CSS, using = "#root > div > div:nth-child(1) > div > div > ul > li:nth-child(5)")
	private WebElement signOutbutton;
	
	@FindBy(how = How.XPATH, using = "(//button[@type='button'])[1]")
	private WebElement yesLogMeOut;
	
	@FindBy(how = How.XPATH, using = "//div[@class='patient-name']")
	private WebElement patientNamefromBroadcastLogs;
	
	@FindBy(how = How.XPATH, using = "//button[text()='OK']")
	private WebElement okButton;
	  
	@FindBy(how = How.XPATH, using = "//button[text()='Save & Continue']")
	private WebElement saveAndContinueButton;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Pay in office']")
	private WebElement payInOfficeButton;

	@FindBy(how = How.XPATH, using = "//button[text()=\"I'm done\"]")
	private WebElement iAmDoneButton;
	 
	@FindBy(how = How.XPATH, using = "//button[text()=\"Log out\"]")
	private WebElement logOutButton;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Skip and pay in office']")
	private WebElement skipAndPayInOffice;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Skip and finish later']")
	private WebElement skipAndFinishLater;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Email reminders log']")
	private WebElement emailReminderLogTitle;

	@FindBy(how = How.XPATH, using = "//span[text()='Text reminders log']")
	private WebElement textReminderLogTitle;
	
	@FindAll({ @FindBy(how = How.XPATH, using = "(//input[@type='checkbox'])") })
	public List<WebElement> allTimeCheckboxes;
	
	@FindBy(how = How.XPATH, using = "//button[text() = 'Add insurance']")
	private WebElement addInsurance;
	
	@FindBy(how = How.XPATH, using = "//input[@id='insuranceName']")
	private WebElement enterInsuranceName;
	
	@FindBy(how = How.XPATH, using = "//input[@id='memberId']")
	private WebElement subscriberId;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Add insurance card']")
	private WebElement addInsuranceCard;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='insurance-count'])[1]")
	private WebElement insuranceCount;
	
	@FindBy(how = How.XPATH, using = "(//p[text()='Edit'])[2]")
	private WebElement editInsuranceCards;
	
	@FindBy(how = How.XPATH, using = "(//button[@class='insurance-btn delete-insurance'])[7]")
	private WebElement deleteInsurance;
	
	@FindBy(how = How.XPATH, using = "//button[@id='delete-insurance_button-confirm']")
	private WebElement deleteButton;
	
	@FindBy(how = How.XPATH, using = "//a[text()='View details']")
	private WebElement viewDetails;

	@FindBy(how = How.XPATH, using = "//div[text()='Removed by patient']")
	private WebElement cardDetailsInApptAfterPrecheck;
	
	@FindBy(how = How.XPATH, using = "//div[text()='Added']")
	private WebElement insuranceCardDetailsInApptAfterPrecheck;
	
	@FindBy(how = How.XPATH, using = "//span[@class='stepper-text stepper-text--selected']")
	private WebElement insuranceStepper;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Skip adding insurance']")
	private WebElement skipAddingInsurance;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Pay now']")
	private WebElement clickOnPayNow;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Add card']")
	private WebElement addCard;
	
	@FindBy(how = How.XPATH, using = "//input[@id='nameOnCard']")
	private WebElement enterNameOnCard;
	
	@FindBy(how = How.XPATH, using = "//input[@name='cardNumber']")
	private WebElement enterCardNumber;
	
	@FindBy(how = How.XPATH, using = "//input[@placeholder='MM/YY']")
	private WebElement enterExpiryDate;
	
	@FindBy(how = How.XPATH, using = "//input[@name='cvv']")
	private WebElement enterCVVCode;
	
	@FindBy(how = How.XPATH, using = "//button[@class='primary-button primary-btn-pay-std']")
	private WebElement clickOnConfirmButtonOfPrecheck;

	@FindBy(how = How.XPATH, using = "//button[@class='primary-button']")
	private WebElement clickOnPayButton;
	
	@FindBy(how = How.XPATH, using = "//div[@class='copay-expanded-cell-content rt-td subrow-precheck-cell payment-expanded expanded-precheck-cell']")
	private WebElement copayAmount;
	
	@FindBy(how = How.XPATH, using = "//input[@id='zip']")
	private WebElement enterPatientZipCodeOfCard;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Verify patient information']")
	private WebElement precheckPageTitle;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Pay in office']")
	private WebElement payInOfficeTextOfAppt;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Copay completed!']")
	private WebElement copayCompletedTitle;
	
	@FindBy(how = How.XPATH, using = "//p[text()='You have already completed your co-payment.']")
	private WebElement copayCompletedMessage;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Confirm payment']")
	private WebElement confirmPaymentTitle;
	
	@FindBy(how = How.XPATH, using = "//p[@class='simple-modal__description']")
	private WebElement processingPaymentMessage;
	
	@FindBy(how = How.XPATH, using = "(//a[@class='stepper-item__main'])[4]")
	private WebElement copayStepper;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Pay balance']")
	private WebElement payBalanceTitle;
	
	@FindBy(how = How.XPATH, using = "//h2[@class='page-subheading']")
	private WebElement subHeadingOfPayBalance;
	
	@FindBy(how = How.XPATH, using = "//span[@class='balance-decide-form_min-balance']")
	private WebElement minimumPaymentAmount;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Pay balance']")
	private WebElement balanceStepper;
	
	@FindBy(how = How.XPATH, using = "//div[@class='demographics-cell-content']")
	private WebElement confirmTickAfterPrecheck;
	
	@FindBy(how = How.XPATH, using = "//div[@class='modal-body']//p")
	private WebElement messageAfterDeletingExistingInsurances;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Thank you for checking in!']")
	private WebElement messageAfterClickingOnImdoneButton;
	
	@FindBy(how = How.XPATH, using = "//p[@class='simple-modal__description']/span")
	private WebElement patientModeCompletionMessageClickingOnImDoneButton;
	
	@FindBy(how = How.XPATH, using = "//div[@class='text-center']")
	private WebElement precheckDoneTitle;
	
	@FindBy(how = How.XPATH, using = "//div//p[text()='Provider']")
	private WebElement providerTextInApptDetailsPage;
	
	@FindBy(how = How.XPATH, using = "//div//p[text()='Date and time']")
	private WebElement dateAndTimeTextInApptDetailsPage;
	
	@FindBy(how = How.XPATH, using = "//div//p[text()='Location']")
	private WebElement locationTextInApptDetailsPage;
	
	@FindBy(how = How.XPATH, using = "//h2[text()='Precheck information']")
	private WebElement precheckInformationTextInApptDetailsPage;
	
	@FindBy(how = How.XPATH, using = "//h2[text()='Patient forms']")
	private WebElement patientFormsTextInApptDetailsPage;
	
	@FindBy(how = How.XPATH, using = "//button[text()='View upcoming appointments']")
	private WebElement upcomingApptsButtonInApptDetailsPage;
	
	@FindBy(how = How.XPATH, using = "(//p[text()='Edit'])[1]")
	private WebElement editPatientInformation;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Insurance information']")
	private WebElement insurancePageTitle;
	
	@FindBy(how = How.XPATH, using = "//span[@class='menu-item-patient-name']")
	private WebElement patientNameTextInMenuOfPrecheckPage;
	
	@FindBy(how = How.XPATH, using = "//li[@class='menu-items__item'][1]")
	private WebElement officeNumberTextInMenuOfPrecheckPage;
	
	@FindBy(how = How.XPATH, using = "//*[@class='menu-items__item'][2]")
	private WebElement languageTextInMenuOfPrecheckPage;
	
	@FindBy(how = How.XPATH, using = "//*[@class='menu-items__item'][3]")
	private WebElement signOutTextInMenuOfPrecheckPage;
	
	@FindBy(how = How.XPATH, using = "//div[@class='simple-modal__icon sign-out-modal__icon']")
	private WebElement logOutIcon;
	
	@FindBy(how = How.XPATH, using = "//p[@class='simple-modal__description']")
	private WebElement logOutMessageInPrecheck;
	
	@FindBy(how = How.XPATH, using = "//button[@class='copay-disclaimer__btn']")
	private WebElement copayDisclaimerbutton;
	
	@FindBy(how = How.XPATH, using = "//div[@class='copay-disclaimer__detail']")
	private WebElement copayDisclaimerText;
	
	@FindBy(how = How.XPATH, using = "(//button[@class='add-insurance-image'])[1]")
	private WebElement clickOnAddFrontImage;
	
	@FindBy(how = How.XPATH, using = "(//button[@class='add-insurance-image'])[2]")
	private WebElement clickOnAddBackImage;
	
	@FindBy(how = How.XPATH, using = "//span[@class='error-text']")
	private WebElement errorMessageAfterUploadingInvalidImage;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='drop-down-icon'])[7]")
	private WebElement clickArrowDropdownForFirstInsurance;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='drop-down-icon'])[8]")
	private WebElement clickArrowDropdownForSecondInsurance;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='drop-down-icon'])[9]")
	private WebElement clickArrowDropdownForThirdInsurance;
	
	@FindBy(how = How.XPATH, using = "//div[@class='image-container']")
	private WebElement visibilityOfInsuranceImages;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Remove']")
	private WebElement clickOnRemoveOption;
	
	@FindBy(how = How.XPATH, using ="//span[@class='mf-color__positive']")
	private WebElement deletedPatientBanner;
	
	@FindBy(how = How.XPATH, using = "//*[@class=\"react-datepicker__time-list-item \"]")
	private WebElement selectTime;
	
	@FindBy(how=How.XPATH, using ="(//input[@type='checkbox'])[2]")
	private WebElement selectPatientCheckbox;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Continue']")
	private WebElement clickOnContinue;

	
	public AppointmentsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	CommonMethods commonMethods = new CommonMethods();

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
		Thread.sleep(10000);
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
		IHGUtil.waitForElement(driver, 10, confirmButtonFromRemove);
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
			String dob, String phoneNo, String emailId, String address1, String city, String zip,
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
		select.selectByIndex(2);
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

	public boolean visibilityPatient(String patientId) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, driver.findElement(By.xpath("//span[text()='" + patientId + "']")));
		boolean visibility = false;
		visibility = driver.findElement(By.xpath("//span[text()='" + patientId + "']")).isDisplayed();
		return visibility;
	}

	public String getBroadcastMessagePopupText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMessagePopup);
		return broadcastMessagePopup.getText();
	}

	public String getBroadcastMessagePopupInstr() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupInstruction);
		return broadcastMsgPopupInstruction.getText();
	}

	public String getBroadcastMessagePopupStartTime() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupSartTime);
		return broadcastMsgPopupSartTime.getText();
	}

	public String currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("M/d/yy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return dateFormat.format(c.getTime());
	}

	public String futureDate(int date) throws InterruptedException {
		DateFormat dateFormat = new SimpleDateFormat("M/d/yy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, +date);
		return dateFormat.format(c.getTime());
	}

	public String getBroadcastMessagePopupEndTime() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupEndTime);
		return broadcastMsgPopupEndTime.getText();
	}

	public String getBroadcastMessagePopupProvider() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupPrivider);
		return broadcastMsgPopupPrivider.getText();
	}

	public String getBroadcastMessagePopupLoaction() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupLocation);
		return broadcastMsgPopupLocation.getText();
	}

	public String getBroadcastMsgPopupEnglishLabel() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupEnglishLabel);
		return broadcastMsgPopupEnglishLabel.getText();
	}

	public String getBroadcastMsgPopupSpanishLabel() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupSpanishLabel);
		return broadcastMsgPopupSpanishLabel.getText();
	}

	public String getBroadcastMsgPopupConfirmThisMsgLabel() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, confirmThisMsgLabel);
		return confirmThisMsgLabel.getText();
	}

	public boolean checkConfirmThisMsgCheckbox() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, confirmThisMsgCheckboxLabel);
		boolean selected = false;
		selected = confirmThisMsgCheckboxLabel.isSelected();
		return selected;
	}

	public String getBroadcastMsgPopupCloseButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupCloseButton);
		return broadcastMsgPopupCloseButton.getText();
	}

	public void hoverOnCloseButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupCloseButton);
		Actions action = new Actions(driver);
		action.moveToElement(broadcastMsgPopupCloseButton);
	}

	public String getBroadcastMsgPopupSendButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupSendButton);
		return broadcastMsgPopupSendButton.getText();
	}

	public boolean visibilityOfBroadcastMsgCrossButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupCrossButton);
		boolean visibility = false;
		visibility = broadcastMsgPopupCrossButton.isDisplayed();
		return visibility;
	}

	public String getBroadcastMsgPopupEnIncrDecrChar() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, englishIncrDecrChar);
		return englishIncrDecrChar.getText();
	}

	public String getBroadcastMsgPopupEsIncrDecrChar() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, spanishIncrDecrChar);
		return spanishIncrDecrChar.getText();
	}

	public boolean closeButtonFromBroadcastMsgPopup() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupCloseButton);
		boolean enable = false;
		enable = broadcastMsgPopupCloseButton.isEnabled();
		return enable;
	}
	
	public void clickOnSendForBroadcastMsg() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, sendMessageButton);
		jse.executeScript("arguments[0].click();", sendMessageButton);
	}
	
	public void closeSelectedPatientBanner() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, bannerCloseButton);
		bannerCloseButton.click();
	}
	
	public boolean sendButtonFromBroadcastMsgPopup() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMsgPopupSendButton);
		boolean enable = false;
		enable = broadcastMsgPopupSendButton.isEnabled();
		return enable;
	}
	
	public boolean visibilitySelectedPatientBanner() {
		IHGUtil.PrintMethodName();
		boolean visibility = false;
		try {
			IHGUtil.waitForElement(driver, 10, selectedPatientBanner);
			visibility = selectedPatientBanner.isDisplayed();
			return visibility;
		} catch (NoSuchElementException exp) {
			return visibility;
		}
	}

	public void clickOnBroadcastEmailForSelectedPatient(String patientId, String apptId) {
		driver.navigate().refresh();
		IHGUtil.waitForElement(driver, 10, driver
				.findElement(By.xpath("//*[@id='select-" + patientId + "-" + apptId + "'" + "]/following::div[29]")));
		WebElement selectBroadCastEmail = driver
				.findElement(By.xpath("//*[@id='select-" + patientId + "-" + apptId + "'" + "]/following::div[29]"));
		selectBroadCastEmail.click();
	}

	public String getBroadcastMessage() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastEmailMsg);
		return broadcastEmailMsg.getText();
	}

	public void EnterBroadcastMessageEnEs(String messageEn, String messageEs) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, broadcastMessageInEn);
		broadcastMessageInEn.sendKeys(messageEn);
		broadcastMessageInEs.sendKeys(messageEs);
		jse.executeScript("arguments[0].click();", confirmThisMsgCheckbox);
	}
	
	public String getStartTime() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, filterStartTime);
		return filterStartTime.getAttribute("value");
	}

	public String getEndTime() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, endTime);
		return endTime.getAttribute("value");
	}
	
	public void selectCurrentDateInEndDateFilter() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, endTime);
		endTime.click();
		log("click on end date filter");
		
		log("Select Month");
		IHGUtil.waitForElement(driver, 10, months);
		DateFormat monthFormat = new SimpleDateFormat("M");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String currentMonth=monthFormat.format(cal.getTime());
		Integer month = Integer.valueOf(currentMonth);
		String monthValue=String.valueOf(month-1); 
		Select selectMonth = new Select(months);
		selectMonth.selectByValue(monthValue);
		
		log("Select Year");
		IHGUtil.waitForElement(driver, 10, years);
		int yyyy = cal.get(Calendar.YEAR);
		String year = Integer.toString(yyyy);
		Select selectYear = new Select(years);
		selectYear.selectByVisibleText(year);
		log("Year : " + (cal.get(Calendar.YEAR)));
		
		log("Select Date");
		DateFormat dateFormat = new SimpleDateFormat("d");
		cal.setTime(new Date());
		String currentDate=dateFormat.format(cal.getTime());
		WebElement date = driver.findElement(By.xpath(
				"(//*[@id=\"page-content-container\"]/div/header/div[2]/div[2]/div[2]/div[2]/div/div/div[2]/div[2]/div//div[text()="
						+ "'" + currentDate + "'" + "])[1]"));
		date.click();
		log("click on current date");
		Thread.sleep(10000);
	}
	
	public void selectEndTime(String time) throws InterruptedException {
		WebElement selectEndTime=driver.findElement(By.xpath("//*[@id=\"page-content-container\"]/div/header/div[2]/div[2]/div[2]/div[2]/div/div/div[3]/div[2]/div/ul/li[text()="
	                  +"'"+time+"'"+"]"));
		selectEndTime.click();
		Thread.sleep(10000);
	}
	
	public void clickOnStartDateFilter(){
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, startTime);
		startTime.click();
	}
	
	public void selectStartTime(String time) throws InterruptedException {
		WebElement selectStartTime=driver.findElement(By.xpath("//*[@id='page-content-container']/div/header/div[2]/div[1]/div[2]/div[2]/div/div/div[3]/div[2]/div/ul/li[text()="
	                  +"'"+time+"'"+"]"));
		selectStartTime.click();
		Thread.sleep(10000);
	}
	
    public boolean selectStartDate() {
    	clickOnStartDateFilter();
    	IHGUtil.PrintMethodName();
		int DateAndTimeSize = startDateTime.size();
		boolean visibility = false;
		log("Total patient size: "+DateAndTimeSize);
		for (int i = 1; i < DateAndTimeSize; i++) {
			WebElement time = startDateTime.get(i);
			visibility = time.isSelected();
		}
		return visibility;
	}
	
    public void clickOnEndTimeFilter() {
    	IHGUtil.PrintMethodName();
    	IHGUtil.waitForElement(driver, 5, endTime);
    	endTime.click();
	}
    
    public boolean selectEndDate() {
    	clickOnEndTimeFilter();
    	IHGUtil.PrintMethodName();
		int DateAndTimeSize = endDateTime.size();
		boolean visibility = false;
		log("Total patient size: "+DateAndTimeSize);
		for (int i = 1; i < DateAndTimeSize-42; i++) {
			WebElement time = endDateTime.get(i);
			visibility = time.isSelected();
		}
		return visibility;
	}
    
    public void selectStartDate(int backMonth) throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, endTime);
		startTime.click();
		log("click on start date filter");
		
		log("Select Month");
		IHGUtil.waitForElement(driver, 10, months);
		DateFormat monthFormat = new SimpleDateFormat("M");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String currentMonth=monthFormat.format(cal.getTime());
		Integer month = Integer.valueOf(currentMonth);
		String monthValue=String.valueOf(month+backMonth); 
		Select selectMonth = new Select(months);
		selectMonth.selectByValue(monthValue);
		
		log("Select Year");
		IHGUtil.waitForElement(driver, 10, years);
		int yyyy = cal.get(Calendar.YEAR);
		String year = Integer.toString(yyyy-1);
		Select selectYear = new Select(years);
		selectYear.selectByVisibleText(year);
		log("Year : " + (cal.get(Calendar.YEAR)));
		
		log("Select Date");
		DateFormat dateFormat = new SimpleDateFormat("d");
		cal.setTime(new Date());
		String currentDate=dateFormat.format(cal.getTime());
		WebElement date = driver.findElement(By.xpath(
				"//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/div[2]/div/div[text()="
						+ "'" + currentDate + "'" + "]"));
		date.click();
		log("click on date");
		Thread.sleep(10000);
	}
    
    public void selectRequiredPage(int reqPageNo) throws InterruptedException{
		IHGUtil.PrintMethodName();
		for(int i=1;i<=10;i++) {
			String getPageNo=jumpToNextPage();
			int page=Integer.parseInt(getPageNo);
			Thread.sleep(3000);
			if(reqPageNo==page) {
				log("User is on page no: "+page);
				break;
			}
		}
		Thread.sleep(5000);
    }
    
    public void selectFirstPatient() {
    	IHGUtil.PrintMethodName();
    	IHGUtil.waitForElement(driver, 5, selectFirstPatient);
    	jse.executeScript("arguments[0].click();", selectFirstPatient);
	}
    
    public String jumpToPreviousPage() throws InterruptedException {
    	IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, previousPage);
		jse.executeScript("arguments[0].click();", previousPage);
		Thread.sleep(10000);
		String pageNo = jumpToPage.getAttribute("value");
		return pageNo;
	}

    public boolean getBannerMessageBaseOnAppt() {
		IHGUtil.PrintMethodName();
		boolean visibility = false;
		try {
			visibility=bannerMesssageBaseOnAppointment.isDisplayed();
			log("Banner message succuss status is displayed");
			return visibility;
		} catch (NoSuchElementException e) {
			log("Banner message succuss status is not displayed");
			return visibility;
		}
	}

	public boolean visibilityOfBannerMessage() {
		IHGUtil.PrintMethodName();
		boolean visibility = false;
		try {
			visibility=selectBannerMesssage.isDisplayed();
			log("Banner message succuss status is displayed");
			return visibility;
		} catch (NoSuchElementException e) {
			log("Banner message succuss status is not displayed");
			return visibility;
		}
	}
	
	public void selectMultipleAppointments(int selectPatients) throws InterruptedException {
		IHGUtil.PrintMethodName();
		for (int i = 1; i <= selectPatients; i++) {
			WebElement patients = allAppointment.get(i);
			jse.executeScript("arguments[0].click();", patients);
		}
	}
	
	public String jumpToNextPage(int pageNumber) throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, jumpToNextPage);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", jumpToNextPage);
		log("Click on jump to page and move to "+pageNumber+" page");
		jse.executeScript("arguments[0].click();", jumpToNextPage);
		IHGUtil.waitForElement(driver, 5, jumpToPage);
		String pageNo = jumpToPage.getAttribute("value");
		jse.executeScript("arguments[0].scrollIntoView(true);", appointmentsTab);
		return pageNo;
	}
	
	public String getBannerMessageSelectAppAppt() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, selectBannerMesssage);
		return selectBannerMesssage.getText();
	}
	
	public void selectPatientIdAppt(String patientId) throws InterruptedException {
		WebElement selectPatientId = driver
				.findElement(By.xpath("//input[@id='filter-patient-id']"));
		selectPatientId.sendKeys(patientId);
		log("patient id entered in patientid field");
		Thread.sleep(2000);
	}
	
	public void clickOnExpandForSelectedPatient(String patientId,String apptId) throws InterruptedException{
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
        WebElement expandButton= driver.findElement(By.xpath(" //input[@id='select-"+patientId+"-"+apptId+"']/following::div[@class='expand-test']"));
        expandButton.click();
    }
	
	public String getPriorEntryTextForSelectedPatient(String patientId,String apptId,int indexPath) throws InterruptedException{
    	IHGUtil.PrintMethodName();
        WebElement viewAll= driver.findElement(By.xpath("(//input[@id='select-"+patientId+"-"+apptId+"']/following::div/a[text()='View all'])["+indexPath+"]"));
        viewAll.click();
        scrollAndWait(2000, -1000, 6000);
        jse.executeScript("arguments[0].scrollIntoView(true);", reminderLogsPopupTitle);
        return priorTypeEntry.getText();
    }
	
	 public String  selectedPatientGetPriorEntryForEmail(String patientId,String apptId,int index) throws InterruptedException{
			IHGUtil.PrintMethodName();
	        WebElement none= driver.findElement(By.xpath("(//input[@id='select-"+patientId+"-"+apptId+"']/following::div[text()='None'])["+index+"]"));
	        return none.getText();
	}
	 
	 public void  clickOnReminderLogsCloseBtn() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, reminderLogsCloseBtn);
			reminderLogsCloseBtn.click();
	}
	 
	 public String  selectedPatientGetPriorEntryForText(String patientId,String apptId,int index) throws InterruptedException{
			IHGUtil.PrintMethodName();
	        WebElement none= driver.findElement(By.xpath("(//input[@id='select-"+patientId+"-"+apptId+"']/following::div[text()='None'])["+index+"]"));
	        return none.getText();
	}
	 
	 public boolean visibilityOfEsTextbox() {
			boolean visibility = false;
			try {
				visibility = broadcastMessageInEs.isDisplayed();
				log("Spanish broadcast Message is display");
				return visibility;
			} catch (NoSuchElementException e) {
				log("Spanish broadcast Message is not display");
				return visibility;
			}
		}

		public void scrollOnBroadcastMsg() {
			log("Scroll On Broadcast Message");
			jse.executeScript("arguments[0].scrollIntoView(true);", broadcastMsgLogs);
		}

		public void sendBroadcastInEnglish(String messageEn) throws Exception {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, broadcastMessageInEn);
			broadcastMessageInEn.sendKeys(messageEn);
			jse.executeScript("arguments[0].click();", confirmThisMsgCheckbox);
			jse.executeScript("arguments[0].click();", sendMessageButton);
		}

		public boolean visibilityOfEnTextbox() {
			boolean visibility = false;
			try {
				visibility = broadcastMessageInEn.isDisplayed();
				log("English broadcast Message is display");
				return visibility;
			} catch (NoSuchElementException e) {
				log("English broadcast Message is not display");
				return visibility;
			}
		}
		
		public boolean visibilityOfSendReminderButton() {
			IHGUtil.waitForElement(driver, 10, sendReminderButton);
			if (sendReminderButton.isDisplayed()) {
				System.out.print("Send Reminder button is enabled. ");
				return true;
			} else {
				System.out.print("Send Reminder button is disabled.");
				return false;
			}
		}
		
		public boolean visibilityOfBroadcastMessageButton() {
			IHGUtil.waitForElement(driver, 10, broadcastMessageButton);
			if (broadcastMessageButton.isDisplayed()) {
				System.out.print("Broadcast Message button is enabled.");
				return true;
			} else {
				System.out.print("Broadcast Message button is disabled.");
				return false;
			}
		}

		public boolean removeBannerMessage() throws InterruptedException {
			IHGUtil.waitForElement(driver, 5, removeBannerMessage);
			if(removeBannerMessage.isDisplayed())
			{
				log("Patients are removed succsessfully");
				return true;
			} else{
				log("Patients are not removed");
				return false;
			}

		}
	 
	 public boolean visibilityOfPageNo() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, pageNo);
			if (pageNo.isDisplayed()) {
				log("page number displayed correctly");
				return true;
			}
			else {
				log("page number is not displayed correctly");
				return false;
			}
		}
	 
	 public void locationDropdown() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, locationDropdown);
			jse.executeScript("arguments[0].click();", locationDropdown);
		}
	 
	 public void locationFilterSelected() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, locationFilterSelected);
			jse.executeScript("arguments[0].click();", locationFilterSelected);
		}
	 
	 public void providerDropdown() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, providerDropdown);
			jse.executeScript("arguments[0].click();", providerDropdown);
		}
	 
	 public void providerFilterSelected() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, providerFilterSelected);
			jse.executeScript("arguments[0].click();", providerFilterSelected);
		}
	 public void clickOnBroadcastMessageButton() {
			IHGUtil.waitForElement(driver, 10, broadcastMessageButton);
			jse.executeScript("arguments[0].click();", broadcastMessageButton);
		}
	 
		public String getBroadcastMsgStatusForSelectedPatient(String patientId, String apptId) throws InterruptedException {
			IHGUtil.PrintMethodName();
			WebElement broadcastMsg = driver.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId
					+ "']//following::div[@class='reminders-cell-content'])[4]"));
			broadcastMsg.click();
			scrollAndWait(2000, 1000, 6000);
			jse.executeScript("arguments[0].scrollIntoView(true);", reminderLogsPopupTitle);
			return messageStatus.getText();
		}
		
		public void selectPatientCheckbox(String patientId, String practiceId) {
			WebElement selectPatient = driver
					.findElement(By.xpath("//*[@id='select-" + patientId + "-" + practiceId + "'" + "]"));
			selectPatient.click();
		}
		
		public String getBroadcastEmailCountForSelectedPatient(String patientId, String apptId)
				throws InterruptedException {
			IHGUtil.PrintMethodName();
			WebElement patientBroadcastCount = driver.findElement(By.xpath(
					"//input[@id='select-" + patientId + "-" + apptId + "']/following::span[@class='broadcast-count']"));
			return patientBroadcastCount.getText();
		}
		
		public String getColorForOneDayReminderStatus(String patientId, String apptId, int retries, String value) {
			IHGUtil.PrintMethodName();
			int TIME_TO_WAIT_MS = 10000;
			String color = null;

			try {
				for (int j = 1; j <= retries; j++) {
					WebElement oneDayReminderStatus = driver.findElement(By.xpath("(//input[@id='select-" + patientId + "-"
							+ apptId + "']/following::span[@class='status-icons']/span/span)[1]"));
					String cssValue = oneDayReminderStatus.getCssValue("color");
					color = Color.fromString(cssValue).asHex();
					if (color.equals(value)) {
						log("Color is :" + color);
						return color;
					}
					log(("Color was") + " not retrieved. Trial number " + j + "/" + retries + "."
							+ (j != retries
									? " Waiting for Color" + ("") + " to arrive for "
											+ TIME_TO_WAIT_MS / 1000 + " s."
									: ""));
					jse.executeScript("arguments[0].click();", refreshTab);
					Thread.sleep(60000);
					if (j != retries) {
						Thread.sleep(TIME_TO_WAIT_MS);
					}
				}
			} catch (Exception e) {
				log("unable to find Color" + e);
			}
			return color;
		}

		public String getColorManualReminderStatus(String patientId, String apptId, int retries, String value) {
			IHGUtil.PrintMethodName();
			int TIME_TO_WAIT_MS = 10000;
			String color = null;
			try {
				for (int j = 1; j <= retries; j++) {
					WebElement manualReminderStatus = driver.findElement(By.xpath("(//input[@id='select-" + patientId + "-"
							+ apptId + "']/following::span[@class='manual-status-icon-and-count']/span)[1]"));
					String cssValue = manualReminderStatus.getCssValue("color");
					color = Color.fromString(cssValue).asHex();
					if (color.equals(value)) {
						log("Color is :" + color);
						return color;
					}
					log(("Color was") + " not retrieved. Trial number " + j + "/" + retries + "."
							+ (j != retries
									? " Waiting for Color" + ("") + " to arrive for "
											+ TIME_TO_WAIT_MS / 1000 + " s."
									: ""));
					jse.executeScript("arguments[0].click();", refreshTab);
					Thread.sleep(60000);
					if (j != retries) {
						Thread.sleep(TIME_TO_WAIT_MS);
					}
				}
			} catch (Exception e) {
				log("unable to find Color" + e);
			}
			return color;
		}
		
		public void clickOnViewAllForReminderStatus(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement viewAll = driver.findElement(
					By.xpath("//input[@id='select-" + patientId + "-" + apptId + "']/following::a[text()='View all']"));
			viewAll.click();
		}

		public String getFaildPriorDayIconColor() {
			IHGUtil.PrintMethodName();
			String cssValue = faildPriorDayReminderIcon.getCssValue("color");
			String color = Color.fromString(cssValue).asHex();
			log("Color is :" + color);
			return color;
		}

		public String getFaildManualIconColor() {
			IHGUtil.PrintMethodName();
			String cssValue = faildManualReminderIcon.getCssValue("color");
			String color = Color.fromString(cssValue).asHex();
			log("Color is :" + color);
			return color;
		}

		public String getPriorDayReminderStatus() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, priorDayReminderStatus);
			return priorDayReminderStatus.getText();
		}

		public String getManualReminderStatus() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, manualReminderStatus);
			return manualReminderStatus.getText();
		}
		
		public void closeReminderStatusModal() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, closeReminderStatusesModal);
			closeReminderStatusesModal.click();
		}
		
		public String getReminderStatus(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement reminderStatus = driver.findElement(By.xpath(
					"(//input[@id='select-" + patientId + "-" + apptId + "']/following::span[@class='status-icons'])[1]"));
			return reminderStatus.getText();
		}
		
		public String getReminderLogStatus(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement reminderStatus = driver.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId
					+ "']/following::div[@class='reminders-expanded-status'])[1]"));
			return reminderStatus.getText();
		}
		
		public void clickOnPatientName(String patientId, String apptId) throws InterruptedException {
			IHGUtil.PrintMethodName();
			if (IHGUtil.getEnvironmentType().equals("DEV3")) {
				WebElement patientName = driver
						.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::td)[3]"));
				patientName.click();
			}else {
					WebElement patientName = driver
							.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::span)[3]"));
					patientName.click();
			}
		}
		
		public void clickOnLaunchPatientModeButton() throws InterruptedException {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, launchPatientModeButton);
			launchPatientModeButton.click();

		}

		public void clickOnContinueButton() throws InterruptedException {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, continueButton);
			continueButton.click();
		}

		public void addPatientDetailsFromPrecheck(String firstName, String middleName,
				String lastName, String email, String PhoneNo) throws InterruptedException {
			
			IHGUtil.waitForElement(driver, 40, precheckPageTitle);
			
			fName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			fName.sendKeys(Keys.BACK_SPACE);
			fName.sendKeys(firstName);

			midName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			midName.sendKeys(Keys.BACK_SPACE);
			midName.sendKeys(middleName);

			lName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			lName.sendKeys(Keys.BACK_SPACE);
			lName.sendKeys(lastName);
			log("Enter first name , middle name and last name");

			phoneNo.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			phoneNo.sendKeys(Keys.BACK_SPACE);
			phoneNo.sendKeys(PhoneNo);

			mail.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			mail.sendKeys(Keys.BACK_SPACE);
			mail.sendKeys(email);
			submit.click();
			
			log("Completing precheck");
				try {
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, okButton);
					okButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
			
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}

				try {
					IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
					skipAndFinishLater.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
					
				iAmDoneButton.click();
				IHGUtil.waitForElement(driver, 5, logOutButton);
				logOutButton.click();
				}	
		
		
		public String getPatientNameFromApptDashboard(String patientId, String apptId) throws InterruptedException {
			IHGUtil.PrintMethodName();
			if (IHGUtil.getEnvironmentType().equals("DEV3")) {
				WebElement patientName = driver
						.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::td)[3]"));
				return patientName.getText();
			}else {
					WebElement patientName = driver
							.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::span)[3]"));
					return patientName.getText();
			}
		}

		public void clickOnBroadcastEmailLogForSelectedPatient(String patientId, String apptId)
				throws InterruptedException {
			IHGUtil.PrintMethodName();
			WebElement broadcastMsg = driver.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId
					+ "']//following::div[@class='reminders-cell-content'])[3]"));
			broadcastMsg.click();
		}
		
		public String getPatientNameFromBroadcastEmailLogs(String patientId, String apptId) throws InterruptedException {
			IHGUtil.PrintMethodName();
			scrollAndWait(2000, -1000, 5000);
			jse.executeScript("arguments[0].scrollIntoView(true);", patientNamefromBroadcastLogs);
			return patientNamefromBroadcastLogs.getText();
		}
		
		public void clickOnBroadcastPhoneLogForSelectedPatient(String patientId, String apptId)
				throws InterruptedException {
			IHGUtil.PrintMethodName();
			WebElement broadcastMsg = driver.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId
					+ "']//following::div[@class='reminders-cell-content'])[4]"));
			broadcastMsg.click();
		}
		
		public String getPatientNameFromBroadcastTextLogs(String patientId, String apptId) throws InterruptedException {
			IHGUtil.PrintMethodName();
			scrollAndWait(2000, -1000, 6000);
			jse.executeScript("arguments[0].scrollIntoView(true);", patientNamefromBroadcastLogs);
			return patientNamefromBroadcastLogs.getText();
		}
		
		public String getPatientEmailFromApptDashboard(String patientId, String apptId) throws InterruptedException {
			IHGUtil.PrintMethodName();
			if (IHGUtil.getEnvironmentType().equals("DEV3")) {
				WebElement patientName = driver
						.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::td)[7]"));
				return patientName.getText();
			}else {
				WebElement patientName = driver
						.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::span)[7]"));
				return patientName.getText();
		   }	
		}
		
		public String getPatientPhoneFromApptDashboard(String patientId, String apptId) throws InterruptedException {
			IHGUtil.PrintMethodName();
			if (IHGUtil.getEnvironmentType().equals("DEV3")) {
			WebElement patientName = driver
					.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::td)[8]"));
			return patientName.getText();
			}else {
				WebElement patientName = driver
						.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::span)[8]"));
				return patientName.getText();
		   }
		}
		
		public boolean visibilityOfDefaultIconForEmailReminder(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement reminderEmailIcon = driver.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId
					+ "']/following::span[@class='mf-icon mf-icon__negative--small mf-color__default'])[1]"));
			return reminderEmailIcon.isDisplayed();
		}

		public boolean visibilityOfDefaultIconForTextReminder(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement reminderTextIcon = driver.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId
					+ "']/following::span[@class='mf-icon mf-icon__negative--small mf-color__default'])[2]"));
			return reminderTextIcon.isDisplayed();
		}

		public boolean visibilityOfPaperPlaneIconForEmailReminder(String patientId, String apptId, int retries)
				throws InterruptedException {
			IHGUtil.PrintMethodName();
			int TIME_TO_WAIT_MS = 10000;
			boolean visibility = false;
			for (int j = 1; j <= retries; j++) {
				patientIdFilter.clear();
				filterPatientId(Appointment.patientId);
				driver.navigate().refresh();
				Thread.sleep(10000);
				try {
					WebElement paperPlaneIconForReminder = driver
							.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId
									+ "']/following::span[@class='mf-icon mf-icon__sent--small mf-color__positive'])[1]"));
					visibility = paperPlaneIconForReminder.isDisplayed();
					return visibility;

				} catch (Exception e) {
					log(("Paper plane Icon") + " not retrieved. Trial number " + j + "/" + retries + "."
							+ (j != retries	? " Waiting for Paper plane Icon" + ("") + " to arrive for "
											+ TIME_TO_WAIT_MS / 1000 + " s.": ""));
					log("unable to find Paper plane Icon" + e);
				}
			}
			return false;
		}

		public boolean visibilityOfPaperPlaneIconForTextReminder(String patientId, String apptId, int retries)
				throws InterruptedException {
			IHGUtil.PrintMethodName();
			int TIME_TO_WAIT_MS = 10000;
			boolean visibility = false;
			for (int j = 1; j <= retries; j++) {
				patientIdFilter.clear();
				filterPatientId(Appointment.patientId);
				driver.navigate().refresh();
				Thread.sleep(10000);
				try {
					WebElement paperPlaneIconForReminder = driver
							.findElement(By.xpath("(//input[@id='select-" + patientId + "-" + apptId
									+ "']/following::span[@class='mf-icon mf-icon__sent--small mf-color__positive'])[1]"));
					visibility = paperPlaneIconForReminder.isDisplayed();
					return visibility;

				} catch (Exception e) {
					log(("Paper plane Icon") + " not retrieved. Trial number " + j + "/" + retries + "."
							+ (j != retries ? " Waiting for Paper plane Icon" + ("") + " to arrive for "
											+ TIME_TO_WAIT_MS / 1000 + " s.": ""));
					log("unable to find Paper plane Icon" + e);
				}
			}
			return visibility;
		}

		public String getCountForEmailReminder(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement reminderTextIcon = driver.findElement(By.xpath(
					"(//input[@id='select-" + patientId + "-" + apptId + "']/following::span[@class='manual-count'])[1]"));
			return reminderTextIcon.getText();
		}

		public String getCountForTextReminder(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement reminderTextIcon = driver.findElement(By.xpath(
					"(//input[@id='select-" + patientId + "-" + apptId + "']/following::span[@class='manual-count'])[2]"));
			return reminderTextIcon.getText();
		}
		
		
		public void clickOnViewAllForEmailReminder(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement reminderTextIcon = driver.findElement(By
					.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::a[text()='View all'])[1]"));
			reminderTextIcon.click();
		}

		public void clickOnViewAllForTextReminder(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			WebElement reminderTextIcon = driver.findElement(By
					.xpath("(//input[@id='select-" + patientId + "-" + apptId + "']/following::a[text()='View all'])[2]"));
			reminderTextIcon.click();
		}

		public boolean visibilityOfMailReminderLogTitle(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, emailReminderLogTitle);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", emailReminderLogTitle);
			boolean visibility = false;
			visibility = emailReminderLogTitle.isDisplayed();
			return visibility;
		}

		public boolean visibilityOfTextReminderLogTitle(String patientId, String apptId) {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, textReminderLogTitle);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", textReminderLogTitle);
			boolean visibility = false;
			visibility = textReminderLogTitle.isDisplayed();
			return visibility;
		}

		public boolean visibilityOfMailReminderLogs(String patientId, String apptId, int indexPath) {
			IHGUtil.PrintMethodName();
			WebElement text = driver
					.findElement(By.xpath("(//span[text()='Email reminders log']/following::td)[" + indexPath + "]"));
			boolean visibility = false;
			visibility = text.isDisplayed();
			return visibility;
		}

		public boolean visibilityOfTextReminderLogs(String patientId, String apptId, int indexPath) {
			IHGUtil.PrintMethodName();
			WebElement text = driver
					.findElement(By.xpath("(//span[text()='Text reminders log']/following::td)[" + indexPath + "]"));
			boolean visibility = false;
			visibility = text.isDisplayed();
			return visibility;
		}

		public String getTextFromEmailRemLogs(String patientId, String apptId, String logs) {
			IHGUtil.PrintMethodName();
			String logContent = null;
			List<WebElement> text = driver.findElements(By.xpath("//span[text()='Email reminders log']/following::td"));
			for (int j = 0; j < text.size(); j++) {
				logContent = text.get(j).getText();
				if (logContent.equals(logs)) {
					log(" Reminder " + logs + "is match");
					return logContent;
				}
			}
			return logContent;
		}

		public void closeReminderLogPopup() throws InterruptedException {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, textReminderLogTitle);
			broadcastMsgPopupCrossButton.click();
			Thread.sleep(3000);
		}
		
		public String getTextFromTextRemLogs(String patientId, String apptId, String logs) {
			IHGUtil.PrintMethodName();
			String logContent = null;
			List<WebElement> text = driver.findElements(By.xpath("//span[text()='Text reminders log']/following::td"));
			for (int j = 0; j < text.size(); j++) {
				logContent = text.get(j).getText();
				if (logContent.equals(logs)) {
					log(" Reminder " + logs + "is match");
					return logContent;
				}
			}
			return logContent;
		}
		
		public void clickOnTimeCheckboxes(int numberOfPatient) {
    		IHGUtil.PrintMethodName();
    		for (int i = 1; i <= numberOfPatient; i++) {
    			WebElement timeCheckboxes = allTimeCheckboxes.get(i+1);
    			jse.executeScript("arguments[0].click();", timeCheckboxes);
    	}
	}
		
		public void startDate() throws InterruptedException {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, startTime);
			startTime.click();
			log("click on start date filter");
			
			log("Select Month");
			IHGUtil.waitForElement(driver, 10, months);
			DateFormat monthFormat = new SimpleDateFormat("M");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String currentMonth=monthFormat.format(cal.getTime());
			Integer month = Integer.valueOf(currentMonth);
			String monthValue=String.valueOf(month-2); 
			Select selectMonth = new Select(months);
			selectMonth.selectByValue(monthValue);
			
			log("Select Year");
			IHGUtil.waitForElement(driver, 10, years);
			int yyyy = cal.get(Calendar.YEAR);
			String year = Integer.toString(yyyy);
			Select selectYear = new Select(years);
			selectYear.selectByVisibleText(year);
			log("Year : " + (cal.get(Calendar.YEAR)));
			
			log("Select Date");
			DateFormat dateFormat = new SimpleDateFormat("d");
			cal.setTime(new Date());
			String currentDate=dateFormat.format(cal.getTime());
			WebElement date = driver.findElement(By.xpath(
					"(//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div/div//div[text()="
							+ "'" + currentDate + "'" + "])[1]"));
			date.click();
			log("click on current date");
			Thread.sleep(10000);
		}
		
		public void enterProvider(String providerName) throws Exception {
			scrollAndWait(0, -1000, 5000);
			jse.executeScript("arguments[0].click();", providerFilter);
			Actions action = new Actions(driver);
			action.sendKeys(providerFilter, providerName).sendKeys(Keys.ENTER).build().perform();
			clickOnRefreshTab();
		}
		
		public void enterLocation(String locationName) throws InterruptedException {
			scrollAndWait(0, -1000, 5000);
			jse.executeScript("arguments[0].click();", patientFilter);
			Actions action = new Actions(driver);
			action.sendKeys(locationFilter, locationName).sendKeys(Keys.ENTER).build().perform();
			clickOnRefreshTab();
		}
		
		public void clickOnLogOutButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, logOutButton);
			jse.executeScript("arguments[0].click();", logOutButton);
		}
		
		public void saveAndContinueButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
			saveAndContinueButton.click();
		}
		
		public void clickOkButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, okButton);
			jse.executeScript("arguments[0].click();", okButton);
		}
		
		public void clickOnSkipAddingInsurance() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, skipAddingInsurance);
			jse.executeScript("arguments[0].click();", skipAddingInsurance);
		}
		
		public void clickOnSkipAndPayInOffice() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, skipAndPayInOffice);
			jse.executeScript("arguments[0].click();", skipAndPayInOffice );
		}
		
		public void clickOnSkipAndFinishLater() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, skipAndFinishLater);
			jse.executeScript("arguments[0].click();", skipAndFinishLater );
		}
		
		public void clickOnImDoneButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, iAmDoneButton);
			jse.executeScript("arguments[0].click();", iAmDoneButton);
		}
		
		public String visibilityOfInsuranceStepper() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10,insuranceStepper );
			return insuranceStepper.getText();
		}
		
		public void clickOnAddCard() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, addCard);
			jse.executeScript("arguments[0].click();", addCard);
			
		}
		
		public String visibilityOfCopay() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10,copayAmount );
			return copayAmount.getText();
		}
		
		public String insuranceCount() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, insuranceCount);
			return insuranceCount.getText();
		}
		
		public void viewDetails() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, viewDetails);
			viewDetails.click();
		}
		
		public String cardDetails() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, cardDetailsInApptAfterPrecheck);
			return cardDetailsInApptAfterPrecheck.getText();
		}
		
		public String insuranceCardDetails() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, insuranceCardDetailsInApptAfterPrecheck);
			return insuranceCardDetailsInApptAfterPrecheck.getText();
		}
		
		
		public void updatePhoneNumberAndEmailFromPrecheck(String PhoneNo, String email) throws InterruptedException {
			{
				phoneNo.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				phoneNo.sendKeys(Keys.BACK_SPACE);
				phoneNo.sendKeys(PhoneNo);

				mail.sendKeys(Keys.chord(Keys.CONTROL, "a"));
				mail.sendKeys(Keys.BACK_SPACE);
				mail.sendKeys(email);
				submit.click();
				
				log("Completing precheck");
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
					
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
					
					try {
						IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
						skipAndPayInOffice.click();
					}catch(NoSuchElementException e){
						IHGUtil.waitForElement(driver, 5, payInOfficeButton);
						payInOfficeButton.click();
					}
	
					try {
						IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
						skipAndPayInOffice.click();
					}catch(NoSuchElementException e){
						IHGUtil.waitForElement(driver, 5, payInOfficeButton);
						payInOfficeButton.click();
					}

					try {
						IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
						skipAndFinishLater.click();
					}catch(NoSuchElementException e){
						IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
						saveAndContinueButton.click();
					}
					
					IHGUtil.waitForElement(driver, 5, iAmDoneButton);	
					iAmDoneButton.click();
				
					IHGUtil.waitForElement(driver, 5, logOutButton);
					logOutButton.click();
				}
			
			}
		
		public void addInsurancesFromPrecheck(String FirstInsuranceName, String FirstSubscriberId, 
				String SecondInsuranceName, String SecondSubscriberId,
				String ThirdInsuranceName, String ThirdSubscriberId) throws InterruptedException {
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				try {
					IHGUtil.waitForElement(driver, 5, addInsuranceCard );
					addInsuranceCard.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton );
					saveAndContinueButton.click();
				}
				log("Enter first insurance");
				IHGUtil.waitForElement(driver, 5, enterInsuranceName);
				enterInsuranceName.sendKeys(FirstInsuranceName);
					
				IHGUtil.waitForElement(driver, 5, subscriberId);
				subscriberId.sendKeys(FirstSubscriberId);
					
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
					
				log("Enter second insurance");
				try {
					IHGUtil.waitForElement(driver, 5, addInsuranceCard);
					addInsuranceCard.click();
				}catch(NoSuchElementException e) {
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, enterInsuranceName);
				enterInsuranceName.sendKeys(SecondInsuranceName);
					
				IHGUtil.waitForElement(driver, 5, subscriberId);
				subscriberId.sendKeys(SecondSubscriberId);
					
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				log("Enter third insurance");
				try {
					IHGUtil.waitForElement(driver, 5, addInsuranceCard);
					addInsuranceCard.click();
				}catch(NoSuchElementException e) {
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, enterInsuranceName);
				enterInsuranceName.sendKeys(ThirdInsuranceName);
					
				IHGUtil.waitForElement(driver, 5, subscriberId);
				subscriberId.sendKeys(ThirdSubscriberId);
					
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}

				try {
					IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
					skipAndFinishLater.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, iAmDoneButton);	
				iAmDoneButton.click();
			
				IHGUtil.waitForElement(driver, 5, logOutButton);
				logOutButton.click();
					
				}
		
		public void removeInsurancesFromPrecheck(String FirstInsuranceName, String FirstSubscriberId, 
				String SecondInsuranceName, String SecondSubscriberId,
				String ThirdInsuranceName, String ThirdSubscriberId) throws InterruptedException {
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				try {
					IHGUtil.waitForElement(driver, 5, addInsuranceCard);
					addInsuranceCard.click();
				}catch(NoSuchElementException e) {
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				log("Enter first insurance");
				IHGUtil.waitForElement(driver, 5, enterInsuranceName);
				enterInsuranceName.sendKeys(FirstInsuranceName);
					
				IHGUtil.waitForElement(driver, 5, subscriberId );
				subscriberId.sendKeys(FirstSubscriberId);
					
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
					
				log("Enter second insurance");
				try {
					IHGUtil.waitForElement(driver, 5, addInsuranceCard);
					addInsuranceCard.click();
				}catch(NoSuchElementException e) {
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				IHGUtil.waitForElement(driver, 5, enterInsuranceName);
				enterInsuranceName.sendKeys(SecondInsuranceName);
					
				IHGUtil.waitForElement(driver, 5, subscriberId);
				subscriberId.sendKeys(SecondSubscriberId);
					
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				log("Enter third insurance");
				try {
					IHGUtil.waitForElement(driver, 5, addInsuranceCard);
					addInsuranceCard.click();
				}catch(NoSuchElementException e) {
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, enterInsuranceName);
				enterInsuranceName.sendKeys(ThirdInsuranceName);
					
				IHGUtil.waitForElement(driver, 5, subscriberId);
				subscriberId.sendKeys(ThirdSubscriberId);
					
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}

				try {
					IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
					skipAndFinishLater.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, editInsuranceCards);
				editInsuranceCards.click();
				
				for (int i = 0 ; i < 9 ; i++) {
				deleteInsurance.click();
				deleteButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, skipAddingInsurance);
				skipAddingInsurance.click();
				
				IHGUtil.waitForElement(driver, 5, continueButton);
				continueButton.click();
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}

				try {
					IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
					skipAndFinishLater.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, iAmDoneButton);	
				iAmDoneButton.click();
			
				IHGUtil.waitForElement(driver, 5, logOutButton);
				logOutButton.click();
					
				}
		
		public void payCopayFromPrecheck(String gender, String state, String CardName,
				String CardNumber, String ExpiryDate, String CVVCode, String ZipCode) throws InterruptedException {
			
			IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
			saveAndContinueButton.click();
			
			IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
			saveAndContinueButton.click();
			
			IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
			saveAndContinueButton.click();
			
			IHGUtil.waitForElement(driver, 5, clickOnPayNow);
			clickOnPayNow.click();
			
			IHGUtil.waitForElement(driver, 5, enterNameOnCard);
			enterNameOnCard.sendKeys(CardName);
		
			IHGUtil.waitForElement(driver, 5, enterCardNumber);
			enterCardNumber.sendKeys(CardNumber);
			
			IHGUtil.waitForElement(driver, 5, enterExpiryDate);
			enterExpiryDate.sendKeys(ExpiryDate);
			
			IHGUtil.waitForElement(driver, 5, enterCVVCode);
			enterCVVCode.sendKeys(CVVCode);
			
			IHGUtil.waitForElement(driver, 5, enterPatientZipCodeOfCard);
			enterPatientZipCodeOfCard.sendKeys(ZipCode);
			
			IHGUtil.waitForElement(driver, 5, clickOnConfirmButtonOfPrecheck);
			clickOnConfirmButtonOfPrecheck.click();
			
			IHGUtil.waitForElement(driver, 5, clickOnPayButton);
			clickOnPayButton.click();
			
			IHGUtil.waitForElement(driver, 5, continueButton);
			continueButton.click();
			
			try {
				IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
				skipAndPayInOffice.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, payInOfficeButton);
				payInOfficeButton.click();
			}

			try {
				IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
				skipAndFinishLater.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
			}
				
			IHGUtil.waitForElement(driver, 5, iAmDoneButton);	
			iAmDoneButton.click();
			
			IHGUtil.waitForElement(driver, 5, logOutButton);
			logOutButton.click();
					
			}
		
		public String  selectedPatientGetPriorEntryForEmail(String patientId,String apptId) throws InterruptedException{
			IHGUtil.PrintMethodName();
	        WebElement reminderEmailStatus= driver.findElement(By.xpath("(//input[@id='select-"+patientId+"-"+apptId+"']/following::div[@class='reminders-expanded-status'])[1]"));
	        return reminderEmailStatus.getText();
	    }
	    
	    public String getPriorEntryForTextForSelectedPatient(String patientId,String apptId) throws InterruptedException{
	    	IHGUtil.PrintMethodName();
	        WebElement reminderTextStatus= driver.findElement(By.xpath("(//input[@id='select-"+patientId+"-"+apptId+"']/following::div[@class='reminders-expanded-status'])[1]"));
	        return reminderTextStatus.getText();
	    }
	    
	    public String visibilityOfPayInOfficeTextOfAppt() {
			IHGUtil.PrintMethodName();
	        return payInOfficeTextOfAppt.getText();
	    }
	    
	    public String visibilityOfCopayCompletedTitle() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, copayCompletedTitle );
			return copayCompletedTitle.getText();
	    }
	    
	    public String visibilityOfCopayCompletedMessage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, copayCompletedMessage );
			return copayCompletedMessage.getText();
	    }
	    
	    public void editInsuranceCards() {
	    IHGUtil.waitForElement(driver, 10, editInsuranceCards);
		editInsuranceCards.click();
	    
	    }
	   
	    public void payCopayByPayInOfficeFromPrecheck() throws InterruptedException {
			
			IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
			saveAndContinueButton.click();
			
			IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
			saveAndContinueButton.click();
			
			IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
			saveAndContinueButton.click();
			
			try {
				IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
				skipAndPayInOffice.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, payInOfficeButton);
				payInOfficeButton.click();
			}
			
			try {
				IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
				skipAndPayInOffice.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, payInOfficeButton);
				payInOfficeButton.click();
			}

			try {
				IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
				skipAndFinishLater.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
			}
				
			IHGUtil.waitForElement(driver, 5, iAmDoneButton);	
			iAmDoneButton.click();
			
			IHGUtil.waitForElement(driver, 5, logOutButton);
			logOutButton.click();
					
			}
	    
	    public void clickOnPayNow() {
	    IHGUtil.waitForElement(driver, 5, clickOnPayNow);
		clickOnPayNow.click();
	    }
		
	    public void enterNameOnCard(String CardName) {
		IHGUtil.waitForElement(driver, 5, enterNameOnCard);
		enterNameOnCard.sendKeys(CardName);
	    }
	
	    public void enterCardNumber(String CardNumber) {
		IHGUtil.waitForElement(driver, 5, enterCardNumber);
		enterCardNumber.sendKeys(CardNumber);
	    }
		
	    public void enterExpiryDate(String ExpiryDate) {
		IHGUtil.waitForElement(driver, 5, enterExpiryDate);
		enterExpiryDate.sendKeys(ExpiryDate);
	    }
		
	    public void enterCVVCode(String CVVCode) {
		IHGUtil.waitForElement(driver, 5, enterCVVCode);
		enterCVVCode.sendKeys(CVVCode);
	    }
		
	    public void enterPatientZipCodeOfCard(String ZipCode) {
		IHGUtil.waitForElement(driver, 5, enterPatientZipCodeOfCard);
		enterPatientZipCodeOfCard.sendKeys(ZipCode);
	    }
		
	    public void clickOnConfirmButtonOfPrecheck() {
		IHGUtil.waitForElement(driver, 5, clickOnConfirmButtonOfPrecheck);
		clickOnConfirmButtonOfPrecheck.click();
	    }
	    
	    public boolean visibilityOfConfirmPaymentTitle(){
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, confirmPaymentTitle);
			if(confirmPaymentTitle.isDisplayed()) {
				log("confirm payment title is displayed");
				return true;
			}
			else {
				log("confirm payment title is not displayed");
				return false;
			}
		}
		
	    public void clickOnPayButton() {
		IHGUtil.waitForElement(driver, 5, clickOnPayButton);
		clickOnPayButton.click();
	    }
		
	    public String visibilityOfProcessingPayment(){
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, processingPaymentMessage );
			return processingPaymentMessage.getText();
			
		}
	    
	    public String visibilityOfCopayStepper() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, copayStepper);
			return copayStepper.getText();
			
		}
	    
	    public String visibilityOfPayBalanceTitle() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, payBalanceTitle);
			return payBalanceTitle.getText();
			
		}
		
		public String visibilityOfSubHeadingOfPayBalance() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, subHeadingOfPayBalance);
			return subHeadingOfPayBalance.getText();
			
		}
	    
		public String visibilityOfMinimumPaymentAmountText() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, minimumPaymentAmount);
			return minimumPaymentAmount.getText();
			
		}
		
		public boolean visibilityOfBalanceStepper() {
			IHGUtil.waitForElement(driver, 5, balanceStepper);
			boolean displayed = balanceStepper.isDisplayed();
			if(!displayed) {
				log("balance stepper is not displayed");
				return true;
			}
			else {
				log("balance stepper is displayed");
				return false;
			}
		}
		
		public void patientPrecheck() throws InterruptedException {
			
			log("Completing precheck");
			try {
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, okButton);
				okButton.click();
			}
		
			try {
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, okButton);
				okButton.click();
			}
		
		
			try {
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, okButton);
				okButton.click();
			}
			
			try {
				IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
				skipAndPayInOffice.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, payInOfficeButton);
				payInOfficeButton.click();
			}
			
			try {
				IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
				skipAndPayInOffice.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, payInOfficeButton);
				payInOfficeButton.click();
			}

			try {
				IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
				skipAndFinishLater.click();
			}catch(NoSuchElementException e){
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
			}
				
			iAmDoneButton.click();
			IHGUtil.waitForElement(driver, 5, logOutButton);
			logOutButton.click();
			}
		
		public boolean visibilityOfconfirmTickAfterPrecheck() {
			IHGUtil.waitForElement(driver, 5, confirmTickAfterPrecheck);
			if(confirmTickAfterPrecheck.isDisplayed()) {
				log("precheck for patient is done");
				return true;
			} else {
				log("precheck for patient os not done");
				return false;
			}
		}
		
		public void deleteInsurance() {
			IHGUtil.waitForElement(driver, 5, deleteInsurance);
				deleteInsurance.click();
		}
		
		public void deleteButton() {
			IHGUtil.waitForElement(driver, 5, deleteButton);
				deleteButton.click();
		}
		
		
		public String getMessageAfterDeletingExistingInsurances() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, messageAfterDeletingExistingInsurances);
			return messageAfterDeletingExistingInsurances.getText();
			
		}
		
		public boolean visibilityOfDeleteButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, deleteButton);
			if(deleteButton.isDisplayed()) {
				log("delete button is displayed");
				return true;
			}
			else {
				log("delete button is not displayed");
				return false;
			}
			
		}
		
		public boolean visibilityOfCancelButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, cancelButtonFromRemove);
			if(cancelButtonFromRemove.isDisplayed()) {
				log("cancel button is displayed");
				return true;
			}
			else {
				log("cancel button is not displayed");
				return false;
			}
			
		}
		
		public void addInsurance() {
		IHGUtil.waitForElement(driver, 5, addInsurance );
		addInsurance.click();
		}
		
		public void enterInsuranceName(String InsuranceName) {
		IHGUtil.waitForElement(driver, 5, enterInsuranceName);
		enterInsuranceName.sendKeys(InsuranceName);
		}
		
		public void subscriberId(String SubscriberId) {
		IHGUtil.waitForElement(driver, 5, subscriberId);
		subscriberId.sendKeys(SubscriberId);
		}
		
		public boolean visibilityOfIAmDoneButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, iAmDoneButton);
			if(iAmDoneButton.isDisplayed()) {
				log("I m done button is displayed");
				return true;
				
			}
			else {
				log("Im done button is not displayed");
				return false;
			}
		}
		
		public String getMessageAfterClickingOnImDoneButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, messageAfterClickingOnImdoneButton);
			return messageAfterClickingOnImdoneButton.getText();
		}
		
		public String getPatientModeCompletionMessageClickingOnImDoneButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, patientModeCompletionMessageClickingOnImDoneButton);
			return patientModeCompletionMessageClickingOnImDoneButton.getText();
		}
		
		public boolean visibilityOfLogOutButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, logOutButton);
			if(logOutButton.isDisplayed()) {
				log("logout button is displayed");
				return true;
			}
			else {
				log("logout button is not displayed");
				return false;
			}
		}
		
		public String visibilityOfPrecheckDoneTitle() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, precheckDoneTitle);
			return precheckDoneTitle.getText();
		}
		
		public String visibilityOfProviderTextInApptDetailsPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, providerTextInApptDetailsPage);
			return providerTextInApptDetailsPage.getText();
		}
		
		public String visibilityOfDateAndTimeTextInApptDetailsPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, dateAndTimeTextInApptDetailsPage);
			return dateAndTimeTextInApptDetailsPage.getText();
		}
		
		public String visibilityOfPrecheckInformationTextInApptDetailsPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, precheckInformationTextInApptDetailsPage);
			return precheckInformationTextInApptDetailsPage.getText();
		}
		
		public String visibilityOfPatientFormsTextInApptDetailsPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, patientFormsTextInApptDetailsPage);
			return patientFormsTextInApptDetailsPage.getText();
		}
		
		public boolean visibilityOfUpcomingApptsButtonInApptDetailsPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, upcomingApptsButtonInApptDetailsPage);
			if(upcomingApptsButtonInApptDetailsPage.isDisplayed()) {
				log("upcoming appt is displayed");
				return true;
			}
			else {
				log("upcoming appt is not displayed");
				return false;
			}
		}
		
		public void editPatientInformation() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, editPatientInformation);
			editPatientInformation.click();
		}
		
		public String visibilityOfPrecheckPageTitle() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, precheckPageTitle);
			return precheckPageTitle.getText();
		}
		
		public String visibilityOfInsurancePageTitle() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, insurancePageTitle);
			return insurancePageTitle.getText();
		}
		
		public String visibilityOfPatientNameTextInMenuOfPrecheckPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, patientNameTextInMenuOfPrecheckPage);
			return patientNameTextInMenuOfPrecheckPage.getText();
		}
		
		public String visibilityOfLanguageTextInMenuOfPrecheckPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, languageTextInMenuOfPrecheckPage);
			return languageTextInMenuOfPrecheckPage.getText();
		}
		
		public String visibilityOfOfficeNumberTextInMenuOfPrecheckPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, officeNumberTextInMenuOfPrecheckPage);
			return officeNumberTextInMenuOfPrecheckPage.getText();
		}
		
		public String visibilityOfSignOutTextInMenuOfPrecheckPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, signOutTextInMenuOfPrecheckPage);
			return signOutTextInMenuOfPrecheckPage.getText();
		}
		
		public void clickOnSignOutTextInMenuOfPrecheckPage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, signOutTextInMenuOfPrecheckPage);
			signOutTextInMenuOfPrecheckPage.click();
		}
		
		public boolean visibilityOfLogOutIcon() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, logOutIcon);
			if(logOutIcon.isDisplayed()) {
				log("log out icon is displayed");
				return true;
			}
			else {
				log("log out icon is not displayed");
				return false;
			}
		}
		
		public boolean visibilityOfYesLogMeOutButton() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, yesLogMeOut);
			if(yesLogMeOut.isDisplayed()) {
				log("yes log out icon is displayed");
				return true;
			}
			else {
				log("yes log out icon is not displayed");
				return false;
			}
		}
		
		public void clickYeslogMeOut() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, yesLogMeOut);
			yesLogMeOut.click();
		}
		
		public String getlogOutMessageInPrecheck() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, logOutMessageInPrecheck);
			return logOutMessageInPrecheck.getText();
		}
		
		public void copayDisclaimer() {
			IHGUtil.waitForElement(driver, 5, copayDisclaimerbutton);
			copayDisclaimerbutton.click();
			
		}
		
		public String getCopayDisclaimerText() {
			IHGUtil.waitForElement(driver, 5, copayDisclaimerText);
			return copayDisclaimerText.getText();
			
		}
		
		public void setClipboard(String file) {
			StringSelection obj = new StringSelection(file);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(obj, null);
		}
		
		public void uploadFile(String filePath) throws AWTException {
			setClipboard(filePath);
			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_CONTROL);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_CONTROL);
			rb.keyRelease(KeyEvent.VK_V);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
		}
		
		
		public void addInsuranceImagesFromPrecheck(String insuranceImagePath1 ,String insuranceImagePath2 ,String FirstInsuranceName, String FirstSubscriberId, 
				String SecondInsuranceName, String SecondSubscriberId,
				String ThirdInsuranceName, String ThirdSubscriberId) throws InterruptedException, AWTException {
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
				
				try {
					IHGUtil.waitForElement(driver, 10, addInsuranceCard);
					addInsuranceCard.click();
				}catch(NoSuchElementException e) {
					IHGUtil.waitForElement(driver, 10, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				log("Enter first insurance");
				Actions act = new Actions(driver);
				act.moveToElement(clickOnAddFrontImage).click().perform();
				Thread.sleep(2000);
				uploadFile(System.getProperty("user.dir")+insuranceImagePath1);
				Thread.sleep(3000);
				
				act.moveToElement(clickOnAddBackImage).click().perform();
				Thread.sleep(2000);
				uploadFile(System.getProperty("user.dir")+insuranceImagePath2);
				Thread.sleep(3000);
				
				IHGUtil.waitForElement(driver, 10, enterInsuranceName);
				enterInsuranceName.sendKeys(FirstInsuranceName);
					
				IHGUtil.waitForElement(driver, 10, subscriberId );
				subscriberId.sendKeys(FirstSubscriberId);
					
				IHGUtil.waitForElement(driver, 10, saveAndContinueButton);
				saveAndContinueButton.click();
					
				log("Enter second insurance");
				try {
					IHGUtil.waitForElement(driver, 10, addInsuranceCard);
					addInsuranceCard.click();
				}catch(NoSuchElementException e) {
					IHGUtil.waitForElement(driver, 10, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				act.moveToElement(clickOnAddFrontImage).click().perform();
				Thread.sleep(2000);
				uploadFile(System.getProperty("user.dir")+insuranceImagePath1);
				Thread.sleep(3000);
				
				act.moveToElement(clickOnAddBackImage).click().perform();
				Thread.sleep(2000);
				uploadFile(System.getProperty("user.dir")+insuranceImagePath2);
				Thread.sleep(3000);
				
				IHGUtil.waitForElement(driver, 10, enterInsuranceName);
				enterInsuranceName.sendKeys(SecondInsuranceName);
					
				IHGUtil.waitForElement(driver, 10, subscriberId);
				subscriberId.sendKeys(SecondSubscriberId);
					
				IHGUtil.waitForElement(driver, 10, saveAndContinueButton);
				saveAndContinueButton.click();
				
				log("Enter third insurance");
				try {
					IHGUtil.waitForElement(driver, 10, addInsuranceCard);
					addInsuranceCard.click();
				}catch(NoSuchElementException e) {
					IHGUtil.waitForElement(driver, 10, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				act.moveToElement(clickOnAddFrontImage).click().perform();
				Thread.sleep(2000);
				uploadFile(System.getProperty("user.dir")+insuranceImagePath1);
				Thread.sleep(3000);
				
				act.moveToElement(clickOnAddBackImage).click().perform();
				Thread.sleep(2000);
				uploadFile(System.getProperty("user.dir")+insuranceImagePath2);
				Thread.sleep(3000);
				
				IHGUtil.waitForElement(driver, 10, enterInsuranceName);
				enterInsuranceName.sendKeys(ThirdInsuranceName);
					
				IHGUtil.waitForElement(driver, 10, subscriberId);
				subscriberId.sendKeys(ThirdSubscriberId);
					
				IHGUtil.waitForElement(driver, 20, saveAndContinueButton);
				saveAndContinueButton.click();
				
				IHGUtil.waitForElement(driver, 20, saveAndContinueButton);
				saveAndContinueButton.click();
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}

				try {
					IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
					skipAndFinishLater.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, iAmDoneButton);	
				iAmDoneButton.click();
			
				IHGUtil.waitForElement(driver, 5, logOutButton);
				logOutButton.click();
					
				}
		
		public void clickArrowDropdownForFirstInsurance() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, clickArrowDropdownForFirstInsurance);
			clickArrowDropdownForFirstInsurance.click();
			
		}
		
		public boolean visibilityOfInsuranceImages() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, visibilityOfInsuranceImages);
			if(visibilityOfInsuranceImages.isDisplayed()) {
				log("insurance images are displayed");
				return true;
			}
			else {
				log("insurance images are not displayed");
				return false;
			}
		}
		
		public void clickArrowDropdownForSecondInsurance() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, clickArrowDropdownForSecondInsurance);
			clickArrowDropdownForSecondInsurance.click();
			
		}
		
		public void clickArrowDropdownForThirdInsurance() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 10, clickArrowDropdownForThirdInsurance);
			clickArrowDropdownForThirdInsurance.click();
			
		}
		
		public String getErrorMessageAfterUploadingInvalidImage() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, errorMessageAfterUploadingInvalidImage);
			return errorMessageAfterUploadingInvalidImage.getText();
		}
		
		public void frontInsuranceImage(String insuranceImagePath) throws InterruptedException, AWTException {
			IHGUtil.PrintMethodName();
			Actions act = new Actions(driver);
			act.moveToElement(clickOnAddFrontImage).click().perform();
			Thread.sleep(2000);
			uploadFile(System.getProperty("user.dir")+insuranceImagePath);
		}
		
		public void addInsuranceCard() {
		IHGUtil.waitForElement(driver, 10, addInsuranceCard);
		addInsuranceCard.click();
		
		}
		
		public void clickOnRemoveOptionFromRemoveButton() throws InterruptedException {
			IHGUtil.waitForElement(driver, 20, clickOnRemoveOption);
			clickOnRemoveOption.click();
			
		}
		
		public boolean isPatientPresent(String patientId) {
			IHGUtil.PrintMethodName();
			boolean visibility = false;
			try {
				visibility= driver.findElement(By.xpath("//*[text()='" + patientId + "']")).isDisplayed();
			return visibility;
			}catch(NoSuchElementException e) {
				return visibility;
			}
		}
		
		public void sendBroadcastMessageInEnglish(String messageEn) throws Exception {
			IHGUtil.waitForElement(driver, 10, broadcastMessageInEn);
			broadcastMessageInEn.sendKeys(messageEn);
			jse.executeScript("arguments[0].click();", confirmThisMsgCheckbox);
			IHGUtil.waitForElement(driver, 10, sendMessageButton);
			jse.executeScript("arguments[0].click();", sendMessageButton);
		}
		
		public String getDeletedPatientBanner() {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, deletedPatientBanner);
				return deletedPatientBanner.getText();
		
		}
		
		public void selectPatientsCheckbox() {
			IHGUtil.waitForElement(driver, 5, selectPatientCheckbox);
			selectPatientCheckbox.click();
		}
		
		public void startDate(int backMonth) throws InterruptedException {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, startTime);
			startTime.click();
			log("click on start date filter");

			log("Select Month");
			IHGUtil.waitForElement(driver, 10, months);
			DateFormat monthFormat = new SimpleDateFormat("M");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String currentMonth = monthFormat.format(cal.getTime());
			Integer month = Integer.valueOf(currentMonth);
			String monthValue = String.valueOf(month - backMonth);
			Select selectMonth = new Select(months);
			selectMonth.selectByValue(monthValue);

			log("Select Year");
			IHGUtil.waitForElement(driver, 10, years);
			int yyyy = cal.get(Calendar.YEAR);
			String year = Integer.toString(yyyy);
			Select selectYear = new Select(years);
			selectYear.selectByVisibleText(year);
			log("Year : " + (cal.get(Calendar.YEAR)));

			log("Select Date");
			DateFormat dateFormat = new SimpleDateFormat("d");
			cal.setTime(new Date());
			String currentDate = dateFormat.format(cal.getTime());
			WebElement date = driver.findElement(By.xpath(
					"//*[@id=\"page-content-container\"]/div/header/div[2]/div[1]/div[2]/div[2]/div/div/div[2]/div[2]/div/div[text()="
							+ "'" + currentDate + "'" + "]"));
			date.click();
			log("click on date");
			Thread.sleep(10000);
		}
		
		public void endDate(int backMonth, int backDate) throws InterruptedException {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, endTime);
			endTime.click();
			log("click on start date filter");

			log("Select Month");
			IHGUtil.waitForElement(driver, 10, months);
			DateFormat monthFormat = new SimpleDateFormat("M");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			String currentMonth = monthFormat.format(cal.getTime());
			Integer month = Integer.valueOf(currentMonth);
			String monthValue = String.valueOf(month - backMonth);
			Select selectMonth = new Select(months);
			selectMonth.selectByValue(monthValue);

			log("Select Year");
			IHGUtil.waitForElement(driver, 10, years);
			int yyyy = cal.get(Calendar.YEAR);
			String year = Integer.toString(yyyy);
			Select selectYear = new Select(years);
			selectYear.selectByVisibleText(year);
			log("Year : " + (cal.get(Calendar.YEAR)));

			log("Select Date");
			DateFormat dateFormat = new SimpleDateFormat("d");
			cal.setTime(new Date());
			String currentDate = dateFormat.format(cal.getTime());
			Integer curDate = Integer.valueOf(currentDate);
			String dateValue = String.valueOf(curDate - backDate);
			WebElement date = driver.findElement(By.xpath(
					"//*[@class=\"react-datepicker__month-container\"]/div[2]//div[text()="+ "'" + dateValue + "'" + "]"));
			date.click();
			log("click on date");
			jse.executeScript("arguments[0].click();", selectTime);
			Thread.sleep(10000);
	}
		

		public void clickOnContinue() throws InterruptedException {
			IHGUtil.PrintMethodName();
			IHGUtil.waitForElement(driver, 5, clickOnContinue);
			jse.executeScript("arguments[0].click();", clickOnContinue);
			Thread.sleep(10000);
			
		}

		
		public boolean getFormInPrecheckFlow(String firstName, String middleName,
				String lastName, String email, String PhoneNo,String form) throws InterruptedException {
			
			IHGUtil.waitForElement(driver, 40, precheckPageTitle);
			
			fName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			fName.sendKeys(Keys.BACK_SPACE);
			fName.sendKeys(firstName);

			midName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			midName.sendKeys(Keys.BACK_SPACE);
			midName.sendKeys(middleName);

			lName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			lName.sendKeys(Keys.BACK_SPACE);
			lName.sendKeys(lastName);
			log("Enter first name , middle name and last name");

			phoneNo.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			phoneNo.sendKeys(Keys.BACK_SPACE);
			phoneNo.sendKeys(PhoneNo);

			mail.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			mail.sendKeys(Keys.BACK_SPACE);
			mail.sendKeys(email);
			submit.click();
			
			log("Completing precheck");
				try {
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, okButton);
					okButton.click();
				}
				
				IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
				saveAndContinueButton.click();
			
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}
				
				try {
					IHGUtil.waitForElement(driver, 5, skipAndPayInOffice);
					skipAndPayInOffice.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, payInOfficeButton);
					payInOfficeButton.click();
				}

				try {
					IHGUtil.waitForElement(driver, 5, skipAndFinishLater);
					skipAndFinishLater.click();
				}catch(NoSuchElementException e){
					IHGUtil.waitForElement(driver, 5, saveAndContinueButton);
					saveAndContinueButton.click();
				}
				
				boolean visibility = false;
				try {
					visibility=driver.findElement(By.xpath("//p[text()='"+form+"']")).isDisplayed();
						log("Form is displayed");
						return visibility;
					} catch (NoSuchElementException e) {
						log("Form is not displayed");
						
					}
				
				jse.executeScript("arguments[0].click();", clickOnContinue);
					
				iAmDoneButton.click();
				IHGUtil.waitForElement(driver, 5, logOutButton);
				logOutButton.click();
				return visibility;
				}
		
		
		}




