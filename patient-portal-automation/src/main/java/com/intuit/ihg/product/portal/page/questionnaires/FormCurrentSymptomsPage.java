package com.intuit.ihg.product.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormCurrentSymptomsPage extends BasePageObject
{


	public FormCurrentSymptomsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_symptoms_general")
	WebElement noSymptoms;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Symptoms
	 * @throws Exception
	 */
	public void setNoSymptoms() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noSymptoms.click();

	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */
	public FormMedicationsPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormMedicationsPage.class);
	}

	/**
	 * @Description:Set Current Symptoms Form Fields
	 * @return FormMedicationsPage
	 * @throws Exception
	 */
	public FormMedicationsPage setCurrentSymptomsFormFields() throws Exception
	{
		setNoSymptoms();

		clickSaveAndContinueButton();

		return PageFactory.initElements(driver, FormMedicationsPage.class);


	}


}
