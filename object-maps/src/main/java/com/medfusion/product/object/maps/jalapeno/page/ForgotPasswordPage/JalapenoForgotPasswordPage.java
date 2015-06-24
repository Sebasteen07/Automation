package com.medfusion.product.object.maps.jalapeno.page.ForgotPasswordPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class JalapenoForgotPasswordPage extends BasePageObject {

	@FindBy(how = How.ID, using = "emailOrUsername")
	public WebElement inputEmail;

	@FindBy(how = How.ID, using = "forgotFormContinueButton")
	public WebElement continueButton;
	
	public JalapenoForgotPasswordPage(WebDriver driver, String url) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading ForgotPasswordPage");
		String sanitizedUrl = url.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl);
		driver.manage().window().maximize();
		IHGUtil.printCookies(driver);
		PageFactory.initElements(driver, this);
	}
	
	public JalapenoForgotPasswordPage(WebDriver driver) {
		super(driver);		
	}
	
	public boolean assessForgotPasswordPageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputEmail);
		webElementsList.add(continueButton);

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
	
	public JalapenoForgotPasswordPage2 fillInDataPage(String email) throws InterruptedException {
		IHGUtil.PrintMethodName();
		
		log("Setting email address as " + email);
		inputEmail.sendKeys(email);
		
		log("Clicking on Continue button");
		
		continueButton.click();
		
		//waitForPageTitle(null, 60);

		return PageFactory.initElements(driver, JalapenoForgotPasswordPage2.class);
	}
	
}
