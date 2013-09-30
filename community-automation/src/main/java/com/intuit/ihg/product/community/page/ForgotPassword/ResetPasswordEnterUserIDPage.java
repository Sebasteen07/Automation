package com.intuit.ihg.product.community.page.ForgotPassword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ResetPasswordEnterUserIDPage extends BasePageObject {

	// Page Objects

	@FindBy(how = How.ID, using = "userid")
	public WebElement User_ID;

	@FindBy(how = How.XPATH, using = ".//*[@id='forgot_password_form']/div/button")
	public WebElement btn_Continue;

	public ResetPasswordEnterUserIDPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

	public ResetPasswordEnterNewPasswordPage enterUserID(String sUser) {
		IHGUtil.PrintMethodName();
		User_ID.sendKeys(sUser);
		btn_Continue.click();			
		return PageFactory.initElements(driver, ResetPasswordEnterNewPasswordPage.class);
		
	}

}
