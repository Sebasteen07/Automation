package com.medfusion.product.object.maps.patientportal2.page;


import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountPage;

public class JalapenoPage extends BasePageObject {

	@FindBy(how = How.LINK_TEXT, using = "My Account")
	private WebElement myAccount;

	@FindBy(how = How.ID, using = "open-top-loggedIn-btn")
	private WebElement rightDropdownButton;

	@FindBy(how = How.ID, using = "signout_dropdown")
	private WebElement signoutDropdownButton;

	@FindBy(how = How.ID, using = "signout")
	private WebElement signoutButton;

	public JalapenoPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public JalapenoMyAccountPage clickOnMyAccount(WebDriver driver) throws Exception {

		log("Trying to click on My Account button - regular resolution");

		try {
			myAccount.click();
		} catch (NoSuchElementException ex) {
			log("Did not find MyAccount button, trying mobile version size");
			rightDropdownButton.click();
			myAccount.click();
		}

		return PageFactory.initElements(driver, JalapenoMyAccountPage.class);
	}

	public JalapenoLoginPage logout(WebDriver driver) {

		IHGUtil.PrintMethodName();
		log("Trying to click on Logout button - regular resolution");

		try {
			signoutButton.click();
		} catch (NoSuchElementException ex) {
			log("Did not find Logout button, trying mobile version size");
			rightDropdownButton.click();
			signoutDropdownButton.click();
		} catch (ElementNotVisibleException ex) {
			log("Element is not currently visible, trying mobile version size");
			rightDropdownButton.click();
			signoutDropdownButton.click();
		}

		return PageFactory.initElements(driver, JalapenoLoginPage.class);
	}


}
