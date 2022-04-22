// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;

/**
 * Contains menu elements that are on every page after login
 */
public abstract class JalapenoMenu extends MedfusionPage {

	@FindBy(how = How.ID, using = "leftMenuToggle")
	private WebElement leftMenuToggle;

	@FindBy(how = How.XPATH, using = "//button[@id='leftMenuToggle']")
	private WebElement leftMenuToggleXpath;

	@FindBy(how = How.XPATH, using = "//*[@id='home']/a/span")
	private WebElement homeMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='messages_lhn']/a/span")
	private WebElement messagesMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='appointments_lhn']/a/span")
	private WebElement appointmentsMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='aska_lhn']")
	private WebElement askAQuestionMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='prescriptions_lhn']/a/span")
	private WebElement prescriptionsMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='payments_lhn']/a/span")
	private WebElement payBillsMenu;

	@FindBy(how = How.XPATH, using = "//*[@id='custom_forms_lhn']/a/span")
	private WebElement healthFormsMenu;

	// please note that bellow links are not the same, each lead to different page
	// (based on Linked Account settings)
	@FindBy(how = How.XPATH, using = "//li[@id='account' or @id='manageAccount']/a")
	private WebElement accountButton;

	@FindBy(how = How.XPATH, using = "//li[@id='account_dropdown' or @id='manageAccount_dropdown']/a")
	private WebElement accountDropdownButton;

	@FindBy(how = How.XPATH, using = "//li[@id='account_dropdownlog' or @id='manageAccount_dropdown']/a")
	private WebElement accountDropdownLinkedButton;
	
	@FindBy(how=How.XPATH,using="//li[@id='account']")
	private WebElement myAccount;

	@FindBy(how = How.LINK_TEXT, using = "Account")
	private WebElement myAccountButton;
 
	@FindBy(how = How.XPATH, using = "//li[@id='account_dropdown']/a")
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
	
	@FindBy(how=How.ID,using="feature_ask_a_practitioner_modal")
	private WebElement asaQuestion;

	public JalapenoMenu(WebDriver driver) {
		super(driver);
	}

	public boolean areMenuElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		return assessPageElements(webElementsList);
	}

	public boolean isHomeButtonPresent(WebDriver driver) {
		openMenuIfClosed();
		return IHGUtil.waitForElement(driver, 15, homeMenu);
	}

	private void openMenuIfClosed() {
		driver.navigate().refresh();
		driver.manage().window().maximize();
		log("Maximized the page to see the HOME menu. Current size= " + driver.manage().window().getSize());
		IHGUtil.waitForElement(driver, 50, homeMenu);

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
		log("Browser size before setting size: " + driver.manage().window().getSize());
		driver.manage().window().setSize(new Dimension(936, 788));
		JavascriptExecutor ex = (JavascriptExecutor) driver;
		ex.executeScript("arguments[0].scrollIntoView();",leftMenuToggle ); 
		IHGUtil.waitForElement(driver, 50, leftMenuToggleXpath);
		leftMenuToggleXpath.click();
		if (IHGUtil.waitForElement(driver, 50, homeMenu)) {
			log("Menu toggle clicked and Jalapeno menu is opened");
		}
	}

	public JalapenoHomePage clickOnMenuHome() {
		openMenuIfClosed();
		log("Clicking on Home menu button");
		homeMenu.click();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public JalapenoMessagesPage clickOnMenuMessages() {
		openMenuIfClosed();
		log("Clicking on Messages menu button");
		messagesMenu.click();
		return PageFactory.initElements(driver, JalapenoMessagesPage.class);
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

	public JalapenoMyAccountProfilePage goToAccountPage() throws InterruptedException {
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
		} catch (ElementNotInteractableException ex) {
			log("Did not find Account button, trying mobile version size for linked Account");
			rightDropdownButton.click();
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(accountDropdownLinkedButton));
			accountDropdownLinkedButton.click();

		}
		return PageFactory.initElements(driver, JalapenoAccountPage.class);
	}

	public JalapenoMyAccountProfilePage clickOnMyAccount() throws InterruptedException {
		log("Clicking on Account button - regular resolution");
		try {
			myAccount.click();
		} catch (ElementNotInteractableException ex) {
			log("Did not find Account button, trying mobile version size");
			javascriptClick(rightDropdownButton);
			new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(myAccountDropdownButton));
			myAccountDropdownButton.click();
		}
		return PageFactory.initElements(driver, JalapenoMyAccountProfilePage.class);
	}

	public JalapenoLoginPage clickOnLogout() throws InterruptedException {

		log("Clicking on Logout button - regular resolution");

		try {
			Thread.sleep(4000);
			if (IHGUtil.exists(driver, signout)) {
			javascriptClick(signout);
			} else {
				driver.manage().window().maximize();
				javascriptClick(signout);
			}

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
	
	public JalapenoLoginEnrollment clickOnLogoutEnrollment() {

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

		return PageFactory.initElements(driver, JalapenoLoginEnrollment.class);
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
			WebElement otherDocumentsButton =
					new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText("Other documents"))));
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
	
	public void unlinkDependentAccount() {
		JalapenoAccountPage accountPage = clickOnAccount();
		accountPage.clickOnUnlinkDependentAccount();
	}
	
	public void unlinkTrustedRepresentativeAccount() {
		JalapenoAccountPage accountPage = clickOnAccount();
		accountPage.clickOnUnlinkTrustedRepresentative();
	}
	public void editTrustedRepAccount() {
		JalapenoAccountPage accountPage = clickOnAccount();
		accountPage.clickOnEditTrustedRepAccount();
	}
}
