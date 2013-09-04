package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormEmergencyContactPage extends BasePageObject
{


	public FormEmergencyContactPage(WebDriver driver) 
	{
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="contactfirstname")
	private WebElement firstName;

	@FindBy(id="contactlastname")
	private WebElement lastName;

	@FindBy(id="relation")
	private WebElement relation;

	@FindBy(id="contactprimaryphone_phone")
	private WebElement primaryPhone;

	@FindBy(id="contactprimaryphone_type")
	private WebElement primaryPhoneType;

	@FindBy(id="contactemail")
	private WebElement email;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;





	/**
	 * @Description:Set First Name
	 * @param input
	 * @throws Exception
	 */
	public void setFirstName(String input) throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		firstName.clear();
		firstName.sendKeys(input);
	}
	/**
	 * @Description:Set Last Name
	 * @param input
	 * @throws Exception
	 */
	public void setLastName(String input) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		lastName.clear();
		lastName.sendKeys(input);
	}
	/**
	 * @Description:Set Relation
	 * @param type
	 * @throws Exception
	 */
	public void setRelation(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(relation);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Set Primary Phone
	 * @param input
	 * @param phoneType
	 * @throws Exception
	 */
	public void setPrimaryPhone (String input, String phoneType) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		primaryPhone.clear();
		primaryPhone.sendKeys(input);
		Select selector = new Select(primaryPhoneType);
		selector.selectByVisibleText(phoneType);
	}


	/**
	 * @Description:Set Email
	 * @param input
	 * @throws Exception
	 */
	public void setEmail(String input) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		email.clear();
		email.sendKeys(input);
	}

	/**
	 * @Description:Click on Save and Continue Button
	 * @return
	 * @throws Exception
	 */
	public FormInsurancePage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormInsurancePage.class);
	}

	/**
	 * @Description:Set Emergency Contact Form Fields
	 * @param pTestData
	 * @return FormInsurancePage
	 * @throws Exception
	 */
	public FormInsurancePage setEmergencyContactFormFields(String pTestData) throws Exception
	{
		setFirstName(PortalConstants.FirstName);

		setLastName(PortalConstants.LastName);

		setRelation(PortalConstants.Relation);

		setPrimaryPhone(PortalUtil.createRandomNumber()+"5",PortalConstants.Mobile);

		if(IHGUtil.getEnvironmentType().equals("DEV3")|| IHGUtil.getEnvironmentType().equals("PROD") ){
		setEmail(PortalUtil.createRandomEmailAddress(pTestData));
		}

		clickSaveAndContinueButton();

		return PageFactory.initElements(driver, FormInsurancePage.class);


	}


}
