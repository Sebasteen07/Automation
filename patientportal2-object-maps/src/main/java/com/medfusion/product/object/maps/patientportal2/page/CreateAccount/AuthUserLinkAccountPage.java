package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class AuthUserLinkAccountPage extends MedfusionPage {
	@FindBy(how = How.ID, using ="password")
	private WebElement passwordInput;

	@FindBy(how = How.ID, using = "userid")
	private WebElement userIdInput;

	@FindBy(how = How.XPATH, using = "(//select[@id='relationshipToPatient'])[1]")
	private WebElement relationshipFirstSelect;

	@FindBy(how = How.ID, using = "email")
	private WebElement emailInput;

	@FindBy(how = How.ID, using = "firstName")
	private WebElement firstNameInput;

	@FindBy(how = How.ID, using = "lastName")
	private WebElement lastNameInput;

	@FindBy(how = How.XPATH, using = "(//select[@id='relationshipToPatient'])[2]")
	private WebElement relationshipSecondSelect;

	@FindBy(how = How.ID, using = "next")
    private WebElement continueButton2;

	@FindBy(how = How.ID, using = "nextStep")
	private WebElement enterPortalButton;

	public AuthUserLinkAccountPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		//webElementsList.add(enterPortalButton);
		webElementsList.add(relationshipSecondSelect);
		webElementsList.add(lastNameInput);
		webElementsList.add(firstNameInput);
		webElementsList.add(relationshipFirstSelect);
		webElementsList.add(userIdInput);
		webElementsList.add(passwordInput);
		webElementsList.add(emailInput);

		return assessPageElements(webElementsList);
	}

	public JalapenoHomePage linkPatientToCreateGuardian(String login, String password, String relationship) {
		IHGUtil.PrintMethodName();

		userIdInput.sendKeys(login);
		passwordInput.sendKeys(password);
		log("Guardian login / password: " + login + " / " + password);

		Select relationshipPatient = new Select(relationshipFirstSelect);
		relationshipPatient.selectByVisibleText(relationship);

		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(enterPortalButton));
		enterPortalButton.click();

		handleWeNeedToConfirmSomethingModal();

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public SecurityDetailsPage continueToCreateGuardianOnly(String name, String lastname, String relationship) {
		IHGUtil.PrintMethodName();

		firstNameInput.clear();
		firstNameInput.sendKeys(name);
		lastNameInput.clear();
		lastNameInput.sendKeys(lastname);
		log("Guardian name: " + name + " " + lastname);

		Select relationshipPatient = new Select(this.relationshipSecondSelect);
		relationshipPatient.selectByVisibleText(relationship);

		//workaround for validation, triggers after focus leaves element
		firstNameInput.sendKeys("");

		javascriptClick(continueButton2);

		return PageFactory.initElements(driver, SecurityDetailsPage.class);
	}

	public void checkDependentInfo(String name, String lastname, String email) {
		IHGUtil.PrintMethodName();

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + name + "')]")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'" + lastname + "')]")));
		wait.until(ExpectedConditions.textToBePresentInElementValue(emailInput, email));
	}
}
