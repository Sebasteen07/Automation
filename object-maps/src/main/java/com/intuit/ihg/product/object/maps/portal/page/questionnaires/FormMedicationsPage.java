package com.intuit.ihg.product.object.maps.portal.page.questionnaires;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormMedicationsPage extends BasePageObject
{


	public FormMedicationsPage(WebDriver driver)  {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="idonot_medications")
	WebElement noMedications;


	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set No Medications
	 * @throws Exception
	 */
	public void setNoMedications() throws Exception 
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		noMedications.click();

	}

	/**
	 * @Description:Click on Save and Continue Form Button
	 * @return
	 * @throws Exception
	 */	 
	public FormAllergiesPage clickSaveAndContinueButton() throws Exception
	{
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormAllergiesPage.class);
	}

	/**
	 * @Description:Set Medication Form Fields
	 * @return FormAllergiesPage
	 * @throws Exception
	 */
	public FormAllergiesPage setMedicationFormFields() throws Exception
	{
		setNoMedications();

		clickSaveAndContinueButton();

		return PageFactory.initElements(driver, FormAllergiesPage.class);

	}

}
