package com.intuit.ihg.product.object.maps.community.page.ForgotUserId;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ForgotUserIdAnswerQuestionPage extends BasePageObject{
	
	//Page Objects
	
	@FindBy(how = How.ID, using = "answer")
	public WebElement Answer;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='forgot_user_id']/div/button")
	public WebElement btn_EmailMe;
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement btnContinue;

	
	@FindBy(how = How.LINK_TEXT, using = "Go To Sign In Page")
	public WebElement btn_Go_To_SignInPage;
	
	public ForgotUserIdAnswerQuestionPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);			
		}
	
	public void completeForgotUserFlow(String answer) {
		IHGUtil.PrintMethodName();
		Answer.sendKeys(answer); 
		btnContinue.click();				
		btn_Go_To_SignInPage.click();
		
	}
}