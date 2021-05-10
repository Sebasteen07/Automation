// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class AccessRules extends SettingsTab {

	@FindBy(how = How.XPATH, using = "//div//input[@id='checkbox1']")
	private WebElement checkNewPatient;

	@FindBy(how = How.XPATH,
			using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/fieldset[2]/div[1]/label")
	private WebElement checkNewPatientLabel;

	@FindBy(how = How.ID, using = "checkbox2")
	private WebElement checkShowInsurancePage;

	@FindBy(how = How.XPATH, using = "//label[@for='showPrivacyPolicyMessageConfig']//i")
	private WebElement checkShowPrivacyPolicyPage;
	
	@FindBy(how = How.XPATH, using = "//label[@for='showPrivacyPolicyMessageConfig']//input")
	private WebElement displayPrivacyPolicyLoginless;	

	@FindBy(how = How.XPATH,
			using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/fieldset[1]/div[1]/div[1]/label[1]/i[1]")
	private WebElement displayPrivacyPolicyLoginlessCheck;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Display Privacy Policy')]")
	private WebElement displayPrivacyPolicyLoginlesseLabel;

	@FindBy(how = How.XPATH, using = "//div//input[@id='checkbox4']")
	private WebElement checkLoginlessExistingPatient;

	@FindBy(how = How.XPATH,
			using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/fieldset[2]/div[1]/div[1]/label[1]/i[1]")
	private WebElement enableOTPCheck;

	@FindBy(how = How.XPATH,
			using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/fieldset[2]/div[1]/div[1]/label[2]")
	private WebElement enableOTPLabel;

	@FindBy(how = How.XPATH, using = "//input[@id='allowOtp']")
	private WebElement enableOTP;

	@FindBy(how = How.XPATH,
			using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/fieldset[2]/div[2]/label")
	private WebElement checkLoginlessExistingPatientLabel;

	@FindBy(how = How.ID, using = "radio0")
	private WebElement selectIDPPatientSchedulingIdentity;

	@FindBy(how = How.ID, using = "ssonew0")
	private WebElement selectIDPNewPatient;

	@FindBy(how = How.ID, using = "ssoexist0")
	private WebElement selectIDPExistingPatient;

	@FindBy(how = How.ID, using = "radio1")
	private WebElement selectIDPMedfusionSSO;

	@FindBy(how = How.XPATH,
			using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[2]/div[1]/button[1]")
	private WebElement buttonSaveLoginLess;

	@FindAll({@FindBy(css = ".btn.btn-primary")})
	private List<WebElement> buttonList;

	@FindAll({@FindBy(xpath = "//div[@class=\"col-md-12\"]/a")})
	private List<WebElement> urlList;

	@FindBy(how = How.ID, using = "enableloginless")
	private WebElement enableLoginless;

	// *******************WebElements for anonymous flow*****************************
	@FindBy(how = How.ID, using = "enableanonymous")
	private WebElement enableAnonymous;

	@FindBy(how = How.ID, using = "showPrivacyForAnonymousConfig")
	private WebElement displayPrivacyPolicyAnonymous;

	@FindBy(how = How.ID, using = "showDuplicatePatient")
	private WebElement allowDuplicatePatientAnonymous;

	@FindBy(how = How.ID, using = "enableOtpAnonymousConfig")
	private WebElement enableOTPAnonymous;

	@FindBy(how = How.XPATH,
			using = "//div[@class='col-md-12']//div[@class='row']//div[@class='col-md-12']//button[@class='btn btn-primary'][contains(text(),'Save')]")
	private WebElement buttonSaveAnonymous;

	public AccessRules(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(urlList.get(0));

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public String getLoginlessURL() {
		commonMethods.highlightElement(urlList.get(0));
		return urlList.get(0).getText();
	}

	public String isLLNewPatientSelected() {
		commonMethods.highlightElement(checkNewPatientLabel);
		commonMethods.highlightElement(checkNewPatient);
		return checkNewPatient.isSelected() ? "true" : "false"; // .getCssValue("background-color"); // .getCssValue("background-color"); //
																														// .getAttribute("ng-reflect-model");
	}

	public String isLLInsurancePageSelected() {
		commonMethods.highlightElement(checkShowInsurancePage);
		return checkShowInsurancePage.isSelected() ? "true" : "false"; // .getCssValue("background-color"); // .getAttribute("ng-reflect-model");
	}

	public boolean isLLPrivacyPolicySelected() {
		commonMethods.highlightElement(displayPrivacyPolicyLoginlesseLabel);
		commonMethods.highlightElement(displayPrivacyPolicyLoginlessCheck);
		return displayPrivacyPolicyLoginless.isSelected();
	}

	public String isEnableOTPSelected() {
		commonMethods.highlightElement(enableOTPLabel);
		commonMethods.highlightElement(enableOTPCheck);
		return enableOTP.getAttribute("ng-reflect-model");
	}

	public void clickEnableOTP() {
		commonMethods.highlightElement(enableOTPCheck);
		enableOTPCheck.click();
		commonMethods.highlightElement(buttonSaveLoginLess);
		buttonSaveLoginLess.click();
	}

	public String isLLExistingPatientSelected() {
		commonMethods.highlightElement(checkLoginlessExistingPatientLabel);
		commonMethods.highlightElement(checkLoginlessExistingPatient);
		return checkLoginlessExistingPatient.isSelected() ? "true" : "false"; // .getCssValue("backgroundColor"); // .getAttribute("ng-reflect-model");
	}

	public void selectLLNewPatient() {
		commonMethods.highlightElement(checkNewPatient);
		checkNewPatient.click();
	}

	public void selectLLShowInsurancePage() {
		commonMethods.highlightElement(checkShowInsurancePage);
		checkShowInsurancePage.click();
	}

	public void selectLLShowPrivacyPolicyPage() {
		commonMethods.highlightElement(checkShowPrivacyPolicyPage);
		checkShowPrivacyPolicyPage.click();
	}

	public void selectLLExistingPatient() {
		commonMethods.highlightElement(checkLoginlessExistingPatient);
		checkLoginlessExistingPatient.click();
	}

	public void selectIDPLogin() {
		selectIDPPatientSchedulingIdentity.click();
	}

	public void selectIDPNewPatient() {
		selectIDPNewPatient.click();
	}

	public void selectIDPNewExistingPatient() {
		selectIDPExistingPatient.click();
	}

	public String getIDPUrl() {
		return urlList.get(1).getText();
	}

	public void selectSSOFlow() {
		selectIDPMedfusionSSO.click();
	}

	public void loginlessPrivacyPolicyClick() {
		commonMethods.highlightElement(checkShowPrivacyPolicyPage);
		javascriptClick(checkShowPrivacyPolicyPage);
		javascriptClick(buttonSaveLoginLess);
	}

	public WebElement getIDPUrlElement() {
		return urlList.get(1);
	}

	public String isLoginlessTrue() {
		commonMethods.highlightElement(enableLoginless);
		return enableLoginless.getAttribute("ng-reflect-model");
	}

	public String getAnonymousUrl() {
		log("Anonymous url is " + urlList.get(2).getText());
		commonMethods.highlightElement(urlList.get(2));
		return urlList.get(2).getText();
	}

	public String isEnableAnonymousSelected() {
		commonMethods.highlightElement(enableAnonymous);
		return enableAnonymous.getAttribute("ng-reflect-model");
	}

	public String isDisplayPrivacypolicyAnonymous() {
		commonMethods.highlightElement(displayPrivacyPolicyAnonymous);
		return displayPrivacyPolicyAnonymous.getAttribute("ng-reflect-model");
	}

	public String isAllowDuplicatePatientAnonymous() {
		commonMethods.highlightElement(displayPrivacyPolicyAnonymous);
		return displayPrivacyPolicyAnonymous.getAttribute("ng-reflect-model");
	}

	public String isEnableOtpAnonymous() {
		commonMethods.highlightElement(enableOTPAnonymous);
		return enableOTPAnonymous.getAttribute("ng-reflect-model");
	}

	public void saveAnonymouSetting() {
		commonMethods.highlightElement(buttonSaveAnonymous);
		buttonSaveAnonymous.click();
		log("buttonSaveAnonymous clicked Sucessfully ");
	}
}
