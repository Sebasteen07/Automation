
package com.intuit.ihg.product.object.maps.community.page.ForgotUserId;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class ForgotUserIdClickForgottenUserIdPage extends BasePageObject{
	
	//Page Objects
	
	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Have you forgotten your User ID?')]")
	public WebElement link_forgottenUserId;
	

	public ForgotUserIdClickForgottenUserIdPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	

}
