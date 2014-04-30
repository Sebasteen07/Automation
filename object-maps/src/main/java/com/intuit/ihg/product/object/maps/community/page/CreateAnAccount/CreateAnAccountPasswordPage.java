package com.intuit.ihg.product.object.maps.community.page.CreateAnAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class CreateAnAccountPasswordPage extends BasePageObject{
	
	@FindBy(how = How.NAME, using = "inputs:0:input:input")
	public WebElement userid;

	@FindBy(how = How.NAME, using = "inputs:2:input:input")
	public WebElement password;

	@FindBy(how = How.NAME, using = "inputs:3:input:input")
	public WebElement confirmPassword;
	
	@FindBy(how = How.NAME, using = "inputs:4:input:input")
	public WebElement chooseSecurityQuestion;

	@FindBy(how = How.NAME, using = "inputs:5:input:input")
	public WebElement securityAnswer;

	@FindBy(how = How.NAME, using = "buttons:submit")
	public WebElement submit;
	
	public CreateAnAccountPasswordPage(WebDriver driver) {
		super(driver);
	}

	public CreateAnAccountWelcomePage clickSubmit() {
		submit.click();
		return PageFactory.initElements(driver, CreateAnAccountWelcomePage.class);
	}
	
	public boolean findUserIdInput(WebDriver driver) {
		IHGUtil.waitForElement(driver, 120, userid);
		return userid.isDisplayed();
	}
}
