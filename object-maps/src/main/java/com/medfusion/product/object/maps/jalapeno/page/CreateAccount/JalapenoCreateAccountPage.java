package com.medfusion.product.object.maps.jalapeno.page.CreateAccount;

import java.util.ArrayList;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.IHGUtil.Gender;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class JalapenoCreateAccountPage extends BasePageObject {

	/**
	 * @Author:Jakub Calabek
	 * @Date:24.7.2013
	 */

	@FindBy(how = How.ID, using = "firstName")
	private WebElement inputPatientFirstName;

	@FindBy(how = How.ID, using = "lastName")
	private WebElement inputPatientLastName;

	@FindBy(how = How.ID, using = "email")
	private WebElement inputEmailAddresss;

	@FindBy(how = How.XPATH, using = "(//select[@id='birthDate_month'])[4]")
	private WebElement inputDateOfBirthMonth;

	@FindBy(how = How.XPATH, using = "(//input[@id='birthDate_day'])[4]")
	private WebElement inputDateOfBirthDay;

	@FindBy(how = How.XPATH, using = "(//input[@id='birthDate_year'])[4]")
	private WebElement inputDateOfBirthYear;

	@FindBy(how = How.ID, using = "gender_male")
	private WebElement maleGender;

	@FindBy(how = How.ID, using = "gender_female")
	private WebElement femaleGender;

	@FindBy(how = How.XPATH, using = "(//input[@id='postalCode'])[4]")
	private WebElement inputZipCode;

	@FindBy(how = How.XPATH, using = "(//a[@id='cancelStep'])[4]")
	private WebElement buttonCancel;

	@FindBy(how = How.XPATH, using = "(//button[@id='nextStep'])[4]")
	private WebElement buttonChooseUserId;

	public JalapenoCreateAccountPage(WebDriver driver) {
		super(driver);
	}

	public JalapenoPatientActivationPage fillInDataPage(String firstName, String lastName,
			String email, String month, String day, String year, Gender gender, String zipCode) {
		IHGUtil.PrintMethodName();

		setName(firstName, lastName);
		setEmail(email);
		setDateOfBirth(month, day, year);
		setGender(gender);
		setZipCode(zipCode);

		return goToNextPage();
	}
	
	public JalapenoCreateAccountPage2 fillInDataPage1(String firstName, String lastName, String email, String month, String day, String year, boolean gender,
			String zipCode) {
		IHGUtil.PrintMethodName();
		
		log("Setting Firstname as " + firstName);
		inputPatientFirstName.sendKeys(firstName);
		log("Setting LastName as " + lastName);
		inputPatientLastName.sendKeys(lastName);
		log("Setting email address as " + email);
		inputEmailAddresss.sendKeys(email);

		log("Setting up month of birth as " + month);
		Select selectMonth = new Select(inputDateOfBirthMonth);
		selectMonth.selectByValue(month);

		log("Setting up day of birth as " + day);
		inputDateOfBirthDay.sendKeys(day);
		log("Setting up day of year as " + year);
		inputDateOfBirthYear.sendKeys(year);

		log("Setting up gender as " + gender);
		if (gender) {
			maleGender.click();
		} else {
			femaleGender.click();
		}
		
		log("Setting up ZipCode as " + zipCode);
		inputZipCode.sendKeys(zipCode);
		
		buttonChooseUserId.click();

		return PageFactory.initElements(driver, JalapenoCreateAccountPage2.class);
	}

	public JalapenoPatientActivationPage fillInDataPage(String firstName, String lastName,
			String email, PropertyFileLoader testData) {

		return fillInDataPage(firstName, lastName, email, testData.getDOBMonth(),
				testData.getDOBDay(), testData.getDOBYear(), Gender.MALE, testData.getZipCode());
	}

	public JalapenoPatientActivationPage goToNextPage() {
		buttonChooseUserId.click();

		return new JalapenoPatientActivationPage(driver);
	}

	public JalapenoCreateAccountPage setZipCode(String zipCode) {
		log("Setting up ZipCode as " + zipCode);
		inputZipCode.sendKeys(zipCode);
		return this;
	}

	public JalapenoCreateAccountPage setGender(Gender gender) {
		log("Setting up gender as " + gender);
		if (gender == Gender.MALE) {
			maleGender.click();
		} else {
			femaleGender.click();
		}
		return this;
	}

	public JalapenoCreateAccountPage setDateOfBirth(String month, String day, String year) {
		log("Setting up month of birth as " + month);
		Select selectMonth = new Select(inputDateOfBirthMonth);
		selectMonth.selectByValue(month);
		log("Setting up day of birth as " + day);
		inputDateOfBirthDay.sendKeys(day);
		log("Setting up day of year as " + year);
		inputDateOfBirthYear.sendKeys(year);
		return this;
	}

	public JalapenoCreateAccountPage setDateOfBirth(PropertyFileLoader testData) {
		return setDateOfBirth(testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear());
	}

	public JalapenoCreateAccountPage setEmail(String email) {
		log("Setting email address as " + email);
		inputEmailAddresss.sendKeys(email);
		return this;
	}

	public JalapenoCreateAccountPage setName(String firstName, String lastName) {
		log("Setting Firstname as " + firstName);
		inputPatientFirstName.sendKeys(firstName);
		log("Setting LastName as " + lastName);
		inputPatientLastName.sendKeys(lastName);
		return this;
	}

//	public JalapenoPatientActivationPage fillInDataPage(String firstName, String lastName,
//			String email, boolean gender, PropertyFileLoader testData) {
//
//	}

	public boolean assessCreateAccountPageElements() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(inputPatientFirstName);
		webElementsList.add(inputPatientLastName);
		webElementsList.add(inputEmailAddresss);
		webElementsList.add(inputDateOfBirthMonth);
		webElementsList.add(inputDateOfBirthDay);
		webElementsList.add(inputDateOfBirthYear);
		webElementsList.add(maleGender);
		webElementsList.add(femaleGender);
		webElementsList.add(inputZipCode);
		webElementsList.add(buttonCancel);
		webElementsList.add(buttonChooseUserId);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
	
	public boolean isTextVisible(String text) {
		return driver.findElement(By.xpath("// * [contains(text(),'" + text + "')]")).isDisplayed();
	}

}
