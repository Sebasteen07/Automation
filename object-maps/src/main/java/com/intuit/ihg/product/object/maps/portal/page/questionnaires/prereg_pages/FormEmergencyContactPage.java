package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormEmergencyContactPage extends PortalFormPage 
{


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
}
