package com.medfusion.product.object.maps.jalapeno.page.CreateAccount;

import java.util.ArrayList;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

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
	 * @StepsToReproduce:
	 */

	@FindBy(how = How.ID, using = "firstName")
	private WebElement inputPatientFirstName;

	@FindBy(how = How.ID, using = "lastName")
	private WebElement inputPatientLastName;

	@FindBy(how = How.ID, using = "email")
	private WebElement inputEmailAddresss;

	@FindBy(how = How.ID, using = "birthDate_month")
	private WebElement inputDateOfBirthMonth;

	@FindBy(how = How.ID, using = "birthDate_day")
	private WebElement inputDateOfBirthDay;

	@FindBy(how = How.ID, using = "birthDate_year")
	private WebElement inputDateOfBirthYear;

	@FindBy(how = How.ID, using = "gender_male")
	private WebElement maleGender;

	@FindBy(how = How.ID, using = "gender_female")
	private WebElement femaleGender;

	@FindBy(how = How.ID, using = "postalCode")
	private WebElement inputZipCode;

	@FindBy(how = How.ID, using = "cancelStep")
	private WebElement buttonCancel;

	@FindBy(how = How.ID, using = "nextStep")
	private WebElement buttonChooseUserId;

	public JalapenoCreateAccountPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
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
	
	public boolean assessCreateAccountPageElements() {

		boolean allElementsDisplayed = false;

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


		for (WebElement w : webElementsList) {

			try {
				IHGUtil.waitForElement(driver, 20, w);
				log("Checking WebElement" + w.toString());
				if (w.isDisplayed()) {
					log("WebElement " + w.toString() + "is displayed");
					allElementsDisplayed = true;
				} else {
					log("WebElement " + w.toString() + "is NOT displayed");
					return false;
				}
			}

			catch (Throwable e) {
				log(e.getStackTrace().toString());
			}

		}
		return allElementsDisplayed;
	}
	
	public boolean isTextVisible(String text) {
		return driver.findElement(By.xpath("// * [contains(text(),'" + text + "')]")).isDisplayed();
	}

}
