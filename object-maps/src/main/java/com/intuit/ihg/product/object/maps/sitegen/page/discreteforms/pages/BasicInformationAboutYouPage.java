package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.ConfiguratorFormPage;
import com.medfusion.common.utils.IHGUtil;

public class BasicInformationAboutYouPage extends ConfiguratorFormPage {

	public BasicInformationAboutYouPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

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
	private WebElement chckGender; // sex


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

}
