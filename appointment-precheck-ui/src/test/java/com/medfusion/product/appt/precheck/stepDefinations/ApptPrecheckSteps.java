// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.appt.precheck.stepDefinations;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.common.utils.YopMail;
import com.medfusion.product.appt.precheck.payload.AptPrecheckPayload;
import com.medfusion.product.appt.precheck.payload.MfAppointmentSchedulerPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.Main.ApptPrecheckMainPage;
import com.medfusion.product.object.maps.appt.precheck.page.Appointments.AppointmentsPage;
import com.medfusion.product.object.maps.appt.precheck.page.CurbsideCheckIn.CurbsideCheckInPage;
import com.medfusion.product.object.maps.appt.precheck.page.Login.AppointmentPrecheckLogin;
import com.medfusion.product.object.maps.appt.precheck.page.Setting.GeneralPage;
import com.medfusion.product.object.maps.appt.precheck.page.Setting.NotificationsPage;
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
	PostAPIRequestMfAppointmentScheduler apptSched;
	MfAppointmentSchedulerPayload payload;
	HeaderConfig headerConfig;
	AccessToken accessToken;
	PostAPIRequestAptPrecheck aptPrecheckPost;
	AptPrecheckPayload aptPrecheckPayload;
	CurbsideCheckInPage curbsidePage;
	PostAPIRequestMfNotificationSubscriptionManager subsManager;;

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
		apptSched = PostAPIRequestMfAppointmentScheduler.getPostAPIRequestMfAppointmentScheduler();
		payload = MfAppointmentSchedulerPayload.getMfAppointmentSchedulerPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		accessToken = AccessToken.getAccessToken();
		aptPrecheckPost = PostAPIRequestAptPrecheck.getPostAPIRequestAptPrecheck();
		aptPrecheckPayload = AptPrecheckPayload.getAptPrecheckPayload();
		subsManager=PostAPIRequestMfNotificationSubscriptionManager.getPostAPIRequestMfNotificationSubscriptionManager();
		commonMethod= new CommonMethods(driver);
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
		Appointment.patientId = commonMethod.generatRandomNum();
		Appointment.apptId = commonMethod.generatRandomNum();
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
		Appointment.patientId = commonMethod.generatRandomNum();
		Appointment.apptId = commonMethod.generatRandomNum();
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
			Appointment.patientId = commonMethod.generatRandomNum();
			Appointment.apptId = commonMethod.generatRandomNum();
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
			Appointment.patientId = commonMethod.generatRandomNum();
			Appointment.apptId = commonMethod.generatRandomNum();
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
			Appointment.patientId = commonMethod.generatRandomNum();
			Appointment.apptId = commonMethod.generatRandomNum();
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
			Appointment.patientId = commonMethod.generatRandomNum();
			Appointment.apptId = commonMethod.generatRandomNum();
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
		assertEquals(apptPage.jumbToNextPage(), "2");
		log("Redirecting to page one");
		assertEquals(apptPage.jumbToPreviousPage(), "1");
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
		assertEquals(apptPage.jumbToNextPage(), "2");
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
		assertEquals(apptPage.jumbToNextPage(), "2");
		log("Redirecting to page one");
		assertEquals(apptPage.jumbToPreviousPage(), "1");
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
		assertEquals(notifPage.getNotificationTypeText(), "Notification Type",
				"Notification Type Text was not match");
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
			Appointment.patientId = commonMethod.generatRandomNum();
			Appointment.apptId = commonMethod.generatRandomNum();
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
		Appointment.patientId = commonMethod.generatRandomNum();
		Appointment.apptId = commonMethod.generatRandomNum();
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
		Appointment.day1=notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(1, "Days",Appointment.day1);
		Appointment.day2=notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(2, "Days", Appointment.day2);
		Appointment.day3=notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.day3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow only days")
	public void verify_system_should_allow_only_days() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getTimingUnitText(),Appointment.day1+", "+Appointment.day2+", "+Appointment.day3, "Days Timing unit was not match");
	}

	@When("enter timing and timing unit only for Hours for {string} and click on save button")
	public void enter_timing_and_timing_unit_only_for_hours_for_and_click_on_save_button(String deliveryMethod)
			throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(), deliveryMethod, "Delivery method was not match");
		log("Delivery Method:" + notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.hour1=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hour1);
		Appointment.hour2=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.hour2);
		Appointment.hour3=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hour3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow only hours")
	public void verify_system_should_allow_only_hours() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getTimingUnitText(), Appointment.hour1+", "+Appointment.hour2+", "+Appointment.hour3, "Hours Timing unit was not match");
	}

	@When("enter timing and timing unit only for Minutes for {string} and click on save button")
	public void enter_timing_and_timing_unit_only_for_minutes_for_and_click_on_save_button(String deliveryMethod)
			throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
		notifPage.checkingFourthTimingIfPresent();
		Appointment.minute1=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(1, "Minutes", Appointment.minute1);
		Appointment.minute2=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minute2);
		Appointment.minute3=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(3, "Minutes", Appointment.minute3);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow only Minutes")
	public void verify_system_should_allow_only_minutes() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimingUnitText(), Appointment.minute1+", "+Appointment.minute2+", "+Appointment.minute3, "Minutes Timing unit was not match");
	}

	@When("enter timing and timing unit for hours,minutes,day,day for {string} and click on save button")
	public void enter_timing_and_timing_unit_for_hours_minutes_day_day_for_and_click_on_save_button(String deliveryMethod)
			throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
		notifPage.addFourthTimingAndTimingUnit();
		Appointment.hours=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(1, "Hours", Appointment.hours);
		Appointment.minutes=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minutes);
		Appointment.day1=notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(3, "Days", Appointment.day1);
		Appointment.day2=notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(4, "Days", Appointment.day2);
		assertTrue(notifPage.visibilityOfSaveChangesbutton());
	}

	@Then("verify system should allow hours,minutes,day,day timing")
	public void verify_system_should_allow_hours_minutes_day_day_timing() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingText(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingText(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingText(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimeUnitTextForDays(), Appointment.day1+", "+Appointment.day2, "Days Timing unit was not match");
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
	public void enter_timing_and_timing_unit_for_day_hours_hours_minutes_for_and_click_on_save_button(String deliveryMethod)
			throws InterruptedException {
		scrollAndWait(0, 500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
		notifPage.addFourthTimingAndTimingUnit();
		Appointment.days=notifPage.enterDays();
		notifPage.enterTimingAndTimingUnit(1, "Days", Appointment.days);
		Appointment.hour1=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(2, "Hours", Appointment.hour1);
		Appointment.hour2=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hour2);
		Appointment.minutes=notifPage.enterMinutes();
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
		assertEquals(notifPage.getTimeUnitTextForHours(), Appointment.hour1+", "+Appointment.hour2, "Hours Timing unit was not match");
		assertEquals(notifPage.getTimeUnitTextForMinutes(), Appointment.minutes, "Minutes Timing unit was not match");
	}

	@When("enter timing and timing unit for minutes,minutes,hours,day for {string} and click on save button")
	public void enter_timing_and_timing_unit_for_minutes_minutes_hours_day_for_and_click_on_save_button(String deliveryMethod)
			throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		assertEquals(notifPage.getDeliveryMethod(),deliveryMethod,"Delivery method was not match");
		log("Delivery Method:"+notifPage.getDeliveryMethod());
		notifPage.addFourthTimingAndTimingUnit();
		Appointment.minute1=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(1, "Minutes", Appointment.minute1);
		Appointment.minute2=notifPage.enterMinutes();
		notifPage.enterTimingAndTimingUnit(2, "Minutes", Appointment.minute2);
		Appointment.hours=notifPage.enterHours();
		notifPage.enterTimingAndTimingUnit(3, "Hours", Appointment.hours);
		Appointment.days=notifPage.enterDays();
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
		assertEquals(notifPage.getTimeUnitTextForMinutes(), Appointment.minute1+", "+Appointment.minute2, "Minutes Timing unit was not match");
	}

	@Then("verify system should allow only days for SMS")
	public void verify_system_should_allow_only_days_for_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getTimingUnitTextForSMS(), Appointment.day1+", "+Appointment.day2+", "+Appointment.day3, "Days Timing unit was not match");
	}

	@Then("verify system should allow only hours SMS")
	public void verify_system_should_allow_only_hours_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getTimingUnitTextForSMS(), Appointment.hour1+", "+Appointment.hour2+", "+Appointment.hour3, "Hours Timing unit was not match");
	}

	@Then("verify system should allow only Minutes SMS")
	public void verify_system_should_allow_only_minutes_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getSingleTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getTimingUnitTextForSMS(), Appointment.minute1+", "+Appointment.minute2+", "+Appointment.minute3, "Minutes Timing unit was not match");
	}
	
	@Then("verify system should allow hours,minutes,day,day timing for SMS")
	public void verify_system_should_allow_hours_minutes_day_day_timing_for_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimeUnitTextForSMS(), Appointment.day1+", "+Appointment.day2, "Days Timing unit was not match");
		assertEquals(notifPage.getHoursTimeUnitTextForSMS(), Appointment.hours, "Hours Timing unit was not match");
		assertEquals(notifPage.getMinutesTimeUnitTextForSMS(), Appointment.minutes, "Minutes Timing unit was not match");
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
		assertEquals(notifPage.getHoursTimeUnitTextForSMS(), Appointment.hour1+", "+Appointment.hour2, "Hours Timing unit was not match");
		assertEquals(notifPage.getMinutesTimeUnitTextForSMS(), Appointment.minutes, "Minutes Timing unit was not match");
	}
	
	@Then("verify system should allow minutes,minutes,hours,day timing for SMS")
	public void verify_system_should_allow_minutes_minutes_hours_day_timing_for_sms() throws InterruptedException {
		scrollAndWait(0, 1000, 5000);
		assertEquals(notifPage.getDaysTimingTextForSMS(), "Days", "Days Timing was not match");
		assertEquals(notifPage.getHoursTimingTextForSMS(), "Hours", "Hours Timing was not match");
		assertEquals(notifPage.getMinutesTimingTextForSMS(), "Minutes", "Minutes Timing was not match");
		assertEquals(notifPage.getDaysTimeUnitTextForSMS(), Appointment.days, "Days Timing unit was not match");
		assertEquals(notifPage.getHoursTimeUnitTextForSMS(), Appointment.hours, "Hours Timing unit was not match");
		assertEquals(notifPage.getMinutesTimeUnitTextForSMS(), Appointment.minute1+", "+Appointment.minute2, "Minutes Timing unit was not match");
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
	public void schedule_multiple_appointments_and_confirm_their_appointment() throws NullPointerException, IOException {
		for (int i=0;i<=9;i++) {
			Appointment.patientId = commonMethod.generatRandomNum();
			Appointment.apptId = commonMethod.generatRandomNum();
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
		driver.navigate().refresh();
	}
	
	@Then("verify checkin button fuctionality after one patient gets checkin")
	public void verify_checkin_button_fuctionality_after_one_patient_gets_checkin() throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		notifPage.selectOnePatient();
		notifPage.checkingCheckinButton();
		log("Check in button is enable");
		assertEquals(notifPage.getCheckinButtonText(),"Check-In (1)","Checkin button text is not match");
		log("Checkin button text:-  "+notifPage.getCheckinButtonText());
		notifPage.clickOnCheckinButton();
		notifPage.checkingCheckinButton();
		log("Check in button is disable");
		assertEquals(curbsidePage.countOfCurbsideCheckinPatient(),9,"Count of Curbside checkin patient was not match");
		
	}
	
	@Then("verify checkin button fuctionality after two patient gets checkin")
	public void verify_checkin_button_fuctionality_after_two_patient_gets_checkin() throws InterruptedException {
		scrollAndWait(0, -500, 5000);
		curbsidePage.selectTwoPatient();
		notifPage.checkingCheckinButton();
		log("Check in button is enable");
		assertEquals(notifPage.getCheckinButtonText(),"Check-In (2)","Checkin button text is not match");
		log("Checkin button text:-  "+notifPage.getCheckinButtonText());
		notifPage.clickOnCheckinButton();
		notifPage.checkingCheckinButton();
		log("Check in button is disable");
		assertEquals(curbsidePage.countOfCurbsideCheckinPatient(),8,"Count of Curbside checkin patient was not match");
	}
	
	@Then("verify checkin button fuctionality after all patient gets checkin")
	public void verify_checkin_button_fuctionality_after_all_patient_gets_checkin() throws InterruptedException {
		scrollAndWait(0, -500, 15000);
		curbsidePage.selectAllAppointment();
		notifPage.checkingCheckinButton();
		log("Check in button is enable");
		assertEquals(notifPage.getCheckinButtonText(),"Check-In (10)","Checkin button text is not match");
		log("Checkin button text:-  "+notifPage.getCheckinButtonText());
		notifPage.clickOnCheckinButton();
		Thread.sleep(10000);
		notifPage.checkingCheckinButton();
		log("Check in button is disable");
		assertEquals(curbsidePage.countOfCurbsideCheckinPatient(),0,"Count of Curbside checkin patient was not match");
	}
	
	@Then("verify select and deselect functionality of all checkbox")
	public void verify_select_and_deselect_functionality_of_all_checkbox() throws InterruptedException {
		assertFalse(curbsidePage.getVisibilityOfAllCheckbox());
		Thread.sleep(10000);
		curbsidePage.selectAllAppointment();
		notifPage.checkingCheckinButton();
		log("Check in button is enabled");
		assertEquals(notifPage.getCheckinButtonText(),"Check-In (10)","Checkin button text is not match");
		assertTrue(curbsidePage.getVisibilityOfAllCheckbox());
		curbsidePage.deselectAllAppointment();
		assertFalse(curbsidePage.getVisibilityOfAllCheckbox());
	}
	
	@Then("verify select functionality of individual checkbox")
	public void verify_select_functionality_of_individual_checkbox() throws InterruptedException {
		Thread.sleep(10000);
		curbsidePage.selectMultiplePatients(8);
		assertEquals(notifPage.getCheckinButtonText(),"Check-In (3)","Checkin button text is not match");
		curbsidePage.getVisibilityOfMultiplePatient(8);
		log("Multiple patient got selected ");
	}
	
	@When("schedule a appointment without phone number")
	public void schedule_a_appointment_without_phone_number() throws NullPointerException, IOException {
		Appointment.patientId = commonMethod.generatRandomNum();
		Appointment.apptId = commonMethod.generatRandomNum();
		long currentTimestamp = System.currentTimeMillis();
		long plus20Minutes = currentTimestamp + TimeUnit.MINUTES.toMillis(5);
		apptSched.aptPutAppointment(propertyData.getProperty("baseurl.mf.appointment.scheduler"),
				propertyData.getProperty("mf.apt.scheduler.practice.id"),
				payload.putAppointmentPayload(plus20Minutes, null,
						propertyData.getProperty("unsubscribed.email")),
				headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), Appointment.patientId,
				Appointment.apptId);
	}
	
	@When("go to on yopmail and from mail unsubscribe a patient")
	public void go_to_on_yopmail_and_from_mail_unsubscribe_a_patient() throws InterruptedException {
		YopMail yopMail= new YopMail(driver);
		String unsubscribeMessage=yopMail.unsubscribeEmail(propertyData.getProperty("unsubscribed.email"),propertyData.getProperty("appt.email.subject"));
		assertEquals(unsubscribeMessage,"You will no longer receive emails from PreCheck and reminder services. Please contact your practice if you wish to opt back in.","Message was nor correct");
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
		assertEquals(apptPage.broadcastMessageStatus(),"Broadcast Message Sent. 0 successful. 1 failed.","Message was nor correct");
	log("Delete Subscription Data");
				Response response = subsManager.deleteAllSubscriptionDataUsingEmailId(propertyData.getProperty("baseurl.mf.notif.subscription.manager"),
						headerConfig.HeaderwithToken(accessToken.getaccessTokenPost()), propertyData.getProperty("unsubscribed.email"));
				log("Verifying the response");
				assertEquals(response.getStatusCode(), 200);
	}
	
	@When("I send message to selected patient")
	public void I_send_message_to_selected_patient() throws InterruptedException {
		curbsidePage.selectMessageFromDropdown(Appointment.patientId, propertyData.getProperty("curbside.checkin.message.parking.lot"));
		curbsidePage.clickOnSendButtonOfSelectedPatient(Appointment.patientId);
	}
	
	@Then("verify last message send succesfully from curbside checkin")
	public void verify_last_message_send_succesfully_from_curbside_checkin() {
		assertEquals("Last Message: "+propertyData.getProperty("curbside.checkin.message.parking.lot"),curbsidePage.getSentMessageSelectedPatient(Appointment.patientId),"Message was not correct");
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
	public void i_schedule_appointments_and_select_patients(int  appt) throws Exception {
		for (int i = 0; i <appt; i++) {
			log("Schedule multiple Appointments");
			Appointment.patientId = commonMethod.generatRandomNum();
			Appointment.apptId = commonMethod.generatRandomNum();
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
	public void verify_broadcast_message_ui_template_visibility_and_when_broadcast_message_entered_in_english_and_spanish_footer_note_character_count_get_decremented() throws NullPointerException, Exception {
		assertEquals(apptPage.getBroadcastMessagePopupText(),"Broadcast Message","Broadcast Message text was not match");
		assertEquals(apptPage.getBroadcastMessagePopupInstr(),"Use the space below to write a message to be sent to the [5] patients selected based on these filters.","Broadcast Message instruction was not match");
		assertEquals(apptPage.getBroadcastMessagePopupStartTime(),"Start Time: "+apptPage.currentDate()+" 12:00 AM","Start time was not match");
		assertEquals(apptPage.getBroadcastMessagePopupEndTime(),"End Time: "+apptPage.futureDate(13)+" 11:59 PM","Endtart time was not match");
		assertEquals(apptPage.getBroadcastMessagePopupProvider(),"Provider: All","All Provider was not match ");
		assertEquals(apptPage.getBroadcastMessagePopupLoaction(),"Location: All","All Provider was not match");
		assertEquals(apptPage.getBroadcastMsgPopupEnglishLabel(),"Broadcast Message (English)","Broadcast Message English label was not match");
		assertEquals(apptPage.getBroadcastMsgPopupSpanishLabel(),"Broadcast Message (Spanish)","Broadcast Message Spanish label was not match");
		assertEquals(apptPage.getBroadcastMsgPopupConfirmThisMsgLabel(),"Confirm this message","Confirm this message label was not match");
		assertFalse(apptPage.checkConfirmThisMsgCheckbox());
		assertEquals(apptPage.getBroadcastMsgPopupCloseButton(),"Close","Close Button text was not match");
		apptPage.hoverOnCloseButton();
		log("After hover check the visibility of close");
		assertTrue(apptPage.closeButtonFromBroadcastMsgPopup());
		assertEquals(apptPage.getBroadcastMsgPopupSendButton(),"Send Message (5)","Send Message text was not match");
		assertFalse(apptPage.sendButtonFromBroadcastMsgPopup());
		assertTrue(apptPage.visibilityOfBroadcastMsgCrossButton());
		assertEquals(apptPage.getBroadcastMsgPopupEnIncrDecrChar(),"450 characters remaining.","450 characters remaining. text for english text box was not match");
		assertEquals(apptPage.getBroadcastMsgPopupEsIncrDecrChar(),"450 characters remaining.","450 characters remaining. text for sapnish text box was not match");
		log("Enter message in English and Spanish");
		apptPage.EnterBroadcastMessageEnEs(propertyData.getProperty("broadcast.message.en"),
				propertyData.getProperty("broadcast.message.es"));
		assertNotEquals(apptPage.getBroadcastMsgPopupEnIncrDecrChar(),"450 characters remaining.");
		assertNotEquals(apptPage.getBroadcastMsgPopupEsIncrDecrChar(),"450 characters remaining.");
		apptPage.clickOnSendForBroadcastMsg();
	}
	
	@When("I schedule {int} appointments")
	public void i_schedule_appointments(int appt) throws NullPointerException, IOException, InterruptedException {
			for (int i = 0; i <appt; i++) {
				log("Schedule multiple new Appointments");
				Appointment.patientId = commonMethod.generatRandomNum();
				Appointment.apptId = commonMethod.generatRandomNum();
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
		Appointment.patientId = commonMethod.generatRandomNum();
		Appointment.apptId = commonMethod.generatRandomNum();
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
		apptPage.clickOnBroadcastEmailForSelectedPatient(Appointment.patientId,	Appointment.apptId);
		Appointment.broadcastMessage=apptPage.getBroadcastMessage();
		log("Sent broadcast message: "+Appointment.broadcastMessage);
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
		apptPage.clickOnBroadcastEmailForSelectedPatient(Appointment.patientId,	Appointment.apptId);
	    assertNotEquals(Appointment.broadcastMessage,apptPage.getBroadcastMessage(),"old broadcast message shown");
	}
		       
}
