package com.medfusion.product.object.maps.patientportal2.page.HomePage;


import java.util.ArrayList;

import org.apache.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage.JalapenoAppointmentsPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PayBillsStatementPage.JalapenoPayBillsStatementPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;
public class JalapenoHomePage extends JalapenoMenu {

	@FindBy(how = How.XPATH, using = "//*[@id='home')]")
	private WebElement home;

	@FindBy(how = How.ID, using = "feature_messaging")
	private WebElement messages;

	@FindBy(how = How.ID, using = "feature_appointment_request")
	private WebElement appointments;

	@FindBy(how = How.ID, using = "feature_ask_a_practitioner")
	private WebElement askAQuestion;

	@FindBy(how = How.ID, using = "feature_rx_renewal")
	private WebElement prescriptions;

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

	@FindBy(how = How.ID, using = "currentPatientBubble")
	private WebElement bubble;

	@FindBy(how = How.ID, using = "bubbleLabel")
	private WebElement bubbleLabel;

	@FindBy(how = How.ID, using = "familyAccountBtn")
	private WebElement viewDifferentPatientButton;

	@FindBy(how = How.ID, using = "listBadge_0")
	private WebElement listBadgeDropdownButton;

	@FindBy(how = How.XPATH, using = "//a[contains(@class, 'success')]")
	private WebElement succPaymentNotification;

	public JalapenoHomePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public JalapenoMessagesPage showMessages(WebDriver driver) throws Exception {

		IHGUtil.PrintMethodName();
		messages.click();
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
		appointments.click();

		return PageFactory.initElements(driver, JalapenoAppointmentRequestPage.class);
	}

	public JalapenoAppointmentRequestV2Step1 clickOnAppointmentV2(WebDriver driver) {
		IHGUtil.PrintMethodName();
		appointments.click();

		return PageFactory.initElements(driver, JalapenoAppointmentRequestV2Step1.class);
	}

	public JalapenoPayBillsStatementPage clickOnPayBills(WebDriver driver) throws Exception {

		log("Clicking on Payments button");
		payments.click();
		return PageFactory.initElements(driver, JalapenoPayBillsStatementPage.class);
	}

	public JalapenoPayBillsMakePaymentPage clickOnNewPayBills(WebDriver driver) throws Exception {

		log("Clicking on Payments button");
		payments.click();

		return PageFactory.initElements(driver, JalapenoPayBillsMakePaymentPage.class);
	}

	public HealthFormListPage clickOnHealthForms() throws Exception {
		log("Clicking on Health Forms button");
		forms.click();
		return PageFactory.initElements(driver, HealthFormListPage.class);
	}

	public JalapenoPrescriptionsPage clickOnPrescriptions(WebDriver driver) {

		log("Clicking on Prescriptions button on dashboard");
		prescriptions.click();
		return PageFactory.initElements(driver, JalapenoPrescriptionsPage.class);
	}

	public MedicalRecordSummariesPage clickOnMedicalRecordSummaries(WebDriver driver) {
		log("Clicking on Medical Record Summaries button on dashboard");
		medicalRecordSummaries.click();
		return PageFactory.initElements(driver, MedicalRecordSummariesPage.class);
	}
	
	public void clickStartRegistrationButton() throws Exception {
		log("Clicking on Start Registration button.");
		startRegistrationButton.click();
		log("Switch to the Forms iframe.");
		IHGUtil.setFrame(driver, "iframe");
	}

	public <T extends PortalFormPage> T clickContinueRegistrationButton(Class<T> pageClass) throws Exception {
		log("Clicking on Continue Registration button.");
		continueRegistrationButton.click();
		log("Switch to the Forms iframe.");
		IHGUtil.setFrame(driver, "iframe");
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Forms']")));
		return PageFactory.initElements(driver, pageClass);
	}

	public boolean isTextDisplayed(String text) {
		log("Looking for notification: " + text);

		try {
			driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]")).getText().contains(text);
			return true;
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

	@Override
	//Checks elements located every time on PI Dashboard
	public boolean areBasicPageElementsPresent() {
		 ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
//		  webElementsList.add(home);
		  webElementsList.add(messages);
		  log("Checking all elements on " + this.getClass().getSimpleName());

		        for (WebElement w : webElementsList) {
		            int attempt = 1;
		            while (attempt < 3) {
		                try {
		                    new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOf(w));
		                    log("Element " + w.toString() + " : is displayed", Level.DEBUG);
		                    attempt = 3;
		                } catch (StaleElementReferenceException ex) {
		                    log("StaleElementReferenceException was catched, attempt: " + attempt++);
		                } catch (TimeoutException ex) {
		                    log(ex.getMessage());
		                    return false;
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                    return false;
		                }
		            }
		        }

		        return true;
		    }
	

	public boolean assessFamilyAccountElements(boolean button) {
		IHGUtil.PrintMethodName();

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(bubble);
		webElementsList.add(bubbleLabel);
		if (button)
			webElementsList.add(viewDifferentPatientButton);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public boolean isHomeButtonPresent(WebDriver driver) {
		return IHGUtil.waitForElement(driver, 120, home);
	}

	public JalapenoAskAStaffPage clickOnAskAStaff(WebDriver driver) {
		IHGUtil.PrintMethodName();
		askAQuestion.click();

		return PageFactory.initElements(driver, JalapenoAskAStaffPage.class);
	}

	public void faChangePatient() {
		IHGUtil.PrintMethodName();

		viewDifferentPatientButton.click();
		listBadgeDropdownButton.click();
	}
}
