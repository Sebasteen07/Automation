//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.physicians;

import static java.lang.Thread.sleep;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

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

	@FindBy(xpath = "//select[@name='active']/option[@value='1']")
	private WebElement yesActive;

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

	public AddPhysicianPage(WebDriver driver) {
		super(driver);
	}

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

	public AddPhysicianStep2EditLocationInfoPage addPhysician(String firstName, String lastName, String title, String dDEANumber, String email, String userID,
			String password) {
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
		return PageFactory.initElements(driver, AddPhysicianStep2EditLocationInfoPage.class);
	}

	public boolean isActiveGroupMemberYesOptionDisabled() {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		return yesActive.getAttribute("disabled") != null;
	}

	public AddPhysicianStep2EditLocationInfoPage deletePhysician() throws InterruptedException  {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);
		javascriptClick(btnDeletePhysican);
		sleep(5000);
		return PageFactory.initElements(driver, AddPhysicianStep2EditLocationInfoPage.class);
	}

}
