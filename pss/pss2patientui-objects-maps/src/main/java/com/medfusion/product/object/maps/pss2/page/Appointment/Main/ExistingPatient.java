package com.medfusion.product.object.maps.pss2.page.Appointment.Main;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.util.DateMatcher;

public class ExistingPatient extends PSS2MainPage {

	@FindBy(how = How.ID, using = "existingfirstname")
	private WebElement inputfirstname;

	@FindBy(how = How.ID, using = "existinglastname")
	private WebElement inputlastName;

	@FindBy(how = How.XPATH, using = "/html/body/div[1]/div/div/div/div/div[5]/div/div[2]/div[3]/div/div/div/span/span")
	private WebElement dateOfBirth;

	@FindBy(how = How.CSS, using = "input[type='email']")
	private WebElement inputEmail;

	@FindBy(how = How.CSS, using = "select[name='gender']")
	private WebElement selectGender;

	@FindBy(how = How.ID, using = "existingZip")
	private WebElement zipCode;

	@FindBy(how = How.CLASS_NAME, using = "custombuttonexistnext")
	private WebElement buttonSubmit;

	@FindBy(how = How.CLASS_NAME, using = "custombuttonexistnext")
	private WebElement buttonCancel;

	@FindAll({@FindBy(css = ".dismissbuttons")})
	private List<WebElement> dismissButton;

	public ExistingPatient(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

	public HomePage login(String userame, String lastname, String dob, String email, String gender, String zipCode) {
		if (inputfirstname.isDisplayed()) {
			inputfirstname.sendKeys(userame);
		}
		if (inputlastName.isDisplayed()) {
			inputlastName.sendKeys(lastname);
		}
		if (dateOfBirth.isDisplayed()) {
			dateOfBirth.click();
			DateMatcher dateMatcher = new DateMatcher();
			dateMatcher.selectDate(dob, driver);
		}
		if (inputEmail.isDisplayed()) {
			inputEmail.sendKeys(email);
		}
		if (selectGender.isDisplayed()) {
			selectGender.click();
			Select selectGenderType = new Select(selectGender);
			selectGenderType.selectByValue(gender);
		}

		buttonSubmit.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

	public PrivacyPolicy loginPatient(String userame, String lastname, String dob, String email, String gender, String zipCode) {
		if (inputfirstname.isDisplayed()) {
			inputfirstname.sendKeys(userame);
		}
		if (inputlastName.isDisplayed()) {
			inputlastName.sendKeys(lastname);
		}
		if (dateOfBirth.isDisplayed()) {
			dateOfBirth.click();
			DateMatcher dateMatcher = new DateMatcher();
			dateMatcher.selectDate(dob, driver);
		}
		if (inputEmail.isDisplayed()) {
			inputEmail.sendKeys(email);
		}
		if (selectGender.isDisplayed()) {
			selectGender.click();
			Select selectGenderType = new Select(selectGender);
			selectGenderType.selectByValue(gender);
		}
		buttonSubmit.click();
		return PageFactory.initElements(driver, PrivacyPolicy.class);
	}

	public void dismissPopUp() {
		for (int i = 0; i < dismissButton.size(); i++) {
			log("Pop up Displayed " + dismissButton.get(i).isDisplayed());
			if (dismissButton.get(i).isDisplayed()) {
				dismissButton.get(i).click();
			}
		}
	}

}