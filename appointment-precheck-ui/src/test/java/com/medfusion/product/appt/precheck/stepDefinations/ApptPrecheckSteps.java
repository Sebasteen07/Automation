// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.appt.precheck.stepDefinations;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.Main.ApptPrecheckMainPage;
import com.medfusion.product.object.maps.appt.precheck.page.Appointments.AppointmentsPage;
import com.medfusion.product.object.maps.appt.precheck.page.Login.AppointmentPrecheckLogin;
import com.medfusion.product.object.maps.appt.precheck.page.Setting.GeneralPage;
import com.medfusion.product.object.maps.appt.precheck.page.Setting.NotificationsPage;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.BaseTest;
import com.medfusion.product.object.maps.appt.precheck.util.CommonMethods;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptPrecheck;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentScheduler;

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
	PostAPIRequestMfAppointmentScheduler apptSched;
	MfAppointmentSchedulerPayload payload;
	HeaderConfig headerConfig;
	AccessToken accessToken;
	PostAPIRequestAptPrecheck aptPrecheckPost;

	@Given("user lauch practice provisioning url")
	public void user_lauch_practice_provisioning_url() throws Exception {
		propertyData = new PropertyFileLoader();
		apptPage = new AppointmentsPage(driver);
		notifPage = new NotificationsPage(driver);
		mainPage = new ApptPrecheckMainPage(driver);
		notifPage = new NotificationsPage(driver);
		generalPage = new GeneralPage();
		apptSched = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		accessToken = AccessToken.getAccessToken();
		aptPrecheckPost = PostAPIRequestAptPrecheck.getPostAPIRequestAptPrecheck();
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
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		Appointment.patientId = String.valueOf(randamNo);
		Appointment.apptId = String.valueOf(randamNo);
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
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		Appointment.patientId = String.valueOf(randamNo);
		Appointment.apptId = String.valueOf(randamNo);
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
	public void schedule_multiple_new_appointments_and_confirm() throws NullPointerException, IOException, InterruptedException {
		for (int i = 0; i < 25; i++) {
			Random random = new Random();
			int randamNo = random.nextInt(100000);
			Appointment.patientId = String.valueOf(randamNo);
			Appointment.apptId = String.valueOf(randamNo);
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
			Random random = new Random();
			int randamNo = random.nextInt(100000);
			Appointment.patientId = String.valueOf(randamNo);
			Appointment.apptId = String.valueOf(randamNo);
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
			Random random = new Random();
			int randamNo = random.nextInt(100000);
			Appointment.patientId = String.valueOf(randamNo);
			Appointment.apptId = String.valueOf(randamNo);
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
		log("get page no." +Appointment.pageNo);
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
}