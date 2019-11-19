package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import java.util.ArrayList;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;

public class JalapenoForgotPasswordPage3 extends MedfusionPage {

		@FindBy(how = How.LINK_TEXT, using = "Sign In Now.")
		private WebElement signInButton;

		@FindBy(how = How.ID, using = "secretAnswer_forgot")
		private WebElement secretAnswer;

		@FindBy(how = How.ID, using = "forgotEnterSecretAnswerFormContinueButton")
		private WebElement continueAndResetButton;

		public JalapenoForgotPasswordPage3(WebDriver driver, String url) {
				super(driver, url);
				IHGUtil.PrintMethodName();;
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
				webElementsList.add(signInButton);
				webElementsList.add(secretAnswer);
				webElementsList.add(continueAndResetButton);
				return assessPageElements(webElementsList);
		}

		public JalapenoForgotPasswordPage4 fillInSecretAnswer(String answer) {

				// catching webdriver exception which started to show up after selenium 2.45 and firefox 36 updates
				// try removing the try catch once newrelic is deprecated and fully removed
				try {
						secretAnswer.sendKeys(answer);
				} catch (org.openqa.selenium.WebDriverException e) {
						secretAnswer.sendKeys(answer);
				}
				continueAndResetButton.click();

				return PageFactory.initElements(driver, JalapenoForgotPasswordPage4.class);
		}

}
