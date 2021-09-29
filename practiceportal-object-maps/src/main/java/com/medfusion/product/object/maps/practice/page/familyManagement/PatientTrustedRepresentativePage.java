//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.familyManagement;


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
import com.medfusion.pojos.Patient;

public class PatientTrustedRepresentativePage extends BasePageObject {

	public PatientTrustedRepresentativePage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
	}


	@FindBy(how = How.ID, using = "guardianFirstName")
	private WebElement lblFirstName;

	@FindBy(how = How.ID, using = "guardianLastName")
	private WebElement lblLastName;

	@FindBy(how = How.ID, using = "newGuardianEmail")
	private WebElement lblEmail;

	@FindBy(how = How.ID, using = "submit")
	private WebElement btnInvite;

	@FindBy(how = How.XPATH, using = "(//input[@name='accesslevel'])[3]")
	private WebElement rdoCustomAccess;

	@FindBy(how = How.XPATH, using = "//input[@id='custom_access']")
	private WebElement rdoManageAccess;

	@FindBy(how = How.XPATH, using = "(//input[@name='forms'])[2]")
	private WebElement rdoFormsViewOnly;

	@FindBy(how = How.XPATH, using = "(//input[@name='forms'])[3]")
	private WebElement rdoFormsNoAccess;


	public void inviteTrustedRepresentative(Patient patient,String textModule,String manageAccessPref) throws InterruptedException {
		IHGUtil.PrintMethodName();
		wait.until(ExpectedConditions.visibilityOf(btnInvite));
		fillLightboxInputs(patient);
		rdoCustomAccess.click();
		updateWithModuleNameAndAccess(textModule, manageAccessPref);
	}

	private void fillLightboxInputs(Patient patient) {
		IHGUtil.PrintMethodName();
		lblFirstName.sendKeys(patient.getFirstName());
		lblLastName.sendKeys(patient.getLastName());
		lblEmail.sendKeys(patient.getEmail());
	}
	
	public void selectCustomAccess() throws InterruptedException {
	       IHGUtil.waitForElement(driver, 10, rdoCustomAccess);
	       rdoCustomAccess.click();
	 }


	public void updateWithModuleNameAndAccess(String textModule, String manageAccessPref) {
		int i=0;
		if (manageAccessPref == "fullAccess") {
			i=1;
		}else if (manageAccessPref == "viewOnly") {
			i=2;
		}else if (manageAccessPref == "noAccess") {
			i=3;
		}
		String moduleName = "//td[p[contains(text()," + "'" + textModule + "')]]";
		String preferenceValue = "/following-sibling::td["+i+"]/input";
		String selectedPreference = moduleName + preferenceValue;
		WebElement preference = driver.findElement(By.xpath(selectedPreference));
		log("Printig the full xpath sending :" + preference);
		if ((new IHGUtil(driver).exists(rdoManageAccess))||(new IHGUtil(driver).exists(rdoCustomAccess))) {

			log("Manage Access Per Category is present");
			if (manageAccessPref == "fullAccess") {
				new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(preference));
				preference.click();
			} else if (manageAccessPref == "viewOnly") {
				new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(preference));
				preference.click();
			} else if (manageAccessPref == "noAccess") {
				new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(preference));
				preference.click();
			}

		}
		btnInvite.click();
	}

}
