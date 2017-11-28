package com.medfusion.product.object.maps.precheck.page.verifyIdentity;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.product.object.maps.precheck.page.myAppointmentsList.AppointmentsListPage;

public class VerifyIdentityPage extends BasePageObject {

	@FindBy(how = How.ID, using = "zipCode")
	private WebElement zipCodeInput;

	@FindBy(how = How.ID, using = "month")
	private WebElement dateOfBirthMonthInput;
	
	@FindBy(how = How.ID, using = "day")
	private WebElement dateOfBirthDayInput;
	
	@FindBy(how = How.ID, using = "year")
	private WebElement dateOfBirthYearInput;
	
	@FindBy(how = How.ID, using = "loginButton")
	private WebElement loginButton;
	
	public VerifyIdentityPage(WebDriver driver,String appointmentLink) {
		super(driver);
		driver.get(appointmentLink);
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void setZipCode(String zipCodeValue) {
		this.zipCodeInput.sendKeys(zipCodeValue);
	}

	public String getDateOfBirthMonthInput() {
		return dateOfBirthMonthInput.getText();
	}

	public void setDateOfBirthMonthInput(String monthValue) {
		dateOfBirthMonthInput.sendKeys(monthValue) ;
	}
	
	public void setDateOfBirthDayInput(String dayValue) {
		dateOfBirthDayInput.sendKeys(dayValue) ;
	}
	
	public void setDateOfBirthYearInput(String yearValue) {
		dateOfBirthYearInput.sendKeys(yearValue) ;
	}
	
	public AppointmentsListPage login() {
		loginButton.click();
		return PageFactory.initElements(driver, AppointmentsListPage.class);
	}
}