package com.intuit.ihg.product.object.maps.community.page.ForgotPassword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ResetPasswordEnterNewPasswordPage extends BasePageObject{
	
	//Page Objects
	
	@FindBy(how = How.ID, using = "answer")
	public WebElement Answer;
	
	@FindBy(how = How.ID, using = "newpassword1")
	public WebElement New_Password_1;
	
	@FindBy(how = How.ID, using = "newpassword2")
	public WebElement New_Password_2;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='forgot_password_reset_form']/div/button")
	public WebElement Email_Me;
	
	
	
	public ResetPasswordEnterNewPasswordPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	public ResetPasswordSummaryPage resetPassword(String sQuestion, String sNewPassword){
		IHGUtil.PrintMethodName();
		Answer.sendKeys(sQuestion);		
		New_Password_1.sendKeys(sNewPassword);
		New_Password_2.sendKeys(sNewPassword);
		Email_Me.click();
		return PageFactory.initElements(driver, ResetPasswordSummaryPage.class);
		
		
	}

}