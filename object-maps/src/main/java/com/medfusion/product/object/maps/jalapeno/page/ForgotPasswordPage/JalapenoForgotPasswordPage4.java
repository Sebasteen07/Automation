package com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

public class JalapenoForgotPasswordPage4 extends BasePageObject {
	
	@FindBy(how = How.ID, using = "newPassword")
	public WebElement newPassword;
	
	@FindBy(how = How.ID, using = "confirmPassword")
	public WebElement confirmPassword;	
	
	@FindBy(how = How.ID, using = "resetPasswordButton")
	public WebElement resetPasswordButton;
	
	public JalapenoForgotPasswordPage4(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading ForgotPasswordPage4");
		driver.manage().window().maximize();
	}
	
	public boolean assessForgotPasswordPage4Elements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(newPassword);
		webElementsList.add(confirmPassword);
		webElementsList.add(resetPasswordButton);

		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 20, w);
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			}

			catch (Throwable e) {
				log(e.getStackTrace().toString());
			}

		}
		return allElementsDisplayed;
	} 
	
	public JalapenoHomePage fillInNewPassword(String password) {
		newPassword.sendKeys(password);
		confirmPassword.sendKeys(password);
		
		resetPasswordButton.click();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
}
