package com.intuit.ihg.product.community.page.MyAccount;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class MyAccountPasswordPage extends BasePageObject {

	// Page Objects

	@FindBy(how = How.XPATH, using = "//div[@id='sidebar_password']//input[@id='password']")
	public WebElement password;

	@FindBy(how = How.ID, using = "newpassword")
	public WebElement newPassword;

	@FindBy(how = How.ID, using = "confirmpassword")
	public WebElement confirmPassword;

	@FindBy(how = How.XPATH, using = "//div[@id='sidebar_password']//button[@type='submit']")
	public WebElement btnSaveChanges;
	
	@FindBy(how = How.XPATH, using = "//h2[contains(text(),'Success')]")
	public WebElement successNotification;

	public MyAccountPasswordPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean areElementsDisplayed() throws InterruptedException {

		WebElement[] elements = { password, newPassword, confirmPassword };

		for (WebElement element : elements) {
			if (!element.isDisplayed()) {
				log("Element :" + element.toString() + " is not displayed");
				return false;
			}
		}
		return true;
	}
	
	public void changePassword(String sPassword, String sNewPassword) throws Exception {
		
		password.sendKeys(sPassword);
		newPassword.sendKeys(sNewPassword);
		confirmPassword.sendKeys(sNewPassword);
	}
	
	public boolean sucessNotification(WebDriver driver) throws Exception {

		(new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//h2[contains(text(),'Success')]")));
		
		return successNotification.isDisplayed();
	}

}
