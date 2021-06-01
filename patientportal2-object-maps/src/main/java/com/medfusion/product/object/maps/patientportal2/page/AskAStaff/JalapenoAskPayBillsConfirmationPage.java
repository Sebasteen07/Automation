// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import java.util.ArrayList;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoAskPayBillsConfirmationPage extends JalapenoMenu {

	@FindBy(how = How.ID, using = "continueButton")
	private WebElement submitButton;

	@FindBy(how = How.ID, using = "confirmationBack")
		private WebElement backButton;

	@FindBy(how = How.XPATH, using = "//span[text()=' MASTERCARD -5100']")
	private WebElement cardEnding;

	public JalapenoAskPayBillsConfirmationPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}

	public JalapenoHomePage commentAndSubmitPayment(String commentString) {

		log("Click on Submit Payment button");
		javascriptClick(submitButton);

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	@Deprecated // same functionality as areBasicElementsPresent(), this method is used by
				// integrations
	public boolean assessPayBillsConfirmationPageElements() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		webElementsList.add(submitButton);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public String getCreditCardEnding() {
		System.out.println("The card text=" + cardEnding.getText());
		return cardEnding.getText().substring(cardEnding.getText().length() - 4);
	}
}
