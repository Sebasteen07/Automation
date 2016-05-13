package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

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

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputEmail);
		webElementsList.add(continueButton);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
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
