// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AccountPage;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.pojos.Patient;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
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

		@FindBy(how = How.ID, using = "sendInvitation")
		private WebElement sendInvitationButton;
		
		@FindBy(how = How.LINK_TEXT, using = "Unlink")
		private WebElement unLink;
		
		@FindBy(how = How.XPATH, using = "//button/span[contains(text(),'Unlink')]")
		private WebElement unLinkButton;
		
		@FindBy(how = How.XPATH, using = "//label[contains(text(),'Manage access per category')]")
		private WebElement manageAccessPerCategory;
		
		@FindBy(how = How.XPATH, using = "//*[contains(text(),' Trusted Representatives ')]/..//*[contains(text(),'Edit')]")
		private WebElement editTrustedRep;

		@FindBy(how=How.ID,using="sendInvitation")
		private WebElement btnContinueAuthPopup;
		
		public JalapenoAccountPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}
		
		public JalapenoMyAccountProfilePage clickOnEditMyAccount() {
				log("Trying to click on Edit button for My Account");
				if (IHGUtil.waitForElement(driver, 50, editMyAccountButton)) {
				editMyAccountButton.click();
				}
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
				log("Trying to click on Health authorization popup");
			
				handleHealthInfoAuthPopUp();
				
				waitUntilLightboxContentLoads(wait);
				fillLightboxInputs(patient);

				sendInvitationButton.click();

				String fullName = patient.getFirstName() + " " + patient.getLastName();
				waitUntilSuccessMessageLoads(wait, fullName);
		}

		private void handleHealthInfoAuthPopUp() {
			wait.until(ExpectedConditions.visibilityOf(btnContinueAuthPopup));
			btnContinueAuthPopup.click();
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
			
			log("Trying to click on Health authorization popup");
			
			handleHealthInfoAuthPopUp();

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
		public void inviteTrustedRepresentativeWithPermission(Patient patient) throws InterruptedException {
			waitUntilLightboxContentLoads(wait);
			fillLightboxInputs(patient);
			sendInvitationButton.click();
			String fullName = patient.getFirstName() + " " + patient.getLastName();
			waitUntilSuccessMessageLoads(wait, fullName);

		}

		public void clickInviteButton() throws InterruptedException {
			wait.until(ExpectedConditions.visibilityOf(inviteNewButton));
			inviteNewButton.click();
			log("Invite New Button got clicked");
			handleHealthInfoAuthPopUp();
			manageAccessPerCategory.click();
			log("Manage Access Per Category Radio Button got clicked");

		}
		public void clickOnRdoManageAccessPerCategory() throws InterruptedException {
			wait.until(ExpectedConditions.visibilityOf(manageAccessPerCategory));
			manageAccessPerCategory.click();
			log("Manage Access Per Category Radio Button got clicked");

		}

		public void givingPermissionWithModuleName(String textModule, String manageAccessPref) {

			String moduleName = "//div[span[contains(text()," + "'" + textModule + "')]]";
			String preferenceValue = "/following-sibling::div/input[@id=" + "'" + manageAccessPref + "'" + "]";
			String preferenceToBeSelected = moduleName + preferenceValue;
			WebElement selectedPreference = driver.findElement(By.xpath(preferenceToBeSelected));
			if (new IHGUtil(driver).exists(manageAccessPerCategory)) {

				log("Manage Access Per Category is present");
				if (manageAccessPref == "fullAccess") {
					new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(selectedPreference));
					selectedPreference.click();
				} else if (manageAccessPref == "viewOnly") {
					new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(selectedPreference));
					selectedPreference.click();
				} else if (manageAccessPref == "noAccess") {
					new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(selectedPreference));
					selectedPreference.click();
                } else if (manageAccessPref == "fullAccessHealthRecord") {
					new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(selectedPreference));
					selectedPreference.click();

				}

			}
		}
		public void clickOnEditTrustedRepAccount(){
			log("Trying to click on Trusted Rep Edit Button");
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(editTrustedRep));
			editTrustedRep.click();
	      }
		public void clickOnSaveMyChangesButton(){
			log("Trying to click on Trusted Rep Edit Button");
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(sendInvitationButton));
			sendInvitationButton.click();
	      }
}
