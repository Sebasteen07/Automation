// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.HomePage;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.commons.httpclient.util.TimeoutController.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.FiltersFormPages;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.NGAppointmentPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage.JalapenoAppointmentsPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page1;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.DocumentsPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.MedicationsPage.MedicationsHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PayBillsStatementPage.JalapenoPayBillsStatementPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;
import com.medfusion.product.object.maps.patientportal2.page.ScheduleAppoinment.JalapenoAppoinmentSchedulingPage;
import com.medfusion.product.object.maps.patientportal2.page.ThirdPartySso.ThirdPartySsoPage;

public class JalapenoHomePage extends JalapenoMenu {
	@FindBy(how = How.ID, using = "feature_messaging")
	private WebElement messages;

	@FindBy(how = How.XPATH, using = "//span[text()='Messages']")
	private WebElement messagesSideBar;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Schedule an Appointment')]")
	private WebElement sheduleanappointment;

	@FindBy(how = How.XPATH, using = "//a[@id = 'feature_appointments'] | //a[@id = 'feature_appointment_request'][1]")
	private WebElement appointments;

	@FindBy(how = How.ID, using = "feature_ask_a_practitioner_modal")
	private WebElement askAQuestion;

	@FindBy(how = How.ID, using = "feature_symptomAssesment")
	private WebElement symptomAss;

	@FindBy(how = How.ID, using = "button-rx-request")
	private WebElement prescriptions;

	@FindBy(how = How.ID, using = "feature_medications")
	private WebElement medications;

	@FindBy(how = How.ID, using = "feature_bill_pay")
	private WebElement payments;

	@FindBy(how = How.ID, using = "feature_discrete_forms")
	private WebElement forms;

	@FindBy(how = How.ID, using = "feature_ccdList")
	private WebElement medicalRecordSummaries;

	@FindBy(how = How.ID, using = "unstartedformbutton")
	private WebElement startRegistrationButton;

	@FindBy(how = How.ID, using = "inprogressformbutton")
	private WebElement continueRegistrationButton;

	@FindBy(how = How.XPATH, using = "//span[@class='badge currentPatientBubble']")
	private WebElement bubble;

	@FindBy(how = How.XPATH, using = "//span[@id='currentPatientBubble-grp']")
	private WebElement mobileViewBubble;

	@FindBy(how = How.XPATH, using = "//li[@class='dependent-dropdown']")
	private WebElement viewDifferentPatientButton;

	@FindBy(how = How.XPATH, using = "//li[@class='open-top-grp dependent-dropdown']")
	private WebElement mobileViewDifferentPatientButton;

	@FindBy(how = How.XPATH, using = "//a[contains(@class, 'success')]")
	private WebElement succPaymentNotification;

	@FindBy(how = How.XPATH, using = "//*[@id=\"feature_bill_pay\"]/span")
	private WebElement outstandingPatientBalance;

	@FindBy(how = How.XPATH, using = "//blinkhealth//*[contains(text(),'×')]")
	private WebElement blinkBannerHideButton;

	@FindBy(how = How.ID, using = "actionButton")
	private WebElement buttonContinue;

	@FindBy(how = How.CSS, using = ".modal-title.ng-binding")
	private WebElement appointmentNotScheduled;

	@FindBy(how = How.XPATH, using = "//a[text()='Ask (paid)']")
	private WebElement askPaid;

	@FindBy(how = How.XPATH, using = "//div[@class='practice-toggle-dropdown']//span[@class='ng-arrow-wrapper']")
	private WebElement practiceToggleSearch;

	@FindBy(how = How.XPATH, using = "//input[@type='text']")
	private WebElement practiceInput;

	@FindBy(how = How.XPATH, using = "//button[@id='switchingPracticeContinueButton']")
	private WebElement switchButtonContinue;

	@FindBy(how = How.XPATH, using = "//*[@title=\"Ask a Staff\"]")
	private WebElement askAStaffButtonOnPopup;

	@FindBy(how = How.XPATH, using = "//*[@title=\"Ask a Doc\"]")
	private WebElement askADocButtonOnPopup;

