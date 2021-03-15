//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import java.util.ArrayList;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoForgotPasswordPage extends MedfusionPage {

		@FindBy(how = How.ID, using = "emailOrUsername")
		public WebElement inputEmail;

		@FindBy(how = How.ID, using = "forgotFormContinueButton")
		public WebElement continueButton;

		public JalapenoForgotPasswordPage(WebDriver driver, String url) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
				webElementsList.add(inputEmail);
				webElementsList.add(continueButton);
				return assessPageElements(webElementsList);
		}

		public JalapenoForgotPasswordPage(WebDriver driver) {
				super(driver);
		}

		public JalapenoForgotPasswordPage2 fillInDataPage(String email) {
				IHGUtil.PrintMethodName();

				log("Setting email address as " + email);
				inputEmail.sendKeys(email);

				log("Clicking on Continue button");

				continueButton.click();

				return PageFactory.initElements(driver, JalapenoForgotPasswordPage2.class);
		}

}
