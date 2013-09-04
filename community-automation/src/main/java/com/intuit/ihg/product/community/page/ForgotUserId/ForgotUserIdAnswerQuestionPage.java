package com.intuit.ihg.product.community.page.ForgotUserId;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class ForgotUserIdAnswerQuestionPage extends BasePageObject{
	
	//Page Objects
	
	@FindBy(how = How.ID, using = "answer")
	public WebElement Answer;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='forgot_user_id']/div/button")
	public WebElement btn_EmailMe;

	public ForgotUserIdAnswerQuestionPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	

}