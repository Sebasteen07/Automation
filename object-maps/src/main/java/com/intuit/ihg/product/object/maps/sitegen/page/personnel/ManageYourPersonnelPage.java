package com.intuit.ihg.product.object.maps.sitegen.page.personnel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/10/2013
 * @Description :- Page Object for SiteGen Manage Your Personnel Page
 * @Note :
 */
public class ManageYourPersonnelPage extends BasePageObject {

	@FindBy(xpath = "//input[@value='Add New Personnel']")
	private WebElement btnAddNewPersonnel;

	@FindBy(linkText = "Personnel (Non-Physicians)")
	private WebElement lnkPersonnel;

	@FindBy(linkText = "Import Personnel and Physicians")
	private WebElement lnkImportPersonnelAndPhysicians;

	@FindBy(linkText = "Export Personnel")
	private WebElement lnkExportPersonnel;

	@FindBy(linkText = "List by Location")
	private WebElement lnkListByLocation;

	@FindBy(linkText = "List All Personnel")
	private WebElement lnkListAllPersonnel;

	@FindBy(xpath = "//input[@value='Import Personnel and Physicians']")
	private WebElement btnImportPersonnelAndPhysicians;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public ManageYourPersonnelPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, btnAddNewPersonnel);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- To click on Link ImportPersonnelAndPhysicians
	 * @return ImportPersonnelAndPhysicians
	 * 
	 */
	public ImportPersonnelAndPhysiciansPage clicklnkImportPersonnelAndPhysicians() {

		IHGUtil.PrintMethodName();
		lnkImportPersonnelAndPhysicians.click();
		return PageFactory.initElements(driver, ImportPersonnelAndPhysiciansPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- To click on Link ImportPersonnelAndPhysicians
	 * @return ImportPersonnelAndPhysicians
	 * 
	 */
	public ImportPersonnelAndPhysiciansPage clickBtnImportPersonnelAndPhysicians() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		btnImportPersonnelAndPhysicians.click();
		return PageFactory.initElements(driver, ImportPersonnelAndPhysiciansPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- To click on Link ExportPersonnel
	 * @return ExportPersonnel
	 * 
	 */
	public ExportPersonnelPage clicklnkExportPersonnel() {

		IHGUtil.PrintMethodName();
		lnkExportPersonnel.click();
		return PageFactory.initElements(driver, ExportPersonnelPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- To click on Link ListAllPersonnel
	 * @return ManageYourPhysiciansPage
	 * 
	 */
	public ManageYourPersonnelPage clicklnkListAllPersonnel() {

		IHGUtil.PrintMethodName();
		lnkListAllPersonnel.click();
		return PageFactory.initElements(driver, ManageYourPersonnelPage.class);

	}

}
