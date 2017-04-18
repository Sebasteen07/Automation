package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;


public class JalapenoMyAccountProfilePage extends JalapenoMyAccountPage {

	@FindBy(how = How.XPATH, using = "//input[@id='address1']")
	private WebElement address1Textbox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='address2']")
	private WebElement address2Textbox;
	
	@FindBy(how = How.XPATH, using = "//input[@id='city']")
	private WebElement cityTextbox;

	@FindBy(how = How.XPATH, using = "//input[@id='postalCode']")
	private WebElement zipCodeTextbox;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_male']")
	private WebElement maleRadioButton;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_female']")
	private WebElement femaleRadioButton;

	@FindBy(how = How.XPATH, using = "//input[@id='gender_unknown']")
	private WebElement declineToAnswerRadioButton;

	@FindBy(how = How.ID, using = "birthDate_year")
	private WebElement DOByear;

	@FindBy(how = How.ID, using = "birthDate_month")
	private WebElement DOBmonth;

	@FindBy(how = How.ID, using = "birthDate_day")
	private WebElement DOBday;

	@FindBy(how = How.ID, using = "state")
	private WebElement stateSelect;

	@FindBy(how = How.XPATH, using = "//select[@id='genderIdentitySelect']")
	private WebElement genderQuestion;

	@FindBy(how = How.XPATH, using = "//button[@id='saveAccountChanges']")
	private WebElement saveMyChanges;

	@FindBy(how = How.XPATH, using = "//select[@id='race']")
	private WebElement race;

	@FindBy(how = How.XPATH, using = "//select[@id='ethnicity']")
	private WebElement ethnicity;
	
	@FindBy(how = How.XPATH, using = "//select[@id='sexualOrientationSelect']")
	private WebElement sexualOrientation;
	
	@FindBy(how = How.ID, using = "firstName")
	private WebElement firstName;
	
	@FindBy(how = How.ID, using = "lastName")
	private WebElement lastName;
	
	@FindBy(how = How.XPATH, using = "//input[@id='phone4']")
	private WebElement phone4;
	
	@FindBy(how = How.XPATH, using = "//input[@id='phone5']")
	private WebElement phone5;
	
	@FindBy(how = How.XPATH, using = "//input[@id='phone6']")
	private WebElement phone6;
	
	public JalapenoMyAccountProfilePage(WebDriver driver) throws InterruptedException {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	public int getDOBday() {
		return Integer.parseInt(DOBday.getAttribute("value"));
	}

	public int getDOByear() {
		return Integer.parseInt(DOByear.getAttribute("value"));
	}

	public int getDOBmonth() {
		return Integer.parseInt(DOBmonth.getAttribute("value"));
	}

	public String getDOB() {
		return getDOBmonth() + "/" + getDOBday() + "/" + getDOByear();
	}

	public boolean checkExtendedGenderQuestion() throws NoSuchElementException
	{
		// String genderValue = "";
		new Select(ethnicity).selectByIndex(1);
		new Select(race).selectByIndex(1);

		if (!declineToAnswerRadioButton.isDisplayed())
 {
			log("Cheking Decline to answer radio button");
			return false;
		}

		List<String> list1 = Arrays.asList("Male", "Female", "Transgender female/Trans woman/Male-to-female (MTF)",
				"Transgender male/Trans man/Female-to-male (FTM)", "Genderqueer, neither exclusively male nor female",
				"Additional gender category/(or other)");

		for (String genderValue : list1) {

			try {
			log("Checking gender value: " + genderValue);
			new Select(genderQuestion).selectByVisibleText(genderValue);
			log("Value " + genderValue + " is verified.");
			saveMyChanges.click();
			IHGUtil.waitForElement(driver, 30, genderQuestion);
			} catch (NoSuchElementException e) {
				log("Gender value: " + genderValue + "is missing.");
				return false;
			}
			try {
				genderQuestion.equals(genderValue);
				log("Gender is saved properly.");
			} catch (NoSuchElementException e) {
				log("Gender has not been saved.");
				return false;
			}
		}
		return true;
	}

	public boolean checkForAddress(WebDriver driver, String addressLine1, String city, String zipCode) {

		log("Checking address in My Account");

		String savedAddressLine1 = address1Textbox.getAttribute("value");
		String savedCity = cityTextbox.getAttribute("value");
		String savedZipCode = zipCodeTextbox.getAttribute("value");

		if (savedAddressLine1.isEmpty() || savedCity.isEmpty() || savedZipCode.isEmpty()) {
			log("One of the address information if missing");
			return false;
		}

		if (!addressLine1.equals(savedAddressLine1)) {
			log("Line 1 is incorrect: " + savedAddressLine1);
			return false;
		}
		if (!city.equals(savedCity)) {
			log("City is incorrect: " + savedCity);
			return false;
		}
		if (!zipCode.equals(savedZipCode)) {
			log("ZipCode is incorrect: " + savedZipCode);
			return false;
		}

		log("Everything is saved, values are as follows");

		log("Address line 1 value: " + savedAddressLine1);
		log("City value: " + savedCity);
		log("ZipCode value: " + savedZipCode);

		return true;
	}

	public boolean checkZipCode(String zipCode) {

		log("Checking ZipCode textbox");

		String savedZipCode = zipCodeTextbox.getAttribute("value");

		if (!StringUtils.equals(zipCode, savedZipCode)) {
			log("ZipCode does not match, expected '" + zipCode + "' but there is '" + savedZipCode + "'");
			return false;
		}

		log("ZipCode value: " + savedZipCode);
		return true;
	}

	public boolean checkGender(Gender genderExpected) {
		log("Checking gender");
		Gender genderOnPage = maleRadioButton.isSelected() ? Gender.MALE : Gender.MALE;
		return genderExpected == genderOnPage;
	}

	public JalapenoHomePage returnToHomePage(WebDriver driver) {
		log("Return to dashboard");
		driver.findElement(By.id("home")).click();

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public JalapenoMyAccountSecurityPage goToSecurityTab(WebDriver driver) {
		log("Click on Security");
		securityTab.click();

		return PageFactory.initElements(driver, JalapenoMyAccountSecurityPage.class);
	}

	public JalapenoMyAccountPreferencesPage goToPreferencesTab(WebDriver driver) {
		log("Click on Preferences");
		preferencesTab.click();

		return PageFactory.initElements(driver, JalapenoMyAccountPreferencesPage.class);
	}

	@Override
	public boolean areBasicPageElementsPresent() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		webElementsList.add(profileTab);
		webElementsList.add(securityTab);
		webElementsList.add(preferencesTab);
		webElementsList.add(address1Textbox);
		webElementsList.add(cityTextbox);
		webElementsList.add(zipCodeTextbox);
		webElementsList.add(maleRadioButton);

		return super.assessPageElements(true) && new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}

	public boolean modifyAndValidatePageContent() {
		Map<WebElement, String> itemsToChange = new HashMap<WebElement, String>();
		itemsToChange.put(address1Textbox, "address");
		itemsToChange.put(cityTextbox, "city");
		itemsToChange.put(stateSelect, "Alaska");
		itemsToChange.put(zipCodeTextbox, "54321");

		return updateAndValidateWebElements(itemsToChange, saveAccountChanges);
	}

	/**
	 * 
	 * @return proper gender or null if not specified
	 */
	public Gender getGender() {
		if (maleRadioButton.isSelected())
			return Gender.MALE;
		if (femaleRadioButton.isSelected())
			return Gender.FEMALE;
		return null;
	}
	
	public int countDropDownValue(char key) {
		IHGUtil.PrintMethodName();
		Select select = null;
		int size = 0;
		switch (key) {
			case 'R':
				select = new Select(race);
				break;
			case 'E':
				select = new Select(ethnicity);
				break;
			case 'G':
				select = new Select(genderQuestion);
				break;
			case 'S':
				select = new Select(sexualOrientation);
				break;
			default:
				break;
		}
		
		List<WebElement> element = select.getOptions();
		size = element.size();

		return size;
	}
	
	public String updateDropDownValue(int i, char key) {
		IHGUtil.PrintMethodName();
		Select select = null;
		String changeValue = null;
		switch (key) {
			case 'R':
				select = new Select(race);
				break;
			case 'E':
				select = new Select(ethnicity);
				break;
			case 'G':
				select = new Select(genderQuestion);
				break;
			case 'S':
				select = new Select(sexualOrientation);
				break;
			default:

				break;
		}
		select.selectByIndex(i);
		WebElement option = select.getFirstSelectedOption();
		changeValue = option.getText();
		saveMyChanges.click();
		return changeValue;
	}
	
	
	public String updateGenderValue(int i, char key) {
		IHGUtil.PrintMethodName();
		String changeValue = null;
		switch (key) {
			case 'M':
				changeValue = "Male";
				new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(maleRadioButton));
				if(maleRadioButton.isSelected()!=true)
				maleRadioButton.click();
				
				break;
			case 'F':
				changeValue = "Female";
				new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(femaleRadioButton));
				femaleRadioButton.click();
				break;
			case 'D':
				new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(declineToAnswerRadioButton));
				declineToAnswerRadioButton.click();
				changeValue = "Decline to answer";
				break;
			default:

				break;
		}
		saveMyChanges.click();
		return changeValue;
	}
	
	public void updateDemographics(List<String> updateData) {
		firstName.clear();
		firstName.sendKeys(updateData.get(0));
		lastName.clear();
		lastName.sendKeys(updateData.get(1));
		String[] DOB = updateData.get(5).split("/");
		DOBday.sendKeys(Keys.BACK_SPACE);
		DOBday.sendKeys(DOB[1]);
		new Select(DOBmonth).selectByIndex(Integer.parseInt(DOB[0]));
		DOByear.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),DOB[2]);
		address1Textbox.clear();
		address1Textbox.sendKeys(updateData.get(2));
		address2Textbox.clear();
		address2Textbox.sendKeys(updateData.get(3));
		phone4.clear();
		phone4.sendKeys(updateData.get(4).substring(0, 3));
		phone5.clear();
		phone5.sendKeys(updateData.get(4).substring(3, 6));
		phone6.clear();
		phone6.sendKeys(updateData.get(4).substring(6, 10));
		new Select(race).selectByVisibleText(updateData.get(7));
		new Select(ethnicity).selectByVisibleText(updateData.get(8));
		zipCodeTextbox.clear();
		zipCodeTextbox.sendKeys(updateData.get(11));
		saveMyChanges.click();
	}
}
