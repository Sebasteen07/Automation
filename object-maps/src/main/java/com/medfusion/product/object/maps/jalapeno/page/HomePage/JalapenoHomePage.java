package com.medfusion.product.object.maps.jalapeno.page.HomePage;

import java.util.ArrayList;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.portal.page.questionnaires.FormWelcomePage;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoPage;
import com.medfusion.product.object.maps.jalapeno.page.AppointmentRequestPage.JalapenoAppointmentRequestPage;
import com.medfusion.product.object.maps.jalapeno.page.HealthForms.JalapenoHealthFormsListPage;
import com.medfusion.product.object.maps.jalapeno.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.jalapeno.page.PayBillsStatementPage.JalapenoPayBillsStatementPage;

public class JalapenoHomePage extends JalapenoPage {
	
	@FindBy(how = How.ID, using = "home")
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
	
	@FindBy(how = How.CSS, using = ".button.ng-binding")
	private WebElement startRegistrationButton;

	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 */
	
	public JalapenoHomePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		//driver.manage().window().maximize();
		driver.manage().window().setSize(new Dimension(1200, 768));
		PageFactory.initElements(driver, this);	
	}
	
	public JalapenoMessagesPage showMessages(WebDriver driver) throws Exception {
		
		IHGUtil.PrintMethodName();
		messages.click();
		
		return PageFactory.initElements(driver, JalapenoMessagesPage.class);
	}
	
	public JalapenoAppointmentRequestPage clickOnAppointment(WebDriver driver) {
		IHGUtil.PrintMethodName();
		appointments.click();
		
		return PageFactory.initElements(driver, JalapenoAppointmentRequestPage.class);
	}

	public JalapenoPayBillsStatementPage clickOnPayBills(WebDriver driver) throws Exception {
		
		log("Clicking on Payments button");
		payments.click();
		return PageFactory.initElements(driver, JalapenoPayBillsStatementPage.class);
	}
	
	public JalapenoHealthFormsListPage clickOnHealthForms(WebDriver driver) throws Exception {

		log("Clicking on Health Forms button");
		forms.click();
		return PageFactory.initElements(driver, JalapenoHealthFormsListPage.class);
	}

	public FormWelcomePage clickStartRegistrationButton(WebDriver driver) throws Exception {
		log("Clicking on Start Registration button.");
		startRegistrationButton.click();
		log("Switch to the Forms iframe.");
		IHGUtil.setFrame(driver, "iframe");
		return PageFactory.initElements(driver.switchTo().frame(0), FormWelcomePage.class);
	}

	public boolean assessHomePageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
	
		webElementsList.add(messages);
		webElementsList.add(appointments);
		webElementsList.add(home);
		webElementsList.add(askAQuestion);
		webElementsList.add(prescriptions);
		webElementsList.add(payments);
		webElementsList.add(forms);

		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 60, w);
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			}

			catch (Throwable e) {
				log(e.getStackTrace().toString());
			}

		}
		return allElementsDisplayed;
	}
	
	public boolean isMessagesButtonDisplayed(WebDriver driver) {
		return IHGUtil.waitForElement(driver, 60, messages);
	}
	
}
