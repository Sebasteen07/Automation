// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
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

public class FormBasicInfoPage extends PortalFormPage {

	public FormBasicInfoPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "streetaddr1")
	private WebElement address;

	@FindBy(id = "city")
	private WebElement city;

	@FindBy(id = "state")
	private WebElement state;

	@FindBy(id = "postalcode")
	private WebElement zip;

	@FindBy(id = "primaryphone_phone")
	private WebElement primaryPhone;

	@FindBy(id = "primaryphone_type")
	private WebElement primaryPhoneType;

	@FindBy(xpath = "//*[@id='gender']")
	private WebElement gender;

	@FindBy(xpath = "//label[@for='gender']")
	private WebElement genderQuestionLabel;

	@FindBy(id = "maritalstatus")
	private WebElement maritalStatus;

	@FindBy(id = "preferredcontact")
	private WebElement preferredCommunication;

	@FindBy(id = "language")
	private WebElement preferredLanguage;

	@FindBy(id = "race")
	private WebElement race;

	@FindBy(id = "ethnicity")
	private WebElement ethnicity;

	@FindBy(id = "formeditor")
	private WebElement whoIsFillingOutForm;

	@FindBy(id = "genderidentity")
	private WebElement genderIdentity;

	@FindBy(id = "genderidentity_with_text")
	private WebElement genderIdentityDetail;

	@FindBy(id = "sexualorientation")
	private WebElement sexualOrientation;

	@FindBy(id = "sexualorientation_with_text")
	private WebElement sexualOrientationDetail;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	@FindBy(className = "save")
	private WebElement save;

	public void setStreetAddress() throws Exception {
		PortalUtil2.PrintMethodName();

		address.clear();
		address.sendKeys(IHGUtil.createRandomStreet());
	}

	public void setStreetAddress_20() throws Exception {
		PortalUtil2.PrintMethodName();
		IHGUtil.waitForElement(driver, 60, address);
		address.clear();
		address.sendKeys(IHGUtil.createRandomStreet());
	}

	public void setCity() throws Exception {
		PortalUtil2.PrintMethodName();

		city.clear();
		city.sendKeys(IHGUtil.createRandomCity());
	}

	public void setCity_20() throws Exception {
		PortalUtil2.PrintMethodName();
		city.clear();
		city.sendKeys(IHGUtil.createRandomCity());
	}

	public void setState(String type) throws Exception {
		PortalUtil2.PrintMethodName();

		Select selector = new Select(state);
		selector.selectByVisibleText(type);
	}

	public void setState_20(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(state);
		selector.selectByVisibleText(type);
	}

	public void setZip() throws Exception {
		PortalUtil2.PrintMethodName();

		zip.clear();
		zip.sendKeys(IHGUtil.createRandomZip());
	}

	public void setZip_20() throws Exception {
		PortalUtil2.PrintMethodName();
		zip.clear();
		zip.sendKeys(IHGUtil.createRandomZip());
	}

	public void setPrimaryPhoneNumber() throws Exception {
		PortalUtil2.PrintMethodName();

		primaryPhone.clear();
		primaryPhone.sendKeys("919-555-" + IHGUtil.createRandomNumericString(4));
	}

	public void setPrimaryPhoneNumber_20() throws Exception {
		PortalUtil2.PrintMethodName();
		primaryPhone.clear();
		primaryPhone.sendKeys("919-555-" + IHGUtil.createRandomNumericString(4));
	}

	public void setPrimaryPhoneType(String type) throws Exception {
		PortalUtil2.PrintMethodName();

		Select selector = new Select(primaryPhoneType);
		selector.selectByVisibleText(type);
	}

	public void setPrimaryPhoneType_20(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(primaryPhoneType);
		selector.selectByVisibleText(type);
	}

	public void setSex(String type) {
		PortalUtil2.PrintMethodName();
		new Select(gender).selectByVisibleText(type);
	}

	public void setSex_20(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		focusSelectAndSelectByValue(gender, type);
	}

	public void setMaritalStatus(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(maritalStatus);
		selector.selectByVisibleText(type);
	}

	public void setPreferredCommunication(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(preferredCommunication);
		selector.selectByVisibleText(type);
	}

	public void setPreferredLanguage(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(preferredLanguage);
		selector.selectByVisibleText(type);
	}

	public void setRace(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(race);
		selector.selectByVisibleText(type);
	}

	public void setEthnicity(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(ethnicity);
		selector.selectByVisibleText(type);
	}

	public void setWhoIsFillingOutForm(String type) throws Exception {
		PortalUtil2.PrintMethodName();

		Select selector = new Select(whoIsFillingOutForm);
		selector.selectByVisibleText(type);
	}

	public FormEmergencyContactPage setBasicInfoFromFields() throws Exception {
		setStreetAddress();
		setCity();
		setState(JalapenoConstants.STATE);
		setZip();
		setPrimaryPhoneNumber();
		setPrimaryPhoneType(JalapenoConstants.PRIMARY_PHONE_TYPE);
		setSex(JalapenoConstants.SEX);

		return clickSaveContinue(FormEmergencyContactPage.class);
	}

	public FormEmergencyContactPage setBasicInfoFromFields_20(String state1, String phoneType, String type,
			Boolean formTypePrecheck) throws Exception {
		if (!formTypePrecheck) {
			WebElement w1 = driver.findElement(By.xpath("//iframe[@title='Forms']"));
			driver.switchTo().frame(w1);
		}

		setStreetAddress_20();
		setCity_20();
		setState_20(state1);
		setZip_20();
		setPrimaryPhoneNumber_20();
		setPrimaryPhoneType_20(phoneType);
		setSex(type);

		return clickSaveContinue(FormEmergencyContactPage.class);
	}

	public void setGenderIdentity(String gi) throws Exception {
		Select selector = new Select(genderIdentity);
		selector.selectByVisibleText(gi);
	}

	public void setSexualOrientation(String so) throws Exception {
		Select selector = new Select(sexualOrientation);
		selector.selectByVisibleText(so);
	}

	public void setGenderIdentityDetails(String gi) throws Exception {
		genderIdentityDetail.sendKeys(gi);
	}

	public void setSexualOrientationDetails(String so) throws Exception {
		sexualOrientationDetail.sendKeys(so);
	}

	public FormEmergencyContactPage setBasicInfoFromField() throws Exception {
		setStreetAddress();
		return clickSaveContinue(FormEmergencyContactPage.class);
	}

	public String getGenderQuestionLabel() {
		return genderQuestionLabel.getText().replaceAll("<[^<]+>", "").trim();
	}

	public String getSelectedGender() {
		return new Select(gender).getFirstSelectedOption().getText();
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Basic Information About You")))
				.isDisplayed();
	}

	public WebElement getGenderIdentity() {
		return genderIdentity;
	}

	public WebElement getGenderIdentityDetail() {
		return genderIdentityDetail;
	}

	public WebElement getSexualOrientation() {
		return sexualOrientation;
	}

	public WebElement getSexualOrientationDetial() {
		return sexualOrientationDetail;
	}

	public FormBasicInfoPage saveAndContinue() throws InterruptedException {
		javascriptClick(saveAndContinuebtn);
		return PageFactory.initElements(driver, FormBasicInfoPage.class);
	}

	public void switchFrame() {
		WebElement w1 = driver.findElement(By.xpath("//iframe[@title='Forms']"));
		driver.switchTo().frame(w1);
	}

}
