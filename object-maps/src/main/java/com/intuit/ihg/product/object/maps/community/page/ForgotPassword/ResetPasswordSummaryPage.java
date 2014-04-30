package com.intuit.ihg.product.object.maps.community.page.ForgotPassword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ResetPasswordSummaryPage extends BasePageObject {

	@FindBy(how = How.XPATH, using = ".//*[@id='frame']/p[3]/a")
	public WebElement Go_To_Sign_Page;
	
	public ResetPasswordSummaryPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	public void confirmPasswordReset(){
		IHGUtil.PrintMethodName();
		Go_To_Sign_Page.click();
		//driver.close();
	}
}


