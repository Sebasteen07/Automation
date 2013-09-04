
package com.intuit.ihg.product.community.page.ForgotUserId;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class ForgotUserIdEnterEmailAddressPage extends BasePageObject{
	
	//Page Objects
	
	@FindBy(how = How.ID, using = "email")
	public WebElement EmailAddress;
	
	@FindBy(how = How.XPATH, using = ".//*[@id='forgot_userid_form']/div/button")
	public WebElement btn_Continue;

	public ForgotUserIdEnterEmailAddressPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	

}