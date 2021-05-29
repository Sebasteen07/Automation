// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.ForgotPasswordPage;

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
