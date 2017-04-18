package com.medfusion.plus.plusautomation;

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

import com.medfusion.plus.angular.NgWebDriver;
import com.medfusion.plus.objects.AboutPage;
import com.medfusion.plus.objects.AccountPage;
import com.medfusion.plus.objects.AddACard;
import com.medfusion.plus.objects.AddAPersonPage;
import com.medfusion.plus.objects.AddBankAccount;
import com.medfusion.plus.objects.AddPaymentMethod;
import com.medfusion.plus.objects.AutoPayPage;
import com.medfusion.plus.objects.CommonUtils;
import com.medfusion.plus.objects.ConnectionsPage;
import com.medfusion.plus.objects.HelpAndSupport;
import com.medfusion.plus.objects.HomePage;
import com.medfusion.plus.objects.LoginPage;
import com.medfusion.plus.objects.NotificationsPage;
import com.medfusion.plus.objects.PaymentMethods;
import com.medfusion.plus.objects.PlusPojos;
import com.medfusion.plus.objects.ProfilePage;
import com.medfusion.plus.objects.PropertyLoader;
import com.medfusion.plus.objects.SelectAProfilePage;
import com.medfusion.plus.objects.SendFeedbackPage;
import com.medfusion.plus.objects.SettingsPage;
import com.medfusion.plus.objects.SupportPage;
import com.medfusion.plus.objects.SupportRequestPage;

public class PlusAcceptanceTests {

	private static final Logger log = LogManager.getLogger(LoginPage.class);
	private FirefoxDriver driver;
	private NgWebDriver ngWebDriver;
	private PlusPojos plusData;

	@BeforeSuite
	public void before_suite() throws InterruptedException {
		// log("Step 1: Get The Bean");
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertyLoader.class);

		plusData = applicationContext.getBean(PlusPojos.class);

		driver = new FirefoxDriver();
		driver.manage().timeouts().setScriptTimeout(180, TimeUnit.SECONDS);
		ngWebDriver = new NgWebDriver(driver);

		driver.get(plusData.getUrl());
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

	@Test(enabled = true, groups = { "Plus" })
	public void testLoginInValidCredentials() throws InterruptedException {

		log.info("Testcase for verifying Invalid Login");

		LoginPage loginPage = new LoginPage(driver);

		assertTrue(loginPage.verifyLoginPageElements());

		log.info("UserName: " + plusData.getInvalidUserName());
		log.info("Password: " + plusData.getInvalidPassword());

		loginPage.login(plusData.getInvalidUserName(), plusData.getInvalidPassword());

		assertTrue(CommonUtils.verifyTextPresent(driver, "valid username & password combination."));
		log.info("Test case executed successfully");
	}

