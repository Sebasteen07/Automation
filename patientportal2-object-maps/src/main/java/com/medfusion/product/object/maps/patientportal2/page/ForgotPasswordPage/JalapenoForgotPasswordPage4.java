package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import java.util.ArrayList;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoForgotPasswordPage4 extends MedfusionPage {

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
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
				webElementsList.add(newPassword);
				webElementsList.add(confirmPassword);
				webElementsList.add(resetPasswordButton);
				return assessPageElements(webElementsList);
		}


		public JalapenoHomePage fillInNewPassword(String password) {
				newPassword.sendKeys(password);
				confirmPassword.sendKeys(password);

				resetPasswordButton.click();
				selectStatementIfRequired();
				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}

		private void selectStatementIfRequired() {
				if (new IHGUtil(driver).exists(electronicPaymentPreference)) {
						electronicPaymentPreference.click();
						okButton.click();
				}
		}
}
