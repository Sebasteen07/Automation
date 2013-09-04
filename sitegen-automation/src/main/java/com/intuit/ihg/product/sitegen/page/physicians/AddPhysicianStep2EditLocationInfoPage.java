package com.intuit.ihg.product.sitegen.page.physicians;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/17/2013
 * @Description :- Page Object for SiteGen AddPhysician Step2
 *              EditLocationInfoPage
 * @Note :
 */
public class AddPhysicianStep2EditLocationInfoPage extends BasePageObject {

	@FindBy(xpath = "//input[@value='Edit Location Information']")
	private WebElement btnEditLocationInformation;

	@FindBy(xpath = "//input[@value='Go back to Manage Physicians']")
	private WebElement btnGoBackToManagePhysicians;

	@FindBy(name = "btnDelete")
	private WebElement btnConfirmDeletePhysican;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public AddPhysicianStep2EditLocationInfoPage(WebDriver driver) {
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
					btnGoBackToManagePhysicians);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Click on Button GoBackToManagePhysicians
	 * @return ManageYourPhysiciansPage
	 * 
	 */
	public ManageYourPhysiciansPage clickBtnGoBackToManagePhysicians() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		btnGoBackToManagePhysicians.click();
		return PageFactory.initElements(driver, ManageYourPhysiciansPage.class);
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- Click on Button ConfirmDeletePhysican
	 * @return ManageYourPhysiciansPage
	 * 
	 */
	public ManageYourPhysiciansPage deletePhysican() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		btnConfirmDeletePhysican.click();
		return PageFactory.initElements(driver, ManageYourPhysiciansPage.class);
	}
}
