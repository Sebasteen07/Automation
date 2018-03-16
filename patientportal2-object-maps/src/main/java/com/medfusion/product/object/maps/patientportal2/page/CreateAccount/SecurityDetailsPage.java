package com.medfusion.product.object.maps.patientportal2.page.CreateAccount;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.MedfusionPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;

public class SecurityDetailsPage extends MedfusionPage {

	public static final String ACTIVE_TAB_XPATH_SELECTOR = "//div[contains(@class,'tab-pane') and contains(@class,'active')]";

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='userid']")
	private WebElement inputUserId;

	@FindBy(how = How.XPATH, using = ACTIVE_TAB_XPATH_SELECTOR + "//*[@id='password']")
	private WebElement inputPassword;

	@FindBy(how = How.ID, using = "secretQuestion")
	private WebElement selectSecretQuestion;

	@FindBy(how = How.ID, using = "secretAnswer")
	private WebElement inputSecretAnswer;

	@FindBy(how = How.ID, using = "phone1")
	private WebElement inputPhone1;

	@FindBy(how = How.ID, using = "phone2")
	private WebElement inputPhone2;

	@FindBy(how = How.ID, using = "phone3")
	private WebElement inputPhone3;

	@FindBy(how = How.ID, using = "phone_type")
	private WebElement selectPhoneType;

	@FindBy(how = How.XPATH, using = "/html/body/div[4]/div/div[2]/div[3]/div[1]/div/div/div/div/div/div/div/div/div/div[4]/div/form/div[1]/div[3]/mf-locations/div/div/div[1]/span")
	private WebElement primaryLocationElement;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[5]/ul/li/div[3]/span/div")
	private WebElement setLocation;

	@FindBy(how = How.ID, using = "prevStep")
	private WebElement buttonPreviousStep;

	@FindBy(how = How.ID, using = "finishStep")
	private WebElement buttonFinishStep;

	@FindBy(how = How.ID, using = "paymentPreference_Electronic")
	private WebElement electronicPaymentPreference;

	@FindBy(how = How.ID, using = "paymentPreference_Paper")
	private WebElement paperPaymentPreference;

	@FindBy(how = How.ID, using = "paymentPreference_Both")
	private WebElement bothPaymentPreference;
	
	@FindBy(how = How.ID, using = "updateMissingInfoButton")
	private WebElement okButton;


	public SecurityDetailsPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputUserId);
		webElementsList.add(inputPassword);
		webElementsList.add(selectSecretQuestion);
		webElementsList.add(inputSecretAnswer);
		webElementsList.add(inputPhone1);
		webElementsList.add(inputPhone2);
		webElementsList.add(inputPhone3);
		webElementsList.add(selectPhoneType);
		webElementsList.add(buttonFinishStep);

		return assessPageElements(webElementsList);
	}

	public JalapenoHomePage fillAccountDetailsAndContinue(String userId, String password, PropertyFileLoader testData) {
		return fillAccountDetailsAndContinue(userId, password, testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber(), 3);
	}

	public JalapenoHomePage fillAccountDetailsAndContinue(String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber) {
		return fillAccountDetailsAndContinue(userId, password, secretQuestion, secretAnswer, phoneNumber, 3);
	}

	public JalapenoHomePage fillAccountDetailsAndContinue(String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber,
			int statementPreference) {
		IHGUtil.PrintMethodName();
		fillAccountDetails(userId, password, secretQuestion, secretAnswer, phoneNumber, statementPreference);
		buttonFinishStep.click();
		selectStatementIfRequired(statementPreference);
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public void fillAccountDetailsAndContinueWithError(String userId, String password, PropertyFileLoader testData) {
		fillAccountDetails(userId, password, testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getPhoneNumber(), 3);
		javascriptClick(buttonFinishStep);
	}

	private void fillAccountDetails(String userId, String password, String secretQuestion, String secretAnswer, String phoneNumber, int statementPreference) {
		log("Setting User Name and Password as " + userId + "/" + password);
		inputUserId.sendKeys(userId);
		inputPassword.sendKeys(password);

		selectSecretQuestion.sendKeys(secretQuestion);

		inputSecretAnswer.sendKeys(secretAnswer);

		inputPhone1.sendKeys(phoneNumber.substring(0, 3));
		inputPhone2.sendKeys(phoneNumber.substring(3, 6));
		inputPhone3.sendKeys(phoneNumber.substring(6, 10));

		if (IHGUtil.exists(driver, 1, primaryLocationElement)) {
			primaryLocationElement.click();
			IHGUtil.waitForElement(driver, 120, setLocation);
			setLocation.click();
		}
	}

	public void selectStatementIfRequired(int deliveryPref) {

		if (new IHGUtil(driver).exists(electronicPaymentPreference)) {
			log("Statement delivery preference lightbox is displayed");

			new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(okButton));
			if (deliveryPref == 1) {
				paperPaymentPreference.click();
			} else if (deliveryPref == 2) {
				electronicPaymentPreference.click();
			} else if (deliveryPref == 3) {
				bothPaymentPreference.click();
			}
			okButton.click();
			
			/*
			 * try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
			 */
		}

	}

}
