// Copyright 2016-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page;

import java.util.ArrayList;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.HealthFormListPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage.JalapenoAppointmentsPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.DocumentsPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;

/**
 * Contains menu elements that are on every page after login
 */
public abstract class JalapenoMenu extends MedfusionPage {

	@FindBy(how = How.ID, using = "leftMenuToggle")
	private WebElement leftMenuToggle;

	@FindBy(how = How.XPATH, using = "//*[@id='home']/a/span")
	private WebElement homeMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='messages_lhn']/a/span")
	private WebElement messagesMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='appointments_lhn']/a/span")
	private WebElement appointmentsMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='aska_lhn']/a/span")
	private WebElement askAQuestionMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='prescriptions_lhn']/a/span")
	private WebElement prescriptionsMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='payments_lhn']/a/span")
	private WebElement payBillsMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='forms_lhn']/a/span")
	private WebElement healthFormsMenu;

	// please note that bellow links are not the same, each lead to different page
	// (based on Linked Account settings)
	@FindBy(how = How.XPATH, using = "//li[@id='manageAccount']/a")
	private WebElement accountButton;
	
	@FindBy(how = How.XPATH, using = "//li[@id='manageAccount_dropdown']/a")
	private WebElement accountDropdownButton;
	
	@FindBy(how = How.XPATH, using = "//li[@id='manageAccount_dropdownlog']/a")
	private WebElement accountDropdownLinkedButton;

	@FindBy(how = How.LINK_TEXT, using = "My Account")
	private WebElement myAccountButton;

	@FindBy(how = How.LINK_TEXT, using = "My Account")
	private WebElement myAccountDropdownButton;

	@FindBy(how = How.ID, using = "open-top-loggedIn-grp")
	private WebElement rightDropdownButton;

	@FindBy(how = How.XPATH, using = "//span[@id='currentPatientBubble-grp']")
	private WebElement rightDropdownLinkedButton;
	
	@FindBy(how = How.XPATH, using = "//li[@id='signout_dropdown']")
	private WebElement signoutDropdownButton;
	
	@FindBy(how = How.XPATH, using = "//li[@id='signout_dropdownlog']")
	private WebElement signoutDropdownLinkedButton;

	@FindBy(how = How.ID, using = "signout")
	private WebElement signout;

	@FindBy(how = How.XPATH, using = "//*[@id='ccdList_lhn']/a/span")
	private WebElement healthRecordMenu;

	public JalapenoMenu(WebDriver driver) {
		super(driver);
	}

	public boolean areMenuElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		return assessPageElements(webElementsList);
	}

	public boolean isHomeButtonPresent(WebDriver driver) {
		openMenuIfClosed();
		return IHGUtil.waitForElement(driver, 120, homeMenu);
	}

	private void openMenuIfClosed() {
		try {
			if (!homeMenu.isDisplayed()) {
				openJalapenoMenu();
			}
		} catch (Exception ex) {
			openJalapenoMenu();
		}
	}

	private void openJalapenoMenu() {
		log("Opening Jalapeno menu");
		leftMenuToggle.click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(homeMenu));
		log("Jalapeno menu is opened");
	}

	public JalapenoHomePage clickOnMenuHome() {
		openMenuIfClosed();
		log("Clicking on Home menu button");
		homeMenu.click();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public JalapenoAppointmentsPage clickOnMenuMessages() {
		openMenuIfClosed();
		log("Clicking on Messages menu button");
		messagesMenu.click();
		return PageFactory.initElements(driver, JalapenoAppointmentsPage.class);
	}

	public JalapenoAppointmentsPage clickOnMenuAppointments() {
		openMenuIfClosed();
		log("Clicking on Appointments menu button");
		appointmentsMenu.click();
		return PageFactory.initElements(driver, JalapenoAppointmentsPage.class);
	}

	public JalapenoAskAStaffPage clickOnMenuAskAQuestionMenu() {
		openMenuIfClosed();
		log("Clicking on Ask a Question menu button");
		askAQuestionMenu.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffPage.class);
	}

	public JalapenoPrescriptionsPage clickOnMenuPrescriptions() {
		openMenuIfClosed();
		log("Clicking on Prescriptions menu button");
		prescriptionsMenu.click();
		return PageFactory.initElements(driver, JalapenoPrescriptionsPage.class);
	}

	public JalapenoPayBillsMakePaymentPage clickOnMenuPayBills() {
		openMenuIfClosed();
		log("Clicking on Pay Bills menu button");
		payBillsMenu.click();
		return PageFactory.initElements(driver, JalapenoPayBillsMakePaymentPage.class);
	}

	public HealthFormListPage clickOnMenuHealthForms() {
		openMenuIfClosed();
		log("Clicking on Health Forms menu button");
		healthFormsMenu.click();
		IHGUtil.setFrame(driver, "iframe");
		return PageFactory.initElements(driver, HealthFormListPage.class);
	}

	public JalapenoMyAccountProfilePage goToAccountPage() {
		JalapenoAccountPage accountPage = clickOnAccount();
		return accountPage.clickOnEditMyAccount();
	}

	public JalapenoAccountPage clickOnAccount() {
		log("Clicking on Account button - regular resolution");

		try {
			JavascriptExecutor ex = (JavascriptExecutor) driver;
			ex.executeScript("arguments[0].click();", accountButton);
			
		} catch (NoSuchElementException ex) {
			log("Did not find Account button, trying mobile version size");
			rightDropdownButton.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(accountDropdownButton));
			accountDropdownButton.click();
		} catch  (ElementNotInteractableException ex) {
			log("Did not find Account button, trying mobile version size for linked Account");
			rightDropdownButton.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(accountDropdownLinkedButton));
			accountDropdownLinkedButton.click();
			
		}
		return PageFactory.initElements(driver, JalapenoAccountPage.class);
	}

	public JalapenoMyAccountProfilePage clickOnMyAccount() {
		log("Clicking on Account button - regular resolution");
		try {
			myAccountButton.click();
		} catch (NoSuchElementException ex) {
			log("Did not find Account button, trying mobile version size");
			rightDropdownButton.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(myAccountButton));
			myAccountDropdownButton.click();
		}
		return PageFactory.initElements(driver, JalapenoMyAccountProfilePage.class);
	}

	public JalapenoLoginPage clickOnLogout() {

		log("Clicking on Logout button - regular resolution");

		try {
			javascriptClick(signout);
			
		} catch (NoSuchElementException ex) {
			log("Did not find Logout button, trying mobile version size");
			rightDropdownButton.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(signoutDropdownButton));
			signoutDropdownButton.click();
		} catch (ElementNotVisibleException ex) {
			log("Element is not currently visible, trying mobile version size");
			rightDropdownButton.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(signoutDropdownButton));
			signoutDropdownButton.click();
		} catch (ElementNotInteractableException ex) {
			log("Element is not currently not intractable, trying mobile version size");
			rightDropdownLinkedButton.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(signoutDropdownLinkedButton));
			signoutDropdownLinkedButton.click();
		}

		return PageFactory.initElements(driver, JalapenoLoginPage.class);
	}

	public MedicalRecordSummariesPage goToHealthRecordsPage() {
		openMenuIfClosed();
		log("Clicking on Health Record menu button");
		healthRecordMenu.click();

		return PageFactory.initElements(driver, MedicalRecordSummariesPage.class);
	}

	public DocumentsPage goToDocumentsPageFromMenu() {
		openMenuIfClosed();
		log("Clicking on Health Record menu button");
		healthRecordMenu.click();
		try {
			WebElement otherDocumentsButton = new WebDriverWait(driver, 30)
					.until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText("Other documents"))));
			otherDocumentsButton.click();
		} catch (NoSuchElementException e) {
			log("Other documents button not found within 30 seconds, are you on the correct page?");
		}
		return PageFactory.initElements(driver, DocumentsPage.class);
	}

	public void menuHealthRecordClickOnly() {
		openMenuIfClosed();
		log("Clicking on Health Record menu button");
		try {
			healthRecordMenu.click();
		} catch (NoSuchElementException e) {
			driver.navigate().refresh();
			new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(healthRecordMenu));
			healthRecordMenu.click();
		}
	}
	
	public NGLoginPage LogoutfromNGMFPortal() {

		log("Clicking on Logout button - regular resolution");

		try {
			    javascriptClick(signout);
		} catch (NoSuchElementException ex) {
				log("Did not find Logout button, trying mobile version size");
				rightDropdownButton.click();
				new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(signoutDropdownButton));
				signoutDropdownButton.click();
		} catch (ElementNotVisibleException ex) {
				log("Element is not currently visible, trying mobile version size");
				rightDropdownButton.click();
				new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(signoutDropdownButton));
				signoutDropdownButton.click();
		} catch (ElementNotInteractableException ex) {
				log("Element is not currently not intractable, trying mobile version size");
				rightDropdownButton.click();
				new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(signoutDropdownButton));
				signoutDropdownButton.click();
		}

		return PageFactory.initElements(driver, NGLoginPage.class);
        }
}
