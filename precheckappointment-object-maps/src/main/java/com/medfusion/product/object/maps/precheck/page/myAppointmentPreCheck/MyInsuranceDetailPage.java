package com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.product.object.maps.precheck.page.myInsurance.PrimaryInsurancePage;
import com.medfusion.product.object.maps.precheck.page.myInsuranceImage.InsuranceImagePage;

public class MyInsuranceDetailPage extends BasePageObject  {

	@FindBy(how = How.XPATH, using = "//*[text() = 'Enter my info']")
	private WebElement insurancePageButton;
	
	@FindBy(how = How.XPATH, using = "//*[text() = 'Take a photo']")
	private WebElement takeAPhotoButton;
	
	@FindBy(how = How.ID, using = "noInsuranceButton")
	private WebElement noInsuranceSubmitButton;
	
	public MyInsuranceDetailPage(WebDriver driver) {
		super(driver);
	}
	
	public PrimaryInsurancePage gotoInsuranceInfoPage() {
		javascriptClick(insurancePageButton);
		return PageFactory.initElements(driver, PrimaryInsurancePage.class);
	}
	
	public InsuranceImagePage gotoInsuranceImagePage() {
		javascriptClick(takeAPhotoButton);
		return PageFactory.initElements(driver, InsuranceImagePage.class);
	}
	
	public MyAppointmentPage gotoAppointmentPage() {
		javascriptClick(noInsuranceSubmitButton);
		return PageFactory.initElements(driver, MyAppointmentPage.class);
	}

}
