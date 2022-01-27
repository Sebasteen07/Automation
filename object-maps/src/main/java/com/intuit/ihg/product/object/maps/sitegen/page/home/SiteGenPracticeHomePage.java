//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.home;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil;
import com.intuit.ihg.product.object.maps.onlinesolutions.ManageSolutionsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.Integrations.ViewIntegrationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.InterfaceSetUp.InterfaceAdministrationPage;
import com.intuit.ihg.product.object.maps.sitegen.page.MerchantAccount.MerchantAccountPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomForms;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.location.ManageYourLocationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.permissionsAndPersonnelTypes.ManageYourGroupPersonnelTypesPage;
import com.intuit.ihg.product.object.maps.sitegen.page.personnel.ManageYourPersonnelPage;
import com.intuit.ihg.product.object.maps.sitegen.page.pharmacy.ManageYourPharmacies;
import com.intuit.ihg.product.object.maps.sitegen.page.physicians.ManageYourPhysiciansPage;
import com.intuti.ihg.product.object.maps.sitegen.page.medfusionadmin.PracticeInfoPage;
import com.intuti.ihg.product.object.maps.sitegen.page.onlineBillPay.EstatementPage;
import com.medfusion.common.utils.IHGConstants;
import com.medfusion.common.utils.IHGUtil;

public class SiteGenPracticeHomePage extends BasePageObject {

	@FindBy(how = How.CSS, using = "a.logoutlink > span")
	public WebElement lnklogout;

	@FindBy(linkText = "Interface Setup")
	private WebElement lnkInterfaceSetup;

	@FindBy(linkText = "Locations")
	private WebElement lnkLocations;

	@FindBy(linkText = "Physicians/Providers")
	private WebElement lnkPhysicians;

	@FindBy(linkText = "Permissions and Personnel Types")
	private WebElement lnkPermissions;

	@FindBy(linkText = "Integration Engine")
	private WebElement lnkIntegrationEngine;

	@FindBy(linkText = "Configure Custom Forms")
	private WebElement lnkCustomForms;

	@FindBy(linkText = "Merchant Account")
	private WebElement merchantAccount;

	@FindBy(linkText = "Personnel (Non-Physicians)")
	private WebElement lnkPersonnelNonPhysicians;

	@FindBy(linkText = "Forms")
	private WebElement formsLink;

	@FindBy(xpath = ".//a[@href = '/configurator/forms/']")
	private WebElement discreteFormsLink;

	@FindBy(xpath = "//div[@class='locationUrl']/a/img/..")
	private WebElement patientPortalUrl;

	@FindBy(linkText = "Practice Information")
	private WebElement practiceInfoLink;

	@FindBy(linkText = "Return to Main")
	private WebElement returnToMain;

	@FindBy(linkText = "Pharmacies")
	private WebElement pharmacyLink;

	@FindBy(linkText = "Online Bill Pay")
	private WebElement onlineBillPay;

	@FindBy(linkText = "eStatements")
	private WebElement eStatementsLink;

	@FindBy(linkText = "Online Solutions")
	private WebElement lnkOnlineSolutions;

	public SiteGenPracticeHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();

