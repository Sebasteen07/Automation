package com.intuit.ihg.product.object.maps.sitegen.page.physicians;

import static org.testng.AssertJUnit.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/18/2013
 * @Description :- Page Object for SiteGen ManageYourPhysiciansPage
 * @Note :
 */
public class ManageYourPhysiciansPage extends BasePageObject {

	@FindBy(xpath = "//input[@value='Add New Physician']")
	private WebElement btnAddNewPhysician;

	@FindBy(linkText = "Add Physician")
	private WebElement lnkAddPhysician;

	@FindBy(linkText = "List All Physicians")
	private WebElement lnkListAllPhysicians;

	@FindBy(linkText = "Edit Physician")
	private WebElement lnkEditPhysician;
	
	@FindBy(linkText = "Import Personnel and Physicians")
	private WebElement lnkImportPersonnelAndPhysicians;
	
	@FindBy(linkText = "Export Personnel")
	private WebElement lnkExportPersonnel;
	
	@FindBy(linkText = "List by Location")
	private WebElement lnkListByLocation;
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public ManageYourPhysiciansPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, btnAddNewPhysician);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on link AddPhysician
	 * @return AddPhysicianPage
	 */
	public AddPhysicianPage clicklnkAddPhysician() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		lnkAddPhysician.click();
		return PageFactory.initElements(driver, AddPhysicianPage.class);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- click on link EditPhysician
	 * @return AddPhysicianPage
	 */
	public AddPhysicianPage clicklnkEditPhysician() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		lnkEditPhysician.click();
		return PageFactory.initElements(driver, AddPhysicianPage.class);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- will return the ProviderName
	 * @return String
	 * 
	 * @param lastname
	 * @param firstName
	 * @param title
	 * @return
	 */
	public String getProviderName(String lastname, String firstName,
			String title) {
		String str = lastname + ", " + firstName + ": " + title;

		return str;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Clean the Test data
	 * @return void
	 * @throws Exception
	 *             Note :- This method is not important from Deployment
	 *             acceptance perspective ie y assert is here
	 */
	public void cleanTestPhysiciansData() throws Exception {
		while (islnkEditPhysicianDisplayed()) {
			AddPhysicianPage pAddPhysicianPage = clicklnkEditPhysician();
			AddPhysicianStep2EditLocationInfoPage pAddPhysicianStep2EditLocationInfoPage = pAddPhysicianPage
					.deletePhysician();
			pAddPhysicianStep2EditLocationInfoPage.deletePhysican();
			assertTrue(SitegenlUtil.verifyTextPresent(driver,
					"Information Updated", 2));
		}

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Checking if link EditPhysician is Displayed or not
	 * @return true or false
	 * 
	 */
	public boolean islnkEditPhysicianDisplayed() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 2, lnkEditPhysician);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	
	/**
	 * @author bkrishnankutty
	 * @Desc:- To click on Link ImportPersonnelAndPhysicians
	 * @return AddPhysicianStep2EditLocationInfoPage
	 * 
	 */
	public AddPhysicianStep2EditLocationInfoPage clicklnkImportPersonnelAndPhysicians() {

		IHGUtil.PrintMethodName();
		lnkImportPersonnelAndPhysicians.click();
		return PageFactory.initElements(driver,
				AddPhysicianStep2EditLocationInfoPage.class);

	}
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- To click on Link ExportPersonnel
	 * @return AddPhysicianStep2EditLocationInfoPage
	 * 
	 */
	public AddPhysicianStep2EditLocationInfoPage clicklnkExportPersonnel() {

		IHGUtil.PrintMethodName();
		lnkExportPersonnel.click();
		return PageFactory.initElements(driver,
				AddPhysicianStep2EditLocationInfoPage.class);

	}
	
	/**
	 * @author bkrishnankutty
	 * @Desc:- To click on Link ListAllPhysicians
	 * @return ManageYourPhysiciansPage
	 * 
	 */
	public ManageYourPhysiciansPage clicklnkListAllPhysicians() {

		IHGUtil.PrintMethodName();
		lnkListAllPhysicians.click();
		return PageFactory.initElements(driver,
				ManageYourPhysiciansPage.class);

	}
}
