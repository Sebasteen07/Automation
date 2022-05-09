// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoPayBillsConfirmationPage extends JalapenoMenu {

		@FindBy(how = How.ID, using = "comment")
		private WebElement comment;

		@FindBy(how = How.ID, using = "makepayment")
		private WebElement submitPayment;

		@FindBy(how = How.ID, using = "confirmationBack")
		private WebElement backButton;

		@FindBy(how = How.ID, using = "cardEnding")
		private WebElement cardEnding;
		
		@FindBy(how = How.XPATH, using = "//div[@class='notification-message']/p")
		private WebElement notificationMessage;

		public JalapenoPayBillsConfirmationPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		public JalapenoHomePage commentAndSubmitPayment(String commentString) {
				log("Insert optional comment");
				comment.sendKeys(commentString);

				log("Click on Submit Payment button");
				javascriptClick(submitPayment);

				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}

		public String getCreditCardEnding() {
				return cardEnding.getText().substring(cardEnding.getText().length() - 4);
		}
		
		public boolean isDuplicatePaymentErrorMessageDisplayed() {
			try {
				log("Looking for error message");
				return notificationMessage.isDisplayed();
			} catch (Exception e) {
			}
			return false;
		}
}
