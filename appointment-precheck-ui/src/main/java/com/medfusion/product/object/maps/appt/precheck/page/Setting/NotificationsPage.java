// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.page.Setting;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class NotificationsPage extends BasePageObject {

	@FindBy(how = How.XPATH, using = "//li[@class='mf-nav__item mf-nav__secondary-menu__item']/a")
	private WebElement notificationTab;

	@FindBy(how = How.CSS, using = "div.notification-features-checkbox > div:nth-child(3) > input")
	private WebElement displayPatientFirstNameCheckbox;

	@FindBy(how = How.XPATH, using = "//button[text()='Save']")
	private WebElement saveButton;

	@FindBy(how = How.XPATH, using = "//*[@class='mf-body-header mf-appointments-header']/h1[text()='Notifications']")
	private WebElement notificationTitle;

	@FindBy(how = How.CSS, using = "div.notification-features-checkbox > div:nth-child(1) > input")
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

	@FindBy(how = How.XPATH, using = "(//*[text()='Appointment Reminder'])[2]")
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
		saveButton.click();
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
		IHGUtil.waitForElement(driver, 5, broadcastMessagingCheckbox);
		boolean selected = broadcastMessagingCheckbox.isSelected();
		if (!selected) {
			log("Enable 'Broadcast Messaging' checkbox");
			broadcastMessagingCheckbox.click();
			Thread.sleep(10000);
		}
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
}
