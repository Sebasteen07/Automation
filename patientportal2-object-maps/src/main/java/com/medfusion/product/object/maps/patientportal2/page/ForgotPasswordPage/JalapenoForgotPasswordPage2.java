package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;

public class JalapenoForgotPasswordPage2 extends BasePageObject {

	@FindBy(how = How.ID, using = "closeButton")
	public WebElement closeButton;
	
	public JalapenoForgotPasswordPage2(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading ForgotPasswordPage2");
		driver.manage().window().maximize();
	}
	
	public boolean assessForgotPasswordPage2Elements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(closeButton);

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
	
	public JalapenoLoginPage clickCloseButton() {
		
		IHGUtil.PrintMethodName();
		log("Clicking on Close button");
		closeButton.click();
		return PageFactory.initElements(driver, JalapenoLoginPage.class);
	}
	
}
