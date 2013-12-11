package com.intuit.ihg.product.sitegen.page.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.BrowserTypeUtil;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayed;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.page.SiteGenLoginPage;
import com.intuit.ihg.product.sitegen.page.Integrations.ViewIntegrationsPage;
import com.intuit.ihg.product.sitegen.page.InterfaceSetUp.InterfaceAdministrationPage;
import com.intuit.ihg.product.sitegen.page.MerchantAccount.MerchantAccountPage;
import com.intuit.ihg.product.sitegen.page.customforms.CreateCustomForms;
import com.intuit.ihg.product.sitegen.page.customforms.ManageYourFormsPage;
import com.intuit.ihg.product.sitegen.page.discreteforms.DiscreteFormsPage;
import com.intuit.ihg.product.sitegen.page.location.ManageYourLocationsPage;
import com.intuit.ihg.product.sitegen.page.permissionsAndPersonnelTypes.ManageYourGroupPersonnelTypesPage;
import com.intuit.ihg.product.sitegen.page.personnel.ManageYourPersonnelPage;
import com.intuit.ihg.product.sitegen.page.physicians.ManageYourPhysiciansPage;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

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

	@FindBy (xpath = ".//a[@href = '/configurator/forms/']")
	private WebElement discreteFormsLink;
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public SiteGenPracticeHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
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
		SitegenlUtil sutil = new SitegenlUtil(driver);
		return (sutil.clickLogout(driver, lnklogout));
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link Locations
	 * @return ManageYourLocationsPage
	 * @throws InterruptedException
	 */

	public ManageYourLocationsPage clickLnkLocations()
			throws InterruptedException {
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

	public ManageYourPhysiciansPage clickLnkPhysicians()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkPhysicians);
		try{
		lnkPhysicians.click();
		}catch(Exception e) {
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

	public ManageYourGroupPersonnelTypesPage clickLnkPermissions()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkPermissions);
		lnkPermissions.click();
		return PageFactory.initElements(driver,
				ManageYourGroupPersonnelTypesPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link InterfaceSetup
	 * @return InterfaceAdministrationPage
	 * @throws InterruptedException
	 */
	public InterfaceAdministrationPage clickLnkInterfaceSetup()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkInterfaceSetup);
		lnkInterfaceSetup.click();
		return PageFactory.initElements(driver,
				InterfaceAdministrationPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link IntegrationEngine
	 * @return ViewIntegrationsPage
	 * @throws InterruptedException
	 */
	public ViewIntegrationsPage clickLnkIntegrationEngine()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkIntegrationEngine);
		lnkIntegrationEngine.click();
		return PageFactory.initElements(driver, ViewIntegrationsPage.class);

	}


	/**
	 * * @author Shanthala
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
		}catch(Exception e) {
			lnkCustomForms.click();
		}
		Thread.sleep(2000);
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		if(TestConfig.getBrowserType().equals(BrowserTypeUtil.BrowserType.iexplore)) {
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
		IHGUtil.waitForElement(driver, 30 ,merchantAccount);
		merchantAccount.click();
		return PageFactory.initElements(driver, MerchantAccountPage.class);	
	}



	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link IntegrationEngine
	 * @return ViewIntegrationsPage
	 * @throws InterruptedException
	 */
	public ManageYourPersonnelPage clickLnkPersonnelNonPhysicians()
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, lnkPersonnelNonPhysicians);
		lnkPersonnelNonPhysicians.click();
		return PageFactory.initElements(driver, ManageYourPersonnelPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * Description : Navigate to DiscreteFormPage
	 */
	public DiscreteFormsPage clickOnDiscreteForms() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, discreteFormsLink);
		discreteFormsLink.click();
		return PageFactory.initElements(driver,DiscreteFormsPage.class);
	}


}
