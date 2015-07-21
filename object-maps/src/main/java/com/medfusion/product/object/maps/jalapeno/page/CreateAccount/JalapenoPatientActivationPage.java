package com.medfusion.product.object.maps.jalapeno.page.CreateAccount;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Arrays;

public class JalapenoPatientActivationPage extends BasePageObject {

	@FindBy(how = How.ID, using = "userid")
	private WebElement inputUserId;

	@FindBy(how = How.ID, using = "password")
	private WebElement inputPassword;

	@FindBy(how = How.ID, using = "secretQuestion")
	private WebElement inputSecretQuestion;

	@FindBy(how = How.ID, using = "secretAnswer")
	private WebElement inputSecretAnswer;

	@FindBy(how = How.ID, using = "phone1")
	private WebElement inputPhone1;

	@FindBy(how = How.ID, using = "phone2")
	private WebElement inputPhone2;

	@FindBy(how = How.ID, using = "phone3")
	private WebElement inputPhone3;

	@FindBy(how = How.ID, using = "phone_type")
	private WebElement inputPhoneType;

	@FindBy(how = How.ID, using = "prevStep")
	private WebElement prevStep;

	@FindBy(how = How.ID, using = "finishStep")
	private WebElement finishStep;

	@FindBy(how = How.XPATH, using = "//*[@id=\"activateAccountStep1_form\"]/div[1]/input")
	private WebElement postalCode;

	@FindBy(how = How.XPATH, using = "//*[@id=\"activateAccountStep1_form\"]/div[2]/select")
	private WebElement birthDate_month;

	@FindBy(how = How.XPATH, using = "//*[@id=\"activateAccountStep1_form\"]/div[2]/input[1]")
	private WebElement birthDate_day;

	@FindBy(how = How.XPATH, using = "//*[@id=\"activateAccountStep1_form\"]/div[2]/input[2]")
	private WebElement birthDate_year;

	@FindBy(how = How.XPATH, using = "//*[@id=\"activateAccountStep1_form_btns\"]/ul[2]/li/button")
	private WebElement nextStep;

	@FindBy(how = How.ID, using = "preferredLocationId")
	private WebElement primaryLocationElement;

	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement electronicPaymentPreference;

	@FindBy(how = How.ID, using = "updateStatementPrefButton")
	private WebElement okButton;

	public JalapenoPatientActivationPage(WebDriver driver) {
		this(driver, null);
	}

	public JalapenoPatientActivationPage(WebDriver driver, String url) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("Loading activation page");

		if (url != null && !url.isEmpty()) {
			String sanitizedUrl = url.trim();
			log("URL: " + sanitizedUrl);
			driver.get(sanitizedUrl);
		}

		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public void verifyPatientIdentity(String zipCode, String month, String day, String year) {
		IHGUtil.PrintMethodName();

		log("Verifying identity");
		log("Setting zipCode as " + zipCode);
		postalCode.sendKeys(zipCode);

		log("Setting DOB month as " + month);
		birthDate_month.sendKeys(month);

		log("Setting DOB day as " + day);
		birthDate_day.sendKeys(day);

		log("Setting DOB year as " + year);
		birthDate_year.sendKeys(year);

		log("Click on continue button");
		nextStep.click();

	}

	public JalapenoHomePage fillInPatientActivation(String userId, String password, String secretQuestion,
			String secretAnswer, String phoneNumber) {
		IHGUtil.PrintMethodName();

		log("Setting User Name as " + userId);
		new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(inputUserId));
		inputUserId.sendKeys(userId);

		log("Setting Password as " + password);
		inputPassword.sendKeys(password);

		log("Secret Question as " + secretQuestion);
		inputSecretQuestion.sendKeys(secretQuestion);

		log("Secret Answer as " + secretAnswer);
		inputSecretAnswer.sendKeys(secretAnswer);

		log("Phone number as " + phoneNumber);

		//splitting phone to the text boxes
		String phone1 = phoneNumber.substring(0, 3);
		String phone2 = phoneNumber.substring(3,6);
		String phone3 = phoneNumber.substring(6,10);

		inputPhone1.sendKeys(phone1);
		inputPhone2.sendKeys(phone2);
		inputPhone3.sendKeys(phone3);

		// if there is a dropdown for selecting location, pick the first one
		if ( IHGUtil.exists(driver, 2, primaryLocationElement) ) {
			Select primaryLocationSelect = new Select(primaryLocationElement);
			primaryLocationSelect.selectByIndex(1);
		}

		finishStep.click();
		selectStatementIfRequired();

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public JalapenoHomePage fillInPatientActivation(String userId, String password,
			PropertyFileLoader testData) {
		return fillInPatientActivation(userId, password, testData.getSecretQuestion(),
				testData.getSecretAnswer(), testData.getPhoneNumber());
	}

	public void selectStatementIfRequired() {
		if ( new IHGUtil(driver).exists(electronicPaymentPreference) ) {
			electronicPaymentPreference.click();
			okButton.click();
		}
	}

	public boolean assessPatientActivationPageElements() {

		boolean allElementsDisplayed = false;

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserId);
		webElementsList.add(inputPassword);
		webElementsList.add(inputSecretQuestion);
		webElementsList.add(inputSecretAnswer);
		webElementsList.add(inputPhone1);
		webElementsList.add(inputPhone2);
		webElementsList.add(inputPhone3);
		webElementsList.add(inputPhoneType);
		webElementsList.add(prevStep);
		webElementsList.add(finishStep);

		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 10, w);
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			}

			catch (Throwable e) {
				log(Arrays.toString(e.getStackTrace()));
			}

		}
		return allElementsDisplayed;
	}

}
