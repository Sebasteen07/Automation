// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class JalapenoAskAStaffV2Page2 extends JalapenoMenu {
		//navigation
		@FindBy(how = How.ID, using = "cancelButton")
		private WebElement backButton;
		@FindBy(how = How.ID, using = "continueButton")
		private WebElement submitButton;

		@FindBy(how = How.ID, using = "subject")
		private WebElement subjectText;
		@FindBy(how = How.ID, using = "question")
		private WebElement questionText;

		public JalapenoAskAStaffV2Page2(WebDriver driver) {
				super(driver);
		}

		public String getSubject() {
			    return subjectText.getText();
		}

		public String getQuestion() {
			    return questionText.getText();
		}

		public JalapenoHomePage submit() {
				submitButton.click();
				return PageFactory.initElements(driver, JalapenoHomePage.class);
		}

		
}
