package com.intuit.ihg.product.object.maps.community.page.ForgotPassword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.community.page.CommunityHomePage;

public class ResetPasswordSignInNewPassword extends BasePageObject{
	
	//Page Objects
	
	@FindBy(how = How.ID, using = "username")
	public WebElement inputUsername;
	
	@FindBy(how = How.ID, using = "password")
	public WebElement inputPassword;
	
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Sign In')]")
	public  WebElement btn_Sign_In;
	
	public ResetPasswordSignInNewPassword(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	
	public CommunityHomePage resetPassword(String sUser,String sPassword){
		IHGUtil.PrintMethodName();
		inputUsername.sendKeys(sUser);
		inputPassword.sendKeys(sPassword);
		btn_Sign_In.click();
		return PageFactory.initElements(driver, CommunityHomePage.class);
		
		
	}

}