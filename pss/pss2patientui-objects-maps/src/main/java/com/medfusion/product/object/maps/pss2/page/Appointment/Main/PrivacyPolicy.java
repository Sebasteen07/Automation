package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;

public class PrivacyPolicy extends PSS2MainPage {
	@FindBy(how = How.ID, using = "privacyCheckbox")
	private WebElement checkBoxAccept;
	@FindBy(how = How.ID, using = "existingCheckbox")
	private WebElement checkBoxAcceptExistingPatient;
	@FindBy(how = How.ID, using = "privacyCancelbutton")
	private WebElement buttonCancel;
	@FindBy(how = How.ID, using = "privacyPreviousButton")
	private WebElement buttonPrevious;
	@FindBy(how = How.ID, using = "privacySubmitButton")
	private WebElement buttonSubmit;
	@FindBy(how = How.ID, using = "existingPrivacySubmitButton")
	private WebElement buttonSubmitExistingPatient;
	@FindBy(how = How.CLASS_NAME, using = "dismissbuttons")
	private WebElement buttonClosePopUp;
	@FindBy(how = How.XPATH, using = "//*[@id=\"alreadyexistModalprivacy\"]/div/div/div[2]/p/span")
	private WebElement patientExistText;
	@FindBy(how = How.XPATH, using = "//*[@id=\"alreadyexistModalprivacy\"]/div/div/div[3]/a")
	private WebElement closeModalPopUpPatientAlreadyExist;
	@FindBy(how = How.XPATH, using = "//*[@id=\"myModalsss\"]/div/div/div[3]/a/span")
	private WebElement dismissPopUpForExistingPatient;
	@FindBy(how = How.XPATH, using = "//*[@id=\"myModalsss\"]/div/div/div[2]/p/span")
	private WebElement dismissPopUpTextForExistingPatient;

	public PrivacyPolicy(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		IHGUtil.PrintMethodName();
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public void closePopup() {
		buttonClosePopUp.click();
	}

	public HomePage submitPrivacyPage() {
		checkBoxAcceptExistingPatient.click();
		buttonSubmitExistingPatient.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

	public OnlineAppointmentScheduling submitPrivacyForExistingPatient() {
		checkBoxAccept.click();
		buttonSubmit.click();
		IHGUtil.waitForElement(driver, 80, patientExistText);
		log("Assert popup text message =" + patientExistText.getText());
		assertTrue(patientExistText.getText().contains("Patient already exists"));
		closeModalPopUpPatientAlreadyExist.click();
		return PageFactory.initElements(driver, OnlineAppointmentScheduling.class);
	}

	public OnlineAppointmentScheduling submitPrivacyForNonExistingPatient() {
		checkBoxAcceptExistingPatient.click();
		buttonSubmitExistingPatient.click();
		IHGUtil.waitForElement(driver, 80, dismissPopUpTextForExistingPatient);
		log("Assert popup text message =" + dismissPopUpTextForExistingPatient.getText());
		assertTrue(dismissPopUpTextForExistingPatient.getText().contains("no patient found"));
		dismissPopUpForExistingPatient.click();
		return PageFactory.initElements(driver, OnlineAppointmentScheduling.class);
	}
}
