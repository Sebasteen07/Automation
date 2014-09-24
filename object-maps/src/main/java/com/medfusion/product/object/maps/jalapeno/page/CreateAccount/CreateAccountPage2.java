package com.medfusion.product.object.maps.jalapeno.page.CreateAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class CreateAccountPage2 extends BasePageObject {

	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 */
	
	@FindBy(how = How.ID, using = "userid")
	private WebElement inputUserId;
	
	@FindBy(how = How.ID, using = "password")
	private WebElement inputPassword;
	
	@FindBy(how = How.ID, using = "confirmpass")
	private WebElement inputConfirmPassword;
	
	@FindBy(how = How.ID, using = "secretQuestion")
	private WebElement inputSecretQuestion;
	
	@FindBy(how = How.ID, using = "secretAnswer")
	private WebElement inputSecretAnswer;
	
	@FindBy(how = How.ID, using = "phone")
	private WebElement inputPhone;
	
	public CreateAccountPage2(WebDriver driver) {
		super(driver);
	}

}
