package com.intuit.ihg.product.object.maps.sitegen.page.InterfaceSetUp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.sitegen.page.home.SiteGenHomePage;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/28/2013
 * @Description :- Page Object for SiteGen Interface Administration Page
 * @Note :
 */
public class InterfaceAdministrationPage extends BasePageObject {

	@FindBy(xpath = "(//input[@name='otherfeatures'])[5]")
	private WebElement chckIntegrationEngine;

	@FindBy(linkText = "Delete Integration Type")
	private WebElement lnkDeleteIntegrationType;

	@FindBy(xpath = "//input[@value='Activate Integration Type']")
	private WebElement btnActivateIntegrationType;

	@FindBy(xpath = "(//input[@name='otherfeatures'])[6]")
	private WebElement chckIHGIntegrationAPI;

	@FindBy(xpath = "//input[@value='Confirm Delete']")
	private WebElement btnConfirmDelete;

	@FindBy(xpath = ".//form/table[1]/tbody/tr[2]/td[1]")
	private WebElement textIntegrationEngine;

	@FindBy(xpath = "//a[@title='Home']")
	public WebElement lnkHome;

	@FindBy(xpath = "//input[@value='Cancel']")
	private WebElement btnCancel;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public InterfaceAdministrationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:-Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6,
					btnActivateIntegrationType);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Add Integration Type
	 * @return String
	 * 
	 */

	public String AddIntegrationEngine() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		chckIntegrationEngine.click();
		btnActivateIntegrationType.click();
		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.visibilityOf(lnkDeleteIntegrationType));
		return textIntegrationEngine.getText();
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- clean TestData Integration type
	 * @return void
	 * 
	 */
	public void cleanTestDataIntegrationEng() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		IHGUtil util = new IHGUtil(driver);
		try {

			while (util.isRendered(lnkDeleteIntegrationType)) {
				lnkDeleteIntegrationType.click();
				WebDriverWait wait = new WebDriverWait(driver, 2);
				wait.until(ExpectedConditions.visibilityOf(btnCancel));
				btnConfirmDelete.click();
			}

			log("Removed all IntegrationType and clean the test data ######");
		} catch (org.openqa.selenium.NoSuchElementException c) {
			log("The test data was already clean ######");
		} catch (Exception e) {
			log("The test data was already clean ######");
		}
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on Link Home
	 * @return SiteGenLoginPage
	 * @throws InterruptedException 
	 * 
	 */
	public SiteGenHomePage clickLinkHome() throws InterruptedException {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElementInDefaultFrame(driver, 30, lnkHome);
		try{
			lnkHome.click();
		} catch (Exception e) {
			Actions ac = new Actions(driver);
			ac.clickAndHold(lnkHome);
			log("Clicked on Home Icon");
		}
		
		return PageFactory.initElements(driver, SiteGenHomePage.class);
	}
}