	@Test(enabled = true, groups = { "Plus" })
	public void testLoginValidCredentials() throws InterruptedException {

		log.info("Testcase for verifying Valid Login");

		LoginPage loginPage = new LoginPage(driver);
		assertTrue(loginPage.verifyLoginPageElements());

		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());

		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());
	}

	@Test(enabled = true, groups = { "Plus" })
	public void testSupportPageOnSignIn() throws InterruptedException {
		log.info("Go to the Login Page");
		LoginPage loginPage = new LoginPage(driver);

		log.info("Navigate to the Support Page");
		loginPage.goToSupportPage();
		SupportPage supportPage = new SupportPage(driver);

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
		SupportRequestPage supportRequestPage = new SupportRequestPage(driver);
		supportRequestPage.clickBackButton();

		log.info("Go to the Send Feedback page");
		supportPage.goToSendFeedback();

		log.info("Verify the Send Feedback page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "Where should we email you?"));
		SendFeedbackPage sendFeedbackPage = new SendFeedbackPage(driver);
		sendFeedbackPage.clickBackButton();

		log.info("Go to the About Medfusion Plus page");
		supportPage.goToAboutMedfusionPlus();

		log.info("Verify the About Medfusion Plus page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "At some point in our lives, we are all patients"));
		AboutPage aboutPage = new AboutPage(driver);
		aboutPage.clickBackButton();

		log.info("Go to the Privacy Statement page");
		supportPage.goToPrivacyStatement();

		log.info("Verify the Privacy Statement page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "Medfusion Plus Privacy Statement"));
		driver.close();
		supportPage.goBackToPreviousWindow(windowBefore);

		log.info("Go to the Terms of Service page");
		supportPage.goToTermsOfService();

		log.info("Verify the Terms of Service page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "Thank you for selecting Medfusion’s Plus application"));
		driver.close();
	}

	@Test(enabled = true, groups = { "Plus" })
	public void testSupportPageOnceLoggedIn() throws InterruptedException {
		log.info("Log into Plus");
		LoginPage loginPage = new LoginPage(driver);
		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());
		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());

		log.info("Navigate to Help and Feedback section");
		SelectAProfilePage nav = new SelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToHelpAndFeedback();

		log.info("Navigate to the Support Page");
		HelpAndSupport supportPage = new HelpAndSupport(driver);

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

		log.info("Go to the Send Feedback page");
		supportPage.goToSendFeedback();

		log.info("Verify the Send Feedback page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "Drop us a note"));
		supportPage.clickBackButton();

		log.info("Go to the About Medfusion Plus page");
		supportPage.goToAboutMedfusionPlus();

		log.info("Verify the About Medfusion Plus page loads");
		assertTrue(CommonUtils.verifyTextPresent(driver, "At some point in our lives, we are all patients"));
		supportPage.clickBackButton();

		log.info("Go to the Privacy Statement page");
		supportPage.goToPrivacyStatement();

		log.info("Verify the Privacy Statement page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "Medfusion Plus Privacy Statement"));
		driver.close();
		supportPage.goBackToPreviousWindow(windowBefore);

		log.info("Go to the Terms of Service page");
		supportPage.goToTermsOfService();

		log.info("Verify the Terms of Service page loads");
		supportPage.switchToNewWindow();
		assertTrue(CommonUtils.verifyTextPresent(driver, "Thank you for selecting Medfusion’s Plus application"));
		driver.close();
	}

	@Test(enabled = true, groups = { "Plus" })
	public void testEditMyProfile() throws InterruptedException {
		log.info("Log into Plus");
		LoginPage loginPage = new LoginPage(driver);
		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());
		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());

		log.info("Select the top Profile");
		SelectAProfilePage selectProfile = new SelectAProfilePage(driver);
		selectProfile.clickProfile();

		log.info("Click on the human silhouette");
		HomePage homePage = new HomePage(driver);
		homePage.selectPersonTab();

		log.info("Click Edit my profile");
		ConnectionsPage connectionsPage = new ConnectionsPage(driver);
		connectionsPage.clickEditMyProfile();

		log.info("Change the Zip Code");
		ProfilePage profilePage = new ProfilePage(driver);
		int zipCode = profilePage.addNewZipCode();
		log.info("Zip Code is now: " + zipCode);

		log.info("Verify that Zip Code has changed");
		connectionsPage.clickEditMyProfile();
		Assert.assertEquals(profilePage.verifyZipCode(), String.valueOf(zipCode));
	}

	@Test(enabled = true, groups = { "Plus" })
	public void testAddAndDeleteAProfile() throws InterruptedException {
		log.info("Log into Plus");
		LoginPage loginPage = new LoginPage(driver);
		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());
		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());

		log.info("Add a Person");
		SelectAProfilePage profileList = new SelectAProfilePage(driver);
		profileList.clickAddAPerson();

		log.info("Add person's info and click create");
		String name = plusData.getName();
		AddAPersonPage info = new AddAPersonPage(driver);
		info.addNewPerson(name, plusData.getZipCode());

		log.info("Verify the person was created");
		HomePage homePage = new HomePage(driver);
		String person = homePage.verifyProfileName();
		Assert.assertEquals(person, name);

		log.info("Go to the person's connection page");
		homePage.selectPersonTab();

		log.info("Edit the profile");
		ConnectionsPage connectionsPage = new ConnectionsPage(driver);
		connectionsPage.clickEditMyProfile();

		log.info("Delete the profile");
		ProfilePage profilePage = new ProfilePage(driver);
		profilePage.clickDeleteThisProfile();

		log.info("Verify the person was deleted");
		Assert.assertTrue(profileList.verifyProfileNotPresent(name));
	}

	@Test(enabled = false, groups = { "Plus" })
	public void testVerifyAccountSettings() throws InterruptedException {
		String salutation = plusData.getSalutation();
		String street = plusData.getStreet();
		String city = plusData.getCity();
		String state = plusData.getState();
		String zip = plusData.getZip();
		String email = plusData.getUpdatedEmail();
		String phone = plusData.getPhone();

		log.info("Log into Plus");
		LoginPage loginPage = new LoginPage(driver);
		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());
		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		SelectAProfilePage nav = new SelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the account section");
		SettingsPage page = new SettingsPage(driver);
		page.goToAccount();

		log.info("Edit my member profile");
		AccountPage action = new AccountPage(driver);
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
		action.resetMemberProfile("Mr", "5501 Dillard Dr", "Cary", "NC", "27518-9233", "test@example.com",
				"(919) 867-5309");
	}

	@Test(enabled = false, groups = { "Plus" })
	public void testAddANewCard() throws InterruptedException {
		String name = plusData.getCardName();
		String number = plusData.getCardNum();
		String expMon = plusData.getCardExpMonth();
		String expYr = plusData.getCardExpYear();
		String cvv = plusData.getCardCvv();
		String address = plusData.getCardAddress();
		String city = plusData.getCardCity();
		String state = plusData.getCardState();
		String zip = plusData.getCardZip();

		log.info("Log into Plus");
		LoginPage loginPage = new LoginPage(driver);
		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());
		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		SelectAProfilePage nav = new SelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the payment methods section");
		SettingsPage page = new SettingsPage(driver);
		page.goToPayments();

		log.info("Add a new payment method");
		PaymentMethods payment = new PaymentMethods(driver);
		payment.addNewPayment();

		log.info("Click on Add a card");
		AddPaymentMethod add = new AddPaymentMethod(driver);
		add.clickAddACard();

		// this won't save currently or redirect back
		log.info("Fill out card information and click submit");
		AddACard card = new AddACard(driver);
		card.addRequiredCardInfo(name, number, expMon, expYr, cvv, address, city, state, zip);

		log.info("Verify the card is in the list of Payment options");
		Thread.sleep(2000);
		Assert.assertTrue(payment.verifyPaymentMethodListed(name));
	}

	@Test(enabled = false, groups = { "Plus" })
	public void testAddNewBankAccount() throws InterruptedException {
		String name = plusData.getBankName();
		String rNumber = plusData.getRoutingNumber();
		String aNumber = plusData.getAccountNumber();
		String address = plusData.getBankAddress();
		String city = plusData.getBankCity();
		String state = plusData.getBankState();
		String zip = plusData.getBankZip();

		log.info("Log into Plus");
		LoginPage loginPage = new LoginPage(driver);
		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());
		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		SelectAProfilePage nav = new SelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the payment methods section");
		SettingsPage page = new SettingsPage(driver);
		page.goToPayments();

		log.info("Add a new payment method");
		PaymentMethods payment = new PaymentMethods(driver);
		payment.addNewPayment();

		log.info("Click on Add a bank account");
		AddPaymentMethod add = new AddPaymentMethod(driver);
		add.clickAddABankAccount();

		// this won't save currently or redirect back
		log.info("Fill out bank information and click submit");
		AddBankAccount bank = new AddBankAccount(driver);
		bank.addRequiredBankInfo(name, rNumber, aNumber, address, city, state, zip);

		log.info("Verify the bank account is in the list of Payment options");
		Assert.assertTrue(payment.verifyPaymentMethodListed(name));
	}

	@Test(enabled = false, groups = { "Plus" })
	public void testVerifyAutopay() throws InterruptedException {
		log.info("Log into Plus");
		LoginPage loginPage = new LoginPage(driver);
		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());
		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		SelectAProfilePage nav = new SelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the autopay section");
		SettingsPage page = new SettingsPage(driver);
		page.goToAutopay();
		Thread.sleep(4000);

		log.info("Turn on autopay");
		AutoPayPage action = new AutoPayPage(driver);
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

	@Test(enabled = true, groups = { "Plus" })
	public void testVerifyNotificationSettings() throws InterruptedException {
		log.info("Log into Plus");
		LoginPage loginPage = new LoginPage(driver);
		log.info("UserName: " + plusData.getValidUserName());
		log.info("Password: " + plusData.getValidPassword());
		loginPage.login(plusData.getValidUserName(), plusData.getValidPassword());

		log.info("Click on more options on the Select Profile Page");
		SelectAProfilePage nav = new SelectAProfilePage(driver);
		nav.clickMoreOptions();
		nav.goToSettings();

		log.info("Go to the notifications section");
		SettingsPage page = new SettingsPage(driver);
		page.goToNotifications();

		log.info("Turn on notifications");
		NotificationsPage action = new NotificationsPage(driver);
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