	@FindBy(how = How.XPATH, using = "//div[@id='menu']//li[@id='home']")
	private WebElement homeButton;

	@FindBy(how = How.XPATH, using = "//*[@id=\"feature_appointments\"]/div")
	private WebElement nextAppointmentSchedule;

	@FindBy(how = How.ID, using = "sentFolder")
	private WebElement sentFolder;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'is no longer linked to your account.')]")
	private WebElement unlinkSuccessfulMsg;

	@FindBy(how = How.XPATH, using = "//h3[text()='3 Party SSO']")
	private WebElement thirdpartysso;

	@FindBy(how = How.XPATH, using = "//h3[contains(text(),'Schedule an Appointment')]")
	private WebElement appoinmentscheduling;

	@FindBy(how = How.XPATH, using = "//button[text()='Live Chat']")
	private WebElement btnLiveChat;
	
	@FindBy(how = How.ID, using = "feature_ccdList")
	private WebElement healthrecord;

	public JalapenoHomePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	public JalapenoMessagesPage showMessages(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, messages);
		messages.click();
		return PageFactory.initElements(driver, JalapenoMessagesPage.class);
	}

	public JalapenoMessagesPage showMessagesSent(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, messages);
		messages.click();
		IHGUtil.waitForElement(driver, 60, sentFolder);
		sentFolder.click();
		return PageFactory.initElements(driver, JalapenoMessagesPage.class);
	}

	// TODO: remove or refactor to go to this page through button after the
	// button will be added
	public JalapenoAppointmentsPage goToAppointmentsPage(String practiceURL) {
		String destinationURL = practiceURL + "#/upcomingappointments";
		log("Going to URL: " + destinationURL);
		driver.get(destinationURL);

		return PageFactory.initElements(driver, JalapenoAppointmentsPage.class);
	}

	public JalapenoAppointmentRequestPage clickOnAppointment(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, appointments);
		appointments.click();
		log("click");
		return PageFactory.initElements(driver, JalapenoAppointmentRequestPage.class);
	}

	public JalapenoAppointmentRequestV2Step1 clickOnAppointmentV2(WebDriver driver) {
		IHGUtil.PrintMethodName();
		javascriptClick(appointments);
		return PageFactory.initElements(driver, JalapenoAppointmentRequestV2Step1.class);
	}

	public JalapenoPayBillsStatementPage clickOnPayBills(WebDriver driver) {

		log("Clicking on Payments button");
		payments.click();
		return PageFactory.initElements(driver, JalapenoPayBillsStatementPage.class);
	}

	public JalapenoPayBillsMakePaymentPage clickOnNewPayBills(WebDriver driver) {
		IHGUtil.waitForElement(driver, 50, payments);
		log("Clicking on Payments button");
		payments.click();

		return PageFactory.initElements(driver, JalapenoPayBillsMakePaymentPage.class);
	}

	public HealthFormListPage clickOnHealthForms() {
		log("Clicking on Health Forms button");
		javascriptClick(forms);
		return PageFactory.initElements(driver, HealthFormListPage.class);
	}

	public JalapenoPrescriptionsPage clickOnPrescriptions(WebDriver driver) {
		log("Clicking on Prescriptions button on dashboard");
		this.clickOnMedications(driver);
		prescriptions.click();
		return PageFactory.initElements(driver, JalapenoPrescriptionsPage.class);
	}

	public MedicationsHomePage clickOnMedications(WebDriver driver) {
		IHGUtil.PrintMethodName();
		javascriptClick(medications);
		return PageFactory.initElements(driver, MedicationsHomePage.class);
	}

	public MedicalRecordSummariesPage clickOnMedicalRecordSummaries(WebDriver driver) {
		log("Clicking on Medical Record Summaries button on dashboard");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)", "");
		medicalRecordSummaries.click();
		return PageFactory.initElements(driver, MedicalRecordSummariesPage.class);
	}

	public DocumentsPage goToDocumentsPage() {

		log("Clicking on Health Record menu button");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,400)", "");
		medicalRecordSummaries.click();
		try {
			WebElement otherDocumentsButton = new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText("Other documents"))));
			otherDocumentsButton.click();
		} catch (NoSuchElementException e) {
			log("Other documents button not found within 30 seconds, are you on the correct page?");
		}
		return PageFactory.initElements(driver, DocumentsPage.class);
	}

	public FiltersFormPages clickStartRegistrationButton() {
		log("Clicking on Start Registration button.");
		startRegistrationButton.click();
		return PageFactory.initElements(driver, FiltersFormPages.class);
	}

	public <T extends PortalFormPage> T clickContinueRegistrationButton(Class<T> pageClass) {
		log("Clicking on Continue Registration button.");
		continueRegistrationButton.click();
		log("Switch to the Forms iframe.");
		IHGUtil.setFrame(driver, "iframe");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Forms']")));
		return PageFactory.initElements(driver, pageClass);
	}

	public boolean isTextDisplayed(String text) {
		log("Looking for notification: " + text);
		IHGUtil.waitForElement(driver, 50, continueRegistrationButton);
		try {
			return driver.findElement(By.xpath("//p[contains(text(),'" + text + "')]")).getText().contains(text);
		} catch (Exception e) {
			log("Text: '" + text + "' wasn't found on the page.");
			return false;
		}
	}

	public boolean wasPayBillsSuccessfull() {
		log("Looking for message about succesfull payment");

		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(succPaymentNotification));
			log("Result: Message is displayed. Payment: " + succPaymentNotification.getAttribute("href"));
			return true;
		} catch (Exception ex) {
			log(ex.getCause().toString());
			return false;
		}
	}

	public String getConfirmationNumberFromPayment() {
		log("Looking for a confirmation number in a payment message");
		try {
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(succPaymentNotification));
			String message = driver.findElement(By.xpath("//div[@class='notification-message']/p/span[2]")).getText();
			log("Result: Message is displayed: " + message);
			message = message.substring(28, message.indexOf("."));
			log("Confirmation Number = " + message);
			return message;
		} catch (Exception ex) {
			log(ex.getCause().toString());
			return "";
		}
	}

	public boolean assessFamilyAccountElements(boolean button) {
		IHGUtil.PrintMethodName();
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		if (button) {
			log("Regular Resolution Bubble is visible " + isElementVisible(bubble, 10));
			log("Mobile Resolution Bubble is visible " + isElementVisible(mobileViewBubble, 10));
			if (isElementVisible(bubble, 10) == true) {
				webElementsList.add(bubble);
				log("Regular size resolution Bubble added to the webElement List");
			} else {
				log("Bubble not visible in regular version, trying mobile version");
				webElementsList.add(mobileViewBubble);
				log("The mobile view Bubble Added to the webElement List");
			}
		}
		return assessPageElements(webElementsList);

	}

	public JalapenoAskAStaffPage clickOnAskAStaff(WebDriver driver) {
		IHGUtil.PrintMethodName();
		askAQuestion.click();
		askAStaffButtonOnPopup.click();

		return PageFactory.initElements(driver, JalapenoAskAStaffPage.class);
	}

	public JalapenoAskAStaffV2Page1 openSpecificAskaV2(String askaName) throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(8000);
		askAQuestion.click();
		Thread.sleep(1000);
		log("It clicked on the ASK a question in homepage");
		try {

			driver.findElement(By.xpath("//a[text()='Ask (Staff)']")).click();

		} catch (NoSuchElementException e) {
			log("No question with the specified link text found! name: " + askaName);
			e.printStackTrace();
		}
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page1.class);
	}

	public JalapenoAskAStaffV2Page1 openSpecificAskaPaidV2(String askaName) throws InterruptedException {
		IHGUtil.PrintMethodName();
		wait.until(ExpectedConditions.visibilityOf(askAQuestion));
		askAQuestion.click();
		System.out.println("It clicked on the ASK a question in homepage");
		try {
			IHGUtil.waitForElement(driver, 80, askPaid);
			driver.findElement(By.xpath("//a[text()='Ask (paid)']")).click();
			System.out.println("clciked the element ASK Paid");
		} catch (NoSuchElementException e) {
			log("No question with the specified link text found! name: " + askaName);
			e.printStackTrace();
		}
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page1.class);
	}

	public JalapenoAskAStaffV2Page1 openSpecificAskaFree(String askaName) throws InterruptedException {
		IHGUtil.PrintMethodName();
		wait.until(ExpectedConditions.visibilityOf(askAQuestion));
		handleWeNeedToConfirmSomethingModal();
		askAQuestion.click();
		System.out.println("It clicked on the ASK a question in homepage");
		try {
			IHGUtil.waitForElement(driver, 80, askPaid);
			driver.findElement(By.xpath("//a[text()='Ask (free)']")).click();
			System.out.println("clciked the element ASK Paid");
		} catch (NoSuchElementException e) {
			log("No question with the specified link text found! name: " + askaName);
			e.printStackTrace();
		}
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page1.class);
	}

	public void faChangePatient() throws InterruptedException {
		IHGUtil.PrintMethodName();
		clickOnBubbleIcon();
		clickOnDifferentPatientView();

	}

	public String getOutstandingPatientBalance() {
		return outstandingPatientBalance.getText();
	}

	/**
	 * checks if the BUTTON is visible, because the banner contracts to an empty
	 * element mf-blink-banner when hidden
	 *
	 * @return
	 */
	public boolean isBlinkBannerDisplayed() {
		boolean ret = false;
		try {
			ret = blinkBannerHideButton.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void clickBlinkBannerHide() {
		javascriptClick(blinkBannerHideButton);
	}

	public void clickFeaturedAppointmentsReq() {
		javascriptClick(sheduleanappointment);
		IHGUtil.waitForElement(driver, 5, buttonContinue);
		javascriptClick(buttonContinue);
	}

	public String appointmentNotScheduled() {
		javascriptClick(sheduleanappointment);
		IHGUtil.waitForElement(driver, 80, appointmentNotScheduled);
		return appointmentNotScheduled.getText();
	}

	public void closeModalPopUp() {
		javascriptClick(buttonContinue);
	}

	public void switchPractice(String practice) {
		log("Clicking on Practice toggle Search");
		driver.navigate().refresh();
		practiceToggleSearch.click();
		practiceInput.sendKeys(practice);
		practiceInput.sendKeys(Keys.ENTER);
		IHGUtil.waitForElement(driver, 80, switchButtonContinue);
		switchButtonContinue.click();
	}

	public void clickOnBubbleIcon() {
		log("Clicking on Bubble icon - regular resolution");

		try {
			JavascriptExecutor ex = (JavascriptExecutor) driver;
			ex.executeScript("arguments[0].click();", bubble);

		} catch (NoSuchElementException ex) {
			log("Did not find Bubble icon, trying mobile version size");
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(mobileViewBubble));
			mobileViewBubble.click();

		}

	}

	public void clickOnDifferentPatientView() {
		log("Clicking on LinkedPatient icon - regular resolution");
		try {
			javascriptClick(viewDifferentPatientButton);
		} catch (NoSuchElementException ex) {
			log("Did not find LinkedPatient icon, trying mobile version size");
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(mobileViewDifferentPatientButton));
			mobileViewDifferentPatientButton.click();

		}

	}

	public void VerifyMuiltiplePracticeToggle() {
		log("Verify Practice toggle Search is not present");
		try {
			Boolean status = practiceToggleSearch.isDisplayed();
			if (!status)
				log("Practice toggle Search is not displayed as expected");
			else {
				log("Practice toggle Search is displayed");
				assertTrue(!status);
			}
		} catch (NoSuchElementException e) {
			log("Step Passed: Practice toggle Search is not displayed");
		}
	}

	public void switchToPractice(String practice) throws InterruptedException {
		try {
			WebElement verifySelectedPractice = driver
					.findElement((By.xpath("(//span[@title='" + practice + "'])[2]")));
			Boolean status = IHGUtil.waitForElement(driver, 15, verifySelectedPractice);
			if (status)
				log(practice + " is already selected as expected");
		} catch (NoSuchElementException e) {
			Log4jUtil.log(e.getMessage());
			log("Clicking on Practice toggle Search");
			practiceToggleSearch.click();
			practiceInput.sendKeys(practice);
			practiceInput.sendKeys(Keys.ENTER);
			Thread.sleep(8000);
			IHGUtil.waitForElement(driver, 90, switchButtonContinue);
			switchButtonContinue.click();
			log("Switch to the practice " + practice + " is completed");
		}
	}

	public JalapenoAskAStaffPage clickOnAskADoc(WebDriver driver) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, askAQuestion);
		askAQuestion.click();
		askADocButtonOnPopup.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffPage.class);
	}

	public void clickonHomeButton() {
		driver.navigate().refresh();
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(homeButton));
		homeButton.click();
	}

	public String getNextScheduledApptDate() {

		String nextAppointmentScheduleText = nextAppointmentSchedule.getText();
		return nextAppointmentScheduleText;
	}

	public JalapenoAskAStaffV2Page1 openSpecificAskaQuestion(String askaName) throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(8000);
		askAQuestion.click();
		log("It clicked on the ASK a question in homepage");
		try {
			Thread.sleep(8000);
			driver.findElement(By.xpath("//a[text()='" + askaName + "']")).click();
		} catch (NoSuchElementException e) {
			log("No question with the specified link text found! name: " + askaName);
			e.printStackTrace();
		}
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page1.class);
	}

	public NGAppointmentPage clickOnAppointmentV3(WebDriver driver) {
		IHGUtil.PrintMethodName();
		javascriptClick(appointments);
		return PageFactory.initElements(driver, NGAppointmentPage.class);
	}

	public boolean wasUnlinkSuccessful() {

		try {
			log("Looking for successful message after unlink");
			new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(unlinkSuccessfulMsg));
			return unlinkSuccessfulMsg.isDisplayed();
		} catch (Exception e) {
			log("Unlink of Dependent account was unsuccessful");
			return false;
		}
	}

	public ThirdPartySsoPage clickOnThirdPartySso(WebDriver driver) {
		IHGUtil.PrintMethodName();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", thirdpartysso);
		javascriptClick(thirdpartysso);
		return PageFactory.initElements(driver, ThirdPartySsoPage.class);
	}

	public JalapenoAppoinmentSchedulingPage clickOnAppoinmentScheduled(WebDriver driver) throws InterruptedException {
		IHGUtil.PrintMethodName();
		javascriptClick(appoinmentscheduling);
		return PageFactory.initElements(driver, JalapenoAppoinmentSchedulingPage.class);

	}

	public JalapenoPayBillsMakePaymentPage clickOnNewPayBillsForDuplicatePayment(WebDriver driver) {
		log("Clicking on Payments button");
		payments.click();

		return PageFactory.initElements(driver, JalapenoPayBillsMakePaymentPage.class);
	}

	public boolean isLiveChatDisplayed() throws TimeoutException {
		log("Verify LiveChat");
		try {
			return btnLiveChat.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Live Chat is not displayed");
			return false;
		}
	}

	public boolean isMessagesDisplayed() {
		log("Verify if Messages is displayed");
		try {
			return messages.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Messages not displayed");
			return false;
		}
	}

	public boolean isFormsSolutionDisplayed() throws TimeoutException {
		log("Verify Forms");
		try {
			return forms.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Forms Solution is not displayed");

			return false;
		}
	}

	public boolean isMessageSolutionDisplayed() throws TimeoutException {
		log("Verify Message solution No Access for Trusted Rep");
		try {
			return !messagesSideBar.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Verify Message solution No Access for Trusted Rep shoud not display");
			return true;
		}
	}

	public boolean isMedicationSolutionisplayed() {
		try {
			return medications.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Verify Medication Solution  for Trusted Rep shoud not display");
			return false;
		}
	}

	public boolean isAppointmentSolutionisplayed() {
		try {
			return appointments.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Verify Appointment Solution  for Trusted Rep shoud not display");
			return false;
		}
	}
	public boolean isHealthRecordSolutionisplayed() {
		try {
			return healthrecord.isDisplayed();
		} catch (NoSuchElementException e) {
			log("Verify Health Record Solution for Trusted Rep shoud not display");
			return false;
		}
	}
}
