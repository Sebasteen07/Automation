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

	@FindAll({@FindBy(id = "existingfirstname")})
	private List<WebElement> inputfirstname;

	@FindAll({@FindBy(id = "existinglastname")})
	private List<WebElement> inputlastName;

	@FindAll({@FindBy(xpath = "/html/body/div[1]/div/div/div/div/div[5]/div/div[2]/div[3]/div/div/div/span/span")})
	private List<WebElement> dateOfBirth;

	@FindAll({@FindBy(css = "input[type='email']")})
	private List<WebElement> inputEmail;

	@FindAll({@FindBy(css = "select[name='gender']")})
	private List<WebElement> selectGender;

	@FindAll({@FindBy(css = "existingZip")})
	private List<WebElement> zipCode;

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
		if (inputfirstname.size() > 0) {
			inputfirstname.get(0).sendKeys(userame);
		}
		if (inputlastName.size() > 0) {
			inputlastName.get(0).sendKeys(lastname);
		}
		if (dateOfBirth.size() > 0) {
			dateOfBirth.get(0).click();
			DateMatcher dateMatcher = new DateMatcher();
			dateMatcher.selectDate(dob, driver);
		}
		if (inputEmail.size() > 0) {
			inputEmail.get(0).sendKeys(email);
		}
		if (selectGender.size() > 0) {
			selectGender.get(0).click();
			Select selectGenderType = new Select(selectGender.get(0));
			selectGenderType.selectByValue(gender);
		}

		buttonSubmit.click();
		return PageFactory.initElements(driver, HomePage.class);
	}

	public PrivacyPolicy loginPatient(String userame, String lastname, String dob, String email, String gender, String zipCode) {
		if (inputfirstname.size() > 0) {
			inputfirstname.get(0).sendKeys(userame);
		}
		if (inputlastName.size() > 0) {
			inputlastName.get(0).sendKeys(lastname);
		}
		if (dateOfBirth.size() > 0) {
			dateOfBirth.get(0).click();
			DateMatcher dateMatcher = new DateMatcher();
			dateMatcher.selectDate(dob, driver);
		}
		if (inputEmail.size() > 0) {
			inputEmail.get(0).sendKeys(email);
		}
		if (selectGender.size() > 0) {
			selectGender.get(0).click();
			Select selectGenderType = new Select(selectGender.get(0));
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