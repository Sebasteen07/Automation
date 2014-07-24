package com.medfusion.product.object.maps.jalapeno.page;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;


public class JalapenoHomePage extends BasePageObject {
	
	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 * @StepsToReproduce:
	 */
	
	@FindBy(how = How.ID, using = "user_id")
	private WebElement inputUserId;
	
	@FindBy(how = How.ID, using = "password")
	private WebElement inputPassword;
	
	@FindBy(how = How.ID, using = "signin_forgot_user_id")
	private WebElement forgotUserIdLink;
	
	@FindBy(how = How.ID, using = "signin_forgot_user_password")
	private WebElement forgotUserPasswordLink;
	
	@FindBy(how = How.ID, using = "signin_btn")
	private WebElement signInButton;
	
	@FindBy(how = How.CLASS_NAME, using = "button big blue ng-binding")
	private WebElement signUpNowLink;
	
	public JalapenoHomePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);

	}

}
