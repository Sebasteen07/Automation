package com.medfusion.mdvip.mdvipautomation;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
// import org.openqa.selenium.interactions.Actions;
// import org.seleniumhq.selenium.fluent.*;
import org.testng.annotations.BeforeSuite;
// import org.eclipse.jetty.util.log.Log;
// import org.eclipse.jetty.util.log.StdErrLog;
import org.testng.annotations.Test;

import com.medfusion.mdvip.angular.NgWebDriver;
import com.medfusion.mdvip.objects.CommonUtils;
import com.medfusion.mdvip.objects.MDVIPAccountPage;
import com.medfusion.mdvip.objects.MDVIPAddACard;
import com.medfusion.mdvip.objects.MDVIPAddAPersonPage;
import com.medfusion.mdvip.objects.MDVIPAddBankAccount;
import com.medfusion.mdvip.objects.MDVIPAddPaymentMethod;
import com.medfusion.mdvip.objects.MDVIPAutoPayPage;
import com.medfusion.mdvip.objects.MDVIPConnectionsPage;
import com.medfusion.mdvip.objects.MDVIPHelpAndFeedback;
import com.medfusion.mdvip.objects.MDVIPHomePage;
import com.medfusion.mdvip.objects.MDVIPLoginPage;
import com.medfusion.mdvip.objects.MDVIPNotificationsPage;
import com.medfusion.mdvip.objects.MDVIPPaymentMethods;
import com.medfusion.mdvip.objects.MDVIPPojos;
import com.medfusion.mdvip.objects.MDVIPProfilePage;
import com.medfusion.mdvip.objects.MDVIPSelectAProfilePage;
import com.medfusion.mdvip.objects.MDVIPSettingsPage;
import com.medfusion.mdvip.objects.MDVIPSupportPage;
import com.medfusion.mdvip.objects.PropertyLoader;

public class MDVIPAcceptanceTests {

	private static final Logger log = LogManager.getLogger(MDVIPLoginPage.class);
	private FirefoxDriver driver;
	private NgWebDriver ngWebDriver;
	private MDVIPPojos mdvipData;

	@BeforeSuite
	public void before_suite() throws InterruptedException {
		// log("Step 1: Get The Bean");
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertyLoader.class);

		mdvipData = applicationContext.getBean(MDVIPPojos.class);

		driver = new FirefoxDriver();
		driver.manage().timeouts().setScriptTimeout(180, TimeUnit.SECONDS);
		ngWebDriver = new NgWebDriver(driver);

		driver.get(mdvipData.getUrl());
		ngWebDriver.waitForAngularRequestsToFinish();

		Thread.sleep(2000);

		List<WebElement> elementList = driver.findElements(By.className("slider-slide"));

		// System.out.println("List Size: "+elementList.size());
		for (int i = 0; i < elementList.size(); i++) {
			WebElement element = elementList.get(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			element.click();
		}
	}

