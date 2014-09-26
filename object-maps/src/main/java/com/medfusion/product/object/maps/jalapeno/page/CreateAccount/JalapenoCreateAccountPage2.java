package com.medfusion.product.object.maps.jalapeno.page.CreateAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

public class JalapenoCreateAccountPage2 extends BasePageObject {

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
	
	@FindBy(how = How.XPATH, using = ".//*[@id='createAccountStep1_form']/p[2]/button[2]")
	private WebElement finishAndSignInElement;
	
	public JalapenoCreateAccountPage2(WebDriver driver) {
		super(driver);
	}
	
	
	public JalapenoHomePage fillInDataPage2(String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber) {

		log("Setting User Name as " + userId);
		inputUserId.sendKeys(userId);
		log("Setting Password as " + password);
		inputPassword.sendKeys(password);
		log("Setting Confirm Password as " + password);
		inputConfirmPassword.sendKeys(password);
		
		log("Secret Question as " + secretQuestion);
		inputSecretQuestion.sendKeys(secretQuestion);
		
		log("Secret Answer as " + secretAnswer);
		inputSecretAnswer.sendKeys(secretAnswer);
		
		log("Phone number as " + phoneNumber);
		inputPhone.sendKeys(phoneNumber);
		
		finishAndSignInElement.click();
		
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

}
