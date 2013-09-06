package com.intuit.ihg.product.community.page.MyAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class MyAccountMenuPage extends BasePageObject {

	// Page Objects

	@FindBy(how = How.ID, using = "profile")
	public WebElement profile;

	@FindBy(how = How.ID, using = "email")
	public WebElement email;

	@FindBy(how = How.ID, using = "password")
	public WebElement password;

	@FindBy(how = How.ID, using = "security")
	public WebElement security;

	public MyAccountMenuPage(WebDriver driver) {

		super(driver);
		PageFactory.initElements(driver, this);

	}

	public MyAccountProfilePage clickMyAccountProfile() {
		IHGUtil.PrintMethodName();
		profile.click();
		return PageFactory.initElements(driver, MyAccountProfilePage.class);
	}

	public MyAccountSecurityQuestionPage clickMyAccountSecurityQuestion() {
		IHGUtil.PrintMethodName();
		security.click();
		return PageFactory.initElements(driver,
				MyAccountSecurityQuestionPage.class);
	}

}
