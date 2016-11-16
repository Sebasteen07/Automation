package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmergencyContactInformationPage extends ConfiguratorFormPage {

	@FindBy(xpath = "//input[@id='hide_emergencycontact_check']")
	private WebElement chckHideEmergencyContact;

	@FindBy(name = "contactfirstname")
	private WebElement chckFirstName;

	@FindBy(name = "contactlastname")
	private WebElement chckLastName;

	@FindBy(name = "relation")
	private WebElement chckRelationToYou;

	@FindBy(name = "contactprimaryphone")
	private WebElement chckPrimaryPhone;

	@FindBy(name = "contactemail")
	private WebElement chckContactEmail;

	@FindBy(id = "save_config_form")
	private WebElement btnSave;

	public EmergencyContactInformationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Clicks on First name, Last name and phone number checkboxes to appear on the page
	 */

	public void selectBasicInfo() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(chckHideEmergencyContact));

		// click on the checkbox for showing and hiding the page for patients
		if (chckHideEmergencyContact.isSelected())
			chckHideEmergencyContact.click();

		// select name and phone items to appear on the page
		// it is done this way because of a bug that automatically selects all the items to appear
		if (chckFirstName.isSelected() == false)
			chckFirstName.click();
		if (chckLastName.isSelected() == false)
			chckLastName.click();
		if (chckPrimaryPhone.isSelected() == false)
			chckPrimaryPhone.click();
	}

}
