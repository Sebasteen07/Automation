package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.medfusion.common.utils.IHGUtil;

public class BasicInformationAboutYouPage extends ConfiguratorFormPage {

	public BasicInformationAboutYouPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public static final String EGQ_SEX_QUESTION_LABEL = "What sex were you assigned at birth on your original birth certificate?";

	@FindBy(id = "streetaddr1")
	private WebElement chckAddress;

	@FindBy(id = "city")
	private WebElement chckCity;

	@FindBy(id = "state")
	private WebElement chckState;

	@FindBy(id = "postalcode")
	private WebElement chckZIP;

	@FindBy(id = "primaryphone")
	private WebElement chckPhoneNumber;

	@FindBy(xpath = "//input[@id='gender']/following-sibling::label")
	private WebElement sexQuestionLabel;

	@FindBy(id = "gender")
	private WebElement chckGender;

	@FindBy(id = "sexualorientation")
	private WebElement sexualOrientation;

	@FindBy(id = "genderidentity")
	private WebElement genderIdentity;



	/**
	 * Select basic info about the patient to appear Address items, phone number, gender
	 */

	public void selectBasicInfo() {
		// wait for the element to load
		IHGUtil.waitForElement(driver, 30, chckAddress);

		chckAddress.click();
		chckCity.click();
		chckState.click();
		chckZIP.click();
		chckPhoneNumber.click();
		chckGender.click();
	}

	/**
	 * Selects more attributes for patient information
	 */
	public void selectAdditionalInfo() {
		selectBasicInfo();
	}

	public String getSexQuetionLabel() {
		return sexQuestionLabel.getText();
	}

	public boolean areEGQQuestionsDisplayed() {
		if (EGQ_SEX_QUESTION_LABEL.equals(getSexQuetionLabel()) && genderIdentity.isDisplayed() && sexualOrientation.isDisplayed())
			return true;
		if ("Sex".equals(getSexQuetionLabel()) && !IHGUtil.exists(driver, 1, genderIdentity) && !IHGUtil.exists(driver, 1, sexualOrientation))
			return false;
		throw new IllegalStateException("sex question illegal state");
	}
}
