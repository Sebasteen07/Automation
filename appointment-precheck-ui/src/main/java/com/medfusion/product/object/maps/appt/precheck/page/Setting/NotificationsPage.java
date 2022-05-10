// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.page.Setting;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
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

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.pojo.Appointment;

public class NotificationsPage extends BasePageObject {

	@FindBy(how = How.XPATH, using = "//li[@class='mf-nav__item mf-nav__secondary-menu__item']/a")
	private WebElement notificationTab;

	@FindBy(how = How.CSS, using = "div.notification-features-checkbox > div:nth-child(3) > input")
	private WebElement displayPatientFirstNameCheckbox;

	@FindBy(how = How.XPATH, using = "//button[text()='Save']")
	private WebElement saveButton;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-body-header mf-appointments-header']/h1[text()='Notifications']")
	private WebElement notificationTitle;

	@FindBy(how = How.XPATH, using = "(//*[@class='features-checkbox'])[1]")
	private WebElement broadcastMessagingCheckbox;

	@FindBy(how = How.XPATH, using = "//*[@id='off']")
	private WebElement offNotificationButton;

	@FindBy(how = How.XPATH, using = " //*[@id='on']")
	private WebElement onNotificationButton;

	@FindBy(how = How.XPATH, using = "(//*[@class='body-opacity'])[3]")
	private WebElement apptReminderEmailhumburgerButton;

	@FindBy(how = How.XPATH, using = "//*[@class=\"dropdown\"]/ul/li[1]")
	private WebElement apptReminderEmailEditButton;

	@FindBy(how = How.CSS, using = "li.mf-nav__item.disabled > a")
	private WebElement previewInEditButton;

	@FindBy(how = How.XPATH, using = "//*[text()='Build']")
	private WebElement buildInEditButton;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-icon mf-icon__navbar-back-arrow']")
	private WebElement clickOnBackArrow;

	@FindBy(how = How.XPATH, using = "(//*[@class=\"right\"])[6]/child::div[@class='table-data']/div/span")
	private WebElement apptReminderSmshumburgerButton;

	@FindBy(how = How.XPATH, using = "//*[@class=\"dropdown\"]/ul/li[1]")
	private WebElement apptReminderSmsEditButton;

	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[1]")
	private WebElement oneDaysTimeUnitTextBox;

	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[2]")
	private WebElement threeDaysTimeUnitTextBox;

	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[3]")
	private WebElement minutesTimeUnitTextBox;

	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[4]")
	private WebElement hoursTimeUnitTextBox;

	@FindBy(how = How.XPATH, using = "(//*[text()='Invalid Units'])[1]")
	private WebElement invalidTextForOneDays;

	@FindBy(how = How.XPATH, using = "(//*[text()='Invalid Units'])[2]")
	private WebElement invalidTextForThreeDays;

	@FindBy(how = How.XPATH, using = "(//*[text()='Invalid Units'])[3]")
	private WebElement invalidTextForminutes;

	@FindBy(how = How.XPATH, using = "(//*[text()='Invalid Units'])[4]")
	private WebElement invalidTextForhours;

	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[1]")
	private WebElement hoursTimeUnitEmailtBox;

	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[2]")
	private WebElement minutesTimeUnitEmailBox;

	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[3]")
	private WebElement daysTimeUnitEmailBox;

	@FindBy(how = How.XPATH, using = "(//*[text()='Invalid Units'])[1]")
	private WebElement invalidTextForHours;

	@FindBy(how = How.XPATH, using = "(//*[text()='Invalid Units'])[2]")
	private WebElement invalidTextForMinutes;

	@FindBy(how = How.XPATH, using = "(//*[text()='Invalid Units'])[3]")
	private WebElement invalidTextForDays;

	@FindBy(how = How.XPATH, using = "(//*[@class=\"right\"])[2]/child::div[@class='table-data']/div/span")
	private WebElement apptConfirmationsEmailhumburgerButton;

	@FindBy(how = How.XPATH, using = "(//*[@class=\"right\"])[3]/child::div[@class='table-data']/div/span")
	private WebElement apptConfirmationsSmshumburgerButton;

	@FindBy(how = How.XPATH, using = "//*[text()=\"Preview\"]")
	private WebElement previewButton;

