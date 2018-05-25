package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.util.DateMatcher;

public class ExistingPatient extends PSS2MainPage {

	public ExistingPatient(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.ID, using = "existingfirstname")
	public WebElement inputfirstname;

	@FindBy(how = How.ID, using = "existinglastname")
	public WebElement inputlastName;

	@FindBy(how = How.XPATH, using = "/html/body/div[1]/div/div/div/div/div[5]/div/div[2]/div[3]/div/div/div/span/span")
	public WebElement dateOfBirth;

	@FindBy(how = How.CSS, using = "input[type='email']")
	public WebElement inputEmail;

	@FindBy(how = How.CSS, using = "select[name='gender']")
	public WebElement selectGender;

	@FindBy(how = How.ID, using = "existingZip")
	public WebElement zipCode;

	@FindBy(how = How.CLASS_NAME, using = "custombuttonexistnext")
	public WebElement buttonSubmit;

	@FindBy(how = How.CLASS_NAME, using = "custombuttonexistnext")
	public WebElement buttonCancel;

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public HomePage login(String userame, String lastname, String dob, String email, String gender, String zipCode) {
		inputfirstname.sendKeys(userame);
		inputlastName.sendKeys(lastname);
		dateOfBirth.click();
		DateMatcher dateMatcher = new DateMatcher();
		dateMatcher.selectDate(dob, driver);
		inputEmail.sendKeys(email);
		selectGender.click();
		Select selectGenderType = new Select(selectGender);
		selectGenderType.selectByValue(gender);
		buttonSubmit.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

}