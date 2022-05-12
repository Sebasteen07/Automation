// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.appt.precheck.stepDefinations;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.medfusion.common.utils.EnvironmentTypeUtil.EnvironmentType;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.common.utils.YopMail;
import com.medfusion.product.appt.precheck.payload.AptPrecheckPayload;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.Main.ApptPrecheckMainPage;
import com.medfusion.product.object.maps.appt.precheck.page.Appointments.AppointmentsPage;
import com.medfusion.product.object.maps.appt.precheck.page.CurbsideCheckIn.CurbsideCheckInPage;
import com.medfusion.product.object.maps.appt.precheck.page.Login.AppointmentPrecheckLogin;
import com.medfusion.product.object.maps.appt.precheck.page.Setting.FormsPage;
import com.medfusion.product.object.maps.appt.precheck.page.Setting.GeneralPage;
import com.medfusion.product.object.maps.appt.precheck.page.Setting.NotificationsPage;
import com.medfusion.product.object.maps.appt.precheck.page.Setting.PreCheckPage;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.BaseTest;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPrecheck;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfNotificationSubscriptionManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class ApptPrecheckSteps extends BaseTest {
	PropertyFileLoader propertyData; 
	AppointmentPrecheckLogin loginPage;
	ApptPrecheckMainPage mainPage;
	AppointmentsPage apptPage;
	NotificationsPage notifPage;
	CommonMethods commonMethod;
	GeneralPage generalPage;
	PreCheckPage precheckPage;
	FormsPage formsPage;
	PostAPIRequestMfAppointmentScheduler apptSched;
	MfAppointmentSchedulerPayload payload;
	HeaderConfig headerConfig;
	AccessToken accessToken;
	PostAPIRequestAptPrecheck aptPrecheckPost;
	AptPrecheckPayload aptPrecheckPayload;
	CurbsideCheckInPage curbsidePage;
	PostAPIRequestMfNotificationSubscriptionManager subsManager;

	@Given("user lauch practice provisioning url")
	public void user_lauch_practice_provisioning_url() throws Exception {
		propertyData = new PropertyFileLoader();
		apptPage = new AppointmentsPage(driver);
		notifPage = new NotificationsPage(driver);
		mainPage = new ApptPrecheckMainPage(driver);
		notifPage = new NotificationsPage(driver);
		curbsidePage = new CurbsideCheckInPage(driver);
		generalPage = new GeneralPage();
		curbsidePage = new CurbsideCheckInPage(driver);
		precheckPage = new PreCheckPage(driver);
		formsPage = new FormsPage(driver);
		apptSched = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		accessToken = AccessToken.getAccessToken();
		aptPrecheckPost = PostAPIRequestAptPrecheck.getPostAPIRequestAptPrecheck();
		aptPrecheckPayload = AptPrecheckPayload.getAptPrecheckPayload();
		subsManager = PostAPIRequestMfNotificationSubscriptionManager
				.getPostAPIRequestMfNotificationSubscriptionManager();
		commonMethod = new CommonMethods();
		log("Practice provisining url-- " + propertyData.getProperty("practice.provisining.url.ge"));
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		log("Verify medfusion page");
		assertTrue(loginPage.visibilityOfMedfusionLogo(), "Medfusion logo not displayed");
		assertTrue(loginPage.logInText().contains("Please log in."));
	}

	@When("user enter username and password")
	public void user_enter_username_and_password() throws Exception {
		log("Practice provisining login details");
		log("Username : " + propertyData.getProperty("practice.provisining.username.ge"));
		log("Password : " + propertyData.getProperty("practice.provisining.password.ge"));
		loginPage.login(propertyData.getProperty("practice.provisining.username.ge"),
				propertyData.getProperty("practice.provisining.password.ge"));
		scrollAndWait(200, 300, 5000);
		log("Verify practice Name-- " + apptPage.getPracticeName());
		assertTrue(apptPage.getPracticeName().contains(propertyData.getProperty("practice.name.ge")));
		log("Verify focus on Appointments page-- " + apptPage.getApptPageTitle());
		assertTrue(apptPage.getApptPageTitle().contains(propertyData.getProperty("appointment.page")));
	}

	@Then("schedule an appointments within one month")
	public void schedule_an_appointments_within_one_month() throws IOException {
		PostAPIRequestMfAppointmentScheduler apptSched = PostAPIRequestMfAppointmentScheduler
				.getPostAPIRequestMfAppointmentScheduler();
		MfAppointmentSchedulerPayload payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		HeaderConfig headerConfig = HeaderConfig.getHeaderConfig();
		AccessToken accessToken = AccessToken.getAccessToken();
		log("schedule more than 50 an appointments ");
		for (int i = 0; i < 54; i++) {
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
			log("Getting patients since timestamp: " + plus20Minutes);
			Response response = apptSched.aptPutAppointment(
					propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("mf.apt.scheduler.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.email"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()),
					propertyData.getProperty("mf.apt.scheduler.put.patient.id") + i,
					propertyData.getProperty("mf.apt.scheduler.put.appt.id") + i);
		}
	}

	@Then("enter date and time within one month")
	public void enter_date_and_time_within_one_month() throws Exception {
		log("Select date within one month");
		apptPage.enterStartTimeWithinMonth();
	}

	@Then("filter is applied for provider and Location")
	public void filter_is_applied_for_provider_and_location() throws Exception {
		log("Provider name-- " + propertyData.getProperty("provider.name.ge"));
		apptPage.enterProviderName(propertyData.getProperty("provider.name.ge"));
		log("Location name-- " + propertyData.getProperty("location.name.ge"));
		apptPage.enterLocationName(propertyData.getProperty("location.name.ge"));
	}

	@Then("select patients based on filters")
	public void select_patients_based_on_filters() throws InterruptedException {
		log("Select all patients");
		apptPage.selectAllCheckboxes();
		log("Selected patient count for broadcast");
		String bannerMessage = apptPage.getBannerMessage();
		log("banner meassage :" + bannerMessage);
		String getcount = bannerMessage.substring(11, 14);
		log("Total count on banner meassage :" + getcount);
	}

	@Then("send broadcast message to all selected patient")
	public void send_broadcast_to_all_selected_patient() throws Exception {
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
	}

	@And("verify count in banner message is coming correct")
	public void verify_the_count_in_banner_message_is_coming_correct() {
		log("Show count for failure and sucess on banner");
		assertTrue(apptPage.broadcastMessageSuccessStatus(),
				"Successful Broadcast message sent status was not displyed");
		assertTrue(apptPage.broadcastMessageFailedStatus(), "Failed Broadcast message sent status was not displyed");
		log("Verify Broadcast successful count in banner message is coming correct");
		log("Verify Broadcast successful count := " + apptPage.getBroadcastMessageSuccessStatus());
		assertEquals(apptPage.getBroadcastMessageSuccessStatus(), 50);
		log("Verify Broadcast failed count : " + apptPage.getBroadcastMessageFailedStatus());
		assertEquals(apptPage.getBroadcastMessageFailedStatus(), 0);
		driver.navigate().refresh();
	}

	@Then("logout from practice provisioning portal")
	public void logout_from_practice_provisioning_portal() throws Exception {
		apptPage.signOut();
	}

	@Then("schedule an appointments for future")
	public void schedule_an_appointments_for_future() throws IOException {
		log("Select date for future for provider and Location");
		PostAPIRequestMfAppointmentScheduler apptSched = PostAPIRequestMfAppointmentScheduler
				.getPostAPIRequestMfAppointmentScheduler();
		MfAppointmentSchedulerPayload payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		HeaderConfig headerConfig = HeaderConfig.getHeaderConfig();
		AccessToken accessToken = AccessToken.getAccessToken();
		for (int i = 0; i < 54; i++) {
			long now = System.currentTimeMillis();
			long nowPlus2Days = now + TimeUnit.MINUTES.toMillis(4000);
			log("Getting patients since timestamp: " + nowPlus2Days);
			Response response = apptSched.aptPutAppointment(
					propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("mf.apt.scheduler.practice.id"),
					payload.putAppointmentPayload(nowPlus2Days, propertyData.getProperty("mf.apt.scheduler.email"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()),
					propertyData.getProperty("mf.apt.scheduler.put.patient.id") + i,
					propertyData.getProperty("mf.apt.scheduler.put.appt.id") + i);
		}

	}

	@Then("enter future date and time")
	public void enter_future_date_and_time() throws InterruptedException {
		log("Select future date");
		apptPage.enterFutureStartTime();
	}

	@Then("enter date and time for backdated greater than one month")
	public void enter_date_and_time_for_backdated_greater_than_one_month() throws InterruptedException {
		log("Select date for backdated greater than one month for provider and Location");
		apptPage.enterOneMonthBackdatedStartTime();
		apptPage.enterEndtime();
	}

	@And("verify the count in banner message is coming correct for one month backdated")
	public void verify_the_count_in_banner_message_is_coming_correct_for_on_month_backdated() {
		log("Show count for failure and sucess on banner");
		assertTrue(apptPage.broadcastMessageSuccessStatus(),
				"Successful Broadcast message sent status was not displyed");
		assertTrue(apptPage.broadcastMessageFailedStatus(), "Failed Broadcast message sent status was not displyed");
		log("Verify Broadcast successful count in banner message is coming correct");
		log("Verify Broadcast successful count := " + apptPage.getBroadcastMessageSuccessStatus());
		log("Verify Broadcast failed count := " + apptPage.getBroadcastMessageFailedStatus());
		assertEquals(apptPage.getBroadcastMessageSuccessStatus(), 0);
	}

	@Then("filter is applied only for provider")
	public void filter_is_applied_only_for_provider() throws Exception {
		log("Enter Provider name-- " + propertyData.getProperty("provider.name.ge"));
		apptPage.enterProviderName(propertyData.getProperty("provider.name.ge"));
	}

	@Then("filter is applied only for location")
	public void filter_is_applied_only_for_location() throws Exception {
		log("Enter Location name-- " + propertyData.getProperty("location.name.ge"));
		apptPage.enterLocationName(propertyData.getProperty("location.name.ge"));
	}

	@Then("go to the setting dashboard and disable notifications checkbox from manage solution in general tab")
	public void go_to_the_setting_dashboard_and_disable_notifications_checkbox_from_manage_solution_in_general_tab()
			throws InterruptedException {
		mainPage = new ApptPrecheckMainPage(driver);
		mainPage.clickOnSettingTab();
		log("verify user should be on General setting dashboard");
		GeneralPage generalPage = GeneralPage.getGeneralPage();
		log("verify General setting Text: " + propertyData.getProperty("general.setting.title"));
		assertTrue(generalPage.generalSettingTitle().contains(propertyData.getProperty("general.setting.title")));
		log("verify Mamage solutions Text: " + propertyData.getProperty("manage.solution.board"));
		assertTrue(generalPage.manageSolutionTab().contains(propertyData.getProperty("manage.solution.board")));
		generalPage.uncheckingNotificationsCheckbox();
		generalPage.clickOnUpdateSettingbutton();
	}

	@Then("on appointments dashboard user is not able to see send reminder and broadcast message column")
	public void on_appointments_dashboard_user_is_not_able_to_see_send_reminder_and_broadcast_message_column_on()
			throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		log("verify user is not able to see send reminder and broadcast message column");
		assertFalse(apptPage.reminderColumn(), "user is able to see send reminder column");
		assertFalse(apptPage.broadcastColumn(), "user is able to see broadcast message column");
	}

	@And("on appointments dashboard user is not able to see send reminder and broadcast message button in actions dropdown")
	public void on_appointments_dashboard_user_is_not_able_to_see_send_reminder_and_broadcast_message_button_in_actions_dropdown() {
		apptPage.clickOnActions();
		log("verify user is not able to see reminder and broadcast message button in actions dropdown");
		assertFalse(apptPage.sendReminder(), "user is able to see send reminder button");
		assertFalse(apptPage.broadcastMessage(), "user is able to see broadcast message button");
	}

	@Then("go to the setting dashboard and enable notifications checkbox from manage solution in general tab")
	public void go_to_the_setting_dashboard_and_enable_notifications_checkbox_from_manage_solution_in_general_tab()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		log("verify user should be on General setting dashboard");
		GeneralPage generalPage = GeneralPage.getGeneralPage();
		generalPage.uncheckingNotificationsCheckbox();
		generalPage.clickOnUpdateSettingbutton();
	}

	@Then("select patients based on filters and select banner then move to second page")
	public void select_patients_based_on_filters_and_select_banner_then_move_to_second_page()
			throws InterruptedException {
		log("Select all patients");
		apptPage.selectAllCheckboxes();
		apptPage.clickOnBannerMessage();
		log("Verify user should be on second page");
		Appointment.pageNo = apptPage.jumpToNextPage();
		assertEquals(Appointment.pageNo, "2");
	}

	@Then("verify banner message should show count for failure and sucess")
	public void verify_banner_message_should_show_count_for_failure_and_sucess() {
		Appointment.bannerMessage = apptPage.getBannerAfterSendBroadcast();
		log("Banner meassage :" + Appointment.bannerMessage);
		log("Show count for failure and sucess on banner");
		assertTrue(apptPage.broadcastMessageSuccessStatus(),
				"Successful Broadcast message sent status was not displyed");
		assertTrue(apptPage.broadcastMessageFailedStatus(), "Failed Broadcast message sent status was not displyed");
	}

	@Then("verify after clicking on refresh button banner should not get close and current page should be displayed")
	public void verify_after_clicking_on_refresh_button_banner_should_not_get_close_and_current_page_should_be_displayed()
			throws InterruptedException {
		apptPage.clickOnRefreshTab();
		log("Verify banner message should be displayed as previous");
		assertEquals(Appointment.bannerMessage, apptPage.getBannerAfterSendBroadcast());
		log("Verify current page should be displayed");
		assertEquals(Appointment.pageNo, apptPage.getPageNo());
	}

	@When("from setting dashboard in notifications disable Broadcast messaging checkbox")
	public void from_setting_dashboard_in_notifications_disable_broadcast_messaging_checkbox()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.disableBroadcastMessagingCheckbox();
		notifPage.saveNotification();
	}

	@Then("verify on appointments dashboard user is not able to see Broadcast message button in actions dropdown")
	public void verify_on_appointments_dashboard_user_is_not_able_to_see_broadcast_message_button_in_actions_dropdown()
			throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		apptPage.clickOnActions();
		log("verify user is not able to see broadcast message button in actions dropdown");
		assertFalse(apptPage.broadcastMessage(), "user is able to see broadcast message button");
	}

	@When("from setting dashboard in notifications Enable Broadcast messaging checkbox")
	public void from_setting_dashboard_in_notifications_enable_broadcast_messaging_checkbox()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.disableBroadcastMessagingCheckbox();
		notifPage.saveNotification();
	}

	@Then("verify on appointments dashboard user is able to see Broadcast message button in actions dropdown")
	public void verify_on_appointments_dashboard_user_is_able_to_see_broadcast_message_button_in_actions_dropdown() {
		mainPage.clickOnAppointmentsTab();
		apptPage.clickOnActions();
		log("verify user is able to see broadcast message button in actions dropdown");
		assertTrue(apptPage.broadcastMessage(), "user is not able to see broadcast message button");
	}

	@When("schedule an appointment without email and phone number")
	public void schedule_an_appointment_without_email_and_phone_number() throws NullPointerException, IOException {
		PostAPIRequestMfAppointmentScheduler apptSched = PostAPIRequestMfAppointmentScheduler
				.getPostAPIRequestMfAppointmentScheduler();
		MfAppointmentSchedulerPayload payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		HeaderConfig headerConfig = HeaderConfig.getHeaderConfig();
		AccessToken accessToken = AccessToken.getAccessToken();
		log("schedule an appointments ");
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, null, ""),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()),
				propertyData.getProperty("patient.id.with.blank.email.and.phone"),
				propertyData.getProperty("appt.id.with.blank.email.and.phone"));
	}

	@Then("select patient which is having blank email and phone number and send broadcast message")
	public void select_patient_which_is_having_blank_email_and_phone_number_and_send_broadcast_message()
			throws Exception {
		mainPage.clickOnAppointmentsTab();
		apptPage.selectPatientWithoutEmailAndPhone();
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		log("banner meassage :" + apptPage.broadcastBannerMessage());
	}

	@Then("verify failed count will consider for blank email and phone number")
	public void verify_failed_count_will_consider_for_blank_email_and_phone_number() {
		assertTrue(apptPage.broadcastMessageSuccessStatus(),
				"Successful Broadcast message sent status was not displyed");
		assertTrue(apptPage.broadcastMessageFailedStatus(), "Failed Broadcast message sent status was not displyed");
		log("Verify Broadcast successful count in banner message is coming correct");
		log("Verify Broadcast successful count := " + apptPage.getBroadcastMessageSuccessStatus());
		assertEquals(apptPage.getBroadcastMessageSuccessStatus(), 0);
		log("Verify Broadcast failed count : " + apptPage.getBroadcastMessageFailedStatus());
		assertEquals(apptPage.getBroadcastMessageFailedStatus(), 1);
	}

	@When("schedule an appointment with invalid email and phone number")
	public void schedule_an_appointment_with_invalid_email_and_phone_number() throws Exception {
		PostAPIRequestMfAppointmentScheduler apptSched = PostAPIRequestMfAppointmentScheduler
				.getPostAPIRequestMfAppointmentScheduler();
		MfAppointmentSchedulerPayload payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		HeaderConfig headerConfig = HeaderConfig.getHeaderConfig();
		AccessToken accessToken = AccessToken.getAccessToken();
		log("schedule an appointments ");
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, "12345678", "abcxyzgmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()),
				propertyData.getProperty("patient.id.with.invalid.email.and.phone"),
				propertyData.getProperty("appt.id.with.invalid.email.and.phone"));
	}

	@Then("select patient which is having invalid email and blank phone number and send broadcast message")
	public void select_patient_which_is_having_invalid_email_and_blank_phone_number_and_send_broadcast_message()
			throws Exception {
		mainPage.clickOnAppointmentsTab();
		apptPage.selectPatientWithInvalidEmailAndPhone();
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		log("banner meassage :" + apptPage.broadcastBannerMessage());
	}

	@Then("verify broadcast message send successfully and success count will consider for invalid email and and blank phone number")
	public void verify_broadcast_message_send_successfully_and_success_count_will_consider_for_invalid_email_and_and_blank_phone_number() {
		log("Show count for failure and sucess on banner");
		assertTrue(apptPage.broadcastMessageSuccessStatus(),
				"Successful Broadcast message sent status was not displyed");
		assertTrue(apptPage.broadcastMessageFailedStatus(), "Failed Broadcast message sent status was not displyed");
		log("Verify Broadcast successful count in banner message is coming correct");
		log("Verify Broadcast successful count := " + apptPage.getBroadcastMessageSuccessStatus());
		assertEquals(apptPage.getBroadcastMessageSuccessStatus(), 1);
		log("Verify Broadcast failed count : " + apptPage.getBroadcastMessageFailedStatus());
		assertEquals(apptPage.getBroadcastMessageFailedStatus(), 0);
	}

	@When("schedule an appointment with invalid phone number and blank email")
	public void schedule_an_appointment_with_invalid_phone_number_and_blank_email() throws Exception {
		PostAPIRequestMfAppointmentScheduler apptSched = PostAPIRequestMfAppointmentScheduler
				.getPostAPIRequestMfAppointmentScheduler();
		MfAppointmentSchedulerPayload payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		HeaderConfig headerConfig = HeaderConfig.getHeaderConfig();
		AccessToken accessToken = AccessToken.getAccessToken();
		log("schedule an appointments ");
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, "12345678", ""),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()),
				propertyData.getProperty("patient.id.with.invalid.phone"),
				propertyData.getProperty("appt.id.with.blank.email"));
	}

	@Then("select patient which is having invalid phone number and blank email and send broadcast message")
	public void select_patient_which_is_having_invalid_phone_number_and_blank_email_and_send_broadcast_message()
			throws Exception {
		mainPage.clickOnAppointmentsTab();
		apptPage.selectPatientWithInvalidPhoneAndBlankEmail();
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		log("banner meassage :" + apptPage.broadcastBannerMessage());
	}

	@Then("verify broadcast message send successfully and count will consider for invalid phone number and blank email")
	public void verify_broadcast_message_send_successfully_and_count_will_consider_for_invalid_phone_number_and_blank_email() {
		assertTrue(apptPage.broadcastMessageSuccessStatus(),
				"Successful Broadcast message sent status was not displyed");
		assertTrue(apptPage.broadcastMessageFailedStatus(), "Failed Broadcast message sent status was not displyed");
		log("Verify Broadcast successful count in banner message is coming correct");
		log("Verify Broadcast successful count := " + apptPage.getBroadcastMessageSuccessStatus());
		assertEquals(apptPage.getBroadcastMessageSuccessStatus(), 0);
		log("Verify Broadcast failed count : " + apptPage.getBroadcastMessageFailedStatus());
		assertEquals(apptPage.getBroadcastMessageFailedStatus(), 1);
	}

	@Then("also verify banner will close after clicked on cross button")
	public void also_verify_banner_will_close_after_clicked_on_cross_button() throws InterruptedException {
		log("Broadcast message is --" + apptPage.broadcastBannerMessage());
		assertTrue(apptPage.visibilityOfBroadcastMessage());
		apptPage.clickOnBannerCrossButton();
		assertFalse(apptPage.visibilityOfBroadcastMessage());
	}

	@When("select patients and click on actions dropdown")
	public void select_patients_and_click_on_actions_dropdown() throws InterruptedException {
		log("Select all patients");
		apptPage.selectAllCheckboxes();
		apptPage.clickOnActions();
	}

	@When("verify count will be reflected on send reminder and broadcast message button")
	public void verify_count_will_be_reflected_on_send_reminder_and_broadcast_message_button() {
		String broadcastMessageCount = apptPage.getbroadcastMessageText();
		String getBroadcastCount = broadcastMessageCount.substring(19, 20);
		int broadcastCount = Integer.parseInt(getBroadcastCount);
		if (broadcastCount > 0) {
			assertTrue(true);
			log("Total broadcast message count :" + broadcastCount);
		} else {
			assertTrue(false);
			log("No total count for broadcast message ");
		}
		String sendReminderCount = apptPage.getSendReminderText();
		String getReminderCount = sendReminderCount.substring(15, 16);
		int reminderCount = Integer.parseInt(getReminderCount);
		log("Total count on banner meassage :" + reminderCount);
		if (reminderCount > 0) {
			assertTrue(true);
			log("Total send reminder count :" + broadcastCount);
		} else {
			assertTrue(false);
			log("No total count for send reminder");
		}
	}

	@When("from setting dashboard in general enable email check box and disable text checkbox")
	public void from_setting_dashboard_in_general_enable_email_check_box_and_disable_text_checkbox()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.enableBroadcastMessagingCheckbox();
		notifPage.saveNotification();
		generalPage.clickOnGeneralTab();
		log("Disable text checkbox");
		generalPage.enableAndDisableTextCheckbox();
		generalPage.clickOnUpdateSettingbutton();
		mainPage.clickOnAppointmentsTab();
	}

	@Then("verify on appointment dashboard user is able to see only mail column under send reminder column and Text column is disappear")
	public void verify_on_appointment_dashboard_user_is_able_to_see_only_mail_column_under_send_reminder_column_and_text_column_is_disappear() {
		log("verify text column will not display under send reminder column on oppointments dashboard");
		assertFalse(apptPage.sendRemibderTextColumn());
	}

	@And("from setting dashboard in general enable email check box and enable text checkbox")
	public void from_setting_dashboard_in_general_enable_email_check_box_and_enable_text_checkbox()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		log("Enable text checkbox");
		generalPage.enableAndDisableTextCheckbox();
		generalPage.clickOnUpdateSettingbutton();
		mainPage.clickOnAppointmentsTab();
	}

	@Then("verify on appointment dashboard user is able to see only mail column under broadcast message column and Text column is disappear")
	public void verify_on_appointment_dashboard_user_is_able_to_see_only_mail_column_under_broadcast_message_column_and_text_column_is_disappear() {
		log("verify text coloumn will not display under broadcast message coloumn on oppointments dashboard");
		assertFalse(apptPage.broadcastMessageTextColumn());
	}

	@When("from setting dashboard in general text checkbox is enable and email checkbox is disable")
	public void from_setting_dashboard_in_general_text_checkbox_is_enable_and_email_checkbox_is_disable()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.enableBroadcastMessagingCheckbox();
		notifPage.saveNotification();
		generalPage.clickOnGeneralTab();
		log("Disable email checkbox");
		generalPage.enableAndDisableEmailCheckbox();
		generalPage.clickOnUpdateSettingbutton();
		mainPage.clickOnAppointmentsTab();
	}

	@Then("verify on appointment dashboard user is able to see only text column under broadcast message column and mail column is disappear")
	public void verify_on_appointment_dashboard_user_is_able_to_see_only_text_column_under_broadcast_message_column_and_mail_column_is_disappear()
			throws InterruptedException {
		log("verify email column will not display under broadcast message column on oppointments dashboard");
		assertTrue(apptPage.visibilityBroadcastMessageTextColumn());
		assertFalse(apptPage.broadcastMessageEmailColumn());
	}

	@And("from setting dashboard in general enable email check box")
	public void from_setting_dashboard_in_general_enable_email_check_box() throws InterruptedException {
		mainPage.clickOnSettingTab();
		log("Enable text checkbox");
		generalPage.enableAndDisableEmailCheckbox();
		generalPage.clickOnUpdateSettingbutton();
	}

	@Then("verify on appointment dashboard user is able to see only text column under send reminder column and mail column is disappear")
	public void verify_on_appointment_dashboard_user_is_able_to_see_only_text_column_under_send_reminder_column_and_mail_column_is_disappear() {
		log("verify email column will not display under send reminder message column on oppointments dashboard");
		assertTrue(apptPage.visibilitySendReminderTextColumn());
		assertFalse(apptPage.sendReminderEmailColumn());
	}

	@When("schedule an appointments")
	public void schedule_an_appointments() throws NullPointerException, IOException {
		PostAPIRequestMfAppointmentScheduler apptSched = PostAPIRequestMfAppointmentScheduler
				.getPostAPIRequestMfAppointmentScheduler();
		MfAppointmentSchedulerPayload payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		HeaderConfig headerConfig = HeaderConfig.getHeaderConfig();
		AccessToken accessToken = AccessToken.getAccessToken();
		log("schedule an appointments ");
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(5);
		log("Getting patients since timestamp: " + plus20Minutes);
		Response response = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()),
				propertyData.getProperty("mf.scheduler.patient.id"), propertyData.getProperty("mf.scheduler.appt.id"));
	}

	@And("in setting dashboard in notifications Enable Broadcast messaging checkbox")
	public void in_setting_dashboard_in_notifications_enable_broadcast_messaging_checkbox()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.enableBroadcastMessagingCheckbox();
		notifPage.saveNotification();
	}

	@Then("verify in appointment dashboard broadcast column for email and text should be visible by default count will be zero")
	public void verify_in_appointment_dashboard_broadcast_column_for_email_and_text_should_be_visible_by_default_count_will_be_zero() {
		mainPage.clickOnAppointmentsTab();
		apptPage.selectPatientWithNewAppt();
		assertEquals(apptPage.broadcastEmailTextCount(), "0");
		assertEquals(apptPage.broadcastTextsTextCount(), "0");
	}

	@Then("Verify on clicking icon for email and text pop up appears and patient name , time , status , message field is displayed")
	public void verify_on_clicking_icon_for_email_and_text_pop_up_appears_and_patient_name_time_status_message_field_is_displayed()
			throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		apptPage.clickOnBroadcastEmail();
		assertEquals(apptPage.broadcastLogsText(), "Broadcast logs");
		assertEquals(apptPage.getPatientName(), propertyData.getProperty("mf.scheduler.patient.name"));
		assertEquals(apptPage.getMessageText(), "Message");
		assertEquals(apptPage.getTimeText(), "Time");
		assertEquals(apptPage.getStatusText(), "Status");
		apptPage.closeBroadcastEmailandTextBox();
		apptPage.clickOnBroadcastText();
		assertEquals(apptPage.broadcastLogsText(), "Broadcast logs");
		assertEquals(apptPage.getPatientName(), propertyData.getProperty("mf.scheduler.patient.name"));
		assertEquals(apptPage.getMessageText(), "Message");
		assertEquals(apptPage.getTimeText(), "Time");
		assertEquals(apptPage.getStatusText(), "Status");
		apptPage.closeBroadcastEmailandTextBox();
	}

	@When("schedule a new appointment")
	public void schedule_a_new_appointment() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(5);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}

	@And("from setting dashboard in general enable email and text checkbox")
	public void from_setting_dashboard_in_general_enable_email_and_check_text_checkbox() {
		mainPage.clickOnSettingTab();
		log("verify user should be on General setting dashboard");
		GeneralPage generalPage = GeneralPage.getGeneralPage();
		log("verify General setting Text: " + propertyData.getProperty("general.setting.title"));
		assertTrue(generalPage.generalSettingTitle().contains(propertyData.getProperty("general.setting.title")));
		log("verify Mamage solutions Text: " + propertyData.getProperty("manage.solution.board"));
		assertTrue(generalPage.manageSolutionTab().contains(propertyData.getProperty("manage.solution.board")));
		generalPage.enableTextCheckbox();
		generalPage.enableEmailCheckbox();
	}

	@And("from notifications in setting dashboard Enable Broadcast messaging checkbox")
	public void from_notifications_in_setting_dashboard_Enable_Broadcast_messaging_checkbox()
			throws InterruptedException {
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.enableBroadcastMessagingCheckbox();
	}

	@Then("verify in appointment dashboard in broadcast column  email and text should be visible and by default count will be zero")
	public void verify_in_appointment_dashboard_in_broadcast_column_email_and_text_should_be_visible_and_by_default_count_will_be_zero() {
		mainPage.clickOnAppointmentsTab();
		log("verify in broadcast column email and text should be visible");
		assertTrue(apptPage.visibilityBroadcastMessageTextColumn());
		assertTrue(apptPage.broadcastMessageEmailColumn());
		log("verify broadcast email and text count by default zero");
		assertEquals(apptPage.broadcastEmailTextCount(), "0");
		assertEquals(apptPage.broadcastTextsTextCount(), "0");
	}

	@Then("verify in appointment dashboard in broadcast column only text column is visible and email column should be disappear")
	public void verify_in_appointment_dashboard_in_broadcast_column_only_text_column_is_visible_and_email_column_should_be_disappear() {
		mainPage.clickOnAppointmentsTab();
		log("verify in broadcast column text should be visible and text disappear");
		assertTrue(apptPage.visibilityBroadcastMessageTextColumn());
		assertTrue(apptPage.broadcastMessageEmailColumn());
		log("verify broadcast email and text count by default zero");
		assertEquals(apptPage.broadcastEmailTextCount(), "0");
		assertEquals(apptPage.broadcastTextsTextCount(), "0");
	}

	@When("from setting in notifications dashboard Enable Broadcast messaging checkbox")
	public void from_setting_in_notifications_dashboard_enable_broadcast_messaging_checkbox()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		log("verify user should be on General setting dashboard");
		GeneralPage generalPage = GeneralPage.getGeneralPage();
		log("verify General setting Text: " + propertyData.getProperty("general.setting.title"));
		assertTrue(generalPage.generalSettingTitle().contains(propertyData.getProperty("general.setting.title")));
		log("verify Mamage solutions Text: " + propertyData.getProperty("manage.solution.board"));
		assertTrue(generalPage.manageSolutionTab().contains(propertyData.getProperty("manage.solution.board")));
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.enableBroadcastMessagingCheckbox();
		notifPage.saveNotification();
	}

	@When("from setting in general text checkbox is enable and email checkbox is disable")
	public void from_setting_in_general_text_checkbox_is_enable_and_email_checkbox_is_disable()
			throws InterruptedException {
		generalPage.clickOnGeneralTab();
		log("Enable text checkbox and Disable email checkbox");
		generalPage.enableAndDisableEmailCheckbox();
		generalPage.clickOnUpdateSettingbutton();
		mainPage.clickOnAppointmentsTab();
	}

	@Then("verify on clicking on text broadcast icon , broadcast logs should appear with patient name , time ,message and status field")
	public void verify_on_clicking_on_text_broadcast_icon_broadcast_logs_should_appear_with_patient_name_time_message_and_status_field()
			throws InterruptedException {
		log("verify in broadcast column text should be visible and email disapper");
		assertTrue(apptPage.visibilityBroadcastTextColumnAfterEmailEnable());
		assertFalse(apptPage.visibilityBroadcasrEmailColoumn());
		log("verify in broadcast text logs fields");
		apptPage.clickOnBroadcastTextAfterEmailEnable();
		assertEquals(apptPage.broadcastLogsText(), "Broadcast logs");
		assertEquals(apptPage.getPatientName(), propertyData.getProperty("mf.scheduler.patient.name"));
		assertEquals(apptPage.getMessageText(), "Message");
		assertEquals(apptPage.getTimeText(), "Time");
		assertEquals(apptPage.getStatusText(), "Status");
		apptPage.closeBroadcastEmailandTextBox();
		log("Enable email checkbox");
		mainPage.clickOnSettingTab();
		generalPage.clickOnGeneralTab();
		generalPage.enableAndDisableEmailCheckbox();
		generalPage.clickOnUpdateSettingbutton();
	}

	@When("from setting in general text checkbox is disable and email checkbox is enable")
	public void from_setting_in_general_text_checkbox_is_disable_and_email_checkbox_is_enable()
			throws InterruptedException {
		generalPage.clickOnGeneralTab();
		log("Disable text checkbox and enable email checkbox");
		generalPage.clickOnTextCheckbox();
		generalPage.clickOnUpdateSettingbutton();
		mainPage.clickOnAppointmentsTab();
	}

	@When("select patient and send broadcast")
	public void select_patient_and_send_broadcast() throws Exception {
		apptPage.selectPatient(Appointment.patientId, Appointment.patientId);
		log("Click on Actions tab and select broadcast message button");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
	}

	@Then("verify on appointment dashboard in broadcast broadcast will be sent to patient on email only and broadcast text column entry will not be seen")
	public void verify_on_appointment_dashboard_in_broadcast_broadcast_will_be_sent_to_patient_on_email_only_and_broadcast_text_column_entry_will_not_be_seen()
			throws InterruptedException {
		log("verify in broadcast email column should be visible and text column disappear");
		assertFalse(apptPage.visibilityBroadcastTextColumnAfterTextDisable());
		assertTrue(apptPage.visibilityBroadcasrEmailColoumnAfterTextDisable());
		log("Disable text checkbox");
		mainPage.clickOnSettingTab();
		generalPage.clickOnGeneralTab();
		generalPage.enableAndDisableTextCheckbox();
		generalPage.clickOnUpdateSettingbutton();
	}

	@When("from setting in general disable notification setting")
	public void from_setting_in_general_disable_notification_setting() throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.offNotification();
		notifPage.saveNotification();
	}

	@Then("verify in appointment dashboard in broadcast column text and email column should not be display")
	public void verify_in_appointment_dashboard_in_broadcast_column_text_and_email_column_should_not_be_display()
			throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		log("verify on appointment dashboard email and text column should not be visible");
		assertFalse(apptPage.visibilityBroadcastMessageTextColumn());
		assertFalse(apptPage.broadcastMessageEmailColumn());
		log("Enable notifications");
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.onNotification();
		notifPage.saveNotification();
	}

	@When("select patient and from action dropdown click on remove")
	public void select_patient_and_from_action_dropdown_click_on_remove() throws Exception {
		apptPage.selectPatient(Appointment.patientId, Appointment.patientId);
		log("Click on Actions tab and select remove button");
		apptPage.clickOnActions();
		apptPage.selectRemoveButton();
	}

	@Then("Verify the pop up message is displayed as per requirement")
	public void verify_the_pop_up_message_is_displayed_as_per_requirement() {
		assertEquals(apptPage.removeButtonMessage(),
				"You are about to delete 1 appointments from the dashboard: You will not be able to communicate with patients regarding these appointments.");
		assertEquals(apptPage.removeButtonQues(), "Are you sure you want to proceed?");
		apptPage.clickOnCancel();
	}

	@When("schedule a new appointment and confirm")
	public void schedule_a_new_appointment_and_confirm() throws NullPointerException, IOException {
		log("Schedule a new Appointment");
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(5);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
	}

	@Then("verify after click on confirm banner message should be display and appointment should be deleted successfully")
	public void verify_after_click_on_confirm_banner_message_should_be_display_and_appointment_should_be_deleted_successfully()
			throws InterruptedException {
		log("verify the pop up message is displayed as per requirement");
		assertEquals(apptPage.removeButtonMessage(),
				"You are about to delete 1 appointments from the dashboard: You will not be able to communicate with patients regarding these appointments.");
		assertEquals(apptPage.removeButtonQues(), "Are you sure you want to proceed?");
		apptPage.clickOnConfirm();
		assertEquals(apptPage.broadcastMessageStatus(), "1 appointments have successfully been deleted.");
		assertFalse(apptPage.visibilityOfDeletedPatient(Appointment.patientId, Appointment.apptId));
	}

	@When("user login to practice provisioning")
	public void user_login_to_practice_provisioning() throws NullPointerException, InterruptedException {
		log("Practice provisining login details");
		log("Username : " + propertyData.getProperty("practice.provisining.username.athena"));
		log("Password : " + propertyData.getProperty("practice.provisining.password.athena"));
		loginPage.login(propertyData.getProperty("practice.provisining.username.athena"),
				propertyData.getProperty("practice.provisining.password.athena"));
		scrollAndWait(200, 300, 5000);
		log("Verify practice Name-- " + apptPage.getPracticeName());
		assertTrue(apptPage.getPracticeName().contains(propertyData.getProperty("practice.name.athena")));
		log("Verify focus on Appointments page-- " + apptPage.getApptPageTitle());
		assertTrue(apptPage.getApptPageTitle().contains(propertyData.getProperty("appointment.page")));
	}

	@When("schedule multiple new appointments and confirm")
	public void schedule_multiple_new_appointments_and_confirm()
			throws NullPointerException, IOException, InterruptedException {
		for (int i = 0; i < 25; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("practice.id.athena"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("practice.id.athena"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);
		}
		apptPage.clickOnRefreshTab();
	}

	@When("select multiple patients and from action dropdown click on remove")
	public void select_multiple_patients_and_from_action_dropdown_click_on_remove() throws InterruptedException {
		Thread.sleep(5000);
		apptPage.selectAllCheckboxes();
		log("Click on Actions tab and select remove button");
		apptPage.clickOnActions();
		apptPage.selectRemoveButton();
	}

	@Then("verify after deleting multiple appointments banner message should be display and appointment should be deleted successfully")
	public void verify_after_deleting_multiple_appointments_banner_message_should_be_display_and_appointment_should_be_deleted_successfully()
			throws InterruptedException {
		String removeButtonMessage = apptPage.removeButtonMessage();
		log("remove button message :" + removeButtonMessage);
		String getcount = removeButtonMessage.substring(24, 26);
		log("Total count on banner meassage :" + getcount);
		log("verify the pop up message is displayed as per requirement");
		assertEquals(apptPage.removeButtonMessage(), "You are about to delete " + getcount
				+ " appointments from the dashboard: You will not be able to communicate with patients regarding these appointments.");
		assertEquals(apptPage.removeButtonQues(), "Are you sure you want to proceed?");
		apptPage.clickOnConfirm();
		assertEquals(apptPage.broadcastMessageStatus(), getcount + " appointments have successfully been deleted.");
	}

	@When("schedule multiple appointments and confirm")
	public void schedule_multiple_appointments_and_confirm()
			throws NullPointerException, IOException, InterruptedException {
		for (int i = 0; i < 54; i++) {
			log("Schedule multiple new Appointments");
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			log("schedule more than 50 an appointments ");
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("practice.id.athena"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("practice.id.athena"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);
		}
		apptPage.clickOnRefreshTab();
	}

	@When("apply the filter such that count of appointment is greater than fifty count and more than one page is coming")
	public void apply_the_filter_such_that_count_of_appointment_is_greater_than_fifty_count_and_more_than_one_page_is_coming()
			throws InterruptedException {
		apptPage.enterStartTimeWithinMonth();
		Thread.sleep(10000);
		apptPage.clickOnPatientFilter();
	}

	@When("select all appointments from appointment dashboard and select banner message")
	public void select_all_appointments_from_appointment_dashboard_and_select_banner_message()
			throws InterruptedException {
		apptPage.selectAllCheckboxes();
		assertTrue(apptPage.clickOnBannerMessage());
	}

	@Then("verify remove button should be disabled and only broadcast button should be enabled")
	public void verify_remove_button_should_be_disabled_and_only_broadcast_button_should_be_enabled() {
		log("Click on Actions tab");
		apptPage.clickOnActions();
		assertFalse(apptPage.removeButton());
		assertFalse(apptPage.sendReminderButton());
		assertTrue(apptPage.broadcastMessageButton());
		assertFalse(apptPage.createButton());
	}

	@When("schedule multiple appointments")
	public void schedule_multiple_appointments() throws NullPointerException, IOException, InterruptedException {
		for (int i = 0; i < 100; i++) {
			log("Schedule multiple new Appointments");
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			log("schedule more than 50 an appointments ");
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("practice.id.athena"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		apptPage.clickOnRefreshTab();
	}

	@When("from appointment dashboard select all appointments and remove from action dropdown")
	public void from_appointment_dashboard_select_all_appointments_and_remove_from_action_dropdown()
			throws InterruptedException {
		Appointment.pageNo = apptPage.getPageNo();
		log("get page no." + Appointment.pageNo);
		apptPage.scrollUp();
		apptPage.selectAllCheckboxes();
		log("Click on Actions tab and select remove button");
		apptPage.clickOnActions();
		apptPage.selectRemoveButton();
		log("verify the pop up message is displayed as per requirement");
		String removeButtonMessage = apptPage.removeButtonMessage();
		log("remove button message :" + removeButtonMessage);
		String getcount = removeButtonMessage.substring(24, 26);
		log("Total count on banner meassage :" + getcount);
		assertEquals(apptPage.removeButtonMessage(), "You are about to delete " + getcount
				+ " appointments from the dashboard: You will not be able to communicate with patients regarding these appointments.");
		assertEquals(apptPage.removeButtonQues(), "Are you sure you want to proceed?");
		apptPage.clickOnConfirm();
		assertEquals(apptPage.broadcastMessageStatus(), getcount + " appointments have successfully been deleted.");
	}

	@Then("verify after clicking on refresh button page count should be count should be same")
	public void verify_after_clicking_on_refresh_button_page_count_should_be_count_should_be_same()
			throws InterruptedException {
		apptPage.clickOnRefreshTab();
		String pageCount = apptPage.getPageNo();
		assertEquals(Appointment.pageNo, pageCount, "page count was wrong");
		log("Page count should be count should be same");
	}

	@Then("verify if notification are off then only create appointment and remove button are visible.")
	public void verify_if_notification_are_off_then_only_create_appointment_and_remove_button_are_visible()
			throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		log("verify on appointment dashboard create and remobe button are visible in action dropdown");
		apptPage.clickOnActions();
		assertTrue(apptPage.visibilityOfRemoveButton());
		assertTrue(apptPage.visibilityOfCreateButton());
		log("Enable notifications setting");
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.onNotification();
		notifPage.saveNotification();
	}

	@When("from appointment dashboard click on action button and click on create option for schedule new appointment")
	public void from_appointment_dashboard_click_on_action_button_and_click_on_create_option_for_schedule_new_appointment()
			throws InterruptedException {
		log("schedule new appointment from practice provisining ui");
		mainPage.clickOnAppointmentsTab();
		apptPage.clickOnActions();
		apptPage.clickOnCreate();
		assertEquals(apptPage.createNewPatientText(), "Create a new appointment");
		apptPage.createNewPatient(propertyData.getProperty("precheck.location"),
				propertyData.getProperty("precheck.appt.type"), propertyData.getProperty("precheck.patient.id"),
				propertyData.getProperty("precheck.first.name"), propertyData.getProperty("precheck.middle.name"),
				propertyData.getProperty("precheck.last.name"), propertyData.getProperty("precheck.dob"),
				propertyData.getProperty("precheck.phone"), propertyData.getProperty("precheck.email"),
				propertyData.getProperty("precheck.address.line1"), propertyData.getProperty("precheck.patient.city"),
				propertyData.getProperty("precheck.patient.state"), propertyData.getProperty("precheck.patient.zip"),
				propertyData.getProperty("precheck.provider.name"), propertyData.getProperty("precheck.copay"),
				propertyData.getProperty("precheck.balance"),
				propertyData.getProperty("precheck.primary.insurance.name"),
				propertyData.getProperty("precheck.primary.insurance.group.No"),
				propertyData.getProperty("precheck.primary.insurance.member.id"));

	}

	@Then("verify appointment get succesfully created and from remove button suceesfully deleted")
	public void verify_appointment_get_succesfully_created_and_from_remove_button_suceesfully_deleted()
			throws InterruptedException {
		log("Searching patient by patient id");
		apptPage.filterPatientId(propertyData.getProperty("precheck.patient.id"));
		apptPage.selectApptPrecheckPatient();
		log("Verify succesfully created patient details");
		assertEquals(apptPage.precheckPatientName(), propertyData.getProperty("appt.precheck.patient.name"));
		assertEquals(apptPage.precheckPatientEmail(), propertyData.getProperty("precheck.email"));
		assertEquals(apptPage.precheckPatientZipcode(), propertyData.getProperty("precheck.patient.zip"));
		assertEquals(apptPage.insuranceName(), propertyData.getProperty("precheck.primary.insurance.name"));
		apptPage.closeApptDetail();
		log("Remove succesfully created patient");
		apptPage.selectCreatedPatient();
		log("Click on Actions tab and select remove button");
		apptPage.clickOnActions();
		apptPage.selectRemoveButton();
		log("verify the pop up message is displayed as per requirement");
		String removeButtonMessage = apptPage.removeButtonMessage();
		log("remove button message :" + removeButtonMessage);
		String getcount = removeButtonMessage.substring(24, 26);
		log("Total count on banner meassage :" + getcount);
		apptPage.clickOnConfirm();
		assertEquals(apptPage.broadcastMessageStatus(), getcount + "appointments have successfully been deleted.");
		log("Enable notifications setting");
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		notifPage.onNotification();
		notifPage.saveNotification();
	}

	@When("confirm appointment")
	public void confirm_appointment() throws NullPointerException, IOException {
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
	}

	@Then("verify after refresh appointment should get confirmed and green tick should be appeared in apt dashbord")
	public void verify_after_refresh_appointment_should_get_confirmed_and_green_tick_should_be_appeared_in_apt_dashbord()
			throws InterruptedException {
		apptPage.clickOnRefreshTab();
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		log("Verify confirmed tick should be display");
		assertTrue(apptPage.displayComfirmTickMark());
	}

	@When("curbside checkin and curbside arrival done")
	public void curbside_checkin_and_curbside_arrival_done() throws NullPointerException, IOException {
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}

	@Then("verify after refresh appointment should get curbside checkin done and black check in symbol should appeared")
	public void verify_after_refresh_appointment_should_get_curbside_checkin_done_and_black_check_in_symbol_should_appeared()
			throws InterruptedException {
		apptPage.clickOnRefreshTab();
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		log("Verify curbside checkin done and black check should be display");
		assertTrue(apptPage.displaycurbsideArrivalMark());
	}

	@When("check in done")
	public void check_in_done() throws NullPointerException, IOException {
		Response checkInResponse = aptPrecheckPost.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				aptPrecheckPayload.getCheckinActionsPayload(Appointment.patientId, Appointment.apptId,
						propertyData.getProperty("apt.precheck.practice.id")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()));
		assertEquals(checkInResponse.getStatusCode(), 200);
	}

	@Then("verify after refresh appointment should get check in done and green check in symbol should appeared")
	public void verify_after_refresh_appointment_should_get_check_in_done_and_green_check_in_symbol_should_appeared()
			throws InterruptedException {
		apptPage.clickOnRefreshTab();
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		log("Verify checkin done and green check in symbol should be display");
		assertTrue(apptPage.displayCheckInMark());
	}

	@When("select patient and send manual reminder to patient")
	public void select_patient_and_send_manual_reminder_to_patient() throws Exception {
		apptPage.clickOnRefreshTab();
		log("Select patient");
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		Thread.sleep(5000);
		log("send manual reminder to patient");
		apptPage.clickOnActions();
		apptPage.clickOnSendReminder();
	}

	@Then("verify after refresh appointment manual reminder paper plane symbol should appear after sending reminder")
	public void verify_after_refresh_appointment_manual_reminder_paper_plane_symbol_should_appear_after_sending_reminder()
			throws InterruptedException {
		apptPage.clickOnRefreshTab();
		log("Verify send reminder message and tick mark");
		assertTrue(apptPage.displaySendReminderMessageTickMark());
		assertEquals(apptPage.getReminderMessage(), "Reminder(s) will be sent for 1 appointment(s)");
		assertTrue(apptPage.displayPaperPlaneSymbol());
	}

	@When("get broadcast count before broadcast send")
	public void get_broadcast_count_before_broadcast_send() throws InterruptedException {
		apptPage.clickOnRefreshTab();
		log("Broadcast Email count before broadcast send--- "
				+ apptPage.getBroadcastEmailCountForSelectedPatient(Appointment.patientId));
		log("Broadcast Text count before broadcast send--- "
				+ apptPage.getBroadcastTextCountForSelectedPatient(Appointment.patientId));
		assertEquals(apptPage.getBroadcastEmailCountForSelectedPatient(Appointment.patientId), "0");
		assertEquals(apptPage.getBroadcastTextCountForSelectedPatient(Appointment.patientId), "0");
	}

	@Then("verify after refresh appointment broadcast count should get updated after sending broadcast")
	public void verify_after_refresh_appointment_broadcast_count_should_get_updated_after_sending_broadcast()
			throws InterruptedException {
		apptPage.clickOnRefreshTab();
		Thread.sleep(200000);
		log("Broadcast message get updated to--- "
				+ apptPage.getBroadcastEmailCountForSelectedPatient(Appointment.patientId));
		log("Broadcast message get updated to--- "
				+ apptPage.getBroadcastTextCountForSelectedPatient(Appointment.patientId));
		assertEquals(apptPage.getBroadcastEmailCountForSelectedPatient(Appointment.patientId), "1");
		assertEquals(apptPage.getBroadcastTextCountForSelectedPatient(Appointment.patientId), "1");
	}

	@When("schedule multiple appointments and select patients")
	public void schedule_multiple_appointments_and_select_patients() throws Exception {
		for (int i = 0; i < 10; i++) {
			log("Schedule multiple new Appointments");
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			log("schedule more than 10 an appointments ");
			Response resonse = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("mf.apt.scheduler.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(resonse.statusCode(), 200);
			apptPage.clickOnRefreshTab();
			apptPage.selectPatients(Appointment.patientId, Appointment.apptId);
		}
	}

	@When("click on actions button and get broadcast message and send reminder count")
	public void click_on_actions_button_and_get_broadcast_message_and_send_reminder_count() {
		apptPage.clickOnActions();
		String broadcastMessageCount = apptPage.getbroadcastMessageText();
		String getBroadcastCount = broadcastMessageCount.substring(19, 21);
		Appointment.broadcastCount = Integer.parseInt(getBroadcastCount);
		if (Appointment.broadcastCount > 0) {
			assertTrue(Appointment.broadcastCount > 0);
			log("Total broadcast message count :" + Appointment.broadcastCount);
		} else {
			assertFalse(Appointment.broadcastCount > 0);
			log("No total count for broadcast message ");
		}

		String sendReminderCount = apptPage.getSendReminderText();
		String getReminderCount = sendReminderCount.substring(15, 17);
		Appointment.sendReminderCount = Integer.parseInt(getReminderCount);
		log("Total count on send reminder :" + Appointment.sendReminderCount);
		if (Appointment.sendReminderCount > 0) {
			assertTrue(Appointment.sendReminderCount > 0);
			log("Total send reminder count :" + Appointment.sendReminderCount);
		} else {
			assertFalse(Appointment.sendReminderCount > 0);
			log("No total count for send reminder");
		}
	}

	@Then("verify after click on refresh button count should be same for broadcast message and send reminder button")
	public void verify_after_click_on_refresh_button_count_should_be_same_for_broadcast_message_and_send_reminder_button()
			throws InterruptedException {
		apptPage.clickOnRefreshTab();
		apptPage.clickOnActions();
		String broadcastMessageCount = apptPage.getbroadcastMessageText();
		String getBroadcastCount = broadcastMessageCount.substring(19, 21);
		int broadcastCount = Integer.parseInt(getBroadcastCount);
		log("Get current broadcast message count:-  " + broadcastCount);
		String sendReminderCount = apptPage.getSendReminderText();
		String getReminderCount = sendReminderCount.substring(15, 17);
		int reminderCount = Integer.parseInt(getReminderCount);
		log("Get current send reminder count:-  " + reminderCount);
		assertEquals(Appointment.broadcastCount, broadcastCount, "Broadcast message count was not same");
		assertEquals(Appointment.sendReminderCount, reminderCount, "Send reminder count was not same");
	}

	@When("select all patients and click on banner")
	public void select_all_patients_and_click_on_banner() throws InterruptedException {
		Thread.sleep(10000);
		mainPage.switchOnAppointmentsTab();
		apptPage.selectAllCheckboxes();
		apptPage.clickOnBannerMessage();
		Appointment.bannerMessage = apptPage.getSelectedBannerMessage();
		log("Get banner message-:" + Appointment.bannerMessage);
	}

	@When("click on actions button and only broadcast count should be seen")
	public void click_on_actions_button_and_only_broadcast_count_should_be_seen() {
		apptPage.clickOnActions();
		Appointment.broadcastMessageButtonText = apptPage.getBroadcastMessageButtonText();
		log("Broadcast message button text is:- " + Appointment.broadcastMessageButtonText);
		assertFalse(apptPage.removeButton());
		assertFalse(apptPage.sendReminderButton());
		assertTrue(apptPage.broadcastMessageButton());
		assertFalse(apptPage.createButton());
	}

	@When("going to second page and redirecting to page one")
	public void going_to_second_page_and_redirecting_to_page_one() throws InterruptedException {
		scrollAndWait(0, 3000, 5000);
		log("switch on second page");
		assertEquals(apptPage.jumpToNextPage(), "2");
		log("Redirecting to page one");
		assertEquals(apptPage.jumpToPreviousPage(), "1");
	}

	@Then("verify system should show the selected banner and appointment count should be same on brodcast button by clicking action button")
	public void verify_system_should_show_the_selected_banner_and_appointment_count_should_be_same_on_brodcast_button_by_clicking_action_button()
			throws InterruptedException {
		log("Verify banner message");
		scrollAndWait(0, -3000, 10000);
		assertEquals(Appointment.bannerMessage, apptPage.getSelectedBannerMessage(), "Banner message was not same");
		apptPage.clickOnActions();
		String broadcastMessageButtonCount = apptPage.getBroadcastMessageButtonText();
		log("Broadcast message button text is:- " + broadcastMessageButtonCount);
		log("Verify broadcast message button count");
		assertEquals(Appointment.broadcastMessageButtonText, broadcastMessageButtonCount,
				"Broadcast message button count was not same");
	}

	@When("select all patients and going to second page")
	public void select_all_patients_and_going_to_second_page() throws InterruptedException {
		mainPage.switchOnAppointmentsTab();
		apptPage.selectAllCheckboxes();
		scrollAndWait(0, 2000, 5000);
		log("switch on second page");
		assertEquals(apptPage.jumpToNextPage(), "2");
	}

	@Then("verify after clicking on action only create button should be enabled")
	public void verify_after_clicking_on_action_only_create_button_should_be_enabled() throws InterruptedException {
		log("Click on actions and verify only create button should be enabled");
		scrollAndWait(0, -2000, 5000);
		apptPage.clickOnActions();
		assertFalse(apptPage.removeButton());
		assertFalse(apptPage.sendReminderButton());
		assertFalse(apptPage.broadcastMessageButton());
		assertTrue(apptPage.createButton());
	}

	@When("click on action and get count for broadcast, send reminder, remove button")
	public void click_on_action_and_get_count_for_broadcast_send_reminder_remove_button() {
		apptPage.clickOnActions();
		log("get count messages from broadcast, send reminder, remove button");
		Appointment.sendReminderButtonCount = apptPage.getSendReminderButtonText();
		log("count messages from Send reminder button:-  " + Appointment.sendReminderButtonCount);
		Appointment.broadcastMessageCount = apptPage.getBroadcastMessageButtonText();
		log("count messages from broadcast Message button:-  " + Appointment.broadcastMessageCount);
		Appointment.removeButtonCount = apptPage.getRemoveButtonText();
		log("count messages from Remove button:-  " + Appointment.removeButtonCount);
	}

	@Then("verify when select all records count get updated for broadcast,send reminder,remove button in action button")
	public void verify_when_select_all_records_count_get_updated_for_broadcast_send_reminder_remove_button_in_action_button()
			throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(10000);
		apptPage.selectAllCheckboxes();
		apptPage.clickOnActions();
		log("get count messages from broadcast, send reminder, remove button");
		String sendReminderButtonCount = apptPage.getSendReminderButtonText();
		log("count messages from Send reminder button:-  " + sendReminderButtonCount);
		String broadcastMessageCount = apptPage.getBroadcastMessageButtonText();
		log("count messages from broadcast Message button:-  " + broadcastMessageCount);
		String removeButtonCount = apptPage.getRemoveButtonText();
		log("count messages from Remove button:-  " + removeButtonCount);
		assertNotEquals(Appointment.sendReminderButtonCount, sendReminderButtonCount,
				"Send reminder button message was same");
		assertNotEquals(Appointment.broadcastMessageCount, broadcastMessageCount, "Broadcast button message was same");
		assertNotEquals(Appointment.removeButtonCount, removeButtonCount, "Remove button message was not same");
	}

	@When("select all patients and later deselect")
	public void select_all_patients_and_later_deselect() throws InterruptedException {
		log("Select all patients");
		apptPage.selectAllCheckboxes();
		log("Deselect all patients");
		apptPage.selectAllCheckboxes();
	}

	@When("going to second page and coming back to page one")
	public void going_to_second_page_and_coming_back_to_page_one() throws InterruptedException {
		scrollAndWait(0, 2000, 5000);
		log("switch on second page");
		assertEquals(apptPage.jumpToNextPage(), "2");
		log("Redirecting to page one");
		assertEquals(apptPage.jumpToPreviousPage(), "1");
	}

	@Then("Verify system does not show selected records and in action button only create button should be enabled")
	public void verify_system_does_not_show_selected_records_and_in_action_button_only_create_button_should_be_enabled()
			throws InterruptedException {
		scrollAndWait(0, -2000, 5000);
		assertFalse(apptPage.allCheckboxes());
		log("Click on actions and verify only create button should be enabled");
		apptPage.clickOnActions();
		assertFalse(apptPage.removeButton());
		assertFalse(apptPage.sendReminderButton());
		assertFalse(apptPage.broadcastMessageButton());
		assertTrue(apptPage.createButton());
	}

	@When("from setting in notifications user click on text edit section of appointment reminders")
	public void from_setting_in_notifications_user_click_on_text_edit_section_of_appointment_reminders()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		log("From Appointment reminders open edit section for text");
		scrollAndWait(0, 2000, 5000);
		notifPage.clickApptReminderSmsHamburgerButton();
		notifPage.clickApptReminderSmsEditButton();
	}

	@When("verify user is not able to see preview and build section on template editor page")
	public void verify_user_is_not_able_to_see_preview_and_build_section_on_template_editor_page()
			throws InterruptedException {
		notifPage.clickOnPreview();
		notifPage.clickOnBuild();
		assertFalse(notifPage.previewTabInEditButton());
		assertFalse(notifPage.buildTabInEditButton());
		notifPage.clickOnBackArrow();
	}

	@When("from setting in notifications user click on email edit section of appointment reminders")
	public void from_setting_in_notifications_user_click_on_email_edit_section_of_appointment_reminders()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		log("From Appointment reminders open edit section for text");
		scrollAndWait(0, 2000, 5000);
		notifPage.clickApptReminderEmailHamburgerButton();
		notifPage.clickApptReminderEmailEditButton();
	}

	@Then("verify user not able to enter zero in timing unit section for sms in appointment reminders")
	public void verify_user_not_able_to_enter_zero_in_timing_unit_section_for_sms_in_appointment_reminders()
			throws InterruptedException {
		log("Verify invalid timing unit for sms for one day");
		notifPage.enterInvalidTimingForSmsInOneDay();
		assertEquals(notifPage.getInvalidTimingTextForSmsInOneDay(), "Invalid Units", "Text was not match");
		assertEquals(notifPage.getColorForInvalidTimingForSmsInOneDay(), "#c72828",
				"Invalid Units text color was not match");
		log("Verify invalid timing unit for sms for three day");
		notifPage.enterInvalidTimingForSmsInThreeDay();
		assertEquals(notifPage.getInvalidTimingTextForSmsInThreeDay(), "Invalid Units", "Text was not match");
		assertEquals(notifPage.getColorForInvalidTimingForSmsInThreeDay(), "#c72828",
				"Invalid Units text color was not match");
		log("Verify invalid timing unit for sms for minutes");
		notifPage.enterInvalidTimingForSmsInminutes();
		assertEquals(notifPage.getInvalidTimingTextForSmsInMinutes(), "Invalid Units", "Text was not match");
		assertEquals(notifPage.getColorForInvalidTimingForSmsInMinutes(), "#c72828",
				"Invalid Units text color was not match");
		log("Verify invalid timing unit for sms for hours");
		notifPage.enterInvalidTimingForSmsInHours();
		assertEquals(notifPage.getInvalidTimingTextForSmsInHours(), "Invalid Units", "Text was not match");
		assertEquals(notifPage.getColorForInvalidTimingForSmsInHours(), "#c72828",
				"Invalid Units text color was not match");
		notifPage.clickOnBackArrow();
	}

	@Then("verify user not able to enter zero in timing unit section for email in appointment reminders")
	public void verify_user_not_able_to_enter_zero_in_timing_unit_section_for_email_in_appointment_reminders()
			throws InterruptedException {
		log("Verify invalid timing for email for hours");
		notifPage.enterInvalidTimingForEmailInHours();
		assertEquals(notifPage.getInvalidTimingTextForEmailInHours(), "Invalid Units", "Text was not match");
		assertEquals(notifPage.getColorForInvalidTimingForEmailInHours(), "#c72828",
				"Invalid Units text color was not match");
		log("Verify invalid timing for email for minutes");
		notifPage.enterInvalidTimingForEmailInMinutes();
		assertEquals(notifPage.getInvalidTimingTextForEmailInMinutes(), "Invalid Units", "Text was not match");
		assertEquals(notifPage.getColorForInvalidTimingForEmailInMinutes(), "#c72828",
				"Invalid Units text color was not match");
		log("Verify invalid timing for email for days");
		notifPage.enterInvalidTimingForEmailInDays();
		assertEquals(notifPage.getInvalidTimingTextForEmailInDays(), "Invalid Units", "Text was not match");
		assertEquals(notifPage.getColorForInvalidTimingForEmailInDays(), "#c72828",
				"Invalid Units text color was not match");
		notifPage.clickOnBackArrow();
	}

	@When("from setting in notifications user click on email hamburgerButton section")
	public void from_setting_in_notifications_user_click_on_email_hamburger_button_section()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		log("From Appointment reminders open edit section for text");
		scrollAndWait(0, 400, 5000);
		notifPage.clickOnApptConfirmationsEmailhumburgerButton();
	}

	@And("user click preview of appointment confirmation")
	public void user_click_preview_of_appointment_confirmation() {
		notifPage.clickOnPreviewButton();
	}

	@Then("verify in settings section all fields are display related to email templates")
	public void verify_in_settings_section_all_fields_are_display_related_to_email_templates() {
		assertEquals(notifPage.visibilityOfPreviewPageTitle(), "Preview", "Preview text was not match");
		assertEquals(notifPage.visibilityOfViewingText(), "Viewing in:", "Viewing in text was not match");
		assertEquals(notifPage.visibilityOfEnglishLang(), "English", "English text was not match");
		assertEquals(notifPage.visibilityOfCloseButton(), "Close", "Text was not match");
		assertEquals(notifPage.visibilityOfSettingText(), "Settings", "Settings text was not match");
		assertEquals(notifPage.visibilityOfNotiTypeText(), "Notification Type:",
				"Notification Type text was not match");
		assertEquals(notifPage.visibilityOfApptConfigText(), "Appointment Confirmation",
				" Appointment Confirmation text was not match");
		assertEquals(notifPage.visibilityOfVersionText(), "Version:", "Version text was not match");
		assertEquals(notifPage.visibilityOfApptMethodText(), "Appointment Method:",
				"Appointment Method text was not match");
		assertEquals(notifPage.visibilityOfInOfficeText(), "In Office", "In Office text was not match");
		assertEquals(notifPage.visibilityOfDeliveryMethodText(), "Delivery Method:",
				"Delivery Method text was not match");
		assertEquals(notifPage.visibilityOfEmailText(), "Email", "Email text was not match");
		assertEquals(notifPage.visibilityOfTimingText(), "Timing:", "Timing text was not match");
		assertEquals(notifPage.visibilityOfUponSchedulingText(), "Upon Scheduling",
				"Upon Scheduling text was not match");
		assertEquals(notifPage.visibilityOfResourcesText(), "Resource(s):", "Text was not match");
		assertEquals(notifPage.visibilityOfAllResourcesText(), "All", "All text was not match");
		assertEquals(notifPage.visibilityOfApptTypesText(), "Appointment Type(s):",
				"Appointment Type(s): text was not match");
		assertEquals(notifPage.visibilityOfAllApptTypesText(), "All", "All text was not match");
		assertEquals(notifPage.visibilityOfLocationText(), "Location(s):", "Location text was not match");
		assertEquals(notifPage.visibilityOfAllLocationText(), "All", "all text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify in desktop view all fields are display related to templates")
	public void verify_in_desktop_view_all_fields_are_display_related_to_templates() throws InterruptedException {
		notifPage.openDesktopViewPage();
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.visibilityOfClickHereText(), "CLICK HERETO VIEW THIS IN A BROWSER WINDOW",
				"click here text was not match");
		assertTrue(notifPage.visibilityOfCadenceImage());
		assertEquals(notifPage.visibilityOfApptScheduledText(), "Appointment Scheduled", "all text was not match");
		assertEquals(notifPage.visibilityOfPatientNameText(), "[Patient Name], your appointment has been scheduled",
				"Appt Scheduled text was not match");
		assertEquals(notifPage.visibilityOfDateAndTimeText(), "Date and Time", "Date And Time text was not match");
		assertEquals(notifPage.visibilityOfDayOfTheWeekText(), "Day of the week at 00:00 AM/PM",
				"Day Of The Week text was not match");
		assertEquals(notifPage.visibilityOfDateFormatText(), "Month DD, YYYY", "Date Format text was not match");
		assertEquals(notifPage.visibilityOfLocationTextInPreview(), "Location", "Location text was not match");
		assertEquals(notifPage.visibilityOfLocationNameText(), "Location Name", "Location Name text was not match");
		assertEquals(notifPage.visibilityOfAddress1Text(), "Location Address1", "Address1 text was not match");
		assertEquals(notifPage.visibilityOfAddress2Text(), "Location Address2", "Address2 text was not match");
		assertEquals(notifPage.visibilityOfCityStateZipText(), "City State, Zip", "City State Zip text was not match");
		assertEquals(notifPage.visibilityOfMobileNoFormatText(), "(XXX) XXX-XXXX", "MobileNoFormat text was not match");
		assertEquals(notifPage.visibilityOfPinToMapText(), "Pin on Map", "Pin To Map text was not match");
		assertEquals(notifPage.visibilityOfProviderText(), "Provider", "Provider text was not match");
		assertEquals(notifPage.visibilityOfProviderNameText(), "Provider Name", "Provider Name text was not match");
		assertEquals(notifPage.visibilityOfRescheduleOrCancelText(), "Reschedule or Cancel",
				"Reschedule Or Cancel text was not match");
		assertEquals(notifPage.visibilityOfInstructionsText(),
				"Please do not reply to this auto generated message. If you have questions about this email, please contact your doctor’s office.",
				"Instructions text was not match");
		assertEquals(notifPage.visibilityOfConfidentialityNoticeText(), "Confidentiality Notice:",
				"Confidentiality Notice text was not match");
		assertEquals(notifPage.visibilityOfUnsubscribeText(), "Unsubscribe", "Unsubscribe text was not match");
		assertEquals(notifPage.visibilityOfBottomInfoText(),
				"*Information included in this communication is based on the data sent from the practice management system.",
				"Bottom Info text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify in mobile view all fields are display related to templates")
	public void verify_in_mobile_view_all_fields_are_display_related_to_templates() throws InterruptedException {
		notifPage.openMobileViewPage();
		scrollAndWait(0, 500, 5000);
		log("Click here Text :- " + notifPage.visibilityOfClickHereText());
		assertTrue(notifPage.visibilityOfCadenceImage());
		assertEquals(notifPage.visibilityOfApptScheduledText(), "Appointment Scheduled", "all text was not match");
		log("Patient name text :- " + notifPage.visibilityOfPatientNameText());
		assertEquals(notifPage.visibilityOfDateAndTimeText(), "Date and Time", "Date And Time text was not match");
		assertEquals(notifPage.visibilityOfDayOfTheWeekText(), "Day of the week at 00:00 AM/PM",
				"Day Of The Week text was not match");
		assertEquals(notifPage.visibilityOfDateFormatText(), "Month DD, YYYY", "Date Format text was not match");
		assertEquals(notifPage.visibilityOfLocationTextInPreview(), "Location", "Location text was not match");
		assertEquals(notifPage.visibilityOfLocationNameText(), "Location Name", "Location Name text was not match");
		assertEquals(notifPage.visibilityOfAddress1Text(), "Location Address1", "Address1 text was not match");
		assertEquals(notifPage.visibilityOfAddress2Text(), "Location Address2", "Address2 text was not match");
		assertEquals(notifPage.visibilityOfCityStateZipText(), "City State, Zip", "City State Zip text was not match");
		assertEquals(notifPage.visibilityOfMobileNoFormatText(), "(XXX) XXX-XXXX", "MobileNoFormat text was not match");
		assertEquals(notifPage.visibilityOfPinToMapText(), "Pin on Map", "Pin To Map text was not match");
		assertEquals(notifPage.visibilityOfProviderText(), "Provider", "Provider text was not match");
		assertEquals(notifPage.visibilityOfProviderNameText(), "Provider Name", "Provider Name text was not match");
		assertEquals(notifPage.visibilityOfRescheduleOrCancelText(), "Reschedule or Cancel",
				"Reschedule Or Cancel text was not match");
		assertEquals(notifPage.visibilityOfInstructionsText(),
				"Please do not reply to this auto generated message. If you have questions about this email, please contact your doctor’s office.",
				"Instructions text was not match");
		assertEquals(notifPage.visibilityOfConfidentialityNoticeText(), "Confidentiality Notice:",
				"Confidentiality Notice text was not match");
		assertEquals(notifPage.visibilityOfUnsubscribeText(), "Unsubscribe", "Unsubscribe text was not match");
		assertEquals(notifPage.visibilityOfBottomInfoText(),
				"*Information included in this communication is based on the data sent from the practice management system.",
				"Bottom Info text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@When("from setting in notifications user click on text hamburgerButton section")
	public void from_setting_in_notifications_user_click_on_text_hamburger_button_section()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		log("From Appointment reminders open edit section for text");
		scrollAndWait(0, 400, 5000);
		notifPage.clickOnApptConfirmationsTextshumburgerButton();
	}

	@Then("verify in settings section all fields are display related to templates")
	public void verify_in_settings_section_all_fields_are_display_related_to_templates() {
		assertEquals(notifPage.visibilityOfPreviewPageTitle(), "Preview", "Preview text was not match");
		assertEquals(notifPage.visibilityOfViewingText(), "Viewing in:", "Viewing in text was not match");
		assertEquals(notifPage.visibilityOfEnglishLang(), "English", "English text was not match");
		assertEquals(notifPage.visibilityOfCloseButton(), "Close", "Text was not match");
		assertEquals(notifPage.visibilityOfSettingText(), "Settings", "Settings text was not match");
		assertEquals(notifPage.visibilityOfNotiTypeText(), "Notification Type:",
				"Notification Type text was not match");
		assertEquals(notifPage.visibilityOfApptConfigText(), "Appointment Confirmation",
				" Appointment Confirmation text was not match");
		assertEquals(notifPage.visibilityOfVersionText(), "Version:", "Version text was not match");
		assertEquals(notifPage.visibilityOfApptMethodText(), "Appointment Method:",
				"Appointment Method text was not match");
		assertEquals(notifPage.visibilityOfInOfficeText(), "In Office", "In Office text was not match");
		assertEquals(notifPage.visibilityOfDeliveryMethodText(), "Delivery Method:",
				"Delivery Method text was not match");
		assertEquals(notifPage.visibilityOfSmsText(), "SMS", "Email text was not match");
		assertEquals(notifPage.visibilityOfTimingText(), "Timing:", "Timing text was not match");
		assertEquals(notifPage.visibilityOfUponSchedulingText(), "Upon Scheduling",
				"Upon Scheduling text was not match");
		assertEquals(notifPage.visibilityOfResourcesText(), "Resource(s):", "Text was not match");
		assertEquals(notifPage.visibilityOfAllResourcesText(), "All", "All text was not match");
		assertEquals(notifPage.visibilityOfApptTypesText(), "Appointment Type(s):",
				"Appointment Type(s): text was not match");
		assertEquals(notifPage.visibilityOfAllApptTypesText(), "All", "All text was not match");
		assertEquals(notifPage.visibilityOfLocationText(), "Location(s):", "Location text was not match");
		assertEquals(notifPage.visibilityOfAllLocationText(), "All", "all text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify in setting section view all fields are display related to text templates")
	public void verify_in_setting_section_view_all_fields_are_display_related_to_text_templates() {
		assertTrue(notifPage.visibilityOfRescheduleAndCancelAppointmentText());
		assertTrue(notifPage.visibilityOfGetDirectionText());
		assertTrue(notifPage.visibilityOfTextStopToUnsubscribeText());
		assertEquals(notifPage.visibilityOfPatientInfoInTextMsg(),
				"[Patient Name], your appointment with [Practice Name] (XXX) XXX-XXXX on [Day of the week], [Month] [DD] [YYYY] [00:00 AM/PM]: is scheduled.",
				"Patient info text was not match");
		assertTrue(notifPage.visibilityOfSmsPreviewBottomtext());
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@When("user login to new practice")
	public void user_login_to_new_practice() throws NullPointerException, InterruptedException {
		log("Practice provisining login details");
		log("Username : " + propertyData.getProperty("new.practice.username.ge"));
		log("Password : " + propertyData.getProperty("new.practice.password.ge"));
		loginPage.login(propertyData.getProperty("new.practice.username.ge"),
				propertyData.getProperty("new.practice.password.ge"));
		scrollAndWait(200, 300, 10000);
		log("Verify practice Name-- " + apptPage.getPracticeName());
		assertTrue(apptPage.getPracticeName().contains(propertyData.getProperty("new.practice.name.ge")));
		log("Verify focus on Appointments page-- " + apptPage.getApptPageTitle());
		assertTrue(apptPage.getApptPageTitle().contains(propertyData.getProperty("appointment.page")));
	}

	@Then("verify in mobile view all fields are display related to templates for new practice")
	public void verify_in_mobile_view_all_fields_are_display_related_to_templates_for_new_practice()
			throws InterruptedException {
		notifPage.openMobileViewPage();
		scrollAndWait(0, 500, 5000);
		log("Click here Text :- " + notifPage.visibilityOfClickHereText());
		assertEquals(notifPage.visibilityOfNewPracticeLogoText(), "Your Logo Here", "Logo text was not match");
		assertEquals(notifPage.visibilityOfApptScheduledText(), "Appointment Scheduled", "all text was not match");
		log("Patient name text :- " + notifPage.visibilityOfPatientNameText());
		assertEquals(notifPage.visibilityOfDateAndTimeText(), "Date and Time", "Date And Time text was not match");
		assertEquals(notifPage.visibilityOfDayOfTheWeekText(), "Day of the week at 00:00 AM/PM",
				"Day Of The Week text was not match");
		assertEquals(notifPage.visibilityOfDateFormatText(), "Month DD, YYYY", "Date Format text was not match");
		assertEquals(notifPage.visibilityOfLocationTextInPreview(), "Location", "Location text was not match");
		assertEquals(notifPage.visibilityOfLocationNameText(), "Location Name", "Location Name text was not match");
		assertEquals(notifPage.visibilityOfAddress1Text(), "Location Address1", "Address1 text was not match");
		assertEquals(notifPage.visibilityOfAddress2Text(), "Location Address2", "Address2 text was not match");
		assertEquals(notifPage.visibilityOfCityStateZipText(), "City State, Zip", "City State Zip text was not match");
		assertEquals(notifPage.visibilityOfMobileNoFormatText(), "(XXX) XXX-XXXX", "MobileNoFormat text was not match");
		assertEquals(notifPage.visibilityOfPinToMapText(), "Pin on Map", "Pin To Map text was not match");
		assertEquals(notifPage.visibilityOfProviderText(), "Provider", "Provider text was not match");
		assertEquals(notifPage.visibilityOfProviderNameText(), "Provider Name", "Provider Name text was not match");
		assertEquals(notifPage.visibilityOfRescheduleOrCancelText(), "Reschedule or Cancel",
				"Reschedule Or Cancel text was not match");
		assertEquals(notifPage.visibilityOfInstructionsText(),
				"Please do not reply to this auto generated message. If you have questions about this email, please contact your doctor’s office.",
				"Instructions text was not match");
		assertEquals(notifPage.visibilityOfConfidentialityNoticeText(), "Confidentiality Notice:",
				"Confidentiality Notice text was not match");
		assertEquals(notifPage.visibilityOfUnsubscribeText(), "Unsubscribe", "Unsubscribe text was not match");
		assertEquals(notifPage.visibilityOfBottomInfoText(),
				"*Information included in this communication is based on the data sent from the practice management system.",
				"Bottom Info text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@When("from setting in notifications user click on email hamburgerButton section of appointment reminder")
	public void from_setting_in_notifications_user_click_on_email_hamburger_button_section_of_appointment_reminder()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		log("From Appointment reminders open edit section for text");
		scrollAndWait(0, 1000, 5000);
		notifPage.clickApptReminderEmailHamburgerButton();
	}

	@When("user click preview of appointment reminder")
	public void user_click_preview_of_appointment_reminder() {
		notifPage.clickOnPreviewButton();
	}

	@Then("verify in desktop view all fields are display related to templates for appointment reminder")
	public void verify_in_desktop_view_all_fields_are_display_related_to_templates_for_appointment_reminder()
			throws InterruptedException {
		notifPage.openDesktopViewPage();
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.visibilityOfClickHereText(), "CLICK HERETO VIEW THIS IN A BROWSER WINDOW",
				"click here text was not match");
		assertTrue(notifPage.visibilityOfCadenceImage());
		assertTrue(notifPage.visibilityOfConfirmApptButton());
		assertEquals(notifPage.visibilityOfStartPrechecklink(), "Start PreCheck", "Start PreCheck text was not match");
		assertEquals(notifPage.visibilityOfApptReminderText(), "Appointment Reminder", "all text was not match");
		assertEquals(notifPage.visibilityOfapptComingUpMessageText(), "[Patient Name], your appointment is coming up.",
				"Appt Scheduled text was not match");
		assertEquals(notifPage.visibilityOfDateAndTimeText(), "Date and Time", "Date And Time text was not match");
		assertEquals(notifPage.visibilityOfDayOfTheWeekText(), "Day of the week at 00:00 AM/PM",
				"Day Of The Week text was not match");
		assertEquals(notifPage.visibilityOfDateFormatText(), "Month DD, YYYY", "Date Format text was not match");
		assertEquals(notifPage.visibilityOfLocationTextInPreview(), "Location", "Location text was not match");
		assertEquals(notifPage.visibilityOfLocationNameText(), "Location Name", "Location Name text was not match");
		assertEquals(notifPage.visibilityOfAddress1Text(), "Location Address1", "Address1 text was not match");
		assertEquals(notifPage.visibilityOfAddress2Text(), "Location Address2", "Address2 text was not match");
		assertEquals(notifPage.visibilityOfCityStateZipText(), "City State, Zip", "City State Zip text was not match");
		assertEquals(notifPage.visibilityOfMobileNoFormatText(), "(XXX) XXX-XXXX", "MobileNoFormat text was not match");
		assertEquals(notifPage.visibilityOfPinToMapText(), "Pin on Map", "Pin To Map text was not match");
		assertEquals(notifPage.visibilityOfProviderText(), "Provider", "Provider text was not match");
		assertEquals(notifPage.visibilityOfProviderNameText(), "Provider Name", "Provider Name text was not match");
		assertEquals(notifPage.visibilityOfRescheduleOrCancelText(), "Reschedule or Cancel",
				"Reschedule Or Cancel text was not match");
		assertEquals(notifPage.visibilityOfInstructionsText(),
				"Please do not reply to this auto generated message. If you have questions about this email, please contact your doctor’s office.",
				"Instructions text was not match");
		assertEquals(notifPage.visibilityOfConfidentialityNoticeText(), "Confidentiality Notice:",
				"Confidentiality Notice text was not match");
		assertEquals(notifPage.visibilityOfUnsubscribeText(), "Unsubscribe", "Unsubscribe text was not match");
		assertEquals(notifPage.visibilityOfBottomInfoText(),
				"*Information included in this communication is based on the data sent from the practice management system.",
				"Bottom Info text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify in desktop view all fields are display related to templates for appointment reminder for new practice")
	public void verify_in_desktop_view_all_fields_are_display_related_to_templates_for_appointment_reminder_for_new_practice()
			throws InterruptedException {
		notifPage.openDesktopViewPage();
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.visibilityOfClickHereText(), "CLICK HERETO VIEW THIS IN A BROWSER WINDOW",
				"click here text was not match");
		assertEquals(notifPage.visibilityOfNewPracticeLogoText(), "Your Logo Here", "Logo text was not match");
		assertTrue(notifPage.visibilityOfConfirmApptButton());
		assertEquals(notifPage.visibilityOfStartPrechecklink(), "Start PreCheck", "Start PreCheck text was not match");
		assertEquals(notifPage.visibilityOfApptReminderText(), "Appointment reminders", "all text was not match");
		assertEquals(notifPage.visibilityOfapptComingUpMessageText(), "[Patient Name], your appointment is coming up.",
				"Appt Scheduled text was not match");
		assertEquals(notifPage.visibilityOfDateAndTimeText(), "Date and Time", "Date And Time text was not match");
		assertEquals(notifPage.visibilityOfDayOfTheWeekText(), "Day of the week at 00:00 AM/PM",
				"Day Of The Week text was not match");
		assertEquals(notifPage.visibilityOfDateFormatText(), "Month DD, YYYY", "Date Format text was not match");
		assertEquals(notifPage.visibilityOfLocationTextInPreview(), "Location", "Location text was not match");
		assertEquals(notifPage.visibilityOfLocationNameText(), "Location Name", "Location Name text was not match");
		assertEquals(notifPage.visibilityOfAddress1Text(), "Location Address1", "Address1 text was not match");
		assertEquals(notifPage.visibilityOfAddress2Text(), "Location Address2", "Address2 text was not match");
		assertEquals(notifPage.visibilityOfCityStateZipText(), "City State, Zip", "City State Zip text was not match");
		assertEquals(notifPage.visibilityOfMobileNoFormatText(), "(XXX) XXX-XXXX", "MobileNoFormat text was not match");
		assertEquals(notifPage.visibilityOfPinToMapText(), "Pin on Map", "Pin To Map text was not match");
		assertEquals(notifPage.visibilityOfProviderText(), "Provider", "Provider text was not match");
		assertEquals(notifPage.visibilityOfProviderNameText(), "Provider Name", "Provider Name text was not match");
		assertEquals(notifPage.visibilityOfRescheduleOrCancelText(), "Reschedule or Cancel",
				"Reschedule Or Cancel text was not match");
		assertEquals(notifPage.visibilityOfInstructionsText(),
				"Please do not reply to this auto generated message. If you have questions about this email, please contact your doctor’s office.",
				"Instructions text was not match");
		assertEquals(notifPage.visibilityOfConfidentialityNoticeText(), "Confidentiality Notice:",
				"Confidentiality Notice text was not match");
		assertEquals(notifPage.visibilityOfUnsubscribeText(), "Unsubscribe", "Unsubscribe text was not match");
		assertEquals(notifPage.visibilityOfBottomInfoText(),
				"*Information included in this communication is based on the data sent from the practice management system.",
				"Bottom Info text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify in mobile view all fields are display related to templates for appointment reminder")
	public void verify_in_mobile_view_all_fields_are_display_related_to_templates_for_appointment_reminder()
			throws InterruptedException {
		notifPage.openMobileViewPage();
		scrollAndWait(0, 1000, 5000);
		assertTrue(notifPage.visibilityOfCadenceImage());
		assertTrue(notifPage.visibilityOfConfirmApptButton());
		assertEquals(notifPage.visibilityOfStartPrechecklink(), "Start PreCheck", "Start PreCheck text was not match");
		assertEquals(notifPage.visibilityOfApptReminderText(), "Appointment Reminder", "all text was not match");
		assertEquals(notifPage.visibilityOfapptComingUpMessageText(), "[Patient Name], your appointment is coming up.",
				"Appt Scheduled text was not match");
		assertEquals(notifPage.visibilityOfDateAndTimeText(), "Date and Time", "Date And Time text was not match");
		assertEquals(notifPage.visibilityOfDayOfTheWeekText(), "Day of the week at 00:00 AM/PM",
				"Day Of The Week text was not match");
		assertEquals(notifPage.visibilityOfDateFormatText(), "Month DD, YYYY", "Date Format text was not match");
		assertEquals(notifPage.visibilityOfLocationTextInPreview(), "Location", "Location text was not match");
		assertEquals(notifPage.visibilityOfLocationNameText(), "Location Name", "Location Name text was not match");
		assertEquals(notifPage.visibilityOfAddress1Text(), "Location Address1", "Address1 text was not match");
		assertEquals(notifPage.visibilityOfAddress2Text(), "Location Address2", "Address2 text was not match");
		assertEquals(notifPage.visibilityOfCityStateZipText(), "City State, Zip", "City State Zip text was not match");
		assertEquals(notifPage.visibilityOfMobileNoFormatText(), "(XXX) XXX-XXXX", "MobileNoFormat text was not match");
		assertEquals(notifPage.visibilityOfPinToMapText(), "Pin on Map", "Pin To Map text was not match");
		assertEquals(notifPage.visibilityOfProviderText(), "Provider", "Provider text was not match");
		assertEquals(notifPage.visibilityOfProviderNameText(), "Provider Name", "Provider Name text was not match");
		assertEquals(notifPage.visibilityOfRescheduleOrCancelText(), "Reschedule or Cancel",
				"Reschedule Or Cancel text was not match");
		assertEquals(notifPage.visibilityOfInstructionsText(),
				"Please do not reply to this auto generated message. If you have questions about this email, please contact your doctor’s office.",
				"Instructions text was not match");
		assertEquals(notifPage.visibilityOfConfidentialityNoticeText(), "Confidentiality Notice:",
				"Confidentiality Notice text was not match");
		assertEquals(notifPage.visibilityOfUnsubscribeText(), "Unsubscribe", "Unsubscribe text was not match");
		assertEquals(notifPage.visibilityOfBottomInfoText(),
				"*Information included in this communication is based on the data sent from the practice management system.",
				"Bottom Info text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify in mobile view all fields are display related to templates for appointment reminder for new practice")
	public void verify_in_mobile_view_all_fields_are_display_related_to_templates_for_appointment_reminder_for_new_practice()
			throws InterruptedException {
		notifPage.openMobileViewPage();
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.visibilityOfNewPracticeLogoText(), "Your Logo Here", "Logo text was not match");
		assertTrue(notifPage.visibilityOfConfirmApptButton());
		assertEquals(notifPage.visibilityOfStartPrechecklink(), "Start PreCheck", "Start PreCheck text was not match");
		assertEquals(notifPage.visibilityOfApptReminderText(), "Appointment Reminder", "all text was not match");
		assertEquals(notifPage.visibilityOfapptComingUpMessageText(), "[Patient Name], your appointment is coming up.",
				"Appt Scheduled text was not match");
		assertEquals(notifPage.visibilityOfDateAndTimeText(), "Date and Time", "Date And Time text was not match");
		assertEquals(notifPage.visibilityOfDayOfTheWeekText(), "Day of the week at 00:00 AM/PM",
				"Day Of The Week text was not match");
		assertEquals(notifPage.visibilityOfDateFormatText(), "Month DD, YYYY", "Date Format text was not match");
		assertEquals(notifPage.visibilityOfLocationTextInPreview(), "Location", "Location text was not match");
		assertEquals(notifPage.visibilityOfLocationNameText(), "Location Name", "Location Name text was not match");
		assertEquals(notifPage.visibilityOfAddress1Text(), "Location Address1", "Address1 text was not match");
		assertEquals(notifPage.visibilityOfAddress2Text(), "Location Address2", "Address2 text was not match");
		assertEquals(notifPage.visibilityOfCityStateZipText(), "City State, Zip", "City State Zip text was not match");
		assertEquals(notifPage.visibilityOfMobileNoFormatText(), "(XXX) XXX-XXXX", "MobileNoFormat text was not match");
		assertEquals(notifPage.visibilityOfPinToMapText(), "Pin on Map", "Pin To Map text was not match");
		assertEquals(notifPage.visibilityOfProviderText(), "Provider", "Provider text was not match");
		assertEquals(notifPage.visibilityOfProviderNameText(), "Provider Name", "Provider Name text was not match");
		assertEquals(notifPage.visibilityOfRescheduleOrCancelText(), "Reschedule or Cancel",
				"Reschedule Or Cancel text was not match");
		assertEquals(notifPage.visibilityOfInstructionsText(),
				"Please do not reply to this auto generated message. If you have questions about this email, please contact your doctor’s office.",
				"Instructions text was not match");
		assertEquals(notifPage.visibilityOfConfidentialityNoticeText(), "Confidentiality Notice:",
				"Confidentiality Notice text was not match");
		assertEquals(notifPage.visibilityOfUnsubscribeText(), "Unsubscribe", "Unsubscribe text was not match");
		assertEquals(notifPage.visibilityOfBottomInfoText(),
				"*Information included in this communication is based on the data sent from the practice management system.",
				"Bottom Info text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify in settings section all fields are display related to email templates of appointment reminder")
	public void verify_in_settings_section_all_fields_are_display_related_to_email_templates_of_appointment_reminder() {
		assertEquals(notifPage.visibilityOfPreviewPageTitle(), "Preview", "Preview text was not match");
		assertEquals(notifPage.visibilityOfViewingText(), "Viewing in:", "Viewing in text was not match");
		assertEquals(notifPage.visibilityOfEnglishLang(), "English", "English text was not match");
		assertTrue(notifPage.visibilityOfEditbuttonInPreview());
		assertEquals(notifPage.visibilityOfCloseButton(), "Close", "Text was not match");
		assertEquals(notifPage.visibilityOfSettingText(), "Settings", "Settings text was not match");
		assertEquals(notifPage.visibilityOfNotiTypeText(), "Notification Type:",
				"Notification Type text was not match");
		assertEquals(notifPage.visibilityOfApptReminderTextforPreview(), "Appointment Reminder",
				" Appointment Confirmation text was not match");
		assertEquals(notifPage.visibilityOfVersionText(), "Version:", "Version text was not match");
		assertEquals(notifPage.visibilityOfApptMethodText(), "Appointment Method:",
				"Appointment Method text was not match");
		assertEquals(notifPage.visibilityOfInOfficeText(), "In Office", "In Office text was not match");
		assertEquals(notifPage.visibilityOfDeliveryMethodText(), "Delivery Method:",
				"Delivery Method text was not match");
		assertEquals(notifPage.visibilityOfEmailText(), "Email", "Email text was not match");
		assertEquals(notifPage.visibilityOfTimingText(), "Timing:", "Timing text was not match");
		assertEquals(notifPage.visibilityOfResourcesText(), "Resource(s):", "Text was not match");
		assertEquals(notifPage.visibilityOfAllResourcesText(), "All", "All text was not match");
		assertEquals(notifPage.visibilityOfApptTypesText(), "Appointment Type(s):",
				"Appointment Type(s): text was not match");
		assertEquals(notifPage.visibilityOfAllApptTypesText(), "All", "All text was not match");
		assertEquals(notifPage.visibilityOfLocationText(), "Location(s):", "Location text was not match");
		assertEquals(notifPage.visibilityOfAllLocationText(), "All", "all text was not match");
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify on preview page from appointment reminder edit button working properly")
	public void verify_on_preview_page_from_appointment_reminder_edit_button_working_properly_for_email()
			throws InterruptedException {
		notifPage.clickOnEditButtonInPreviewPage();
		log("User redirect on template design page");
		assertEquals(notifPage.emailDesignPage(), "Design", "Design page was not display");
		;
		assertEquals(notifPage.emailEditingPage(), "Editing:", "Editing page was not display");
		log("Editing and design page displayed");
		notifPage.clickOnBackArrow();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@When("from setting in notifications user click on text hamburgerButton section of appointment reminder")
	public void from_setting_in_notifications_user_click_on_text_hamburger_button_section_of_appointment_reminder()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		log("From Appointment reminders open edit section for text");
		scrollAndWait(0, 1000, 5000);
		notifPage.clickApptReminderSmsHamburgerButton();
	}

	@Then("verify in settings section all fields are display related to text templates of appointment reminder")
	public void verify_in_settings_section_all_fields_are_display_related_to_text_templates_of_appointment_reminder() {
		assertEquals(notifPage.visibilityOfPreviewPageTitle(), "Preview", "Preview text was not match");
		assertEquals(notifPage.visibilityOfViewingText(), "Viewing in:", "Viewing in text was not match");
		assertEquals(notifPage.visibilityOfEnglishLang(), "English", "English text was not match");
		assertTrue(notifPage.visibilityOfEditbuttonInPreview());
		assertEquals(notifPage.visibilityOfCloseButton(), "Close", "Text was not match");
		assertEquals(notifPage.visibilityOfSettingText(), "Settings", "Settings text was not match");
		assertEquals(notifPage.visibilityOfNotiTypeText(), "Notification Type:",
				"Notification Type text was not match");
		assertEquals(notifPage.visibilityOfApptReminderTextforPreview(), "Appointment Reminder",
				" Appointment Confirmation text was not match");
		assertEquals(notifPage.visibilityOfVersionText(), "Version:", "Version text was not match");
		assertEquals(notifPage.visibilityOfDefaultVersionText(), "Default", "Default Version text was not match");
		assertEquals(notifPage.visibilityOfApptMethodText(), "Appointment Method:",
				"Appointment Method text was not match");
		assertEquals(notifPage.visibilityOfInOfficeText(), "In Office", "In Office text was not match");
		assertEquals(notifPage.visibilityOfDeliveryMethodText(), "Delivery Method:",
				"Delivery Method text was not match");
		assertEquals(notifPage.visibilityOfSmsText(), "SMS", "Email text was not match");
		assertEquals(notifPage.visibilityOfTimingText(), "Timing:", "Timing text was not match");
		assertEquals(notifPage.visibilityOfResourcesText(), "Resource(s):", "Text was not match");
		assertEquals(notifPage.visibilityOfAllResourcesText(), "All", "All text was not match");
		assertEquals(notifPage.visibilityOfApptTypesText(), "Appointment Type(s):",
				"Appointment Type(s): text was not match");
		assertEquals(notifPage.visibilityOfAllApptTypesText(), "All", "All text was not match");
		assertEquals(notifPage.visibilityOfLocationText(), "Location(s):", "Location text was not match");
		assertEquals(notifPage.visibilityOfAllLocationText(), "All", "all text was not match");
		assertTrue(notifPage.visibilityOfPatientInfoInTextMsgForApptRemText());
		assertTrue(notifPage.visibilityOfConfirmApptlinkForApptRemText());
		assertTrue(notifPage.visibilityOfRescheduleAndCancelLinkApptRemText());
		assertTrue(notifPage.visibilityGetDirectionLinkApptRemText());
		assertEquals(notifPage.visibilityTextToStopUnsubscribeApptRemText(), "Text STOP to unsubscribe",
				"Text STOP to unsubscribe was mot match");
		assertTrue(notifPage.visibilityOfSmsPreviewBottomtext());
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@When("user mouse hover on info tab from setting in notifications")
	public void user_mouse_hover_on_info_tab_from_setting_in_notifications() {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify {string} message will be display")
	public void verify_message_will_be_display(String sendNotificationText) throws InterruptedException {
		log("Send notification traingle text:- " + notifPage.getTextFronSendNotifTraingleTab());
		assertEquals(notifPage.getTextFronSendNotifTraingleTab(), sendNotificationText,
				"Send notification text was not match");
	}

	@When("from setting dashboard in notifications disable broadcast checkbox")
	public void from_setting_dashboard_in_notifications_disable_broadcast_checkbox() throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.disableBroadcastMessagingCheckbox();
		notifPage.saveNotification();
	}

	@Then("verify in appointment dashboard in broadcast column should not be display")
	public void verify_in_appointment_dashboard_in_broadcast_column_should_not_be_display() {
		mainPage.clickOnAppointmentsTab();
		log("verify on appointment dashboard email and text column should not be visible");
		assertFalse(apptPage.broadcastMessageTextColumn());
		assertFalse(apptPage.broadcastMessageEmailColumn());
	}

	@When("from setting in notifications user click on curbside checkin tab and click on english button")
	public void from_setting_in_notifications_user_click_on_curbside_checkin_tab_and_click_on_english_button()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.clickOnCurbsideCheckInTabInNotif();
		scrollAndWait(0, 500, 3000);
		notifPage.clickOnEnglishButton();
	}

	@Then("verify if user is able see additional arrival instruction message in english")
	public void verify_if_user_is_able_see_additional_arrival_instruction_message_in_english() {
		log("Additional Arrival Instruction Msg In English:- "
				+ notifPage.getAdditionalArrivalInstructionMsgTextInEnglish());
		log("Additional Arrival instructions is displayed.");
		assertEquals(notifPage.getAdditionalArrivalInstructionMsgTextInEnglish(), "hello welcome to curbside checkin",
				"Additional arrival instruction text in english is not matched");
	}

	@When("from setting in notifications user click on curbside checkin tab and click on spanish button")
	public void from_setting_in_notifications_user_click_on_curbside_checkin_tab_and_click_on_spanish_button()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.clickOnCurbsideCheckInTabInNotif();
		scrollAndWait(0, 500, 3000);
		notifPage.clickOnSpanishButton();
	}

	@Then("verify if user is able see additional arrival instruction message in spanish")
	public void verify_if_user_is_able_see_additional_arrival_instruction_message_in_spanish() {
		log("Additional Arrival Instruction Msg In Spanish:- "
				+ notifPage.getAdditionalArrivalInstructionMsgTextInSpanish());
		log("Additional Arrival instructions is displayed.");
		assertEquals(notifPage.getAdditionalArrivalInstructionMsgTextInSpanish(),
				"hola bienvenido al registro en la acera",
				"Arrival confirmation message text in spanish is not matched");
	}

	@Then("verify if user is able see arrival confirmation message in english")
	public void verify_if_user_is_able_see_arrival_confirmation_message_in_english() throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		notifPage.clickOnEnglishButton();
		assertEquals(notifPage.visibilityOfArrivalConfirmationMsgInEnglish(), "Arrival confirmation message",
				"Arrival confirmation message text is not matched");
	}

	@Then("verify if user is able see arrival confirmation message in spanish")
	public void verify_if_user_is_able_see_arrival_confirmation_message_in_spanish() throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		notifPage.clickOnSpanishButton();
		assertEquals(notifPage.visibilityOfArrivalConfirmationMsgInSpanish(), "Arrival confirmation message",
				"Arrival confirmation message text is not matched");
	}

	@When("from setting in notifications user click on notification tab")
	public void from_setting_in_notifications_user_click_on_notification_tab() {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify if user is able to see appointment confirmation and appointment reminder section")
	public void verify_if_user_is_able_to_see_appointment_confirmation_and_appointment_reminder_section() {
		assertEquals(notifPage.getAppointmentConfirmationsText(), "Appointment confirmations",
				"Appointment confirmations text is not matched");
		assertEquals(notifPage.getAppointmentRemindersText(), "Appointment reminders",
				"Appointment reminders text is not matched");
	}

	@Then("verify if user is able to see two templates one for mail and another for text templates under appointment confirmation section")
	public void verify_if_user_is_able_to_see_two_templates_one_for_mail_and_another_for_text_templates_under_appointment_confirmation_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfEmailTemplateUnderAptConfirmation(), "Email",
				"Email section is not matched");
		assertEquals(notifPage.visibilityOfSMSTemplateUnderAptConfirmation(), "SMS", "SMS section is not matched");
	}

	@Then("verify if user is able to see two templates one for mail and another for text templates under appointment reminder section")
	public void verify_if_user_is_able_to_see_two_templates_one_for_mail_and_another_for_text_templates_under_appointment_reminder_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfEmailTemplateUnderAptReminder(), "Email", "Email text is not matched");
		assertEquals(notifPage.visibilityOfSMSTemplateUnderAptReminder(), "SMS", "SMS text is not matched");
	}

	@Then("verify if user is able to see published status under appointment reminder and appointment confirmation section")
	public void verify_if_user_is_able_to_see_published_status_under_appointment_reminder_and_appointment_confirmation_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfPublishedStatusEmailAptConf(), "Published", "Published text is not matched");
		assertEquals(notifPage.visibilityOfPublishedStatusSMSAptConf(), "Published", "Published text is not matched");
		assertEquals(notifPage.visibilityOfPublishedStatusEmailAptRem(), "Published", "Published text is not matched");
		assertEquals(notifPage.visibilityOfPublishedStatusSMSAptRem(), "Published", "Published text is not matched");
	}

	@Then("verify if user is able to see appointment confirmation text in bold fonts along with info icon")
	public void verify_if_user_is_able_to_see_appointment_confirmation_text_in_bold_fonts_along_with_info_icon() {
		assertEquals(notifPage.getAppointmentConfirmationsText(), "Appointment confirmations",
				"Appointment confirmations text is not in bold");
		assertTrue(notifPage.visibiliyOfAptConfirmationInfoIcon());
	}

	@Then("verify mouse hover over to info icon and system shows {string} on pop up")
	public void verify_mouse_hover_over_to_info_icon_and_system_shows_on_pop_up(String aptConfirmationInfoIconText) {
		log("Info icon of Appointment Confirmation text :- " + notifPage.getTextFromAptConfirmationInfoIcon());
		assertEquals(notifPage.getTextFromAptConfirmationInfoIcon(), aptConfirmationInfoIconText,
				" Appointment confirmation info icon text is not match");
	}

	@Then("verify in delivery method section mail and text fields are display under appointment confirmation section")
	public void verify_in_delivery_method_section_mail_and_text_fields_are_display_under_appointment_confirmation_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfDeliveryMethodTextNotificationTab(), "Delivery Method",
				"Delivery Method text is not match");
		assertEquals(notifPage.visibilityOfEmailTemplateUnderAptConfirmation(), "Email", "Email text is not matched");
		assertEquals(notifPage.visibilityOfSMSTemplateUnderAptConfirmation(), "SMS", "SMS text is not matched");
	}

	@Then("verify user is able to see default status for mail and sms under version section of appointment confirmation")
	public void verify_user_is_able_to_see_default_status_for_mail_and_sms_under_version_section_of_appointment_confirmation_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfDefaultEmailAptConf(), "Default", "Default text is not match");
		assertEquals(notifPage.visibilityOfDefaultSMSAptConf(), "Default", "Default text is not match");
		assertEquals(notifPage.visibilityOfVersionTextAptConfNotificationTab(), "Version", "Version text is not match");
	}

	@Then("verify by default user is able to see in office status for mail and text fields under appointment method section of appointment confirmation")
	public void verify_by_default_user_is_able_to_see_in_office_status_for_mail_and_text_fields_under_appointment_method_section_of_appointment_confirmation_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfApptMethodTextAptConfNotificationTab(), "Appt. Method",
				"Appt. Method is not match");
		assertEquals(notifPage.visibilityOfInOfficeTextEmailAptConf(), "In Office", "In Office text is not match");
		assertEquals(notifPage.visibilityOfInOfficeTextSMSAptConf(), "In Office", "In Office text is not match");
	}

	@Then("verify user is able to see upon scheduling status for mail and text fields under timing section of appointment confirmation")
	public void verify_user_is_able_to_see_upon_scheduling_status_for_mail_and_text_fields_under_timing_section_of_appointment_confirmation_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfUponSchedulingEmailAptConfNotificationTab(), "Upon Scheduling",
				"Upon Scheduling is not match");
		assertEquals(notifPage.visibilityOfUponSchedulingSMSAptConfNotificationTab(), "Upon Scheduling",
				"Upon Scheduling is not match");
		assertEquals(notifPage.visibilityOfTimingTextNotificationTab(), "Timing", "Timing text is not match");
	}

	@Then("verify by default user is able to see published status under status section for mail and text fields under timing section of appointment confirmation")
	public void verify_by_default_user_is_able_to_see_published_status_under_status_section_for_mail_and_text_fields_under_timing_section_of_appointment_confirmation_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfPublishedStatusEmailAptConf(), "Published", "Published text is not matched");
		assertEquals(notifPage.visibilityOfPublishedStatusSMSAptConf(), "Published", "Published text is not matched");
		assertEquals(notifPage.visibilityOfStatusTextAptConf(), "Status", "Status text is not matched");
	}

	@Then("verify if user is able to see appointment reminder text in bold fonts along with info icon")
	public void verify_if_user_is_able_to_see_appointment_reminder_text_in_bold_fonts_along_with_info_icon()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfApptReminderText(), "Appointment reminders",
				"Appointment reminders text is not in bold");
		assertTrue(notifPage.visibiliyOfAptReminderInfoIcon());
	}

	@Then("verify mouse hover over to info icon of appointment reminders and system shows {string} on pop up")
	public void verify_mouse_hover_over_to_info_icon_of_appointment_reminders_and_system_shows_on_pop_up(
			String aptRemindersInfoIconText) throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		log("Info icon of Appointment Reminders text :- " + notifPage.visibiliyOfAptReminderInfoIcon());
		log("Appointment Reminders icon message text :- " + notifPage.getTextFromAptRemindersInfoIcon());
		assertEquals(notifPage.getTextFromAptRemindersInfoIcon(), aptRemindersInfoIconText,
				" Appointment reminders info icon text is not match");
	}

	@Then("verify in delivery method section mail and text fields are display under appointment reminders section")
	public void verify_in_delivery_method_section_mail_and_text_fields_are_display_under_appointment_reminders_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfDeliveryMethodTextNotificationTab(), "Delivery Method",
				"Delivery Method text is not match");
		assertEquals(notifPage.visibilityOfEmailTemplateUnderAptReminder(), "Email", "Email text is not matched");
		assertEquals(notifPage.visibilityOfSMSTemplateUnderAptReminder(), "SMS", "SMS text is not matched");
	}

	@Then("verify user is able to see default status for mail and sms under version section of appointment reminders")
	public void verify_user_is_able_to_see_default_status_for_mail_and_sms_under_version_section_of_appointment_reminders_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfv2EmailAptRem(), "v2", "v2 text is not match");
		assertEquals(notifPage.visibilityOfDefaultSMSAptRem(), "Default", "Default text is not match");
		assertEquals(notifPage.visibilityOfVersionTextAptRemNotificationTab(), "Version", "Version text is not match");
	}

	@Then("verify by default user is able to see in office status for mail and text fields under appointment method section of appointment reminders")
	public void verify_by_default_user_is_able_to_see_in_office_status_for_mail_and_text_fields_under_appointment_method_section_of_appointment_reminders_section()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		assertEquals(notifPage.visibilityOfApptMethodTextAptRemNotificationTab(), "Appt. Method",
				"Appt. Method is not match");
		assertEquals(notifPage.visibilityOfInOfficeTextEmailAptRem(), "In Office", "In Office text is not match");
		assertEquals(notifPage.visibilityOfInOfficeTextSMSAptRem(), "In Office", "In Office text is not match");
	}

	@Then("verify if day,hour and minutes are configured in email and sms template then it reflect in timing section of appointment reminder")
	public void verify_if_day_hour_and_minutes_are_configured_in_email_and_sms_template_then_it_reflect_in_timing_section_of_appointment_reminder_section() {
		assertEquals(notifPage.visibilityOfDaysEmailAptRemNotificationTab(), "Days", "Days is not match");
		assertEquals(notifPage.visibilityOfDaysSMSAptRemNotificationTab(), "Days", "Days is not match");
		assertEquals(notifPage.visibilityOfHoursEmailAptRemNotificationTab(), "Hours", "Hours is not match");
		assertEquals(notifPage.visibilityOfHoursSMSAptRemNotificationTab(), "Hours", "Hours is not match");
		assertEquals(notifPage.visibilityOfMinutesEmailAptRemNotificationTab(), "Minutes", "Minutes is not match");
		assertEquals(notifPage.visibilityOfMinutesSMSAptRemNotificationTab(), "Minutes", "Minutes is not match");
		assertEquals(notifPage.visibilityOfTimingTextUnderAptRemNotificationTab(), "Timing", "Timing is not match");
	}

	@Then("verify if user is able to see timing units under timing unit section of appointment reminder")
	public void verify_if_user_is_able_to_see_timing_units_under_timing_unit_section_of_appointment_reminder_section() {
		assertEquals(notifPage.visibilityOfOneUnitUnderTimingUnitNotificationTab(), "1", "1 is not match");
		assertEquals(notifPage.visibilityOfthirtyUnitUnderTimingUnitNotificationTab(), "30", "30 is not match");
		assertEquals(notifPage.visibilityOfTimingUnitsUnderAptRemNotificationTab(), "Timing Units",
				"Timing Units not is match");
	}

	@Then("verify by default user is able to see published status under status section for mail and text fields under timing section of appointment reminder section")
	public void verify_by_default_user_is_able_to_see_published_status_under_status_section_for_mail_and_text_fields_under_timing_section_of_appointment_reminder_section() {
		assertEquals(notifPage.visibilityOfPublishedStatusEmailAptRem(), "Published", "Published text is not matched");
		assertEquals(notifPage.visibilityOfPublishedStatusSMSAptRem(), "Published", "Published text is not matched");
		assertEquals(notifPage.visibilityOfStatusTextAptRem(), "Status", "Status text is not matched");
	}

	@Then("verify if user is able to see preview button")
	public void verify_if_user_is_able_to_see_preview_button() throws InterruptedException {
		notifPage.clickOnApptConfirmationsEmailhumburgerButton();
		log("Preview button is displayed");
		assertEquals(notifPage.getOnPreviewButtonText(), "Preview", "Preview button is not match");
	}

	@Then("verify if user is able to see edit and preview button")
	public void verify_if_user_is_able_to_see_edit_and_preview_button() throws InterruptedException {
		scrollAndWait(0, 1000, 3000);
		assertEquals(notifPage.visibilityOfEditTextUnderHamburger(), "Edit", "Edit text is not matched");
		assertEquals(notifPage.getOnPreviewButtonText(), "Preview", "Preview text is not matched");
		log("Preview and Edit buttons are displayed");
	}

	@Then("user hit edit button of email for appointment reminder")
	public void user_hit_edit_button_of_email_for_appointment_reminder() throws InterruptedException {
		scrollAndWait(0, 1000, 3000);
		notifPage.clickOnEditButtonHamburgerButton();
		log("User redirect on edit template design page");
	}

	@Then("verify if user is able to hit back button and system should redirect back user on notification tab")
	public void verify_if_user_is_able_to_hit_back_button_and_system_should_redirect_back_user_on_notification_tab()
			throws InterruptedException {
		notifPage.clickOnBackArrow();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify if user is able to hit preview button and system should redirect on preview template page")
	public void verify_if_user_is_able_to_hit_preview_button_and_system_should_redirect_on_preview_template_page()
			throws InterruptedException {
		Thread.sleep(5000);
		notifPage.clickOnPreviewButton();
		assertEquals(notifPage.visibilityOfPreviewPageTitle(), "Preview", "Preview template design page is not match");
		log("User redirect on preview template design page");
	}

	@Then("user hit preview button")
	public void user_hit_preview_button() throws InterruptedException {
		Thread.sleep(5000);
		notifPage.clickOnPreviewButton();
		log("User redirect on preview template design page");
	}

	@Then("verify if user is able to hit close button and system should redirect back user on notification tab")
	public void verify_if_user_is_able_to_hit_close_button_and_system_should_redirect_back_user_on_notification_tab() {
		notifPage.closePreviewPage();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@When("from settings in general under manage solutions tab remove practice display name")
	public void from_settings_in_general_under_manage_solutions_tab_remove_practice_display_name()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		generalPage.clickOnManageSolutionsTab();
		log("verify General setting Text: " + propertyData.getProperty("general.setting.title"));
		assertTrue(generalPage.generalSettingTitle().contains(propertyData.getProperty("general.setting.title")));
		log("verify Mamage solutions Text: " + propertyData.getProperty("manage.solution.board"));
		assertTrue(generalPage.manageSolutionTab().contains(propertyData.getProperty("manage.solution.board")));
		log("user on Manage Solutions Tab");
		generalPage.clearPracticeDisplayName();
	}

	@When("click on update settings and it showing {string} error")
	public void click_on_update_settings_and_it_showing_error(String practiceDisplayNameError)
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		generalPage.clickOnUpdateSettingbutton();
		log("Error is shown after removing practice display name :- "
				+ generalPage.visibilityOfPracticeDisplayNameError());
		assertEquals(generalPage.visibilityOfPracticeDisplayNameError(), practiceDisplayNameError,
				"Error is shown after removing practice display text is not match");
	}

	@Then("navigate to forms tab and again navigate to manage solutions tab")
	public void navigate_to_forms_tab_and_again_navigate_to_manage_solutions_tab() throws InterruptedException {
		generalPage.clickOnFormsTab();
		assertTrue(generalPage.getFormsText().contains("Forms"));
		log("user on Forms Tab");
		generalPage.clickOnGeneralTab();
		generalPage.clickOnManageSolutionsTab();
		scrollAndWait(0, 500, 3000);
		assertTrue(generalPage.manageSolutionTab().contains(propertyData.getProperty("manage.solution.board")));
	}

	@Then("verify if the user is able to see the practice display name remains as initial value")
	public void verify_if_the_user_is_able_to_see_the_practice_display_name_remains_as_initial_value()
			throws InterruptedException {
		assertEquals(generalPage.visibilityOfPracticeDisplayName(), "PSS-GE-24333-PRACTICE",
				"PSS-GE-24333-PRACTICE is not match");
	}

	@Then("press space bar on textbox")
	public void press_space_bar_on_textbox() {
		generalPage.savePracticeDisplayName();
	}

	@When("click on update settings")
	public void click_on_update_settings() throws InterruptedException {
		generalPage.clickOnUpdateSettingbutton();
	}

	@Then("verify if user is able to see validation message {string}")
	public void verify_if_user_is_able_to_see_validation_message(String practiceDisplayNameError) {
		log("Error is shown after removing practice display name :- "
				+ generalPage.visibilityOfPracticeDisplayNameError());
		assertEquals(generalPage.visibilityOfPracticeDisplayNameError(), practiceDisplayNameError,
				"Error is shown after removing practice display text is not match");
	}

	@When("from setting in notifications user turn off send notification radio button")
	public void from_setting_in_notifications_user_turn_off_send_notification_radio_button() {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.offNotification();
		log("send notification radio button is off");
	}

	@Then("click on save button")
	public void click_on_save_button() throws InterruptedException {
		notifPage.saveNotification();
	}

	@Then("verify if user is able to see changes are reflected in notification tab as send notification is off")
	public void verify_if_user_is_able_to_see_changes_are_reflected_in_notification_tab_as_send_notification_is_off() {
		assertTrue(notifPage.visibilityOfNotificationOffRadioButton());
	}

	@When("from setting in notifications user turn on send notification radio button")
	public void from_setting_in_notifications_user_turn_on_send_notification_radio_button() {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.onNotification();
		log("send notification radio button is on");
	}

	@Then("verify if user is able to see changes are reflected in notification tab as send notification is on")
	public void verify_if_user_is_able_to_see_changes_are_reflected_in_notification_tab_as_send_notification_is_on() {
		assertTrue(notifPage.visibilityOfNotificationOnRadioButton());
	}

	@Then("user keep blank additional arrival instructions text box of english language")
	public void user_keep_blank_additional_arrival_instructions_text_box_of_english_language()
			throws InterruptedException {
		notifPage.clearAdditionalArrivalInstTextboxEn();
		Thread.sleep(5000);
	}

	@Then("verify if user is able to see changes are reflected in curbside checkin tab of english language")
	public void verify_if_user_is_able_to_see_changes_are_reflected_in_curbside_checkin_tab_of_english_language() {
		assertEquals(notifPage.AdditionalArrivalInstTextboxBlankEn(), "",
				"Additional arrival instrunction textbox is not blank");
	}

	@Then("user rewrite additional arrival instructions text box of english language")
	public void user_rewrite_additional_arrival_instructions_text_box_of_english_language() {
		notifPage.addArrivalInstructionTextInEnglish("hello welcome to curbside checkin");
	}

	@Then("user keep blank additional arrival instructions text box of spanish language")
	public void user_keep_blank_additional_arrival_instructions_text_box_of_spanish_language() {
		notifPage.clearAdditionalArrivalInstTextboxEs();
	}

	@Then("verify if user is able to see changes are reflected in curbside checkin tab of spanish language")
	public void verify_if_user_is_able_to_see_changes_are_reflected_in_curbside_checkin_tab_of_spanish_language()
			throws InterruptedException {
		Thread.sleep(5000);
		assertEquals(notifPage.additionalArrivalInstTextboxBlankEs(), "",
				"Additional arrival instrunction textbox is not blank");
	}

	@Then("user rewrite additional arrival instructions text box of spanish language")
	public void user_rewrite_additional_arrival_instructions_text_box_of_spanish_language() {
		notifPage.addArrivalInstructionTextInEnglish("hola bienvenido al registro en la acera");
	}

	@When("from setting in notifications user click on practice language preference drop down and select english and spanish language")
	public void from_setting_in_notifications_user_click_on_practice_language_preference_drop_down_and_select_english_and_spanish_language()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.clickOnPracticePrefLangDropDown();
		notifPage.selectEnglishSpanishPracticePrefLang();
		log("user select english and spanish practice preference language");
		notifPage.saveNotification();
	}

	@Then("verify if user is able to see changes are reflected in notification tab as english and spanish language")
	public void verify_if_user_is_able_to_see_changes_are_reflected_in_notification_tab_as_english_and_spanish_language() {
		assertTrue(notifPage.visiblityOfBroadcastMessageTextBoxInEnEs(),
				"English & Spanish language preferance is not match");
	}

	@When("from setting in notifications user click on practice language preference drop down and select english")
	public void from_setting_in_notifications_user_click_on_practice_language_preference_drop_down_and_select_english()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.clickOnPracticePrefLangDropDown();
		notifPage.clickOnEnglishPracticePrefLang();
		log("user select english practice preference language");
		notifPage.saveNotification();
	}

	@Then("verify if user is able to see changes are reflected in notification tab english language")
	public void verify_if_user_is_able_to_see_changes_are_reflected_in_notification_tab_english_language() {
		assertTrue(notifPage.visibilityOfPracticePrefenceLangEn(), "English language preferance is not match");
	}

	@When("user is on notification tab in setting")
	public void user_is_on_notification_tab_in_setting() {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify that notification tab is showing all the fields,button,textbox,radio button,infobutton as per requirement")
	public void verify_that_notification_tab_is_showing_all_the_fields_button_textbox_radio_button_infobutton_as_per_requirement() {
		assertEquals(notifPage.getNotificationTitle(), "Notifications", "Notifications text was not match");
		assertEquals(notifPage.getSaveButtonText(), "Save", "Save Button text was not match");
		assertEquals(notifPage.getOnNotificationText(), "ON", "ON Button text was not match");
		assertEquals(notifPage.getOfOffNotificationText(), "OFF", "OFF Button text was not match");
		assertEquals(notifPage.getFeatureText(), "Features", "Features text was not match");
		assertEquals(notifPage.getBroadcastMessagingText(), "Broadcast messaging",
				"Broadcast messaging text was not match");
		assertEquals(notifPage.getCurbsideReminderText(), "Curbside check-in reminder",
				"Curbside check-in reminder text was not match");
		assertEquals(notifPage.getPatientFirstNameText(), "Display patient's first name",
				"Display patient's first name text was not match");
		notifPage.onNotification();
		assertEquals(notifPage.getNotificationTypeText(), "Notification Type", "Notification Type Text was not match");
		assertEquals(notifPage.getApptConfirmationText(), "Appointment confirmations",
				"Appointment confirmations Text was not match");
		assertEquals(notifPage.getApptReminderTextOnNotif(), "Appointment reminders",
				"Appointment reminders Text was not match");
		notifPage.clickOnCurbsideOption();
		String str = "Curbside check-in notifications will be sent 1 hour prior to the appointment.";
		assertEquals(notifPage.getCurbsideParagraphText(), str, "Curbside Paragraph Text was not match");
		assertEquals(notifPage.getEnglishButtonText(), "English", "English Button Text was not match");
		assertEquals(notifPage.getSpanishButtonText(), "Spanish", "Spanish Button Text was not match");
		notifPage.clickOnEnglishButton();
		assertEquals(notifPage.getArrivalConfMsgHeadingText(), "Arrival confirmation message",
				"Arrival confirmation message Heading was not match");
	}

	@When("logged into precheck admin and user is able to view appointment dashboard screen")
	public void logged_into_precheck_admin_and_user_is_able_to_view_appointment_dashboard_screen() {
		assertTrue(apptPage.getApptPageTitle().contains("Appointments"));
		log("User is on Appointment Dashboard");
	}

	@And("click on Curbside check-in tab")
	public void click_on_curbside_check_in_tab() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
		log("User is on Curbside check in tab");
	}

	@Then("verify the column header of grid section in arrival dashboard screen.")
	public void verify_the_column_header_of_grid_section_in_arrival_dashboard_screen() {
		assertEquals(curbsidePage.getApptTimeText(), "Appt time", "Appt time text was not match");
		assertEquals(curbsidePage.getWaitTimeText(), "Wait time", "Wait time text was not match");
		assertEquals(curbsidePage.getMessagesText(), "Messages", "Messages text was not match");
		assertEquals(curbsidePage.getSendMessagesText(), "Send message", "Send messages text was not match");
		assertEquals(curbsidePage.getHistoryText(), "History", "History text was not match");
		assertEquals(curbsidePage.getPatientNameText(), "Patient name", "Patient name text was not match");
		assertEquals(curbsidePage.getPatientIdText(), "Patient ID", "Patient ID text was not match");
		assertEquals(curbsidePage.getProviderText(), "Provider", "Provider text was not match");
		assertEquals(curbsidePage.getLocationText(), "Location", "Location text was not match");
		assertEquals(curbsidePage.getEmailText(), "Email", "Email text was not match");
		assertEquals(curbsidePage.getPhoneText(), "Phone", "Phone text was not match");
	}

	@And("click on Notifications tab in Setting tab")
	public void click_on_notifications_tab_in_setting_tab() {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}

	@Then("verify the Curbside check-in reminder option")
	public void verify_the_Curbside_check_in_reminder_option() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		notifPage.clickOnCurbsideOption();
		String message = "Curbside check-in notifications will be sent 1 hour prior to the appointment.";
		assertEquals(notifPage.visibilityOf1HrPriorCurbsideReminder(), message,
				"Curbside check-in reminder text was not match");
	}

	@When("schedule an appointment for four patient and have confirmed their arrival")
	public void schedule_an_appointment_for_four_patient_and_have_confirmed_their_arrival()
			throws NullPointerException, IOException {
		for (int i = 0; i < 4; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);

			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
	}

	@And("click on the notification icon")
	public void click_on_the_notification_icon() throws InterruptedException {
		apptPage.clickOnNotifIcon();
		assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
		log("User is on Curbside check in tab");
	}

	@Then("verify the notification icon count on the top")
	public void verify_the_notification_icon_count_on_the_top() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		assertEquals(curbsidePage.getNotificationCount(), "4", "Notification Count is not match");
	}

	@When("schedule an appointment and confirmed their arrival")
	public void schedule_an_appointment_and_confirmed_their_arrival() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}

	@When("select patient and click on dropdown")
	public void select_patient_and_click_on_dropdown() throws InterruptedException {
		curbsidePage.selectPatient(Appointment.patientId, Appointment.apptId);
		curbsidePage.clickOnSelectedPatientDropdown(Appointment.patientId);
	}

	@Then("verify common fields and buttons are available for every patient details")
	public void verify_common_fields_and_buttons_are_available_for_every_patient_details() {
		assertTrue(curbsidePage.visibilityOfSendMessageDropdown());
		assertEquals(curbsidePage.visibilityOfDefaultMessage(Appointment.patientId), "Select Message",
				"Default message was not same");
		assertEquals(curbsidePage.visibilityOfParkingLotMsgInSendMsg(Appointment.patientId),
				"Wait in the parking lot until we send you a message to come in.", "Parking lot was not same");
		assertEquals(curbsidePage.visibilityOfInsuranceInstMsg(Appointment.patientId),
				"We will call you shortly to collect your insurance information.", "Insurance message was not same");
		assertEquals(curbsidePage.visibilityOfComeInOfficeMsg(Appointment.patientId), "Come in the office now.",
				"Come in the office now message was not same");
		assertEquals(curbsidePage.visibilityOfOtherMsg(Appointment.patientId), "Other", "Other message was not same");
		assertTrue(curbsidePage.visibilityOfSendButton(Appointment.patientId));
		assertTrue(curbsidePage.visibilityOfHistory(Appointment.patientId));
		assertTrue(curbsidePage.visibilityOfCheckinButton());
	}

	@When("sent message from dropdown")
	public void sent_message_from_dropdown() throws InterruptedException {
		curbsidePage.selectPatient(Appointment.patientId, Appointment.apptId);
		curbsidePage.clickOnSelectedPatientDropdown(Appointment.patientId);
		log("Sent message : " + curbsidePage.sendMsgFromDropdown(Appointment.patientId));
	}

	@When("click on the history link")
	public void click_on_the_history_link() throws InterruptedException {
		curbsidePage.clickOnHistoryLink(Appointment.patientId);
	}

	@Then("verify on curbside notification logs popup patient name,date,time,message and medium to be displayed")
	public void verify_on_curbside_notification_logs_popup_patient_name_date_time_message_and_medium_to_be_displayed()
			throws InterruptedException {
		assertTrue(curbsidePage.visibilityOfCurbsideNotificationLogsPopup());
		assertTrue(curbsidePage.visibilityOfIconOnCurbsideNotificationLogsPopup());
		log("Patient Name on curbside notification popup"
				+ curbsidePage.patientNameOnOnCurbsideNotificationLogsPopup());
		assertEquals(curbsidePage.patientNameOnOnCurbsideNotificationLogsPopup(),
				propertyData.getProperty("notification.log.popup.patient.name"), "Patient name was not match");
		assertTrue(curbsidePage.visibilityOfDateOnCurbsideNotifLogPopup());
		assertTrue(curbsidePage.visibilityOfTimeOnCurbsideNotifLogPopup());
		String lastSendMessage = curbsidePage.getLastSendMessage(Appointment.patientId);
		log("Last Send Message " + lastSendMessage);
		assertEquals(curbsidePage.getMessageFromCurbsideNotifLogPopup(),
				"wait in the parking lot until we send you a message to come in.", "Meassge was not match");
		assertTrue(curbsidePage.visibilityOfEmailAndTextOnCurbsideNotifLogPopup());
		curbsidePage.closeCurbsideNotifLogPopup();
	}

	@Then("select end time of previous day in end time filter and verify previous day date is disable in curbside check in")
	public void select_end_time_of_previous_day_in_end_time_filter_and_verify_previous_day_date_is_disable_in_curbside_check_in()
			throws InterruptedException {
		String endDate = curbsidePage.selectOneDayBeforeDate("22", "11.59 PM");
		assertNotEquals(endDate, curbsidePage.getCurrentEndDateAndTime(), "End date and time is match");
		log("End date and Time not matched");
		assertEquals(curbsidePage.getDesectedEndDateColor(), "#cccccc", "color was nor same");
	}

	@Then("verify messages list should be displayed in send message dropdown")
	public void verify_messages_list_should_be_displayed_in_send_message_dropdown() {
		assertTrue(curbsidePage.visibilityOfSendMessageDropdown());
		assertEquals(curbsidePage.visibilityOfDefaultMessage(Appointment.patientId), "Select Message",
				"Default message was not same");
		assertEquals(curbsidePage.visibilityOfParkingLotMsgInSendMsg(Appointment.patientId),
				"Wait in the parking lot until we send you a message to come in.", "Parking lot was not same");
		assertEquals(curbsidePage.visibilityOfInsuranceInstMsg(Appointment.patientId),
				"We will call you shortly to collect your insurance information.", "Insurance message was not same");
		assertEquals(curbsidePage.visibilityOfComeInOfficeMsg(Appointment.patientId), "Come in the office now.",
				"Come in the office now message was not same");
		assertEquals(curbsidePage.visibilityOfOtherMsg(Appointment.patientId), "Other", "Other message was not same");
	}

	@When("switch on appointment dashboard and Select all appointment")
	public void switch_on_appointment_dashboard_and_select_all_appointment() throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		apptPage.selectAllCheckboxes();
	}

	@Then("verify after selecting all appointment ribbon message should be display as per expected")
	public void verify_after_selecting_all_appointment_ribbon_message_should_be_display_as_per_expected() {
		assertTrue(apptPage.visibilityBannerMessage());
		assertTrue(apptPage.visibilityOfBannerMessageBaseOnFilter());
	}

	@When("enter timing and timing unit only for Days for {string} and click on save button")
	public void enter_timing_and_timing_unit_only_for_days_for_and_click_on_save_button(String deliveryMethod)
			throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.day1 = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(1, "Days", Appointment.day1);
		Appointment.day2 = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(2, "Days", Appointment.day2);
		Appointment.day3 = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.day3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow only days")
	public void verify_system_should_allow_only_days() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getTimingUnitText(),
				Appointment.day1 + ", " + Appointment.day2 + ", " + Appointment.day3, "Days Timing unit was not match");
	}

	@When("enter timing and timing unit only for Hours for {string} and click on save button")
	public void enter_timing_and_timing_unit_only_for_hours_for_and_click_on_save_button(String deliveryMethod)
			throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.hour1 = notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hour1);
		Appointment.hour2 = notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.hour2);
		Appointment.hour3 = notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hour3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow only hours")
	public void verify_system_should_allow_only_hours() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getTimingUnitText(),
				Appointment.hour1 + ", " + Appointment.hour2 + ", " + Appointment.hour3,
				"Hours Timing unit was not match");
	}

	@When("enter timing and timing unit only for Minutes for {string} and click on save button")
	public void enter_timing_and_timing_unit_only_for_minutes_for_and_click_on_save_button(String deliveryMethod)
			throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.minute1 = notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(1, "Minutes", Appointment.minute1);
		Appointment.minute2 = notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minute2);
		Appointment.minute3 = notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(3, "Minutes", Appointment.minute3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow only Minutes")
	public void verify_system_should_allow_only_minutes() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimingUnitText(),
				Appointment.minute1 + ", " + Appointment.minute2 + ", " + Appointment.minute3,
				"Minutes Timing unit was not match");
	}

	@When("enter timing and timing unit for hours,minutes,day,day for {string} and click on save button")
	public void enter_timing_and_timing_unit_for_hours_minutes_day_day_for_and_click_on_save_button(
			String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.addFourthTimingAndTimingUnit();
		Appointment.hours = notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hours);
		Appointment.minutes = notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minutes);
		Appointment.day1 = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.day1);
		Appointment.day2 = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(4, "Days", Appointment.day2);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow hours,minutes,day,day timing")
	public void verify_system_should_allow_hours_minutes_day_day_timing() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimeUnitTextForDays(), Appointment.day1 + ", " + Appointment.day2,
				"Days Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForHours(), Appointment.hours, "Hours Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForMinutes(), Appointment.minutes, "Minutes Timing unit was not match");
	}

	@Then("click on edit for email and remove one cadence and save")
	public void click_on_edit_for_email_and_remove_one_cadence_and_save() throws InterruptedException {
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		log("From Appointment reminders open edit section for Email");
		scrollAndWait(0, 2000, 5000);
		notifPage.clickApptReminderEmailHamburgerButton();
		notifPage.clickApptReminderEmailEditButton();
		notifPage.clickOnRemoveTiming();
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@When("enter timing and timing unit for day,hours,hours,minutes for {string} and click on save button")
	public void enter_timing_and_timing_unit_for_day_hours_hours_minutes_for_and_click_on_save_button(
			String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.addFourthTimingAndTimingUnit();
		Appointment.days = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(1, "Days", Appointment.days);
		Appointment.hour1 = notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.hour1);
		Appointment.hour2 = notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hour2);
		Appointment.minutes = notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(4, "Minutes", Appointment.minutes);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow day,hours,minutes,minutes timing")
	public void verify_system_should_allow_day_hours_minutes_minutes_timing() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimeUnitTextForDays(), Appointment.days, "Days Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForHours(), Appointment.hour1 + ", " + Appointment.hour2,
				"Hours Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForMinutes(), Appointment.minutes, "Minutes Timing unit was not match");
	}

	@When("enter timing and timing unit for minutes,minutes,hours,day for {string} and click on save button")
	public void enter_timing_and_timing_unit_for_minutes_minutes_hours_day_for_and_click_on_save_button(
			String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.addFourthTimingAndTimingUnit();
		Appointment.minute1 = notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(1, "Minutes", Appointment.minute1);
		Appointment.minute2 = notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minute2);
		Appointment.hours = notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hours);
		Appointment.days = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(4, "Days", Appointment.days);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow minutes,minutes,hours,day timing")
	public void verify_system_should_allow_minutes_minutes_hours_day_timing() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimeUnitTextForDays(), Appointment.days, "Days Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForHours(), Appointment.hours, "Hours Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForMinutes(), Appointment.minute1 + ", " + Appointment.minute2,
				"Minutes Timing unit was not match");
	}

	@Then("verify system should allow only days for SMS")
	public void verify_system_should_allow_only_days_for_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getTimingUnitTextForSMS(),
				Appointment.day1 + ", " + Appointment.day2 + ", " + Appointment.day3, "Days Timing unit was not match");
	}

	@Then("verify system should allow only hours SMS")
	public void verify_system_should_allow_only_hours_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getTimingUnitTextForSMS(),
				Appointment.hour1 + ", " + Appointment.hour2 + ", " + Appointment.hour3,
				"Hours Timing unit was not match");
	}

	@Then("verify system should allow only Minutes SMS")
	public void verify_system_should_allow_only_minutes_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimingUnitTextForSMS(),
				Appointment.minute1 + ", " + Appointment.minute2 + ", " + Appointment.minute3,
				"Minutes Timing unit was not match");
	}

	@Then("verify system should allow hours,minutes,day,day timing for SMS")
	public void verify_system_should_allow_hours_minutes_day_day_timing_for_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimeUnitTextForSMS(), Appointment.day1 + ", " + Appointment.day2,
				"Days Timing unit was not match");
		assertEquals(notifPage.getHoursTimeUnitTextForSMS(), Appointment.hours, "Hours Timing unit was not match");
		assertEquals(notifPage.getMinutesTimeUnitTextForSMS(), Appointment.minutes,
				"Minutes Timing unit was not match");
	}

	@Then("click on edit for text and remove one cadence and save")
	public void click_on_edit_for_text_and_remove_one_cadence_and_save() throws InterruptedException {
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		log("From Appointment reminders open edit section for text");
		scrollAndWait(0, 2000, 5000);
		notifPage.clickApptReminderSmsHamburgerButton();
		notifPage.clickApptReminderSmsEditButton();
		notifPage.clickOnRemoveTiming();
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow day,hours,minutes,minutes timing for SMS")
	public void verify_system_should_allow_day_hours_minutes_minutes_timing_for_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimeUnitTextForSMS(), Appointment.days, "Days Timing unit was not match");
		assertEquals(notifPage.getHoursTimeUnitTextForSMS(), Appointment.hour1 + ", " + Appointment.hour2,
				"Hours Timing unit was not match");
		assertEquals(notifPage.getMinutesTimeUnitTextForSMS(), Appointment.minutes,
				"Minutes Timing unit was not match");
	}

	@Then("verify system should allow minutes,minutes,hours,day timing for SMS")
	public void verify_system_should_allow_minutes_minutes_hours_day_timing_for_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimeUnitTextForSMS(), Appointment.days, "Days Timing unit was not match");
		assertEquals(notifPage.getHoursTimeUnitTextForSMS(), Appointment.hours, "Hours Timing unit was not match");
		assertEquals(notifPage.getMinutesTimeUnitTextForSMS(), Appointment.minute1 + ", " + Appointment.minute2,
				"Minutes Timing unit was not match");
	}

	@When("user on curbside checkin tab and clear all appointments")
	public void user_on_curbside_checkin_tab_and_clear_all_appointments() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
		log("User is on Curbside check in tab");
		notifPage.checkingCheckinButton();
		log("Curbside button is disable");
		curbsidePage.selectAllAppointment();
		notifPage.clickOnCheckinButton();
		log("Clear all appointment");
	}

	@When("schedule multiple appointments and confirm their appointment")
	public void schedule_multiple_appointments_and_confirm_their_appointment()
			throws NullPointerException, IOException {
		for (int i = 0; i <= 9; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);

			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
		driver.navigate().refresh();
	}

	@Then("verify checkin button fuctionality after one patient gets checkin")
	public void verify_checkin_button_fuctionality_after_one_patient_gets_checkin() throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		notifPage.selectOnePatient();
		notifPage.checkingCheckinButton();
		log("Check in button is enable");
		assertEquals(notifPage.getCheckinButtonText(), "Check-In (1)", "Checkin button text is not match");
		log("Checkin button text:-  " + notifPage.getCheckinButtonText());
		notifPage.clickOnCheckinButton();
		notifPage.checkingCheckinButton();
		log("Check in button is disable");
		assertEquals(curbsidePage.countOfCurbsideCheckinPatient(), 9,
				"Count of Curbside checkin patient was not match");

	}

	@Then("verify checkin button fuctionality after two patient gets checkin")
	public void verify_checkin_button_fuctionality_after_two_patient_gets_checkin() throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		curbsidePage.selectTwoPatient();
		notifPage.checkingCheckinButton();
		log("Check in button is enable");
		assertEquals(notifPage.getCheckinButtonText(), "Check-In (2)", "Checkin button text is not match");
		log("Checkin button text:-  " + notifPage.getCheckinButtonText());
		notifPage.clickOnCheckinButton();
		notifPage.checkingCheckinButton();
		log("Check in button is disable");
		assertEquals(curbsidePage.countOfCurbsideCheckinPatient(), 8,
				"Count of Curbside checkin patient was not match");
	}

	@Then("verify checkin button fuctionality after all patient gets checkin")
	public void verify_checkin_button_fuctionality_after_all_patient_gets_checkin() throws InterruptedException {
		scrollAndWait(0, -500, 15000);
		curbsidePage.selectAllAppointment();
		notifPage.checkingCheckinButton();
		log("Check in button is enable");
		assertEquals(notifPage.getCheckinButtonText(), "Check-In (10)", "Checkin button text is not match");
		log("Checkin button text:-  " + notifPage.getCheckinButtonText());
		notifPage.clickOnCheckinButton();
		Thread.sleep(10000);
		notifPage.checkingCheckinButton();
		log("Check in button is disable");
		assertEquals(curbsidePage.countOfCurbsideCheckinPatient(), 0,
				"Count of Curbside checkin patient was not match");
	}

	@Then("verify select and deselect functionality of all checkbox")
	public void verify_select_and_deselect_functionality_of_all_checkbox() throws InterruptedException {
		assertFalse(curbsidePage.getVisibilityOfAllCheckbox());
		Thread.sleep(10000);
		curbsidePage.selectAllAppointment();
		notifPage.checkingCheckinButton();
		log("Check in button is enabled");
		assertEquals(notifPage.getCheckinButtonText(), "Check-In (10)", "Checkin button text is not match");
		assertTrue(curbsidePage.getVisibilityOfAllCheckbox());
		curbsidePage.deselectAllAppointment();
		assertFalse(curbsidePage.getVisibilityOfAllCheckbox());
	}

	@Then("verify select functionality of individual checkbox")
	public void verify_select_functionality_of_individual_checkbox() throws InterruptedException {
		Thread.sleep(10000);
		curbsidePage.selectMultiplePatients(8);
		assertEquals(notifPage.getCheckinButtonText(), "Check-In (3)", "Checkin button text is not match");
		curbsidePage.getVisibilityOfMultiplePatient(8);
		log("Multiple patient got selected ");
	}

	@When("schedule a appointment without phone number")
	public void schedule_a_appointment_without_phone_number() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(5);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, null, propertyData.getProperty("unsubscribed.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}

	@When("go to on yopmail and from mail unsubscribe a patient")
	public void go_to_on_yopmail_and_from_mail_unsubscribe_a_patient() throws InterruptedException {
		YopMail yopMail = new YopMail(driver);
		String unsubscribeMessage = yopMail.unsubscribeEmail(propertyData.getProperty("unsubscribed.email"),
				propertyData.getProperty("appt.email.subject"));
		assertEquals(unsubscribeMessage,
				"You will no longer receive emails from PreCheck and reminder services. Please contact your practice if you wish to opt back in.",
				"Message was nor correct");
	}

	@When("I switch on practice provisioning url")
	public void I_switch_on_practice_provisioning_url() {
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}

	@When("I select patient and send broadcast message from appointment dashboard")
	public void I_select_patient_and_send_broadcast_message_from_appointment_dashboard() throws Exception {
		mainPage.clickOnAppointmentsTab();
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		log("banner meassage :" + apptPage.broadcastBannerMessage());
	}

	@Then("verify banner status should come as failure")
	public void verify_banner_status_should_come_as_failure() throws NullPointerException, IOException {
		assertEquals(apptPage.broadcastMessageStatus(), "Broadcast Message Sent. 0 successful. 1 failed.",
				"Message was nor correct");
		log("Delete Subscription Data");
		Response response = subsManager.deleteAllSubscriptionDataUsingEmailId(
				propertyData.getProperty("baseurl.mf.notif.subscription.manager"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()),
				propertyData.getProperty("unsubscribed.email"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@When("I send message to selected patient")
	public void I_send_message_to_selected_patient() throws InterruptedException {
		curbsidePage.selectMessageFromDropdown(Appointment.patientId,
				propertyData.getProperty("curbside.checkin.message.parking.lot"));
		curbsidePage.clickOnSendButtonOfSelectedPatient(Appointment.patientId);
	}

	@Then("verify last message send succesfully from curbside checkin")
	public void verify_last_message_send_succesfully_from_curbside_checkin() {
		assertEquals("Last Message: " + propertyData.getProperty("curbside.checkin.message.parking.lot"),
				curbsidePage.getSentMessageSelectedPatient(Appointment.patientId), "Message was not correct");
	}

	@When("I select patient and click on check in")
	public void I_select_patient_and_click_on_check_in() {
		curbsidePage.selectPatient(Appointment.patientId, Appointment.apptId);
		curbsidePage.clickOnCheckInButton();
	}

	@When("I switch to the appointment dashboard tab")
	public void I_switch_to_the_appointment_dashboard_tab() throws InterruptedException {
		scrollAndWait(0, 100, 5000);
		mainPage.clickOnAppointmentsTab();
	}

	@Then("verify check in patient should be added in the appointments dashboard")
	public void verify_check_in_patient_should_be_added_in_the_appointments_dashboard() {
		apptPage.selectPatient(Appointment.patientId, Appointment.patientId);
		assertTrue(apptPage.visibilityPatient(Appointment.patientId));
	}

	@When("I schedule {int} appointments and select patients")
	public void i_schedule_appointments_and_select_patients(int appt) throws Exception {
		for (int i = 0; i < appt; i++) {
			log("Schedule multiple Appointments");
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			log("schedule more than 10 an appointments ");
			Response resonse = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("mf.apt.scheduler.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(resonse.statusCode(), 200);
			apptPage.clickOnRefreshTab();
			apptPage.selectPatients(Appointment.patientId, Appointment.apptId);
		}
	}

	@When("I select broadcast message button from action dropdown")
	public void i_select_broadcast_message_button_from_action_dropdown() throws Exception {
		scrollAndWait(0, -2000, 5000);
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
	}

	@Then("verify broadcast message UI template visibility and when broadcast message entered in english and spanish footer note character count get decremented")
	public void verify_broadcast_message_ui_template_visibility_and_when_broadcast_message_entered_in_english_and_spanish_footer_note_character_count_get_decremented()
			throws NullPointerException, Exception {
		assertEquals(apptPage.getBroadcastMessagePopupText(), "Broadcast Message",
				"Broadcast Message text was not match");
		assertEquals(apptPage.getBroadcastMessagePopupInstr(),
				"Use the space below to write a message to be sent to the [5] patients selected based on these filters.",
				"Broadcast Message instruction was not match");
		assertEquals(apptPage.getBroadcastMessagePopupStartTime(),
				"Start Time: " + apptPage.currentDate() + " 12:00 AM", "Start time was not match");
		assertEquals(apptPage.getBroadcastMessagePopupEndTime(), "End Time: " + apptPage.futureDate(13) + " 11:59 PM",
				"Endtart time was not match");
		assertEquals(apptPage.getBroadcastMessagePopupProvider(), "Provider: All", "All Provider was not match ");
		assertEquals(apptPage.getBroadcastMessagePopupLoaction(), "Location: All", "All Provider was not match");
		assertEquals(apptPage.getBroadcastMsgPopupEnglishLabel(), "Broadcast Message (English)",
				"Broadcast Message English label was not match");
		assertEquals(apptPage.getBroadcastMsgPopupSpanishLabel(), "Broadcast Message (Spanish)",
				"Broadcast Message Spanish label was not match");
		assertEquals(apptPage.getBroadcastMsgPopupConfirmThisMsgLabel(), "Confirm this message",
				"Confirm this message label was not match");
		assertFalse(apptPage.checkConfirmThisMsgCheckbox());
		assertEquals(apptPage.getBroadcastMsgPopupCloseButton(), "Close", "Close Button text was not match");
		apptPage.hoverOnCloseButton();
		log("After hover check the visibility of close");
		assertTrue(apptPage.closeButtonFromBroadcastMsgPopup());
		assertEquals(apptPage.getBroadcastMsgPopupSendButton(), "Send Message (5)", "Send Message text was not match");
		assertFalse(apptPage.sendButtonFromBroadcastMsgPopup());
		assertTrue(apptPage.visibilityOfBroadcastMsgCrossButton());
		assertEquals(apptPage.getBroadcastMsgPopupEnIncrDecrChar(), "450 characters remaining.",
				"450 characters remaining. text for english text box was not match");
		assertEquals(apptPage.getBroadcastMsgPopupEsIncrDecrChar(), "450 characters remaining.",
				"450 characters remaining. text for sapnish text box was not match");
		log("Enter message in English and Spanish");
		apptPage.EnterBroadcastMessageEnEs(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		assertNotEquals(apptPage.getBroadcastMsgPopupEnIncrDecrChar(), "450 characters remaining.");
		assertNotEquals(apptPage.getBroadcastMsgPopupEsIncrDecrChar(), "450 characters remaining.");
		apptPage.clickOnSendForBroadcastMsg();
	}

	@When("I schedule {int} appointments")
	public void i_schedule_appointments(int appt) throws NullPointerException, IOException, InterruptedException {
		for (int i = 0; i < appt; i++) {
			log("Schedule multiple new Appointments");
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			log("schedule more than 10 an appointments ");
			Response resonse = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("mf.apt.scheduler.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(resonse.statusCode(), 200);
		}
		apptPage.clickOnRefreshTab();
	}

	@When("I select all patients")
	public void i_select_all_patients() throws InterruptedException {
		apptPage.selectAllCheckboxes();
	}

	@When("verify after closing banner message all selected appointments are deselected")
	public void verify_after_closing_banner_message_all_selected_appointments_are_deselected() {
		assertTrue(apptPage.visibilitySelectedPatientBanner());
		apptPage.closeSelectedPatientBanner();
		assertFalse(apptPage.visibilitySelectedPatientBanner());
	}

	@When("I schedule a new appointment")
	public void i_schedule_a_new_appointment() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		log("schedule more than 10 an appointments ");
		Response resonse = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(resonse.statusCode(), 200);
	}

	@When("I select patient from appointment dashboard and send broadcast message")
	public void I_select_patient_from_appointment_dashboard_and_send_broadcast_message() throws Exception {
		mainPage.clickOnAppointmentsTab();
		apptPage.clickOnRefreshTab();
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
	}

	@When("I click on selected patient broadcast message for email and get message")
	public void I_click_on_selected_patient_broadcast_message_for_email() throws InterruptedException {
		Thread.sleep(10000);
		apptPage.clickOnBroadcastEmailForSelectedPatient(Appointment.patientId, Appointment.apptId);
		Appointment.broadcastMessage = apptPage.getBroadcastMessage();
		log("Sent broadcast message: " + Appointment.broadcastMessage);
	}

	@When("I reschedule an appointment")
	public void I_reschedule_an_appointment() throws NullPointerException, IOException {
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		log("schedule more than 10 an appointments ");
		Response resonse = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(resonse.statusCode(), 200);
		driver.navigate().refresh();
	}

	@Then("verify old broadcast message sent should not be shown")
	public void verify_old_broadcast_message_sent_should_not_be_shown() throws InterruptedException {
		scrollAndWait(0, -1000, 5000);
		apptPage.clickOnBroadcastEmailForSelectedPatient(Appointment.patientId, Appointment.apptId);
		assertNotEquals(Appointment.broadcastMessage, apptPage.getBroadcastMessage(), "old broadcast message shown");
	}

	@When("I click on Notifications tab from Setting tab and disable curbside remainder checkbox")
	public void i_click_on_notifications_tab_from_setting_tab_and_disable_curbside_remainder_checkbox()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.disableCurbsideCheckinRemCheckbox();
		log("Disable Curbside Checkin Reminder Checkbox");
		notifPage.saveNotification();
	}

	@When("I schedule a new appointment and confirm arrival")
	public void i_schedule_a_new_appointment_and_confirm_arrival() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		log("schedule more than 10 an appointments ");
		Response resonse = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("curbside.checkin.mail")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(resonse.statusCode(), 200);
	}

	@Then("verify curbside reminder is not receive to patient")
	public void verify_curbside_reminder_is_not_receive_to_patient() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertFalse(yopMail.isMessageInInbox(propertyData.getProperty("curbside.checkin.mail"),
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("curbside.checkin.mail.title"), 5));
	}

	@Then("from notifications tab in Setting tab and enable curbside remainder checkbox")
	public void from_notifications_tab_in_setting_tab_and_enable_curbside_remainder_checkbox()
			throws InterruptedException {
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.enableCurbsideCheckinRemCheckbox();
		log("Enbled Curbside Checkin Reminder Checkbox");
		notifPage.saveNotification();
	}

	@When("I enabled curbside remainder checkbox from notifications tab in Setting tab")
	public void i_enabled_curbside_remainder_checkbox_from_notifications_tab_in_setting_tab()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.enableCurbsideCheckinRemCheckbox();
	}

	@When("I schedule a new appointment after one hour of current time")
	public void i_schedule_a_new_appointment_after_one_hour_of_current_time() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(60);
		log("schedule more than 10 an appointments ");
		Response resonse = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("curbside.checkin.mail")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(resonse.statusCode(), 200);
	}

	@Then("I disable curbside remainder checkbox after five minutes of current time")
	public void i_disable_curbside_remainder_checkbox_after_five_minutes_of_current_time() throws InterruptedException {
		log("After five minutes disable curbside remainder checkbox");
		Thread.sleep(300000);
		notifPage.disableCurbsideCheckinRemCheckbox();
		log("Disable Curbside Checkin Reminder Checkbox");
		notifPage.saveNotification();
	}

	@Then("verify curbside reminder is receive to patient")
	public void verify_curbside_reminder_is_receive_to_patient() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox(propertyData.getProperty("curbside.checkin.mail"),
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("curbside.checkin.mail.title"), 5));
	}

	@When("I schedule an appointment and have confirmed there arrival")
	public void i_schedule_an_appointment_and_have_confirmed_there_arrival() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(60);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("precheck.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}

	@When("I click on Curbside check-in tab and select patient")
	public void i_click_on_curbside_check_in_tab_and_select_patient() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
		log("User is on Curbside check in tab");
		curbsidePage.selectPatient(Appointment.patientId, Appointment.apptId);
	}

	@When("I click on dropdown and select {string} option")
	public void i_click_on_dropdown_and_select_option(String other) throws InterruptedException {
		curbsidePage.selectOtherOptionFromDropdown(Appointment.apptId, other);
	}

	@Then("verify other message is able to send from curbside checkin in drop down list")
	public void verify_other_message_is_able_to_send_from_curbside_checkin_in_drop_down_list()
			throws NullPointerException, InterruptedException {
		String lastMessage = curbsidePage.sendCustomizedMessage(Appointment.apptId,
				propertyData.getProperty("customized.message.from.other.option"));
		log("Send customized message from other option");
		assertEquals(lastMessage, "Last Message: " + propertyData.getProperty("customized.message.from.other.option"),
				"Messsage was not same");
	}

	@When("I send other message from curbside checkin in drop down list")
	public void i_send_other_message_from_curbside_checkin_in_drop_down_list() throws InterruptedException {
		curbsidePage.selectOtherOptionFromDropdown(Appointment.apptId, "Other");
		String lastMessage = curbsidePage.sendCustomizedMessage(Appointment.apptId,
				propertyData.getProperty("customized.message.from.other.option"));
		log("Send customized message from other option");
		assertEquals(lastMessage, "Last Message: " + propertyData.getProperty("customized.message.from.other.option"),
				"Messsage was not same");
	}

	@Then("verify practice staff is able to send another message by using other option from the drop down list")
	public void verify_practice_staff_is_able_to_send_another_message_by_using_other_option_from_the_drop_down_list()
			throws InterruptedException {
		curbsidePage.selectOtherOptionFromDropdown(Appointment.apptId, "Other");
		String lastMessage = curbsidePage.sendCustomizedMessage(Appointment.apptId,
				propertyData.getProperty("customized.message.from.other.option"));
		log("Send customized message from other option");
		assertEquals(lastMessage, "Last Message: " + propertyData.getProperty("customized.message.from.other.option"),
				"Messsage was not same");
	}

	@When("I switch on appointment dashboard")
	public void i_switch_on_appointment_dashboard() {
		mainPage.clickOnAppointmentsTab();
		log("Verify focus on Appointments page-- " + apptPage.getApptPageTitle());
		assertTrue(apptPage.getApptPageTitle().contains(propertyData.getProperty("appointment.page")));
	}

	@Then("verify System should show default date and time on appointment dashboard")
	public void verify_system_should_show_default_date_and_time_on_appointment_dashboard() throws InterruptedException {
		log("Start Time from portal:" + apptPage.getStartTime());
		log("Current date : " + apptPage.currentDate());
		assertEquals(apptPage.getStartTime(), apptPage.currentDate() + " 12:00 AM",
				"Current date and time was not match");

		log("End Time from portal:" + apptPage.getEndTime());
		log("Two week future date from current date: " + apptPage.futureDate(13));
		assertEquals(apptPage.getEndTime(), apptPage.futureDate(13) + " 11:59 PM",
				"Current date and time was not match");
	}

	@When("I select end date as current date at two AM and select start date as current date after two AM")
	public void i_select_end_date_as_current_date_at_two_am_and_select_start_date_as_current_date_after_two_am()
			throws InterruptedException {
		apptPage.selectCurrentDateInEndDateFilter();
		apptPage.selectEndTime("2:00 AM");
		apptPage.clickOnStartDateFilter();
		apptPage.selectStartTime("2:00 AM");
	}

	@Then("verify system should not allow user to select start time after two AM for same day and after two AM slots should be disable")
	public void verify_system_should_not_allow_user_to_select_start_time_after_two_am_for_same_day_and_after_two_am_slots_should_be_disable()
			throws InterruptedException {
		apptPage.clickOnStartDateFilter();
		assertFalse(apptPage.selectStartDate());
		apptPage.selectStartTime("3:00 AM");
		log("Start Time from portal:" + apptPage.getStartTime());
		log("Current date : " + apptPage.currentDate());
		assertNotEquals(apptPage.getStartTime(), apptPage.currentDate() + " 3:00 AM",
				"Current date and time was not match");
	}

	@When("I select start date as current date at three AM and select end date as current date before three AM")
	public void i_select_start_date_as_current_date_at_three_am_and_select_end_date_as_current_date_before_three_am()
			throws InterruptedException {
		apptPage.clickOnStartDateFilter();
		apptPage.selectStartTime("3:00 AM");
		apptPage.selectCurrentDateInEndDateFilter();
		apptPage.selectEndTime("2:00 AM");
	}

	@Then("verify system should not allow user to select end time before three AM for same day and before three AM slots should be disable")
	public void verify_system_should_not_allow_user_to_select_end_time_before_three_am_for_same_day_and_before_three_am_slots_should_be_disable()
			throws InterruptedException {
		apptPage.clickOnEndTimeFilter();
		assertFalse(apptPage.selectEndDate());
		apptPage.selectEndTime("2:00 AM");
		log("End Time from portal:" + apptPage.getEndTime());
		assertNotEquals(apptPage.getEndTime(), apptPage.getEndTime() + " 2:00 AM",
				"Current date and time was not match");

	}

	@When("I select start date and time and navigate on fifth page")
	public void i_select_start_date_and_time_and_navigate_on_fifth_page() throws InterruptedException {
		apptPage.selectStartDate(8);
		apptPage.selectRequiredPage(5);
	}

	@When("I select a appointment and send manual reminder")
	public void i_select_a_appointment_and_send_manual_reminder() throws InterruptedException {
		apptPage.selectFirstPatient();
		scrollAndWait(0, -3000, 10000);
		apptPage.clickOnActions();
		apptPage.clickOnSendReminder();
	}

	@When("I click on refresh button from apt dashboard and lands on same page")
	public void i_click_on_refresh_button_from_apt_dashboard_and_lands_on_same_page() throws InterruptedException {
		apptPage.clickOnRefreshTab();
	}

	@Then("I verify that I am still on page five and arrows are working")
	public void I_verify_that_I_am_still_on_page_five_and_arrows_are_working() throws InterruptedException {
		assertEquals(apptPage.jumpToNextPage(), "6", "Not navigate to next page");
		apptPage.jumpToPreviousPage();
		log("again jump no previous page");
		String previousPage = apptPage.jumpToPreviousPage();
		assertEquals(previousPage, "4", "Not navigate to next page");
	}
	
	@When("I enable Broadcast messaging checkbox from setting in notifications dashboard")
	public void i_enable_broadcast_messaging_checkbox_from_setting_in_notifications_dashboard()
			throws InterruptedException {
		mainPage.clickOnSettingTab();
		log("verify user should be on General setting dashboard");
		GeneralPage generalPage = GeneralPage.getGeneralPage();
		assertTrue(generalPage.generalSettingTitle().contains(propertyData.getProperty("general.setting.title")));
		assertTrue(generalPage.manageSolutionTab().contains(propertyData.getProperty("manage.solution.board")));
		log("User on setting tab");
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.braodcastMessagingCheckbox();
		log("Enable broadcast messaging checkbox");
		notifPage.saveNotification();
	}

	@When("I select past start date and select all appointment")
	public void i_select_past_start_date_and_select_all_appointment() throws InterruptedException {
		apptPage.selectStartDate(9);
		apptPage.selectStartTime("12:00 AM");
		apptPage.selectAllCheckboxes();
	}

	@Then("I verify ribbon message will be display as per expected")
	public void i_verify_ribbon_message_will_be_display_as_per_expected() {
		String bannerMessage = apptPage.getBannerMessageSelectAppAppt();
		log("Banner message :" + bannerMessage);
		assertTrue(apptPage.getBannerMessageBaseOnAppt());
		assertTrue(apptPage.visibilityOfBannerMessage());
	}

	@When("I select past start date")
	public void i_select_past_start_date() throws InterruptedException {
		apptPage.selectStartDate(9);
		apptPage.selectStartTime("12:00 AM");
	}

	@When("I select {int} patients records from first page")
	public void i_select_patients_records_from_first_page(int patients) throws InterruptedException {
		assertEquals(apptPage.getPageNo(), "1", "Page number was not correct");
		scrollAndWait(0, -2500, 5000);
		apptPage.selectMultipleAppointments(patients);
		log("select multiple appointment from first Page");
	}

	@When("I select {int} patient records from second page")
	public void i_select_patient_records_from_second_page(int patients) throws InterruptedException {
		assertEquals(apptPage.jumpToNextPage(2), "2", "Page number was not correct");
		apptPage.selectMultipleAppointments(patients);
		log("select multiple appointment from second Page");
	}

	@When("I select {int} patient records from third page")
	public void i_select_patient_records_from_third_page(int patients) throws InterruptedException {
		assertEquals(apptPage.jumpToNextPage(3), "3", "Page number was not correct");
		apptPage.selectMultipleAppointments(patients);
		log("select multiple appointment from third Page");
	}

	@When("I select {int} patient records from fourth page")
	public void i_select_patient_records_from_fourth_page(int patients) throws InterruptedException {
		assertEquals(apptPage.jumpToNextPage(4), "4", "Page number was not correct");
		apptPage.selectMultipleAppointments(patients);
		log("select multiple appointment from fourth Page");
	}

	@Then("I verify on appointments dashboard multiple records are selected from different pages then it will not show the ribbon on top of the page")
	public void i_verify_on_appointments_dashboard_multiple_records_are_selected_from_different_pages_then_it_will_not_show_the_ribbon_on_top_of_the_page()
			throws InterruptedException {
		scrollAndWait(0, -2500, 5000);
		assertFalse(apptPage.getBannerMessageBaseOnAppt());
		assertFalse(apptPage.visibilityOfBannerMessage());
		log("select multiple appointment from first Page");
	}

	@When("I switch on curbside checkin tab")
	public void i_switch_on_curbside_checkin_tab() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
		log("User is on Curbside check in tab");
		notifPage.checkingCheckinButton();
		log("Curbside button is disable");
		Thread.sleep(5000);
		curbsidePage.selectAllAppointment();
		notifPage.clickOnCheckinButton();
		log("Clear all appointment");
	}

	@When("I schedule {int} appointment and confirmed their arrival")
	public void i_schedule_appointment_and_confirmed_their_arrival(int appt)
			throws NullPointerException, IOException, InterruptedException {
		for (int i = 0; i < appt; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);

			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
		driver.navigate().refresh();
	}

	@When("{int} rows should be display on curbside checkin page and notification icon updated")
	public void rows_should_be_display_on_curbside_checkin_page_and_notification_icon_updated(int appt)
			throws InterruptedException {
		Thread.sleep(5000);;
		assertEquals(curbsidePage.countOfCurbsideCheckinPatient(), appt,
				"Count from curnside checkin page was not same");
		String count = String.valueOf(appt);
		assertEquals(curbsidePage.getNotificationCount(), count, "Count from notification page was not same");
	}

	@When("I switches to Appointmant dashboard")
	public void i_switches_to_appointmant_dashboard_and_schedule_one_appointments_and_confirmed_their_arrival()
			throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		log("Verify focus on Appointments page-- " + apptPage.getApptPageTitle());
		log("User is on Appointments tab");
	}

	@When("one notification update should be displayed in the notification icon on the top")
	public void one_notification_update_should_be_displayed_in_the_notification_icon_on_the_top()
			throws InterruptedException {
		scrollAndWait(0, -500, 10000);
		assertEquals(curbsidePage.getNotificationCount(), "6", "Count from notification page was not same");
	}

	@Then("I verify when switches to curbside checkin tab {int} row must be displayed without clicking on the notification icon on the top")
	public void i_verify_when_switches_to_curbside_checkin_tab_row_must_be_displayed_without_clicking_on_the_notification_icon_on_the_top(
			int appt) throws InterruptedException {
		assertTrue(apptPage.getApptPageTitle().contains(propertyData.getProperty("appointment.page")));
		mainPage.clickOnCurbsideTab();
		assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
		log("User is on Curbside check in tab");
		scrollAndWait(0, -500, 10000);
		assertEquals(curbsidePage.countOfCurbsideCheckinPatient(), appt,
				"Count from curnside checkin page was not same");
	}
	
	@When("I click on Curbside check-in tab")
	public void i_click_on_curbside_check_in_tab() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
		log("User is on Curbside check in tab");
		Thread.sleep(5000);
		curbsidePage.selectPatient(Appointment.patientId, Appointment.apptId);
	}
	
	@When("I select patient and click on dropdown")
	public void i_select_patient_and_click_on_dropdown() throws InterruptedException {
		curbsidePage.selectPatient(Appointment.patientId, Appointment.apptId);
		curbsidePage.clickOnSelectedPatientDropdown(Appointment.apptId);
	}
	
	@Then("I verify messages list should be displayed in send message dropdown")
	public void i_verify_messages_list_should_be_displayed_in_send_message_dropdown() {
		assertTrue(curbsidePage.visibilityOfSendMessageDropdown());
		assertEquals(curbsidePage.visibilityOfDefaultMessage(Appointment.apptId), "Select Message",
				"Default message was not same");
		assertEquals(curbsidePage.visibilityOfParkingLotMsgInSendMsg(Appointment.apptId),
				"Wait in the parking lot until we send you a message to come in.", "Parking lot was not same");
		assertEquals(curbsidePage.visibilityOfInsuranceInstMsg(Appointment.apptId),
				"We will call you shortly to collect your insurance information.", "Insurance message was not same");
		assertEquals(curbsidePage.visibilityOfComeInOfficeMsg(Appointment.apptId), "Come in the office now.",
				"Come in the office now message was not same");
		assertEquals(curbsidePage.visibilityOfOtherMsg(Appointment.apptId), "Other", "Other message was not same");
	}
	
	@When("from setting in notifications curbside check-in reminder checkbox is check")
	public void from_setting_in_notifications_curbside_check_in_reminder_checkbox_is_check() throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		notifPage.enableCurbsideCheckinRemCheckbox();
	}
	@When("I click on save button in notifications tab")
	public void i_click_on_save_button_in_notifications_tab() throws InterruptedException {
		notifPage.saveNotification();
		log("notifications saved");
	}
	@When("I schedule an appointment")
	public void i_schedule_an_appointment() throws NullPointerException, IOException {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		Appointment.patientId = String.valueOf(randamNo);
		Appointment.apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
		propertyData.getProperty("mf.apt.scheduler.practice.id"),
		payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
		propertyData.getProperty("mf.apt.scheduler.email")),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
		Appointment.apptId); 
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
		Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200); Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
		
	}
	@When("from curbside check-in filtration is done for location")
	public void from_curbside_check_in_filtration_is_done_for_location() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		log("user on curbside page");
		curbsidePage.clickOncurbsideCheckinLocationDropDown();
		log("user clicks on dropdown");
		curbsidePage.selectLocationinDropDown();
		log("user select the location");
	}
	@Then("I verify notification count get updated after arrival entry in appointment dashboard without refresh")
	public void i_verify_notification_count_get_updated_after_arrival_entry_in_appointment_dashboard_without_refresh() {
		mainPage.clickOnAppointmentsTab();
	    log("user on appointments page");
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is updated");
		
	}
	@When("I schedule an appointment for location L2")
	public void i_schedule_an_appointment_for_location_l2() throws NullPointerException, IOException {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		Appointment.patientId = String.valueOf(randamNo);
		Appointment.apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
		propertyData.getProperty("mf.apt.scheduler.practice.id"),
		payload.putAppointmentPayloadwithDifferentlocation(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
		propertyData.getProperty("mf.apt.scheduler.email")),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
		Appointment.apptId); 
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
		Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200); Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}
	@When("from curbside check-in tab filtration is done for location L1 when there is already arrival entry for location L2")
	public void from_curbside_check_in_tab_filtration_is_done_for_location_l1_when_there_is_already_arrival_entry_for_location_l2() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		log("user on curbside page");
		curbsidePage.clickOncurbsideCheckinLocationDropDown();
		log("user clicks on dropdown");
		curbsidePage.selectLocationL1inDropDown();
		log("user select the location");
	}
	@Then("I verify notification count should not get updated after arrival entry in curbside dashboard for location L2 without refresh")
	public void i_verify_notification_count_should_not_get_updated_after_arrival_entry_in_curbside_dashboard_for_location_l2_without_refresh() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is not updated for location L2");
	}
	@Then("I verify notification count should not get updated after arrival entry in appointment dashboard for location L2 without refresh")
	public void i_verify_notification_count_should_not_get_updated_after_arrival_entry_in_appointment_dashboard_for_location_l2_without_refresh() {
		mainPage.clickOnAppointmentsTab();
	    log("user on appointments page");
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is not updated for location L2");
		
	}
	
	@When("I booked an appointment for patient {string} and confirmed his arrival for Location {string}")
	public void i_booked_an_appointment_for_patient_and_confirmed_his_arrival_for_location(String patientName, String locationName) throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus30Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(30);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus30Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email"),
						propertyData.getProperty("provider.name"),
						patientName, locationName),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
		driver.navigate().refresh();
	}
	
	@When("I schedule an appointment for location L1")
	public void i_schedule_an_appointment_for_location_l1() throws NullPointerException, IOException {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		Appointment.patientId = String.valueOf(randamNo);
		Appointment.apptId = String.valueOf(randamNo);
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
		propertyData.getProperty("mf.apt.scheduler.practice.id"),
		payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
		propertyData.getProperty("mf.apt.scheduler.email")),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
		Appointment.apptId); 
		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
		Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId, Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200); Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
		propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("mf.apt.scheduler.practice.id"),
		headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId, Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}
	@When("from curbside check-in filtration is done for location L2")
	public void from_curbside_check_in_filtration_is_done_for_location_l2() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		curbsidePage.clickOncurbsideCheckinLocationDropDown();
		curbsidePage.selectLocationL2inDropDown();
	}
	@Then("I verify notification count should not get updated after arrival entry in curbside dashboard for location L1")
	public void i_verify_notification_count_should_not_get_updated_after_arrival_entry_in_curbside_dashboard_for_location_l1() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is not updated for location L1");
	}
	@When("from curbside check-in remove filter for location L2")
	public void from_curbside_check_in_remove_filter_for_location_l2() {
		curbsidePage.removeIconforLocationInCurbsidecheckin();
		log("filter removed for location L2");
	}
	@Then("I verify notification count should get updated for all the patients in curbside dashboard")
	public void i_verify_notification_count_should_get_updated_for_all_the_patients_in_curbside_dashboard() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is updated for all patients");
	}
	@When("from curbside check-in filtration is done for location L1")
	public void from_curbside_check_in_filtration_is_done_for_location_l1() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		curbsidePage.clickOncurbsideCheckinLocationDropDown();
		curbsidePage.selectLocationL1inDropDown();
	}
	@Then("I verify notification count should not get updated after arrival entry in curbside dashboard for location L2")
	public void i_verify_notification_count_should_not_get_updated_after_arrival_entry_in_curbside_dashboard_for_location_l2() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is not updated for location L2");
	}
	@When("from curbside check-in remove filter for location L1")
	public void from_curbside_check_in_remove_filter_for_location_l1() {
		curbsidePage.removeIconforLocationInCurbsidecheckin();
		log("filter removed for location L1");
	}
	@Then("I verify notification count should get updated for all the patients in arrival grid")
	public void i_verify_notification_count_should_get_updated_for_all_the_patients_in_arrival_grid() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is updated for all patients");
	}
	@Then("I verify notification count should not get updated after arrival entry in appointment dashboard for location L1")
	public void i_verify_notification_count_should_not_get_updated_after_arrival_entry_in_appointment_dashboard_for_location_l1() {
	    mainPage.clickOnAppointmentsTab();
	    assertTrue(curbsidePage.visibilityOfNotifIcon());
	    log("Notification count is not updated for location L1");
	}
	@Then("I verify notification count should get updated for all the patients after arrival entry in appointment dashboard for location L1")
	public void i_verify_notification_count_should_get_updated_for_all_the_patients_after_arrival_entry_in_appointment_dashboard_for_location_l1() {
		mainPage.clickOnAppointmentsTab();
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is updated for all patients");
	}
	@Then("I verify notification count should not get updated after arrival entry in appointment dashboard for location L2")
	public void i_verify_notification_count_should_not_get_updated_after_arrival_entry_in_appointment_dashboard_for_location_l2() {
		mainPage.clickOnAppointmentsTab();
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is not updated for location L2");
	}
	@Then("I verify notification count should get updated for all the patients after arrival entry in appointment dashboard for location L2")
	public void i_verify_notification_count_should_get_updated_for_all_the_patients_after_arrival_entry_in_appointment_dashboard_for_location_l2() {
		mainPage.clickOnAppointmentsTab();
		assertTrue(curbsidePage.visibilityOfNotifIcon());
		log("Notification count is updated for all patients");
	}
	
	@When("I schedule an appointment for three patients")
	public void i_schedule_an_appointment_for_three_patients() throws NullPointerException, IOException {
		for (int i = 0; i < 3; i++)
		{
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);

			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
		
	}
	@When("I go to curbside check-in where three patients are already there in arrival grid")
	public void i_go_to_curbside_check_in_where_three_patients_are_already_there_in_arrival_grid() throws InterruptedException {
	    mainPage.clickOnCurbsideTab();
	    
	}
	@When("I go to appointments dashboard")
	public void i_go_to_appointments_dashboard() throws InterruptedException {
	    mainPage.clickOnAppointmentsTab();
	}
	@Then("I verify notification count on top in appointment dashboard should be three")
	public void i_verify_notification_count_on_top_in_appointment_dashboard_should_be_three() {
	    assertTrue(curbsidePage.visibilityOfNotifIcon());
	    assertEquals(curbsidePage.getNotificationCount(), "3", "Notification Count is not match");
	}
	@When("I go to curbside check-in where three patients are already there in arrival tab")
	public void i_go_to_curbside_check_in_where_three_patients_are_already_there_in_arrival_tab() throws InterruptedException {
		 mainPage.clickOnCurbsideTab();
	}
	@When("I go to curbside check-in tab after new patient arrival")
	public void i_go_to_curbside_check_in_tab_after_new_patient_arrival() throws InterruptedException {
		 mainPage.clickOnCurbsideTab();
	} 
	@Then("I verify notification count on top in appointment dashboard should get updated to four without refresh")
	public void i_verify_notification_count_on_top_in_appointment_dashboard_should_get_updated_to_four_without_refresh() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
	    assertEquals(curbsidePage.getNotificationCount(), "4", "Notification Count is not match");
	}

	@Then("I verify the notification count and arrival count on the grid when change the location filter from L1 {string} to L2 {string}")
	public void i_verify_the_notification_count_and_arrival_count_on_the_grid_when_change_the_location_filter_from_l1_to_l2(String location1, String location2) throws InterruptedException {
		scrollAndWait(0, 500, 10000);
		  assertEquals(curbsidePage.getNotificationCount(), "3", "Notification Count is not match");
		  curbsidePage.enterLocationName(location1);
		  assertEquals(curbsidePage.getPatientsCount(),2,"Patient count was not same");
		  log("Patient count for location L1");
		  curbsidePage.clearLocationFilterTextbox();
		  assertEquals(curbsidePage.getNotificationCount(), "3", "Notification Count is not match");
		  curbsidePage.enterLocationName(location2);
		  assertEquals(curbsidePage.getPatientsCount(),1,"Patient count was not same");
		  log("Patient count for location L2");
	}
		
	@When("I booked an appointment for patient {string} and confirmed his arrival for provider {string}")
	public void i_booked_an_appointment_for_patient_and_confirmed_his_arrival_for_provider(String patientName, String providerName) throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus30Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(30);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus30Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email"),
						providerName,
						patientName, propertyData.getProperty("location.name")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
		driver.navigate().refresh();
	}
	
	@Then("I verify the notification count and arrival count on the grid when change the provider filter from PR1 {string} to PR2 {string}")
	public void i_verify_the_notification_count_and_arrival_count_on_the_grid_when_change_the_provider_filter_from_pr1_to_pr2(String provider1, String provider2) throws InterruptedException {
		 scrollAndWait(0, 500, 10000);
		  assertEquals(curbsidePage.getNotificationCount(), "3", "Notification Count is not match");
		  curbsidePage.enterProviderName(provider1);
		  assertEquals(curbsidePage.getPatientsCount(),2,"Patient count was not same");
		  log("Patient count for provider PR1");
		  driver.navigate().refresh();
		  Thread.sleep(5000);
		  assertEquals(curbsidePage.getNotificationCount(), "3", "Notification Count is not match");
		  curbsidePage.enterProviderName(provider2);
		  assertEquals(curbsidePage.getPatientsCount(),1,"Patient count was not same");
		  log("Patient count for provider PR2");
		  driver.navigate().refresh();
		  Thread.sleep(5000);
		  assertEquals(curbsidePage.getPatientsCount(),3,"Patient count was not same");
	}
	
	@When("I turn off send notification radio button from setting in notifications")
	public void i_turn_off_send_notification_radio_button_from_setting_in_notifications() throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.offNotification();
		log("send notification radio button is off");
		notifPage.saveNotification();
	}
	
	@Then("verify on mail no cadence reminder is sent when send notifiaction is off")
	public void verify_on_mail_no_cadence_reminder_is_sent_when_send_notifiaction_is_off() throws Exception {
	   YopMail yopMail = new YopMail(driver);
	   yopMail.isMessageInInbox(propertyData.getProperty("curbside.checkin.mail"), "Your appointment is coming up!", "Appointment Reminder", 5);
	}
	
	@Then("I hit edit button of email for appointment reminder")
	public void i_user_hit_edit_button_of_email_for_appointment_reminder() throws InterruptedException {
		scrollAndWait(0, 1000, 3000);
		notifPage.clickOnEditButtonHamburgerButton();
		log("User redirect on edit template design page");
	}
	
	@Then("I verify is able to see by default three timing with default days configured and timing units with configured one,three,five on template editor page")
	public void i_verify_user_is_able_to_see_by_default_three_timing_with_default_days_configured_and_timing_units_with_configured_one_three_five_on_template_editor_page() throws InterruptedException {
		for(int i=1;i<=3;i++) {
			 assertTrue(notifPage.visibilityOfTiming(i));
			 assertEquals(notifPage.getTextTiming(i),"Days", "Default Timing days text was not match");
			 log(i+" Timing is displayed");
			 assertTrue(notifPage.visibilityOfTimingUnit(i));
			 log(i+" Timing unit is displayed");
		}
		 assertEquals(notifPage.getTimingUnit(1),"1","Default Timing unit for 1 days was not match" );
		 assertEquals(notifPage.getTimingUnit(2),"3","Default Timing unit for 3 days was not match" );
		 assertEquals(notifPage.getTimingUnit(3),"5","Default Timing unit for 5 days was not match" );
		notifPage.clickOnBackArrow();
	}
	
	@When("I select timing days and enter timing unit for {string}")
	public void i_select_timing_days_and_enter_timing_unit_for(String deliveriMethod) throws InterruptedException {
		scrollAndWait(0, 200, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveriMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.day1=notifPage.enterUnlimitedDays();
		notifPage.enterTimingAndTimingUnit(1, "Days",Appointment.day1);
		Appointment.day2=notifPage.enterUnlimitedDays();
		notifPage.enterTimingAndTimingUnit(2, "Days", Appointment.day2);
		Appointment.day3=notifPage.enterUnlimitedDays();
		notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.day3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}
	
	@Then("I verify user is able to enter unlimited numbers so there is no limit")
	public void i_verify_user_is_able_to_enter_unlimited_numbers_so_there_is_no_limit() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getTimingUnitText(),Appointment.day1+", "+Appointment.day2+", "+Appointment.day3, "Days Timing unit was not match");
	}
	
	@Then("I verify system is allowing to enter integers from one to twenty three in timing unit section for email in appointment reminders for {string}")
	public void i_verify_system_is_allowing_to_enter_integers_from_one_to_twenty_three_in_timing_unit_section_for_email_in_appointment_reminders_for(String deliveriMethod) throws InterruptedException {
		scrollAndWait(0, 200, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveriMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.hour1=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hour1);
		Appointment.hour2=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.hour2);
		Appointment.hour3=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hour3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getTimingUnitText(), Appointment.hour1+", "+Appointment.hour2+", "+Appointment.hour3, "Hours Timing unit was not match");
	}
	
	@Then("I verify user able to enter integer from ten to fifty nine in timing unit section for minutes in appointment reminders for {string}")
	public void i_verify_user_able_to_enter_integer_from_ten_to_fifty_nine_in_timing_unit_section_for_minutes_in_appointment_reminders_for(String deliveriMethod)  throws InterruptedException {
		scrollAndWait(0, 200, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveriMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.minute1=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(1, "Minutes", Appointment.minute1);
		Appointment.minute2=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minute2);
		Appointment.minute3=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(3, "Minutes", Appointment.minute3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimingUnitText(), Appointment.minute1+", "+Appointment.minute2+", "+Appointment.minute3, "Minutes Timing unit was not match");
	}
	
	@When("I go to curbside check-in where there are patients click one or multiple patients as checkedin")
	public void i_go_to_curbside_check_in_where_there_are_patients_click_one_or_multiple_patients_as_checkedin() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		curbsidePage.selectPatientscheckbox();
		curbsidePage.clickOnCheckInButton();
	}
	@Then("I verify once the patient marked as checkedin that particular entries should not be there in curbside checkin grid")
	public void i_verify_once_the_patient_marked_as_checkedin_that_particular_entries_should_not_be_there_in_curbside_checkin_grid() {
		assertTrue(curbsidePage.visibilityOfCheckinButton());
	}
	@When("I go to curbside check-in where there are patients click one or multiple patients whose timer is still on and check-in")
	public void i_go_to_curbside_check_in_where_there_are_patients_click_one_or_multiple_patients_whose_timer_is_still_on_and_check_in() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		curbsidePage.selectPatientscheckbox();
		curbsidePage.selectPatientscheckboxwithTimerOn();
		curbsidePage.clickOnCheckInButton();
	}
	@Then("I verify once the patient marked as checkedin that particular entries should get removed from curbside checkin grid")
	public void i_verify_once_the_patient_marked_as_checkedin_that_particular_entries_should_get_removed_from_curbside_checkin_grid() {
		assertTrue(curbsidePage.visibilityOfCheckinButton());
	}
	@When("I go to appointment dashboard")
	public void i_go_to_appointment_dashboard() throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		apptPage.selectPatientIdAppt(Appointment.patientId);	
	}
	@Then("I verify once the patient marked as checkedin and in appointment dashboard there should be a checkedin symbol for that patient")
	public void i_verify_once_the_patient_marked_as_checkedin_and_in_appointment_dashboard_there_should_be_a_checkedin_symbol_for_that_patient() throws InterruptedException {
		assertTrue(apptPage.displayCheckInMark());
		
	}
	
	@When("I go to curbside check-in tab select individual or multiple patients checkbox")
	public void i_go_to_curbside_check_in_tab_select_individual_or_multiple_patients_checkbox() throws InterruptedException {
	   mainPage.clickOnCurbsideTab();
	   curbsidePage.selectPatientscheckbox();
	}
	@Then("I verify all the patients should be selected on the curbside checkin tab")
	public void i_verify_all_the_patients_should_be_selected_on_the_curbside_checkin_tab() {
		 assertTrue(curbsidePage.visibilityofselectPatientscheckbox());
	}
	@When("I go to curbside check-in tab select three patients checkbox")
	public void i_go_to_curbside_check_in_tab_select_three_patients_checkbox() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		curbsidePage.selectPatientscheckbox();
		curbsidePage.selectPatientscheckbox2();
		curbsidePage.selectPatientscheckbox3();
	}
	@Then("I verify three patients checkbox should be selected on the curbside checkin tab")
	public void i_verify_three_patients_checkbox_should_be_selected_on_the_curbside_checkin_tab() {
		 assertTrue(curbsidePage.visibilityofselectPatientscheckbox());
		 assertTrue(curbsidePage.visibilityofselectPatientscheckbox2());
		 assertTrue(curbsidePage.visibilityofselectPatientscheckbox3());
	}
	
	@When("I go to appointment tab and see the arrival displayed for the patient in the arrival grid")
	public void i_go_to_appointment_tab_and_see_the_arrival_displayed_for_the_patient_in_the_arrival_grid() throws InterruptedException {
		apptPage.selectPatientIdAppt(Appointment.patientId);
	}
	@Then("I verify scheduling details are displayed for patient P1 but checked-in icon is not displayed until the staff checks him in")
	public void i_verify_scheduling_details_are_displayed_for_patient_p1_but_checked_in_icon_is_not_displayed_until_the_staff_checks_him_in() throws InterruptedException {
		assertTrue(apptPage.displaycurbsideArrivalMark());
	}
	@When("I go to curbside check-in tab and clicks on check-in button for the patient P1")
	public void i_go_to_curbside_check_in_tab_and_clicks_on_check_in_button_for_the_patient_p1() throws InterruptedException {
	    mainPage.clickOnCurbsideTab();
	    curbsidePage.selectPatientscheckbox();    
	    curbsidePage.clickOnCheckInButton();
	}
	@Then("I verify patient P1 entry is removed from the curbside check-in grid")
	public void i_verify_patient_p1_entry_is_removed_from_the_curbside_check_in_grid() {
	    assertTrue(curbsidePage.visibilityOfNotifIcon());
	}
	@Then("I verify checked in icon is added in a new column for patient P1 along with the other data in appointment dashboard")
	public void i_verify_checked_in_icon_is_added_in_a_new_column_for_patient_p1_along_with_the_other_data_in_appointment_dashboard() throws InterruptedException {
	    assertTrue(apptPage.displayCheckInMark());
	    
	}

	@When("I schedule an appointment and perform checkin action")
	public void i_schedule_an_appointment_and_perform_checkin_action() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);

		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
		
		Response checkInResponse = aptPrecheckPost.getCheckinActions(propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				aptPrecheckPayload.getCheckinActionsPayload(Appointment.apptId,Appointment.patientId,
						propertyData.getProperty("apt.precheck.practice.id")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()));
		assertEquals(checkInResponse.getStatusCode(), 200);
	}
	
	@When("I click on setting tab and ON notification setting")
	public void i_click_on_setting_tab_and_on_notification_setting() throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.onNotification();
		notifPage.saveNotification();
	}
	
	@When("I switch on appointment dashboard and send broadcast message for curbside check in patient")
	public void i_switch_on_appointment_dashboard_and_send_broadcast_message_for_curbside_check_in_patient() throws Exception {
		mainPage.switchOnAppointmentsTab();
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
	}
	
	@Then("I verify system should not show day prior entry in reminder section")
	public void i_verify_system_should_not_show_day_prior_entry_in_reminder_section() throws InterruptedException {
		scrollAndWait(0, 2000, 60000);
		apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
		assertNotEquals(apptPage.selectedPatientGetPriorEntryForEmail(Appointment.patientId, Appointment.apptId,1),"days prior","Email statuse was not match");
		assertNotEquals(apptPage.selectedPatientGetPriorEntryForText(Appointment.patientId, Appointment.apptId,2),"days prior","Text statuse was not match");
	}
	
	@Then("I verify user is able to edit {string} cadence template from cadence editor page")
	public void i_verify_user_is_able_to_edit_cadence_template_from_cadence_editor_page(String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
		assertEquals(notifPage.getApptReminderTextInEmail(),"Appointment Reminder","Appointment Reminder text was not match");
		assertEquals(notifPage.getNotificationTypeText(),"Notification Type :","Notification Type text was not match");
		assertEquals(notifPage.getApptMtdTextInEmail(),"Appointment Method:","Appointment Method text was not match");
		assertEquals(notifPage.visibilityOfInOfficeText(),"In Office","In Office text was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		assertTrue(notifPage.visibilityOfTiming(1));
		assertTrue(notifPage.visibilityOfTiming(2));
		assertTrue(notifPage.visibilityOfTiming(3));
		assertTrue(notifPage.visibilityOfTimingUnit(1));
		assertTrue(notifPage.visibilityOfTimingUnit(2));
		assertTrue(notifPage.visibilityOfTimingUnit(3));
		assertTrue(notifPage.visibilityOfAddButton());
		notifPage.addFourthTimingAndTimingUnit();
		assertFalse(notifPage.visibilityOfAddButton());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.day1 = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(1, "Days", Appointment.day1);
		Appointment.day2 = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(2, "Days", Appointment.day2);
		Appointment.day3 = notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.day3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
		scrollAndWait(0, -500, 5000);
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
	}
	
	@When("from setting in notifications user click on curbside checkin tab")
	public void from_setting_in_notifications_user_click_on_curbside_checkin_tab() throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.clickOnCurbsideCheckInTabInNotif();
		scrollAndWait(0, 500, 3000);
	}
	
	@Then("I verify if additional arrival message text box is present and max size limit for additional arrival message for custom fields for English and Spanish")
	public void i_verify_if_additional_arrival_message_text_box_is_present_and_max_size_limit_for_additional_arrival_message_for_custom_fields_for_english_and_spanish() throws InterruptedException {
		notifPage.clickOnEnglishButton();
		assertTrue(notifPage.visibilityOfArrivalInstTextBox());
		notifPage.clearArrivalInstTextbox();
		notifPage.enterTextInArrivalInstTextbox(propertyData.getProperty("more.than.size.of.arrival.conf.inst"));
		notifPage.saveNotification();
		assertEquals(notifPage.getMaxLengthChar(),"(500/500 characters)","Character count was not same");
		notifPage.clearArrivalInstTextbox();
		notifPage.enterTextInArrivalInstTextbox(propertyData.getProperty("add.arrival.instruction.in.en"));
		notifPage.saveNotification();
		scrollAndWait(0, 500, 3000);
		
		notifPage.clickOnSpanishButton();
		assertTrue(notifPage.visibilityOfArrivalInstTextBox());
		notifPage.clearArrivalInstTextbox();
		notifPage.enterTextInArrivalInstTextbox(propertyData.getProperty("more.than.size.of.arrival.conf.inst"));
		notifPage.saveNotification();
		assertEquals(notifPage.getMaxLengthChar(),"(500/500 characters)","Character count was not same");
		notifPage.clearArrivalInstTextbox();
		notifPage.enterTextInArrivalInstTextbox(propertyData.getProperty("add.arrival.instruction.in.es"));
		notifPage.saveNotification();
	}

	@Then("I verify user is able see default arrival confirmation message in english and Spanish in text box")
	public void i_verify_user_is_able_see_default_arrival_confirmation_message_in_english_and_spanish_in_text_box() {
		notifPage.clickOnEnglishButton();
		assertTrue(notifPage.visibilityOfArrivalConfirmMsg());
		notifPage.clickOnSpanishButton();
		assertTrue(notifPage.visibilityOfArrivalConfirmMsg());

}
	
	@When("I select start date and time")
	public void i_select_start_date_and_time() throws InterruptedException {
		apptPage.selectStartDate(8);
	}

	@And("I am able to navigate from first page to second page and third page")
	public void i_am_able_to_navigate_from_first_page_to_second_page_and_third_page() throws InterruptedException {
		scrollAndWait(0, 3000, 5000);
		assertEquals(apptPage.getPageNo(), "1", "Page number was not correct");
		assertEquals(apptPage.jumpToNextPage(), "2", "Not navigate to next page");
		assertEquals(apptPage.jumpToNextPage(), "3", "Not navigate to next page");
	}

	@Then("I verify after clicking on refresh button user on third page and able to navigate on first page")
	public void i_verify_after_clicking_on_refresh_button_user_on_third_page_and_able_to_navigate_on_first_page() throws InterruptedException {
		apptPage.clickOnRefreshTab();
		assertEquals(apptPage.getPageNo(), "3", "Page number was not correct");
		apptPage.jumpToPreviousPage();
		apptPage.jumpToPreviousPage();
		assertEquals(apptPage.getPageNo(), "1", "Page number was not correct");
}
	
	@When("I schedule an appointment for location L1 and provider A1")
	public void i_schedule_an_appointment_for_location_l1_and_provider_a1() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
		
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}
	@When("in curbside check-in filtration is done for location L1 and provider A1")
	public void in_curbside_check_in_filtration_is_done_for_location_l1_and_provider_A1() throws InterruptedException {
	    mainPage.clickOnCurbsideTab();
	    curbsidePage.clickOncurbsideCheckinLocationDropDown();
	    curbsidePage.selectLocationL1inDropDown();
	    curbsidePage.clickProviderdropdown();
		curbsidePage.selectProviderA1inDropdown();

	}
	@When("I schedule {int} appointments for location L1 and provider A2")
	public void i_schedule_appointments_for_location_l1_and_provider_a2(int appts) throws NullPointerException, IOException {
		for(int i=0 ; i < appts ; i++) {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayloadwithdifferentProvider(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
		
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}
	}
	@Then("I verify notification count will get updated but entry will not come in arrival grid for location L1 and provider A2")
	public void i_verify_notification_count_will_get_updated_but_entry_will_not_come_in_arrival_grid_for_location_l1_and_provider_a2() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
	    assertEquals(curbsidePage.getNotificationCount(),"2","Notification count is not match");
	}
	@When("I schedule {int} appointments for location L1 and provider A1")
	public void i_schedule_appointments_for_location_l1_and_provider_a1(int appts) throws NullPointerException, IOException {
		for(int i=0 ; i < appts ; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);
			
			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
		
	}
	
	@When("in curbside check-in filtration is done for location L1")
	public void in_curbside_check_in_filtration_is_done_for_location_l1() throws InterruptedException {
	    mainPage.clickOnCurbsideTab();
	    curbsidePage.clickOncurbsideCheckinLocationDropDown();
	    curbsidePage.selectLocationinDropDown();
	    
	}
	@Then("I verify notification count get updated after arrival entry in curbside dashboard without refresh")
	public void i_verify_notification_count_get_updated_after_arrival_entry_in_curbside_dashboard_without_refresh() {
	   assertTrue(curbsidePage.visibilityOfNotifIcon());
	   assertEquals(curbsidePage.getNotificationCount(),"1","Notification count is not match");
	}
	
	@When("I schedule {int} appointments who have confirmed their arrival")
	public void i_schedule_appointments_who_have_confirmed_their_arrival(int appts) throws NullPointerException, IOException {
			for (int i = 0; i < appts; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);
			
			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
	}
	@Then("I verify notification count will get updated and entry will be seen in arrival grid for location L1 and provider A1")
	public void i_verify_notification_count_will_get_updated_and_entry_will_be_seen_in_arrival_grid_for_location_l1_and_provider_a1() {
		 assertTrue(curbsidePage.visibilityOfNotifIcon());
		 assertEquals(curbsidePage.getNotificationCount(),"2","Notification count is not match");
	}
	@When("from curbside check-in filtration is done for location L1 and provider A1")
	public void from_curbside_check_in_filtration_is_done_for_location_l1_and_provider_a1() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
	    curbsidePage.clickOncurbsideCheckinLocationDropDown();
	    curbsidePage.selectLocationL1inDropDown();
	    curbsidePage.clickProviderdropdown();		
	    curbsidePage.selectProviderA1inDropdown();

	}
	@When("I schedule {int} appointments for location L2 and provider A1")
	public void i_schedule_appointments_for_location_l2_and_provider_a1(int appts) throws NullPointerException, IOException {
		for(int i=0; i<appts; i++) {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putApptPayloadwithDifferentlocationandProvider(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
		
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}
	}
	@Then("I verify notification count should not get updated and no entries will be seen in arrival grid for location L2 and provider A1")
	public void i_verify_notification_count_should_not_get_updated_and_no_entries_will_be_seen_in_arrival_grid_for_location_l2_and_provider_a1() {
		 assertTrue(curbsidePage.visibilityOfNotifIcon());
	}
	@When("I schedule {int} appointments for location L2 and provider A2")
	public void i_schedule_appointments_for_location_l2_and_provider_a2(int appts) throws NullPointerException, IOException {
		for(int i=0; i < appts; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayloadwithDifferentlocation(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);
			
			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
	   
	}
	
	@Then("I verify notification count should not get updated and no entries will be seen in arrival grid for location L2 and provider A2")
	public void i_verify_notification_count_should_not_get_updated_and_no_entries_will_be_seen_in_arrival_grid_for_location_l2_and_provider_a2() {
		 assertTrue(curbsidePage.visibilityOfNotifIcon());
	}
	@When("I schedule an appointment for location L1 and patient P1")
	public void i_schedule_an_appointment_for_location_l1_and_patient_p1() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
				propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
		
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}
	@When("from curbside check-in filtration is done for location L1 and patient P1")
	public void from_curbside_check_in_filtration_is_done_for_location_l1_and_patient_p1() throws InterruptedException {
	    mainPage.clickOnCurbsideTab();
	    curbsidePage.clickOncurbsideCheckinLocationDropDown();
	    curbsidePage.selectLocationL1inDropDown();
	    curbsidePage.clickpatientNamedropdown();
	    curbsidePage.selectPatientP1();
	}
	@Then("I verify notification count should get updated and entries will be seen in arrival grid for for location L1 and patient P1")
	public void i_verify_notification_count_should_get_updated_and_entries_will_be_seen_in_arrival_grid_for_for_location_l1_and_patient_p1() {
	    assertTrue(curbsidePage.visibilityOfNotifIcon());
	    assertEquals(curbsidePage.getNotificationCount(),"1","Notification count is not match");
	}
			
	@When("I go to curbside check-in tab select the top checkbox")
	public void i_go_to_curbside_check_in_tab_select_the_top_checkbox() throws InterruptedException {
	   mainPage.clickOnCurbsideTab();
	   curbsidePage.selectAllAppointment();
	}
	@When("I later deselect top checkbox in the curbside check-in tab")
	public void i_later_deselect_top_checkbox_in_the_curbside_check_in_tab() throws InterruptedException {
		curbsidePage.deselectAllAppointment();
	}
	@Then("I verify all the patients should be selected and deselected on the curbside tab")
	public void i_verify_all_the_patients_should_be_selected_and_deselected_on_the_curbside_tab() {
	   assertTrue(curbsidePage.visibilityOfSelectAllCheckbox());

	}
	
	@When("I schedule an appointment for location L1 and provider A2")
	public void i_schedule_an_appointment_for_location_l1_and_provider_a2() throws NullPointerException, IOException, InterruptedException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayloadwithdifferentProvider(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						propertyData.getProperty("mf.apt.scheduler.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);

		Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(actionResponse.getStatusCode(), 200);
		
		Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

		Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
				propertyData.getProperty("baseurl.apt.precheck"),
				propertyData.getProperty("apt.precheck.practice.id"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(arrivalResponse.getStatusCode(), 200);
	}
	
	@Then("I verify notification count will get updated but entry will not come in arrival grid for provider A2")
	public void i_verify_notification_count_will_get_updated_but_entry_will_not_come_in_arrival_grid_for_provider_a2() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
	}
	@Then("I verify notification count will get updated and entry will also be seen in arrival grid for provider A1")
	public void i_verify_notification_count_will_get_updated_and_entry_will_also_be_seen_in_arrival_grid_for_provider_a1() {
		assertTrue(curbsidePage.visibilityOfNotifIcon());
	    assertEquals(curbsidePage.getNotificationCount(),"1","Notification count is not match");
	}
	
	@When("from setting in notifications I click on email hamburgerbutton section of appointment reminder")
	public void from_setting_in_notifications_i_click_on_email_hamburgerbutton_section_of_appointment_reminder() {
	    mainPage.clickOnSettingTab();
	    notifPage.clickOnNotificationTab();
	    notifPage.clickApptReminderEmailHamburgerButton();
	}
	@When("I hit edit button")
	public void i_hit_edit_button() {
	   notifPage.clickApptReminderEmailEditButton();
	}
	@Then("I verify on template editor page all fields are displayed properly of appointment reminder for email")
	public void i_verify_on_template_editor_page_all_fields_are_displayed_properly_of_appointment_reminder_for_email() throws InterruptedException {
		assertTrue(notifPage.visibilityOfeditPageTemplate(), "Editing:Appointment Reminder");
		assertEquals(notifPage.visibilityOfversionTextInEditPage(), "Version:" ,"Version: is not match");
		assertEquals(notifPage.visibilityOfversionV2TextInEditPage(), "v2" ,"v2 is not match");
		assertTrue(notifPage.visibilityOflogoInEditPage());
		assertEquals(notifPage.visibilityOfappointmentReminderTextInEditPage(),"Appointment Reminder","Appointment Reminder is not match");
		assertEquals(notifPage.visibilityOfconfirmAppointmentButtonInEditPage(), "Confirm Appointment", "Confirm Appointment is not match");
		assertEquals(notifPage.visibilityOfstartPrecheckLinkInEditPage(), "Start PreCheck", "Start PreCheck is not match");
		assertEquals(notifPage.visibilityOfpatientTextComingUpInEditPage(), "[Patient Name], your appointment is coming up.","[Patient Name], your appointment is coming up. is not match");
		assertEquals(notifPage.visibilityOfdateAndTimeTextInEditPage(),"Date and Time","Date and Time is not match");
		assertEquals(notifPage.visibilityOfdayOfTheWeekTextInEditPage(),"Day of the week at 00:00 AM/PM","Day of the week at 00:00 AM/PM is not match");
		assertEquals(notifPage.visibilityOfmonthDDYYYYFormatTextInEditPage(),"Month DD, YYYY","Month DD, YYYY is not match");
		assertEquals(notifPage.visibilityOflocationTextInEditPage(),"Location","Location is not match");
		assertEquals(notifPage.visibilityOflocationNameTextInEditPage(),"Location Name","Location Name is not match");
		assertEquals(notifPage.visibilityOflocationAddress1TextInEditPage(),"Location Address1","Location Address1 is not match");
		assertEquals(notifPage.visibilityOflocationAddress2TextInEditPage(),"Location Address2","Location Address2 is not match");
		assertEquals(notifPage.visibilityOfcityStateZipTextInEditPage(),"City State, Zip","City State, Zip is not match");
		assertEquals(notifPage.visibilityOfnumberTextInEditPage(),"(XXX) XXX-XXXX","(XXX) XXX-XXXX is not match");
		assertEquals(notifPage.visibilityOfpinOnMapLinkTextInEditPage(),"Pin on Map","Pin on Map is not match");
		assertEquals(notifPage.visibilityOfproviderTextInEditPage(),"Provider","Provider is not match");
		assertEquals(notifPage.visibilityOfproviderNameTextInEditPage(),"Provider Name","Provider Name is not match");
		assertEquals(notifPage.visibilityOfrescheduleOrCancelLinkTextInEditPage(),"Reschedule or Cancel","Reschedule or Cancel is not match");
		notifPage.clickOnBackArrow();
	}
	@When("from setting in notifications I click on text hamburgerbutton section of appointment reminder")
	public void from_setting_in_notifications_i_click_on_text_hamburgerbutton_section_of_appointment_reminder() {
		 mainPage.clickOnSettingTab();
		 notifPage.clickOnNotificationTab();
		 notifPage.clickApptReminderSmsHamburgerButton();
	}
	@Then("I verify on template editor page all fields are displayed properly of appointment reminder for text")
	public void i_verify_on_template_editor_page_all_fields_are_displayed_properly_of_appointment_reminder_for_text() throws InterruptedException {
		assertTrue(notifPage.visibilityOfeditPageTemplateForText(), "Editing:Appointment Reminder");
		assertEquals(notifPage.visibilityOfversionTextInEditPageForText(),"Version:","Version: is not match");
		assertEquals(notifPage.visibilityOfdefaultTextInEditPageForText(),"Default","Default is not match");
		assertTrue(notifPage.visibilityOfpatientTextInEditPageForText(),"[Patient Name], check in now for your appointment with [Resource Name] at [Practice Name] -   [Location Name] on [Day of the week], [Month] [DD] [YYYY] [00:00 AM/PM]:https://medfusion.page.link/wnoag9aksagQA");
		assertTrue(notifPage.visibilityOfconfirmTextInEditPageForText(),"Confirm your appointment at:https://medfusion.page.link/wnoag9aksagQA");
		assertTrue(notifPage.visibilityOfrescheduleCancelTextInEditPageForText(),"Reschedule/Cancel Appointmentat: https://medfusion.page.link/wnoags0gw29shg");
		assertTrue(notifPage.visibilityOfdirectionsTextInEditPageForText(),"Get Directions at:https://medfusion.page.link/wnoags0gw29shg");
		assertEquals(notifPage.visibilityOfstopToUnsubscribeTextInEditPageForText(),"Text STOP to unsubscribe","Text STOP to unsubscribe is not match");
		notifPage.clickOnBackArrow();
	}
	
	@When("I click on setting tab")
	public void i_click_on_setting_tab() {
	   mainPage.clickOnSettingTab();
	}
	@When("I click on notification tab")
	public void i_click_on_notification_tab() {
	   notifPage.clickOnNotificationTab();
	}
	@When("I click on edit of hamburger button for email in appointment reminders")
	public void i_click_on_edit_of_hamburger_button_for_email_in_appointment_reminders() {
		notifPage.clickApptReminderEmailHamburgerButton();
		notifPage.clickApptReminderEmailEditButton();
	}
	@Then("I verify that design tab is visible under setting tab")
	public void i_verify_that_design_tab_is_visible_under_setting_tab() throws InterruptedException {
	   assertTrue(notifPage.visibilityOfsettingTabunderDesignsection());
	   notifPage.clickOnBackArrow();
	}
	@Then("I verify that system should show all default value on cadence page properly")
	public void i_verify_that_system_should_show_all_default_value_on_cadence_page_properly() throws InterruptedException {
	   assertEquals(notifPage.visibilityOfnotificationTypetextUnderdesignTab(),"Notification Type","Notification Type is not match");
	   assertEquals(notifPage.visibilityOfappointmentReminderstextUnderdesignTab(),"Appointment Reminder","Appointment Reminder is not match");
	   assertEquals(notifPage.visibilityOfversionTextunderDesigntab(),"Version Name :","Version Name : is not match");
	   assertEquals(notifPage.visibilityOfv2TextunderDesigntab(),"v2","v2 is not match");
	   assertEquals(notifPage.visibilityOfappointmentMethodTextunderDesigntab(),"Appointment Method:","Appointment Method: is not match");
	   assertEquals(notifPage.visibilityOfinOfficeTextunderDesigntab(),"In Office","In Office is not match");
	   assertEquals(notifPage.visibilityOfdeliveryMethodTextunderDesigntab(),"Delivery Method:","Delivery Method: is not match");
	   notifPage.clickOnBackArrow();
	}
	@Then("I verify that system should show  default three timing units")
	public void i_verify_that_system_should_show_default_three_timing_units() throws InterruptedException {
	    assertEquals(notifPage.visibilityOftimingtextUnderdesignTab(),"Days","Days is not match");
	    assertTrue(notifPage.visibilityOftimingUnitstextUnderdesignTab());
	    assertEquals(notifPage.visibilityOfdefaultTimingtextUnderdesignTab(),"Days","Days is not match");
	    assertTrue(notifPage.visibilityOfdefaultTimingUnitstUnderdesignTab());
	    assertEquals(notifPage.visibilityOfdefaultTimingstextUnderdesignTab(),"Days","Days is not match");
	    assertTrue(notifPage.visibilityOfdefaultTimingUnitstextUnderdesignTab());
	    notifPage.clickOnBackArrow();
	}
	@When("I click on add button and add timing and timing unit")
	public void i_click_on_add_button_and_add_timing_and_timing_unit() {
	   notifPage.ClickonAddbutton();
	   notifPage.clickOntimingdropdownunderDesigntab();
	   notifPage.selectDaydropdownunderDesigntab();
	   notifPage.enterTimingunitUnderdesignTab();
	   
	}
	@Then("I verify that system should not show add button after adding fourth timing and timing unit")
	public void i_verify_that_system_should_not_show_add_button_after_adding_fourth_timing_and_timing_unit() throws InterruptedException {
	   assertFalse(notifPage.visibilityOfClickonAddbutton());
	   notifPage.saveChangesButton();
	   notifPage.clickOnBackArrow();	
	   
	}
	@When("I click on delete button of timing and timing unit")
	public void i_click_on_delete_button_of_timing_and_timing_unit() {
	   notifPage.deleteTimingunitUnderdesignTab();
	}
	@Then("I verify that system should show add button after deleting fourth timing and timing unit")
	public void i_verify_that_system_should_show_add_button_after_deleting_fourth_timing_and_timing_unit() throws InterruptedException {
		 assertTrue(notifPage.visibilityOfClickonAddbutton());
		 notifPage.clickOnBackArrow();	
	}
	@When("I add timings in timing and timing unit fields and save changes")
	public void i_add_timings_in_timing_and_timing_unit_fields_and_save_changes() throws InterruptedException {
		notifPage.ClickonAddbutton();
		notifPage.clickOntimingdropdownunderDesigntab();
		notifPage.selectDaydropdownunderDesigntab();
		notifPage.enterTimingunitUnderdesignTab();
		notifPage.saveChangesButton();
		notifPage.clickOnBackArrow();
	}
	@Then("I verify that system should redirect to the notification tab page after the changes are saved")
	public void i_verify_that_system_should_redirect_to_the_appointment_tab_page_after_the_changes_are_saved() {
	  assertTrue(notifPage.visibilityOfnotificationsTab());
	  
	}
	
	@When("I schedule an appointment in {string}")
	public void i_schedule_an_appointment_in(String language) throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com", language,
						propertyData.getProperty("patient.name")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	
	@Then("verify on appointment dashboard broadcast message and send reminder button not displayed")
	public void verify_on_appointment_dashboard_broadcast_message_and_send_reminder_button_not_displayed() throws InterruptedException {
		mainPage.clickOnAppointmentsTab();
		Thread.sleep(5000);
		apptPage.clickOnActions();
		log("verify user is not able to see reminder and broadcast message button in actions dropdown");
		assertFalse(apptPage.sendReminder(), "user is able to see send reminder button");
		assertFalse(apptPage.broadcastMessage(), "user is able to see broadcast message button");
		log("Enable notifications");
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		notifPage.onNotification();
		notifPage.saveNotification();
	}
	
	@When("I select practice language as {string} from notification in setting")
	public void i_select_practice_language_as_from_notification_in_setting(String english) throws InterruptedException {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		scrollAndWait(0, 200, 5000);
		notifPage.selectPracticeLanguagePreference(english);
		notifPage.saveNotification();
	}
	
	@When("I switch on appointment dashboard select patient and send broadcast message in english from action button")
	public void i_switch_on_appointment_dashboard_select_patient_and_send_broadcast_message_in_english_from_action_button() throws Exception {
		mainPage.clickOnAppointmentsTab();
		log("Click on Actions tab and select broadcast message");
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		apptPage.performAction();
		assertFalse(apptPage.visibilityOfEsTextbox());
		scrollAndWait(0, -2000, 5000);
		apptPage.sendBroadcastInEnglish(propertyData.getProperty("broadcast.message.en"));
		Thread.sleep(10000);
	}
	
	@Then("I verify on while sending broadcast only english language option text box should be seen in broadcast and Email should be recieved in english only")
	public void i_verify_on_while_sending_broadcast_only_english_language_option_text_box_should_be_seen_in_broadcast_and_email_should_be_recieved_in_english_only() throws NullPointerException, Exception {
		String practiceName=apptPage.getPracticeName();
		YopMail yopMail= new YopMail(driver);
	    assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
	    		"Important Message from"+" "+practiceName,propertyData.getProperty("patient.name")+ "," + " "
						+ propertyData.getProperty("broadcast.message.en"), 10));
	    loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	    mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		Thread.sleep(5000);
		notifPage.selectPracticeLanguagePreference("English & Spanish");
		notifPage.saveNotification();
	}
	
	@When("I switch on appointment dashboard select patient and send broadcast message in english and spanish from action button")
	public void i_switch_on_appointment_dashboard_select_patient_and_send_broadcast_message_in_english_and_spanish_from_action_button() throws Exception {
		mainPage.clickOnAppointmentsTab();
		log("Click on Actions tab and select broadcast message");
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		apptPage.performAction();
		assertTrue(apptPage.visibilityOfEnTextbox());
		assertTrue(apptPage.visibilityOfEsTextbox());
		scrollAndWait(0, -2000, 5000);
		apptPage.scrollOnBroadcastMsg();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		Thread.sleep(10000);
	}
	
	@Then("I verify on while sending broadcast in english and spanish language option text box should be seen in broadcast and Email should be recieved in english")
	public void i_verify_on_while_sending_broadcast_in_english_and_spanish_language_option_text_box_should_be_seen_in_broadcast_and_email_should_be_recieved_in_english() throws NullPointerException, Exception {
		String practiceName=apptPage.getPracticeName();
		YopMail yopMail= new YopMail(driver);
		log("Message should get in english and spanish");
	    assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
	    		"Important Message from"+" "+practiceName,propertyData.getProperty("patient.name")+ "," + " "
						+ propertyData.getProperty("broadcast.message.en"), 10));
	    
	    assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
	    		"Un mensaje importantede"+" "+practiceName,propertyData.getProperty("patient.name")+ "," + " "+ propertyData.getProperty("broadcast.message.en")+" / "+"Lo llamaremos en breve para recopilar la información de su seguro.", 10));
	    log("Make notification setting ON");
	    loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	    mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		log("user should be on notification page");
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		Thread.sleep(5000);
		notifPage.selectPracticeLanguagePreference("English & Spanish");
		notifPage.saveNotification();
	}
	
	@When("I click on settings tab")
	public void i_click_on_settings_tab() {
	   mainPage.clickOnSettingTab();
	}
	@When("I click on notifications tab")
	public void i_click_on_notifications_tab() {
	    notifPage.clickOnNotificationTab();
	}
	@When("I select send notifications as On and Save notifications")
	public void i_select_send_notifications_as_on_and_save_notifications() throws InterruptedException {
	   notifPage.onNotification();
	   notifPage.saveNotification();
	}
	@When("I click on appointments tab")
	public void i_click_on_appointments_tab() {
	   mainPage.clickOnAppointmentsTab();
	}
	@When("I schedule an appointment when notification is on")
	public void i_schedule_an_appointment_when_notification_is_on() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@When("I send reminder to the patient")
	public void i_send_reminder_to_the_patient() throws InterruptedException {
		 apptPage.filterPatientId(Appointment.patientId);
		 apptPage.selectFirstPatient();
		 apptPage.clickOnActions();
		 apptPage.clickOnSendReminder();
	}
	@Then("I verify system should send reminder,curbside mail to patient")
	public void i_verify_system_should_send_reminder_curbside_mail_to_patient() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),
				propertyData.getProperty("appointment.email.title"), 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),
				"Appointment Reminder", 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("curbside.checkin.mail.title"), 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		
	}
	@When("I select send notifications as Off and Save notifications")
	public void i_select_send_notifications_as_off_and_save_notifications() throws InterruptedException {
	    notifPage.offNotification();
	    notifPage.saveNotification();
	}
	@When("I schedule an appointment when notification is off")
	public void i_schedule_an_appointment_when_notification_is_off() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@Then("I verify system should not send reminder,curbside mail to patient")
	public void i_verify_system_should_not_send_reminder_curbside_mail_to_patient() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),
				propertyData.getProperty("appointment.email.title"), 5));
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),
				"Appointment Reminder", 5));
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("curbside.checkin.mail.title"), 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	@Then("I verify in appt dashboard reminder and broadcast column will not be visible")
	public void i_verify_in_appt_dashboard_reminder_and_broadcast_column_will_not_be_visible() {
		assertFalse(apptPage.sendReminderEmailColumn());
		assertFalse(apptPage.sendRemibderTextColumn());
		assertFalse(apptPage.broadcastMessageEmailColumn());
		assertFalse(apptPage.visibilityBroadcastMessageTextColumn());
	}
	@When("I select the patient and click on actions dropdown")
	public void i_select_the_patient_and_click_on_actions_dropdown() throws InterruptedException {
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectFirstPatient();
		apptPage.clickOnActions();
	}
	@Then("there will be no options for send a broadcast and send a reminder in Actions dropdown")
	public void there_will_be_no_options_for_send_a_broadcast_and_send_a_reminder_in_actions_dropdown() {
		assertFalse(apptPage.visibilityOfSendReminderButton());
		assertFalse(apptPage.visibilityOfBroadcastMessageButton());
	}
	@When("I go to settings tab")
	public void i_go_to_settings_tab() {
		mainPage.clickOnSettingTab();
	}
	@When("when from settings email checkbox is enable")
	public void when_from_settings_email_checkbox_is_enable() {
		generalPage.enableEmailCheckbox();
		
	}
	@When("I click on appointment tab")
	public void i_click_on_appointment_tab() {
	    mainPage.clickOnAppointmentsTab();
	}
	@When("I schedule an appointment when email checkbox is on")
	public void i_schedule_an_appointment_when_email_checkbox_is_on() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@When("I click on page {int} from appointment dashboard")
	public void i_click_on_page_from_appointment_dashboard(Integer int1) throws InterruptedException {
		apptPage.enterStartTimeWithinMonth();
		apptPage.jumpToNextPage();
		
	}
	@When("I select all {int} records from page {int}")
	public void i_select_all_records_from_page(Integer int1, Integer int2) throws InterruptedException {
		apptPage.selectAllCheckboxes();
	}
	@When("I click on remove button from actions dropdown")
	public void i_click_on_remove_button_from_actions_dropdown() throws InterruptedException {
	    apptPage.clickOnActions();
	    apptPage.clickOnRemoveButton();
	    apptPage.clickOnRefreshTab();
	    apptPage.jumpToPreviousPage();
	}
	@Then("I verify all {int} records should get deleted after deleting the records on  clicking refresh button previous page {int} records should come on page {int} records")
	public void i_verify_all_records_should_get_deleted_after_deleting_the_records_on_clicking_refresh_button_previous_page_records_should_come_on_page_records(Integer int1, Integer int2, Integer int3) throws InterruptedException {
	   assertTrue(apptPage.removeBannerMessage());
	}
	
	@When("I select appointment from appointment dashboard")
	public void i_select_appointment_from_appointment_dashboard() {
	  apptPage.selectFirstPatient();
	}
	@When("I click on Actions button")
	public void i_click_on_actions_button() {
	    apptPage.clickOnActions();
	}
	@When("I click on Send Reminder option and dont close the banner")
	public void i_click_on_send_reminder_option_and_dont_close_the_banner() throws InterruptedException {
	   apptPage.clickOnSendReminder();
	}
	@When("I click on broadcast message options")
	public void i_click_on_broadcast_message_options() throws Exception {
	  apptPage.selectFirstPatient();
	  apptPage.clickOnActions();
	  apptPage.clickOnBroadcastMessageButton();
	  apptPage.sendBroadcastMessage("welcome to curbside check-in", "Bienvenida al check-in en la acera");
	}
	@Then("I verify now on sending broadcast broadcast banner should get updated at the top right corner")
	public void i_verify_now_on_sending_broadcast_broadcast_banner_should_get_updated_at_the_top_right_corner() {
		assertEquals(apptPage.broadcastMessageStatus(), "Broadcast Message Sent. 0 successful. 1 failed.",
				"Message was not correct");
	}
	@When("I click on remove button options")
	public void i_click_on_remove_button_options() {
		apptPage.selectFirstPatient();
		apptPage.clickOnActions();
		apptPage.selectRemoveButton();
	}
	@Then("I verify now on removing appointment the appointment remove count  should get updated at the top right corner")
	public void i_verify_now_on_removing_appointment_the_appointment_remove_count_should_get_updated_at_the_top_right_corner() throws InterruptedException {
		 assertTrue(apptPage.removeBannerMessage());
	}
	@Then("I verify now on removing appointment the appointment remove count should get updated at the right corner")
	public void i_verify_now_on_removing_appointment_the_appointment_remove_count_should_get_updated_at_the_right_corner() throws InterruptedException {
		 assertTrue(apptPage.removeBannerMessage());
	}
	
	@When("I apply filter for start date and end date in curbside arrival grid")
	public void i_apply_filter_for_start_date_and_end_date_in_curbside_arrival_grid() throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		curbsidePage.selectOneDayBeforeDateforStartdate("22", "12.00 AM");
		curbsidePage.getCurrentEndDateAndTime();
	}
	@When("I switch to appointments tab and again switch to curbside arrival grid and I checkin two patients")
	public void i_switch_to_appointments_tab_and_again_switch_to_curbside_arrival_grid_and_I_checkin_two_patients() throws InterruptedException {
	   mainPage.clickOnAppointmentsTab();
	   mainPage.clickOnCurbsideTab();
	   curbsidePage.selectPatientscheckbox();
	   curbsidePage.selectPatientscheckbox2();
	   curbsidePage.clickOnCheckInButton();
	}
	@Then("I verify that after switching from appointments tab to curbside arrival grid filter resets to current date and time and check-in two patients then notifictaion count reduces")
	public void i_verify_that_after_switching_from_appointments_tab_to_curbside_arrival_grid_filter_resets_to_current_date_and_time_and_check_in_two_patients_then_notifictaion_count_reduces() {
		assertTrue(curbsidePage.visibiltyOfStartTimeFilter());
	    assertTrue(curbsidePage.visibiltyOfEndTimeFilter());
	    assertTrue(curbsidePage.visibilityOfNotifIcon());
	    
	}
	@When("I apply filter for start date as current date {int} and end date as current date {int} in curbside arrival grid")
	public void i_apply_filter_for_start_date_as_current_date_and_end_date_as_current_date_in_curbside_arrival_grid(Integer int1, Integer int2) throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		curbsidePage.selectOneDayBeforeDateforStartdate("22", "12.00 AM");
		curbsidePage.selectOneDayBeforeDate("22", "11.59 PM");
	}
	@When("I switch to appointments tab and again switch to curbside arrival grid and I checkin one patient")
	public void i_switch_to_appointments_tab_and_again_switch_to_curbside_arrival_grid_and_i_checkin_one_patient() throws InterruptedException {
		 mainPage.clickOnAppointmentsTab();
		 mainPage.clickOnCurbsideTab();
		 curbsidePage.selectPatientscheckbox();
		 curbsidePage.clickOnCheckInButton();
	}
	@Then("I verify that after switching from appointments tab to curbside arrival grid filter resets to current date and time and check-in one patient then notifictaion count reduces")
	public void i_verify_that_after_switching_from_appointments_tab_to_curbside_arrival_grid_filter_resets_to_current_date_and_time_and_check_in_one_patient_then_notifictaion_count_reduces() {
		assertTrue(curbsidePage.visibiltyOfStartTimeFilter());
	    assertTrue(curbsidePage.visibiltyOfEndTimeFilter());
	    assertTrue(curbsidePage.visibilityOfNotifIcon());
	}
	@When("I apply filter for start date as current date {int} and end date as current date in curbside arrival grid")
	public void i_apply_filter_for_start_date_as_current_date_and_end_date_as_current_date_in_curbside_arrival_grid(Integer int1) throws InterruptedException {
		mainPage.clickOnCurbsideTab();
		curbsidePage.selectOneDayBeforeDateforStartdate("22", "12.00 AM");
		curbsidePage.selectEndDate("22", "11.59PM");
	}
	@When("I switch to appointments tab and again switch to curbside arrival grid and I checkin all patients")
	public void i_switch_to_appointments_tab_and_again_switch_to_curbside_arrival_grid_and_i_checkin_all_patients() throws InterruptedException {
		 mainPage.clickOnAppointmentsTab();
		 mainPage.clickOnCurbsideTab();
		 curbsidePage.clickOnselectAllCheckbox();
		 curbsidePage.clickOnCheckInButton();
	}
	@Then("I verify that after switching from appointments tab to curbside arrival grid filter resets to current date and time and check-in all patients then notifictaion count becomes zero")
	public void i_verify_that_after_switching_from_appointments_tab_to_curbside_arrival_grid_filter_resets_to_current_date_and_time_and_check_in_all_patients_then_notifictaion_count_becomes_zero() {
		assertTrue(curbsidePage.visibiltyOfStartTimeFilter());
	    assertTrue(curbsidePage.visibiltyOfEndTimeFilter());
	    assertTrue(curbsidePage.visibilityOfNotifIcon());
	}
	@When("I schedule two appointments")
	public void i_schedule_two_appointments() throws NullPointerException, IOException {
		for (int i = 0; i < 2; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							propertyData.getProperty("mf.apt.scheduler.email")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);

			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
	}
	@When("I apply filter for start date as current date and end date as current date in curbside arrival grid")
	public void i_apply_filter_for_start_date_as_current_date_and_end_date_as_current_date_in_curbside_arrival_grid() throws InterruptedException {
	   mainPage.clickOnCurbsideTab();
	   curbsidePage.selectCurrentdateforStartdate("22", "12.00 AM");
	   curbsidePage.selectEndDate("22", "11.59 PM");
	   
	}
	@Then("I verify that after switching from appointments tab to curbside arrival grid filter resets to current date and time and check-in two patients then notifictaion count becomes reduces")
	public void i_verify_that_after_switching_from_appointments_tab_to_curbside_arrival_grid_filter_resets_to_current_date_and_time_and_check_in_two_patients_then_notifictaion_count_becomes_reduces() {
		assertTrue(curbsidePage.visibiltyOfStartTimeFilter());
	    assertTrue(curbsidePage.visibiltyOfEndTimeFilter());
	    assertTrue(curbsidePage.visibilityOfNotifIcon());
	}

	@When("I select all {int} records appointments from backdated {int} month")
	public void i_select_all_records_appointments_from_backdated_month(Integer int1, Integer int2) throws InterruptedException {
		apptPage.enterOneMonthBackdatedStartTime();
		apptPage.selectAllCheckboxes();
		
	}
	@When("I delete all {int} records appointments")
	public void i_delete_all_records_appointments(Integer int1) {
	    apptPage.clickOnActions();
	    apptPage.clickOnRemoveButton();
	}
	@Then("I verify all appointment whose entry does not exist in arrival grid should not get deleted rest appointment entry should get deleted")
	public void i_verify_all_appointment_whose_entry_does_not_exist_in_arrival_grid_should_not_get_deleted_rest_appointment_entry_should_get_deleted() {
	    assertTrue(apptPage.visibilityBannerMessage());
	}
	@When("I select all {int} records appointments from page {int}")
	public void i_select_all_records_appointments_from_page(Integer int1, Integer int2) throws InterruptedException {
		apptPage.enterOneMonthBackdatedStartTime();
		apptPage.selectAllCheckboxes();
	}
	@When("I delete all {int} records appointments from page {int} and click on refresh button")
	public void i_delete_all_records_appointments_from_page_and_click_on_refresh_button(Integer int1, Integer int2) throws InterruptedException {
	   apptPage.clickOnActions();
	   apptPage.clickOnRemoveButton();
	   apptPage.clickOnRefreshTab();
	}
	@Then("I verify all {int} records should get deleted and after deleting the records on clicking refresh button next page {int} records should come on page {int} records")
	public void i_verify_all_records_should_get_deleted_and_after_deleting_the_records_on_clicking_refresh_button_next_page_records_should_come_on_page_records(Integer int1, Integer int2, Integer int3) throws InterruptedException {
		assertTrue(apptPage.visibilityOfPageNo());
	}
	
	@When("I apply filter for location and provider on appointment page")
	public void i_apply_filter_for_location_and_provider_on_appointment_page() {
		apptPage.locationDropdown();
		apptPage.locationFilterSelected();
		apptPage.providerDropdown();
		apptPage.providerFilterSelected();
	}
	@When("I select all records from page {int}")
	public void i_select_all_records_from_page(Integer int1) throws InterruptedException {
	    apptPage.selectAllCheckboxes();
	}
	@Then("I verify all records should get deleted after deleting the records on  clicking refresh button previous page {int} records should come on page {int} records")
	public void i_verify_all_records_should_get_deleted_after_deleting_the_records_on_clicking_refresh_button_previous_page_records_should_come_on_page_records(Integer int1, Integer int2) {
		assertTrue(apptPage.visibilityOfPageNo());
	}

	@When("I schedule an appointment where mobile number is valid ten digit number but not US number")
	public void i_schedule_an_appointment_where_mobile_number_is_valid_ten_digit_number_but_not_us_number()
			throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@When("I send broadcast message on email")
	public void i_send_broadcast_message_on_email() throws Exception {
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectFirstPatient();
		apptPage.performAction();
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
			   propertyData.getProperty("broadcast.message.es"));
	}
	@Then("I verify system should send reminder,curbside,broadcast on email")
	public void i_verify_system_should_send_reminder_curbside_broadcast_on_email() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),
				propertyData.getProperty("appointment.email.title"), 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("broadcast.email.subject"),
				propertyData.getProperty("broadcast.email.title"), 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),
				"Appointment Reminder", 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("curbside.checkin.mail.title"), 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	@When("when from settings email checkbox is disable")
	public void when_from_settings_email_checkbox_is_disable() throws InterruptedException {
		generalPage.enableAndDisableEmailCheckbox();
		generalPage.clickOnUpdateSettingbutton();
	}
	@When("I schedule an appointment when email checkbox is off")
	public void i_schedule_an_appointment_when_email_checkbox_is_off() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@Then("I verify system should not send reminder,curbside on email")
	public void i_verify_system_should_not_send_reminder_curbside_on_email() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),
				propertyData.getProperty("appointment.email.title"), 5));
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),
				"Appointment Reminder", 5));
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("curbside.checkin.mail.title"), 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	
	@When("I schedule an appointment using schedule Appointment API")
	public void i_schedule_an_appointment_using_schedule_appointment_api() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}

	@Then("I verify if broadcast is sent sucessfully and status in broadcast log is failed with message sent")
	public void i_verify_if_broadcast_is_sent_sucessfully_and_status_in_broadcast_log_is_failed_with_message_sent()
			throws InterruptedException {
		Thread.sleep(20000);
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
		scrollAndWait(300, 2000, 10000);
		assertEquals(apptPage.getBroadcastMsgStatusForSelectedPatient(Appointment.patientId, Appointment.apptId),
				"FAILED", "Status was not match");
	}
	
	@When("from setting in notifications I click on curbside checkin tab")
	public void from_setting_in_notifications_i_click_on_curbside_checkin_tab() {
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.clickOnCurbsideCheckInTabInNotif();
	}

	@When("I click on english button and additional arrival instructions text box keep blank")
	public void i_click_on_english_button_and_additional_arrival_instructions_text_box_keep_blank()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		notifPage.clickOnEnglishButton();
		notifPage.clearArrivalInstTextbox();
	}

	@When("I click on save button")
	public void i_click_on_save_button() throws InterruptedException {
		notifPage.saveNotification();
	}

	@Then("I verify user is not able to see additional arrival instruction in mail for english")
	public void i_verify_user_is_not_able_to_see_additional_arrival_instruction_in_mail_for_english() throws Exception {
		YopMail yopMail = new YopMail(driver);
		assertNotEquals(
				yopMail.getMessageAfterArrival("jordan" + Appointment.randomNumber + "@YOPmail.com",
						propertyData.getProperty("curbside.checkin.mail.subject"),
						propertyData.getProperty("email.title.in.en"),
						propertyData.getProperty("add.arrival.instruction.in.en")),
				propertyData.getProperty("add.arrival.instruction.in.en"), "Message is displayed");
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.clickOnCurbsideCheckInTabInNotif();
		scrollAndWait(0, 500, 3000);
		notifPage.clickOnEnglishButton();
		notifPage.enterTextInArrivalInstTextbox(propertyData.getProperty("add.arrival.instruction.in.en"));
		notifPage.saveNotification();
	}
	
	@When("I click on spanish button and additional arrival instructions text box keep blank")
	public void i_click_on_spanish_button_and_additional_arrival_instructions_text_box_keep_blank()
			throws InterruptedException {
		scrollAndWait(0, 500, 3000);
		notifPage.clickOnSpanishButton();
		notifPage.clearArrivalInstTextbox();
	}

	@Then("I verify user is not able to see additional arrival instruction in mail for spanish")
	public void i_verify_user_is_not_able_to_see_additional_arrival_instruction_in_mail_for_spanish() throws Exception {
		YopMail yopMail = new YopMail(driver);
		assertNotEquals(
				yopMail.getMessageAfterArrival("jordan" + Appointment.randomNumber + "@YOPmail.com",
						propertyData.getProperty("curbside.checkin.mail.subject.in.es"),
						propertyData.getProperty("email.title.in.es"),
						propertyData.getProperty("add.arrival.instruction.in.es")),
				propertyData.getProperty("add.arrival.instruction.in.es"), "Message is displayed");
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		mainPage.clickOnSettingTab();
		notifPage.clickOnNotificationTab();
		assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		log("user on notification page");
		notifPage.clickOnCurbsideCheckInTabInNotif();
		scrollAndWait(0, 500, 3000);
		notifPage.clickOnSpanishButton();
		notifPage.enterTextInArrivalInstTextbox(propertyData.getProperty("add.arrival.instruction.in.es"));
		notifPage.saveNotification();
	}
	
	@When("I schedule an appointment for patient {string} after {int} minutes")
	public void i_schedule_an_appointment_for_patient_after_minutes(String patientName, int time)
			throws NullPointerException, IOException, InterruptedException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@Then("I verify appointment should be received from {string} in email")
	public void i_verify_appointment_should_be_received_from_in_email(String string) throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),
				"PSS-GE-24333-PRACTICE@@@#&%^&&%&^% <no-reply@medfusion.net>", 5));
		
	}
	@Then("I verify confirmation reminder for appointment should be received from {string} in email")
	public void i_verify_confirmation_reminder_for_appointment_should_be_received_from_in_email(String string) throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),
				"PSS-GE-24333-PRACTICE@@@#&%^&&%&^% <no-reply@medfusion.net>", 5));
	}
	@Then("I verify appointment should be received from {string} in email for curbside reminder")
	public void i_verify_appointment_should_be_received_from_in_email_for_curbside_reminder(String string) throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				"PSS-GE-24333-PRACTICE@@@#&%^&&%&^% <no-reply@medfusion.net>", 5));
	}
	@Then("I verify appointment should be received from {string} in email for broadcast reminder")
	public void i_verify_appointment_should_be_received_from_in_email_for_broadcast_reminder(String string) throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("broadcast.email.subject"),
				"PSS-GE-24333-PRACTICE@@@#&%^&&%&^% <no-reply@medfusion.net>", 5));
	}
	@Then("I verify appointment should be received from {string} in email for manual reminder")
	public void i_verify_appointment_should_be_received_from_in_email_for_manual_reminder(String string) throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),
				"PSS-GE-24333-PRACTICE@@@#&%^&&%&^% <no-reply@medfusion.net>", 5));
	}

	@When("I received curbside message on email and confirm arrival")
	public void i_received_curbside_message_on_email_and_confirm_arrival() throws NullPointerException, Exception {
		String currentWindow = driver.getWindowHandle();
		YopMail yopMail = new YopMail(driver);
		yopMail.confirmArrivalFromEmail("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("email.title.in.en"));
		driver.close();
		driver.switchTo().window(currentWindow);
		Thread.sleep(10000);
	}

	@Then("I verify patient1 has oldest time enrty so patient1 should get displayed at the top of patient2")
	public void i_verify_patient1_has_oldest_time_enrty_so_patient1_should_get_displayed_at_the_top_of_patient2()
			throws InterruptedException {
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		mainPage.clickOnCurbsideTab();
		Thread.sleep(5000);
		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
		assertEquals(curbsidePage.getPatient1FromCurbsideDev(),
				propertyData.getProperty("patient1.fname.lname"), "Entry of patient was not correct");
		assertEquals(curbsidePage.getPatient2FromCurbsideDev(),
				propertyData.getProperty("patient2.fname.lname"), "Entry of patient was not correct");
		}else {
			assertEquals(curbsidePage.getPatient1FromCurbsideDemo(),
					propertyData.getProperty("patient1.fname.lname"), "Entry of patient was not correct");
			assertEquals(curbsidePage.getPatient2FromCurbsideDemo(),
					propertyData.getProperty("patient2.fname.lname"), "Entry of patient was not correct");
		}

	}
	
	@Then("I verify latest patient arrived goes to the end of the line that is row is added to the bottom")
	public void i_verify_latest_patient_arrived_goes_to_the_end_of_the_line_that_is_row_is_added_to_the_bottom()
			throws InterruptedException {
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		mainPage.clickOnCurbsideTab();
		Thread.sleep(5000);
		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
		assertEquals(curbsidePage.getPatient1FromCurbsideDev(),
				propertyData.getProperty("patient1.fname.lname"), "Entry of patient was not correct");
		assertEquals(curbsidePage.getPatient2FromCurbsideDev(),
				propertyData.getProperty("patient2.fname.lname"), "Entry of patient was not correct");
		}else {
			assertEquals(curbsidePage.getPatient1FromCurbsideDemo(),
					propertyData.getProperty("patient1.fname.lname"), "Entry of patient was not correct");
			assertEquals(curbsidePage.getPatient2FromCurbsideDemo(),
					propertyData.getProperty("patient2.fname.lname"), "Entry of patient was not correct");
		}
	}
	
	@When("I click on logo tab")
	public void i_click_on_logo_tab() throws InterruptedException {
	    generalPage.clickOnlogoTab();
	}
	@When("I upload new logo image")
	public void i_upload_new_logo_image() throws InterruptedException, AWTException {
		generalPage.chooseFileforLogo(propertyData.getProperty("upload.practice.logo"));
		generalPage.clickOnuploadButton();
	}
	@Then("I verify on patient UI new logo image should be reflected after upload of new image")
	public void i_verify_on_patient_ui_new_logo_image_should_be_reflected_after_upload_of_new_image() {
		assertTrue(generalPage.visibilityOfLogoImage());
	}
	
	
	@When("I click on providers tab")
	public void i_click_on_providers_tab() {
		 generalPage.clickOnProvidersTab();
	}
	@When("I click on add a new provider button")
	public void i_click_on_add_a_new_provider_button() {
		generalPage.clickOnAddnewProviderbutton();
	}
	@When("I click on choose image link,add providerId,provider firstname,middlename,lastname,title")
	public void i_click_on_choose_image_link_add_provider_id_provider_firstname_middlename_lastname_title() throws InterruptedException, AWTException, IOException {
		   generalPage.providerImage(propertyData.getProperty("upload.provider.image"));	   
		   generalPage.providerId(propertyData.getProperty("provider.id"));
		   generalPage.firstName(propertyData.getProperty("provider.first.name"));
		   generalPage.middleName(propertyData.getProperty("provider.middle.name"));
		   generalPage.lastName(propertyData.getProperty("provider.last.name"));
		   generalPage.title(propertyData.getProperty("provider.title"));
	}
	@When("I click on save button for provider")
	public void i_click_on_save_button_for_provider() throws InterruptedException {
		generalPage.saveButton();
	}
	@When("I apply filter for provider")
	public void i_apply_filter_for_provider() {
		generalPage.Providerfilter();
	}
	@When("I delete the provider")
	public void i_delete_the_provider() {
		 generalPage.deleteProvider();
	}
	@Then("I verify user is able to add a provider and delete a provider")
	public void i_verify_user_is_able_to_add_a_provider_and_delete_a_provider() {
	    assertTrue(generalPage.visibilityofProviderfilter());
	    assertTrue(generalPage.visibilityofdeletedProvider());
	}

	@When("I click on practice preference language and select English and Spanish language from dropdown")
	public void i_click_on_practice_preference_language_and_select_english_and_spanish_language_from_dropdown() throws InterruptedException {
		notifPage.selectPracticeLanguagePreference("English & Spanish");
		notifPage.saveNotification();
	}
	@When("I schedule an appointment for English and Spanish language")
	public void i_schedule_an_appointment_for_english_and_spanish_language() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com","en-es",
						propertyData.getProperty("patient.name")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@Then("I verify system should send emails and text in English and Spanish language")
	public void i_verify_system_should_send_emails_and_text_in_english_and_spanish_language() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),"Appointment Scheduled Cita programada" , 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	@When("I click on practice preference language and select English language from dropdown")
	public void i_click_on_practice_preference_language_and_select_english_language_from_dropdown() throws InterruptedException {
		notifPage.selectPracticeLanguagePreference("English");
		notifPage.saveNotification();
	}
	@When("I schedule an appointment for English language")
	public void i_schedule_an_appointment_for_english_language() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
						propertyData.getProperty("patient.name")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@Then("I verify system should send emails and text in English language")
	public void i_verify_system_should_send_emails_and_text_in_english_language() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),
				propertyData.getProperty("appointment.email.title") , 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}

	@When("I enable display patients first name checkbox")
	public void i_enable_display_patients_first_name_checkbox() throws InterruptedException {
		notifPage.displayPatientFirstNameCheckbox();
	}
	@When("I switch on appointments tab")
	public void i_switch_on_appointments_tab() {
		mainPage.clickOnAppointmentsTab();
	}
	@When("I send manual reminder for the scheduled appointment")
	public void i_send_manual_reminder_for_the_scheduled_appointment() throws InterruptedException {
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectFirstPatient();
		apptPage.clickOnActions();
		apptPage.clickOnSendReminder();
	}
	@Then("I verify user is able to see first name in manual reminder,scheduled reminder,confirmation reminder,curbside reminder in mail")
	public void i_verify_user_is_able_to_see_first_name_in_manual_reminder_scheduled_reminder_confirmation_reminder_curbside_reminder_in_mail() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),"jordan, your appointment is coming up." , 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),"jordan, your appointment has been scheduled" , 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),"jordan, your appointment is coming up." , 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),"jordan, we provide curbside check-in. Click on the button below when you have arrived in the parking lot." , 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	@When("I send broadcast reminder for the scheduled appointment")
	public void i_send_broadcast_reminder_for_the_scheduled_appointment() throws Exception {
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectFirstPatient();
		apptPage.performAction();
	}
	@Then("I verify user is able to see first name in appointment scheduled reminder,appointment confirmation reminder , curbside reminder,broadcast in mail")
	public void i_verify_user_is_able_to_see_first_name_in_appointment_scheduled_reminder_appointment_confirmation_reminder_curbside_reminder_broadcast_in_mail() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),"jordan, your appointment is coming up." , 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),"jordan, your appointment has been scheduled" , 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),"jordan, your appointment is coming up." , 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),"jordan, we provide curbside check-in. Click on the button below when you have arrived in the parking lot." , 5));
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("broadcast.email.subject"),
				propertyData.getProperty("broadcast.email.title"), 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	@When("I disable display patients first name checkbox")
	public void i_disable_display_patients_first_name_checkbox() throws InterruptedException {
	   notifPage.disableDisplayPatientFirstNameCheckbox();
	}
	@Then("I verify user is not able to see first name in manual reminder,scheduled reminder,confirmation reminder,curbside reminderin mail")
	public void i_verify_user_is_not_able_to_see_first_name_in_manual_reminder_scheduled_reminder_confirmation_reminder_curbside_reminderin_mail() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"),"jordan, your appointment is coming up." , 5));
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appointment.email.subject"),"jordan, your appointment has been scheduled" , 5));
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),"jordan, we provide curbside check-in. Click on the button below when you have arrived in the parking lot." , 5));
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("broadcast.email.subject"),
				propertyData.getProperty("broadcast.email.title"), 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	
	@When("I enable broadcast messaging checkbox")
	public void i_enable_broadcast_messaging_checkbox() throws InterruptedException {
	    notifPage.enableBroadcastMessagingCheckbox();
	    notifPage.saveNotification();
	}
	@When("I click on appointment dashboard")
	public void i_click_on_appointment_dashboard() {
	   mainPage.clickOnAppointmentsTab();
	}
	@When("I select the patient checkbox")
	public void i_select_the_patient_checkbox() throws Exception {
	   apptPage.filterPatientId(Appointment.patientId);
	   apptPage.selectFirstPatient();
	}
	@Then("I verify that broadcast button is visible in actions dropdown in appointment dashboard")
	public void i_verify_that_broadcast_button_is_visible_in_actions_dropdown_in_appointment_dashboard() {
	    assertTrue(apptPage.broadcastMessage());
	}
	@When("I disable broadcast messaging checkbox")
	public void i_disable_broadcast_messaging_checkbox() throws InterruptedException {
	   notifPage.disableBroadcastMessagingCheckbox();
	   notifPage.saveNotification();
	}
	@Then("I verify that broadcast button is not visible in actions dropdown in appointment dashboard")
	public void i_verify_that_broadcast_button_is_not_visible_in_actions_dropdown_in_appointment_dashboard() {
		 assertFalse(apptPage.broadcastMessage());
	}
	
	@When("I click on curbside checkin")
	public void i_click_on_curbside_checkin() {
	   notifPage.clickOnCurbsideCheckInTabInNotif(); 
	}
	@When("I enter curbside arrival instruction msg for english and add {int} characters")
	public void i_enter_curbside_arrival_instruction_msg_for_english_and_add_characters(Integer int1) {
		notifPage.clickOnEnglishButton();
		notifPage.clearAdditionalArrivalInstTextboxEn();
		notifPage.addArrivalInstructionTextInEnglish("hello welcome to curbside check-in tab!!!!!!!!!!!hello welcome to curbside check-in tab!!!!!!!!!!.We will give you a message as soon as the doctor arrives .Sorry for the inconvenience happened to you.!!!!!!!!!We apologise for this. !!!!!!!!Thankyou for your cooperation...We will let you know this as soon as possible!!!!!!!!Please give us some time Once our Doctor arrives we will let you..!!!!!!!!!Please wait for some time...Thankyou for your patience....!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	@Then("I verify custom arrival instruction msg should allow to add up to {int} characters and in the left side corner it should show filled character count in left for spanish and english language after {int} character is filled system should not allow to update arrival instruction message")
	public void i_verify_custom_arrival_instruction_msg_should_allow_to_add_up_to_characters_and_in_the_left_side_corner_it_should_show_filled_character_count_in_left_for_spanish_and_english_language_after_character_is_filled_system_should_not_allow_to_update_arrival_instruction_message(Integer int1, Integer int2) {
	   assertEquals(notifPage.characterCount(),"(500/500 characters)");
	   
	}
	@When("I enter curbside arrival instruction msg for spanish and add {int} characters")
	public void i_enter_curbside_arrival_instruction_msg_for_spanish_and_add_characters(Integer int1) {
		notifPage.clickOnSpanishButton();
		notifPage.clearAdditionalArrivalInstTextboxEs();
		notifPage.addArrivalInstructionTextInSpanish("¡Hola, bienvenido a la pestaña de registro en la acera! ¡Hola, bienvenido a la pestaña de registro en la acera! .Disculpe las molestias que le sucedieron.!!!!!!!!Le pedimos disculpas por esto. !!!!!!!!Gracias por su cooperación... ¡Le informaremos lo antes posible! !!!!!!!!Por favor, espere un momento... Gracias por su paciencia....!!!!!!!!!!!!!!!!!!!!!!!! !!!!!!!!!!!!!!!!¡Hola, bienvenido a la pestaña de registro en la acera! ¡Hola, bienvenido a la pestaña de registro en la acera! .Disculpe las");
	}
	@Then("I verify custom arrival instruction msg should allow to add up to {int} characters and in the left side corner it should show filled character count in left for spanish language after {int} character is filled system should not allow to update arrival instruction message")
	public void i_verify_custom_arrival_instruction_msg_should_allow_to_add_up_to_characters_and_in_the_left_side_corner_it_should_show_filled_character_count_in_left_for_spanish_language_after_character_is_filled_system_should_not_allow_to_update_arrival_instruction_message(Integer int1, Integer int2) {
		assertEquals(notifPage.characterCount(),"(500/500 characters)");
	}
	@When("I enter curbside arrival instruction msg for english and add upto {int} characters")
	public void i_enter_curbside_arrival_instruction_msg_for_english_and_add_upto_characters(Integer int1) {
		notifPage.clickOnEnglishButton();
		notifPage.clearAdditionalArrivalInstTextboxEn();
		notifPage.addArrivalInstructionTextInEnglish("hello welcome to curbside check-in tab!!!!!!!!!!!hello welcome to curbside check-in tab...!!!!!!!!!!");
	}
	@Then("I verify when {int} character are entered then total character count should should be {int}\\/{int}")
	public void i_verify_when_character_are_entered_then_total_character_count_should_should_be(Integer int1, Integer int2, Integer int3) {
		 assertEquals(notifPage.characterCount(),"(100/500 characters)");
	}
	
	@When("I click on cadence editor template page and select hours,min,day")
	public void i_click_on_cadence_editor_template_page_and_select_hours_min_day() throws InterruptedException, IOException {
		notifPage.clickTimingDropdownunderDesigntab();
		notifPage.selectHourDropdownunderDesigntab();
		notifPage.enterTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.hour.one"));
		notifPage.click2ndTimingDropdownunderDesigntab();
		notifPage.selectMinutesTimingDropdownunderDesigntab();
		notifPage.enter2ndTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.minutes"));
		notifPage.click3rdTimingDropdownunderDesigntab();
		notifPage.selectDayDropdownunderDesigntab();
		notifPage.enter3rdTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.day"));
		notifPage.saveChangesButton();
		
	}
	@Then("I verify system should allow to edit template as per user point of view")
	public void i_verify_system_should_allow_to_edit_template_as_per_user_point_of_view() throws InterruptedException {
		assertTrue(notifPage.visibilityOfselectHourDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenterTimingUnitunderDesigntab());
	    assertTrue(notifPage.visibilityOfselectMinutesTimingDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenter2ndTimingUnitunderDesigntab());
	    assertTrue(notifPage.visibilityOfselectDayTimingDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenter3rdTimingUnitunderDesigntab());
	}
	
	@Then("I verify system should show timing units on template in proper format Days,Hours,Minutes")
	public void i_verify_system_should_show_timing_units_on_template_in_proper_format_days_hours_minutes() throws InterruptedException {
		scrollAndWait(200, 300, 5000);
		assertTrue(notifPage.visibilityOfTiming());
		assertTrue(notifPage.visibilityOfTimingUnit());
	}
	
	@When("I click on cadence editor template page and select min,hours,day")
	public void i_click_on_cadence_editor_template_page_and_select_min_hours_day() throws InterruptedException, IOException {
		notifPage.clickTimingDropdownunderDesigntab(); 
		notifPage.selectMinutesTimingDropdownunderDesigntab();
		notifPage.enter1stMinutesTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.minutes"));
		notifPage.click2ndTimingDropdownunderDesigntab();
		notifPage.selectHourDropdownunderDesigntab();
		notifPage.enter2ndHoursTimingUnitunderDesignTab(propertyData.getProperty("timing.unit.hour"));
		notifPage.click3rdTimingDropdownunderDesigntab();
		notifPage.selectDayDropdownunderDesigntab();
		notifPage.enter3rdTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.day"));
		notifPage.saveChangesButton();
	}
	@Then("I verify system should show timing units on template properly")
	public void i_verify_system_should_show_timing_units_on_template_properly() throws InterruptedException {
		assertTrue(notifPage.visibilityOfselectMinutesTimingDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenter1stMinutesTimingUnitunderDesigntab());
	    assertTrue(notifPage.visibilityOfselectHourDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenter2ndHoursTimingUnitunderDesignTab());
	    assertTrue(notifPage.visibilityOfselectDayTimingDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenter3rdTimingUnitunderDesigntab());
	}
	
	@When("I click on cadence editor template page and select first hour,min,hour and Day")
	public void i_click_on_cadence_editor_template_page_and_select_first_hour_min_hour_and_day() throws InterruptedException, IOException {
		notifPage.clickTimingDropdownunderDesigntab();
		notifPage.selectHourDropdownunderDesigntab();
		notifPage.enterTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.hour.one"));
		notifPage.click2ndTimingDropdownunderDesigntab();
		notifPage.selectMinutesTimingDropdownunderDesigntab();
		notifPage.enter2ndTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.minutes"));
		notifPage.click3rdTimingDropdownunderDesigntab();
		notifPage.select2ndHourDropdownunderDesigntab();
		notifPage.enter3rdHourtimingUnitunderDesigntab(propertyData.getProperty("timing.unit.hour"));
		notifPage.ClickonAddbutton();
		notifPage.click4thTimingDropdownunderDesigntab();
		notifPage.selectDayDropdownunderDesigntab();
		notifPage.enter4thTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.day"));
		notifPage.saveChangesButton();
		
	}
	@Then("I verify system should allow to edit template in proper format as per user point of view")
	public void i_verify_system_should_allow_to_edit_template_in_proper_format_as_per_user_point_of_view() throws InterruptedException {
		assertTrue(notifPage.visibilityOfselectHourDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenterTimingUnitunderDesigntab());
	    assertTrue(notifPage.visibilityOfselectMinutesTimingDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenter2ndTimingUnitunderDesigntab());
	    assertTrue(notifPage.visibilityOfselect2ndHourDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenter3rdHourtimingUnitunderDesigntab());
	    assertTrue(notifPage.visibilityOfselectDayDropdownunderDesigntab());
	    assertTrue(notifPage.visibilityOfenter4thTimingUnitunderDesigntab());
	
	}
	
	@Then("I verify system should show timing units on template in proper format hours,minutes,hours and day")
	public void i_verify_system_should_show_timing_units_on_template_in_proper_format_hours_minutes_hours_and_day() throws InterruptedException {
		scrollAndWait(0, -3000, 10000);
		assertTrue(notifPage.visibilityOfTiming());
		assertTrue(notifPage.visibilityOfTimingUnit());
	}
	
	@When("I hit edit button of {string} for appointment reminder")
	public void i_hit_edit_button_of_for_appointment_reminder(String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, 1000, 3000);
		notifPage.clickOnEditButtonHamburgerButton();
		log("User redirect on edit template design page");
		scrollAndWait(0, 200, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
	}

	@When("I delete all timing and timing unit and save configuration")
	public void i_delete_all_timing_and_timing_unit_and_save_configuration() throws InterruptedException {
		notifPage.checkingFourthTimingIfPresent();
		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
			notifPage.removeTimingAndTimingUnitDev();
		} else {
			notifPage.removeTimingAndTimingUnitDemo();
		}
		notifPage.clickOnSaveChangesbutton();
	}

	@Then("I verify for mail on appointment reminder section timing and timing units fields system show blank")
	public void i_verify_for_mail_on_appointment_reminder_section_timing_and_timing_units_fields_system_show_blank()
			throws InterruptedException {

		assertEquals(notifPage.getTimingTextForEmail(), "", "Blank value is not display for timing");
		assertEquals(notifPage.getTimingUnitTextForEmail(), "", "Blank value is not display for timing unit");

		scrollAndWait(0, 1000, 10000);
		notifPage.clickApptReminderEmailHamburgerButton();
		notifPage.clickOnEditButtonHamburgerButton();
		notifPage.addTimingAndTimingUnit();

		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
			notifPage.enterTimingAndTimingUnit(1, "Days", "1");
			notifPage.enterTimingAndTimingUnit(2, "Days", "3");
			notifPage.enterTimingAndTimingUnit(3, "Days", "5");
		} else {
			notifPage.enterTimingAndTimingUnitDemo(1, "Days", "1");
			notifPage.enterTimingAndTimingUnitDemo(2, "Days", "3");
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", "5");
		}
		notifPage.clickOnSaveChangesbutton();
	}

	@Then("I verify for text on appointment reminder section timing and timing units fields system show blank")
	public void i_verify_for_text_on_appointment_reminder_section_timing_and_timing_units_fields_system_show_blank()
			throws InterruptedException {
		assertEquals(notifPage.getTimingTextForSms(), "", "Display blank value for timing");
		assertEquals(notifPage.getTimingUnitTextForSms(), "", "Display blank value for timing unit");

		scrollAndWait(0, 1000, 5000);
		notifPage.clickApptReminderSmsHamburgerButton();
		notifPage.clickOnEditButtonHamburgerButton();
		notifPage.addTimingAndTimingUnit();

		if (IHGUtil.getEnvironmentType().equals("DEV3")) {
			notifPage.enterTimingAndTimingUnit(1, "Days", "1");
			notifPage.enterTimingAndTimingUnit(2, "Days", "3");
			notifPage.enterTimingAndTimingUnit(3, "Days", "5");
		} else {
			notifPage.enterTimingAndTimingUnitDemo(1, "Days", "1");
			notifPage.enterTimingAndTimingUnitDemo(2, "Days", "3");
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", "5");
		}
		notifPage.clickOnSaveChangesbutton();
	}
	
	@When("from mail I unsubscribe a patient")
	public void from_mail_i_unsubscribe_a_patient() throws NullPointerException, InterruptedException {
		YopMail yopMail = new YopMail(driver);
		String unsubscribeMessage = yopMail.unsubscribeEmail("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"));
		assertEquals(unsubscribeMessage,
				"You will no longer receive emails from PreCheck and reminder services. Please contact your practice if you wish to opt back in.",
				"Message was nor correct");
	}

	@When("I get count from email broadcast logs")
	public void i_get_count_from_email_broadcast_logs() throws InterruptedException {
		assertEquals(apptPage.getBroadcastEmailCountForSelectedPatient(Appointment.patientId, Appointment.apptId), "0",
				"Count for broadcast email logs was not match");
	}

	@When("I resubscribe patient mail")
	public void i_resubscribe_patient_mail() throws NullPointerException, IOException, InterruptedException {
		log("Delete Subscription Data");
		Response response = subsManager.deleteAllSubscriptionDataUsingEmailId(
				propertyData.getProperty("baseurl.mf.notif.subscription.manager"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()),
				"jordan" + Appointment.randomNumber + "@YOPmail.com");
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		driver.navigate().refresh();
		Thread.sleep(30000);
	}

	@Then("I verify after sending broadcast message count will be increases")
	public void i_verify_after_sending_broadcast_message_count_will_be_increases()
			throws NullPointerException, Exception {
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		log("banner meassage :" + apptPage.broadcastBannerMessage());
		driver.navigate().refresh();
		Thread.sleep(20000);
		assertEquals(apptPage.getBroadcastEmailCountForSelectedPatient(Appointment.patientId, Appointment.apptId), "1",
				"Count for broadcast email logs was not match");
	}
	
	@When("I schedule an appointment with invalid email and phone number")
	public void i_schedule_an_appointment_with_invalid_email_and_phone_number()
			throws NullPointerException, IOException, InterruptedException {
		mainPage.clickOnAppointmentsTab();
		Thread.sleep(5000);
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, "4566778119", "abc@mm.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		apptPage.clickOnRefreshTab();
	}
	
	@Then("I verify paper plane icon and logs shows red colur and status is failed")
	public void i_verify_paper_plane_icon_and_logs_shows_red_colur_and_status_is_failed() throws InterruptedException {
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		apptPage.clickOnActions();
		apptPage.clickOnSendReminder();
		Thread.sleep(10000);
		apptPage.clickOnRefreshTab();
		Thread.sleep(5000);
		assertEquals(
				apptPage.getColorForOneDayReminderStatus(Appointment.patientId, Appointment.apptId, 10,
						propertyData.getProperty("one.day.reminder.status.color")),
				propertyData.getProperty("one.day.reminder.status.color"), "Red color was not match");
		assertEquals(
				apptPage.getColorManualReminderStatus(Appointment.patientId, Appointment.apptId, 10,
						propertyData.getProperty("manual.reminder.status.color")),
				propertyData.getProperty("manual.reminder.status.color"), "Red color was not match");
		apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
		apptPage.clickOnViewAllForReminderStatus(Appointment.patientId, Appointment.apptId);
		scrollAndWait(0, -2000, 5000);
		assertEquals(apptPage.getFaildPriorDayIconColor(), propertyData.getProperty("one.day.reminder.status.color"),
				"Red color was not match");
		assertEquals(apptPage.getFaildManualIconColor(), propertyData.getProperty("one.day.reminder.status.color"),
				"Red color was not match");
		assertEquals(apptPage.getPriorDayReminderStatus(), "Failed", "Statue was not found as failed");
		assertEquals(apptPage.getManualReminderStatus(), "Failed", "Statue was not found as failed");
		apptPage.closeReminderStatusModal();
	}
	
	@When("I schedule an appointment with valid email and phone number")
	public void i_schedule_an_appointment_with_valid_email_and_phone_number() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}

	@When("again I schedule an appointment with same email and phone number")
	public void again_i_schedule_an_appointment_with_same_email_and_phone_number()
			throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}

	@Then("I verify paper plane icon and logs shows blank value")
	public void i_verify_paper_plane_icon_and_logs_shows_blank_value() throws InterruptedException {
		apptPage.selectPatient(Appointment.patientId, Appointment.apptId);
		apptPage.clickOnActions();
		apptPage.clickOnSendReminder();
		Thread.sleep(10000);
		apptPage.clickOnRefreshTab();
		Thread.sleep(5000);
		assertEquals(apptPage.getReminderStatus(Appointment.patientId, Appointment.apptId), "",
				"Remainder status does not shows blank value");
		apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
		assertEquals(apptPage.getReminderLogStatus(Appointment.patientId, Appointment.apptId), "None",
				"Remainder logs status does not shows blank value");
	}
	
	@When("I click on notifications")
	public void i_click_on_notifications() {
	    notifPage.clickOnNotificationTab();
	}
	@When("I enable Curbside checkin reminder checkbox")
	public void i_enable_curbside_checkin_reminder_checkbox() throws InterruptedException {
	   notifPage.enableCurbsideCheckinRemCheckbox();
	   notifPage.saveNotification();
	}
	@When("I schedule an appointment for curbside checkin reminder")
	public void i_schedule_an_appointment_for_curbside_checkin_reminder() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@Then("I verify system should send curbside checkin reminder within next one hour")
	public void i_verify_system_should_send_curbside_checkin_reminder_within_next_one_hour() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("curbside.checkin.mail.title"), 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	
	@When("I disable Curbside checkin reminder checkbox")
	public void i_disable_curbside_checkin_reminder_checkbox() throws InterruptedException {
	    notifPage.disableCurbsideCheckinRemCheckbox();
	    notifPage.saveNotification();
	}
	@Then("I verify system should not send curbside reminder")
	public void i_verify_system_should_not_send_curbside_reminder() throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("curbside.checkin.mail.title"), 5));
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	
	@When("I go to notifications tab")
	public void i_go_to_notifications_tab() {
	    notifPage.clickOnNotificationTab();
	}
	@When("I click on Curbside check-in of notification tab")
	public void i_click_on_curbside_check_in_of_notification_tab() {
	   notifPage.clickOnCurbsideCheckInTabInNotif();
	}
	@When("I add additional arrival instructions in English")
	public void i_add_additional_arrival_instructions_in_english() throws InterruptedException {
	    notifPage.clearAdditionalArrivalInstTextboxEn();
	    notifPage.addArrivalInstructionTextInEnglish("hello welcome to curbside checkin");
	    notifPage.saveNotification();
	}
	@When("I go to Appointment tab")
	public void i_go_to_appointment_tab() {
		mainPage.clickOnAppointmentsTab();
	}
	@When("I schedule an appointment and confirm the arrival from mail")
	public void i_schedule_an_appointment_and_confirm_the_arrival_from_mail() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("apt.precheck.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan" + Appointment.randomNumber + "@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	@Then("I verify user is able to see the arrival message after confirmation in mail")
	public void i_verify_user_is_able_to_see_the_arrival_message_after_confirmation_in_mail() throws NullPointerException, Exception {
		String currentWindow = driver.getWindowHandle();
		YopMail yopMail = new YopMail(driver);
		yopMail.confirmArrivalFromEmail("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("email.title.in.en"));
		driver.close();
		driver.switchTo().window(currentWindow);
		Thread.sleep(10000);
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	@When("I clear additional arrival instructions in English")
	public void i_clear_additional_arrival_instructions_in_english() throws InterruptedException {
		 notifPage.clearAdditionalArrivalInstTextboxEn();
		 notifPage.saveNotification();
	}
	@Then("I verify user is able to keep blank arrival instruction message after confirmation in mail")
	public void i_verify_user_is_able_to_keep_blank_arrival_instruction_message_after_confirmation_in_mail() throws NullPointerException, Exception {
		String currentWindow = driver.getWindowHandle();
		YopMail yopMail = new YopMail(driver);
		yopMail.getMessageAfterArrival("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("curbside.checkin.mail.subject"),
				propertyData.getProperty("email.title.in.en"), currentWindow);
		driver.close();
		driver.switchTo().window(currentWindow);
		Thread.sleep(10000);
		
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
	
	@When("I click on precheck tab")
	public void i_click_on_precheck_tab() {
	   precheckPage.clickOnPrecheckPage();
	}
	@When("I add patient mode completion message")
	public void i_add_patient_mode_completion_message() {
		precheckPage.clearPatientModeCompletionMessageTextBoxArea();
	    precheckPage.addPatientModeCompletionMessageTextBoxArea(propertyData.getProperty("patient.mode.completion.message"));
	    precheckPage.clickOnsaveChanges();
	}
	@Then("I verify that user is able to add or edit patient mode completion message")
	public void i_verify_that_user_is_able_to_add_or_edit_patient_mode_completion_message() {
	    assertTrue(precheckPage.visibilityOfPatientModeCompletionMessage());
	}
	
	@When("I click on general tab")
	public void i_click_on_general_tab() throws InterruptedException {
	    generalPage.clickOnGeneralTab();
	}
	@When("I add new practice display name and update changes")
	public void i_add_new_practice_display_name_and_update_changes() throws InterruptedException {
	    generalPage.clearPracticeDisplayName();
	    generalPage.addPracticeDisplayName(propertyData.getProperty("practice.name.ge"));
	    generalPage.clickOnUpdateSettingbutton();
	}
	@Then("I verify that practice display name is changed in appointment dashboard")
	public void i_verify_that_practice_display_name_is_changed_in_appointment_dashboard() {
	    assertEquals(apptPage.getPracticeName(),"PSS-GE-24333-PRACTICE","PSS-GE-24333-PRACTICE is not match");
	}
	@When("I add phone number")
	public void i_add_phone_number() {
		precheckPage.clearPhoneNumber();
		precheckPage.addPhoneNumber(propertyData.getProperty("phone.number"));
	}
	@When("I edit phone number")
	public void i_edit_phone_number() {
		precheckPage.clearPhoneNumber();
		precheckPage.editPhoneNumber(propertyData.getProperty("phone.number"));
	}
	@When("I click on save changes button")
	public void i_click_on_save_changes_button() {
	   precheckPage.clickOnsaveChanges();
	}
	@Then("I verify user is able to add or edit phone number")
	public void i_verify_user_is_able_to_add_or_edit_phone_number() {
		assertTrue(precheckPage.visbilityofAddPhoneNumber());
		assertTrue(precheckPage.visbilityofEditPhoneNumber());
	}
	@When("I click on forms tab")
	public void i_click_on_forms_tab() {
	   formsPage.clickOnFormsTab();
	}
	@When("I click on add form button")
	public void i_click_on_add_form_button() {
	   formsPage.clickOnAddFormButton();
	   formsPage.clickOnMedfusionForm();
	   formsPage.enterFormName(propertyData.getProperty("form.name"));
	   formsPage.enterFormURL(propertyData.getProperty("form.url"));
	   formsPage.clickOnAddformButton();
	}
	@Then("I verify user is able to add form in the forms grid")
	public void i_verify_user_is_able_to_add_form_in_the_forms_grid() {
	   assertTrue(formsPage.visibilityOfForms());
	}
	@When("I associate appointment type with form")
	public void i_associate_appointment_type_with_form() throws InterruptedException {
	   formsPage.clickOnAppointmentTypes();
	   formsPage.selectAppointmentType();
	   formsPage.clickOnBackArrow();
	}
	@Then("I verify user is able to associate appointment type to form")
	public void i_verify_user_is_able_to_associate_appointment_type_to_form() {
	   assertEquals(formsPage.countOfAppointmentType(),"1","1 is not match");
	}
	@When("I delete the form")
	public void i_delete_the_form() throws InterruptedException {
	   formsPage.clickOnDeleteForm();
	   formsPage.clickDeleteButton();
	}
	@Then("I verify user is able to delete the form and form cannot be seen in form grid")
	public void i_verify_user_is_able_to_delete_the_form_and_form_cannot_be_seen_in_form_grid() {
	    assertFalse(formsPage.visibilityOfForms());
	}
	@When("I add instructions for primary,secondary,tertiary")
	public void i_add_instructions_for_primary_secondary_tertiary() throws InterruptedException {
		precheckPage.clearPrimaryInstructionsMessage();
	    precheckPage.primaryInstructionsMessage(propertyData.getProperty("primary.instruction.message.english"));
	    precheckPage.clickPrimaryInstructionsMessageInSpanish();
	    precheckPage.clearPrimaryInstructionsMessageInSpanish();
	    precheckPage.primaryInstructionsMessageInSpanish(propertyData.getProperty("primary.instruction.message.spanish"));
	    precheckPage.saveChangesButton();
	}
	@Then("I verify that user is able to see instructions for primary,secondary,tertiary on UI")
	public void i_verify_that_user_is_able_to_see_instructions_for_primary_secondary_tertiary_on_ui() throws InterruptedException {
		assertTrue(precheckPage.visibilityofPrimaryInstructionsMessageInEnglish());
		precheckPage.clickPrimaryInstructionsMessageInSpanish();
		assertTrue(precheckPage.visibilityOfprimaryInstructionsMessageInSpanish());
	}

	@When("appointment should be scheduled and reminder & confirmation email should be received to patient")
	public void appointment_should_be_scheduled_and_reminder_confirmation_email_should_be_received_to_patient()
			throws NullPointerException, Exception {
		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.schedule.subject"), propertyData.getProperty("appt.schedule.title"),10));

		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("appt.email.subject"), propertyData.getProperty("appt.reminder.title"), 10));
	}

	@When("I do the precheck and update first name, middle name, last name")
	public void i_do_the_precheck_and_update_first_name_middle_name_last_name() throws NullPointerException, Exception {
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		apptPage.clickOnPatientName(Appointment.patientId, Appointment.apptId);
		scrollAndWait(0, -3000, 5000);
		apptPage.clickOnLaunchPatientModeButton();
		scrollAndWait(0, -3000, 5000);
		apptPage.clickOnContinueButton();
		apptPage.addPatientDetailsFromPrecheck(propertyData.getProperty("precheck.page.title"),
				propertyData.getProperty("precheck.first.name"), propertyData.getProperty("precheck.middle.name"),
				propertyData.getProperty("precheck.last.name"), "jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("precheck.phone.number"));
	}

	@Then("I verify updated first name, middle name, last name should be reflect on appt dashboard, broadcast email notification logs and on email")
	public void i_verify_updated_first_name_middle_name_last_name_should_be_reflect_on_appt_dashboard_broadcast_email_notification_logs_and_on_email()
			throws NullPointerException, Exception {

		loginPage.login(propertyData.getProperty("practice.provisining.username.ge"),
				propertyData.getProperty("practice.provisining.password.ge"));
		scrollAndWait(200, -300, 5000);
		String practiceName = apptPage.getPracticeName();

		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		log("Get patient name from appointment dashboard: "
				+ apptPage.getPatientNameFromApptDashboard(Appointment.patientId, Appointment.apptId));
		assertEquals(apptPage.getPatientNameFromApptDashboard(Appointment.patientId, Appointment.apptId),
				propertyData.getProperty("precheck.first.name") + " " + propertyData.getProperty("precheck.middle.name")
						+ " " + propertyData.getProperty("precheck.last.name"),
				"Patient first name , middle name and  last name was not match");

		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		apptPage.clickOnBroadcastEmailLogForSelectedPatient(Appointment.patientId, Appointment.apptId);
		log("Get patient name from Broadcast Email Log: "
				+ apptPage.getPatientNameFromBroadcastEmailLogs(Appointment.patientId, Appointment.apptId));
		assertEquals(apptPage.getPatientNameFromBroadcastEmailLogs(Appointment.patientId, Appointment.apptId),
				propertyData.getProperty("precheck.first.name") + " " + propertyData.getProperty("precheck.middle.name")
						+ " " + propertyData.getProperty("precheck.last.name"),
				"Patient first name , middle name and  last name was not match");
		apptPage.closeBroadcastEmailandTextBox();
		Thread.sleep(3000);

		apptPage.clickOnBroadcastPhoneLogForSelectedPatient(Appointment.patientId, Appointment.apptId);
		log("Get patient name from Broadcast Email Log: "
				+ apptPage.getPatientNameFromBroadcastTextLogs(Appointment.patientId, Appointment.apptId));
		assertEquals(apptPage.getPatientNameFromBroadcastTextLogs(Appointment.patientId, Appointment.apptId),
				propertyData.getProperty("precheck.first.name") + " " + propertyData.getProperty("precheck.middle.name")
						+ " " + propertyData.getProperty("precheck.last.name"),
				"Patient first name , middle name and  last name was not match");
		apptPage.closeBroadcastEmailandTextBox();

		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		Thread.sleep(20000);

		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com", practiceName,
				propertyData.getProperty("precheck.first.name") + "," + " "+ propertyData.getProperty("broadcast.message.en"),10));

		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}

	@When("I do the precheck and update first name, middle name, last name, email and phone number")
	public void i_do_the_precheck_and_update_first_name_middle_name_last_name_email_and_phone_number()
			throws InterruptedException {
		Appointment.randomNumber = commonMethod.generateRandomNum();
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		apptPage.clickOnPatientName(Appointment.patientId, Appointment.apptId);
		scrollAndWait(0, -3000, 5000);
		apptPage.clickOnLaunchPatientModeButton();
		scrollAndWait(0, -3000, 5000);
		apptPage.clickOnContinueButton();
		apptPage.addPatientDetailsFromPrecheck(propertyData.getProperty("precheck.page.title"),
				propertyData.getProperty("precheck.first.name"), propertyData.getProperty("precheck.middle.name"),
				propertyData.getProperty("precheck.last.name"), "jordan" + Appointment.randomNumber + "@YOPmail.com",
				propertyData.getProperty("precheck.phone.number"));
	}

		@Then("I verify updated first name, middle name, last name, email should be reflect on appt dashboard, broadcast email notification logs and on email")
	public void i_verify_updated_first_name_middle_name_last_name_email_should_be_reflect_on_appt_dashboard_broadcast_email_notification_logs_and_on_email()
			throws NullPointerException, Exception {
		
		loginPage.login(propertyData.getProperty("practice.provisining.username.ge"),
				propertyData.getProperty("practice.provisining.password.ge"));
		scrollAndWait(200, -300, 5000);
		String practiceName = apptPage.getPracticeName();
		
		apptPage.filterPatientId(Appointment.patientId);
		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		log("Get patient name from appointment dashboard: "
				+ apptPage.getPatientNameFromApptDashboard(Appointment.patientId, Appointment.apptId));
		assertEquals(apptPage.getPatientNameFromApptDashboard(Appointment.patientId, Appointment.apptId),
				propertyData.getProperty("precheck.first.name") + " " + propertyData.getProperty("precheck.middle.name")
						+ " " + propertyData.getProperty("precheck.last.name"),
				"Patient first name , middle name and  last name was not match");

		assertEquals(apptPage.getPatientEmailFromApptDashboard(Appointment.patientId, Appointment.apptId),
				"jordan" + Appointment.randomNumber + "@YOPmail.com", "Patient email was not match");

		assertEquals(apptPage.getPatientPhoneFromApptDashboard(Appointment.patientId, Appointment.apptId),
				propertyData.getProperty("precheck.phone"), "Patient phone was not match");

		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		apptPage.clickOnBroadcastEmailLogForSelectedPatient(Appointment.patientId, Appointment.apptId);
		log("Get patient name from Broadcast Email Log: "
				+ apptPage.getPatientNameFromBroadcastEmailLogs(Appointment.patientId, Appointment.apptId));
		assertEquals(apptPage.getPatientNameFromBroadcastEmailLogs(Appointment.patientId, Appointment.apptId),
				propertyData.getProperty("precheck.first.name") + " " + propertyData.getProperty("precheck.middle.name")
						+ " " + propertyData.getProperty("precheck.last.name"),
				"Patient first name , middle name and  last name was not match");
		apptPage.closeBroadcastEmailandTextBox();

		apptPage.clickOnBroadcastPhoneLogForSelectedPatient(Appointment.patientId, Appointment.apptId);
		log("Get patient name from Broadcast Email Log: "
				+ apptPage.getPatientNameFromBroadcastTextLogs(Appointment.patientId, Appointment.apptId));
		assertEquals(apptPage.getPatientNameFromBroadcastTextLogs(Appointment.patientId, Appointment.apptId),
				propertyData.getProperty("precheck.first.name") + " " + propertyData.getProperty("precheck.middle.name")
						+ " " + propertyData.getProperty("precheck.last.name"),
				"Patient first name , middle name and  last name was not match");
		apptPage.closeBroadcastEmailandTextBox();

		apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
		log("Click on Actions tab and select broadcast message");
		apptPage.performAction();
		log("Enter message in English and Spanish");
		apptPage.sendBroadcastMessage(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		Thread.sleep(20000);

		YopMail yopMail = new YopMail(driver);
		assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com", practiceName,
				propertyData.getProperty("precheck.first.name") + "," + " "
						+ propertyData.getProperty("broadcast.message.en"),
				10));
		loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
	}
		
		@When("I am able to click on > expand icon")
		public void i_am_able_to_click_on_expand_icon() throws InterruptedException {
			driver.navigate().refresh();
			apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
		}

		@When("on appointment dashboard default icon should be display")
		public void on_appointment_dashboard_default_icon_should_be_display() throws InterruptedException {
			apptPage.filterPatientId(Appointment.patientId);
			scrollAndWait(0, 3000, 5000);
			assertTrue(apptPage.visibilityOfDefaultIconForEmailReminder(Appointment.patientId, Appointment.apptId));
			assertTrue(apptPage.visibilityOfDefaultIconForTextReminder(Appointment.patientId, Appointment.apptId));
		}

		@Then("I verify after getting cadance reminder default icon is replace with paper plane and on that paper plane icon count {int} will display")
		public void i_verify_after_getting_cadance_reminder_default_icon_is_replace_with_paper_plane_and_on_that_paper_plane_icon_count_will_display(
				Integer int1) throws InterruptedException {
			assertTrue(apptPage.visibilityOfPaperPlaneIconForEmailReminder(Appointment.patientId, Appointment.apptId, 10));
			assertTrue(apptPage.visibilityOfPaperPlaneIconForTextReminder(Appointment.patientId, Appointment.apptId, 10));
			assertEquals(apptPage.getCountForEmailReminder(Appointment.patientId, Appointment.apptId), "1",
					"Email count not match");
			assertEquals(apptPage.getCountForTextReminder(Appointment.patientId, Appointment.apptId), "1",
					"Email count not match");
		}
		
		@When("I go to settings tab and click on notifications tab")
		public void i_go_to_settings_tab_and_click_on_notifications_tab() {
		    mainPage.clickOnSettingTab();
		    notifPage.clickOnNotificationTab();
		}
		@When("I disable the display patient first name and save the notifications")
		public void i_disable_the_display_patient_first_name_and_save_the_notifications() throws InterruptedException {
		   notifPage.disableDisplayPatientFirstNameCheckbox();
		   notifPage.saveNotification();
		}
		@When("I schedule an appointment and I receive the appointment scheduled confirmation mail")
		public void i_schedule_an_appointment_and_i_receive_the_appointment_scheduled_confirmation_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify appointment scheduled confirmation mail recieved should not show first name")
		public void i_verify_appointment_scheduled_confirmation_mail_recieved_should_not_show_first_name() throws NullPointerException, Exception {
		   YopMail yopMail = new YopMail(driver);
		   assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
				   propertyData.getProperty("appt.schedule.subject"),
				   propertyData.getProperty("patient.name"),5));
		   
		   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		   mainPage.clickOnSettingTab();
		   notifPage.clickOnNotificationTab();
		   notifPage.displayPatientFirstNameCheckbox();
		}
		@When("I schedule an appointment and I receive the appointment reminder in mail")
		public void i_schedule_an_appointment_and_i_receive_the_appointment_reminder_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify appointment reminder recieved in mail should not show first name")
		public void i_verify_appointment_reminder_recieved_in_mail_should_not_show_first_name() throws NullPointerException, Exception {
			 YopMail yopMail = new YopMail(driver);
			   assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("appt.email.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
			   mainPage.clickOnSettingTab();
			   notifPage.clickOnNotificationTab();
			   notifPage.displayPatientFirstNameCheckbox();
		}
		@When("I schedule an appointment and I receive the broadcast message in mail")
		public void i_schedule_an_appointment_and_i_receive_the_broadcast_message_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@When("I send broadcast message to patient")
		public void i_send_broadcast_message_to_patient() throws Exception {
		   apptPage.filterPatientId(Appointment.patientId);
		   apptPage.selectFirstPatient();
		   apptPage.performAction();
		   apptPage.sendBroadcastMessage("Hello!!", "Hola!!");
		}
		@Then("I verify broadcast message recieved in mail should not show first name")
		public void i_verify_broadcast_message_recieved_in_mail_should_not_show_first_name() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
			   assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("broadcast.email.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
			   mainPage.clickOnSettingTab();
			   notifPage.clickOnNotificationTab();
			   notifPage.displayPatientFirstNameCheckbox();
		}
		@When("I schedule an appointment and I receive the curbside reminder in mail")
		public void i_schedule_an_appointment_and_i_receive_the_curbside_reminder_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify curbside reminder recieved in mail should not show first name")
		public void i_verify_curbside_reminder_recieved_in_mail_should_not_show_first_name() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
			   assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("curbside.checkin.mail.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
			   mainPage.clickOnSettingTab();
			   notifPage.clickOnNotificationTab();
			   notifPage.displayPatientFirstNameCheckbox();
		}
		@When("I schedule an appointment and I receive the manual reminder in mail")
		public void i_schedule_an_appointment_and_i_receive_the_manual_reminder_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@When("I send manual reminder for that appointment")
		public void i_send_manual_reminder_for_that_appointment() throws InterruptedException {
			apptPage.filterPatientId(Appointment.patientId);
			apptPage.selectFirstPatient();
			apptPage.clickOnActions();
			apptPage.clickOnSendReminder();
		}
		@Then("I verify manual reminder recieved in mail should not show first name")
		public void i_verify_manual_reminder_recieved_in_mail_should_not_show_first_name() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
			   assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("appt.email.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
			   mainPage.clickOnSettingTab();
			   notifPage.clickOnNotificationTab();
			   notifPage.displayPatientFirstNameCheckbox();
		}
		
		@Then("I verify system should show by default days configured in days section and in timing units {double} configured")
		public void i_verify_system_should_show_by_default_days_configured_in_days_section_and_in_timing_units_configured(Double double1) throws InterruptedException {
		    assertTrue(notifPage.visibilityOfDefaultTiming1ForDay());
		    assertTrue(notifPage.visibilityOfDefaultTiming2ForDay());
		    assertTrue(notifPage.visibilityOfDefaultTiming3ForDay());
		    assertTrue(notifPage.visibilityOfDefaultTimingUnit1ForDay());
		    assertTrue(notifPage.visibilityOfDefaultTimingUnit2ForDay());
		    assertTrue(notifPage.visibilityOfDefaultTimingUnit3ForDay());
		    notifPage.clickOnBackArrow();
		}
		@When("I click on dropdown of timing")
		public void i_click_on_dropdown_of_timing() throws InterruptedException {
			notifPage.clickTimingDropdownunderDesigntab();
		}
		@Then("I verify system should show only {int} fields in dropdown of timing that is Day,hours,minutes")
		public void i_verify_system_should_show_only_fields_in_dropdown_of_timing_that_is_day_hours_minutes(Integer int1) throws InterruptedException {
			assertTrue(notifPage.visibilityOfselectDayForTiming());
			notifPage.clickTimingDropdownunderDesigntab();
		    assertTrue(notifPage.visibilityOfselectHourforTiming());
		    notifPage.clickTimingDropdownunderDesigntab();
		    assertTrue(notifPage.visibilityOfselectMinutesforTiming());
		    notifPage.clickOnBackArrow();
		}
		@When("I enter {int} to unlimited numbers in timing units")
		public void i_enter_to_unlimited_numbers_in_timing_units(Integer int1) {
		    notifPage.enterTimingUnitUnderDesignTab(propertyData.getProperty("timing.unit.days"));
		    notifPage.saveChangesButton();
		}
		@Then("I verify system should allow user to enter values from {int} to no limit")
		public void i_verify_system_should_allow_user_to_enter_values_from_to_no_limit(Integer int1) throws InterruptedException {
		   assertTrue(notifPage.visibilityTimingUnitTextUnderDesignTab());
		   notifPage.clickOnBackArrow();
		}
		@When("I select Hours and enter {int} to {int} number in timing units")
		public void i_select_hours_and_enter_to_number_in_timing_units(Integer int1, Integer int2) throws InterruptedException {
			notifPage.clickTimingDropdownunderDesigntab();
			notifPage.selectHourforTiming();
			notifPage.enterTimingUnit(propertyData.getProperty("timing.unit.new"));
			notifPage.saveChangesButton();
		}
		@Then("I verify system should allow only {int} to {int} number in the timing units section")
		public void i_verify_system_should_allow_only_to_number_in_the_timing_units_section(Integer int1, Integer int2) throws InterruptedException {
		    assertTrue(notifPage.visibilityofenterTimingUnit());
		}
		@When("I select minutes and enter {int} number in timing units")
		public void i_select_minutes_and_enter_number_in_timing_units(Integer int1) throws InterruptedException {
			notifPage.clickTimingDropdownunderDesigntab();
			notifPage.selectMinutesforTiming();
			notifPage.enterTimingUnitforMinutes(propertyData.getProperty("timing.unit.minutes"));
			notifPage.saveChangesButton();
		}
		@Then("I verify system should allow only {int} integer in the timing units section")
		public void i_verify_system_should_allow_only_integer_in_the_timing_units_section(Integer int1) {
			assertTrue(notifPage.visibilityofenterTimingUnit());
		}
		@When("I select minutes and enter {int} number in timing unit")
		public void i_select_minutes_and_enter_number_in_timing_unit(Integer int1) throws InterruptedException {
			notifPage.clickTimingDropdownunderDesigntab();
			notifPage.selectMinutesforTiming();
			notifPage.enterTimingUnitforMinutes(propertyData.getProperty("timing.unit.minutes.new"));
			notifPage.saveChangesButton();
		}
		@Then("I verify system should allow only {int} integer in the timing unit section")
		public void i_verify_system_should_allow_only_integer_in_the_timing_unit_section(Integer int1) {
			assertTrue(notifPage.visibilityofenterTimingUnit());
		    
		}

		@When("I select patient and send manual reminder")
		public void i_select_patient_and_send_manual_reminder() throws InterruptedException {
			mainPage.clickOnAppointmentsTab();
			Thread.sleep(5000);
			apptPage.filterPatientId(Appointment.patientId);
			apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
			scrollAndWait(-1000, 0, 20000);
			apptPage.clickOnActions();
			apptPage.clickOnSendReminder();
		}
		
		@Then("I verify for email and text reminder system should show all manual and cadence reminder log on mails history  pop up")
		public void i_verify_for_email_and_text_reminder_system_should_show_all_manual_and_cadence_reminder_log_on_mails_history_pop_up()
				throws InterruptedException {
			assertTrue(apptPage.visibilityOfPaperPlaneIconForEmailReminder(Appointment.patientId, Appointment.apptId, 10));
			assertTrue(apptPage.visibilityOfPaperPlaneIconForTextReminder(Appointment.patientId, Appointment.apptId, 10));
			apptPage.filterPatientId(Appointment.patientId);
			apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
			scrollAndWait(-3000, 0, 5000);
			apptPage.clickOnViewAllForEmailReminder(Appointment.patientId, Appointment.apptId);
			log("Get details for email reminder logs");
			assertTrue(apptPage.visibilityOfMailReminderLogTitle(Appointment.patientId, Appointment.apptId));
			log("Cadence reminder histroy detals");
			assertTrue(apptPage.visibilityOfMailReminderLogs(Appointment.patientId, Appointment.apptId, 1));
			assertEquals(apptPage.getTextFromEmailRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("one.day.prior.logs")),
					propertyData.getProperty("one.day.prior.logs"), "1 hour prior entry was not match");
			assertTrue(apptPage.visibilityOfMailReminderLogs(Appointment.patientId, Appointment.apptId, 3));
			assertEquals(apptPage.getTextFromEmailRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("one.day.prior.logs.status")), propertyData.getProperty("one.day.prior.logs.status"),
					"Status was not match");
			log("Manual reminder histroy detals");
			assertTrue(apptPage.visibilityOfMailReminderLogs(Appointment.patientId, Appointment.apptId, 5));
			assertEquals(apptPage.getTextFromEmailRemLogs(Appointment.patientId, Appointment.apptId,
					propertyData.getProperty("one.day.prior.logs")),
					propertyData.getProperty("one.day.prior.logs"), "1 hour prior entry was not match");
			assertTrue(apptPage.visibilityOfMailReminderLogs(Appointment.patientId, Appointment.apptId, 7));
			assertEquals(apptPage.getTextFromEmailRemLogs(Appointment.patientId, Appointment.apptId,
					propertyData.getProperty("one.day.prior.logs.status")), propertyData.getProperty("one.day.prior.logs.status"),
					"Status was not match");
			apptPage.closeReminderLogPopup();

			log("Get details for text remonder logs");
			apptPage.clickOnViewAllForTextReminder(Appointment.patientId, Appointment.apptId);
			log("Manual reminder histroy detals");
			assertTrue(apptPage.visibilityOfTextReminderLogTitle(Appointment.patientId, Appointment.apptId));
			assertTrue(apptPage.visibilityOfTextReminderLogs(Appointment.patientId, Appointment.apptId, 1));
			assertEquals(apptPage.getTextFromTextRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("one.day.prior.logs")),
					propertyData.getProperty("one.day.prior.logs"), "1 hour prior entry was not match");
			assertTrue(apptPage.visibilityOfTextReminderLogs(Appointment.patientId, Appointment.apptId, 3));
			assertEquals(apptPage.getTextFromTextRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("one.day.prior.logs.status")), propertyData.getProperty("one.day.prior.logs.status"),
					"Status was not match");

			assertTrue(apptPage.visibilityOfTextReminderLogs(Appointment.patientId, Appointment.apptId, 5));
			assertEquals(apptPage.getTextFromTextRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("one.day.prior.logs")),
					propertyData.getProperty("one.day.prior.logs"), "1 hour prior entry was not match");
			assertTrue(apptPage.visibilityOfTextReminderLogs(Appointment.patientId, Appointment.apptId, 7));
			assertEquals(apptPage.getTextFromTextRemLogs(Appointment.patientId, Appointment.apptId,
					propertyData.getProperty("one.day.prior.logs.status")), propertyData.getProperty("one.day.prior.logs.status"),
					"Status was not match");
			apptPage.closeReminderLogPopup();
		}
		
		@When("I enable the display patient first name and save the notifications")
		public void i_enable_the_display_patient_first_name_and_save_the_notifications() throws InterruptedException {
		   notifPage.displayPatientFirstNameCheckbox();
		   notifPage.saveNotification();
		}
		@When("I schedule an appointment and I receive the appointment scheduled confirmation in mail")
		public void i_schedule_an_appointment_and_i_receive_the_appointment_scheduled_confirmation_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify appointment scheduled confirmation mail recieved should show first name")
		public void i_verify_appointment_scheduled_confirmation_mail_recieved_should_show_first_name() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
			   assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("appt.schedule.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		@When("I schedule an appointment and I get the appointment reminder in mail")
		public void i_schedule_an_appointment_and_i_get_the_appointment_reminder_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify appointment reminder recieved in mail should show first name")
		public void i_verify_appointment_reminder_recieved_in_mail_should_show_first_name() throws NullPointerException, Exception {
			 YopMail yopMail = new YopMail(driver);
			   assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("appt.email.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		@When("I schedule an appointment and I receive the broadcast message reminder in mail")
		public void i_schedule_an_appointment_and_i_receive_the_broadcast_message_reminder_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify broadcast message recieved in mail should show first name")
		public void i_verify_broadcast_message_recieved_in_mail_should_show_first_name() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
			   assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("broadcast.email.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		@When("I schedule an appointment and I get the curbside reminder in mail")
		public void i_schedule_an_appointment_and_i_get_the_curbside_reminder_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify curbside reminder recieved in mail should show first name")
		public void i_verify_curbside_reminder_recieved_in_mail_should_show_first_name() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
			   assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("curbside.checkin.mail.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		@When("I schedule an appointment and I get the manual reminder in mail")
		public void i_schedule_an_appointment_and_i_get_the_manual_reminder_in_mail() throws NullPointerException, IOException {
			mainPage.clickOnAppointmentsTab();
			
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify manual reminder recieved in mail should show first name")
		public void i_verify_manual_reminder_recieved_in_mail_should_show_first_name() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
			   assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
					   propertyData.getProperty("appt.email.subject"),
					   propertyData.getProperty("patient.name"),5));
			   
			   loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		@When("I click on 4th timing unit")
		public void i_click_on_4th_timing_unit() throws NullPointerException, InterruptedException, IOException {
		    notifPage.addFourthTimingAndTimingUnit();
		    notifPage.enter4thTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.day"));
		}
		@When("I click on delete button of 4th timing unit")
		public void i_click_on_delete_button_of_4th_timing_unit() {
			notifPage.clickOnRemoveTiming();
		}
		@Then("I verify that system should allow user to delete timing units from page")
		public void i_verify_that_system_should_allow_user_to_delete_timing_units_from_page() throws InterruptedException {
			 assertTrue(notifPage.visibilityOfRemoveTimingUnit());
			 notifPage.clickOnBackArrow();
		}
		@Then("I verify add button should display in enable format")
		public void i_verify_add_button_should_display_in_enable_format() throws InterruptedException {
			assertTrue(notifPage.visibilityOfAddButton());
			notifPage.clickOnBackArrow();
		}
		@When("I click on delete button of 4th and 3rd timing unit")
		public void i_click_on_delete_button_of_4th_and_3rd_timing_unit() {
			notifPage.clickOnRemoveTiming();
			notifPage.removeButtonInEditFor3rdTimingUnit();
		}
		@Then("I verify add button should display in enable format if I delete {int} timing units")
		public void i_verify_add_button_should_display_in_enable_format_if_i_delete_timing_units(Integer int1) throws InterruptedException {
			assertTrue(notifPage.visibilityOfAddButton());
			notifPage.clickOnBackArrow();
		}
		@When("I click on delete button of all timing unit fields")
		public void i_click_on_delete_button_of_all_timing_unit_fields() {
			notifPage.removeButtonInEditFor3rdTimingUnit();
			notifPage.removeButtonInEditFor2ndTimingUnit();
			notifPage.removeButtonInEditFor1stTimingUnit();
			notifPage.saveChangesButton();
		}
		@Then("I verify system should allow user to delete all timing units fields and in notification tab the timing and timing unit section blank space should display")
		public void i_verify_system_should_allow_user_to_delete_all_timing_units_fields_and_in_notification_tab_the_timing_and_timing_unit_section_blank_space_should_display() {
			assertTrue(notifPage.visibilityOfTimingUnitsInNotifpage());
		}
		@When("I click on add button on cadence editor template page")
		public void i_click_on_add_button_on_cadence_editor_template_page() throws NullPointerException, InterruptedException, IOException {
		    notifPage.ClickonAddbutton();
		    notifPage.ClickonAddbutton();
		    notifPage.enter2ndTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.seconddefault"));
		    notifPage.ClickonAddbutton();
		    notifPage.enter3rdTimingUnitunderDesigntab(propertyData.getProperty("timing.unit.third.default"));
		    
		}
		@Then("I verify system should allow user to add timing fields on cadence editor template page")
		public void i_verify_system_should_allow_user_to_add_timing_fields_on_cadence_editor_template_page() {
			assertTrue(notifPage.visibilityOfClickonAddbutton());
			notifPage.saveChangesButton();
		}
		
		@When("I am on the reminder section of the appointment reminder")
		public void i_am_on_the_reminder_section_of_the_appointment_reminder() throws InterruptedException {
		   notifPage.clickApptReminderEmailHamburgerButton();
		   notifPage.clickOnEditButtonHamburgerButton();
		   notifPage.clickOnBackArrow();
		}
		@When("I click on appointments tabs")
		public void i_click_on_appointments_tabs() {
			mainPage.clickOnAppointmentsTab();
		}
		@When("I go to appointment dashboard and select one user from appointment dashboard")
		public void i_go_to_appointment_dashboard_and_select_one_user_from_appointment_dashboard() throws InterruptedException {
		   apptPage.filterPatientId(Appointment.patientId);
		}
		@When("I click on {string} icon of the selected patient from dashboard")
		public void i_click_on_icon_of_the_selected_patient_from_dashboard(String string) throws InterruptedException {
			driver.navigate().refresh();
			apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
		}
		@Then("I verify system should show latest cadence reminder status on page")
		public void i_verify_system_should_show_latest_cadence_reminder_status_on_page() throws InterruptedException {
			assertTrue(apptPage.visibilityOfPaperPlaneIconForEmailReminder(Appointment.patientId, Appointment.apptId, 10));
			assertTrue(apptPage.visibilityOfPaperPlaneIconForTextReminder(Appointment.patientId, Appointment.apptId, 10));
			apptPage.filterPatientId(Appointment.patientId);
			apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
			apptPage.clickOnViewAllForEmailReminder(Appointment.patientId, Appointment.apptId);
			assertTrue(apptPage.visibilityOfMailReminderLogs(Appointment.patientId, Appointment.apptId, 1));
			assertEquals(apptPage.getTextFromEmailRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("one.day.prior.logs")),
					propertyData.getProperty("one.day.prior.logs"), "1 hour prior entry was not match");
			assertTrue(apptPage.visibilityOfMailReminderLogs(Appointment.patientId, Appointment.apptId, 3));
			assertEquals(apptPage.getTextFromEmailRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("one.day.prior.logs.status")), propertyData.getProperty("one.day.prior.logs.status"),
					"Status was not match");
		}
		@When("I select one user from appointment dashboard and send manual reminder")
		public void i_select_one_user_from_appointment_dashboard_and_send_manual_reminder() throws InterruptedException {
			apptPage.filterPatientId(Appointment.patientId);
			apptPage.selectPatientCheckbox(Appointment.patientId, Appointment.apptId);
			apptPage.clickOnActions();
			apptPage.clickOnSendReminder();
		}
		@Then("I verify system should show manual reminder status on page")
		public void i_verify_system_should_show_manual_reminder_status_on_page() throws InterruptedException {
			assertTrue(apptPage.visibilityOfPaperPlaneIconForEmailReminder(Appointment.patientId, Appointment.apptId, 10));
			assertTrue(apptPage.visibilityOfPaperPlaneIconForTextReminder(Appointment.patientId, Appointment.apptId, 10));
			apptPage.filterPatientId(Appointment.patientId);
			apptPage.clickOnExpandForSelectedPatient(Appointment.patientId, Appointment.apptId);
			apptPage.clickOnViewAllForEmailReminder(Appointment.patientId, Appointment.apptId);
			assertTrue(apptPage.visibilityOfMailReminderLogs(Appointment.patientId, Appointment.apptId, 1));
			assertEquals(apptPage.getTextFromEmailRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("manual.log")),
					propertyData.getProperty("manual.log"), "Manual was not match");
			assertTrue(apptPage.visibilityOfMailReminderLogs(Appointment.patientId, Appointment.apptId, 3));
			assertEquals(apptPage.getTextFromEmailRemLogs(Appointment.patientId, Appointment.apptId, 
					propertyData.getProperty("one.day.prior.logs.status")), propertyData.getProperty("one.day.prior.logs.status"),
					"Status was not match");
		}
		
		@When("I click on email edit section of appointment reminders from setting in notifications tab")
		public void i_click_on_email_edit_section_of_appointment_reminders_from_setting_in_notifications_tab() throws InterruptedException {
			mainPage.clickOnSettingTab();
			notifPage.clickOnNotificationTab();
			log("user move to notification page");
			assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
			scrollAndWait(0, 2000, 5000);
			notifPage.clickApptReminderEmailHamburgerButton();
			log("From Appointment reminders open edit section for text");
			notifPage.clickApptReminderEmailEditButton();
		}
		
		@Then("I verify add button functionality for {string}")
		public void i_verify_add_button_functionality_for(String deliveryMethod) throws InterruptedException {
			scrollAndWait(0, 500, 5000);
			assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
			log("Delivery Method:" + notifPage.getDeliveryMethod());
			notifPage.addThreeTimingAndTimingUnit();
			assertTrue(notifPage.isAddButtonEnable());

			log("Add fourth timing and timing unit");
			notifPage.addTimingUnit();
			assertFalse(notifPage.isAddButtonEnable());
			
			log("Delete fourth timing and timing unit");
			notifPage.removeTimingAndTimingUnit(4);
			assertTrue(notifPage.isAddButtonEnable());
			
			log("Delete two more timing and timing unit");
			notifPage.removeTimingAndTimingUnit(3);
			notifPage.removeTimingAndTimingUnit(2);
			assertTrue(notifPage.isAddButtonEnable());

			log("Delete All timing and timing unit");
			notifPage.removeTimingAndTimingUnit(1);
			notifPage.clickOnSaveChangesButton();
			Thread.sleep(5000);
			assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		}
		
		@Then("I save cadence for email")
		public void i_save_cadence_for_email() throws InterruptedException {
			log("From Appointment reminders open edit section for text");
			notifPage.clickApptReminderEmailHamburgerButton();
			notifPage.clickApptReminderEmailEditButton();
			scrollAndWait(0, 500, 5000);
			notifPage.addThreeTimingAndTimingUnit();
			Appointment.day1 = notifPage.enterDays();
			notifPage.enterTimingAndTimingUnit(1, "Days", Appointment.day1);
			Appointment.day2 = notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.day2);
			Appointment.day3 = notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnit(3, "Minutes", Appointment.day3);
			notifPage.clickOnSaveChangesButton();
			log("user move to notification page");
			assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		}
		
		@When("I click on text edit section of appointment reminders from setting in notifications tab")
		public void i_click_on_text_edit_section_of_appointment_reminders_from_setting_in_notifications_tab() throws InterruptedException {
			mainPage.clickOnSettingTab();
			notifPage.clickOnNotificationTab();
			log("user move to notification page");
			assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
			log("From Appointment reminders open edit section for text");
			scrollAndWait(0, 2000, 5000);
			notifPage.clickApptReminderSmsHamburgerButton();
			notifPage.clickApptReminderSmsEditButton();
		}
		
		@Then("I save cadence for text")
		public void i_save_cadence_for_text() throws InterruptedException {
			log("From Appointment reminders open edit section for text");
			notifPage.clickApptReminderSmsHamburgerButton();
			notifPage.clickApptReminderSmsEditButton();
			scrollAndWait(0, 500, 5000);
			notifPage.addThreeTimingAndTimingUnit();
			Appointment.day1 = notifPage.enterDays();
			notifPage.enterTimingAndTimingUnit(1, "Days", Appointment.day1);
			Appointment.day2 = notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.day2);
			Appointment.day3 = notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnit(3, "Minutes", Appointment.day3);
			notifPage.clickOnSaveChangesButton();
			log("user move to notification page");
			assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
		}
		
		@When("I schedule an appointment in spanish {string} and have confirmed there arrival")
		public void i_schedule_an_appointment_in_spanish_and_have_confirmed_there_arrival(String language)  throws Exception, IOException {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan"+Appointment.randomNumber+"@YOPmail.com",language,propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);

			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"), propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
		
		@When("I select patient and click on dropdown from curbside checkin page")
		public void i_select_patient_and_click_on_dropdown_from_curbside_checkin_page() throws InterruptedException {
			curbsidePage.filterPatientId(Appointment.patientId);
			curbsidePage.selectPatientCheckbox();
			curbsidePage.clickOnSelectedPatientDropdown(Appointment.apptId);
		}
		
		@When("I send {string} message to selected patient")
		public void i_send_message_to_selected_patient(String message) throws InterruptedException {
			loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
			mainPage.clickOnCurbsideTab();
			assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
			log("User is on Curbside check in tab");
			curbsidePage.selectMessageFromDropdown(Appointment.apptId,message);
			curbsidePage.clickOnSendButtonOfSelectedPatient(Appointment.apptId);
		}
		
		@When("I received message {string} in email in spanish language")
		public void i_received_message_in_email_in_spanish_language(String message) throws Exception {
			scrollAndWait(0, -500, 5000);
			YopMail yopMail = new YopMail(driver);
			assertTrue(yopMail.isMessageInInbox("jordan"+Appointment.randomNumber+"@YOPmail.com","Actualización sobre su registro en la acera",propertyData.getProperty("patient.name")+", "+message, 10));
		}
		
		@Then("I verify message {string} receive in spanish language")
		public void i_verify_message_receive_in_spanish_language(String message) throws Exception {
			scrollAndWait(0, -500, 5000);
			YopMail yopMail = new YopMail(driver);
			assertTrue(yopMail.isMessageInInbox("jordan"+Appointment.randomNumber+"@YOPmail.com","Actualización sobre su registro en la acera",propertyData.getProperty("patient.name")+", "+message, 10));
			loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}

		@When("I clear all appointments from on curbside checkin tab")
		public void i_clear_all_appointments_from_on_curbside_checkin_tab() throws InterruptedException {
			mainPage.clickOnCurbsideTab();
			assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
			log("User is on Curbside check in tab");
			notifPage.checkingCheckinButton();
			log("Curbside button is disable");
			curbsidePage.selectAllAppointment();
			notifPage.clickOnCheckinButton();
			log("Clear all appointment");
		}
		
		@When("on nitification icon {string} count should be displayed on the top left corner")
		public void on_nitification_icon_count_should_be_displayed_on_the_top_left_corner(String count) {
			assertEquals(curbsidePage.getNotificationCount(), count, "Notification Count is not match");
		}
		
		@When("I schedule {int} appointments and finish curbside checkin process from mail by click on I have arrived")
		public void i_schedule_appointments_and_finish_curbside_checkin_process_from_mail_by_click_on_i_have_arrived(Integer count) throws Exception {
			for (int i = 0; i < count; i++) {
				Appointment.patientId = commonMethod.generateRandomNum();
				Appointment.apptId = commonMethod.generateRandomNum();
				Appointment.randomNumber = commonMethod.generateRandomNum();
				long currentTimestamp = System.currentTimeMillis();
				long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(20);
				apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
						propertyData.getProperty("apt.precheck.practice.id"),
						payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
								"jordan"+Appointment.randomNumber+"@YOPmail.com"),
						headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
						Appointment.apptId);

				Thread.sleep(3000);
				scrollAndWait(0, -500, 5000);
				String currentWindow = driver.getWindowHandle();
				YopMail yopMail = new YopMail(driver);
				String arrivalConfirmed=yopMail.confirmArrivalFromEmail("jordan"+Appointment.randomNumber+"@YOPmail.com",
						propertyData.getProperty("curbside.checkin.mail.subject"),propertyData.getProperty("curbside.checkin.mail.title"));
			assertEquals(arrivalConfirmed,"Arrival confirmed");
			driver.close();
			driver.switchTo().window(currentWindow);
			}
		}
		
		@When("I logged into practice provisioning and view the notification icon count {string} on the top left corner")
		public void i_logged_into_practice_provisioning_and_view_the_notification_icon_count_on_the_top_left_corner(String count) {
		    loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
			log("User is on Curbside check in tab");
			assertTrue(curbsidePage.visibilityOfNotifIcon());
			assertEquals(curbsidePage.getNotificationCount(), count, "Notification Count is not match");
		}
		
		@When("I switch on curbside checkin tab and select patient and click on check in button")
		public void i_switch_on_curbside_checkin_tab_and_select_patient_and_click_on_check_in_button() throws InterruptedException {
			mainPage.clickOnCurbsideTab();
			assertTrue(curbsidePage.getCurbsideTitle().contains("Curbside Check-ins"));
			Thread.sleep(10000);
			curbsidePage.filterPatientId(Appointment.patientId);
			curbsidePage.selectPatientCheckbox();
			curbsidePage.clickOnCheckInButton();
			Thread.sleep(10000);
		}
		
		@When("I verify on nitification icon {string} count should be displayed on the top left corner")
		public void i_verify_on_nitification_icon_count_should_be_displayed_on_the_top_left_corner(String count) {
			assertEquals(curbsidePage.getNotificationCount(), count, "Notification Count is not match");
		}
		
		@When("I click on curbside check-in of notifications tab")
		public void i_click_on_curbside_check_in_of_notifications_tab() {
		   notifPage.clickOnCurbsideCheckInTabInNotif();
		}
		@Then("I verify default arrival instruction message for spanish and english section is not editable")
		public void i_verify_default_arrival_instruction_message_for_spanish_and_english_section_is_not_editable() {
		    notifPage.clickOnEnglishButton();
		    assertEquals(notifPage.visibilityOfArrivalConfirmationMsgInEnglish(),"Arrival confirmation message"," Arrival confirmation message is not displayed");
		    notifPage.clickOnSpanishButton();
		    assertEquals(notifPage.visibilityOfArrivalConfirmationMsgInSpanish(),"Arrival confirmation message"," Arrival confirmation message is not displayed");
		}
		@Then("I verify arrival confirmation additional instruction text is displayed and textbox is displayed below for English and spanish language")
		public void i_verify_arrival_confirmation_additional_instruction_text_is_displayed_and_textbox_is_displayed_below_for_english_and_spanish_language() {
		    notifPage.clickOnEnglishButton();
			assertEquals(notifPage.getAdditionalArrivalInstructionMsgTextInEnglish(),"hello welcome to curbside checkin","text not match");
		    notifPage.clickOnSpanishButton();
			assertEquals(notifPage.getAdditionalArrivalInstructionMsgTextInSpanish(),"hola bienvenido al registro en la acera","text not match");
		}
		@When("I click on additional arrival instruction for English section")
		public void i_click_on_additional_arrival_instruction_for_english_section() throws InterruptedException {
			notifPage.clickOnEnglishButton();
			notifPage.clearAdditionalArrivalInstTextboxEn();
			notifPage.saveNotification();
		}
		@Then("I verify arrival confirmation additional instruction text is displayed and blank textbox is displayed below for English and spanish language")
		public void i_verify_arrival_confirmation_additional_instruction_text_is_displayed_and_blank_textbox_is_displayed_below_for_english_and_spanish_language() throws InterruptedException {
			assertEquals(notifPage.AdditionalArrivalInstTextboxBlankEn(), "",
					"Additional arrival instrunction textbox is not blank");
			notifPage.addArrivalInstructionTextInEnglish(propertyData.getProperty("add.arrival.instruction.in.en"));
			notifPage.saveNotification();
		}
		@When("I click on additional arrival instruction for Spanish section")
		public void i_click_on_additional_arrival_instruction_for_spanish_section() throws InterruptedException {
		   notifPage.clickOnSpanishButton();
		   notifPage.clearAdditionalArrivalInstTextboxEs();
		   notifPage.saveNotification();
		}
		@Then("I verify arrival confirmation additional instruction text is displayed and blank textbox is displayed below for Spanish language")
		public void i_verify_arrival_confirmation_additional_instruction_text_is_displayed_and_blank_textbox_is_displayed_below_for_spanish_language() throws InterruptedException {
			assertEquals(notifPage.additionalArrivalInstTextboxBlankEs(), "",
					"Additional arrival instrunction textbox is not blank");
			notifPage.addArrivalInstructionTextInSpanish(propertyData.getProperty("add.arrival.instruction.in.es"));
			notifPage.saveNotification();
		}
		@When("I click on additional arrival instruction for English section and add arrival instruction")
		public void i_click_on_additional_arrival_instruction_for_english_section_and_add_arrival_instruction() throws InterruptedException {
			notifPage.clickOnEnglishButton();
			notifPage.clearAdditionalArrivalInstTextboxEn();
			notifPage.addArrivalInstructionTextInEnglish(propertyData.getProperty("additional.arrival.instruction.en.new"));
			notifPage.saveNotification();
		}
		@Then("I verify arrival confirmation additional instruction text msg length should not exceeds above {int} character")
		public void i_verify_arrival_confirmation_additional_instruction_text_msg_length_should_not_exceeds_above_character(Integer int1) throws InterruptedException {
			assertEquals(notifPage.characterCount(),"(500/500 characters)","count not match");
			notifPage.addArrivalInstructionTextInEnglish(propertyData.getProperty("add.arrival.instruction.in.en"));
			notifPage.saveNotification();
		}
		@When("I click on additional arrival instruction for Spanish section and add arrival instruction")
		public void i_click_on_additional_arrival_instruction_for_spanish_section_and_add_arrival_instruction() throws InterruptedException {
			notifPage.clickOnSpanishButton();
			notifPage.clearAdditionalArrivalInstTextboxEs();
			notifPage.addArrivalInstructionTextInSpanish(propertyData.getProperty("additional.arrival.instruction.es.new"));
			notifPage.saveNotification();
		}
		@Then("I verify arrival confirmation additional instruction text msg length should not exceeds above {int} character for Spanish language")
		public void i_verify_arrival_confirmation_additional_instruction_text_msg_length_should_not_exceeds_above_character_for_spanish_language(Integer int1) throws InterruptedException {
			assertEquals(notifPage.characterCount(),"(500/500 characters)","count not match");
			notifPage.addArrivalInstructionTextInSpanish(propertyData.getProperty("add.arrival.instruction.in.es"));
			notifPage.saveNotification();
		}
		@When("I select English as preferred language")
		public void i_select_english_as_preferred_language() throws InterruptedException {
		   notifPage.clickOnPracticePrefLangDropDown();
		   notifPage.clickOnEnglishPracticePrefLang();
		}
		@When("I schedule an appointment and confirm arrival message")
		public void i_schedule_an_appointment_and_confirm_arrival_message() throws NullPointerException, IOException {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify arrival confirmation default msg should be received to patient in English language for email and text")
		public void i_verify_arrival_confirmation_default_msg_should_be_received_to_patient_in_english_language_for_email_and_text() throws NullPointerException, Exception {
			String currentWindow = driver.getWindowHandle();
			YopMail yopMail = new YopMail(driver);
			yopMail.confirmArrivalFromEmail("jordan" + Appointment.randomNumber + "@YOPmail.com",
					propertyData.getProperty("curbside.checkin.mail.subject"),
					propertyData.getProperty("email.title.in.en"));
			driver.close();
			driver.switchTo().window(currentWindow);
			Thread.sleep(10000);
			
			loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		
		@Then("I verify after user able to add above five hundred character in default arrival instruction message for english and it should shows filled character count in left")
		public void i_verify_after_user_able_to_add_above_five_hundred_character_in_default_arrival_instruction_message_for_english_and_it_should_shows_filled_character_count_in_left() {
				notifPage.enterCharInAddArrivalInstTextBox(propertyData.getProperty("additional.arrival.inst.msg.en"));
				assertEquals(notifPage.getMaxLengthChar(),"(500/500 characters)","Character count was not same");
		}
		
		@Then("I click on save for english and redirect to notification dashboard")
		public void i_click_on_save_for_english_and_redirect_to_notification_dashboard() throws InterruptedException {
			notifPage.saveNotification();
			log("user should be on notification page");
			assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
			scrollAndWait(0, -500, 3000);
			notifPage.clickOnCurbsideCheckInTabInNotif();
			notifPage.clickOnEnglishButton();
			notifPage.enterCharInAddArrivalInstTextBox(propertyData.getProperty("arrival.inst.msg.in.en"));
			notifPage.saveNotification();
		}
		
		@Then("I verify user able to enter hundred characters in arrival instructions text box for english then count should show correct")
		public void i_verify_user_able_to_enter_hundred_characters_in_arrival_instructions_text_box_for_english_then_count_should_show_correct() {
			notifPage.enterCharInAddArrivalInstTextBox(propertyData.getProperty("hundred.char.arrival.inst.msg.en"));
			assertEquals(notifPage.getMaxLengthChar(),"(100/500 characters)","Character count was not same");
		}
		
		@Then("I verify after user able to add above five hundred character in default arrival instruction message for spanish and it should shows filled character count in left")
		public void i_verify_after_user_able_to_add_above_five_hundred_character_in_default_arrival_instruction_message_for_spanish_and_it_should_shows_filled_character_count_in_left() {
			notifPage.enterCharInAddArrivalInstTextBox(propertyData.getProperty("additional.arrival.inst.msg.es"));
			assertEquals(notifPage.getMaxLengthChar(),"(500/500 characters)","Character count was not same");
		}
		
		@Then("I click on save for spanish and redirect to notification dashboard")
		public void i_click_on_save_for_spanish_and_redirect_to_notification_dashboard() throws InterruptedException {
			notifPage.saveNotification();
			log("user should be on notification page");
			assertTrue(notifPage.getNotificationTitle().contains("Notifications"));
			scrollAndWait(0, -500, 2000);
			notifPage.clickOnCurbsideCheckInTabInNotif();
			notifPage.clickOnSpanishButton();
			notifPage.enterCharInAddArrivalInstTextBox(propertyData.getProperty("arrival.inst.msg.in.es"));
			notifPage.saveNotification();
			scrollAndWait(0, -500, 2000);
		}
		
		@Then("I verify user able to enter hundred characters in arrival instructions text box for spanish then count should show correct")
		public void i_verify_user_able_to_enter_hundred_characters_in_arrival_instructions_text_box_for_spanish_then_count_should_show_correct() {
			notifPage.enterCharInAddArrivalInstTextBox(propertyData.getProperty("hundred.char.arrival.inst.msg.es"));
			assertEquals(notifPage.getMaxLengthChar(),"(100/500 characters)","Character count was not same");
		}
		
		@When("I click on curbside checkin tab of notification tab and add additional arrival instruction for English section")
		public void i_click_on_curbside_checkin_tab_of_notification_tab_and_add_additional_arrival_instruction_for_english_section() throws InterruptedException {
			notifPage.clickOnCurbsideCheckInTabInNotif();
			notifPage.clickOnPracticePrefLangDropDown();
			notifPage.clickOnEnglishPracticePrefLang();
			notifPage.clickOnEnglishButton();
			notifPage.addArrivalInstructionTextInEnglish(propertyData.getProperty("additional.arrival.inst.msg.en"));
			notifPage.saveNotification();
		}
		@When("I schedule an appointment with English language and confirm arrival message")
		public void i_schedule_an_appointment_with_english_language_and_confirm_arrival_message() throws NullPointerException, IOException {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify arrival confirmation default msg + custom msg should be received to patient in English language for email and text")
		public void i_verify_arrival_confirmation_default_msg_custom_msg_should_be_received_to_patient_in_english_language_for_email_and_text() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
			assertEquals(yopMail.getMessageAfterArrival("jordan" + Appointment.randomNumber + "@YOPmail.com", 
					propertyData.getProperty("curbside.checkin.mail.subject"),
					propertyData.getProperty("curbside.checkin.mail.title"),
					propertyData.getProperty("additional.arrival.inst.msg.en")),
					propertyData.getProperty("additional.arrival.inst.msg.en"), "Message is displayed");
			
			loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
			mainPage.clickOnSettingTab();
			notifPage.clickOnNotificationTab();
			notifPage.clickOnCurbsideCheckInTabInNotif();
			notifPage.clickOnEnglishButton();
			notifPage.clearAdditionalArrivalInstTextboxEn();
			notifPage.addArrivalInstructionTextInEnglish(propertyData.getProperty("add.arrival.instruction.in.en"));
			notifPage.saveNotification();
		}
		@When("I select English and Spanish as preferred language")
		public void i_select_english_and_spanish_as_preferred_language() throws InterruptedException {
		    notifPage.clickOnCurbsideCheckInTabInNotif();
		    notifPage.clickOnPracticePrefLangDropDown();
		    notifPage.selectEnglishSpanishPracticePrefLang();
		    notifPage.saveNotification();
		}
		@When("I schedule an appointment with English and Spanish language and confirm the arrival")
		public void i_schedule_an_appointment_with_english_and_spanish_language_and_confirm_the_arrival() throws NullPointerException, IOException {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en-es",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			aptPrecheckPost.aptArrivalActionsCurbsideArrival(propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			
		}
		@Then("I verify arrival notification msg should show mobile number of appointment location in the arrival text msg and msg should come in English language")
		public void i_verify_arrival_notification_msg_should_show_mobile_number_of_appointment_location_in_the_arrival_text_msg_and_msg_should_come_in_english_language() throws Exception {
		    YopMail yopMail = new YopMail(driver);
		    assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
		    		propertyData.getProperty("curbside.mail.subject.en.es"),
		    		propertyData.getProperty("location.and.phone.number"),5));
		    
		    loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		@When("I schedule an appointment with English language and confirm the arrival")
		public void i_schedule_an_appointment_with_english_language_and_confirm_the_arrival() throws NullPointerException, IOException {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan" + Appointment.randomNumber + "@YOPmail.com","en",
							propertyData.getProperty("patient.name")),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			aptPrecheckPost.aptArrivalActionsCurbsideArrival(propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify arrival notification message should show mobile number of appointment location in the arrival text msg and Msg should come in English language")
		public void i_verify_arrival_notification_message_should_show_mobile_number_of_appointment_location_in_the_arrival_text_msg_and_msg_should_come_in_english_language() throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
		    assertTrue(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
		    		propertyData.getProperty("curbside.checkin.mail.subject"),
		    		propertyData.getProperty("location.and.phone.number"),5));
		    
		    loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		@When("I schedule an appointment with English language and set appointment location and phone number as blank")
		public void i_schedule_an_appointment_with_english_language_and_set_appointment_location_and_phone_number_as_blank() throws NullPointerException, IOException {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			Appointment.randomNumber = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putApptPayloadwithblankPhoneandLocation(plus20Minutes, null, 
							"jordan" + Appointment.randomNumber + "@YOPmail.com",
							propertyData.getProperty("provider.first.name"),
							propertyData.getProperty("patient.name"), null),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			aptPrecheckPost.aptArrivalActionsCurbsideArrival(propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
		}
		@Then("I verify arrival notification msg should not show {string} text  in the arrival text msg")
		public void i_verify_arrival_notification_msg_should_not_show_text_in_the_arrival_text_msg(String string) throws NullPointerException, Exception {
			YopMail yopMail = new YopMail(driver);
		    assertFalse(yopMail.isMessageInInbox("jordan" + Appointment.randomNumber + "@YOPmail.com",
		    		propertyData.getProperty("curbside.checkin.mail.subject"),
		    		propertyData.getProperty("location.and.phone.number"),5));
		    
		    loginPage = new AppointmentPrecheckLogin(driver, propertyData.getProperty("practice.provisining.url.ge"));
		}
		
	@When("I select first Hours, min and then Day in timing units and save changes for {string}")
	public void i_select_first_hours_min_and_then_day_in_timing_units_and_save_changes_for(String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());

		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
		notifPage.checkingFourthTimingIfPresent();
		if (currentEnv.equals("DEV3")) {
			notifPage.addTimingAndTimingUnit();
			Appointment.hours=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hours);
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minutes);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.days);
				assertTrue(notifPage.visibilityOfSaveChangesbutton());
		} else {
			notifPage.addTimingAndTimingUnitDemo();
			Appointment.hours=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnitDemo(1, "Hours", Appointment.hours);
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnitDemo(2, "Minutes", Appointment.minutes);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
		}
	}
			
		
	@Then("I verify user is able to see timing units in proper sequence on notification tab for email")
	public void i_verify_user_is_able_to_see_timing_units_in_proper_sequence_on_notification_tab_for_email() throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getHoursTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getTimeUnitTextForHours(), Appointment.hours, "Hours Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForMinutes(), Appointment.minutes, "Minutes Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForDays(), Appointment.days, "Days Timing unit was not match");
		scrollAndWait(0, 1000, 10000);
		
		notifPage.clickApptReminderEmailHamburgerButton();
		notifPage.clickOnEditButtonHamburgerButton();
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
		if (currentEnv.equals("DEV3")) {
			notifPage.enterTimingAndTimingUnit(1, "Days", "1");
			notifPage.enterTimingAndTimingUnit(2, "Days", "3");
			notifPage.enterTimingAndTimingUnit(3, "Days", "5");
		} else {
			notifPage.enterTimingAndTimingUnitDemo(1, "Days", "1");
			notifPage.enterTimingAndTimingUnitDemo(2, "Days", "3");
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", "5");
		}
			notifPage.clickOnSaveChangesbutton();
	}
		
	@When("I select first minutes, hours and then Day in timing units and save changes for {string}")
	public void i_user_select_first_minutes_hours_and_then_day_in_timing_units_and_save_changes_for(String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
			
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
		notifPage.checkingFourthTimingIfPresent();
		if (currentEnv.equals("DEV3")) {
			notifPage.addTimingAndTimingUnit();
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnit(1, "Minutes", Appointment.minutes);
			Appointment.hours=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.hours);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.days);
		}else {
			notifPage.addTimingAndTimingUnitDemo();
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnitDemo(1, "Minutes", Appointment.minutes);
			Appointment.hours=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnitDemo(2, "Hours", Appointment.hours);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", Appointment.days);
		}
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}
		
	@When("I select first hour, min, hour and then Day in timing units and save changes for {string}")
	public void i_select_first_hour_min_hour_and_then_day_in_timing_units_and_save_changes_for(String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
			
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
		if (currentEnv.equals("DEV3")) {
			notifPage.addTimingAndTimingUnit();
			notifPage.addFourthTimingAndTimingUnit();
			Appointment.hour1=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hour1);
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minutes);
			Appointment.hour2=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hour2);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnit(4, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
		}else {
			notifPage.addTimingAndTimingUnitDemo();
			notifPage.addFourthTimingAndTimingUnit();
			Appointment.hour1=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnitDemo(1, "Hours", Appointment.hour1);
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnitDemo(2, "Minutes", Appointment.minutes);
			Appointment.hour2=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnitDemo(3, "Hours", Appointment.hour2);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnitDemo(4, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
		}
	}
		
	@Then("I verify if user is able to see timing units in proper sequence on notification tab for email")
	public void i_verify_if_user_is_able_to_see_timing_units_in_proper_sequence_on_notification_tab_for_email() throws InterruptedException {
		assertEquals(notifPage.getHoursTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getTimeUnitTextForMinutes(), Appointment.minutes, "Minutes Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForDays(), Appointment.days, "Days Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForHours(), Appointment.hour1+", "+Appointment.hour2, "Hours Timing unit was not match");
	    scrollAndWait(0, 1000, 10000);
	    
		notifPage.clickApptReminderEmailHamburgerButton();
		notifPage.clickOnEditButtonHamburgerButton();	
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
		notifPage.checkingFourthTimingIfPresent();
		if (currentEnv.equals("DEV3")) {
			notifPage.enterTimingAndTimingUnit(1, "Days", "1");
			notifPage.enterTimingAndTimingUnit(2, "Days", "3");
			notifPage.enterTimingAndTimingUnit(3, "Days", "5");
				
		} else {
			notifPage.enterTimingAndTimingUnitDemo(1, "Days", "1");
			notifPage.enterTimingAndTimingUnitDemo(2, "Days", "3");
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", "5");
		}
			notifPage.clickOnSaveChangesbutton();
	}
		
	@When("I hit edit button of text for appointment reminder")
	public void i_hit_edit_button_of_text_for_appointment_reminder() throws InterruptedException {
		scrollAndWait(0, 1000, 3000);
		notifPage.clickOnEditButtonHamburgerButton();
		log("User redirect on edit template design page");
	}
		
	@When("I select first Hours, min and then Day in timing units in text and save changes for {string}")
	public void i_select_first_hours_min_and_then_day_in_timing_units_in_text_and_save_changes_for(String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
			
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
	    String currentEnv = String.valueOf(env);
		notifPage.checkingFourthTimingIfPresent();
		if (currentEnv.equals("DEV3")) {
			notifPage.addTimingAndTimingUnit();
			Appointment.hours=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hours);
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minutes);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
		}else {
			notifPage.addTimingAndTimingUnitDemo();
			Appointment.hours=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnitDemo(1, "Hours", Appointment.hours);
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnitDemo(2, "Minutes", Appointment.minutes);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
		}
	}
		
		
	@Then("I verify user is able to see timing units in proper sequence on notification tab for text")
	public void i_verify_user_is_able_to_see_timing_units_in_proper_sequence_on_notification_tab_for_text() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getHoursTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimeUnitTextForSMS(), Appointment.hours, "Hours Timing unit was not match");
		assertEquals(notifPage.getMinutesTimeUnitTextForSMS(), Appointment.minutes, "Minutes Timing unit was not match");
		assertEquals(notifPage.getDaysTimeUnitTextForSMS(), Appointment.days,"Days Timing unit was not match");
			
		scrollAndWait(0, 1000, 10000);
		notifPage.clickApptReminderSmsHamburgerButton();
		notifPage.clickOnEditButtonHamburgerButton();
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
		if (currentEnv.equals("DEV3")) {
			notifPage.enterTimingAndTimingUnit(1, "Days", "1");
			notifPage.enterTimingAndTimingUnit(2, "Days", "3");
			notifPage.enterTimingAndTimingUnit(3, "Days", "5");
		} else {
			notifPage.addTimingAndTimingUnitDemo();
			notifPage.enterTimingAndTimingUnitDemo(1, "Days", "1");
			notifPage.enterTimingAndTimingUnitDemo(2, "Days", "3");
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", "5");
		}
			notifPage.clickOnSaveChangesbutton();		
	}
		
	@When("I select first minutes, hours and then Day in timing units in text and save changes for {string}")
	public void i_select_first_minutes_hours_and_then_day_in_timing_units_in_text_and_save_changes_for(String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
			
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
	    notifPage.checkingFourthTimingIfPresent();
		if (currentEnv.equals("DEV3")) {
			notifPage.addTimingAndTimingUnit();
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnit(1, "Minutes", Appointment.minutes);
			Appointment.hours=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.hours);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
		}else {
			notifPage.addTimingAndTimingUnitDemo();
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnitDemo(1, "Minutes", Appointment.minutes);
			Appointment.hours=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnitDemo(2, "Hours", Appointment.hours);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());	
		 }
	}
		
	@When("I select first hour, min, hour and then Day in timing units in text and save changes for {string}")
	public void i_select_first_hour_min_hour_and_then_day_in_timing_units_in_text_and_save_changes_for(String deliveryMethod) throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
			
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
		if (currentEnv.equals("DEV3")) {
			notifPage.addTimingAndTimingUnit();
			notifPage.addFourthTimingAndTimingUnit();
			Appointment.hour1=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hour1);
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minutes);
			Appointment.hour2=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hour2);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnit(4, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
		}else {
			notifPage.addTimingAndTimingUnitDemo();
			notifPage.addFourthTimingAndTimingUnit();
			Appointment.hour1=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnitDemo(1, "Hours", Appointment.hour1);
			Appointment.minutes=notifPage.enterMinutes();
			notifPage.enterTimingAndTimingUnitDemo(2, "Minutes", Appointment.minutes);
			Appointment.hour2=notifPage.enterHours();
			notifPage.enterTimingAndTimingUnitDemo(3, "Hours", Appointment.hour2);
			Appointment.days=notifPage.enterDays();
			notifPage.enterTimingAndTimingUnitDemo(4, "Days", Appointment.days);
			assertTrue(notifPage.visibilityOfSaveChangesbutton());
		}
	}
		
	@Then("I verify if user is able to see timing units in proper sequence on notification tab for text")
	public void i_verify_if_user_is_able_to_see_timing_units_in_proper_sequence_on_notification_tab_for_text() throws InterruptedException {
		assertEquals(notifPage.getHoursTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getMinutesTimeUnitTextForSMS(), Appointment.minutes, "Minutes Timing unit was not match");
		assertEquals(notifPage.getDaysTimeUnitTextForSMS(), Appointment.days,"Days Timing unit was not match");
		assertEquals(notifPage.getHoursTimeUnitTextForSMS(), Appointment.hour1+", "+Appointment.hour2, "Hours Timing unit was not match");
		scrollAndWait(0, 1000, 10000);
		
		notifPage.clickApptReminderSmsHamburgerButton();
		notifPage.clickOnEditButtonHamburgerButton();
		EnvironmentType envType =IHGUtil.getEnvironmentType();
		String env = envType.toString();
		String currentEnv = String.valueOf(env);
		notifPage.checkingFourthTimingIfPresent();
		if (currentEnv.equals("DEV3")) {
			notifPage.enterTimingAndTimingUnit(1, "Days", "1");
			notifPage.enterTimingAndTimingUnit(2, "Days", "3");
			notifPage.enterTimingAndTimingUnit(3, "Days", "5");
		} else {
			notifPage.enterTimingAndTimingUnitDemo(1, "Days", "1");
			notifPage.enterTimingAndTimingUnitDemo(2, "Days", "3");
			notifPage.enterTimingAndTimingUnitDemo(3, "Days", "5");
			}
			notifPage.clickOnSaveChangesbutton();
		}
	
	@And("I schedule multiple appointments and perform arrival actions confirm")
	public void i_schedule_multiple_appointments_and_perform_arrival_actions_confirm()  throws NullPointerException, IOException {
		for (int i = 0; i <= 4; i++) {
			Appointment.patientId = commonMethod.generateRandomNum();
			Appointment.apptId = commonMethod.generateRandomNum();
			long currentTimestamp = System.currentTimeMillis();
			long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
			apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
					propertyData.getProperty("apt.precheck.practice.id"),
					payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
							"jordan@YOPmail.com"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);

			Response actionResponse = aptPrecheckPost.aptAppointmentActionsConfirm(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(actionResponse.getStatusCode(), 200);

			Response curbsideCheckinResponse = aptPrecheckPost.aptArrivalActionsCurbsideCurbscheckin(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(curbsideCheckinResponse.getStatusCode(), 200);

			Response arrivalResponse = aptPrecheckPost.aptArrivalActionsCurbsideArrival(
					propertyData.getProperty("baseurl.apt.precheck"),
					propertyData.getProperty("apt.precheck.practice.id"),
					headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
					Appointment.apptId);
			assertEquals(arrivalResponse.getStatusCode(), 200);
		}
		driver.navigate().refresh();
	}	
	
	@When("I schedule a new appointment and not having confirm")
	public void i_schedule_a_new_appointment_only_and_having_confirm() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generateRandomNum();
		Appointment.apptId = commonMethod.generateRandomNum();
		Appointment.randomNumber = commonMethod.generateRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(10);
		log("schedule more than 10 an appointments ");
		Response resonse = apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, propertyData.getProperty("mf.apt.scheduler.phone"),
						"jordan"+Appointment.randomNumber+"@YOPmail.com"),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
		assertEquals(resonse.statusCode(), 200);
	}

	@When("I click on the curbside link and finish curbside checkin process from mail")
	public void i_click_on_the_curbside_link_and_finish_curbside_checkin_process_from_mail() throws Exception {
		scrollAndWait(0, -500, 5000);
		YopMail yopMail = new YopMail(driver);
		String arrivalConfirmedTitle=yopMail.confirmArrivalFromEmail("jordan"+Appointment.randomNumber+"@YOPmail.com", "Curbside check-in for your appointment","I have arrived");
		assertEquals(arrivalConfirmedTitle,"Arrival confirmed");
	}

	@Then("I verify notification icon count decreases and patient should be disappeared")
	public void i_verify_notification_icon_count_decreases_and_patient_should_be_disappeared() {
		assertEquals(curbsidePage.getNotificationCount(), "5", "Notification Count is not match");
	}
	
	@When("I applied filter for start date")
	public void i_applied_filter_for_start_date() throws InterruptedException {
		scrollAndWait(0, -500, 3000);
		apptPage.startDate();
		apptPage.selectStartTime("12:00 AM");
	}
	
	@When("I select all record from page {string}")
	public void i_select_all_record_from_page(String pageNo) throws InterruptedException {
		log("User is on page: "+apptPage.getPageNo());
		assertEquals(apptPage.getPageNo(),pageNo,"Page number was not correct");
		log("User on first page");
		apptPage.selectAllCheckboxes();;
	}
	
	@When("I deselect few records from page {string} and switch to page {int} and select all records")
	public void i_deselect_few_records_from_page_and_switch_to_page_and_select_all_records(String pageNo, int reqPage) throws InterruptedException {
		scrollAndWait(0, 5000, 5000);
		apptPage.clickOnTimeCheckboxes(4);
		log("Deselect few records from page one");
		assertEquals(apptPage.getPageNo(),pageNo,"Page number was not correct");
		apptPage.selectRequiredPage(reqPage);
		apptPage.selectAllCheckboxes();
	}
	
	@When("I again switch on page {int} click on banner message")
	public void i_again_switch_on_page_click_on_banner_message(int reqPage) throws InterruptedException {
		String page=String.valueOf(reqPage);
		assertEquals(apptPage.jumpToPreviousPage(),page,"Page number was not correct");
		apptPage.clickOnBannerMessage();
	}
	
	@Then("I verify broadcast message count from action button after clicking on banner message on page one")
	public void i_verify_broadcast_message_count_from_action_button_after_clicking_on_banner_message_on_page_one() throws InterruptedException {
		Appointment.bannerMessage = apptPage.getSelectedBannerMessage();
		log("Get banner message-:" + Appointment.bannerMessage);
		String bannerCount = Appointment.bannerMessage.substring(3,8);
		String getBannerCount = bannerCount.replaceAll("[^\\d]"," ");
		log("Total count on banner meassage: " + getBannerCount.trim());
		String bannerMessageCount=getBannerCount.trim();
		
		apptPage.clickOnActions();
		String broadcastMessageButtonText =apptPage.getBroadcastMessageButtonText();
		log("Get broadcast message button text-:" + broadcastMessageButtonText);
		String broadcastMessageBtnCount = broadcastMessageButtonText.substring(19,23);
		String getBroadcastMsgBtnCount = broadcastMessageBtnCount.replaceAll("[^\\d{}]"," ").replaceAll("\\(", ""). replaceAll("\\)","");
		log("Total count on broadcast message button: " + getBroadcastMsgBtnCount.trim());
		String broadcastCount=getBroadcastMsgBtnCount.trim();
		assertEquals(bannerMessageCount,broadcastCount,"Banner and broadcast message button count was not same");
	}
	
	@When("I switch on page {string} and select all records")
	public void i_switch_on_page_and_select_all_records(String pageNo) throws InterruptedException {
		int page=Integer. parseInt(pageNo);
		apptPage.selectRequiredPage(page);
		assertEquals(apptPage.getPageNo(),pageNo,"Page number was not correct");
		apptPage.selectAllCheckboxes();
	}
	
	@When("I switch on page {string} and deselect few records")
	public void i_switch_on_page_and_deselect_few_records(String page) throws InterruptedException {
		assertEquals(apptPage.jumpToPreviousPage(),page,"Page number was not correct");
		apptPage.clickOnTimeCheckboxes(4);
		log("Deselect few records from page one");
	}
	
	@Then("I verify broadcast message count from action button after clicking on banner message on page two")
	public void i_verify_broadcast_message_count_from_action_button_after_clicking_on_banner_message_on_page_two() {
		Appointment.bannerMessage = apptPage.getSelectedBannerMessage();
		log("Get banner message-:" + Appointment.bannerMessage);
		String bannerCount = Appointment.bannerMessage.substring(3,8);
		String getBannerCount = bannerCount.replaceAll("[^\\d]"," ");
		log("Total count on banner meassage: " + getBannerCount.trim());
		String bannerMessageCount=getBannerCount.trim();
		
		apptPage.clickOnActions();
		String broadcastMessageButtonText =apptPage.getBroadcastMessageButtonText();
		log("Get broadcast message button text-:" + broadcastMessageButtonText);
		String broadcastMessageBtnCount = broadcastMessageButtonText.substring(19,23);
		String getBroadcastMsgBtnCount = broadcastMessageBtnCount.replaceAll("[^\\d{}]"," ").replaceAll("\\(", ""). replaceAll("\\)","");
		log("Total count on broadcast message button: " + getBroadcastMsgBtnCount.trim());
		String broadcastCount=getBroadcastMsgBtnCount.trim();
		assertEquals(bannerMessageCount,broadcastCount,"Banner and broadcast message button count was not same");
	}
	
	@When("I applied filter for start date, provider and location")
	public void i_applied_filter_for_start_date_provider_and_location() throws NullPointerException, Exception {
		scrollAndWait(0, -500, 3000);
		apptPage.startDate();
		apptPage.selectStartTime("12:00 AM");
		scrollAndWait(0, -500, 10000);
		log("Provider name-- " + propertyData.getProperty("provider.name.ge"));
		apptPage.enterProvider(propertyData.getProperty("provider.name.ge"));
		log("Location name-- " + propertyData.getProperty("location.name.ge"));
		apptPage.enterLocation(propertyData.getProperty("location.name.ge"));
		
	}
}
