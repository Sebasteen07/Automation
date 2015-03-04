package com.intuit.ihg.product.object.maps.sitegen.page.physicians;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

/**
 * @author bkrishnankutty
 * @Date 6/16/2013
 * @Description :- Page Object for SiteGen Add PhysicianPage
 * @Note :
 */
public class AddPhysicianPage extends BasePageObject {

	@FindBy(id = "firstname")
	private WebElement txtFirstName;

	@FindBy(id = "lastname")
	private WebElement txtLastName;

	@FindBy(id = "Title")
	private WebElement txtTitle;

	@FindBy(name = "gender")
	private WebElement rbtnGender;

	@FindBy(id = "dea_number")
	private WebElement txtDEANumber;

	@FindBy(id = "email")
	private WebElement txtEmailAddress;

	@FindBy(id = "email2")
	private WebElement txtConfirmEmail;

	@FindBy(name = "board_certify")
	private WebElement dropDownBoardCertified;

	@FindBy(name = "active")
	private WebElement dropDownActiveGroupMember;

	@FindBy(id = "userID")
	private WebElement txtUserID;

	@FindBy(id = "password")
	private WebElement txtPassword;

	@FindBy(id = "cfmpassword")
	private WebElement txtConfirmPassword;

	@FindBy(name = "submitPhys")
	private WebElement btnAddPhysican;

	@FindBy(name = "btn_Delete")
	private WebElement btnDeletePhysican;

	/**
	 * @author bkrishnankutty
	 * @Desc:- constructor for this Page
	 * @param driver
	 */
	public AddPhysicianPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, txtFirstName);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- To add the Physician
	 * @return AddPhysicianStep2EditLocationInfoPage
	 * 
	 * @param firstName
	 * @param lastName
	 * @param title
	 * @param dDEANumber
	 * @param email
	 * @param userID
	 * @param password
	 */
	public AddPhysicianStep2EditLocationInfoPage addPhysician(String firstName,
			String lastName, String title, String dDEANumber, String email,
			String userID, String password) {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		txtFirstName.sendKeys(firstName);
		txtLastName.sendKeys(lastName);
		txtTitle.sendKeys(title);
		rbtnGender.click();
		txtDEANumber.sendKeys(dDEANumber);
		txtEmailAddress.sendKeys(email);
		txtConfirmEmail.sendKeys(email);
		Select boardCertify = new Select(dropDownBoardCertified);
		boardCertify.selectByVisibleText("Yes");
		Select active = new Select(dropDownActiveGroupMember);
		active.selectByVisibleText("No");
		txtUserID.sendKeys(userID);
		txtPassword.sendKeys(password);
		txtConfirmPassword.sendKeys(password);
		btnAddPhysican.click();
		driver.switchTo().alert().accept();
		return PageFactory.initElements(driver,
				AddPhysicianStep2EditLocationInfoPage.class);

	}

	/**
	 * @author bkrishnankutty
	 * @Desc:- To delete the Physician
	 * @return AddPhysicianStep2EditLocationInfoPage
	 * 
	 */
	public AddPhysicianStep2EditLocationInfoPage deletePhysician() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		btnDeletePhysican.click();
		return PageFactory.initElements(driver,
				AddPhysicianStep2EditLocationInfoPage.class);

	}

}