	@FindBy(how = How.XPATH, using = "//*[text()=\"Viewing in: \"]")
	private WebElement viewingInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[@class=\" css-1hwfws3\"])[1]")
	private WebElement englishLangInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()=\"Settings\"])[1]")
	private WebElement settingInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()=\"Close\"]")
	private WebElement closeButtonInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()=\"Notification Type:\"]")
	private WebElement notifTypeInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()=\"Appointment Confirmation\"]")
	private WebElement apptConfInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()=\"Version:\"]")
	private WebElement versionInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()=\"v2\"])[1]")
	private WebElement v2InPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Appointment Method:']")
	private WebElement apptMethodInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='In Office'])[1]")
	private WebElement inOfficeInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='Upon Scheduling'])[1]")
	private WebElement uponSchedulingInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Delivery Method:']")
	private WebElement deliveryMethodInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='Email'])[1]")
	private WebElement emailInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='SMS'])[1]")
	private WebElement smsInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='Timing:'])[1]")
	private WebElement timingInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='Timing:'])[1]/following-sibling::div")
	private WebElement allTimingInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Resource(s): ']")
	private WebElement resourcesInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Resource(s): ']/following-sibling::div")
	private WebElement allResourcesInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='Appointment Type(s): '])[1]")
	private WebElement apptTypesInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Appointment Type(s): ']/following-sibling::div")
	private WebElement allApptTypesInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Location(s): ']")
	private WebElement locationInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Location(s): ']/following-sibling::div")
	private WebElement allLocationInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[@class=\"sidebar-bottom-nav\"]/div)[1]")
	private WebElement desktopIcon;

	@FindBy(how = How.XPATH, using = "(//*[@class=\"sidebar-bottom-nav\"]/div)[2]/span")
	private WebElement mobileIcon;

	@FindBy(how = How.XPATH, using = "//*[text()=\"CLICK HERE\"]")
	private WebElement clickHereText;

	@FindBy(how = How.XPATH, using = "//*[text()=\"TO VIEW THIS IN A\"]")
	private WebElement toViewThisInAText;

	@FindBy(how = How.XPATH, using = "//*[text()=\" BROWSER WINDOW\"]")
	private WebElement browserWindowText;

	@FindBy(how = How.XPATH, using = "//img[@class='cadence-img']")
	private WebElement cadenceImage;

	@FindBy(how = How.XPATH, using = "//*[text()='Appointment Scheduled']")
	private WebElement apptScheduledText;

	@FindBy(how = How.XPATH, using = "//*[text()='[Patient Name], your appointment has']")
	private WebElement patienNameText;

	@FindBy(how = How.XPATH, using = "//*[text()='been scheduled']")
	private WebElement beenScheduledText;

	@FindBy(how = How.XPATH, using = "//*[text()='Date and Time']")
	private WebElement dateAndTimeText;

	@FindBy(how = How.XPATH, using = "//*[text()='Day of the week at 00:00 AM/PM']")
	private WebElement dayOfTheWeekText;

	@FindBy(how = How.XPATH, using = "//*[text()=' Month DD, YYYY']")
	private WebElement dateFormat;

	@FindBy(how = How.XPATH, using = "//*[text()='Location']")
	private WebElement locationText;

	@FindBy(how = How.XPATH, using = "//*[text()='Location Name']")
	private WebElement locationNameText;

	@FindBy(how = How.XPATH, using = "//*[text()='Location Address1']")
	private WebElement address1Text;

	@FindBy(how = How.XPATH, using = "//*[text()='Location Address2']")
	private WebElement address2Text;

	@FindBy(how = How.XPATH, using = "//*[text()='City State, Zip']")
	private WebElement cityStateZipText;

	@FindBy(how = How.XPATH, using = "//*[text()='(XXX) XXX-XXXX']")
	private WebElement mobileNoFormat;

	@FindBy(how = How.XPATH, using = "//*[text()='Pin on Map']")
	private WebElement pinToMapText;

	@FindBy(how = How.XPATH, using = "//*[text()='Provider']")
	private WebElement providerText;

	@FindBy(how = How.XPATH, using = "//*[text()='Provider Name']")
	private WebElement providerNameText;

	@FindBy(how = How.XPATH, using = "//*[text()='Reschedule or Cancel']")
	private WebElement rescheduleOrCancelText;

	@FindBy(how = How.XPATH, using = "//*[@class='pb-20']")
	private WebElement instructions;

	@FindBy(how = How.XPATH, using = "//*[text()='Confidentiality Notice:']")
	private WebElement confidentialityNoticeText;

	@FindBy(how = How.XPATH, using = "//*[text()='Unsubscribe']")
	private WebElement unsubscribeText;

	@FindBy(how = How.XPATH, using = "//*[@class='preview-bottom']")
	private WebElement bottomInfo;

	@FindBy(how = How.XPATH, using = "(//*[@class='text'])[1]")
	private WebElement patientInfoInTextMsg;

	@FindBy(how = How.XPATH, using = "(//*[@class='text'])[2]")
	private WebElement rescheAndCancelText;

	@FindBy(how = How.XPATH, using = "(//*[@class='text'])[3]")
	private WebElement getDitectionText;

	@FindBy(how = How.XPATH, using = "(//*[@class='text'])[4]")
	private WebElement textStopToUnsubscribe;

	@FindBy(how = How.XPATH, using = "//div[@class='sms-preview-bottom']")
	private WebElement smsPreviewBottom;

	@FindBy(how = How.XPATH, using = "//*[text()='Your Logo Here']")
	private WebElement newPracticeBlankLogoText;

	@FindBy(how = How.XPATH, using = "//*[text()='Confirm Appointment']")
	private WebElement confirmApptButton;

	@FindBy(how = How.XPATH, using = "//*[text()='Start PreCheck']")
	private WebElement startPreCheck;

	@FindBy(how = How.XPATH, using = "//*[text()='Appointment reminders']")
	private WebElement apptReminderText;

	@FindBy(how = How.XPATH, using = "//*[@class='pt-20']")
	private WebElement apptComingUpMessage;

	@FindBy(how = How.XPATH, using = "(//*[text()='Appointment Reminder'])[1]")
	private WebElement apptReminderInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='Edit'])[1]")
	private WebElement editButtonInPreviewPage;

	@FindBy(how = How.XPATH, using = "(//*[text()='Default'])[1]")
	private WebElement defaultTextInPreviewPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Editing:']")
	private WebElement emailEditingPage;

	@FindBy(how = How.XPATH, using = "//*[text()='Design']")
	private WebElement emaildesignPage;

	@FindBy(how = How.XPATH, using = "(//*[@class='text'])[2]")
	private WebElement confirmApptlink;

	@FindBy(how = How.XPATH, using = "(//*[@class='text'])[3]")
	private WebElement rescheduleAndCancelLink;

	@FindBy(how = How.XPATH, using = "(//*[@class='text'])[4]")
	private WebElement getDirectionLink;

	@FindBy(how = How.XPATH, using = "(//*[@class='text'])[5]")
	private WebElement textToStopUnsubscribe;

	@FindBy(how = How.XPATH, using = "//div[@class='appointment-data-tab-icon']")
	private WebElement apptDataTraingleTab;

	@FindBy(how = How.XPATH, using = "//*[text()='Will not affect Appointment Confirmations sent via PSS']")
	private WebElement sendNotifTraingleTabText;

	@FindBy(how = How.XPATH, using = "//*[@class='cadence-select-dropdown']")
	private WebElement setTiming;

	@FindBy(how = How.XPATH, using = "//button[text()='Save changes']")
	private WebElement saveChangesButton;

	@FindBy(how = How.XPATH, using = "//li[contains(text(),'Curbside check-in')]")
	private WebElement curbsideCheckInTabInNotif;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'English')]")
	private WebElement englishButton;

	@FindBy(how = How.XPATH, using = "//textarea[contains(text(),'hello welcome to curbside checkin')]")
	private WebElement additionalArrivalInstrInEnglish;

	@FindBy(how = How.XPATH, using = "//textarea[contains(text(),'hola bienvenido al registro en la acera')]")
	private WebElement additionalArrivalInstrInSpanish;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Spanish')]")
	private WebElement spanishButton;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Arrival confirmation message')]")
	private WebElement arrivalConfirmationMsg;

	@FindBy(how = How.XPATH, using = "	//*[text()='Appointment confirmations']")
	private WebElement apptConfirmationsText;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')][1]")
	private WebElement appointmentRemindersText;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[text()='Email'][1]")
	private WebElement emailAppointmentConfirmations;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[text()='SMS'][1]")
	private WebElement smsAppointmentConfirmations;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[text()='Email']")
	private WebElement emailAppointmentReminders;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[text()='SMS']")
	private WebElement smsAppointmentReminders;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[text()='Published'][1]")
	private WebElement publishedStatusEmailForApptConfirmations;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[text()='Published'][2]")
	private WebElement publishedStatusSmsForApptConfirmations;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[text()='Published'][1]")
	private WebElement publishedStatusEmailForApptReminders;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[text()='Published'][2]")
	private WebElement publishedStatusSmsApptReminders;

	@FindBy(how = How.XPATH, using = "//div[@data-for='notification-info-confirmation']//span[@class='mf-icon mf-icon__notification-heading-information']")
	private WebElement aptConfirmationInfoIcon;

	@FindBy(how = How.XPATH, using = "//*[text()='Appointment confirmations are communications sent to notify a patient that an appointment has been scheduled.']")
	private WebElement aptConfirmationInfoIconText;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Delivery Method')]")
	private WebElement deliveryMethodOfAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[contains(text(),'Default')][1]")
	private WebElement defaultTextEmailAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[contains(text(),'Default')][2]")
	private WebElement defaultTextSMSAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[contains(text(),'v2')]")
	private WebElement v2TextEmailAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[contains(text(),'Default')]")
	private WebElement defaultTextSMSAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[contains(text(),'Version')]")
	private WebElement versionTextAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[contains(text(),'Appt. Method')][1]")
	private WebElement apptMethodTextAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'In Office')]")
	private WebElement inOfficeTextEmailAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'In Office')]")
	private WebElement inOfficeTextSMSAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[contains(text(),'Upon Scheduling')][1]")
	private WebElement uponSchedulingEmailAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[contains(text(),'Upon Scheduling')][2]")
	private WebElement uponSchedulingSMSAptConf;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Upon Scheduling')]//preceding::div[contains(text(),'Timing')]")
	private WebElement timingText;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment confirmations')]//following::div[contains(text(),'Status')][1]")
	private WebElement statusTextAptConf;

	@FindBy(how = How.XPATH, using = "(//span[@class='mf-icon mf-icon__notification-heading-information'])[2]")
	private WebElement aptRemInfoIcon;

	@FindBy(how = How.XPATH, using = "//*[text()='Appointment reminders are communications sent to remind a patient they have an upcoming appointment.']")
	private WebElement aptRemindersInfoIconText;

	@FindBy(how = How.XPATH, using = "(//div[contains(text(),'Delivery Method')])[2]")
	private WebElement delivaryMtdForApptRemindert;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[contains(text(),'Version')]")
	private WebElement versionTextAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[contains(text(),'Appt. Method')]")
	private WebElement apptMethodTextAptRem;

	@FindBy(how = How.XPATH, using = "(//div[contains(text(),'In Office')])[3]")
	private WebElement inOfficeTextEmailAptRem;

	@FindBy(how = How.XPATH, using = "(//div[contains(text(),'In Office')])[4]")
	private WebElement inOfficeTextSMSAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Timing')]//following::div[contains(text(),'Days')][1]")
	private WebElement daysTextEmailAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Timing')]//following::div[contains(text(),'Hours')][1]")
	private WebElement hoursTextEmailAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Timing')]//following::div[contains(text(),'Minutes')][1]")
	private WebElement minutesTextEmailAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Timing')]//following::div[contains(text(),'Days')][2]")
	private WebElement daysTextSMSAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Timing')]//following::div[contains(text(),'Hours')][2]")
	private WebElement hoursTextSMsAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Timing')]//following::div[contains(text(),'Minutes')][2]")
	private WebElement minutesTextSMSAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Upon Scheduling')]//following::div[contains(text(),'Timing')][1]")
	private WebElement timingTextUnderAptRem;

	@FindBy(how = How.XPATH, using = "//div[@class='table-content']//div[contains(text(),'1')]")
	private WebElement oneTimeUnit;

	@FindBy(how = How.XPATH, using = "//div[@class='table-content']//div[contains(text(),'30')]")
	private WebElement thirtyTimeUnit;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Timing Units')]")
	private WebElement timingunitsTextUnderAptRem;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment reminders')]//following::div[contains(text(),'Status')]")
	private WebElement statusTextAptRem;

	@FindBy(how = How.XPATH, using = "//li[contains(text(),'Edit')]")
	private WebElement editTextUnderHamburgerButton;

	@FindBy(how = How.XPATH, using = "//textarea[contains(text(),'hello welcome to curbside checkin')]")
	private WebElement additionalArrivalInstructionsTextBoxEn;

	@FindBy(how = How.XPATH, using = "//textarea[@name='arrivalsConfirmationInstructionsEN']")
	private WebElement additionalArrivalInstructionsTextBoxText;

	@FindBy(how = How.XPATH, using = "//textarea[contains(text(),'hola bienvenido al registro en la acera')]")
	private WebElement additionalArrivalInstructionsTextBoxEs;

	@FindBy(how = How.XPATH, using = "(//input[@class='mf-notification-checkbox'])[2]")
	private WebElement curbsideCheckInReminderCheckbox;

	@FindBy(how = How.XPATH, using = "//div[@class=' css-tlfecz-indicatorContainer']")
	private WebElement practicePrefenceLangDropDown;

	@FindBy(how = How.XPATH, using = "//*[text()='English & Spanish']")
	private WebElement englishSpanishPracticePrefenceLang;

	@FindBy(how = How.XPATH, using = "//*[text()='English']")
	private WebElement englishPracticePrefenceLang;

	@FindBy(how = How.XPATH, using = "//h1[contains(text(),'Features')]")
	private WebElement featureText;

	@FindBy(how = How.XPATH, using = " //label[contains(text(),'ON')]")
	private WebElement onText;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'OFF')]")
	private WebElement offText;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Broadcast messaging')]")
	private WebElement broadcastText;

	@FindBy(how = How.XPATH, using = "//div[@class='notification-features-checkbox']/div[2]/input[@type='checkbox']")
	private WebElement curbsideCheckbox;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Curbside check-in reminder')]")
	private WebElement curbsideText;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Notification Type')]")
	private WebElement notifTypeText;

	@FindBy(how = How.XPATH, using = "//div[@class='notification-heading'and contains(text(),'Appointment confirmations')]")
	private WebElement apptConfirmText;

	@FindBy(how = How.XPATH, using = "//div[@class='curbside-tab']/p")
	private WebElement curbsidePara;

	@FindBy(how = How.XPATH, using = "//li[@id='react-tabs-10']")
	private WebElement curbOption;

	@FindBy(how = How.XPATH, using = "//div[@class='notification-features-checkbox']/div[3]/input[@type='checkbox']")
	private WebElement patientNameCheckbox;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),\"Display patient's first name\")]")
	private WebElement patientFirstText;

	@FindBy(how = How.XPATH, using = "(//div[@class='table-data']/div)[3]")
	private WebElement daysText;

	@FindBy(how = How.XPATH, using = "(//div[@class='table-data']/div)[4]")
	private WebElement timingUnitText;

	@FindBy(how = How.XPATH, using = "//button[text()='+ Add']")
	private WebElement addButtonInEdit;

	@FindBy(how = How.XPATH, using = "(//div[@class='cadence-remove-icon'])[4]")
	private WebElement removeButtonInEdit;

	@FindBy(how = How.XPATH, using = "(//div[@class='table-data']/div)[4]")
	private WebElement hoursText;

	@FindBy(how = How.XPATH, using = "(//div[@class='table-data']/div)[5]")
	private WebElement minutesText;

	@FindBy(how = How.XPATH, using = "(//div[@class='table-data']/div)[6]")
	private WebElement timeUnitTextForDays;

	@FindBy(how = How.XPATH, using = "(//div[@class='table-data']/div)[7]")
	private WebElement timeUnitTextForHours;

	@FindBy(how = How.XPATH, using = "(//div[@class='table-data']/div)[8]")
	private WebElement timeUnitTextForMinutes;

	@FindBy(how = How.CSS, using = "div:nth-child(2) > div.left > div:nth-child(4) > div")
	private WebElement timingTextForSMS;

	@FindBy(how = How.CSS, using = "div.table-content > div:nth-child(2) > div.gap > div > div")
	private WebElement timingUnitTextForSMS;

	@FindBy(how = How.CSS, using = "div:nth-child(4) > div.version-value")
	private WebElement deliveryMethod;

	@FindBy(how = How.CSS, using = "div:nth-child(2) > div.left > div:nth-child(4)>div:nth-child(1)")
	private WebElement daysTimingTextForSMS;

	@FindBy(how = How.CSS, using = "div:nth-child(2) > div.left > div:nth-child(4)>div:nth-child(2)")
	private WebElement hoursTimingTextForSMS;

	@FindBy(how = How.CSS, using = "div:nth-child(2) > div.left > div:nth-child(4)>div:nth-child(3)")
	private WebElement minutesTimingTextForSMS;

	@FindBy(how = How.CSS, using = "div:nth-child(2) > div.gap > div > div:nth-child(1)")
	private WebElement daysTimeUnitTextForSMS;

	@FindBy(how = How.CSS, using = "div:nth-child(2) > div.gap > div > div:nth-child(2)")
	private WebElement hoursTimeUnitTextForSMS;

	@FindBy(how = How.CSS, using = "div:nth-child(2) > div.gap > div > div:nth-child(3)")
	private WebElement minutesTimeUnitTextForSMS;

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Check-In')]")
	private WebElement checkinButton;

	@FindBy(how = How.XPATH, using = "(//input[@type='checkbox'])[2]")
	private WebElement selectOnePatient;

	@FindBy(how = How.XPATH, using = "(//input[@class='mf-notification-checkbox'])[2]")
	private WebElement curbsideCheckinRemCheckbox;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Appointment Method']")
	private WebElement apptMethodtextInEmail;
	
	@FindBy(how = How.XPATH, using = "//textarea[@class='mf-form__input--text-area']")
	private WebElement arrivalInstTextbox;
	
	@FindBy(how = How.XPATH, using = "//label[@class='number-of-characters']")
	private WebElement numOfCharacter;
	
	@FindBy(how = How.XPATH, using = "//div[@class='language-tab']")
	private WebElement arrivalConfirmMsg;
	
	@FindBy(how=How.XPATH, using ="//div[@class='heading d-flex-row']")
	private WebElement editPageTemplate;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='version'])[5]")
	private WebElement versionTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='version-value'])[5]")
	private WebElement versionV2TextInEditPage;

	@FindBy(how=How.XPATH, using ="//img[@class='cadence-img']")
	private WebElement logoInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[@class='template-heading']")
	private WebElement appointmentReminderTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//button[@id='submitNotifications']")
	private WebElement confirmAppointmentButtonInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[@class='template-sub-heading']")
	private WebElement startPrecheckLinkInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[text()='[Patient Name], your appointment is coming up.']")
	private WebElement patientTextComingUpInEditPage;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='bold-heading'])[1]")
	private WebElement dateAndTimeTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Day of the week at 00:00 AM/PM')]")
	private WebElement dayOfTheWeekTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Month DD, YYYY')]")
	private WebElement monthDDYYYYFormatTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='bold-heading'])[2]")
	private WebElement locationTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Location Name')]")
	private WebElement locationNameTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Location Address1')]")
	private WebElement locationAddress1TextInEditPage;

	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Location Address2')]")
	private WebElement locationAddress2TextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'City State, Zip')]")
	private WebElement cityStateZipTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'(XXX) XXX-XXXX')]")
	private WebElement numberTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Pin on Map')]")
	private WebElement pinOnMapLinkTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='bold-heading'])[3]")
	private WebElement providerTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Provider Name')]")
	private WebElement providerNameTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[contains(text(),'Reschedule or Cancel')]")
	private WebElement rescheduleOrCancelLinkTextInEditPage;
	
	@FindBy(how=How.XPATH, using ="//div[@class='heading d-flex-row']")
	private WebElement editPageTemplateForText;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='version'])[5]")
	private WebElement versionTextInEditPageForText;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='version-value'])[5]")
	private WebElement defaultTextInEditPageForText;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='text'])[1]")
	private WebElement patientTextInEditPageForText;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='text'])[2]")
	private WebElement confirmTextInEditPageForText;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='text'])[3]")
	private WebElement rescheduleCancelTextInEditPageForText;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='text'])[4]")
	private WebElement directionsTextInEditPageForText;
	
	@FindBy(how=How.XPATH, using ="(//div[@class='text'])[5]")
	private WebElement stopToUnsubscribeTextInEditPageForText;
	
	@FindBy(how = How.XPATH, using = "//li[text()='Settings']")
	private WebElement settingTabunderDesignsection;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Notification Type')]")
	private WebElement notificationTypetextUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Appointment Reminder')]")
	private WebElement appointmentReminderstextUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Version Name')]")
	private WebElement versionTextunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='version-value'])[2]")
	private WebElement v2TextunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='version'])[3]")
	private WebElement apointmentMethodTextunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='version-value'])[3]")
	private WebElement inOfficeTextunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='version'])[4]")
	private WebElement deliveryMethodTextunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[text()='Days'])[1]")
	private WebElement timingtextUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "//input[@value='1']")
	private WebElement timingUnitstextUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "(//div[text()='Days'])[2]")
	private WebElement defaultTimingtextUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "//input[@value='3']")
	private WebElement defaultTimingUnitstUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "(//div[text()='Days'])[3]")
	private WebElement defaultTimingstextUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "//input[@value='5']")
	private WebElement defaultTimingUnitstextUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "//button[text()='+ Add']")
	private WebElement ClickonAddbutton;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-tlfecz-indicatorContainer'])[4]")
	private WebElement clickOntimingdropdownunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[text()='Days'])[2]")
	private WebElement selectDaydropdownunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[4]")
	private WebElement enterTimingunitUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "(//span[@class='mf-icon mf-icon__cadence-setting-remove mf-icon-pointer'])[4]")
	private WebElement deleteTimingunitUnderdesignTab;
	
	@FindBy(how = How.XPATH, using = "//div[@class=' css-qc6sy-singleValue']")
	private WebElement prcticeLanguage;
	
	@FindBy(how = How.XPATH, using = "(//input[@type='checkbox'])[3]")
	private WebElement disableDisplayPatientFirstNameCheckbox;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-tlfecz-indicatorContainer'])[1]")
	private WebElement clickTimingDropdownunderDesigntab;  
	
	@FindBy(how = How.XPATH, using = "//div[contains(text() ,'Hours')]")
	private WebElement selectHourDropdownunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[1]")
	private WebElement enterTimingUnitunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-tlfecz-indicatorContainer'])[2]")
	private WebElement click2ndTimingDropdownunderDesigntab;  
	
	@FindBy(how = How.XPATH, using = "//div[text()='Minutes']")
	private WebElement selectMinutesTimingDropdownunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[2]")
	private WebElement enter2ndTimingUnitunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-tlfecz-indicatorContainer'])[3]")
	private WebElement click3rdTimingDropdownunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text() , 'Days')]")
	private WebElement selectDayDropdownunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[3]")
	private WebElement enter3rdTimingUnitunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[1]")
	private WebElement enter1stMinutesTimingUnitunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[2]")
	private WebElement enter2ndHoursTimingUnitunderDesignTab;
	
	@FindBy(how = How.XPATH, using = "(//div[contains(text() , 'Hours')])[2]")
	private WebElement select2ndHourDropdownunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[3]")
	private WebElement enter3rdHourtimingUnitunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-tlfecz-indicatorContainer'])[4]")
	private WebElement click4thTimingDropdownunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[4]")
	private WebElement enter4thTimingUnitunderDesigntab;
	
	@FindBy(how = How.XPATH, using = "//label[@class='number-of-characters']")
	private WebElement characterCount;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='table-data'])[14]")
	private WebElement timing;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='table-data'])[15]")
	private WebElement timingunit;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='table-data'])[14]")
	private WebElement timingForEmail;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='table-data'])[15]")
	private WebElement timingUnitForEmail;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='table-data'])[20]")
	private WebElement timingForSMS;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='table-data'])[21]")
	private WebElement timingUnitForSMS;
	
	@FindBy(how = How.XPATH, using = "(//div[text()='Days'])[1]")
	private WebElement defaultTiming1ForDay;
	
	@FindBy(how = How.XPATH, using = "(//div[text()='Days'])[2]")
	private WebElement defaultTiming2ForDay;

	@FindBy(how = How.XPATH, using = "(//div[text()='Days'])[3]")
	private WebElement defaultTiming3ForDay;
	
	@FindBy(how = How.XPATH, using = "//input[@value='1']")
	private WebElement defaultTimingUnit1ForDay;
	
	@FindBy(how = How.XPATH, using = "//input[@value='3']")
	private WebElement defaultTimingUnit2ForDay;
	
	@FindBy(how = How.XPATH, using = "//input[@value='5']")
	private WebElement defaultTimingUnit3ForDay;
	
	@FindBy(how = How.XPATH, using = "//div[text()='Hours']")
	private WebElement selectHourforTiming;
	
	@FindBy(how = How.XPATH, using = "//div[text() = 'Minutes']")
	private WebElement selectMinutesforTiming;

	@FindBy(how = How.XPATH, using = "(//input[@value='12345678904567889990432225566771117656884323456777'])[1]")
	private WebElement timingUnitTextUnderDesignTab;
	
	@FindBy(how = How.XPATH, using = "(//input[@class='cadence-period-value'])[1]")
	private WebElement enterTimingUnit;

	@FindBy(how = How.XPATH, using = "(//span[@class='mf-icon mf-icon__cadence-setting-remove mf-icon-pointer'])[3]")
	private WebElement removeButtonInEditFor3rdTimingUnit;
	
	@FindBy(how = How.XPATH, using = "(//span[@class='mf-icon mf-icon__cadence-setting-remove mf-icon-pointer'])[2]")
	private WebElement removeButtonInEditFor2ndTimingUnit;
	
	@FindBy(how = How.XPATH, using = "(//span[@class='mf-icon mf-icon__cadence-setting-remove mf-icon-pointer'])[1]")
	private WebElement removeButtonInEditFor1stTimingUnit;
	
	@FindBy(how = How.XPATH, using = "//div[contains(text() ,'Timing Units')]")
	private WebElement timingUnitsInNotifpage;

	@FindBy(how = How.XPATH, using = "(//div[@class=' css-1y2qbuw'])[1]")
	private WebElement firstTimingAndTimingUnit;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-1y2qbuw'])[2]")
	private WebElement secondTimingAndTimingUnit;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-1y2qbuw'])[3]")
	private WebElement thirdTimingAndTimingUnit;
	
	@FindBy(how = How.XPATH, using = "(//div[@class=' css-1y2qbuw'])[4]")
	private WebElement fourthTimingAndTimingUnit;

	public NotificationsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void clickOnNotificationTab() {
		IHGUtil.waitForElement(driver, 5, notificationTab);
		jse.executeScript("arguments[0].click();", notificationTab);
		log("Switch on notification tab");
	}

	public void displayPatientFirstNameCheckbox() throws InterruptedException {
		boolean selected = displayPatientFirstNameCheckbox.isSelected();
		if (!selected) {
			log("Enable 'Display patient's' first name' checkbox");
			displayPatientFirstNameCheckbox.click();
			Thread.sleep(3000);
		}
	}

	public void saveNotification() throws InterruptedException {
		IHGUtil.waitForElement(driver, 5, saveButton);
		jse.executeScript("arguments[0].click();", saveButton);
		Thread.sleep(10000);
	}

	public void enablePatientFirstNameCheckbox() throws InterruptedException {
		clickOnNotificationTab();
		displayPatientFirstNameCheckbox();
		saveNotification();
	}

	public String getNotificationTitle() {
		IHGUtil.waitForElement(driver, 5, notificationTitle);
		return notificationTitle.getText();
	}

	public void disableBroadcastMessagingCheckbox() throws InterruptedException {
		driver.navigate().refresh();
		IHGUtil.waitForElement(driver, 5, broadcastMessagingCheckbox);
		boolean selected = broadcastMessagingCheckbox.isSelected();
		if (selected) {
			broadcastMessagingCheckbox.click();
		} else if (!selected) {
			log("Enable 'Broadcast Messaging' checkbox");
			broadcastMessagingCheckbox.click();
		}
		Thread.sleep(10000);
	}

	public void enableBroadcastMessagingCheckbox() throws InterruptedException {
		driver.navigate().refresh();
		IHGUtil.waitForElement(driver, 5, broadcastMessagingCheckbox);
		log("Enable 'Broadcast Messaging' checkbox");
		broadcastMessagingCheckbox.click();
		Thread.sleep(10000);
	}

	public void offNotification() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, offNotificationButton);
		offNotificationButton.click();
	}

	public void onNotification() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, onNotificationButton);
		onNotificationButton.click();
	}

	public void clickApptReminderEmailHamburgerButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, apptReminderEmailhumburgerButton);
		apptReminderEmailhumburgerButton.click();
	}

	public void clickApptReminderEmailEditButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, apptReminderEmailEditButton);
		apptReminderEmailEditButton.click();
	}

	public void clickApptReminderSmsHamburgerButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, apptReminderSmshumburgerButton);
		jse.executeScript("arguments[0].click();", apptReminderSmshumburgerButton);
	}

	public void clickApptReminderSmsEditButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, apptReminderSmsEditButton);
		apptReminderSmsEditButton.click();
	}

	public void clickOnPreview() {
		IHGUtil.waitForElement(driver, 10, previewInEditButton);
		previewInEditButton.click();
	}

	public boolean previewTabInEditButton() {
		IHGUtil.waitForElement(driver, 10, previewInEditButton);
		if (previewInEditButton.isSelected()) {
			System.out.print("Preview tab from edit is enabled.");
			return true;
		} else {
			System.out.print("Preview tab from edit is not enabled.");
			return false;
		}
	}

	public void clickOnBuild() {
		IHGUtil.waitForElement(driver, 10, buildInEditButton);
		buildInEditButton.click();
	}

	public boolean buildTabInEditButton() {
		IHGUtil.waitForElement(driver, 10, buildInEditButton);
		if (buildInEditButton.isSelected()) {
			System.out.print("Build tab from edit is enabled.");
			return true;
		} else {
			System.out.print("Build tab from edit is not enabled.");
			return false;
		}
	}

	public void clickOnBackArrow() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, clickOnBackArrow);
		clickOnBackArrow.click();
		Thread.sleep(10000);
	}

	public void enterInvalidTimingForSmsInOneDay() {
		IHGUtil.waitForElement(driver, 10, oneDaysTimeUnitTextBox);
		oneDaysTimeUnitTextBox.clear();
		oneDaysTimeUnitTextBox.sendKeys("0");
	}

	public String getInvalidTimingTextForSmsInOneDay() {
		IHGUtil.waitForElement(driver, 10, invalidTextForOneDays);
		return invalidTextForOneDays.getText();
	}

	public String getColorForInvalidTimingForSmsInOneDay() {
		IHGUtil.waitForElement(driver, 10, invalidTextForOneDays);
		String getCsvValue = invalidTextForOneDays.getCssValue("color");
		String color = Color.fromString(getCsvValue).asHex();
		return color;
	}

	public void enterInvalidTimingForSmsInThreeDay() {
		IHGUtil.waitForElement(driver, 10, threeDaysTimeUnitTextBox);
		threeDaysTimeUnitTextBox.clear();
		threeDaysTimeUnitTextBox.sendKeys("0");
	}

	public String getInvalidTimingTextForSmsInThreeDay() {
		IHGUtil.waitForElement(driver, 10, invalidTextForThreeDays);
		return invalidTextForThreeDays.getText();
	}

	public String getColorForInvalidTimingForSmsInThreeDay() {
		IHGUtil.waitForElement(driver, 10, invalidTextForThreeDays);
		String getCsvValue = invalidTextForThreeDays.getCssValue("color");
		String color = Color.fromString(getCsvValue).asHex();
		return color;
	}

	public void enterInvalidTimingForSmsInminutes() {
		IHGUtil.waitForElement(driver, 10, minutesTimeUnitTextBox);
		minutesTimeUnitTextBox.clear();
		minutesTimeUnitTextBox.sendKeys("0");
	}

	public String getInvalidTimingTextForSmsInMinutes() {
		IHGUtil.waitForElement(driver, 10, invalidTextForminutes);
		return invalidTextForminutes.getText();
	}

	public String getColorForInvalidTimingForSmsInMinutes() {
		IHGUtil.waitForElement(driver, 10, invalidTextForminutes);
		String getCsvValue = invalidTextForminutes.getCssValue("color");
		String color = Color.fromString(getCsvValue).asHex();
		return color;
	}

	public void enterInvalidTimingForSmsInHours() {
		IHGUtil.waitForElement(driver, 10, hoursTimeUnitTextBox);
		hoursTimeUnitTextBox.clear();
		hoursTimeUnitTextBox.sendKeys("0");
	}

	public String getInvalidTimingTextForSmsInHours() {
		IHGUtil.waitForElement(driver, 10, invalidTextForhours);
		return invalidTextForhours.getText();
	}

	public String getColorForInvalidTimingForSmsInHours() {
		IHGUtil.waitForElement(driver, 10, invalidTextForhours);
		String getCsvValue = invalidTextForhours.getCssValue("color");
		String color = Color.fromString(getCsvValue).asHex();
		return color;
	}

	public void enterInvalidTimingForEmailInHours() {
		IHGUtil.waitForElement(driver, 10, hoursTimeUnitEmailtBox);
		hoursTimeUnitEmailtBox.clear();
		hoursTimeUnitEmailtBox.sendKeys("0");
	}

	public String getInvalidTimingTextForEmailInHours() {
		IHGUtil.waitForElement(driver, 10, invalidTextForHours);
		return invalidTextForHours.getText();
	}

	public String getColorForInvalidTimingForEmailInHours() {
		IHGUtil.waitForElement(driver, 10, invalidTextForHours);
		String getCsvValue = invalidTextForHours.getCssValue("color");
		String color = Color.fromString(getCsvValue).asHex();
		return color;
	}

	public void enterInvalidTimingForEmailInMinutes() {
		IHGUtil.waitForElement(driver, 10, minutesTimeUnitEmailBox);
		minutesTimeUnitEmailBox.clear();
		minutesTimeUnitEmailBox.sendKeys("0");
	}

	public String getInvalidTimingTextForEmailInMinutes() {
		IHGUtil.waitForElement(driver, 10, invalidTextForMinutes);
		return invalidTextForMinutes.getText();
	}

	public String getColorForInvalidTimingForEmailInMinutes() {
		IHGUtil.waitForElement(driver, 10, invalidTextForMinutes);
		String getCsvValue = invalidTextForMinutes.getCssValue("color");
		String color = Color.fromString(getCsvValue).asHex();
		return color;
	}

	public void enterInvalidTimingForEmailInDays() {
		IHGUtil.waitForElement(driver, 10, daysTimeUnitEmailBox);
		daysTimeUnitEmailBox.clear();
		daysTimeUnitEmailBox.sendKeys("0");
	}

	public String getInvalidTimingTextForEmailInDays() {
		IHGUtil.waitForElement(driver, 10, invalidTextForDays);
		return invalidTextForDays.getText();
	}

	public String getColorForInvalidTimingForEmailInDays() {
		IHGUtil.waitForElement(driver, 10, invalidTextForDays);
		String getCsvValue = invalidTextForDays.getCssValue("color");
		String color = Color.fromString(getCsvValue).asHex();
		return color;
	}

	public void clickOnApptConfirmationsEmailhumburgerButton() {
		IHGUtil.waitForElement(driver, 10, apptConfirmationsEmailhumburgerButton);
		jse.executeScript("arguments[0].click();", apptConfirmationsEmailhumburgerButton);
	}

	public void clickOnApptConfirmationsTextshumburgerButton() {
		IHGUtil.waitForElement(driver, 10, apptConfirmationsSmshumburgerButton);
		jse.executeScript("arguments[0].click();", apptConfirmationsSmshumburgerButton);
	}

	public void clickOnPreviewButton() {
		IHGUtil.waitForElement(driver, 10, previewButton);
		jse.executeScript("arguments[0].click();", previewButton);
	}

	public String visibilityOfPreviewPageTitle() {
		IHGUtil.waitForElement(driver, 10, previewButton);
		log("Preview page is displayed.");
		return previewButton.getText();
	}

	public String visibilityOfViewingText() {
		IHGUtil.waitForElement(driver, 10, viewingInPreviewPage);
		log("Viewing text is displayed.");
		return viewingInPreviewPage.getText();
	}

	public String visibilityOfEnglishLang() {
		IHGUtil.waitForElement(driver, 10, englishLangInPreviewPage);
		log("English Language is displayed.");
		return englishLangInPreviewPage.getText();
	}

	public String visibilityOfSettingText() {
		IHGUtil.waitForElement(driver, 10, settingInPreviewPage);
		log("Setting Text is displayed.");
		return settingInPreviewPage.getText();
	}

	public String visibilityOfCloseButton() {
		IHGUtil.waitForElement(driver, 10, closeButtonInPreviewPage);
		log("Close button is displayed.");
		return closeButtonInPreviewPage.getText();
	}

	public String visibilityOfNotiTypeText() {
		IHGUtil.waitForElement(driver, 10, notifTypeInPreviewPage);
		log("Notifications type Text is displayed.");
		return notifTypeInPreviewPage.getText();
	}

	public String visibilityOfApptConfigText() {
		IHGUtil.waitForElement(driver, 10, apptConfInPreviewPage);
		log("Appointment Configuration Text is displayed.");
		return apptConfInPreviewPage.getText();
	}

	public String visibilityOfVersionText() {
		IHGUtil.waitForElement(driver, 10, versionInPreviewPage);
		log("Version Text is displayed.");
		return versionInPreviewPage.getText();
	}

	public String visibilityOfDeliveryMethodText() {
		IHGUtil.waitForElement(driver, 10, deliveryMethodInPreviewPage);
		log("Delivery Method Text is displayed.");
		return deliveryMethodInPreviewPage.getText();
	}

	public String visibilityOfEmailText() {
		IHGUtil.waitForElement(driver, 10, emailInPreviewPage);
		log("Email Text is displayed.");
		return emailInPreviewPage.getText();
	}

	public String visibilityOfTimingText() {
		IHGUtil.waitForElement(driver, 10, timingInPreviewPage);
		log("Timing Text is displayed.");
		return timingInPreviewPage.getText();
	}

	public String visibilityOfAllTimingText() {
		IHGUtil.waitForElement(driver, 10, allTimingInPreviewPage);
		log("All Timing Text is displayed.");
		return allTimingInPreviewPage.getText();
	}

	public String visibilityOfResourcesText() {
		IHGUtil.waitForElement(driver, 10, resourcesInPreviewPage);
		log("Resources Text is displayed.");
		return resourcesInPreviewPage.getText();
	}

	public String visibilityOfAllResourcesText() {
		IHGUtil.waitForElement(driver, 10, allResourcesInPreviewPage);
		log("All Resources Text is displayed.");
		return allResourcesInPreviewPage.getText();
	}

	public String visibilityOfApptTypesText() {
		IHGUtil.waitForElement(driver, 10, apptTypesInPreviewPage);
		log("Appt Types Text is displayed.");
		return apptTypesInPreviewPage.getText();
	}

	public String visibilityOfAllApptTypesText() {
		IHGUtil.waitForElement(driver, 10, allApptTypesInPreviewPage);
		log("Appt All Text is displayed.");
		return allApptTypesInPreviewPage.getText();
	}

	public String visibilityOfLocationText() {
		IHGUtil.waitForElement(driver, 10, locationInPreviewPage);
		log("Location Text is displayed.");
		return locationInPreviewPage.getText();
	}

	public String visibilityOfAllLocationText() {
		IHGUtil.waitForElement(driver, 10, allLocationInPreviewPage);
		log("Location All Text is displayed.");
		return allLocationInPreviewPage.getText();
	}

	public String visibilityOfApptMethodText() {
		IHGUtil.waitForElement(driver, 10, apptMethodInPreviewPage);
		log("Appointment method Text is displayed.");
		return apptMethodInPreviewPage.getText();
	}

	public String visibilityOfInOfficeText() {
		IHGUtil.waitForElement(driver, 10, inOfficeInPreviewPage);
		log("In Office Text is displayed.");
		return inOfficeInPreviewPage.getText();
	}

	public String visibilityOfUponSchedulingText() {
		IHGUtil.waitForElement(driver, 10, uponSchedulingInPreviewPage);
		log("In Office Text is displayed.");
		return uponSchedulingInPreviewPage.getText();
	}

	public void closePreviewPage() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, closeButtonInPreviewPage);
		log("Setting Text is displayed.");
		jse.executeScript("arguments[0].click();", closeButtonInPreviewPage);
	}

	public void openDesktopViewPage() {
		IHGUtil.waitForElement(driver, 10, desktopIcon);
		desktopIcon.isDisplayed();
		log("Desktop view is displayed.");
		jse.executeScript("arguments[0].click();", desktopIcon);
		log("Open Desktop view");
	}

	public void openMobileViewPage() {
		IHGUtil.waitForElement(driver, 10, mobileIcon);
		mobileIcon.isDisplayed();
		log("Mobile view is displayed.");
		jse.executeScript("arguments[0].click();", mobileIcon);
		log("Open Mobile view");
	}

	public String visibilityOfClickHereText() {
		IHGUtil.waitForElement(driver, 10, clickHereText);
		clickHereText.isDisplayed();
		log("Click Here text is displayed.");
		return clickHereText.getText();
	}

	public String visibilityOfBrowserWindowText() {
		IHGUtil.waitForElement(driver, 10, browserWindowText);
		browserWindowText.isDisplayed();
		log("Browser Window text is displayed.");
		return browserWindowText.getText();
	}

	public boolean visibilityOfCadenceImage() {
		IHGUtil.waitForElement(driver, 10, cadenceImage);
		if (cadenceImage.isDisplayed()) {
			log("Cadence Image is displayed.");
			return true;
		} else {
			log("Cadence Image is not displayed.");
			return false;
		}
	}

	public String visibilityOfApptScheduledText() {
		IHGUtil.waitForElement(driver, 10, apptScheduledText);
		apptScheduledText.isDisplayed();
		log("Appointment scheduled text is displayed.");
		return apptScheduledText.getText();
	}

	public String visibilityOfPatientNameText() {
		IHGUtil.waitForElement(driver, 10, patienNameText);
		patienNameText.isDisplayed();
		log("Patient Name text is displayed.");
		return patienNameText.getText();
	}

	public String visibilityOfBeenScheduledText() {
		IHGUtil.waitForElement(driver, 10, beenScheduledText);
		beenScheduledText.isDisplayed();
		log("Been Scheduled text is displayed.");
		return beenScheduledText.getText();
	}

	public String visibilityOfDateAndTimeText() {
		IHGUtil.waitForElement(driver, 10, dateAndTimeText);
		dateAndTimeText.isDisplayed();
		log("Date And Time text is displayed.");
		return dateAndTimeText.getText();
	}

	public String visibilityOfDayOfTheWeekText() {
		IHGUtil.waitForElement(driver, 10, dayOfTheWeekText);
		dayOfTheWeekText.isDisplayed();
		log("Day Of The Week text is displayed.");
		return dayOfTheWeekText.getText();
	}

	public String visibilityOfDateFormatText() {
		IHGUtil.waitForElement(driver, 10, dateFormat);
		dateFormat.isDisplayed();
		log("Date Format text is displayed.");
		return dateFormat.getText();
	}

	public String visibilityOfLocationTextInPreview() {
		IHGUtil.waitForElement(driver, 10, locationText);
		locationText.isDisplayed();
		log("Location text is displayed.");
		return locationText.getText();
	}

	public String visibilityOfLocationNameText() {
		IHGUtil.waitForElement(driver, 10, locationNameText);
		locationNameText.isDisplayed();
		log("Location Name text is displayed.");
		return locationNameText.getText();
	}

	public String visibilityOfAddress1Text() {
		IHGUtil.waitForElement(driver, 10, address1Text);
		address1Text.isDisplayed();
		log("Address1 text is displayed.");
		return address1Text.getText();
	}

	public String visibilityOfAddress2Text() {
		IHGUtil.waitForElement(driver, 10, address2Text);
		address2Text.isDisplayed();
		log("Address2 text is displayed.");
		return address2Text.getText();
	}

	public String visibilityOfCityStateZipText() {
		IHGUtil.waitForElement(driver, 10, cityStateZipText);
		cityStateZipText.isDisplayed();
		log("City State Zip text is displayed.");
		return cityStateZipText.getText();
	}

	public String visibilityOfMobileNoFormatText() {
		IHGUtil.waitForElement(driver, 10, mobileNoFormat);
		mobileNoFormat.isDisplayed();
		log("CMobile No. Format text is displayed.");
		return mobileNoFormat.getText();
	}

	public String visibilityOfPinToMapText() {
		IHGUtil.waitForElement(driver, 10, pinToMapText);
		pinToMapText.isDisplayed();
		log("Pin To Map text is displayed.");
		return pinToMapText.getText();
	}

	public String visibilityOfProviderText() {
		IHGUtil.waitForElement(driver, 10, providerText);
		providerText.isDisplayed();
		log("Provider text is displayed.");
		return providerText.getText();
	}

	public String visibilityOfProviderNameText() {
		IHGUtil.waitForElement(driver, 10, providerNameText);
		providerNameText.isDisplayed();
		log("Provider Name text is displayed.");
		return providerNameText.getText();
	}

	public String visibilityOfRescheduleOrCancelText() {
		IHGUtil.waitForElement(driver, 10, rescheduleOrCancelText);
		rescheduleOrCancelText.isDisplayed();
		log("Reschedule Or Cancel link is displayed.");
		return rescheduleOrCancelText.getText();
	}

	public String visibilityOfInstructionsText() {
		IHGUtil.waitForElement(driver, 10, instructions);
		instructions.isDisplayed();
		log("Instructions is displayed.");
		return instructions.getText();
	}

	public String visibilityOfConfidentialityNoticeText() {
		IHGUtil.waitForElement(driver, 10, confidentialityNoticeText);
		confidentialityNoticeText.isDisplayed();
		log("Confidentiality Notice text is displayed.");
		return confidentialityNoticeText.getText();
	}

	public String visibilityOfUnsubscribeText() {
		IHGUtil.waitForElement(driver, 10, unsubscribeText);
		unsubscribeText.isDisplayed();
		log("Unsubscribe text is displayed.");
		return unsubscribeText.getText();
	}

	public String visibilityOfBottomInfoText() {
		IHGUtil.waitForElement(driver, 10, bottomInfo);
		bottomInfo.isDisplayed();
		log("Bottom Info is displayed.");
		return bottomInfo.getText();
	}

	public String visibilityOfSmsText() {
		IHGUtil.waitForElement(driver, 10, smsInPreviewPage);
		smsInPreviewPage.isDisplayed();
		log("SMS Text is displayed.");
		return smsInPreviewPage.getText();
	}

	public String visibilityOfPatientInfoInTextMsg() {
		IHGUtil.waitForElement(driver, 10, patientInfoInTextMsg);
		patientInfoInTextMsg.isDisplayed();
		log("Patient inf in sms is displayed.");
		return patientInfoInTextMsg.getText();
	}

	public boolean visibilityOfRescheduleAndCancelAppointmentText() {
		IHGUtil.waitForElement(driver, 10, rescheAndCancelText);
		if (rescheAndCancelText.isDisplayed()) {
			log("Patient inf in sms is displayed.");
			log("Reschedule And Cancel Appointment text is :- " + rescheAndCancelText.getText());
			return true;
		} else {
			log("Reschedule And Cancel Appointment text is not displayed.");
			return false;
		}
	}

	public boolean visibilityOfGetDirectionText() {
		IHGUtil.waitForElement(driver, 10, getDitectionText);
		if (getDitectionText.isDisplayed()) {
			log("Patient inf in sms is displayed.");
			log("Patient inf in sms is :- " + getDitectionText.getText());
			return true;
		} else {
			log("Patient information is not displayed.");
			return false;
		}
	}

	public boolean visibilityOfTextStopToUnsubscribeText() {
		IHGUtil.waitForElement(driver, 10, textStopToUnsubscribe);
		if (textStopToUnsubscribe.isDisplayed()) {
			log("Patient inf in sms is displayed.");
			log("Text Stop To Unsubscribe Text is :- " + textStopToUnsubscribe.getText());
			return true;
		} else {
			log("Patient information is not displayed.");
			return false;
		}
	}

	public boolean visibilityOfSmsPreviewBottomtext() {
		IHGUtil.waitForElement(driver, 10, smsPreviewBottom);
		if (smsPreviewBottom.isDisplayed()) {
			log("SMS preview bottom text is displayed.");
			log("SMS preview bottom text is :- " + smsPreviewBottom.getText());
			return true;
		} else {
			log("SMS preview bottom text is not displayed.");
			return false;
		}
	}

	public String visibilityOfNewPracticeLogoText() {
		IHGUtil.waitForElement(driver, 10, newPracticeBlankLogoText);
		newPracticeBlankLogoText.isDisplayed();
		log("Patient inf in sms is displayed.");
		return newPracticeBlankLogoText.getText();
	}

	public boolean visibilityOfConfirmApptButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, confirmApptButton);
		confirmApptButton.isDisplayed();
		log("Confirm Appointment button is displayed.");
		log("Confirm Appointment button text is :- " + confirmApptButton.getText());
		if (confirmApptButton.isEnabled()) {
			log("Confirm Appointment button is enabled.");
			return true;
		} else {
			log("Confirm Appointment button is not enabled.");
			return false;
		}
	}

	public String visibilityOfStartPrechecklink() {
		IHGUtil.waitForElement(driver, 10, startPreCheck);
		startPreCheck.isDisplayed();
		log("Start Precheck links is displayed.");
		return startPreCheck.getText();
	}

	public String visibilityOfApptReminderText() {
		IHGUtil.waitForElement(driver, 10, apptReminderText);
		apptReminderText.isDisplayed();
		log("Appointment scheduled text is displayed.");
		return apptReminderText.getText();
	}

	public String visibilityOfapptComingUpMessageText() {
		IHGUtil.waitForElement(driver, 10, apptComingUpMessage);
		apptComingUpMessage.isDisplayed();
		log("Patient Name text is displayed.");
		return apptComingUpMessage.getText();
	}

	public String visibilityOfApptReminderTextforPreview() {
		IHGUtil.waitForElement(driver, 10, apptReminderInPreviewPage);
		apptReminderInPreviewPage.isDisplayed();
		log("Appointment reminder text is displayed.");
		return apptReminderInPreviewPage.getText();
	}

	public boolean visibilityOfEditbuttonInPreview() {
		IHGUtil.waitForElement(driver, 10, editButtonInPreviewPage);
		apptReminderInPreviewPage.isDisplayed();
		log("Appointment reminder text is displayed.");
		if (editButtonInPreviewPage.isEnabled()) {
			log("Edit button is enabled.");
			return true;
		} else {
			log("Edit button is enabled.");
			return false;
		}
	}

	public void clickOnEditButtonInPreviewPage() {
		IHGUtil.waitForElement(driver, 10, editButtonInPreviewPage);
		jse.executeScript("arguments[0].click();", editButtonInPreviewPage);
	}

	public String visibilityOfDefaultVersionText() {
		IHGUtil.waitForElement(driver, 10, defaultTextInPreviewPage);
		log("Delivery Method Text is displayed.");
		return defaultTextInPreviewPage.getText();
	}

	public String emailDesignPage() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, emaildesignPage);
		return emaildesignPage.getText();
	}

	public String emailEditingPage() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, emailEditingPage);
		return emailEditingPage.getText();
	}

	public boolean visibilityOfPatientInfoInTextMsgForApptRemText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, patientInfoInTextMsg);
		if (patientInfoInTextMsg.isDisplayed()) {
			log("Patient information in sms is displayed.");
			return true;
		} else {
			log("Patient information in sms is not displayed.");
			return false;
		}
	}

	public boolean visibilityOfConfirmApptlinkForApptRemText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, confirmApptlink);
		if (patientInfoInTextMsg.isDisplayed()) {
			log("Confirm appointment link is displayed.");
			return true;
		} else {
			log("Confirm appointment link is not displayed.");
			return false;
		}
	}

	public boolean visibilityOfRescheduleAndCancelLinkApptRemText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, rescheduleAndCancelLink);
		if (rescheduleAndCancelLink.isDisplayed()) {
			log("Reschedule And Cancel Link is displayed.");
			return true;
		} else {
			log("Reschedule And Cancel Link is not displayed.");
			return false;
		}
	}

	public boolean visibilityGetDirectionLinkApptRemText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, getDirectionLink);
		if (getDirectionLink.isDisplayed()) {
			log("Get Direction Link is displayed.");
			return true;
		} else {
			log("Get Direction Link is not displayed.");
			return false;
		}
	}

	public String visibilityTextToStopUnsubscribeApptRemText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, textToStopUnsubscribe);
		textToStopUnsubscribe.isDisplayed();
		log("Patient information in sms is displayed.");
		return textToStopUnsubscribe.getText();
	}

	public String getTextFronSendNotifTraingleTab() {
		IHGUtil.PrintMethodName();
		log("Hover on send notification traingle");
		Actions action = new Actions(driver);
		action.moveToElement(apptDataTraingleTab).perform();
		return sendNotifTraingleTabText.getText();
	}

	public void clickOnCurbsideCheckInTabInNotif() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, curbsideCheckInTabInNotif);
		jse.executeScript("arguments[0].click();", curbsideCheckInTabInNotif);
	}

	public void clickOnEnglishButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, englishButton);
		englishButton.click();
	}

	public String getAdditionalArrivalInstructionMsgTextInEnglish() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, additionalArrivalInstrInEnglish);
		return additionalArrivalInstrInEnglish.getText();
	}

	public void clickOnSpanishButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, spanishButton);
		jse.executeScript("arguments[0].click();", spanishButton);
	}

	public String getAdditionalArrivalInstructionMsgTextInSpanish() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, additionalArrivalInstrInSpanish);
		return additionalArrivalInstrInSpanish.getText();
	}

	public String visibilityOfArrivalConfirmationMsgInEnglish() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, arrivalConfirmationMsg);
		log("Arrival Confirmation Message is displayed.");
		return arrivalConfirmationMsg.getText();
	}

	public String visibilityOfArrivalConfirmationMsgInSpanish() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, arrivalConfirmationMsg);
		log("Arrival Confirmation Message is displayed.");
		return arrivalConfirmationMsg.getText();
	}

	public String getAppointmentConfirmationsText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, apptConfirmationsText);
		log("Appointment Confirmations section is displayed under notification type.");
		return apptConfirmationsText.getText();
	}

	public String getAppointmentRemindersText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, appointmentRemindersText);
		log("Appointment Reminders section is displayed under notification type.");
		return appointmentRemindersText.getText();
	}

	public String visibilityOfEmailTemplateUnderAptConfirmation() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, emailAppointmentConfirmations);
		log("Email template is displayed under Appointment Confirmations section");
		return emailAppointmentConfirmations.getText();
	}

	public String visibilityOfSMSTemplateUnderAptConfirmation() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, smsAppointmentConfirmations);
		log("SMS template is displayed under Appointment Confirmations section");
		return smsAppointmentConfirmations.getText();
	}

	public String visibilityOfEmailTemplateUnderAptReminder() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, emailAppointmentReminders);
		log("Email template is displayed under Appointment Reminder section");
		return emailAppointmentReminders.getText();
	}

	public String visibilityOfSMSTemplateUnderAptReminder() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, smsAppointmentReminders);
		log("SMS template is displayed under Appointment Reminder section");
		return smsAppointmentReminders.getText();
	}

	public String visibilityOfPublishedStatusEmailAptConf() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, publishedStatusEmailForApptConfirmations);
		log("Publish status is displayed under Appointment Confirmation section of email template");
		return publishedStatusEmailForApptConfirmations.getText();
	}

	public String visibilityOfPublishedStatusSMSAptConf() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, publishedStatusSmsForApptConfirmations);
		log("Publish status is displayed under Appointment Confirmation section of sms template");
		return publishedStatusSmsForApptConfirmations.getText();
	}

	public String visibilityOfPublishedStatusEmailAptRem() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, publishedStatusEmailForApptReminders);
		log("Publish status is displayed under Appointment reminders section of email template");
		return publishedStatusEmailForApptReminders.getText();
	}

	public String visibilityOfPublishedStatusSMSAptRem() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, publishedStatusSmsApptReminders);
		log("Publish status is displayed under Appointment reminders section of sms template");
		return publishedStatusSmsApptReminders.getText();
	}

	public boolean visibiliyOfAptConfirmationInfoIcon() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, aptConfirmationInfoIcon);
		aptConfirmationInfoIcon.isDisplayed();
		if (aptConfirmationInfoIcon.isDisplayed()) {
			log("Appointment Confirmation Info Icon is visible");
			return true;
		} else {
			log("Appointment Confirmation Info Icon is not visible");
			return false;
		}
	}

	public String getTextFromAptConfirmationInfoIcon() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, aptConfirmationInfoIcon);
		log("Hover on info icon of Appointment confirmation");
		Actions action = new Actions(driver);
		action.moveToElement(aptConfirmationInfoIcon).perform();
		return aptConfirmationInfoIconText.getText();
	}

	public String visibilityOfDeliveryMethodTextNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, deliveryMethodOfAptConf);
		log("In appointment confirmation under Delivery Method section mail and text fields are display");
		return deliveryMethodOfAptConf.getText();
	}

	public String visibilityOfDefaultEmailAptConf() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTextEmailAptConf);
		log("Default status is displayed for mail under version section of appointment confirmation section");
		return defaultTextEmailAptConf.getText();
	}

	public String visibilityOfDefaultSMSAptConf() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTextSMSAptConf);
		log("Default status is displayed for sms under version section of appointment confirmation section");
		return defaultTextSMSAptConf.getText();
	}

	public String visibilityOfVersionTextAptConfNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, versionTextAptConf);
		log("Version section of appointment confirmation section is display");
		return versionTextAptConf.getText();
	}

	public String visibilityOfApptMethodTextAptConfNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, apptMethodTextAptConf);
		log("Appointment method Text is displayed under Appointment confirmations.");
		return apptMethodTextAptConf.getText();
	}

	public String visibilityOfInOfficeTextEmailAptConf() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, inOfficeTextEmailAptConf);
		log("In Office Text is displayed for email under Appt method of Appointment confirmations section.");
		return inOfficeTextEmailAptConf.getText();
	}

	public String visibilityOfInOfficeTextSMSAptConf() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, inOfficeTextSMSAptConf);
		log("In Office Text is displayed for sms under Appt method of Appointment confirmations section.");
		return inOfficeTextSMSAptConf.getText();
	}

	public String visibilityOfUponSchedulingEmailAptConfNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, uponSchedulingEmailAptConf);
		log("Upon scheduling Text is displayed for email under Timing of appointment confirmations section.");
		return uponSchedulingEmailAptConf.getText();
	}

	public String visibilityOfUponSchedulingSMSAptConfNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, uponSchedulingSMSAptConf);
		log("Upon scheduling Text is displayed for sms under Timing of appointment confirmations section.");
		return uponSchedulingSMSAptConf.getText();
	}

	public String visibilityOfTimingTextNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, timingText);
		log("Timing Text is displayed in appointment confirmation section.");
		return timingText.getText();
	}

	public String visibilityOfStatusTextAptConf() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, statusTextAptConf);
		log("Publish status is displayed under Status Section of Appointment Confirmation");
		return statusTextAptConf.getText();
	}

	public boolean visibiliyOfAptReminderInfoIcon() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, aptRemInfoIcon);
		if (aptRemInfoIcon.isDisplayed()) {
			log("Appointment Reminders Info Icon is visible");
			return true;
		} else {
			log("Appointment Reminders Info Icon is not visible");
			return false;
		}
	}

	public String getTextFromAptRemindersInfoIcon() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, aptRemInfoIcon);
		Actions action = new Actions(driver);
		action.moveToElement(aptRemInfoIcon).perform();
		log("Hover on info icon of Appointment reminders");
		return aptRemindersInfoIconText.getText();
	}

	public String visibilityOfDeliveryMethodTextForApptRem() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, delivaryMtdForApptRemindert);
		log("In Delivery Method section is displayed for mail and text fields under appointment reminder section");
		return delivaryMtdForApptRemindert.getText();
	}

	public String visibilityOfv2EmailAptRem() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, v2TextEmailAptRem);
		log("v2 status is displayed for mail under version section of appointment reminders section");
		return v2TextEmailAptRem.getText();
	}

	public String visibilityOfDefaultSMSAptRem() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTextSMSAptRem);
		log("Default status is displayed for sms under version section of appointment reminders section ");
		return defaultTextSMSAptRem.getText();
	}

	public String visibilityOfVersionTextAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, versionTextAptRem);
		log("Version section of appointment reminders section is display");
		return versionTextAptRem.getText();
	}

	public String visibilityOfApptMethodTextAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, apptMethodTextAptRem);
		log("Appointment method Text is displayed under Appointment reminders.");
		return apptMethodTextAptRem.getText();
	}

	public String visibilityOfInOfficeTextEmailAptRem() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, inOfficeTextEmailAptRem);
		log("In Office Text is displayed for email under Appt method of Appointment reminders section.");
		return inOfficeTextEmailAptRem.getText();
	}

	public String visibilityOfInOfficeTextSMSAptRem() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, inOfficeTextSMSAptRem);
		log("In Office Text is displayed for email under Appt method of Appointment confirmations section.");
		return inOfficeTextSMSAptRem.getText();
	}

	public String visibilityOfDaysEmailAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, daysTextEmailAptRem);
		log("Days Text is displayed under timing section of email template.");
		return daysTextEmailAptRem.getText();
	}

	public String visibilityOfHoursEmailAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, hoursTextEmailAptRem);
		log("Hours Text is displayed under timing section of email template.");
		return hoursTextEmailAptRem.getText();
	}

	public String visibilityOfMinutesEmailAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, minutesTextEmailAptRem);
		log("Minutes Text is displayed under timing section of email template");
		return minutesTextEmailAptRem.getText();
	}

	public String visibilityOfDaysSMSAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, daysTextSMSAptRem);
		log("Days Text is displayed under timing section of sms template.");
		return daysTextSMSAptRem.getText();
	}

	public String visibilityOfHoursSMSAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, hoursTextSMsAptRem);
		log("Hours Text is displayed under timing section of sms template.");
		return hoursTextSMsAptRem.getText();
	}

	public String visibilityOfMinutesSMSAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, minutesTextSMSAptRem);
		log("Minutes Text is displayed under timing section of sms template");
		return minutesTextSMSAptRem.getText();
	}

	public String visibilityOfTimingTextUnderAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, timingTextUnderAptRem);
		log("Timing section is displayed under Appointment Reminders.");
		return timingTextUnderAptRem.getText();
	}

	public String visibilityOfOneUnitUnderTimingUnitNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, oneTimeUnit);
		log("One unit is displayed under Timing Unit section");
		return oneTimeUnit.getText();
	}

	public String visibilityOfthirtyUnitUnderTimingUnitNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, thirtyTimeUnit);
		log("Thirty unit is displayed under Timing Unit section");
		return thirtyTimeUnit.getText();
	}

	public String visibilityOfTimingUnitsUnderAptRemNotificationTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, timingunitsTextUnderAptRem);
		log("Timing units section is displayed under Appointment Reminders");
		return timingunitsTextUnderAptRem.getText();
	}

	public String visibilityOfStatusTextAptRem() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, statusTextAptRem);
		log("Publish status is displayed under Status Section of Appointment reminders");
		return statusTextAptRem.getText();
	}

	public String getOnPreviewButtonText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, previewButton);
		return previewButton.getText();
	}

	public String visibilityOfEditTextUnderHamburger() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, editTextUnderHamburgerButton);
		log("Edit button is displayed.");
		return editTextUnderHamburgerButton.getText();
	}

	public void clickOnEditButtonHamburgerButton() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, editTextUnderHamburgerButton);
		jse.executeScript("arguments[0].click();", editTextUnderHamburgerButton);
		Thread.sleep(5000);
	}

	public boolean visibilityOfNotificationOffRadioButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, offNotificationButton);
		log("send notification radio button is displayed.");
		if (offNotificationButton.isEnabled()) {
			log("send notification radio button is enabled.");
			return true;
		} else {
			log("send notification radio button is disabled.");
			return false;
		}
	}

	public boolean visibilityOfNotificationOnRadioButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, onNotificationButton);
		log("send notification radio button is displayed.");
		if (onNotificationButton.isEnabled()) {
			log("send notification radio button is enabled.");
			return true;
		} else {
			log("send notification radio button is disabled.");
			return false;
		}
	}

	public void clearAdditionalArrivalInstTextboxEn() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, additionalArrivalInstructionsTextBoxEn);
		additionalArrivalInstructionsTextBoxEn.clear();
		log("Clear Additional arrival instrunction textbox");
	}

	public String AdditionalArrivalInstTextboxBlankEn() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, additionalArrivalInstructionsTextBoxText);
		log("Additional arrival instrunction textbox is blank.");
		return additionalArrivalInstructionsTextBoxText.getText();
	}

	public void addArrivalInstructionTextInEnglish(String addArrivalInstruction) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, additionalArrivalInstructionsTextBoxText);
		additionalArrivalInstructionsTextBoxText.sendKeys(addArrivalInstruction);
		log("Add Additional arrival instrunction message for english");
	}

	public void clearAdditionalArrivalInstTextboxEs() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, additionalArrivalInstructionsTextBoxEs);
		additionalArrivalInstructionsTextBoxEs.clear();
		log("Clear Additional arrival instrunction textbox for spanish");
	}

	public String additionalArrivalInstTextboxBlankEs() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, additionalArrivalInstructionsTextBoxText);
		log("Additional arrival instrunction textbox is blank.");
		return additionalArrivalInstructionsTextBoxText.getText();
	}

	public void addArrivalInstructionTextInSpanish(String addArrivalInstruction) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, additionalArrivalInstructionsTextBoxText);
		additionalArrivalInstructionsTextBoxText.sendKeys(addArrivalInstruction);
		log("Add Additional arrival instrunction message in spanish");
	}

	public void clickOnPracticePrefLangDropDown() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, practicePrefenceLangDropDown);
		practicePrefenceLangDropDown.click();
	}

	public void selectEnglishSpanishPracticePrefLang() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, englishSpanishPracticePrefenceLang);
		englishSpanishPracticePrefenceLang.click();
	}

	public boolean visiblityOfBroadcastMessageTextBoxInEnEs() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, englishSpanishPracticePrefenceLang);
		if (englishSpanishPracticePrefenceLang.isDisplayed()) {
			log("Broadcast Message Text box in english and spanish language is visible");
			return true;
		} else {
			log("Broadcast Message Text box in english and spanish language is not visible");
			return false;
		}
	}

	public void clickOnEnglishPracticePrefLang() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, englishPracticePrefenceLang);
		jse.executeScript("arguments[0].click();", englishPracticePrefenceLang);
	}

	public boolean visibilityOfPracticePrefenceLangEn() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, englishPracticePrefenceLang);
		if (englishPracticePrefenceLang.isDisplayed()) {
			log("Broadcast Message Text box in english language is visible");
			return true;
		} else {
			log("Broadcast Message Text box in english language is not visible");
			return false;
		}
	}

	public String getSaveButtonText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, saveButton);
		log("Save button is displayed.");
		return saveButton.getText();
	}

	public String getOnNotificationText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, onNotificationButton);
		return onText.getText();
	}

	public String getOfOffNotificationText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, offNotificationButton);
		return offText.getText();
	}

	public String getFeatureText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, featureText);
		log("Feature Text is displayed.");
		return featureText.getText();
	}

	public String getBroadcastMessagingText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, broadcastMessagingCheckbox);
		return broadcastText.getText();
	}

	public String getCurbsideReminderText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, curbsideCheckbox);
		return curbsideText.getText();
	}

	public String getPatientFirstNameText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, patientNameCheckbox);
		return patientFirstText.getText();
	}

	public String getNotificationTypeText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, notifTypeText);
		return notifTypeText.getText();
	}

	public String getApptConfirmationText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, apptConfirmText);
		return apptConfirmText.getText();
	}

	public String getApptReminderTextOnNotif() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, apptReminderText);
		return apptReminderText.getText();
	}

	public void clickOnCurbsideOption() {
		IHGUtil.waitForElement(driver, 5, curbOption);
		jse.executeScript("arguments[0].click();", curbOption);

	}

	public String getCurbsideParagraphText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, curbsidePara);
		return curbsidePara.getText();
	}

	public String getEnglishButtonText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, englishButton);
		return englishButton.getText();
	}

	public String getSpanishButtonText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, spanishButton);
		return spanishButton.getText();
	}

	public String getArrivalConfMsgHeadingText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, arrivalConfirmationMsg);
		return arrivalConfirmationMsg.getText();
	}

	public String visibilityOf1HrPriorCurbsideReminder() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, curbsidePara);
		if (curbsidePara.isDisplayed()) {
			log("1 hour prior - Curbside check-in reminder is displayed");
			return curbsidePara.getText();
		} else {
			log("1 hour prior - Curbside check-in reminder is not displayed");
		}
		return null;
	}

	public void enterTimingAndTimingUnit(int pathIndex, String timing, String timingUnit) throws InterruptedException {
		IHGUtil.PrintMethodName();
		log("Select timing and timing unit for: " + timing);
		jse.executeScript("arguments[0].click();",
				driver.findElement(By.xpath("(//div[@class=' css-1s2u09g-control'])[" + pathIndex + "]")));
		Actions action = new Actions(driver);
		action.sendKeys(driver.findElement(By.xpath("(//div[@class=' css-1s2u09g-control'])[" + pathIndex + "]")),
				timing).sendKeys(Keys.ENTER).build().perform();
		driver.findElement(By.xpath("(//input[@class='cadence-period-value'])[" + pathIndex + "]")).clear();
		driver.findElement(By.xpath("(//input[@class='cadence-period-value'])[" + pathIndex + "]"))
				.sendKeys(timingUnit);
	}

	public boolean visibilityOfSaveChangesbutton() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, saveChangesButton);
		if (saveChangesButton.isEnabled()) {
			jse.executeScript("arguments[0].click();", saveChangesButton);
			log("Save changes button is enable");
			Thread.sleep(5000);
			return true;
		} else {
			log("Save changes button is enable");
			return false;
		}
	}

	public String getSingleTimingText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, daysText);
		return daysText.getText();
	}

	public String getTimingUnitText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, timingUnitText);
		return timingUnitText.getText();
	}

	public void addFourthTimingAndTimingUnit() {
		IHGUtil.PrintMethodName();
		try {
			IHGUtil.waitForElement(driver, 60, addButtonInEdit);
			addButtonInEdit.isDisplayed();
			addButtonInEdit.click();
			log("Added Fourth Timing and Timing unit");
		} catch (NoSuchElementException e) {
			log("Fourth Timing and Timing unit already present");
		}
	}

	public void clickOnRemoveTiming() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, removeButtonInEdit);
		removeButtonInEdit.click();
	}

	public String getDaysTimingText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, daysText);
		return daysText.getText();
	}

	public String getMinutesTimingText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, minutesText);
		return minutesText.getText();
	}

	public String getHoursTimingText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, hoursText);
		return hoursText.getText();
	}

	public String getTimeUnitTextForDays() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, timeUnitTextForDays);
		return timeUnitTextForDays.getText();
	}

	public String getTimeUnitTextForHours() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, timeUnitTextForHours);
		return timeUnitTextForHours.getText();
	}

	public String getTimeUnitTextForMinutes() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, timeUnitTextForMinutes);
		return timeUnitTextForMinutes.getText();
	}

	public void checkingFourthTimingIfPresent() {
		IHGUtil.PrintMethodName();
		try {
			IHGUtil.waitForElement(driver, 60, removeButtonInEdit);
			removeButtonInEdit.isDisplayed();
			removeButtonInEdit.click();
			log("Remove Fourth Timing and Timing unit");
		} catch (NoSuchElementException e) {
			log("only three Timing and Timing is present");
		}
	}

	public String getSingleTimingTextForSMS() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, timingTextForSMS);
		return timingTextForSMS.getText();
	}

	public String getTimingUnitTextForSMS() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, timingUnitTextForSMS);
		return timingUnitTextForSMS.getText();
	}

	public String getDeliveryMethod() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, deliveryMethod);
		return deliveryMethod.getText();
	}

	public String getDaysTimingTextForSMS() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, daysTimingTextForSMS);
		return daysTimingTextForSMS.getText();
	}

	public String getHoursTimingTextForSMS() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, hoursTimingTextForSMS);
		return hoursTimingTextForSMS.getText();
	}

	public String getMinutesTimingTextForSMS() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, minutesTimingTextForSMS);
		return minutesTimingTextForSMS.getText();
	}

	public String getDaysTimeUnitTextForSMS() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, daysTimeUnitTextForSMS);
		return daysTimeUnitTextForSMS.getText();
	}

	public String getHoursTimeUnitTextForSMS() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, hoursTimeUnitTextForSMS);
		return hoursTimeUnitTextForSMS.getText();
	}

	public String getMinutesTimeUnitTextForSMS() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, minutesTimeUnitTextForSMS);
		return minutesTimeUnitTextForSMS.getText();
	}

	public void checkingCheckinButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, checkinButton);
		checkinButton.isEnabled();
	}

	public void clickOnCheckinButton() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, checkinButton);
		checkinButton.click();
		Thread.sleep(10000);
	}

	public void selectOnePatient() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, selectOnePatient);
		selectOnePatient.click();
	}

	public String getCheckinButtonText() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, checkinButton);
		return checkinButton.getText();
	}

	public String enterDays() {
		Random random = new Random();
		int randamNo = random.nextInt(400);
		return Appointment.days = String.valueOf(randamNo);
	}

	public String enterHours() {
		Random random = new Random();
		int randamNo = random.nextInt(23);
		return Appointment.hours = String.valueOf(randamNo);
	}

	public String enterMinutes() {
		Random random = new Random();
		int randamNo = random.nextInt(49) + 10;
		return Appointment.minutes = String.valueOf(randamNo);
	}

	public void disableCurbsideCheckinRemCheckbox() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, curbsideCheckinRemCheckbox);
		boolean selected = curbsideCheckinRemCheckbox.isSelected();
		if (selected) {
			curbsideCheckinRemCheckbox.click();
			log("Curbside checkin reminder checkbox disabled");
		} else if (!selected) {
			log("Curbside checkin reminder checkbox is already Enabled");
		}
		Thread.sleep(10000);
	}

	public void enableCurbsideCheckinRemCheckbox() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, curbsideCheckinRemCheckbox);
		boolean selected = curbsideCheckinRemCheckbox.isSelected();
		if (selected) {
			log("Curbside checkin reminder checkbox is already Enabled");
		} else if (!selected) {
			curbsideCheckinRemCheckbox.click();
			log("Curbside checkin reminder checkbox Enabled");
		}
		Thread.sleep(10000);
	}

	public void braodcastMessagingCheckbox() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, broadcastMessagingCheckbox);
		boolean selected = broadcastMessagingCheckbox.isSelected();
		if (selected) {
			log("Broadcast Messaging checkbox already Enabled");
		} else if (!selected) {
			broadcastMessagingCheckbox.click();
			log("Broadcast Messaging checkbox is Enabled");
		}
		Thread.sleep(10000);
	}

	public boolean visibilityOfTiming(int pathIndex) throws InterruptedException {
		IHGUtil.PrintMethodName();
		boolean visibility = false;
		try {
		visibility=driver.findElement(By.xpath("(//div[@class=' css-1s2u09g-control'])[" + pathIndex + "]")).isDisplayed();
			log("Timing is displayed");
			return visibility;
		} catch (NoSuchElementException e) {
			log("Timing is displayed is not displayed");
			return visibility;
		}
	}

	public boolean visibilityOfTimingUnit(int pathIndex) throws InterruptedException {
		IHGUtil.PrintMethodName();
		boolean visibility = false;
		try {
		visibility=driver.findElement(By.xpath("(//input[@class='cadence-period-value'])[" + pathIndex + "]")).isDisplayed();
			log("Timing is displayed");
			return visibility;
		} catch (NoSuchElementException e) {
			log("Timing is displayed is not displayed");
			return visibility;
		}
	}

	public String getTextTiming(int pathIndex) {
		IHGUtil.PrintMethodName();
		return driver.findElement(By.xpath("(//div[@class=' css-1s2u09g-control'])[" + pathIndex + "]"))
				.getText();
	}

	public String getTimingUnit(int pathIndex) {
		IHGUtil.PrintMethodName();
		return driver.findElement(By.xpath("(//input[@class='cadence-period-value'])[" + pathIndex + "]"))
				.getAttribute("value");
	}

	public String enterUnlimitedDays() {
		Random random = new Random();
		int randamNo = random.nextInt(1000000000);
		return Appointment.days = String.valueOf(randamNo);
	}
	
	public void clearArrivalInstTextbox() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, arrivalInstTextbox);
		arrivalInstTextbox.sendKeys(Keys.chord(Keys.CONTROL,"a"));
		arrivalInstTextbox.sendKeys(Keys.BACK_SPACE);
	}
	public void enterTextInArrivalInstTextbox(String message) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, arrivalInstTextbox);
		arrivalInstTextbox.sendKeys(message);
	}

	public boolean visibilityOfAddButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, addButtonInEdit);
		boolean visibility = false;
		try {
			visibility=addButtonInEdit.isDisplayed();
			log("Add button is displayed");
		} catch (NoSuchElementException e) {
			log("Add button is not displayed");
		}
		return visibility;
	}
	
	public String getApptReminderTextInEmail() {
		IHGUtil.waitForElement(driver, 10, apptReminderInPreviewPage);
		apptReminderInPreviewPage.isDisplayed();
		log("Appointment reminder text is displayed.");
		return apptReminderInPreviewPage.getText();
	}
	
	public String getApptMtdTextInEmail() {
		IHGUtil.waitForElement(driver, 10, apptReminderInPreviewPage);
		apptReminderInPreviewPage.isDisplayed();
		log("Appointment reminder text is displayed.");
		return apptMethodtextInEmail.getText();
	}
	public String getMaxLengthChar() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, numOfCharacter);
		return numOfCharacter.getText();
	}
	
	public boolean visibilityOfArrivalInstTextBox() throws InterruptedException {
		IHGUtil.PrintMethodName();
		boolean visibility = false;
		try {
		visibility=additionalArrivalInstructionsTextBoxText.isDisplayed();
			log("Additional Arrival Instructions TextBox is displayed");
			return visibility;
		} catch (NoSuchElementException e) {
			log("Additional Arrival Instructions TextBox is not displayed");
			return visibility;
		}
	}
	
	public boolean visibilityOfArrivalConfirmMsg() {
		IHGUtil.PrintMethodName();
		boolean visibility = false;
		IHGUtil.waitForElement(driver, 5, arrivalConfirmMsg);
		visibility=arrivalConfirmMsg.isDisplayed();
			log("Arrival confirmation message is displayed");
			return visibility;
		
	}
	
	public boolean visibilityOfeditPageTemplate() {
		IHGUtil.waitForElement(driver, 10, editPageTemplate);
		if( editPageTemplate.isDisplayed()) {
			log("text="+editPageTemplate.getText());
		return true;
		}
		else
		return false;
		}
	
	public String visibilityOfversionTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,versionTextInEditPage);
		return versionTextInEditPage.getText();
		}
	
	public String visibilityOfversionV2TextInEditPage() {
		IHGUtil.waitForElement(driver, 10,versionV2TextInEditPage);
		return versionV2TextInEditPage.getText();
		}
	
	public boolean visibilityOflogoInEditPage() {
		IHGUtil.waitForElement(driver, 10,logoInEditPage);
		if( logoInEditPage.isDisplayed()) {
			log("logo is displayed");
		return true;
		}
		else
		return false;
	}
	
	public String visibilityOfappointmentReminderTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,appointmentReminderTextInEditPage);
		return appointmentReminderTextInEditPage.getText();
	}
	
	public String visibilityOfconfirmAppointmentButtonInEditPage() {
		IHGUtil.waitForElement(driver, 10,confirmAppointmentButtonInEditPage);
		return confirmAppointmentButtonInEditPage.getText();
	}
	
	public String visibilityOfstartPrecheckLinkInEditPage() {
		IHGUtil.waitForElement(driver, 10,startPrecheckLinkInEditPage);
		return startPrecheckLinkInEditPage.getText();
	}
	
	public String visibilityOfpatientTextComingUpInEditPage() {
		IHGUtil.waitForElement(driver, 10,patientTextComingUpInEditPage);
		return patientTextComingUpInEditPage.getText();
		}
	
	public String visibilityOfdateAndTimeTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,dateAndTimeTextInEditPage);
		return dateAndTimeTextInEditPage.getText();
		}
	
	public String visibilityOfdayOfTheWeekTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,dayOfTheWeekTextInEditPage);
		return dayOfTheWeekTextInEditPage.getText();
		}
	
	public String visibilityOfmonthDDYYYYFormatTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,monthDDYYYYFormatTextInEditPage);
		return monthDDYYYYFormatTextInEditPage.getText();
		}
	
	public String visibilityOflocationTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,locationTextInEditPage);
		return locationTextInEditPage.getText();
		}
	
	public String visibilityOflocationNameTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,locationNameTextInEditPage);
		return locationNameTextInEditPage.getText();
		}
	
	public String visibilityOflocationAddress1TextInEditPage() {
		IHGUtil.waitForElement(driver, 10,locationAddress1TextInEditPage);
		return locationAddress1TextInEditPage.getText();
		}
	
	public String visibilityOflocationAddress2TextInEditPage() {
		IHGUtil.waitForElement(driver, 10,locationAddress2TextInEditPage);
		return locationAddress2TextInEditPage.getText();
		}
	
	public String visibilityOfcityStateZipTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,cityStateZipTextInEditPage);
		return cityStateZipTextInEditPage.getText();
		}
	
	public String visibilityOfnumberTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,numberTextInEditPage);
		return numberTextInEditPage.getText();
		}
	
	public String visibilityOfpinOnMapLinkTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,pinOnMapLinkTextInEditPage);
		return pinOnMapLinkTextInEditPage.getText();
		}
	
	public String visibilityOfproviderTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,providerTextInEditPage);
		return providerTextInEditPage.getText();
		}
	
	public String visibilityOfproviderNameTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,providerNameTextInEditPage);
		return providerNameTextInEditPage.getText();
		}
	
	public String visibilityOfrescheduleOrCancelLinkTextInEditPage() {
		IHGUtil.waitForElement(driver, 10,rescheduleOrCancelLinkTextInEditPage);
		return rescheduleOrCancelLinkTextInEditPage.getText();
		}
	
	public boolean visibilityOfeditPageTemplateForText() {
		IHGUtil.waitForElement(driver, 10, editPageTemplateForText);
		if( editPageTemplateForText.isDisplayed()) {
			log("text="+editPageTemplateForText.getText());
		return true;
		}
		else
		return false;
		}
	
	public String visibilityOfversionTextInEditPageForText() {
		IHGUtil.waitForElement(driver, 10,versionTextInEditPageForText);
		return versionTextInEditPageForText.getText();
		}
	
	public String visibilityOfdefaultTextInEditPageForText() {
		IHGUtil.waitForElement(driver, 10,defaultTextInEditPageForText);
		return defaultTextInEditPageForText.getText();
		}
	
	public Boolean visibilityOfpatientTextInEditPageForText() {
		IHGUtil.waitForElement(driver, 10,patientTextInEditPageForText);
		if(patientTextInEditPageForText.isDisplayed()) {
			log("text="+patientTextInEditPageForText.getText());
		
		return true;
		}
		else
			return false;
		}
	
	public Boolean visibilityOfconfirmTextInEditPageForText() {
		IHGUtil.waitForElement(driver, 10,confirmTextInEditPageForText);
		if(confirmTextInEditPageForText.isDisplayed()) {
			log("text="+confirmTextInEditPageForText.getText());
		
		return true;
		}
		else
			return false;
		}
	
	public Boolean visibilityOfrescheduleCancelTextInEditPageForText() {
		IHGUtil.waitForElement(driver, 10,rescheduleCancelTextInEditPageForText);
		if(rescheduleCancelTextInEditPageForText.isDisplayed()) {
			log("text="+rescheduleCancelTextInEditPageForText.getText());
		
		return true;
		}
		else
			return false;
		}
	
	public Boolean visibilityOfdirectionsTextInEditPageForText() {
		IHGUtil.waitForElement(driver, 10,directionsTextInEditPageForText);
		if(directionsTextInEditPageForText.isDisplayed()) {
			log("text="+directionsTextInEditPageForText.getText());
		
		return true;
		}
		else
			return false;
		}
	
	public String visibilityOfstopToUnsubscribeTextInEditPageForText() {
		IHGUtil.waitForElement(driver, 10,stopToUnsubscribeTextInEditPageForText);
		return stopToUnsubscribeTextInEditPageForText.getText();
		}
	
	public boolean visibilityOfsettingTabunderDesignsection() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, settingTabunderDesignsection);
		if(settingTabunderDesignsection.isDisplayed()) {
			log("settings tab is displayed under design tab");
			return true;
		}
		else {
			log("settings tab is not displayed under design tab");
			return false;
		}
	}
	
	public String visibilityOfnotificationTypetextUnderdesignTab() {
		IHGUtil.waitForElement(driver, 10, notificationTypetextUnderdesignTab);
		return notificationTypetextUnderdesignTab.getText();
	}
	
	public String visibilityOfappointmentReminderstextUnderdesignTab() {
		IHGUtil.waitForElement(driver, 10, appointmentReminderstextUnderdesignTab);
		return appointmentReminderstextUnderdesignTab.getText();
	}
	
	public String visibilityOfversionTextunderDesigntab() {
		IHGUtil.waitForElement(driver, 10, versionTextunderDesigntab);
		return versionTextunderDesigntab.getText();
	}
	
	public String visibilityOfv2TextunderDesigntab() {
		IHGUtil.waitForElement(driver, 10, v2TextunderDesigntab);
		return v2TextunderDesigntab.getText();
	}
	
	public String visibilityOfappointmentMethodTextunderDesigntab() {
		IHGUtil.waitForElement(driver, 10, apointmentMethodTextunderDesigntab);
		return apointmentMethodTextunderDesigntab.getText();
	}
	
	public String visibilityOfinOfficeTextunderDesigntab() {
		IHGUtil.waitForElement(driver, 10, inOfficeTextunderDesigntab);
		return inOfficeTextunderDesigntab.getText();
	}
	
	public String visibilityOfdeliveryMethodTextunderDesigntab() {
		IHGUtil.waitForElement(driver, 10, deliveryMethodTextunderDesigntab);
		return deliveryMethodTextunderDesigntab.getText();
	}
	
	public String visibilityOftimingtextUnderdesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, timingtextUnderdesignTab);
		return timingtextUnderdesignTab.getText();
	}
	
	public boolean visibilityOftimingUnitstextUnderdesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, timingUnitstextUnderdesignTab);
		if(timingUnitstextUnderdesignTab.isDisplayed()) {
		log("1st day is displayed");
		return true;
		}
		else {
			return false;
		}
	}
	
	public String visibilityOfdefaultTimingtextUnderdesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTimingtextUnderdesignTab);
		return defaultTimingtextUnderdesignTab.getText();
	}
	
	public boolean visibilityOfdefaultTimingUnitstUnderdesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTimingUnitstUnderdesignTab);
		if(defaultTimingUnitstUnderdesignTab.isDisplayed())
		{
			log("3rd day is displayed");
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public String visibilityOfdefaultTimingstextUnderdesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTimingstextUnderdesignTab);
		return defaultTimingstextUnderdesignTab.getText();
	}
	
	public boolean visibilityOfdefaultTimingUnitstextUnderdesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10,defaultTimingUnitstextUnderdesignTab );
		if(defaultTimingUnitstextUnderdesignTab.isDisplayed())
		{
			log("5th day is displayed");
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void ClickonAddbutton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, ClickonAddbutton);
		jse.executeScript("arguments[0].click();", ClickonAddbutton);

	}
	
	public void clickOntimingdropdownunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, clickOntimingdropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", clickOntimingdropdownunderDesigntab);

	}
	
	public void selectDaydropdownunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, selectDaydropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", selectDaydropdownunderDesigntab);

	}
	
	public void enterTimingunitUnderdesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, enterTimingunitUnderdesignTab);
		enterTimingunitUnderdesignTab.clear();
		enterTimingunitUnderdesignTab.sendKeys("2");

	}
	
	public boolean visibilityOfClickonAddbutton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, ClickonAddbutton);
		if(ClickonAddbutton.isEnabled())
		{
			log("add button is displayed");
			return true;
		}
		else {
			
			log("add button is not displayed");
			return false;
		}
		
	}
	
	public void deleteTimingunitUnderdesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, deleteTimingunitUnderdesignTab);
		jse.executeScript("arguments[0].click();", deleteTimingunitUnderdesignTab);

	}

	public void saveChangesButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, saveChangesButton);
		jse.executeScript("arguments[0].click();", saveChangesButton);
		
	}
	
	public boolean visibilityOfnotificationsTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, notificationTab);
		if(notificationTab.isDisplayed()) {
		log("notification tab is displayed");
		return true;
		}
		else {
			log("notification tab is not displayed");
			return false;
		}
	}
	
	public void selectPracticeLanguagePreference(String language) {
		IHGUtil.PrintMethodName();
		Actions ac= new Actions(driver);
	    if(prcticeLanguage.getText().equals(language)) {
		log("select language as: "+prcticeLanguage.getText());
	   } else {
		   ac.click(prcticeLanguage).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
		   log("select language as: "+prcticeLanguage.getText());
	   }
	}
	
	public String characterCount() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, characterCount);
		return characterCount.getText();
		
	}
	
	public void disableDisplayPatientFirstNameCheckbox() throws InterruptedException {
		boolean enabled = displayPatientFirstNameCheckbox.isEnabled();
		if (!enabled) {
			log("Disable 'Display patient's' first name' checkbox");
			displayPatientFirstNameCheckbox.click();
			Thread.sleep(3000);
		}
	}
	
	public void clickTimingDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, clickTimingDropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", clickTimingDropdownunderDesigntab);
	}
	
	public void selectHourDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectHourDropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", selectHourDropdownunderDesigntab);
	}
	
	public void enterTimingUnitunderDesigntab(String timingunithour) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enterTimingUnitunderDesigntab);
		enterTimingUnitunderDesigntab.clear();
		enterTimingUnitunderDesigntab.sendKeys(timingunithour);
	}
	
	public boolean visibilityOfselectHourDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectHourDropdownunderDesigntab);
		if(selectHourDropdownunderDesigntab.isDisplayed()) {
			log("Hours timing is displayed");
			return true;
		} else {
			log("Hours timing is not displayed");
			return false;
		}
		
	}
	
	public boolean visibilityOfenterTimingUnitunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enterTimingUnitunderDesigntab);
		if (enterTimingUnitunderDesigntab.isDisplayed()) {
			log("Hours timing units is displayed");
			return true;
		} else {
			log("Hours timing units is displayed");
			return false;
		}
	}
	
	public void selectMinutesTimingDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectMinutesTimingDropdownunderDesigntab);
		selectMinutesTimingDropdownunderDesigntab.click();
	}
	
	public void enter2ndTimingUnitunderDesigntab(String timingunitminutes) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter2ndTimingUnitunderDesigntab);
		enter2ndTimingUnitunderDesigntab.clear();
		enter2ndTimingUnitunderDesigntab.sendKeys(timingunitminutes);
	}

	public void click2ndTimingDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, click2ndTimingDropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", click2ndTimingDropdownunderDesigntab);
	}

	public boolean visibilityOfselectMinutesTimingDropdownunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectMinutesTimingDropdownunderDesigntab);
		if(selectMinutesTimingDropdownunderDesigntab.isDisplayed()) {
			log("Minutes timing is displayed");
			return true;
		} else {
			log("Minutes timing is not displayed");
			return false;
		}
	}

	public boolean visibilityOfenter2ndTimingUnitunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter2ndTimingUnitunderDesigntab);
		if(enter2ndTimingUnitunderDesigntab.isDisplayed()) {
			log("Minutes timing units is displayed");
			return true;
		} else {
			log("Minutes timing units is not displayed");
			return false;
		}
	}
	
	public void selectDayDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectDayDropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", selectDayDropdownunderDesigntab);
	}
	
	public void click3rdTimingDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, click3rdTimingDropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", click3rdTimingDropdownunderDesigntab);
	}
	
	public void enter3rdTimingUnitunderDesigntab(String timingunitday) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter3rdTimingUnitunderDesigntab);
		enter3rdTimingUnitunderDesigntab.clear();
		enter3rdTimingUnitunderDesigntab.sendKeys(timingunitday);
	}

	public boolean visibilityOfselectDayTimingDropdownunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectDayDropdownunderDesigntab);
		if(selectDayDropdownunderDesigntab.isDisplayed()) {
			log("Days timing is displayed");
			return true;
		} else {
			log("Days timing is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfenter3rdTimingUnitunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter3rdTimingUnitunderDesigntab);
		if(enter3rdTimingUnitunderDesigntab.isDisplayed()) {
			log("3rd timingunit is displayed");
			return true;
		} else {
			log("3rd timingunit is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfTiming() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, timing);
		if(timing.isDisplayed()) {
			log("timing is displayed in Days,Hours,Minutes");
			return true;
		} else {
			log("timing is not displayed in Days,Hours,Minutes");
			return false;
		}
	}
	
	public boolean visibilityOfTimingUnit() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, timingunit);
		if(timingunit.isDisplayed()) {
			log("timingunit is displayed in sequence");
			return true;
		} else {
			log("timingunit is not displayed in sequence");
			return false;
		}
	}
	
	public void enter1stMinutesTimingUnitunderDesigntab(String timingunitminutes) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter1stMinutesTimingUnitunderDesigntab);
		enter1stMinutesTimingUnitunderDesigntab.clear();
		enter1stMinutesTimingUnitunderDesigntab.sendKeys(timingunitminutes);
	}
	
	public void enter2ndHoursTimingUnitunderDesignTab(String timingunithours) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter2ndHoursTimingUnitunderDesignTab);
		enter2ndHoursTimingUnitunderDesignTab.clear();
		enter2ndHoursTimingUnitunderDesignTab.sendKeys(timingunithours);
	}
	
	public boolean visibilityOfenter1stMinutesTimingUnitunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter1stMinutesTimingUnitunderDesigntab);
		if(enter1stMinutesTimingUnitunderDesigntab.isDisplayed()) {
			log("Minutes timingunit is displayed");
			return true;
		} else {
			log("Minutes timingunit is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfenter2ndHoursTimingUnitunderDesignTab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter2ndHoursTimingUnitunderDesignTab);
		if(enter2ndHoursTimingUnitunderDesignTab.isDisplayed()) {
			log("Hours timingunit is displayed");
			return true;
		} else {
			log("Hours timingunit is not displayed");
			return false;
		}
	}
	
	public void select2ndHourDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, select2ndHourDropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", select2ndHourDropdownunderDesigntab);
	}
	
	public void enter3rdHourtimingUnitunderDesigntab(String timingunithour) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter3rdHourtimingUnitunderDesigntab);
		enter3rdHourtimingUnitunderDesigntab.clear();
		enter3rdHourtimingUnitunderDesigntab.sendKeys(timingunithour);
	}
	
	public void click4thTimingDropdownunderDesigntab() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, click4thTimingDropdownunderDesigntab);
		jse.executeScript("arguments[0].click();", click4thTimingDropdownunderDesigntab);
	}
	
	public void enter4thTimingUnitunderDesigntab(String timingunitday) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter4thTimingUnitunderDesigntab);
		enter4thTimingUnitunderDesigntab.clear();
		enter4thTimingUnitunderDesigntab.sendKeys(timingunitday);
	}
	
	public boolean visibilityOfselect2ndHourDropdownunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, select2ndHourDropdownunderDesigntab);
		if(select2ndHourDropdownunderDesigntab.isDisplayed()) {
			log("Hours is displayed");
			return true;
		} else {
			log("Hours is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfenter3rdHourtimingUnitunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter3rdHourtimingUnitunderDesigntab);
		if(enter3rdHourtimingUnitunderDesigntab.isDisplayed()) {
			log("Hours timingunit is displayed");
			return true;
		} else {
			log("Hours timingunit is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfselectDayDropdownunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectDayDropdownunderDesigntab);
		if(selectDayDropdownunderDesigntab.isDisplayed()) {
			log("Day timing is displayed");
			return true;
		} else {
			log("Day timing is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfenter4thTimingUnitunderDesigntab() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, enter4thTimingUnitunderDesigntab);
		if(enter4thTimingUnitunderDesigntab.isDisplayed()) {
			log("Day timingunit is displayed");
			return true;
		} else {
			log("Day timingunit is not displayed");
			return false;
		}
	}
	
	public void removeTimingAndTimingUnitDev() {
		IHGUtil.PrintMethodName();
		for(int i=4;i>=1;i--) {
		try {
			driver.findElement(By.xpath("(//div[@class=' css-1s2u09g-control'])[" + i + "]")).isDisplayed();
			WebElement timeUnitMinus = driver
					.findElement(By.xpath("(//div[@class='cadence-remove-icon'])[" + i + "]"));
			timeUnitMinus.click();
				log(i+ " Timing and timing unit textbox is removed");
			} catch (NoSuchElementException e) {
				log(i+ " Timing and timing unit textbox is not present");
			}
		}
	}
	
	public void clickOnSaveChangesbutton() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, saveChangesButton);
		jse.executeScript("arguments[0].click();", saveChangesButton);
		Thread.sleep(5000);
	}
	
	public void removeTimingAndTimingUnitDemo() {
		IHGUtil.PrintMethodName();
		for(int i=4;i>=1;i--) {
		try {
			driver.findElement(By.xpath("(//div[@class=' css-yk16xz-control'])[" + i + "]")).isDisplayed();
			WebElement timeUnitMinus = driver
					.findElement(By.xpath("(//div[@class='cadence-remove-icon'])[" + i + "]"));
			timeUnitMinus.click();
				log(i+ " Timing and timing unit textbox is removed");
			} catch (NoSuchElementException e) {
				log(i+ " Timing and timing unit textbox is not present");
			}
		}
	}
	
	public String getTimingTextForEmail() {
		IHGUtil.PrintMethodName();
		log("Timing Text for Email : "+timingForEmail.getText());
		return timingForEmail.getText();
	}
	
	public String getTimingUnitTextForEmail() {
		IHGUtil.PrintMethodName();
		log("Timing Unit Text for Email: "+timingUnitForEmail.getText());
		return timingUnitForEmail.getText();
	}
	
	public void addTimingAndTimingUnit() {
		for(int i=1;i<=3;i++) {
		try {
			driver.findElement(By.xpath("(//div[@class=' css-1s2u09g-control'])[" + i + "]")).isDisplayed();
				log(i+ " Timing and timing unit textbox is present");
			} catch (NoSuchElementException e) {
				log("Add"+ i+" timing and timing unit textbox");
				addButtonInEdit.click();
			}
		}
	}
	
	public void enterTimingAndTimingUnitDemo(int pathIndex, String timing, String timingUnit) throws InterruptedException {
		IHGUtil.PrintMethodName();
		log("Select timing and timing unit for: " + timing);
		jse.executeScript("arguments[0].click();",
				driver.findElement(By.xpath("(//div[@class=' css-yk16xz-control'])[" + pathIndex + "]")));
		Actions action = new Actions(driver);
		action.sendKeys(driver.findElement(By.xpath("(//div[@class=' css-yk16xz-control'])[" + pathIndex + "]")),
				timing).sendKeys(Keys.ENTER).build().perform();
		driver.findElement(By.xpath("(//input[@class='cadence-period-value'])[" + pathIndex + "]")).clear();
		driver.findElement(By.xpath("(//input[@class='cadence-period-value'])[" + pathIndex + "]"))
				.sendKeys(timingUnit);
	}
	
	public String getTimingTextForSms() {
		IHGUtil.PrintMethodName();
		log("Timing Text for Email : "+timingForSMS.getText());
		return timingForSMS.getText();
	}
	
	public String getTimingUnitTextForSms() {
		IHGUtil.PrintMethodName();
		log("Timing Unit Text for Email: "+timingUnitForSMS.getText());
		return timingUnitForSMS.getText();
	}
	
	public boolean visibilityOfDefaultTiming1ForDay() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, defaultTiming1ForDay);
		if (defaultTiming1ForDay.isDisplayed()) {
			log("Default time Days is displayed");
			return true;
		} else {
			log("Default time Days is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfDefaultTiming2ForDay() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, defaultTiming2ForDay);
		if (defaultTiming2ForDay.isDisplayed()) {
			log("Default time Days is displayed");
			return true;
		} else {
			log("Default time Days is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfDefaultTiming3ForDay() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, defaultTiming3ForDay);
		if (defaultTiming3ForDay.isDisplayed()) {
			log("Default time Days is displayed");
			return true;
		} else {
			log("Default time Days is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfDefaultTimingUnit1ForDay() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, defaultTimingUnit1ForDay);
		if (defaultTimingUnit1ForDay.isDisplayed()) {
			log("Default time unit 1 is displayed");
			return true;
		} else {
			log("Default time unit 1 is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfDefaultTimingUnit2ForDay() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTimingUnit2ForDay);
		if (defaultTimingUnit2ForDay.isDisplayed()) {
			log("Default time unit 3 is displayed");
			return true;
		} else {
			log("Default time unit 3 is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfDefaultTimingUnit3ForDay() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTimingUnit3ForDay);
		if (defaultTimingUnit3ForDay.isDisplayed()) {
			log("Default time unit 5 is displayed");
			return true;
		} else {
			log("Default time unit 5 is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfselectDayForTiming() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, defaultTiming1ForDay);
		if (defaultTiming1ForDay.isDisplayed()) {
			log("Default time day is displayed");
			return true;
		} else {
			log("Default time day is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfselectHourforTiming() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectHourforTiming);
		if (selectHourforTiming.isDisplayed()) {
			log("Default time Hour is displayed");
			return true;
		} else {
			log("Default time Hour is not displayed");
			return false;
		}
	}
	
	public boolean visibilityOfselectMinutesforTiming() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, selectMinutesforTiming);
		if (selectMinutesforTiming.isDisplayed()) {
			log("Default time Minutes is displayed");
			return true;
		} else {
			log("Default time Minutes is not displayed");
			return false;
		}
	}
	
	public void enterTimingUnitUnderDesignTab(String timingunit) {
		IHGUtil.waitForElement(driver, 10, timingUnitstextUnderdesignTab);
		timingUnitstextUnderdesignTab.clear();
		timingUnitstextUnderdesignTab.sendKeys(timingunit);
		
	}
	
	public boolean visibilityTimingUnitTextUnderDesignTab() {
		IHGUtil.waitForElement(driver, 10, timingUnitTextUnderDesignTab);
		if(timingUnitTextUnderDesignTab.isDisplayed()) {
		log("timing units is displayed");
		return true;
		}
		else {
			log("timing units is not displayed displayed");
			return false;
		}
	}
	
	public void enterTimingUnit(String timing) {
		IHGUtil.waitForElement(driver, 10, enterTimingUnit);
		enterTimingUnit.clear();
		enterTimingUnit.sendKeys(timing);
		
	}
	
	public boolean visibilityofenterTimingUnit() {
		IHGUtil.waitForElement(driver, 10, enterTimingUnit);
		if(enterTimingUnit.isDisplayed()) {
			log("timing unit is displayed ");
			return true;
		}
		else {
			log("timing unit is not displayed ");
			return false;
		}
	}
	
	public void selectHourforTiming() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, selectHourforTiming);
		selectHourforTiming.click();
		
	}
	
	public void selectMinutesforTiming() throws InterruptedException {
		IHGUtil.waitForElement(driver, 10, selectMinutesforTiming);
		selectMinutesforTiming.click();
		
	}
	
	public void enterTimingUnitforMinutes(String minutes) {
		IHGUtil.waitForElement(driver, 10, enterTimingUnit);
		enterTimingUnit.clear();
		enterTimingUnit.sendKeys(minutes);
	}
	
	public boolean visibilityOfRemoveTimingUnit() {
		IHGUtil.PrintMethodName();
		if(removeButtonInEditFor3rdTimingUnit.isDisplayed()) {
			log("remove button of 3rd timing unit is displayed");
			return true;
		}
		else {
			log("remove button of 3rd timing unit is not displayed");
			return false;
		}
		
	}
	
	public void removeButtonInEditFor3rdTimingUnit() {
		IHGUtil.waitForElement(driver, 5, removeButtonInEditFor3rdTimingUnit);
		removeButtonInEditFor3rdTimingUnit.click();
		
	}
	
	public void removeButtonInEditFor2ndTimingUnit() {
		IHGUtil.waitForElement(driver, 5, removeButtonInEditFor2ndTimingUnit);
		removeButtonInEditFor2ndTimingUnit.click();
		
	}
	
	public void removeButtonInEditFor1stTimingUnit() {
		IHGUtil.waitForElement(driver, 5, removeButtonInEditFor1stTimingUnit);
		removeButtonInEditFor1stTimingUnit.click();
		
	}
	
	public boolean visibilityOfTimingUnitsInNotifpage() {
		IHGUtil.PrintMethodName();
		if(timingUnitsInNotifpage.isDisplayed()) {
			log("timing units are blank");
			return true;
		}
		else {
			log("timing units are not blank");
			return false;
		}
	}
	
	public void defaultTiming2ForDay() {
		IHGUtil.waitForElement(driver, 5, defaultTiming2ForDay);
		defaultTiming2ForDay.click();
		
	}
	
	public void defaultTiming3ForDay() {
		IHGUtil.waitForElement(driver, 5, defaultTiming3ForDay);
		defaultTiming3ForDay.click();
		
	}
	
	public void addThreeTimingAndTimingUnit() throws InterruptedException {
		IHGUtil.PrintMethodName();
		try {
			firstTimingAndTimingUnit.isDisplayed();
			log("First timing and timing unit is display");
		} catch (NoSuchElementException e) {
			addButtonInEdit.click();
			log("First timing and timing unit is added");
		}

		try {
			secondTimingAndTimingUnit.isDisplayed();
			log("Second timing and timing unit is display");
		} catch (NoSuchElementException e) {
			addButtonInEdit.click();
			log("Second timing and timing unit is added");
		}

		try {
			thirdTimingAndTimingUnit.isDisplayed();
			log("Third timing and timing unit is display");
		} catch (NoSuchElementException e) {
			addButtonInEdit.click();
			log("Third timing and timing unit is added");
		}

		try {
			fourthTimingAndTimingUnit.isDisplayed();
			removeButtonInEdit.click();
			log("Fourth timing and timing unit is deleted");
		} catch (NoSuchElementException e) {
			log("Fourth timing and timing unit is not display");
		}
	}

	public boolean isAddButtonEnable() {
		IHGUtil.PrintMethodName();
		boolean visibility = false;
		try {
			visibility = addButtonInEdit.isEnabled();
			log("Add button is Enabled");
			return visibility;
		} catch (NoSuchElementException e) {
			log("Add button is not Enabled");
			return visibility;
		}
	}

	public void removeTimingAndTimingUnit(int pathIndex) {
		IHGUtil.PrintMethodName();
		WebElement timeUnitMinus = driver
				.findElement(By.xpath("(//div[@class='cadence-remove-icon'])[" + pathIndex + "]"));
		timeUnitMinus.click();
	}

	public void clickOnSaveChangesButton() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, saveChangesButton);
		jse.executeScript("arguments[0].click();",saveChangesButton);
	}
	
	public void addTimingUnit() {
		for(int i=1;i<=4;i++) {
		try {
			driver.findElement(By.xpath("(//div[@class=' css-1s2u09g-control'])[" + i + "]")).isDisplayed();
				log(i+ " Timing and timing unit textbox is present");
			} catch (NoSuchElementException e) {
				log("Add"+ i+" timing and timing unit textbox");
				addButtonInEdit.click();
			}
		}
	}
	
	public void enterCharInAddArrivalInstTextBox(String instructionMessage) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 5, additionalArrivalInstructionsTextBoxText);
		additionalArrivalInstructionsTextBoxText.clear();
		additionalArrivalInstructionsTextBoxText.sendKeys(instructionMessage);
	}
	
	public void addTimingAndTimingUnitDemo() {
		for(int i=1;i<=3;i++) {
		try {
			driver.findElement(By.xpath("(//div[@class=' css-yk16xz-control'])[" + i + "]")).isDisplayed();
				log(i+ " Timing and timing unit textbox is present");
			} catch (NoSuchElementException e) {
				log("Add "+ i+" timing and timing unit textbox");
				addButtonInEdit.click();
			}
		}
	}

}
