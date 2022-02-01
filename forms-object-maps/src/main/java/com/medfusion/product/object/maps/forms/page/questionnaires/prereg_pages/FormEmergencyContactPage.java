//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class FormEmergencyContactPage extends PortalFormPage {

	public FormEmergencyContactPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "contactfirstname")
	private WebElement firstName;

	@FindBy(id = "contactlastname")
	private WebElement lastName;

	@FindBy(id = "relation")
	private WebElement relation;

	@FindBy(id = "contactprimaryphone_phone")
	private WebElement primaryPhone;

	@FindBy(id = "contactprimaryphone_type")
	private WebElement primaryPhoneType;

	@FindBy(id = "contactemail")
	private WebElement email;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	public void setFirstName(String input) throws Exception {
		PortalUtil2.PrintMethodName();
		// PortalUtil2.setquestionnarieFrame(driver);
		firstName.clear();
		firstName.sendKeys(input);
	}

	public void setFirstName_20(String input, Boolean isFormTypePreCheck) throws Exception {
		PortalUtil2.PrintMethodName();
		if (!isFormTypePreCheck) {
			WebElement W1 = driver.findElement(By.xpath("//iframe[@title='Forms']"));
			driver.switchTo().frame(W1);
		}
		IHGUtil.waitForElement(driver, 50, firstName);
		firstName.clear();
		firstName.sendKeys(input);
	}

	public void setLastName(String input) throws Exception {
		PortalUtil2.PrintMethodName();
		// PortalUtil2.setquestionnarieFrame(driver);
		lastName.clear();
		lastName.sendKeys(input);
	}

	public void setLastName_20(String input) throws Exception {
		PortalUtil2.PrintMethodName();
		lastName.clear();
		lastName.sendKeys(input);
	}

	public void setRelation(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		// PortalUtil2.setquestionnarieFrame(driver);
		Select selector = new Select(relation);
		selector.selectByVisibleText(type);
	}

	public void setRelation_20(String relation1) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(relation);
		selector.selectByVisibleText(relation1);
	}

	public void setPrimaryPhone(String input, String phoneType) throws Exception {
		PortalUtil2.PrintMethodName();
		// PortalUtil2.setquestionnarieFrame(driver);
		primaryPhone.clear();
		primaryPhone.sendKeys(input);
		Select selector = new Select(primaryPhoneType);
		selector.selectByVisibleText(phoneType);
	}

	public void setPrimaryPhone_20(String input, String phoneType) throws Exception {
		PortalUtil2.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, primaryPhone);
		primaryPhone.clear();
		primaryPhone.sendKeys(input);
		Select selector = new Select(primaryPhoneType);
		selector.selectByVisibleText(phoneType);

	}

	public void setEmail(String input) throws Exception {
		PortalUtil2.PrintMethodName();
		// PortalUtil2.setquestionnarieFrame(driver);
		email.clear();
		email.sendKeys(input);
	}

	public FormCurrentSymptomsPage fillEmergencyContactFormFields() throws Exception {
		setFirstName(JalapenoConstants.FIRST_NAME);

		setLastName(JalapenoConstants.LAST_NAME);

		setRelation(JalapenoConstants.RELATION);

		setPrimaryPhone("1234567890", JalapenoConstants.MOBILE);

		clickSaveContinue();

		return PageFactory.initElements(driver, FormCurrentSymptomsPage.class);
	}

	public FormCurrentSymptomsPage fillEmergencyContactFormFields_20(String relFirstNAme, String relLastName,
			String relation1, String number, String phonetype1, Boolean isFormTypePreCheck) throws Exception {

		setFirstName_20(relFirstNAme, isFormTypePreCheck);

		setLastName_20(relLastName);

		setRelation_20(relation1);

		setPrimaryPhone_20(number, phonetype1);

		clickSaveContinue();

		return PageFactory.initElements(driver, FormCurrentSymptomsPage.class);
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Emergency Contact Information")))
				.isDisplayed();
	}

}
