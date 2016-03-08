package com.medfusion.product.object.maps.precheck.page.patient;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class InsurancePage extends BasePageObject {

	@FindBy(how = How.ID, using = "noInsuranceButton")
	private WebElement noInsuranceButton;
	
	public InsurancePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public PatientHomePage clickNoInsurance() {
		noInsuranceButton.click();
		return PageFactory.initElements(driver, PatientHomePage.class);
	}
	
	

}
