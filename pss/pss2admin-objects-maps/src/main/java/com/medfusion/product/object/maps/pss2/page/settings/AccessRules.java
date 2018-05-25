package com.medfusion.product.object.maps.pss2.page.settings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class AccessRules extends SettingsTab {

	@FindBy(how = How.ID, using = "checkbox1")
	private WebElement checkNewPatient;

	@FindBy(how = How.ID, using = "checkbox2")
	private WebElement checkShowInsurancePage;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/div/main/div[2]/div/div/div/section/div/div/div[2]/div[2]/div[2]/div/fieldset/div[2]/div[1]/input")
	private WebElement checkShowPrivacyPolicyPage;

	@FindBy(how = How.ID, using = "checkbox4")
	private WebElement checkLoginlessExistingPatient;

	@FindBy(how = How.XPATH, using = "/html/body/app/layout/div/main/div[2]/div/div/div/section/div/div/div[2]/div[2]/div[2]/div/div[1]/a")
	private WebElement loginlessPatientURL;

	@FindBy(how = How.ID, using = "radio0")
	private WebElement selectIDPPatientSchedulingIdentity;

	@FindBy(how = How.ID, using = "ssonew0")
	private WebElement selectIDPNewPatient;

	@FindBy(how = How.ID, using = "ssoexist0")
	private WebElement selectIDPExistingPatient;

	@FindBy(how = How.ID, using = "radio1")
	private WebElement selectIDPMedfusionSSO;

	@FindBy(how = How.XPATH, using = "//*[@id=\"par\"]/div[2]/div/div[2]/div/button")
	private WebElement buttonSaveLoginLess;

	@FindBy(how = How.XPATH, using = "//*[@id=\"par\"]/div[3]/div/div[3]/div/button")
	private WebElement buttonSaveIDP;

	@FindBy(how = How.XPATH, using = "//*[@id=\"par\"]/div[3]/div/div[1]/div[2]/div/a")
	private WebElement idpUrl;

	@FindAll({@FindBy(css = ".btn.btn-primary")})
	public List<WebElement> buttonList;

	@FindAll({@FindBy(xpath = ".//*[@class=\"col-md-12\"]/a")})
	public List<WebElement> urlList;
	
	public AccessRules(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		// webElementsList.add(loginlessPatientURL);
		// webElementsList.add(selectIDPMedfusionSSO);
		// webElementsList.add(buttonSaveLoginLess);
		// webElementsList.add(buttonSaveIDP);
		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public String getLoginlessURL() {
		return urlList.get(0).getText();
	}

	public String isLLNewPatientSelected() {
		return checkNewPatient.getAttribute("ng-reflect-model");
	}

	public String isLLInsurancePageSelected() {
		return checkShowInsurancePage.getAttribute("ng-reflect-model");
	}

	public String isLLPrivacyPolicySelected() {
		return checkShowPrivacyPolicyPage.getAttribute("ng-reflect-model");
	}

	public String isLLExistingPatientSelected() {
		return checkLoginlessExistingPatient.getAttribute("ng-reflect-model");
	}

	public void selectLLNewPatient() {
		checkNewPatient.click();
	}

	public void selectLLShowInsurancePage() {
		checkShowInsurancePage.click();
	}

	public void selectLLShowPrivacyPolicyPage() {
		checkShowPrivacyPolicyPage.click();
	}

	public void selectLLExistingPatient() {
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
		javascriptClick(checkShowPrivacyPolicyPage);
		javascriptClick(buttonSaveLoginLess);
	}

	public WebElement getIDPUrlElement() {
		return urlList.get(1);
	}
}