package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import org.testng.annotations.Test;

public class FormBasicInfoPage extends PortalFormPage 
{

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

	@FindBy(id = "altphone_phone")
	private WebElement secondaryPhone;

	@FindBy(id = "altphone_type")
	private WebElement secondaryPhoneType;

    @FindBy(id = "gender")
    private WebElement gender;
    
	@FindBy(id = "maritalstatus")
	private WebElement maritalStatus;

	@FindBy(id = "preferredcontact")
	private WebElement preferredCommunication;

	@FindBy(id = "language")
	private WebElement preferredLanguage;

	@FindBy(id = "idonot_race")
	private WebElement unreportedRace;

	@FindBy(id = "race_white")
	private WebElement whiteCheckbox;

	@FindBy(id = "race_asian")
	private WebElement asianCheckbox;

	@FindBy(id = "ethnicity")
	private WebElement ethnicity;

	@FindBy(id = "formeditor")
	private WebElement whoIsFillingOutForm;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;
	
	@FindBy(className = "save")
	private WebElement save;

	public FormBasicInfoPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Set street Address
	 * @throws Exception
	 */
	public void setStreetAddress() throws Exception {
		PortalUtil.PrintMethodName();
		address.clear();
		address.sendKeys(PortalUtil.createRandomNumericString(4) + "Required St");
	}
	
	/**
	 * Set City
	 * @throws Exception
	 */
	public void setCity() throws Exception {
		PortalUtil.PrintMethodName();
		city.clear();
		city.sendKeys(PortalUtil.createRandomNumericString(4) + "-City");
	}
	
	
	/**
	 * Set State
	 * @throws Exception
	 */
	public void setState(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(state);
		selector.selectByVisibleText(type);
	}
	
	
	/**
	 * Set Zip
	 * @throws Exception
	 */
	public void setZip() throws Exception {
		PortalUtil.PrintMethodName();
		zip.clear();
		zip.sendKeys(PortalUtil.createRandomNumericString(5));
	}
	
	/**
	 * Set Primary Phone Number
	 * @throws Exception
	 */
	public void setPrimaryPhoneNumber() throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		primaryPhone.clear();
		primaryPhone.sendKeys("919-555-" + PortalUtil.createRandomNumericString(4));
	}
	
	/**
	 * Set Primary Phone Type
	 * @param type
	 * @throws Exception
	 */
	public void setPrimaryPhoneType(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(primaryPhoneType);
		selector.selectByVisibleText(type);
	}

	/**
	 * Set Primary Phone Number
	 * @throws Exception
	 */
	public void setSecondaryPhoneNumber(String phoneNumber) throws Exception {
		PortalUtil.PrintMethodName();
		secondaryPhone.clear();
		secondaryPhone.sendKeys(phoneNumber);
	}
	/**
	 * Set Primary Phone Number
	 * @throws Exception
	 */
	public void setSecondaryPhoneNumber() throws Exception {
		setSecondaryPhoneNumber("919-555-" + PortalUtil.createRandomNumericString(4));
	}

	/**
	 * Set Primary Phone Type
	 * @param type
	 * @throws Exception
	 */
	public void setSecondaryPhoneType(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(secondaryPhoneType);
		selector.selectByVisibleText(type);
	}

	/**
	 * Set Sex
	 * @param type
	 * @throws Exception
	 */
	public void setSex(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(gender);
		selector.selectByVisibleText(type);
	}


	/**
	 * Set Marital Status
	 * @param type
	 * @throws Exception
	 */
	public void setMaritalStatus(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(maritalStatus);
		selector.selectByVisibleText(type);
	}

	/**
	 * Set Preferred Communication
	 * @param type
	 * @throws Exception
	 */
	public void setPreferredCommunication(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(preferredCommunication);
		selector.selectByVisibleText(type);
	}

	/**
	 * Set Preferred Language
	 * @param type
	 * @throws Exception
	 */
	public void setPreferredLanguage(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(preferredLanguage);
		selector.selectByVisibleText(type);
	}

	public void clickRefusedToReportRace() {
		unreportedRace.click();
	}

	public void clickWhiteRaceCheckbox() {
		whiteCheckbox.click();
	}

	public void clickAsianRaceCheckbox() {
		asianCheckbox.click();
	}

	/**
	 * Set Ethnicity
	 * @param type
	 * @throws Exception
	 */
	public void setEthnicity(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(ethnicity);
		selector.selectByVisibleText(type);
	}

	/**
	 * Set Who Is Filling Out Form
	 * @param type
	 * @throws Exception
	 */
	public void setWhoIsFillingOutForm(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(whoIsFillingOutForm);
		selector.selectByVisibleText(type);
	}

	/**
	 * Set Basic Information Form Fields
	 * @return FormEmergencyContactPage
	 * @throws Exception
	 */
	public FormEmergencyContactPage setBasicInfoFromFields() throws Exception { 
		setStreetAddress();		
		setCity();		
		setState(PortalConstants.State);		
		setZip();	
		setPrimaryPhoneNumber();		
		setPrimaryPhoneType(PortalConstants.PrimaryPhoneType);
		setSex(PortalConstants.Sex);

		return clickSaveContinue(FormEmergencyContactPage.class);
	}

	public FormEmergencyContactPage setBasicInfoFromField() throws Exception {
		setStreetAddress();		
		return clickSaveContinue(FormEmergencyContactPage.class);
	}

	@Override
	public void testValidation() throws Exception {
		assertErrorMessageAfterContinuing();

		setMaritalStatus("Single");
		setWhoIsFillingOutForm("Parent");
		setSecondaryPhoneNumber("123456");

		assertErrorMessageAfterContinuing();

		setSecondaryPhoneType("Mobile");

		assertErrorMessageAfterContinuing("Alternate Phone Number*");

		setSecondaryPhoneNumber();
		setPreferredLanguage("English");
		clickRefusedToReportRace();

		assertErrorMessageAfterContinuing("Race*");

		clickWhiteRaceCheckbox();
		clickAsianRaceCheckbox();
	}

}
