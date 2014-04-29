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

public class MyAccountEmailAddressPage extends BasePageObject {

	// Page Objects
	@FindBy(how = How.XPATH, using = "//div[@id='sidebar_email']//input[@id='password']")
	public WebElement password;
	
	@FindBy(how = How.ID, using = "newemail")
	public WebElement newEmail;

	@FindBy(how = How.ID, using = "confirmemail")
	public WebElement confirmEmail;

	@FindBy(how = How.XPATH, using = "//div[@id='sidebar_email']//button[@type='submit']")
	public WebElement btnSaveChanges;
	
	@FindBy(how = How.XPATH, using = "//h2[contains(text(),'Success')]")
	public WebElement successNotification;

	public MyAccountEmailAddressPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean areElementsDisplayed() throws InterruptedException {

		WebElement[] elements = {password, newEmail, confirmEmail };

		for (WebElement element : elements) {
			if (!element.isDisplayed()) {
				log("Element :" + element.toString() + " is not displayed");
				return false;
			}
		}
		return true;
	}
	
	public void setEmail(WebDriver driver,String sEmail, String sPassword) throws Exception {
		
		password.sendKeys(sPassword);
		Thread.sleep(1000);
		newEmail.sendKeys(sEmail);
		Thread.sleep(1000);
		confirmEmail.sendKeys(sEmail);
	}
	
	public boolean sucessNotification(WebDriver driver) throws Exception {

		(new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By
						.xpath("//h2[contains(text(),'Success')]")));
		
		return successNotification.isDisplayed();
	}
}
