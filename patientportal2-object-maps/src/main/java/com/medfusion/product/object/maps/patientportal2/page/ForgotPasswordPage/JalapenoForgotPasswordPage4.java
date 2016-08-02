package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoForgotPasswordPage4 extends BasePageObject {
	
	@FindBy(how = How.ID, using = "newPassword")
	public WebElement newPassword;
	
	@FindBy(how = How.ID, using = "confirmPassword")
	public WebElement confirmPassword;	
	
	@FindBy(how = How.ID, using = "resetPasswordButton")
	public WebElement resetPasswordButton;
	
	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement electronicPaymentPreference;

	@FindBy(how = How.ID, using = "updateMissingInfoButton")
	private WebElement okButton;
	
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
		selectStatementIfRequired();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
	
	private void selectStatementIfRequired() {
		if ( new IHGUtil(driver).exists(electronicPaymentPreference) ) {
			electronicPaymentPreference.click();
			okButton.click();
		}
	}
}
