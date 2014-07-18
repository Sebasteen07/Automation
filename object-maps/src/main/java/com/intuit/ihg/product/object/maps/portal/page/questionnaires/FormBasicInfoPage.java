package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormBasicInfoPage extends BasePageObject
{

	public FormBasicInfoPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id="streetaddr1")
	private WebElement address;

    @FindBy(id="city")
	private WebElement city;

    @FindBy(id="state")
    private WebElement state;

    @FindBy(id="postalcode")
	private WebElement zip;

    @FindBy(id="primaryphone_phone")
	private WebElement primaryPhone;

    @FindBy(id="primaryphone_type")
    private WebElement primaryPhoneType;
    
    @FindBy(id="gender")
    private WebElement gender;
    
	@FindBy(id="maritalstatus")
	private WebElement maritalStatus;

	@FindBy(id="preferredcontact")
	private WebElement preferredCommunication;

	@FindBy(id="language")
	private WebElement preferredLanguage;

	@FindBy(id="race")
	private WebElement race;

	@FindBy(id="ethnicity")
	private WebElement ethnicity;

	@FindBy(id="formeditor")
	private WebElement whoIsFillingOutForm;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;
	
	@FindBy(className = "save")
	private WebElement save;
	
	/**
	 * @Description:Set street Address 
	 * @param type
	 * @throws Exception
	 */
	public void setStreetAddress() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		address.clear();
		address.sendKeys(PortalUtil.createRandomNumericString(4)+"Required St");
		
	}
	
	/**
	 * @Description:Set City
	 * @param type
	 * @throws Exception
	 */
	public void setCity() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		city.clear();
		city.sendKeys(PortalUtil.createRandomNumericString(4)+"-City");
		
	}
	
	
	/**
	 * @Description:Set State
	 * @param type
	 * @throws Exception
	 */
	public void setState(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(state);
		selector.selectByVisibleText(type);
	}
	
	
	/**
	 * @Description:Set Zip
	 * @param type
	 * @throws Exception
	 */
	public void setZip() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		zip.clear();
		zip.sendKeys(PortalUtil.createRandomNumericString(5));
		
	}
	
	/**
	 * @Description:Set Primary Phone Number
	 * @param type
	 * @throws Exception
	 */
	public void setPrimaryPhoneNumber() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		primaryPhone.clear();
		primaryPhone.sendKeys("919-555-"+PortalUtil.createRandomNumericString(4));
		
	}
	
	/**
	 * @Description:Set Primary Phone Type
	 * @param type
	 * @throws Exception
	 */
	public void setPrimaryPhoneType(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(primaryPhoneType);
		selector.selectByVisibleText(type);
	}
	
	/**
	 * @Description:Set Sex
	 * @param type
	 * @throws Exception
	 */
	public void setSex(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(gender);
		selector.selectByVisibleText(type);
	}


	/**
	 * @Description:Set Marital Status
	 * @param type
	 * @throws Exception
	 */
	public void setMaritalStatus(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(maritalStatus);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Set Preferred Communication
	 * @param type
	 * @throws Exception
	 */
	public void setPreferredCommunication(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(preferredCommunication);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Set Preferred Language
	 * @param type
	 * @throws Exception
	 */
	public void setPreferredLanguage(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(preferredLanguage);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Set Race
	 * @param type
	 * @throws Exception
	 */
	public void setRace(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(race);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Set Ethnicity 
	 * @param type
	 * @throws Exception
	 */
	public void setEthnicity(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(ethnicity);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:set Who Is Filling Out Form
	 * @param type
	 * @throws Exception
	 */
	public void setWhoIsFillingOutForm(String type) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(whoIsFillingOutForm);
		selector.selectByVisibleText(type);
	}

	/**
	 * @brief Click on Continue Button
	 * @param nextPageClass Class of the following page in the form
	 * @return initialized PageObject for the next page
	 * @throws Exception
	 */
	public <T extends BasePageObject> T clickSaveAndContinueButton(Class<T> nextPageClass) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(saveAndContinuebtn));
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, nextPageClass);
	}


	/**
	 * @Description:Set Basic Information Form Fields
	 * @return FormEmergencyContactPage
	 * @throws Exception
	 */
	public FormEmergencyContactPage setBasicInfoFromFields() throws Exception
	{ 
		setStreetAddress();		
		setCity();		
		setState(PortalConstants.State);		
		setZip();	
		setPrimaryPhoneNumber();		
		setPrimaryPhoneType(PortalConstants.PrimaryPhoneType);
		setSex(PortalConstants.Sex);

		return clickSaveAndContinueButton(FormEmergencyContactPage.class);

	}
	
	public void saveAndFinishAnotherTime() throws Exception
	{
		setStreetAddress();		
		setCity();		
		setState(PortalConstants.State);		
		setZip();	
		setPrimaryPhoneNumber();		
		setPrimaryPhoneType(PortalConstants.PrimaryPhoneType);
		setSex(PortalConstants.Sex);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(save));
		
		save.click();
	}

}
