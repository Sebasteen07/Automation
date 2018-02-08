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

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MyAccountPage.JalapenoMyAccountProfilePage;
import com.medfusion.product.patientportal2.pojo.JalapenoPatient;

public class JalapenoAccountPage extends BasePageObject {

	// won't work if linked accounts are disabled
	@FindBy(how = How.XPATH, using = "//*[@id='frame']/div[2]/ul/li/div/div[3]/button")
	private WebElement editMyAccountButton;
	
	@FindBy(how = How.XPATH, using = "//*[@id='frame']/div[3]/div/ul/li/div/div[3]/button")
	private WebElement editDependentAccountButton;

	@FindBy(how = How.XPATH, using = "//*[@id=\'frame\']/div[3]/div/button")
	private WebElement inviteNewButton;

	@FindBy(how = How.ID, using = "trustedRepFirstName")
	private WebElement trustedRepFirstNameInput;

	@FindBy(how = How.ID, using = "trustedRepLastName")
	private WebElement trustedRepLastNameInput;

	@FindBy(how = How.ID, using = "trustedRepEmail")
	private WebElement trustedRepEmailInput;

	@FindBy(how = How.ID, using = "trustedRepRelationship")
	private WebElement trustedRepRelationshipSelect;

	@FindBy(how = How.XPATH, using = "//*[@class='button primary ladda-button ng-binding inviteBtn pull-right']")
	private WebElement sendInvitationButton;

	public JalapenoAccountPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public JalapenoMyAccountProfilePage clickOnEditMyAccount() {

		log("Trying to click on Edit button for My Account");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(editMyAccountButton));
		editMyAccountButton.click();

		return PageFactory.initElements(driver, JalapenoMyAccountProfilePage.class);
	}
	
	public JalapenoMyAccountProfilePage clickOnEdiDependentAccount() {

		log("Trying to click on Dependent Edit button for Account");
		editDependentAccountButton.click();

		return PageFactory.initElements(driver, JalapenoMyAccountProfilePage.class);
	}

	public void inviteTrustedRepresentative(JalapenoPatient patient) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
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

	private void fillLightboxInputs(JalapenoPatient patient) {
		trustedRepFirstNameInput.sendKeys(patient.getFirstName());
		trustedRepLastNameInput.sendKeys(patient.getLastName());
		trustedRepEmailInput.sendKeys(patient.getEmail());
	}

	private void waitUntilSuccessMessageLoads(WebDriverWait wait, String fullName) {
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//*[@data-ng-show='inviteSuccess' and contains(.,'You have successfully invited " + fullName + " to be your trusted representative')]")));
	}
}
