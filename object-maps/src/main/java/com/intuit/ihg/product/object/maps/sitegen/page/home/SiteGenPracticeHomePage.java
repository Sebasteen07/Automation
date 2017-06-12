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
import com.intuit.ihg.product.object.maps.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.object.maps.sitegen.page.Integrations.ViewIntegrationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.InterfaceSetUp.InterfaceAdministrationPage;
import com.intuit.ihg.product.object.maps.sitegen.page.MerchantAccount.MerchantAccountPage;
import com.intuit.ihg.product.object.maps.sitegen.page.customforms.CreateCustomForms;
import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.DiscreteFormsList;
import com.intuit.ihg.product.object.maps.sitegen.page.location.ManageYourLocationsPage;
import com.intuit.ihg.product.object.maps.sitegen.page.permissionsAndPersonnelTypes.ManageYourGroupPersonnelTypesPage;
import com.intuit.ihg.product.object.maps.sitegen.page.personnel.ManageYourPersonnelPage;
import com.intuit.ihg.product.object.maps.sitegen.page.physicians.ManageYourPhysiciansPage;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;
import com.intuti.ihg.product.object.maps.sitegen.page.medfusionadmin.PracticeInfoPage;
import com.medfusion.common.utils.IHGUtil;

/**
 * @author bkrishnankutty
 * @Date 6/10/2013
 * @Description :- Page Object for SiteGen Practice HomePage
 * @Note :This is home page for practice
 */
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

	@FindBy(linkText = "Custom Forms")
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

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public SiteGenPracticeHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:-Indicates if the search page is loaded
	 * @return true or false
	 */
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

	/**
	 * @author bkrishnankutty
	 * @Desc:- Log out from site gen
	 * @return SiteGenLoginPage
	 * @throws InterruptedException
	 * 
	 */

	public SiteGenLoginPage clicklogout() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnklogout);
		return (clickLogout(driver, lnklogout));
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link Locations
	 * @return ManageYourLocationsPage
	 * @throws InterruptedException
	 */

	public ManageYourLocationsPage clickLnkLocations() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkLocations);
		lnkLocations.click();
		return PageFactory.initElements(driver, ManageYourLocationsPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link Physicians
	 * @return ManageYourPhysiciansPage
	 * @throws InterruptedException
	 */

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

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link Permissions
	 * @return ManageYourGroupPersonnelTypesPage
	 * @throws InterruptedException
	 */

	public ManageYourGroupPersonnelTypesPage clickLnkPermissions() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkPermissions);
		lnkPermissions.click();
		return PageFactory.initElements(driver, ManageYourGroupPersonnelTypesPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link InterfaceSetup
	 * @return InterfaceAdministrationPage
	 * @throws InterruptedException
	 */
	public InterfaceAdministrationPage clickLnkInterfaceSetup() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkInterfaceSetup);
		lnkInterfaceSetup.click();
		return PageFactory.initElements(driver, InterfaceAdministrationPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link IntegrationEngine
	 * @return ViewIntegrationsPage
	 * @throws InterruptedException
	 */
	public ViewIntegrationsPage clickLnkIntegrationEngine() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkIntegrationEngine);
		lnkIntegrationEngine.click();
		return PageFactory.initElements(driver, ViewIntegrationsPage.class);

	}


	/**
	 * * @author Shanthala
	 * 
	 * @Desc:- click on Link Custom Form
	 * @return ViewIntegrationsPage
	 * @throws InterruptedException
	 * 
	 */

	public CreateCustomForms clickCustomForms() throws Exception {

		log("Clicking on Custom Forms");
		IHGUtil.waitForElement(driver, 50, lnkCustomForms);
		try {
			lnkCustomForms.click();
		} catch (Exception e) {
			lnkCustomForms.click();
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

	/**
	 * @author:bbinisha
	 * @Desc: Navigating to Merchant Account List Page
	 * @throws Exception
	 * 
	 */
	public MerchantAccountPage clickOnMerchantAccountLink() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		IHGUtil.waitForElement(driver, 30, merchantAccount);
		merchantAccount.click();
		return PageFactory.initElements(driver, MerchantAccountPage.class);
	}



	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link IntegrationEngine
	 * @return ViewIntegrationsPage
	 * @throws InterruptedException
	 */
	public ManageYourPersonnelPage clickLnkPersonnelNonPhysicians() throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkPersonnelNonPhysicians);
		lnkPersonnelNonPhysicians.click();
		return PageFactory.initElements(driver, ManageYourPersonnelPage.class);

	}

	/**
	 * @author bkrishnankutty Description : Navigate to DiscreteFormPage
	 */
	public DiscreteFormsList clickOnDiscreteForms() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, discreteFormsLink);
		discreteFormsLink.click();
		return PageFactory.initElements(driver, DiscreteFormsList.class);
	}


	/**
	 * * @author Shanthala
	 * 
	 * @Desc:- click on Link Custom Form
	 * @return ViewIntegrationsPage
	 * @throws InterruptedException
	 * 
	 */

	// public DiscreteFormConfigurationUtilityPage clickLnkDiscreteForms() throws Exception {
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

	/**
	 * @author bkrishnankutty
	 * @Desc:- Generic method for logging out from site gen
	 * @return SiteGenLoginPage
	 * @param driver
	 * @param logout
	 * @return
	 * @throws InterruptedException
	 */
	public SiteGenLoginPage clickLogout(WebDriver driver, WebElement logout) throws InterruptedException {

		IHGUtil.PrintMethodName();
		IHGUtil util = new IHGUtil(driver);
		driver.switchTo().defaultContent();
		if (util.isRendered(logout)) {
			System.out.println("DEBUG: LOGOUT ELEMENT FOUND.");
			driver.manage().timeouts().implicitlyWait(SitegenConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
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

}