	@AfterSuite
	public void after_suite() {
		// driver.quit();
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testLoginInValidCredentials() throws InterruptedException {

		log.info("Testcase for verifying Invalid Login");

		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);

		assertTrue(loginPage.verifyLoginPageElements());

		log.info("UserName: " + mdvipData.getInvalidUserName());
		log.info("Password: " + mdvipData.getInvalidPassword());

		loginPage.login(mdvipData.getInvalidUserName(), mdvipData.getInvalidPassword());

		assertTrue(CommonUtils.verifyTextPresent(driver, "valid username & password combination."));
		log.info("Test case executed successfully");
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testLoginValidCredentials() throws InterruptedException {

		log.info("Testcase for verifying Valid Login");

		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		assertTrue(loginPage.verifyLoginPageElements());

		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());

		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testSupportPageOnSignIn() throws InterruptedException {
		log.info("Go to the Login Page");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);

		log.info("Navigate to the Support Page");
		loginPage.goToSupportPage();
		MDVIPSupportPage supportPage = new MDVIPSupportPage(driver);

		log.info("Go to the FAQ page");
		String windowBefore = supportPage.getWindowBeforePopUp();
		supportPage.goToFAQ();

		log.info("Verify the FAQ page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "Frequently Asked Questions"));
		driver.close();
		supportPage.goBackToPreviousWindow(windowBefore);

		log.info("Go to the New Support Request page");
		supportPage.goToSupportRequest();

		log.info("Verify the New Support Request page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "Where should we email you?"));
		supportPage.clickBackButton();

		log.info("Go to the About MDVIP Connect page");
		supportPage.goToAboutMDVIPConnect();

		log.info("Verify the About MDVIP Connect page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "Thank you for downloading our MDVIP daVinci app."));
		supportPage.clickBackButton();

		log.info("Go to the Privacy Statement page");
		supportPage.goToPrivacyStatement();

		log.info("Verify the Privacy Statement page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "MDVIP Privacy Policy Summary"));
		driver.close();
		supportPage.goBackToPreviousWindow(windowBefore);

		log.info("Go to the Terms of Service page");
		supportPage.goToTermsOfService();

		log.info("Verify the Terms of Service page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "Please read the following Terms of Use (the “Terms of Use”) carefully."));
		driver.close();
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testSupportPageOnceLoggedIn() throws InterruptedException {
		log.info("Log into MDVIP");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());

		log.info("Navigate to Help and Feedback section");
		MDVIPSelectAProfilePage nav = new MDVIPSelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToHelpAndFeedback();

		log.info("Navigate to the Support Page");
		MDVIPHelpAndFeedback supportPage = new MDVIPHelpAndFeedback(driver);

		log.info("Go to the FAQ page");
		String windowBefore = supportPage.getWindowBeforePopUp();
		supportPage.goToFAQ();

		log.info("Verify the FAQ page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "Frequently Asked Questions"));
		driver.close();
		supportPage.goBackToPreviousWindow(windowBefore);

		log.info("Go to the Support Request page");
		supportPage.goToSupportRequest();

		log.info("Verify the Support Request page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "Where should we email you?"));
		supportPage.clickBackButton();

		log.info("Go to the Request a Provider page");
		supportPage.goToRequestProvider();

		log.info("Verify the Request a Provider page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "Tell us about the practice where you see your provider"));
		supportPage.clickBackButton();

		// log.info("Go to the Send Feedback page");
		// supportPage.goToSendFeedback();
		//
		// log.info("Verify the Send Feedback page loads");
		// assertTrue(CommonUtils.verifyTextPresent(driver, "Drop us a note"));

		log.info("Go to the About MDVIP Connect page");
		supportPage.goToAboutMDVIPConnect();

		log.info("Verify the About MDVIP Connect page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "Thank you for downloading our MDVIP daVinci app."));
		supportPage.clickBackButton();

		log.info("Go to the Privacy Statement page");
		supportPage.goToPrivacyStatement();

		log.info("Verify the Privacy Statement page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "MDVIP Privacy Policy Summary"));
		driver.close();
		supportPage.goBackToPreviousWindow(windowBefore);

		log.info("Go to the Terms of Service page");
		supportPage.goToTermsOfService();

		log.info("Verify the Terms of Service page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "Please read the following Terms of Use (the “Terms of Use”) carefully."));
		driver.close();
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testEditMyProfile() throws InterruptedException {
		log.info("Log into MDVIP");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());

		log.info("Select the top Profile");
		MDVIPSelectAProfilePage selectProfile = new MDVIPSelectAProfilePage(driver);
		selectProfile.clickProfile();

		log.info("Click on the human silhouette");
		MDVIPHomePage homePage = new MDVIPHomePage(driver);
		homePage.selectPersonTab();

		log.info("Click Edit my profile");
		MDVIPConnectionsPage connectionsPage = new MDVIPConnectionsPage(driver);
		connectionsPage.clickEditMyProfile();

		log.info("Change the Zip Code");
		MDVIPProfilePage profilePage = new MDVIPProfilePage(driver);
		int zipCode = profilePage.addNewZipCode();
		log.info("Zip Code is now: " + zipCode);

		log.info("Verify that Zip Code has changed");
		connectionsPage.clickEditMyProfile();
		Assert.assertEquals(profilePage.verifyZipCode(), String.valueOf(zipCode));
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testAddAndDeleteAProfile() throws InterruptedException {
		log.info("Log into MDVIP");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());

		log.info("Add a Person");
		MDVIPSelectAProfilePage profileList = new MDVIPSelectAProfilePage(driver);
		profileList.clickAddAPerson();

		log.info("Add person's info and click create");
		String name = mdvipData.getName();
		MDVIPAddAPersonPage info = new MDVIPAddAPersonPage(driver);
		info.addNewPerson(name, mdvipData.getZipCode());

		log.info("Verify the person was created");
		MDVIPHomePage homePage = new MDVIPHomePage(driver);
		String person = homePage.verifyProfileName();
		Assert.assertEquals(person, name);

		log.info("Go to the person's connection page");
		homePage.selectPersonTab();

		log.info("Edit the profile");
		MDVIPConnectionsPage connectionsPage = new MDVIPConnectionsPage(driver);
		connectionsPage.clickEditMyProfile();

		log.info("Delete the profile");
		MDVIPProfilePage profilePage = new MDVIPProfilePage(driver);
		profilePage.clickDeleteThisProfile();

		log.info("Verify the person was deleted");
		Assert.assertTrue(profileList.verifyProfileNotPresent(name));
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testVerifyAccountSettings() throws InterruptedException {
		String salutation = mdvipData.getSalutation();
		String street = mdvipData.getStreet();
		String city = mdvipData.getCity();
		String state = mdvipData.getState();
		String zip = mdvipData.getZip();
		String email = mdvipData.getUpdatedEmail();
		String phone = mdvipData.getPhone();

		log.info("Log into MDVIP");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		MDVIPSelectAProfilePage nav = new MDVIPSelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the account section");
		MDVIPSettingsPage page = new MDVIPSettingsPage(driver);
		page.goToAccount();

		log.info("Edit my member profile");
		MDVIPAccountPage action = new MDVIPAccountPage(driver);
		action.updateMemberProfile(salutation, street, city, state, zip, email, phone);

		log.info("Go back to account section");
		page.goToAccount();

		log.info("Verify that member was updated");
		Assert.assertEquals(action.getSalutation(), salutation);
		Assert.assertEquals(action.getStreet(), street);
		Assert.assertEquals(action.getCity(), city);
		Assert.assertEquals(action.getEmail(), email);
		Assert.assertEquals(action.getPhone(), phone);

		log.info("Reset values to previous values");
		action.resetMemberProfile("Mr", "5501 Dillard Dr", "Cary", "NC", "27518-9233", "test@example.com", "(919) 867-5309");
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testAddANewCard() throws InterruptedException {
		String name = mdvipData.getCardName();
		String number = mdvipData.getCardNum();
		String expMon = mdvipData.getCardExpMonth();
		String expYr = mdvipData.getCardExpYear();
		String cvv = mdvipData.getCardCvv();
		String address = mdvipData.getCardAddress();
		String city = mdvipData.getCardCity();
		String state = mdvipData.getCardState();
		String zip = mdvipData.getCardZip();

		log.info("Log into MDVIP");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		MDVIPSelectAProfilePage nav = new MDVIPSelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the payment methods section");
		MDVIPSettingsPage page = new MDVIPSettingsPage(driver);
		page.goToPayments();

		log.info("Add a new payment method");
		MDVIPPaymentMethods payment = new MDVIPPaymentMethods(driver);
		payment.addNewPayment();

		log.info("Click on Add a card");
		MDVIPAddPaymentMethod add = new MDVIPAddPaymentMethod(driver);
		add.clickAddACard();

		// this won't save currently or redirect back
		log.info("Fill out card information and click submit");
		MDVIPAddACard card = new MDVIPAddACard(driver);
		card.addRequiredCardInfo(name, number, expMon, expYr, cvv, address, city, state, zip);

		log.info("Verify the card is in the list of Payment options");
		Thread.sleep(2000);
		Assert.assertTrue(payment.verifyPaymentMethodListed(name));
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testAddNewBankAccount() throws InterruptedException {
		String name = mdvipData.getBankName();
		String rNumber = mdvipData.getRoutingNumber();
		String aNumber = mdvipData.getAccountNumber();
		String address = mdvipData.getBankAddress();
		String city = mdvipData.getBankCity();
		String state = mdvipData.getBankState();
		String zip = mdvipData.getBankZip();

		log.info("Log into MDVIP");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		MDVIPSelectAProfilePage nav = new MDVIPSelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the payment methods section");
		MDVIPSettingsPage page = new MDVIPSettingsPage(driver);
		page.goToPayments();

		log.info("Add a new payment method");
		MDVIPPaymentMethods payment = new MDVIPPaymentMethods(driver);
		payment.addNewPayment();

		log.info("Click on Add a bank account");
		MDVIPAddPaymentMethod add = new MDVIPAddPaymentMethod(driver);
		add.clickAddABankAccount();

		// this won't save currently or redirect back
		log.info("Fill out bank information and click submit");
		MDVIPAddBankAccount bank = new MDVIPAddBankAccount(driver);
		bank.addRequiredBankInfo(name, rNumber, aNumber, address, city, state, zip);

		log.info("Verify the bank account is in the list of Payment options");
		Assert.assertTrue(payment.verifyPaymentMethodListed(name));
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testVerifyAutopay() throws InterruptedException {
		log.info("Log into MDVIP");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		MDVIPSelectAProfilePage nav = new MDVIPSelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the autopay section");
		MDVIPSettingsPage page = new MDVIPSettingsPage(driver);
		page.goToAutopay();
		Thread.sleep(4000);

		log.info("Turn on autopay");
		MDVIPAutoPayPage action = new MDVIPAutoPayPage(driver);
		action.enableAutopay();

		log.info("Go back to settings page");
		action.clickBackButton();
		Thread.sleep(3000);

		log.info("Go back to autopay and verify autopay is turned on");
		page.goToAutopay();
		Assert.assertTrue(action.verifyAutopayIsOn(), "Autopay is enabled");
		Thread.sleep(1000);

		log.info("Reset notifications to off");
		action.disableAutopay();
	}

	@Test(enabled = true, groups = {"Plus"})
	public void testVerifyNotificationSettings() throws InterruptedException {
		log.info("Log into MDVIP");
		MDVIPLoginPage loginPage = new MDVIPLoginPage(driver);
		log.info("UserName: " + mdvipData.getValidUserName());
		log.info("Password: " + mdvipData.getValidPassword());
		loginPage.login(mdvipData.getValidUserName(), mdvipData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		MDVIPSelectAProfilePage nav = new MDVIPSelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the notifications section");
		MDVIPSettingsPage page = new MDVIPSettingsPage(driver);
		page.goToNotifications();

		log.info("Turn on notifications");
		MDVIPNotificationsPage action = new MDVIPNotificationsPage(driver);
		action.enableNotifications();

		log.info("Go back to settings page");
		action.clickBackButton();

		log.info("Go back to notifications and verify notifications are turned on");
		page.goToNotifications();
		Assert.assertTrue(action.verifyNotificationIsOn(), "Notifications are enabled");

		log.info("Reset notifications to off");
		action.disableNotifications();
	}
}
