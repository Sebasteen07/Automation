package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

import java.util.ArrayList;

import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.AuthUserLinkAccountPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;

public class JalapenoForgotPasswordPage2 extends MedfusionPage {

		@FindBy(how = How.ID, using = "closeButton")
		public WebElement closeButton;

		public JalapenoForgotPasswordPage2(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
				webElementsList.add(closeButton);
				return assessPageElements(webElementsList);
		}

		public JalapenoLoginPage clickCloseButton() {

				IHGUtil.PrintMethodName();
				log("Clicking on Close button");
				javascriptClick(closeButton);
				return PageFactory.initElements(driver, JalapenoLoginPage.class);
		}
		
		public AuthUserLinkAccountPage clickOnCloseButton() {

			IHGUtil.PrintMethodName();
			log("Clicking on Close button");
			javascriptClick(closeButton);
			return PageFactory.initElements(driver, AuthUserLinkAccountPage.class);
	}

}