		boolean result = false;
		try {
			result = lnkLocations.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public SiteGenLoginPage clicklogout() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnklogout);
		return (clickLogout(driver, lnklogout));
	}
	
	public ManageYourLocationsPage clickLnkLocations() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkLocations);
		lnkLocations.click();
		return PageFactory.initElements(driver, ManageYourLocationsPage.class);

	}

	public ManageYourPhysiciansPage clickLnkPhysicians() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkPhysicians);
		try {
			lnkPhysicians.click();
		} catch (Exception e) {
			lnkPhysicians.click();
		}
		return PageFactory.initElements(driver, ManageYourPhysiciansPage.class);
	}

	public ManageYourGroupPersonnelTypesPage clickLnkPermissions() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkPermissions);
		lnkPermissions.click();
		return PageFactory.initElements(driver, ManageYourGroupPersonnelTypesPage.class);
	}

	public InterfaceAdministrationPage clickLnkInterfaceSetup() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkInterfaceSetup);
		lnkInterfaceSetup.click();
		return PageFactory.initElements(driver, InterfaceAdministrationPage.class);

	}

	public ViewIntegrationsPage clickLnkIntegrationEngine() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkIntegrationEngine);
		lnkIntegrationEngine.click();
		return PageFactory.initElements(driver, ViewIntegrationsPage.class);
	}

	public CreateCustomForms clickCustomForms() throws Exception {

		log("Clicking on Custom Forms");
		IHGUtil.waitForElement(driver, 50, formsLink);
		try {
			formsLink.click();
			IHGUtil.waitForElement(driver, 50, lnkCustomForms);
			lnkCustomForms.click();
			returnToMain.click();
		} catch (Exception e) {
			formsLink.click();
			IHGUtil.waitForElement(driver, 50, lnkCustomForms);
			lnkCustomForms.click();
			returnToMain.click();
		}
		Thread.sleep(2000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		if (TestConfig.getBrowserType().equals(BrowserTypeUtil.BrowserType.iexplore)) {
			driver.manage().window().maximize();
		}

		return PageFactory.initElements(driver, CreateCustomForms.class);
	}

	public MerchantAccountPage clickOnMerchantAccountLink() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		IHGUtil.waitForElement(driver, 30, merchantAccount);
		merchantAccount.click();
		return PageFactory.initElements(driver, MerchantAccountPage.class);
	}

	public ManageYourPersonnelPage clickLnkPersonnelNonPhysicians() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkPersonnelNonPhysicians);
		lnkPersonnelNonPhysicians.click();
		return PageFactory.initElements(driver, ManageYourPersonnelPage.class);
	}

	public DiscreteFormsList clickOnDiscreteForms() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, discreteFormsLink);
		discreteFormsLink.click();
		return PageFactory.initElements(driver, DiscreteFormsList.class);
	}

	public DiscreteFormsList clickLnkDiscreteForms() throws Exception {

		log("Clicking on Discrete forms");
		IHGUtil.waitForElement(driver, 50, formsLink);
		try {
			formsLink.click();
			IHGUtil.waitForElement(driver, 50, discreteFormsLink);
			discreteFormsLink.click();
		} catch (Exception e) {
			formsLink.click();
			IHGUtil.waitForElement(driver, 50, discreteFormsLink);
			discreteFormsLink.click();
		}
		Thread.sleep(2000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		if (TestConfig.getBrowserType().equals(BrowserTypeUtil.BrowserType.iexplore)) {
			driver.manage().window().maximize();
		}

		return PageFactory.initElements(driver, DiscreteFormsList.class);
	}

	public String getPatientPortalUrl() {
		IHGUtil.waitForElement(driver, 10, patientPortalUrl);
		return patientPortalUrl.getAttribute("href");
	}

	public SiteGenLoginPage clickLogout(WebDriver driver, WebElement logout) throws InterruptedException {

		IHGUtil.PrintMethodName();
		IHGUtil util = new IHGUtil(driver);
		driver.switchTo().defaultContent();
		if (util.isRendered(logout)) {
			System.out.println("DEBUG: LOGOUT ELEMENT FOUND.");
			driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
			try {
				logout.click();
			} catch (Exception e) {
				Actions ac = new Actions(driver);
				ac.clickAndHold(logout).build().perform();
				log("Clicked on logout");
			}

		}
		SiteGenLoginPage homePage = PageFactory.initElements(driver, SiteGenLoginPage.class);
		System.out.println("### DELETE ALL COOKIES");
		driver.manage().deleteAllCookies();
		return homePage;
	}

	public PracticeInfoPage openPracticeInfo() {
		practiceInfoLink.click();
		return PageFactory.initElements(driver, PracticeInfoPage.class);
	}

	public ManageYourPharmacies clickOnPharmacy() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, pharmacyLink);
		pharmacyLink.click();
		return PageFactory.initElements(driver, ManageYourPharmacies.class);
	}

	public EstatementPage clickOnOnlineBillPay() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, onlineBillPay);
		onlineBillPay.click();
		eStatementsLink.click();
		return PageFactory.initElements(driver, EstatementPage.class);
	}

	public ManageSolutionsPage clickOnOnlineSolutions() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkOnlineSolutions);
		lnkOnlineSolutions.click();
		return PageFactory.initElements(driver, ManageSolutionsPage.class);
	}
}
