package com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.product.object.maps.precheck.page.myInsurance.PrimaryInsurancePage;

public class MyInsuranceDetailPage extends BasePageObject  {

	@FindBy(how = How.XPATH, using = "//*[text() = 'Enter my info']")
	private WebElement insurancePageButton;
	
	public MyInsuranceDetailPage(WebDriver driver) {
		super(driver);
	}
	
	public PrimaryInsurancePage gotoInsuranceInfoPage() {
		insurancePageButton.click();
		return PageFactory.initElements(driver, PrimaryInsurancePage.class);
	}
	

}
