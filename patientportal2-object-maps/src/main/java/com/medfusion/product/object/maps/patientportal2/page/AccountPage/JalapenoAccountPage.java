// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AccountPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;

public class JalapenoAccountPage extends JalapenoMenu {

		// won't work if linked accounts are disabled
		@FindBy(how = How.XPATH, using = "//*[contains(text(),'Edit')]")
		private WebElement editMyAccountButton;

		@FindBy(how = How.XPATH, using = "//*[contains(text(),'Dependents')]/..//*[contains(text(),'Edit')]")
		private WebElement editDependentAccountButton;

		@FindBy(how = How.XPATH, using = "//*[contains(text(),'Trusted Representatives')]/..//*[contains(text(),'Invite New')]")
		private WebElement inviteNewButton;

		@FindBy(how = How.ID, using = "trustedRepFirstName")
		private WebElement trustedRepFirstNameInput;

		@FindBy(how = How.ID, using = "trustedRepLastName")
		private WebElement trustedRepLastNameInput;

		@FindBy(how = How.ID, using = "trustedRepEmail")
		private WebElement trustedRepEmailInput;

		@FindBy(how = How.ID, using = "trustedRepRelationship")
		private WebElement trustedRepRelationshipSelect;

		//@FindBy(how = How.XPATH, using = "//*[@class='button primary ladda-button ng-binding inviteBtn pull-right']")
		@FindBy(how = How.ID, using = "sendInvitation")
		private WebElement sendInvitationButton;
		
		@FindBy(how = How.LINK_TEXT, using = "Unlink")
		private WebElement unLink;
		
		@FindBy(how = How.XPATH, using = "//button/span[contains(text(),'Unlink')]")
		private WebElement unLinkButton;

		public JalapenoAccountPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}
		
		public JalapenoMyAccountProfilePage clickOnEditMyAccount() {
				log("Trying to click on Edit button for My Account");
				wait.until(ExpectedConditions.visibilityOf(editMyAccountButton));
				editMyAccountButton.click();
				return PageFactory.initElements(driver, JalapenoMyAccountProfilePage.class);
		}

		public JalapenoMyAccountProfilePage clickOnEditDependentAccount() {
				log("Trying to click on Dependent Edit button for Account");
				editDependentAccountButton.click();

				return PageFactory.initElements(driver, JalapenoMyAccountProfilePage.class);
		}

		public void inviteTrustedRepresentative(Patient patient) {
				wait.until(ExpectedConditions.visibilityOf(inviteNewButton));

				inviteNewButton.click();

				waitUntilLightboxContentLoads(wait);
				fillLightboxInputs(patient);

				sendInvitationButton.click();

				String fullName = patient.getFirstName() + " " + patient.getLastName();
				waitUntilSuccessMessageLoads(wait, fullName);
		}

		private void waitUntilLightboxContentLoads(WebDriverWait wait) {
				List<WebElement> elements = new ArrayList<WebElement>();
				elements.add(trustedRepFirstNameInput);
				elements.add(trustedRepLastNameInput);
				elements.add(trustedRepEmailInput);
				elements.add(trustedRepRelationshipSelect);
				elements.add(sendInvitationButton);

				wait.until(ExpectedConditions.visibilityOfAllElements(elements));
		}

		private void fillLightboxInputs(Patient patient) {
				trustedRepFirstNameInput.sendKeys(patient.getFirstName());
				trustedRepLastNameInput.sendKeys(patient.getLastName());
				trustedRepEmailInput.sendKeys(patient.getEmail());
		}

		private void waitUntilSuccessMessageLoads(WebDriverWait wait, String fullName) {
				wait.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//p[contains(.,'You have successfully invited " + fullName + " to be your trusted representative')]")));
		}
		
		private void fillLightboxInputs(String fname,String lname,String email) {
			trustedRepFirstNameInput.sendKeys(fname);
			trustedRepLastNameInput.sendKeys(lname);
			trustedRepEmailInput.sendKeys(email);
	    }
		
		public void inviteTrustedRepresentative(String fname, String lname,String email) {
			wait.until(ExpectedConditions.visibilityOf(inviteNewButton));

			inviteNewButton.click();

			waitUntilLightboxContentLoads(wait);
			fillLightboxInputs(fname,lname, email);

			sendInvitationButton.click();

			String fullName = fname + " " + lname;
			waitUntilSuccessMessageLoads(wait, fullName);
	}
		
		public void clickOnUnlinkDependentAccount(){
			log("Trying to click on Dependent unlink link for Account");
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(unLink));
			unLink.click();
			
			log("Click on Unlink button");
			wait.until(ExpectedConditions.visibilityOf(unLinkButton));
			unLinkButton.click();
	}
		
		public void clickOnUnlinkTrustedRepresentative(){
			log("Trying to click on Trusted Representative unlink link");
			wait.until(ExpectedConditions.visibilityOf(unLink));
			unLink.click();
			
			log("Click on Unlink button");
			wait.until(ExpectedConditions.visibilityOf(unLinkButton));
			unLinkButton.click();
	}
}
